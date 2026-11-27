package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.MathProblem
import com.example.ui.components.Grade5AngleDiagram
import com.example.ui.components.KidNumberPad
import com.example.ui.theme.*
import com.example.viewmodel.AnswerCheckResult
import com.example.viewmodel.AnswerChecker
import kotlin.math.abs

@Composable
fun EstimateOpRenderer(
  problem: MathProblem.EstimateOpProblem,
  onResult: (AnswerCheckResult) -> Unit,
) {
  if (problem.choiceOptions != null && problem.correctChoice != null) {
    var selected by remember(problem.itemNumber) { mutableStateOf<String?>(null) }
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(12.dp)) {
      if (problem.promptText != null) {
        Surface(color = WarmSurfaceVariant, shape = RoundedCornerShape(16.dp), modifier = Modifier.fillMaxWidth()) {
          Text(
            text = problem.promptText,
            style = MaterialTheme.typography.titleMedium,
            color = InkDark,
            modifier = Modifier.padding(14.dp),
          )
        }
      }
      problem.choiceOptions.forEach { option ->
        Button(
          onClick = {
            selected = option
            val ok = option == problem.correctChoice
            onResult(
              AnswerCheckResult(
                isCorrect = ok,
                message = if (ok) "Smart estimate! ₱200 is cheaper than ₱250." else "Round both prices and compare the estimated totals.",
                expectedDisplay = problem.correctChoice,
              ),
            )
          },
          modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 54.dp),
          shape = RoundedCornerShape(16.dp),
          colors = ButtonDefaults.buttonColors(
            containerColor = if (selected == option) LeafEmerald else BagacayBlue,
            contentColor = Color.White,
          ),
        ) {
          Text(option, fontSize = 15.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
        }
      }
    }
    return
  }

  val count = problem.roundedFactors.size
  val factorInputs = remember(problem.itemNumber) { mutableStateListOf<String>().apply { repeat(count) { add("") } } }
  var productInput by remember(problem.itemNumber) { mutableStateOf("") }
  var activeSlot by remember(problem.itemNumber) { mutableStateOf(0) } // 0..count-1 rounded factors, count = product

  Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(10.dp)) {
    if (problem.promptText != null) {
      Surface(color = WarmSurfaceVariant, shape = RoundedCornerShape(16.dp), modifier = Modifier.fillMaxWidth()) {
        Text(
          text = problem.promptText,
          style = MaterialTheme.typography.titleMedium,
          color = InkDark,
          modifier = Modifier.padding(14.dp),
        )
      }
    } else {
      Text("Round each factor to its highest place, then multiply:", style = MaterialTheme.typography.titleMedium, color = InkDark, textAlign = TextAlign.Center)
      Surface(color = WarmSurfaceVariant, shape = RoundedCornerShape(18.dp), modifier = Modifier.fillMaxWidth()) {
        Text(
          text = problem.factors.joinToString("  ×  ") { "%,d".format(it) },
          fontSize = 26.sp,
          fontWeight = FontWeight.Bold,
          color = InkDark,
          textAlign = TextAlign.Center,
          modifier = Modifier.padding(14.dp),
        )
      }
    }

    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
      problem.factors.forEachIndexed { idx, f ->
        FieldBox(
          label = "Round ${"%,d".format(f)}",
          value = factorInputs.getOrElse(idx) { "" },
          active = activeSlot == idx,
          modifier = Modifier.weight(1f),
        ) { activeSlot = idx }
      }
    }
    FieldBox(
      label = "Estimated Product",
      value = productInput,
      active = activeSlot == count,
      modifier = Modifier.fillMaxWidth(),
    ) { activeSlot = count }

    KidNumberPad(
      onDigit = { d ->
        if (activeSlot < count) {
          val cur = factorInputs[activeSlot]
          if (cur.length < 7) factorInputs[activeSlot] = cur + d
        } else {
          if (productInput.length < 10) productInput += d
        }
      },
      onBackspace = {
        if (activeSlot < count) {
          val cur = factorInputs[activeSlot]
          if (cur.isNotEmpty()) factorInputs[activeSlot] = cur.dropLast(1)
        } else if (productInput.isNotEmpty()) {
          productInput = productInput.dropLast(1)
        }
      },
      onClear = {
        if (activeSlot < count) factorInputs[activeSlot] = "" else productInput = ""
      },
      onSubmit = {
        val factorsOk = factorInputs.indices.all { i ->
          AnswerChecker.normalizeDigits(factorInputs[i]).toIntOrNull() == problem.roundedFactors[i]
        }
        val prodOk = AnswerChecker.normalizeDigits(productInput).toLongOrNull() == problem.expectedEstimate
        val ok = factorsOk && prodOk
        val expectedText = "${problem.roundedFactors.joinToString(" × ") { "%,d".format(it) }} = ${"%,d".format(problem.expectedEstimate)}"
        onResult(
          AnswerCheckResult(
            isCorrect = ok,
            message = if (ok) "Great estimation!" else "Check each rounded factor and their product.",
            expectedDisplay = expectedText,
          ),
        )
      },
      submitEnabled = factorInputs.all { it.isNotEmpty() } && productInput.isNotEmpty(),
      accentColor = LeafEmerald,
    )
  }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MentalMultiplyRenderer(
  problem: MathProblem.MentalMultiplyProblem,
  onResult: (AnswerCheckResult) -> Unit,
) {
  var productInput by remember(problem.itemNumber) { mutableStateOf("") }
  var selectedStrategy by remember(problem.itemNumber) { mutableStateOf<String?>(null) }

  Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(10.dp)) {
    Text("Multiply mentally & pick your strategy!", style = MaterialTheme.typography.titleMedium, color = InkDark)
    Surface(color = BagacayBlueLight.copy(alpha = 0.55f), shape = RoundedCornerShape(20.dp), modifier = Modifier.fillMaxWidth()) {
      Text(
        text = "${problem.factorA}  ×  ${problem.factorB}  =  ?",
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold,
        color = InkDark,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(vertical = 18.dp),
      )
    }

    Text("Mental Strategy Used:", style = MaterialTheme.typography.labelLarge, color = InkMuted)
    FlowRow(
      horizontalArrangement = Arrangement.spacedBy(8.dp),
      verticalArrangement = Arrangement.spacedBy(6.dp),
      modifier = Modifier.fillMaxWidth(),
    ) {
      problem.strategyChoices.forEach { strat ->
        FilterChip(
          selected = selectedStrategy == strat,
          onClick = { selectedStrategy = strat },
          label = { Text(strat, fontSize = 12.sp) },
          modifier = Modifier.defaultMinSize(minHeight = 48.dp),
        )
      }
    }

    AnswerDisplayBox(text = productInput, placeholder = "Mental product")
    KidNumberPad(
      onDigit = { if (productInput.length < 6) productInput += it },
      onBackspace = { if (productInput.isNotEmpty()) productInput = productInput.dropLast(1) },
      onClear = { productInput = "" },
      onSubmit = {
        val prodOk = AnswerChecker.normalizeDigits(productInput).toIntOrNull() == problem.answer
        val hasStrat = selectedStrategy != null
        val ok = prodOk && hasStrat
        val msg = when {
          !hasStrat -> "Pick a mental math strategy chip too! (Tip: ${problem.recommendedStrategy})"
          prodOk -> "Sharp mental math! Tip used: ${problem.recommendedStrategy}."
          else -> "Try breaking apart the numbers or using ${problem.recommendedStrategy}."
        }
        onResult(AnswerCheckResult(ok, msg, "${problem.answer} (${problem.recommendedStrategy})"))
      },
      submitEnabled = productInput.isNotEmpty() && selectedStrategy != null,
      accentColor = LeafEmerald,
    )
  }
}

