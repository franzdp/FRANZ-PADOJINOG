package com.example.data.g5

import com.example.model.MathProblem

object Grade5DivisionData {

  private fun divLong(item: Int, dividend: Int, divisor: Int) = MathProblem.DivisionProblem(
    itemNumber = item,
    dividend = dividend,
    divisor = divisor,
    quotient = dividend / divisor,
    remainder = dividend % divisor,
    showBracket = true,
    hint = "Divide step by step from left to right. Put any leftover in the Remainder box.",
  )

  private fun divMental(item: Int, dividend: Int, divisor: Int) = MathProblem.DivisionProblem(
    itemNumber = item,
    dividend = dividend,
    divisor = divisor,
    quotient = dividend / divisor,
    remainder = dividend % divisor,
    showBracket = false,
    hint = when (divisor) {
      10 -> "Dividing by 10: the last digit is the remainder; the remaining digits form the quotient."
      100 -> "Dividing by 100: the last 2 digits form the remainder; the front digits form the quotient."
      else -> "Dividing by 1,000: the last 3 digits form the remainder; the front digits form the quotient."
    },
  )

  private fun divWord(item: Int, prompt: String, dividend: Int, divisor: Int, unit: String, hint: String) =
    MathProblem.DivisionProblem(
      itemNumber = item,
      dividend = dividend,
      divisor = divisor,
      quotient = dividend / divisor,
      remainder = dividend % divisor,
      promptText = prompt,
      unitLabel = unit,
      showBracket = false,
      hint = hint,
    )

  private fun compatChoice(
    item: Int,
    dividend: Int,
    divisor: Int,
    opts: List<String>,
    correctIdx: Int,
    accepted: List<Int>,
  ) = MathProblem.CompatibleEstimateProblem(
    itemNumber = item,
    dividend = dividend,
    divisor = divisor,
    compatibleOptions = opts,
    correctCompatibleIndex = correctIdx,
    acceptedQuotients = accepted,
    primaryEstimate = accepted.first(),
    exampleCompatibleExpr = opts[correctIdx],
    hint = "Pick the compatible division expression closest to $dividend ÷ $divisor, then divide mentally.",
  )

  private fun bestEstChoice(
    item: Int,
    dividend: Int,
    divisor: Int,
    estOpts: List<Int>,
    correctValue: Int,
  ) = MathProblem.CompatibleEstimateProblem(
    itemNumber = item,
    dividend = dividend,
    divisor = divisor,
    estimatedOptions = estOpts,
    acceptedQuotients = listOf(correctValue),
    primaryEstimate = correctValue,
    hint = "Round $divisor if 2+ digits, choose a compatible dividend near $dividend, then divide.",
  )

  private fun openEst(
    item: Int,
    dividend: Int,
    divisor: Int,
    accepted: List<Int>,
    expr: String,
    prompt: String? = null,
  ) = MathProblem.CompatibleEstimateProblem(
    itemNumber = item,
    dividend = dividend,
    divisor = divisor,
    promptText = prompt,
    acceptedQuotients = accepted,
    primaryEstimate = accepted.first(),
    exampleCompatibleExpr = expr,
    hint = "Round $divisor if 2+ digits, then replace $dividend with a close compatible multiple of your rounded divisor so you can divide mentally!",
  )

  val ws18: List<MathProblem> = listOf(
    divLong(1, 554, 2), divLong(2, 1111, 11), divLong(3, 252, 4), divLong(4, 2838, 55),
    divLong(5, 3523, 26), divLong(6, 5890, 95), divLong(7, 3794, 14), divLong(8, 8410, 22),
    divLong(9, 8120, 54), divLong(10, 7960, 60), divLong(11, 6500, 25), divLong(12, 8460, 56),
    divLong(13, 236, 59), divLong(14, 9234, 38), divLong(15, 4164, 87), divLong(16, 2287, 60),
  )

