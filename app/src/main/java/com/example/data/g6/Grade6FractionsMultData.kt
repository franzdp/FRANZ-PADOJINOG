package com.example.data.g6

import com.example.model.FractionOpProblem
import com.example.model.MathProblem

object Grade6FractionsMultData {

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

  private fun multArea(item: Int, na: Int, da: Int, nb: Int, db: Int): FractionOpProblem {
    val rn = na * nb
    val rd = da * db
    val g = gcd(rn, rd)
    return FractionOpProblem(
      itemNumber = item,
      expressionDisplay = "$na/$da × $nb/$db",
      expectedNum = rn / g,
      expectedDen = rd / g,
      showAreaModel = true,
      modelNumA = na,
      modelDenA = da,
      modelNumB = nb,
      modelDenB = db,
      isDivisionModel = false,
      hint = "Shade $na of $da columns and $nb of $db rows: count the overlapped cells out of ${da * db} total cells, then simplify to lowest terms!",
    )
  }

  private fun multFrac(item: Int, expr: String, rawNum: Int, rawDen: Int, hint: String): FractionOpProblem {
    val g = gcd(rawNum, rawDen)
    return FractionOpProblem(
      itemNumber = item,
      expressionDisplay = expr,
      expectedNum = rawNum / g,
      expectedDen = rawDen / g,
      hint = hint,
    )
  }

  private fun multWord(item: Int, prompt: String, expr: String, rawNum: Int, rawDen: Int, hint: String): FractionOpProblem {
    val g = gcd(rawNum, rawDen)
    return FractionOpProblem(
      itemNumber = item,
      expressionDisplay = expr,
      expectedNum = rawNum / g,
      expectedDen = rawDen / g,
      promptText = prompt,
      hint = hint,
    )
  }

  val ws14: List<MathProblem> = listOf(
    multArea(1, 1, 3, 1, 3),
    multArea(2, 2, 4, 6, 8),
    multArea(3, 3, 5, 3, 4),
    multArea(4, 6, 7, 2, 3),
    multArea(5, 2, 3, 1, 2),
    multArea(6, 3, 6, 3, 5),
    multArea(7, 6, 7, 4, 5),
    multArea(8, 8, 9, 2, 3),
  )

  val ws15: List<MathProblem> = listOf(
    multFrac(1, "5/7 × 1/4", 5, 28, "Multiply numerator × numerator and denominator × denominator."),
    multFrac(2, "3/4 × 1/4", 3, 16, "Multiply the numerators across the top and the denominators across the bottom."),
    multFrac(3, "6/7 × 4/5", 24, 35, "Multiply numerator × numerator and denominator × denominator."),
    multFrac(4, "1/2 × 3/6", 3, 12, "Multiply straight across (or simplify 3/6 first), then reduce to lowest terms!"),
    multFrac(5, "1/2 × 3/6", 3, 12, "Multiply straight across (or simplify 3/6 first), then reduce to lowest terms!"),
    multFrac(6, "2/10 × 1/9", 2, 90, "Multiply straight across (or simplify 2/10 first), then reduce to lowest terms!"),
    multFrac(7, "1/3 × 2/3", 2, 9, "Multiply the numerators across the top and the denominators across the bottom."),
    multFrac(8, "6/7 × 1/7", 6, 49, "Multiply numerator × numerator and denominator × denominator."),
    multFrac(9, "1/6 × 1/13", 1, 78, "Multiply the numerators (1 × 1) and the denominators (6 × 13)."),
    multFrac(10, "1/8 × 3/7", 3, 56, "Multiply numerator × numerator and denominator × denominator."),
    multFrac(11, "2/9 × 1/11", 2, 99, "Multiply numerator × numerator and denominator × denominator."),
    multFrac(12, "3/10 × 7/8", 21, 80, "Multiply numerator × numerator and denominator × denominator."),
    multFrac(13, "5/7 × 2/11", 10, 77, "Multiply numerator × numerator and denominator × denominator."),
    multFrac(14, "2/3 × 4/9", 8, 27, "Multiply numerator × numerator and denominator × denominator."),
    multFrac(15, "1/4 × 2/7", 2, 28, "Cross-cancel 2 and 4 first (÷ 2), multiply, and simplify to lowest terms."),
    // 16-30 cross cancellation
    multFrac(16, "2/9 × 3/4", 6, 36, "Cross-cancel 2 & 4 (÷ 2) and 3 & 9 (÷ 3), then multiply straight across."),
    multFrac(17, "2/10 × 2/3", 4, 30, "Simplify 2/10 first (or multiply straight across), then reduce to lowest terms."),
    multFrac(18, "1/4 × 2/3", 2, 12, "Cross-cancel 2 and 4 (÷ 2) before multiplying."),
    multFrac(19, "1/3 × 3/5", 3, 15, "Cross-cancel the common factor 3 in numerator and denominator, then multiply."),
    multFrac(20, "1/2 × 2/5", 2, 10, "Cross-cancel the common factor 2 in numerator and denominator, then multiply."),
    multFrac(21, "2/3 × 1/2", 2, 6, "Cross-cancel the common factor 2 in numerator and denominator, then multiply."),
    multFrac(22, "5/10 × 4/5", 20, 50, "Cross-cancel 5 & 5 and simplify 4/10 to lowest terms."),
    multFrac(23, "1/3 × 3/4", 3, 12, "Cross-cancel the common factor 3 in numerator and denominator, then multiply."),
    multFrac(24, "2/3 × 1/2", 2, 6, "Cross-cancel the common factor 2 in numerator and denominator, then multiply."),
    multFrac(25, "4/5 × 3/4", 12, 20, "Cross-cancel the common factor 4 in numerator and denominator, then multiply."),
    multFrac(26, "3/4 × 2/5", 6, 20, "Cross-cancel 2 and 4 (÷ 2) before multiplying."),
    multFrac(27, "4/5 × 1/2", 4, 10, "Cross-cancel 4 and 2 (÷ 2) before multiplying."),
    multFrac(28, "2/15 × 3/16", 6, 240, "Cross-cancel 2 & 16 (÷ 2) and 3 & 15 (÷ 3), then multiply."),
    multFrac(29, "5/18 × 3/10", 15, 180, "Cross-cancel 5 & 10 (÷ 5) and 3 & 18 (÷ 3), then multiply."),
    multFrac(30, "3/5 × 10/18", 30, 90, "Cross-cancel 3 & 18 (÷ 3) and 10 & 5 (÷ 5), multiply, and simplify!"),
  )

