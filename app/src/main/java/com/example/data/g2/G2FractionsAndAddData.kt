package com.example.data.g2

import com.example.model.*

object G2FractionsAndAddData {

  fun getWorksheet14(): List<MathProblem> = listOf(
    EqualPartsPartitionProblem(1, PartitionShapeKind.SQUARE, 2, "1/2", "Choose the cut that makes 2 equal halves (1/2 each)."),
    EqualPartsPartitionProblem(2, PartitionShapeKind.CIRCLE, 4, "1/4", "Choose the cut that makes 4 equal quarters (1/4 each)."),
    EqualPartsPartitionProblem(3, PartitionShapeKind.RECTANGLE, 4, "1/4", "Choose the cut that makes 4 equal parts (1/4 each)."),
    EqualPartsPartitionProblem(4, PartitionShapeKind.HEXAGON, 2, "1/2", "Choose the cut that makes 2 equal halves (1/2 each)."),
    EqualPartsPartitionProblem(5, PartitionShapeKind.HEART, 2, "1/2", "Choose the cut that makes 2 equal halves (1/2 each)."),
  )

  fun getWorksheet15(): List<MathProblem> = listOf(
    InteractiveFractionProblem(1, InteractiveFractionMode.PICK_FRACTION_FROM_FIGURE, G2FractionFigureKind.CIRCLE_4_ORTHO, "Choose the correct fraction for the shaded parts:", 4, 2, setOf(1, 3), listOf("1/2", "1/4", "2/4"), "2/4", hint = "Count 2 shaded parts out of 4 equal parts."),
    InteractiveFractionProblem(2, InteractiveFractionMode.PICK_FRACTION_FROM_FIGURE, G2FractionFigureKind.CIRCLE_2_VERT, "Choose the correct fraction for the shaded part:", 2, 1, setOf(0), listOf("1/2", "3/4", "1/4"), "1/2", hint = "1 part is shaded out of 2 equal parts."),
    InteractiveFractionProblem(3, InteractiveFractionMode.PICK_FRACTION_FROM_FIGURE, G2FractionFigureKind.CIRCLE_2_VERT, "Choose the correct fraction for the shaded part:", 2, 1, setOf(0), listOf("2/4", "1/2", "1/4"), "1/2", hint = "Half of the circle is shaded (1 out of 2 equal parts)."),
    InteractiveFractionProblem(4, InteractiveFractionMode.PICK_FRACTION_FROM_FIGURE, G2FractionFigureKind.CIRCLE_4_DIAG, "Choose the correct fraction for the shaded parts:", 4, 2, setOf(0, 2), listOf("1/2", "2/2", "2/4"), "2/4", hint = "There are 2 shaded slices out of 4 total slices."),
    InteractiveFractionProblem(5, InteractiveFractionMode.G2_WRITE_NUMERATOR, G2FractionFigureKind.TRIANGLE_2_VERT, "Write the NUMERATOR (top number) for the shaded part:", 2, 1, setOf(0), hint = "Count how many parts are shaded."),
    InteractiveFractionProblem(6, InteractiveFractionMode.G2_WRITE_NUMERATOR, G2FractionFigureKind.CIRCLE_2_VERT, "Write the NUMERATOR (top number) for the shaded part:", 2, 1, setOf(0), hint = "The numerator is the count of shaded parts."),
    InteractiveFractionProblem(7, InteractiveFractionMode.G2_WRITE_NUMERATOR, G2FractionFigureKind.DIAMOND_4_GRID, "Write the NUMERATOR (top number) for the shaded part:", 4, 1, setOf(2), hint = "How many diamond sections are shaded?"),
    InteractiveFractionProblem(8, InteractiveFractionMode.G2_WRITE_NUMERATOR, G2FractionFigureKind.SQUARE_4_GRID, "Write the NUMERATOR (top number) for the shaded part:", 4, 1, setOf(1), hint = "Count the shaded small square(s)."),
    InteractiveFractionProblem(9, InteractiveFractionMode.G2_WRITE_NUMERATOR, G2FractionFigureKind.HEXAGON_4_GRID, "Write the NUMERATOR (top number) for the shaded part:", 4, 1, setOf(0), hint = "Only count the shaded part(s)."),
    InteractiveFractionProblem(10, InteractiveFractionMode.G2_WRITE_NUMERATOR, G2FractionFigureKind.RECT_2_DIAG, "Write the NUMERATOR (top number) for the shaded part:", 2, 1, setOf(1), hint = "Count how many triangles of the rectangle are shaded."),
    InteractiveFractionProblem(11, InteractiveFractionMode.G2_WRITE_DENOMINATOR, G2FractionFigureKind.RECT_2_VERT, "Write the DENOMINATOR (bottom number) for the figure:", 2, 1, setOf(1), hint = "Count ALL equal parts in the rectangle (shaded + unshaded)."),
    InteractiveFractionProblem(12, InteractiveFractionMode.G2_WRITE_DENOMINATOR, G2FractionFigureKind.CIRCLE_4_ORTHO, "Write the DENOMINATOR (bottom number) for the figure:", 4, 1, setOf(0), hint = "Count ALL equal parts in the circle."),
    InteractiveFractionProblem(13, InteractiveFractionMode.G2_WRITE_DENOMINATOR, G2FractionFigureKind.CIRCLE_4_ORTHO, "Write the DENOMINATOR (bottom number) for the figure:", 4, 1, setOf(0), hint = "How many equal pieces is the whole circle divided into?"),
    InteractiveFractionProblem(14, InteractiveFractionMode.G2_WRITE_DENOMINATOR, G2FractionFigureKind.HEXAGON_2_VERT, "Write the DENOMINATOR (bottom number) for the figure:", 2, 1, setOf(0), hint = "Count the total equal halves in the hexagon."),
    InteractiveFractionProblem(15, InteractiveFractionMode.G2_WRITE_DENOMINATOR, G2FractionFigureKind.SQUARE_4_DIAG, "Write the DENOMINATOR (bottom number) for the figure:", 4, 1, setOf(3), hint = "Count all 4 equal triangles inside the square."),
    InteractiveFractionProblem(16, InteractiveFractionMode.G2_WRITE_DENOMINATOR, G2FractionFigureKind.RECT_2_HORIZ, "Write the DENOMINATOR (bottom number) for the figure:", 2, 1, setOf(0), hint = "Count how many equal boxes make the whole rectangle."),
  )