  val ws19: List<MathProblem> = listOf(
    divLong(1, 350, 25), divLong(2, 1456, 56), divLong(3, 288, 9), divLong(4, 2732, 42),
    divLong(5, 562, 10), divLong(6, 8268, 81), divLong(7, 786, 12), divLong(8, 4293, 33),
    divLong(9, 9268, 68), divLong(10, 9003, 30), divLong(11, 2348, 68), divLong(12, 9244, 99),
    divLong(13, 1290, 32), divLong(14, 354, 6), divLong(15, 3168, 44), divLong(16, 5084, 82),
  )

  val ws20: List<MathProblem> = listOf(
    divMental(1, 47, 10), divMental(2, 356, 10), divMental(3, 842, 10), divMental(4, 3729, 10),
    divMental(5, 645, 100), divMental(6, 2431, 100), divMental(7, 7058, 100), divMental(8, 8410, 100),
    divMental(9, 6789, 1000), divMental(10, 9305, 1000), divMental(11, 83, 10), divMental(12, 508, 10),
    divMental(13, 7296, 10), divMental(14, 1652, 100), divMental(15, 5734, 100), divMental(16, 9876, 100),
    divMental(17, 4528, 1000), divMental(18, 6931, 1000), divMental(19, 2145, 10), divMental(20, 4378, 100),
    divMental(21, 3248, 10), divMental(22, 87, 10), divMental(23, 6540, 100), divMental(24, 742, 100),
    divMental(25, 9163, 10), divMental(26, 58, 10), divMental(27, 4389, 1000), divMental(28, 635, 100),
    divMental(29, 2916, 10), divMental(30, 7805, 100), divMental(31, 3478, 1000), divMental(32, 96, 10),
    divMental(33, 8120, 100), divMental(34, 419, 10), divMental(35, 5233, 100), divMental(36, 2807, 1000),
    divMental(37, 73, 10), divMental(38, 6946, 100), divMental(39, 8753, 10), divMental(40, 362, 100),
    divMental(41, 1502, 10), divMental(42, 7634, 1000), divMental(43, 45, 10), divMental(44, 4271, 100),
    divMental(45, 5019, 1000), divMental(46, 312, 10),
  )

  val ws21: List<MathProblem> = listOf(
    divMental(1, 64, 10), divMental(2, 583, 100), divMental(3, 2417, 10), divMental(4, 7300, 100),
    divMental(5, 89, 10), divMental(6, 1608, 100), divMental(7, 4251, 1000), divMental(8, 35, 10),
    divMental(9, 782, 100), divMental(10, 3964, 1000), divMental(11, 147, 10), divMental(12, 6470, 100),
    divMental(13, 2039, 1000), divMental(14, 92, 10), divMental(15, 5686, 100), divMental(16, 276, 10),
    divMental(17, 8503, 1000), divMental(18, 608, 100), divMental(19, 4715, 10), divMental(20, 1100, 100),
    divMental(21, 7842, 1000), divMental(22, 53, 10), divMental(23, 3369, 100), divMental(24, 990, 10),
    divMental(25, 6024, 100), divMental(26, 2867, 1000), divMental(27, 87, 10), divMental(28, 2935, 100),
    divMental(29, 4607, 1000), divMental(30, 153, 10), divMental(31, 6843, 100), divMental(32, 9264, 1000),
    divMental(33, 46, 10), divMental(34, 1472, 100), divMental(35, 3719, 10), divMental(36, 5368, 1000),
    divMental(37, 94, 10), divMental(38, 7126, 100), divMental(39, 2584, 10), divMental(40, 8742, 1000),
    divMental(41, 329, 10), divMental(42, 4896, 100), divMental(43, 6072, 1000), divMental(44, 128, 10),
    divMental(45, 3845, 100), divMental(46, 9851, 1000), divMental(47, 61, 10), divMental(48, 7963, 100),
    divMental(49, 2765, 1000), divMental(50, 483, 10), divMental(51, 4821, 10), divMental(52, 6423, 100),
    divMental(53, 7914, 1000), divMental(54, 135, 10), divMental(55, 8760, 100), divMental(56, 3254, 1000),
    divMental(57, 72, 10), divMental(58, 6985, 100), divMental(59, 1265, 1000), divMental(60, 9308, 10),
    divMental(61, 5943, 100), divMental(62, 2437, 1000), divMental(63, 84, 10), divMental(64, 4672, 100),
    divMental(65, 7561, 1000), divMental(66, 216, 10), divMental(67, 3817, 100), divMental(68, 9402, 1000),
    divMental(69, 58, 10), divMental(70, 6239, 100),
  )

