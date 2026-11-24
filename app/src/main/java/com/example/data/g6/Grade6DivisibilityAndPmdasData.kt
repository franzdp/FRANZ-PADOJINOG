package com.example.data.g6

import com.example.model.DivisibilityCheckProblem
import com.example.model.DivisibilityRuleJudgeProblem
import com.example.model.MathProblem
import com.example.model.SmallestDigitDivisibilityProblem

object Grade6DivisibilityAndPmdasData {

  private val div5Cols = listOf(2, 3, 5, 9, 10)
  private val div468Cols = listOf(4, 6, 8)
  private val div8Cols = listOf(2, 3, 4, 5, 6, 8, 9, 10)
  private val div10Cols = listOf(2, 3, 4, 5, 6, 8, 9, 10, 11, 12)

  private fun divCheck(item: Int, n: Int, cols: List<Int>): DivisibilityCheckProblem {
    val valid = cols.filter { n % it == 0 }
    return DivisibilityCheckProblem(
      itemNumber = item,
      number = n,
      divisorsToCheck = cols,
      validDivisors = valid,
      hint = "Test each divisor rule on ${"%,d".format(n)}: check last digit(s), digit sum, or alternating sums.",
    )
  }

  private fun smallDigit(item: Int, pattern: String, div: Int, ans: Int, hint: String) =
    SmallestDigitDivisibilityProblem(item, pattern, div, ans, hint)

  private fun ruleYesNo(item: Int, n: Int, d: Int, yes: Boolean, hint: String) =
    DivisibilityRuleJudgeProblem(
      itemNumber = item,
      firstNumber = n,
      secondNumber = d,
      isYesNoStep = true,
      choiceOptions = listOf("Yes", "No"),
      correctChoice = if (yes) "Yes" else "No",
      hint = hint,
    )

  private fun ruleJustify(
    item: Int,
    n: Int,
    d: Int,
    correct: String,
    distractors: List<String>,
    hint: String,
  ) = DivisibilityRuleJudgeProblem(
    itemNumber = item,
    firstNumber = n,
    secondNumber = d,
    isYesNoStep = false,
    choiceOptions = (listOf(correct) + distractors).sorted(),
    correctChoice = correct,
    hint = hint,
  )

  private fun pmdas(item: Int, expr: String, ans: Int) =
    MathProblem.OrderOfOpsProblem(
      itemNumber = item,
      expression = expr,
      expectedValue = ans.toDouble(),
      expectedDisplay = ans.toString(),
      hint = "Solve inside ( ) first, then × or ÷ from left to right, then + or − from left to right.",
    )

  val ws1: List<MathProblem> = listOf(
    divCheck(1, 245, div5Cols), divCheck(2, 240, div5Cols), divCheck(3, 256, div5Cols),
    divCheck(4, 432, div5Cols), divCheck(5, 454, div5Cols), divCheck(6, 455, div5Cols),
    divCheck(7, 480, div5Cols), divCheck(8, 543, div5Cols), divCheck(9, 610, div5Cols),
    divCheck(10, 612, div5Cols), divCheck(11, 630, div5Cols), divCheck(12, 662, div5Cols),
    divCheck(13, 729, div5Cols), divCheck(14, 750, div5Cols), divCheck(15, 768, div5Cols),
    divCheck(16, 780, div5Cols), divCheck(17, 850, div5Cols), divCheck(18, 998, div5Cols),
    divCheck(19, 1065, div5Cols), divCheck(20, 2150, div5Cols),
    smallDigit(21, "2_4", 9, 3, "Sum of all digits (including the blank) must be a multiple of 9. Pick the smallest digit 0–9."),
    smallDigit(22, "41_", 2, 0, "A number is divisible by 2 if its last digit is even (0, 2, 4, 6, or 8). Pick the smallest!"),
    smallDigit(23, "6_7", 3, 2, "Add the known digits (6 + 7) and find the smallest digit 0–9 so the total sum is a multiple of 3."),
    smallDigit(24, "74_", 10, 0, "A number divisible by 10 must end in what digit?"),
    smallDigit(25, "_77", 9, 4, "Add 7 + 7, then pick the smallest digit (1–9 since it's the leading digit) to reach a multiple of 9."),
    smallDigit(26, "16_", 5, 0, "Numbers ending in 0 or 5 are divisible by 5 — pick the smallest ending digit!"),
    smallDigit(27, "_85", 3, 2, "Add 8 + 5, then pick the smallest nonzero digit (1–9) so the sum is a multiple of 3."),
    smallDigit(28, "72_", 9, 0, "Add 7 + 2 first: if it is already a multiple of 9, can the last digit be 0?"),
    smallDigit(29, "5,23_", 10, 0, "What single ending digit makes a number divisible by 10?"),
    smallDigit(30, "7,67_", 5, 0, "Numbers ending in 0 or 5 are divisible by 5 — pick the smallest valid digit."),
    smallDigit(31, "4,6_7", 9, 1, "Add 4 + 6 + 7, then find the smallest digit 0–9 that brings the sum to a multiple of 9."),
    smallDigit(32, "95_", 5, 0, "Numbers ending in 0 or 5 are divisible by 5 — pick the smallest valid digit."),
    smallDigit(33, "_615", 3, 3, "Add 6 + 1 + 5 first. Remember the leading digit cannot be 0, so pick the smallest digit ≥ 1 so the total sum is divisible by 3!"),
    smallDigit(34, "35_", 9, 1, "Add 3 + 5, then find the digit 0–9 that brings the sum to a multiple of 9."),
    smallDigit(35, "3,34_", 10, 0, "What single ending digit makes any whole number divisible by 10?"),
  )

