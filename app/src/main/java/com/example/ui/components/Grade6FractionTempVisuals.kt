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
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import kotlin.math.abs

private fun lcmLocal(a: Int, b: Int): Int {
  var x = a.coerceAtLeast(1)
  var y = b.coerceAtLeast(1)
  while (y != 0) {
    val t = x % y
    x = y
    y = t
  }
  return (a.coerceAtLeast(1) / x) * b.coerceAtLeast(1)
}

@Composable
fun FractionModelCanvas(
  numA: Int,
  denA: Int,
  numB: Int,
  denB: Int,
  isDivision: Boolean,
  modifier: Modifier = Modifier,
) {
  val textMeasurer = rememberTextMeasurer()
  if (!isDivision) {
    val cols = denA.coerceAtLeast(1)
    val rows = denB.coerceAtLeast(1)
    Column(
      modifier = modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(18.dp))
        .background(WarmSurfaceVariant.copy(alpha = 0.6f))
        .padding(12.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
      Text(
        "Area Model: ${numA}/${denA} (columns) × ${numB}/${denB} (rows)",
        style = MaterialTheme.typography.labelLarge,
        color = InkDark,
        fontWeight = FontWeight.Bold,
      )
      Canvas(modifier = Modifier.fillMaxWidth().height(132.dp)) {
        val gridW = size.minDimension.coerceAtMost(size.width * 0.72f)
        val gridH = size.height - 8.dp.toPx()
        val left = (size.width - gridW) / 2f
        val top = 4.dp.toPx()
        val cellW = gridW / cols
        val cellH = gridH / rows

        for (r in 0 until rows) {
          for (c in 0 until cols) {
            val inA = c < numA
            val inB = r < numB
            val cellColor = when {
              inA && inB -> LeafEmerald.copy(alpha = 0.72f)
              inA -> BagacayBlue.copy(alpha = 0.28f)
              inB -> MangoGold.copy(alpha = 0.32f)
              else -> Color.White
            }
            drawRect(
              color = cellColor,
              topLeft = Offset(left + c * cellW, top + r * cellH),
              size = Size(cellW, cellH),
            )
            drawRect(
              color = InkDark.copy(alpha = 0.45f),
              topLeft = Offset(left + c * cellW, top + r * cellH),
              size = Size(cellW, cellH),
              style = Stroke(width = 1.2.dp.toPx()),
            )
          }
        }
        drawRect(
          color = InkDark,
          topLeft = Offset(left, top),
          size = Size(gridW, gridH),
          style = Stroke(width = 2.dp.toPx()),
        )
      }
      Text(
        "Green overlap = ${numA * numB} out of ${cols * rows} equal boxes",
        style = MaterialTheme.typography.labelMedium,
        color = LeafEmerald,
        fontWeight = FontWeight.Bold,
      )
    }
  } else {
    val commonDen = lcmLocal(denA, denB)
    val partsA = numA * (commonDen / denA.coerceAtLeast(1))
    val partsB = numB * (commonDen / denB.coerceAtLeast(1))
    Column(
      modifier = modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(18.dp))
        .background(WarmSurfaceVariant.copy(alpha = 0.6f))
        .padding(12.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
      Text(
        "Fraction Bars: How many ${numB}/${denB} fit inside ${numA}/${denA}?",
        style = MaterialTheme.typography.labelLarge,
        color = InkDark,
        fontWeight = FontWeight.Bold,
      )
      Canvas(modifier = Modifier.fillMaxWidth().height(98.dp)) {
        val barLeft = 10.dp.toPx()
        val barW = size.width - 20.dp.toPx()
        val barH = 26.dp.toPx()
        val unitW = barW / commonDen

        // Top bar: Dividend A
        val y1 = 16.dp.toPx()
        val lblA = textMeasurer.measure("$numA/$denA = $partsA/$commonDen", TextStyle(fontSize = 11.sp, fontWeight = FontWeight.Bold, color = BagacayBlue))
        drawText(lblA, topLeft = Offset(barLeft, 0f))
        for (i in 0 until commonDen) {
          drawRect(
            color = if (i < partsA) BagacayBlue.copy(alpha = 0.72f) else Color.White,
            topLeft = Offset(barLeft + i * unitW, y1),
            size = Size(unitW, barH),
          )
          drawRect(
            color = InkDark.copy(alpha = 0.45f),
            topLeft = Offset(barLeft + i * unitW, y1),
            size = Size(unitW, barH),
            style = Stroke(1.dp.toPx()),
          )
        }

        // Bottom bar: Divisor B
        val y2 = y1 + barH + 22.dp.toPx()
        val lblB = textMeasurer.measure("$numB/$denB = $partsB/$commonDen", TextStyle(fontSize = 11.sp, fontWeight = FontWeight.Bold, color = CoralTangerine))
        drawText(lblB, topLeft = Offset(barLeft, y1 + barH + 5.dp.toPx()))
        for (i in 0 until commonDen) {
          drawRect(
            color = if (i < partsB) CoralTangerine.copy(alpha = 0.72f) else Color.White,
            topLeft = Offset(barLeft + i * unitW, y2),
            size = Size(unitW, barH),
          )
          drawRect(
            color = InkDark.copy(alpha = 0.45f),
            topLeft = Offset(barLeft + i * unitW, y2),
            size = Size(unitW, barH),
            style = Stroke(1.dp.toPx()),
          )
        }
      }
    }
  }
}

