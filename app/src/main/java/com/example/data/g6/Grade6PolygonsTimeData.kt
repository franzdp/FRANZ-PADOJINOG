package com.example.data.g6

import com.example.model.MathProblem
import com.example.model.PolygonRegularProblem
import com.example.model.PolygonTableProblem
import com.example.model.TimeConvertProblem

object Grade6PolygonsTimeData {

  private val polygonNamesList = listOf(
    "Quadrilateral",
    "Pentagon",
    "Hexagon",
    "Heptagon",
    "Octagon",
    "Nonagon",
    "Decagon",
    "Hendecagon",
    "Dodecagon",
  )

  private fun polyRow(
    item: Int,
    sides: Int,
    givenName: String?,
    givenSides: Int?,
    showFig: Boolean,
    name: String,
    hint: String,
  ) = PolygonTableProblem(
    itemNumber = item,
    polygonSides = sides,
    givenName = givenName,
    givenSides = givenSides,
    showFigure = showFig,
    expectedName = name,
    expectedSides = sides,
    choiceNames = polygonNamesList,
    hint = hint,
  )

  private fun regularItem(item: Int, isRegular: Boolean, hint: String) =
    PolygonRegularProblem(itemNumber = item, polygonId = item, isRegular = isRegular, hint = hint)

  private fun timeTable24(item: Int, label12: String, hour24: Int): TimeConvertProblem {
    val hh = "%02d".format(hour24)
    return TimeConvertProblem(
      itemNumber = item,
      prompt = "Write $label12 in 24-hour format:",
      sourceDisplay = label12,
      to24Hour = true,
      validAnswers = listOf("$hh:00", "${hh}00", "$hour24:00"),
      displayAnswer = "$hh:00",
      hint = "12:00 AM (midnight) starts day at 00:00; 1:00–11:00 AM keep the hour padded to 2 digits (01–11); 12:00 PM (noon) is 12:00; 1:00–11:00 PM add 12 to the hour.",
    )
  }

  private fun clock24(item: Int, hour12: Int, min: Int, isPm: Boolean): TimeConvertProblem {
    val h24 = when {
      !isPm && hour12 == 12 -> 0
      isPm && hour12 != 12 -> hour12 + 12
      else -> hour12
    }
    val hh = "%02d".format(h24)
    val mm = "%02d".format(min)
    val period = if (isPm) "PM" else "AM"
    return TimeConvertProblem(
      itemNumber = item,
      prompt = "Read the analog clock ($period) and write the time in 24-hour format:",
      sourceDisplay = period,
      to24Hour = true,
      clockHour = hour12,
      clockMinute = min,
      clockIsPm = isPm,
      validAnswers = listOf("$hh:$mm", "$hh$mm", "$h24:$mm"),
      displayAnswer = "$hh:$mm",
      hint = "Read the hour and minute hands on the analog clock first, then convert $period to 24-hour format (for 12 AM use 00; for 1–11 PM add 12 to the hour).",
    )
  }

  private fun to24(item: Int, h12: Int, m: Int, isPm: Boolean): TimeConvertProblem {
    val period = if (isPm) "PM" else "AM"
    val src = "%d:%02d %s".format(h12, m, period)
    val h24 = when {
      !isPm && h12 == 12 -> 0
      isPm && h12 != 12 -> h12 + 12
      else -> h12
    }
    val hh = "%02d".format(h24)
    val mm = "%02d".format(m)
    return TimeConvertProblem(
      itemNumber = item,
      prompt = "Convert to 24-hour format:",
      sourceDisplay = src,
      to24Hour = true,
      validAnswers = listOf("$hh:$mm", "$hh$mm", "$h24:$mm"),
      displayAnswer = "$hh:$mm",
      hint = "For 12:__ AM use 00:__; for 1:__–11:__ AM keep the hour padded to 2 digits; for 12:__ PM keep 12:__; for 1:__–11:__ PM add 12 to the hour.",
    )
  }

