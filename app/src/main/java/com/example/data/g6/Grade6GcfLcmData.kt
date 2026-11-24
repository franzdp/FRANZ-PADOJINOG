package com.example.data.g6

import com.example.model.GcfLcmProblem
import com.example.model.MathProblem
import com.example.model.MathProblem.NumericFieldSpec
import com.example.model.TimeConvertProblem

object Grade6GcfLcmData {

  private fun gcd(a: Int, b: Int): Int {
    var x = a
    var y = b
    while (y != 0) {
      val t = x % y
      x = y
      y = t
    }
    return x
  }

  private fun lcm2(a: Int, b: Int): Int = (a / gcd(a, b)) * b
  private fun gcdList(nums: List<Int>): Int = nums.reduce(::gcd)
  private fun lcmList(nums: List<Int>): Int = nums.reduce(::lcm2)

  private fun gcfOnly(item: Int, vararg nums: Int): GcfLcmProblem {
    val list = nums.toList()
    val g = gcdList(list)
    val l = lcmList(list)
    return GcfLcmProblem(
      itemNumber = item,
      numbers = list,
      askGcf = true,
      askLcm = false,
      expectedGcf = g,
      expectedLcm = l,
      hint = "Divide ${list.joinToString(" and ")} by common prime factors until quotients have no common factor (>1), then multiply the divisors.",
    )
  }

  private fun lcmOnly(item: Int, vararg nums: Int, givenGcf: Int? = null): GcfLcmProblem {
    val list = nums.toList()
    val g = givenGcf ?: gcdList(list)
    val l = lcmList(list)
    val hintText = if (givenGcf != null) {
      "Use LCM = (${list[0]} × ${list[1]}) ÷ $givenGcf."
    } else {
      "Divide by common prime factors (even of 2 numbers, bringing down the other), then multiply all divisors and final quotients."
    }
    return GcfLcmProblem(
      itemNumber = item,
      numbers = list,
      askGcf = false,
      askLcm = true,
      givenGcf = givenGcf,
      expectedGcf = g,
      expectedLcm = l,
      hint = hintText,
    )
  }

  private fun bothShortcut(item: Int, a: Int, b: Int): GcfLcmProblem {
    val g = gcd(a, b)
    val l = lcm2(a, b)
    return GcfLcmProblem(
      itemNumber = item,
      numbers = listOf(a, b),
      askGcf = true,
      askLcm = true,
      showFactorShortcut = true,
      expectedGcf = g,
      expectedLcm = l,
      hint = "Check if $a divides $b evenly: when the smaller number is a factor of the larger number, the GCF is the smaller number and the LCM is the larger number!",
    )
  }

  private fun table11(item: Int, a: Int, b: Int): MathProblem.WordProblemMultiFieldProblem {
    val prod = a * b
    val g = gcd(a, b)
    val l = lcm2(a, b)
    return MathProblem.WordProblemMultiFieldProblem(
      itemNumber = item,
      promptText = "Numbers: $a and $b\nFill in the Product, GCF, and LCM.",
      fields = listOf(
        NumericFieldSpec("Product", prod.toDouble()),
        NumericFieldSpec("GCF", g.toDouble()),
        NumericFieldSpec("LCM", l.toDouble()),
      ),
      hint = "First multiply $a × $b for Product, find GCF by factoring, then compute LCM = Product ÷ GCF.",
    )
  }

  val ws9: List<MathProblem> = listOf(
    gcfOnly(1, 10, 50), gcfOnly(2, 16, 40), gcfOnly(3, 28, 40), gcfOnly(4, 36, 52),
    gcfOnly(5, 20, 50), gcfOnly(6, 35, 50), gcfOnly(7, 18, 27), gcfOnly(8, 26, 40),
    gcfOnly(9, 50, 80), gcfOnly(10, 56, 60), gcfOnly(11, 24, 60), gcfOnly(12, 48, 72),
    gcfOnly(13, 32, 44), gcfOnly(14, 110, 150), gcfOnly(15, 36, 51), gcfOnly(16, 28, 40),
    gcfOnly(17, 49, 77), gcfOnly(18, 120, 150), gcfOnly(19, 90, 135), gcfOnly(20, 150, 200),
  )

