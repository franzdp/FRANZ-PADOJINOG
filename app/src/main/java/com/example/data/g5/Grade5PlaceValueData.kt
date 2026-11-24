package com.example.data.g5

import com.example.model.DiscCounts
import com.example.model.MathProblem

object Grade5PlaceValueData {

  private fun discRead(item: Int, c50: Int, c10: Int, c1k: Int, c100: Int, c10t: Int, c1: Int): MathProblem.NumberDiscsReadProblem {
    val dc = DiscCounts(c50, c10, c1k, c100, c10t, c1)
    return MathProblem.NumberDiscsReadProblem(
      itemNumber = item,
      counts = dc,
      answer = dc.total,
      hint = "Add 50,000s, 10,000s, 1,000s, 100s, 10s, and 1s carefully.",
    )
  }

  private fun placeName(posFromRight: Int): String = when (posFromRight) {
    0 -> "Ones"
    1 -> "Tens"
    2 -> "Hundreds"
    3 -> "Thousands"
    4 -> "Ten Thousands"
    else -> "Hundred Thousands"
  }

  private fun pvByDigit(item: Int, digit: Int, number: Int): MathProblem.PlaceValueProblem {
    val s = number.toString()
    val idx = s.indexOf(digit.digitToChar())
    val posFromRight = s.length - 1 - idx
    var mult = 1
    repeat(posFromRight) { mult *= 10 }
    val pName = placeName(posFromRight)
    return MathProblem.PlaceValueProblem(
      itemNumber = item,
      number = number,
      targetDigit = digit,
      expectedValue = digit * mult,
      expectedPlaceValue = pName,
      askPlaceValue = true,
      askValue = true,
      promptText = "Find the value and place value of $digit in ${"%,d".format(number)}:",
      hint = "Count places from the right: Ones, Tens, Hundreds, Thousands, Ten Thousands.",
    )
  }

  private fun valByPlace(item: Int, number: Int, placeLabel: String): MathProblem.PlaceValueProblem {
    val posFromRight = when (placeLabel.lowercase().trim()) {
      "ones" -> 0
      "tens" -> 1
      "hundreds" -> 2
      "thousands" -> 3
      "ten thousands" -> 4
      else -> 5
    }
    var mult = 1
    repeat(posFromRight) { mult *= 10 }
    val digit = (number / mult) % 10
    return MathProblem.PlaceValueProblem(
      itemNumber = item,
      number = number,
      targetDigit = digit,
      expectedValue = digit * mult,
      expectedPlaceValue = placeName(posFromRight),
      askPlaceValue = false,
      askValue = true,
      promptText = "Give the value of ${"%,d".format(number)} at the $placeLabel place:",
      hint = "Find the digit in the $placeLabel place and multiply it by ${"%,d".format(mult)}.",
    )
  }

  val ws1: List<MathProblem> = listOf(
    discRead(1, 0, 2, 1, 2, 2, 0),
    discRead(2, 0, 1, 2, 3, 1, 0),
    discRead(3, 0, 1, 1, 3, 2, 1),
    discRead(4, 0, 0, 4, 2, 0, 0),
    discRead(5, 0, 1, 0, 2, 3, 3),
    discRead(6, 0, 2, 1, 1, 1, 2),
    discRead(7, 0, 1, 0, 3, 3, 1),
    discRead(8, 0, 0, 4, 0, 2, 2),
    discRead(9, 0, 1, 3, 2, 0, 1),
    discRead(10, 0, 0, 2, 3, 2, 3),
    discRead(11, 0, 0, 3, 1, 2, 2),
    discRead(12, 0, 1, 0, 2, 4, 0),
    discRead(13, 0, 1, 1, 1, 2, 4),
    discRead(14, 0, 0, 2, 4, 1, 1),
    discRead(15, 0, 2, 2, 1, 1, 0),
    discRead(16, 0, 1, 2, 2, 2, 2),
    discRead(17, 0, 1, 1, 1, 1, 1),
    discRead(18, 0, 0, 1, 3, 1, 2),
    discRead(19, 0, 0, 0, 5, 2, 1),
    discRead(20, 0, 1, 1, 3, 0, 3),
    discRead(21, 0, 0, 1, 3, 2, 1),
    discRead(22, 0, 1, 3, 2, 1, 1),
    discRead(23, 0, 4, 0, 1, 1, 1),
    discRead(24, 0, 0, 2, 3, 1, 2),
    discRead(25, 0, 1, 1, 2, 2, 1),
    discRead(26, 0, 2, 1, 2, 1, 2),
    discRead(27, 0, 0, 3, 1, 3, 1),
    discRead(28, 0, 1, 2, 1, 1, 1),
    discRead(29, 0, 3, 1, 2, 1, 1),
    discRead(30, 0, 0, 0, 3, 3, 3),
    discRead(31, 0, 0, 1, 5, 0, 1),
    discRead(32, 0, 1, 2, 2, 2, 2),
    discRead(33, 0, 1, 2, 2, 1, 2),
    discRead(34, 0, 0, 2, 3, 2, 2),
    discRead(35, 0, 2, 1, 3, 1, 1),
    discRead(36, 0, 0, 3, 1, 2, 3),
    discRead(37, 0, 1, 0, 3, 3, 1),
    discRead(38, 0, 2, 3, 0, 2, 0),
    discRead(39, 0, 0, 0, 4, 2, 3),
    discRead(40, 0, 1, 1, 2, 1, 1),
    discRead(41, 0, 3, 0, 2, 2, 1),
    discRead(42, 0, 0, 1, 4, 1, 2),
    discRead(43, 0, 1, 3, 2, 1, 0),
    discRead(44, 0, 0, 0, 2, 3, 4),
    discRead(45, 0, 2, 1, 1, 2, 1),
    discRead(46, 1, 3, 0, 1, 1, 2),
    discRead(47, 1, 4, 0, 2, 1, 0),
    discRead(48, 1, 4, 2, 1, 1, 0),
  )

