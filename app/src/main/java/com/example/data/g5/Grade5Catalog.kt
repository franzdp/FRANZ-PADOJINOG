package com.example.data

import com.example.model.AralGrade
import com.example.model.LearningWorld
import com.example.model.MiniLesson
import com.example.model.WorksheetInfo

object Grade5Catalog {

  private val discLesson = MiniLesson(
    heading = "Visualizing 5-Digit Numbers",
    rules = listOf(
      "Each shape stands for a place value: Octagon = 50,000, Star = 10,000, Hexagon = 1,000, Square = 100, Trapezoid = 10, Circle = 1.",
      "Count how many of each shape appear and add their values together.",
      "To build a number, add discs matching its Ten Thousands, Thousands, Hundreds, Tens, and Ones digits.",
    ),
    exampleTitle = "Example: 1 Octagon + 2 Stars + 1 Hundred + 2 Ones",
    exampleBody = "50,000 + 20,000 + 100 + 2 = 70,102",
  )

  private val placeValueLesson = MiniLesson(
    heading = "Place Value & Value",
    rules = listOf(
      "From right to left: Ones, Tens, Hundreds, Thousands, Ten Thousands, Hundred Thousands.",
      "Place Value is the position name (like Ten Thousands).",
      "Value is the digit multiplied by its place (e.g. 5 in Ten Thousands = 50,000).",
    ),
    exampleTitle = "Example: 64,259",
    exampleBody = "Digit 4 is in the Thousands place; its value is 4,000.",
  )

  private val wordsCompareLesson = MiniLesson(
    heading = "Reading, Writing & Comparing Numbers",
    rules = listOf(
      "Use a comma after the thousands group when writing digits (e.g., 97,304).",
      "Use a hyphen between tens and ones ending in -y (e.g., twenty-one, sixty-five).",
      "Compare from the highest place value on the left toward the right.",
    ),
    exampleTitle = "Example: 42,300 vs forty-two thousand, three hundred",
    exampleBody = "Both equal 42,300, so use =.",
  )

  private val roundingOrderLesson = MiniLesson(
    heading = "Rounding & Ordering Numbers",
    rules = listOf(
      "Look at the digit immediately to the right of the rounding place.",
      "If it is 0–4, round down (keep the target digit). If 5–9, round up (+1), then turn all digits to the right into 0.",
      "Increasing = smallest to largest; Decreasing = largest to smallest.",
    ),
    exampleTitle = "Example: Round 86,854 to thousands",
    exampleBody = "Thousands digit is 6, next digit is 8 (≥ 5) → rounds up to 87,000.",
  )

  private val multiplyLesson = MiniLesson(
    heading = "Multi-Digit & Mental Multiplication",
    rules = listOf(
      "Multiply ones first, then tens (place a 0 placeholder in the second row), then add partial products.",
      "To estimate a product, round each 2+ digit factor to its highest place value first. Keep 1-digit factors as-is.",
      "Mental shortcuts: break apart tens & ones (14 × 3 = 10×3 + 4×3 = 42) or use ×10 / ×11.",
    ),
    exampleTitle = "Estimate: 240 × 63",
    exampleBody = "240 ≈ 200 and 63 ≈ 60 → 200 × 60 = 12,000.",
  )

  private val divideLesson = MiniLesson(
    heading = "Division, ÷10/100/1,000 & Compatible Numbers",
    rules = listOf(
      "Dividing by 10: last 1 digit is the remainder. Dividing by 100: last 2 digits are the remainder. By 1,000: last 3 digits are the remainder.",
      "To estimate quotients, round a 2+ digit divisor to its highest place, then pick a nearby compatible dividend that divides evenly.",
    ),
    exampleTitle = "Example: 637 ÷ 8",
    exampleBody = "640 is compatible with 8 (64 ÷ 8 = 8), so 637 ÷ 8 ≈ 640 ÷ 8 = 80.",
  )

  private val pmdasLesson = MiniLesson(
    heading = "Word Problems & PMDAS",
    rules = listOf(
      "P: Solve inside Parentheses first.",
      "MD: Multiply and Divide in order from LEFT to RIGHT.",
      "AS: Add and Subtract in order from LEFT to RIGHT.",
    ),
    exampleTitle = "Example: 25 × 23 + 6,675 ÷ 3",
    exampleBody = "25×23 = 575, 6,675÷3 = 2,225, then 575 + 2,225 = 2,800.",
  )