  val ws16: List<MathProblem> = listOf(
    multFrac(1, "9 × 2/5", 18, 5, "Write 9 as 9/1, multiply across, and convert the improper fraction to a mixed number."),
    multFrac(2, "5 × 1/4", 5, 4, "Write 5 as 5/1, multiply across, and convert the improper fraction to a mixed number."),
    multFrac(3, "2 × 1/2", 2, 2, "Write 2 as 2/1 and cross-cancel 2 with 2 (or simplify 2/2)."),
    multFrac(4, "8 × 1/2", 8, 2, "Write 8 as 8/1, multiply across, and divide numerator by denominator."),
    multFrac(5, "3 × 2/3", 6, 3, "Write 3 as 3/1 and cross-cancel 3 with 3."),
    multFrac(6, "10 × 1/2", 10, 2, "Find half of 10 (cross-cancel 10 and 2 by 2)."),
    multFrac(7, "2 × 4/9", 8, 9, "Write 2 as 2/1 and multiply the numerators over 9."),
    multFrac(8, "3/9 × 16", 48, 9, "Simplify 3/9 first, multiply by 16/1, then convert to a mixed number."),
    multFrac(9, "11/14 × 21", 231, 14, "Cross-cancel 21 and 14 by 7, multiply, then write as a mixed number."),
    multFrac(10, "8/14 × 24", 192, 14, "Simplify 8/14 first (÷ 2), multiply by 24/1, then convert to a mixed number."),
    multFrac(11, "2/5 × 18", 36, 5, "Write 18 as 18/1, multiply straight across, then convert to a mixed number."),
    multFrac(12, "4/7 × 6", 24, 7, "Write 6 as 6/1, multiply straight across, then convert to a mixed number."),
    multFrac(13, "5 × 2/5", 10, 5, "Write 5 as 5/1 and cross-cancel 5 with 5."),
    multFrac(14, "10 × 3/20", 30, 20, "Cross-cancel 10 and 20 (÷ 10), then write as a mixed number."),
    multFrac(15, "4/6 × 9", 36, 6, "Simplify 4/6 (or multiply 4 × 9 then divide by 6)."),
    // 16-30 mixed × mixed
    multFrac(16, "2 1/2 × 4 2/5", 5 * 22, 2 * 5, "Convert both mixed numbers to improper fractions first, cross-cancel, and multiply!"),
    multFrac(17, "2 1/2 × 3 4/5", 5 * 19, 2 * 5, "Convert both mixed numbers to improper fractions, cross-cancel 5s, and write as a mixed number."),
    multFrac(18, "2 1/5 × 3 1/4", 11 * 13, 5 * 4, "Convert both mixed numbers to improper fractions, multiply, and write as a mixed number."),
    multFrac(19, "3 4/5 × 4 1/2", 19 * 9, 5 * 2, "Convert both mixed numbers to improper fractions, multiply, and write as a mixed number."),
    multFrac(20, "3 1/3 × 2 9/10", 10 * 29, 3 * 10, "Convert both mixed numbers to improper fractions, cross-cancel 10s, and simplify!"),
    multFrac(21, "4 4/5 × 2 7/10", 24 * 27, 5 * 10, "Convert both mixed numbers to improper fractions, cross-cancel 24 & 10, multiply, and write as a mixed number."),
    multFrac(22, "4 4/5 × 2 1/3", 24 * 7, 5 * 3, "Convert both mixed numbers to improper fractions, cross-cancel 24 & 3, multiply, and simplify!"),
    multFrac(23, "2 2/5 × 2 1/2", 12 * 5, 5 * 2, "Convert both mixed numbers to improper fractions, cross-cancel common factors, and simplify!"),
    multFrac(24, "4 1/3 × 3 1/2", 13 * 7, 3 * 2, "Convert both mixed numbers to improper fractions, multiply, and write as a mixed number."),
    multFrac(25, "3 1/4 × 2 5/9", 13 * 23, 4 * 9, "Convert both mixed numbers to improper fractions, multiply, and write as a mixed number."),
    multFrac(26, "3 5/8 × 4 1/2", 29 * 9, 8 * 2, "Convert both mixed numbers to improper fractions, multiply, and write as a mixed number."),
    multFrac(27, "5 4/5 × 2 2/3", 29 * 8, 5 * 3, "Convert both mixed numbers to improper fractions, multiply, and write as a mixed number."),
    multFrac(28, "5 1/2 × 4 1/2", 11 * 9, 2 * 2, "Convert both mixed numbers to improper fractions, multiply, and write as a mixed number."),
    multFrac(29, "2 4/7 × 3 8/9", 18 * 35, 7 * 9, "Convert both mixed numbers to improper fractions, cross-cancel 18 & 9 and 35 & 7, then multiply!"),
    multFrac(30, "6 1/2 × 3 2/5", 13 * 17, 2 * 5, "Convert both mixed numbers to improper fractions, multiply, and write as a mixed number."),
  )

