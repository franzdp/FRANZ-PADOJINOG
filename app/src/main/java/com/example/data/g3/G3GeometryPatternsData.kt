package com.example.data.g3

import com.example.model.*

object G3GeometryPatternsData {

  fun getWorksheet20(): List<MathProblem> = listOf(
    InteractiveFractionProblem(1, InteractiveFractionMode.G3_TRACE_OR_COLOR_CIRCLE, G2FractionFigureKind.CIRCLE_2_VERT, "1. Trace the dotted circle and choose your favorite color!", 1, 1, g3PictureKind = "watermelon", correctOption = "Traced", hint = "Pick a color and tap Confirm once you've studied the circle."),
    InteractiveFractionProblem(2, InteractiveFractionMode.G3_BUILD_CIRCLE_PARTS, G2FractionFigureKind.CIRCLE_2_VERT, "2. Color HALF (1/2) of the circle:", 2, 1, hint = "A circle divided into 2 equal parts — tap 1 half to color it!"),
    InteractiveFractionProblem(3, InteractiveFractionMode.G3_BUILD_CIRCLE_PARTS, G2FractionFigureKind.CIRCLE_4_ORTHO, "3. Color a QUARTER (1/4) of the circle:", 4, 1, hint = "A circle divided into 4 equal parts — tap 1 quarter to color it!"),
    InteractiveFractionProblem(4, InteractiveFractionMode.G3_BUILD_CIRCLE_PARTS, G2FractionFigureKind.CIRCLE_2_VERT, "4. Color the WHOLE circle (2/2):", 2, 2, hint = "Tap all 2 equal parts to color the whole circle!"),
    InteractiveFractionProblem(5, InteractiveFractionMode.G3_HALF_OR_QUARTER_JUDGE, G2FractionFigureKind.CIRCLE_2_VERT, "5. Is this watermelon slice a Half circle (H) or a Quarter circle (Q)?", 2, 1, correctOption = "H", g3PictureKind = "watermelon", hint = "Two of these slices make a whole round watermelon -> Half circle (H)."),
    InteractiveFractionProblem(6, InteractiveFractionMode.G3_HALF_OR_QUARTER_JUDGE, G2FractionFigureKind.CIRCLE_2_VERT, "6. Is this pizza piece a Half circle (H) or a Quarter circle (Q)?", 2, 1, correctOption = "H", g3PictureKind = "pizza_half", hint = "Look at the straight cut down the middle — 2 pieces make 1 whole pizza!"),
    InteractiveFractionProblem(7, InteractiveFractionMode.G3_HALF_OR_QUARTER_JUDGE, G2FractionFigureKind.CIRCLE_4_ORTHO, "7. Is this slice a Half circle (H) or a Quarter circle (Q)?", 4, 1, correctOption = "Q", g3PictureKind = "quarter_slice", hint = "Look at the 90° corner — 4 of these slices make 1 full circle!"),
    InteractiveFractionProblem(8, InteractiveFractionMode.G3_HALF_OR_QUARTER_JUDGE, G2FractionFigureKind.CIRCLE_2_VERT, "8. Is this protractor shaped like a Half circle (H) or Quarter circle (Q)?", 2, 1, correctOption = "H", g3PictureKind = "protractor_half", hint = "Two protractors placed flat edge to flat edge form a full circle!"),
    InteractiveFractionProblem(9, InteractiveFractionMode.G3_BUILD_CIRCLE_PARTS, G2FractionFigureKind.CIRCLE_2_VERT, "9. Build a HALF circle (1/2):", 2, 1, hint = "Shade 1 of 2 equal parts."),
    InteractiveFractionProblem(10, InteractiveFractionMode.G3_BUILD_CIRCLE_PARTS, G2FractionFigureKind.CIRCLE_2_VERT, "10. Build a WHOLE circle (2/2):", 2, 2, hint = "Shade both halves (2 out of 2)."),
    InteractiveFractionProblem(11, InteractiveFractionMode.G3_BUILD_CIRCLE_PARTS, G2FractionFigureKind.CIRCLE_4_ORTHO, "11. Build a QUARTER circle (1/4):", 4, 1, hint = "Shade 1 of 4 equal parts."),
    InteractiveFractionProblem(12, InteractiveFractionMode.G3_BUILD_CIRCLE_PARTS, G2FractionFigureKind.CIRCLE_4_ORTHO, "12. Build 2 QUARTERS of a circle (2/4):", 4, 2, hint = "Shade 2 out of 4 equal quarter parts."),
    InteractiveFractionProblem(13, InteractiveFractionMode.G3_BUILD_CIRCLE_PARTS, G2FractionFigureKind.CIRCLE_2_VERT, "13. Build 2 HALVES of a circle (2/2):", 2, 2, hint = "Shade both halves."),
    InteractiveFractionProblem(14, InteractiveFractionMode.G3_BUILD_CIRCLE_PARTS, G2FractionFigureKind.CIRCLE_2_VERT, "14. Build a HALF circle (1/2):", 2, 1, hint = "Shade 1 of the 2 equal parts."),
    InteractiveFractionProblem(15, InteractiveFractionMode.G3_BUILD_CIRCLE_PARTS, G2FractionFigureKind.CIRCLE_4_ORTHO, "15. Build a QUARTER circle (1/4):", 4, 1, hint = "Shade 1 of the 4 equal parts."),
    InteractiveFractionProblem(16, InteractiveFractionMode.G3_BUILD_CIRCLE_PARTS, G2FractionFigureKind.CIRCLE_4_ORTHO, "16. Build a WHOLE circle (4/4):", 4, 4, hint = "Shade all 4 quarters to complete the whole circle!"),
  )

