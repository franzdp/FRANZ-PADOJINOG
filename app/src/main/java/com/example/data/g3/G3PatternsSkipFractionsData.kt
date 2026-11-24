package com.example.data.g3

import com.example.model.*

object G3PatternsSkipFractionsData {

  private fun pat(
    item: Int,
    terms: List<String>,
    expected: List<String>,
    choices: List<String>,
    hint: String,
    blanks: Int = expected.size,
  ): MathProblem.PatternProblem = MathProblem.PatternProblem(
    itemNumber = item,
    tokens = terms.map { PatternToken(PatternTokenKind.TEXT, it) } + List(blanks) { PatternToken(PatternTokenKind.BLANK) },
    expectedAnswers = expected,
    choices = choices,
    isShapeOrLetterChoice = true,
    hint = hint,
  )

  fun getWorksheet5(): List<MathProblem> = listOf(
    pat(1, listOf("2", "B", "4", "D", "6", "F", "8", "H", "10"), listOf("J", "12"), listOf("J", "12", "I", "11"), "Letters skip 1 forward (B, D, F, H -> J) and numbers add 2 (10 -> 12)."),
    pat(2, listOf("5", "K", "10", "L", "15", "M", "20", "N"), listOf("25", "O"), listOf("25", "O", "30", "P"), "Numbers add 5 (5, 10, 15, 20 -> 25); letters go K, L, M, N -> O."),
    pat(3, listOf("A", "1", "C", "2", "E", "3", "G", "4", "I"), listOf("5", "K"), listOf("5", "K", "6", "J"), "After letter I comes the next number (+1: 4 -> 5), then letters skip 1 (G, I -> K)."),
    pat(4, listOf("Z", "26", "Y", "24", "X", "22", "W", "20"), listOf("V", "18"), listOf("V", "18", "U", "16"), "Letters go backward (Z, Y, X, W -> V) and numbers subtract 2 (20 -> 18)."),
    pat(5, listOf("10", "D", "15", "E", "20", "F", "25", "G"), listOf("30", "H"), listOf("30", "H", "35", "I"), "Numbers add 5 (25 -> 30); letters advance 1 step (D, E, F, G -> H)."),
    pat(6, listOf("Q", "17", "P", "15", "O", "13", "N", "11"), listOf("M", "9"), listOf("M", "9", "L", "10"), "Letters step backward (Q, P, O, N -> M) and numbers subtract 2 (11 -> 9)."),
    pat(7, listOf("2", "A", "4", "B", "6", "C", "8", "D"), listOf("10", "E"), listOf("10", "E", "12", "F"), "Numbers add 2 (8 -> 10) and letters advance A, B, C, D -> E."),
    pat(8, listOf("M", "10", "N", "15", "O", "20", "P", "25"), listOf("Q", "30"), listOf("Q", "30", "R", "35"), "Letters go M, N, O, P -> Q and numbers add 5 (25 -> 30)."),
    pat(9, listOf("2", "Z", "4", "Y", "6", "X", "8", "W"), listOf("10", "V"), listOf("10", "V", "12", "U"), "Numbers add 2 (8 -> 10) while letters go backward (Z, Y, X, W -> V)."),
    pat(10, listOf("2", "G", "4", "H", "6", "I", "8", "J"), listOf("10", "K"), listOf("10", "K", "12", "L"), "Numbers add 2 (8 -> 10) and letters step G, H, I, J -> K."),
    pat(11, listOf("B", "2", "C", "4", "D", "6", "E", "8"), listOf("F", "10"), listOf("F", "10", "G", "12"), "Letters go B, C, D, E -> F and numbers add 2 (8 -> 10)."),
    pat(12, listOf("10", "J", "20", "I", "30", "H", "40", "G"), listOf("50", "F"), listOf("50", "F", "60", "E"), "Numbers add 10 (40 -> 50); letters step backward (J, I, H, G -> F)."),
    pat(13, listOf("A", "5", "B", "10", "C", "15", "D", "20"), listOf("E", "25"), listOf("E", "25", "F", "30"), "Letters go A, B, C, D -> E and numbers add 5 (20 -> 25)."),
    pat(14, listOf("R", "18", "Q", "16", "P", "14", "O", "12"), listOf("N", "10"), listOf("N", "10", "M", "8"), "Letters go backward (R, Q, P, O -> N) and numbers subtract 2 (12 -> 10)."),
    pat(15, listOf("2", "M", "4", "N", "6", "O", "8", "P"), listOf("10", "Q"), listOf("10", "Q", "12", "R"), "Numbers add 2 (8 -> 10) and letters advance M, N, O, P -> Q."),
    pat(16, listOf("L", "30", "K", "25", "J", "20", "I", "15"), listOf("H", "10"), listOf("H", "10", "G", "5"), "Letters go backward (L, K, J, I -> H) and numbers subtract 5 (15 -> 10)."),
    pat(17, listOf("1", "Z", "2", "Y", "3", "X", "4", "W"), listOf("5", "V"), listOf("5", "V", "6", "U"), "Numbers add 1 (4 -> 5) and letters go backward (Z, Y, X, W -> V)."),
    pat(18, listOf("5", "E", "10", "F", "15", "G", "20", "H"), listOf("25", "I"), listOf("25", "I", "30", "J"), "Numbers add 5 (20 -> 25) and letters advance E, F, G, H -> I."),
    pat(19, listOf("S", "19", "T", "17", "U", "15", "V", "13"), listOf("W", "11"), listOf("W", "11", "X", "9"), "Letters advance S, T, U, V -> W and numbers subtract 2 (13 -> 11)."),
    pat(20, listOf("100", "A", "200", "C", "300", "E", "400", "G"), listOf("500", "I"), listOf("500", "I", "600", "H"), "Numbers add 100 (400 -> 500) and letters skip 1 forward (A, C, E, G -> I)."),
    pat(21, listOf("20", "A", "40", "B", "60", "C", "80", "D", "100", "E"), listOf("120", "F"), listOf("120", "F", "140", "G"), "Numbers add 20 (100 -> 120) and letters advance A..E -> F."),
    pat(22, listOf("Q", "5", "P", "10", "O", "15", "N", "20", "M", "25"), listOf("L", "30"), listOf("L", "30", "K", "35"), "Letters go backward (Q..M -> L) and numbers add 5 (25 -> 30)."),
    pat(23, listOf("2", "K", "4", "L", "6", "M", "8", "N", "10", "O"), listOf("12", "P"), listOf("12", "P", "14", "Q"), "Numbers add 2 (10 -> 12) and letters advance K..O -> P."),
    pat(24, listOf("A", "5", "B", "10", "C", "15", "D", "20", "E", "25"), listOf("F", "30"), listOf("F", "30", "G", "35"), "Letters go A..E -> F and numbers add 5 (25 -> 30)."),
    pat(25, listOf("Z", "30", "Y", "28", "X", "26", "W", "24", "V", "22"), listOf("U", "20"), listOf("U", "20", "T", "18"), "Letters go backward (Z..V -> U) and numbers subtract 2 (22 -> 20)."),
    pat(26, listOf("20", "D", "40", "E", "60", "F", "80", "G", "100", "H"), listOf("120", "I"), listOf("120", "I", "140", "J"), "Numbers add 20 (100 -> 120) and letters go D..H -> I."),
    pat(27, listOf("M", "2", "N", "4", "O", "6", "P", "8", "Q", "10"), listOf("R", "12"), listOf("R", "12", "S", "14"), "Letters go M..Q -> R and numbers add 2 (10 -> 12)."),
    pat(28, listOf("5", "X", "10", "W", "15", "V", "20", "U", "25", "T"), listOf("30", "S"), listOf("30", "S", "35", "R"), "Numbers add 5 (25 -> 30) and letters go backward (X..T -> S)."),
    pat(29, listOf("H", "8", "G", "6", "F", "4", "E", "2", "D", "0"), listOf("C", "-2"), listOf("C", "-2", "B", "2"), "Letters step backward (H..D -> C) and numbers subtract 2 each time (0 - 2 = -2)."),
    pat(30, listOf("30", "A", "40", "B", "50", "C", "60", "D", "70", "E"), listOf("80", "F"), listOf("80", "F", "90", "G"), "Numbers add 10 (70 -> 80) and letters advance A..E -> F."),
    pat(31, listOf("R", "8", "S", "10", "T", "12", "U", "14", "V", "16"), listOf("W", "18"), listOf("W", "18", "X", "20"), "Letters advance R..V -> W and numbers add 2 (16 -> 18)."),
    pat(32, listOf("9", "M", "8", "N", "7", "O", "6", "P", "5", "Q"), listOf("4", "R"), listOf("4", "R", "3", "S"), "Numbers subtract 1 (5 -> 4) and letters advance M..Q -> R."),
    pat(33, listOf("C", "45", "D", "40", "E", "35", "F", "30", "G", "25"), listOf("H", "20"), listOf("H", "20", "I", "15"), "Letters go C..G -> H and numbers subtract 5 (25 -> 20)."),
    pat(34, listOf("20", "A", "40", "B", "60", "C", "80", "D", "100"), listOf("E", "120"), listOf("E", "120", "F", "140"), "After 100 comes the letter after D (E), then 100 + 20 = 120."),
    pat(35, listOf("L", "5", "K", "10", "J", "15", "I", "20", "H", "25"), listOf("G", "30"), listOf("G", "30", "F", "35"), "Letters step backward (L..H -> G) and numbers add 5 (25 -> 30)."),
    pat(36, listOf("100", "Q", "98", "R", "96", "S", "94", "T", "92", "U"), listOf("90", "V"), listOf("90", "V", "88", "W"), "Numbers subtract 2 (92 -> 90) and letters advance Q..U -> V."),
    pat(37, listOf("B", "45", "C", "50", "D", "55", "E", "60", "F", "65"), listOf("G", "70"), listOf("G", "70", "H", "75"), "Letters advance B..F -> G and numbers add 5 (65 -> 70)."),
    pat(38, listOf("20", "J", "18", "I", "16", "H", "14", "G", "12", "F"), listOf("10", "E"), listOf("10", "E", "8", "D"), "Numbers subtract 2 (12 -> 10) and letters step backward (J..F -> E)."),
    pat(39, listOf("P", "0", "O", "50", "N", "100", "M", "150", "L", "200"), listOf("K", "250"), listOf("K", "250", "J", "300"), "Letters step backward (P..L -> K) and numbers add 50 (200 -> 250)."),
    pat(40, listOf("45", "Z", "40", "Y", "35", "X", "30", "W", "25", "V"), listOf("20", "U"), listOf("20", "U", "15", "T"), "Numbers subtract 5 (25 -> 20) and letters step backward (Z..V -> U)."),
  )

