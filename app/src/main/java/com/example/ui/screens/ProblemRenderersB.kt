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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.DiscCounts
import com.example.model.MathProblem
import com.example.ui.components.*
import com.example.ui.theme.*
import com.example.viewmodel.AnswerCheckResult
import com.example.viewmodel.AnswerChecker

@Composable
fun ArithmeticRenderer(
  problem: MathProblem.ArithmeticProblem,
  onResult: (AnswerCheckResult) -> Unit,
) {
  var input by remember(problem.itemNumber) { mutableStateOf("") }
  Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(12.dp)) {
    if (problem.promptText != null) {
      Surface(color = WarmSurfaceVariant, shape = RoundedCornerShape(18.dp), modifier = Modifier.fillMaxWidth()) {
        Text(
          text = problem.promptText,
          style = MaterialTheme.typography.titleMedium,
          color = InkDark,
          modifier = Modifier.padding(14.dp),
        )
      }
    }
    if (problem.showVerticalColumns && problem.operands.isNotEmpty()) {
      Surface(
        color = Color(0xFFFFFDF8),
        shape = RoundedCornerShape(18.dp),
        modifier = Modifier.border(1.5.dp, OutlineSoft, RoundedCornerShape(18.dp)),
      ) {
        Column(
          horizontalAlignment = Alignment.End,
          modifier = Modifier.padding(horizontal = 28.dp, vertical = 14.dp),
        ) {
          problem.operands.forEachIndexed { idx, opVal ->
            val prefix = if (idx == problem.operands.lastIndex) "${problem.op.symbol}  " else "   "
            Text(
              text = "$prefix${"%,d".format(opVal)}",
              fontSize = 28.sp,
              fontFamily = FontFamily.Monospace,
              fontWeight = FontWeight.Bold,
              color = InkDark,
            )
          }
          HorizontalDivider(modifier = Modifier.width(150.dp).padding(vertical = 4.dp), thickness = 3.dp, color = InkDark)
        }
      }
    } else if (problem.promptText == null && problem.operands.isNotEmpty()) {
      Text(
        text = problem.operands.joinToString(" ${problem.op.symbol} ") { "%,d".format(it) } + " =",
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        color = InkDark,
      )
    }

    AnswerDisplayBox(
      text = input,
      placeholder = if (problem.unitLabel != null) "Answer (${problem.unitLabel})" else "Enter answer",
    )
    KidNumberPad(
      onDigit = { if (input.length < 9) input += it },
      onBackspace = { if (input.isNotEmpty()) input = input.dropLast(1) },
      onClear = { input = "" },
      onSubmit = {
        val ok = AnswerChecker.normalizeDigits(input).toIntOrNull() == problem.answer
        val expected = "%,d".format(problem.answer) + (problem.unitLabel?.let { " $it" } ?: "")
        onResult(AnswerCheckResult(ok, if (ok) "Correct! Well done!" else "Check your calculation or try the Scratchpad!", expected))
      },
      submitEnabled = input.isNotEmpty(),
    )
  }
}

@Composable
fun GroupingDivisionRenderer(
  problem: MathProblem.GroupingDivisionProblem,
  onResult: (AnswerCheckResult) -> Unit,
) {
  var showGroups by remember(problem.itemNumber) { mutableStateOf(false) }
  var input by remember(problem.itemNumber) { mutableStateOf("") }
  Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(10.dp)) {
    Text(problem.prompt, style = MaterialTheme.typography.titleMedium, color = InkDark, textAlign = TextAlign.Center)
    GroupingDivisionScene(problem.scene, problem.total, problem.groupSize, showGroups)
    FilledTonalButton(
      onClick = { showGroups = !showGroups },
      shape = RoundedCornerShape(14.dp),
    ) {
      Text(if (showGroups) "Show Ungrouped" else "Tap to Group Into ${problem.groupSize}s!")
    }
    AnswerDisplayBox(text = input, placeholder = "How many groups?")
    KidNumberPad(
      onDigit = { if (input.length < 3) input += it },
      onBackspace = { if (input.isNotEmpty()) input = input.dropLast(1) },
      onClear = { input = "" },
      onSubmit = {
        val ok = input.toIntOrNull() == problem.answerGroups
        onResult(AnswerCheckResult(ok, if (ok) "Great grouping! ${problem.total} ÷ ${problem.groupSize} = ${problem.answerGroups}" else "Count the equal groups!", "${problem.answerGroups} groups"))
      },
      submitEnabled = input.isNotEmpty(),
    )
  }
}

