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
import com.example.viewmodel.AnswerChecker

@Composable
fun CountObjectsRenderer(
  problem: CountObjectsProblem,
  onResult: (AnswerCheckResult) -> Unit,
) {
  var numInput by remember(problem) { mutableStateOf("") }
  var wordInput by remember(problem) { mutableStateOf("") }

  Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(12.dp)) {
    Text(problem.prompt, style = MaterialTheme.typography.titleMedium, color = InkDark, textAlign = TextAlign.Center)
    if (problem.storyContext != null) {
      Surface(color = WarmSurfaceVariant, shape = RoundedCornerShape(16.dp), modifier = Modifier.fillMaxWidth()) {
        Text(problem.storyContext, style = MaterialTheme.typography.bodyMedium, color = InkDark, modifier = Modifier.padding(12.dp))
      }
    }

    when (problem.mode) {
      CountObjectsMode.ENTER_NUMBER -> {
        ObjectCollectionTray(kind = problem.objectKind, count = problem.count)
        AnswerDisplayBox(numInput, "Count and enter number")
        KidNumberPad(
          onDigit = { if (numInput.length < 3) numInput += it },
          onBackspace = { if (numInput.isNotEmpty()) numInput = numInput.dropLast(1) },
          onClear = { numInput = "" },
          onSubmit = {
            val ok = numInput.toIntOrNull() == problem.count
            onResult(AnswerCheckResult(ok, if (ok) "Awesome counting! There are ${problem.count} ${problem.objectKind.plural}." else "Count each ${problem.objectKind.singular} carefully one by one!", "${problem.count}"))
          },
          submitEnabled = numInput.isNotEmpty(),
        )
      }

      CountObjectsMode.WORD_SENTENCE -> {
        ObjectCollectionTray(kind = problem.objectKind, count = problem.count)
        OutlinedTextField(
          value = wordInput,
          onValueChange = { wordInput = it },
          label = { Text("Write the number in words (e.g. four, eighteen)") },
          singleLine = true,
          modifier = Modifier.fillMaxWidth().testTag("count_words_input"),
        )
        Button(
          onClick = {
            val clean = AnswerChecker.normalizeWords(wordInput)
            val exp = AnswerChecker.normalizeWords(problem.expectedWords)
            val singular = problem.objectKind.singular.lowercase()
            val plural = problem.objectKind.plural.lowercase()
            val ok = clean == exp ||
              clean == "$exp $plural" ||
              clean == "$exp $singular" ||
              clean == "there are $exp $plural" ||
              clean == "there is $exp $singular" ||
              clean == "there are $exp" ||
              clean == "there is $exp"
            onResult(
              AnswerCheckResult(
                isCorrect = ok,
                message = if (ok) "Great spelling! ${problem.expectedWords}!" else "Count the objects and type the number word cleanly (like 'four' or 'eighteen').",
                expectedDisplay = problem.expectedWords,
              ),
            )
          },
          enabled = wordInput.isNotBlank(),
          modifier = Modifier.fillMaxWidth().height(54.dp).testTag("submit_count_words"),
          shape = RoundedCornerShape(16.dp),
        ) {
          Text("Check Number Word", fontWeight = FontWeight.Bold)
        }
      }

      CountObjectsMode.MATCH_GROUPS, CountObjectsMode.MATCH_WORD_TO_OBJECT -> {
        Surface(color = BagacayBlueLight, shape = RoundedCornerShape(18.dp), modifier = Modifier.fillMaxWidth()) {
          Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(14.dp)) {
            Text("Target Number Word:", style = MaterialTheme.typography.labelLarge, color = BagacayBlue)
            Text(problem.wordPrompt, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = InkDark)
          }
        }
        Text("Count each group and tap the matching set:", style = MaterialTheme.typography.bodyMedium, color = InkMuted)
        @OptIn(ExperimentalLayoutApi::class)
        FlowRow(
          horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
          verticalArrangement = Arrangement.spacedBy(8.dp),
          modifier = Modifier.fillMaxWidth(),
        ) {
          problem.candidates.forEachIndexed { idx, cand ->
            Surface(
              color = WarmSurfaceVariant,
              shape = RoundedCornerShape(16.dp),
              modifier = Modifier
                .fillMaxWidth()
                .border(1.5.dp, OutlineSoft, RoundedCornerShape(16.dp))
                .clickable {
                  val ok = cand.count == problem.count && cand.kind == problem.objectKind
                  onResult(
                    AnswerCheckResult(
                      isCorrect = ok,
                      message = if (ok) "Nice match! That set has ${problem.wordPrompt} ${cand.label}!" else "Count every item inside this box carefully.",
                      expectedDisplay = "${problem.count} (${problem.wordPrompt})",
                    ),
                  )
                }
                .testTag("match_group_option_$idx"),
            ) {
              Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text("Set ${'A' + idx} (${cand.label})", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold, color = BagacayBlue)
                ObjectCollectionTray(kind = cand.kind, count = cand.count, maxPerRow = 7, chipBg = Color.White)
              }
            }
          }
        }
      }
    }
  }
}

