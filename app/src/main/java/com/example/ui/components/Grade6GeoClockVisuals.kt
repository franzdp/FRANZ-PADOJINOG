package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.MeasureShapeKind
import com.example.ui.theme.*
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun PolygonRegularShapeCanvas(
  polygonSides: Int,
  modifier: Modifier = Modifier,
  fillColor: Color = BagacayBlueLight,
  strokeColor: Color = BagacayBlue,
) {
  val sides = polygonSides.coerceIn(3, 12)
  Canvas(
    modifier = modifier
      .fillMaxWidth()
      .height(148.dp)
      .clip(RoundedCornerShape(18.dp))
      .background(WarmSurfaceVariant.copy(alpha = 0.6f)),
  ) {
    val cx = size.width / 2f
    val cy = size.height / 2f
    val r = size.minDimension * 0.37f
    val startDeg = if (sides == 4) -45f else -90f
    val path = Path()
    for (i in 0 until sides) {
      val rad = (startDeg + i * (360f / sides)) * (PI / 180.0)
      val x = cx + (r * cos(rad)).toFloat()
      val y = cy + (r * sin(rad)).toFloat()
      if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
    }
    path.close()
    drawPath(path, color = fillColor)
    drawPath(path, color = strokeColor, style = Stroke(width = 3.5.dp.toPx(), join = StrokeJoin.Round))
  }
}

@Composable
fun PolygonPuzzle40Canvas(
  polygonId: Int,
  modifier: Modifier = Modifier,
) {
  val normVerts = normalizedPolygonVertices(polygonId)
  Canvas(
    modifier = modifier
      .fillMaxWidth()
      .height(160.dp)
      .clip(RoundedCornerShape(20.dp))
      .background(WarmSurfaceVariant.copy(alpha = 0.65f)),
  ) {
    val cx = size.width / 2f
    val cy = size.height / 2f
    val scale = size.minDimension * 0.38f
    val path = Path()
    normVerts.forEachIndexed { idx, (nx, ny) ->
      val x = cx + nx * scale
      val y = cy + ny * scale
      if (idx == 0) path.moveTo(x, y) else path.lineTo(x, y)
    }
    path.close()
    drawPath(path, color = BerryLight.copy(alpha = 0.85f))
    drawPath(path, color = BerryPurple, style = Stroke(width = 3.2.dp.toPx(), join = StrokeJoin.Round))
  }
}

private fun regularNorm(sides: Int, startDeg: Float = -90f): List<Pair<Float, Float>> =
  (0 until sides).map { i ->
    val rad = (startDeg + i * (360f / sides)) * (PI / 180.0)
    cos(rad).toFloat() to sin(rad).toFloat()
  }

private fun starNorm(points: Int, outerR: Float = 1f, innerR: Float = 0.42f): List<Pair<Float, Float>> =
  (0 until points * 2).map { i ->
    val r = if (i % 2 == 0) outerR else innerR
    val rad = (-90f + i * (180f / points)) * (PI / 180.0)
    (r * cos(rad)).toFloat() to (r * sin(rad)).toFloat()
  }