@Composable
fun DivisionPartRenderer(
  problem: MathProblem.DivisionPartProblem,
  onResult: (AnswerCheckResult) -> Unit,
) {
  val expected = when (problem.askPart) {
    com.example.model.DivisionPartType.DIVIDEND -> problem.dividend
    com.example.model.DivisionPartType.DIVISOR -> problem.divisor
    com.example.model.DivisionPartType.QUOTIENT -> problem.quotient
  }
  Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(14.dp)) {
    Surface(color = WarmSurfaceVariant, shape = RoundedCornerShape(18.dp), modifier = Modifier.fillMaxWidth()) {
      Text(problem.prompt, style = MaterialTheme.typography.titleLarge, color = InkDark, textAlign = TextAlign.Center, modifier = Modifier.padding(16.dp))
    }
    Text("Tap the matching number:", style = MaterialTheme.typography.bodyMedium, color = InkMuted)
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
      listOf(problem.dividend, problem.divisor, problem.quotient).distinct().forEach { num ->
        Button(
          onClick = {
            val ok = num == expected
            onResult(AnswerCheckResult(ok, if (ok) "Correct! $expected is the ${problem.askPart.name.lowercase()}." else "Remember: Dividend ÷ Divisor = Quotient.", "$expected"))
          },
          modifier = Modifier.weight(1f).height(62.dp).testTag("div_part_$num"),
          shape = RoundedCornerShape(18.dp),
          colors = ButtonDefaults.buttonColors(containerColor = TealOcean),
        ) {
          Text("$num", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }
      }
    }
  }
}

@Composable
fun DivisionRenderer(
  problem: MathProblem.DivisionProblem,
  onResult: (AnswerCheckResult) -> Unit,
) {
  var qIn by remember(problem.itemNumber) { mutableStateOf("") }
  var rIn by remember(problem.itemNumber) { mutableStateOf("") }
  var active by remember(problem.itemNumber) { mutableStateOf(0) }
  val needsRemainder = problem.remainder > 0 || problem.showBracket

  Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(10.dp)) {
    if (problem.promptText != null) {
      Surface(color = WarmSurfaceVariant, shape = RoundedCornerShape(18.dp), modifier = Modifier.fillMaxWidth()) {
        Text(problem.promptText, style = MaterialTheme.typography.titleMedium, color = InkDark, textAlign = TextAlign.Center, modifier = Modifier.padding(14.dp))
      }
    } else if (problem.showBracket) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
          .clip(RoundedCornerShape(18.dp))
          .background(Color(0xFFFFFDF8))
          .border(1.5.dp, OutlineSoft, RoundedCornerShape(18.dp))
          .padding(horizontal = 24.dp, vertical = 14.dp),
      ) {
        Text("${problem.divisor}", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = CoralTangerine)
        Text("  ⟌ ", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = InkDark)
        Text("${problem.dividend}", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = BagacayBlue)
      }
    } else {
      Text("${problem.dividend} ÷ ${problem.divisor} =", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = InkDark)
    }

    if (needsRemainder) {
      Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
        FieldBox("Quotient", qIn, active == 0, Modifier.weight(1.3f)) { active = 0 }
        FieldBox("Remainder (optional if 0)", rIn, active == 1, Modifier.weight(1f)) { active = 1 }
      }
    } else {
      AnswerDisplayBox(qIn, if (problem.unitLabel != null) "Answer (${problem.unitLabel})" else "Quotient")
    }

    KidNumberPad(
      onDigit = {
        if (active == 0) { if (qIn.length < 5) qIn += it }
        else { if (rIn.length < 3) rIn += it }
      },
      onBackspace = {
        if (active == 0 && qIn.isNotEmpty()) qIn = qIn.dropLast(1)
        else if (active == 1 && rIn.isNotEmpty()) rIn = rIn.dropLast(1)
      },
      onClear = { if (active == 0) qIn = "" else rIn = "" },
      onSubmit = { onResult(AnswerChecker.checkDivisionInput(problem, qIn, rIn)) },
      submitEnabled = qIn.isNotEmpty(),
    )
  }
}