@Composable
fun StampObjectsRenderer(
  problem: StampObjectsProblem,
  onResult: (AnswerCheckResult) -> Unit,
) {
  var singleCount by remember(problem) { mutableStateOf(0) }
  val storyCounts = remember(problem) {
    mutableStateListOf<Int>().apply { repeat(problem.storyItems.size) { add(0) } }
  }

  Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(10.dp)) {
    Text(problem.prompt, style = MaterialTheme.typography.titleMedium, color = InkDark, textAlign = TextAlign.Center)
    if (problem.storyText != null) {
      Surface(color = WarmSurfaceVariant, shape = RoundedCornerShape(16.dp), modifier = Modifier.fillMaxWidth()) {
        Text(problem.storyText, style = MaterialTheme.typography.bodyLarge, color = InkDark, modifier = Modifier.padding(12.dp))
      }
    }

    if (problem.storyItems.isEmpty()) {
      if (singleCount == 0) {
        Surface(
          color = Color(0xFFFFFDF8),
          shape = RoundedCornerShape(18.dp),
          modifier = Modifier.fillMaxWidth().height(92.dp).border(1.5.dp, OutlineSoft, RoundedCornerShape(18.dp)),
        ) {
          Box(contentAlignment = Alignment.Center) {
            Text("Tap + Draw to place ${problem.objectKind.plural}", color = InkMuted)
          }
        }
      } else {
        ObjectCollectionTray(kind = problem.objectKind, count = singleCount, maxPerRow = 5)
      }
      Text("Drawn: $singleCount ${if (singleCount == 1) problem.objectKind.singular else problem.objectKind.plural}", fontWeight = FontWeight.Bold, color = BagacayBlue)
      Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
        OutlinedButton(
          onClick = { if (singleCount > 0) singleCount-- },
          modifier = Modifier.weight(1f).height(50.dp).testTag("stamp_remove"),
          shape = RoundedCornerShape(14.dp),
        ) { Text("-1", fontWeight = FontWeight.Bold) }
        FilledTonalButton(
          onClick = { if (singleCount < 15) singleCount++ },
          modifier = Modifier.weight(1.6f).height(50.dp).testTag("stamp_add"),
          shape = RoundedCornerShape(14.dp),
        ) { Text("+ Draw ${problem.objectKind.displaySymbol()}", fontWeight = FontWeight.Bold) }
        OutlinedButton(
          onClick = { singleCount = 0 },
          modifier = Modifier.weight(1f).height(50.dp),
          shape = RoundedCornerShape(14.dp),
        ) { Text("Clear") }
      }
      Button(
        onClick = {
          val ok = singleCount == problem.targetCount
          onResult(AnswerCheckResult(ok, if (ok) "Awesome drawing! Exactly ${problem.targetCount}!" else "Check the target number and count how many you drew!", "${problem.targetCount}"))
        },
        modifier = Modifier.fillMaxWidth().height(54.dp).testTag("stamp_submit"),
        shape = RoundedCornerShape(16.dp),
      ) { Text("Check Drawing", fontWeight = FontWeight.Bold) }
    } else {
      problem.storyItems.forEachIndexed { idx, spec ->
        val cnt = storyCounts.getOrElse(idx) { 0 }
        Surface(
          color = Color(0xFFFFFDF8),
          shape = RoundedCornerShape(14.dp),
          modifier = Modifier.fillMaxWidth().border(1.dp, OutlineSoft, RoundedCornerShape(14.dp)),
        ) {
          Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
          ) {
            Column(modifier = Modifier.weight(1f)) {
              Text("${spec.kind.displaySymbol()} ${spec.label}: $cnt", fontWeight = FontWeight.Bold, color = InkDark)
              if (cnt > 0) {
                Text(spec.kind.displaySymbol().repeat(cnt), fontSize = 15.sp)
              }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
              OutlinedButton(
                onClick = { if (cnt > 0) storyCounts[idx] = cnt - 1 },
                modifier = Modifier.defaultMinSize(minWidth = 48.dp, minHeight = 48.dp),
                contentPadding = PaddingValues(0.dp),
              ) { Text("-", fontSize = 18.sp, fontWeight = FontWeight.Bold) }
              FilledTonalButton(
                onClick = { if (cnt < 15) storyCounts[idx] = cnt + 1 },
                modifier = Modifier.defaultMinSize(minWidth = 54.dp, minHeight = 48.dp),
                contentPadding = PaddingValues(4.dp),
              ) { Text("+1", fontWeight = FontWeight.Bold) }
            }
          }
        }
      }
      Button(
        onClick = {
          val ok = problem.storyItems.indices.all { storyCounts[it] == problem.storyItems[it].expectedCount }
          onResult(AnswerCheckResult(ok, if (ok) "Story complete! Every group matches the story!" else "Re-read the story sentence by sentence and count each group!", problem.storyItems.joinToString(", ") { "${it.expectedCount} ${it.label}" }))
        },
        modifier = Modifier.fillMaxWidth().height(54.dp).testTag("stamp_story_submit"),
        shape = RoundedCornerShape(16.dp),
      ) { Text("Check Story Drawing", fontWeight = FontWeight.Bold) }
    }
  }
}

