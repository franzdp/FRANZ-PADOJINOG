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
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.BasicShapeKind
import com.example.model.RealWorldObjectKind
import com.example.ui.theme.*

@Composable
fun BasicShapeCanvas(
  shape: BasicShapeKind,
  modifier: Modifier = Modifier,
  size: Dp = 68.dp,
  fill: Color = BagacayBlueLight,
  stroke: Color = InkDark,
) {
  Canvas(modifier = modifier.size(size)) {
    val w = this.size.width
    val h = this.size.height
    val border = Stroke(2.5.dp.toPx())
    when (shape) {
      BasicShapeKind.CIRCLE -> {
        val r = minOf(w, h) * 0.40f
        drawCircle(fill, r, Offset(w / 2f, h / 2f))
        drawCircle(stroke, r, Offset(w / 2f, h / 2f), style = border)
      }
      BasicShapeKind.SQUARE -> {
        val side = minOf(w, h) * 0.72f
        val left = (w - side) / 2f
        val top = (h - side) / 2f
        drawRect(fill, Offset(left, top), Size(side, side))
        drawRect(stroke, Offset(left, top), Size(side, side), style = border)
      }
      BasicShapeKind.RECTANGLE -> {
        drawRect(fill, Offset(w * 0.08f, h * 0.24f), Size(w * 0.84f, h * 0.52f))
        drawRect(stroke, Offset(w * 0.08f, h * 0.24f), Size(w * 0.84f, h * 0.52f), style = border)
      }
      BasicShapeKind.TRIANGLE -> {
        val p = Path().apply {
          moveTo(w / 2f, h * 0.14f)
          lineTo(w * 0.86f, h * 0.84f)
          lineTo(w * 0.14f, h * 0.84f)
          close()
        }
        drawPath(p, fill)
        drawPath(p, stroke, style = border)
      }
    }
  }
}

fun RealWorldObjectKind.glyph(): String = when (this) {
  RealWorldObjectKind.COIN -> "🪙"
  RealWorldObjectKind.NOTEBOOK -> "📓"
  RealWorldObjectKind.RULER -> "📏"
  RealWorldObjectKind.CORN_CHIP -> "🔺"
  RealWorldObjectKind.TUNA_CAN -> "🥫"
  RealWorldObjectKind.CHEESE_WEDGE -> "🧀"
  RealWorldObjectKind.FLOPPY_DISK -> "💾"
  RealWorldObjectKind.CLOCK -> "🕒"
  RealWorldObjectKind.DOOR -> "🚪"
  RealWorldObjectKind.CHALKBOARD -> "🟩"
  RealWorldObjectKind.SUITCASE -> "💼"
  RealWorldObjectKind.PIZZA_WHOLE -> "🍕"
  RealWorldObjectKind.PIZZA_SLICE -> "🍕"
  RealWorldObjectKind.WATERMELON_SLICE -> "🍉"
  RealWorldObjectKind.DONUT -> "🍩"
  RealWorldObjectKind.MOON_COOKIE -> "🍪"
  RealWorldObjectKind.SQUARE_CLOCK -> "🖼️"
  RealWorldObjectKind.SQUARE_FRAME -> "🖼️"
  RealWorldObjectKind.HANDKERCHIEF -> "⬜"
  RealWorldObjectKind.WINDOW -> "🪟"
  RealWorldObjectKind.MOUNTAIN -> "⛰️"
  RealWorldObjectKind.TRIANGLE_FLAG -> "🚩"
  RealWorldObjectKind.TOWEL -> "🟨"
  RealWorldObjectKind.BALL -> "🏀"
}

