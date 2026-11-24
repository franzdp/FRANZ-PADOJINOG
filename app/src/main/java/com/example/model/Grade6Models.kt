package com.example.model

enum class MeasureShapeKind {
  TABLE_ONLY,
  TRIANGLE,
  TRIANGLE_RIGHT,
  TRAPEZOID,
  PARALLELOGRAM,
  RECT_PRISM,
  CUBE,
}

enum class ThermometerMode {
  MERCURY_READ,
  MERCURY_SET,
  DIGITAL_READ,
  DIGITAL_SET,
}

data class DivisibilityCheckProblem(
  override val itemNumber: Int,
  val number: Int,
  val divisorsToCheck: List<Int>,
  val validDivisors: List<Int>,
  override val hint: String,
) : MathProblem

data class SmallestDigitDivisibilityProblem(
  override val itemNumber: Int,
  val templatePattern: String,
  val divisor: Int,
  val expectedSmallestDigit: Int,
  override val hint: String,
) : MathProblem

data class DivisibilityRuleJudgeProblem(
  override val itemNumber: Int,
  val firstNumber: Int,
  val secondNumber: Int,
  val isYesNoStep: Boolean,
  val choiceOptions: List<String>,
  val correctChoice: String,
  override val hint: String,
) : MathProblem

data class GcfLcmProblem(
  override val itemNumber: Int,
  val numbers: List<Int>,
  val askGcf: Boolean,
  val askLcm: Boolean,
  val givenGcf: Int? = null,
  val showFactorShortcut: Boolean = false,
  val expectedGcf: Int,
  val expectedLcm: Int,
  val promptText: String? = null,
  override val hint: String,
) : MathProblem

data class FractionOpProblem(
  override val itemNumber: Int,
  val expressionDisplay: String,
  val expectedNum: Int,
  val expectedDen: Int,
  val promptText: String? = null,
  val isReciprocal: Boolean = false,
  val showAreaModel: Boolean = false,
  val modelNumA: Int = 0,
  val modelDenA: Int = 0,
  val modelNumB: Int = 0,
  val modelDenB: Int = 0,
  val isDivisionModel: Boolean = false,
  val alternateDecimals: List<Double> = emptyList(),
  override val hint: String,
) : MathProblem

data class PolygonTableProblem(
  override val itemNumber: Int,
  val polygonSides: Int,
  val givenName: String?,
  val givenSides: Int?,
  val showFigure: Boolean,
  val expectedName: String,
  val expectedSides: Int,
  val choiceNames: List<String>,
  override val hint: String,
) : MathProblem

data class PolygonRegularProblem(
  override val itemNumber: Int,
  val polygonId: Int,
  val isRegular: Boolean,
  override val hint: String,
) : MathProblem

data class TimeConvertProblem(
  override val itemNumber: Int,
  val prompt: String,
  val sourceDisplay: String,
  val to24Hour: Boolean,
  val clockHour: Int? = null,
  val clockMinute: Int? = null,
  val clockIsPm: Boolean = false,
  val validAnswers: List<String>,
  val displayAnswer: String,
  override val hint: String,
) : MathProblem

data class GeometryMeasureProblem(
  override val itemNumber: Int,
  val shapeKind: MeasureShapeKind,
  val dimLabels: List<String>,
  val promptText: String,
  val fields: List<MathProblem.NumericFieldSpec>,
  val interactiveLabelCheck: Boolean = false,
  override val hint: String,
) : MathProblem

data class ThermometerProblem(
  override val itemNumber: Int,
  val mode: ThermometerMode,
  val targetCelsius: Double,
  override val hint: String,
) : MathProblem
