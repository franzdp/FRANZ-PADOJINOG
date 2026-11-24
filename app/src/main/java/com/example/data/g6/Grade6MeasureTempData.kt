package com.example.data.g6

import com.example.model.GeometryMeasureProblem
import com.example.model.MathProblem
import com.example.model.MathProblem.NumericFieldSpec
import com.example.model.MeasureShapeKind
import com.example.model.ThermometerMode
import com.example.model.ThermometerProblem

object Grade6MeasureTempData {

  private fun triFig(
    item: Int,
    b: Int,
    h: Int,
    unit: String,
    isRight: Boolean = false,
  ): GeometryMeasureProblem {
    val area = (b * h) / 2.0
    val allowDec = area % 1.0 != 0.0
    return GeometryMeasureProblem(
      itemNumber = item,
      shapeKind = if (isRight) MeasureShapeKind.TRIANGLE_RIGHT else MeasureShapeKind.TRIANGLE,
      dimLabels = listOf("$b $unit", "$h $unit"),
      promptText = "Find the area of the triangle (base = $b $unit, height = $h $unit):",
      fields = listOf(NumericFieldSpec("Area (sq $unit)", area, "sq $unit", allowDec)),
      hint = "Area = (base × height) ÷ 2 = ($b × $h) ÷ 2.",
    )
  }

  private fun triTable(item: Int, b: Int, h: Int): GeometryMeasureProblem {
    val area = (b * h) / 2.0
    val allowDec = area % 1.0 != 0.0
    return GeometryMeasureProblem(
      itemNumber = item,
      shapeKind = MeasureShapeKind.TABLE_ONLY,
      dimLabels = listOf("$b in", "$h in"),
      promptText = "Base = $b in, Height = $h in\nCalculate the area of the triangle:",
      fields = listOf(NumericFieldSpec("Area (sq in)", area, "sq in", allowDec)),
      hint = "Area = ($b × $h) ÷ 2.",
    )
  }

  private fun trap(
    item: Int,
    b1: Int,
    b2: Int,
    h: Int,
    unit: String,
    hasFig: Boolean,
  ): GeometryMeasureProblem {
    val area = ((b1 + b2) * h) / 2.0
    val allowDec = area % 1.0 != 0.0
    val unitSuffix = if (unit.isNotBlank()) " $unit" else ""
    return GeometryMeasureProblem(
      itemNumber = item,
      shapeKind = if (hasFig) MeasureShapeKind.TRAPEZOID else MeasureShapeKind.TABLE_ONLY,
      dimLabels = listOf("$b1$unitSuffix", "$b2$unitSuffix", "$h$unitSuffix"),
      promptText = "Find the area of the trapezoid (Upper base = $b1$unitSuffix, Lower base = $b2$unitSuffix, Height = $h$unitSuffix):",
      fields = listOf(NumericFieldSpec("Area", area, if (unit.isNotBlank()) "sq $unit" else "sq units", allowDec)),
      hint = "Area = ((b₁ + b₂) × h) ÷ 2 = (($b1 + $b2) × $h) ÷ 2.",
    )
  }

  private fun para(
    item: Int,
    b: Int,
    h: Int,
    unit: String,
    hasFig: Boolean,
  ): GeometryMeasureProblem {
    val area = (b * h).toDouble()
    val unitSuffix = if (unit.isNotBlank()) " $unit" else ""
    return GeometryMeasureProblem(
      itemNumber = item,
      shapeKind = if (hasFig) MeasureShapeKind.PARALLELOGRAM else MeasureShapeKind.TABLE_ONLY,
      dimLabels = listOf("$b$unitSuffix", "$h$unitSuffix"),
      promptText = "Find the area of the parallelogram (Base = $b$unitSuffix, Height = $h$unitSuffix):",
      fields = listOf(NumericFieldSpec("Area", area, if (unit.isNotBlank()) "sq $unit" else "sq units")),
      hint = "Area = base × height = $b × $h.",
    )
  }

  private fun prismItem(
    item: Int,
    l: Int,
    w: Int,
    h: Int,
    unit: String,
    label: String,
    expected: Int,
    unitOut: String,
  ) = GeometryMeasureProblem(
    itemNumber = item,
    shapeKind = MeasureShapeKind.RECT_PRISM,
    dimLabels = listOf("$l$unit", "$w$unit", "$h$unit"),
    promptText = "Look at the rectangular prism ($l$unit × $w$unit × $h$unit). Identify its $label:",
    fields = listOf(NumericFieldSpec(label.replaceFirstChar { it.uppercase() }, expected.toDouble(), unitOut)),
    hint = if (label == "volume") "V = l × w × h = $l × $w × $h." else "Read the $label labeled on the prism.",
  )

  private fun prismTable(item: Int, l: Int, w: Int, h: Int, unit: String) =
    GeometryMeasureProblem(
      itemNumber = item,
      shapeKind = MeasureShapeKind.TABLE_ONLY,
      dimLabels = listOf("$l $unit", "$w $unit", "$h $unit"),
      promptText = "Length = $l $unit, Width = $w $unit, Height = $h $unit\nSolve for the volume:",
      fields = listOf(NumericFieldSpec("Volume ($unit³)", (l * w * h).toDouble(), "$unit³")),
      hint = "V = $l × $w × $h.",
    )