@Composable
fun SkipCountGridRenderer(
  problem: SkipCountGridProblem,
  onResult: (AnswerCheckResult) -> Unit,
) {
  val blankCount = problem.expectedBlanks.size.coerceAtLeast(1)
  val inputs = remember(problem) { mutableStateListOf<String>().apply { repeat(blankCount) { add("") } } }
  var activeIdx by remember(problem) { mutableStateOf(0) }

  Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(12.dp)) {
    Text(problem.title, style = MaterialTheme.typography.titleMedium, color = InkDark, textAlign = TextAlign.Center)

    when (problem.style) {
      SkipGridStyle.NUMBER_GRID_4X5 -> {
        Column(verticalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.fillMaxWidth()) {
          for (r in 0 until 5) {
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.fillMaxWidth()) {
              for (c in 0 until 4) {
                val n = problem.gridStart + r * 4 + c
                val isGiven = n in problem.gridGivenValues
                val isTarget = n == problem.targetBlankValue
                Box(
                  modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(
                      when {
                        isTarget -> MangoGoldLight
                        isGiven -> BagacayBlueLight.copy(alpha = 0.65f)
                        else -> WarmSurfaceVariant
                      },
                    )
                    .border(
                      width = if (isTarget) 2.5.dp else 1.dp,
                      color = if (isTarget) CoralTangerine else OutlineSoft,
                      shape = RoundedCornerShape(10.dp),
                    ),
                  contentAlignment = Alignment.Center,
                ) {
                  Text(
                    text = when {
                      isTarget -> inputs[0].ifEmpty { "?" }
                      isGiven -> "$n"
                      else -> "__"
                    },
                    fontWeight = FontWeight.Bold,
                    color = if (isTarget) CoralTangerine else InkDark,
                  )
                }
              }
            }
          }
        }
      }

      SkipGridStyle.COIN_GROUP_TOTAL -> {
        @OptIn(ExperimentalLayoutApi::class)
        FlowRow(
          horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
          verticalArrangement = Arrangement.spacedBy(8.dp),
          modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(18.dp)).background(WarmSurfaceVariant).padding(12.dp),
        ) {
          repeat(problem.coinCount) {
            Surface(
              color = MangoGoldLight,
              shape = RoundedCornerShape(50),
              modifier = Modifier.defaultMinSize(minWidth = 54.dp, minHeight = 54.dp).border(2.dp, MangoDark, RoundedCornerShape(50)),
            ) {
              Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(horizontal = 10.dp)) {
                Text(problem.iconLabel.ifEmpty { "${problem.coinDenom}" }, fontWeight = FontWeight.Bold, color = InkDark)
              }
            }
          }
        }
        AnswerDisplayBox(inputs[0], "Total (${problem.iconLabel})")
      }

      SkipGridStyle.OBJECT_SEQUENCE, SkipGridStyle.ANIMAL_TRACK -> {
        var bCounter = 0
        @OptIn(ExperimentalLayoutApi::class)
        FlowRow(
          horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally),
          verticalArrangement = Arrangement.spacedBy(8.dp),
          modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(18.dp)).background(WarmSurfaceVariant).padding(12.dp),
        ) {
          problem.sequenceValues.forEach { v ->
            if (v == null) {
              val bIdx = bCounter++
              Box(
                modifier = Modifier
                  .defaultMinSize(minWidth = 54.dp, minHeight = 48.dp)
                  .clip(RoundedCornerShape(12.dp))
                  .background(if (activeIdx == bIdx) BagacayBlueLight else Color.White)
                  .border(2.dp, if (activeIdx == bIdx) BagacayBlue else MangoGold, RoundedCornerShape(12.dp))
                  .clickable { activeIdx = bIdx }
                  .padding(horizontal = 8.dp),
                contentAlignment = Alignment.Center,
              ) {
                Text(inputs.getOrElse(bIdx) { "" }.ifEmpty { "?" }, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = BagacayBlue)
              }
            } else {
              Surface(color = Color.White, shape = RoundedCornerShape(12.dp), modifier = Modifier.defaultMinSize(minWidth = 48.dp, minHeight = 48.dp).border(1.dp, OutlineSoft, RoundedCornerShape(12.dp))) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(horizontal = 10.dp)) {
                  Text("$v", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = InkDark)
                }
              }
            }
          }
        }
      }
    }

    KidNumberPad(
      onDigit = { ch ->
        val cur = inputs.getOrElse(activeIdx) { "" }
        if (cur.length < 4) inputs[activeIdx] = cur + ch
      },
      onBackspace = {
        val cur = inputs.getOrElse(activeIdx) { "" }
        if (cur.isNotEmpty()) inputs[activeIdx] = cur.dropLast(1)
      },
      onClear = { inputs[activeIdx] = "" },
      onSubmit = {
        val ok = inputs.size == problem.expectedBlanks.size &&
          inputs.zip(problem.expectedBlanks).all { (u, e) -> u.toIntOrNull() == e }
        onResult(AnswerCheckResult(ok, if (ok) "Great skip counting!" else "Count on by ${problem.stepSize} from the previous number!", problem.expectedBlanks.joinToString(", ")))
      },
      submitEnabled = inputs.all { it.isNotEmpty() },
    )
  }
}

