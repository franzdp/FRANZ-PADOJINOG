package com.example.model

import androidx.compose.ui.graphics.Color

enum class AralGrade(
  val gradeNumber: Int,
  val shortLabel: String,
  val displayTitle: String,
  val sheetTabName: String,
  val totalWorksheets: Int,
  val sheetColumnSlots: Int = totalWorksheets,
  val legacyTabNames: List<String> = emptyList(),
) {
  GRADE_2(2, "Grade 2", "ARAL Math Grade 2", "Grade 2", totalWorksheets = 36, sheetColumnSlots = 36),
  GRADE_3(3, "Grade 3", "ARAL Math Grade 3", "Grade 3", totalWorksheets = 24, sheetColumnSlots = 24),
  GRADE_4(4, "Grade 4", "ARAL Math Grade 4", "Grade 4", totalWorksheets = 34, sheetColumnSlots = 34, legacyTabNames = listOf("LearnerScores")),
  GRADE_5(5, "Grade 5", "ARAL Math Grade 5", "Grade 5", totalWorksheets = 32, sheetColumnSlots = 32, legacyTabNames = listOf("Grade5_Scores")),
  GRADE_6(6, "Grade 6", "ARAL Math Grade 6", "Grade 6", totalWorksheets = 35, sheetColumnSlots = 35, legacyTabNames = listOf("Grade6_Scores")),
  ;

  val hasActivities: Boolean get() = totalWorksheets > 0

  companion object {
    fun fromGradeLevelText(raw: String): AralGrade {
      val lower = raw.lowercase()
      return when {
        "6" in lower -> GRADE_6
        "5" in lower -> GRADE_5
        "4" in lower -> GRADE_4
        "3" in lower -> GRADE_3
        "2" in lower -> GRADE_2
        else -> GRADE_4
      }
    }
  }
}

