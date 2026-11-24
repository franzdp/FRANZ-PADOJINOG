package com.example.data

import com.example.model.LearningWorld
import com.example.model.MiniLesson
import com.example.model.WorksheetInfo

object WorksheetCatalog {
  val allWorksheets: List<WorksheetInfo> = listOf(
    WorksheetInfo(
      id = 1,
      title = "Odd and Even Numbers",
      shortTag = "WS 1 • 40 items",
      world = LearningWorld.NUMBER_SENSE,
      itemCount = 40,
      instructions = "Determine if each number is odd or even.",
      miniLesson = MiniLesson(
        heading = "Odd and Even Numbers",
        rules = listOf(
          "Numbers that end with 0, 2, 4, 6, 8 are EVEN.",
          "Numbers that end with 1, 3, 5, 7, 9 are ODD.",
        ),
        exampleTitle = "Examples",
        exampleBody = "Even: 24, 56, 88, 100\nOdd: 13, 45, 99, 101",
      ),
    ),
    WorksheetInfo(
      id = 2,
      title = "Comparing Fractions",
      shortTag = "WS 2 • 50 items",
      world = LearningWorld.FRACTIONS,
      itemCount = 50,
      instructions = "Compare similar and dissimilar fractions using <, >, or =.",
      miniLesson = MiniLesson(
        heading = "Comparing Similar & Dissimilar Fractions",
        rules = listOf(
          "Similar Fractions have the same denominator — compare the numerators directly.",
          "Dissimilar Fractions have different denominators — use the Cross Product Method!",
        ),
        exampleTitle = "Examples",
        exampleBody = "Similar: 2/5 < 3/5 since 2 < 3.\nDissimilar: 2/5 vs 3/7 -> 7×2 = 14 and 5×3 = 15. Since 14 < 15, 2/5 < 3/7.",
      ),
    ),
    WorksheetInfo(
      id = 3,
      title = "Arranging Fractions",
      shortTag = "WS 3 • 30 items",
      world = LearningWorld.FRACTIONS,
      itemCount = 30,
      instructions = "Arrange fractions in ascending (least to greatest) or descending (greatest to least) order.",
      miniLesson = MiniLesson(
        heading = "Ordering Fractions",
        rules = listOf(
          "Same Denominator: Larger numerator = LARGER fraction.",
          "Same Numerator: Bigger denominator = SMALLER fraction.",
        ),
        exampleTitle = "Examples",
        exampleBody = "Ascending (1/16, 1/9, 1/4, 1/2) — biggest denominator comes first!\nDescending (4/4, 3/4, 2/4, 1/4) — biggest numerator comes first!",
      ),
    ),
    WorksheetInfo(
      id = 4,
      title = "Parts of a Fraction & Visuals",
      shortTag = "WS 4 • 39 items",
      world = LearningWorld.FRACTIONS,
      itemCount = 39,
      instructions = "Identify numerators, denominators, shaded shape fractions, and picture group fractions.",
      miniLesson = MiniLesson(
        heading = "What Is a Fraction?",
        rules = listOf(
          "A fraction represents a part of a whole.",
          "Numerator (top): number of shaded or named parts.",
          "Denominator (bottom): total number of equal parts.",
        ),
        exampleTitle = "Example: 3/5",
        exampleBody = "3 out of 5 equal boxes are shaded -> Numerator = 3, Denominator = 5.",
      ),
    ),
    WorksheetInfo(
      id = 5,
      title = "Writing Fractions in Words",
      shortTag = "WS 5 • 42 items",
      world = LearningWorld.FRACTIONS,
      itemCount = 42,
      instructions = "Write fractions in words, fill in equal-part blanks, and convert words to numbers.",
      miniLesson = MiniLesson(
        heading = "Fractions in Words",
        rules = listOf(
          "Write the numerator as a cardinal number (one, two, three...).",
          "Write the denominator as an ordinal number (third, fifths, thirteenths...).",
          "Exception: 2 as denominator is 'half'; when numerator is 1, denominator is singular!",
        ),
        exampleTitle = "Examples",
        exampleBody = "1/3 = one-third\n3/5 = three-fifths\n13/30 = thirteen-thirtieths",
      ),
    ),
    WorksheetInfo(
      id = 6,
      title = "Number & Shape Patterns",
      shortTag = "WS 6 • 55 items",
      world = LearningWorld.PATTERNS,
      itemCount = 55,
      instructions = "Find the missing numbers, letters, or shapes in each pattern.",
      miniLesson = MiniLesson(
        heading = "Finding Patterns",
        rules = listOf(
          "Check how much is added, subtracted, or multiplied from one number to the next.",
          "For alternating patterns, look at shapes, letters, and numbers separately.",
        ),
        exampleTitle = "Example",
        exampleBody = "4, 8, 12, ___, 20 -> Rule is +4, so missing number is 16!",
      ),
    ),
    WorksheetInfo(
      id = 7,
      title = "3- & 4-Digit Addition",
      shortTag = "WS 7 • 40 items",
      world = LearningWorld.ADD_SUB,
      itemCount = 40,
      instructions = "Find the sum of 2 or 3 addends.",
      miniLesson = MiniLesson(
        heading = "Adding Multi-Digit Numbers",
        rules = listOf(
          "Align digits by place value (ones under ones, tens under tens).",
          "Add from right to left and regroup (carry) when a column is 10 or more.",
        ),
        exampleTitle = "Example",
        exampleBody = "620 + 156 = 776\n1,173 + 2,122 = 3,295",
      ),
    ),
    WorksheetInfo(
      id = 8,
      title = "Addition Sentences & Stories",
      shortTag = "WS 8 • 35 items",
      world = LearningWorld.ADD_SUB,
      itemCount = 35,
      instructions = "Solve addition questions and Philippine history & school word problems.",
      miniLesson = MiniLesson(
        heading = "Solving Addition Word Problems",
        rules = listOf(
          "Look for clue words like 'in all', 'altogether', 'sum', 'more', 'later', or 'increased by'.",
          "Add the given numbers carefully and label your answer.",
        ),
        exampleTitle = "Example",
        exampleBody = "Magellan arrived in 1521. Legazpi arrived 44 years later: 1521 + 44 = 1565.",
      ),
    ),
    WorksheetInfo(
      id = 9,
      title = "Subtraction Practice",
      shortTag = "WS 9 • 40 items",
      world = LearningWorld.ADD_SUB,
      itemCount = 40,
      instructions = "Find the difference ('-', 'less', and 'subtracted from').",
      miniLesson = MiniLesson(
        heading = "Subtraction Clues",
        rules = listOf(
          "'A less B' means A - B.",
          "'B subtracted from A' also means A - B (start with A!).",
          "Borrow/regroup from the left column whenever the top digit is smaller.",
        ),
        exampleTitle = "Example",
        exampleBody = "205 subtracted from 841 -> 841 - 205 = 636.",
      ),
    ),
    WorksheetInfo(
      id = 10,
      title = "Two-Step Word Problems",
      shortTag = "WS 10 • 22 items",
      world = LearningWorld.ADD_SUB,
      itemCount = 22,
      instructions = "Read carefully and solve multi-step addition and subtraction stories.",
      miniLesson = MiniLesson(
        heading = "Two-Step Problem Solving",
        rules = listOf(
          "Step 1: Find what happens first (add or subtract).",
          "Step 2: Use that result with the next event in the story.",
        ),
        exampleTitle = "Example",
        exampleBody = "Mona had 714 + 186 = 900 pesos, then spent 220 -> 900 - 220 = 680 pesos.",
      ),
    ),
    WorksheetInfo(
      id = 11,
      title = "Multiply 2-3 Digit by 1-Digit",
      shortTag = "WS 11 • 14 items",
      world = LearningWorld.MULTIPLY,
      itemCount = 14,
      instructions = "Multiply 2- and 3-digit numbers by a 1-digit factor and solve tricycle problems.",
      miniLesson = MiniLesson(
        heading = "2-3 Digit × 1-Digit",
        rules = listOf(
          "1) Multiply each digit of the first factor by the ones digit of the second factor.",
          "2) Regroup (carry) to the next place value if needed.",
        ),
        exampleTitle = "Example: 54 × 3",
        exampleBody = "4 × 3 = 12 (write 2, carry 1)\n5 × 3 = 15 + 1 = 16 -> 162",
      ),
    ),
    WorksheetInfo(
      id = 12,
      title = "Multiply 2-Digit by 2-Digit",
      shortTag = "WS 12 • 14 items",
      world = LearningWorld.MULTIPLY,
      itemCount = 14,
      instructions = "Find partial products and add them together, plus jeepney word problems.",
      miniLesson = MiniLesson(
        heading = "2-Digit × 2-Digit",
        rules = listOf(
          "1) Multiply the first factor by the ones digit.",
          "2) Multiply by the tens digit (align under the tens place!).",
          "3) Add the two partial products.",
        ),
        exampleTitle = "Example: 54 × 23",
        exampleBody = "54 × 3 = 162\n54 × 20 = 1,080\n162 + 1,080 = 1,242",
      ),
    ),
    WorksheetInfo(
      id = 13,
      title = "Multiply by 10 and 100",
      shortTag = "WS 13 • 30 items",
      world = LearningWorld.MULTIPLY,
      itemCount = 30,
      instructions = "Use the quick zero-affix rule to multiply by 10 and 100.",
      miniLesson = MiniLesson(
        heading = "Multiplying by 10 and 100",
        rules = listOf(
          "1) Copy the first factor.",
          "2) Affix 1 zero when multiplying by 10; affix 2 zeros when multiplying by 100.",
        ),
        exampleTitle = "Examples",
        exampleBody = "34 × 10 = 340\n265 × 100 = 26,500",
      ),
    ),
    WorksheetInfo(
      id = 14,
      title = "Multiplication Word Problems",
      shortTag = "WS 14 • 16 items",
      world = LearningWorld.MULTIPLY,
      itemCount = 16,
      instructions = "Solve real-life multiplication and two-step stories.",
      miniLesson = MiniLesson(
        heading = "Multiplication in Real Life",
        rules = listOf(
          "When items come in equal baskets, rows, shelves, or boxes, multiply!",
          "Watch out for extra additions or damaged/used items subtracted afterward.",
        ),
        exampleTitle = "Example",
        exampleBody = "8 baskets × 7 oranges = 56 oranges.",
      ),
    ),
    WorksheetInfo(
      id = 15,
      title = "Intro to Division & Parts",
      shortTag = "WS 15 • 40 items",
      world = LearningWorld.DIVIDE_GEO,
      itemCount = 40,
      instructions = "Group objects equally, identify Dividend/Divisor/Quotient, and solve basic division.",
      miniLesson = MiniLesson(
        heading = "Division & Its Parts",
        rules = listOf(
          "Division means separating things into equal groups.",
          "Dividend ÷ Divisor = Quotient (e.g. 8 ÷ 2 = 4).",
        ),
        exampleTitle = "Example: 12 ÷ 2 = 6",
        exampleBody = "Dividend = 12, Divisor = 2, Quotient = 6.",
      ),
    ),
    WorksheetInfo(
      id = 16,
      title = "Division Facts Practice",
      shortTag = "WS 16 • 45 items",
      world = LearningWorld.DIVIDE_GEO,
      itemCount = 45,
      instructions = "Practice dividing numbers quickly using multiplication facts.",
      miniLesson = MiniLesson(
        heading = "Think Multiplication!",
        rules = listOf(
          "To solve 32 ÷ 4, ask: 'What number times 4 equals 32?'",
          "Since 4 × 8 = 32, 32 ÷ 4 = 8!",
        ),
        exampleTitle = "Example",
        exampleBody = "48 ÷ 6 = 8 because 6 × 8 = 48.",
      ),
    ),
    WorksheetInfo(
      id = 17,
      title = "Worded Division Drills",
      shortTag = "WS 17 • 44 items",
      world = LearningWorld.DIVIDE_GEO,
      itemCount = 44,
      instructions = "Solve 'divided by' and 'Divide A by B' division sentences.",
      miniLesson = MiniLesson(
        heading = "Reading Division Phrases",
        rules = listOf(
          "'15 divided by 3' means 15 ÷ 3 = 5.",
          "'Divide 81 by 9' means 81 ÷ 9 = 9.",
        ),
        exampleTitle = "Example",
        exampleBody = "Divide 54 by 6 -> 54 ÷ 6 = 9.",
      ),
    ),
    WorksheetInfo(
      id = 18,
      title = "Long Division (1-Digit Divisor)",
      shortTag = "WS 18 • 15 items",
      world = LearningWorld.DIVIDE_GEO,
      itemCount = 15,
      instructions = "Divide 2- and 3-digit numbers by a 1-digit divisor, including remainders.",
      miniLesson = MiniLesson(
        heading = "Division with Remainders",
        rules = listOf(
          "If the first digit is smaller than the divisor, take the first two digits together.",
          "Divide, Multiply, Subtract, and Bring Down! Anything left over is the Remainder (R).",
        ),
        exampleTitle = "Examples",
        exampleBody = "28 ÷ 5 = 5 R 3 (since 5×5 = 25 and 28 - 25 = 3)\n248 ÷ 4 = 62",
      ),
    ),
    WorksheetInfo(
      id = 19,
      title = "Division by 2-Digit Divisor",
      shortTag = "WS 19 • 14 items",
      world = LearningWorld.DIVIDE_GEO,
      itemCount = 14,
      instructions = "Divide by 2-digit divisors and solve puto-seko & yema problems.",
      miniLesson = MiniLesson(
        heading = "Dividing by 2-Digit Divisors",
        rules = listOf(
          "Try multiplying the divisor by 1, 2, 3... to find the closest product not greater than the dividend.",
          "Subtract to find any remainder.",
        ),
        exampleTitle = "Example: 78 ÷ 36",
        exampleBody = "36 × 2 = 72 (closest to 78)\n78 - 72 = 6 -> 2 R 6",
      ),
    ),
    WorksheetInfo(
      id = 20,
      title = "Divide by 10 and 100",
      shortTag = "WS 20 • 20 items",
      world = LearningWorld.DIVIDE_GEO,
      itemCount = 20,
      instructions = "Find the quotient when dividing multiples of 10 and 100.",
      miniLesson = MiniLesson(
        heading = "Quick Division by 10 and 100",
        rules = listOf(
          "When dividing by 10, cancel one ending zero from the dividend.",
          "When dividing by 100, cancel two ending zeros from the dividend.",
        ),
        exampleTitle = "Examples",
        exampleBody = "300 ÷ 10 = 30\n800 ÷ 100 = 8",
      ),
    ),
    WorksheetInfo(
      id = 21,
      title = "Points, Lines, Segments & Rays",
      shortTag = "WS 21 • 30 items",
      world = LearningWorld.DIVIDE_GEO,
      itemCount = 30,
      instructions = "Identify geometric figures, find symbols in the diagram, and practice drawing.",
      miniLesson = MiniLesson(
        heading = "Basic Geometric Figures",
        rules = listOf(
          "Point: represented by a dot (e.g. Point A).",
          "Line: extends infinitely in both directions (2 arrowheads).",
          "Ray: has 1 endpoint and 1 arrowhead.",
          "Line Segment: has 2 endpoints.",
        ),
        exampleTitle = "Symbols",
        exampleBody = "Line QR (↔), Ray MN (→), Segment ST (—)",
      ),
    ),
    WorksheetInfo(
      id = 22,
      title = "Parallel, Intersecting & Perpendicular",
      shortTag = "WS 22 • 17 items",
      world = LearningWorld.DIVIDE_GEO,
      itemCount = 17,
      instructions = "Classify pairs of lines and name the lines in geometric figures.",
      miniLesson = MiniLesson(
        heading = "Kinds of Lines",
        rules = listOf(
          "Parallel Lines: never meet or intersect; always the same distance apart.",
          "Intersecting Lines: cross/meet at exactly one point.",
          "Perpendicular Lines: meet and form a 90° right angle.",
        ),
        exampleTitle = "Symbols",
        exampleBody = "AB || CD (parallel)\nJL ⊥ KM (perpendicular)",
      ),
    ),
    WorksheetInfo(
      id = 23,
      title = "Congruent Line Segments",
      shortTag = "WS 23 • 24 items",
      world = LearningWorld.DIVIDE_GEO,
      itemCount = 24,
      instructions = "Compare equal (congruent) line segments and measure with an interactive ruler.",
      miniLesson = MiniLesson(
        heading = "Congruent Line Segments",
        rules = listOf(
          "Congruent line segments have the exact same length.",
          "Opposite sides of a rectangle are congruent, and diagonals bisect into equal halves.",
        ),
        exampleTitle = "Example",
        exampleBody = "In rectangle ADEG, AD = EG (Equal), and DO = OG (Equal).",
      ),
    ),
    WorksheetInfo(
      id = 24,
      title = "Visualizing Numbers (Discs)",
      shortTag = "WS 24 • 48 items",
      world = LearningWorld.NUMBER_SENSE,
      itemCount = 48,
      instructions = "Count the place value discs (50,000 down to 1) and write the total number.",
      miniLesson = MiniLesson(
        heading = "Reading Number Discs",
        rules = listOf(
          "Group the discs by value: 50,000, 10,000, 1,000, 100, 10, and 1.",
          "Add the values for each place to get the full number!",
        ),
        exampleTitle = "Example",
        exampleBody = "Two 10,000 + one 1,000 + two 100 + two 10 = 21,220",
      ),
    ),
    WorksheetInfo(
      id = 25,
      title = "Build & Read Number Discs",
      shortTag = "WS 25 • 56 items",
      world = LearningWorld.NUMBER_SENSE,
      itemCount = 56,
      instructions = "Build 5-digit numbers using number discs (1–40) and read disc sets (41–56).",
      miniLesson = MiniLesson(
        heading = "Building Numbers with Discs",
        rules = listOf(
          "Each digit tells you how many discs of that place value you need.",
          "Tap +10,000, +1,000, +100, +10, and +1 discs to match the target!",
        ),
        exampleTitle = "Example: 12,345",
        exampleBody = "1×10,000 + 2×1,000 + 3×100 + 4×10 + 5×1",
      ),
    ),
    WorksheetInfo(
      id = 26,
      title = "Value & Place Value",
      shortTag = "WS 26 • 60 items",
      world = LearningWorld.NUMBER_SENSE,
      itemCount = 60,
      instructions = "Find both the Value and Place Value of the highlighted digit.",
      miniLesson = MiniLesson(
        heading = "Place Value vs. Value",
        rules = listOf(
          "Place Value is the position name: Ones, Tens, Hundreds, Thousands, Ten Thousands.",
          "Value is the digit multiplied by its place (e.g. 5 in Tens = 50).",
        ),
        exampleTitle = "Example: 5 in 29,151",
        exampleBody = "Place Value = Tens\nValue = 50",
      ),
    ),
    WorksheetInfo(
      id = 27,
      title = "Digit Value by Place",
      shortTag = "WS 27 • 46 items",
      world = LearningWorld.NUMBER_SENSE,
      itemCount = 46,
      instructions = "Give the value of the digit corresponding to the given place value.",
      miniLesson = MiniLesson(
        heading = "Finding Value from Place Name",
        rules = listOf(
          "Locate the digit at the asked place (ones, tens, hundreds, thousands, ten thousands).",
          "Multiply that digit by 1, 10, 100, 1,000, or 10,000.",
        ),
        exampleTitle = "Example: 64,259 (thousands)",
        exampleBody = "4 is in the thousands place -> Value = 4,000.",
      ),
    ),
    WorksheetInfo(
      id = 28,
      title = "Place Value Mastery",
      shortTag = "WS 28 • 50 items",
      world = LearningWorld.NUMBER_SENSE,
      itemCount = 50,
      instructions = "Give the value of the number corresponding to the indicated place value.",
      miniLesson = MiniLesson(
        heading = "Up to Hundred Thousands",
        rules = listOf(
          "Remember the places from right to left: Ones, Tens, Hundreds, Thousands, Ten Thousands, Hundred Thousands.",
        ),
        exampleTitle = "Example: 64,875 (ten thousands)",
        exampleBody = "6 is in ten thousands -> Value = 60,000.",
      ),
    ),
    WorksheetInfo(
      id = 29,
      title = "Numbers in Words & Digits",
      shortTag = "WS 29 • 35 items",
      world = LearningWorld.NUMBER_SENSE,
      itemCount = 35,
      instructions = "Write numerals in words (1–15) and worded numbers in digits (16–35).",
      miniLesson = MiniLesson(
        heading = "Writing Numbers in Words",
        rules = listOf(
          "Use a comma between thousands and units periods (e.g. 97,304).",
          "Use a hyphen in two-digit words like 'twenty-one' or 'ninety-seven'.",
          "Do not write 'and' between hundreds and tens!",
        ),
        exampleTitle = "Example",
        exampleBody = "97,304 -> ninety-seven thousand, three hundred four",
      ),
    ),
    WorksheetInfo(
      id = 30,
      title = "Compare Whole Numbers",
      shortTag = "WS 30 • 40 items",
      world = LearningWorld.NUMBER_SENSE,
      itemCount = 40,
      instructions = "Compare numbers given in words and digits using >, <, or =.",
      miniLesson = MiniLesson(
        heading = "Comparing Big Numbers",
        rules = listOf(
          "First read the worded number into digits carefully.",
          "Compare from the leftmost (highest) place value digit by digit.",
        ),
        exampleTitle = "Example",
        exampleBody = "forty-two thousand, three hundred (42,300) = 42,300",
      ),
    ),
    WorksheetInfo(
      id = 31,
      title = "Rounding Off Numbers",
      shortTag = "WS 31 • 50 items",
      world = LearningWorld.NUMBER_SENSE,
      itemCount = 50,
      instructions = "Round each number to the nearest thousands or ten thousands.",
      miniLesson = MiniLesson(
        heading = "Rounding Rules",
        rules = listOf(
          "Find the rounding digit, then look one digit to its right.",
          "If the right digit is 5, 6, 7, 8, or 9 -> round UP (+1 to rounding digit).",
          "If it is 0, 1, 2, 3, or 4 -> round DOWN (keep rounding digit). Replace right digits with 0s!",
        ),
        exampleTitle = "Examples",
        exampleBody = "86,854 (thousands) -> 87,000\n78,949 (ten thousands) -> 80,000",
      ),
    ),
    WorksheetInfo(
      id = 32,
      title = "Rounding Numbers Extended",
      shortTag = "WS 32 • 75 items",
      world = LearningWorld.NUMBER_SENSE,
      itemCount = 75,
      instructions = "Master rounding 5-digit numbers to thousands and ten thousands.",
      miniLesson = MiniLesson(
        heading = "Rounding Practice",
        rules = listOf(
          "5 or more -> raise the score! 4 or less -> let it rest!",
          "All digits after the rounded place become zeros.",
        ),
        exampleTitle = "Example",
        exampleBody = "37,684 (thousands) -> look at 6 -> 38,000",
      ),
    ),
    WorksheetInfo(
      id = 33,
      title = "Ordering Numbers",
      shortTag = "WS 33 • 52 items",
      world = LearningWorld.NUMBER_SENSE,
      itemCount = 52,
      instructions = "Arrange 5 numbers in Increasing (least to greatest) or Decreasing (greatest to least) order.",
      miniLesson = MiniLesson(
        heading = "Increasing & Decreasing Order",
        rules = listOf(
          "Increasing = Smallest to Largest.",
          "Decreasing = Largest to Smallest.",
          "Compare how many digits first, then compare from left to right!",
        ),
        exampleTitle = "Example (Increasing)",
        exampleBody = "9,876 < 12,890 < 33,111 < 45,267 < 78,345",
      ),
    ),
    WorksheetInfo(
      id = 34,
      title = "Ordering Numbers Mastery",
      shortTag = "WS 34 • 26 items",
      world = LearningWorld.NUMBER_SENSE,
      itemCount = 26,
      instructions = "Final challenge: order 5-digit numbers in increasing and decreasing order.",
      miniLesson = MiniLesson(
        heading = "Final Ordering Challenge",
        rules = listOf(
          "Check the ten-thousands digit first, then thousands, hundreds, tens, and ones.",
        ),
        exampleTitle = "Tip",
        exampleBody = "4-digit numbers are always smaller than 5-digit numbers!",
      ),
    ),
  )

  fun getById(id: Int): WorksheetInfo = allWorksheets.first { it.id == id }

  fun getWorksheets(grade: com.example.model.AralGrade): List<WorksheetInfo> = when (grade) {
    com.example.model.AralGrade.GRADE_2 -> Grade2Catalog.worksheets
    com.example.model.AralGrade.GRADE_3 -> Grade3Catalog.worksheets
    com.example.model.AralGrade.GRADE_4 -> allWorksheets
    com.example.model.AralGrade.GRADE_5 -> Grade5Catalog.worksheets
    com.example.model.AralGrade.GRADE_6 -> Grade6Catalog.worksheets
  }

  fun getById(id: Int, grade: com.example.model.AralGrade): WorksheetInfo =
    getWorksheets(grade).first { it.id == id }
}