  val ws22: List<MathProblem> = listOf(
    compatChoice(1, 876, 3, listOf("800 ÷ 3", "600 ÷ 3", "900 ÷ 3"), 2, listOf(300)),
    compatChoice(2, 434, 7, listOf("400 ÷ 7", "420 ÷ 7", "490 ÷ 7"), 1, listOf(60)),
    compatChoice(3, 161, 3, listOf("150 ÷ 3", "180 ÷ 3", "210 ÷ 3"), 0, listOf(50)),
    compatChoice(4, 296, 5, listOf("250 ÷ 5", "350 ÷ 5", "300 ÷ 5"), 2, listOf(60)),
    compatChoice(5, 901, 7, listOf("630 ÷ 7", "1,400 ÷ 7", "700 ÷ 7"), 2, listOf(100)),
    compatChoice(6, 843, 9, listOf("900 ÷ 9", "720 ÷ 9", "810 ÷ 9"), 2, listOf(90)),
    compatChoice(7, 894, 5, listOf("500 ÷ 5", "1,000 ÷ 5", "100 ÷ 5"), 1, listOf(200)),
    compatChoice(8, 1490, 5, listOf("2,000 ÷ 5", "1,500 ÷ 5", "1,400 ÷ 5"), 1, listOf(300)),
    compatChoice(9, 1728, 32, listOf("1,500 ÷ 30", "2,000 ÷ 30", "1,800 ÷ 30"), 2, listOf(60)),
    compatChoice(10, 6272, 28, listOf("6,000 ÷ 30", "6,000 ÷ 20", "9,000 ÷ 30"), 0, listOf(200)),
    compatChoice(11, 6919, 7, listOf("6,300 ÷ 7", "5,600 ÷ 7", "7,000 ÷ 7"), 2, listOf(1000)),
    compatChoice(12, 7819, 4, listOf("8,000 ÷ 4", "6,000 ÷ 4", "10,000 ÷ 4"), 0, listOf(2000)),
    compatChoice(13, 1299, 12, listOf("1,200 ÷ 10", "1,200 ÷ 12", "1,440 ÷ 12"), 0, listOf(120, 100)),
    compatChoice(14, 795, 84, listOf("800 ÷ 80", "700 ÷ 80", "400 ÷ 80"), 0, listOf(10)),
    compatChoice(15, 819, 94, listOf("900 ÷ 90", "810 ÷ 90", "800 ÷ 8"), 1, listOf(9)),
    // 16-30 best estimate choice
    bestEstChoice(16, 895, 8, listOf(200, 100, 300), 100),
    bestEstChoice(17, 336, 7, listOf(50, 60, 70), 50),
    bestEstChoice(18, 324, 4, listOf(100, 10, 80), 80),
    bestEstChoice(19, 816, 6, listOf(100, 50, 10), 100),
    bestEstChoice(20, 521, 3, listOf(300, 200, 100), 200),
    bestEstChoice(21, 777, 8, listOf(50, 100, 200), 100),
    bestEstChoice(22, 4821, 5, listOf(100, 500, 1000), 1000),
    bestEstChoice(23, 5895, 4, listOf(1000, 2000, 3000), 1000),
    bestEstChoice(24, 6335, 51, listOf(200, 100, 150), 100),
    bestEstChoice(25, 9339, 81, listOf(10, 1000, 100), 100),
    bestEstChoice(26, 1344, 26, listOf(50, 500, 5000), 50),
    bestEstChoice(27, 6290, 68, listOf(90, 950, 900), 90),
    bestEstChoice(28, 9812, 998, listOf(100, 10, 1000), 10),
    bestEstChoice(29, 5679, 529, listOf(100, 10, 1000), 10),
    bestEstChoice(30, 2598, 25, listOf(500, 1000, 100), 100),
    // 31-45 open compatible quotient
    openEst(31, 261, 5, listOf(50, 52, 60), "250 ÷ 5"),
    openEst(32, 168, 9, listOf(20, 18), "180 ÷ 9"),
    openEst(33, 2985, 6, listOf(500), "3,000 ÷ 6"),
    openEst(34, 436, 4, listOf(100, 110), "400 ÷ 4"),
    openEst(35, 296, 4, listOf(70, 75, 80), "280 ÷ 4"),
    openEst(36, 723, 6, listOf(120, 100), "720 ÷ 6"),
    openEst(37, 417, 2, listOf(200, 210), "400 ÷ 2"),
    openEst(38, 331, 7, listOf(50, 40), "350 ÷ 7"),
    openEst(39, 888, 3, listOf(300), "900 ÷ 3"),
    openEst(40, 4047, 2, listOf(2000), "4,000 ÷ 2"),
    openEst(41, 2452, 8, listOf(300, 310), "2,400 ÷ 8"),
    openEst(42, 1311, 7, listOf(200, 180, 190), "1,400 ÷ 7"),
    openEst(43, 9344, 9, listOf(1000, 1040), "9,000 ÷ 9"),
    openEst(44, 1597, 83, listOf(20), "1,600 ÷ 80"),
    openEst(45, 1197, 42, listOf(30), "1,200 ÷ 40"),
  )

