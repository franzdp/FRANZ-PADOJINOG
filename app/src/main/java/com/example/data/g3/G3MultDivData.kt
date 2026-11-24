package com.example.data.g3

import com.example.model.*

object G3MultDivData {

  fun getWorksheet14(): List<MathProblem> {
    val phraseItems = listOf(
      Triple("4 fives", 4, 5),
      Triple("3 fours", 3, 4),
      Triple("2 eights", 2, 8),
      Triple("6 fives", 6, 5),
      Triple("4 twos", 4, 2),
      Triple("5 nines", 5, 9),
      Triple("10 ones", 10, 1),
      Triple("1 seven", 1, 7),
      Triple("7 fours", 7, 4),
      Triple("3 fives", 3, 5),
    ).mapIndexed { idx, (expr, g, each) ->
      MultDivSentenceProblem(
        itemNumber = idx + 1,
        mode = MultDivExerciseMode.PHRASE_OR_REPEAT_TO_MULT,
        prompt = "Write a multiplication sentence for:",
        visualExpression = expr,
        expectedFirst = g,
        expectedSecond = each,
        expectedResult = g * each,
        hint = "'$expr' means $g groups of $each -> $g × $each.",
      )
    }

    val repeatItems = listOf(
      Triple("4 + 4 + 4 + 4", 4, 4),
      Triple("7 + 7 + 7 + 7 + 7", 5, 7),
      Triple("1 + 1 + 1", 3, 1),
      Triple("9 + 9 + 9 + 9 + 9", 5, 9),
      Triple("5 + 5 + 5 + 5", 4, 5),
      Triple("8 + 8 + 8 + 8", 4, 8),
      Triple("2 + 2 + 2 + 2 + 2 + 2 + 2", 7, 2),
      Triple("4 + 4 + 4 + 4 + 4 + 4 + 4 + 4", 8, 4),
      Triple("3 + 3 + 3 + 3 + 3 + 3 + 3 + 3 + 3 + 3", 10, 3),
      Triple("6 + 6 + 6 + 6", 4, 6),
    ).mapIndexed { idx, (expr, count, addend) ->
      MultDivSentenceProblem(
        itemNumber = 11 + idx,
        mode = MultDivExerciseMode.PHRASE_OR_REPEAT_TO_MULT,
        prompt = "Write a multiplication sentence for the repeated addition:",
        visualExpression = expr,
        expectedFirst = count,
        expectedSecond = addend,
        expectedResult = count * addend,
        hint = "Count how many times $addend is added ($count times) -> $count × $addend.",
      )
    }

    val readLineItems = listOf(
      Triple(21, 3, 4),
      Triple(22, 2, 6),
      Triple(23, 5, 3),
      Triple(24, 5, 2),
      Triple(25, 7, 1),
    ).map { (num, hops, hopSize) ->
      MultDivSentenceProblem(
        itemNumber = num,
        mode = MultDivExerciseMode.NUMBER_LINE_READ,
        prompt = "Fill in the multiplication sentence shown by the number line:",
        visualExpression = "",
        expectedFirst = hops,
        expectedSecond = hopSize,
        expectedResult = hops * hopSize,
        numberLineMax = 15,
        hint = "Count how many jumps are drawn ($hops), multiply by the size of each jump ($hopSize).",
      )
    }

    val buildLineItems = listOf(
      Triple(26, 9, 2),
      Triple(27, 2, 8),
      Triple(28, 6, 3),
      Triple(29, 4, 5),
      Triple(30, 1, 10),
    ).map { (num, hops, hopSize) ->
      MultDivSentenceProblem(
        itemNumber = num,
        mode = MultDivExerciseMode.NUMBER_LINE_BUILD,
        prompt = "Draw the jumps on the number line and find the product:",
        visualExpression = "$hops × $hopSize = ?",
        expectedFirst = hops,
        expectedSecond = hopSize,
        expectedResult = hops * hopSize,
        numberLineMax = 20,
        hint = "Tap '+1 Jump of $hopSize' $hops times, then enter where you land on the number line.",
      )
    }

    return phraseItems + repeatItems + readLineItems + buildLineItems
  }