  fun getWorksheet6(): List<MathProblem> = listOf(
    pat(1, listOf("2", "4", "6", "8", "10"), listOf("12", "14"), listOf("12", "14", "16", "11"), "Add 2 to each term: 10 + 2 = 12, then 14."),
    pat(2, listOf("A", "B", "C", "D", "E"), listOf("F", "G"), listOf("F", "G", "H", "I"), "Recite the alphabet: after E come F and G."),
    pat(3, listOf("January", "February", "March", "April"), listOf("May", "June"), listOf("May", "June", "July", "August"), "Follow the 12 months in order after April."),
    pat(4, listOf("Monday", "Tuesday", "Wednesday", "Thursday"), listOf("Friday", "Saturday"), listOf("Friday", "Saturday", "Sunday", "Monday"), "Follow the days of the week after Thursday."),
    pat(5, listOf("2", "4", "6", "8", "10", "12", "14"), listOf("16"), listOf("15", "16", "17", "18"), "Count by 2s: 14 + 2 = 16."),
    pat(6, listOf("3", "3", "3", "3", "3", "3", "3"), listOf("3"), listOf("3", "6", "9", "4"), "Every term in this repeating pattern is 3."),
    pat(7, listOf("15", "20", "25", "30", "35"), listOf("40", "45"), listOf("40", "45", "50", "38"), "Add 5 each step: 35 + 5 = 40, then 45."),
    pat(8, listOf("31", "36", "41", "46", "51"), listOf("56", "61"), listOf("56", "61", "55", "60"), "Add 5 each step: 51 + 5 = 56, then 61."),
    pat(9, listOf("G", "H", "I", "J", "K"), listOf("L", "M"), listOf("L", "M", "N", "O"), "Follow alphabet order after K."),
    pat(10, listOf("1st", "2nd", "3rd", "4th", "5th"), listOf("6th", "7th"), listOf("6th", "7th", "8th", "9th"), "Continue the ordinal numbers after 5th."),
    pat(11, listOf("A", "B", "A", "B", "A", "B"), listOf("A", "B"), listOf("A", "B", "C", "D"), "Repeats A, B over and over."),
    pat(12, listOf("X", "Y", "Z", "X", "Y", "Z"), listOf("X", "Y"), listOf("X", "Y", "Z", "W"), "3-letter cycle: X, Y, Z repeats from X."),
    pat(13, listOf("red", "blue", "red", "blue", "red", "blue"), listOf("red", "blue"), listOf("red", "blue", "green", "yellow"), "Alternates red then blue."),
    pat(14, listOf("cat", "dog", "cat", "dog", "cat", "dog"), listOf("cat", "dog"), listOf("cat", "dog", "bird", "fish"), "Alternates cat then dog."),
    pat(15, listOf("5", "10", "5", "10", "5", "10"), listOf("5", "10"), listOf("5", "10", "15", "20"), "Alternates 5 then 10."),
    pat(16, listOf("1", "2", "1", "2", "1", "2"), listOf("1", "2"), listOf("1", "2", "3", "4"), "Alternates 1 then 2."),
    pat(17, listOf("2", "4", "2", "4", "2", "4"), listOf("2", "4"), listOf("2", "4", "6", "8"), "Alternates 2 then 4."),
    pat(18, listOf("A", "A", "B", "B", "A", "A", "B", "B"), listOf("A", "A"), listOf("A", "B", "C"), "Cycle is A, A, B, B — after B, B it starts back with A, A."),
    pat(19, listOf("sunny", "rainy", "sunny", "rainy", "sunny", "rainy"), listOf("sunny", "rainy"), listOf("sunny", "rainy", "cloudy", "windy"), "Alternates sunny then rainy."),
    pat(20, listOf("100", "90", "80", "70", "60"), listOf("50", "40"), listOf("50", "40", "30", "55"), "Subtract 10 each step: 60 - 10 = 50, then 40."),
    pat(21, listOf("3", "6", "9", "12", "15"), listOf("18", "21"), listOf("18", "21", "24", "17"), "Count by 3s: 15 + 3 = 18, then 21."),
    pat(22, listOf("K", "L", "M", "N", "O"), listOf("P", "Q"), listOf("P", "Q", "R", "S"), "Alphabet letters after O."),
    pat(23, listOf("May", "June", "July", "August"), listOf("September", "October"), listOf("September", "October", "November", "December"), "Follow the months after August."),
    pat(24, listOf("Saturday", "Sunday", "Monday", "Tuesday"), listOf("Wednesday", "Thursday"), listOf("Wednesday", "Thursday", "Friday", "Monday"), "Follow the days of the week after Tuesday."),
    pat(25, listOf("1", "11", "111", "1111", "11111"), listOf("111111", "1111111"), listOf("111111", "1111111", "11111", "11111111"), "Each term adds one more digit '1' (5 ones -> 6 ones -> 7 ones)."),
    pat(26, listOf("yes", "yes", "no", "yes", "yes", "no", "yes", "yes"), listOf("no", "yes"), listOf("yes", "no", "maybe"), "Cycle is yes, yes, no — after two 'yes', next is 'no', then 'yes'."),
    pat(27, listOf("50", "55", "60", "65"), listOf("70", "75"), listOf("70", "75", "80", "68"), "Add 5 each step: 65 + 5 = 70, then 75."),
    pat(28, listOf("P", "Q", "R", "S", "T", "U"), listOf("V", "W"), listOf("V", "W", "X", "Y"), "Follow alphabet order after U."),
    pat(29, listOf("10th", "11th", "12th", "13th"), listOf("14th", "15th"), listOf("14th", "15th", "16th", "17th"), "Count ordinal numbers up by 1 after 13th."),
    pat(30, listOf("100", "200", "300", "400"), listOf("500", "600"), listOf("500", "600", "700", "450"), "Add 100 each step: 400 -> 500 -> 600."),
    pat(31, listOf("red", "blue", "red", "blue", "red", "blue", "red", "blue"), listOf("red", "blue"), listOf("red", "blue", "green", "orange"), "Alternates red, blue."),
    pat(32, listOf("black", "white", "black", "white", "black", "white"), listOf("black", "white"), listOf("black", "white", "gray", "blue"), "Alternates black, white."),
    pat(33, listOf("cat", "cat", "dog", "cat", "cat", "dog", "cat", "cat", "dog"), listOf("cat", "cat"), listOf("cat", "dog", "bird"), "Cycle is cat, cat, dog — after dog, start again with cat, cat."),
    pat(34, listOf("apple", "banana", "apple", "banana", "apple", "banana"), listOf("apple", "banana"), listOf("apple", "banana", "orange", "mango"), "Alternates apple, banana."),
    pat(35, listOf("2", "4", "2", "4", "2", "4", "2", "4"), listOf("2", "4"), listOf("2", "4", "6", "8"), "Alternates 2, 4."),
    pat(36, listOf("A", "A", "B", "A", "A", "B", "A", "A", "B"), listOf("A", "A"), listOf("A", "B", "C"), "Cycle is A, A, B — after B, start back with A, A."),
    pat(37, listOf("1", "1", "2", "1", "1", "2", "1", "1", "2"), listOf("1", "1"), listOf("1", "2", "3"), "Cycle is 1, 1, 2 — after 2 comes 1, 1."),
    pat(38, listOf("Monday", "Monday", "Tuesday", "Monday", "Monday", "Tuesday"), listOf("Monday", "Monday"), listOf("Monday", "Tuesday", "Wednesday"), "Cycle is Monday, Monday, Tuesday — starts back with Monday, Monday."),
    pat(39, listOf("high", "low", "high", "low", "high", "low", "high", "low"), listOf("high", "low"), listOf("high", "low", "tall", "short"), "Alternates high, low."),
    pat(40, listOf("up", "down", "up", "down", "up", "down", "up", "down"), listOf("up", "down"), listOf("up", "down", "left", "right"), "Alternates up, down."),
  )

