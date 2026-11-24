package com.example.data.g5

import com.example.model.ArithmeticOp
import com.example.model.MathProblem

object Grade5MultiplyData {

  private fun mult(item: Int, a: Int, b: Int) = MathProblem.ArithmeticProblem(
    itemNumber = item,
    operands = listOf(a, b),
    op = ArithmeticOp.MULTIPLY,
    answer = a * b,
    hint = "Multiply by the ones digit first, then by the tens digit (remember the 0 placeholder), then add!",
  )

  private fun wordMult(item: Int, prompt: String, ans: Int, unit: String, hint: String) =
    MathProblem.ArithmeticProblem(
      itemNumber = item,
      operands = emptyList(),
      op = ArithmeticOp.MULTIPLY,
      answer = ans,
      promptText = prompt,
      unitLabel = unit,
      showVerticalColumns = false,
      hint = hint,
    )

  private fun highestRound(n: Int): Int {
    if (n < 10) return n
    var p = 1
    while (n / p >= 10) p *= 10
    val lead = n / p
    val rem = n % p
    return if (rem >= p / 2) (lead + 1) * p else lead * p
  }

  private fun estProd(item: Int, a: Int, b: Int): MathProblem.EstimateOpProblem {
    val ra = highestRound(a)
    val rb = highestRound(b)
    return MathProblem.EstimateOpProblem(
      itemNumber = item,
      factors = listOf(a, b),
      roundedFactors = listOf(ra, rb),
      expectedEstimate = ra.toLong() * rb.toLong(),
      hint = "Round each factor to its highest (leftmost) place value: check the next digit (5+ rounds up, 0–4 keeps it), then multiply!",
    )
  }

  private fun estWord(item: Int, prompt: String, a: Int, b: Int): MathProblem.EstimateOpProblem {
    val ra = highestRound(a)
    val rb = highestRound(b)
    return MathProblem.EstimateOpProblem(
      itemNumber = item,
      factors = listOf(a, b),
      roundedFactors = listOf(ra, rb),
      expectedEstimate = ra.toLong() * rb.toLong(),
      promptText = prompt,
      hint = "Round each factor to its highest (leftmost) place value: check the next digit (5+ rounds up, 0–4 keeps it), then multiply!",
    )
  }

  private fun mental(item: Int, a: Int, b: Int, strategy: String, hint: String) =
    MathProblem.MentalMultiplyProblem(
      itemNumber = item,
      factorA = a,
      factorB = b,
      answer = a * b,
      recommendedStrategy = strategy,
      hint = hint,
    )

  val ws12: List<MathProblem> = listOf(
    mult(1, 79, 45), mult(2, 123, 20), mult(3, 111, 22), mult(4, 3, 77),
    mult(5, 687, 2), mult(6, 974, 32), mult(7, 13, 67), mult(8, 110, 32),
    mult(9, 456, 21), mult(10, 147, 92), mult(11, 213, 74), mult(12, 999, 11),
    mult(13, 999, 55), mult(14, 325, 32), mult(15, 987, 41), mult(16, 698, 65),
    mult(17, 987, 25), mult(18, 581, 99), mult(19, 229, 12), mult(20, 412, 96),
    mult(21, 339, 36), mult(22, 769, 41), mult(23, 668, 78), mult(24, 901, 55),
    mult(25, 888, 11), mult(26, 634, 12), mult(27, 406, 46), mult(28, 800, 64),
    mult(29, 422, 50), mult(30, 247, 52), mult(31, 536, 34), mult(32, 999, 99),
  )

