package com.example.data

import com.example.model.*

object FractionsAndPatternsData {

  fun getWorksheet1(): List<MathProblem> {
    val numbers = listOf(
      23, 25, 88, 22, 73, 16, 29, 72, 48, 84,
      45, 183, 902, 100, 528, 565, 982, 126, 609, 432,
      25, 71, 91, 10, 63, 83, 174, 125, 364, 723,
      352, 947, 727, 736, 720, 500, 238, 147, 371, 945,
    )
    return numbers.mapIndexed { index, num ->
      MathProblem.OddEvenProblem(
        itemNumber = index + 1,
        number = num,
        isEven = num % 2 == 0,
      )
    }
  }

  fun getWorksheet2(): List<MathProblem> {
    data class RawFracPair(val ln: Int, val ld: Int, val rn: Int, val rd: Int, val leftStr: String? = null)
    val similar = listOf(
      RawFracPair(1, 3, 2, 3), RawFracPair(4, 4, 1, 4), RawFracPair(3, 10, 9, 10),
      RawFracPair(5, 6, 3, 6), RawFracPair(9, 17, 15, 17), RawFracPair(8, 9, 7, 9),
      RawFracPair(10, 10, 9, 10), RawFracPair(11, 15, 14, 15), RawFracPair(16, 18, 6, 18),
      RawFracPair(200, 300, 250, 300), RawFracPair(431, 576, 123, 576), RawFracPair(323, 522, 332, 522),
      RawFracPair(101, 200, 110, 200), RawFracPair(134, 150, 144, 150), RawFracPair(366, 700, 300, 700),
      RawFracPair(250, 280, 240, 280), RawFracPair(66, 400, 60, 400), RawFracPair(990, 1000, 999, 1000),
      RawFracPair(780, 800, 708, 800), RawFracPair(236, 347, 347, 347), RawFracPair(845, 987, 897, 987),
      RawFracPair(624, 676, 345, 676),
    )
    val dissimilar = listOf(
      RawFracPair(1, 3, 1, 5), RawFracPair(3, 4, 3, 5), RawFracPair(2, 4, 2, 8),
      RawFracPair(5, 3, 5, 4), RawFracPair(7, 10, 5, 8), RawFracPair(1, 1, 1, 4, "1"),
      RawFracPair(2, 5, 1, 2), RawFracPair(3, 5, 3, 4), RawFracPair(1, 2, 1, 6),
      RawFracPair(1, 6, 1, 5), RawFracPair(3, 9, 3, 6), RawFracPair(4, 7, 4, 8),
      RawFracPair(7, 10, 7, 15), RawFracPair(4, 15, 3, 16), RawFracPair(25, 75, 50, 70),
      RawFracPair(8, 16, 8, 32), RawFracPair(5, 15, 5, 10), RawFracPair(40, 70, 40, 60),
      RawFracPair(4, 16, 4, 20), RawFracPair(6, 10, 8, 12), RawFracPair(30, 50, 35, 60),
      RawFracPair(20, 25, 17, 30), RawFracPair(1, 5, 1, 3), RawFracPair(2, 3, 2, 5),
      RawFracPair(1, 3, 1, 6), RawFracPair(4, 6, 4, 5), RawFracPair(1, 2, 1, 3),
      RawFracPair(2, 3, 2, 5),
    )
    val all = similar.map { it to true } + dissimilar.map { it to false }
    return all.mapIndexed { idx, (pair, isSim) ->
      val leftCross = pair.ln * pair.rd
      val rightCross = pair.rn * pair.ld
      val sym = when {
        leftCross < rightCross -> "<"
        leftCross > rightCross -> ">"
        else -> "="
      }
      val lLabel = pair.leftStr ?: "${pair.ln}/${pair.ld}"
      val rLabel = "${pair.rn}/${pair.rd}"
      val hint = if (isSim) {
        "Same denominator (${pair.ld}): compare numerators ${pair.ln} and ${pair.rn}."
      } else {
        "Cross-multiply numerator × opposite denominator (${pair.ln} × ${pair.rd} vs ${pair.ld} × ${pair.rn}) to compare both sides!"
      }
      MathProblem.CompareFractionsProblem(
        itemNumber = idx + 1,
        leftLabel = lLabel,
        leftNum = pair.ln,
        leftDen = pair.ld,
        rightLabel = rLabel,
        rightNum = pair.rn,
        rightDen = pair.rd,
        answerSymbol = sym,
        isSimilar = isSim,
        hint = hint,
      )
    }
  }

