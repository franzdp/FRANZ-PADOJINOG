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
import com.example.ui.components.FractionModelCanvas
import com.example.ui.components.KidNumberPad
import com.example.ui.theme.*
import com.example.viewmodel.AnswerCheckResult
import kotlin.math.abs

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DivisibilityCheckRenderer(
  problem: DivisibilityCheckProblem,
  onResult: (AnswerCheckResult) -> Unit,
) {
  val selected = remember(problem.itemNumber, problem.number) { mutableStateListOf<Int>() }
  var noneSelected by remember(problem.itemNumber, problem.number) { mutableStateOf(false) }

  val expectedSorted = problem.validDivisors.sorted()
  val expectedDisplay = if (expectedSorted.isEmpty()) "None" else expectedSorted.joinToString(", ")

  Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(12.dp)) {
    Text(
      "Select ALL numbers that divide ${"%,d".format(problem.number)} evenly:",
      style = MaterialTheme.typography.titleMedium,
      color = InkDark,
      textAlign = TextAlign.Center,
    )
    Surface(color = WarmSurfaceVariant, shape = RoundedCornerShape(20.dp), modifier = Modifier.fillMaxWidth()) {
      Text(
        text = "%,d".format(problem.number),
        fontSize = 40.sp,
        fontWeight = FontWeight.Bold,
        color = BagacayBlue,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(vertical = 14.dp),
      )
    }

    FlowRow(
      horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
      verticalArrangement = Arrangement.spacedBy(8.dp),
      modifier = Modifier.fillMaxWidth(),
    ) {
      problem.divisorsToCheck.forEach { d ->
        val isPicked = d in selected
        Box(
          modifier = Modifier
            .defaultMinSize(minWidth = 54.dp, minHeight = 48.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(if (isPicked) BagacayBlue else WarmSurfaceVariant)
            .border(1.5.dp, if (isPicked) BagacayBlue else OutlineSoft, RoundedCornerShape(14.dp))
            .clickable {
              noneSelected = false
              if (isPicked) selected.remove(d) else selected.add(d)
            }
            .padding(horizontal = 14.dp, vertical = 10.dp)
            .testTag("div_chip_$d"),
          contentAlignment = Alignment.Center,
        ) {
          Text(
            text = "÷ $d",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = if (isPicked) Color.White else InkDark,
          )
        }
      }
      Box(
        modifier = Modifier
          .defaultMinSize(minWidth = 88.dp, minHeight = 48.dp)
          .clip(RoundedCornerShape(14.dp))
          .background(if (noneSelected) CoralTangerine else WarmSurfaceVariant)
          .border(1.5.dp, if (noneSelected) CoralTangerine else OutlineSoft, RoundedCornerShape(14.dp))
          .clickable {
            selected.clear()
            noneSelected = !noneSelected
          }
          .padding(horizontal = 14.dp, vertical = 10.dp)
          .testTag("div_chip_none"),
        contentAlignment = Alignment.Center,
      ) {
        Text(
          text = "None",
          fontWeight = FontWeight.Bold,
          fontSize = 15.sp,
          color = if (noneSelected) Color.White else InkDark,
        )
      }
    }

    Button(
      onClick = {
        val picked = if (noneSelected) emptyList() else selected.sorted()
        val ok = picked == expectedSorted
        onResult(
          AnswerCheckResult(
            isCorrect = ok,
            message = if (ok) "Awesome! Divisible by $expectedDisplay." else "Check every divisibility rule for ${"%,d".format(problem.number)}.",
            expectedDisplay = expectedDisplay,
          ),
        )
      },
      enabled = selected.isNotEmpty() || noneSelected,
      modifier = Modifier.fillMaxWidth().height(54.dp).testTag("submit_div_check"),
      shape = RoundedCornerShape(18.dp),
    ) {
      Text("Check Divisors", fontWeight = FontWeight.Bold, fontSize = 16.sp)
    }
  }
}

