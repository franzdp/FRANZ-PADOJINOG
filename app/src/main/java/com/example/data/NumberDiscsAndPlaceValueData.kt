package com.example.data

import com.example.model.DiscCounts
import com.example.model.MathProblem

object NumberDiscsAndPlaceValueData {

  private fun disc(f50: Int, f10: Int, f1k: Int, h: Int, t: Int, o: Int) =
    DiscCounts(f50, f10, f1k, h, t, o)

  fun getWorksheet24(): List<MathProblem> {
    val sets = listOf(
      disc(0, 2, 1, 2, 2, 0), // 1
      disc(0, 1, 2, 3, 1, 0), // 2
      disc(0, 1, 1, 3, 2, 1), // 3
      disc(0, 0, 4, 2, 0, 0), // 4
      disc(0, 1, 0, 2, 2, 3), // 5
      disc(0, 2, 1, 1, 1, 2), // 6
      disc(0, 1, 0, 3, 3, 1), // 7
      disc(0, 0, 4, 0, 2, 2), // 8
      disc(0, 1, 3, 2, 0, 1), // 9
      disc(0, 0, 2, 3, 2, 3), // 10
      disc(0, 0, 3, 1, 2, 2), // 11
      disc(0, 1, 0, 2, 4, 0), // 12
      disc(0, 1, 1, 1, 2, 4), // 13
      disc(0, 0, 2, 4, 1, 1), // 14
      disc(0, 2, 2, 1, 1, 0), // 15
      disc(0, 1, 2, 2, 2, 2), // 16
      disc(0, 1, 1, 1, 1, 1), // 17
      disc(0, 0, 1, 3, 1, 2), // 18
      disc(0, 0, 0, 5, 2, 1), // 19
      disc(0, 1, 1, 3, 0, 3), // 20
      disc(0, 0, 1, 3, 2, 1), // 21
      disc(0, 1, 3, 2, 1, 1), // 22
      disc(0, 4, 0, 1, 1, 1), // 23
      disc(0, 0, 2, 3, 1, 2), // 24
      disc(0, 1, 1, 2, 2, 1), // 25
      disc(0, 2, 1, 2, 1, 2), // 26
      disc(0, 0, 3, 1, 3, 1), // 27
      disc(0, 1, 2, 1, 1, 1), // 28
      disc(0, 3, 1, 2, 1, 1), // 29
      disc(0, 0, 0, 3, 3, 3), // 30
      disc(0, 0, 1, 5, 0, 1), // 31
      disc(0, 1, 2, 2, 2, 2), // 32
      disc(0, 1, 2, 2, 1, 2), // 33
      disc(0, 0, 2, 3, 2, 2), // 34
      disc(0, 2, 1, 3, 1, 1), // 35
      disc(0, 0, 3, 1, 2, 3), // 36
      disc(0, 1, 0, 3, 3, 1), // 37
      disc(0, 2, 3, 0, 2, 0), // 38
      disc(0, 0, 0, 4, 2, 3), // 39
      disc(0, 1, 1, 2, 1, 1), // 40
      disc(0, 3, 0, 2, 2, 1), // 41
      disc(0, 0, 1, 4, 1, 2), // 42
      disc(0, 1, 3, 2, 1, 0), // 43
      disc(0, 0, 0, 2, 3, 4), // 44
      disc(0, 2, 1, 1, 2, 1), // 45
      disc(1, 3, 0, 1, 1, 2), // 46
      disc(1, 4, 0, 2, 1, 0), // 47
      disc(1, 4, 2, 1, 1, 0), // 48
    )
    return sets.mapIndexed { idx, counts ->
      MathProblem.NumberDiscsReadProblem(
        itemNumber = idx + 1,
        counts = counts,
        answer = counts.total,
        hint = "Count how many 50k, 10k, 1k, 100, 10, and 1 discs are shown and add their values together by place.",
      )
    }
  }