  fun getWorksheet3(): List<MathProblem> {
    val ascendingSets = listOf(
      listOf(2 to 9, 5 to 9, 3 to 9, 4 to 9),
      listOf(4 to 15, 2 to 15, 7 to 15, 5 to 15),
      listOf(3 to 50, 15 to 50, 22 to 50, 11 to 50),
      listOf(45 to 60, 34 to 60, 9 to 60, 16 to 60),
      listOf(6 to 12, 6 to 15, 6 to 20, 6 to 25),
      listOf(7 to 49, 7 to 56, 7 to 35, 7 to 28),
      listOf(5 to 30, 5 to 25, 5 to 70, 5 to 65),
      listOf(5 to 90, 5 to 50, 5 to 10, 5 to 20),
      listOf(15 to 45, 15 to 30, 15 to 40, 15 to 50),
      listOf(15 to 20, 10 to 20, 6 to 20, 8 to 20),
      listOf(11 to 22, 15 to 22, 19 to 22, 7 to 22),
      listOf(18 to 90, 9 to 90, 27 to 90, 30 to 90),
      listOf(16 to 30, 16 to 40, 16 to 50, 16 to 60),
      listOf(16 to 25, 11 to 25, 6 to 25, 21 to 25),
    )
    val descendingSets = listOf(
      listOf(13 to 26, 13 to 50, 13 to 30, 13 to 45),
      listOf(24 to 60, 24 to 64, 24 to 20, 24 to 50),
      listOf(8 to 64, 8 to 80, 8 to 12, 8 to 40),
      listOf(9 to 45, 5 to 45, 20 to 45, 25 to 45),
      listOf(4 to 8, 2 to 8, 3 to 8, 5 to 8),
      listOf(9 to 16, 10 to 16, 8 to 16, 12 to 16),
      listOf(9 to 27, 9 to 18, 9 to 36, 9 to 45),
      listOf(8 to 40, 8 to 30, 8 to 20, 8 to 24),
      listOf(45 to 50, 44 to 50, 42 to 50, 40 to 50),
      listOf(30 to 60, 30 to 50, 30 to 45, 30 to 55),
      listOf(12 to 24, 12 to 25, 12 to 22, 12 to 20),
      listOf(19 to 87, 15 to 87, 24 to 87, 20 to 87),
      listOf(7 to 15, 6 to 15, 5 to 15, 3 to 15),
      listOf(11 to 23, 5 to 23, 15 to 23, 20 to 23),
      listOf(55 to 90, 45 to 90, 50 to 90, 60 to 90),
      listOf(32 to 64, 25 to 64, 39 to 64, 46 to 64),
    )
    val items = mutableListOf<MathProblem>()
    ascendingSets.forEachIndexed { idx, raw ->
      val fracs = raw.map { FractionItem(it.first, it.second) }
      val expected = fracs.sortedBy { it.num.toDouble() / it.den }
      val sameDen = fracs.all { it.den == fracs.first().den }
      items += MathProblem.OrderFractionsProblem(
        itemNumber = idx + 1,
        fractions = fracs,
        ascending = true,
        expectedOrder = expected,
        hint = if (sameDen) "Same denominator: smaller numerator = smaller fraction."
        else "Same numerator: larger denominator = smaller fraction!",
      )
    }
    descendingSets.forEachIndexed { idx, raw ->
      val fracs = raw.map { FractionItem(it.first, it.second) }
      val expected = fracs.sortedByDescending { it.num.toDouble() / it.den }
      val sameDen = fracs.all { it.den == fracs.first().den }
      items += MathProblem.OrderFractionsProblem(
        itemNumber = 15 + idx,
        fractions = fracs,
        ascending = false,
        expectedOrder = expected,
        hint = if (sameDen) "Same denominator: larger numerator = larger fraction."
        else "Same numerator: smaller denominator = larger fraction!",
      )
    }
    return items
  }