@Composable
fun GeometryIdentifyRenderer(
  problem: MathProblem.GeometryIdentifyProblem,
  onResult: (AnswerCheckResult) -> Unit,
) {
  Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(12.dp)) {
    Text(problem.prompt, style = MaterialTheme.typography.titleMedium, color = InkDark, textAlign = TextAlign.Center)
    if (problem.figure != null) GeoFigureCanvas(problem.figure)
    @OptIn(ExperimentalLayoutApi::class)
    FlowRow(
      horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
      verticalArrangement = Arrangement.spacedBy(10.dp),
      modifier = Modifier.fillMaxWidth(),
    ) {
      problem.options.forEach { opt ->
        Button(
          onClick = {
            val ok = opt.equals(problem.answer, ignoreCase = true)
            onResult(AnswerCheckResult(ok, if (ok) "Nice geometry eye! That's $opt." else "Look closely at endpoints and arrows.", problem.answer))
          },
          modifier = Modifier.defaultMinSize(minWidth = 136.dp, minHeight = 52.dp).testTag("geo_opt_$opt"),
          shape = RoundedCornerShape(16.dp),
          colors = ButtonDefaults.buttonColors(containerColor = TealOcean),
        ) {
          Text(opt, fontWeight = FontWeight.Bold)
        }
      }
    }
  }
}

@Composable
fun GeometryBoxFindRenderer(
  problem: MathProblem.GeometryBoxFindProblem,
  onResult: (AnswerCheckResult) -> Unit,
) {
  var input by remember(problem.itemNumber) { mutableStateOf("") }
  Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(10.dp)) {
    Text("Find a ${problem.categoryLabel} in the figure box (type the letter(s), e.g., BC or A):", style = MaterialTheme.typography.titleMedium, color = InkDark, textAlign = TextAlign.Center)
    GeoBoxDiagram()
    OutlinedTextField(
      value = input,
      onValueChange = { input = it.uppercase().take(3) },
      label = { Text("Letters (e.g. BC, ED, HI, A)") },
      singleLine = true,
      modifier = Modifier.fillMaxWidth().testTag("geo_box_input"),
    )
    Button(
      onClick = {
        val clean = input.trim().uppercase()
        val ok = clean in problem.validAnswers
        onResult(AnswerCheckResult(ok, if (ok) "Found it! $clean is valid." else "Check the endpoints and arrows in the box!", problem.validAnswers.first()))
      },
      enabled = input.isNotBlank(),
      modifier = Modifier.fillMaxWidth().height(52.dp),
      shape = RoundedCornerShape(16.dp),
    ) {
      Text("Check Symbol", fontWeight = FontWeight.Bold)
    }
  }
}

@Composable
fun GeometryDrawRenderer(
  problem: MathProblem.GeometryDrawProblem,
  onResult: (AnswerCheckResult) -> Unit,
) {
  Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(10.dp)) {
    Text(problem.prompt, style = MaterialTheme.typography.titleMedium, color = InkDark, textAlign = TextAlign.Center)
    InteractiveGeometryDrawPad(
      sampleSegmentUnits = problem.sampleSegmentUnits,
      onMarkDone = {
        onResult(AnswerCheckResult(true, "Awesome drawing! Great job using the ruler guide.", problem.guideHint))
      },
    )
  }
}

@Composable
fun GeometryNameLinesRenderer(
  problem: MathProblem.GeometryNameLinesProblem,
  onResult: (AnswerCheckResult) -> Unit,
) {
  var input by remember(problem.itemNumber) { mutableStateOf("") }
  Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(10.dp)) {
    Text(problem.prompt, style = MaterialTheme.typography.titleMedium, color = InkDark, textAlign = TextAlign.Center)
    NamedLinesDiagram(problem.diagramId)
    OutlinedTextField(
      value = input,
      onValueChange = { input = it.uppercase().take(2) },
      label = { Text("2-letter line name (e.g. KL)") },
      singleLine = true,
      modifier = Modifier.fillMaxWidth(),
    )
    Button(
      onClick = {
        val ok = input.trim().uppercase() in problem.validAnswers
        onResult(AnswerCheckResult(ok, if (ok) "Great! Line ${input.uppercase()} is in the diagram." else "Use two labeled points on one straight line.", problem.validAnswers.first()))
      },
      enabled = input.trim().length == 2,
      modifier = Modifier.fillMaxWidth().height(52.dp),
      shape = RoundedCornerShape(16.dp),
    ) {
      Text("Check Line Name", fontWeight = FontWeight.Bold)
    }
  }
}