  val ws2: List<MathProblem> = listOf(
    divCheck(1, 648, div468Cols), divCheck(2, 924, div468Cols), divCheck(3, 216, div468Cols),
    divCheck(4, 460, div468Cols), divCheck(5, 618, div468Cols), divCheck(6, 248, div468Cols),
    divCheck(7, 6816, div468Cols), divCheck(8, 6424, div468Cols), divCheck(9, 9428, div468Cols),
    divCheck(10, 6864, div468Cols), divCheck(11, 9232, div468Cols), divCheck(12, 4656, div468Cols),
    divCheck(13, 7344, div468Cols), divCheck(14, 9462, div468Cols), divCheck(15, 1284, div468Cols),
    divCheck(16, 5312, div8Cols), divCheck(17, 1492, div8Cols), divCheck(18, 4272, div8Cols),
    divCheck(19, 2725, div8Cols), divCheck(20, 9963, div8Cols), divCheck(21, 1586, div8Cols),
    divCheck(22, 4686, div8Cols), divCheck(23, 8714, div8Cols), divCheck(24, 7089, div8Cols),
    divCheck(25, 5930, div8Cols), divCheck(26, 2967, div8Cols), divCheck(27, 5680, div8Cols),
    divCheck(28, 6657, div8Cols), divCheck(29, 7260, div8Cols), divCheck(30, 9688, div8Cols),
  )