@Composable
fun OrdinalShapeRenderer(
  problem: OrdinalShapeProblem,
  onResult: (AnswerCheckResult) -> Unit,
) {
  Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(12.dp)) {
    Text(problem.prompt, style = MaterialTheme.typography.titleMedium, color = InkDark, textAlign = TextAlign.Center)
    Surface(color = WarmSurfaceVariant, shape = RoundedCornerShape(18.dp), modifier = Modifier.fillMaxWidth()) {
      if (problem.isVertical) {
        Column(
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.spacedBy(6.dp),
          modifier = Modifier.fillMaxWidth().padding(12.dp),
        ) {
          Text("TOP", style = MaterialTheme.typography.labelSmall, color = InkMuted, fontWeight = FontWeight.Bold)
          problem.shapes.forEachIndexed { idx, kind ->
            Surface(
              color = Color.White,
              shape = RoundedCornerShape(12.dp),
              modifier = Modifier
                .defaultMinSize(minWidth = 84.dp, minHeight = 48.dp)
                .border(1.5.dp, OutlineSoft, RoundedCornerShape(12.dp))
                .clickable {
                  val ok = idx == problem.correctIndex
                  onResult(AnswerCheckResult(ok, if (ok) "Correct position! That is the ${kind.label}." else "Double-check whether the question counts from TOP or BOTTOM!", problem.shapes[problem.correctIndex].label))
                }
                .testTag("ordinal_shape_$idx"),
            ) {
              Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(6.dp)) {
                OrdinalShapeGlyph(kind, size = 34.dp)
              }
            }
          }
          Text("BOTTOM", style = MaterialTheme.typography.labelSmall, color = InkMuted, fontWeight = FontWeight.Bold)
        }
      } else {
        @OptIn(ExperimentalLayoutApi::class)
        Column(modifier = Modifier.fillMaxWidth().padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
          Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("LEFT →", style = MaterialTheme.typography.labelSmall, color = InkMuted, fontWeight = FontWeight.Bold)
            Text("← RIGHT", style = MaterialTheme.typography.labelSmall, color = InkMuted, fontWeight = FontWeight.Bold)
          }
          FlowRow(
            horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally),
            verticalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.fillMaxWidth(),
          ) {
            problem.shapes.forEachIndexed { idx, kind ->
              Surface(
                color = Color.White,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                  .defaultMinSize(minWidth = 52.dp, minHeight = 52.dp)
                  .border(1.5.dp, OutlineSoft, RoundedCornerShape(12.dp))
                  .clickable {
                    val ok = idx == problem.correctIndex
                    onResult(AnswerCheckResult(ok, if (ok) "Nice! ${kind.label} is in the right spot!" else "Carefully count left vs. right from the edge!", problem.shapes[problem.correctIndex].label))
                  }
                  .testTag("ordinal_shape_$idx"),
              ) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(6.dp)) {
                  OrdinalShapeGlyph(kind, size = 34.dp)
                }
              }
            }
          }
        }
      }
    }
  }
}