@Composable
fun CongruentSegmentsRenderer(
  problem: MathProblem.CongruentSegmentsProblem,
  onResult: (AnswerCheckResult) -> Unit,
) {
  Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(10.dp)) {
    Text(problem.prompt, style = MaterialTheme.typography.titleMedium, color = InkDark, textAlign = TextAlign.Center)
    CongruentFigureCanvas(problem.subType, problem.setIndex)
    @OptIn(ExperimentalLayoutApi::class)
    FlowRow(
      horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
      verticalArrangement = Arrangement.spacedBy(10.dp),
      modifier = Modifier.fillMaxWidth(),
    ) {
      problem.options.forEach { opt ->
        Button(
          onClick = {
            val ok = opt in problem.validAnswers
            onResult(AnswerCheckResult(ok, if (ok) "Correct! Congruent segments have equal length." else "Compare the lengths carefully!", problem.validAnswers.first()))
          },
          modifier = Modifier.defaultMinSize(minWidth = 110.dp, minHeight = 52.dp),
          shape = RoundedCornerShape(16.dp),
          colors = ButtonDefaults.buttonColors(containerColor = TealOcean),
        ) {
          Text(opt, fontWeight = FontWeight.Bold)
        }
      }
    }
  }
}

@Composable
fun NumberDiscsReadRenderer(
  problem: MathProblem.NumberDiscsReadProblem,
  onResult: (AnswerCheckResult) -> Unit,
) {
  var input by remember(problem.itemNumber) { mutableStateOf("") }
  Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(10.dp)) {
    Text("Count the discs and write the total number:", style = MaterialTheme.typography.titleMedium, color = InkDark)
    Surface(
      color = Color(0xFFFFFDF8),
      shape = RoundedCornerShape(18.dp),
      modifier = Modifier.fillMaxWidth().border(1.5.dp, OutlineSoft, RoundedCornerShape(18.dp)),
    ) {
      DiscCollectionTray(problem.counts, modifier = Modifier.padding(12.dp))
    }
    AnswerDisplayBox(input, "Total amount")
    KidNumberPad(
      onDigit = { if (input.length < 6) input += it },
      onBackspace = { if (input.isNotEmpty()) input = input.dropLast(1) },
      onClear = { input = "" },
      onSubmit = {
        val ok = input.toIntOrNull() == problem.answer
        onResult(AnswerCheckResult(ok, if (ok) "Awesome! Total is ${"%,d".format(problem.answer)}." else "Group by 10,000, 1,000, 100, 10, and 1!", "%,d".format(problem.answer)))
      },
      submitEnabled = input.isNotEmpty(),
    )
  }
}