  val ws3: List<MathProblem> = listOf(
    // 1-7 Yes/No
    ruleYesNo(1, 160, 2, true, "Rule for 2: check whether the last digit is even (0, 2, 4, 6, or 8)."),
    ruleYesNo(2, 297, 3, true, "Rule for 3: add all the digits and check if the sum is a multiple of 3."),
    ruleYesNo(3, 590, 5, true, "Rule for 5: check whether the last digit is 0 or 5."),
    ruleYesNo(4, 876, 6, true, "Rule for 6: the number must pass BOTH the rule for 2 (even) and the rule for 3 (digit sum ÷ 3)."),
    ruleYesNo(5, 595, 12, false, "Rule for 12: the number must pass BOTH the rule for 3 (digit sum ÷ 3) and the rule for 4 (last two digits ÷ 4)."),
    ruleYesNo(6, 215, 4, false, "Rule for 4: check whether the last two digits form a multiple of 4."),
    ruleYesNo(7, 5634, 10, false, "Rule for 10: check whether the last digit is 0."),
    // 8-14 Justifications
    ruleJustify(8, 160, 2, "Last digit is 0 (even), so divisible by 2", listOf("Sum of digits = 7, divisible by 2", "Ends in 0, so divisible by 3"), "Divisibility by 2 checks if the last digit is even (0, 2, 4, 6, or 8)."),
    ruleJustify(9, 297, 3, "2 + 9 + 7 = 18, which is divisible by 3", listOf("Ends in 7, which is divisible by 3", "Last two digits 97 are divisible by 3"), "Add all the digits of 297 and check if the sum is a multiple of 3."),
    ruleJustify(10, 590, 5, "Last digit is 0 (ends in 0 or 5)", listOf("5 + 9 + 0 = 14, divisible by 5", "Last two digits 90 ÷ 4"), "Divisibility by 5 checks the ones (last) digit."),
    ruleJustify(11, 876, 6, "Even (÷2) AND 8+7+6=21 (÷3)", listOf("Ends in 6, so automatically ÷6", "876 ÷ 4 leaves no remainder"), "Divisible by 6 requires passing BOTH the 2 rule and the 3 rule."),
    ruleJustify(12, 595, 12, "Not divisible by 3 (5+9+5=19) and 95 not ÷4", listOf("5 + 9 + 5 = 19, divisible by 12", "Ends in 5, so divisible by 12"), "Divisible by 12 requires passing BOTH the 3 rule and the 4 rule."),
    ruleJustify(13, 215, 4, "Last two digits 15 are not divisible by 4", listOf("2 + 1 + 5 = 8, so divisible by 4", "Ends in 5, so divisible by 4"), "Divisibility by 4 checks only the last two digits."),
    ruleJustify(14, 5634, 10, "Last digit is 4, not 0", listOf("5 + 6 + 3 + 4 = 18, divisible by 10", "Last two digits 34 ÷ 10"), "Divisible by 10 checks the last digit only."),
    // 15-23 Yes/No
    ruleYesNo(15, 900, 3, true, "Rule for 3: add the digits of 900 and check if the sum is divisible by 3."),
    ruleYesNo(16, 4016, 8, true, "Rule for 8: check whether the last three digits form a multiple of 8."),
    ruleYesNo(17, 2079, 11, true, "Rule for 11: add alternating digits (2+7 and 0+9) and check if their difference is 0 or a multiple of 11."),
    ruleYesNo(18, 5935, 6, false, "Rule for 6: check BOTH the rule for 2 (even last digit) and the rule for 3 (digit sum)."),
    ruleYesNo(19, 4850, 10, true, "Rule for 10: check whether the last digit is 0."),
    ruleYesNo(20, 5634, 6, true, "Rule for 6: check if 5,634 is even AND if its digit sum is divisible by 3."),
    ruleYesNo(21, 2105, 5, true, "Rule for 5: check whether the last digit is 0 or 5."),
    ruleYesNo(22, 5136, 12, true, "Rule for 12: check if the digit sum is divisible by 3 AND the last two digits are divisible by 4."),
    ruleYesNo(23, 2104, 2, true, "Rule for 2: check whether the last digit is even (0, 2, 4, 6, or 8)."),
    // 24-32 Justifications
    ruleJustify(24, 900, 3, "9 + 0 + 0 = 9, divisible by 3", listOf("Ends in 00, so not divisible by 3", "9 + 0 = 9, divisible by 4"), "Divisibility by 3 uses the sum of all digits."),
    ruleJustify(25, 4016, 8, "Last 3 digits 016 (16) is divisible by 8", listOf("4 + 0 + 1 + 6 = 11, divisible by 8", "Last digit is 6, so divisible by 8"), "Divisibility by 8 checks the last 3 digits."),
    ruleJustify(26, 2079, 11, "Alternating sums (2+7) − (0+9) = 0", listOf("2 + 0 + 7 + 9 = 18, divisible by 11", "Last two digits 79 ÷ 11"), "Apply the alternating-digit sum rule for 11."),
    ruleJustify(27, 5935, 6, "Last digit 5 is odd (not ÷2) and sum 22 not ÷3", listOf("Ends in 5, so divisible by 6", "Last two digits 35 ÷ 6"), "Divisibility by 6 requires passing both the 2 rule and the 3 rule."),
    ruleJustify(28, 4850, 10, "Ends in 0, so divisible by 10", listOf("4 + 8 + 5 + 0 = 17, divisible by 10", "Last two digits 50 ÷ 4"), "Divisible by 10 checks the last digit only."),
    ruleJustify(29, 5634, 6, "Even (ends in 4) AND 5+6+3+4=18 (÷3)", listOf("Ends in 34, so not divisible by 6", "Odd last digit fails rule for 2"), "Test both the rule for 2 and the rule for 3 on 5,634."),
    ruleJustify(30, 2105, 5, "Ends in 5 (ends in 0 or 5)", listOf("2 + 1 + 0 + 5 = 8, divisible by 5", "Ends in 05, divisible by 10"), "Divisibility by 5 checks the ones digit (0 or 5)."),
    ruleJustify(31, 5136, 12, "Sum = 15 (÷3) AND last two digits 36 (÷4)", listOf("Ends in 6, so divisible by 12", "Sum = 15 (÷3) and ends in 36 (÷9)"), "Divisibility by 12 requires passing both the 3 rule and the 4 rule."),
    ruleJustify(32, 2104, 2, "Last digit is 4 (even)", listOf("2 + 1 + 0 + 4 = 7, divisible by 2", "Ends in 04, divisible by 5"), "Divisibility by 2 checks whether the last digit is even."),
  )

