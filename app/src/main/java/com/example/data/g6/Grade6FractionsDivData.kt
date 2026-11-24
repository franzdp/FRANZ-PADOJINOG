package com.example.data.g6

import com.example.model.FractionOpProblem
import com.example.model.MathProblem

object Grade6FractionsDivData {

  private fun gcd(a: Int, b: Int): Int {
    var x = kotlin.math.abs(a)
    var y = kotlin.math.abs(b)
    while (y != 0) {
      val t = x % y
      x = y
      y = t
    }
    return if (x == 0) 1 else x
  }

  private fun divModel(item: Int, na: Int, da: Int, nb: Int, db: Int): FractionOpProblem {
    val rn = na * db
    val rd = da * nb
    val g = gcd(rn, rd)
    return FractionOpProblem(
      itemNumber = item,
      expressionDisplay = "$na/$da ÷ $nb/$db",
      expectedNum = rn / g,
      expectedDen = rd / g,
      showAreaModel = true,
      modelNumA = na,
      modelDenA = da,
      modelNumB = nb,
      modelDenB = db,
      isDivisionModel = true,
      hint = "How many $nb/$db pieces fit inside $na/$da? Multiply $na/$da by $db/$nb!",
    )
  }

  private fun recip(item: Int, expr: String, rawNum: Int, rawDen: Int, hint: String): FractionOpProblem {
    val g = gcd(rawNum, rawDen)
    return FractionOpProblem(
      itemNumber = item,
      expressionDisplay = expr,
      expectedNum = rawNum / g,
      expectedDen = rawDen / g,
      isReciprocal = true,
      hint = hint,
    )
  }

  private fun divFrac(
    item: Int,
    expr: String,
    rawNum: Int,
    rawDen: Int,
    hint: String,
    prompt: String? = null,
    altDecimals: List<Double> = emptyList(),
  ): FractionOpProblem {
    val g = gcd(rawNum, rawDen)
    return FractionOpProblem(
      itemNumber = item,
      expressionDisplay = expr,
      expectedNum = rawNum / g,
      expectedDen = rawDen / g,
      promptText = prompt,
      alternateDecimals = altDecimals,
      hint = hint,
    )
  }

  val ws19: List<MathProblem> = listOf(
    divModel(1, 1, 2, 1, 4),
    divModel(2, 5, 6, 1, 2),
    divModel(3, 3, 4, 4, 5),
    divModel(4, 5, 8, 2, 3),
    divModel(5, 1, 2, 2, 3),
    divModel(6, 3, 5, 1, 4),
    divModel(7, 2, 3, 1, 6),
    divModel(8, 3, 4, 2, 3),
  )