enum class LearningWorld(
  val title: String,
  val subtitle: String,
  val accentColorHex: Long,
  val containerColorHex: Long,
  val grade: AralGrade = AralGrade.GRADE_4,
) {
  // Grade 2 Worlds
  G2_COUNT_WORDS("Counting & Words", "Count objects, match & number words", 0xFF1565C0, 0xFFD6E9FF, AralGrade.GRADE_2),
  G2_COMPARE_ORDER("Compare, Order & Ordinals", "Compare, order sets, grids & ordinals", 0xFF7B1FA2, 0xFFF3D9FF, AralGrade.GRADE_2),
  G2_FRACTIONS("Fractions Fun", "Equal parts, halves, fourths & shading", 0xFFEF6C00, 0xFFFFE5CC, AralGrade.GRADE_2),
  G2_ADD_SUB("Add & Subtract", "Addition & subtraction up to 2 digits", 0xFF2E7D32, 0xFFD7F5DC, AralGrade.GRADE_2),
  G2_SHAPES_CALENDAR("Shapes, Patterns & Calendar", "Basic shapes, patterns, days & months", 0xFF00796B, 0xFFD0F2EE, AralGrade.GRADE_2),

  // Grade 3 Worlds
  G3_NUMBERS_COMPARE("Place Value & Comparing", "3-digit place value, words, expanded & order", 0xFF1565C0, 0xFFD6E9FF, AralGrade.GRADE_3),
  G3_PATTERNS_SKIP("Patterns & Skip Counting", "1- & 2-attribute patterns & skip counting", 0xFF7B1FA2, 0xFFF3D9FF, AralGrade.GRADE_3),
  G3_FRACTIONS_ADD_SUB("Fractions, Add & Subtract", "Unit/similar fractions & 3-digit add/sub", 0xFFEF6C00, 0xFFFFE5CC, AralGrade.GRADE_3),
  G3_MULT_DIV("Multiply & Divide", "Number lines, properties, facts & stories", 0xFF2E7D32, 0xFFD7F5DC, AralGrade.GRADE_3),
  G3_SHAPES_LINES("Circles, Shapes & Lines", "Halves/quarters, shapes, patterns & surfaces", 0xFF00796B, 0xFFD0F2EE, AralGrade.GRADE_3),

  // Grade 4 Worlds (unchanged)
  FRACTIONS("Fractions Fun", "Compare, order & read fractions", 0xFFEF6C00, 0xFFFFE5CC, AralGrade.GRADE_4),
  NUMBER_SENSE("Number Sense", "Odd/even, discs, place value & rounding", 0xFF1565C0, 0xFFD6E9FF, AralGrade.GRADE_4),
  PATTERNS("Pattern Detectives", "Find missing numbers & shapes", 0xFF7B1FA2, 0xFFF3D9FF, AralGrade.GRADE_4),
  ADD_SUB("Add & Subtract", "Multi-digit sums, differences & stories", 0xFFD97706, 0xFFFFECB3, AralGrade.GRADE_4),
  MULTIPLY("Multiply Mastery", "2-digit × 1/2-digit, ×10/100 & stories", 0xFF2E7D32, 0xFFD7F5DC, AralGrade.GRADE_4),
  DIVIDE_GEO("Divide & Geometry", "Division facts, remainders, lines & shapes", 0xFF00796B, 0xFFD0F2EE, AralGrade.GRADE_4),

  // Grade 5 Worlds
  G5_NUMBERS("Numbers & Rounding", "Discs, place value, words, compare & round", 0xFF1565C0, 0xFFD6E9FF, AralGrade.GRADE_5),
  G5_MULTIPLY("Multiply & Estimate", "Multi-digit multiplication, estimation & mental math", 0xFF2E7D32, 0xFFD7F5DC, AralGrade.GRADE_5),
  G5_DIVIDE("Divide & Estimate", "Long division, ÷10/100/1000 & compatible quotients", 0xFF00796B, 0xFFD0F2EE, AralGrade.GRADE_5),
  G5_PROBLEM_OPS("Word Problems & PMDAS", "Multi-step stories & order of operations", 0xFFD97706, 0xFFFFECB3, AralGrade.GRADE_5),
  G5_ANGLES("Angles Explorer", "Naming angles & acute/right/obtuse figures", 0xFF7B1FA2, 0xFFF3D9FF, AralGrade.GRADE_5),

  // Grade 6 Worlds
  G6_DIVISIBILITY("Divisibility & PMDAS", "Divisibility rules 2–12 & parentheses", 0xFF1565C0, 0xFFD6E9FF, AralGrade.GRADE_6),
  G6_GCF_LCM("GCF & LCM", "Continuous division, factor checks & stories", 0xFF00796B, 0xFFD0F2EE, AralGrade.GRADE_6),
  G6_FRACTIONS("Fractions Operations", "Multiply & divide fractions, mixed numbers & models", 0xFFEF6C00, 0xFFFFE5CC, AralGrade.GRADE_6),
  G6_GEO_TIME("Polygons & Time", "Polygons 4–12, regular vs irregular & 24-hr clocks", 0xFF7B1FA2, 0xFFF3D9FF, AralGrade.GRADE_6),
  G6_MEASURE("Area, Volume & Temp", "Triangle, trapezoid, prism, cube & thermometers", 0xFF2E7D32, 0xFFD7F5DC, AralGrade.GRADE_6),
  ;

  val accentColor: Color get() = Color(accentColorHex)
  val containerColor: Color get() = Color(containerColorHex)

  companion object {
    fun forGrade(grade: AralGrade): List<LearningWorld> = entries.filter { it.grade == grade }
  }
}

data class MiniLesson(
  val heading: String,
  val rules: List<String>,
  val exampleTitle: String,
  val exampleBody: String,
)

data class WorksheetInfo(
  val id: Int,
  val title: String,
  val shortTag: String,
  val world: LearningWorld,
  val itemCount: Int,
  val instructions: String,
  val miniLesson: MiniLesson? = null,
  val grade: AralGrade = AralGrade.GRADE_4,
)

data class FractionItem(val num: Int, val den: Int) {
  val label: String get() = "$num/$den"
}

