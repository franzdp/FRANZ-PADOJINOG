package com.example.data.g2

import com.example.model.*

object G2SubtractShapesCalendarData {

  fun getWorksheet24(): List<MathProblem> = listOf(
    VisualAddSubSentenceProblem(1, false, CountObjectKind.HEART, 3, 1, 2, "Write the subtraction sentence for the broken hearts:", "Count all 3 hearts first, take away 1 broken heart, and count the rest."),
    VisualAddSubSentenceProblem(2, false, CountObjectKind.ICECREAM, 7, 3, 4, "Write the subtraction sentence for the ice cream cones:", "Start with all 7 ice creams, subtract the 3 eaten cones."),
    VisualAddSubSentenceProblem(3, false, CountObjectKind.CAT, 5, 2, 3, "Write the subtraction sentence for the cats:", "Count all 5 cats, subtract the 2 cats walking away."),
    VisualAddSubSentenceProblem(4, false, CountObjectKind.GLASS, 4, 3, 1, "Write the subtraction sentence for the glasses:", "There are 4 glasses total; 3 are empty/taken away."),
    VisualAddSubSentenceProblem(5, false, CountObjectKind.SMILEY, 6, 4, 2, "Write the subtraction sentence for the faces:", "Count all 6 faces, take away the 4 sad faces."),
  )

  fun getWorksheet25(): List<MathProblem> = listOf(
    EquationPartProblem(1, 8, 4, 4, SubSentencePart.MINUEND, "The first number we start with before subtracting is the MINUEND."),
    EquationPartProblem(2, 6, 2, 2, SubSentencePart.DIFFERENCE, "The answer after '=' in subtraction is the DIFFERENCE."),
    EquationPartProblem(3, 9, 3, 6, SubSentencePart.MINUEND, "The starting whole number in subtraction is the MINUEND."),
    EquationPartProblem(4, 7, 1, 6, SubSentencePart.SUBTRAHEND, "The number being taken away after '-' is the SUBTRAHEND."),
    EquationPartProblem(5, 6, 5, 1, SubSentencePart.SUBTRAHEND, "The number subtracted after '-' is the SUBTRAHEND."),
    EquationPartProblem(6, 7, 3, 4, SubSentencePart.DIFFERENCE, "The result after '=' is called the DIFFERENCE."),
    EquationPartProblem(7, 8, 2, 6, SubSentencePart.DIFFERENCE, "The result of subtraction after '=' is the DIFFERENCE."),
    EquationPartProblem(8, 10, 5, 5, SubSentencePart.MINUEND, "The first number in the subtraction sentence is the MINUEND."),
    EquationPartProblem(9, 10, 6, 4, SubSentencePart.SUBTRAHEND, "The number taken away is the SUBTRAHEND."),
    EquationPartProblem(10, 10, 2, 8, SubSentencePart.DIFFERENCE, "The final answer after '=' is the DIFFERENCE."),
  )

  fun getWorksheet26(): List<MathProblem> = listOf(
    25 to 4, 26 to 3, 24 to 2, 26 to 2, 28 to 8,
    35 to 5, 36 to 6, 37 to 2, 35 to 4, 38 to 5,
    42 to 1, 45 to 3, 46 to 4, 48 to 5, 49 to 6,
    53 to 2, 55 to 1, 57 to 5, 58 to 4, 56 to 3,
  ).mapIndexed { idx, (a, b) ->
    MathProblem.ArithmeticProblem(
      itemNumber = idx + 1,
      operands = listOf(a, b),
      op = ArithmeticOp.SUBTRACT,
      answer = a - b,
      showVerticalColumns = true,
      hint = "Subtract ones (${a % 10} - $b = ${(a % 10) - b}), then bring down the tens digit ${a / 10}.",
    )
  }