@Composable
fun CompatibleEstimateRenderer(
  problem: MathProblem.CompatibleEstimateProblem,
  onResult: (AnswerCheckResult) -> Unit,
) {
  // Mode 1: choose best estimated quotient from 3 buttons
  if (problem.estimatedOptions != null) {
    var picked by remember(problem.itemNumber) { mutableStateOf<Int?>(null) }
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(12.dp)) {
      Text("Choose the best estimated quotient:", style = MaterialTheme.typography.titleMedium, color = InkDark)
      Surface(color = WarmSurfaceVariant, shape = RoundedCornerShape(18.dp), modifier = Modifier.fillMaxWidth()) {
        Text(
          text = "${"%,d".format(problem.dividend)}  ÷  ${"%,d".format(problem.divisor)}  ≈  ?",
          fontSize = 28.sp,
          fontWeight = FontWeight.Bold,
          color = InkDark,
          textAlign = TextAlign.Center,
          modifier = Modifier.padding(16.dp),
        )
      }
      Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
        problem.estimatedOptions.forEach { opt ->
          Button(
            onClick = {
              picked = opt
              val ok = opt in problem.acceptedQuotients
              onResult(AnswerCheckResult(ok, if (ok) "Bullseye estimate!" else "Think of a compatible multiple near ${problem.dividend}.", "${problem.primaryEstimate}"))
            },
            modifier = Modifier.weight(1f).height(58.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
              containerColor = if (picked == opt) TealOcean else BagacayBlue,
              contentColor = Color.White,
            ),
          ) {
            Text("%,d".format(opt), fontSize = 18.sp, fontWeight = FontWeight.Bold)
          }
        }
      }
    }
    return
  }

  // Mode 2: compatible expression choices + quotient input; OR Mode 3: open compatible quotient input
  var chosenExprIdx by remember(problem.itemNumber) { mutableStateOf<Int?>(null) }
  var qInput by remember(problem.itemNumber) { mutableStateOf("") }

  Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(10.dp)) {
    if (problem.promptText != null) {
      Surface(color = WarmSurfaceVariant, shape = RoundedCornerShape(16.dp), modifier = Modifier.fillMaxWidth()) {
        Text(problem.promptText, style = MaterialTheme.typography.titleMedium, color = InkDark, modifier = Modifier.padding(14.dp))
      }
    } else {
      Text(
        text = if (problem.compatibleOptions != null) "Select the best compatible expression & enter the estimate:" else "Estimate the quotient using compatible numbers:",
        style = MaterialTheme.typography.titleMedium,
        color = InkDark,
        textAlign = TextAlign.Center,
      )
      Surface(color = TealOcean.copy(alpha = 0.14f), shape = RoundedCornerShape(18.dp), modifier = Modifier.fillMaxWidth()) {
        Text(
          text = "${"%,d".format(problem.dividend)}  ÷  ${"%,d".format(problem.divisor)}",
          fontSize = 28.sp,
          fontWeight = FontWeight.Bold,
          color = InkDark,
          textAlign = TextAlign.Center,
          modifier = Modifier.padding(14.dp),
        )
      }
    }

    if (problem.compatibleOptions != null) {
      Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
        problem.compatibleOptions.forEachIndexed { idx, expr ->
          val isSelected = chosenExprIdx == idx
          OutlinedButton(
            onClick = { chosenExprIdx = idx },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.outlinedButtonColors(
              containerColor = if (isSelected) TealOcean.copy(alpha = 0.18f) else Color.White,
              contentColor = InkDark,
            ),
          ) {
            Text("${('a' + idx)})  $expr", fontSize = 16.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium)
          }
        }
      }
    }

    AnswerDisplayBox(text = qInput, placeholder = "Estimated quotient")
    KidNumberPad(
      onDigit = { if (qInput.length < 7) qInput += it },
      onBackspace = { if (qInput.isNotEmpty()) qInput = qInput.dropLast(1) },
      onClear = { qInput = "" },
      onSubmit = {
        val parsed = AnswerChecker.normalizeDigits(qInput).toIntOrNull()
        val exprOk = problem.correctCompatibleIndex == null || chosenExprIdx == problem.correctCompatibleIndex
        val qOk = parsed != null && (
          parsed in problem.acceptedQuotients ||
            (problem.compatibleOptions == null && abs(parsed - problem.primaryEstimate) <= maxOf(5, problem.primaryEstimate / 8))
          )
        val ok = exprOk && qOk
        val expectedDesc = if (problem.exampleCompatibleExpr.isNotBlank()) {
          "${problem.exampleCompatibleExpr} = ${problem.primaryEstimate}"
        } else {
          "${problem.primaryEstimate}"
        }
        onResult(AnswerCheckResult(ok, if (ok) "Awesome compatible estimate!" else "Check the compatible numbers and divide mentally.", expectedDesc))
      },
      submitEnabled = qInput.isNotEmpty() && (problem.compatibleOptions == null || chosenExprIdx != null),
      accentColor = TealOcean,
    )
  }
}