  fun getWorksheet4(): List<MathProblem> {
    val list = mutableListOf<MathProblem>()
    // 1-5: Identify numerator
    listOf(1 to 5, 25 to 30, 6 to 10, 11 to 16, 7 to 4).forEachIndexed { i, (n, d) ->
      list += MathProblem.FractionPartProblem(
        itemNumber = i + 1, num = n, den = d, askNumerator = true,
        hint = "The numerator is the TOP number above the fraction bar.",
      )
    }
    // 6-10: Identify denominator
    listOf(21 to 35, 15 to 20, 6 to 8, 10 to 28, 4 to 5).forEachIndexed { i, (n, d) ->
      list += MathProblem.FractionPartProblem(
        itemNumber = i + 6, num = n, den = d, askNumerator = false,
        hint = "The denominator is the BOTTOM number below the fraction bar.",
      )
    }
    // 11-19 (5 figures: Diamond 11-13, Triangles 14-16, Rect 17, Hex 18, Circle 19)
    list += MathProblem.ShadedFigureFractionProblem(
      itemNumber = 11, figureType = ShadedFigureType.DIAMOND_4, num = 1, den = 4,
      prompt = "Nos. 11–13: Fill in the Numerator, Denominator, and Fraction for the diamond.",
      askBreakdown = true,
      hint = "Count the shaded triangle(s) out of all equal triangles in the diamond.",
    )
    list += MathProblem.ShadedFigureFractionProblem(
      itemNumber = 14, figureType = ShadedFigureType.TRIANGLES_3, num = 1, den = 3,
      prompt = "Nos. 14–16: Fill in the Numerator, Denominator, and Fraction for the 3-triangle shape.",
      askBreakdown = true,
      hint = "Count the shaded triangle(s) out of all equal triangles in the shape.",
    )
    list += MathProblem.ShadedFigureFractionProblem(
      itemNumber = 17, figureType = ShadedFigureType.RECT_4, num = 3, den = 4,
      prompt = "What fraction of the rectangle is shaded?",
      askBreakdown = false,
      hint = "Count the shaded strips (numerator) over all equal strips (denominator).",
    )
    list += MathProblem.ShadedFigureFractionProblem(
      itemNumber = 18, figureType = ShadedFigureType.HEXAGON_6, num = 2, den = 6,
      prompt = "What fraction of the hexagon is shaded?",
      askBreakdown = false,
      hint = "Count the shaded triangles (numerator) over all equal triangles in the hexagon (denominator).",
    )
    list += MathProblem.ShadedFigureFractionProblem(
      itemNumber = 19, figureType = ShadedFigureType.CIRCLE_8, num = 3, den = 8,
      prompt = "What fraction of the circle is shaded?",
      askBreakdown = false,
      hint = "Count the shaded pie slices (numerator) over all equal pie slices (denominator).",
    )
    // 20-39: Picture fractions
    val pictureItems = listOf(
      Triple(20, PictureSceneType.HALLOWEEN_GROUP, "What fraction of the group are vampires?") to (2 to 9),
      Triple(21, PictureSceneType.HALLOWEEN_GROUP, "What fraction of the group are skeletons?") to (3 to 9),
      Triple(22, PictureSceneType.HALLOWEEN_GROUP, "What fraction of the group are witches?") to (2 to 9),
      Triple(23, PictureSceneType.HALLOWEEN_GROUP, "What fraction of the group are ghosts?") to (2 to 9),
      Triple(24, PictureSceneType.TENNIS_RACKETS, "What fraction of the tennis rackets are upside down?") to (2 to 5),
      Triple(25, PictureSceneType.BASKETBALLS, "What fraction of the balls are for basketball?") to (3 to 8),
      Triple(26, PictureSceneType.OWLS_READING, "What fraction of the owls are reading?") to (1 to 9),
      Triple(27, PictureSceneType.PAINT_BRUSHES, "What fraction of the brushes have fat tips?") to (2 to 5),
      Triple(28, PictureSceneType.SMALL_PENCILS, "What fraction of the pencils are small?") to (4 to 6),
      Triple(29, PictureSceneType.SHAPES_GRID_A, "What fraction of the shapes have 4 sides?") to (4 to 12),
      Triple(30, PictureSceneType.SHAPES_GRID_A, "What fraction of the shapes have more than 6 sides?") to (2 to 12),
      Triple(31, PictureSceneType.SHAPES_GRID_A, "What fraction of the shapes have no sides?") to (2 to 12),
      Triple(32, PictureSceneType.SHAPES_GRID_B, "What fraction of the shapes have 3 sides?") to (6 to 10),
      Triple(33, PictureSceneType.PETS_GROUP, "What fraction of the pets are rabbits?") to (3 to 8),
      Triple(34, PictureSceneType.PETS_GROUP, "What fraction of the pets are dogs?") to (3 to 8),
      Triple(35, PictureSceneType.TARSIERS_GROUP, "What fraction of the tarsiers are clinging to a branch?") to (2 to 5),
      Triple(36, PictureSceneType.TARSIERS_GROUP, "What fraction of the tarsiers have goggles?") to (1 to 5),
      Triple(37, PictureSceneType.TARSIERS_GROUP, "What fraction of the tarsiers are facing left?") to (2 to 5),
      Triple(38, PictureSceneType.DRESSES_RIBBON, "What fraction of the dresses have ribbons?") to (4 to 6),
      Triple(39, PictureSceneType.BALLOONS_LIGHT, "What fraction of the balloons are light-colored?") to (2 to 8),
    )
    pictureItems.forEach { (meta, frac) ->
      list += MathProblem.PictureFractionProblem(
        itemNumber = meta.first,
        scene = meta.second,
        prompt = meta.third,
        num = frac.first,
        den = frac.second,
        hint = "Count how many items match the question (top number) out of all items in the picture (bottom number).",
      )
    }
    return list
  }