  fun getWorksheet21(): List<MathProblem> {
    val nameObjects = listOf(
      RealWorldObjectKind.COIN,
      RealWorldObjectKind.NOTEBOOK,
      RealWorldObjectKind.RULER,
      RealWorldObjectKind.CORN_CHIP,
      RealWorldObjectKind.TUNA_CAN,
      RealWorldObjectKind.CHEESE_WEDGE,
      RealWorldObjectKind.FLOPPY_DISK,
      RealWorldObjectKind.CLOCK,
    ).mapIndexed { idx, obj ->
      ShapeClassificationProblem(
        itemNumber = idx + 1,
        mode = ShapeExerciseMode.NAME_OBJECT_SHAPE,
        prompt = "Name the shape of this object:",
        targetShape = obj.shapeKind,
        shownObject = obj,
        hint = "Circle is round; Triangle has 3 sides; Square has 4 equal sides; Rectangle has 2 pairs of equal sides.",
      )
    }

    val circles = listOf(
      listOf(RealWorldObjectKind.COIN, RealWorldObjectKind.RULER, RealWorldObjectKind.NOTEBOOK, RealWorldObjectKind.CORN_CHIP),
      listOf(RealWorldObjectKind.TUNA_CAN, RealWorldObjectKind.DOOR, RealWorldObjectKind.CHEESE_WEDGE, RealWorldObjectKind.FLOPPY_DISK),
      listOf(RealWorldObjectKind.PIZZA_WHOLE, RealWorldObjectKind.CHALKBOARD, RealWorldObjectKind.TRIANGLE_FLAG, RealWorldObjectKind.WINDOW),
    ).mapIndexed { i, opts -> ShapeClassificationProblem(9 + i, ShapeExerciseMode.PICK_OBJECT_BY_SHAPE, "Pick an object shaped like a CIRCLE:", BasicShapeKind.CIRCLE, objectChoices = opts, hint = "Look for the round object with no corners.") }

    val squares = listOf(
      listOf(RealWorldObjectKind.NOTEBOOK, RealWorldObjectKind.COIN, RealWorldObjectKind.RULER, RealWorldObjectKind.CORN_CHIP),
      listOf(RealWorldObjectKind.FLOPPY_DISK, RealWorldObjectKind.DONUT, RealWorldObjectKind.PIZZA_SLICE, RealWorldObjectKind.SUITCASE),
      listOf(RealWorldObjectKind.SQUARE_CLOCK, RealWorldObjectKind.CLOCK, RealWorldObjectKind.MOUNTAIN, RealWorldObjectKind.DOOR),
    ).mapIndexed { i, opts -> ShapeClassificationProblem(12 + i, ShapeExerciseMode.PICK_OBJECT_BY_SHAPE, "Pick an object shaped like a SQUARE:", BasicShapeKind.SQUARE, objectChoices = opts, hint = "Look for 4 equal sides and 4 square corners.") }

    val rects = listOf(
      listOf(RealWorldObjectKind.RULER, RealWorldObjectKind.COIN, RealWorldObjectKind.SQUARE_FRAME, RealWorldObjectKind.MOUNTAIN),
      listOf(RealWorldObjectKind.DOOR, RealWorldObjectKind.DONUT, RealWorldObjectKind.HANDKERCHIEF, RealWorldObjectKind.WATERMELON_SLICE),
      listOf(RealWorldObjectKind.CHALKBOARD, RealWorldObjectKind.BALL, RealWorldObjectKind.TOWEL, RealWorldObjectKind.CHEESE_WEDGE),
    ).mapIndexed { i, opts -> ShapeClassificationProblem(15 + i, ShapeExerciseMode.PICK_OBJECT_BY_SHAPE, "Pick an object shaped like a RECTANGLE:", BasicShapeKind.RECTANGLE, objectChoices = opts, hint = "Look for 2 long sides and 2 short sides.") }

    val tris = listOf(
      listOf(RealWorldObjectKind.CORN_CHIP, RealWorldObjectKind.COIN, RealWorldObjectKind.RULER, RealWorldObjectKind.NOTEBOOK),
      listOf(RealWorldObjectKind.PIZZA_SLICE, RealWorldObjectKind.DONUT, RealWorldObjectKind.DOOR, RealWorldObjectKind.WINDOW),
      listOf(RealWorldObjectKind.TRIANGLE_FLAG, RealWorldObjectKind.BALL, RealWorldObjectKind.SUITCASE, RealWorldObjectKind.FLOPPY_DISK),
    ).mapIndexed { i, opts -> ShapeClassificationProblem(18 + i, ShapeExerciseMode.PICK_OBJECT_BY_SHAPE, "Pick an object shaped like a TRIANGLE:", BasicShapeKind.TRIANGLE, objectChoices = opts, hint = "Look for 3 sides and 3 pointed corners.") }

    return nameObjects + circles + squares + rects + tris
  }