  val ws2: List<MathProblem> = listOf(
    12345, 23476, 34102, 45678, 56789, 67890, 78901, 89120, 98765, 10234,
    11111, 21343, 31212, 41987, 50050, 60606, 70707, 80808, 90909, 99999,
    25678, 35210, 42341, 53456, 62500, 72345, 82456, 91234, 14407, 24024,
    33033, 44444, 55555, 66666, 77777, 88888, 99090, 17890, 28180, 39390,
  ).mapIndexed { idx, num ->
    MathProblem.NumberDiscsBuildProblem(idx + 1, num)
  } + listOf(
    discRead(41, 0, 3, 1, 2, 1, 1),
    discRead(42, 0, 0, 3, 3, 1, 2),
    discRead(43, 0, 1, 2, 1, 2, 3),
    discRead(44, 0, 0, 0, 3, 4, 2),
    discRead(45, 0, 2, 1, 2, 2, 1),
    discRead(46, 0, 0, 4, 2, 1, 2),
    discRead(47, 0, 1, 0, 3, 3, 3),
    discRead(48, 0, 1, 2, 1, 1, 3),
    discRead(49, 0, 2, 2, 2, 2, 1),
    discRead(50, 0, 1, 1, 2, 3, 2),
    discRead(51, 0, 0, 2, 4, 1, 1),
    discRead(52, 0, 2, 2, 1, 1, 2),
    discRead(53, 0, 0, 0, 5, 2, 4),
    discRead(54, 0, 1, 2, 3, 2, 1),
    discRead(55, 0, 0, 1, 5, 1, 1),
    discRead(56, 0, 3, 1, 2, 1, 1),
  )

  val ws3: List<MathProblem> = listOf(
    pvByDigit(1, 5, 29151), pvByDigit(2, 1, 54251), pvByDigit(3, 8, 86172), pvByDigit(4, 3, 38758), pvByDigit(5, 7, 72838),
    pvByDigit(6, 5, 90512), pvByDigit(7, 5, 54418), pvByDigit(8, 8, 85371), pvByDigit(9, 1, 51523), pvByDigit(10, 2, 24655),
    pvByDigit(11, 4, 62545), pvByDigit(12, 6, 64782), pvByDigit(13, 4, 47967), pvByDigit(14, 9, 11947), pvByDigit(15, 8, 56784),
    pvByDigit(16, 5, 5319), pvByDigit(17, 7, 87340), pvByDigit(18, 1, 84155), pvByDigit(19, 9, 97323), pvByDigit(20, 3, 94399),
    // 21-25 digit 3
    pvByDigit(21, 3, 93879), pvByDigit(22, 3, 35181), pvByDigit(23, 3, 1397), pvByDigit(24, 3, 41213), pvByDigit(25, 3, 89938),
    // 26-30 digit 5
    pvByDigit(26, 5, 58916), pvByDigit(27, 5, 35090), pvByDigit(28, 5, 9095), pvByDigit(29, 5, 21531), pvByDigit(30, 5, 23451),
    // 31-35 digit 6
    pvByDigit(31, 6, 97162), pvByDigit(32, 6, 21746), pvByDigit(33, 6, 60531), pvByDigit(34, 6, 46810), pvByDigit(35, 6, 6324),
    // 36-40 digit 9
    pvByDigit(36, 9, 9412), pvByDigit(37, 9, 91578), pvByDigit(38, 9, 29122), pvByDigit(39, 9, 41955), pvByDigit(40, 9, 74129),
    // 41-45 digit 2
    pvByDigit(41, 2, 42718), pvByDigit(42, 2, 26531), pvByDigit(43, 2, 72946), pvByDigit(44, 2, 13215), pvByDigit(45, 2, 80462),
    // 46-50 digit 7
    pvByDigit(46, 7, 37501), pvByDigit(47, 7, 67284), pvByDigit(48, 7, 19763), pvByDigit(49, 7, 74095), pvByDigit(50, 7, 87630),
    // 51-55 digit 4
    pvByDigit(51, 4, 84259), pvByDigit(52, 4, 14837), pvByDigit(53, 4, 64701), pvByDigit(54, 4, 42963), pvByDigit(55, 4, 35124),
    // 56-60 digit 8
    pvByDigit(56, 8, 18526), pvByDigit(57, 8, 68341), pvByDigit(58, 8, 82769), pvByDigit(59, 8, 90483), pvByDigit(60, 8, 28150),
  )