  val ws4: List<MathProblem> = listOf(
    ruleYesNo(1, 265, 9, false, "Rule for 9: add all digits of 265 and check if the sum is a multiple of 9."),
    ruleYesNo(2, 420, 2, true, "Rule for 2: check whether the last digit of 420 is even."),
    ruleYesNo(3, 505, 11, false, "Rule for 11: compare the alternating digit sums ((5 + 5) and 0) — is their difference 0 or a multiple of 11?"),
    ruleYesNo(4, 111, 3, true, "Rule for 3: add the digits of 111 and check if the sum is divisible by 3."),
    ruleYesNo(5, 696, 5, false, "Rule for 5: check whether the last digit is 0 or 5."),
    ruleYesNo(6, 1600, 4, true, "Rule for 4: check whether the last two digits form a number divisible by 4 (00 counts as divisible by 4!)."),
    ruleYesNo(7, 2228, 8, false, "Rule for 8: check whether the last three digits (228) divide evenly by 8."),
    ruleYesNo(8, 4132, 12, false, "Rule for 12: check if 4,132 passes BOTH the 3 rule (digit sum) and the 4 rule (last two digits)."),
    ruleYesNo(9, 5640, 6, true, "Rule for 6: check if 5,640 passes BOTH the 2 rule (even) and the 3 rule (digit sum)."),
    ruleYesNo(10, 7517, 10, false, "Rule for 10: check whether the last digit is 0."),
    // 11-20 all possible divisors
    divCheck(11, 800, div10Cols), divCheck(12, 878, div10Cols), divCheck(13, 160, div10Cols),
    divCheck(14, 663, div10Cols), divCheck(15, 445, div10Cols), divCheck(16, 9999, div10Cols),
    divCheck(17, 1144, div10Cols), divCheck(18, 7326, div10Cols), divCheck(19, 7400, div10Cols),
    divCheck(20, 4215, div10Cols),
    // 21-30 matrix check
    divCheck(21, 216, div10Cols), divCheck(22, 970, div10Cols), divCheck(23, 752, div10Cols),
    divCheck(24, 459, div10Cols), divCheck(25, 760, div10Cols), divCheck(26, 9645, div10Cols),
    divCheck(27, 7653, div10Cols), divCheck(28, 5580, div10Cols), divCheck(29, 5900, div10Cols),
    divCheck(30, 9040, div10Cols),
  )