  private fun sPat(
    item: Int,
    seq: List<String>,
    expected: List<String>,
    choices: List<String>,
    hint: String,
  ): MathProblem.PatternProblem = MathProblem.PatternProblem(
    itemNumber = item,
    tokens = seq.map { PatternToken(PatternTokenKind.TEXT, it) } + List(expected.size) { PatternToken(PatternTokenKind.BLANK) },
    expectedAnswers = expected,
    choices = choices,
    isShapeOrLetterChoice = true,
    hint = hint,
  )

  fun getWorksheet22(): List<MathProblem> = listOf(
    sPat(1, listOf("○", "■", "●", "■", "○", "■"), listOf("●", "■"), listOf("●", "■", "○", "□"), "Cycle is ○, ■, ●, ■ — after ○, ■ come ●, ■."),
    sPat(2, listOf("△", "△", "●", "●", "△", "△"), listOf("●", "●"), listOf("●", "△", "○", "■"), "Two white triangles alternate with two black circles."),
    sPat(3, listOf("★", "■", "★", "□", "★", "■"), listOf("★", "□"), listOf("★", "□", "■", "☆"), "Black star stays between ■ and □ alternating: ★ ■ ★ □ ★ ■ -> ★ □."),
    sPat(4, listOf("♡", "▲", "♥", "▲", "♡", "▲"), listOf("♥", "▲"), listOf("♥", "▲", "♡", "△"), "Cycle is ♡, ▲, ♥, ▲ -> next are ♥, ▲."),
    sPat(5, listOf("●", "○", "●", "○", "●", "○"), listOf("●", "○"), listOf("●", "○", "■", "△"), "Alternates filled circle ● and white circle ○."),
    sPat(6, listOf("□", "■", "△", "▲", "□", "■"), listOf("△", "▲"), listOf("△", "▲", "□", "■"), "Cycle is □, ■, △, ▲ -> after □, ■ come △, ▲."),
    sPat(7, listOf("☺", "♡", "☺", "♡", "☺", "♡"), listOf("☺", "♡"), listOf("☺", "♡", "♥", "★"), "Alternates smiley ☺ and heart ♡."),
    sPat(8, listOf("◇", "◆", "○", "●", "◇", "◆"), listOf("○", "●"), listOf("○", "●", "◇", "◆"), "Cycle is ◇, ◆, ○, ● -> next are ○, ●."),
    sPat(9, listOf("★", "□", "★", "■", "★", "□"), listOf("★", "■"), listOf("★", "■", "□", "☆"), "Cycle is ★, □, ★, ■ -> after ★, □ come ★, ■."),
    sPat(10, listOf("△", "●", "△", "●", "△", "●"), listOf("△", "●"), listOf("△", "●", "○", "▲"), "Alternates white triangle △ and black circle ●."),
    sPat(11, listOf("♡", "♥", "★", "★", "♡", "♥", "★", "★"), listOf("♡", "♥"), listOf("♡", "♥", "★", "☆"), "4-term cycle ♡, ♥, ★, ★ restarts with ♡, ♥."),
    sPat(12, listOf("□", "■", "○", "●", "□", "■", "○"), listOf("●", "□"), listOf("●", "□", "■", "○"), "4-term cycle □, ■, ○, ● — after ○ comes ●, then □."),
    sPat(13, listOf("▲", "▲", "○", "○", "▲", "▲", "○"), listOf("○", "▲"), listOf("○", "▲", "●", "△"), "Pairs ▲, ▲, ○, ○ — finish the ○ pair then start ▲."),
    sPat(14, listOf("★", "☆", "□", "■", "★", "☆", "□"), listOf("■", "★"), listOf("■", "★", "☆", "□"), "Cycle ★, ☆, □, ■ — after □ comes ■, then ★."),
    sPat(15, listOf("☺", "☻", "♡", "♥", "☺", "☻", "♡"), listOf("♥", "☺"), listOf("♥", "☺", "☻", "♡"), "Cycle ☺, ☻, ♡, ♥ — after ♡ comes ♥, then ☺."),
    sPat(16, listOf("◇", "◆", "□", "■", "◇", "◆", "□"), listOf("■", "◇"), listOf("■", "◇", "◆", "□"), "Cycle ◇, ◆, □, ■ — after □ comes ■, then ◇."),
    sPat(17, listOf("△", "▲", "△", "▲", "□", "■", "□"), listOf("■", "△"), listOf("■", "△", "□", "▲"), "Alternating pairs: △ ▲ △ ▲, then □ ■ □ ■ -> finish with ■, then △."),
    sPat(18, listOf("●", "○", "□", "■", "●", "○", "□"), listOf("■", "●"), listOf("■", "●", "○", "□"), "Cycle ●, ○, □, ■ — after □ comes ■, then ●."),
    sPat(19, listOf("♡", "♥", "★", "☆", "♡", "♥", "★", "☆"), listOf("♡", "♥"), listOf("♡", "♥", "★", "☆"), "Cycle ♡, ♥, ★, ☆ repeats from ♡, ♥."),
    sPat(20, listOf("□", "■", "△", "▲", "□", "■", "△"), listOf("▲", "□"), listOf("▲", "□", "■", "△"), "Cycle □, ■, △, ▲ — after △ comes ▲, then □."),
  )

