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
import com.example.model.*
import com.example.ui.components.*
import com.example.ui.theme.*
import com.example.viewmodel.AnswerCheckResult
import kotlin.math.abs

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PolygonTableRenderer(
  problem: PolygonTableProblem,
  onResult: (AnswerCheckResult) -> Unit,
) {
  var sidesIn by remember(problem.itemNumber) { mutableStateOf("") }

  Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(12.dp)) {
    if (problem.showFigure) {
      PolygonRegularShapeCanvas(problem.polygonSides)
      if (problem.givenName == null) {
        Text(
          "A polygon with ${problem.givenSides} sides is called a:",
          style = MaterialTheme.typography.titleMedium,
          color = InkDark,
          textAlign = TextAlign.Center,
        )
        FlowRow(
          horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
          verticalArrangement = Arrangement.spacedBy(8.dp),
          modifier = Modifier.fillMaxWidth(),
        ) {
          problem.choiceNames.forEach { name ->
            FilledTonalButton(
              onClick = {
                val ok = name.equals(problem.expectedName, ignoreCase = true)
                onResult(AnswerCheckResult(ok, if (ok) "Yes! A ${problem.expectedSides}-sided polygon is a ${problem.expectedName}." else "Count the sides and match the Greek prefix.", problem.expectedName))
              },
              shape = RoundedCornerShape(14.dp),
              modifier = Modifier.defaultMinSize(minHeight = 48.dp).testTag("poly_name_$name"),
            ) {
              Text(name, fontWeight = FontWeight.Bold)
            }
          }
        }
      } else {
        Text(
          "How many sides does a ${problem.givenName} have?",
          style = MaterialTheme.typography.titleMedium,
          color = InkDark,
          textAlign = TextAlign.Center,
        )
        AnswerDisplayBox(text = sidesIn, placeholder = "Number of sides")
        KidNumberPad(
          onDigit = { if (sidesIn.length < 3) sidesIn += it },
          onBackspace = { if (sidesIn.isNotEmpty()) sidesIn = sidesIn.dropLast(1) },
          onClear = { sidesIn = "" },
          onSubmit = {
            val ok = sidesIn.toIntOrNull() == problem.expectedSides
            onResult(AnswerCheckResult(ok, if (ok) "Correct! A ${problem.expectedName} has ${problem.expectedSides} sides." else "Count each straight edge on the figure!", "${problem.expectedSides}"))
          },
          submitEnabled = sidesIn.isNotEmpty(),
        )
      }
    } else {
      Text(
        "Tap the matching ${problem.expectedName} (${problem.expectedSides} sides):",
        style = MaterialTheme.typography.titleMedium,
        color = InkDark,
        textAlign = TextAlign.Center,
      )
      val options = remember(problem.itemNumber, problem.polygonSides) {
        val pool = (4..12).filter { it != problem.polygonSides }
        val picks = listOf(
          pool[(problem.itemNumber) % pool.size],
          pool[(problem.itemNumber + 2) % pool.size],
          pool[(problem.itemNumber + 5) % pool.size],
        ).distinct()
        (listOf(problem.polygonSides) + picks.take(3)).shuffled(java.util.Random(problem.itemNumber.toLong()))
      }
      Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
        options.chunked(2).forEach { pair ->
          Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
            pair.forEach { sidesChoice ->
              Box(
                modifier = Modifier
                  .weight(1f)
                  .clip(RoundedCornerShape(18.dp))
                  .border(1.5.dp, OutlineSoft, RoundedCornerShape(18.dp))
                  .clickable {
                    val ok = sidesChoice == problem.polygonSides
                    onResult(AnswerCheckResult(ok, if (ok) "Great eye! That polygon has ${problem.polygonSides} sides." else "Count the corners carefully!", "${problem.polygonSides}-sided ${problem.expectedName}"))
                  }
                  .padding(6.dp)
                  .testTag("poly_fig_pick_$sidesChoice"),
              ) {
                PolygonRegularShapeCanvas(sidesChoice, modifier = Modifier.height(110.dp))
              }
            }
          }
        }
      }
    }
  }
}