  val ws13: List<MathProblem> = listOf(
    mult(1, 2, 26), mult(2, 34, 14), mult(3, 234, 45), mult(4, 882, 23),
    mult(5, 134, 11), mult(6, 204, 13), mult(7, 8, 32), mult(8, 436, 16),
    mult(9, 251, 64), mult(10, 422, 24), mult(11, 324, 19), mult(12, 218, 76),
    mult(13, 198, 31), mult(14, 189, 42), mult(15, 199, 12), mult(16, 315, 36),
    wordMult(17, "Angelo and nine of his friends rode the UP IKOT jeepney to go to their classes. How much would they pay the driver altogether if the student fare was seven pesos per head?", 70, "pesos", "Angelo + 9 friends = 10 students; 10 × 7."),
    wordMult(18, "Malacañang of the North has an entrance fee of 20 pesos per head. If a group of 17 people wants to visit, how much will the group pay?", 340, "pesos", "Multiply ₱20 × 17."),
    wordMult(19, "Betty is now 28 years old. Her grandmother's age is thrice her age. How old is her grandmother now?", 84, "years old", "Thrice means × 3: 28 × 3."),
    wordMult(20, "The military sent 999 soldiers and equipped each one with 99 bullets. How many bullets should the military send in all?", 98901, "bullets", "Multiply 999 × 99."),
    wordMult(21, "A shrine has 85 gardeners making the shrine clean and green. If each gardener gets a daily salary of ₱950, how much is the total cost of cleaning the shrine for a day?", 80750, "pesos", "Multiply 85 × 950."),
    wordMult(22, "In San Jose Church in Ivana, Batanes, each person donates 15 pesos every mass. If there are 912 people who attend mass in a week, how much is the total collection?", 13680, "pesos", "Multiply 15 × 912."),
    wordMult(23, "From the previous question (₱13,680 collected each week in San Jose Church), how much is the total collection for a year (52 weeks)?", 711360, "pesos", "There are 52 weeks in 1 year: 13,680 × 52."),
    wordMult(24, "The San Pablo Church in San Pablo, Isabela features a tall bell tower. If 32 people, each having the same height of 6 feet, stand on each other to match the height of the bell tower, how high is the tower in feet?", 192, "feet", "Multiply 32 × 6."),
    wordMult(25, "In St. Hyacinth Church in Tuguegarao, Cagayan, each person donates 6 pesos every mass. If there are 926 persons who attend mass every week, how much donation will the church receive in a year (52 weeks)?", 288912, "pesos", "First find weekly total (6 × 926), then multiply by 52 weeks."),
  )

  val ws14: List<MathProblem> = listOf(
    estProd(1, 240, 63), estProd(2, 1424, 89), estProd(3, 4386, 73), estProd(4, 9307, 308),
    estProd(5, 5138, 49), estProd(6, 7246, 245), estProd(7, 5015, 527), estProd(8, 3615, 413),
    estProd(9, 966, 876), estProd(10, 5220, 642), estProd(11, 735, 968), estProd(12, 6531, 127),
    estProd(13, 2271, 282), estProd(14, 8284, 451), estProd(15, 9978, 646), estProd(16, 5475, 708),
    estProd(17, 3833, 95), estProd(18, 1976, 965), estProd(19, 983, 61), estProd(20, 5419, 638),
    estProd(21, 3357, 28), estProd(22, 768, 115), estProd(23, 4321, 890), estProd(24, 446, 325),
    estProd(25, 5924, 45), estProd(26, 1010, 333), estProd(27, 7456, 422), estProd(28, 8326, 438),
    estProd(29, 4295, 368), estProd(30, 1661, 22), estProd(31, 6320, 34), estProd(32, 3276, 52),
    estProd(33, 6439, 838), estProd(34, 368, 230), estProd(35, 1589, 137), estProd(36, 7258, 603),
    estProd(37, 1393, 656), estProd(38, 6973, 52), estProd(39, 9365, 257), estProd(40, 3359, 369),
    estProd(41, 8112, 578), estProd(42, 5372, 974), estProd(43, 4222, 16), estProd(44, 3355, 234),
    estProd(45, 8124, 576), estProd(46, 2435, 776),
  )