  fun getWorksheet16(): List<MathProblem> = listOf(
    InteractiveFractionProblem(1, InteractiveFractionMode.SHADE_PARTS, G2FractionFigureKind.CIRCLE_2_VERT, "Shade the figure to show 1/2:", 2, 1, hint = "Tap 1 of the 2 equal parts to show 1/2."),
    InteractiveFractionProblem(2, InteractiveFractionMode.SHADE_PARTS, G2FractionFigureKind.SQUARE_4_DIAG, "Shade the figure to show 1/4:", 4, 1, hint = "Tap 1 of the 4 triangles to show 1/4."),
    InteractiveFractionProblem(3, InteractiveFractionMode.SHADE_PARTS, G2FractionFigureKind.HEXAGON_4_GRID, "Shade the figure to show 2/4:", 4, 2, hint = "Tap 2 of the 4 equal parts to show 2/4."),
    InteractiveFractionProblem(4, InteractiveFractionMode.SHADE_PARTS, G2FractionFigureKind.CROSS_2_VERT, "Shade the figure to show 1/2:", 2, 1, hint = "Tap 1 half of the cross to show 1/2."),
    InteractiveFractionProblem(5, InteractiveFractionMode.SHADE_PARTS, G2FractionFigureKind.CIRCLE_4_ORTHO, "Shade the figure to show 1/4:", 4, 1, hint = "Tap 1 quarter slice to show 1/4."),
    InteractiveFractionProblem(6, InteractiveFractionMode.SHADE_PARTS, G2FractionFigureKind.HEXAGON_4_GRID, "Shade the figure to show 1/4:", 4, 1, hint = "Tap 1 of the 4 parts."),
    InteractiveFractionProblem(7, InteractiveFractionMode.SHADE_PARTS, G2FractionFigureKind.TRIANGLE_2_VERT, "Shade the figure to show 1/2:", 2, 1, hint = "Tap 1 half of the triangle."),
    InteractiveFractionProblem(8, InteractiveFractionMode.SHADE_PARTS, G2FractionFigureKind.RECT_4_STRIP, "Shade the figure to show 1/4:", 4, 1, hint = "Tap 1 of the 4 strips."),
    InteractiveFractionProblem(9, InteractiveFractionMode.SHADE_PARTS, G2FractionFigureKind.DIAMOND_4_GRID, "Shade the figure to show 1/4:", 4, 1, hint = "Tap 1 of the 4 equal diamond parts."),
    InteractiveFractionProblem(10, InteractiveFractionMode.SHADE_PARTS, G2FractionFigureKind.RECT_2_VERT, "Shade the figure to show 1/2:", 2, 1, hint = "Tap 1 of the 2 equal parts."),
    InteractiveFractionProblem(11, InteractiveFractionMode.DIVIDE_AND_SHADE, G2FractionFigureKind.CIRCLE_2_VERT, "Divide and shade the circle to show 1/2:", 2, 1, allowAnyDivisorFirst = true, hint = "Divide into 2 halves, then shade 1 part."),
    InteractiveFractionProblem(12, InteractiveFractionMode.DIVIDE_AND_SHADE, G2FractionFigureKind.CIRCLE_4_ORTHO, "Divide and shade the circle to show 1/4:", 4, 1, allowAnyDivisorFirst = true, hint = "Divide into 4 fourths, then shade 1 part."),
    InteractiveFractionProblem(13, InteractiveFractionMode.DIVIDE_AND_SHADE, G2FractionFigureKind.CIRCLE_4_ORTHO, "Divide and shade the circle to show 1/4:", 4, 1, allowAnyDivisorFirst = true, hint = "Divide into 4 fourths, then shade 1 part."),
    InteractiveFractionProblem(14, InteractiveFractionMode.DIVIDE_AND_SHADE, G2FractionFigureKind.CIRCLE_2_VERT, "Divide and shade the circle to show 1/2:", 2, 1, allowAnyDivisorFirst = true, hint = "Divide into 2 halves, then shade 1 part."),
    InteractiveFractionProblem(15, InteractiveFractionMode.DIVIDE_AND_SHADE, G2FractionFigureKind.RECT_4_STRIP, "Divide and shade the rectangle to show 1/4:", 4, 1, allowAnyDivisorFirst = true, hint = "Divide into 4 equal parts, then shade 1 part."),
    InteractiveFractionProblem(16, InteractiveFractionMode.DIVIDE_AND_SHADE, G2FractionFigureKind.RECT_4_STRIP, "Divide and shade the rectangle to show 1/4:", 4, 1, allowAnyDivisorFirst = true, hint = "Divide into 4 equal parts, then shade 1 part."),
    InteractiveFractionProblem(17, InteractiveFractionMode.DIVIDE_AND_SHADE, G2FractionFigureKind.RECT_4_STRIP, "Divide and shade the rectangle to show 1/4:", 4, 1, allowAnyDivisorFirst = true, hint = "Divide into 4 equal parts, then shade 1 part."),
    InteractiveFractionProblem(18, InteractiveFractionMode.DIVIDE_AND_SHADE, G2FractionFigureKind.RECT_2_VERT, "Divide and shade the rectangle to show 1/2:", 2, 1, allowAnyDivisorFirst = true, hint = "Divide into 2 halves, then shade 1 part."),
  )