private fun normalizedPolygonVertices(id: Int): List<Pair<Float, Float>> = when (id) {
  1 -> regularNorm(3) // Regular triangle
  2 -> listOf(-0.9f to -0.6f, 0.2f to -0.8f, 0.9f to -0.2f, 0.2f to 0.1f, 0.7f to 0.8f, -0.5f to 0.6f) // Concave indent
  3 -> regularNorm(5) // Regular pentagon
  4 -> listOf(-0.7f to -0.8f, -0.1f to -0.8f, -0.1f to 0.2f, 0.8f to 0.2f, 0.8f to 0.8f, -0.7f to 0.8f) // L-shape
  5 -> listOf(-0.95f to 0.65f, -0.35f to -0.75f, 0.95f to 0.45f) // Scalene triangle
  6 -> listOf(-0.8f to -0.2f, 0f to -0.85f, 0.9f to -0.1f, 0.45f to 0.75f, -0.5f to 0.75f) // Stretched pentagon
  7 -> regularNorm(8, -67.5f) // Regular octagon
  8 -> regularNorm(6, -60f) // Regular hexagon
  9 -> listOf(-0.55f to -0.65f, 0.55f to -0.65f, 0.95f to 0.65f, -0.95f to 0.65f) // Trapezoid
  10 -> listOf(-0.9f to -0.7f, -0.1f to -0.2f, 0.4f to -0.8f, 0.9f to 0.2f, 0.2f to 0.8f, -0.6f to 0.4f) // Concave zigzag
  11 -> listOf(-0.85f to -0.75f, -0.85f to 0.75f, 0.95f to 0.75f) // Right scalene triangle
  12 -> starNorm(5) // 5-point star
  13 -> listOf(-0.85f to -0.35f, 0.15f to -0.85f, 0.9f to -0.2f, 0.65f to 0.75f, -0.45f to 0.6f) // Asymmetric pentagon
  14 -> listOf(-0.9f to -0.4f, 0f to -0.7f, 0.85f to -0.3f, 0.6f to 0.7f, -0.2f to 0.4f, -0.75f to 0.8f) // Unequal 6-gon
  15 -> listOf(-0.85f to -0.7f, 0.85f to -0.7f, 0.85f to -0.1f, 0.2f to 0.1f, 0.85f to 0.7f, -0.85f to 0.7f) // Notched polygon
  16 -> listOf(-0.9f to -0.8f, 0.9f to -0.8f, 0.9f to -0.25f, 0.3f to -0.25f, 0.3f to 0.85f, -0.3f to 0.85f, -0.3f to -0.25f, -0.9f to -0.25f) // T-shape
  17 -> listOf(-0.7f to -0.75f, 0.7f to -0.75f, 0.8f to 0.15f, 0f to 0.85f, -0.8f to 0.15f) // Shield pentagon
  18 -> listOf(-0.45f to -0.65f, 0.95f to -0.65f, 0.45f to 0.65f, -0.95f to 0.65f) // Slanted parallelogram
  19 -> regularNorm(7) // Regular heptagon
  20 -> listOf(-0.95f to 0f, -0.45f to -0.55f, 0.45f to -0.55f, 0.95f to 0f, 0.45f to 0.55f, -0.45f to 0.55f) // Stretched hexagon
  21 -> listOf(-0.8f to -0.8f, 0.8f to -0.8f, 0.2f to 0f, 0.8f to 0.8f, -0.8f to 0.8f, -0.2f to 0f) // Hourglass polygon
  22 -> listOf(-0.7f to -0.8f, 0.4f to -0.8f, 0.9f to 0f, 0.5f to 0.8f, -0.85f to 0.5f) // Slanted-corner pentagon
  23 -> regularNorm(5) // Regular pentagon
  24 -> regularNorm(3) // Regular triangle
  25 -> listOf(-0.8f to -0.8f, 0.8f to -0.8f, 0.8f to -0.3f, -0.25f to -0.3f, -0.25f to 0.3f, 0.8f to 0.3f, 0.8f to 0.8f, -0.8f to 0.8f) // C-hook
  26 -> listOf(0f to -0.85f, 0.85f to 0.75f, 0f to 0.15f, -0.85f to 0.75f) // Dart concave
  27 -> regularNorm(6, -90f) // Regular hexagon
  28 -> listOf(-0.9f to -0.7f, 0.2f to -0.7f, 0.9f to 0f, 0.2f to 0.7f, -0.9f to 0.7f, -0.3f to 0f) // Chevron arrow
  29 -> listOf(-0.95f to -0.45f, 0.95f to -0.45f, 0.95f to 0.45f, -0.95f to 0.45f) // Rectangle
  30 -> regularNorm(9) // Regular nonagon
  31 -> listOf(0f to -0.9f, 0.6f to -0.2f, 0f to 0.9f, -0.6f to -0.2f) // Kite
  32 -> listOf(-0.9f to 0.2f, -0.1f to -0.8f, 0.85f to -0.35f, 0.65f to 0.75f, -0.55f to 0.75f) // Unequal pentagon
  33 -> regularNorm(9, -70f) // Regular nonagon
  34 -> starNorm(4, 1f, 0.36f) // 4-point star
  35 -> listOf(-0.9f to -0.3f, 0.1f to -0.3f, 0.1f to -0.75f, 0.9f to 0f, 0.1f to 0.75f, 0.1f to 0.3f, -0.9f to 0.3f) // Block arrow
  36 -> listOf(-0.6f to -0.85f, -0.6f to 0.85f, 0.9f to 0.85f) // Right triangle
  37 -> regularNorm(8, -67.5f) // Regular octagon
  38 -> listOf(-0.9f to -0.7f, -0.3f to -0.1f, 0f to -0.8f, 0.3f to -0.1f, 0.9f to -0.7f, 0.75f to 0.75f, -0.75f to 0.75f) // Crown zigzag
  39 -> listOf(-0.85f to -0.5f, 0.1f to -0.85f, 0.9f to -0.4f, 0.4f to 0.1f, 0.85f to 0.75f, -0.45f to 0.85f, -0.2f to 0.15f) // Jagged 7-gon
  else -> regularNorm(3) // Regular triangle (40)
}