  fun getWorksheet27(): List<MathProblem> = listOf(
    23 to 5, 32 to 4, 53 to 4, 63 to 9, 57 to 8,
    40 to 3, 55 to 6, 45 to 7, 51 to 2, 60 to 5,
  ).mapIndexed { idx, (a, b) ->
    MathProblem.ArithmeticProblem(
      itemNumber = idx + 1,
      operands = listOf(a, b),
      op = ArithmeticOp.SUBTRACT,
      answer = a - b,
      showVerticalColumns = true,
      hint = "Borrow 1 ten from ${a / 10} (leaving ${(a / 10) - 1} tens) and add 10 to ${a % 10} -> ${10 + (a % 10)} - $b!",
    )
  }

  fun getWorksheet28(): List<MathProblem> = listOf(
    25 to 12, 24 to 11, 36 to 13, 64 to 14, 25 to 15, 29 to 18, 39 to 25,
    47 to 13, 54 to 32, 58 to 35, 43 to 32, 36 to 22, 45 to 24, 55 to 15,
  ).mapIndexed { idx, (a, b) ->
    MathProblem.ArithmeticProblem(
      itemNumber = idx + 1,
      operands = listOf(a, b),
      op = ArithmeticOp.SUBTRACT,
      answer = a - b,
      showVerticalColumns = true,
      hint = "Ones: ${a % 10} - ${b % 10}. Tens: ${a / 10} - ${b / 10}.",
    )
  }

  fun getWorksheet29(): List<MathProblem> = listOf(
    65 to 56, 63 to 45, 71 to 62, 78 to 53, 67 to 38,
    76 to 59, 72 to 56, 68 to 39, 64 to 57, 74 to 45,
  ).mapIndexed { idx, (a, b) ->
    MathProblem.ArithmeticProblem(
      itemNumber = idx + 1,
      operands = listOf(a, b),
      op = ArithmeticOp.SUBTRACT,
      answer = a - b,
      showVerticalColumns = true,
      hint = if ((a % 10) < (b % 10)) "Borrow 1 ten from ${a / 10} -> ${(a % 10) + 10} - ${b % 10} in ones, then ${(a / 10) - 1} - ${b / 10} in tens!" else "Subtract ones (${a % 10} - ${b % 10}), then subtract tens (${a / 10} - ${b / 10}).",
    )
  }