@Composable
fun NumberDiscsBuildRenderer(
  problem: MathProblem.NumberDiscsBuildProblem,
  onResult: (AnswerCheckResult) -> Unit,
) {
  var c10k by remember(problem.itemNumber) { mutableStateOf(0) }
  var c1k by remember(problem.itemNumber) { mutableStateOf(0) }
  var c100 by remember(problem.itemNumber) { mutableStateOf(0) }
  var c10 by remember(problem.itemNumber) { mutableStateOf(0) }
  var c1 by remember(problem.itemNumber) { mutableStateOf(0) }
  val counts = DiscCounts(0, c10k, c1k, c100, c10, c1)

  Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(10.dp)) {
    Text("Tap the discs below to build:", style = MaterialTheme.typography.titleMedium, color = InkMuted)
    Text("%,d".format(problem.targetNumber), fontSize = 36.sp, fontWeight = FontWeight.Bold, color = BagacayBlue)
    Surface(
      color = Color(0xFFFFFDF8),
      shape = RoundedCornerShape(18.dp),
      modifier = Modifier.fillMaxWidth().defaultMinSize(minHeight = 90.dp).border(1.5.dp, OutlineSoft, RoundedCornerShape(18.dp)),
    ) {
      if (counts.total == 0) {
        Box(Modifier.fillMaxWidth().padding(18.dp), contentAlignment = Alignment.Center) {
          Text("Tap + buttons below to add discs!", color = InkMuted)
        }
      } else {
        DiscCollectionTray(counts, modifier = Modifier.padding(10.dp))
      }
    }
    Text("Current tray total: ${"%,d".format(counts.total)}", fontWeight = FontWeight.Bold, color = InkDark)
    Row(horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.fillMaxWidth()) {
      listOf(
        Triple("10k", c10k) { if (c10k < 9) c10k++ },
        Triple("1k", c1k) { if (c1k < 9) c1k++ },
        Triple("100", c100) { if (c100 < 9) c100++ },
        Triple("10", c10) { if (c10 < 9) c10++ },
        Triple("1", c1) { if (c1 < 9) c1++ },
      ).forEach { (lbl, cnt, onAdd) ->
        FilledTonalButton(
          onClick = onAdd,
          modifier = Modifier.weight(1f).height(52.dp),
          shape = RoundedCornerShape(14.dp),
          contentPadding = PaddingValues(2.dp),
        ) {
          Text("+$lbl\n($cnt)", textAlign = TextAlign.Center, fontSize = 12.sp, lineHeight = 14.sp, fontWeight = FontWeight.Bold)
        }
      }
    }
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
      OutlinedButton(
        onClick = { c10k = 0; c1k = 0; c100 = 0; c10 = 0; c1 = 0 },
        modifier = Modifier.weight(1f).height(52.dp),
      ) { Text("Clear") }
      Button(
        onClick = {
          val ok = counts.total == problem.targetNumber
          onResult(AnswerCheckResult(ok, if (ok) "Perfect disc build!" else "Tray is ${"%,d".format(counts.total)}, target is ${"%,d".format(problem.targetNumber)}.", "%,d".format(problem.targetNumber)))
        },
        modifier = Modifier.weight(1.5f).height(52.dp),
      ) { Text("Check Discs", fontWeight = FontWeight.Bold) }
    }
  }
}

@Composable
fun PlaceValueRenderer(
  problem: MathProblem.PlaceValueProblem,
  onResult: (AnswerCheckResult) -> Unit,
) {
  var valueIn by remember(problem) { mutableStateOf("") }
  var selectedPv by remember(problem) { mutableStateOf<String?>(null) }
  val pvOptions = remember(problem.number, problem.expectedPlaceValue) {
    when {
      problem.number >= 100_000 || problem.expectedPlaceValue.equals("Hundred Thousands", ignoreCase = true) ->
        listOf("Ones", "Tens", "Hundreds", "Thousands", "Ten Thousands", "Hundred Thousands")
      problem.number < 1_000 ->
        listOf("Ones", "Tens", "Hundreds")
      else ->
        listOf("Ones", "Tens", "Hundreds", "Thousands", "Ten Thousands")
    }
  }

  Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(10.dp)) {
    Text(problem.promptText, style = MaterialTheme.typography.titleMedium, color = InkDark, textAlign = TextAlign.Center)
    if (problem.askPlaceValue) {
      @OptIn(ExperimentalLayoutApi::class)
      FlowRow(horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally), verticalArrangement = Arrangement.spacedBy(6.dp)) {
        pvOptions.forEach { pv ->
          FilterChip(
            selected = selectedPv == pv,
            onClick = { selectedPv = pv },
            label = { Text(pv, fontWeight = FontWeight.Bold) },
          )
        }
      }
    }
    if (problem.askValue) {
      AnswerDisplayBox(valueIn, "Value of the digit (e.g. 50)")
      KidNumberPad(
        onDigit = { if (valueIn.length < 7) valueIn += it },
        onBackspace = { if (valueIn.isNotEmpty()) valueIn = valueIn.dropLast(1) },
        onClear = { valueIn = "" },
        onSubmit = {
          val valOk = AnswerChecker.normalizeDigits(valueIn).toIntOrNull() == problem.expectedValue
          val pvOk = !problem.askPlaceValue || selectedPv.equals(problem.expectedPlaceValue, ignoreCase = true)
          val ok = valOk && pvOk
          val exp = if (problem.askPlaceValue) "${"%,d".format(problem.expectedValue)} (${problem.expectedPlaceValue})" else "%,d".format(problem.expectedValue)
          onResult(AnswerCheckResult(ok, if (ok) "Spot on!" else "Check both the place name and digit value!", exp))
        },
        submitEnabled = valueIn.isNotEmpty() && (!problem.askPlaceValue || selectedPv != null),
      )
    } else {
      Button(
        onClick = {
          val pvOk = selectedPv.equals(problem.expectedPlaceValue, ignoreCase = true)
          onResult(AnswerCheckResult(pvOk, if (pvOk) "Spot on! That digit is in the ${problem.expectedPlaceValue} place." else "Count the places from the right: Ones, Tens, Hundreds!", problem.expectedPlaceValue))
        },
        enabled = selectedPv != null,
        modifier = Modifier.fillMaxWidth().height(54.dp).testTag("submit_place_value_only"),
        shape = RoundedCornerShape(16.dp),
      ) {
        Text("Check Place Value", fontWeight = FontWeight.Bold)
      }
    }
  }
}