@Composable
fun WordProblemMultiFieldRenderer(
  problem: MathProblem.WordProblemMultiFieldProblem,
  onResult: (AnswerCheckResult) -> Unit,
) {
  val inputs = remember(problem.itemNumber) { mutableStateListOf<String>().apply { repeat(problem.fields.size) { add("") } } }
  var activeIdx by remember(problem.itemNumber) { mutableStateOf(0) }
  val anyDecimal = problem.fields.any { it.allowDecimal }

  Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(10.dp)) {
    Surface(color = WarmSurfaceVariant, shape = RoundedCornerShape(16.dp), modifier = Modifier.fillMaxWidth()) {
      Text(
        text = problem.promptText,
        style = MaterialTheme.typography.titleMedium,
        color = InkDark,
        modifier = Modifier.padding(14.dp),
      )
    }

    if (problem.fields.size == 1) {
      val f = problem.fields.first()
      AnswerDisplayBox(
        text = inputs[0],
        placeholder = f.unitLabel?.let { "${f.label} ($it)" } ?: f.label,
      )
    } else {
      Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
        problem.fields.forEachIndexed { idx, spec ->
          FieldBox(
            label = spec.label,
            value = inputs.getOrElse(idx) { "" },
            active = activeIdx == idx,
            modifier = Modifier.weight(1f),
          ) { activeIdx = idx }
        }
      }
    }

    KidNumberPad(
      showDecimal = anyDecimal,
      onDigit = { ch ->
        if (activeIdx in inputs.indices) {
          val cur = inputs[activeIdx]
          if (ch == "." && "." in cur) return@KidNumberPad
          if (cur.length < 11) inputs[activeIdx] = cur + ch
        }
      },
      onBackspace = {
        if (activeIdx in inputs.indices) {
          val cur = inputs[activeIdx]
          if (cur.isNotEmpty()) inputs[activeIdx] = cur.dropLast(1)
        }
      },
      onClear = {
        if (activeIdx in inputs.indices) inputs[activeIdx] = ""
      },
      onSubmit = {
        val ok = problem.fields.indices.all { i ->
          val spec = problem.fields[i]
          val parsed = AnswerChecker.normalizeDigits(inputs[i]).toDoubleOrNull() ?: return@all false
          val candidates = listOf(spec.expectedAnswer) + spec.alternateAnswers
          candidates.any { abs(parsed - it) <= 0.02 }
        }
        val expectedStr = problem.fields.joinToString(" | ") { spec ->
          val numStr = if (spec.expectedAnswer % 1.0 == 0.0) {
            "%,d".format(spec.expectedAnswer.toLong())
          } else {
            spec.expectedAnswer.toString()
          }
          if (spec.unitLabel != null) "$numStr ${spec.unitLabel}" else numStr
        }
        onResult(AnswerCheckResult(ok, if (ok) "Problem solved cleanly!" else "Re-read each step and check your arithmetic.", expectedStr))
      },
      submitEnabled = inputs.all { it.isNotEmpty() && it != "." },
      accentColor = CoralTangerine,
    )
  }
}