  fun getWorksheet15(): List<MathProblem> {
    val part1 = listOf(
      Triple("7 × (4 × 9) = (7 × 4) × 9", "APM", "Grouping changed with parentheses — Associative Property (APM)."),
      Triple("2 × 3 = 3 × 2", "CPM", "Order of factors switched — Commutative Property (CPM)."),
      Triple("453 × 1 = 453", "IPM", "Any number multiplied by 1 equals itself — Identity Property (IPM)."),
      Triple("4 × (30 + 5) = (4 × 30) + (4 × 5)", "DPM", "4 is distributed across (30 + 5) — Distributive Property (DPM)."),
      Triple("19 × 1 = 19", "IPM", "Multiplied by 1 gives the same number — Identity Property (IPM)."),
      Triple("8 × 17 = 17 × 8", "CPM", "Order switched — Commutative Property (CPM)."),
      Triple("89 × 0 = 0", "ZPM", "Any number multiplied by 0 equals 0 — Zero Property (ZPM)."),
      Triple("(4 × 3) × 2 = 4 × (3 × 2)", "APM", "Grouping of factors moved — Associative Property (APM)."),
      Triple("(2 + 8) × 9 = (9 × 2) + (9 × 8)", "DPM", "9 is distributed to 2 and 8 — Distributive Property (DPM)."),
      Triple("97 × 0 = 0", "ZPM", "Multiplied by 0 equals 0 — Zero Property (ZPM)."),
    ).mapIndexed { idx, (expr, code, hint) ->
      MultDivSentenceProblem(
        itemNumber = idx + 1,
        mode = MultDivExerciseMode.MULT_PROPERTY_ONLY,
        prompt = "Select the property illustrated:",
        visualExpression = expr,
        expectedFirst = 0,
        expectedSecond = 0,
        expectedResult = 0,
        expectedPropertyCode = code,
        hint = hint,
      )
    }

    val leftPropItems = listOf(
      Triple("179 × 1 = ___", "IPM", "Multiplied by 1 keeps the number unchanged (IPM)."),
      Triple("28 × 15 = 15 × ___", "CPM", "Order of the two factors flipped (CPM)."),
      Triple("175 × ___ = 0", "ZPM", "Product is 0 -> Zero Property (ZPM)."),
      Triple("10 × (3 + 9) = (10 × 3) + (___ × 9)", "DPM", "10 distributes over 3 and 9 (DPM)."),
      Triple("4 × (6 × 2) = (4 × ___) × 2", "APM", "Parentheses regrouped (APM)."),
      Triple("255 × ___ = 255", "IPM", "Product stays 255 -> Identity Property (IPM)."),
      Triple("7 × (8 + 2) = (___ × 8) + (7 × 2)", "DPM", "7 distributes over 8 and 2 (DPM)."),
    ).mapIndexed { idx, (expr, code, hint) ->
      MultDivSentenceProblem(11 + idx, MultDivExerciseMode.MULT_PROPERTY_ONLY, "Identify the property illustrated:", expr, 0, 0, 0, code, hint = hint)
    }

    val leftValItems = listOf(
      Triple("179 × 1 = ___", 179, "By IPM, 179 × 1 = 179."),
      Triple("28 × 15 = 15 × ___", 28, "By CPM, swap the two factors: 28 goes in the blank."),
      Triple("175 × ___ = 0", 0, "By ZPM, any number times 0 gives 0."),
      Triple("10 × (3 + 9) = (10 × 3) + (___ × 9)", 10, "By DPM, 10 multiplies BOTH 3 and 9."),
      Triple("4 × (6 × 2) = (4 × ___) × 2", 6, "By APM, the three factors 4, 6, 2 stay the same; only grouping moves."),
      Triple("255 × ___ = 255", 1, "By IPM, what number times 255 stays 255?"),
      Triple("7 × (8 + 2) = (___ × 8) + (7 × 2)", 7, "By DPM, 7 multiplies BOTH 8 and 2."),
    ).mapIndexed { idx, (expr, missing, hint) ->
      MultDivSentenceProblem(18 + idx, MultDivExerciseMode.MULT_PROPERTY_MISSING_VALUE, "Write the missing number:", expr, 0, 0, missing, hint = hint)
    }

    val rightPropItems = listOf(
      Triple("67 × 0 = ___", "ZPM", "Multiplying by 0 -> Zero Property (ZPM)."),
      Triple("3 × 11 = 11 × ___", "CPM", "Order of factors swapped -> Commutative Property (CPM)."),
      Triple("___ × 1 = 95", "IPM", "Multiplying by 1 keeps 95 -> Identity Property (IPM)."),
      Triple("9 × (2 × 4) = (9 × 2) × ___", "APM", "Grouping moved -> Associative Property (APM)."),
      Triple("(7 × 3) × 5 = ___ × (3 × 5)", "APM", "Parentheses regrouped -> Associative Property (APM)."),
      Triple("2 × (3 + 4) = (2 × 3) + (2 × ___)", "DPM", "2 distributes over 3 and 4 -> Distributive Property (DPM)."),
      Triple("59 × ___ = 0", "ZPM", "Product is 0 -> Zero Property (ZPM)."),
    ).mapIndexed { idx, (expr, code, hint) ->
      MultDivSentenceProblem(25 + idx, MultDivExerciseMode.MULT_PROPERTY_ONLY, "Identify the property illustrated:", expr, 0, 0, 0, code, hint = hint)
    }

    val rightValItems = listOf(
      Triple("67 × 0 = ___", 0, "Any number × 0 = 0."),
      Triple("3 × 11 = 11 × ___", 3, "By CPM, 3 × 11 equals 11 × 3."),
      Triple("___ × 1 = 95", 95, "By IPM, 95 × 1 = 95."),
      Triple("9 × (2 × 4) = (9 × 2) × ___", 4, "By APM, the missing third factor is 4."),
      Triple("(7 × 3) × 5 = ___ × (3 × 5)", 7, "By APM, the missing first factor is 7."),
      Triple("2 × (3 + 4) = (2 × 3) + (2 × ___)", 4, "By DPM, 2 multiplies 3 and 2 multiplies 4."),
      Triple("59 × ___ = 0", 0, "By ZPM, 59 × 0 = 0."),
    ).mapIndexed { idx, (expr, missing, hint) ->
      MultDivSentenceProblem(32 + idx, MultDivExerciseMode.MULT_PROPERTY_MISSING_VALUE, "Write the missing number:", expr, 0, 0, missing, hint = hint)
    }

    return part1 + leftPropItems + leftValItems + rightPropItems + rightValItems
  }