  val ws23: List<MathProblem> = listOf(
    compatChoice(1, 742, 4, listOf("600 ÷ 4", "700 ÷ 4", "800 ÷ 4"), 2, listOf(200)),
    compatChoice(2, 918, 3, listOf("800 ÷ 3", "900 ÷ 3", "1,000 ÷ 3"), 1, listOf(300)),
    compatChoice(3, 1253, 6, listOf("1,100 ÷ 6", "1,200 ÷ 6", "1,300 ÷ 6"), 1, listOf(200)),
    compatChoice(4, 388, 5, listOf("200 ÷ 5", "400 ÷ 5", "500 ÷ 5"), 1, listOf(80)),
    compatChoice(5, 1675, 8, listOf("1,600 ÷ 8", "1,700 ÷ 8", "1,800 ÷ 8"), 0, listOf(200)),
    compatChoice(6, 2842, 7, listOf("2,800 ÷ 7", "2,900 ÷ 7", "3,000 ÷ 7"), 0, listOf(400)),
    compatChoice(7, 639, 2, listOf("600 ÷ 2", "700 ÷ 2", "800 ÷ 2"), 0, listOf(300)),
    compatChoice(8, 3467, 9, listOf("3,400 ÷ 9", "3,500 ÷ 9", "3,600 ÷ 9"), 2, listOf(400)),
    compatChoice(9, 4923, 6, listOf("4,900 ÷ 6", "5,000 ÷ 6", "5,100 ÷ 6"), 2, listOf(850, 800)),
    compatChoice(10, 7108, 5, listOf("7,000 ÷ 5", "7,100 ÷ 5", "7,200 ÷ 5"), 0, listOf(1400, 1420)),
    compatChoice(11, 1586, 4, listOf("1,500 ÷ 4", "1,600 ÷ 4", "1,700 ÷ 4"), 1, listOf(400)),
    compatChoice(12, 2394, 3, listOf("2,300 ÷ 3", "2,400 ÷ 3", "2,500 ÷ 3"), 1, listOf(800)),
    compatChoice(13, 8276, 9, listOf("8,200 ÷ 9", "8,300 ÷ 9", "8,400 ÷ 9"), 0, listOf(900, 910, 920)),
    compatChoice(14, 974, 6, listOf("900 ÷ 6", "1,000 ÷ 6", "1,100 ÷ 6"), 0, listOf(150, 160)),
    compatChoice(15, 5637, 7, listOf("5,600 ÷ 7", "5,700 ÷ 7", "5,800 ÷ 7"), 0, listOf(800)),
    compatChoice(16, 1458, 7, listOf("1,400 ÷ 7", "1,500 ÷ 7", "1,600 ÷ 7"), 0, listOf(200)),
    compatChoice(17, 6384, 8, listOf("6,200 ÷ 8", "6,300 ÷ 8", "6,400 ÷ 8"), 2, listOf(800)),
    compatChoice(18, 2715, 5, listOf("2,700 ÷ 5", "2,800 ÷ 5", "2,600 ÷ 5"), 0, listOf(540, 500)),
    compatChoice(19, 783, 3, listOf("700 ÷ 3", "800 ÷ 3", "900 ÷ 3"), 2, listOf(300)),
    compatChoice(20, 4166, 6, listOf("4,100 ÷ 6", "4,200 ÷ 6", "4,300 ÷ 6"), 1, listOf(700)),
    compatChoice(21, 919, 4, listOf("800 ÷ 4", "900 ÷ 4", "1,000 ÷ 4"), 0, listOf(200, 225, 230)),
    compatChoice(22, 3532, 7, listOf("3,500 ÷ 7", "3,600 ÷ 7", "3,400 ÷ 7"), 0, listOf(500)),
    compatChoice(23, 5863, 9, listOf("5,800 ÷ 9", "5,900 ÷ 9", "6,000 ÷ 9"), 2, listOf(600, 650, 666)),
    compatChoice(24, 1074, 2, listOf("1,000 ÷ 2", "1,100 ÷ 2", "1,200 ÷ 2"), 1, listOf(550, 500)),
    compatChoice(25, 8391, 6, listOf("8,300 ÷ 6", "8,400 ÷ 6", "8,500 ÷ 6"), 1, listOf(1400)),
    compatChoice(26, 2205, 3, listOf("2,100 ÷ 3", "2,200 ÷ 3", "2,300 ÷ 3"), 0, listOf(700)),
    compatChoice(27, 7642, 7, listOf("7,600 ÷ 7", "7,700 ÷ 7", "7,800 ÷ 7"), 1, listOf(1100)),
    compatChoice(28, 638, 5, listOf("600 ÷ 5", "700 ÷ 5", "800 ÷ 5"), 0, listOf(120, 130)),
    compatChoice(29, 4807, 8, listOf("4,700 ÷ 8", "4,800 ÷ 8", "4,900 ÷ 8"), 1, listOf(600)),
    compatChoice(30, 3149, 4, listOf("3,100 ÷ 4", "3,200 ÷ 4", "3,300 ÷ 4"), 1, listOf(800)),
    compatChoice(31, 5075, 5, listOf("5,000 ÷ 5", "5,100 ÷ 5", "5,200 ÷ 5"), 0, listOf(1000)),
    compatChoice(32, 1834, 9, listOf("1,800 ÷ 9", "1,900 ÷ 9", "2,000 ÷ 9"), 0, listOf(200)),
    compatChoice(33, 7968, 6, listOf("7,900 ÷ 6", "8,000 ÷ 6", "8,100 ÷ 6"), 2, listOf(1350, 1300)),
    compatChoice(34, 952, 8, listOf("900 ÷ 8", "1,000 ÷ 8", "1,100 ÷ 8"), 1, listOf(125, 120)),
    compatChoice(35, 6517, 7, listOf("6,400 ÷ 7", "6,500 ÷ 7", "6,600 ÷ 7"), 1, listOf(900, 930)),
    // 36-50 open compatible quotient
    openEst(36, 1402, 6, listOf(200, 230, 233), "1,200 ÷ 6"),
    openEst(37, 576, 4, listOf(150, 140, 144), "600 ÷ 4"),
    openEst(38, 3853, 7, listOf(550, 500, 600), "3,500 ÷ 7"),
    openEst(39, 2735, 5, listOf(550, 540, 500), "2,750 ÷ 5"),
    openEst(40, 7632, 8, listOf(950, 1000, 900), "8,000 ÷ 8"),
    openEst(41, 5947, 3, listOf(2000), "6,000 ÷ 3"),
    openEst(42, 9210, 6, listOf(1500), "9,000 ÷ 6"),
    openEst(43, 1764, 2, listOf(900, 880, 850), "1,800 ÷ 2"),
    openEst(44, 4838, 7, listOf(700), "4,900 ÷ 7"),
    openEst(45, 6713, 9, listOf(700, 750, 800), "6,300 ÷ 9"),
    openEst(46, 8963, 11, listOf(900, 800, 815), "9,000 ÷ 10"),
    openEst(47, 2649, 4, listOf(650, 700, 660, 600), "2,800 ÷ 4"),
    openEst(48, 5354, 8, listOf(700, 650, 600), "5,600 ÷ 8"),
    openEst(49, 4071, 3, listOf(1300, 1400, 1000), "4,200 ÷ 3"),
    openEst(50, 918, 6, listOf(150), "900 ÷ 6"),
  )

