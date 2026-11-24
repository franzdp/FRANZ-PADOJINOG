package com.example.data

import com.example.model.ArithmeticOp
import com.example.model.MathProblem

object ArithmeticData {

  fun getWorksheet7(): List<MathProblem> {
    val sets = listOf(
      listOf(620, 156), listOf(380, 208), listOf(723, 250), listOf(324, 241), listOf(435, 224),
      listOf(158, 250), listOf(175, 256), listOf(574, 150), listOf(642, 161), listOf(276, 163),
      listOf(550, 105, 312), listOf(427, 310, 231), listOf(200, 420, 179), listOf(204, 360, 303), listOf(462, 227, 305),
      listOf(200, 219, 452), listOf(110, 720, 171), listOf(408, 350, 383), listOf(458, 320, 457), listOf(329, 690, 324),
      listOf(1173, 2122), listOf(8671, 1106), listOf(4384, 2313), listOf(5352, 3127), listOf(3103, 3166),
      listOf(3287, 3281), listOf(1127, 2437), listOf(3156, 3706), listOf(2451, 1646), listOf(2136, 1256),
      listOf(1605, 1331, 2042), listOf(4150, 2304, 1023), listOf(2324, 1123, 230), listOf(5102, 2342, 537), listOf(3561, 1523, 920),
      listOf(4176, 2508, 1891), listOf(3392, 1203, 975), listOf(1500, 780, 863), listOf(4150, 3052, 969), listOf(1242, 3180, 5325),
    )
    return sets.mapIndexed { idx, ops ->
      MathProblem.ArithmeticProblem(
        itemNumber = idx + 1,
        operands = ops,
        op = ArithmeticOp.ADD,
        answer = ops.sum(),
        showVerticalColumns = true,
        hint = "Add ones first (${ops.joinToString(" + ") { (it % 10).toString() }}), then tens, hundreds, and thousands!",
      )
    }
  }