  private val anglesLesson = MiniLesson(
    heading = "Naming & Classifying Angles",
    rules = listOf(
      "An angle is formed by two rays meeting at a vertex.",
      "Name an angle using 3 letters (vertex in the middle, e.g. ∠CHE), its vertex (∠H) if single, or its angle number (∠1).",
      "Acute < 90°, Right = 90° (square corner), Obtuse > 90° and < 180°.",
    ),
    exampleTitle = "Example: Angle CHE with vertex H",
    exampleBody = "You can name it ∠CHE, ∠EHC, or ∠H.",
  )

  val worksheets: List<WorksheetInfo> = listOf(
    WorksheetInfo(1, "Visualizing Numbers (Discs)", "WS 1", LearningWorld.G5_NUMBERS, 48, "Count the 50,000 to 1 discs and write the total number.", discLesson, AralGrade.GRADE_5),
    WorksheetInfo(2, "Building & Reading 5-Digit Discs", "WS 2", LearningWorld.G5_NUMBERS, 56, "Build each 5-digit number with discs, then read disc collections.", discLesson, AralGrade.GRADE_5),
    WorksheetInfo(3, "Value & Place Value", "WS 3", LearningWorld.G5_NUMBERS, 60, "Identify both the place value name and the value of the target digit.", placeValueLesson, AralGrade.GRADE_5),
    WorksheetInfo(4, "Value by Place Position", "WS 4", LearningWorld.G5_NUMBERS, 46, "Give the value of the digit corresponding to the given place value.", placeValueLesson, AralGrade.GRADE_5),
    WorksheetInfo(5, "Place Value Drill (to 100,000)", "WS 5", LearningWorld.G5_NUMBERS, 50, "Find the value of the digit in the given place value position.", placeValueLesson, AralGrade.GRADE_5),
    WorksheetInfo(6, "Numbers in Words & Numerals", "WS 6", LearningWorld.G5_NUMBERS, 35, "Write numerals in words and worded numbers back in digits.", wordsCompareLesson, AralGrade.GRADE_5),
    WorksheetInfo(7, "Comparing Whole Numbers", "WS 7", LearningWorld.G5_NUMBERS, 40, "Compare worded and digit numbers using <, >, or =.", wordsCompareLesson, AralGrade.GRADE_5),
    WorksheetInfo(8, "Rounding to Thousands & 10-Thousands", "WS 8", LearningWorld.G5_NUMBERS, 50, "Round each number to the indicated place value.", roundingOrderLesson, AralGrade.GRADE_5),
    WorksheetInfo(9, "Rounding Mastery Drill", "WS 9", LearningWorld.G5_NUMBERS, 75, "Round 75 numbers to thousands or ten thousands.", roundingOrderLesson, AralGrade.GRADE_5),
    WorksheetInfo(10, "Ordering Numbers Set A", "WS 10", LearningWorld.G5_NUMBERS, 52, "Arrange 5 numbers in increasing or decreasing order.", roundingOrderLesson, AralGrade.GRADE_5),
    WorksheetInfo(11, "Ordering Numbers Set B", "WS 11", LearningWorld.G5_NUMBERS, 52, "Order another 52 sets of 5-digit numbers cleanly.", roundingOrderLesson, AralGrade.GRADE_5),

    WorksheetInfo(12, "Multi-Digit Multiplication", "WS 12", LearningWorld.G5_MULTIPLY, 32, "Multiply 2-digit and 3-digit numbers accurately.", multiplyLesson, AralGrade.GRADE_5),
    WorksheetInfo(13, "Multiplication & Word Stories", "WS 13", LearningWorld.G5_MULTIPLY, 25, "Solve multi-digit products and real-life Philippine math stories.", multiplyLesson, AralGrade.GRADE_5),
    WorksheetInfo(14, "Estimating Products", "WS 14", LearningWorld.G5_MULTIPLY, 46, "Round each factor to its highest place value, then multiply.", multiplyLesson, AralGrade.GRADE_5),
    WorksheetInfo(15, "Estimating Products & Problems", "WS 15", LearningWorld.G5_MULTIPLY, 36, "Estimate products and solve estimation word problems.", multiplyLesson, AralGrade.GRADE_5),
    WorksheetInfo(16, "Mental Multiplication Set A", "WS 16", LearningWorld.G5_MULTIPLY, 30, "Multiply mentally and pick the smart strategy used.", multiplyLesson, AralGrade.GRADE_5),
    WorksheetInfo(17, "Mental Multiplication Set B", "WS 17", LearningWorld.G5_MULTIPLY, 20, "Multiply 2-digit numbers mentally with strategy selection.", multiplyLesson, AralGrade.GRADE_5),

    WorksheetInfo(18, "Long Division Set A", "WS 18", LearningWorld.G5_DIVIDE, 16, "Divide by 1-digit and 2-digit divisors (include remainders if any).", divideLesson, AralGrade.GRADE_5),
    WorksheetInfo(19, "Long Division Set B", "WS 19", LearningWorld.G5_DIVIDE, 16, "Solve 16 multi-digit division equations with remainders.", divideLesson, AralGrade.GRADE_5),
    WorksheetInfo(20, "Divide Mentally by 10, 100, 1,000 A", "WS 20", LearningWorld.G5_DIVIDE, 46, "Use the quick digit-split trick to find quotient and remainder.", divideLesson, AralGrade.GRADE_5),
    WorksheetInfo(21, "Divide Mentally by 10, 100, 1,000 B", "WS 21", LearningWorld.G5_DIVIDE, 70, "Master mental division by 10, 100, and 1,000 across 70 items.", divideLesson, AralGrade.GRADE_5),
    WorksheetInfo(22, "Estimating Quotients Set A", "WS 22", LearningWorld.G5_DIVIDE, 45, "Pick the closest compatible numbers and give the estimated quotient.", divideLesson, AralGrade.GRADE_5),
    WorksheetInfo(23, "Estimating Quotients Set B", "WS 23", LearningWorld.G5_DIVIDE, 50, "Choose compatible numbers and estimate quotients mentally.", divideLesson, AralGrade.GRADE_5),
    WorksheetInfo(24, "Division Word Problems", "WS 24", LearningWorld.G5_DIVIDE, 16, "Solve equal-sharing division word problems.", divideLesson, AralGrade.GRADE_5),
    WorksheetInfo(25, "Estimating Quotient Word Problems", "WS 25", LearningWorld.G5_DIVIDE, 20, "Estimate answers to sinigang, balut, travel, and school stories.", divideLesson, AralGrade.GRADE_5),

    WorksheetInfo(26, "Multi-Step Word Problems Set A", "WS 26", LearningWorld.G5_PROBLEM_OPS, 30, "Read carefully and solve multi-step problems with +, −, ×, ÷.", pmdasLesson, AralGrade.GRADE_5),
    WorksheetInfo(27, "Multi-Step Word Problems Set B", "WS 27", LearningWorld.G5_PROBLEM_OPS, 30, "Tackle 30 multi-step Philippine word problems.", pmdasLesson, AralGrade.GRADE_5),
    WorksheetInfo(28, "Order of Operations (PMDAS) Set A", "WS 28", LearningWorld.G5_PROBLEM_OPS, 32, "Follow PMDAS (Parentheses, Multiply/Divide L→R, Add/Subtract L→R).", pmdasLesson, AralGrade.GRADE_5),
    WorksheetInfo(29, "Order of Operations & Word Expressions", "WS 29", LearningWorld.G5_PROBLEM_OPS, 32, "Evaluate PMDAS equations and translated word expressions.", pmdasLesson, AralGrade.GRADE_5),

    WorksheetInfo(30, "Naming Angles", "WS 30", LearningWorld.G5_ANGLES, 26, "Name each angle using 3 letters, its vertex letter, or its number.", anglesLesson, AralGrade.GRADE_5),
    WorksheetInfo(31, "Kinds of Angles", "WS 31", LearningWorld.G5_ANGLES, 27, "Name angles in KERI, classify acute/right/obtuse, and explore WYUFMC.", anglesLesson, AralGrade.GRADE_5),
    WorksheetInfo(32, "Angles in Intersecting Figures", "WS 32", LearningWorld.G5_ANGLES, 36, "Find acute, obtuse, and right angles in four geometric diagrams.", anglesLesson, AralGrade.GRADE_5),
  )
}
