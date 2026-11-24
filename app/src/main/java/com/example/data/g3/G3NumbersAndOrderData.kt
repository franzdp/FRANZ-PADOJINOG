package com.example.data.g3

import com.example.model.*

object G3NumbersAndOrderData {

  private fun formatUnderlined3(n: Int, posFromLeft: Int): String {
    val s = n.toString()
    return s.mapIndexed { idx, ch -> if (idx == posFromLeft) "[$ch]" else "$ch" }.joinToString("")
  }

  private fun placeValueOf(posFromLeft: Int): String = when (posFromLeft) {
    0 -> "Hundreds"
    1 -> "Tens"
    else -> "Ones"
  }

  private fun digitMultiplier(posFromLeft: Int): Int = when (posFromLeft) {
    0 -> 100
    1 -> 10
    else -> 1
  }

  fun getWorksheet1(): List<MathProblem> {
    // 1-13: Place value of underlined digit (posFromLeft: 0=Hundreds, 1=Tens, 2=Ones)
    val pvItems = listOf(
      234 to 2, 467 to 1, 780 to 2, 518 to 0, 265 to 0,
      247 to 2, 191 to 2, 563 to 2, 480 to 1, 734 to 0,
      567 to 1, 345 to 0, 678 to 0,
    ).mapIndexed { idx, (num, pos) ->
      val d = num.toString()[pos].digitToInt()
      val pv = placeValueOf(pos)
      MathProblem.PlaceValueProblem(
        itemNumber = idx + 1,
        number = num,
        targetDigit = d,
        expectedValue = 0,
        expectedPlaceValue = pv,
        askPlaceValue = true,
        askValue = false,
        promptText = "Find the PLACE VALUE of the highlighted digit [d]: ${formatUnderlined3(num, pos)}",
        hint = "Count places from the right: rightmost is Ones, middle is Tens, leftmost of 3 digits is Hundreds.",
      )
    }

    // 14-28: Value of underlined digit
    val valItems = listOf(
      500 to 0, 300 to 0, 731 to 1, 780 to 0, 452 to 2,
      234 to 0, 734 to 0, 564 to 0, 345 to 2, 577 to 0,
      372 to 1, 843 to 0, 123 to 0, 102 to 1, 611 to 0,
    ).mapIndexed { idx, (num, pos) ->
      val d = num.toString()[pos].digitToInt()
      val mult = digitMultiplier(pos)
      val pv = placeValueOf(pos)
      MathProblem.PlaceValueProblem(
        itemNumber = 14 + idx,
        number = num,
        targetDigit = d,
        expectedValue = d * mult,
        expectedPlaceValue = pv,
        askPlaceValue = false,
        askValue = true,
        promptText = "Write the VALUE of the highlighted digit [d]: ${formatUnderlined3(num, pos)}",
        hint = "The highlighted digit $d is in the $pv place, so multiply $d × $mult.",
      )
    }

    return pvItems + valItems
  }

  private fun hundredsWord(h: Int): String = when (h) {
    1 -> "one hundred"; 2 -> "two hundred"; 3 -> "three hundred"; 4 -> "four hundred"
    5 -> "five hundred"; 6 -> "six hundred"; 7 -> "seven hundred"; 8 -> "eight hundred"
    9 -> "nine hundred"; else -> ""
  }