@Composable
fun NumberWordsRenderer(
  problem: MathProblem.NumberWordsProblem,
  onResult: (AnswerCheckResult) -> Unit,
) {
  var input by remember(problem.itemNumber) { mutableStateOf("") }
  Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(12.dp)) {
    if (problem.toWords) {
      Text("Write this numeral in words:", style = MaterialTheme.typography.titleMedium, color = InkDark)
      Text("%,d".format(problem.number), fontSize = 36.sp, fontWeight = FontWeight.Bold, color = BagacayBlue)
      OutlinedTextField(
        value = input,
        onValueChange = { input = it },
        label = { Text("Number in words") },
        modifier = Modifier.fillMaxWidth(),
      )
      Button(
        onClick = { onResult(AnswerChecker.checkNumberWords(problem, input)) },
        enabled = input.isNotBlank(),
        modifier = Modifier.fillMaxWidth().height(54.dp),
      ) { Text("Check Words", fontWeight = FontWeight.Bold) }
    } else {
      Text("Write this worded number in digits:", style = MaterialTheme.typography.titleMedium, color = InkDark)
      Surface(color = WarmSurfaceVariant, shape = RoundedCornerShape(18.dp), modifier = Modifier.fillMaxWidth()) {
        Text(problem.words, style = MaterialTheme.typography.titleLarge, color = InkDark, textAlign = TextAlign.Center, modifier = Modifier.padding(16.dp))
      }
      AnswerDisplayBox(input, "Enter digits")
      KidNumberPad(
        onDigit = { if (input.length < 7) input += it },
        onBackspace = { if (input.isNotEmpty()) input = input.dropLast(1) },
        onClear = { input = "" },
        onSubmit = { onResult(AnswerChecker.checkNumberWords(problem, input)) },
        submitEnabled = input.isNotEmpty(),
      )
    }
  }
}

@Composable
fun CompareWholeNumbersRenderer(
  problem: MathProblem.CompareWholeNumbersProblem,
  onResult: (AnswerCheckResult) -> Unit,
) {
  Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(14.dp)) {
    Text("Compare the two numbers:", style = MaterialTheme.typography.titleMedium, color = InkDark)
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(8.dp),
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Surface(color = BagacayBlueLight, shape = RoundedCornerShape(16.dp), modifier = Modifier.weight(1f)) {
        Text(problem.leftDisplay, style = MaterialTheme.typography.titleMedium, textAlign = TextAlign.Center, modifier = Modifier.padding(12.dp))
      }
      Text("?", fontSize = 26.sp, fontWeight = FontWeight.Bold, color = MangoDark)
      Surface(color = CoralLight, shape = RoundedCornerShape(16.dp), modifier = Modifier.weight(1f)) {
        Text(problem.rightDisplay, style = MaterialTheme.typography.titleMedium, textAlign = TextAlign.Center, modifier = Modifier.padding(12.dp))
      }
    }
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
      listOf("<", ">", "=").forEach { sym ->
        Button(
          onClick = {
            val ok = sym == problem.answerSymbol
            onResult(AnswerCheckResult(ok, if (ok) "Yes! ${"%,d".format(problem.leftValue)} ${problem.answerSymbol} ${"%,d".format(problem.rightValue)}." else "Convert the words to digits first!", problem.answerSymbol))
          },
          modifier = Modifier.weight(1f).height(58.dp),
          shape = RoundedCornerShape(18.dp),
        ) {
          Text(sym, fontSize = 26.sp, fontWeight = FontWeight.Bold)
        }
      }
    }
  }
}