@Composable
fun MercuryThermometerCanvas(
  celsius: Double,
  modifier: Modifier = Modifier,
) {
  val textMeasurer = rememberTextMeasurer()
  val clamped = celsius.coerceIn(-30.0, 50.0)

  Canvas(
    modifier = modifier
      .fillMaxWidth()
      .height(176.dp)
      .clip(RoundedCornerShape(20.dp))
      .background(WarmSurfaceVariant.copy(alpha = 0.65f))
      .padding(horizontal = 12.dp, vertical = 10.dp),
  ) {
    // Horizontal thermometer with clear -30 to +50 °C scale
    val bulbCx = 36.dp.toPx()
    val cy = size.height * 0.48f
    val tubeStartX = bulbCx + 18.dp.toPx()
    val tubeEndX = size.width - 26.dp.toPx()
    val tubeW = (tubeEndX - tubeStartX).coerceAtLeast(80f)
    val tubeH = 18.dp.toPx()

    // Outer glass tube + bulb
    drawRoundRect(
      color = Color.White,
      topLeft = Offset(tubeStartX - 8.dp.toPx(), cy - tubeH / 2f),
      size = Size(tubeW + 14.dp.toPx(), tubeH),
      cornerRadius = CornerRadius(tubeH / 2f, tubeH / 2f),
    )
    drawRoundRect(
      color = InkDark,
      topLeft = Offset(tubeStartX - 8.dp.toPx(), cy - tubeH / 2f),
      size = Size(tubeW + 14.dp.toPx(), tubeH),
      cornerRadius = CornerRadius(tubeH / 2f, tubeH / 2f),
      style = Stroke(width = 2.dp.toPx()),
    )

    val minT = -30.0
    val maxT = 50.0
    val frac = ((clamped - minT) / (maxT - minT)).toFloat().coerceIn(0f, 1f)
    val mercuryTipX = tubeStartX + frac * tubeW

    // Mercury column
    drawRoundRect(
      color = CrimsonRed,
      topLeft = Offset(bulbCx, cy - tubeH * 0.28f),
      size = Size((mercuryTipX - bulbCx).coerceAtLeast(4f), tubeH * 0.56f),
      cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx()),
    )
    // Bulb
    drawCircle(color = CrimsonRed, radius = 17.dp.toPx(), center = Offset(bulbCx, cy))
    drawCircle(color = InkDark, radius = 17.dp.toPx(), center = Offset(bulbCx, cy), style = Stroke(2.dp.toPx()))
    drawCircle(color = Color.White.copy(alpha = 0.45f), radius = 5.dp.toPx(), center = Offset(bulbCx - 5.dp.toPx(), cy - 5.dp.toPx()))

    // Ticks every 5°C from -30 to 50, labels every 10°C
    val scaleTopY = cy - tubeH / 2f - 4.dp.toPx()
    val scaleBotY = cy + tubeH / 2f + 4.dp.toPx()
    var deg = -30
    while (deg <= 50) {
      val f = ((deg - minT) / (maxT - minT)).toFloat()
      val x = tubeStartX + f * tubeW
      val isMajor10 = deg % 10 == 0
      val tickLen = if (isMajor10) 14.dp.toPx() else 8.dp.toPx()
      drawLine(
        color = if (deg == 0) BagacayBlue else InkDark,
        start = Offset(x, scaleTopY),
        end = Offset(x, scaleTopY - tickLen),
        strokeWidth = if (isMajor10) 2.2.dp.toPx() else 1.2.dp.toPx(),
      )
      drawLine(
        color = if (deg == 0) BagacayBlue else InkDark.copy(alpha = 0.65f),
        start = Offset(x, scaleBotY),
        end = Offset(x, scaleBotY + tickLen * 0.7f),
        strokeWidth = if (isMajor10) 2.dp.toPx() else 1.1.dp.toPx(),
      )
      if (isMajor10) {
        val m = textMeasurer.measure(
          text = "$deg",
          style = TextStyle(
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = if (deg == 0) BagacayBlue else InkDark,
          ),
        )
        drawText(m, topLeft = Offset(x - m.size.width / 2f, scaleTopY - tickLen - m.size.height - 2.dp.toPx()))
      }
      deg += 5
    }

    // Unit badge °C on right
    val unitM = textMeasurer.measure("°C", TextStyle(fontSize = 13.sp, fontWeight = FontWeight.Bold, color = InkDark))
    drawText(unitM, topLeft = Offset(tubeEndX + 4.dp.toPx(), cy - unitM.size.height / 2f))

    // Pointer marker at mercury tip below tube
    drawLine(
      color = CrimsonRed,
      start = Offset(mercuryTipX, cy - tubeH * 0.7f),
      end = Offset(mercuryTipX, cy + tubeH * 1.15f),
      strokeWidth = 2.dp.toPx(),
    )
  }
}

