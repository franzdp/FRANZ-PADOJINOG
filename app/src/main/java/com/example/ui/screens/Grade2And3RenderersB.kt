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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.*
import com.example.ui.components.*
import com.example.ui.theme.*
import com.example.viewmodel.AnswerCheckResult
import com.example.viewmodel.AnswerChecker

@Composable
fun EquationPartRenderer(
  problem: EquationPartProblem,
  onResult: (AnswerCheckResult) -> Unit,
) {
  val expectedLabel = when (problem.underlinedPart) {
    SubSentencePart.MINUEND -> "Minuend"
    SubSentencePart.SUBTRAHEND -> "Subtrahend"
    SubSentencePart.DIFFERENCE -> "Difference"
  }
  Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(14.dp)) {
    Text("Identify the underlined part of the subtraction sentence:", style = MaterialTheme.typography.titleMedium, color = InkDark, textAlign = TextAlign.Center)
    Surface(color = WarmSurfaceVariant, shape = RoundedCornerShape(18.dp), modifier = Modifier.fillMaxWidth()) {
      Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(18.dp),
      ) {
        Text(
          text = "${problem.minuend}",
          fontSize = 34.sp,
          fontWeight = FontWeight.Bold,
          color = if (problem.underlinedPart == SubSentencePart.MINUEND) CoralTangerine else InkDark,
          textDecoration = if (problem.underlinedPart == SubSentencePart.MINUEND) TextDecoration.Underline else null,
        )
        Text("  -  ", fontSize = 30.sp, fontWeight = FontWeight.Bold, color = InkDark)
        Text(
          text = "${problem.subtrahend}",
          fontSize = 34.sp,
          fontWeight = FontWeight.Bold,
          color = if (problem.underlinedPart == SubSentencePart.SUBTRAHEND) CoralTangerine else InkDark,
          textDecoration = if (problem.underlinedPart == SubSentencePart.SUBTRAHEND) TextDecoration.Underline else null,
        )
        Text("  =  ", fontSize = 30.sp, fontWeight = FontWeight.Bold, color = InkDark)
        Text(
          text = "${problem.difference}",
          fontSize = 34.sp,
          fontWeight = FontWeight.Bold,
          color = if (problem.underlinedPart == SubSentencePart.DIFFERENCE) CoralTangerine else InkDark,
          textDecoration = if (problem.underlinedPart == SubSentencePart.DIFFERENCE) TextDecoration.Underline else null,
        )
      }
    }
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
      listOf("Minuend", "Subtrahend", "Difference").forEach { opt ->
        Button(
          onClick = {
            val ok = opt == expectedLabel
            onResult(AnswerCheckResult(ok, if (ok) "Correct! That number is the $expectedLabel." else "Remember: Minuend - Subtrahend = Difference.", expectedLabel))
          },
          modifier = Modifier.weight(1f).height(56.dp).testTag("eq_part_$opt"),
          shape = RoundedCornerShape(16.dp),
          colors = ButtonDefaults.buttonColors(containerColor = BagacayBlue),
          contentPadding = PaddingValues(4.dp),
        ) { Text(opt, fontWeight = FontWeight.Bold, fontSize = 13.sp, maxLines = 1) }
      }
    }
  }
}

