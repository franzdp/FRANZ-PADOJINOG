package com.example.data.g2

import com.example.model.*

object G2CompareOrderData {

  private fun cmp(item: Int, a: Int, b: Int): MathProblem.CompareWholeNumbersProblem {
    val sym = when {
      a > b -> ">"
      a < b -> "<"
      else -> "="
    }
    return MathProblem.CompareWholeNumbersProblem(
      itemNumber = item,
      leftDisplay = "$a",
      leftValue = a,
      rightDisplay = "$b",
      rightValue = b,
      leftIsWords = false,
      answerSymbol = sym,
      hint = "Check how many digits each number has first. If the same, compare from the leftmost digit.",
    )
  }

  private fun orderSet(item: Int, nums: List<Int>, increasing: Boolean): MathProblem.OrderNumbersProblem =
    MathProblem.OrderNumbersProblem(
      itemNumber = item,
      numbers = nums,
      increasing = increasing,
      expectedOrder = if (increasing) nums.sorted() else nums.sortedDescending(),
      hint = if (increasing) "Pick the smallest tens digit first; if equal, compare ones." else "Pick the largest tens digit first; if equal, compare ones.",
    )

  fun getWorksheet7(): List<MathProblem> = listOf(
    302 to 456, 657 to 257, 105 to 430, 341 to 48, 987 to 999,
    143 to 689, 540 to 541, 987 to 987, 32 to 56, 57 to 57,
    15 to 43, 34 to 48, 87 to 99, 13 to 89, 40 to 41,
    98 to 98, 350 to 467, 485 to 204, 352 to 351, 506 to 607,
  ).mapIndexed { idx, (a, b) -> cmp(idx + 1, a, b) }

  fun getWorksheet8(): List<MathProblem> = listOf(
    listOf(23, 43, 52, 18, 22),
    listOf(45, 52, 28, 10, 3),
    listOf(72, 38, 93, 68, 74),
    listOf(28, 35, 82, 16, 67),
    listOf(38, 25, 92, 16, 24),
    listOf(64, 48, 71, 47, 90, 73),
    listOf(79, 93, 25, 94, 72, 29),
    listOf(78, 38, 12, 28, 84, 55),
    listOf(68, 74, 44, 11, 15, 61),
    listOf(21, 89, 58, 59, 79, 37),
    listOf(76, 24, 77, 98, 52, 70),
    listOf(31, 13, 21, 57, 85, 71),
    listOf(40, 27, 63, 66, 43, 35),
    listOf(81, 16, 77, 72, 61, 56),
    listOf(87, 72, 74, 11, 35, 43),
  ).mapIndexed { idx, nums -> orderSet(idx + 1, nums, increasing = true) }

  fun getWorksheet9(): List<MathProblem> {
    val compares = listOf(
      89 to 52, 69 to 59, 70 to 21, 83 to 27, 23 to 39,
      3 to 88, 37 to 37, 36 to 38, 99 to 77, 58 to 54,
    ).mapIndexed { idx, (a, b) -> cmp(idx + 1, a, b) }

    val orders = listOf(
      listOf(67, 43, 25, 13, 15),
      listOf(48, 27, 89, 95, 34, 97),
      listOf(29, 12, 35, 84, 16, 19),
      listOf(12, 25, 72, 98, 43, 52),
      listOf(35, 95, 85, 75, 84, 64),
      listOf(36, 75, 84, 15, 45, 75),
      listOf(95, 74, 43, 87, 86, 24),
      listOf(15, 41, 78, 23, 56, 57),
      listOf(35, 96, 56, 25, 53, 61),
      listOf(53, 25, 58, 98, 67),
    ).mapIndexed { idx, nums -> orderSet(11 + idx, nums, increasing = true) }
    return compares + orders
  }

  fun getWorksheet10(): List<MathProblem> = listOf(
    listOf(65, 12, 34, 23, 13),
    listOf(23, 40, 16, 12, 33),
    listOf(36, 32, 24, 28, 18),
    listOf(10, 27, 40, 94, 70),
    listOf(84, 72, 42, 56, 12),
    listOf(40, 15, 47, 14, 25),
    listOf(46, 75, 63, 54, 15),
    listOf(49, 58, 35, 25, 45),
    listOf(72, 63, 64, 7, 68),
    listOf(14, 47, 83, 21, 87),
    listOf(55, 78, 79, 83, 53),
    listOf(34, 94, 68, 12, 77),
    listOf(87, 25, 74, 16, 46),
    listOf(31, 54, 6, 47, 57),
    listOf(56, 97, 48, 45, 19),
  ).mapIndexed { idx, nums -> orderSet(idx + 1, nums, increasing = false) }