  fun getWorksheet8(): List<MathProblem> {
    val list = mutableListOf<MathProblem>()
    val sentences = listOf(
      Triple(145, 153, "What is the sum of 145 and 153?"),
      Triple(232, 361, "Add 232 and 361. What is the answer?"),
      Triple(376, 211, "What is the sum of 376 and 211?"),
      Triple(135, 234, "What is the sum of 135 and 234?"),
      Triple(712, 234, "Add 712 and 234. What is the answer?"),
      Triple(840, 567, "Add 840 and 567. What is the answer?"),
      Triple(815, 991, "What is the sum of 815 and 991?"),
      Triple(525, 784, "What is the sum of 525 and 784?"),
      Triple(1857, 1891, "Add 1,857 and 1,891. What is the answer?"),
      Triple(3904, 795, "Add 3,904 and 795. What is the answer?"),
      Triple(275, 135, "What is the sum of 275 and 135?"),
      Triple(3847, 815, "Add 3,847 and 815. What is the answer?"),
      Triple(2047, 464, "What is the sum of 2,047 and 464?"),
      Triple(3627, 196, "What is the sum of 3,627 and 196?"),
      Triple(3756, 1236, "Add 3,756 and 1,236. What is the answer?"),
      Triple(3725, 967, "Add 3,725 and 967. What is the answer?"),
      Triple(1657, 3091, "What is the sum of 1,657 and 3,091?"),
      Triple(1967, 2375, "What is the sum of 1,967 and 2,375?"),
      Triple(1077, 1832, "Add 1,077 and 1,832. What is the answer?"),
      Triple(9015, 566, "Add 9,015 and 566. What is the answer?"),
    )
    sentences.forEachIndexed { idx, (a, b, prompt) ->
      list += MathProblem.ArithmeticProblem(
        itemNumber = idx + 1, operands = listOf(a, b), op = ArithmeticOp.ADD, answer = a + b,
        promptText = prompt, showVerticalColumns = true,
        hint = "Add $a + $b.",
      )
    }
    data class WordAdd(val ops: List<Int>, val prompt: String, val unit: String, val hint: String)
    val wordProblems = listOf(
      WordAdd(listOf(1521, 44), "Magellan arrived in our country in the year 1521. Miguel Lopez de Legazpi arrived 44 years later. In what year did Legazpi come to our country?", "year", "Add 1521 + 44."),
      WordAdd(listOf(1861, 35), "Jose Rizal was born in 1861 and was executed at the age of 35. What year was Rizal executed?", "year", "Add 1861 + 35."),
      WordAdd(listOf(1565, 333), "The Spaniards conquered our country in 1565 and colonized the Philippines for 333 years. When did we gain our freedom from the Spanish colonizers?", "year", "Add 1565 + 333."),
      WordAdd(listOf(1589, 50), "Ateneo de Manila was founded by the Jesuits in 1589. When did the school celebrate its 50th year?", "year", "Add 1589 + 50."),
      WordAdd(listOf(30, 27, 48), "If Princess Urduja ordered 30 durians, 27 mangoes, and 48 papayas, how many fruits did she order in all?", "fruits", "Add 30 + 27 + 48."),
      WordAdd(listOf(896, 195), "There are 896 Grade 6 students in Bagumbayan Elementary School. If 195 students transfer to the school, how many Grade 6 students will there be?", "students", "Add 896 + 195."),
      WordAdd(listOf(2345, 5129), "If our school spends ₱2,345.00 for the repainting of the walls and allots ₱5,129.00 more for other repairs, what is our school budget for other repairs?", "pesos (₱)", "Add 2,345 + 5,129."),
      WordAdd(listOf(1968, 50), "Jose Maria Sison founded the Communist Party of the Philippines (CCP) in 1968. When will the CCP reach its 50th year?", "year", "Add 1968 + 50."),
      WordAdd(listOf(1998, 3), "Joseph Estrada became the 13th President of our country in 1998 and was ousted 3 years later. What year was Joseph Estrada ousted?", "year", "Add 1998 + 3."),
      WordAdd(listOf(497, 795), "If Gen. Douglas MacArthur originally had 497 American Soldiers when he came back to the Philippines and 795 Filipino Soldiers joined him, how many soldiers did he have in battle?", "soldiers", "Add 497 + 795."),
      WordAdd(listOf(17, 20), "Gloria Arroyo buys a kilo of rice for ₱17.00. If the price of rice increased by ₱20.00 per kilo, how much is the price of rice now?", "pesos (₱)", "Add 17 + 20."),
      WordAdd(listOf(3281, 1970), "Cory Aquino bought 3,281 yellow shirts for her supporters while her daughter Kris bought 1,970 yellow shirts. How many yellow shirts do they have?", "yellow shirts", "Add 3,281 + 1,970."),
      WordAdd(listOf(1928, 29), "Fidel Ramos was born in 1928 and became a Battalion Commander when he was 29 years old. What year was he promoted to be a Battalion Commander?", "year", "Add 1928 + 29."),
      WordAdd(listOf(1982, 2192), "If there were 1,982 American Soldiers and 2,192 Filipino Soldiers who were in the Death March, how many soldiers were there in all?", "soldiers", "Add 1,982 + 2,192."),
      WordAdd(listOf(1006, 2767), "There are 1,006 males in the country who are over 65 years old and 2,767 females who are over 65 years old. How many persons are over 65 years old in the country?", "persons", "Add 1,006 + 2,767."),
    )
    wordProblems.forEachIndexed { idx, w ->
      list += MathProblem.ArithmeticProblem(
        itemNumber = 21 + idx, operands = w.ops, op = ArithmeticOp.ADD, answer = w.ops.sum(),
        promptText = w.prompt, unitLabel = w.unit, showVerticalColumns = false, hint = w.hint,
      )
    }
    return list
  }

  fun getWorksheet9(): List<MathProblem> {
    val pairs = listOf(
      Triple(900, 250, null), Triple(815, 404, null), Triple(984, 231, null), Triple(635, 420, null), Triple(558, 237, null),
      Triple(950, 150, null), Triple(500, 250, null), Triple(800, 375, null), Triple(700, 250, null), Triple(850, 250, null),
      Triple(450, 240, null), Triple(824, 413, null), Triple(774, 124, null), Triple(2100, 250, null), Triple(3500, 501, null),
      Triple(6500, 2500, null), Triple(4200, 3000, null), Triple(5800, 3200, null),
      Triple(612, 411, "612 less 411"), Triple(778, 512, "778 less 512"), Triple(950, 250, "950 less 250"),
      Triple(500, 100, "500 less 100"), Triple(800, 500, "800 less 500"), Triple(700, 450, "700 less 450"),
      Triple(850, 120, "850 less 120"), Triple(450, 270, "450 less 270"), Triple(800, 250, "800 less 250"),
      Triple(952, 321, "952 less 321"), Triple(958, 235, "958 less 235"), Triple(583, 21, "583 less 21"),
      Triple(1200, 157, "1,200 less 157"), Triple(1400, 245, "1,400 less 245"), Triple(1600, 412, "1,600 less 412"),
      Triple(1800, 467, "1,800 less 467"),
      Triple(841, 205, "205 subtracted from 841"), Triple(816, 141, "141 subtracted from 816"),
      Triple(764, 425, "425 subtracted from 764"), Triple(892, 426, "426 subtracted from 892"),
      Triple(982, 628, "628 subtracted from 982"), Triple(8800, 3400, "3,400 subtracted from 8,800"),
    )
    return pairs.mapIndexed { idx, (a, b, prompt) ->
      MathProblem.ArithmeticProblem(
        itemNumber = idx + 1, operands = listOf(a, b), op = ArithmeticOp.SUBTRACT, answer = a - b,
        promptText = prompt, showVerticalColumns = true,
        hint = "Subtract $b from $a ($a - $b). Borrow from the left place if needed.",
      )
    }
  }

