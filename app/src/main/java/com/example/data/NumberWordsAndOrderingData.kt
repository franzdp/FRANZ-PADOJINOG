package com.example.data

import com.example.model.MathProblem

object NumberWordsAndOrderingData {

  fun getWorksheet29(): List<MathProblem> {
    val list = mutableListOf<MathProblem>()
    val numToWords = listOf(
      1357 to "one thousand, three hundred fifty-seven",
      13332 to "thirteen thousand, three hundred thirty-two",
      23017 to "twenty-three thousand, seventeen",
      49012 to "forty-nine thousand, twelve",
      67887 to "sixty-seven thousand, eight hundred eighty-seven",
      90187 to "ninety thousand, one hundred eighty-seven",
      11320 to "eleven thousand, three hundred twenty",
      21207 to "twenty-one thousand, two hundred seven",
      42894 to "forty-two thousand, eight hundred ninety-four",
      33696 to "thirty-three thousand, six hundred ninety-six",
      43444 to "forty-three thousand, four hundred forty-four",
      52001 to "fifty-two thousand, one",
      88022 to "eighty-eight thousand, twenty-two",
      68848 to "sixty-eight thousand, eight hundred forty-eight",
      87226 to "eighty-seven thousand, two hundred twenty-six",
    )
    numToWords.forEachIndexed { idx, (n, w) ->
      list += MathProblem.NumberWordsProblem(
        itemNumber = idx + 1, number = n, words = w, toWords = true,
        hint = "Separate thousands and hundreds with a comma, and hyphenate compound tens like fifty-seven.",
      )
    }
    val wordsToNum = listOf(
      "twelve thousand, six hundred seventy-one" to 12671,
      "seventy-five thousand, six hundred eighty" to 75680,
      "three thousand, ninety-two" to 3092,
      "one hundred thousand" to 100000,
      "thirteen thousand, seven hundred thirty-six" to 13736,
      "fourteen thousand, two hundred forty-four" to 14244,
      "six thousand, five hundred" to 6500,
      "fifty-eight thousand, five hundred five" to 58505,
      "fifty-two thousand, one hundred one" to 52101,
      "fifteen thousand, three hundred nineteen" to 15319,
      "seven hundred seventy-seven" to 777,
      "eighty thousand, eighty-eight" to 80088,
      "nine thousand, nine hundred ninety" to 9990,
      "one thousand, one hundred ten" to 1110,
      "ninety-nine thousand, nine hundred nineteen" to 99919,
      "three thousand, two hundred twelve" to 3212,
      "thirteen thousand, thirteen" to 13013,
      "five hundred twenty eight" to 528,
      "eighteen thousand, six hundred fifty-two" to 18652,
      "fourteen thousand, seventeen" to 14017,
    )
    wordsToNum.forEachIndexed { idx, (w, n) ->
      list += MathProblem.NumberWordsProblem(
        itemNumber = 16 + idx, number = n, words = w, toWords = false,
        hint = "Read the thousands period before the comma, then fill hundreds, tens, and ones.",
      )
    }
    return list
  }