@Composable
fun ShapeClassificationRenderer(
  problem: ShapeClassificationProblem,
  onResult: (AnswerCheckResult) -> Unit,
) {
  Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(12.dp)) {
    Text(problem.prompt, style = MaterialTheme.typography.titleMedium, color = InkDark, textAlign = TextAlign.Center)

    when (problem.mode) {
      ShapeExerciseMode.NAME_OBJECT_SHAPE -> {
        problem.shownObject?.let { RealWorldObjectVisualBox(it, modifier = Modifier.fillMaxWidth(0.75f)) }
        @OptIn(ExperimentalLayoutApi::class)
        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally), verticalArrangement = Arrangement.spacedBy(8.dp)) {
          BasicShapeKind.entries.forEach { shapeKind ->
            Surface(
              color = BagacayBlueLight,
              shape = RoundedCornerShape(16.dp),
              modifier = Modifier
                .defaultMinSize(minWidth = 136.dp, minHeight = 56.dp)
                .border(1.5.dp, BagacayBlue, RoundedCornerShape(16.dp))
                .clickable {
                  val ok = shapeKind == problem.targetShape
                  onResult(AnswerCheckResult(ok, if (ok) "Yes! It is a ${problem.targetShape.label}." else "Count the sides and corners carefully!", problem.targetShape.label))
                }
                .testTag("shape_opt_${shapeKind.name}"),
            ) {
              Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)) {
                BasicShapeCanvas(shapeKind, size = 30.dp, fill = Color.White)
                Text(shapeKind.label, fontWeight = FontWeight.Bold, color = InkDark)
              }
            }
          }
        }
      }

      ShapeExerciseMode.PICK_OBJECT_BY_SHAPE, ShapeExerciseMode.PICK_FROM_THREE_OBJECTS -> {
        BasicShapeCanvas(problem.targetShape, size = 66.dp, fill = MangoGoldLight)
        @OptIn(ExperimentalLayoutApi::class)
        FlowRow(horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally), verticalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
          problem.objectChoices.forEachIndexed { idx, obj ->
            Box(
              modifier = Modifier
                .defaultMinSize(minWidth = 140.dp, minHeight = 90.dp)
                .clickable {
                  val ok = obj.shapeKind == problem.targetShape
                  onResult(AnswerCheckResult(ok, if (ok) "Great match! The ${obj.label} is shaped like a ${problem.targetShape.label}!" else "Look closely at the outline shape of the ${obj.label}.", problem.objectChoices.firstOrNull { it.shapeKind == problem.targetShape }?.label ?: problem.targetShape.label))
                }
                .testTag("obj_choice_$idx"),
            ) {
              RealWorldObjectVisualBox(obj)
            }
          }
        }
      }

      ShapeExerciseMode.PICK_FROM_THREE_SHAPES -> {
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
          problem.shapeChoices.forEach { sk ->
            Surface(
              color = WarmSurfaceVariant,
              shape = RoundedCornerShape(16.dp),
              modifier = Modifier
                .weight(1f)
                .defaultMinSize(minHeight = 96.dp)
                .border(1.5.dp, OutlineSoft, RoundedCornerShape(16.dp))
                .clickable {
                  val ok = sk == problem.targetShape
                  onResult(AnswerCheckResult(ok, if (ok) "Spot on! That is a ${problem.targetShape.label}!" else "Check the sides and corners!", problem.targetShape.label))
                }
                .testTag("three_shape_${sk.name}"),
            ) {
              Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier.padding(10.dp)) {
                BasicShapeCanvas(sk, size = 56.dp)
              }
            }
          }
        }
      }
    }
  }
}

