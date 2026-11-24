package com.example.data

import com.example.model.DivisionPartType
import com.example.model.GroupingSceneType
import com.example.model.MathProblem

object DivisionData {

  fun getWorksheet15(): List<MathProblem> {
    val list = mutableListOf<MathProblem>()
    // 1-6 grouping visual items
    list += MathProblem.GroupingDivisionProblem(1, GroupingSceneType.COLOR_PENCILS, 10, 2, 5, "Group the 10 color pencils into groups of two.", "Tap 'Group' to circle 2 items at a time, then count how many groups you formed.")
    list += MathProblem.GroupingDivisionProblem(2, GroupingSceneType.DONUTS, 4, 2, 2, "Group the 4 donuts into groups of two.", "Tap 'Group' to circle 2 items at a time, then count how many groups you formed.")
    list += MathProblem.GroupingDivisionProblem(3, GroupingSceneType.FLOWERS, 12, 6, 2, "Group the 12 flowers into groups of six.", "Tap 'Group' to circle 6 items at a time, then count how many groups you formed.")
    list += MathProblem.GroupingDivisionProblem(4, GroupingSceneType.BALLOONS, 12, 3, 4, "Group the 12 balloons into groups of three.", "Tap 'Group' to circle 3 items at a time, then count how many groups you formed.")
    list += MathProblem.GroupingDivisionProblem(5, GroupingSceneType.CANDIES, 20, 4, 5, "Group the 20 candies into groups of 4.", "Tap 'Group' to circle 4 items at a time, then count how many groups you formed.")
    list += MathProblem.GroupingDivisionProblem(6, GroupingSceneType.BROOMS, 15, 5, 3, "Group the 15 brooms into groups of five.", "Tap 'Group' to circle 5 items at a time, then count how many groups you formed.")

    // 7-17 parts of a division equation
    list += MathProblem.DivisionPartProblem(7, 12, 2, 6, DivisionPartType.QUOTIENT, "In 12 ÷ 2 = 6, what is the quotient?", "The quotient is the result/answer after the '=' sign.")
    list += MathProblem.DivisionPartProblem(8, 12, 2, 6, DivisionPartType.DIVISOR, "In 12 ÷ 2 = 6, what is the divisor?", "The divisor is the number you divide by (right after ÷).")
    list += MathProblem.DivisionPartProblem(9, 12, 2, 6, DivisionPartType.DIVIDEND, "In 12 ÷ 2 = 6, what is the dividend?", "The dividend is the starting total being divided (before ÷).")
    list += MathProblem.DivisionPartProblem(10, 20, 5, 4, DivisionPartType.QUOTIENT, "In 20 divided by 5 equals 4, which of them is the quotient?", "The quotient is the result/answer of the division.")
    list += MathProblem.DivisionPartProblem(11, 20, 5, 4, DivisionPartType.DIVIDEND, "In 20 divided by 5 equals 4, which of them is the dividend?", "The dividend is the starting number being divided.")
    list += MathProblem.DivisionPartProblem(12, 20, 5, 4, DivisionPartType.DIVISOR, "In 20 divided by 5 equals 4, which is the divisor?", "The divisor is the number you are dividing by.")
    list += MathProblem.DivisionPartProblem(13, 30, 6, 5, DivisionPartType.QUOTIENT, "In 30 divided by 6 equals 5, which of them is the quotient?", "The quotient is the result/answer of the division.")
    list += MathProblem.DivisionPartProblem(14, 27, 3, 9, DivisionPartType.QUOTIENT, "In 27 divided by 3 equals 9, which of them is the quotient?", "The quotient is the result/answer of the division.")
    list += MathProblem.DivisionPartProblem(15, 27, 3, 9, DivisionPartType.DIVIDEND, "In 27 divided by 3 equals 9, which of them is the dividend?", "The dividend is the starting number being divided.")
    list += MathProblem.DivisionPartProblem(16, 15, 3, 5, DivisionPartType.DIVISOR, "Which digit is the divisor in the equation: 15 ÷ 3 = 5?", "The divisor is the number right after the ÷ sign.")
    list += MathProblem.DivisionPartProblem(17, 15, 3, 5, DivisionPartType.DIVIDEND, "Which number is the dividend in the equation: 15 ÷ 3 = 5?", "The dividend is the starting number before the ÷ sign.")

    // 18-40 basic division problems
    val basic = listOf(
      5 to 1, 4 to 1, 2 to 1, 3 to 1, 8 to 1, 9 to 1,
      12 to 2, 16 to 4, 7 to 1, 30 to 3, 45 to 5, 63 to 7,
      81 to 9, 48 to 6, 42 to 7, 64 to 8, 72 to 8, 30 to 10,
      16 to 2, 20 to 1, 20 to 10, 27 to 3, 27 to 9,
    )
    basic.forEachIndexed { idx, (a, b) ->
      list += MathProblem.DivisionProblem(
        itemNumber = 18 + idx, dividend = a, divisor = b, quotient = a / b,
        hint = "Think: what number × $b = $a?",
      )
    }
    return list
  }