enum class ShadedFigureType { DIAMOND_4, TRIANGLES_3, RECT_4, HEXAGON_6, CIRCLE_8 }

enum class PictureSceneType {
  HALLOWEEN_GROUP,
  TENNIS_RACKETS,
  BASKETBALLS,
  OWLS_READING,
  PAINT_BRUSHES,
  SMALL_PENCILS,
  SHAPES_GRID_A,
  SHAPES_GRID_B,
  PETS_GROUP,
  TARSIERS_GROUP,
  DRESSES_RIBBON,
  BALLOONS_LIGHT,
  WHITE_CAPS,
  HEART_SHAPES,
  UNLOCKED_PADLOCKS,
}

enum class GroupingSceneType {
  COLOR_PENCILS,
  DONUTS,
  FLOWERS,
  BALLOONS,
  CANDIES,
  BROOMS,
}

enum class PatternTokenKind {
  TEXT,
  FILLED_TRIANGLE,
  OUTLINE_TRIANGLE,
  FILLED_SQUARE,
  OUTLINE_SQUARE,
  FILLED_STAR,
  OUTLINE_STAR,
  APPLE,
  COOKIE,
  FILLED_HEART,
  OUTLINE_HEART,
  FLOWER,
  LEAF,
  OUTLINE_CIRCLE,
  BLANK,
}

data class PatternToken(
  val kind: PatternTokenKind,
  val text: String = "",
)

enum class ArithmeticOp(val symbol: String) {
  ADD("+"),
  SUBTRACT("-"),
  MULTIPLY("×"),
}

enum class DivisionPartType { DIVIDEND, DIVISOR, QUOTIENT }

enum class GeoFigureType {
  POINT_D,
  RAY_RIGHT,
  SEGMENT_HORIZ,
  LINE_HORIZ,
  RAY_DOWN_LEFT,
  SEGMENT_TILTED,
  POINT_A,
  LINE_DIAG,
  POINT_F,
  RAY_MN,
  POINT_P,
  LINE_QR,
  SEGMENT_ST,
  PAIR_PARALLEL_TILT_DOWN,
  PAIR_NOT_PARALLEL_1,
  PAIR_NOT_PARALLEL_2,
  PAIR_PARALLEL_TILT_UP,
  PAIR_INTERSECT_X,
  PAIR_PERP_CROSS,
  PAIR_PARALLEL_HORIZ,
  PAIR_PARALLEL_VERT,
  PAIR_INTERSECT_SLANT,
  PAIR_PERP_T,
}

enum class CongruentSubType {
  ADEG_EQUAL_OR_NOT,
  LNRQ_EQUAL_PAIRS,
  TUV_ALTITUDE_EQUAL,
  TUV_EQUAL_SEGMENTS,
  KLNM_CONGRUENT,
  COMPARE_THREE_SEGMENTS,
}

data class DiscCounts(
  val fiftyThousands: Int = 0,
  val tenThousands: Int = 0,
  val thousands: Int = 0,
  val hundreds: Int = 0,
  val tens: Int = 0,
  val ones: Int = 0,
) {
  val total: Int
    get() = fiftyThousands * 50000 +
      tenThousands * 10000 +
      thousands * 1000 +
      hundreds * 100 +
      tens * 10 +
      ones
}

sealed interface MathProblem {
  val itemNumber: Int
  val hint: String

  data class OddEvenProblem(
    override val itemNumber: Int,
    val number: Int,
    val isEven: Boolean,
    override val hint: String = "Look at the last digit: 0, 2, 4, 6, 8 are EVEN; 1, 3, 5, 7, 9 are ODD.",
  ) : MathProblem

  data class CompareFractionsProblem(
    override val itemNumber: Int,
    val leftLabel: String,
    val leftNum: Int,
    val leftDen: Int,
    val rightLabel: String,
    val rightNum: Int,
    val rightDen: Int,
    val answerSymbol: String,
    val isSimilar: Boolean,
    override val hint: String,
  ) : MathProblem

