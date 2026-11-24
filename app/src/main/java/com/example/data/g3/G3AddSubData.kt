package com.example.data.g3

import com.example.model.ArithmeticOp
import com.example.model.MathProblem

object G3AddSubData {

  fun getWorksheet9(): List<MathProblem> = listOf(
    // 1-14: without regrouping
    103 to 23, 110 to 21, 122 to 25, 124 to 35, 127 to 52,
    132 to 66, 136 to 52, 140 to 41, 141 to 42, 145 to 24,
    146 to 32, 152 to 21, 153 to 34, 155 to 42,
    // 15-24: with regrouping
    221 to 29, 234 to 39, 236 to 44, 241 to 49, 243 to 47,
    244 to 48, 245 to 35, 248 to 33, 259 to 23, 264 to 27,
  ).mapIndexed { idx, (a, b) ->
    val needsRegroup = (a % 10) + (b % 10) >= 10
    MathProblem.ArithmeticProblem(
      itemNumber = idx + 1,
      operands = listOf(a, b),
      op = ArithmeticOp.ADD,
      answer = a + b,
      showVerticalColumns = true,
      hint = if (needsRegroup) {
        "Add ones (${a % 10} + ${b % 10} = ${(a % 10) + (b % 10)}); write the ones digit and carry 1 to tens!"
      } else {
        "Add ones (${a % 10} + ${b % 10}), add tens (${(a / 10) % 10} + ${b / 10}), and bring down ${a / 100}."
      },
    )
  }

  fun getWorksheet10(): List<MathProblem> = listOf(
    // 1-14: without regrouping
    165 to 224, 502 to 344, 406 to 502, 682 to 214, 101 to 824,
    187 to 101, 824 to 163, 624 to 122, 183 to 400, 406 to 143,
    471 to 110, 245 to 204, 413 to 510, 846 to 112,
    // 15-24: with regrouping
    113 to 217, 116 to 219, 124 to 226, 125 to 228, 127 to 229,
    132 to 238, 138 to 244, 145 to 247, 147 to 248, 162 to 249,
  ).mapIndexed { idx, (a, b) ->
    MathProblem.ArithmeticProblem(
      itemNumber = idx + 1,
      operands = listOf(a, b),
      op = ArithmeticOp.ADD,
      answer = a + b,
      showVerticalColumns = true,
      hint = if (idx >= 14) {
        "Add ones first and carry 1 to the tens column when ones >= 10 (watch if tens >= 10 too!)."
      } else {
        "Add ones (${a % 10}+${b % 10}), then tens (${(a / 10) % 10}+${(b / 10) % 10}), then hundreds (${a / 100}+${b / 100})."
      },
    )
  }

  private fun addStory(item: Int, a: Int, b: Int, text: String, unit: String?, hint: String): MathProblem.ArithmeticProblem =
    MathProblem.ArithmeticProblem(
      itemNumber = item,
      operands = listOf(a, b),
      op = ArithmeticOp.ADD,
      answer = a + b,
      promptText = text,
      unitLabel = unit,
      showVerticalColumns = false,
      hint = hint,
    )

  fun getWorksheet11(): List<MathProblem> = listOf(
    addStory(1, 110, 25, "110 oranges are in the basket. 25 more oranges are put in the basket. How many oranges are in the basket now?", "oranges", "Add 110 + 25."),
    addStory(2, 307, 210, "Leni bought 307 shirts for an orphanage. She bought 210 shirts for another orphanage. How many did she buy altogether?", "shirts", "Add 307 + 210."),
    addStory(3, 124, 235, "Nico has ₱124 in his coin bank. His lola gave him ₱235 more. How much money does he have now?", "pesos", "Add 124 + 235."),
    addStory(4, 115, 24, "115 plums were in the basket. 24 more plums were added to the basket. How many plums are in the basket now?", "plums", "Add 115 + 24."),
    addStory(5, 312, 256, "A vendor earned ₱312 in the morning and ₱256 in the afternoon selling fish balls. How much did she earn for the whole day?", "pesos", "Add 312 + 256."),
    addStory(6, 216, 48, "Emie had 216 chocolate coins. Arvin had 48 chocolate coins. How many coins did they have in all?", "coins", "Add 216 + 48 (regroup 6 + 8 = 14)."),
    addStory(7, 267, 185, "Mrs. Santos had ₱267 in her wallet. She received ₱185 as payment for baked goods. How much money does she have altogether?", "pesos", "Add 267 + 185 with regrouping."),
    addStory(8, 453, 544, "Gary plays a game and scores 453 points in the first round and 544 points in the second round. What is his total at the end of the game?", "points", "Add 453 + 544."),
    addStory(9, 349, 278, "Piolo saved ₱349 from his tricycle fares last week. This week he saved ₱278 more. How much has he saved in total?", "pesos", "Add 349 + 278 with regrouping."),
    addStory(10, 100, 65, "100 red apples and 65 green apples were in the basket. How many apples are there altogether?", "apples", "Add 100 + 65."),
    addStory(11, 167, 332, "There are 167 books in one classroom and 332 books in the other. How many books are there altogether in both classrooms?", "books", "Add 167 + 332."),
    addStory(12, 324, 142, "Reina picked up 324 leaves off the grass. Aries's dad helped her and picked up 142 leaves. How many leaves did they pick up together?", "leaves", "Add 324 + 142."),
    addStory(13, 324, 56, "Chester read 324 pages on Saturday and 56 pages on Sunday. How many pages was he able to read?", "pages", "Add 324 + 56 (regroup 4 + 6 = 10)."),
    addStory(14, 580, 119, "Marianne sold 580 cookies. Abram sold 119 cookies. How many cookies did they sell in all?", "cookies", "Add 580 + 119."),
    addStory(15, 789, 45, "Wesley's farm grew seven hundred eighty-nine pumpkins. Mang Ambo's farm grew forty-five. How many pumpkins did they grow altogether?", "pumpkins", "Convert words to digits (789 + 45) and add with regrouping."),
    addStory(16, 692, 269, "Rae's farm grew 692 watermelons last year and 269 this year. How many watermelons did he grow in both years?", "watermelons", "Add 692 + 269 with regrouping."),
    addStory(17, 357, 145, "Feliz caught 357 fish. Jhun caught 145 fish. How many fish did they catch in all?", "fish", "Add 357 + 145 with regrouping."),
    addStory(18, 400, 65, "The school library bought four hundred new books last year and sixty-five new books this year. How many books did they buy altogether?", "books", "Convert words to digits: 400 + 65."),
    addStory(19, 208, 58, "Dave read the first two hundred eight pages of a book. There are fifty-eight pages left to read. How many pages does the book have in total?", "pages", "Convert words to digits (208 + 58) and add."),
    addStory(20, 228, 71, "There are two hundred twenty-eight people riding the first train and seventy-one people on the second train. How many people are on both trains?", "people", "Convert words to digits: 228 + 71."),
    addStory(21, 230, 140, "There are 230 lions and 140 tigers in the forest. Find the total of the wild animals in the forest.", "animals", "Add 230 + 140."),
    addStory(22, 338, 324, "Cathy's orchard grew 338 apples last month and 324 apples this month. How many apples did she get in both months combined?", "apples", "Add 338 + 324 (regroup 8 + 4 = 12)."),
  )