@Composable
fun RoundingRenderer(
  problem: MathProblem.RoundingProblem,
  onResult: (AnswerCheckResult) -> Unit,
) {
  var input by remember(problem.itemNumber) { mutableStateOf("") }
  Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(12.dp)) {
    Text("Round to the nearest ${problem.targetPlace.uppercase()}:", style = MaterialTheme.typography.titleMedium, color = InkDark)
    Text("%,d".format(problem.number), fontSize = 38.sp, fontWeight = FontWeight.Bold, color = BagacayBlue)
    AnswerDisplayBox(input, "Rounded number")
    KidNumberPad(
      onDigit = { if (input.length < 7) input += it },
      onBackspace = { if (input.isNotEmpty()) input = input.dropLast(1) },
      onClear = { input = "" },
      onSubmit = {
        val ok = AnswerChecker.normalizeDigits(input).toIntOrNull() == problem.answer
        onResult(AnswerCheckResult(ok, if (ok) "Great rounding!" else "Look at the digit right after ${problem.targetPlace}.", "%,d".format(problem.answer)))
      },
      submitEnabled = input.isNotEmpty(),
    )
  }
}

@Composable
fun OrderNumbersRenderer(
  problem: MathProblem.OrderNumbersProblem,
  onResult: (AnswerCheckResult) -> Unit,
) {
  val chosenIndices = remember(problem) { mutableStateListOf<Int>() }
  val chosenValues = chosenIndices.map { problem.numbers[it] }
  val availableIndexed = problem.numbers.withIndex().filter { it.index !in chosenIndices }
  Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(12.dp)) {
    Text(
      text = if (problem.increasing) "Order INCREASING (Smallest → Largest)" else "Order DECREASING (Largest → Smallest)",
      style = MaterialTheme.typography.titleMedium,
      color = InkDark,
    )
    @OptIn(ExperimentalLayoutApi::class)
    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally), verticalArrangement = Arrangement.spacedBy(8.dp)) {
      repeat(problem.numbers.size) { slotIdx ->
        val srcIdx = chosenIndices.getOrNull(slotIdx)
        val num = srcIdx?.let { problem.numbers[it] }
        Box(
          modifier = Modifier
            .defaultMinSize(minWidth = 86.dp, minHeight = 48.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(if (num != null) BagacayBlueLight else WarmSurfaceVariant)
            .border(1.5.dp, if (num != null) BagacayBlue else OutlineSoft, RoundedCornerShape(14.dp))
            .clickable(enabled = srcIdx != null) { if (srcIdx != null) chosenIndices.remove(srcIdx) }
            .padding(horizontal = 10.dp, vertical = 6.dp),
          contentAlignment = Alignment.Center,
        ) {
          Text(num?.let { "%,d".format(it) } ?: "#${slotIdx + 1}", fontWeight = FontWeight.Bold, color = InkDark)
        }
      }
    }
    Text("Tap numbers below in order:", style = MaterialTheme.typography.bodyMedium, color = InkMuted)
    @OptIn(ExperimentalLayoutApi::class)
    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally), verticalArrangement = Arrangement.spacedBy(8.dp)) {
      availableIndexed.forEach { (idx, n) ->
        FilledTonalButton(onClick = { chosenIndices.add(idx) }, shape = RoundedCornerShape(14.dp), modifier = Modifier.height(48.dp)) {
          Text("%,d".format(n), fontWeight = FontWeight.Bold)
        }
      }
    }
    Button(
      onClick = {
        val ok = chosenValues == problem.expectedOrder
        onResult(AnswerCheckResult(ok, if (ok) "Awesome ordering!" else "Compare place values from left to right!", problem.expectedOrder.joinToString(", ") { "%,d".format(it) }))
      },
      enabled = chosenIndices.size == problem.numbers.size,
      modifier = Modifier.fillMaxWidth().height(54.dp),
      shape = RoundedCornerShape(16.dp),
    ) { Text("Check Order", fontWeight = FontWeight.Bold) }
  }
}