  val ws5: List<MathProblem> = listOf(
    pmdas(1, "3 × (13 × 10)", 390), pmdas(2, "6 + (10 × 14)", 146), pmdas(3, "17 × (2 × 10)", 340),
    pmdas(4, "(3 × 44) × 1", 132), pmdas(5, "3 × (1 × 14)", 42), pmdas(6, "(17 × 2) × 2", 68),
    pmdas(7, "(3 × 11) + 2", 35), pmdas(8, "(10 × 12) + 4", 124), pmdas(9, "7 × (10 × 16)", 1120),
    pmdas(10, "40 × (4 × 10)", 1600), pmdas(11, "9 × (10 × 12)", 1080), pmdas(12, "8 × (13 × 10)", 1040),
    pmdas(13, "19 × (10 × 3)", 570), pmdas(14, "(3 × 8) + 2", 26), pmdas(15, "1 + (10 × 6)", 61),
    pmdas(16, "2 × (6 ÷ 6)", 2), pmdas(17, "(7 × 5) − 2", 33), pmdas(18, "(10 − 4) + 8", 14),
    pmdas(19, "(7 + 5) + 9", 21), pmdas(20, "(10 − 10) + 4", 4), pmdas(21, "(10 + 1) + 10", 21),
    pmdas(22, "(7 × 6) − 10", 32), pmdas(23, "(8 ÷ 4) × 6", 12), pmdas(24, "(10 + 5) + 5", 20),
    pmdas(25, "5 + (7 × 8)", 61), pmdas(26, "(9 ÷ 9) × 2", 2), pmdas(27, "(4 × 10) − 9", 31),
    pmdas(28, "1 + (1 × 8)", 9), pmdas(29, "(10 + 9) + 3", 22), pmdas(30, "7 − (9 − 5)", 3),
    pmdas(31, "10 + (2 × 6)", 22), pmdas(32, "(6 ÷ 2) × 7", 21), pmdas(33, "5 + (9 ÷ 1)", 14),
    pmdas(34, "(6 × 12) − 2", 70), pmdas(35, "8 + (9 × 7)", 71), pmdas(36, "(9 × 2) − 3", 15),
    pmdas(37, "4 × (5 × 9)", 180), pmdas(38, "3 + (5 × 4)", 23), pmdas(39, "(5 × 6) + 2", 32),
    pmdas(40, "(12 × 6) − 6", 66),
  )

  val ws6: List<MathProblem> = listOf(
    pmdas(1, "(10 × 7) + 1", 71), pmdas(2, "(8 × 2) + 9", 25), pmdas(3, "3 × (9 × 2)", 54),
    pmdas(4, "4 + (3 × 6)", 22), pmdas(5, "(31 × 1) + 1", 32), pmdas(6, "(36 ÷ 6) ÷ 1", 6),
    pmdas(7, "(27 ÷ 9) ÷ 3", 1), pmdas(8, "(110 ÷ 10) ÷ 1", 11), pmdas(9, "(36 ÷ 9) ÷ 2", 2),
    pmdas(10, "(12 ÷ 4) ÷ 1", 3), pmdas(11, "(49 ÷ 7) ÷ 1", 7), pmdas(12, "(48 ÷ 8) ÷ 1", 6),
    pmdas(13, "(9 ÷ 9) ÷ 1", 1), pmdas(14, "50 ÷ (200 ÷ 4)", 1), pmdas(15, "(60 ÷ 10) ÷ 6", 1),
    pmdas(16, "(16 ÷ 4) ÷ 2", 2), pmdas(17, "(15 ÷ 5) ÷ 3", 1), pmdas(18, "(10 ÷ 5) × 10", 20),
    pmdas(19, "1 × (1 ÷ 1)", 1), pmdas(20, "(7 × 5) ÷ 1", 35), pmdas(21, "10 + (8 × 1)", 18),
    pmdas(22, "1 × (9 − 7)", 2), pmdas(23, "7 + (6 + 1)", 14), pmdas(24, "3 × (9 ÷ 3)", 9),
    pmdas(25, "(6 × 6) × 1", 36), pmdas(26, "7 × (5 × 2)", 70), pmdas(27, "(8 × 2) × 2", 32),
    pmdas(28, "6 × (5 ÷ 1)", 30), pmdas(29, "(10 × 9) − 7", 83), pmdas(30, "7 + (6 + 9)", 22),
    pmdas(31, "7 + (7 + 9)", 23), pmdas(32, "(1 − 1) + 1", 1), pmdas(33, "6 + (8 × 3)", 30),
    pmdas(34, "(3 × 3) + 9", 18), pmdas(35, "1 × (4 − 2)", 2), pmdas(36, "(2 × 3) + 10", 16),
    pmdas(37, "(6 ÷ 3) + 7", 9), pmdas(38, "(5 × 5) + 7", 32), pmdas(39, "(9 × 9) − 8", 73),
    pmdas(40, "4 + (3 × 8)", 28),
  )