@Composable
fun SmallestDigitDivisibilityRenderer(
  problem: SmallestDigitDivisibilityProblem,
  onResult: (AnswerCheckResult) -> Unit,
) {
  var chosenDigit by remember(problem.itemNumber, problem.templatePattern) { mutableStateOf<Int?>(null) }
  val preview = if (chosenDigit != null) {
    problem.templatePattern.replace("_", chosenDigit.toString())
  } else {
    problem.templatePattern.replace("_", " ? ")
  }

  Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(12.dp)) {
    Text(
      "Choose the SMALLEST digit (0–9) so the number is divisible by ${problem.divisor}:",
      style = MaterialTheme.typography.titleMedium,
      color = InkDark,
      textAlign = TextAlign.Center,
    )
    Surface(color = WarmSurfaceVariant, shape = RoundedCornerShape(20.dp), modifier = Modifier.fillMaxWidth()) {
      Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(preview, fontSize = 36.sp, fontWeight = FontWeight.Bold, color = BagacayBlue)
        Text("Divisible by ${problem.divisor}", style = MaterialTheme.typography.labelLarge, color = TealOcean, fontWeight = FontWeight.Bold)
      }
    }

    for (rowDigits in listOf(listOf(0, 1, 2, 3, 4), listOf(5, 6, 7, 8, 9))) {
      Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
        rowDigits.forEach { d ->
          val isSel = chosenDigit == d
          FilledTonalButton(
            onClick = { chosenDigit = d },
            modifier = Modifier.weight(1f).height(52.dp).testTag("small_digit_$d"),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.filledTonalButtonColors(
              containerColor = if (isSel) BagacayBlue else WarmSurfaceVariant,
              contentColor = if (isSel) Color.White else InkDark,
            ),
            contentPadding = PaddingValues(0.dp),
          ) {
            Text("$d", fontSize = 20.sp, fontWeight = FontWeight.Bold)
          }
        }
      }
    }

    Button(
      onClick = {
        val d = chosenDigit ?: return@Button
        val ok = d == problem.expectedSmallestDigit
        onResult(
          AnswerCheckResult(
            isCorrect = ok,
            message = if (ok) "Correct! ${problem.templatePattern.replace("_", d.toString())} is divisible by ${problem.divisor}." else "Check if there is an even smaller valid digit (or non-zero leading digit).",
            expectedDisplay = "${problem.expectedSmallestDigit}",
          ),
        )
      },
      enabled = chosenDigit != null,
      modifier = Modifier.fillMaxWidth().height(52.dp).testTag("submit_small_digit"),
      shape = RoundedCornerShape(16.dp),
    ) {
      Text("Check Digit", fontWeight = FontWeight.Bold, fontSize = 16.sp)
    }
  }
}

@Composable
fun DivisibilityRuleJudgeRenderer(
  problem: DivisibilityRuleJudgeProblem,
  onResult: (AnswerCheckResult) -> Unit,
) {
  Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(12.dp)) {
    Text(
      text = if (problem.isYesNoStep) {
        "Is ${"%,d".format(problem.firstNumber)} divisible by ${problem.secondNumber}?"
      } else {
        "Why is ${"%,d".format(problem.firstNumber)} divisible (or not) by ${problem.secondNumber}?"
      },
      style = MaterialTheme.typography.titleMedium,
      color = InkDark,
      textAlign = TextAlign.Center,
    )
    Surface(color = WarmSurfaceVariant, shape = RoundedCornerShape(20.dp), modifier = Modifier.fillMaxWidth()) {
      Text(
        text = "${"%,d".format(problem.firstNumber)}  ÷  ${problem.secondNumber}",
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
        color = BagacayBlue,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(vertical = 14.dp),
      )
    }

    if (problem.isYesNoStep) {
      Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
        problem.choiceOptions.forEach { opt ->
          Button(
            onClick = {
              val ok = opt.equals(problem.correctChoice, ignoreCase = true)
              onResult(
                AnswerCheckResult(
                  isCorrect = ok,
                  message = if (ok) "Correct! The answer is ${problem.correctChoice}." else "Check the divisibility rule for ${problem.secondNumber}.",
                  expectedDisplay = problem.correctChoice,
                ),
              )
            },
            modifier = Modifier.weight(1f).height(58.dp).testTag("judge_choice_$opt"),
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(containerColor = if (opt == "Yes") LeafEmerald else CoralTangerine),
          ) {
            Text(opt, fontSize = 20.sp, fontWeight = FontWeight.Bold)
          }
        }
      }
    } else {
      problem.choiceOptions.forEachIndexed { idx, opt ->
        OutlinedButton(
          onClick = {
            val ok = opt == problem.correctChoice
            onResult(
              AnswerCheckResult(
                isCorrect = ok,
                message = if (ok) "Great reasoning!" else "Read the rule carefully and try again.",
                expectedDisplay = problem.correctChoice,
              ),
            )
          },
          modifier = Modifier.fillMaxWidth().defaultMinSize(minHeight = 52.dp).testTag("judge_reason_$idx"),
          shape = RoundedCornerShape(16.dp),
        ) {
          Text(opt, textAlign = TextAlign.Center, fontWeight = FontWeight.SemiBold, color = InkDark)
        }
      }
    }
  }
}