  val ws20: List<MathProblem> = listOf(
    recip(1, "4/5", 5, 4, "Swap the numerator and denominator!"),
    recip(2, "7/6", 6, 7, "Swap the numerator and denominator!"),
    recip(3, "4/3", 3, 4, "Swap the numerator and denominator!"),
    recip(4, "10/12", 12, 10, "Swap the numerator and denominator, then simplify to lowest terms!"),
    recip(5, "2", 1, 2, "Write the whole number over 1 first, then flip numerator and denominator!"),
    recip(6, "3/5", 5, 3, "Swap the numerator and denominator!"),
    recip(7, "2 2/8", 8, 18, "Convert the mixed number to an improper fraction first, then flip and simplify!"),
    recip(8, "1 2/3", 3, 5, "Convert the mixed number to an improper fraction first, then swap numerator and denominator!"),
    recip(9, "6 7/11", 11, 73, "Convert the mixed number to an improper fraction first, then swap numerator and denominator!"),
    recip(10, "3 1/3", 3, 10, "Convert the mixed number to an improper fraction first, then swap numerator and denominator!"),
    recip(11, "1 2/9", 9, 11, "Convert the mixed number to an improper fraction first, then swap numerator and denominator!"),
    recip(12, "3 3/6", 6, 21, "Simplify or convert the mixed number to an improper fraction first, then flip!"),
    recip(13, "1 3/4", 4, 7, "Convert the mixed number to an improper fraction first, then swap numerator and denominator!"),
    recip(14, "6 2/5", 5, 32, "Convert the mixed number to an improper fraction first, then swap numerator and denominator!"),
    recip(15, "1 9/10", 10, 19, "Convert the mixed number to an improper fraction first, then swap numerator and denominator!"),
    // 16-30 Division
    divFrac(16, "4/5 ÷ 1/2", 8, 5, "Keep 4/5, change ÷ to ×, flip 1/2, multiply, and write as a mixed number."),
    divFrac(17, "2/7 ÷ 1/3", 6, 7, "Keep 2/7, change ÷ to ×, flip 1/3, and multiply straight across."),
    divFrac(18, "3/8 ÷ 1/4", 12, 8, "Keep 3/8, change ÷ to ×, flip 1/4, cross-cancel 4 & 8, and write as a mixed number."),
    divFrac(19, "6/7 ÷ 1/5", 30, 7, "Keep 6/7, change ÷ to ×, flip 1/5, multiply, and write as a mixed number."),
    divFrac(20, "4/5 ÷ 5/6", 24, 25, "Keep 4/5, change ÷ to ×, flip 5/6, and multiply straight across."),
    divFrac(21, "2/9 ÷ 2/7", 14, 18, "Keep 2/9, change ÷ to ×, flip 2/7, and cross-cancel the 2s!"),
    divFrac(22, "1/2 ÷ 5/8", 8, 10, "Keep 1/2, change ÷ to ×, flip 5/8, cross-cancel 2 & 8, and multiply."),
    divFrac(23, "1/5 ÷ 1/9", 9, 5, "Keep 1/5, change ÷ to ×, flip 1/9, multiply, and write as a mixed number."),
    divFrac(24, "2/5 ÷ 3/10", 20, 15, "Keep 2/5, change ÷ to ×, flip 3/10, cross-cancel 5 & 10, and write as a mixed number."),
    divFrac(25, "7/9 ÷ 2/11", 77, 18, "Keep 7/9, change ÷ to ×, flip 2/11, multiply, and write as a mixed number."),
    divFrac(26, "9/10 ÷ 5/12", 108, 50, "Keep 9/10, change ÷ to ×, flip 5/12, cross-cancel 10 & 12 (÷ 2), multiply, and write as a mixed number."),
    divFrac(27, "1/4 ÷ 2/13", 13, 8, "Keep 1/4, change ÷ to ×, flip 2/13, multiply, and write as a mixed number."),
    divFrac(28, "2/3 ÷ 3/4", 8, 9, "Keep 2/3, change ÷ to ×, flip 3/4, and multiply straight across."),
    divFrac(29, "2/9 ÷ 4/15", 30, 36, "Keep 2/9, change ÷ to ×, flip 4/15, cross-cancel (÷ 2 and ÷ 3), and multiply."),
    divFrac(30, "5/14 ÷ 5/9", 45, 70, "Keep 5/14, change ÷ to ×, flip 5/9, and cross-cancel the 5s!"),
  )