@Composable
fun PolygonRegularRenderer(
  problem: PolygonRegularProblem,
  onResult: (AnswerCheckResult) -> Unit,
) {
  Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(14.dp)) {
    Text(
      "Is this polygon REGULAR or IRREGULAR?",
      style = MaterialTheme.typography.titleMedium,
      color = InkDark,
      textAlign = TextAlign.Center,
    )
    PolygonPuzzle40Canvas(problem.polygonId)
    Text(
      "Tip: Regular polygons have ALL equal sides and ALL equal angles.",
      style = MaterialTheme.typography.bodySmall,
      color = InkMuted,
      textAlign = TextAlign.Center,
    )
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
      Button(
        onClick = {
          val ok = problem.isRegular
          onResult(AnswerCheckResult(ok, if (ok) "Yes! All sides and angles are equal → Regular." else "Check carefully: are ALL sides equal in length and ALL interior angles equal?", if (problem.isRegular) "Regular" else "Irregular"))
        },
        modifier = Modifier.weight(1f).height(58.dp).testTag("btn_poly_regular"),
        shape = RoundedCornerShape(18.dp),
        colors = ButtonDefaults.buttonColors(containerColor = LeafEmerald),
      ) {
        Text("Regular", fontSize = 18.sp, fontWeight = FontWeight.Bold)
      }
      Button(
        onClick = {
          val ok = !problem.isRegular
          onResult(AnswerCheckResult(ok, if (ok) "Yes! Sides or angles differ → Irregular." else "Check carefully: do any sides or angles differ, or do all of them match?", if (problem.isRegular) "Regular" else "Irregular"))
        },
        modifier = Modifier.weight(1f).height(58.dp).testTag("btn_poly_irregular"),
        shape = RoundedCornerShape(18.dp),
        colors = ButtonDefaults.buttonColors(containerColor = CoralTangerine),
      ) {
        Text("Irregular", fontSize = 18.sp, fontWeight = FontWeight.Bold)
      }
    }
  }
}

@Composable
fun TimeConvertRenderer(
  problem: TimeConvertProblem,
  onResult: (AnswerCheckResult) -> Unit,
) {
  var hourIn by remember(problem.itemNumber, problem.sourceDisplay) { mutableStateOf("") }
  var minIn by remember(problem.itemNumber, problem.sourceDisplay) { mutableStateOf("") }
  var period by remember(problem.itemNumber, problem.sourceDisplay) { mutableStateOf("AM") }
  var activeBox by remember(problem.itemNumber, problem.sourceDisplay) { mutableStateOf(0) }

  Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(12.dp)) {
    Text(problem.prompt, style = MaterialTheme.typography.titleMedium, color = InkDark, textAlign = TextAlign.Center)

    if (problem.clockHour != null && problem.clockMinute != null) {
      AnalogClockCanvas(problem.clockHour, problem.clockMinute, problem.clockIsPm)
    } else {
      Surface(color = WarmSurfaceVariant, shape = RoundedCornerShape(18.dp), modifier = Modifier.fillMaxWidth()) {
        Text(
          text = problem.sourceDisplay,
          fontSize = 28.sp,
          fontWeight = FontWeight.Bold,
          color = BagacayBlue,
          textAlign = TextAlign.Center,
          modifier = Modifier.padding(vertical = 14.dp, horizontal = 12.dp),
        )
      }
    }

    Row(
      horizontalArrangement = Arrangement.spacedBy(8.dp),
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier.fillMaxWidth(),
    ) {
      FieldBox("Hour", hourIn, activeBox == 0, Modifier.weight(1f)) { activeBox = 0 }
      Text(":", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = InkDark)
      FieldBox("Minute", minIn, activeBox == 1, Modifier.weight(1f)) { activeBox = 1 }
      if (!problem.to24Hour) {
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
          listOf("AM", "PM").forEach { p ->
            Box(
              modifier = Modifier
                .defaultMinSize(minWidth = 56.dp, minHeight = 32.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(if (period == p) BagacayBlue else WarmSurfaceVariant)
                .clickable { period = p }
                .padding(horizontal = 12.dp, vertical = 6.dp)
                .testTag("time_period_$p"),
              contentAlignment = Alignment.Center,
            ) {
              Text(p, fontWeight = FontWeight.Bold, color = if (period == p) Color.White else InkDark)
            }
          }
        }
      }
    }

    KidNumberPad(
      onDigit = { ch ->
        if (activeBox == 0) {
          if (hourIn.length < 2) hourIn += ch
          if (hourIn.length == 2) activeBox = 1
        } else {
          if (minIn.length < 2) minIn += ch
        }
      },
      onBackspace = {
        if (activeBox == 0 && hourIn.isNotEmpty()) hourIn = hourIn.dropLast(1)
        else if (activeBox == 1 && minIn.isNotEmpty()) minIn = minIn.dropLast(1)
        else if (activeBox == 1 && minIn.isEmpty()) activeBox = 0
      },
      onClear = {
        hourIn = ""
        minIn = ""
        activeBox = 0
      },
      onSubmit = {
        val hVal = hourIn.toIntOrNull() ?: -1
        val mVal = minIn.toIntOrNull() ?: -1
        val hPad = "%02d".format(hVal.coerceAtLeast(0))
        val mPad = "%02d".format(mVal.coerceAtLeast(0))
        val candidates = if (problem.to24Hour) {
          listOf("$hPad:$mPad", "$hPad$mPad", "$hVal:$mPad")
        } else {
          listOf("$hVal:$mPad $period", "$hPad:$mPad $period", "$hPad:$mPad", "$hPad$mPad")
        }
        val validNorm = problem.validAnswers.map { it.trim().uppercase() }
        val ok = hVal >= 0 && mVal in 0..59 && candidates.any { it.uppercase() in validNorm }
        onResult(
          AnswerCheckResult(
            isCorrect = ok,
            message = if (ok) "Exact time! ${problem.displayAnswer}." else "Check 12-hour vs 24-hour conversion (add 12 for 1–11 PM).",
            expectedDisplay = problem.displayAnswer,
          ),
        )
      },
      submitEnabled = hourIn.isNotEmpty() && minIn.isNotEmpty(),
    )
  }
}