@Composable
fun AnalogClockCanvas(
  hour12: Int,
  minute: Int,
  isPm: Boolean,
  modifier: Modifier = Modifier,
) {
  val textMeasurer = rememberTextMeasurer()
  Row(
    modifier = modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.Center,
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Canvas(modifier = Modifier.size(168.dp)) {
      val cx = size.width / 2f
      val cy = size.height / 2f
      val radius = size.minDimension / 2f - 8.dp.toPx()

      drawCircle(color = Color.White, radius = radius, center = Offset(cx, cy))
      drawCircle(color = BagacayBlue, radius = radius, center = Offset(cx, cy), style = Stroke(width = 4.dp.toPx()))

      for (m in 0 until 60) {
        val deg = m * 6f - 90f
        val rad = deg * (PI / 180.0)
        val isHourTick = m % 5 == 0
        val innerR = radius * if (isHourTick) 0.86f else 0.92f
        val outerR = radius * 0.97f
        drawLine(
          color = if (isHourTick) InkDark else InkMuted.copy(alpha = 0.55f),
          start = Offset(cx + (innerR * cos(rad)).toFloat(), cy + (innerR * sin(rad)).toFloat()),
          end = Offset(cx + (outerR * cos(rad)).toFloat(), cy + (outerR * sin(rad)).toFloat()),
          strokeWidth = if (isHourTick) 2.5.dp.toPx() else 1.2.dp.toPx(),
          cap = StrokeCap.Round,
        )
      }

      for (h in 1..12) {
        val rad = (h * 30f - 90f) * (PI / 180.0)
        val labelR = radius * 0.72f
        val measured = textMeasurer.measure(
          text = "$h",
          style = TextStyle(fontSize = 13.sp, fontWeight = FontWeight.Bold, color = InkDark),
        )
        drawText(
          textLayoutResult = measured,
          topLeft = Offset(
            cx + (labelR * cos(rad)).toFloat() - measured.size.width / 2f,
            cy + (labelR * sin(rad)).toFloat() - measured.size.height / 2f,
          ),
        )
      }

      val hourAngle = ((hour12 % 12) + minute / 60f) * 30f - 90f
      val hourRad = hourAngle * (PI / 180.0)
      drawLine(
        color = InkDark,
        start = Offset(cx, cy),
        end = Offset(cx + (radius * 0.48f * cos(hourRad)).toFloat(), cy + (radius * 0.48f * sin(hourRad)).toFloat()),
        strokeWidth = 5.dp.toPx(),
        cap = StrokeCap.Round,
      )

      val minAngle = minute * 6f - 90f
      val minRad = minAngle * (PI / 180.0)
      drawLine(
        color = CoralTangerine,
        start = Offset(cx, cy),
        end = Offset(cx + (radius * 0.68f * cos(minRad)).toFloat(), cy + (radius * 0.68f * sin(minRad)).toFloat()),
        strokeWidth = 3.dp.toPx(),
        cap = StrokeCap.Round,
      )

      drawCircle(color = BagacayBlue, radius = 5.dp.toPx(), center = Offset(cx, cy))
    }

    Spacer(Modifier.width(12.dp))
    Surface(
      color = if (isPm) CoralLight else BagacayBlueLight,
      shape = RoundedCornerShape(14.dp),
      modifier = Modifier.border(1.5.dp, if (isPm) CoralTangerine else BagacayBlue, RoundedCornerShape(14.dp)),
    ) {
      Text(
        text = if (isPm) "PM" else "AM",
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        color = if (isPm) MangoDark else BagacayBlue,
        modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
      )
    }
  }
}