  fun getWorksheet10(): List<MathProblem> {
    data class WordProb(val answer: Int, val prompt: String, val unit: String, val hint: String)
    val problems = listOf(
      WordProb(680, "Mona had 714 pesos. Her mother gave her 186 more. After spending 220 pesos on a claw machine, how much money does she have left?", "pesos", "714 + 186 = 900; then 900 - 220."),
      WordProb(224, "Ethan's high school played 544 baseball games this year. He attended 233 games and watched 87 games on television. How many baseball games did Ethan not watch?", "games", "544 - 233 - 87."),
      WordProb(359, "Judith has 757 books while Justin has 253 books. Justin received 145 more books. How many more books does Judith have than Justin?", "books", "Justin has 253 + 145 = 398; then 757 - 398."),
      WordProb(310, "There are three hundred thirty-nine colored pencils in the drawer. Fred put one hundred sixteen colored pencils into the drawer. Then, he took one hundred forty-five colored pencils from the drawer. How many colored pencils are now in the drawer?", "colored pencils", "339 + 116 - 145."),
      WordProb(571, "Larkin had 2,174 basketball cards. His uncle gave him 326 more basketball cards. Then, he gave 1,929 of them to his friend. How many basketball cards does he have left?", "cards", "2,174 + 326 = 2,500; then 2,500 - 1,929."),
      WordProb(1511, "Greg has ₱3,451 and his brother, Brent, has ₱5,387. Greg received ₱425 from his grandmother. How much more money does Brent have than Greg?", "pesos (₱)", "Greg has 3,451 + 425 = 3,876; then 5,387 - 3,876."),
      WordProb(6137, "There are 8,005 fairy tale books in the library. 1,245 new fairy tale books arrived. On Monday, 3,113 were taken out. How many fairy tale books are left?", "books", "8,005 + 1,245 - 3,113."),
      WordProb(1896, "There were 4,277 fiction books. The library received 523 more fiction books. Sadly, 2,904 were ruined in a flood. How many are left?", "books", "4,277 + 523 - 2,904."),
      WordProb(439, "There are 388 Bibles on the top shelf and 297 on the bottom shelf. If 246 Bibles were taken out, how many Bibles are left?", "Bibles", "388 + 297 - 246."),
      WordProb(282, "The coffee shop had 415 cups of coffee to sell. The staff prepared 85 more cups of coffee. The cashier sold 218 cups of coffee. How many cups of coffee are left?", "cups", "415 + 85 - 218."),
      WordProb(529, "Liam had 808 building blocks. His sister gave him 126 more. He lost 405 of them. How many building blocks does Liam have now?", "blocks", "808 + 126 - 405."),
      WordProb(899, "Ms. Garcia had 1,496 notebooks in her closet. She received 238 more notebooks. She gave 835 away. How many notebooks does Ms. Garcia have left?", "notebooks", "1,496 + 238 - 835."),
      WordProb(799, "The school had 1,376 computers. The school bought 245 new computers. Then, 822 computers were given away. How many computers does the school have now?", "computers", "1,376 + 245 - 822."),
      WordProb(1852, "Jamaica made 2,982 cookies for the party. She made 468 more cookies in the afternoon. Kimberly made 1,598 cookies. How many more cookies did Jamaica make than Kimberly?", "cookies", "Jamaica made 2,982 + 468 = 3,450; then 3,450 - 1,598."),
      WordProb(820, "There were 1,369 thermometers in the science lab. The school bought 214 more thermometers. The students used 763 thermometers. How many thermometers were not used?", "thermometers", "1,369 + 214 - 763."),
      WordProb(2999, "3,420 children were playing in the tree house. Then, 549 went out to play on the field. Afterward, 128 children went inside the tree house. How many children are playing in the tree house now?", "children", "3,420 - 549 + 128."),
      WordProb(3304, "A rooster found 5,256 seeds on the ground. It ate 2,139 seeds then walked away. Later, it found 187 more seeds. How many seeds are left on the ground?", "seeds", "5,256 - 2,139 + 187."),
      WordProb(409, "Liza has 521 children in her class this year. Last year she had 112 less children in her class. How many children were in Liza's class last year?", "children", "521 - 112."),
      WordProb(850, "I found 1,453 frogs by the pond. Then, 125 more frogs joined them. 728 of them hopped away. How many frogs are still on the pond?", "frogs", "1,453 + 125 - 728."),
      WordProb(2321, "Helen and I baked 3,846 cupcakes. We baked 512 more cupcakes in the afternoon. We gave away 2,037 cupcakes. How many cupcakes are left?", "cupcakes", "3,846 + 512 - 2,037."),
      WordProb(215, "Daniel gathered 650 eggs from two poultry houses. He gathered 435 eggs from one poultry house. How many eggs did he gather from the other?", "eggs", "650 - 435."),
      WordProb(1314, "Belle collected 2,012 pieces of shell from the shore of Jomalig Island. Kate collected 698 less shells than Belle. How many shells did Kate collect?", "shells", "2,012 - 698."),
    )
    return problems.mapIndexed { idx, w ->
      MathProblem.ArithmeticProblem(
        itemNumber = idx + 1, operands = emptyList(), op = ArithmeticOp.ADD, answer = w.answer,
        promptText = w.prompt, unitLabel = w.unit, showVerticalColumns = false, hint = w.hint,
      )
    }
  }