@Composable
fun RealWorldObjectVisualBox(
  obj: RealWorldObjectKind,
  modifier: Modifier = Modifier,
) {
  Column(
    modifier = modifier
      .clip(RoundedCornerShape(16.dp))
      .background(WarmSurfaceVariant)
      .border(1.5.dp, OutlineSoft, RoundedCornerShape(16.dp))
      .padding(horizontal = 14.dp, vertical = 10.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(4.dp),
  ) {
    BasicShapeCanvas(
      shape = obj.shapeKind,
      size = 54.dp,
      fill = when (obj.shapeKind) {
        BasicShapeKind.CIRCLE -> MangoGoldLight
        BasicShapeKind.SQUARE -> BagacayBlueLight
        BasicShapeKind.RECTANGLE -> LeafEmeraldLight
        BasicShapeKind.TRIANGLE -> CoralLight
      },
    )
    Text("${obj.glyph()} ${obj.label}", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold, color = InkDark, textAlign = TextAlign.Center)
  }
}

@Composable
fun April2014CalendarCard(modifier: Modifier = Modifier) {
  val headers = listOf("S", "M", "T", "W", "T", "F", "S")
  val weeks = listOf(
    listOf("", "", "1", "2", "3", "4", "5"),
    listOf("6", "7", "8", "9", "10", "11", "12"),
    listOf("13", "14", "15", "16", "17", "18", "19"),
    listOf("20", "21", "22", "23", "24", "25", "26"),
    listOf("27", "28", "29", "30", "", "", ""),
  )

  Surface(
    color = Color(0xFFFFFDF8),
    shape = RoundedCornerShape(18.dp),
    modifier = modifier
      .fillMaxWidth()
      .border(1.5.dp, BagacayBlue.copy(alpha = 0.6f), RoundedCornerShape(18.dp)),
  ) {
    Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
      Text(
        text = "APRIL 2014",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        color = BagacayBlue,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth(),
      )
      Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        headers.forEach { d ->
          Text(d, fontWeight = FontWeight.Bold, color = CoralTangerine, textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
        }
      }
      weeks.forEach { wk ->
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
          wk.forEach { cell ->
            Box(
              modifier = Modifier
                .weight(1f)
                .padding(2.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(if (cell.isNotEmpty()) BagacayBlueLight.copy(alpha = 0.35f) else Color.Transparent)
                .padding(vertical = 4.dp),
              contentAlignment = Alignment.Center,
            ) {
              Text(cell, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = InkDark)
            }
          }
        }
      }
    }
  }
}

@Composable
fun NumberLineHopCanvas(
  maxValue: Int,
  hopsCount: Int,
  hopSize: Int,
  modifier: Modifier = Modifier,
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(16.dp))
      .background(Color(0xFFFFFDF8))
      .border(1.5.dp, OutlineSoft, RoundedCornerShape(16.dp))
      .padding(horizontal = 10.dp, vertical = 14.dp),
  ) {
    Canvas(
      modifier = Modifier
        .fillMaxWidth()
        .height(74.dp),
    ) {
      val padX = 16.dp.toPx()
      val lineW = size.width - padX * 2f
      val stepX = lineW / maxValue.toFloat()
      val axisY = size.height * 0.72f
      val axisStroke = 2.dp.toPx()

      drawLine(InkDark, Offset(padX - 8f, axisY), Offset(padX + lineW + 8f, axisY), strokeWidth = axisStroke)
      for (i in 0..maxValue) {
        val x = padX + i * stepX
        drawLine(InkDark, Offset(x, axisY - 6.dp.toPx()), Offset(x, axisY + 6.dp.toPx()), strokeWidth = axisStroke)
      }

      // Curved hops
      for (hIdx in 0 until hopsCount) {
        val startVal = hIdx * hopSize
        val endVal = (hIdx + 1) * hopSize
        if (endVal > maxValue) break
        val sx = padX + startVal * stepX
        val ex = padX + endVal * stepX
        val mx = (sx + ex) / 2f
        val peakY = axisY - 40.dp.toPx()
        val p = Path().apply {
          moveTo(sx, axisY - 4.dp.toPx())
          quadraticTo(mx, peakY, ex, axisY - 4.dp.toPx())
        }
        drawPath(p, BagacayBlue, style = Stroke(2.5.dp.toPx(), cap = StrokeCap.Round))
        drawCircle(CoralTangerine, 3.5.dp.toPx(), Offset(ex, axisY - 4.dp.toPx()))
      }
    }
    Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
      for (i in 0..maxValue) {
        Text(
          text = "$i",
          fontSize = if (maxValue > 16) 9.sp else 11.sp,
          fontWeight = FontWeight.Bold,
          color = if (i % hopSize == 0 && i > 0 && i <= hopsCount * hopSize) BagacayBlue else InkDark,
        )
      }
    }
  }
}