@Composable
fun EqualPartsPartitionRenderer(
  problem: EqualPartsPartitionProblem,
  onResult: (AnswerCheckResult) -> Unit,
) {
  var cutChosen by remember(problem) { mutableStateOf<String?>(null) }

  Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(12.dp)) {
    Text(
      "Step 1: Pick the EQUAL cut into ${problem.requiredParts} equal parts (${problem.shape.name.lowercase()}):",
      style = MaterialTheme.typography.titleMedium,
      color = InkDark,
      textAlign = TextAlign.Center,
    )
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
      listOf(
        Triple("EQUAL", true, problem.requiredParts),
        Triple("UNEQUAL", false, problem.requiredParts),
      ).forEach { (tag, isEq, parts) ->
        val selected = cutChosen == tag
        Surface(
          color = if (selected) BagacayBlueLight else Color.White,
          shape = RoundedCornerShape(16.dp),
          modifier = Modifier
            .weight(1f)
            .border(2.dp, if (selected) BagacayBlue else OutlineSoft, RoundedCornerShape(16.dp))
            .clickable { cutChosen = tag }
            .padding(12.dp)
            .testTag("cut_choice_$tag"),
        ) {
          Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(6.dp)) {
            PartitionPreviewCanvas(problem.shape, parts, isEq)
            Text(if (isEq) "Equal Parts" else "Unequal Parts", fontWeight = FontWeight.Bold, color = InkDark)
          }
        }
      }
    }
    Text("Step 2: What fraction is 1 equal part?", style = MaterialTheme.typography.titleMedium, color = InkDark)
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
      listOf("1/2", "1/4").forEach { frac ->
        Button(
          onClick = {
            val ok = cutChosen == "EQUAL" && frac == problem.expectedFractionLabel
            onResult(
              AnswerCheckResult(
                isCorrect = ok,
                message = if (ok) "Equal parts! 1 of ${problem.requiredParts} equal parts is ${problem.expectedFractionLabel}!" else "Make sure you pick the EQUAL cut AND match 1 out of ${problem.requiredParts} parts!",
                expectedDisplay = "Equal (${problem.expectedFractionLabel})",
              ),
            )
          },
          enabled = cutChosen != null,
          modifier = Modifier.weight(1f).height(56.dp).testTag("frac_part_btn_$frac"),
          shape = RoundedCornerShape(16.dp),
          colors = ButtonDefaults.buttonColors(containerColor = BerryPurple),
        ) {
          Text(frac, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
      }
    }
  }
}