  fun getWorksheet16(): List<MathProblem> = listOf(
    5 to 5, 2 to 5, 1 to 3, 4 to 3, 7 to 2,
    6 to 2, 8 to 10, 3 to 10, 9 to 5, 10 to 5,
    5 to 4, 2 to 4, 1 to 4, 4 to 4, 7 to 4,
    6 to 4, 8 to 4, 3 to 4, 9 to 4, 10 to 4,
    4 to 2, 4 to 10, 4 to 8, 4 to 3, 4 to 5,
  ).mapIndexed { idx, (a, b) ->
    MathProblem.ArithmeticProblem(
      itemNumber = idx + 1,
      operands = listOf(a, b),
      op = ArithmeticOp.MULTIPLY,
      answer = a * b,
      showVerticalColumns = false,
      hint = "Skip count by ${b}s $a times (or ${a}s $b times).",
    )
  }

  fun getWorksheet17(): List<MathProblem> {
    val pictureItems = listOf(
      Triple(CountObjectKind.UMBRELLA, 6 to 2, "There are ___ groups of 2 in 6. Write the division sentence:"),
      Triple(CountObjectKind.BOOK, 15 to 3, "There are ___ groups of 3 in 15. Write the division sentence:"),
      Triple(CountObjectKind.PEAR, 30 to 5, "There are ___ groups of 5 in 30. Write the division sentence:"),
      Triple(CountObjectKind.BASKETBALL, 60 to 10, "There are ___ groups of 10 in 60. Write the division sentence:"),
      Triple(CountObjectKind.TSHIRT, 16 to 4, "Box into sets of 4! There are ___ groups of 4 in 16. Write the division sentence:"),
      Triple(CountObjectKind.DIAMOND, 36 to 4, "Box into sets of 4! There are ___ groups of 4 in 36. Write the division sentence:"),
    ).mapIndexed { idx, (kind, pair, prompt) ->
      val (total, groupSize) = pair
      MultDivSentenceProblem(
        itemNumber = idx + 1,
        mode = MultDivExerciseMode.GROUPING_PICTURE_TO_DIV,
        prompt = prompt,
        visualExpression = "___ ÷ $groupSize = ___",
        expectedFirst = total,
        expectedSecond = groupSize,
        expectedResult = total / groupSize,
        pictureKind = kind,
        hint = "Total is $total, divisor (group size) is $groupSize — count how many groups are formed!",
      )
    }

    val repeatSubs = listOf(
      Triple("10 - 2 - 2 - 2 - 2 - 2 = 0", 10, 2),
      Triple("15 - 5 - 5 - 5 = 0", 15, 5),
      Triple("40 - 10 - 10 - 10 - 10 = 0", 40, 10),
      Triple("18 - 3 - 3 - 3 - 3 - 3 - 3 = 0", 18, 3),
      Triple("16 - 4 - 4 - 4 - 4 = 0", 16, 4),
      Triple("20 - 10 - 10 = 0", 20, 10),
      Triple("14 - 2 - 2 - 2 - 2 - 2 - 2 - 2 = 0", 14, 2),
      Triple("30 - 5 - 5 - 5 - 5 - 5 - 5 = 0", 30, 5),
      Triple("15 - 3 - 3 - 3 - 3 - 3 = 0", 15, 3),
      Triple("24 - 4 - 4 - 4 - 4 - 4 - 4 = 0", 24, 4),
    ).mapIndexed { idx, (expr, total, sub) ->
      val q = total / sub
      MultDivSentenceProblem(
        itemNumber = 7 + idx,
        mode = MultDivExerciseMode.REPEAT_SUB_TO_DIV,
        prompt = "Write the division sentence for this repeated subtraction:",
        visualExpression = expr,
        expectedFirst = total,
        expectedSecond = sub,
        expectedResult = q,
        hint = "Start with $total, divide by $sub (the subtracted number), and count the $q subtractions!",
      )
    }

    return pictureItems + repeatSubs
  }