@Composable
fun G3CirclePartVisual(
  pictureKind: String,
  modifier: Modifier = Modifier,
) {
  Canvas(modifier = modifier.size(120.dp)) {
    val w = size.width
    val h = size.height
    val cx = w / 2f
    val cy = h / 2f
    val r = w * 0.40f
    val stroke = Stroke(2.5.dp.toPx())

    when (pictureKind) {
      "watermelon" -> {
        drawArc(CoralTangerine.copy(alpha = 0.75f), 0f, 180f, true, Offset(cx - r, cy - r * 0.4f), Size(r * 2, r * 2))
        drawArc(LeafEmerald, 0f, 180f, false, Offset(cx - r, cy - r * 0.4f), Size(r * 2, r * 2), style = Stroke(5.dp.toPx()))
        drawLine(InkDark, Offset(cx - r, cy + r * 0.6f), Offset(cx + r, cy + r * 0.6f), strokeWidth = stroke.width)
      }
      "pizza_half" -> {
        drawArc(MangoGoldLight, 90f, 180f, true, Offset(cx - r, cy - r), Size(r * 2, r * 2))
        drawArc(InkDark, 90f, 180f, true, Offset(cx - r, cy - r), Size(r * 2, r * 2), style = stroke)
      }
      "quarter_slice" -> {
        drawArc(MangoGold, 180f, 90f, true, Offset(cx - r * 0.8f, cy - r * 0.2f), Size(r * 2, r * 2))
        drawArc(InkDark, 180f, 90f, true, Offset(cx - r * 0.8f, cy - r * 0.2f), Size(r * 2, r * 2), style = stroke)
      }
      "protractor_half" -> {
        drawArc(BagacayBlueLight, -90f, 180f, true, Offset(cx - r, cy - r), Size(r * 2, r * 2))
        drawArc(InkDark, -90f, 180f, true, Offset(cx - r, cy - r), Size(r * 2, r * 2), style = stroke)
      }
    }
  }
}

@Composable
fun LineOrSurfaceVisual(
  figureId: Int,
  figureLabel: String,
  modifier: Modifier = Modifier,
) {
  Surface(
    color = Color(0xFFFFFDF8),
    shape = RoundedCornerShape(18.dp),
    modifier = modifier
      .fillMaxWidth()
      .border(1.5.dp, OutlineSoft, RoundedCornerShape(18.dp)),
  ) {
    Column(
      modifier = Modifier.padding(14.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
      if (figureId in 1..9) {
        Canvas(modifier = Modifier.fillMaxWidth().height(90.dp)) {
          val w = size.width
          val h = size.height
          val stroke = Stroke(4.dp.toPx(), cap = StrokeCap.Round)
          when (figureId) {
            1 -> drawLine(BagacayBlue, Offset(w * 0.15f, h * 0.25f), Offset(w * 0.85f, h * 0.55f), strokeWidth = stroke.width)
            2 -> {
              val p = Path().apply {
                moveTo(w * 0.15f, h * 0.2f)
                cubicTo(w * 0.55f, h * 0.2f, w * 0.45f, h * 0.8f, w * 0.85f, h * 0.8f)
              }
              drawPath(p, CoralTangerine, style = stroke)
            }
            3 -> drawLine(BagacayBlue, Offset(w * 0.15f, h * 0.78f), Offset(w * 0.85f, h * 0.22f), strokeWidth = stroke.width)
            4 -> drawArc(CoralTangerine, 0f, 180f, false, Offset(w * 0.18f, h * 0.1f), Size(w * 0.64f, h * 0.75f), style = stroke)
            5 -> drawLine(BagacayBlue, Offset(w * 0.5f, h * 0.12f), Offset(w * 0.5f, h * 0.88f), strokeWidth = stroke.width)
            6 -> drawArc(CoralTangerine, 100f, 160f, false, Offset(w * 0.22f, h * 0.15f), Size(w * 0.56f, h * 0.7f), style = stroke)
            7 -> {
              val p = Path().apply {
                moveTo(w * 0.14f, h * 0.45f)
                cubicTo(w * 0.35f, h * 0.05f, w * 0.45f, h * 0.95f, w * 0.68f, h * 0.5f)
                cubicTo(w * 0.82f, h * 0.25f, w * 0.72f, h * 0.85f, w * 0.88f, h * 0.55f)
              }
              drawPath(p, CoralTangerine, style = stroke)
            }
            8 -> drawLine(BagacayBlue, Offset(w * 0.12f, h * 0.5f), Offset(w * 0.88f, h * 0.5f), strokeWidth = stroke.width)
            9 -> {
              val p = Path().apply {
                moveTo(w * 0.65f, h * 0.18f)
                cubicTo(w * 0.30f, h * 0.10f, w * 0.30f, h * 0.48f, w * 0.50f, h * 0.50f)
                cubicTo(w * 0.72f, h * 0.52f, w * 0.72f, h * 0.88f, w * 0.32f, h * 0.82f)
              }
              drawPath(p, CoralTangerine, style = stroke)
            }
          }
        }
      } else {
        Text(
          text = figureLabel,
          style = MaterialTheme.typography.headlineSmall,
          fontWeight = FontWeight.Bold,
          color = InkDark,
          textAlign = TextAlign.Center,
        )
      }
    }
  }
}