  val ws17: List<MathProblem> = listOf(
    multFrac(1, "1/2 × 1/9", 1, 18, "Multiply straight across: numerator × numerator over denominator × denominator."),
    multFrac(2, "3/11 × 5/13", 15, 143, "Multiply numerator × numerator and denominator × denominator."),
    multFrac(3, "5/13 × 3/7", 15, 91, "Multiply numerator × numerator and denominator × denominator."),
    multFrac(4, "5/7 × 9/11", 45, 77, "Multiply numerator × numerator and denominator × denominator."),
    multFrac(5, "3/10 × 4/9", 12, 90, "Cross-cancel 3 & 9 (÷ 3) and 4 & 10 (÷ 2), then multiply."),
    multFrac(6, "2/3 × 9/14", 18, 42, "Cross-cancel 2 & 14 (÷ 2) and 9 & 3 (÷ 3), then multiply."),
    multFrac(7, "5/8 × 10/25", 50, 200, "Cross-cancel 5 & 25 (÷ 5) and 10 & 8 (÷ 2), then multiply and simplify!"),
    multFrac(8, "6/18 × 3/4", 18, 72, "Simplify 6/18 to lowest terms first, then cross-cancel 3s and multiply."),
    multFrac(9, "3/4 × 4/9", 12, 36, "Cross-cancel 4 & 4 and 3 & 9 (÷ 3), then multiply."),
    multFrac(10, "3/5 × 1/18", 3, 90, "Cross-cancel 3 and 18 (÷ 3), then multiply."),
    multFrac(11, "3/7 × 4", 12, 7, "Write 4 as 4/1, multiply straight across, and convert to a mixed number."),
    multFrac(12, "18 × 2/9", 36, 9, "Write 18 as 18/1 and cross-cancel 18 and 9 (÷ 9) before multiplying."),
    multFrac(13, "20 × 4/5", 80, 5, "Write 20 as 20/1 and cross-cancel 20 and 5 (÷ 5) before multiplying."),
    multFrac(14, "5/12 × 30", 150, 12, "Cross-cancel 30 and 12 by 6, multiply, then write as a mixed number."),
    multFrac(15, "6/8 × 14", 84, 8, "Simplify 6/8 first (÷ 2), cross-cancel with 14 (÷ 2), multiply, and write as a mixed number."),
    multFrac(16, "2 4/9 × 4 1/8", 22 * 33, 9 * 8, "Convert both mixed numbers to improper fractions, cross-cancel (÷ 2 and ÷ 3), multiply, and write as a mixed number."),
    multFrac(17, "3 1/5 × 3 1/4", 16 * 13, 5 * 4, "Convert both mixed numbers to improper fractions, cross-cancel 16 & 4, multiply, and write as a mixed number."),
    multFrac(18, "5 2/9 × 4 7/8", 47 * 39, 9 * 8, "Convert both mixed numbers to improper fractions, cross-cancel 39 & 9 (÷ 3), multiply, and write as a mixed number."),
    multFrac(19, "3 1/4 × 3 6/7", 13 * 27, 4 * 7, "Convert both mixed numbers to improper fractions, multiply, and write as a mixed number."),
    multFrac(20, "3 5/6 × 6 1/2", 23 * 13, 6 * 2, "Convert both mixed numbers to improper fractions, multiply, and write as a mixed number."),
    multWord(21, "1/4 of 2 1/2 is what number?", "1/4 × 2 1/2", 5, 8, "Convert 2 1/2 to an improper fraction, then multiply by 1/4."),
    multWord(22, "What is the product of 2 2/3 and 3/4?", "2 2/3 × 3/4", 24, 12, "Convert 2 2/3 to an improper fraction, cross-cancel common factors with 3/4, and multiply!"),
    multWord(23, "If you triple 2 4/5, what number will you get?", "3 × 2 4/5", 42, 5, "Convert 2 4/5 to an improper fraction, multiply by 3/1, and write as a mixed number."),
    multWord(24, "Harald has three buckets of fries. Each bucket contains 10 2/3 fries. How many fries does Harald have?", "3 × 10 2/3", 96, 3, "Convert 10 2/3 to an improper fraction and cross-cancel the 3s!"),
    multWord(25, "On Monday a team of street sweepers cleaned 3/5 of a city block. Tuesday, the team cleaned 2 2/5 times as many blocks as on Monday. How many city blocks did they clean on Tuesday?", "2 2/5 × 3/5", 36, 25, "Convert 2 2/5 to an improper fraction, multiply by 3/5, and write as a mixed number."),
    multWord(26, "Dawn has 3 1/2 cookies. Each costs 5. What is the cost of all the cookies?", "3 1/2 × 5", 35, 2, "Convert 3 1/2 to an improper fraction, multiply by 5/1, and write as a mixed number."),
    multWord(27, "Anna's height is 1 3/5 of Jenny's height. Jenny is 5 1/2 feet tall. What is Anna's height?", "1 3/5 × 5 1/2", 88, 10, "Convert 1 3/5 and 5 1/2 to improper fractions, cross-cancel (÷ 2), multiply, and write as a mixed number."),
    multWord(28, "Gino's score is 2 1/8 of Jona's score. Jona scored 56 1/4. What is Gino's score?", "2 1/8 × 56 1/4", 17 * 225, 32, "Convert 2 1/8 and 56 1/4 to improper fractions, multiply, and write as a mixed number."),
    multWord(29, "Allan's weight is 3 1/2 times heavier than Nico's weight. Nico's weight is 50 1/4 kg. What is Allan's weight?", "3 1/2 × 50 1/4", 7 * 201, 8, "Convert 3 1/2 and 50 1/4 to improper fractions, multiply, and write as a mixed number."),
    multWord(30, "Bambi's foot size is 1 1/2 of Dona's foot size. Dona's foot size is 5 1/2 inches. What is Bambi's foot size?", "1 1/2 × 5 1/2", 33, 4, "Convert 1 1/2 and 5 1/2 to improper fractions, multiply, and write as a mixed number."),
  )