  fun getWorksheet30(): List<MathProblem> {
    data class CmpItem(val lDisp: String, val lVal: Int, val rDisp: String, val rVal: Int, val leftWords: Boolean)
    val items = listOf(
      CmpItem("forty-two thousand, three hundred", 42300, "42,300", 42300, true),
      CmpItem("15,678", 15678, "fifteen thousand, six hundred seventy-eight", 15678, false),
      CmpItem("seventy thousand, five", 70005, "69,995", 69995, true),
      CmpItem("8,204", 8204, "eight thousand, two hundred forty", 8240, false),
      CmpItem("ninety-nine thousand, nine hundred", 99900, "99,899", 99899, true),
      CmpItem("23,450", 23450, "twenty-three thousand, four hundred forty-nine", 23449, false),
      CmpItem("sixty thousand, sixty", 60060, "60,600", 60600, true),
      CmpItem("17,999", 17999, "seventeen thousand, nine hundred ninety-nine", 17999, false),
      CmpItem("five thousand, five", 5005, "5,050", 5050, true),
      CmpItem("83,120", 83120, "eighty-three thousand, one hundred twenty", 83120, false),
      CmpItem("thirty-four thousand, seven hundred", 34700, "35,000", 35000, true),
      CmpItem("10,010", 10010, "ten thousand, nine", 10009, false),
      CmpItem("seventy-eight thousand, nine", 78009, "78,900", 78900, true),
      CmpItem("46,789", 46789, "forty-six thousand, seven hundred ninety", 46790, false),
      CmpItem("ninety thousand, ninety", 90090, "89,999", 89999, true),
      CmpItem("24,001", 24001, "twenty-four thousand, one", 24001, false),
      CmpItem("fifty-two thousand, five hundred", 52500, "52,050", 52050, true),
      CmpItem("9,876", 9876, "nine thousand, eight hundred seventy-six", 9876, false),
      CmpItem("one hundred thousand", 100000, "99,999", 99999, true),
      CmpItem("31,235", 31235, "thirty-one thousand, two hundred thirty-five", 31235, false),
      CmpItem("forty-three thousand, five hundred", 43500, "43,050", 43050, true),
      CmpItem("27,458", 27458, "twenty-seven thousand, five hundred sixty-two", 27562, false),
      CmpItem("ninety-one thousand, two hundred", 91200, "91,200", 91200, true),
      CmpItem("56,780", 56780, "fifty-six thousand, nine hundred", 56900, false),
      CmpItem("thirty-three thousand, two hundred", 33200, "33,250", 33250, true),
      CmpItem("74,000", 74000, "seventy-four thousand, eighty-nine", 74089, false),
      CmpItem("ninety-eight thousand, seven hundred twelve", 98712, "98,712", 98712, true),
      CmpItem("63,029", 63029, "sixty-three thousand, four hundred thirty-two", 63432, false),
      CmpItem("49,875", 49875, "50,000", 50000, false),
      CmpItem("ten thousand, nine hundred fifty-six", 10956, "10,956", 10956, true),
      CmpItem("sixty-two thousand, three hundred", 62300, "62,730", 62730, true),
      CmpItem("seventy-five thousand, nine hundred", 75900, "75,901", 75901, true),
      CmpItem("48,672", 48672, "forty-nine thousand, six hundred seventy-two", 49672, false),
      CmpItem("one hundred thousand", 100000, "99,999", 99999, true),
      CmpItem("67,504", 67504, "sixty-seven thousand, seven hundred ninety", 67790, false),
      CmpItem("52,317", 52317, "fifty-two thousand, three hundred twenty six", 52326, false),
      CmpItem("76,540", 76540, "seventy-six thousand, six hundred forty-nine", 76649, false),
      CmpItem("85,600", 85600, "eighty-five thousand, six hundred", 85600, false),
      CmpItem("73,219", 73219, "seventy-three thousand, three hundred twenty", 73320, false),
      CmpItem("68,432", 68432, "sixty-eight thousand, five hundred", 68500, false),
    )
    return items.mapIndexed { idx, it ->
      val sym = when {
        it.lVal < it.rVal -> "<"
        it.lVal > it.rVal -> ">"
        else -> "="
      }
      MathProblem.CompareWholeNumbersProblem(
        itemNumber = idx + 1,
        leftDisplay = it.lDisp,
        leftValue = it.lVal,
        rightDisplay = it.rDisp,
        rightValue = it.rVal,
        leftIsWords = it.leftWords,
        answerSymbol = sym,
        hint = "Write the worded number in digits carefully, then compare place values from left to right.",
      )
    }
  }

  private fun roundOff(n: Int, place: String): Int {
    val unit = if (place == "thousands") 1000 else 10000
    return ((n + unit / 2) / unit) * unit
  }

  fun getWorksheet31(): List<MathProblem> {
    val items = listOf(
      86854 to "thousands", 78949 to "ten thousands", 34987 to "thousands", 84531 to "ten thousands", 35724 to "thousands",
      94333 to "ten thousands", 94637 to "ten thousands", 96423 to "thousands", 34706 to "ten thousands", 62893 to "thousands",
      84241 to "ten thousands", 98569 to "thousands", 45139 to "ten thousands", 48557 to "ten thousands", 24558 to "thousands",
      46476 to "thousands", 34671 to "thousands", 40194 to "ten thousands", 44388 to "thousands", 98433 to "ten thousands",
      25199 to "thousands", 2999 to "thousands", 48214 to "ten thousands", 82262 to "ten thousands", 38238 to "ten thousands",
      67842 to "thousands", 28719 to "ten thousands", 43586 to "thousands", 91765 to "ten thousands", 36294 to "thousands",
      75683 to "ten thousands", 19457 to "thousands", 82918 to "thousands", 29134 to "thousands", 64975 to "ten thousands",
      88230 to "thousands", 37868 to "ten thousands", 66511 to "thousands", 73049 to "ten thousands", 24365 to "thousands",
      95672 to "ten thousands", 41286 to "thousands", 58947 to "ten thousands", 33418 to "thousands", 79304 to "ten thousands",
      21657 to "thousands", 86123 to "ten thousands", 14892 to "thousands", 92605 to "ten thousands", 27531 to "thousands",
    )
    return items.mapIndexed { idx, (num, place) ->
      val ans = roundOff(num, place)
      val checkDiv = if (place == "thousands") 100 else 1000
      val nextDigit = (num / checkDiv) % 10
      MathProblem.RoundingProblem(
        itemNumber = idx + 1, number = num, targetPlace = place, answer = ans,
        hint = "Check the digit immediately to the right of the $place place: 5 or more rounds UP, 4 or less rounds DOWN!",
      )
    }
  }