  data class OrderFractionsProblem(
    override val itemNumber: Int,
    val fractions: List<FractionItem>,
    val ascending: Boolean,
    val expectedOrder: List<FractionItem>,
    override val hint: String,
  ) : MathProblem

  data class FractionPartProblem(
    override val itemNumber: Int,
    val num: Int,
    val den: Int,
    val askNumerator: Boolean,
    override val hint: String,
  ) : MathProblem

  data class ShadedFigureFractionProblem(
    override val itemNumber: Int,
    val figureType: ShadedFigureType,
    val num: Int,
    val den: Int,
    val prompt: String,
    val askBreakdown: Boolean,
    override val hint: String,
  ) : MathProblem

  data class PictureFractionProblem(
    override val itemNumber: Int,
    val scene: PictureSceneType,
    val prompt: String,
    val num: Int,
    val den: Int,
    override val hint: String,
  ) : MathProblem

  data class FractionWordsProblem(
    override val itemNumber: Int,
    val num: Int,
    val den: Int,
    val words: String,
    val toWords: Boolean,
    val sentenceWithHighlight: String? = null,
    override val hint: String,
  ) : MathProblem

  data class FractionBlankProblem(
    override val itemNumber: Int,
    val num: Int,
    val den: Int,
    val askNum: Boolean,
    val askDen: Boolean,
    override val hint: String,
  ) : MathProblem

  data class PatternProblem(
    override val itemNumber: Int,
    val tokens: List<PatternToken>,
    val expectedAnswers: List<String>,
    val choices: List<String>? = null,
    val isShapeOrLetterChoice: Boolean = false,
    override val hint: String,
  ) : MathProblem

  data class ArithmeticProblem(
    override val itemNumber: Int,
    val operands: List<Int>,
    val op: ArithmeticOp,
    val answer: Int,
    val promptText: String? = null,
    val unitLabel: String? = null,
    val showVerticalColumns: Boolean = true,
    val highlightDigitsMode: Boolean = false,
    override val hint: String,
  ) : MathProblem

  data class GroupingDivisionProblem(
    override val itemNumber: Int,
    val scene: GroupingSceneType,
    val total: Int,
    val groupSize: Int,
    val answerGroups: Int,
    val prompt: String,
    override val hint: String,
  ) : MathProblem

  data class DivisionPartProblem(
    override val itemNumber: Int,
    val dividend: Int,
    val divisor: Int,
    val quotient: Int,
    val askPart: DivisionPartType,
    val prompt: String,
    override val hint: String,
  ) : MathProblem

  data class DivisionProblem(
    override val itemNumber: Int,
    val dividend: Int,
    val divisor: Int,
    val quotient: Int,
    val remainder: Int = 0,
    val promptText: String? = null,
    val unitLabel: String? = null,
    val showBracket: Boolean = false,
    override val hint: String,
  ) : MathProblem

  data class GeometryIdentifyProblem(
    override val itemNumber: Int,
    val prompt: String,
    val figure: GeoFigureType?,
    val options: List<String>,
    val answer: String,
    override val hint: String,
  ) : MathProblem

  data class GeometryBoxFindProblem(
    override val itemNumber: Int,
    val categoryLabel: String,
    val validAnswers: List<String>,
    override val hint: String,
  ) : MathProblem

  data class GeometryDrawProblem(
    override val itemNumber: Int,
    val prompt: String,
    val guideHint: String,
    val sampleSegmentUnits: Int? = null,
    override val hint: String,
  ) : MathProblem

  data class GeometryNameLinesProblem(
    override val itemNumber: Int,
    val diagramId: Int,
    val prompt: String,
    val validAnswers: List<String>,
    override val hint: String,
  ) : MathProblem

  data class CongruentSegmentsProblem(
    override val itemNumber: Int,
    val subType: CongruentSubType,
    val prompt: String,
    val options: List<String>,
    val validAnswers: List<String>,
    val setIndex: Int = 0,
    override val hint: String,
  ) : MathProblem

  data class NumberDiscsReadProblem(
    override val itemNumber: Int,
    val counts: DiscCounts,
    val answer: Int,
    override val hint: String,
  ) : MathProblem