  val ws18: List<MathProblem> = listOf(
    multFrac(1, "3/4 × 2/7 × 2/9", 12, 252, "Cross-cancel 2 × 2 with 4, and 3 with 9 before multiplying!"),
    multFrac(2, "4/5 × 1/4 × 5/6", 20, 120, "Cross-cancel 4 & 4 and 5 & 5 before multiplying!"),
    multFrac(3, "3/4 × 4/7 × 2/6", 24, 168, "Cross-cancel 4 & 4 and (3 × 2) with 6 before multiplying!"),
    multFrac(4, "4/10 × 5/9 × 3/8", 60, 720, "Cross-cancel 4 & 8 (÷ 4), 5 & 10 (÷ 5), and 3 & 9 (÷ 3), then multiply!"),
    multFrac(5, "3/10 × 4/9 × 2/7", 24, 630, "Cross-cancel 3 & 9 (÷ 3) and 2 & 10 (÷ 2), multiply, and simplify to lowest terms."),
    multFrac(6, "2/5 × 1/4 × 5/9", 10, 180, "Cross-cancel 5 & 5 and 2 & 4 (÷ 2), then multiply!"),
    multFrac(7, "3/4 × 1/2 × 6/9", 18, 72, "Simplify 6/9 first, then cross-cancel 3 & 3 and 2 & 2!"),
    multFrac(8, "2/5 × 3/7 × 2/10", 12, 350, "Cross-cancel 2 & 10 (÷ 2), then multiply straight across."),
    multFrac(9, "2/9 × 3/10 × 4/8", 24, 720, "Simplify 4/8 first, then cross-cancel 3 & 9 and 2 & 2 before multiplying!"),
    multFrac(10, "2/7 × 3/5 × 7/9", 42, 315, "Cross-cancel 7 & 7 and 3 & 9 (÷ 3), then multiply!"),
    multFrac(11, "4/10 × 10/20 × 7/8", 280, 1600, "Cross-cancel 10 & 10 and 4 & 8 (÷ 4), then simplify!"),
    multFrac(12, "5 × 3/5 × 3/10", 45, 50, "Write 5 as 5/1 and cross-cancel 5 & 5 before multiplying!"),
    multFrac(13, "6 × 4/6 × 5/8", 120, 48, "Write 6 as 6/1, cross-cancel 6 & 6 and 4 & 8, then write as a mixed number."),
    multFrac(14, "7 × 6/7 × 3/8", 126, 56, "Write 7 as 7/1, cross-cancel 7 & 7, simplify, and write as a mixed number."),
    multFrac(15, "8 × 3/4 × 2/15", 48, 60, "Cross-cancel 8 & 4 and 3 & 15, multiply, and simplify!"),
    multFrac(16, "9 × 1/6 × 8/10", 72, 60, "Cross-cancel common factors (9 & 6 by 3, etc.), multiply, and write as a mixed number."),
    multFrac(17, "3 1/4 × 1/6 × 3/5", 13 * 3, 4 * 30, "Convert 3 1/4 to an improper fraction, cross-cancel 3 & 6 (÷ 3), and multiply!"),
    multFrac(18, "2 1/7 × 5 3/5 × 4 2/10", 15 * 28 * 42, 7 * 5 * 10, "Convert all three mixed numbers to improper fractions, cross-cancel common factors, multiply, and simplify!"),
    multFrac(19, "3 3/4 × 1/3 × 2 2/3", 15 * 8, 4 * 3 * 3, "Convert mixed numbers to improper fractions, cross-cancel common factors, multiply, and write as a mixed number."),
    multFrac(20, "1 1/2 × 2 1/3 × 3 1/4", 3 * 7 * 13, 2 * 3 * 4, "Convert all three mixed numbers to improper fractions, cross-cancel 3 & 3, multiply, and write as a mixed number."),
    multFrac(21, "5 1/2 × 3/8 × 1 2/9", 11 * 3 * 11, 2 * 8 * 9, "Convert mixed numbers to improper fractions, cross-cancel 3 & 9, multiply, and write as a mixed number."),
    multFrac(22, "8 × 1 1/8 × 2 2/3", 8 * 9 * 8, 8 * 3, "Write 8 as 8/1 and convert mixed numbers to improper fractions, then cross-cancel 8s and 9 & 3!"),
    multFrac(23, "9 × 2 3/5 × 1 5/9", 9 * 13 * 14, 5 * 9, "Write 9 as 9/1 and convert mixed numbers to improper fractions, cross-cancel 9 & 9, multiply, and write as a mixed number."),
    multFrac(24, "4/5 × 2 2/5 × 10", 4 * 12 * 10, 5 * 5, "Convert 2 2/5 to an improper fraction and 10 to 10/1, cross-cancel 10 & 5, multiply, and write as a mixed number."),
    multFrac(25, "3/8 × 3 3/4 × 8/9", 3 * 15 * 8, 8 * 4 * 9, "Convert 3 3/4 to an improper fraction, cross-cancel 8 & 8 and 3 & 9, multiply, and simplify!"),
  )
}
