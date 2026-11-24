package com.example.data.g2

import com.example.model.*

object G2CountAndWordsData {

  private fun wordForNumber(n: Int): String {
    val ones = listOf("", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine",
      "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen")
    val tens = listOf("", "", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety")
    return when {
      n == 0 -> "zero"
      n < 20 -> ones[n]
      n == 100 -> "one hundred"
      n % 10 == 0 -> tens[n / 10]
      else -> "${tens[n / 10]}-${ones[n % 10]}"
    }
  }

  fun getWorksheet1(): List<MathProblem> {
    val enterItems = listOf(
      CountObjectKind.HEART to 5,
      CountObjectKind.MOON to 3,
      CountObjectKind.STAR to 2,
      CountObjectKind.SMILEY to 6,
      CountObjectKind.BABY to 4,
      CountObjectKind.PARROT to 8,
      CountObjectKind.LEAF to 10,
      CountObjectKind.FLOWER to 7,
      CountObjectKind.TREE to 1,
      CountObjectKind.CLOUD to 9,
    ).mapIndexed { idx, (kind, cnt) ->
      CountObjectsProblem(
        itemNumber = idx + 1,
        mode = CountObjectsMode.ENTER_NUMBER,
        objectKind = kind,
        count = cnt,
        prompt = "Count the ${kind.plural} and write the number:",
        hint = "Tap or point to each ${kind.singular} one by one while counting aloud from 1.",
      )
    }

    val wordSentenceItems = listOf(
      Triple(CountObjectKind.DOG, 18, "eighteen"),
      Triple(CountObjectKind.BANANA, 14, "fourteen"),
      Triple(CountObjectKind.TRUCK, 15, "fifteen"),
      Triple(CountObjectKind.CHICKEN, 11, "eleven"),
      Triple(CountObjectKind.BEACHBALL, 18, "eighteen"),
      Triple(CountObjectKind.CARABAO, 12, "twelve"),
    ).mapIndexed { idx, (kind, cnt, word) ->
      CountObjectsProblem(
        itemNumber = 11 + idx,
        mode = CountObjectsMode.WORD_SENTENCE,
        objectKind = kind,
        count = cnt,
        prompt = "How many ${kind.plural} are there? Write the number in words:",
        expectedWords = word,
        hint = "Count the top row first, then continue counting the bottom row, and write the word (e.g., eighteen).",
      )
    }
    return enterItems + wordSentenceItems
  }

  fun getWorksheet2(): List<MathProblem> {
    val drawItems = listOf(
      CountObjectKind.BALLOON to 5,
      CountObjectKind.FLOWER to 4,
      CountObjectKind.CUP to 3,
      CountObjectKind.PEN to 7,
      CountObjectKind.WATCH to 6,
      CountObjectKind.CHILD to 2,
      CountObjectKind.MONKEY to 1,
      CountObjectKind.CANDY to 9,
      CountObjectKind.BUTTERFLY to 8,
      CountObjectKind.TELEPHONE to 4,
      CountObjectKind.ERASER to 9,
      CountObjectKind.CAKE to 5,
    ).mapIndexed { idx, (kind, cnt) ->
      StampObjectsProblem(
        itemNumber = idx + 1,
        prompt = "Draw the objects described: $cnt ${if (cnt == 1) kind.singular else kind.plural}",
        objectKind = kind,
        targetCount = cnt,
        hint = "Tap '+ Draw' once for each object and count 1, 2, 3... up to $cnt!",
      )
    }

    val story1 = StampObjectsProblem(
      itemNumber = 13,
      prompt = "Story #1: Draw the objects described in the story:",
      objectKind = CountObjectKind.BUTTERFLY,
      targetCount = 6,
      storyText = "On a bright Sunday morning, six butterflies were flying around ten flowers in a garden. Two dogs barked at them and three cats tried to chase them. There was one little girl who asked her pets to leave the butterflies alone. She gave them five cookies to eat.",
      storyItems = listOf(
        StoryObjectSpec(6, "butterflies", CountObjectKind.BUTTERFLY),
        StoryObjectSpec(10, "flowers", CountObjectKind.FLOWER),
        StoryObjectSpec(2, "dogs", CountObjectKind.DOG),
        StoryObjectSpec(3, "cats", CountObjectKind.CAT),
        StoryObjectSpec(1, "little girl", CountObjectKind.GIRL),
        StoryObjectSpec(5, "cookies", CountObjectKind.COOKIE),
      ),
      hint = "Read each sentence carefully and look for number words (six, ten, two, three, one, five).",
    )

    val story2 = StampObjectsProblem(
      itemNumber = 14,
      prompt = "Story #2: Draw the objects described in the story:",
      objectKind = CountObjectKind.FAN,
      targetCount = 7,
      storyText = "Andres went to the market to sell seven paper fans and four canes that he made. Three women looked at his items and decided to buy them all. Andres used his earnings to buy two books at the store. He also bought five mangoes for his brothers and sisters.",
      storyItems = listOf(
        StoryObjectSpec(7, "paper fans", CountObjectKind.FAN),
        StoryObjectSpec(4, "canes", CountObjectKind.CANE),
        StoryObjectSpec(3, "women", CountObjectKind.WOMAN),
        StoryObjectSpec(2, "books", CountObjectKind.BOOK),
        StoryObjectSpec(5, "mangoes", CountObjectKind.MANGO),
      ),
      hint = "Find the number words in Story #2: seven, four, three, two, five.",
    )
    return drawItems + listOf(story1, story2)
  }

  fun getWorksheet3(): List<MathProblem> {
    val duckPool = listOf(2, 13, 3, 18, 11, 1, 19, 4, 9, 5)
    val ducks = duckPool.mapIndexed { idx, target ->
      val opts = listOf(target, duckPool[(idx + 2) % duckPool.size], duckPool[(idx + 5) % duckPool.size], duckPool[(idx + 7) % duckPool.size])
        .distinct().take(4).sorted()
      CountObjectsProblem(
        itemNumber = idx + 1,
        mode = CountObjectsMode.MATCH_GROUPS,
        objectKind = CountObjectKind.DUCK,
        count = target,
        prompt = "Match the number to the correct group of ducks:",
        wordPrompt = "$target (${wordForNumber(target)})",
        candidates = opts.map { CountCandidateGroup(it, CountObjectKind.DUCK, "ducks") },
        hint = "Count every duck inside each set until you reach $target.",
      )
    }

    val ballPool = listOf(8, 14, 17, 6, 15, 20, 10, 16, 7, 12)
    val balls = ballPool.mapIndexed { idx, target ->
      val opts = listOf(target, ballPool[(idx + 3) % ballPool.size], ballPool[(idx + 6) % ballPool.size], ballPool[(idx + 8) % ballPool.size])
        .distinct().take(4).sorted()
      CountObjectsProblem(
        itemNumber = 11 + idx,
        mode = CountObjectsMode.MATCH_GROUPS,
        objectKind = CountObjectKind.BASKETBALL,
        count = target,
        prompt = "Match the number to the correct group of basketballs:",
        wordPrompt = "$target (${wordForNumber(target)})",
        candidates = opts.map { CountCandidateGroup(it, CountObjectKind.BASKETBALL, "basketballs") },
        hint = "Count carefully by rows to match $target basketballs.",
      )
    }

    val objPairs = listOf(
      4 to CountObjectKind.RIBBON,
      7 to CountObjectKind.PENCIL,
      15 to CountObjectKind.CRAYON,
      2 to CountObjectKind.FLOWER,
      1 to CountObjectKind.MONKEY,
      16 to CountObjectKind.CAMERA,
      19 to CountObjectKind.TOMATO,
      11 to CountObjectKind.FAN,
      9 to CountObjectKind.LEAF,
      10 to CountObjectKind.BOOK,
    )
    val matchWords = objPairs.mapIndexed { idx, (cnt, kind) ->
      val d1 = objPairs[(idx + 3) % objPairs.size]
      val d2 = objPairs[(idx + 6) % objPairs.size]
      val d3 = objPairs[(idx + 8) % objPairs.size]
      val cands = listOf(cnt to kind, d1, d2, d3).distinctBy { it.second }.sortedBy { it.first }
      CountObjectsProblem(
        itemNumber = 21 + idx,
        mode = CountObjectsMode.MATCH_WORD_TO_OBJECT,
        objectKind = kind,
        count = cnt,
        prompt = "Match the number word to the objects with that exact count:",
        wordPrompt = wordForNumber(cnt),
        candidates = cands.map { (c, k) -> CountCandidateGroup(c, k, k.plural) },
        hint = "'${wordForNumber(cnt)}' is the number $cnt. Count each group of objects!",
      )
    }
    return ducks + balls + matchWords
  }

  fun getWorksheet4(): List<MathProblem> {
    val camping = listOf(
      CountObjectKind.TREE to 3,
      CountObjectKind.BOY_SCOUT to 6,
      CountObjectKind.STAR to 6,
      CountObjectKind.TENT to 1,
      CountObjectKind.SNAKE to 2,
      CountObjectKind.FLASHLIGHT to 5,
      CountObjectKind.MATCHBOX to 6,
      CountObjectKind.JACKET to 3,
      CountObjectKind.BAT to 10,
      CountObjectKind.FLOWER to 4,
      CountObjectKind.WATER_JUG to 5,
      CountObjectKind.GRASSHOPPER to 7,
    ).mapIndexed { idx, (kind, cnt) ->
      CountObjectsProblem(
        itemNumber = idx + 1,
        mode = CountObjectsMode.WORD_SENTENCE,
        objectKind = kind,
        count = cnt,
        prompt = "Join the camping! Count the ${if (cnt == 1) kind.singular else kind.plural} and write the answer in words:",
        expectedWords = wordForNumber(cnt),
        hint = "Count the ${kind.plural} shown, then write the number word (like 'three' or 'ten').",
      )
    }

    val storyContext = "Story — One Happy Morning: Aling Teresa bought eggs and bottles of milk, put them in baskets, met girls singing happily, saw cats (one wearing a hat), and cooked plates of pancakes!"
    val storyItems = listOf(
      Triple(CountObjectKind.EGG, 4, "eggs"),
      Triple(CountObjectKind.MILK_BOTTLE, 3, "bottles of milk"),
      Triple(CountObjectKind.BASKET, 2, "baskets"),
      Triple(CountObjectKind.GIRL, 3, "girls singing happily"),
      Triple(CountObjectKind.CAT, 5, "total cats"),
      Triple(CountObjectKind.CAT, 1, "cat wearing a hat"),
      Triple(CountObjectKind.PANCAKE, 4, "plates of pancakes"),
    ).mapIndexed { idx, (kind, cnt, desc) ->
      CountObjectsProblem(
        itemNumber = 13 + idx,
        mode = CountObjectsMode.WORD_SENTENCE,
        objectKind = kind,
        count = cnt,
        prompt = "Complete the story in words — How many $desc?",
        expectedWords = wordForNumber(cnt),
        storyContext = storyContext,
        hint = "Count the $desc in the picture and spell the number word.",
      )
    }
    return camping + storyItems
  }

  fun getWorksheet5(): List<MathProblem> = listOf(
    35, 32, 23, 12, 7, 18, 26, 98, 76, 45, 32, 25, 47, 56, 76, 89, 93, 47, 99, 38,
  ).mapIndexed { idx, num ->
    MathProblem.NumberWordsProblem(
      itemNumber = idx + 1,
      number = num,
      words = wordForNumber(num),
      toWords = true,
      hint = "Write the tens word first, then a hyphen and the ones word (e.g., 36 -> thirty-six).",
    )
  }

  fun getWorksheet6(): List<MathProblem> = listOf(
    54, 36, 28, 84, 43, 25, 47, 89, 77, 68, 72, 39, 26, 64, 44, 93, 87, 48, 25, 33,
  ).mapIndexed { idx, num ->
    MathProblem.NumberWordsProblem(
      itemNumber = idx + 1,
      number = num,
      words = wordForNumber(num),
      toWords = false,
      hint = "Convert the tens word to the first digit and the ones word to the second digit.",
    )
  }
}