  fun getWorksheet16(): List<MathProblem> {
    val eqs = listOf(
      32 to 4, 35 to 5, 48 to 6, 36 to 4, 36 to 6, 42 to 6, 45 to 9, 48 to 8, 49 to 7, 63 to 9,
      64 to 8, 18 to 2, 12 to 3, 15 to 3, 12 to 4, 24 to 3, 28 to 4, 20 to 4, 40 to 5, 30 to 6,
      42 to 6, 28 to 7, 56 to 7, 64 to 8, 8 to 4, 8 to 2, 10 to 5, 5 to 5, 12 to 4, 16 to 4,
      15 to 5, 20 to 5, 14 to 2,
    )
    val worded = listOf(
      9 to 3, 4 to 2, 16 to 2, 20 to 5, 50 to 10, 16 to 4,
      27 to 9, 45 to 5, 50 to 5, 8 to 4, 9 to 3, 12 to 2,
    )
    val list = mutableListOf<MathProblem>()
    eqs.forEachIndexed { idx, (a, b) ->
      list += MathProblem.DivisionProblem(
        itemNumber = idx + 1, dividend = a, divisor = b, quotient = a / b,
        hint = "What number × $b = $a?",
      )
    }
    worded.forEachIndexed { idx, (a, b) ->
      list += MathProblem.DivisionProblem(
        itemNumber = 34 + idx, dividend = a, divisor = b, quotient = a / b,
        promptText = "$a divided by $b",
        hint = "$a ÷ $b: what number × $b = $a?",
      )
    }
    return list
  }

  fun getWorksheet17(): List<MathProblem> {
    val pairs = listOf(
      Triple(15, 3, "15 divided by 3"),
      Triple(16, 4, "16 divided by 4"),
      Triple(24, 3, "24 divided by 3"),
      Triple(27, 3, "27 divided by 3"),
      Triple(28, 4, "28 divided by 4"),
      Triple(30, 3, "30 divided by 3"),
      Triple(32, 4, "32 divided by 4"),
      Triple(35, 5, "35 divided by 5"),
      Triple(36, 4, "36 divided by 4"),
      Triple(40, 4, "40 divided by 4"),
      Triple(40, 5, "40 divided by 5"),
      Triple(14, 7, "Divide 14 by 7"),
      Triple(18, 9, "Divide 18 by 9"),
      Triple(24, 6, "Divide 24 by 6"),
      Triple(16, 4, "Divide 16 by 4"),
      Triple(30, 6, "Divide 30 by 6"),
      Triple(35, 7, "Divide 35 by 7"),
      Triple(36, 9, "Divide 36 by 9"),
      Triple(42, 7, "Divide 42 by 7"),
      Triple(45, 9, "Divide 45 by 9"),
      Triple(81, 9, "Divide 81 by 9"),
      Triple(28, 7, "Divide 28 by 7"),
      Triple(32, 8, "Divide 32 by 8"),
      Triple(24, 8, "Divide 24 by 8"),
      Triple(56, 8, "Divide 56 by 8"),
      Triple(48, 8, "Divide 48 by 8"),
      Triple(45, 9, "Divide 45 by 9"),
      Triple(27, 9, "Divide 27 by 9"),
      Triple(36, 6, "Divide 36 by 6"),
      Triple(12, 6, "Divide 12 by 6"),
      Triple(18, 6, "Divide 18 by 6"),
      Triple(49, 7, "Divide 49 by 7"),
      Triple(72, 8, "Divide 72 by 8"),
      Triple(21, 7, "Divide 21 by 7"),
      Triple(16, 8, "Divide 16 by 8"),
      Triple(40, 8, "Divide 40 by 8"),
      Triple(64, 8, "Divide 64 by 8"),
      Triple(80, 8, "Divide 80 by 8"),
      Triple(72, 9, "Divide 72 by 9"),
      Triple(63, 9, "Divide 63 by 9"),
      Triple(56, 7, "Divide 56 by 7"),
      Triple(32, 4, "Divide 32 by 4"),
      Triple(72, 9, "Divide 72 by 9"),
      Triple(54, 6, "Divide 54 by 6"),
    )
    return pairs.mapIndexed { idx, (a, b, prompt) ->
      MathProblem.DivisionProblem(
        itemNumber = idx + 1, dividend = a, divisor = b, quotient = a / b,
        promptText = prompt,
        hint = "$a ÷ $b: think ${b} × ? = $a.",
      )
    }
  }