  fun getWorksheet5(): List<MathProblem> {
    val list = mutableListOf<MathProblem>()
    val numToWord = listOf(
      Triple(1, 2, "one-half"), Triple(3, 4, "three-fourths"), Triple(5, 9, "five-ninths"),
      Triple(2, 11, "two-elevenths"), Triple(6, 7, "six-sevenths"), Triple(2, 3, "two-thirds"),
      Triple(8, 9, "eight-ninths"), Triple(3, 20, "three-twentieths"), Triple(3, 5, "three-fifths"),
      Triple(7, 16, "seven-sixteenths"), Triple(2, 17, "two-seventeenths"), Triple(23, 25, "twenty-three twenty-fifths"),
      Triple(18, 19, "eighteen-nineteenths"), Triple(16, 30, "sixteen-thirtieths"), Triple(20, 23, "twenty twenty-thirds"),
      Triple(88, 90, "eighty-eight ninetieths"),
    )
    numToWord.forEachIndexed { idx, (n, d, w) ->
      list += MathProblem.FractionWordsProblem(
        itemNumber = idx + 1, num = n, den = d, words = w, toWords = true,
        hint = "Write the top number in cardinal form and the bottom number in ordinal form (add -s if numerator > 1).",
      )
    }
    // 17-19 picture fractions
    list += MathProblem.PictureFractionProblem(
      17, PictureSceneType.WHITE_CAPS, "What fraction of the set of caps are white?", 3, 5,
      "Count the white caps out of all caps in the picture.",
    )
    list += MathProblem.PictureFractionProblem(
      18, PictureSceneType.HEART_SHAPES, "What fraction of the shapes are hearts?", 4, 8,
      "Count the heart shapes out of all shapes in the picture.",
    )
    list += MathProblem.PictureFractionProblem(
      19, PictureSceneType.UNLOCKED_PADLOCKS, "What fraction of the padlocks are unlocked?", 5, 5,
      "Check the shackle on each padlock: count how many are open out of all padlocks.",
    )
    // 20-29 fill in blanks
    val blanks = listOf(
      Triple(1, 3, false to true),
      Triple(1, 4, false to true),
      Triple(1, 8, false to true),
      Triple(3, 8, false to true),
      Triple(1, 5, true to false),
      Triple(1, 6, true to true),
      Triple(3, 4, true to true),
      Triple(7, 8, true to true),
      Triple(2, 3, true to true),
      Triple(5, 9, true to true),
    )
    blanks.forEachIndexed { idx, (n, d, flags) ->
      list += MathProblem.FractionBlankProblem(
        itemNumber = 20 + idx, num = n, den = d, askNum = flags.first, askDen = flags.second,
        hint = "Look at the fraction on the left: the top number tells the parts taken, and the bottom number tells total equal parts.",
      )
    }
    // 30-42 words to numbers
    val wordSentences = listOf(
      Triple(1, 4, "one-fourth") to "Mother gave me [one-fourth] of the cake.",
      Triple(1, 3, "one-third") to "Arnold fills [one-third] of the glass with water.",
      Triple(3, 6, "three-sixths") to "Arlita cuts [three-sixths] of the cartolina.",
      Triple(2, 3, "two-thirds") to "Ben painted [two-thirds] of the wall.",
      Triple(2, 5, "two-fifths") to "[Two-fifths] of section Masipag are boys.",
      Triple(1, 8, "one-eighth") to "Each of Aling Cristina's children had [one-eighth] of the pie.",
      Triple(1, 2, "one-half") to "[One-half] of the section Maligaya got a perfect score in their Math test.",
      Triple(3, 4, "three-fourths") to "[Three-fourths] of the world is composed of water.",
      Triple(8, 17, "eight-seventeenths") to "Among the girl scouts, [eight-seventeenths] are grade four students.",
      Triple(5, 6, "five-sixths") to "[Five-sixths] of the mangoes in the basket are already ripe.",
      Triple(9, 10, "nine-tenths") to "[Nine-tenths] of the class are already done with their seatwork.",
      Triple(6, 11, "six-elevenths") to "[Six-elevenths] of the garden is covered with grass.",
      Triple(19, 20, "nineteen-twentieths") to "Vito answered [nineteen-twentieths] of the test questions correctly.",
    )
    wordSentences.forEachIndexed { idx, (frac, sent) ->
      list += MathProblem.FractionWordsProblem(
        itemNumber = 30 + idx, num = frac.first, den = frac.second, words = frac.third,
        toWords = false, sentenceWithHighlight = sent,
        hint = "The first word gives the numerator (top) and the second word gives the denominator (bottom).",
      )
    }
    return list
  }