  fun getWorksheet18(): List<MathProblem> {
    val factDivs = listOf(
      Triple("5 × 7 = 35 (Sentence 1: ÷ 5)", 35, 5),
      Triple("5 × 7 = 35 (Sentence 2: ÷ 7)", 35, 7),
      Triple("8 × 2 = 16 (Sentence 1: ÷ 8)", 16, 8),
      Triple("8 × 2 = 16 (Sentence 2: ÷ 2)", 16, 2),
      Triple("4 × 5 = 20 (Sentence 1: ÷ 4)", 20, 4),
      Triple("4 × 5 = 20 (Sentence 2: ÷ 5)", 20, 5),
      Triple("10 × 5 = 50 (Sentence 1: ÷ 10)", 50, 10),
      Triple("10 × 5 = 50 (Sentence 2: ÷ 5)", 50, 5),
      Triple("4 × 6 = 24 (Sentence 1: ÷ 4)", 24, 4),
      Triple("4 × 6 = 24 (Sentence 2: ÷ 6)", 24, 6),
      Triple("3 × 9 = 27 (Sentence 1: ÷ 3)", 27, 3),
      Triple("3 × 9 = 27 (Sentence 2: ÷ 9)", 27, 9),
    ).mapIndexed { idx, (label, total, div) ->
      MultDivSentenceProblem(
        itemNumber = idx + 1,
        mode = MultDivExerciseMode.FACT_TO_DIV_SENTENCE,
        prompt = "Write the matching division sentence ($total ÷ $div):",
        visualExpression = label,
        expectedFirst = total,
        expectedSecond = div,
        expectedResult = total / div,
        hint = "Use the related multiplication fact: put $total first, divide by $div.",
      )
    }

    // 13-28: (visual, missingFactor, quotient)
    val inverseItems = listOf(
      Triple("5 × ___ = 20   so   20 ÷ 5 = ___", 4, 4),
      Triple("___ × 7 = 28   so   28 ÷ 4 = ___", 4, 7),
      Triple("2 × ___ = 12   so   12 ÷ 2 = ___", 6, 6),
      Triple("9 × ___ = 36   so   36 ÷ 4 = ___", 4, 9),
      Triple("___ × 3 = 24   so   24 ÷ 3 = ___", 8, 8),
      Triple("10 × ___ = 30   so   30 ÷ 10 = ___", 3, 3),
      Triple("___ × 4 = 12   so   12 ÷ 4 = ___", 3, 3),
      Triple("8 × ___ = 80   so   80 ÷ 10 = ___", 10, 8),
      Triple("4 × ___ = 20   so   20 ÷ 4 = ___", 5, 5),
      Triple("___ × 7 = 21   so   21 ÷ 3 = ___", 3, 7),
      Triple("1 × ___ = 10   so   10 ÷ 1 = ___", 10, 10),
      Triple("8 × ___ = 32   so   32 ÷ 4 = ___", 4, 8),
      Triple("___ × 5 = 30   so   30 ÷ 5 = ___", 6, 6),
      Triple("3 × ___ = 30   so   30 ÷ 3 = ___", 10, 10),
      Triple("___ × 2 = 18   so   18 ÷ 2 = ___", 9, 9),
      Triple("6 × ___ = 60   so   60 ÷ 10 = ___", 10, 6),
    ).mapIndexed { idx, (expr, missing, q) ->
      MultDivSentenceProblem(
        itemNumber = 13 + idx,
        mode = MultDivExerciseMode.INVERSE_MISSING_FACTOR_AND_QUOTIENT,
        prompt = "Fill in the missing factor and complete the division sentence:",
        visualExpression = expr,
        expectedFirst = missing,
        expectedSecond = 0,
        expectedResult = q,
        hint = "Solve the multiplication equation first to find the missing factor, then solve the division sentence!",
      )
    }

    val phraseItems = listOf(
      Triple("How many groups of 3 are there in 15?", 15, 3),
      Triple("How many sets of 2 are there in 10?", 10, 2),
      Triple("How many 4s are there in 8?", 8, 4),
      Triple("How many groups of 5 are there in 10?", 10, 5),
      Triple("How many sets of 4 are there in 36?", 36, 4),
      Triple("How many 10s are there in 40?", 40, 10),
      Triple("How many groups of 10 are there in 100?", 100, 10),
      Triple("How many sets of 3 are there in 24?", 24, 3),
      Triple("How many 4s are there in 20?", 20, 4),
      Triple("How many sets of 3 are there in 27?", 27, 3),
    ).mapIndexed { idx, (qText, total, div) ->
      MultDivSentenceProblem(
        itemNumber = 29 + idx,
        mode = MultDivExerciseMode.WORD_PHRASE_TO_DIV,
        prompt = "Write a division sentence for:",
        visualExpression = qText,
        expectedFirst = total,
        expectedSecond = div,
        expectedResult = total / div,
        hint = "Put the total ($total) first, divide by the group size ($div).",
      )
    }

    return factDivs + inverseItems + phraseItems
  }