  val ws10: List<MathProblem> = listOf(
    lcmOnly(1, 10, 15, 30), lcmOnly(2, 24, 32, 40), lcmOnly(3, 40, 24, 48),
    lcmOnly(4, 27, 36, 54), lcmOnly(5, 16, 20, 24), lcmOnly(6, 9, 12, 18),
    lcmOnly(7, 12, 16, 14), lcmOnly(8, 14, 18, 30), lcmOnly(9, 9, 18, 27),
    lcmOnly(10, 12, 16, 20), lcmOnly(11, 8, 20, 28), lcmOnly(12, 10, 25, 30),
    lcmOnly(13, 12, 20, 24), lcmOnly(14, 15, 18, 21), lcmOnly(15, 12, 15, 18),
    lcmOnly(16, 28, 30, 32), lcmOnly(17, 25, 30, 50), lcmOnly(18, 22, 34, 44),
  )

  val ws11: List<MathProblem> = listOf(
    lcmOnly(1, 12, 30, givenGcf = 6), lcmOnly(2, 10, 50, givenGcf = 10),
    lcmOnly(3, 9, 12, givenGcf = 3), lcmOnly(4, 14, 24, givenGcf = 2),
    lcmOnly(5, 12, 40, givenGcf = 4), lcmOnly(6, 15, 35, givenGcf = 5),
    lcmOnly(7, 25, 40, givenGcf = 5), lcmOnly(8, 28, 42, givenGcf = 14),
    lcmOnly(9, 40, 60, givenGcf = 20), lcmOnly(10, 15, 85, givenGcf = 5),
    table11(11, 21, 24), table11(12, 15, 25), table11(13, 24, 56), table11(14, 30, 36),
    table11(15, 27, 63), table11(16, 18, 24), table11(17, 28, 40), table11(18, 21, 27),
    table11(19, 20, 55), table11(20, 14, 35),
  )

  val ws12: List<MathProblem> = listOf(
    bothShortcut(1, 10, 30), bothShortcut(2, 27, 54), bothShortcut(3, 25, 50),
    bothShortcut(4, 22, 44), bothShortcut(5, 15, 30), bothShortcut(6, 50, 100),
    bothShortcut(7, 26, 52), bothShortcut(8, 13, 39), bothShortcut(9, 24, 72),
    bothShortcut(10, 4, 28), bothShortcut(11, 15, 30), bothShortcut(12, 48, 144),
    bothShortcut(13, 49, 196), bothShortcut(14, 13, 169), bothShortcut(15, 80, 320),
    bothShortcut(16, 55, 165), bothShortcut(17, 15, 120), bothShortcut(18, 66, 198),
    bothShortcut(19, 35, 105), bothShortcut(20, 25, 175), bothShortcut(21, 19, 114),
    bothShortcut(22, 32, 224),
  )