@Composable
fun GcfLcmRenderer(
  problem: GcfLcmProblem,
  onResult: (AnswerCheckResult) -> Unit,
) {
  var gcfIn by remember(problem.itemNumber, problem.numbers) { mutableStateOf("") }
  var lcmIn by remember(problem.itemNumber, problem.numbers) { mutableStateOf("") }
  var activeField by remember(problem.itemNumber, problem.numbers) {
    mutableStateOf(if (problem.askGcf) 0 else 1)
  }

  val title = problem.promptText ?: when {
    problem.askGcf && problem.askLcm -> "Find the GCF and LCM of:"
    problem.askGcf -> "Find the Greatest Common Factor (GCF) of:"
    else -> "Find the Least Common Multiple (LCM) of:"
  }

  Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(12.dp)) {
    Text(title, style = MaterialTheme.typography.titleMedium, color = InkDark, textAlign = TextAlign.Center)
    Surface(color = WarmSurfaceVariant, shape = RoundedCornerShape(20.dp), modifier = Modifier.fillMaxWidth()) {
      Column(modifier = Modifier.padding(14.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(
          text = problem.numbers.joinToString(",  "),
          fontSize = 30.sp,
          fontWeight = FontWeight.Bold,
          color = BagacayBlue,
        )
        if (problem.givenGcf != null) {
          Surface(color = LeafEmeraldLight, shape = RoundedCornerShape(10.dp)) {
            Text(
              "Given: GCF = ${problem.givenGcf}",
              modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
              color = LeafEmerald,
              fontWeight = FontWeight.Bold,
            )
          }
        }
        if (problem.showFactorShortcut) {
          Text(
            "Shortcut: Check if the smaller number is a factor of the larger number!",
            style = MaterialTheme.typography.labelMedium,
            color = TealOcean,
            textAlign = TextAlign.Center,
          )
        }
      }
    }

    Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
      if (problem.askGcf) {
        FieldBox("GCF", gcfIn, activeField == 0, Modifier.weight(1f)) { activeField = 0 }
      }
      if (problem.askLcm) {
        FieldBox("LCM", lcmIn, activeField == 1, Modifier.weight(1f)) { activeField = 1 }
      }
    }

    KidNumberPad(
      onDigit = { ch ->
        if (activeField == 0) {
          if (problem.askGcf && gcfIn.length < 6) gcfIn += ch
        } else {
          if (problem.askLcm && lcmIn.length < 7) lcmIn += ch
        }
      },
      onBackspace = {
        if (activeField == 0) {
          if (problem.askGcf && gcfIn.isNotEmpty()) gcfIn = gcfIn.dropLast(1)
        } else {
          if (problem.askLcm && lcmIn.isNotEmpty()) lcmIn = lcmIn.dropLast(1)
        }
      },
      onClear = {
        if (activeField == 0) {
          if (problem.askGcf) gcfIn = ""
        } else {
          if (problem.askLcm) lcmIn = ""
        }
      },
      onSubmit = {
        val gOk = !problem.askGcf || gcfIn.toIntOrNull() == problem.expectedGcf
        val lOk = !problem.askLcm || lcmIn.toIntOrNull() == problem.expectedLcm
        val ok = gOk && lOk
        val exp = buildList {
          if (problem.askGcf) add("GCF = ${problem.expectedGcf}")
          if (problem.askLcm) add("LCM = ${problem.expectedLcm}")
        }.joinToString(", ")
        onResult(AnswerCheckResult(ok, if (ok) "Great work! $exp." else "Check your prime factors!", exp))
      },
      submitEnabled = (!problem.askGcf || gcfIn.isNotEmpty()) && (!problem.askLcm || lcmIn.isNotEmpty()),
    )
  }
}

