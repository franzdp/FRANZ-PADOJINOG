package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import com.example.model.FractionItem
import com.example.model.MathProblem
import com.example.model.PatternTokenKind
import com.example.ui.components.*
import com.example.ui.theme.*
import com.example.viewmodel.AnswerCheckResult
import com.example.viewmodel.AnswerChecker

@Composable
fun OddEvenRenderer(
  problem: MathProblem.OddEvenProblem,
  accentColor: Color,
  onResult: (AnswerCheckResult) -> Unit,
) {
  val digits = problem.number.toString()
  Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(16.dp)) {
    Text("Is this number ODD or EVEN?", style = MaterialTheme.typography.titleLarge, color = InkDark)
    Row(
      horizontalArrangement = Arrangement.Center,
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier
        .clip(RoundedCornerShape(24.dp))
        .background(WarmSurfaceVariant)
        .padding(horizontal = 28.dp, vertical = 18.dp),
    ) {
      digits.forEachIndexed { idx, ch ->
        val isLast = idx == digits.lastIndex
        Text(
          text = ch.toString(),
          fontSize = 48.sp,
          fontWeight = FontWeight.Bold,
          color = if (isLast) accentColor else InkDark,
        )
      }
    }
    Text("Tip: Check the colored last digit!", style = MaterialTheme.typography.bodyMedium, color = InkMuted)
    Row(horizontalArrangement = Arrangement.spacedBy(14.dp), modifier = Modifier.fillMaxWidth()) {
      Button(
        onClick = {
          val ok = !problem.isEven
          onResult(AnswerCheckResult(ok, if (ok) "Yes! ${problem.number} ends in ${digits.last()}, so it is ODD." else "Look at the last digit ${digits.last()}.", "ODD"))
        },
        modifier = Modifier.weight(1f).height(64.dp).testTag("btn_odd"),
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(containerColor = CoralTangerine),
      ) {
        Text("ODD", fontSize = 20.sp, fontWeight = FontWeight.Bold)
      }
      Button(
        onClick = {
          val ok = problem.isEven
          onResult(AnswerCheckResult(ok, if (ok) "Yes! ${problem.number} ends in ${digits.last()}, so it is EVEN." else "Look at the last digit ${digits.last()}.", "EVEN"))
        },
        modifier = Modifier.weight(1f).height(64.dp).testTag("btn_even"),
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(containerColor = BagacayBlue),
      ) {
        Text("EVEN", fontSize = 20.sp, fontWeight = FontWeight.Bold)
      }
    }
  }
}

@Composable
fun CompareFractionsRenderer(
  problem: MathProblem.CompareFractionsProblem,
  onResult: (AnswerCheckResult) -> Unit,
) {
  var selected by remember(problem.itemNumber) { mutableStateOf<String?>(null) }
  Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(14.dp)) {
    Text(
      text = if (problem.isSimilar) "Compare Similar Fractions" else "Compare Dissimilar Fractions",
      style = MaterialTheme.typography.titleLarge,
      color = InkDark,
    )
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceEvenly,
      verticalAlignment = Alignment.CenterVertically,
    ) {
      FractionTile(problem.leftLabel, BagacayBlueLight)
      Box(
        modifier = Modifier
          .size(64.dp)
          .clip(RoundedCornerShape(18.dp))
          .background(MangoGoldLight)
          .border(2.dp, MangoGold, RoundedCornerShape(18.dp)),
        contentAlignment = Alignment.Center,
      ) {
        Text(selected ?: "?", fontSize = 30.sp, fontWeight = FontWeight.Bold, color = InkDark)
      }
      FractionTile(problem.rightLabel, CoralLight)
    }
    FractionComparisonBars(problem.leftNum, problem.leftDen, problem.rightNum, problem.rightDen)
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
      listOf("<", ">", "=").forEach { sym ->
        Button(
          onClick = {
            selected = sym
            val ok = sym == problem.answerSymbol
            onResult(
              AnswerCheckResult(
                isCorrect = ok,
                message = if (ok) "Awesome! ${problem.leftLabel} ${problem.answerSymbol} ${problem.rightLabel}." else "Look at the bars or cross-multiply!",
                expectedDisplay = problem.answerSymbol,
              ),
            )
          },
          modifier = Modifier.weight(1f).height(58.dp).testTag("cmp_$sym"),
          shape = RoundedCornerShape(18.dp),
          colors = ButtonDefaults.buttonColors(
            containerColor = if (selected == sym) BagacayBlue else WarmSurfaceVariant,
            contentColor = if (selected == sym) Color.White else InkDark,
          ),
        ) {
          Text(sym, fontSize = 26.sp, fontWeight = FontWeight.Bold)
        }
      }
    }
  }
}