  val ws21: List<MathProblem> = listOf(
    divFrac(1, "4/5 ÷ 2", 4, 10, "Keep 4/5, multiply by the reciprocal of 2 (which is 1/2), cross-cancel, and simplify!"),
    divFrac(2, "2/7 ÷ 3", 2, 21, "Keep 2/7 and multiply by the reciprocal of 3 (1/3)."),
    divFrac(3, "3/8 ÷ 4", 3, 32, "Keep 3/8 and multiply by the reciprocal of 4 (1/4)."),
    divFrac(4, "6/7 ÷ 5", 6, 35, "Keep 6/7 and multiply by the reciprocal of 5 (1/5)."),
    divFrac(5, "4/5 ÷ 6", 4, 30, "Keep 4/5, multiply by 1/6, cross-cancel 4 & 6 (÷ 2), and simplify."),
    divFrac(6, "2/9 ÷ 7", 2, 63, "Keep 2/9 and multiply by the reciprocal of 7 (1/7)."),
    divFrac(7, "1/2 ÷ 8", 1, 16, "Keep 1/2 and multiply by the reciprocal of 8 (1/8)."),
    divFrac(8, "1/5 ÷ 9", 1, 45, "Keep 1/5 and multiply by the reciprocal of 9 (1/9)."),
    divFrac(9, "2/5 ÷ 10", 2, 50, "Keep 2/5, multiply by 1/10, and cross-cancel 2 & 10 (÷ 2)."),
    divFrac(10, "7/9 ÷ 11", 7, 99, "Keep 7/9 and multiply by the reciprocal of 11 (1/11)."),
    divFrac(11, "9/10 ÷ 12", 9, 120, "Keep 9/10, multiply by 1/12, cross-cancel 9 & 12 (÷ 3), and multiply."),
    divFrac(12, "1/4 ÷ 13", 1, 52, "Keep 1/4 and multiply by the reciprocal of 13 (1/13)."),
    divFrac(13, "2/3 ÷ 14", 2, 42, "Keep 2/3, multiply by 1/14, and cross-cancel 2 & 14 (÷ 2)."),
    divFrac(14, "2/9 ÷ 15", 2, 135, "Keep 2/9 and multiply by the reciprocal of 15 (1/15)."),
    divFrac(15, "5 ÷ 5/9", 45, 5, "Keep 5/1, multiply by the reciprocal 9/5, and cross-cancel the 5s!"),
    divFrac(16, "4 ÷ 2/7", 28, 2, "Keep 4/1, multiply by the reciprocal 7/2, and cross-cancel 4 & 2."),
    divFrac(17, "7 ÷ 4/7", 49, 4, "Keep 7/1, multiply by the reciprocal 7/4, and convert to a mixed number."),
    divFrac(18, "1 ÷ 2/9", 9, 2, "Dividing 1 by a fraction gives its reciprocal — write it as a mixed number!"),
    divFrac(19, "4 ÷ 1/2", 8, 1, "Multiply 4 by the reciprocal of 1/2."),
    divFrac(20, "9 ÷ 5/6", 54, 5, "Keep 9/1, multiply by the reciprocal 6/5, and convert to a mixed number."),
    divFrac(21, "5 ÷ 1/2", 10, 1, "Multiply 5 by the reciprocal of 1/2."),
    divFrac(22, "2 ÷ 1/3", 6, 1, "Multiply 2 by the reciprocal of 1/3."),
    divFrac(23, "3 ÷ 1/4", 12, 1, "Multiply 3 by the reciprocal of 1/4."),
    divFrac(24, "6 ÷ 1/5", 30, 1, "Multiply 6 by the reciprocal of 1/5."),
    divFrac(25, "5 ÷ 5/6", 30, 5, "Keep 5/1, multiply by the reciprocal 6/5, and cross-cancel the 5s!"),
    divFrac(26, "9 ÷ 2/7", 63, 2, "Keep 9/1, multiply by the reciprocal 7/2, and write as a mixed number."),
    divFrac(27, "1 ÷ 5/8", 8, 5, "Dividing 1 by 5/8 gives its reciprocal — convert to a mixed number!"),
    divFrac(28, "5 ÷ 1/9", 45, 1, "Multiply 5 by the reciprocal of 1/9."),
    divFrac(29, "2 ÷ 3/10", 20, 3, "Keep 2/1, multiply by the reciprocal 10/3, and write as a mixed number."),
    divFrac(30, "7 ÷ 2/11", 77, 2, "Keep 7/1, multiply by the reciprocal 11/2, and write as a mixed number."),
  )