  fun getWorksheet11(): List<MathProblem> {
    val compares = listOf(
      41 to 48, 87 to 99, 43 to 89, 40 to 41, 87 to 87,
      50 to 67, 85 to 4, 52 to 51, 56 to 67, 15 to 43,
    ).mapIndexed { idx, (a, b) -> cmp(idx + 1, a, b) }

    val orders = listOf(
      listOf(14, 26, 98, 75, 42, 36),
      listOf(65, 98, 64, 75, 10, 29),
      listOf(36, 35, 48, 14, 89, 75),
      listOf(65, 41, 46, 56, 98, 78),
      listOf(80, 63, 23, 49, 26, 11),
      listOf(51, 82, 89, 50, 57, 37),
      listOf(63, 54, 29, 84, 39, 29),
      listOf(67, 14, 29, 78, 96, 42),
      listOf(34, 79, 75, 46, 58, 67),
      listOf(39, 18, 64, 12, 48, 46),
    ).mapIndexed { idx, nums -> orderSet(11 + idx, nums, increasing = false) }
    return compares + orders
  }

  fun getWorksheet12(): List<MathProblem> {
    val series1To6 = listOf(
      Triple(1, listOf(7, null, 9, null, 11, null), listOf(8, 10, 12)),
      Triple(2, listOf(4, 5, null, null, 8, null), listOf(6, 7, 9)),
      Triple(3, listOf(null, 10, 11, null, null, 14), listOf(9, 12, 13)),
      Triple(4, listOf(null, null, 14, null, 16, 17), listOf(12, 13, 15)),
      Triple(5, listOf(15, null, 17, null, 19, null), listOf(16, 18, 20)),
      Triple(6, listOf(null, 9, null, null, 12, 13), listOf(8, 10, 11)),
    ).map { (item, seq, blanks) ->
      SkipCountGridProblem(
        itemNumber = item,
        style = SkipGridStyle.OBJECT_SEQUENCE,
        title = "Write the missing numbers in the series:",
        stepSize = 1,
        sequenceValues = seq,
        expectedBlanks = blanks,
        hint = "Count forward by 1 (or count backward by 1 from the next number).",
      )
    }

    val grid1Given = setOf(3, 5, 6, 10, 12, 13, 17, 18)
    val grid1Targets = (1..20).filter { it !in grid1Given }
    val grid1Items = grid1Targets.mapIndexed { idx, target ->
      SkipCountGridProblem(
        itemNumber = 7 + idx,
        style = SkipGridStyle.NUMBER_GRID_4X5,
        title = "Fill the highlighted empty box in the 1–20 chart:",
        stepSize = 1,
        gridStart = 1,
        gridGivenValues = grid1Given,
        targetBlankValue = target,
        expectedBlanks = listOf(target),
        hint = "Count along the row from left to right starting at 1, 2, 3, 4...",
      )
    }

    val animals = listOf(
      Triple(19, listOf(15, null, 17, null, 19, null), listOf(16, 18, 20)),
      Triple(20, listOf(null, 19, 20, null, 22, null), listOf(18, 21, 23)),
      Triple(21, listOf(21, 22, null, 24, null, null), listOf(23, 25, 26)),
      Triple(22, listOf(25, null, null, 28, 29, null), listOf(26, 27, 30)),
      Triple(23, listOf(null, 25, null, 27, null, 29), listOf(24, 26, 28)),
      Triple(24, listOf(19, null, 21, null, null, 24), listOf(20, 22, 23)),
    ).map { (item, seq, blanks) ->
      SkipCountGridProblem(
        itemNumber = item,
        style = SkipGridStyle.ANIMAL_TRACK,
        title = "Help the animal jump across the number path:",
        stepSize = 1,
        sequenceValues = seq,
        expectedBlanks = blanks,
        hint = "Add 1 for each hop along the animal path!",
      )
    }

    val grid2Given = setOf(12, 14, 15, 20, 21, 26, 27, 29)
    val grid2Targets = (11..30).filter { it !in grid2Given }
    val grid2Items = grid2Targets.mapIndexed { idx, target ->
      SkipCountGridProblem(
        itemNumber = 25 + idx,
        style = SkipGridStyle.NUMBER_GRID_4X5,
        title = "Fill the highlighted empty box in the 11–30 chart:",
        stepSize = 1,
        gridStart = 11,
        gridGivenValues = grid2Given,
        targetBlankValue = target,
        expectedBlanks = listOf(target),
        hint = "Start at 11 in the top-left box and count forward by 1.",
      )
    }

    return series1To6 + grid1Items + animals + grid2Items
  }