@Composable
fun GeometryMeasureCanvas(
  shapeKind: MeasureShapeKind,
  dimLabels: List<String>,
  hideLabels: Boolean = false,
  modifier: Modifier = Modifier,
) {
  val textMeasurer = rememberTextMeasurer()
  val dashEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 8f), 0f)

  Canvas(
    modifier = modifier
      .fillMaxWidth()
      .height(172.dp)
      .clip(RoundedCornerShape(20.dp))
      .background(WarmSurfaceVariant.copy(alpha = 0.65f)),
  ) {
    when (shapeKind) {
      MeasureShapeKind.TRIANGLE -> drawTriangleDiagram(dimLabels, isRight = false, dashEffect, textMeasurer)
      MeasureShapeKind.TRIANGLE_RIGHT -> drawTriangleDiagram(dimLabels, isRight = true, dashEffect, textMeasurer)
      MeasureShapeKind.TRAPEZOID -> drawTrapezoidDiagram(dimLabels, dashEffect, textMeasurer)
      MeasureShapeKind.PARALLELOGRAM -> drawParallelogramDiagram(dimLabels, dashEffect, textMeasurer)
      MeasureShapeKind.RECT_PRISM -> drawRectPrismDiagram(dimLabels, hideLabels, textMeasurer)
      MeasureShapeKind.CUBE -> drawCubeDiagram(dimLabels, hideLabels, textMeasurer)
      MeasureShapeKind.TABLE_ONLY -> Unit
    }
  }
}

private fun DrawScope.drawBadgeLabel(
  textMeasurer: TextMeasurer,
  label: String,
  center: Offset,
  color: Color = InkDark,
) {
  if (label.isBlank()) return
  val m = textMeasurer.measure(
    text = label,
    style = TextStyle(fontSize = 13.sp, fontWeight = FontWeight.Bold, color = color),
  )
  val padX = 5.dp.toPx()
  val padY = 2.dp.toPx()
  drawRoundRect(
    color = Color.White.copy(alpha = 0.9f),
    topLeft = Offset(center.x - m.size.width / 2f - padX, center.y - m.size.height / 2f - padY),
    size = Size(m.size.width + padX * 2, m.size.height + padY * 2),
    cornerRadius = androidx.compose.ui.geometry.CornerRadius(6.dp.toPx()),
  )
  drawText(
    textLayoutResult = m,
    topLeft = Offset(center.x - m.size.width / 2f, center.y - m.size.height / 2f),
  )
}

private fun DrawScope.drawTriangleDiagram(
  dims: List<String>,
  isRight: Boolean,
  dashEffect: PathEffect,
  tm: TextMeasurer,
) {
  val w = size.width
  val h = size.height
  val left = w * 0.24f
  val right = w * 0.76f
  val bottom = h * 0.77f
  val top = h * 0.18f
  val apexX = if (isRight) left else w * 0.44f

  val path = Path().apply {
    moveTo(left, bottom)
    lineTo(right, bottom)
    lineTo(apexX, top)
    close()
  }
  drawPath(path, color = BagacayBlueLight.copy(alpha = 0.75f))
  drawPath(path, color = BagacayBlue, style = Stroke(width = 3.dp.toPx(), join = StrokeJoin.Round))

  if (isRight) {
    val sq = 14.dp.toPx()
    drawRect(color = CoralTangerine, topLeft = Offset(left, bottom - sq), size = Size(sq, sq), style = Stroke(2.dp.toPx()))
    drawBadgeLabel(tm, "h = ${dims.getOrElse(1) { "" }}", Offset(left - 34.dp.toPx(), (top + bottom) / 2f), CoralTangerine)
  } else {
    drawLine(CoralTangerine, Offset(apexX, top), Offset(apexX, bottom), strokeWidth = 2.5.dp.toPx(), pathEffect = dashEffect)
    val sq = 10.dp.toPx()
    drawRect(color = CoralTangerine, topLeft = Offset(apexX, bottom - sq), size = Size(sq, sq), style = Stroke(1.8.dp.toPx()))
    drawBadgeLabel(tm, "h = ${dims.getOrElse(1) { "" }}", Offset(apexX + 36.dp.toPx(), (top + bottom) / 2f), CoralTangerine)
  }
  drawBadgeLabel(tm, "b = ${dims.getOrElse(0) { "" }}", Offset((left + right) / 2f, bottom + 15.dp.toPx()), BagacayBlue)
}