@Composable
private fun FractionTile(label: String, bg: Color) {
  Box(
    modifier = Modifier
      .clip(RoundedCornerShape(18.dp))
      .background(bg)
      .padding(horizontal = 18.dp, vertical = 14.dp),
    contentAlignment = Alignment.Center,
  ) {
    Text(label, fontSize = 26.sp, fontWeight = FontWeight.Bold, color = InkDark)
  }
}

@Composable
fun OrderFractionsRenderer(
  problem: MathProblem.OrderFractionsProblem,
  onResult: (AnswerCheckResult) -> Unit,
) {
  val chosen = remember(problem.itemNumber) { mutableStateListOf<FractionItem>() }
  val available = problem.fractions.filter { it !in chosen }

  Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(14.dp)) {
    Text(
      text = if (problem.ascending) "Arrange from LEAST to GREATEST (Ascending)" else "Arrange from GREATEST to LEAST (Descending)",
      style = MaterialTheme.typography.titleMedium,
      color = InkDark,
      textAlign = TextAlign.Center,
    )
    // Slots
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
      repeat(problem.fractions.size) { idx ->
        val item = chosen.getOrNull(idx)
        Box(
          modifier = Modifier
            .weight(1f)
            .height(60.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(if (item != null) BagacayBlueLight else WarmSurfaceVariant)
            .border(1.5.dp, if (item != null) BagacayBlue else OutlineSoft, RoundedCornerShape(16.dp))
            .clickable(enabled = item != null) { if (item != null) chosen.remove(item) },
          contentAlignment = Alignment.Center,
        ) {
          Text(
            text = item?.label ?: "${idx + 1}",
            fontSize = if (item != null) 18.sp else 14.sp,
            fontWeight = FontWeight.Bold,
            color = if (item != null) InkDark else InkMuted,
          )
        }
      }
    }
    Text("Tap fractions below in order (tap placed tile to undo):", style = MaterialTheme.typography.bodyMedium, color = InkMuted)
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
      available.forEach { frac ->
        FilledTonalButton(
          onClick = { chosen.add(frac) },
          modifier = Modifier.weight(1f).height(54.dp).testTag("frac_pick_${frac.label}"),
          shape = RoundedCornerShape(16.dp),
        ) {
          Text(frac.label, fontSize = 17.sp, fontWeight = FontWeight.Bold)
        }
      }
    }
    Button(
      onClick = {
        val ok = chosen.toList() == problem.expectedOrder
        onResult(
          AnswerCheckResult(
            isCorrect = ok,
            message = if (ok) "Great ordering!" else "Check numerators/denominators and try again.",
            expectedDisplay = problem.expectedOrder.joinToString(", ") { it.label },
          ),
        )
      },
      enabled = chosen.size == problem.fractions.size,
      modifier = Modifier.fillMaxWidth().height(54.dp).testTag("submit_order_fracs"),
      shape = RoundedCornerShape(18.dp),
    ) {
      Text("Check Order", fontSize = 16.sp, fontWeight = FontWeight.Bold)
    }
  }
}