  private fun divStory(item: Int, dividend: Int, divisor: Int, unit: String, text: String): MathProblem.DivisionProblem =
    MathProblem.DivisionProblem(
      itemNumber = item,
      dividend = dividend,
      divisor = divisor,
      quotient = dividend / divisor,
      promptText = text,
      unitLabel = unit,
      showBracket = false,
      hint = "Divide $dividend by $divisor ($divisor × ? = $dividend).",
    )

  fun getWorksheet19(): List<MathProblem> = listOf(
    divStory(1, 16, 4, "boxes", "Eddie has 16 toy cars. He keeps 4 toy cars in each box. How many boxes does he use?"),
    divStory(2, 24, 4, "rows", "Mang Nestor has 24 tomato seeds. He plants 4 seeds for every row of his garden. How many rows are planted with tomato seeds?"),
    divStory(3, 30, 5, "bags", "Elena harvested 30 rambutans. She placed 5 rambutans in each bag. How many bags did she use?"),
    divStory(4, 40, 10, "baskets", "There are 40 apples on the table. Marianne wants to put 10 apples in each basket. How many baskets does she need?"),
    divStory(5, 35, 5, "pupils", "There are 35 candies in a jar. Feliz distributes 5 to each of her pupils. How many pupils does she have?"),
    divStory(6, 20, 2, "vendors", "Aling Nelia made 20 sumans. Each of her vendors gets 2 sumans to sell. How many vendors does she have?"),
    divStory(7, 36, 4, "packs", "Marissa made 36 puto paos. She packs them in groups of 4. How many packs did she make?"),
    divStory(8, 16, 2, "packs", "Aling Neri makes 16 polvorons. She packs them into twos. How many packs will she make?"),
    divStory(9, 45, 5, "stacks", "There are 45 cups on the table. Willy stacks the cups by fives. How many stacks does he form?"),
    divStory(10, 27, 3, "batches", "Catherine made 27 lumpias. She fries them in batches of threes. How many times does she have to fry?"),
    divStory(11, 40, 4, "bump cars", "Forty children are to ride a bump car. Each bump car can carry 4 children. How many bump cars are needed?"),
    divStory(12, 18, 2, "trips", "Mang Edgar delivers sacks of sugar. He can carry 2 sacks at a time. If there are 18 sacks, how many times does he need to deliver them all?"),
    divStory(13, 27, 3, "baskets", "Aunt Helen's hen laid 27 eggs. She puts 3 eggs in each basket. How many baskets does she use?"),
    divStory(14, 15, 3, "tricycles", "Fifteen people are about to ride a tricycle. If a tricycle can carry 3 people, how many tricycles are needed to give them all a ride?"),
    divStory(15, 90, 10, "friends", "On his birthday, Nikolai wants to give away 90 marbles to his friends. If he will put 10 marbles in each loot bag, how many friends will receive a giveaway?"),
    divStory(16, 30, 3, "bundles", "Marlon had 30 keys. He bundled them in threes. How many bundles of keys does he have in all?"),
  )
}