@Composable
fun InteractiveFractionRenderer(
  problem: InteractiveFractionProblem,
  onResult: (AnswerCheckResult) -> Unit,
) {
  val shaded = remember(problem) { mutableStateListOf<Int>() }
  var divideSelected by remember(problem) { mutableStateOf(!problem.allowAnyDivisorFirst) }
  var chosenColorIdx by remember(problem) { mutableStateOf(0) }
  var numInput by remember(problem) { mutableStateOf("") }

  Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(12.dp)) {
    Text(problem.prompt, style = MaterialTheme.typography.titleMedium, color = InkDark, textAlign = TextAlign.Center)

    when (problem.mode) {
      InteractiveFractionMode.PICK_FRACTION_FROM_FIGURE -> {
        InteractiveG2FractionFigure(problem.figureKind, problem.preShadedIndices, interactive = false)
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
          problem.fractionOptions.forEach { opt ->
            Button(
              onClick = {
                val ok = opt == problem.correctOption
                onResult(AnswerCheckResult(ok, if (ok) "Yes! $opt of the figure is shaded." else "Count shaded parts over total equal parts!", problem.correctOption))
              },
              modifier = Modifier.weight(1f).height(56.dp).testTag("pick_frac_$opt"),
              shape = RoundedCornerShape(16.dp),
            ) { Text(opt, fontSize = 20.sp, fontWeight = FontWeight.Bold) }
          }
        }
      }

      InteractiveFractionMode.G2_WRITE_NUMERATOR, InteractiveFractionMode.G2_WRITE_DENOMINATOR -> {
        InteractiveG2FractionFigure(problem.figureKind, problem.preShadedIndices, interactive = false)
        val askNum = problem.mode == InteractiveFractionMode.G2_WRITE_NUMERATOR
        val expectedNum = if (askNum) problem.targetShadedParts else problem.totalParts
        Column(
          horizontalAlignment = Alignment.CenterHorizontally,
          modifier = Modifier.clip(RoundedCornerShape(16.dp)).background(WarmSurfaceVariant).padding(horizontal = 24.dp, vertical = 10.dp),
        ) {
          Text(if (askNum) numInput.ifEmpty { "?" } else "${problem.targetShadedParts}", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = if (askNum) BagacayBlue else InkDark)
          HorizontalDivider(Modifier.width(56.dp), thickness = 3.dp, color = InkDark)
          Text(if (!askNum) numInput.ifEmpty { "?" } else "${problem.totalParts}", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = if (!askNum) CoralTangerine else InkDark)
        }
        KidNumberPad(
          onDigit = { if (numInput.length < 2) numInput += it },
          onBackspace = { if (numInput.isNotEmpty()) numInput = numInput.dropLast(1) },
          onClear = { numInput = "" },
          onSubmit = {
            val ok = numInput.toIntOrNull() == expectedNum
            onResult(AnswerCheckResult(ok, if (ok) "Great job!" else if (askNum) "Numerator = shaded parts on top!" else "Denominator = total equal parts!", "$expectedNum"))
          },
          submitEnabled = numInput.isNotEmpty(),
        )
      }

      InteractiveFractionMode.SHADE_PARTS,
      InteractiveFractionMode.DIVIDE_AND_SHADE,
      InteractiveFractionMode.G3_BUILD_CIRCLE_PARTS -> {
        if (!divideSelected && problem.allowAnyDivisorFirst) {
          Text("First, divide the whole into equal parts:", style = MaterialTheme.typography.bodyMedium, color = InkMuted)
          Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
            listOf(2 to "Halves (2 parts)", 4 to "Fourths (4 parts)").forEach { (parts, label) ->
              FilledTonalButton(
                onClick = {
                  if (parts == problem.totalParts) {
                    divideSelected = true
                  } else {
                    onResult(AnswerCheckResult(false, "Look at the denominator! It tells you how many equal parts to divide into.", "${problem.totalParts} parts"))
                  }
                },
                modifier = Modifier.weight(1f).height(52.dp).testTag("divide_into_$parts"),
                shape = RoundedCornerShape(14.dp),
              ) { Text(label, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center) }
            }
          }
        } else {
          InteractiveG2FractionFigure(
            figureKind = problem.figureKind,
            shadedParts = shaded.toSet(),
            interactive = true,
            onTogglePart = { idx -> if (idx in shaded) shaded.remove(idx) else shaded.add(idx) },
          )
          Button(
            onClick = {
              val ok = shaded.size == problem.targetShadedParts
              onResult(
                AnswerCheckResult(
                  isCorrect = ok,
                  message = if (ok) "Perfect shading! ${problem.targetShadedParts}/${problem.totalParts}!" else "Tap ${problem.targetShadedParts} equal part(s) (currently shaded: ${shaded.size}).",
                  expectedDisplay = "${problem.targetShadedParts}/${problem.totalParts}",
                ),
              )
            },
            modifier = Modifier.fillMaxWidth().height(54.dp).testTag("submit_shade_parts"),
            shape = RoundedCornerShape(16.dp),
          ) { Text("Check Shading (${shaded.size} shaded)", fontWeight = FontWeight.Bold) }
        }
      }

      InteractiveFractionMode.G3_TRACE_OR_COLOR_CIRCLE -> {
        val palette = listOf(CoralTangerine, MangoGold, LeafEmerald, BagacayBlue)
        Surface(color = palette[chosenColorIdx].copy(alpha = 0.14f), shape = RoundedCornerShape(18.dp), modifier = Modifier.fillMaxWidth()) {
          Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            G3CirclePartVisual(problem.g3PictureKind)
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
              palette.forEachIndexed { idx, col ->
                Button(
                  onClick = { chosenColorIdx = idx },
                  colors = ButtonDefaults.buttonColors(containerColor = col),
                  modifier = Modifier.size(48.dp),
                  contentPadding = PaddingValues(0.dp),
                ) {
                  if (chosenColorIdx == idx) Text("✓", color = Color.White, fontWeight = FontWeight.Bold)
                }
              }
            }
          }
        }
        Button(
          onClick = { onResult(AnswerCheckResult(true, "Well done exploring this circle shape!", problem.correctOption.ifEmpty { "Completed" })) },
          modifier = Modifier.fillMaxWidth().height(54.dp).testTag("g3_circle_done"),
          shape = RoundedCornerShape(16.dp),
        ) { Text("Confirm Colored & Studied", fontWeight = FontWeight.Bold) }
      }

      InteractiveFractionMode.G3_HALF_OR_QUARTER_JUDGE -> {
        Surface(color = WarmSurfaceVariant, shape = RoundedCornerShape(18.dp), modifier = Modifier.fillMaxWidth()) {
          Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(16.dp)) {
            G3CirclePartVisual(problem.g3PictureKind)
          }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
          listOf("H" to "Half Circle (H)", "Q" to "Quarter Circle (Q)").forEach { (code, label) ->
            Button(
              onClick = {
                val ok = code == problem.correctOption
                onResult(AnswerCheckResult(ok, if (ok) "Spot on! That is a ${if (code == "H") "Half" else "Quarter"} circle!" else "Check if 2 pieces make a full circle (Half) or 4 pieces make a full circle (Quarter).", problem.correctOption))
              },
              modifier = Modifier.weight(1f).height(58.dp).testTag("judge_hq_$code"),
              shape = RoundedCornerShape(16.dp),
              colors = ButtonDefaults.buttonColors(containerColor = if (code == "H") BagacayBlue else CoralTangerine),
            ) { Text(label, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center) }
          }
        }
      }
    }
  }
}