@Composable
fun FractionPartRenderer(
  problem: MathProblem.FractionPartProblem,
  onResult: (AnswerCheckResult) -> Unit,
) {
  var input by remember(problem.itemNumber) { mutableStateOf("") }
  val expected = if (problem.askNumerator) problem.num else problem.den
  Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(12.dp)) {
    Text(
      text = if (problem.askNumerator) "What is the NUMERATOR?" else "What is the DENOMINATOR?",
      style = MaterialTheme.typography.titleLarge,
      color = InkDark,
    )
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier
        .clip(RoundedCornerShape(20.dp))
        .background(WarmSurfaceVariant)
        .padding(horizontal = 32.dp, vertical = 14.dp),
    ) {
      Text("${problem.num}", fontSize = 36.sp, fontWeight = FontWeight.Bold, color = if (problem.askNumerator) BagacayBlue else InkDark)
      HorizontalDivider(modifier = Modifier.width(64.dp), thickness = 3.dp, color = InkDark)
      Text("${problem.den}", fontSize = 36.sp, fontWeight = FontWeight.Bold, color = if (!problem.askNumerator) CoralTangerine else InkDark)
    }
    AnswerDisplayBox(text = input, placeholder = "Enter number")
    KidNumberPad(
      onDigit = { if (input.length < 4) input += it },
      onBackspace = { if (input.isNotEmpty()) input = input.dropLast(1) },
      onClear = { input = "" },
      onSubmit = {
        val ok = input.toIntOrNull() == expected
        onResult(AnswerCheckResult(ok, if (ok) "Correct!" else "Remember: numerator is on top, denominator is on bottom.", "$expected"))
      },
      submitEnabled = input.isNotEmpty(),
    )
  }
}

@Composable
fun ShadedFigureRenderer(
  problem: MathProblem.ShadedFigureFractionProblem,
  onResult: (AnswerCheckResult) -> Unit,
) {
  var numInput by remember(problem.itemNumber) { mutableStateOf("") }
  var denInput by remember(problem.itemNumber) { mutableStateOf("") }
  var fracInput by remember(problem.itemNumber) { mutableStateOf("") }
  var activeField by remember(problem.itemNumber) { mutableStateOf(if (problem.askBreakdown) 0 else 2) }

  Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(10.dp)) {
    Text(problem.prompt, style = MaterialTheme.typography.titleMedium, color = InkDark, textAlign = TextAlign.Center)
    ShadedFigureCanvas(problem.figureType)
    if (problem.askBreakdown) {
      Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
        FieldBox("Numerator", numInput, activeField == 0, Modifier.weight(1f)) { activeField = 0 }
        FieldBox("Denominator", denInput, activeField == 1, Modifier.weight(1f)) { activeField = 1 }
        FieldBox("Fraction", fracInput, activeField == 2, Modifier.weight(1f)) { activeField = 2 }
      }
    } else {
      AnswerDisplayBox(text = fracInput, placeholder = "e.g. 3/4")
    }
    KidNumberPad(
      showSlash = !problem.askBreakdown || activeField == 2,
      onDigit = { ch ->
        when (activeField) {
          0 -> if (ch != "/" && numInput.length < 3) numInput += ch
          1 -> if (ch != "/" && denInput.length < 3) denInput += ch
          else -> if (fracInput.length < 6) fracInput += ch
        }
      },
      onBackspace = {
        when (activeField) {
          0 -> if (numInput.isNotEmpty()) numInput = numInput.dropLast(1)
          1 -> if (denInput.isNotEmpty()) denInput = denInput.dropLast(1)
          else -> if (fracInput.isNotEmpty()) fracInput = fracInput.dropLast(1)
        }
      },
      onClear = {
        when (activeField) {
          0 -> numInput = ""
          1 -> denInput = ""
          else -> fracInput = ""
        }
      },
      onSubmit = {
        val ok = if (problem.askBreakdown) {
          numInput.toIntOrNull() == problem.num &&
            denInput.toIntOrNull() == problem.den &&
            AnswerChecker.checkFractionInput(fracInput, problem.num, problem.den)
        } else {
          AnswerChecker.checkFractionInput(fracInput, problem.num, problem.den)
        }
        onResult(AnswerCheckResult(ok, if (ok) "Awesome! Shaded part is ${problem.num}/${problem.den}." else "Count shaded parts over total equal parts!", "${problem.num}/${problem.den}"))
      },
      submitEnabled = if (problem.askBreakdown) numInput.isNotEmpty() && denInput.isNotEmpty() && fracInput.isNotEmpty() else fracInput.isNotEmpty(),
    )
  }
}