  private fun seq(item: Int, title: String, step: Int, vals: List<Int?>, exp: List<Int>, hint: String): SkipCountGridProblem =
    SkipCountGridProblem(
      itemNumber = item,
      style = SkipGridStyle.OBJECT_SEQUENCE,
      title = title,
      stepSize = step,
      sequenceValues = vals,
      expectedBlanks = exp,
      hint = hint,
    )

  private fun coins(item: Int, title: String, denom: Int, count: Int): SkipCountGridProblem =
    SkipCountGridProblem(
      itemNumber = item,
      style = SkipGridStyle.COIN_GROUP_TOTAL,
      title = title,
      stepSize = denom,
      expectedBlanks = listOf(denom * count),
      coinDenom = denom,
      coinCount = count,
      iconLabel = "$denom",
      hint = "Skip count by ${denom}s for each of the $count circles ($count × $denom).",
    )

  fun getWorksheet7(): List<MathProblem> = listOf(
    seq(1, "Skip count by 10s:", 10, listOf(10, 20, 30, null, null, null, null, 80, 90, 100), listOf(40, 50, 60, 70), "Add 10 each step after 30."),
    seq(2, "Skip counting 50:", 50, listOf(50, 100, 150, null, null), listOf(200, 250), "Add 50 each step: 150 + 50, then + 50."),
    seq(3, "Skip counting 100:", 100, listOf(100, 200, null, null, 500, null), listOf(300, 400, 600), "Add 100 each hop."),
    seq(4, "Skip count by 50s:", 50, listOf(350, 400, 450, null, null, null), listOf(500, 550, 600), "Add 50 each step after 450."),
    seq(5, "Skip count by 10s:", 10, listOf(5, 15, 25, null, null, null, 65, 75, 85), listOf(35, 45, 55), "Add 10 each step — keep the 5 in the ones place!"),
    seq(6, "Skip counting 100:", 100, listOf(300, null, null, 600, null, null, 900, 1000), listOf(400, 500, 700, 800), "Add 100 each hop from 300 to 1000."),
    seq(7, "Skip counting 10:", 10, listOf(20, 30, null, null, 60, null, null), listOf(40, 50, 70, 80), "Add 10 each hop."),
    seq(8, "Skip count by 100s:", 100, listOf(100, 200, 300, null, null, null, 700, 800, 900), listOf(400, 500, 600), "Add 100 each step after 300."),
    seq(9, "Skip counting 100:", 100, listOf(100, 200, null, null, 500, null), listOf(300, 400, 600), "Add 100 each hop."),
    seq(10, "Skip count by 10s:", 10, listOf(30, 40, 50, null, null, null, 90, 100), listOf(60, 70, 80), "Add 10 each step after 50."),
    seq(11, "Skip counting 10:", 10, listOf(5, 15, 25, null, null, null), listOf(35, 45, 55), "Add 10 each hop after 25."),
    seq(12, "Skip count by 50s:", 50, listOf(75, 125, 175, null, null, null, 375, 425, 475), listOf(225, 275, 325), "Add 50 each step after 175."),
    seq(13, "Skip count by 100s:", 100, listOf(200, 300, 400, null, null, 700, 800, 900), listOf(500, 600), "Add 100 each step after 400."),
    coins(14, "How many in all? (Skip count by 100s)", 100, 4),
    seq(15, "Skip counting 50:", 50, listOf(100, 150, null, 250, null), listOf(200, 300), "Add 50 each hop."),
    seq(16, "Skip count by 100s:", 100, listOf(150, 250, 350, null, null, null, 750, 850, 950), listOf(450, 550, 650), "Add 100 each step — keep 50 in tens/ones."),
    seq(17, "Skip count by 10s:", 10, listOf(25, 35, null, null, null, 75, 85, null, null), listOf(45, 55, 65, 95, 105), "Add 10 each step across all 5 blanks."),
    seq(18, "Skip counting 50:", 50, listOf(75, null, null, 225, null, null, 375), listOf(125, 175, 275, 325), "Add 50 each hop starting from 75."),
    coins(19, "How many in all? (Skip count by 50s)", 50, 6),
    seq(20, "Skip count by 100s:", 100, listOf(100, 200, 300, 400, null, null, null, 800, 900, 1000), listOf(500, 600, 700), "Add 100 each step after 400."),
    seq(21, "Skip counting 10:", 10, listOf(60, 70, 80, null, null, null), listOf(90, 100, 110), "Add 10 each hop after 80."),
    seq(22, "Skip counting 50:", 50, listOf(150, 200, 250, null, null, null, 450, 500), listOf(300, 350, 400), "Add 50 each hop after 250."),
    seq(23, "Skip counting 100:", 100, listOf(100, 200, 300, null, null, 600, 700, 800), listOf(400, 500), "Add 100 each hop after 300."),
    seq(24, "Skip counting 10:", 10, listOf(30, 40, null, null, null, 80, 90, 100), listOf(50, 60, 70), "Add 10 each hop after 40."),
    seq(25, "Skip counting 50:", 50, listOf(50, 100, 150, null, null), listOf(200, 250), "Add 50 each hop after 150."),
    seq(26, "Skip counting 100:", 100, listOf(200, 300, 400, null, null, 700, 800, 900), listOf(500, 600), "Add 100 each hop after 400."),
    seq(27, "Skip counting 10:", 10, listOf(80, 90, 100, null, null), listOf(110, 120), "Add 10 each hop after 100."),
    seq(28, "Skip counting 50:", 50, listOf(450, 500, 550, null, null), listOf(600, 650), "Add 50 each hop after 550."),
    seq(29, "Skip counting 100:", 100, listOf(700, 800, 900, null, null), listOf(1000, 1100), "Add 100 after 900 -> 1,000, then 1,100."),
    coins(30, "Skip counting 10. How many?", 10, 8),
    coins(31, "Skip counting 50. How many?", 50, 8),
    coins(32, "Skip counting 100. How many?", 100, 7),
    coins(33, "Skip counting 10. How many?", 10, 8),
    coins(34, "Skip counting 50. How many?", 50, 5),
    coins(35, "Skip counting 100. How many?", 100, 5),
    coins(36, "Skip counting 10. How many?", 10, 9),
    coins(37, "Skip counting 50. How many?", 50, 5),
    coins(38, "Skip counting 100. How many?", 100, 6),
  )