@Composable
fun CalendarRenderer(
  problem: CalendarProblem,
  onResult: (AnswerCheckResult) -> Unit,
) {
  var textInput by remember(problem) { mutableStateOf("") }

  Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(12.dp)) {
    if (problem.mode == CalendarMode.APRIL_CALENDAR_READ) {
      April2014CalendarCard()
    }
    Surface(color = WarmSurfaceVariant, shape = RoundedCornerShape(18.dp), modifier = Modifier.fillMaxWidth()) {
      Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(problem.prompt, style = MaterialTheme.typography.titleMedium, color = InkDark, textAlign = TextAlign.Center)
        if (problem.referenceLabel.isNotBlank()) {
          Text(problem.referenceLabel, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = BagacayBlue, textAlign = TextAlign.Center)
        }
      }
    }

    if (problem.quickChoices.isNotEmpty()) {
      @OptIn(ExperimentalLayoutApi::class)
      FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally), verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
        problem.quickChoices.forEach { opt ->
          FilledTonalButton(
            onClick = {
              val clean = AnswerChecker.normalizeWords(opt)
              val ok = problem.validAnswers.any { AnswerChecker.normalizeWords(it) == clean }
              onResult(AnswerCheckResult(ok, if (ok) "Correct! ${problem.displayAnswer}!" else "Recite the days/months in order to check!", problem.displayAnswer))
            },
            modifier = Modifier.defaultMinSize(minWidth = 84.dp, minHeight = 48.dp).testTag("cal_choice_$opt"),
            shape = RoundedCornerShape(14.dp),
          ) { Text(opt, fontWeight = FontWeight.Bold) }
        }
      }
    } else {
      OutlinedTextField(
        value = textInput,
        onValueChange = { textInput = it },
        label = { Text("Enter answer") },
        singleLine = true,
        modifier = Modifier.fillMaxWidth().testTag("calendar_text_input"),
      )
      Button(
        onClick = {
          val clean = AnswerChecker.normalizeWords(textInput)
          val ok = problem.validAnswers.any { AnswerChecker.normalizeWords(it) == clean }
          onResult(AnswerCheckResult(ok, if (ok) "Correct! ${problem.displayAnswer}!" else "Check the April 2014 calendar rows carefully!", problem.displayAnswer))
        },
        enabled = textInput.isNotBlank(),
        modifier = Modifier.fillMaxWidth().height(54.dp).testTag("calendar_submit"),
        shape = RoundedCornerShape(16.dp),
      ) { Text("Check Calendar Answer", fontWeight = FontWeight.Bold) }
    }
  }
}

@Composable
fun ExpandedFormRenderer(
  problem: ExpandedFormProblem,
  onResult: (AnswerCheckResult) -> Unit,
) {
  val digits = problem.number.toString()
  val isThreeDigit = problem.number >= 100
  val fieldCount = if (isThreeDigit) 3 else 2
  val inputs = remember(problem) { mutableStateListOf<String>().apply { repeat(fieldCount) { add("") } } }
  var active by remember(problem) { mutableStateOf(0) }
  var usePlaceNames by remember(problem) { mutableStateOf(problem.mode == ExpandedFormMode.FIRST_FORMAT) }

  val hDigit = if (isThreeDigit) digits[0].digitToInt() else 0
  val tDigit = if (isThreeDigit) digits[1].digitToInt() else digits[0].digitToInt()
  val oDigit = digits.last().digitToInt()

  Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(10.dp)) {
    Text(problem.promptDisplay, style = MaterialTheme.typography.titleMedium, color = InkDark, textAlign = TextAlign.Center)
    Text("${problem.number}", fontSize = 36.sp, fontWeight = FontWeight.Bold, color = BagacayBlue)

    if (problem.mode == ExpandedFormMode.ANY_FORMAT) {
      Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
        FilterChip(
          selected = !usePlaceNames,
          onClick = { usePlaceNames = false },
          label = { Text("Sum Form (___ + ___)") },
          modifier = Modifier.weight(1f),
        )
        FilterChip(
          selected = usePlaceNames,
          onClick = { usePlaceNames = true },
          label = { Text("Word Place Form") },
          modifier = Modifier.weight(1f),
        )
      }
    }

    val labels = when {
      usePlaceNames && isThreeDigit -> listOf("Hundreds", "Tens", "Ones")
      usePlaceNames -> listOf("Tens", "Ones")
      isThreeDigit -> listOf("Hundreds Value", "Tens Value", "Ones Value")
      else -> listOf("Tens Value", "Ones Value")
    }

    Row(horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.fillMaxWidth()) {
      labels.forEachIndexed { idx, lbl ->
        FieldBox(lbl, inputs[idx], active == idx, Modifier.weight(1f)) { active = idx }
      }
    }

    KidNumberPad(
      onDigit = { ch -> if (inputs[active].length < 4) inputs[active] = inputs[active] + ch },
      onBackspace = { if (inputs[active].isNotEmpty()) inputs[active] = inputs[active].dropLast(1) },
      onClear = { inputs[active] = "" },
      onSubmit = {
        val expectedList = when {
          usePlaceNames && isThreeDigit -> listOf(hDigit, tDigit, oDigit)
          usePlaceNames -> listOf(tDigit, oDigit)
          isThreeDigit -> listOf(hDigit * 100, tDigit * 10, oDigit)
          else -> listOf(tDigit * 10, oDigit)
        }
        val ok = inputs.zip(expectedList).all { (u, e) -> u.toIntOrNull() == e }
        onResult(AnswerCheckResult(ok, if (ok) "Awesome expanded form!" else "Check each digit's place and zeros!", expectedList.joinToString(" + ")))
      },
      submitEnabled = inputs.all { it.isNotEmpty() },
    )
  }
}