  private fun to12(item: Int, code24: String): TimeConvertProblem {
    val h24 = code24.substring(0, 2).toInt()
    val m = code24.substring(2, 4).toInt()
    val isPm = h24 >= 12
    val h12 = when {
      h24 == 0 -> 12
      h24 > 12 -> h24 - 12
      else -> h24
    }
    val mm = "%02d".format(m)
    val period = if (isPm) "PM" else "AM"
    val std = "$h12:$mm $period"
    val pad = "%02d:%02d %s".format(h12, m, period)
    return TimeConvertProblem(
      itemNumber = item,
      prompt = "Convert $code24 to 12-hour format:",
      sourceDisplay = code24,
      to24Hour = false,
      validAnswers = listOf(std, pad),
      displayAnswer = std,
      hint = "For 00:__ use 12:__ AM; for 01–11 keep the hour and use AM; for 12:__ use 12:__ PM; for 13–23 subtract 12 from the hour and use PM.",
    )
  }

  val ws25: List<MathProblem> = listOf(
    polyRow(1, 4, "Quadrilateral", null, true, "Quadrilateral", "Count the straight sides/corners on the figure, or use the prefix Quad-."),
    polyRow(2, 6, "Hexagon", null, true, "Hexagon", "Count the straight sides/corners on the figure, or use the prefix Hexa-."),
    polyRow(3, 8, "Octagon", null, true, "Octagon", "Count the straight sides/corners on the figure, or use the prefix Octa- (like octopus)."),
    polyRow(4, 10, null, 10, true, "Decagon", "Match the side count using Greek prefixes: Quad=4, Penta=5, Hexa=6, Hepta=7, Octa=8, Nona=9, Deca=10."),
    polyRow(5, 7, "Heptagon", null, true, "Heptagon", "Count the straight sides/corners on the figure, or use the prefix Hepta-."),
    polyRow(6, 9, "Nonagon", 9, false, "Nonagon", "Count the corners/sides on each polygon choice to find the matching figure."),
    polyRow(7, 5, "Pentagon", null, true, "Pentagon", "Count the straight sides/corners on the figure, or use the prefix Penta-."),
    polyRow(8, 10, "Decagon", 10, false, "Decagon", "Count the corners/sides on each polygon choice to find the matching figure."),
    polyRow(9, 11, "Hendecagon", null, true, "Hendecagon", "Count the straight sides/corners on the figure, or use the prefix Hendeca-."),
    polyRow(10, 9, null, 9, true, "Nonagon", "Match the side count using Greek prefixes: Hepta=7, Octa=8, Nona=9, Deca=10, Hendeca=11."),
    polyRow(11, 8, "Octagon", 8, false, "Octagon", "Count the corners/sides on each polygon choice to find the matching figure."),
    polyRow(12, 12, "Dodecagon", null, true, "Dodecagon", "Count the straight sides/corners on the figure, or use the prefix Dodeca-."),
    polyRow(13, 6, null, 6, true, "Hexagon", "Match the side count using Greek prefixes: Quad=4, Penta=5, Hexa=6, Hepta=7, Octa=8."),
    polyRow(14, 7, "Heptagon", 7, false, "Heptagon", "Count the corners/sides on each polygon choice to find the matching figure."),
    polyRow(15, 10, "Decagon", null, true, "Decagon", "Count the straight sides/corners on the figure, or use the prefix Deca-."),
    polyRow(16, 5, "Pentagon", null, true, "Pentagon", "Count the straight sides/corners on the figure, or use the prefix Penta-."),
    polyRow(17, 8, null, 8, true, "Octagon", "Match the side count using Greek prefixes: Hexa=6, Hepta=7, Octa=8, Nona=9, Deca=10."),
    polyRow(18, 4, "Quadrilateral", 4, false, "Quadrilateral", "Count the corners/sides on each polygon choice to find the matching figure."),
  )

  private val regHint = "Check every side and angle carefully: a Regular polygon has ALL equal side lengths AND ALL equal interior angles (no inward dents). If any side or angle differs, it is Irregular."

  val ws26: List<MathProblem> = listOf(
    regularItem(1, true, regHint),
    regularItem(2, false, regHint),
    regularItem(3, true, regHint),
    regularItem(4, false, regHint),
    regularItem(5, false, regHint),
    regularItem(6, false, regHint),
    regularItem(7, true, regHint),
    regularItem(8, true, regHint),
    regularItem(9, false, regHint),
    regularItem(10, false, regHint),
    regularItem(11, false, regHint),
    regularItem(12, false, regHint),
    regularItem(13, false, regHint),
    regularItem(14, false, regHint),
    regularItem(15, false, regHint),
    regularItem(16, false, regHint),
    regularItem(17, false, regHint),
    regularItem(18, false, regHint),
    regularItem(19, true, regHint),
    regularItem(20, false, regHint),
    regularItem(21, false, regHint),
    regularItem(22, false, regHint),
    regularItem(23, true, regHint),
    regularItem(24, true, regHint),
    regularItem(25, false, regHint),
    regularItem(26, false, regHint),
    regularItem(27, true, regHint),
    regularItem(28, false, regHint),
    regularItem(29, false, regHint),
    regularItem(30, true, regHint),
    regularItem(31, false, regHint),
    regularItem(32, false, regHint),
    regularItem(33, true, regHint),
    regularItem(34, false, regHint),
    regularItem(35, false, regHint),
    regularItem(36, false, regHint),
    regularItem(37, true, regHint),
    regularItem(38, false, regHint),
    regularItem(39, false, regHint),
    regularItem(40, true, regHint),
  )

