package com.example.data

import com.example.model.AralGrade
import com.example.model.LearningWorld
import com.example.model.MiniLesson
import com.example.model.WorksheetInfo

object Grade6Catalog {

  private val divisibilityLesson = MiniLesson(
    heading = "Divisibility Rules (2–12)",
    rules = listOf(
      "2: Last digit is even (0,2,4,6,8). 5: Ends in 0 or 5. 10: Ends in 0.",
      "3: Sum of digits ÷ 3. 9: Sum of digits ÷ 9. 6: Divisible by BOTH 2 and 3.",
      "4: Last 2 digits ÷ 4. 8: Last 3 digits ÷ 8. 12: Divisible by BOTH 3 and 4.",
      "11: Difference between alternating digit sums is 0 or a multiple of 11.",
    ),
    exampleTitle = "Example: Is 5,136 divisible by 12?",
    exampleBody = "Sum = 5+1+3+6 = 15 (÷3) and last two digits 36 (÷4) → Yes, divisible by 12!",
  )

  private val pmdasLesson = MiniLesson(
    heading = "PMDAS (Order of Operations)",
    rules = listOf(
      "P: Always evaluate inside Parentheses ( ) first.",
      "MD: Multiply and Divide in order from LEFT to RIGHT.",
      "AS: Add and Subtract in order from LEFT to RIGHT.",
    ),
    exampleTitle = "Example: 50 ÷ (200 ÷ 4)",
    exampleBody = "First (200 ÷ 4) = 50, then 50 ÷ 50 = 1.",
  )

  private val gcfLcmLesson = MiniLesson(
    heading = "GCF & LCM Strategies",
    rules = listOf(
      "Continuous Division: Divide by common prime factors until quotients are relatively prime.",
      "Formula Shortcut: LCM = (N₁ × N₂) ÷ GCF.",
      "Factor Rule: If the smaller number divides the larger number, GCF = smaller, LCM = larger!",
    ),
    exampleTitle = "Example: 14 and 35",
    exampleBody = "Product = 490, GCF = 7 → LCM = 490 ÷ 7 = 70.",
  )

  private val fractionMultLesson = MiniLesson(
    heading = "Multiplying Fractions & Mixed Numbers",
    rules = listOf(
      "Multiply numerators together and denominators together; simplify to lowest terms.",
      "Cross-cancel common factors between any numerator and denominator before multiplying.",
      "For mixed numbers, convert to improper fractions first (e.g., 2 1/2 = 5/2).",
    ),
    exampleTitle = "Example: 2/9 × 3/4",
    exampleBody = "Cancel 2&4 (→1,2) and 3&9 (→1,3): 1/3 × 1/2 = 1/6.",
  )

  private val fractionDivLesson = MiniLesson(
    heading = "Reciprocals & Dividing Fractions",
    rules = listOf(
      "Reciprocal: Flip numerator and denominator (for mixed numbers, make improper first).",
      "Keep the dividend, Change ÷ to ×, and Flip the divisor to its reciprocal.",
      "Convert whole numbers to n/1 before flipping or multiplying.",
    ),
    exampleTitle = "Example: 3/4 ÷ 2/5",
    exampleBody = "3/4 × 5/2 = 15/8 = 1 7/8.",
  )

  private val polygonLesson = MiniLesson(
    heading = "Polygons: Names & Regular vs Irregular",
    rules = listOf(
      "4: Quadrilateral, 5: Pentagon, 6: Hexagon, 7: Heptagon, 8: Octagon.",
      "9: Nonagon, 10: Decagon, 11: Hendecagon, 12: Dodecagon.",
      "Regular polygon: ALL sides equal AND ALL angles equal. Otherwise Irregular!",
    ),
    exampleTitle = "Example: Regular Hexagon vs Star",
    exampleBody = "A 6-sided honeycomb hexagon is Regular; a 5-point star has inward corners so it is Irregular.",
  )

  private val timeLesson = MiniLesson(
    heading = "12-Hour & 24-Hour Clock Format",
    rules = listOf(
      "12:00 AM–12:59 AM → 00:00–00:59. 1:00 AM–11:59 AM → 01:00–11:59.",
      "12:00 PM–12:59 PM → 12:00–12:59.",
      "1:00 PM–11:59 PM → Add 12 hours (e.g., 4:45 PM → 16:45).",
    ),
    exampleTitle = "Example: 1950 in 12-hour",
    exampleBody = "19 − 12 = 7 → 7:50 PM.",
  )

  private val areaVolumeLesson = MiniLesson(
    heading = "Area & Volume Formulas",
    rules = listOf(
      "Triangle: A = (b × h) ÷ 2.",
      "Trapezoid: A = ((b₁ + b₂) × h) ÷ 2. Parallelogram: A = b × h.",
      "Rectangular Prism: V = l × w × h. Cube: V = s³ (s × s × s).",
    ),
    exampleTitle = "Example: Trapezoid b₁=5, b₂=8, h=3",
    exampleBody = "((5 + 8) × 3) ÷ 2 = 39 ÷ 2 = 19.5 cm².",
  )