  val ws24: List<MathProblem> = listOf(
    divWord(1, "Kate, Elaine, Via and Ronna are best friends. They decided to take a taxi to go to their destination and divide the fare among them. If the fare meter reads three hundred sixty-four pesos, how much should each of them pay?", 364, 4, "pesos", "There are 4 friends sharing ₱364 equally: 364 ÷ 4."),
    divWord(2, "Mang Totoy is a tricycle driver. He needs to earn 2,400 pesos. If he earns 200 pesos an hour, for how many hours does he need to work?", 2400, 200, "hours", "Divide 2,400 ÷ 200."),
    divWord(3, "A school collected 1,680 pesos for a field trip. The money will be shared equally among 24 students for transportation. How much money will each student receive?", 1680, 24, "pesos", "Divide 1,680 ÷ 24."),
    divWord(4, "A bookstore earned 3,240 pesos from selling notebooks in one day. The money was divided equally among 18 sales staff as an incentive. How much money did each staff member receive?", 3240, 18, "pesos", "Divide 3,240 ÷ 18."),
    divWord(5, "Mandy needs to visit a doctor and decides to take a taxi. If she only has twenty-peso bills, how many twenty-peso bills should she pay if the meter reads three hundred pesos?", 300, 20, "bills", "Divide 300 ÷ 20."),
    divWord(6, "Kristen rode the tricycle to go to Ateneo from the Mini Stop store. If she only had five-peso coins, how many five-peso coins should she pay if the fare was 35 pesos?", 35, 5, "coins", "Divide 35 ÷ 5."),
    divWord(7, "If the Paoay church can seat 33 people per pew or row, how many pews will be filled up if 4,290 people will attend a mass?", 4290, 33, "pews", "Divide 4,290 ÷ 33."),
    divWord(8, "Enrique and Malena will divide their shared allowance of 9,634 pesos among themselves. How much will each of them get?", 9634, 2, "pesos", "2 people sharing equally: 9,634 ÷ 2."),
    divWord(9, "A family paid 3,276 pesos for their electricity bill. If the amount is divided equally among 12 members of the family, how much should each member pay?", 3276, 12, "pesos", "Divide 3,276 ÷ 12."),
    divWord(10, "A shop has 4,800 pesos in sales for one day. If the amount is shared equally among 16 staff members, how much will each staff receive?", 4800, 16, "pesos", "Divide 4,800 ÷ 16."),
    divWord(11, "A bus company collected 7,980 pesos from its trips. If the money is divided equally among 15 drivers, how much money will each driver get?", 7980, 15, "pesos", "Divide 7,980 ÷ 15."),
    divWord(12, "A farmer harvested 2,772 kilograms of rice. If he packs it equally in 18 sacks, how many kilograms of rice will be in each sack?", 2772, 18, "kg", "Divide 2,772 ÷ 18."),
    divWord(13, "A teacher bought 2,880 pesos worth of school supplies. She wants to give the same amount of money to 24 pupils for their projects. How much will each pupil receive?", 2880, 24, "pesos", "Divide 2,880 ÷ 24."),
    divWord(14, "A printing company printed 8,064 flyers. If the flyers are packed equally in 21 boxes, how many flyers are in each box?", 8064, 21, "flyers", "Divide 8,064 ÷ 21."),
    divWord(15, "The barangay collected 5,670 pesos for a community event. If the money is shared equally among 27 volunteers, how much will each volunteer receive?", 5670, 27, "pesos", "Divide 5,670 ÷ 27."),
    divWord(16, "A store earned 6,960 pesos in one week. If the amount is divided equally among 20 employees as a bonus, how much bonus will each employee get?", 6960, 20, "pesos", "Divide 6,960 ÷ 20."),
  )