  fun getWorksheet32(): List<MathProblem> {
    val items = listOf(
      37684 to "thousands", 65429 to "ten thousands", 82915 to "thousands", 14237 to "ten thousands", 96358 to "thousands",
      28471 to "ten thousands", 59863 to "thousands", 73146 to "ten thousands", 41902 to "thousands", 68257 to "ten thousands",
      23516 to "thousands", 87329 to "ten thousands", 31647 to "thousands", 92874 to "ten thousands", 16938 to "thousands",
      74261 to "ten thousands", 48599 to "thousands", 69713 to "ten thousands", 25381 to "thousands", 83645 to "ten thousands",
      61472 to "thousands", 15762 to "ten thousands", 93108 to "thousands", 36849 to "ten thousands", 77235 to "thousands",
      52347 to "thousands", 18265 to "ten thousands", 83914 to "thousands", 27638 to "ten thousands", 61759 to "thousands",
      74216 to "ten thousands", 33892 to "thousands", 91163 to "ten thousands", 24578 to "thousands", 46892 to "ten thousands",
      68453 to "thousands", 37129 to "ten thousands", 95087 to "thousands", 15683 to "ten thousands", 73946 to "thousands",
      22845 to "ten thousands", 84297 to "thousands", 39615 to "ten thousands", 58734 to "thousands", 67481 to "ten thousands",
      29356 to "thousands", 93628 to "ten thousands", 42179 to "thousands", 16987 to "ten thousands", 78254 to "thousands",
      42376 to "thousands", 87651 to "ten thousands", 23894 to "thousands", 76218 to "ten thousands", 19735 to "thousands",
      68903 to "ten thousands", 55472 to "thousands", 91386 to "ten thousands", 34621 to "thousands", 72549 to "ten thousands",
      28765 to "thousands", 63842 to "ten thousands", 84917 to "thousands", 17438 to "ten thousands", 93204 to "thousands",
      46573 to "ten thousands", 29618 to "thousands", 81746 to "ten thousands", 37592 to "thousands", 65309 to "ten thousands",
      22184 to "thousands", 98731 to "ten thousands", 14867 to "thousands", 76592 to "ten thousands", 39285 to "thousands",
    )
    return items.mapIndexed { idx, (num, place) ->
      val ans = roundOff(num, place)
      val checkDiv = if (place == "thousands") 100 else 1000
      val nextDigit = (num / checkDiv) % 10
      MathProblem.RoundingProblem(
        itemNumber = idx + 1, number = num, targetPlace = place, answer = ans,
        hint = "Check the digit immediately to the right of the $place place: 5 or more rounds UP, 4 or less rounds DOWN (and trailing digits become 0).",
      )
    }
  }