  fun getWorksheet11(): List<MathProblem> {
    val mults = listOf(
      43 to 2, 36 to 4, 41 to 3, 73 to 5,
      46 to 3, 75 to 5, 67 to 4, 81 to 5,
      521 to 4, 326 to 3, 524 to 8, 328 to 2,
    )
    val list = mults.mapIndexed { idx, (a, b) ->
      MathProblem.ArithmeticProblem(
        itemNumber = idx + 1, operands = listOf(a, b), op = ArithmeticOp.MULTIPLY, answer = a * b,
        showVerticalColumns = true,
        hint = "Multiply each digit of $a by $b from right to left, and regroup when ≥ 10.",
      )
    }.toMutableList()
    list += MathProblem.ArithmeticProblem(
      13, listOf(32, 4), ArithmeticOp.MULTIPLY, 32 * 4,
      promptText = "A unique means of transportation we are known for is the tricycle. One tricycle can occupy 4 passengers. If there are 32 tricycles of the same size, how many passengers can be accommodated in all?",
      unitLabel = "passengers", showVerticalColumns = false, hint = "Multiply 32 × 4.",
    )
    list += MathProblem.ArithmeticProblem(
      14, listOf(125, 9), ArithmeticOp.MULTIPLY, 125 * 9,
      promptText = "Tricycle bound for Stop & Shop are popular because of the bumper car-like ride. If a driver collects 125 pesos from a full load in a single trip, how much does he receive in a day for 9 trips?",
      unitLabel = "pesos", showVerticalColumns = false, hint = "Multiply 125 × 9.",
    )
    return list
  }

  fun getWorksheet12(): List<MathProblem> {
    val mults = listOf(
      43 to 12, 36 to 21, 41 to 29, 73 to 31,
      46 to 23, 75 to 31, 67 to 45, 81 to 55,
      51 to 24, 36 to 34, 24 to 18, 98 to 42,
    )
    val list = mults.mapIndexed { idx, (a, b) ->
      val p1 = a * (b % 10)
      val p2 = a * (b / 10) * 10
      MathProblem.ArithmeticProblem(
        itemNumber = idx + 1, operands = listOf(a, b), op = ArithmeticOp.MULTIPLY, answer = a * b,
        showVerticalColumns = true,
        hint = "Partial products: $a × ${b % 10} = $p1, and $a × ${(b / 10) * 10} = $p2. Add $p1 + $p2!",
      )
    }.toMutableList()
    list += MathProblem.ArithmeticProblem(
      13, listOf(32, 18), ArithmeticOp.MULTIPLY, 32 * 18,
      promptText = "A unique means of transportation we are known for is the jeepney. One jeepney can occupy 18 passengers. If there are 32 jeepneys of the same size, how many passengers can be accommodated in all?",
      unitLabel = "passengers", showVerticalColumns = false, hint = "Multiply 32 × 18.",
    )
    list += MathProblem.ArithmeticProblem(
      14, listOf(95, 12), ArithmeticOp.MULTIPLY, 95 * 12,
      promptText = "Jeepneys bound for Stop & Shop are popular because of the rollercoaster-like ride. If a driver collects 95 pesos from a full load in a single trip, how much does he receive in a day for 12 trips?",
      unitLabel = "pesos", showVerticalColumns = false, hint = "Multiply 95 × 12.",
    )
    return list
  }