  private val tempLesson = MiniLesson(
    heading = "Reading & Setting Thermometers (°C)",
    rules = listOf(
      "Above 0 °C values are positive (warm/hot); below 0 °C values are negative (freezing/cold).",
      "On mercury scales, major marks count by 10s with halfway ticks at 5s.",
      "Include the minus sign (−) for temperatures below zero.",
    ),
    exampleTitle = "Example: -10 °C vs 37.2 °C",
    exampleBody = "-10 °C is 10 degrees below zero; 37.2 °C is normal body temperature.",
  )

  val worksheets: List<WorksheetInfo> = listOf(
    WorksheetInfo(1, "Divisibility (2, 3, 5, 9, 10)", "WS 1", LearningWorld.G6_DIVISIBILITY, 35, "Check all divisors that divide each number, then fill in the smallest missing digit.", divisibilityLesson, AralGrade.GRADE_6),
    WorksheetInfo(2, "Divisibility (4, 6, 8 & Mixed)", "WS 2", LearningWorld.G6_DIVISIBILITY, 30, "Check all boxes (4, 6, 8 and 2–10) by which each number is divisible.", divisibilityLesson, AralGrade.GRADE_6),
    WorksheetInfo(3, "Divisibility Rules & Proof", "WS 3", LearningWorld.G6_DIVISIBILITY, 32, "Decide Yes/No for divisibility, then choose the rule justification.", divisibilityLesson, AralGrade.GRADE_6),
    WorksheetInfo(4, "Divisibility Mastery Check", "WS 4", LearningWorld.G6_DIVISIBILITY, 30, "Answer Yes/No, list all divisors from 2–12, and complete the divisibility grid.", divisibilityLesson, AralGrade.GRADE_6),
    WorksheetInfo(5, "PMDAS Order of Operations 1", "WS 5", LearningWorld.G6_DIVISIBILITY, 40, "Evaluate expressions using PMDAS (Parentheses first).", pmdasLesson, AralGrade.GRADE_6),
    WorksheetInfo(6, "PMDAS Order of Operations 2", "WS 6", LearningWorld.G6_DIVISIBILITY, 40, "Evaluate multi-step division, multiplication, addition, and subtraction.", pmdasLesson, AralGrade.GRADE_6),
    WorksheetInfo(7, "PMDAS Order of Operations 3", "WS 7", LearningWorld.G6_DIVISIBILITY, 40, "Solve expressions with parentheses accurately using PMDAS.", pmdasLesson, AralGrade.GRADE_6),
    WorksheetInfo(8, "PMDAS Order of Operations 4", "WS 8", LearningWorld.G6_DIVISIBILITY, 40, "Master PMDAS order of operations across 40 mixed equations.", pmdasLesson, AralGrade.GRADE_6),

    WorksheetInfo(9, "GCF by Continuous Division", "WS 9", LearningWorld.G6_GCF_LCM, 20, "Find the Greatest Common Factor (GCF) using continuous division.", gcfLcmLesson, AralGrade.GRADE_6),
    WorksheetInfo(10, "LCM of 3 Numbers", "WS 10", LearningWorld.G6_GCF_LCM, 18, "Find the Least Common Multiple (LCM) of three numbers.", gcfLcmLesson, AralGrade.GRADE_6),
    WorksheetInfo(11, "GCF & LCM Formula Table", "WS 11", LearningWorld.G6_GCF_LCM, 20, "Use N₁ × N₂ ÷ GCF = LCM to solve for LCM, Product, and GCF.", gcfLcmLesson, AralGrade.GRADE_6),
    WorksheetInfo(12, "GCF & LCM Factor Shortcut", "WS 12", LearningWorld.G6_GCF_LCM, 22, "Check when the smaller number is a factor of the larger number to get GCF & LCM.", gcfLcmLesson, AralGrade.GRADE_6),
    WorksheetInfo(13, "GCF & LCM + Word Stories", "WS 13", LearningWorld.G6_GCF_LCM, 43, "Find GCF and LCM simultaneously and solve real-life word problems.", gcfLcmLesson, AralGrade.GRADE_6),

    WorksheetInfo(14, "Fraction Area Models (×)", "WS 14", LearningWorld.G6_FRACTIONS, 8, "Explore the shaded grid area model and compute the product of fractions.", fractionMultLesson, AralGrade.GRADE_6),
    WorksheetInfo(15, "Multiplying Fractions & Cancel", "WS 15", LearningWorld.G6_FRACTIONS, 30, "Multiply fractions and use cross cancellation; write answers in lowest terms.", fractionMultLesson, AralGrade.GRADE_6),
    WorksheetInfo(16, "Whole & Mixed × Fractions", "WS 16", LearningWorld.G6_FRACTIONS, 30, "Multiply whole numbers and mixed numbers; reduce to lowest terms.", fractionMultLesson, AralGrade.GRADE_6),
    WorksheetInfo(17, "Fraction Mult Mastery & Stories", "WS 17", LearningWorld.G6_FRACTIONS, 30, "Multiply fractions/mixed numbers and solve word problems.", fractionMultLesson, AralGrade.GRADE_6),
    WorksheetInfo(18, "Multiplying 3 Fractions", "WS 18", LearningWorld.G6_FRACTIONS, 25, "Multiply three fractions and mixed numbers using simultaneous cancellation.", fractionMultLesson, AralGrade.GRADE_6),
    WorksheetInfo(19, "Dividing Fractions Models (÷)", "WS 19", LearningWorld.G6_FRACTIONS, 8, "Use the fraction grid model to visualize and solve fraction division.", fractionDivLesson, AralGrade.GRADE_6),
    WorksheetInfo(20, "Reciprocals & Fraction Division", "WS 20", LearningWorld.G6_FRACTIONS, 30, "Find reciprocals of fractions, whole & mixed numbers, then divide fractions.", fractionDivLesson, AralGrade.GRADE_6),
    WorksheetInfo(21, "Dividing Fractions & Wholes 1", "WS 21", LearningWorld.G6_FRACTIONS, 30, "Divide fractions by whole numbers and whole numbers by fractions.", fractionDivLesson, AralGrade.GRADE_6),
    WorksheetInfo(22, "Dividing Fractions & Wholes 2", "WS 22", LearningWorld.G6_FRACTIONS, 30, "Find quotients of whole numbers and fractions in lowest terms.", fractionDivLesson, AralGrade.GRADE_6),
    WorksheetInfo(23, "Fraction Division + Stories", "WS 23", LearningWorld.G6_FRACTIONS, 30, "Divide fractions and solve multi-context word problems.", fractionDivLesson, AralGrade.GRADE_6),
    WorksheetInfo(24, "Dividing 3 Fractions", "WS 24", LearningWorld.G6_FRACTIONS, 25, "Convert all divisors to reciprocals and divide three numbers.", fractionDivLesson, AralGrade.GRADE_6),

    WorksheetInfo(25, "Polygons & Number of Sides", "WS 25", LearningWorld.G6_GEO_TIME, 18, "Complete each polygon row: name, figure, and number of sides (4–12).", polygonLesson, AralGrade.GRADE_6),
    WorksheetInfo(26, "Regular vs Irregular Polygons", "WS 26", LearningWorld.G6_GEO_TIME, 40, "Tell whether each given polygon figure is Regular or Irregular.", polygonLesson, AralGrade.GRADE_6),
    WorksheetInfo(27, "12-Hour to 24-Hour Table", "WS 27", LearningWorld.G6_GEO_TIME, 24, "Convert hourly 12-hour times from 12:00 AM to 11:00 PM into 24-hour format.", timeLesson, AralGrade.GRADE_6),
    WorksheetInfo(28, "Reading Clocks in 24-Hr Format", "WS 28", LearningWorld.G6_GEO_TIME, 16, "Read each analog clock with AM/PM and write the time in 24-hour format.", timeLesson, AralGrade.GRADE_6),
    WorksheetInfo(29, "12-Hour ↔ 24-Hour Time", "WS 29", LearningWorld.G6_GEO_TIME, 48, "Convert times between 12-hour format and 24-hour format.", timeLesson, AralGrade.GRADE_6),

    WorksheetInfo(30, "Area of a Triangle", "WS 30", LearningWorld.G6_MEASURE, 26, "Compute the area of each triangle using A = 1/2 × base × height.", areaVolumeLesson, AralGrade.GRADE_6),
    WorksheetInfo(31, "Area: Trapezoid & Parallelogram", "WS 31", LearningWorld.G6_MEASURE, 30, "Find the area of trapezoids and parallelograms from figures and tables.", areaVolumeLesson, AralGrade.GRADE_6),
    WorksheetInfo(32, "Volume of Prisms & Cubes", "WS 32", LearningWorld.G6_MEASURE, 40, "Identify dimensions and solve for the volume of rectangular prisms and cubes.", areaVolumeLesson, AralGrade.GRADE_6),
    WorksheetInfo(33, "Read Mercury Thermometer (°C)", "WS 33", LearningWorld.G6_MEASURE, 16, "Read the Celsius temperature shown on each mercury thermometer.", tempLesson, AralGrade.GRADE_6),
    WorksheetInfo(34, "Set Thermometer Level (°C)", "WS 34", LearningWorld.G6_MEASURE, 16, "Adjust the mercury level on the thermometer to match the target °C.", tempLesson, AralGrade.GRADE_6),
    WorksheetInfo(35, "Digital Thermometer (°C)", "WS 35", LearningWorld.G6_MEASURE, 10, "Read digital thermometer displays and enter target Celsius temperatures.", tempLesson, AralGrade.GRADE_6),
  )
}