  val ws15: List<MathProblem> = listOf(
    estProd(1, 126, 873), estProd(2, 639, 13), estProd(3, 5245, 932), estProd(4, 2456, 567),
    estProd(5, 3658, 987), estProd(6, 6234, 75), estProd(7, 897, 43), estProd(8, 6234, 557),
    estProd(9, 7789, 240), estProd(10, 8438, 768), estProd(11, 4435, 12), estProd(12, 6436, 561),
    estProd(13, 6034, 22), estProd(14, 6258, 10), estProd(15, 570, 912), estProd(16, 4457, 240),
    estProd(17, 735, 128), estProd(18, 9346, 47), estProd(19, 3222, 705), estProd(20, 8257, 67),
    estProd(21, 2046, 124), estProd(22, 7005, 900), estProd(23, 8246, 257), estProd(24, 1540, 239),
    estProd(25, 1366, 99), estProd(26, 6280, 235),
    estWord(27, "Matthew sold 124 pencils each day for 21 days. About how many pencils did he sell in all?", 124, 21),
    estWord(28, "If 274 persons are diagnosed to have respiratory infections everyday, how many get infected in 25 days? Estimate your answer.", 274, 25),
    estWord(29, "1,776 people attended the local carnival per week. Around how many people attended the carnival in 32 weeks?", 1776, 32),
    estWord(30, "Mrs. Garcia bought 326 packets of stickers. Each sticker packet had 32 stickers. What was the best estimate of the number of stickers Mrs. Garcia bought?", 326, 32),
    estWord(31, "Shawn sold 23 candy bars each day for 116 days. What was the best estimate of the number of candy bars he sold during this time?", 23, 116),
    estWord(32, "A farmer sold 2,010 sacks of mangoes. Each sack had 79 pieces of mangoes inside. What is the best estimate of the number of mangoes sold by the farmer?", 2010, 79),
    MathProblem.EstimateOpProblem(
      itemNumber = 33,
      factors = listOf(5, 48, 12, 23),
      roundedFactors = listOf(5, 50, 10, 20),
      expectedEstimate = 200L,
      promptText = "Estimate which is cheaper: to buy 5 boxes of juice that cost 48 pesos per box OR 12 bottles of softdrinks that cost 23 pesos per bottle.",
      choiceOptions = listOf("5 boxes of juice (≈ ₱250)", "12 bottles of softdrinks (≈ ₱200)"),
      correctChoice = "12 bottles of softdrinks (≈ ₱200)",
      hint = "Round each price and quantity to its highest place value, multiply to estimate both totals, and compare which is cheaper!",
    ),
    estWord(34, "Pedro buys 23 bags of jolens. Each bag contains 102 jolens. Around how many jolens does Pedro have in all?", 23, 102),
    estWord(35, "You will make 135 flower arrangements and each arrangement has 26 flowers. Around how many flowers will you need to buy?", 135, 26),
    estWord(36, "Janice bought 250 pieces of crayons. If each crayon is priced at ₱16, how much did Janice pay (estimate)?", 250, 16),
  )

  val ws16: List<MathProblem> = listOf(
    mental(1, 7, 6, "Double & halve / skip count", "Break apart 7 into 5 + 2, or skip-count by 7 six times."),
    mental(2, 3, 8, "Double & halve / skip count", "Think of 3 groups of 8 (or double 8 and add 8 more)."),
    mental(3, 5, 12, "Double & halve / skip count", "Halve 12 and double 5 to turn this into an easy ×10 multiplication!"),
    mental(4, 10, 4, "Multiply by 10 / 11 shortcut", "To multiply a whole number by 10, annex one zero to the end."),
    mental(5, 12, 6, "Break apart tens & ones", "Break 12 into 10 + 2: multiply each part by 6, then add."),
    mental(6, 14, 3, "Break apart tens & ones", "Break 14 into 10 + 4: multiply each part by 3, then add."),
    mental(7, 23, 2, "Break apart tens & ones", "Double the tens (20 × 2) and double the ones (3 × 2), then add."),
    mental(8, 14, 11, "Multiply by 10 / 11 shortcut", "Multiply 14 × 10, then add one more 14."),
    mental(9, 22, 9, "Multiply by 10 then subtract", "Multiply 22 × 10 first, then subtract one 22."),
    mental(10, 13, 12, "Break apart tens & ones", "Break 12 into 10 + 2: find (13 × 10) + (13 × 2)."),
    mental(11, 18, 7, "Break apart tens & ones", "Break 18 into 10 + 8: multiply each part by 7, then add."),
    mental(12, 24, 6, "Break apart tens & ones", "Break 24 into 20 + 4: multiply each part by 6, then add."),
    mental(13, 15, 12, "Double & halve / skip count", "Double 15 and halve 12 so you multiply an easy multiple of 10!"),
    mental(14, 22, 5, "Double & halve / skip count", "Halve 22 and double 5 to turn this into an easy ×10 multiplication!"),
    mental(15, 17, 9, "Multiply by 10 then subtract", "Multiply 17 × 10 first, then subtract one 17."),
    mental(16, 26, 4, "Double & halve / skip count", "To multiply by 4 mentally, double 26 and then double that result again!"),
    mental(17, 19, 8, "Multiply by 10 then subtract", "Use 20 × 8 (since 19 is 1 less than 20), then subtract one 8."),
    mental(18, 21, 7, "Break apart tens & ones", "Break 21 into 20 + 1: find (20 × 7) + (1 × 7)."),
    mental(19, 13, 11, "Multiply by 10 / 11 shortcut", "Multiply 13 × 10, then add one more 13."),
    mental(20, 16, 10, "Multiply by 10 / 11 shortcut", "Multiply by 10 by annexing one zero to 16."),
    mental(21, 18, 5, "Double & halve / skip count", "Halve 18 and double 5 to turn this into an easy ×10 multiplication!"),
    mental(22, 24, 4, "Double & halve / skip count", "To multiply by 4 mentally, double 24 and then double that result again!"),
    mental(23, 12, 9, "Multiply by 10 then subtract", "Multiply 12 × 10 first, then subtract one 12."),
    mental(24, 14, 6, "Break apart tens & ones", "Break 14 into 10 + 4: multiply each part by 6, then add."),
    mental(25, 20, 7, "Multiply by 10 / 11 shortcut", "Multiply 2 × 7, then annex one zero to the end."),
    mental(26, 16, 9, "Multiply by 10 then subtract", "Multiply 16 × 10 first, then subtract one 16."),
    mental(27, 15, 7, "Break apart tens & ones", "Break 15 into 10 + 5: multiply each part by 7, then add."),
    mental(28, 17, 5, "Break apart tens & ones", "Break 17 into 10 + 7: multiply each part by 5, then add."),
    mental(29, 13, 8, "Break apart tens & ones", "Break 13 into 10 + 3: multiply each part by 8, then add."),
    mental(30, 11, 12, "Multiply by 10 / 11 shortcut", "Multiply 10 × 12, then add one more 12."),
  )