  private fun cubeFig(item: Int, s: Int, unit: String) =
    GeometryMeasureProblem(
      itemNumber = item,
      shapeKind = MeasureShapeKind.CUBE,
      dimLabels = listOf("$s $unit"),
      promptText = "Solve for the volume of the cube (side = $s $unit):",
      fields = listOf(NumericFieldSpec("Volume ($unit³)", (s.toDouble() * s * s), "$unit³")),
      hint = "V = s × s × s = $s × $s × $s.",
    )

  private fun cubeTable(item: Int, s: Int, unit: String) =
    GeometryMeasureProblem(
      itemNumber = item,
      shapeKind = MeasureShapeKind.TABLE_ONLY,
      dimLabels = listOf("s = $s $unit"),
      promptText = "Side s = $s $unit\nSolve for the volume of the cube:",
      fields = listOf(NumericFieldSpec("Volume ($unit³)", (s.toDouble() * s * s), "$unit³")),
      hint = "V = $s³ = $s × $s × $s.",
    )

  val ws30: List<MathProblem> = listOf(
    triFig(1, 6, 4, "ft"), triFig(2, 54, 45, "cm"), triFig(3, 72, 64, "ft"), triFig(4, 6, 2, "cm"),
    triTable(5, 12, 6), triTable(6, 8, 8), triTable(7, 19, 4), triTable(8, 22, 10),
    triTable(9, 31, 24), triTable(10, 35, 12), triTable(11, 22, 6), triTable(12, 42, 14),
    triFig(13, 36, 36, "m"), triFig(14, 60, 24, "m"), triFig(15, 12, 16, "m"), triFig(16, 80, 10, "m"),
    triFig(17, 27, 63, "m", isRight = true), triFig(18, 32, 48, "ft", isRight = true),
    triTable(19, 18, 10), triTable(20, 40, 36), triTable(21, 32, 16), triTable(22, 17, 5),
    triTable(23, 14, 9), triTable(24, 35, 21), triTable(25, 42, 23), triTable(26, 53, 33),
  )

  val ws31: List<MathProblem> = listOf(
    trap(1, 14, 16, 8, "", false), trap(2, 7, 9, 5, "", false), trap(3, 24, 63, 35, "", false),
    trap(4, 150, 180, 17, "", false), trap(5, 120, 135, 55, "", false),
    trap(6, 8, 16, 8, "cm", true), trap(7, 25, 43, 19, "cm", true),
    trap(8, 49, 27, 16, "m", true), trap(9, 57, 38, 58, "m", true),
    trap(10, 44, 57, 44, "ft", true), trap(11, 150, 330, 90, "cm", true),
    trap(12, 80, 160, 60, "cm", true), trap(13, 84, 76, 55, "m", true),
    trap(14, 140, 210, 90, "ft", true), trap(15, 570, 283, 38, "m", true),
    // 16-30 Parallelogram
    para(16, 200, 120, "cm", true), para(17, 20, 12, "cm", true),
    para(18, 160, 120, "m", true), para(19, 150, 10, "m", true),
    para(20, 15, 9, "cm", true), para(21, 115, 94, "cm", true),
    para(22, 10, 15, "cm", true), para(23, 16, 20, "m", true),
    para(24, 164, 88, "m", true), para(25, 16, 8, "m", true),
    para(26, 40, 60, "", false), para(27, 65, 34, "", false),
    para(28, 220, 46, "", false), para(29, 289, 92, "", false),
    para(30, 125, 59, "", false),
  )