@Composable
fun PictureFractionRenderer(
  problem: MathProblem.PictureFractionProblem,
  onResult: (AnswerCheckResult) -> Unit,
) {
  var fracInput by remember(problem.itemNumber) { mutableStateOf("") }
  Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(10.dp)) {
    Text(problem.prompt, style = MaterialTheme.typography.titleMedium, color = InkDark, textAlign = TextAlign.Center)
    PictureFractionScene(problem.scene)
    AnswerDisplayBox(text = fracInput, placeholder = "e.g. ${problem.num}/${problem.den}".replace(Regex("\\d"), "?"))
    KidNumberPad(
      showSlash = true,
      onDigit = { if (fracInput.length < 6) fracInput += it },
      onBackspace = { if (fracInput.isNotEmpty()) fracInput = fracInput.dropLast(1) },
      onClear = { fracInput = "" },
      onSubmit = {
        val ok = AnswerChecker.checkFractionInput(fracInput, problem.num, problem.den)
        onResult(AnswerCheckResult(ok, if (ok) "Great counting! ${problem.num}/${problem.den}!" else "Count the asked items over ALL items.", "${problem.num}/${problem.den}"))
      },
      submitEnabled = fracInput.isNotEmpty(),
    )
  }
}

@Composable
fun FractionWordsRenderer(
  problem: MathProblem.FractionWordsProblem,
  onResult: (AnswerCheckResult) -> Unit,
) {
  var textInput by remember(problem.itemNumber) { mutableStateOf("") }
  Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(12.dp)) {
    if (problem.toWords) {
      Text("Write this fraction in words:", style = MaterialTheme.typography.titleMedium, color = InkDark)
      FractionTile("${problem.num}/${problem.den}", BagacayBlueLight)
      OutlinedTextField(
        value = textInput,
        onValueChange = { textInput = it },
        label = { Text("Type in words (e.g. three-fifths)") },
        singleLine = true,
        modifier = Modifier.fillMaxWidth().testTag("fraction_words_input"),
      )
      Button(
        onClick = { onResult(AnswerChecker.checkFractionWords(problem, textInput)) },
        enabled = textInput.isNotBlank(),
        modifier = Modifier.fillMaxWidth().height(54.dp).testTag("submit_fraction_words"),
        shape = RoundedCornerShape(18.dp),
      ) {
        Text("Check Answer", fontWeight = FontWeight.Bold)
      }
    } else {
      Text("Write the underlined fraction in numbers:", style = MaterialTheme.typography.titleMedium, color = InkDark)
      Surface(color = WarmSurfaceVariant, shape = RoundedCornerShape(18.dp), modifier = Modifier.fillMaxWidth()) {
        Text(
          text = problem.sentenceWithHighlight ?: problem.words,
          style = MaterialTheme.typography.titleLarge,
          color = InkDark,
          textAlign = TextAlign.Center,
          modifier = Modifier.padding(16.dp),
        )
      }
      AnswerDisplayBox(text = textInput, placeholder = "e.g. 1/4")
      KidNumberPad(
        showSlash = true,
        onDigit = { if (textInput.length < 6) textInput += it },
        onBackspace = { if (textInput.isNotEmpty()) textInput = textInput.dropLast(1) },
        onClear = { textInput = "" },
        onSubmit = { onResult(AnswerChecker.checkFractionWords(problem, textInput)) },
        submitEnabled = textInput.isNotEmpty(),
      )
    }
  }
}