  fun getWorksheet17(): List<MathProblem> = listOf(
    VisualAddSubSentenceProblem(1, true, CountObjectKind.CAR, 3, 2, 5, "Put the groups together and complete the addition sentence:", "Count the cars on the left, add the cars on the right, then find the total."),
    VisualAddSubSentenceProblem(2, true, CountObjectKind.CRAYON, 6, 3, 9, "Put the groups together and complete the addition sentence:", "Count the 6 crayons on the left and 3 crayons on the right, then add!"),
    VisualAddSubSentenceProblem(3, true, CountObjectKind.FLOWER, 3, 4, 7, "Put the groups together and complete the addition sentence:", "Count the left flowers, add the right flowers, and count all together."),
    VisualAddSubSentenceProblem(4, true, CountObjectKind.GRASSHOPPER, 2, 4, 6, "Put the groups together and complete the addition sentence:", "Count the left grasshoppers plus the right grasshoppers."),
    VisualAddSubSentenceProblem(5, true, CountObjectKind.TULIP, 9, 1, 10, "Put the groups together and complete the addition sentence:", "Count the 9 tulips on the left plus 1 tulip on the right."),
  )

  fun getWorksheet18(): List<MathProblem> = listOf(
    1 to 7, 1 to 8, 1 to 9, 2 to 6, 2 to 8,
    2 to 9, 3 to 6, 3 to 8, 3 to 7, 3 to 9,
    4 to 6, 4 to 7, 4 to 9, 4 to 8, 5 to 5,
  ).mapIndexed { idx, (a, b) ->
    MathProblem.ArithmeticProblem(
      itemNumber = idx + 1,
      operands = listOf(a, b),
      op = ArithmeticOp.ADD,
      answer = a + b,
      showVerticalColumns = false,
      hint = "Start at the larger number (${maxOf(a, b)}) and count on ${minOf(a, b)} more!",
    )
  }