private fun DrawScope.drawTrapezoidDiagram(
  dims: List<String>,
  dashEffect: PathEffect,
  tm: TextMeasurer,
) {
  val w = size.width
  val h = size.height
  val topLeft = w * 0.34f
  val topRight = w * 0.66f
  val botLeft = w * 0.2f
  val botRight = w * 0.8f
  val top = h * 0.22f
  val bot = h * 0.77f

  val path = Path().apply {
    moveTo(topLeft, top)
    lineTo(topRight, top)
    lineTo(botRight, bot)
    lineTo(botLeft, bot)
    close()
  }
  drawPath(path, color = TealLight.copy(alpha = 0.75f))
  drawPath(path, color = TealOcean, style = Stroke(width = 3.dp.toPx(), join = StrokeJoin.Round))

  val hx = topLeft + 10.dp.toPx()
  drawLine(CoralTangerine, Offset(hx, top), Offset(hx, bot), strokeWidth = 2.5.dp.toPx(), pathEffect = dashEffect)
  drawBadgeLabel(tm, "b₁ = ${dims.getOrElse(0) { "" }}", Offset(w / 2f, top - 15.dp.toPx()), TealOcean)
  drawBadgeLabel(tm, "b₂ = ${dims.getOrElse(1) { "" }}", Offset(w / 2f, bot + 15.dp.toPx()), TealOcean)
  drawBadgeLabel(tm, "h = ${dims.getOrElse(2) { "" }}", Offset(hx + 34.dp.toPx(), (top + bot) / 2f), CoralTangerine)
}

private fun DrawScope.drawParallelogramDiagram(
  dims: List<String>,
  dashEffect: PathEffect,
  tm: TextMeasurer,
) {
  val w = size.width
  val h = size.height
  val shift = w * 0.12f
  val left = w * 0.22f
  val right = w * 0.72f
  val top = h * 0.24f
  val bot = h * 0.77f

  val path = Path().apply {
    moveTo(left + shift, top)
    lineTo(right + shift, top)
    lineTo(right, bot)
    lineTo(left, bot)
    close()
  }
  drawPath(path, color = MangoGoldLight.copy(alpha = 0.75f))
  drawPath(path, color = MangoDark, style = Stroke(width = 3.dp.toPx(), join = StrokeJoin.Round))

  val hx = left + shift + 8.dp.toPx()
  drawLine(BagacayBlue, Offset(hx, top), Offset(hx, bot), strokeWidth = 2.5.dp.toPx(), pathEffect = dashEffect)
  drawBadgeLabel(tm, "b = ${dims.getOrElse(0) { "" }}", Offset((left + right) / 2f, bot + 15.dp.toPx()), MangoDark)
  drawBadgeLabel(tm, "h = ${dims.getOrElse(1) { "" }}", Offset(hx + 34.dp.toPx(), (top + bot) / 2f), BagacayBlue)
}