  private fun txt(s: String) = PatternToken(PatternTokenKind.TEXT, s)
  private fun blank() = PatternToken(PatternTokenKind.BLANK, "?")

  fun getWorksheet6(): List<MathProblem> {
    val list = mutableListOf<MathProblem>()
    fun addNumPattern(num: Int, tokens: List<PatternToken>, ans: List<String>, hint: String) {
      list += MathProblem.PatternProblem(num, tokens, ans, hint = hint)
    }
    // 1-35
    addNumPattern(1, listOf(txt("10"), blank(), blank(), txt("40")), listOf("20", "30"), "Add 10 each step.")
    addNumPattern(2, listOf(txt("3"), blank(), txt("9"), txt("12"), blank()), listOf("6", "15"), "Count by 3s (+3).")
    addNumPattern(3, listOf(txt("5"), blank(), blank(), blank(), txt("25")), listOf("10", "15", "20"), "Count by 5s (+5).")
    addNumPattern(4, listOf(blank(), txt("60"), txt("90"), blank(), blank()), listOf("30", "120", "150"), "Add 30 each step.")
    addNumPattern(5, listOf(txt("20"), blank(), txt("60"), txt("80"), blank()), listOf("40", "100"), "Add 20 each step.")
    addNumPattern(6, listOf(txt("50"), blank(), blank(), txt("200"), txt("250")), listOf("100", "150"), "Add 50 each step.")
    addNumPattern(7, listOf(txt("60"), txt("120"), blank(), blank(), blank()), listOf("180", "240", "300"), "Add 60 each step.")
    addNumPattern(8, listOf(txt("12"), blank(), txt("24"), blank(), blank()), listOf("18", "30", "36"), "Add 6 each step.")
    addNumPattern(9, listOf(txt("15"), blank(), blank(), txt("60"), txt("75")), listOf("30", "45"), "Add 15 each step.")
    addNumPattern(10, listOf(txt("25"), txt("50"), blank(), blank(), txt("125")), listOf("75", "100"), "Count by 25s (+25).")
    addNumPattern(11, listOf(txt("210"), txt("280"), blank(), blank(), txt("490")), listOf("350", "420"), "Add 70 each step.")
    addNumPattern(12, listOf(txt("240"), blank(), txt("400"), txt("480"), blank(), txt("640")), listOf("320", "560"), "Add 80 each step.")
    addNumPattern(13, listOf(txt("250"), txt("500"), blank(), blank(), txt("1250")), listOf("750", "1000"), "Add 250 each step.")
    addNumPattern(14, listOf(txt("45"), txt("90"), blank(), txt("180"), blank()), listOf("135", "225"), "Add 45 each step.")
    addNumPattern(15, listOf(txt("150"), blank(), blank(), txt("600"), txt("750")), listOf("300", "450"), "Add 150 each step.")
    addNumPattern(16, listOf(txt("1"), txt("2"), blank(), blank(), txt("5"), txt("6")), listOf("3", "4"), "Add 1 each step.")
    addNumPattern(17, listOf(txt("43"), txt("50"), txt("57"), txt("64"), blank(), txt("78"), blank()), listOf("71", "85"), "Add 7 each step.")
    addNumPattern(18, listOf(txt("2"), txt("2"), txt("4"), txt("4"), txt("6"), txt("6"), txt("8"), txt("8"), blank(), blank()), listOf("10", "10"), "Each even number repeats twice.")
    addNumPattern(19, listOf(txt("0"), blank(), txt("8"), txt("12"), txt("16")), listOf("4"), "Add 4 each step.")
    addNumPattern(20, listOf(txt("4"), txt("11"), txt("18"), blank()), listOf("25"), "Add 7 each step.")
    addNumPattern(21, listOf(txt("1"), txt("2"), txt("4"), txt("8"), blank()), listOf("16"), "Multiply by 2 each step.")
    addNumPattern(22, listOf(txt("1"), txt("3"), txt("9"), blank(), txt("81")), listOf("27"), "Multiply by 3 each step.")
    addNumPattern(23, listOf(txt("26"), blank(), txt("78"), txt("104")), listOf("52"), "Add 26 each step.")
    addNumPattern(24, listOf(txt("1"), txt("2"), txt("4"), txt("5"), txt("10"), txt("11"), blank(), blank()), listOf("22", "23"), "Alternate ×2 and +1.")
    addNumPattern(25, listOf(txt("2"), txt("3"), txt("5"), txt("6"), txt("8"), txt("9"), blank(), blank(), blank()), listOf("11", "12", "14"), "Alternate +1 and +2.")
    addNumPattern(26, listOf(txt("19"), txt("28"), txt("37"), txt("46"), blank(), blank()), listOf("55", "64"), "Add 9 each step.")
    addNumPattern(27, listOf(txt("10"), txt("9"), txt("8"), txt("7"), blank(), blank()), listOf("6", "5"), "Subtract 1 each step.")
    addNumPattern(28, listOf(txt("100"), txt("90"), blank(), txt("70"), txt("60")), listOf("80"), "Subtract 10 each step.")
    addNumPattern(29, listOf(txt("1000"), txt("750"), txt("500"), blank()), listOf("250"), "Subtract 250 each step.")
    addNumPattern(30, listOf(txt("30"), txt("25"), txt("21"), txt("18"), blank(), blank()), listOf("16", "15"), "Subtract 5, then 4, then 3, then 2, then 1.")
    addNumPattern(31, listOf(txt("50"), txt("41"), txt("33"), txt("26"), blank()), listOf("20"), "Subtract 9, then 8, then 7, then 6.")
    addNumPattern(32, listOf(txt("3"), txt("6"), txt("8"), txt("12"), txt("15"), txt("17"), txt("21"), txt("24"), txt("26"), blank(), blank(), blank()), listOf("30", "33", "35"), "Repeat +3, +2, +4.")
    addNumPattern(33, listOf(txt("100"), txt("99"), txt("97"), txt("94"), txt("90"), blank(), blank()), listOf("85", "79"), "Subtract 1, 2, 3, 4, 5, 6.")
    addNumPattern(34, listOf(txt("2"), txt("4"), txt("8"), txt("16"), blank(), blank()), listOf("32", "64"), "Multiply by 2 each step.")
    addNumPattern(35, listOf(txt("9"), txt("14"), txt("19"), txt("24"), blank(), blank()), listOf("29", "34"), "Add 5 each step.")
    // 36-40
    addNumPattern(36, listOf(txt("4"), txt("8"), txt("12"), blank(), txt("20")), listOf("16"), "Add 4 each step.")
    addNumPattern(37, listOf(txt("9"), txt("18"), blank(), txt("36"), txt("45")), listOf("27"), "Add 9 each step.")
    addNumPattern(38, listOf(txt("30"), txt("27"), blank(), txt("21"), txt("18")), listOf("24"), "Subtract 3 each step.")
    addNumPattern(39, listOf(txt("100"), txt("90"), blank(), txt("70"), txt("60")), listOf("80"), "Subtract 10 each step.")
    addNumPattern(40, listOf(txt("7"), txt("14"), txt("21"), blank()), listOf("28"), "Add 7 each step.")
    // 41-45 mixed terms
    addNumPattern(
      41,
      listOf(
        PatternToken(PatternTokenKind.FILLED_TRIANGLE), txt("10"),
        PatternToken(PatternTokenKind.FILLED_SQUARE), txt("20"),
        PatternToken(PatternTokenKind.FILLED_TRIANGLE), blank(),
        PatternToken(PatternTokenKind.FILLED_SQUARE),
      ),
      listOf("30"),
      "Look at how much the numbers increase each step and continue the rule.",
    )
    list += MathProblem.PatternProblem(
      42,
      listOf(txt("A"), txt("5"), txt("B"), txt("10"), txt("C"), txt("15"), blank(), txt("20")),
      listOf("D"),
      choices = listOf("D", "E", "25", "C"),
      isShapeOrLetterChoice = true,
      hint = "Notice two alternating patterns: letters step forward alphabetically, while numbers count by 5s.",
    )
    addNumPattern(
      43,
      listOf(
        txt("2"), PatternToken(PatternTokenKind.FILLED_STAR),
        txt("4"), PatternToken(PatternTokenKind.OUTLINE_STAR),
        txt("6"), PatternToken(PatternTokenKind.FILLED_STAR),
        blank(), PatternToken(PatternTokenKind.OUTLINE_STAR),
      ),
      listOf("8"),
      "Look at the numbers between the stars — how much do they increase each time?",
    )
    list += MathProblem.PatternProblem(
      44,
      listOf(
        PatternToken(PatternTokenKind.APPLE), txt("8"),
        PatternToken(PatternTokenKind.COOKIE), txt("16"),
        PatternToken(PatternTokenKind.APPLE), txt("24"),
        blank(), txt("32"),
      ),
      listOf("COOKIE"),
      choices = listOf("COOKIE", "APPLE", "30", "STAR"),
      isShapeOrLetterChoice = true,
      hint = "Check how the two pictures take turns in every odd and even position.",
    )
    list += MathProblem.PatternProblem(
      45,
      listOf(txt("M"), txt("3"), txt("N"), txt("6"), txt("O"), txt("9"), blank(), txt("12")),
      listOf("P"),
      choices = listOf("P", "Q", "10", "N"),
      isShapeOrLetterChoice = true,
      hint = "Follow the alphabetical order of the letters alternating with multiples of 3.",
    )
    // 46-50 multiple choice
    list += MathProblem.PatternProblem(
      46,
      listOf(
        PatternToken(PatternTokenKind.OUTLINE_TRIANGLE),
        PatternToken(PatternTokenKind.OUTLINE_SQUARE),
        PatternToken(PatternTokenKind.OUTLINE_TRIANGLE),
        PatternToken(PatternTokenKind.OUTLINE_SQUARE),
        PatternToken(PatternTokenKind.OUTLINE_TRIANGLE),
        blank(),
      ),
      listOf("SQUARE"),
      choices = listOf("SQUARE", "STAR", "TRIANGLE"),
      isShapeOrLetterChoice = true,
      hint = "Notice the two shapes taking turns one after the other.",
    )
    list += MathProblem.PatternProblem(
      47,
      listOf(txt("4"), txt("8"), txt("12"), blank()),
      listOf("16"),
      choices = listOf("13", "16", "20"),
      isShapeOrLetterChoice = true,
      hint = "Find how much is added each step from 4 to 8 to 12, then add that to 12.",
    )
    list += MathProblem.PatternProblem(
      48,
      listOf(txt("A"), txt("B"), txt("C"), txt("A"), txt("B"), blank()),
      listOf("C"),
      choices = listOf("D", "A", "C"),
      isShapeOrLetterChoice = true,
      hint = "Look at the repeating 3-letter group from the start.",
    )
    list += MathProblem.PatternProblem(
      49,
      listOf(txt("15"), txt("20"), txt("25"), blank()),
      listOf("30"),
      choices = listOf("30", "35", "20"),
      isShapeOrLetterChoice = true,
      hint = "Find how much is added each step from 15 to 20 to 25, then add that to 25.",
    )
    list += MathProblem.PatternProblem(
      50,
      listOf(
        PatternToken(PatternTokenKind.FILLED_HEART),
        PatternToken(PatternTokenKind.OUTLINE_STAR),
        PatternToken(PatternTokenKind.FILLED_HEART),
        PatternToken(PatternTokenKind.OUTLINE_STAR),
        blank(),
      ),
      listOf("FILLED_HEART"),
      choices = listOf("OUTLINE_STAR", "FILLED_HEART", "OUTLINE_HEART"),
      isShapeOrLetterChoice = true,
      hint = "Pattern alternates Filled Heart, Outline Star.",
    )
    // 51-55 missing term in box
    list += MathProblem.PatternProblem(
      51,
      listOf(
        PatternToken(PatternTokenKind.FLOWER),
        PatternToken(PatternTokenKind.LEAF),
        PatternToken(PatternTokenKind.FLOWER),
        PatternToken(PatternTokenKind.LEAF),
        blank(),
        PatternToken(PatternTokenKind.LEAF),
        PatternToken(PatternTokenKind.FLOWER),
      ),
      listOf("FLOWER"),
      choices = listOf("FLOWER", "LEAF", "CIRCLE"),
      isShapeOrLetterChoice = true,
      hint = "Alternates Flower, Leaf, Flower, Leaf...",
    )
    addNumPattern(52, listOf(txt("12"), blank(), txt("24"), txt("30"), txt("36")), listOf("18"), "Add 6 each step.")
    list += MathProblem.PatternProblem(
      53,
      listOf(txt("A"), blank(), txt("C"), txt("A"), txt("B"), txt("C")),
      listOf("B"),
      choices = listOf("B", "A", "C", "D"),
      isShapeOrLetterChoice = true,
      hint = "Repeating block is A, B, C.",
    )
    list += MathProblem.PatternProblem(
      54,
      listOf(
        PatternToken(PatternTokenKind.OUTLINE_TRIANGLE),
        PatternToken(PatternTokenKind.OUTLINE_CIRCLE),
        blank(),
        PatternToken(PatternTokenKind.OUTLINE_CIRCLE),
        PatternToken(PatternTokenKind.OUTLINE_TRIANGLE),
        PatternToken(PatternTokenKind.OUTLINE_CIRCLE),
      ),
      listOf("TRIANGLE"),
      choices = listOf("TRIANGLE", "CIRCLE", "SQUARE"),
      isShapeOrLetterChoice = true,
      hint = "Alternates Triangle, Circle.",
    )
    addNumPattern(55, listOf(txt("50"), txt("45"), txt("40"), blank(), txt("30")), listOf("35"), "Subtract 5 each step.")
    return list
  }
}