@Composable
fun CompareWordRenderer(
  problem: CompareWordProblem,
  onResult: (AnswerCheckResult) -> Unit,
) {
  Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(14.dp)) {
    Surface(color = WarmSurfaceVariant, shape = RoundedCornerShape(18.dp), modifier = Modifier.fillMaxWidth()) {
      Text(problem.prompt, style = MaterialTheme.typography.titleMedium, color = InkDark, textAlign = TextAlign.Center, modifier = Modifier.padding(14.dp))
    }
    Column(verticalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
      listOf(problem.optionA, problem.optionB).forEachIndexed { idx, opt ->
        Button(
          onClick = {
            val ok = opt == problem.correctOption
            onResult(AnswerCheckResult(ok, if (ok) "Spot on! ${problem.correctOption}!" else "Compare the numbers digit by digit from left to right!", problem.correctOption))
          },
          modifier = Modifier.fillMaxWidth().defaultMinSize(minHeight = 56.dp).testTag("compare_word_$idx"),
          shape = RoundedCornerShape(16.dp),
          colors = ButtonDefaults.buttonColors(containerColor = if (idx == 0) BagacayBlue else BerryPurple),
        ) { Text(opt, fontSize = 16.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center) }
      }
    }
  }
}

@Composable
fun MultDivSentenceRenderer(
  problem: MultDivSentenceProblem,
  onResult: (AnswerCheckResult) -> Unit,
) {
  var f1 by remember(problem) { mutableStateOf("") }
  var f2 by remember(problem) { mutableStateOf("") }
  var res by remember(problem) { mutableStateOf("") }
  var active by remember(problem) { mutableStateOf(0) }
  var builtHops by remember(problem) { mutableStateOf(0) }

  Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(10.dp)) {
    Text(problem.prompt, style = MaterialTheme.typography.titleMedium, color = InkDark, textAlign = TextAlign.Center)
    if (problem.visualExpression.isNotBlank()) {
      Surface(color = WarmSurfaceVariant, shape = RoundedCornerShape(16.dp), modifier = Modifier.fillMaxWidth()) {
        Text(problem.visualExpression, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = InkDark, textAlign = TextAlign.Center, modifier = Modifier.padding(14.dp))
      }
    }

    if (problem.mode == MultDivExerciseMode.NUMBER_LINE_READ) {
      NumberLineHopCanvas(problem.numberLineMax, problem.expectedFirst, problem.expectedSecond)
    } else if (problem.mode == MultDivExerciseMode.NUMBER_LINE_BUILD) {
      NumberLineHopCanvas(problem.numberLineMax, builtHops, problem.expectedSecond)
      Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
        OutlinedButton(onClick = { if (builtHops > 0) builtHops-- }, modifier = Modifier.weight(1f).height(48.dp)) { Text("Undo Hop") }
        FilledTonalButton(
          onClick = { if ((builtHops + 1) * problem.expectedSecond <= problem.numberLineMax) builtHops++ },
          modifier = Modifier.weight(1.5f).height(48.dp).testTag("build_hop_btn"),
        ) { Text("+1 Jump of ${problem.expectedSecond} ($builtHops jumps)", fontWeight = FontWeight.Bold) }
      }
    } else if (problem.mode == MultDivExerciseMode.GROUPING_PICTURE_TO_DIV && problem.pictureKind != null) {
      ObjectCollectionTray(kind = problem.pictureKind, count = problem.expectedFirst, maxPerRow = problem.expectedSecond.coerceIn(2, 6))
    }

    when (problem.mode) {
      MultDivExerciseMode.MULT_PROPERTY_ONLY -> {
        val props = listOf("CPM" to "CPM (Commutative)", "APM" to "APM (Associative)", "IPM" to "IPM (Identity)", "ZPM" to "ZPM (Zero)", "DPM" to "DPM (Distributive)")
        @OptIn(ExperimentalLayoutApi::class)
        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally), verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
          props.forEach { (code, label) ->
            Button(
              onClick = {
                val ok = code == problem.expectedPropertyCode
                onResult(AnswerCheckResult(ok, if (ok) "Yes! $label!" else "Check if order changed (CPM), grouping changed (APM), ×1 (IPM), ×0 (ZPM), or split sum (DPM).", problem.expectedPropertyCode))
              },
              modifier = Modifier.defaultMinSize(minWidth = 140.dp, minHeight = 50.dp).testTag("prop_btn_$code"),
              shape = RoundedCornerShape(14.dp),
            ) { Text(label, fontWeight = FontWeight.Bold, fontSize = 13.sp) }
          }
        }
      }

      MultDivExerciseMode.MULT_PROPERTY_MISSING_VALUE -> {
        AnswerDisplayBox(res, "Missing number")
        KidNumberPad(
          onDigit = { if (res.length < 4) res += it },
          onBackspace = { if (res.isNotEmpty()) res = res.dropLast(1) },
          onClear = { res = "" },
          onSubmit = {
            val ok = res.toIntOrNull() == problem.expectedResult
            onResult(AnswerCheckResult(ok, if (ok) "Correct! Missing number is ${problem.expectedResult}!" else "Balance both sides using the multiplication property!", "${problem.expectedResult}"))
          },
          submitEnabled = res.isNotEmpty(),
        )
      }

      MultDivExerciseMode.NUMBER_LINE_BUILD -> {
        AnswerDisplayBox(res, "Product (${problem.expectedFirst} × ${problem.expectedSecond} = ?)")
        KidNumberPad(
          onDigit = { if (res.length < 4) res += it },
          onBackspace = { if (res.isNotEmpty()) res = res.dropLast(1) },
          onClear = { res = "" },
          onSubmit = {
            val ok = builtHops == problem.expectedFirst && res.toIntOrNull() == problem.expectedResult
            onResult(AnswerCheckResult(ok, if (ok) "Great jumps! ${problem.expectedFirst} jumps of ${problem.expectedSecond} = ${problem.expectedResult}!" else "Make ${problem.expectedFirst} jumps of ${problem.expectedSecond} on the number line, then type where you land!", "${problem.expectedFirst} jumps -> ${problem.expectedResult}"))
          },
          submitEnabled = res.isNotEmpty() && builtHops > 0,
        )
      }

      MultDivExerciseMode.INVERSE_MISSING_FACTOR_AND_QUOTIENT -> {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
          FieldBox("Missing Factor", f1, active == 0, Modifier.weight(1f)) { active = 0 }
          FieldBox("Quotient", res, active == 2, Modifier.weight(1f)) { active = 2 }
        }
        KidNumberPad(
          onDigit = { ch -> if (active == 0) { if (f1.length < 3) f1 += ch } else { if (res.length < 3) res += ch } },
          onBackspace = { if (active == 0 && f1.isNotEmpty()) f1 = f1.dropLast(1) else if (active != 0 && res.isNotEmpty()) res = res.dropLast(1) },
          onClear = { if (active == 0) f1 = "" else res = "" },
          onSubmit = {
            val ok = f1.toIntOrNull() == problem.expectedFirst && res.toIntOrNull() == problem.expectedResult
            val msg = if (ok) {
              if (problem.expectedFirst == problem.expectedResult) {
                "Awesome inverse fact! Both blanks are ${problem.expectedResult}!"
              } else {
                "Awesome inverse fact! Missing factor = ${problem.expectedFirst}, quotient = ${problem.expectedResult}!"
              }
            } else {
              "Check both equations carefully — what number makes each multiplication and division sentence true?"
            }
            onResult(AnswerCheckResult(ok, msg, "${problem.expectedFirst}, ${problem.expectedResult}"))
          },
          submitEnabled = f1.isNotEmpty() && res.isNotEmpty(),
        )
      }

      else -> {
        val isMult = problem.mode == MultDivExerciseMode.PHRASE_OR_REPEAT_TO_MULT || problem.mode == MultDivExerciseMode.NUMBER_LINE_READ
        val sym = if (isMult) "×" else "÷"
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
          FieldBox(if (isMult) "Groups" else "Total", f1, active == 0, Modifier.weight(1f)) { active = 0 }
          Text(sym, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = BagacayBlue)
          FieldBox(if (isMult) "Each" else "Divisor", f2, active == 1, Modifier.weight(1f)) { active = 1 }
          Text("=", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = InkDark)
          FieldBox(if (isMult) "Product" else "Quotient", res, active == 2, Modifier.weight(1f)) { active = 2 }
        }
        KidNumberPad(
          onDigit = { ch -> when (active) { 0 -> if (f1.length < 4) f1 += ch; 1 -> if (f2.length < 4) f2 += ch; else -> if (res.length < 4) res += ch } },
          onBackspace = { when (active) { 0 -> if (f1.isNotEmpty()) f1 = f1.dropLast(1); 1 -> if (f2.isNotEmpty()) f2 = f2.dropLast(1); else -> if (res.isNotEmpty()) res = res.dropLast(1) } },
          onClear = { when (active) { 0 -> f1 = ""; 1 -> f2 = ""; else -> res = "" } },
          onSubmit = {
            val ok = f1.toIntOrNull() == problem.expectedFirst && f2.toIntOrNull() == problem.expectedSecond && res.toIntOrNull() == problem.expectedResult
            onResult(AnswerCheckResult(ok, if (ok) "Spot on! ${problem.expectedFirst} $sym ${problem.expectedSecond} = ${problem.expectedResult}!" else "Check each part of the $sym equation carefully!", "${problem.expectedFirst} $sym ${problem.expectedSecond} = ${problem.expectedResult}"))
          },
          submitEnabled = f1.isNotEmpty() && f2.isNotEmpty() && res.isNotEmpty(),
        )
      }
    }
  }
}

@Composable
fun LineSurfaceRenderer(
  problem: LineSurfaceProblem,
  onResult: (AnswerCheckResult) -> Unit,
) {
  Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(12.dp)) {
    Text(problem.prompt, style = MaterialTheme.typography.titleMedium, color = InkDark, textAlign = TextAlign.Center)
    LineOrSurfaceVisual(problem.figureId, problem.figureLabel)
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
      listOf(problem.optionA, problem.optionB).forEachIndexed { idx, opt ->
        Button(
          onClick = {
            val ok = opt.equals(problem.correctOption, ignoreCase = true)
            onResult(AnswerCheckResult(ok, if (ok) "Yes! ${problem.correctOption}!" else "Check if it bends/curves or is completely straight/flat!", problem.correctOption))
          },
          modifier = Modifier.weight(1f).defaultMinSize(minHeight = 56.dp).testTag("line_surface_opt_$idx"),
          shape = RoundedCornerShape(16.dp),
          colors = ButtonDefaults.buttonColors(containerColor = if (idx == 0) BagacayBlue else CoralTangerine),
        ) { Text(opt, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center) }
      }
    }
  }
}