  val ws17: List<MathProblem> = listOf(
    mental(1, 64, 3, "Break apart tens & ones", "Break 64 into 60 + 4: multiply each part by 3, then add."),
    mental(2, 13, 2, "Break apart tens & ones", "Double the tens (10 × 2) and double the ones (3 × 2), then add."),
    mental(3, 21, 7, "Break apart tens & ones", "Break 21 into 20 + 1: find (20 × 7) + (1 × 7)."),
    mental(4, 12, 8, "Break apart tens & ones", "Break 12 into 10 + 2: multiply each part by 8, then add."),
    mental(5, 15, 4, "Double & halve / skip count", "Double 15 and halve 4 (or double 15 twice)."),
    mental(6, 23, 5, "Break apart tens & ones", "Break 23 into 20 + 3: multiply each part by 5, then add."),
    mental(7, 13, 10, "Multiply by 10 / 11 shortcut", "Multiply by 10 by annexing one zero to 13."),
    mental(8, 11, 9, "Multiply by 10 / 11 shortcut", "Multiply 10 × 9 and add one more 9 (or use the 11s pattern)."),
    mental(9, 10, 12, "Multiply by 10 / 11 shortcut", "Multiply 12 by 10 by annexing one zero."),
    mental(10, 25, 3, "Break apart tens & ones", "Break 25 into 20 + 5: multiply each part by 3, then add."),
    mental(11, 15, 12, "Double & halve / skip count", "Double 15 and halve 12 to turn this into an easy ×10 multiplication!"),
    mental(12, 13, 11, "Multiply by 10 / 11 shortcut", "Multiply 13 × 10, then add one more 13."),
    mental(13, 14, 13, "Break apart tens & ones", "Break 13 into 10 + 3: find (14 × 10) + (14 × 3)."),
    mental(14, 12, 14, "Break apart tens & ones", "Break 14 into 10 + 4: find (12 × 10) + (12 × 4)."),
    mental(15, 22, 9, "Multiply by 10 then subtract", "Multiply 22 × 10 first, then subtract one 22."),
    mental(16, 18, 2, "Break apart tens & ones", "Break 18 into 10 + 8: double each part, then add."),
    mental(17, 27, 3, "Break apart tens & ones", "Break 27 into 20 + 7: multiply each part by 3, then add."),
    mental(18, 16, 5, "Double & halve / skip count", "Halve 16 and double 5 to turn this into an easy ×10 multiplication!"),
    mental(19, 24, 2, "Break apart tens & ones", "Double the tens (20 × 2) and double the ones (4 × 2), then add."),
    mental(20, 19, 3, "Multiply by 10 then subtract", "Compute 20 × 3 (since 19 is 1 less than 20), then subtract one 3."),
  )
}