  val ws7: List<MathProblem> = listOf(
    pmdas(1, "(8 × 5) − 10", 30), pmdas(2, "(9 × 9) − 8", 73), pmdas(3, "(3 × 5) + 8", 23),
    pmdas(4, "(7 × 6) + 23", 65), pmdas(5, "(18 ÷ 2) − 3", 6), pmdas(6, "(45 ÷ 5) − 3", 6),
    pmdas(7, "(8 × 5) + 5", 45), pmdas(8, "45 − (2 × 5)", 35), pmdas(9, "(2 × 10) − 5", 15),
    pmdas(10, "(8 × 3) + 2", 26), pmdas(11, "(10 × 6) + 5", 65), pmdas(12, "(24 ÷ 3) − 3", 5),
    pmdas(13, "(72 ÷ 9) − 6", 2), pmdas(14, "(5 × 9) + 0", 45), pmdas(15, "34 − (6 × 2)", 22),
    pmdas(16, "(5 × 3) − 2", 13), pmdas(17, "(9 × 3) − 1", 26), pmdas(18, "(9 × 3) − 9", 18),
    pmdas(19, "(7 × 5) + 7", 42), pmdas(20, "(6 × 4) + 4", 28), pmdas(21, "(6 × 6) × 10", 360),
    pmdas(22, "(6 × 8) ÷ 4", 12), pmdas(23, "(3 × 4) ÷ 6", 2), pmdas(24, "(8 ÷ 4) × 5", 10),
    pmdas(25, "6 × (10 − 4)", 36), pmdas(26, "9 + (5 × 5)", 34), pmdas(27, "10 + (2 × 8)", 26),
    pmdas(28, "(5 ÷ 5) × 6", 6), pmdas(29, "(8 + 9) − 7", 10), pmdas(30, "4 × (6 ÷ 2)", 12),
    pmdas(31, "(5 × 5) + 7", 32), pmdas(32, "(3 × 4) − 1", 11), pmdas(33, "(5 × 4) + 5", 25),
    pmdas(34, "1 − (10 − 9)", 0), pmdas(35, "(5 × 5) − 10", 15), pmdas(36, "10 + (6 + 9)", 25),
    pmdas(37, "(6 × 9) − 9", 45), pmdas(38, "10 + (10 ÷ 5)", 12), pmdas(39, "(2 ÷ 1) × 3", 6),
    pmdas(40, "6 × (3 × 5)", 90),
  )

  val ws8: List<MathProblem> = listOf(
    pmdas(1, "4 − (9 ÷ 9)", 3), pmdas(2, "(8 ÷ 4) + 9", 11), pmdas(3, "(10 × 3) ÷ 5", 6),
    pmdas(4, "3 × (5 × 1)", 15), pmdas(5, "(7 + 5) − 2", 10), pmdas(6, "(7 × 2) + 10", 24),
    pmdas(7, "(4 × 3) + 5", 17), pmdas(8, "(10 + 10) + 1", 21), pmdas(9, "2 + (3 × 7)", 23),
    pmdas(10, "(10 × 5) − 4", 46), pmdas(11, "(7 × 3) − 10", 11), pmdas(12, "(4 − 3) × 5", 5),
    pmdas(13, "1 × (10 × 8)", 80), pmdas(14, "(9 − 7) + 1", 3), pmdas(15, "(10 + 9) − 6", 13),
    pmdas(16, "(9 × 8) ÷ 4", 18), pmdas(17, "(9 + 7) − 1", 15), pmdas(18, "(3 × 3) + 1", 10),
    pmdas(19, "(8 × 3) ÷ 1", 24), pmdas(20, "(3 × 8) − 6", 18), pmdas(21, "(8 + 7) + 7", 22),
    pmdas(22, "(8 × 3) × 9", 216), pmdas(23, "(8 − 2) + 7", 13), pmdas(24, "(5 × 3) + 6", 21),
    pmdas(25, "(2 + 1) × 9", 27), pmdas(26, "(4 ÷ 1) ÷ 2", 2), pmdas(27, "(8 × 3) + 7", 31),
    pmdas(28, "(8 × 4) − 10", 22), pmdas(29, "(6 + 8) + 7", 21), pmdas(30, "(9 × 8) − 1", 71),
    pmdas(31, "(2 × 3) + 8", 14), pmdas(32, "1 + (9 × 5)", 46), pmdas(33, "1 + (8 × 9)", 73),
    pmdas(34, "(6 × 7) − 6", 36), pmdas(35, "1 + (6 × 2)", 13), pmdas(36, "(5 + 9) + 6", 20),
    pmdas(37, "(6 × 5) − 3", 27), pmdas(38, "2 ÷ (4 ÷ 2)", 1), pmdas(39, "(7 × 5) − 1", 34),
    pmdas(40, "(3 × 4) + 10", 22),
  )
}