@Composable
fun FractionBlankRenderer(
  problem: MathProblem.FractionBlankProblem,
  onResult: (AnswerCheckResult) -> Unit,
) {
  var numIn by remember(problem.itemNumber) { mutableStateOf("") }
  var denIn by remember(problem.itemNumber) { mutableStateOf("") }
  var active by remember(problem.itemNumber) { mutableStateOf(if (problem.askNum) 0 else 1) }

  Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(12.dp)) {
    Text("Fill in the blank(s) with the correct number:", style = MaterialTheme.typography.titleMedium, color = InkDark)
    Surface(color = WarmSurfaceVariant, shape = RoundedCornerShape(20.dp), modifier = Modifier.fillMaxWidth()) {
      Row(
        modifier = Modifier.padding(16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
      ) {
        Text("${problem.num}/${problem.den}  is  ", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        if (problem.askNum) {
          SmallInlineBlank(numIn, active == 0) { active = 0 }
        } else {
          Text("${problem.num}", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = BagacayBlue)
        }
        Text("  of  ", style = MaterialTheme.typography.titleLarge)
        if (problem.askDen) {
          SmallInlineBlank(denIn, active == 1) { active = 1 }
        } else {
          Text("${problem.den}", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = CoralTangerine)
        }
        Text("  equal parts", style = MaterialTheme.typography.titleMedium)
      }
    }
    KidNumberPad(
      onDigit = {
        if (active == 0 && problem.askNum && numIn.length < 3) numIn += it
        else if (denIn.length < 3) denIn += it
      },
      onBackspace = {
        if (active == 0 && problem.askNum && numIn.isNotEmpty()) numIn = numIn.dropLast(1)
        else if (denIn.isNotEmpty()) denIn = denIn.dropLast(1)
      },
      onClear = {
        if (active == 0 && problem.askNum) numIn = "" else denIn = ""
      },
      onSubmit = {
        val numOk = !problem.askNum || numIn.toIntOrNull() == problem.num
        val denOk = !problem.askDen || denIn.toIntOrNull() == problem.den
        val ok = numOk && denOk
        onResult(AnswerCheckResult(ok, if (ok) "Exact match!" else "Check numerator and denominator!", "${problem.num} of ${problem.den}"))
      },
      submitEnabled = (!problem.askNum || numIn.isNotEmpty()) && (!problem.askDen || denIn.isNotEmpty()),
    )
  }
}

@Composable
fun PatternRenderer(
  problem: MathProblem.PatternProblem,
  onResult: (AnswerCheckResult) -> Unit,
) {
  val blankCount = problem.tokens.count { it.kind == PatternTokenKind.BLANK }
  val inputs = remember(problem) { mutableStateListOf<String>().apply { repeat(blankCount) { add("") } } }
  var activeBlank by remember(problem) { mutableStateOf(0) }

  Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(12.dp)) {
    Text("Find the missing term(s) in the pattern:", style = MaterialTheme.typography.titleMedium, color = InkDark)
    @OptIn(ExperimentalLayoutApi::class)
    FlowRow(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(20.dp))
        .background(WarmSurfaceVariant)
        .padding(14.dp),
      horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally),
      verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
      var bIdx = 0
      problem.tokens.forEach { tok ->
        if (tok.kind == PatternTokenKind.BLANK) {
          val curIdx = bIdx++
          val text = inputs.getOrElse(curIdx) { "" }
          Box(
            modifier = Modifier
              .defaultMinSize(minWidth = 52.dp, minHeight = 44.dp)
              .clip(RoundedCornerShape(12.dp))
              .background(if (activeBlank == curIdx) BagacayBlueLight else Color.White)
              .border(2.dp, if (activeBlank == curIdx) BagacayBlue else MangoGold, RoundedCornerShape(12.dp))
              .clickable { activeBlank = curIdx }
              .padding(horizontal = 8.dp, vertical = 4.dp),
            contentAlignment = Alignment.Center,
          ) {
            Text(text.replace("_", " ").ifEmpty { "?" }, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = BagacayBlue)
          }
        } else {
          PatternTokenBadge(tok)
        }
      }
    }

    if (problem.isShapeOrLetterChoice && problem.choices != null) {
      if (blankCount <= 1) {
        @OptIn(ExperimentalLayoutApi::class)
        FlowRow(
          horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
          verticalArrangement = Arrangement.spacedBy(8.dp),
          modifier = Modifier.fillMaxWidth(),
        ) {
          problem.choices.forEach { choice ->
            Button(
              onClick = {
                onResult(AnswerChecker.checkPatternAnswer(problem, listOf(choice)))
              },
              modifier = Modifier.defaultMinSize(minWidth = 96.dp, minHeight = 52.dp).testTag("pattern_choice_$choice"),
              shape = RoundedCornerShape(16.dp),
              colors = ButtonDefaults.buttonColors(containerColor = BerryPurple),
            ) {
              Text(choice.replace("_", " "), fontSize = 13.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
            }
          }
        }
      } else {
        Text("Tap choices below to fill Blank #${activeBlank + 1}:", style = MaterialTheme.typography.bodySmall, color = InkMuted)
        @OptIn(ExperimentalLayoutApi::class)
        FlowRow(
          horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
          verticalArrangement = Arrangement.spacedBy(8.dp),
          modifier = Modifier.fillMaxWidth(),
        ) {
          problem.choices.forEach { choice ->
            FilledTonalButton(
              onClick = {
                if (activeBlank in inputs.indices) {
                  inputs[activeBlank] = choice
                  val nextEmpty = inputs.indexOfFirst { it.isEmpty() }
                  if (nextEmpty >= 0) {
                    activeBlank = nextEmpty
                  } else {
                    activeBlank = (activeBlank + 1) % blankCount
                  }
                }
              },
              modifier = Modifier.defaultMinSize(minWidth = 90.dp, minHeight = 48.dp).testTag("pattern_choice_$choice"),
              shape = RoundedCornerShape(14.dp),
            ) {
              Text(choice.replace("_", " "), fontSize = 13.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
            }
          }
        }
        Button(
          onClick = { onResult(AnswerChecker.checkPatternAnswer(problem, inputs.toList())) },
          enabled = inputs.all { it.isNotEmpty() },
          modifier = Modifier.fillMaxWidth().height(52.dp).testTag("submit_multi_pattern"),
          shape = RoundedCornerShape(16.dp),
        ) {
          Text("Check Pattern", fontWeight = FontWeight.Bold)
        }
      }
    } else {
      KidNumberPad(
        onDigit = { ch ->
          val cur = inputs.getOrElse(activeBlank) { "" }
          if (cur.length < 6) inputs[activeBlank] = cur + ch
        },
        onBackspace = {
          val cur = inputs.getOrElse(activeBlank) { "" }
          if (cur.isNotEmpty()) inputs[activeBlank] = cur.dropLast(1)
        },
        onClear = { inputs[activeBlank] = "" },
        onSubmit = {
          onResult(AnswerChecker.checkPatternAnswer(problem, inputs.toList()))
        },
        submitEnabled = inputs.all { it.isNotEmpty() },
      )
    }
  }
}