  fun getWorksheet33(): List<MathProblem> {
    val sets = listOf(
      // 1-13 Increasing
      listOf(45267, 12890, 78345, 33111, 9876) to true,
      listOf(63452, 21089, 76234, 14567, 58901) to true,
      listOf(9234, 72345, 56789, 18900, 31246) to true,
      listOf(84321, 27654, 39876, 60111, 15432) to true,
      listOf(47890, 3456, 92321, 66220, 19008) to true,
      listOf(72468, 38291, 14375, 59832, 7609) to true,
      listOf(28136, 65879, 11234, 93540, 49002) to true,
      listOf(50777, 23456, 81900, 12345, 67210) to true,
      listOf(99001, 34560, 5678, 73245, 16432) to true,
      listOf(44210, 88765, 24809, 60312, 2997) to true,
      listOf(15678, 70230, 32456, 89001, 6543) to true,
      listOf(61234, 29876, 83150, 9432, 51009) to true,
      listOf(7890, 45678, 26543, 90876, 18234) to true,
      // 14-26 Decreasing
      listOf(77888, 54321, 12678, 90234, 23456) to false,
      listOf(30018, 4567, 69890, 21345, 81276) to false,
      listOf(85430, 17999, 42160, 60501, 7250) to false,
      listOf(3210, 64578, 38900, 72604, 19871) to false,
      listOf(26700, 58340, 7890, 91108, 45620) to false,
      listOf(68120, 12305, 39456, 85780, 5678) to false,
      listOf(13015, 94230, 50400, 28630, 6789) to false,
      listOf(41111, 73892, 2345, 60870, 19234) to false,
      listOf(8765, 27540, 63012, 92800, 44130) to false,
      listOf(57160, 14098, 80302, 23671, 4509) to false,
      listOf(95432, 31276, 9876, 67210, 20543) to false,
      listOf(16203, 73561, 35420, 8910, 62700) to false,
      listOf(49999, 22011, 77770, 3333, 88888) to false,
      // 27-39 Increasing
      listOf(36741, 5892, 87213, 14650, 63409) to true,
      listOf(73564, 28191, 9876, 54320, 41265) to true,
      listOf(12987, 65432, 31509, 2345, 88210) to true,
      listOf(91876, 47382, 16245, 78901, 3210) to true,
      listOf(22134, 5678, 83451, 44209, 19876) to true,
      listOf(60321, 7890, 39456, 72813, 25694) to true,
      listOf(17308, 92145, 11986, 48672, 36519) to true,
      listOf(84729, 13567, 55820, 2478, 67310) to true,
      listOf(29654, 80123, 9234, 41897, 66542) to true,
      listOf(4567, 32198, 75460, 18309, 59812) to true,
      listOf(61908, 26740, 8321, 53104, 14679) to true,
      listOf(92001, 37695, 15870, 68234, 7456) to true,
      listOf(10345, 47820, 23167, 85603, 3218) to true,
      // 40-52 Decreasing
      listOf(66789, 14234, 91672, 45321, 2109) to false,
      listOf(78456, 21987, 63210, 39805, 16432) to false,
      listOf(52640, 8905, 74321, 19876, 3450) to false,
      listOf(87654, 12345, 60978, 28109, 7890) to false,
      listOf(93215, 41876, 25634, 11987, 3210) to false,
      listOf(70432, 18345, 55671, 29876, 6543) to false,
      listOf(61203, 9876, 48765, 23111, 4890) to false,
      listOf(85321, 30987, 12654, 69432, 8765) to false,
      listOf(74890, 22199, 51360, 7654, 14298) to false,
      listOf(99123, 13567, 65432, 28890, 5678) to false,
      listOf(43210, 16789, 9012, 61054, 2345) to false,
      listOf(89432, 31210, 55876, 7109, 12456) to false,
      listOf(77210, 24568, 48903, 3867, 19001) to false,
    )
    return sets.mapIndexed { idx, (nums, inc) ->
      MathProblem.OrderNumbersProblem(
        itemNumber = idx + 1,
        numbers = nums,
        increasing = inc,
        expectedOrder = if (inc) nums.sorted() else nums.sortedDescending(),
        hint = if (inc) "Tap the smallest number first, then work up to the largest!" else "Tap the largest number first, then work down to the smallest!",
      )
    }
  }

  fun getWorksheet34(): List<MathProblem> {
    val sets = listOf(
      // 1-13 Increasing
      listOf(7654, 23110, 65432, 12208, 98765) to true,
      listOf(33210, 15678, 72901, 4567, 27890) to true,
      listOf(5432, 81234, 16789, 24302, 9876) to true,
      listOf(60501, 14234, 39800, 27345, 7678) to true,
      listOf(45610, 2345, 91230, 30456, 11789) to true,
      listOf(25678, 70123, 8910, 44333, 17654) to true,
      listOf(13579, 62480, 9321, 51903, 22710) to true,
      listOf(86100, 31245, 15899, 42876, 6544) to true,
      listOf(77709, 4321, 68540, 29876, 12345) to true,
      listOf(18765, 59320, 23456, 90011, 7899) to true,
      listOf(22477, 71095, 3468, 84230, 16052) to true,
      listOf(50901, 19345, 63654, 2789, 30210) to true,
      listOf(8234, 41678, 26135, 55400, 13912) to true,
      // 14-26 Decreasing
      listOf(91203, 42567, 15678, 73999, 6321) to false,
      listOf(34210, 8765, 62300, 21987, 11456) to false,
      listOf(88120, 27799, 60234, 13045, 7890) to false,
      listOf(2345, 76891, 40300, 18654, 9210) to false,
      listOf(58432, 6789, 93100, 24567, 33890) to false,
      listOf(16980, 41222, 73450, 12309, 5678) to false,
      listOf(64753, 29301, 11045, 50670, 3210) to false,
      listOf(7899, 90888, 25410, 14999, 6654) to false,
      listOf(52109, 31875, 2456, 69000, 17650) to false,
      listOf(44321, 9876, 70555, 20110, 1234) to false,
      listOf(96010, 13245, 8679, 61432, 27890) to false,
      listOf(38421, 72678, 4999, 19805, 53210) to false,
      listOf(66777, 22344, 85910, 3567, 47123) to false,
    )
    return sets.mapIndexed { idx, (nums, inc) ->
      MathProblem.OrderNumbersProblem(
        itemNumber = idx + 1,
        numbers = nums,
        increasing = inc,
        expectedOrder = if (inc) nums.sorted() else nums.sortedDescending(),
        hint = if (inc) "4-digit numbers come before 5-digit numbers!" else "Start with the largest ten-thousands digit.",
      )
    }
  }
}