  val ws27: List<MathProblem> = listOf(
    timeTable24(1, "12:00 AM", 0), timeTable24(2, "1:00 AM", 1), timeTable24(3, "2:00 AM", 2),
    timeTable24(4, "3:00 AM", 3), timeTable24(5, "4:00 AM", 4), timeTable24(6, "5:00 AM", 5),
    timeTable24(7, "6:00 AM", 6), timeTable24(8, "7:00 AM", 7), timeTable24(9, "8:00 AM", 8),
    timeTable24(10, "9:00 AM", 9), timeTable24(11, "10:00 AM", 10), timeTable24(12, "11:00 AM", 11),
    timeTable24(13, "12:00 PM", 12), timeTable24(14, "1:00 PM", 13), timeTable24(15, "2:00 PM", 14),
    timeTable24(16, "3:00 PM", 15), timeTable24(17, "4:00 PM", 16), timeTable24(18, "5:00 PM", 17),
    timeTable24(19, "6:00 PM", 18), timeTable24(20, "7:00 PM", 19), timeTable24(21, "8:00 PM", 20),
    timeTable24(22, "9:00 PM", 21), timeTable24(23, "10:00 PM", 22), timeTable24(24, "11:00 PM", 23),
  )

  val ws28: List<MathProblem> = listOf(
    clock24(1, 3, 0, false),
    clock24(2, 1, 30, false),
    clock24(3, 2, 0, true),
    clock24(4, 5, 45, true),
    clock24(5, 12, 30, false),
    clock24(6, 6, 0, true),
    clock24(7, 9, 45, true),
    clock24(8, 8, 15, true),
    clock24(9, 8, 45, true),
    clock24(10, 6, 15, false),
    clock24(11, 6, 10, false),
    clock24(12, 4, 45, true),
    clock24(13, 12, 15, false),
    clock24(14, 1, 45, true),
    clock24(15, 8, 0, false),
    clock24(16, 10, 15, true),
  )

  val ws29: List<MathProblem> = listOf(
    // 1-12: 12hr -> 24hr
    to24(1, 12, 10, false), to24(2, 7, 45, true), to24(3, 5, 5, false),
    to24(4, 4, 45, true), to24(5, 9, 30, false), to24(6, 2, 15, false),
    to24(7, 8, 25, true), to24(8, 1, 55, true), to24(9, 8, 0, false),
    to24(10, 10, 15, true), to24(11, 12, 0, true), to24(12, 3, 40, false),
    // 13-24: 24hr -> 12hr
    to12(13, "1650"), to12(14, "2235"), to12(15, "1800"), to12(16, "1545"),
    to12(17, "1330"), to12(18, "0000"), to12(19, "2015"), to12(20, "1755"),
    to12(21, "2140"), to12(22, "0615"), to12(23, "0410"), to12(24, "2355"),
    // 25-36: 12hr -> 24hr
    to24(25, 2, 50, false), to24(26, 7, 35, true), to24(27, 11, 48, true),
    to24(28, 7, 0, false), to24(29, 9, 0, true), to24(30, 10, 15, true),
    to24(31, 12, 15, false), to24(32, 8, 45, false), to24(33, 1, 30, true),
    to24(34, 4, 15, true), to24(35, 3, 35, true), to24(36, 5, 0, false),
    // 37-48: 24hr -> 12hr
    to12(37, "0545"), to12(38, "1950"), to12(39, "1200"), to12(40, "1715"),
    to12(41, "0910"), to12(42, "0010"), to12(43, "1445"), to12(44, "2230"),
    to12(45, "1115"), to12(46, "2325"), to12(47, "1650"), to12(48, "0335"),
  )
}