@Composable
fun GeometryMeasureRenderer(
  problem: GeometryMeasureProblem,
  onResult: (AnswerCheckResult) -> Unit,
) {
  val inputs = remember(problem.itemNumber, problem.promptText) {
    mutableStateListOf<String>().apply { repeat(problem.fields.size) { add("") } }
  }
  var activeField by remember(problem.itemNumber, problem.promptText) { mutableStateOf(0) }
  val anyDecimal = remember(problem) { problem.fields.any { it.allowDecimal } }

  Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(10.dp)) {
    Text(problem.promptText, style = MaterialTheme.typography.titleMedium, color = InkDark, textAlign = TextAlign.Center)

    if (problem.shapeKind != MeasureShapeKind.TABLE_ONLY) {
      GeometryMeasureCanvas(
        shapeKind = problem.shapeKind,
        dimLabels = problem.dimLabels,
        hideLabels = problem.interactiveLabelCheck,
      )
    }

    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
      problem.fields.forEachIndexed { idx, spec ->
        val unitText = spec.unitLabel.orEmpty()
        val label = if (unitText.isNotBlank() && !spec.label.contains(unitText)) "${spec.label} ($unitText)" else spec.label
        FieldBox(label, inputs.getOrElse(idx) { "" }, activeField == idx, Modifier.weight(1f)) { activeField = idx }
      }
    }

    KidNumberPad(
      showDecimal = anyDecimal,
      onDigit = { ch ->
        val cur = inputs.getOrElse(activeField) { "" }
        if (cur.length < 10) inputs[activeField] = cur + ch
      },
      onBackspace = {
        val cur = inputs.getOrElse(activeField) { "" }
        if (cur.isNotEmpty()) inputs[activeField] = cur.dropLast(1)
      },
      onClear = { inputs[activeField] = "" },
      onSubmit = {
        val ok = problem.fields.indices.all { idx ->
          val spec = problem.fields[idx]
          val v = inputs[idx].toDoubleOrNull()
          v != null && (abs(v - spec.expectedAnswer) < 0.05 || spec.alternateAnswers.any { abs(v - it) < 0.05 })
        }
        val exp = problem.fields.joinToString(", ") { f ->
          val numStr = if (f.expectedAnswer % 1.0 == 0.0) "${f.expectedAnswer.toInt()}" else "${f.expectedAnswer}"
          val unitText = f.unitLabel.orEmpty()
          if (unitText.isNotBlank()) "$numStr $unitText" else numStr
        }
        onResult(AnswerCheckResult(ok, if (ok) "Awesome! $exp." else "Check the formula and dimensions carefully.", exp))
      },
      submitEnabled = inputs.all { it.isNotEmpty() },
    )
  }
}