  private fun twoDigitWord(n: Int): String {
    val ones = listOf("", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
    val teens = listOf("ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen")
    val tens = listOf("", "", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety")
    return when {
      n == 0 -> ""
      n < 10 -> ones[n]
      n < 20 -> teens[n - 10]
      n % 10 == 0 -> tens[n / 10]
      else -> "${tens[n / 10]}-${ones[n % 10]}"
    }
  }

  private fun numberToWords3(n: Int): String {
    if (n == 1000) return "one thousand"
    val h = n / 100
    val rem = n % 100
    val hw = hundredsWord(h)
    val rw = twoDigitWord(rem)
    return listOf(hw, rw).filter { it.isNotEmpty() }.joinToString(" ")
  }

  fun getWorksheet2(): List<MathProblem> {
    val toWordsNums = listOf(
      346, 476, 258, 389, 956,
      899, 401, 256, 677, 555,
      210, 587, 689, 897, 512,
      734, 930, 456, 545, 1000,
      400, 320, 141, 196, 658,
    )
    val part1 = toWordsNums.mapIndexed { idx, num ->
      MathProblem.NumberWordsProblem(
        itemNumber = idx + 1,
        number = num,
        words = numberToWords3(num),
        toWords = true,
        hint = if (num == 1000) "1,000 is 'one thousand'." else "Start with '${hundredsWord(num / 100)}', then write the remaining ${num % 100} in words (with a hyphen if 21–99).",
      )
    }

    val wordsToNums = listOf(
      345 to "three hundred forty-five",
      672 to "six hundred seventy-two",
      501 to "five hundred one",
      277 to "two hundred seventy-seven",
      853 to "eight hundred fifty-three",
      200 to "two hundred",
      504 to "five hundred four",
      640 to "six hundred forty",
      919 to "nine hundred nineteen",
      214 to "two hundred fourteen",
      443 to "four hundred forty-three",
      999 to "nine hundred ninety-nine",
      876 to "eight hundred seventy-six",
      210 to "two hundred ten",
      115 to "one hundred fifteen",
      206 to "two hundred six",
      723 to "seven hundred twenty-three",
      318 to "three hundred eighteen",
      818 to "eight hundred eighteen",
      1000 to "one thousand",
    )
    val part2 = wordsToNums.mapIndexed { idx, (num, w) ->
      MathProblem.NumberWordsProblem(
        itemNumber = 26 + idx,
        number = num,
        words = w,
        toWords = false,
        hint = if (num == 1000) "One thousand has 1 followed by three zeros." else "Read the hundreds digit first (${num / 100}), then place the tens and ones digits (use 0 if none).",
      )
    }
    return part1 + part2
  }

  fun getWorksheet3(): List<MathProblem> {
    val firstFmt = listOf(345, 789, 127, 600, 507, 654, 534, 463, 370, 241).mapIndexed { idx, n ->
      ExpandedFormProblem(
        itemNumber = idx + 1,
        number = n,
        promptDisplay = "Write in Expanded Form (First format: e.g. 6 hundreds, 7 tens, 8 ones or place breakdown):",
        mode = ExpandedFormMode.FIRST_FORMAT,
        hint = "Break $n into its hundreds, tens, and ones digits.",
      )
    }

    val secondFmt = listOf(507, 654, 862, 671, 804, 975, 534, 363, 234, 156).mapIndexed { idx, n ->
      ExpandedFormProblem(
        itemNumber = 11 + idx,
        number = n,
        promptDisplay = "Write in Expanded Form (Sum of Values: e.g. 600 + 70 + 8):",
        mode = ExpandedFormMode.SECOND_FORMAT,
        hint = "Multiply each digit by 100, 10, or 1 to get its place value.",
      )
    }

    val wordSpecs = listOf(
      77 to "seventy-seven",
      26 to "twenty-six",
      90 to "Ninety",
      69 to "Sixty-nine",
      47 to "Forty-seven",
      38 to "Thirty-eight",
      95 to "Ninety-five",
      728 to "seven hundred twenty-eight",
      999 to "Nine hundred ninety-nine",
      152 to "One hundred fifty-two",
      620 to "Six hundred twenty",
      919 to "Nine hundred nineteen",
      254 to "Two hundred fifty-four",
      651 to "Six hundred fifty-one",
    ).mapIndexed { idx, (n, w) ->
      ExpandedFormProblem(
        itemNumber = 21 + idx,
        number = n,
        promptDisplay = "Write \"$w\" in expanded form (you may use either format):",
        mode = ExpandedFormMode.ANY_FORMAT,
        hint = "Separate $n into ${if (n >= 100) "hundreds, tens, and ones" else "tens and ones"}.",
      )
    }

    val digitAnySpecs = listOf(652, 235, 981, 644, 267, 109, 631, 317, 783, 430, 999).mapIndexed { idx, n ->
      ExpandedFormProblem(
        itemNumber = 35 + idx,
        number = n,
        promptDisplay = "Write in expanded form (you may use either format):",
        mode = ExpandedFormMode.ANY_FORMAT,
        hint = "Break $n into hundreds value, tens value, and ones value.",
      )
    }

    return firstFmt + secondFmt + wordSpecs + digitAnySpecs
  }

  fun getWorksheet4(): List<MathProblem> {
    val compares = listOf(
      Triple(998899, 99889, ">"),
      Triple(500001, 500010, "<"),
      Triple(99000, 90999, ">"),
      Triple(85538, 58358, ">"),
      Triple(678898, 697889, "<"),
      Triple(455600, 466500, "<"),
      Triple(989800, 998800, "<"),
      Triple(100002, 100010, "<"),
      Triple(323522, 322523, ">"),
      Triple(123115, 102231, ">"),
    ).mapIndexed { idx, (a, b, sym) ->
      MathProblem.CompareWholeNumbersProblem(
        itemNumber = idx + 1,
        leftDisplay = "%,d".format(a),
        leftValue = a,
        rightDisplay = "%,d".format(b),
        rightValue = b,
        leftIsWords = false,
        answerSymbol = sym,
        hint = "First count how many digits each number has; if tied, compare from the leftmost digit.",
      )
    }

    val stories = listOf(
      CompareWordProblem(11, "Currently, Quezon City has 18 public hospitals and 54 health centers. Which facility does Quezon City have MORE of?", "18 public hospitals", "54 health centers", "54 health centers", "Compare 18 and 54 to see which number is greater."),
      CompareWordProblem(12, "Quezon City has 96 elementary schools and 42 secondary schools. Which type of school does Quezon City have LESS of?", "96 elementary schools", "42 secondary schools", "42 secondary schools", "Look for 'LESS' — compare 96 and 42 and pick the smaller number."),
      CompareWordProblem(13, "There are 6 television networks and 10 radio stations located in Quezon City. Which type does Quezon City have MORE of?", "6 television networks", "10 radio stations", "10 radio stations", "Compare 6 and 10 — which is greater?"),
      CompareWordProblem(14, "Batangas has 31 municipalities while Bulacan has 21 municipalities. Which province has MORE municipalities?", "Batangas (31)", "Bulacan (21)", "Batangas (31)", "Compare 31 and 21 — which province has the bigger number?"),
      CompareWordProblem(15, "Approximately 51% of the population are male and 49% are female. Which group does the Philippines have LESS of?", "Male (51%)", "Female (49%)", "Female (49%)", "Look for 'LESS' — compare 51% and 49%."),
      CompareWordProblem(16, "In 2006, there were 41,983 schools while in 2007 there was a total of 42,152 schools. In what year were there MORE schools?", "2006 (41,983)", "2007 (42,152)", "2007 (42,152)", "Compare 41,983 vs. 42,152 starting from the ten-thousands and thousands places."),
      CompareWordProblem(17, "In 2003 there were 160,828 teachers; in 2004 this became 163,437. In what year were there MORE teachers?", "2003 (160,828)", "2004 (163,437)", "2004 (163,437)", "Compare 160,828 and 163,437 at the thousands digit (0 vs 3)."),
      CompareWordProblem(18, "The City of Manila has 1,581,082 people while the City of Caloocan has 1,177,604 people. In which city was the population LESS?", "City of Manila", "City of Caloocan", "City of Caloocan", "Compare 1,581,082 and 1,177,604 at the hundred-thousands place (5 vs 1)."),
      CompareWordProblem(19, "The City of Marikina has 391,170 people while the City of Pasay has 354,908 people. Which city has LESS people in it?", "City of Marikina", "City of Pasay", "City of Pasay", "Compare 391,170 and 354,908 at the ten-thousands digit (9 vs 5)."),
    )

    val ascSets = listOf(
      listOf(1, 4, 3, 2), listOf(3, 6, 4, 5), listOf(9, 11, 10, 8), listOf(3, 0, 2, 1),
      listOf(2, 5, 4, 3), listOf(4, 6, 7, 5), listOf(8, 6, 7, 9), listOf(8, 7, 6, 5),
      listOf(9, 2, 6, 3), listOf(9, 7, 6, 0), listOf(10, 8, 9, 7),
    ).mapIndexed { idx, nums ->
      MathProblem.OrderNumbersProblem(
        itemNumber = 20 + idx,
        numbers = nums,
        increasing = true,
        expectedOrder = nums.sorted(),
        hint = "Ascending means LEAST to GREATEST — tap the smallest remaining number each time.",
      )
    }

    val descSets = listOf(
      listOf(0, 11, 9, 5), listOf(10, 8, 4, 2), listOf(3, 9, 6, 12), listOf(12, 11, 9, 8),
      listOf(18, 20, 19, 21), listOf(19, 21, 20, 22), listOf(20, 17, 18, 19), listOf(11, 13, 12, 14),
      listOf(20, 23, 21, 22), listOf(17, 15, 16, 14),
    ).mapIndexed { idx, nums ->
      MathProblem.OrderNumbersProblem(
        itemNumber = 31 + idx,
        numbers = nums,
        increasing = false,
        expectedOrder = nums.sortedDescending(),
        hint = "Descending means GREATEST to LEAST — start with the largest number.",
      )
    }

    return compares + stories + ascSets + descSets
  }
}