  fun getWorksheet19(): List<MathProblem> = listOf(
    listOf(4, 2, 9), listOf(5, 6, 3), listOf(7, 8, 2), listOf(8, 7, 6), listOf(3, 9, 4),
    listOf(6, 6, 7), listOf(5, 7, 4), listOf(9, 9, 2), listOf(4, 8, 5), listOf(7, 5, 4),
    listOf(7, 3, 9), listOf(8, 4, 6), listOf(3, 5, 8), listOf(7, 6, 7), listOf(4, 8, 9),
    listOf(7, 4, 6), listOf(6, 3, 7), listOf(5, 4, 9), listOf(4, 6, 8), listOf(9, 3, 5),
  ).mapIndexed { idx, ops ->
    MathProblem.ArithmeticProblem(
      itemNumber = idx + 1,
      operands = ops,
      op = ArithmeticOp.ADD,
      answer = ops.sum(),
      showVerticalColumns = false,
      hint = "Add the first two numbers first, or pair two that make 10, then add the third!",
    )
  }

  fun getWorksheet20(): List<MathProblem> = listOf(
    10 to 3, 12 to 4, 14 to 5, 15 to 2, 25 to 4, 23 to 6, 24 to 5,
    26 to 2, 20 to 8, 31 to 5, 30 to 6, 32 to 7, 35 to 4, 33 to 5,
  ).mapIndexed { idx, (a, b) ->
    MathProblem.ArithmeticProblem(
      itemNumber = idx + 1,
      operands = listOf(a, b),
      op = ArithmeticOp.ADD,
      answer = a + b,
      showVerticalColumns = true,
      hint = "Add the ones digits (${a % 10} + $b), then keep the ${a / 10} ten(s).",
    )
  }

  fun getWorksheet21(): List<MathProblem> = listOf(
    17 to 3, 16 to 4, 18 to 5, 19 to 2, 25 to 5, 24 to 6, 29 to 7,
    23 to 7, 22 to 8, 36 to 5, 35 to 6, 34 to 7, 37 to 4, 35 to 5,
  ).mapIndexed { idx, (a, b) ->
    MathProblem.ArithmeticProblem(
      itemNumber = idx + 1,
      operands = listOf(a, b),
      op = ArithmeticOp.ADD,
      answer = a + b,
      showVerticalColumns = true,
      hint = "Add the ones (${a % 10} + $b = ${(a % 10) + b}); write the ones digit and carry 1 to the tens place!",
    )
  }

  fun getWorksheet22(): List<MathProblem> = listOf(
    15 to 12, 24 to 11, 36 to 13, 64 to 14, 72 to 24, 15 to 21, 10 to 44,
    65 to 20, 54 to 23, 52 to 23, 77 to 20, 12 to 26, 10 to 79, 25 to 11,
  ).mapIndexed { idx, (a, b) ->
    MathProblem.ArithmeticProblem(
      itemNumber = idx + 1,
      operands = listOf(a, b),
      op = ArithmeticOp.ADD,
      answer = a + b,
      showVerticalColumns = true,
      hint = "Add the ones column (${a % 10} + ${b % 10}), then add the tens column (${a / 10} + ${b / 10}).",
    )
  }

  fun getWorksheet23(): List<MathProblem> = listOf(
    27 to 13, 28 to 14, 36 to 17, 38 to 14, 37 to 24, 35 to 29, 42 to 48,
    45 to 26, 44 to 27, 57 to 23, 55 to 26, 65 to 26, 67 to 29, 65 to 19,
  ).mapIndexed { idx, (a, b) ->
    MathProblem.ArithmeticProblem(
      itemNumber = idx + 1,
      operands = listOf(a, b),
      op = ArithmeticOp.ADD,
      answer = a + b,
      showVerticalColumns = true,
      hint = "Ones: ${a % 10} + ${b % 10} = ${(a % 10) + (b % 10)} (carry 1 ten!). Then add all tens.",
    )
  }
}