  fun getWorksheet12(): List<MathProblem> = listOf(
    // 1-14: without regrouping
    157 to 12, 196 to 11, 145 to 13, 155 to 25, 158 to 18,
    167 to 21, 179 to 25, 267 to 25, 264 to 14, 258 to 35,
    288 to 33, 225 to 15, 277 to 24, 239 to 19,
    // 15-24: with regrouping
    134 to 27, 135 to 26, 148 to 39, 143 to 24, 145 to 28,
    247 to 29, 252 to 33, 282 to 46, 281 to 27, 224 to 16,
  ).mapIndexed { idx, (a, b) ->
    val needsBorrow = (a % 10) < (b % 10)
    MathProblem.ArithmeticProblem(
      itemNumber = idx + 1,
      operands = listOf(a, b),
      op = ArithmeticOp.SUBTRACT,
      answer = a - b,
      showVerticalColumns = true,
      hint = if (needsBorrow) {
        "Borrow 1 ten from ${(a / 10) % 10} (making ${(a % 10) + 10} - ${b % 10} in ones), then subtract tens and bring down hundreds!"
      } else {
        "Subtract ones (${a % 10} - ${b % 10}), subtract tens (${(a / 10) % 10} - ${b / 10}), and bring down ${a / 100}."
      },
    )
  }

  private fun subStory(item: Int, a: Int, b: Int, text: String, unit: String?, hint: String): MathProblem.ArithmeticProblem =
    MathProblem.ArithmeticProblem(
      itemNumber = item,
      operands = listOf(a, b),
      op = ArithmeticOp.SUBTRACT,
      answer = a - b,
      promptText = text,
      unitLabel = unit,
      showVerticalColumns = false,
      hint = hint,
    )

  fun getWorksheet13(): List<MathProblem> = listOf(
    subStory(1, 74, 20, "Nica had 74 coins. After spending 20 coins on a football, how many coins does she have left?", "coins", "Subtract 74 - 20."),
    subStory(2, 54, 23, "Elmer's high school played 54 baseball games this year. He attended 23 games. How many baseball games did Elmer miss?", "games", "Subtract 54 - 23."),
    subStory(3, 950, 684, "Aling Fe had ₱950 in her cash box. She used ₱684 to buy ingredients for her turon business. How much money is left in her cash box?", "pesos", "Subtract 950 - 684 with regrouping."),
    subStory(4, 39, 16, "There are thirty-nine pencils in the drawer. Sam took sixteen pencils from the drawer. How many pencils are now in the drawer?", "pencils", "Convert words to digits: 39 - 16."),
    subStory(5, 74, 29, "Lawrence had 74 baseball cards. Then, he gave his friend 29 of them. How many does he have left?", "cards", "Subtract 74 - 29 (borrow 1 ten from 7 tens)."),
    subStory(6, 79, 45, "Gerard has 45 cents and his brother, Allen has 79 cents. How much more money does Allen have than Gerard?", "cents", "Subtract 79 - 45."),
    subStory(7, 780, 225, "Nardo saved ₱780 from his allowance. He spent ₱225 on new school shoes. How much of his savings is left?", "pesos", "Subtract 780 - 225 (borrow 1 ten for 10 - 5)."),
    subStory(8, 77, 29, "There were 77 sports books. Sadly, 29 were ruined in a flood. How many are left?", "books", "Subtract 77 - 29 with regrouping."),
    subStory(9, 899, 543, "A canteen had ₱899 in sales for the week. ₱543 was used to pay the supplier. How much money remains?", "pesos", "Subtract 899 - 543."),
    subStory(10, 45, 21, "The ice cream shop had 45 ice cream cones to sell. The cashier sold 21 ice cream cones. How many ice cream cones are left?", "cones", "Subtract 45 - 21."),
  )
}