@Composable
fun ThermometerRenderer(
  problem: ThermometerProblem,
  onResult: (AnswerCheckResult) -> Unit,
) {
  val expectedText = remember(problem) { "${formatCelsiusValue(problem.targetCelsius)}°C" }

  Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(12.dp)) {
    when (problem.mode) {
      ThermometerMode.MERCURY_READ -> {
        var input by remember(problem.itemNumber) { mutableStateOf("") }
        Text("Read the temperature shown on the thermometer (°C):", style = MaterialTheme.typography.titleMedium, color = InkDark, textAlign = TextAlign.Center)
        MercuryThermometerCanvas(problem.targetCelsius)
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(8.dp),
          modifier = Modifier.fillMaxWidth(),
        ) {
          FilledTonalButton(
            onClick = {
              input = if (input.startsWith("-")) input.drop(1) else "-$input"
            },
            modifier = Modifier.height(58.dp).testTag("thermo_neg_toggle"),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.filledTonalButtonColors(
              containerColor = if (input.startsWith("-")) CoralLight else WarmSurfaceVariant,
            ),
          ) {
            Text("+ / −", fontWeight = FontWeight.Bold, fontSize = 18.sp)
          }
          Box(modifier = Modifier.weight(1f)) {
            AnswerDisplayBox(text = if (input.isEmpty()) "" else "$input °C", placeholder = "e.g. -10")
          }
        }
        KidNumberPad(
          onDigit = { if (input.length < 5) input += it },
          onBackspace = { if (input.isNotEmpty()) input = input.dropLast(1) },
          onClear = { input = "" },
          onSubmit = {
            val v = input.toDoubleOrNull()
            val ok = v != null && abs(v - problem.targetCelsius) < 0.1
            onResult(AnswerCheckResult(ok, if (ok) "Exact reading: $expectedText!" else "Check if it is below 0 (−) or above 0!", expectedText))
          },
          submitEnabled = input.isNotEmpty() && input != "-",
        )
      }

      ThermometerMode.MERCURY_SET -> {
        var sliderVal by remember(problem.itemNumber) { mutableStateOf(0f) }
        Text(
          "Move the thermometer level to $expectedText:",
          style = MaterialTheme.typography.titleMedium,
          color = InkDark,
          textAlign = TextAlign.Center,
        )
        MercuryThermometerCanvas(sliderVal.toDouble())
        Text(
          "Current setting: ${sliderVal.toInt()}°C",
          style = MaterialTheme.typography.titleLarge,
          fontWeight = FontWeight.Bold,
          color = BagacayBlue,
        )
        Slider(
          value = sliderVal,
          onValueChange = { sliderVal = (kotlin.math.round(it / 5f) * 5f).coerceIn(-30f, 50f) },
          valueRange = -30f..50f,
          steps = 15,
          modifier = Modifier.fillMaxWidth().testTag("thermo_slider"),
        )
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
          OutlinedButton(
            onClick = { sliderVal = (sliderVal - 5f).coerceAtLeast(-30f) },
            modifier = Modifier.weight(1f).height(48.dp).testTag("thermo_minus_5"),
          ) { Text("−5°C", fontWeight = FontWeight.Bold) }
          OutlinedButton(
            onClick = { sliderVal = (sliderVal + 5f).coerceAtMost(50f) },
            modifier = Modifier.weight(1f).height(48.dp).testTag("thermo_plus_5"),
          ) { Text("+5°C", fontWeight = FontWeight.Bold) }
        }
        Button(
          onClick = {
            val ok = abs(sliderVal.toDouble() - problem.targetCelsius) < 0.1
            onResult(AnswerCheckResult(ok, if (ok) "Great! Thermometer set to $expectedText." else "Check the scale marks carefully and slide to match the given temperature.", expectedText))
          },
          modifier = Modifier.fillMaxWidth().height(54.dp).testTag("submit_thermo_set"),
          shape = RoundedCornerShape(18.dp),
        ) {
          Text("Check Thermometer", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
      }

      ThermometerMode.DIGITAL_READ, ThermometerMode.DIGITAL_SET -> {
        var input by remember(problem.itemNumber) { mutableStateOf("") }
        val isRead = problem.mode == ThermometerMode.DIGITAL_READ
        Text(
          text = if (isRead) "Read the temperature on the digital thermometer:" else "Enter $expectedText onto the digital thermometer:",
          style = MaterialTheme.typography.titleMedium,
          color = InkDark,
          textAlign = TextAlign.Center,
        )
        DigitalThermometerGraphic(
          readoutText = if (isRead) expectedText else if (input.isEmpty()) "--°C" else "${input}°C",
        )
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(8.dp),
          modifier = Modifier.fillMaxWidth(),
        ) {
          FilledTonalButton(
            onClick = {
              input = if (input.startsWith("-")) input.drop(1) else "-$input"
            },
            modifier = Modifier.height(58.dp).testTag("digital_neg_toggle"),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.filledTonalButtonColors(
              containerColor = if (input.startsWith("-")) CoralLight else WarmSurfaceVariant,
            ),
          ) {
            Text("+ / −", fontWeight = FontWeight.Bold, fontSize = 18.sp)
          }
          Box(modifier = Modifier.weight(1f)) {
            AnswerDisplayBox(text = if (input.isEmpty()) "" else "$input °C", placeholder = "e.g. 37.2 or -7")
          }
        }
        KidNumberPad(
          showDecimal = true,
          onDigit = { if (input.length < 7) input += it },
          onBackspace = { if (input.isNotEmpty()) input = input.dropLast(1) },
          onClear = { input = "" },
          onSubmit = {
            val v = input.toDoubleOrNull()
            val ok = v != null && abs(v - problem.targetCelsius) < 0.05
            onResult(AnswerCheckResult(ok, if (ok) "Perfect! $expectedText." else "Check the minus sign (−) and decimal point.", expectedText))
          },
          submitEnabled = input.isNotEmpty() && input != "-" && input != ".",
        )
      }
    }
  }
}