@Composable
fun VisualAddSubSentenceRenderer(
  problem: VisualAddSubSentenceProblem,
  onResult: (AnswerCheckResult) -> Unit,
) {
  var firstIn by remember(problem) { mutableStateOf("") }
  var secondIn by remember(problem) { mutableStateOf("") }
  var resIn by remember(problem) { mutableStateOf("") }
  var active by remember(problem) { mutableStateOf(0) }
  val opSign = if (problem.isAddition) "+" else "-"

  Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(10.dp)) {
    Text(problem.prompt, style = MaterialTheme.typography.titleMedium, color = InkDark, textAlign = TextAlign.Center)
    if (problem.isAddition) {
      Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
        ObjectCollectionTray(problem.objectKind, problem.firstNumber, maxPerRow = 3, modifier = Modifier.weight(1f))
        Text("+", fontSize = 26.sp, fontWeight = FontWeight.Bold, color = BagacayBlue)
        ObjectCollectionTray(problem.objectKind, problem.secondNumber, maxPerRow = 3, modifier = Modifier.weight(1f))
      }
    } else {
      ObjectCollectionTray(
        kind = problem.objectKind,
        count = problem.firstNumber,
        brokenCount = problem.secondNumber,
        maxPerRow = 5,
      )
    }

    Row(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
      FieldBox("First", firstIn, active == 0, Modifier.weight(1f)) { active = 0 }
      Text(opSign, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = BagacayBlue)
      FieldBox("Second", secondIn, active == 1, Modifier.weight(1f)) { active = 1 }
      Text("=", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = InkDark)
      FieldBox("Answer", resIn, active == 2, Modifier.weight(1f)) { active = 2 }
    }

    KidNumberPad(
      onDigit = { ch ->
        when (active) {
          0 -> if (firstIn.length < 3) firstIn += ch
          1 -> if (secondIn.length < 3) secondIn += ch
          else -> if (resIn.length < 3) resIn += ch
        }
      },
      onBackspace = {
        when (active) {
          0 -> if (firstIn.isNotEmpty()) firstIn = firstIn.dropLast(1)
          1 -> if (secondIn.isNotEmpty()) secondIn = secondIn.dropLast(1)
          else -> if (resIn.isNotEmpty()) resIn = resIn.dropLast(1)
        }
      },
      onClear = {
        when (active) {
          0 -> firstIn = ""
          1 -> secondIn = ""
          else -> resIn = ""
        }
      },
      onSubmit = {
        val ok = firstIn.toIntOrNull() == problem.firstNumber &&
          secondIn.toIntOrNull() == problem.secondNumber &&
          resIn.toIntOrNull() == problem.resultNumber
        onResult(
          AnswerCheckResult(
            isCorrect = ok,
            message = if (ok) "Great number sentence! ${problem.firstNumber} $opSign ${problem.secondNumber} = ${problem.resultNumber}!" else if (problem.isAddition) "Count each group, then count all items together!" else "Start with ALL items, subtract the crossed-out ones, then count what is left!",
            expectedDisplay = "${problem.firstNumber} $opSign ${problem.secondNumber} = ${problem.resultNumber}",
          ),
        )
      },
      submitEnabled = firstIn.isNotEmpty() && secondIn.isNotEmpty() && resIn.isNotEmpty(),
    )
  }
}