  val ws32: List<MathProblem> = listOf(
    prismItem(1, 20, 5, 15, "mm", "length", 20, "mm"),
    prismItem(2, 20, 5, 15, "mm", "width", 5, "mm"),
    prismItem(3, 20, 5, 15, "mm", "height", 15, "mm"),
    prismItem(4, 20, 5, 15, "mm", "volume", 1500, "mm³"),
    prismItem(5, 40, 10, 30, "cm", "length", 40, "cm"),
    prismItem(6, 40, 10, 30, "cm", "width", 10, "cm"),
    prismItem(7, 40, 10, 30, "cm", "height", 30, "cm"),
    prismItem(8, 40, 10, 30, "cm", "volume", 12000, "cm³"),
    prismItem(9, 50, 30, 20, "m", "length", 50, "m"),
    prismItem(10, 50, 30, 20, "m", "width", 30, "m"),
    prismItem(11, 50, 30, 20, "m", "height", 20, "m"),
    prismItem(12, 50, 30, 20, "m", "volume", 30000, "m³"),
    prismTable(13, 4, 3, 1, "cm"), prismTable(14, 12, 9, 3, "cm"),
    prismTable(15, 8, 6, 2, "m"), prismTable(16, 40, 30, 10, "mm"),
    prismTable(17, 5, 3, 2, "in"), prismTable(18, 6, 3, 3, "in"),
    prismTable(19, 9, 6, 5, "m"), prismTable(20, 36, 28, 5, "ft"),
    prismTable(21, 80, 15, 1, "cm"), prismTable(22, 6, 48, 12, "mm"),
    GeometryMeasureProblem(
      itemNumber = 23,
      shapeKind = MeasureShapeKind.RECT_PRISM,
      dimLabels = listOf("3m", "10m", "10m"),
      promptText = "Label the rectangular prism with Length = 3 m, Width = 10 m, Height = 10 m:",
      fields = listOf(
        NumericFieldSpec("Length (m)", 3.0, "m"),
        NumericFieldSpec("Width (m)", 10.0, "m"),
        NumericFieldSpec("Height (m)", 10.0, "m"),
      ),
      interactiveLabelCheck = true,
      hint = "Check the Length, Width, and Height given in the question and enter each into its matching box.",
    ),
    prismItem(24, 3, 10, 10, "m", "volume", 300, "m³"),
    cubeFig(25, 10, "mm"), cubeFig(26, 16, "mm"), cubeFig(27, 9, "mm"),
    cubeFig(28, 11, "m"), cubeFig(29, 90, "mm"), cubeFig(30, 15, "mm"),
    cubeFig(31, 2, "cm"), cubeFig(32, 18, "mm"),
    cubeTable(33, 5, "cm"), cubeTable(34, 100, "mm"), cubeTable(35, 35, "mm"),
    cubeTable(36, 3, "m"), cubeTable(37, 6, "ft"), cubeTable(38, 4, "cm"),
    GeometryMeasureProblem(
      itemNumber = 39,
      shapeKind = MeasureShapeKind.CUBE,
      dimLabels = listOf("7 cm"),
      promptText = "Label the cube with side s = 7 cm:",
      fields = listOf(NumericFieldSpec("Side (cm)", 7.0, "cm")),
      interactiveLabelCheck = true,
      hint = "Check the side length s given in the question and enter it into the box.",
    ),
    cubeFig(40, 7, "cm"),
  )

  private fun mercuryRead(item: Int, c: Int) = ThermometerProblem(
    itemNumber = item,
    mode = ThermometerMode.MERCURY_READ,
    targetCelsius = c.toDouble(),
    hint = "Look where the dark mercury column stops along the °C scale (negative below 0).",
  )

  private fun mercurySet(item: Int, c: Int) = ThermometerProblem(
    itemNumber = item,
    mode = ThermometerMode.MERCURY_SET,
    targetCelsius = c.toDouble(),
    hint = "Adjust the slider or +/- buttons until the mercury level reaches $c °C.",
  )

  val ws33: List<MathProblem> = listOf(
    mercuryRead(1, 15), mercuryRead(2, 10), mercuryRead(3, 45), mercuryRead(4, -10),
    mercuryRead(5, 40), mercuryRead(6, -5), mercuryRead(7, -20), mercuryRead(8, 35),
    mercuryRead(9, -25), mercuryRead(10, 40), mercuryRead(11, 20), mercuryRead(12, 30),
    mercuryRead(13, -15), mercuryRead(14, 25), mercuryRead(15, 0), mercuryRead(16, -30),
  )

  val ws34: List<MathProblem> = listOf(
    mercurySet(1, 0), mercurySet(2, -10), mercurySet(3, 10), mercurySet(4, 35),
    mercurySet(5, 20), mercurySet(6, 15), mercurySet(7, 5), mercurySet(8, -25),
    mercurySet(9, 20), mercurySet(10, 45), mercurySet(11, -30), mercurySet(12, -10),
    mercurySet(13, -25), mercurySet(14, 50), mercurySet(15, 25), mercurySet(16, 5),
  )

  val ws35: List<MathProblem> = listOf(
    ThermometerProblem(1, ThermometerMode.DIGITAL_READ, 37.2, "Read the Celsius number shown on the digital screen."),
    ThermometerProblem(2, ThermometerMode.DIGITAL_READ, 12.0, "Read the Celsius temperature on the display."),
    ThermometerProblem(3, ThermometerMode.DIGITAL_READ, 52.0, "Read the Celsius temperature on the display."),
    ThermometerProblem(4, ThermometerMode.DIGITAL_READ, -7.0, "Look closely at the minus sign (−) in front of the number on the digital display."),
    ThermometerProblem(5, ThermometerMode.DIGITAL_READ, 42.1, "Include the decimal tenth (.1)."),
    ThermometerProblem(6, ThermometerMode.DIGITAL_SET, 38.0, "Enter 38 onto the digital thermometer."),
    ThermometerProblem(7, ThermometerMode.DIGITAL_SET, 72.3, "Enter 72.3 onto the digital thermometer."),
    ThermometerProblem(8, ThermometerMode.DIGITAL_SET, -20.1, "Use +/- to enter negative -20.1."),
    ThermometerProblem(9, ThermometerMode.DIGITAL_SET, -15.7, "Use +/- to enter negative -15.7."),
    ThermometerProblem(10, ThermometerMode.DIGITAL_SET, 57.5, "Enter 57.5 onto the digital thermometer."),
  )
}