  fun getWorksheet23(): List<MathProblem> = listOf(
    sPat(1, listOf("○", "□", "○", "□", "○", "□"), listOf("○", "□"), listOf("○", "□", "△", "●"), "Alternates circle ○ and square □."),
    sPat(2, listOf("△", "△△", "△△△", "△△△△"), listOf("△△△△△", "△△△△△△"), listOf("△△△△△", "△△△△△△", "△△△", "△△△△△△△"), "Adds 1 triangle each step: 1, 2, 3, 4 -> 5, 6 triangles."),
    sPat(3, listOf("Tiny □", "Small □", "Medium □", "Large □", "XL □"), listOf("XXL □", "XXXL □"), listOf("XXL □", "XXXL □", "Small □", "Tiny □"), "Square grows larger one size step each time."),
    sPat(4, listOf("→", "→→", "→→→", "→→→→"), listOf("→→→→→", "→→→→→→"), listOf("→→→→→", "→→→→→→", "→→→", "←←←"), "Adds 1 right arrow each step: 1, 2, 3, 4 -> 5, 6."),
    sPat(5, listOf("○", "○○", "○○○", "○○○○"), listOf("○○○○○", "○○○○○○"), listOf("○○○○○", "○○○○○○", "○○○", "□□□"), "Adds 1 circle each step: 1, 2, 3, 4 -> 5, 6."),
    sPat(6, listOf("★", "★★", "★★★", "★★★★"), listOf("★★★★★", "★★★★★★"), listOf("★★★★★", "★★★★★★", "★★★", "★★★★"), "Adds 1 star each step: 1, 2, 3, 4 -> 5, 6."),
    sPat(7, listOf("↓", "←", "↑", "→", "↓", "←"), listOf("↑", "→"), listOf("↑", "→", "↓", "←"), "Arrow turns clockwise 90°: ↓ -> ← -> ↑ -> →."),
    sPat(8, listOf("Tiny ○", "Small ○", "Medium ○", "Large ○", "XL ○"), listOf("XXL ○", "XXXL ○"), listOf("XXL ○", "XXXL ○", "Small ○", "Tiny ○"), "Circle grows one size larger each step."),
    sPat(9, listOf("Top □", "Bottom □", "Top □", "Bottom □", "Top □", "Bottom □"), listOf("Top □", "Bottom □"), listOf("Top □", "Bottom □", "Left □", "Middle □"), "Square alternates position: Top, Bottom, Top, Bottom..."),
    sPat(10, listOf("△", "▽", "△", "▽", "△", "▽"), listOf("△", "▽"), listOf("△", "▽", "▷", "◁"), "Triangle flips alternating Up △ and Down ▽."),
    sPat(11, listOf("♡", "♡♡", "♡", "♡♡", "♡"), listOf("♡♡", "♡"), listOf("♡", "♡♡", "♡♡♡", "♥"), "Alternates 1 heart and 2 hearts."),
    sPat(12, listOf("Tiny ○", "Small ○", "Medium ○", "Large ○", "XL ○"), listOf("XXL ○", "XXXL ○"), listOf("XXL ○", "XXXL ○", "Small ○", "Tiny ○"), "Circles increase steadily in size."),
    sPat(13, listOf("★★★★★", "★★★★", "★★★", "★★"), listOf("★", "0 stars"), listOf("★", "0 stars", "★★", "★★★"), "Subtracts 1 star each step: 5, 4, 3, 2 -> 1 star, then 0 stars."),
    sPat(14, listOf("←", "←←", "←←←", "←←←←"), listOf("←←←←←", "←←←←←←"), listOf("←←←←←", "←←←←←←", "←←←", "→→→"), "Adds 1 left arrow each step: 1, 2, 3, 4 -> 5, 6."),
    sPat(15, listOf("Top △", "Bottom △", "Top △", "Bottom △", "Top △", "Bottom △"), listOf("Top △", "Bottom △"), listOf("Top △", "Bottom △", "Middle △"), "Triangle alternates high (Top) and low (Bottom)."),
    sPat(16, listOf("Small □", "Large □", "Small □", "Large □", "Small □", "Large □", "Small □"), listOf("Large □", "Small □"), listOf("Small □", "Large □", "Tiny □"), "Alternates Small □ and Large □ — after Small □ comes Large □, then Small □."),
    sPat(17, listOf("◇", "◇◇", "◇◇◇", "◇◇◇◇"), listOf("◇◇◇◇◇", "◇◇◇◇◇◇"), listOf("◇◇◇◇◇", "◇◇◇◇◇◇", "◇◇◇", "□□□"), "Adds 1 diamond each step: 1, 2, 3, 4 -> 5, 6."),
    sPat(18, listOf("□", "■", "□", "■", "□", "■"), listOf("□", "■"), listOf("□", "■", "○", "●"), "Alternates outline square □ and filled square ■."),
    sPat(19, listOf("*", "**", "***", "****"), listOf("*****", "******"), listOf("*****", "******", "***", "****"), "Adds 1 asterisk each step: 1, 2, 3, 4 -> 5, 6."),
    sPat(20, listOf("→", "→→", "→", "→→", "→"), listOf("→→", "→"), listOf("→", "→→", "→→→", "←"), "Alternates 1 arrow and 2 arrows."),
    sPat(21, listOf("○", "□", "○", "□", "○", "□"), listOf("○", "□"), listOf("○", "□", "◇", "△"), "Alternates ○ and □."),
    sPat(22, listOf("Tiny ○", "Small ○", "Medium ○", "Large ○", "XL ○", "XXL ○"), listOf("3XL ○", "4XL ○"), listOf("3XL ○", "4XL ○", "Small ○", "Medium ○"), "Each circle gets one size bigger."),
    sPat(23, listOf("□", "◇", "□", "◇", "□", "◇"), listOf("□", "◇"), listOf("□", "◇", "○", "△"), "Alternates square □ and diamond ◇."),
    sPat(24, listOf("→", "↗", "↑", "↖", "←", "↙"), listOf("↓", "↘"), listOf("↓", "↘", "→", "↗"), "Arrow rotates 45° counterclockwise each step: after ↙ comes ↓, then ↘."),
    sPat(25, listOf("XXL ○", "XL ○", "Large ○", "Medium ○", "Small ○"), listOf("Tiny ○", "Dot •"), listOf("Tiny ○", "Dot •", "Large ○", "XXL ○"), "Circles shrink one size smaller each step."),
    sPat(26, listOf("◇", "□", "◇", "□", "◇", "□"), listOf("◇", "□"), listOf("◇", "□", "○", "△"), "Alternates diamond ◇ and square □."),
    sPat(27, listOf("↓", "↘", "→", "↗", "↑", "↖"), listOf("←", "↙"), listOf("←", "↙", "↓", "→"), "Arrow rotates 45° counterclockwise: after ↖ comes ←, then ↙."),
    sPat(28, listOf("△", "○", "△", "○", "△", "○"), listOf("△", "○"), listOf("△", "○", "□", "◇"), "Alternates triangle △ and circle ○."),
    sPat(29, listOf("□", "○", "□", "○", "□", "○"), listOf("□", "○"), listOf("□", "○", "△", "◇"), "Alternates square □ and circle ○."),
    sPat(30, listOf("△", "▷", "▽", "◁", "△", "▷"), listOf("▽", "◁"), listOf("▽", "◁", "△", "▷"), "Triangle turns clockwise 90°: Up △ -> Right ▷ -> Down ▽ -> Left ◁."),
    sPat(31, listOf("□", "◇", "▽", "□", "◇", "▽"), listOf("□", "◇"), listOf("□", "◇", "▽", "○"), "3-shape cycle □, ◇, ▽ repeats with □, ◇."),
    sPat(32, listOf("Circle |", "Circle /", "Circle —", "Circle \\", "Circle |", "Circle /"), listOf("Circle —", "Circle \\"), listOf("Circle —", "Circle \\", "Circle |", "Circle /"), "Line inside circle rotates clockwise 45° each step: |, /, —, \\."),
    sPat(33, listOf("↑", "→", "↓", "←", "↑", "→"), listOf("↓", "←"), listOf("↓", "←", "↑", "→"), "Arrow rotates clockwise 90°: ↑ -> → -> ↓ -> ←."),
    sPat(34, listOf("□", "◇", "▽", "◁", "□", "◇"), listOf("▽", "◁"), listOf("▽", "◁", "□", "◇"), "4-shape cycle □, ◇, ▽, ◁ — after □, ◇ come ▽, ◁."),
    sPat(35, listOf("Size 1 ○", "Size 2 ○", "Size 3 ○", "Size 4 ○", "Size 5 ○", "Size 4 ○", "Size 3 ○"), listOf("Size 2 ○", "Size 1 ○"), listOf("Size 2 ○", "Size 1 ○", "Size 5 ○", "Size 4 ○"), "Circle grows 1->2->3->4->5, then shrinks back down 4->3 -> 2 -> 1!"),
    sPat(36, listOf("△", "□", "△", "□", "△", "□"), listOf("△", "□"), listOf("△", "□", "○", "◇"), "Alternates triangle △ and square □."),
    sPat(37, listOf("○", "□", "○", "□", "○", "□"), listOf("○", "□"), listOf("○", "□", "△", "◇"), "Alternates circle ○ and square □."),
    sPat(38, listOf("◇", "△", "◇", "△", "◇", "△"), listOf("◇", "△"), listOf("◇", "△", "□", "○"), "Alternates diamond ◇ and triangle △."),
    sPat(39, listOf("▷", "◁", "▷", "◁", "▷", "◁"), listOf("▷", "◁"), listOf("▷", "◁", "△", "▽"), "Triangle alternates pointing Right ▷ and Left ◁."),
    sPat(40, listOf("←", "↖", "↑", "↗", "→", "↘"), listOf("↓", "↙"), listOf("↓", "↙", "←", "↑"), "Arrow rotates clockwise 45°: after ↘ comes ↓, then ↙."),
  )