  fun getWorksheet25(): List<MathProblem> {
    val buildTargets = listOf(
      12345, 23476, 34102, 45678, 56789, 67890, 78901, 89120, 98765, 10234,
      11111, 21343, 31212, 41987, 50050, 60606, 70707, 80808, 90909, 99999,
      25678, 35210, 42341, 53456, 62500, 72345, 82456, 91234, 14407, 24024,
      33033, 44444, 55555, 66666, 77777, 88888, 99090, 17890, 28180, 39390,
    )
    val list: MutableList<MathProblem> = buildTargets.mapIndexedTo(mutableListOf()) { idx, target ->
      MathProblem.NumberDiscsBuildProblem(itemNumber = idx + 1, targetNumber = target)
    }
    val readSets = listOf(
      disc(0, 3, 1, 2, 1, 1), // 41: 31,211
      disc(0, 0, 3, 3, 1, 2), // 42: 3,312
      disc(0, 1, 2, 1, 2, 3), // 43: 12,123
      disc(0, 0, 0, 3, 4, 2), // 44: 342
      disc(0, 2, 1, 2, 2, 1), // 45: 21,221
      disc(0, 0, 4, 2, 1, 2), // 46: 4,212
      disc(0, 1, 0, 3, 3, 3), // 47: 10,333
      disc(0, 1, 2, 1, 1, 2), // 48: 12,112
      disc(0, 2, 2, 2, 2, 1), // 49: 22,221
      disc(0, 1, 1, 2, 3, 2), // 50: 11,232
      disc(0, 0, 2, 4, 1, 1), // 51: 2,411
      disc(0, 2, 2, 1, 1, 2), // 52: 22,112
      disc(0, 0, 0, 5, 2, 4), // 53: 524
      disc(0, 1, 2, 3, 2, 1), // 54: 12,321
      disc(0, 0, 1, 5, 1, 1), // 55: 1,511
      disc(0, 3, 1, 2, 1, 1), // 56: 31,211
    )
    readSets.forEachIndexed { idx, counts ->
      list += MathProblem.NumberDiscsReadProblem(
        itemNumber = 41 + idx,
        counts = counts,
        answer = counts.total,
        hint = "Count each group of discs carefully to find the total number.",
      )
    }
    return list
  }

  private fun placeValueForDigit(number: Int, digit: Int): Pair<Int, String> {
    val places = listOf(
      1 to "Ones",
      10 to "Tens",
      100 to "Hundreds",
      1000 to "Thousands",
      10000 to "Ten Thousands",
      100000 to "Hundred Thousands",
    )
    var temp = number
    for ((mult, name) in places) {
      if (temp % 10 == digit) {
        return (digit * mult) to name
      }
      temp /= 10
      if (temp == 0) break
    }
    return 0 to "Ones"
  }

  fun getWorksheet26(): List<MathProblem> {
    val items = listOf(
      // 1-20 mixed
      5 to 29151, 1 to 54251, 8 to 86172, 3 to 38758, 7 to 72838,
      5 to 90512, 5 to 54418, 8 to 85371, 1 to 51523, 2 to 24655,
      4 to 62545, 6 to 64782, 4 to 47967, 9 to 11947, 8 to 56784,
      5 to 5319, 7 to 87340, 1 to 84155, 9 to 97323, 3 to 94399,
      // 21-25 digit 3
      3 to 93879, 3 to 35181, 3 to 1397, 3 to 41213, 3 to 89938,
      // 26-30 digit 5
      5 to 58916, 5 to 35090, 5 to 9095, 5 to 21531, 5 to 23451,
      // 31-35 digit 6
      6 to 97162, 6 to 21746, 6 to 60531, 6 to 46810, 6 to 6324,
      // 36-40 digit 9
      9 to 9412, 9 to 91578, 9 to 29122, 9 to 41955, 9 to 74129,
      // 41-45 digit 2
      2 to 42718, 2 to 26531, 2 to 72946, 2 to 13215, 2 to 80462,
      // 46-50 digit 7
      7 to 37501, 7 to 67284, 7 to 19763, 7 to 74095, 7 to 87630,
      // 51-55 digit 4
      4 to 84259, 4 to 14837, 4 to 64701, 4 to 42963, 4 to 35124,
      // 56-60 digit 8
      8 to 18526, 8 to 68341, 8 to 82769, 8 to 90483, 8 to 28150,
    )
    return items.mapIndexed { idx, (digit, num) ->
      // Note: for items where digit appears twice (e.g., 5 in 54,251? No, 1 in 54,251; what about 5 in 51,523? Wait: 1 in 51,523 is unique! Check 5 in 54,418 -> unique; 4 in 62,545 -> unique; 9 in 97,323 -> unique; 3 in 94,399 -> unique; 9 in 91,578 -> unique; 8 in 28,150 -> unique!)
      // Check if any have repeated target digit: 29151(5 unique), 54251(1 unique), 24655(2 unique), 47967(4 unique), 11947(9 unique), 84155(1 unique), 35181(3 unique), 93879(3 unique), 41955(9 unique), 74129(9 unique), 13215(2 unique), 80462(2 unique). All target digits are unique in their number!
      val (valNum, pv) = placeValueForDigit(num, digit)
      MathProblem.PlaceValueProblem(
        itemNumber = idx + 1,
        number = num,
        targetDigit = digit,
        expectedValue = valNum,
        expectedPlaceValue = pv,
        askPlaceValue = true,
        askValue = true,
        promptText = "Find the Value and Place Value of $digit in ${"%,d".format(num)}.",
        hint = "Find where $digit sits (Ones, Tens, Hundreds, Thousands, Ten Thousands) and multiply $digit by that place!",
      )
    }
  }