  val ws22: List<MathProblem> = listOf(
    divFrac(1, "2 ÷ 1/8", 16, 1, "Keep 2, change ÷ to ×, and multiply by the reciprocal of 1/8."),
    divFrac(2, "3 ÷ 3/5", 15, 3, "Keep 3, multiply by the reciprocal 5/3, and cross-cancel 3s!"),
    divFrac(3, "2 ÷ 2/3", 6, 2, "Keep 2, multiply by the reciprocal 3/2, and cross-cancel 2s!"),
    divFrac(4, "3 ÷ 1/2", 6, 1, "Keep 3, change ÷ to ×, and multiply by the reciprocal of 1/2."),
    divFrac(5, "4 ÷ 3/7", 28, 3, "Keep 4, multiply by the reciprocal 7/3, and write as a mixed number."),
    divFrac(6, "4 ÷ 5/8", 32, 5, "Keep 4, multiply by the reciprocal 8/5, and write as a mixed number."),
    divFrac(7, "1/10 ÷ 4", 1, 40, "Keep 1/10 and multiply by the reciprocal of 4 (1/4)."),
    divFrac(8, "1/8 ÷ 3", 1, 24, "Keep 1/8 and multiply by the reciprocal of 3 (1/3)."),
    divFrac(9, "4/5 ÷ 3", 4, 15, "Keep 4/5 and multiply by the reciprocal of 3 (1/3)."),
    divFrac(10, "1/7 ÷ 4", 1, 28, "Keep 1/7 and multiply by the reciprocal of 4 (1/4)."),
    divFrac(11, "7/9 ÷ 3", 7, 27, "Keep 7/9 and multiply by the reciprocal of 3 (1/3)."),
    divFrac(12, "7/8 ÷ 5", 7, 40, "Keep 7/8 and multiply by the reciprocal of 5 (1/5)."),
    divFrac(13, "4 ÷ 1/2", 8, 1, "Keep 4 and multiply by the reciprocal of 1/2."),
    divFrac(14, "3 ÷ 2/7", 21, 2, "Keep 3, multiply by the reciprocal 7/2, and write as a mixed number."),
    divFrac(15, "5 ÷ 3/10", 50, 3, "Keep 5, multiply by the reciprocal 10/3, and write as a mixed number."),
    divFrac(16, "6 ÷ 4/9", 54, 4, "Keep 6, multiply by 9/4, cross-cancel 6 & 4 (÷ 2), and write as a mixed number."),
    divFrac(17, "5 ÷ 4/5", 25, 4, "Keep 5, multiply by the reciprocal 5/4, and write as a mixed number."),
    divFrac(18, "3 ÷ 1/3", 9, 1, "Keep 3 and multiply by the reciprocal of 1/3."),
    divFrac(19, "7 ÷ 7/10", 70, 7, "Keep 7, multiply by 10/7, and cross-cancel the 7s!"),
    divFrac(20, "8 ÷ 5/9", 72, 5, "Keep 8, multiply by the reciprocal 9/5, and write as a mixed number."),
    divFrac(21, "6 ÷ 7/8", 48, 7, "Keep 6, multiply by the reciprocal 8/7, and write as a mixed number."),
    divFrac(22, "1/2 ÷ 8", 1, 16, "Keep 1/2 and multiply by the reciprocal of 8 (1/8)."),
    divFrac(23, "1/8 ÷ 3", 1, 24, "Keep 1/8 and multiply by the reciprocal of 3 (1/3)."),
    divFrac(24, "5/7 ÷ 8", 5, 56, "Keep 5/7 and multiply by the reciprocal of 8 (1/8)."),
    divFrac(25, "2/3 ÷ 7", 2, 21, "Keep 2/3 and multiply by the reciprocal of 7 (1/7)."),
    divFrac(26, "5/9 ÷ 6", 5, 54, "Keep 5/9 and multiply by the reciprocal of 6 (1/6)."),
    divFrac(27, "3/8 ÷ 6", 3, 48, "Keep 3/8, multiply by 1/6, and cross-cancel 3 & 6 (÷ 3)."),
    divFrac(28, "3/10 ÷ 4", 3, 40, "Keep 3/10 and multiply by the reciprocal of 4 (1/4)."),
    divFrac(29, "3/8 ÷ 5", 3, 40, "Keep 3/8 and multiply by the reciprocal of 5 (1/5)."),
    divFrac(30, "5/8 ÷ 9", 5, 72, "Keep 5/8 and multiply by the reciprocal of 9 (1/9)."),
  )