  fun getWorksheet24(): List<MathProblem> {
    val lines = listOf(
      Triple(1, "Straight line", "Does this line stay straight without bending?"),
      Triple(2, "Curved line", "Notice how this line bends smoothly like a wave."),
      Triple(3, "Straight line", "Can you lay a ruler right along this line?"),
      Triple(4, "Curved line", "It bends like a smile arc."),
      Triple(5, "Straight line", "A vertical segment with no bends."),
      Triple(6, "Curved line", "It bends like the letter C."),
      Triple(7, "Curved line", "Notice all the rounded bends."),
      Triple(8, "Straight line", "A flat horizontal straight line."),
      Triple(9, "Curved line", "Shaped like a smooth S curve."),
    ).map { (id, ans, hint) ->
      LineSurfaceProblem(id, LineSurfaceMode.LINE_STRAIGHT_OR_CURVED, id, "Line #$id", "Is this a Straight line or a Curved line?", "Straight line", "Curved line", ans, hint)
    }

    val surfaces = listOf(
      Triple(10, "📖 Book", "Flat surface"),
      Triple(11, "🥣 Bowl", "Curved surface"),
      Triple(12, "🏀 Basketball", "Curved surface"),
      Triple(13, "🍎 Apple", "Curved surface"),
      Triple(14, "☕ Mug", "Curved surface"),
      Triple(15, "📦 Cardboard Box", "Flat surface"),
      Triple(16, "💻 Laptop Screen & Base", "Flat surface"),
      Triple(17, "🍼 Bottle", "Curved surface"),
      Triple(18, "🪑 Table Top", "Flat surface"),
    ).map { (id, label, ans) ->
      LineSurfaceProblem(id, LineSurfaceMode.SURFACE_FLAT_OR_CURVED_PICK, id, label, "Does this object have Flat surfaces or Curved surfaces?", "Flat surface", "Curved surface", ans, "Imagine rolling or placing your palm on it — flat lies level; curved wraps around.")
    }

    val objLines = listOf(
      Triple(19, "🚪 Door", "Straight lines"),
      Triple(20, "🍊 Orange", "Curved lines"),
      Triple(21, "🥣 Cereal Box", "Straight lines"),
      Triple(22, "💡 Lightbulb", "Curved lines"),
      Triple(23, "🪟 Window", "Straight lines"),
      Triple(24, "🛞 Car Wheel", "Curved lines"),
      Triple(25, "🎁 Gift Box", "Straight lines"),
      Triple(26, "🪙 Coin", "Curved lines"),
      Triple(27, "🌍 Globe", "Curved lines"),
    ).map { (id, label, ans) ->
      LineSurfaceProblem(id, LineSurfaceMode.OBJECT_LINE_STRAIGHT_OR_CURVED, id, label, "Does this object show Straight lines (✓) or Curved lines (✗)?", "Straight lines", "Curved lines", ans, "Look at the edges — boxy edges have straight lines; round outlines have curved lines.")
    }

    val objSurfaces = listOf(
      Triple(28, "🧊 Ice Cube", "FLAT"),
      Triple(29, "🎈 Balloon", "CURVED"),
      Triple(30, "🪣 Bucket", "CURVED"),
      Triple(31, "🏺 Vase", "CURVED"),
      Triple(32, "🎳 Bowling Pin", "CURVED"),
      Triple(33, "🟫 Floor Tile", "FLAT"),
      Triple(34, "📓 Notebook", "FLAT"),
      Triple(35, "🖼️ Picture Frame", "FLAT"),
      Triple(36, "🌽 Corn Cob", "CURVED"),
    ).map { (id, label, ans) ->
      LineSurfaceProblem(id, LineSurfaceMode.OBJECT_SURFACE_FLAT_OR_CURVED_WORD, id, label, "Identify the surface: FLAT or CURVED?", "FLAT", "CURVED", ans, "Ask yourself: is the outside smooth and flat like a tabletop (FLAT) or rounded (CURVED)?")
    }

    return lines + surfaces + objLines + objSurfaces
  }
}