  private fun digitAtPlace(number: Int, place: String): Pair<Int, Int> {
    val divisor = when (place.lowercase()) {
      "ones" -> 1
      "tens" -> 10
      "hundreds" -> 100
      "thousands" -> 1000
      "ten thousands" -> 10000
      "hundred thousands" -> 100000
      else -> 1
    }
    val d = (number / divisor) % 10
    return d to (d * divisor)
  }

  fun getWorksheet27(): List<MathProblem> {
    val raw = listOf(
      64259 to "ones", 64259 to "ten thousands", 64259 to "tens", 64259 to "hundreds", 64259 to "thousands",
      49402 to "hundreds", 49402 to "tens", 49402 to "ones", 49402 to "ten thousands", 49402 to "thousands",
      3546 to "thousands", 3546 to "hundreds", 3546 to "tens", 3546 to "ones",
      93546 to "tens", 93546 to "thousands", 93546 to "ten thousands", 93546 to "hundreds", 93546 to "ones",
      1234 to "ones", 1234 to "hundreds", 1234 to "tens", 1234 to "thousands",
      17382 to "ten thousands", 17382 to "thousands", 17382 to "tens", 17382 to "ones", 17382 to "hundreds",
      70615 to "thousands", 70615 to "hundreds", 70615 to "ones", 70615 to "ten thousands", 70615 to "tens",
      8190 to "hundreds", 8190 to "ones", 8190 to "tens", 8190 to "thousands",
      25971 to "ten thousands", 25971 to "thousands", 25971 to "hundreds", 25971 to "ones", 25971 to "tens",
      9403 to "tens", 9403 to "hundreds", 9403 to "thousands", 9403 to "ones",
    )
    return raw.mapIndexed { idx, (num, place) ->
      val (digit, value) = digitAtPlace(num, place)
      MathProblem.PlaceValueProblem(
        itemNumber = idx + 1,
        number = num,
        targetDigit = digit,
        expectedValue = value,
        expectedPlaceValue = place.replaceFirstChar { it.uppercase() },
        askPlaceValue = false,
        askValue = true,
        promptText = "In ${"%,d".format(num)}, give the value of the digit in the $place place.",
        hint = "Find the digit sitting in the $place place, then multiply it by the value of the $place place.",
      )
    }
  }

  fun getWorksheet28(): List<MathProblem> {
    val raw = listOf(
      64875 to "ten thousands", 87443 to "hundreds", 100000 to "hundred thousands", 20534 to "ones",
      83021 to "tens", 21108 to "ten thousands", 63921 to "hundreds", 94124 to "thousands",
      5912 to "hundreds", 15773 to "ones", 74694 to "ones", 28455 to "tens",
      77722 to "ten thousands", 95857 to "hundreds", 8112 to "hundreds", 31627 to "ten thousands",
      54614 to "thousands", 24712 to "ten thousands", 1237 to "ones", 32114 to "tens",
      5732 to "hundreds", 64421 to "hundreds", 89365 to "tens", 45188 to "ten thousands",
      25714 to "ten thousands", 76438 to "hundreds", 13907 to "thousands", 92315 to "ones",
      8642 to "tens", 43120 to "ten thousands", 55209 to "hundreds", 67818 to "hundreds",
      2319 to "ones", 71463 to "tens", 96580 to "thousands", 19237 to "ten thousands",
      5824 to "hundreds", 38619 to "ones", 14256 to "tens", 84372 to "hundreds",
      62905 to "ten thousands", 7531 to "thousands", 29847 to "ones", 91208 to "hundreds",
      36741 to "tens", 48203 to "hundreds", 73690 to "ten thousands", 2146 to "ones",
      85509 to "thousands", 60934 to "hundreds",
    )
    return raw.mapIndexed { idx, (num, place) ->
      val (digit, value) = digitAtPlace(num, place)
      MathProblem.PlaceValueProblem(
        itemNumber = idx + 1,
        number = num,
        targetDigit = digit,
        expectedValue = value,
        expectedPlaceValue = place.replaceFirstChar { it.uppercase() },
        askPlaceValue = false,
        askValue = true,
        promptText = "${"%,d".format(num)} ($place)",
        hint = "Find the digit sitting in the $place place and multiply it by the value of that place.",
      )
    }
  }
}