@Composable
fun FractionOpRenderer(
  problem: FractionOpProblem,
  onResult: (AnswerCheckResult) -> Unit,
) {
  var wholeIn by remember(problem.itemNumber, problem.expressionDisplay) { mutableStateOf("") }
  var fracIn by remember(problem.itemNumber, problem.expressionDisplay) { mutableStateOf("") }
  var activeBox by remember(problem.itemNumber, problem.expressionDisplay) { mutableStateOf(1) }

  val formattedExpected = remember(problem) {
    val n = problem.expectedNum
    val d = problem.expectedDen
    when {
      d == 1 -> "$n"
      n > d && !problem.isReciprocal -> "${n / d} ${n % d}/$d (or $n/$d)"
      else -> "$n/$d"
    }
  }

  Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(10.dp)) {
    if (problem.promptText != null) {
      Surface(color = WarmSurfaceVariant, shape = RoundedCornerShape(18.dp), modifier = Modifier.fillMaxWidth()) {
        Text(
          text = problem.promptText,
          style = MaterialTheme.typography.bodyLarge,
          color = InkDark,
          modifier = Modifier.padding(14.dp),
        )
      }
    } else {
      Text(
        text = if (problem.isReciprocal) "Write the RECIPROCAL of:" else "Solve and simplify your answer:",
        style = MaterialTheme.typography.titleMedium,
        color = InkDark,
      )
    }

    if (problem.showAreaModel) {
      FractionModelCanvas(
        numA = problem.modelNumA,
        denA = problem.modelDenA,
        numB = problem.modelNumB,
        denB = problem.modelDenB,
        isDivision = problem.isDivisionModel,
      )
    }

    Surface(color = BagacayBlueLight.copy(alpha = 0.55f), shape = RoundedCornerShape(18.dp), modifier = Modifier.fillMaxWidth()) {
      Text(
        text = problem.expressionDisplay,
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        color = BagacayBlue,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(vertical = 12.dp, horizontal = 12.dp),
      )
    }

    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
      FieldBox("Whole (if mixed)", wholeIn, activeBox == 0, Modifier.weight(0.9f)) { activeBox = 0 }
      FieldBox("Fraction (a/b or whole)", fracIn, activeBox == 1, Modifier.weight(1.3f)) { activeBox = 1 }
    }

    KidNumberPad(
      showSlash = activeBox == 1,
      showDecimal = activeBox == 1 && problem.alternateDecimals.isNotEmpty(),
      onDigit = { ch ->
        if (activeBox == 0) {
          if (ch != "/" && ch != "." && wholeIn.length < 5) wholeIn += ch
        } else {
          if (fracIn.length < 9) fracIn += ch
        }
      },
      onBackspace = {
        if (activeBox == 0 && wholeIn.isNotEmpty()) wholeIn = wholeIn.dropLast(1)
        else if (activeBox == 1 && fracIn.isNotEmpty()) fracIn = fracIn.dropLast(1)
      },
      onClear = {
        if (activeBox == 0) wholeIn = "" else fracIn = ""
      },
      onSubmit = {
        val ok = checkMixedOrFraction(wholeIn, fracIn, problem.expectedNum, problem.expectedDen, problem.alternateDecimals)
        onResult(
          AnswerCheckResult(
            isCorrect = ok,
            message = if (ok) "Spot on! Answer: $formattedExpected." else "Remember to convert mixed numbers to improper fractions first (and flip the second fraction when dividing).",
            expectedDisplay = formattedExpected,
          ),
        )
      },
      submitEnabled = wholeIn.isNotEmpty() || fracIn.isNotEmpty(),
    )
  }
}

private fun checkMixedOrFraction(
  wholeText: String,
  fracText: String,
  expectedNum: Int,
  expectedDen: Int,
  altDecimals: List<Double>,
): Boolean {
  val wStr = wholeText.trim()
  val fStr = fracText.trim()
  if (wStr.isEmpty() && fStr.isEmpty()) return false

  // Decimal check if provided
  if (wStr.isEmpty() && fStr.contains(".")) {
    val d = fStr.toDoubleOrNull() ?: return false
    val expDec = expectedNum.toDouble() / expectedDen.toDouble()
    if (abs(d - expDec) < 0.01) return true
    return altDecimals.any { abs(d - it) < 0.01 }
  }

  val w = if (wStr.isEmpty()) 0L else (wStr.toLongOrNull() ?: return false)
  if (fStr.isEmpty()) {
    return w * expectedDen.toLong() == expectedNum.toLong()
  }

  if (fStr.contains("/")) {
    val parts = fStr.split("/")
    if (parts.size != 2) return false
    val fn = parts[0].toLongOrNull() ?: return false
    val fd = parts[1].toLongOrNull() ?: return false
    if (fd <= 0L) return false
    val totalNum = w * fd + fn
    return totalNum * expectedDen.toLong() == fd * expectedNum.toLong()
  }

  val onlyInt = fStr.toLongOrNull() ?: return false
  if (wStr.isNotEmpty()) return false
  return onlyInt * expectedDen.toLong() == expectedNum.toLong()
}