  val ws23: List<MathProblem> = listOf(
    divFrac(1, "2/7 ÷ 1/2", 4, 7, "Keep 2/7, change ÷ to ×, and flip 1/2."),
    divFrac(2, "5/9 ÷ 1/3", 15, 9, "Keep 5/9, flip 1/3, cross-cancel 3 & 9, and write as a mixed number."),
    divFrac(3, "3/8 ÷ 2/5", 15, 16, "Keep 3/8, change ÷ to ×, and flip 2/5."),
    divFrac(4, "2/5 ÷ 8", 2, 40, "Keep 2/5, multiply by 1/8, and cross-cancel 2 & 8 (÷ 2)."),
    divFrac(5, "3/7 ÷ 10", 3, 70, "Keep 3/7 and multiply by the reciprocal of 10 (1/10)."),
    divFrac(6, "3/11 ÷ 12", 3, 132, "Keep 3/11, multiply by 1/12, and cross-cancel 3 & 12 (÷ 3)."),
    divFrac(7, "4 ÷ 1/7", 28, 1, "Keep 4 and multiply by the reciprocal of 1/7."),
    divFrac(8, "2 ÷ 5/9", 18, 5, "Keep 2, multiply by the reciprocal 9/5, and write as a mixed number."),
    divFrac(9, "7 ÷ 2/3", 21, 2, "Keep 7, multiply by the reciprocal 3/2, and write as a mixed number."),
    divFrac(10, "2/3 ÷ 5", 2, 15, "Keep 2/3 and multiply by the reciprocal of 5 (1/5)."),
    divFrac(11, "3/5 ÷ 5", 3, 25, "Keep 3/5 and multiply by the reciprocal of 5 (1/5)."),
    divFrac(12, "3 ÷ 2/7", 21, 2, "Keep 3, multiply by the reciprocal 7/2, and write as a mixed number."),
    divFrac(13, "4 ÷ 5/6", 24, 5, "Keep 4, multiply by the reciprocal 6/5, and write as a mixed number."),
    divFrac(14, "2/3 ÷ 4", 2, 12, "Keep 2/3, multiply by 1/4, and cross-cancel 2 & 4 (÷ 2)."),
    divFrac(15, "9 ÷ 5/8", 72, 5, "Keep 9, multiply by the reciprocal 8/5, and write as a mixed number."),
    divFrac(16, "1/10 ÷ 4", 1, 40, "Keep 1/10 and multiply by the reciprocal of 4 (1/4)."),
    divFrac(17, "3 ÷ 1/8", 24, 1, "Keep 3 and multiply by the reciprocal of 1/8."),
    divFrac(18, "2/5 ÷ 8", 2, 40, "Keep 2/5, multiply by 1/8, and cross-cancel 2 & 8 (÷ 2)."),
    divFrac(19, "10 ÷ 1/7", 70, 1, "Keep 10 and multiply by the reciprocal of 1/7."),
    divFrac(20, "7/9 ÷ 8", 7, 72, "Keep 7/9 and multiply by the reciprocal of 8 (1/8)."),
    // 21-30 Word problems
    divFrac(21, "2/3 ÷ 6", 2, 18, "Divide 2/3 ÷ 6 by multiplying 2/3 × 1/6 and simplifying.", "Jessa has 2/3 cake left in her house and she wants to divide it equally among her 6 friends. What part will each friend receive?"),
    divFrac(22, "3 ÷ 1/10", 30, 1, "Divide 3 ÷ 1/10 by multiplying 3 by the reciprocal of 1/10.", "Carla is baking cookies. A batch uses 3 pounds of chocolate chips. The average weight of a chocolate chip is 1/10 pound. How many chocolate chips are in a batch?"),
    divFrac(23, "25 ÷ 1/5", 125, 1, "Divide 25 ÷ 1/5 by multiplying 25 by the reciprocal of 1/5.", "If the total weight of Marie's laundry is 1/5 kilo and she pays ₱25, how much is the laundry per kilo?"),
    divFrac(24, "9/10 ÷ 1/4", 36, 10, "Divide 9/10 ÷ 1/4 by multiplying 9/10 × 4/1 and writing as a simplified mixed number.", "Each block is 1/4 ft high. If the total height of blocks stacked together is 9/10 ft, how many blocks are there?"),
    divFrac(25, "45 min ÷ 8", 45, 8, "Convert 3/4 hour to 45 minutes, then divide 45 ÷ 8 and write as a mixed number or decimal.", "You only have 3/4 hour left for your exam, and you still have 8 questions unanswered. How many minutes should you allot for each question?", listOf(5.625)),
    divFrac(26, "12 ÷ 1/2", 24, 1, "Divide 12 ÷ 1/2 by multiplying 12 by the reciprocal of 1/2.", "Lenny has already written 12 novels. If she writes 1/2 of a novel every month, how many months did she take to finish all 12?"),
    divFrac(27, "8/9 ÷ 1/3", 24, 9, "Divide 8/9 ÷ 1/3 by multiplying 8/9 × 3/1 and writing as a simplified mixed number.", "Icko can memorize a poem in 8/9 minute. Steph can memorize it in 1/3 minute. How many times is Steph faster in memorizing than Icko?"),
    divFrac(28, "40 ÷ 4/5", 200, 4, "Divide 40 ÷ 4/5 by multiplying 40 × 5/4 (cross-cancel 40 & 4 first!).", "A student spends ₱40 at a net café playing for 4/5 hour. How much is the probable hourly rate?"),
    divFrac(29, "10 ÷ 5/8", 80, 5, "Divide 10 ÷ 5/8 by multiplying 10 × 8/5 (cross-cancel 10 & 5 first!).", "The total weight of the books is 10 kg. If each book weighs 5/8 kg, how many books are there?"),
    divFrac(30, "1 1/2 ÷ 1/4", 12, 2, "Convert 1 1/2 to an improper fraction, then multiply by the reciprocal of 1/4.", "How many small bottles will you need if each bottle holds 1/4 tablespoon of salt and you have 1 1/2 tablespoons of salt?"),
  )