  fun getWorksheet30(): List<MathProblem> {
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
        hint = "Count the sides and corners — Circle is round; Triangle has 3 sides; Square has 4 equal sides; Rectangle has 2 long & 2 short sides.",
      )
    }

    val pickCircles = listOf(
      listOf(RealWorldObjectKind.COIN, RealWorldObjectKind.RULER, RealWorldObjectKind.NOTEBOOK, RealWorldObjectKind.CORN_CHIP),
      listOf(RealWorldObjectKind.CLOCK, RealWorldObjectKind.DOOR, RealWorldObjectKind.CHEESE_WEDGE, RealWorldObjectKind.FLOPPY_DISK),
      listOf(RealWorldObjectKind.BALL, RealWorldObjectKind.CHALKBOARD, RealWorldObjectKind.TRIANGLE_FLAG, RealWorldObjectKind.WINDOW),
    ).mapIndexed { i, opts -> ShapeClassificationProblem(9 + i, ShapeExerciseMode.PICK_OBJECT_BY_SHAPE, "Pick an object that takes the shape of a CIRCLE:", BasicShapeKind.CIRCLE, objectChoices = opts, hint = "Look for the round object with no corners.") }

    val pickSquares = listOf(
      listOf(RealWorldObjectKind.NOTEBOOK, RealWorldObjectKind.COIN, RealWorldObjectKind.RULER, RealWorldObjectKind.CORN_CHIP),
      listOf(RealWorldObjectKind.FLOPPY_DISK, RealWorldObjectKind.DONUT, RealWorldObjectKind.PIZZA_SLICE, RealWorldObjectKind.SUITCASE),
      listOf(RealWorldObjectKind.WINDOW, RealWorldObjectKind.CLOCK, RealWorldObjectKind.MOUNTAIN, RealWorldObjectKind.DOOR),
    ).mapIndexed { i, opts -> ShapeClassificationProblem(12 + i, ShapeExerciseMode.PICK_OBJECT_BY_SHAPE, "Pick an object that takes the shape of a SQUARE:", BasicShapeKind.SQUARE, objectChoices = opts, hint = "Look for an object with 4 equal sides.") }

    val pickRects = listOf(
      listOf(RealWorldObjectKind.RULER, RealWorldObjectKind.COIN, RealWorldObjectKind.SQUARE_FRAME, RealWorldObjectKind.MOUNTAIN),
      listOf(RealWorldObjectKind.DOOR, RealWorldObjectKind.DONUT, RealWorldObjectKind.HANDKERCHIEF, RealWorldObjectKind.WATERMELON_SLICE),
      listOf(RealWorldObjectKind.CHALKBOARD, RealWorldObjectKind.BALL, RealWorldObjectKind.TOWEL, RealWorldObjectKind.CHEESE_WEDGE),
    ).mapIndexed { i, opts -> ShapeClassificationProblem(15 + i, ShapeExerciseMode.PICK_OBJECT_BY_SHAPE, "Pick an object that takes the shape of a RECTANGLE:", BasicShapeKind.RECTANGLE, objectChoices = opts, hint = "Look for an object with 2 long sides and 2 short sides.") }

    val pickTris = listOf(
      listOf(RealWorldObjectKind.CORN_CHIP, RealWorldObjectKind.COIN, RealWorldObjectKind.RULER, RealWorldObjectKind.NOTEBOOK),
      listOf(RealWorldObjectKind.PIZZA_SLICE, RealWorldObjectKind.DONUT, RealWorldObjectKind.DOOR, RealWorldObjectKind.WINDOW),
      listOf(RealWorldObjectKind.TRIANGLE_FLAG, RealWorldObjectKind.BALL, RealWorldObjectKind.SUITCASE, RealWorldObjectKind.FLOPPY_DISK),
    ).mapIndexed { i, opts -> ShapeClassificationProblem(18 + i, ShapeExerciseMode.PICK_OBJECT_BY_SHAPE, "Pick an object that takes the shape of a TRIANGLE:", BasicShapeKind.TRIANGLE, objectChoices = opts, hint = "Look for an object with 3 sides and 3 corners.") }

    return nameObjects + pickCircles + pickSquares + pickRects + pickTris
  }

  fun getWorksheet31(): List<MathProblem> = listOf(
    ShapeClassificationProblem(1, ShapeExerciseMode.PICK_FROM_THREE_SHAPES, "Tap the CIRCLE:", BasicShapeKind.CIRCLE, shapeChoices = listOf(BasicShapeKind.TRIANGLE, BasicShapeKind.RECTANGLE, BasicShapeKind.CIRCLE), hint = "Find the round shape with 0 corners."),
    ShapeClassificationProblem(2, ShapeExerciseMode.PICK_FROM_THREE_SHAPES, "Tap the RECTANGLE:", BasicShapeKind.RECTANGLE, shapeChoices = listOf(BasicShapeKind.SQUARE, BasicShapeKind.RECTANGLE, BasicShapeKind.TRIANGLE), hint = "Find the 4-sided shape that is longer than it is wide."),
    ShapeClassificationProblem(3, ShapeExerciseMode.PICK_FROM_THREE_SHAPES, "Tap the TRIANGLE:", BasicShapeKind.TRIANGLE, shapeChoices = listOf(BasicShapeKind.RECTANGLE, BasicShapeKind.CIRCLE, BasicShapeKind.TRIANGLE), hint = "Find the 3-sided shape."),
    ShapeClassificationProblem(4, ShapeExerciseMode.PICK_FROM_THREE_SHAPES, "Tap the SQUARE:", BasicShapeKind.SQUARE, shapeChoices = listOf(BasicShapeKind.SQUARE, BasicShapeKind.TRIANGLE, BasicShapeKind.CIRCLE), hint = "Find the shape with 4 equal sides."),
    ShapeClassificationProblem(5, ShapeExerciseMode.PICK_FROM_THREE_SHAPES, "Tap the TRIANGLE:", BasicShapeKind.TRIANGLE, shapeChoices = listOf(BasicShapeKind.CIRCLE, BasicShapeKind.TRIANGLE, BasicShapeKind.SQUARE), hint = "Look for 3 corners and 3 straight sides."),
    ShapeClassificationProblem(6, ShapeExerciseMode.PICK_FROM_THREE_SHAPES, "Tap the RECTANGLE:", BasicShapeKind.RECTANGLE, shapeChoices = listOf(BasicShapeKind.CIRCLE, BasicShapeKind.SQUARE, BasicShapeKind.RECTANGLE), hint = "Look for two longer sides and two shorter sides."),
    ShapeClassificationProblem(7, ShapeExerciseMode.PICK_FROM_THREE_OBJECTS, "Check the object shaped like a SQUARE:", BasicShapeKind.SQUARE, objectChoices = listOf(RealWorldObjectKind.PIZZA_WHOLE, RealWorldObjectKind.TOWEL, RealWorldObjectKind.DOOR), hint = "Which object has 4 equal sides?"),
    ShapeClassificationProblem(8, ShapeExerciseMode.PICK_FROM_THREE_OBJECTS, "Check the object shaped like a TRIANGLE:", BasicShapeKind.TRIANGLE, objectChoices = listOf(RealWorldObjectKind.WATERMELON_SLICE, RealWorldObjectKind.RULER, RealWorldObjectKind.SQUARE_FRAME), hint = "Which food or object has 3 sides and 3 corners?"),
    ShapeClassificationProblem(9, ShapeExerciseMode.PICK_FROM_THREE_OBJECTS, "Check the object shaped like a RECTANGLE:", BasicShapeKind.RECTANGLE, objectChoices = listOf(RealWorldObjectKind.SUITCASE, RealWorldObjectKind.CHEESE_WEDGE, RealWorldObjectKind.BALL), hint = "Which object has 4 corners with 2 long sides and 2 short sides?"),
    ShapeClassificationProblem(10, ShapeExerciseMode.PICK_FROM_THREE_OBJECTS, "Check the object shaped like a TRIANGLE:", BasicShapeKind.TRIANGLE, objectChoices = listOf(RealWorldObjectKind.HANDKERCHIEF, RealWorldObjectKind.CHALKBOARD, RealWorldObjectKind.MOUNTAIN), hint = "Which picture has 3 pointed corners?"),
    ShapeClassificationProblem(11, ShapeExerciseMode.PICK_FROM_THREE_OBJECTS, "Check the object shaped like a CIRCLE:", BasicShapeKind.CIRCLE, objectChoices = listOf(RealWorldObjectKind.SQUARE_CLOCK, RealWorldObjectKind.PIZZA_SLICE, RealWorldObjectKind.DONUT), hint = "Which object is completely round?"),
    ShapeClassificationProblem(12, ShapeExerciseMode.PICK_FROM_THREE_OBJECTS, "Check the object shaped like a SQUARE:", BasicShapeKind.SQUARE, objectChoices = listOf(RealWorldObjectKind.WINDOW, RealWorldObjectKind.MOON_COOKIE, RealWorldObjectKind.TRIANGLE_FLAG), hint = "Which object has 4 equal edges?"),
  )

  private fun txtPat(item: Int, seq: List<String>, answer: String, choices: List<String>, hint: String): MathProblem.PatternProblem =
    MathProblem.PatternProblem(
      itemNumber = item,
      tokens = seq.map { PatternToken(PatternTokenKind.TEXT, it) } + PatternToken(PatternTokenKind.BLANK),
      expectedAnswers = listOf(answer),
      choices = choices,
      isShapeOrLetterChoice = true,
      hint = hint,
    )

  fun getWorksheet32(): List<MathProblem> = listOf(
    txtPat(1, listOf("Hexagon", "Rectangle", "Hexagon", "Rectangle", "Hexagon"), "Rectangle", listOf("Rectangle", "Hexagon"), "Notice the alternating A-B pattern: Hexagon, Rectangle..."),
    txtPat(2, listOf("5-Star", "4-Spark", "Tall Triangle", "5-Star", "4-Spark", "Tall Triangle", "5-Star"), "4-Spark", listOf("Tall Triangle", "4-Spark"), "Three shapes repeat: 5-Star -> 4-Spark -> Tall Triangle."),
    txtPat(3, listOf("Right →", "Right →", "Left ←", "Right →", "Right →", "Left ←"), "Right →", listOf("Right →", "Left ←"), "Pattern repeats: Right, Right, Left, then starts again!"),
    txtPat(4, listOf("Heart ♥", "Spade ♠", "Heart ♥", "Spade ♠"), "Heart ♥", listOf("Spade ♠", "Heart ♥"), "Alternates Heart, Spade, Heart, Spade..."),
    txtPat(5, listOf("Black ●", "White ○", "White ○", "Black ●", "White ○"), "White ○", listOf("Black ●", "White ○"), "Pattern is Black, White, White — notice two Whites follow each Black!"),
    txtPat(6, listOf("Up ▲", "Down ▼", "Up ▲", "Down ▼", "Up ▲"), "Down ▼", listOf("Up ▲", "Down ▼"), "Triangles alternate pointing Up and Down."),
    txtPat(7, listOf("Square □", "Circle ○", "Circle ○", "Square □", "Circle ○"), "Circle ○", listOf("Square □", "Circle ○"), "Each Square is followed by TWO Circles."),
    txtPat(8, listOf("White Moon ☽", "Black Moon ☾", "White Moon ☽", "Black Moon ☾"), "White Moon ☽", listOf("White Moon ☽", "Black Moon ☾"), "White and Black crescent moons alternate."),
    txtPat(9, listOf("Box", "Box", "Cone", "Box", "Box", "Cone"), "Box", listOf("Box", "Cone"), "Two Boxes followed by one Cone, then repeat!"),
    txtPat(10, listOf("White ↑", "White ↓", "Gray ↑", "Gray ↓", "White ↑", "White ↓", "Gray ↑"), "Gray ↓", listOf("Gray ↓", "White ↓"), "Follows White ↑, White ↓, Gray ↑, Gray ↓!"),
    txtPat(11, listOf("Parallelogram ▱", "Right Triangle ⊿", "Parallelogram ▱"), "Right Triangle ⊿", listOf("Parallelogram ▱", "Right Triangle ⊿"), "Alternating Parallelogram and Right Triangle."),
    txtPat(12, listOf("Parallelogram ▱", "Right Triangle ⊿", "Parallelogram ▱", "Right Triangle ⊿"), "Parallelogram ▱", listOf("Parallelogram ▱", "Right Triangle ⊿"), "Alternates Parallelogram then Right Triangle."),
    txtPat(13, listOf("Black Diamond ◆", "Checkered Diamond ❖"), "Black Diamond ◆", listOf("Black Diamond ◆", "Checkered Diamond ❖"), "Alternates Black Diamond and Checkered Diamond."),
    txtPat(14, listOf("Checkered Diamond ❖", "Black Diamond ◆", "Checkered Diamond ❖", "Black Diamond ◆"), "Checkered Diamond ❖", listOf("Black Diamond ◆", "Checkered Diamond ❖"), "Alternating Black Diamond and Checkered Diamond."),
    txtPat(15, listOf("Horizontal Oval", "Star ☆", "Horizontal Oval", "Vertical Oval"), "Star ☆", listOf("Star ☆", "Vertical Oval"), "Look at the repeating column: Horizontal Oval, Star, Horizontal Oval, Vertical Oval, Star!"),
    txtPat(16, listOf("Vertical Oval", "Star ☆", "Horizontal Oval", "Vertical Oval", "Star ☆"), "Horizontal Oval", listOf("Horizontal Oval", "Vertical Oval"), "Each Star sits between Horizontal Oval and Vertical Oval!"),
    txtPat(17, listOf("White Rect", "Gray Rect", "Gray Rect", "White Rect"), "Gray Rect", listOf("White Rect", "Gray Rect"), "Pattern: 1 White rectangle then 2 Gray rectangles!"),
    txtPat(18, listOf("Gray Rect", "White Rect", "Gray Rect", "Gray Rect"), "White Rect", listOf("White Rect", "Gray Rect"), "Pattern: White, Gray, Gray, White, Gray, Gray!"),
  )

  fun getWorksheet33(): List<MathProblem> {
    val allDays = listOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")
    return listOf(
      Triple("The day that comes BEFORE Friday is:", "Friday", "Thursday"),
      Triple("The day that comes AFTER Tuesday is:", "Tuesday", "Wednesday"),
      Triple("What day comes BEFORE Wednesday?", "Wednesday", "Tuesday"),
      Triple("The day that comes BEFORE Thursday is:", "Thursday", "Wednesday"),
      Triple("What day comes BEFORE Sunday?", "Sunday", "Saturday"),
      Triple("What day comes AFTER Friday?", "Friday", "Saturday"),
      Triple("The day that comes AFTER Saturday is:", "Saturday", "Sunday"),
      Triple("What day comes AFTER Thursday?", "Thursday", "Friday"),
      Triple("What day comes AFTER Wednesday?", "Wednesday", "Thursday"),
      Triple("The day that comes AFTER Tuesday is:", "Tuesday", "Wednesday"),
      Triple("What is the day AFTER Monday?", "Monday", "Tuesday"),
      Triple("The day that comes AFTER Sunday is:", "Sunday", "Monday"),
      Triple("What day comes BEFORE Saturday?", "Saturday", "Friday"),
      Triple("The day that comes BEFORE Monday is:", "Monday", "Sunday"),
    ).mapIndexed { idx, (prompt, ref, ans) ->
      CalendarProblem(
        itemNumber = idx + 1,
        mode = CalendarMode.DAY_BEFORE_AFTER,
        prompt = prompt,
        referenceLabel = ref,
        validAnswers = listOf(ans),
        displayAnswer = ans,
        quickChoices = allDays,
        hint = "Recite the week: Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday.",
      )
    }
  }

  fun getWorksheet34(): List<MathProblem> {
    val monthOrds = (1..12).map { "${it}${when (it) { 1 -> "st"; 2 -> "nd"; 3 -> "rd"; else -> "th" }}" }
    val dayOrds = (1..7).map { "${it}${when (it) { 1 -> "st"; 2 -> "nd"; 3 -> "rd"; else -> "th" }}" }
    val months = listOf(
      "August" to "8th", "June" to "6th", "September" to "9th", "February" to "2nd",
      "December" to "12th", "April" to "4th", "July" to "7th", "November" to "11th",
      "March" to "3rd", "May" to "5th", "October" to "10th", "January" to "1st",
    ).mapIndexed { idx, (name, ord) ->
      CalendarProblem(idx + 1, CalendarMode.MONTH_ORDINAL, "Write the order of this month in the year:", name, listOf(ord), ord, monthOrds, "Count the months starting with January = 1st.")
    }
    val days = listOf(
      "Tuesday" to "3rd", "Thursday" to "5th", "Saturday" to "7th", "Monday" to "2nd",
      "Friday" to "6th", "Wednesday" to "4th", "Sunday" to "1st",
    ).mapIndexed { idx, (name, ord) ->
      CalendarProblem(13 + idx, CalendarMode.DAY_ORDINAL, "Write the order of this day in the week:", name, listOf(ord), ord, dayOrds, "Count the days starting with Sunday = 1st.")
    }
    return months + days
  }

  fun getWorksheet35(): List<MathProblem> {
    val allMonths = listOf("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December")
    return listOf(
      Triple("The month that comes BEFORE February is:", "February", "January"),
      Triple("What month comes AFTER October?", "October", "November"),
      Triple("The month that comes AFTER September is:", "September", "October"),
      Triple("What month comes BEFORE March?", "March", "February"),
      Triple("What month comes BEFORE June?", "June", "May"),
      Triple("The month that comes AFTER August is:", "August", "September"),
      Triple("What month comes BEFORE April?", "April", "March"),
      Triple("The month that comes AFTER November is:", "November", "December"),
      Triple("What month comes AFTER July?", "July", "August"),
      Triple("The month that comes BEFORE May is:", "May", "April"),
      Triple("What month comes AFTER May?", "May", "June"),
      Triple("The month that comes AFTER June is:", "June", "July"),
    ).mapIndexed { idx, (prompt, ref, ans) ->
      CalendarProblem(
        itemNumber = idx + 1,
        mode = CalendarMode.MONTH_BEFORE_AFTER,
        prompt = prompt,
        referenceLabel = ref,
        validAnswers = listOf(ans),
        displayAnswer = ans,
        quickChoices = allMonths,
        hint = "List the 12 months in order: Jan, Feb, Mar, Apr, May, Jun, Jul, Aug, Sep, Oct, Nov, Dec.",
      )
    }
  }

  fun getWorksheet36(): List<MathProblem> = listOf(
    CalendarProblem(1, CalendarMode.APRIL_CALENDAR_READ, "April has _____ days.", "", listOf("30", "30 days"), "30", listOf("28", "29", "30", "31"), "Look at the last date number in April 2014."),
    CalendarProblem(2, CalendarMode.APRIL_CALENDAR_READ, "April 14 is a _____.", "", listOf("Monday"), "Monday", listOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"), "Find 14 on the calendar and look up to its day column letter (M = Monday)."),
    CalendarProblem(3, CalendarMode.APRIL_CALENDAR_READ, "There are _____ Tuesdays in April.", "", listOf("5"), "5", listOf("3", "4", "5", "6"), "Count every date in the first T (Tuesday) column: 1, 8, 15, 22, 29."),
    CalendarProblem(4, CalendarMode.APRIL_CALENDAR_READ, "How many weeks are there in April?", "", listOf("4", "4 weeks", "5", "5 weeks"), "4 weeks", listOf("3", "4", "5", "6"), "A month is taught as having 4 weeks (though April spans parts of 5 calendar rows)."),
    CalendarProblem(5, CalendarMode.APRIL_CALENDAR_READ, "What day of the week is April 30?", "", listOf("Wednesday"), "Wednesday", listOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"), "Find 30 at the bottom row and look up to the W column."),
    CalendarProblem(6, CalendarMode.APRIL_CALENDAR_READ, "What is five days after April 3?", "", listOf("April 8", "8"), "April 8", listOf("April 6", "April 7", "April 8", "April 9"), "Start at April 3 and add 5 days: 3 + 5 = 8!"),
    CalendarProblem(7, CalendarMode.APRIL_CALENDAR_READ, "How many Sundays are there in April?", "", listOf("4"), "4", listOf("3", "4", "5", "6"), "Count all dates in the leftmost S (Sunday) column: 6, 13, 20, 27."),
    CalendarProblem(8, CalendarMode.APRIL_CALENDAR_READ, "What is 6 days before April 18?", "", listOf("April 12", "12"), "April 12", listOf("April 10", "April 11", "April 12", "April 13"), "Start at April 18 and subtract 6 days: 18 - 6 = 12!"),
  )
}