  fun getWorksheet13(): List<MathProblem> {
    val pairs = listOf(
      9 to 10, 45 to 10, 786 to 10, 1998 to 10, 7 to 10,
      67 to 10, 886 to 10, 6 to 10, 21 to 10, 597 to 10,
      5 to 10, 75 to 10, 145 to 10, 340 to 10, 7 to 10,
      6 to 10, 4 to 10, 9 to 10, 9 to 100, 65 to 100,
      5 to 100, 75 to 100, 145 to 100, 340 to 100, 7 to 100,
      6 to 100, 4 to 100, 9 to 100, 9 to 100, 65 to 100,
    )
    return pairs.mapIndexed { idx, (a, b) ->
      MathProblem.ArithmeticProblem(
        itemNumber = idx + 1, operands = listOf(a, b), op = ArithmeticOp.MULTIPLY, answer = a * b,
        showVerticalColumns = false,
        hint = if (b == 10) "When multiplying by 10, copy the number and affix 1 zero at the end." else "When multiplying by 100, copy the number and affix 2 zeros at the end.",
      )
    }
  }

  fun getWorksheet14(): List<MathProblem> {
    data class WordMult(val ans: Int, val prompt: String, val unit: String, val hint: String)
    val items = listOf(
      WordMult(56, "There are 8 baskets. Each basket has 7 oranges. How many oranges are there in all?", "oranges", "8 × 7."),
      WordMult(54, "A classroom has 6 rows of chairs. Each row has 9 chairs. How many chairs are there in all?", "chairs", "6 × 9."),
      WordMult(72, "A notebook costs ₱18. Mia buys 4 notebooks. How much does she pay?", "pesos (₱)", "18 × 4."),
      WordMult(84, "A garden has 7 rows of plants. Each row has 12 plants. How many plants are there in all?", "plants", "7 × 12."),
      WordMult(159, "Carlo sells 24 bottles of juice each day for 6 days. On the last day, he sold 15 more bottles. How many bottles did he sell in all?", "bottles", "24 × 6 = 144, then 144 + 15."),
      WordMult(148, "A library has 7 shelves with 18 books on each shelf. The librarian donated 22 more books. How many books are in the library now?", "books", "7 × 18 = 126, then 126 + 22."),
      WordMult(108, "Lina planted 9 rows of vegetables with 14 plants in each row. After a storm, 18 plants were damaged. How many plants were left?", "plants", "9 × 14 = 126, then 126 - 18."),
      WordMult(68, "A runner completes 12 laps each day for 5 days. He ran 8 extra laps on Saturday. How many laps did he run in all?", "laps", "12 × 5 = 60, then 60 + 8."),
      WordMult(72, "A baker made 9 trays of cupcakes. Each tray has 8 cupcakes. How many cupcakes did the baker make?", "cupcakes", "9 × 8."),
      WordMult(70, "There are 5 shelves. Each shelf has 14 books. How many books are there in all?", "books", "5 × 14."),
      WordMult(66, "A farmer packed 11 baskets with 6 mangoes in each basket. How many mangoes are there in all?", "mangoes", "11 × 6."),
      WordMult(120, "A toy store has 8 boxes. Each box contains 15 toy cars. How many toy cars are there in all?", "toy cars", "8 × 15."),
      WordMult(151, "A factory produces 28 bottles every hour for 6 hours. It found 17 defective bottles. How many good bottles were produced?", "good bottles", "28 × 6 = 168, then 168 - 17."),
      WordMult(210, "Anna bought 7 notebooks costing ₱25 each. She also bought a ruler worth ₱35. How much did she spend in all?", "pesos (₱)", "7 × 25 = 175, then 175 + 35."),
      WordMult(78, "A class prepared 8 tables with 9 chairs at each table. 6 extra chairs were brought in. How many chairs are there altogether?", "chairs", "8 × 9 = 72, then 72 + 6."),
      WordMult(185, "A school bought 9 boxes of crayons. Each box has 24 crayons. The teacher used 31 crayons for an art activity. How many crayons were left?", "crayons", "9 × 24 = 216, then 216 - 31."),
    )
    return items.mapIndexed { idx, w ->
      MathProblem.ArithmeticProblem(
        itemNumber = idx + 1, operands = emptyList(), op = ArithmeticOp.MULTIPLY, answer = w.ans,
        promptText = w.prompt, unitLabel = w.unit, showVerticalColumns = false, hint = w.hint,
      )
    }
  }
}