@Composable
fun OrderOfOpsRenderer(
  problem: MathProblem.OrderOfOpsProblem,
  onResult: (AnswerCheckResult) -> Unit,
) {
  var input by remember(problem.itemNumber) { mutableStateOf("") }

  Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(10.dp)) {
    Surface(color = MangoGoldLight.copy(alpha = 0.55f), shape = RoundedCornerShape(12.dp)) {
      Text(
        "PMDAS: Parentheses → Multiply/Divide (L→R) → Add/Subtract (L→R)",
        style = MaterialTheme.typography.labelMedium,
        fontWeight = FontWeight.Bold,
        color = MangoDark,
        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
        textAlign = TextAlign.Center,
      )
    }

    if (problem.promptText != null) {
      Surface(color = WarmSurfaceVariant, shape = RoundedCornerShape(16.dp), modifier = Modifier.fillMaxWidth()) {
        Text(problem.promptText, style = MaterialTheme.typography.titleMedium, color = InkDark, modifier = Modifier.padding(14.dp))
      }
    }

    Surface(color = BagacayBlueLight.copy(alpha = 0.45f), shape = RoundedCornerShape(18.dp), modifier = Modifier.fillMaxWidth()) {
      Text(
        text = "${problem.expression}  =  ?",
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = InkDark,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(16.dp),
      )
    }

    AnswerDisplayBox(text = input, placeholder = "Final value")
    KidNumberPad(
      showDecimal = true,
      onDigit = { ch ->
        if (ch == "." && "." in input) return@KidNumberPad
        if (input.length < 12) input += ch
      },
      onBackspace = { if (input.isNotEmpty()) input = input.dropLast(1) },
      onClear = { input = "" },
      onSubmit = {
        val parsed = AnswerChecker.normalizeDigits(input).toDoubleOrNull()
        val ok = parsed != null && abs(parsed - problem.expectedValue) <= 0.01
        onResult(AnswerCheckResult(ok, if (ok) "PMDAS mastered!" else "Follow PMDAS step by step from left to right.", problem.expectedDisplay))
      },
      submitEnabled = input.isNotEmpty() && input != ".",
      accentColor = MangoDark,
    )
  }
}