  val ws13: List<MathProblem> = listOf(
    gcfOnly(1, 44, 48), lcmOnly(2, 44, 48),
    gcfOnly(3, 25, 30), lcmOnly(4, 25, 30),
    gcfOnly(5, 21, 24), lcmOnly(6, 21, 24),
    gcfOnly(7, 39, 42), lcmOnly(8, 39, 42),
    gcfOnly(9, 3, 24), lcmOnly(10, 3, 24),
    gcfOnly(11, 10, 18), lcmOnly(12, 10, 18),
    gcfOnly(13, 15, 24, 27), lcmOnly(14, 15, 24, 27),
    gcfOnly(15, 15, 18, 20), lcmOnly(16, 15, 18, 20),
    gcfOnly(17, 25, 45, 60), lcmOnly(18, 25, 45, 60),
    gcfOnly(19, 13, 27), lcmOnly(20, 13, 27),
    gcfOnly(21, 9, 14, 25), lcmOnly(22, 9, 14, 25),
    gcfOnly(23, 21, 50), lcmOnly(24, 21, 50),
    gcfOnly(25, 6, 17, 35), lcmOnly(26, 6, 17, 35),
    gcfOnly(27, 18, 90), lcmOnly(28, 18, 90),
    gcfOnly(29, 10, 50), lcmOnly(30, 10, 50),
    gcfOnly(31, 23, 92), lcmOnly(32, 23, 92),
    // 33-43 Word problems
    MathProblem.WordProblemMultiFieldProblem(
      33,
      "Melissa wants to make paper bouquets. She has 12 red, 14 yellow and 16 orange colored papers. If each bouquet has equal numbers of flowers per color, how many bouquets can she make at the most?",
      listOf(NumericFieldSpec("Max bouquets", 2.0, "bouquets")),
      "Find the GCF of 12, 14, and 16.",
    ),
    MathProblem.WordProblemMultiFieldProblem(
      34,
      "Using Melissa's 12 red, 14 yellow, and 16 orange flowers split across 2 bouquets: how many flowers of each color are in each bouquet?",
      listOf(
        NumericFieldSpec("Red", 6.0),
        NumericFieldSpec("Yellow", 7.0),
        NumericFieldSpec("Orange", 8.0),
      ),
      "Divide each count by 2 bouquets: 12÷2, 14÷2, 16÷2.",
    ),
    MathProblem.WordProblemMultiFieldProblem(
      35,
      "Three blinking neon signs are turned on at the same time. Each blinks every 6 seconds, 11 seconds and 25 seconds respectively. After how many seconds will they blink together?",
      listOf(NumericFieldSpec("Seconds", 1650.0, "seconds")),
      "Find the LCM of 6, 11, and 25 by multiplying their common and unique prime factors.",
    ),
    MathProblem.WordProblemMultiFieldProblem(
      36,
      "Jona can arrange her toy collections in a box. She can either arrange 15 toys, 20 toys or 18 toys in a box. How many toys can she have at the least?",
      listOf(NumericFieldSpec("Least toys", 180.0, "toys")),
      "Find the LCM of 15, 20, and 18.",
    ),
    MathProblem.WordProblemMultiFieldProblem(
      37,
      "Cathy is giving donation to calamity victims. She has 12 clothes, 28 packs of goods, and 36 kilos of rice. What is the greatest number of bags she can make equally?",
      listOf(NumericFieldSpec("Greatest bags", 4.0, "bags")),
      "Find the GCF of 12, 28, and 36.",
    ),
    MathProblem.WordProblemMultiFieldProblem(
      38,
      "Cathy divides 12 clothes, 28 packs of goods, and 36 kilos of rice equally into 4 bags. How many of each item will go into one bag?",
      listOf(
        NumericFieldSpec("Clothes", 3.0),
        NumericFieldSpec("Goods packs", 7.0),
        NumericFieldSpec("Kilos rice", 9.0),
      ),
      "Divide 12÷4, 28÷4, and 36÷4.",
    ),
    MathProblem.WordProblemMultiFieldProblem(
      39,
      "Teachers will make loot bags. Stickers come in sheets of 9, glitter pens in packs of 12, and they plan to buy 36 water bottles so each bag has equal items. At least how many stickers should they buy?",
      listOf(NumericFieldSpec("Stickers (pieces)", 36.0, "stickers", alternateAnswers = listOf(4.0))),
      "Find the LCM of 9, 12, and 36 so each loot bag receives the same number of items.",
    ),
    MathProblem.WordProblemMultiFieldProblem(
      40,
      "In the same loot-bag problem (36 water bottles, stickers in sheets of 9, glitter pens in packs of 12): at least how many glitter pens should they buy?",
      listOf(NumericFieldSpec("Glitter pens (pieces)", 36.0, "pens", alternateAnswers = listOf(3.0))),
      "Find the LCM of 9, 12, and 36 to match the equal loot-bag total!",
    ),
    MathProblem.WordProblemMultiFieldProblem(
      41,
      "Elena has 18 apples, 27 pears, and 21 bananas. She wants to make fruit baskets with the same number of each fruit. How many fruit baskets can she make at the most?",
      listOf(NumericFieldSpec("Max baskets", 3.0, "baskets")),
      "Find the GCF of 18, 27, and 21.",
    ),
    MathProblem.WordProblemMultiFieldProblem(
      42,
      "Elena packs 18 apples, 27 pears, and 21 bananas equally into 3 baskets. How many of each fruit are in one basket?",
      listOf(
        NumericFieldSpec("Apples", 6.0),
        NumericFieldSpec("Pears", 9.0),
        NumericFieldSpec("Bananas", 7.0),
      ),
      "Divide 18÷3, 27÷3, and 21÷3.",
    ),
    TimeConvertProblem(
      itemNumber = 43,
      prompt = "Cagsawa Liner stops every 3 hrs, Isarog Lines every 4 hrs, and Penafrancia Tours every 2 hrs on a 12-hour trip to Bicol. If they left Cubao together at 12:00 NN, what time will they all be at a bus stop at the same time?",
      sourceDisplay = "Depart: 12:00 NN | Stops every 3h, 4h, 2h",
      to24Hour = false,
      validAnswers = listOf("12:00 AM", "12:00 MN", "00:00", "0000"),
      displayAnswer = "12:00 AM",
      hint = "Find LCM(3, 4, 2) hours, then add those hours starting from 12:00 NN (remember: 12 hours after noon is midnight!).",
    ),
  )
}