  fun getWorksheet18(): List<MathProblem> {
    val pairs = listOf(
      46 to 6, 67 to 7, 29 to 5, 75 to 8, 61 to 7, 46 to 8,
      29 to 3, 26 to 6, 29 to 9, 255 to 5, 248 to 8, 115 to 5,
      169 to 8, 179 to 8, 325 to 4,
    )
    return pairs.mapIndexed { idx, (a, b) ->
      val q = a / b
      val r = a % b
      MathProblem.DivisionProblem(
        itemNumber = idx + 1, dividend = a, divisor = b, quotient = q, remainder = r,
        showBracket = true,
        hint = "Find the largest multiple of $b that fits into $a without going over for the quotient, then subtract it from $a for the remainder.",
      )
    }
  }

  fun getWorksheet19(): List<MathProblem> {
    val pairs = listOf(
      66 to 11, 48 to 12, 75 to 25, 60 to 30,
      80 to 15, 95 to 35, 98 to 45, 85 to 26,
      81 to 18, 88 to 16, 58 to 14, 75 to 23,
    )
    val list = pairs.mapIndexed { idx, (a, b) ->
      val q = a / b
      val r = a % b
      MathProblem.DivisionProblem(
        itemNumber = idx + 1, dividend = a, divisor = b, quotient = q, remainder = r,
        showBracket = true,
        hint = "Skip-count or multiply $b × 1, 2, 3... to find how many times $b fits inside $a, then subtract to find any remainder.",
      )
    }.toMutableList()
    list += MathProblem.DivisionProblem(
      13, 92, 46, 2, 0,
      promptText = "On their closing program, Ms. Gomez brought 92 pieces of puto-seko, a kind of biscuit made from rice flour, and distributed it among her 46 students. How many puto-sekos did each student receive?",
      unitLabel = "puto-sekos", showBracket = false,
      hint = "Divide 92 ÷ 46 by checking how many groups of 46 make 92.",
    )
    list += MathProblem.DivisionProblem(
      14, 75, 25, 3, 0,
      promptText = "As a source of money for her daily allowance in school, Elena sells yema to her classmates. She has 75 pieces of yemas everyday, which she divides among her 25 classmates. How many yemas does she sell to each of them?",
      unitLabel = "yemas", showBracket = false,
      hint = "Divide 75 ÷ 25 by skip-counting by 25 up to 75.",
    )
    return list
  }

  fun getWorksheet20(): List<MathProblem> {
    val pairs = listOf(
      30 to 10, 60 to 10, 50 to 10, 70 to 10, 90 to 10, 80 to 10,
      300 to 10, 600 to 10, 400 to 10, 800 to 10, 500 to 10, 900 to 10,
      400 to 10, 500 to 10, 800 to 100, 700 to 100, 200 to 100, 900 to 100,
      600 to 100, 500 to 100,
    )
    return pairs.mapIndexed { idx, (a, b) ->
      MathProblem.DivisionProblem(
        itemNumber = idx + 1, dividend = a, divisor = b, quotient = a / b, remainder = 0,
        showBracket = true,
        hint = if (b == 10) "Dividing by 10 removes 1 trailing zero from the dividend." else "Dividing by 100 removes 2 trailing zeros from the dividend.",
      )
    }
  }
}