internal fun evaluateAngleNameInput(
  rawInput: String,
  validAnswers: List<String>,
): Pair<Boolean, String> {
  val cleaned = rawInput
    .trim()
    .uppercase()
    .replace(" ", "")
    .trimStart('∠', '<')
  val normalizedValid = validAnswers.map {
    it.trim().uppercase().replace(" ", "").trimStart('∠', '<')
  }
  val ok = cleaned.isNotEmpty() && cleaned in normalizedValid
  val formatted = if (cleaned.isNotEmpty()) "∠$cleaned" else ""
  return ok to formatted
}

@Composable
fun AngleNameRenderer(
  problem: MathProblem.AngleNameProblem,
  onResult: (AnswerCheckResult) -> Unit,
) {
  var textInput by remember(problem.itemNumber) { mutableStateOf("∠") }

  val quickLetters = remember(problem.validAnswers) {
    problem.validAnswers
      .flatMap { it.replace("∠", "").toList() }
      .distinct()
      .map { it.toString() }
  }

  Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(10.dp)) {
    Text(problem.prompt, style = MaterialTheme.typography.titleMedium, color = InkDark, textAlign = TextAlign.Center)
    Grade5AngleDiagram(diagramId = problem.diagramId, highlightIndex = problem.highlightIndex)

    OutlinedTextField(
      value = textInput,
      onValueChange = { textInput = it.uppercase() },
      label = { Text("Angle name (e.g. ∠ABC or ABC)") },
      singleLine = true,
      modifier = Modifier
        .fillMaxWidth()
        .testTag("angle_name_input"),
    )

    if (quickLetters.isNotEmpty()) {
      @OptIn(ExperimentalLayoutApi::class)
      FlowRow(
        horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally),
        verticalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier.fillMaxWidth(),
      ) {
        FilledTonalButton(
          onClick = { if (!textInput.startsWith("∠")) textInput = "∠$textInput" },
          modifier = Modifier.defaultMinSize(minHeight = 48.dp),
        ) { Text("∠", fontWeight = FontWeight.Bold) }
        quickLetters.forEach { ch ->
          FilledTonalButton(
            onClick = { if (textInput.length < 6) textInput += ch },
            modifier = Modifier.defaultMinSize(minHeight = 48.dp),
          ) { Text(ch, fontWeight = FontWeight.Bold) }
        }
        OutlinedButton(
          onClick = { textInput = "∠" },
          modifier = Modifier.defaultMinSize(minHeight = 48.dp),
        ) { Text("Clear") }
      }
    }

    Button(
      onClick = {
        val (ok, enteredAngle) = evaluateAngleNameInput(textInput, problem.validAnswers)
        onResult(
          AnswerCheckResult(
            isCorrect = ok,
            message = if (ok) "Great angle naming! $enteredAngle" else "Put the vertex letter in the middle or use the interior number.",
            expectedDisplay = if (ok) enteredAngle else problem.validAnswers.take(3).joinToString(" / "),
          ),
        )
      },
      enabled = textInput.trim().trimStart('∠', '<').isNotBlank(),
      modifier = Modifier
        .fillMaxWidth()
        .height(54.dp)
        .testTag("submit_angle_name"),
      shape = RoundedCornerShape(18.dp),
      colors = ButtonDefaults.buttonColors(containerColor = BerryPurple),
    ) {
      Text("Check Angle Name", fontSize = 16.sp, fontWeight = FontWeight.Bold)
    }
  }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AngleKindRenderer(
  problem: MathProblem.AngleKindProblem,
  onResult: (AnswerCheckResult) -> Unit,
) {
  var selected by remember(problem.itemNumber) { mutableStateOf<String?>(null) }

  Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(10.dp)) {
    Text(problem.prompt, style = MaterialTheme.typography.titleMedium, color = InkDark, textAlign = TextAlign.Center)
    Grade5AngleDiagram(diagramId = problem.diagramId)

    if (problem.isSingleClassify) {
      Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
        problem.options.forEach { opt ->
          val btnColor = when (opt) {
            "Acute" -> BagacayBlue
            "Right" -> LeafEmerald
            else -> BerryPurple
          }
          Button(
            onClick = {
              selected = opt
              val ok = opt in problem.validAnswers
              onResult(
                AnswerCheckResult(
                  isCorrect = ok,
                  message = if (ok) "Spot on! That is a $opt angle." else "Compare the opening with a 90° square corner!",
                  expectedDisplay = problem.validAnswers.first(),
                ),
              )
            },
            modifier = Modifier
              .weight(1f)
              .height(56.dp)
              .testTag("angle_kind_$opt"),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = btnColor),
          ) {
            Text(opt, fontWeight = FontWeight.Bold, fontSize = 15.sp)
          }
        }
      }
    } else {
      FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth(),
      ) {
        problem.options.forEach { opt ->
          val isChosen = selected == opt
          Button(
            onClick = {
              selected = opt
              val cleaned = opt.trim().uppercase().removePrefix("∠")
              val ok = problem.validAnswers.any { it.trim().uppercase().removePrefix("∠") == cleaned }
              onResult(
                AnswerCheckResult(
                  isCorrect = ok,
                  message = if (ok) "Nice eye! $opt matches!" else "Trace the vertex and rays carefully on the diagram.",
                  expectedDisplay = problem.validAnswers.take(3).joinToString(" / "),
                ),
              )
            },
            modifier = Modifier.defaultMinSize(minWidth = 88.dp, minHeight = 48.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
              containerColor = if (isChosen) BerryPurple else BagacayBlueLight,
              contentColor = if (isChosen) Color.White else InkDark,
            ),
          ) {
            Text(opt, fontWeight = FontWeight.Bold)
          }
        }
      }
    }
  }
}