  val ws4: List<MathProblem> = listOf(
    valByPlace(1, 64259, "ones"), valByPlace(2, 64259, "ten thousands"), valByPlace(3, 64259, "tens"), valByPlace(4, 64259, "hundreds"), valByPlace(5, 64259, "thousands"),
    valByPlace(6, 49402, "hundreds"), valByPlace(7, 49402, "tens"), valByPlace(8, 49402, "ones"), valByPlace(9, 49402, "ten thousands"), valByPlace(10, 49402, "thousands"),
    valByPlace(11, 3546, "thousands"), valByPlace(12, 3546, "hundreds"), valByPlace(13, 3546, "tens"), valByPlace(14, 3546, "ones"),
    valByPlace(15, 93546, "tens"), valByPlace(16, 93546, "thousands"), valByPlace(17, 93546, "ten thousands"), valByPlace(18, 93546, "hundreds"), valByPlace(19, 93546, "ones"),
    valByPlace(20, 1234, "ones"), valByPlace(21, 1234, "hundreds"), valByPlace(22, 1234, "tens"), valByPlace(23, 1234, "thousands"),
    valByPlace(24, 17382, "ten thousands"), valByPlace(25, 17382, "thousands"), valByPlace(26, 17382, "tens"), valByPlace(27, 17382, "ones"), valByPlace(28, 17382, "hundreds"),
    valByPlace(29, 70615, "thousands"), valByPlace(30, 70615, "hundreds"), valByPlace(31, 70615, "ones"), valByPlace(32, 70615, "ten thousands"), valByPlace(33, 70615, "tens"),
    valByPlace(34, 8190, "hundreds"), valByPlace(35, 8190, "ones"), valByPlace(36, 8190, "tens"), valByPlace(37, 8190, "thousands"),
    valByPlace(38, 25971, "ten thousands"), valByPlace(39, 25971, "thousands"), valByPlace(40, 25971, "hundreds"), valByPlace(41, 25971, "ones"), valByPlace(42, 25971, "tens"),
    valByPlace(43, 9403, "tens"), valByPlace(44, 9403, "hundreds"), valByPlace(45, 9403, "thousands"), valByPlace(46, 9403, "ones"),
  )

  val ws5: List<MathProblem> = listOf(
    valByPlace(1, 64875, "ten thousands"),
    valByPlace(2, 87443, "hundreds"),
    valByPlace(3, 100000, "hundred thousands"),
    valByPlace(4, 20534, "ones"),
    valByPlace(5, 83021, "tens"),
    valByPlace(6, 21108, "ten thousands"),
    valByPlace(7, 63921, "hundreds"),
    valByPlace(8, 94124, "thousands"),
    valByPlace(9, 5912, "hundreds"),
    valByPlace(10, 15773, "ones"),
    valByPlace(11, 74694, "ones"),
    valByPlace(12, 28455, "tens"),
    valByPlace(13, 77722, "ten thousands"),
    valByPlace(14, 95857, "hundreds"),
    valByPlace(15, 8112, "hundreds"),
    valByPlace(16, 31627, "ten thousands"),
    valByPlace(17, 54614, "thousands"),
    valByPlace(18, 24712, "ten thousands"),
    valByPlace(19, 1237, "ones"),
    valByPlace(20, 32114, "tens"),
    valByPlace(21, 5732, "hundreds"),
    valByPlace(22, 64421, "hundreds"),
    valByPlace(23, 89365, "tens"),
    valByPlace(24, 45188, "ten thousands"),
    valByPlace(25, 25714, "ten thousands"),
    valByPlace(26, 76438, "hundreds"),
    valByPlace(27, 13907, "thousands"),
    valByPlace(28, 92315, "ones"),
    valByPlace(29, 8642, "tens"),
    valByPlace(30, 43120, "ten thousands"),
    valByPlace(31, 55209, "hundreds"),
    valByPlace(32, 67818, "hundreds"),
    valByPlace(33, 2319, "ones"),
    valByPlace(34, 71463, "tens"),
    valByPlace(35, 96580, "thousands"),
    valByPlace(36, 19237, "ten thousands"),
    valByPlace(37, 5824, "hundreds"),
    valByPlace(38, 38619, "ones"),
    valByPlace(39, 14256, "tens"),
    valByPlace(40, 84372, "hundreds"),
    valByPlace(41, 62905, "ten thousands"),
    valByPlace(42, 7531, "thousands"),
    valByPlace(43, 29847, "ones"),
    valByPlace(44, 91208, "hundreds"),
    valByPlace(45, 36741, "tens"),
    valByPlace(46, 48203, "hundreds"),
    valByPlace(47, 73690, "ten thousands"),
    valByPlace(48, 2146, "ones"),
    valByPlace(49, 85509, "thousands"),
    valByPlace(50, 60934, "hundreds"),
  )
}