  private fun cmpUnit(item: Int, d1: Int, d2: Int, sym: String): MathProblem.CompareFractionsProblem =
    MathProblem.CompareFractionsProblem(
      itemNumber = item,
      leftLabel = "1/$d1", leftNum = 1, leftDen = d1,
      rightLabel = "1/$d2", rightNum = 1, rightDen = d2,
      answerSymbol = sym,
      isSimilar = false,
      hint = "For unit fractions (top = 1), a BIGGER denominator means a SMALLER slice!",
    )

  private fun cmpSim(item: Int, n1: Int, n2: Int, den: Int, sym: String): MathProblem.CompareFractionsProblem =
    MathProblem.CompareFractionsProblem(
      itemNumber = item,
      leftLabel = "$n1/$den", leftNum = n1, leftDen = den,
      rightLabel = "$n2/$den", rightNum = n2, rightDen = den,
      answerSymbol = sym,
      isSimilar = true,
      hint = "Same denominator ($den): compare the numerators $n1 and $n2 directly.",
    )

  fun getWorksheet8(): List<MathProblem> {
    val unitCmps = listOf(
      cmpUnit(1, 5, 8, ">"), cmpUnit(2, 2, 3, ">"), cmpUnit(3, 5, 6, ">"), cmpUnit(4, 6, 4, "<"), cmpUnit(5, 6, 3, "<"),
      cmpUnit(6, 4, 4, "="), cmpUnit(7, 3, 6, ">"), cmpUnit(8, 8, 3, "<"), cmpUnit(9, 5, 2, "<"), cmpUnit(10, 6, 6, "="),
    )

    val asc = listOf(
      listOf(1 to 4, 1 to 6, 1 to 3),
      listOf(1 to 3, 1 to 4, 1 to 2),
      listOf(1 to 6, 1 to 5, 1 to 8),
      listOf(1 to 2, 1 to 8, 1 to 6),
    ).mapIndexed { idx, pairs ->
      val items = pairs.map { FractionItem(it.first, it.second) }
      MathProblem.OrderFractionsProblem(
        itemNumber = 11 + idx,
        fractions = items,
        ascending = true,
        expectedOrder = items.sortedByDescending { it.den },
        hint = "Ascending (least to greatest) unit fractions: biggest denominator comes FIRST!",
      )
    }

    val desc = listOf(
      listOf(1 to 6, 1 to 2, 1 to 3),
      listOf(1 to 8, 1 to 4, 1 to 2),
      listOf(1 to 6, 1 to 3, 1 to 4),
      listOf(1 to 3, 1 to 8, 1 to 6),
    ).mapIndexed { idx, pairs ->
      val items = pairs.map { FractionItem(it.first, it.second) }
      MathProblem.OrderFractionsProblem(
        itemNumber = 15 + idx,
        fractions = items,
        ascending = false,
        expectedOrder = items.sortedBy { it.den },
        hint = "Descending (greatest to least) unit fractions: smallest denominator comes FIRST!",
      )
    }

    val simCmps = listOf(
      cmpSim(19, 3, 1, 8, ">"), cmpSim(20, 4, 5, 7, "<"), cmpSim(21, 2, 2, 5, "="),
      cmpSim(22, 5, 4, 10, ">"), cmpSim(23, 8, 1, 9, ">"), cmpSim(24, 4, 5, 6, "<"),
      cmpSim(25, 1, 2, 2, "<"), cmpSim(26, 6, 4, 7, ">"), cmpSim(27, 3, 7, 10, "<"),
      cmpSim(28, 2, 1, 3, ">"), cmpSim(29, 1, 1, 10, "="), cmpSim(30, 4, 1, 4, ">"),
    )

    return unitCmps + asc + desc + simCmps
  }
}