  fun getWorksheet13(): List<MathProblem> {
    val row1 = listOf(OrdinalShapeKind.TRIANGLE, OrdinalShapeKind.OVAL, OrdinalShapeKind.RECTANGLE, OrdinalShapeKind.CLOUD, OrdinalShapeKind.DIAMOND)
    val col1 = listOf(OrdinalShapeKind.SQUARE, OrdinalShapeKind.SMILEY, OrdinalShapeKind.CIRCLE, OrdinalShapeKind.TRIANGLE)
    val row2 = listOf(
      OrdinalShapeKind.SQUARE, OrdinalShapeKind.SQUARE, OrdinalShapeKind.SMILEY, OrdinalShapeKind.SQUARE, OrdinalShapeKind.SQUARE,
      OrdinalShapeKind.TRIANGLE, OrdinalShapeKind.CIRCLE, OrdinalShapeKind.SQUARE, OrdinalShapeKind.CLOUD, OrdinalShapeKind.DIAMOND,
    )
    val col2 = listOf(
      OrdinalShapeKind.TRIANGLE, OrdinalShapeKind.SQUARE, OrdinalShapeKind.CIRCLE, OrdinalShapeKind.TRIANGLE,
      OrdinalShapeKind.CIRCLE, OrdinalShapeKind.SQUARE, OrdinalShapeKind.CIRCLE, OrdinalShapeKind.HEART,
    )

    return listOf(
      OrdinalShapeProblem(1, row1, false, "What is the 2nd figure from the right?", 3, "Start from the RIGHTMOST shape (Diamond = 1st) and count 1, 2."),
      OrdinalShapeProblem(2, row1, false, "What is the 4th figure from the right?", 1, "Start from the RIGHT (Diamond = 1st) and count left to 4th."),
      OrdinalShapeProblem(3, row1, false, "What is the 3rd figure from the left?", 2, "Start from the LEFT (Triangle = 1st) and count right to 3rd."),
      OrdinalShapeProblem(4, row1, false, "What is the 1st figure from the left?", 0, "The 1st from the left is the very first shape on the left side."),
      OrdinalShapeProblem(5, row1, false, "What is the 1st figure from the right?", 4, "The 1st from the right is the very first shape on the right side."),
      OrdinalShapeProblem(6, col1, true, "What is the 2nd figure from the bottom?", 2, "Start at the BOTTOM (Triangle = 1st) and count up to 2nd."),
      OrdinalShapeProblem(7, col1, true, "What is the 1st figure from the top?", 0, "Look at the very TOP shape in the column."),
      OrdinalShapeProblem(8, col1, true, "What is the 2nd figure from the top?", 1, "Start at the TOP (1st) and move down one step."),
      OrdinalShapeProblem(9, col1, true, "What is the 3rd figure from the top?", 2, "Count 1st, 2nd, 3rd starting from the TOP."),
      OrdinalShapeProblem(10, col1, true, "What is the 3rd figure from the bottom?", 1, "Count 1st, 2nd, 3rd going UP from the BOTTOM."),
      OrdinalShapeProblem(11, row2, false, "What is the 6th figure from the left?", 5, "Count 1st through 6th starting from the LEFT."),
      OrdinalShapeProblem(12, row2, false, "What is the 8th figure from the right?", 2, "Start from the RIGHTMOST figure (1st) and count 8 spots left."),
      OrdinalShapeProblem(13, row2, false, "What is the 9th figure from the left?", 8, "Start from the LEFTMOST figure and count 9 spots right."),
      OrdinalShapeProblem(14, row2, false, "What is the 7th figure from the right?", 3, "Start from the RIGHTMOST figure (Diamond = 1st) and count 7 spots left."),
      OrdinalShapeProblem(15, row2, false, "What is the 6th figure from the right?", 4, "Start from the RIGHTMOST figure (Diamond = 1st) and count 6 spots left."),
      OrdinalShapeProblem(16, col2, true, "What is the 5th figure from the top?", 4, "Count 1, 2, 3, 4, 5 starting at the TOP."),
      OrdinalShapeProblem(17, col2, true, "What is the 7th figure from the bottom?", 1, "Start at Heart (1st from bottom) and count up to 7th."),
      OrdinalShapeProblem(18, col2, true, "What is the 6th figure from the bottom?", 2, "Start at Heart (1st from bottom) and count up to 6th."),
      OrdinalShapeProblem(19, col2, true, "What is the 8th figure from the top?", 7, "Count down from the TOP (1st) all the way to the 8th figure."),
      OrdinalShapeProblem(20, col2, true, "What is the 6th figure from the top?", 5, "Count down from the TOP 1, 2, 3, 4, 5, 6."),
    )
  }
}