private fun DrawScope.drawRectPrismDiagram(
  dims: List<String>,
  hideLabels: Boolean,
  tm: TextMeasurer,
) {
  val cx = size.width / 2f
  val cy = size.height / 2f
  val fw = 128.dp.toPx()
  val fh = 74.dp.toPx()
  val dx = 36.dp.toPx()
  val dy = -26.dp.toPx()

  val x0 = cx - fw / 2f - dx / 2f
  val y0 = cy - fh / 2f - dy / 2f

  val topFace = Path().apply {
    moveTo(x0, y0)
    lineTo(x0 + dx, y0 + dy)
    lineTo(x0 + fw + dx, y0 + dy)
    lineTo(x0 + fw, y0)
    close()
  }
  val rightFace = Path().apply {
    moveTo(x0 + fw, y0)
    lineTo(x0 + fw + dx, y0 + dy)
    lineTo(x0 + fw + dx, y0 + fh + dy)
    lineTo(x0 + fw, y0 + fh)
    close()
  }
  drawPath(topFace, color = BagacayBlueLight.copy(alpha = 0.9f))
  drawPath(rightFace, color = BagacayBlue.copy(alpha = 0.25f))
  drawRect(color = BagacayBlueLight.copy(alpha = 0.55f), topLeft = Offset(x0, y0), size = Size(fw, fh))

  val stroke = Stroke(width = 2.5.dp.toPx(), join = StrokeJoin.Round)
  drawRect(color = BagacayBlue, topLeft = Offset(x0, y0), size = Size(fw, fh), style = stroke)
  drawPath(topFace, color = BagacayBlue, style = stroke)
  drawPath(rightFace, color = BagacayBlue, style = stroke)

  val lStr = if (hideLabels) "l = ?" else "l = ${dims.getOrElse(0) { "" }}"
  val wStr = if (hideLabels) "w = ?" else "w = ${dims.getOrElse(1) { "" }}"
  val hStr = if (hideLabels) "h = ?" else "h = ${dims.getOrElse(2) { "" }}"
  drawBadgeLabel(tm, lStr, Offset(x0 + fw / 2f, y0 + fh + 14.dp.toPx()), BagacayBlue)
  drawBadgeLabel(tm, wStr, Offset(x0 + fw + dx + 22.dp.toPx(), y0 + fh + dy / 2f + 10.dp.toPx()), TealOcean)
  drawBadgeLabel(tm, hStr, Offset(x0 - 26.dp.toPx(), y0 + fh / 2f), CoralTangerine)
}

private fun DrawScope.drawCubeDiagram(
  dims: List<String>,
  hideLabels: Boolean,
  tm: TextMeasurer,
) {
  val cx = size.width / 2f
  val cy = size.height / 2f
  val s = 80.dp.toPx()
  val dx = 28.dp.toPx()
  val dy = -24.dp.toPx()

  val x0 = cx - s / 2f - dx / 2f
  val y0 = cy - s / 2f - dy / 2f

  val topFace = Path().apply {
    moveTo(x0, y0)
    lineTo(x0 + dx, y0 + dy)
    lineTo(x0 + s + dx, y0 + dy)
    lineTo(x0 + s, y0)
    close()
  }
  val rightFace = Path().apply {
    moveTo(x0 + s, y0)
    lineTo(x0 + s + dx, y0 + dy)
    lineTo(x0 + s + dx, y0 + s + dy)
    lineTo(x0 + s, y0 + s)
    close()
  }
  drawPath(topFace, color = LeafEmeraldLight)
  drawPath(rightFace, color = LeafEmerald.copy(alpha = 0.25f))
  drawRect(color = LeafEmeraldLight.copy(alpha = 0.65f), topLeft = Offset(x0, y0), size = Size(s, s))

  val stroke = Stroke(width = 2.5.dp.toPx(), join = StrokeJoin.Round)
  drawRect(color = LeafEmerald, topLeft = Offset(x0, y0), size = Size(s, s), style = stroke)
  drawPath(topFace, color = LeafEmerald, style = stroke)
  drawPath(rightFace, color = LeafEmerald, style = stroke)

  val sStr = if (hideLabels) "s = ?" else "s = ${dims.getOrElse(0) { "" }}"
  drawBadgeLabel(tm, sStr, Offset(x0 + s / 2f, y0 + s + 15.dp.toPx()), LeafEmerald)
}