  data class NumberDiscsBuildProblem(
    override val itemNumber: Int,
    val targetNumber: Int,
    override val hint: String = "Tap the 10,000, 1,000, 100, 10, and 1 discs to match the digits of the number.",
  ) : MathProblem

  data class PlaceValueProblem(
    override val itemNumber: Int,
    val number: Int,
    val targetDigit: Int,
    val expectedValue: Int,
    val expectedPlaceValue: String,
    val askPlaceValue: Boolean,
    val askValue: Boolean,
    val promptText: String,
    override val hint: String,
  ) : MathProblem

  data class NumberWordsProblem(
    override val itemNumber: Int,
    val number: Int,
    val words: String,
    val toWords: Boolean,
    override val hint: String,
  ) : MathProblem

  data class CompareWholeNumbersProblem(
    override val itemNumber: Int,
    val leftDisplay: String,
    val leftValue: Int,
    val rightDisplay: String,
    val rightValue: Int,
    val leftIsWords: Boolean,
    val answerSymbol: String,
    override val hint: String,
  ) : MathProblem

  data class RoundingProblem(
    override val itemNumber: Int,
    val number: Int,
    val targetPlace: String,
    val answer: Int,
    override val hint: String,
  ) : MathProblem

  data class OrderNumbersProblem(
    override val itemNumber: Int,
    val numbers: List<Int>,
    val increasing: Boolean,
    val expectedOrder: List<Int>,
    override val hint: String,
  ) : MathProblem

  // --- Grade 5 Additional Problem Models ---

  data class NumericFieldSpec(
    val label: String,
    val expectedAnswer: Double,
    val unitLabel: String? = null,
    val allowDecimal: Boolean = false,
    val alternateAnswers: List<Double> = emptyList(),
  )

  data class EstimateOpProblem(
    override val itemNumber: Int,
    val factors: List<Int>,
    val roundedFactors: List<Int>,
    val expectedEstimate: Long,
    val promptText: String? = null,
    val choiceOptions: List<String>? = null,
    val correctChoice: String? = null,
    override val hint: String,
  ) : MathProblem

  data class MentalMultiplyProblem(
    override val itemNumber: Int,
    val factorA: Int,
    val factorB: Int,
    val answer: Int,
    val recommendedStrategy: String,
    val strategyChoices: List<String> = listOf(
      "Break apart tens & ones",
      "Multiply by 10 / 11 shortcut",
      "Double & halve / skip count",
      "Multiply by 10 then subtract",
    ),
    override val hint: String,
  ) : MathProblem

  data class CompatibleEstimateProblem(
    override val itemNumber: Int,
    val dividend: Int,
    val divisor: Int,
    val promptText: String? = null,
    val compatibleOptions: List<String>? = null,
    val correctCompatibleIndex: Int? = null,
    val estimatedOptions: List<Int>? = null,
    val acceptedQuotients: List<Int>,
    val primaryEstimate: Int = acceptedQuotients.first(),
    val exampleCompatibleExpr: String = "",
    override val hint: String,
  ) : MathProblem

  data class WordProblemMultiFieldProblem(
    override val itemNumber: Int,
    val promptText: String,
    val fields: List<NumericFieldSpec>,
    override val hint: String,
  ) : MathProblem

  data class OrderOfOpsProblem(
    override val itemNumber: Int,
    val expression: String,
    val promptText: String? = null,
    val expectedValue: Double,
    val expectedDisplay: String,
    override val hint: String,
  ) : MathProblem

  data class AngleNameProblem(
    override val itemNumber: Int,
    val diagramId: Int,
    val prompt: String,
    val validAnswers: List<String>,
    val exampleAnswer: String,
    val highlightIndex: Int = 0,
    override val hint: String,
  ) : MathProblem

  data class AngleKindProblem(
    override val itemNumber: Int,
    val diagramId: Int,
    val prompt: String,
    val isSingleClassify: Boolean,
    val options: List<String>,
    val validAnswers: List<String>,
    override val hint: String,
  ) : MathProblem
}