@Composable
fun DigitalThermometerGraphic(
  readoutText: String,
  modifier: Modifier = Modifier,
) {
  Row(
    modifier = modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.Center,
    verticalAlignment = Alignment.CenterVertically,
  ) {
    // Probe tip
    Box(
      modifier = Modifier
        .width(28.dp)
        .height(14.dp)
        .clip(RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp))
        .background(Color(0xFFB0BEC5))
        .border(1.5.dp, Color(0xFF607D8B), RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp)),
    )
    // Digital body
    Surface(
      color = BagacayBlueLight,
      shape = RoundedCornerShape(24.dp),
      modifier = Modifier
        .width(220.dp)
        .border(2.5.dp, BagacayBlue, RoundedCornerShape(24.dp)),
    ) {
      Row(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
      ) {
        // LCD Screen
        Box(
          modifier = Modifier
            .weight(1f)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFC8E6C9))
            .border(2.dp, LeafEmerald, RoundedCornerShape(12.dp))
            .padding(horizontal = 14.dp, vertical = 10.dp),
          contentAlignment = Alignment.Center,
        ) {
          Text(
            text = readoutText,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace,
            color = InkDark,
          )
        }
        Spacer(Modifier.width(10.dp))
        // Power button dot
        Box(
          modifier = Modifier
            .size(18.dp)
            .clip(RoundedCornerShape(9.dp))
            .background(CoralTangerine),
        )
      }
    }
  }
}

fun formatCelsiusValue(value: Double): String {
  val roundedInt = kotlin.math.round(value).toInt()
  return if (abs(value - roundedInt) < 0.001) {
    "$roundedInt"
  } else {
    String.format("%.1f", value)
  }
}