  val ws25: List<MathProblem> = listOf(
    openEst(1, 585, 7, listOf(80, 84), "560 ÷ 7", "A recipe for sinigang needs 7 kg of pork. Mama Sita has 585 kg of pork. About how many times can she make sinigang?"),
    openEst(2, 478, 7, listOf(70, 68), "490 ÷ 7", "A recipe for sinigang needs 7 kg of pork. Mang Tomas has 478 kg of pork. About how many times can he make sinigang?"),
    openEst(3, 6226, 7, listOf(900, 890), "6,300 ÷ 7", "A recipe for sinigang needs 7 kg of pork. Aling Lydia has 6,226 kg of pork. About how many times can she make sinigang?"),
    openEst(4, 1388, 7, listOf(200), "1,400 ÷ 7", "A recipe for sinigang needs 7 kg of pork. Aling Rufina has 1,388 kg of pork. About how many times can she make sinigang?"),
    openEst(5, 419, 5, listOf(80, 84), "400 ÷ 5", "The balut vendor has 419 baluts. About how many baluts will he place in each basket if he uses 5 baskets?"),
    openEst(6, 419, 6, listOf(70), "420 ÷ 6", "The balut vendor has 419 baluts. About how many baluts will he place in each basket if he uses 6 baskets?"),
    openEst(7, 419, 7, listOf(60), "420 ÷ 7", "The balut vendor has 419 baluts. About how many baluts will he place in each basket if he uses 7 baskets?"),
    openEst(8, 419, 19, listOf(20, 21), "400 ÷ 20", "The balut vendor has 419 baluts. About how many baluts will he place in each basket if he uses 19 baskets?"),
    openEst(9, 314, 8, listOf(40, 39), "320 ÷ 8", "At Isdaan in Tarlac, one table can seat 8 guests. Around how many tables will be occupied by 314 guests?"),
    openEst(10, 375, 18, listOf(20, 19), "400 ÷ 20", "A stack of domino can hold as many as 18 dominos. About how many stacks can you form out of 375 dominos?"),
    openEst(11, 479, 6, listOf(80), "480 ÷ 6", "A boat going to Boracay island can hold as many as 6 persons. About how many boats will be needed to transfer 479 tourists?"),
    openEst(12, 421, 13, listOf(40, 32, 30), "400 ÷ 10", "The club president brought 421 yemas to a rotary club meeting. Estimate how many yemas each of the 13 club members gets."),
    openEst(13, 695, 7, listOf(100), "700 ÷ 7", "In a running event at Marikina Sports Center, 695 runners are divided equally among 7 lanes. About how many runners are on each lane?"),
    openEst(14, 5772, 72, listOf(80, 85), "5,600 ÷ 70", "During harvest season, 5,772 mangoes are harvested from 72 trees. About how many mangoes does each tree produce?"),
    openEst(15, 985, 49, listOf(20), "1,000 ÷ 50", "Mother bought 985 strawberries from Baguio. If she uses 49 strawberries in every bottle of jam, about how many bottles can she make?"),
    openEst(16, 376, 9, listOf(40, 42), "360 ÷ 9", "You can put 9 shoe boxes per stack. About how many stacks will you make if you have 376 boxes of shoes?"),
    openEst(17, 202, 11, listOf(20, 18), "200 ÷ 10", "Trinoma's parking area has 202 parking slots arranged in 11 rows. About how many parking slots are in each row?"),
    openEst(18, 718, 8, listOf(90), "720 ÷ 8", "Johnny's ninang gives him 718 stickers. If he puts them in 8 scrapbooks equally, about how many stickers per scrapbook?"),
    openEst(19, 544, 6, listOf(90), "540 ÷ 6", "A chef has 544 celery sticks and uses 6 celery sticks per plate. About how many plates can he decorate?"),
    openEst(20, 228, 8, listOf(30, 28, 25), "240 ÷ 8", "8 coconuts are needed to make one container of sherbet. About how many containers can be made from 228 coconuts?"),
  )
}