@Composable
fun AnswerDisplayBox(text: String, placeholder: String) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .height(58.dp)
      .clip(RoundedCornerShape(18.dp))
      .background(Color.White)
      .border(2.dp, BagacayBlue, RoundedCornerShape(18.dp)),
    contentAlignment = Alignment.Center,
  ) {
    Text(
      text = text.ifEmpty { placeholder },
      fontSize = 24.sp,
      fontWeight = FontWeight.Bold,
      color = if (text.isEmpty()) InkMuted.copy(alpha = 0.6f) else InkDark,
    )
  }
}

@Composable
fun FieldBox(label: String, value: String, active: Boolean, modifier: Modifier = Modifier, onClick: () -> Unit) {
  Column(
    modifier = modifier
      .clip(RoundedCornerShape(16.dp))
      .background(if (active) BagacayBlueLight else Color.White)
      .border(2.dp, if (active) BagacayBlue else OutlineSoft, RoundedCornerShape(16.dp))
      .clickable(onClick = onClick)
      .padding(8.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    Text(label, style = MaterialTheme.typography.labelMedium, color = InkMuted)
    Text(value.ifEmpty { "?" }, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = InkDark)
  }
}

@Composable
private fun SmallInlineBlank(text: String, active: Boolean, onClick: () -> Unit) {
  Box(
    modifier = Modifier
      .defaultMinSize(minWidth = 48.dp, minHeight = 42.dp)
      .clip(RoundedCornerShape(12.dp))
      .background(if (active) BagacayBlueLight else Color.White)
      .border(2.dp, if (active) BagacayBlue else OutlineSoft, RoundedCornerShape(12.dp))
      .clickable(onClick = onClick)
      .padding(horizontal = 10.dp, vertical = 4.dp),
    contentAlignment = Alignment.Center,
  ) {
    Text(text.ifEmpty { "?" }, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = BagacayBlue)
  }
}