  val ws24: List<MathProblem> = listOf(
    divFrac(1, "4/5 ÷ 8/15 ÷ 2/3", 4 * 15 * 3, 5 * 8 * 2, "Keep 4/5, then multiply by the reciprocals (15/8 and 3/2) — cross-cancel before multiplying!"),
    divFrac(2, "1/3 ÷ 4/5 ÷ 10/21", 21 * 5, 3 * 4 * 10, "Keep 1/3, then multiply by the reciprocals (5/4 and 21/10) — cross-cancel common factors!"),
    divFrac(3, "1/5 ÷ 3/4 ÷ 8/15", 4 * 15, 5 * 3 * 8, "Keep 1/5, then multiply by the reciprocals (4/3 and 15/8) — cross-cancel common factors!"),
    divFrac(4, "1/2 ÷ 5/6 ÷ 10/11", 6 * 11, 2 * 5 * 10, "Keep 1/2, then multiply by the reciprocals (6/5 and 11/10) — cross-cancel 6 & 2 first!"),
    divFrac(5, "5/6 ÷ 1/10 ÷ 2/3", 5 * 10 * 3, 6 * 2, "Keep 5/6, then multiply by the reciprocals (10/1 and 3/2) — cross-cancel and write as a mixed number."),
    divFrac(6, "1/6 ÷ 3/8 ÷ 4/7", 8 * 7, 6 * 3 * 4, "Keep 1/6, then multiply by the reciprocals (8/3 and 7/4) — cross-cancel 8 & 4 first!"),
    divFrac(7, "11/12 ÷ 1/9 ÷ 11/14", 11 * 9 * 14, 12 * 11, "Keep 11/12, then multiply by the reciprocals (9/1 and 14/11) — cross-cancel 11s, 9 & 12, etc.!"),
    divFrac(8, "1/15 ÷ 3/5 ÷ 3/7", 5 * 7, 15 * 3 * 3, "Keep 1/15, then multiply by the reciprocals (5/3 and 7/3) — cross-cancel 5 & 15 first!"),
    divFrac(9, "2/3 ÷ 3/14 ÷ 9/10", 2 * 14 * 10, 3 * 3 * 9, "Keep 2/3, then multiply by 14/3 and 10/9, and write as a mixed number."),
    divFrac(10, "3/4 ÷ 2/3 ÷ 1/9", 3 * 3 * 9, 4 * 2, "Keep 3/4, multiply by 3/2 and 9/1, then convert to a mixed number."),
    divFrac(11, "1/2 ÷ 2/5 ÷ 3", 5, 2 * 2 * 3, "Keep 1/2, then multiply by the reciprocals (5/2 and 1/3)."),
    divFrac(12, "2 ÷ 5/8 ÷ 3", 2 * 8, 5 * 3, "Keep 2/1, multiply by 8/5 and 1/3, then write as a mixed number."),
    divFrac(13, "5/9 ÷ 3 ÷ 1/3", 5 * 3, 9 * 3, "Keep 5/9, multiply by 1/3 and 3/1, and cross-cancel the 3s!"),
    divFrac(14, "2/3 ÷ 6 ÷ 5", 2, 3 * 6 * 5, "Keep 2/3, multiply by 1/6 and 1/5, and cross-cancel 2 & 6 (÷ 2)."),
    divFrac(15, "3/4 ÷ 4 ÷ 2/5", 3 * 5, 4 * 4 * 2, "Keep 3/4, then multiply by the reciprocals (1/4 and 5/2)."),
    divFrac(16, "2/3 ÷ 4/5 ÷ 2/5", 2 * 5 * 5, 3 * 4 * 2, "Keep 2/3, multiply by 5/4 and 5/2, cross-cancel 2s, and write as a mixed number."),
    divFrac(17, "3 ÷ 5/6 ÷ 4", 3 * 6, 5 * 4, "Keep 3/1, multiply by 6/5 and 1/4, cross-cancel 6 & 4 (÷ 2), and multiply."),
    divFrac(18, "4 ÷ 6/7 ÷ 2/7", 4 * 7 * 7, 6 * 2, "Keep 4/1, multiply by 7/6 and 7/2, cross-cancel common factors, and write as a mixed number."),
    divFrac(19, "8 ÷ 4/5 ÷ 2", 8 * 5, 4 * 2, "Keep 8/1, multiply by 5/4 and 1/2, and cross-cancel 8 with 4 × 2!"),
    divFrac(20, "9 ÷ 3/7 ÷ 3/4", 9 * 7 * 4, 3 * 3, "Keep 9/1, multiply by 7/3 and 4/3, and cross-cancel 9 with 3 × 3!"),
    divFrac(21, "10 ÷ 4/5 ÷ 1/3", 10 * 5 * 3, 4, "Keep 10/1, multiply by 5/4 and 3/1, cross-cancel 10 & 4 (÷ 2), and write as a mixed number."),
    divFrac(22, "5/7 ÷ 3/7 ÷ 5", 5 * 7, 7 * 3 * 5, "Keep 5/7, multiply by 7/3 and 1/5, and cross-cancel 5s and 7s!"),
    divFrac(23, "3/8 ÷ 1/5 ÷ 6", 3 * 5, 8 * 6, "Keep 3/8, multiply by 5/1 and 1/6, and cross-cancel 3 & 6 (÷ 3)."),
    divFrac(24, "2/9 ÷ 8 ÷ 2/5", 2 * 5, 9 * 8 * 2, "Keep 2/9, multiply by 1/8 and 5/2, and cross-cancel the 2s!"),
    divFrac(25, "5/6 ÷ 10 ÷ 3/4", 5 * 4, 6 * 10 * 3, "Keep 5/6, multiply by 1/10 and 4/3, and cross-cancel common factors before multiplying!"),
  )
}
