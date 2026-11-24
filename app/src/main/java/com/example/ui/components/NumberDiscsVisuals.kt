package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.DiscCounts
import com.example.model.PatternToken
import com.example.model.PatternTokenKind
import com.example.ui.theme.*
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

enum class DiscDenom(val value: Int, val label: String, val fill: Color) {
  FIFTY_K(50000, "50,000", BerryPurple),
  TEN_K(10000, "10,000", CoralTangerine),
  ONE_K(1000, "1,000", BagacayBlue),
  HUNDRED(100, "100", LeafEmerald),
  TEN(10, "10", MangoGold),
  ONE(1, "1", TealOcean),
}

@Composable
fun NumberDiscView(
  denom: DiscDenom,
  size: Dp = 54.dp,
  modifier: Modifier = Modifier,
) {
  Box(
    modifier = modifier.size(size),
    contentAlignment = Alignment.Center,
  ) {
    Canvas(modifier = Modifier.fillMaxSize()) {
      val w = this.size.width
      val h = this.size.height
      val cx = w / 2f
      val cy = h / 2f
      val r = minOf(w, h) * 0.44f
      val stroke = Stroke(width = 2.5.dp.toPx())
      val path = Path()
      when (denom) {
        DiscDenom.FIFTY_K -> {
          // Octagon
          for (i in 0 until 8) {
            val a = (i * 45.0 + 22.5) * PI / 180.0
            val px = cx + (r * cos(a)).toFloat()
            val py = cy + (r * sin(a)).toFloat()
            if (i == 0) path.moveTo(px, py) else path.lineTo(px, py)
          }
          path.close()
        }
        DiscDenom.TEN_K -> {
          // 8-point starburst
          for (i in 0 until 16) {
            val rad = if (i % 2 == 0) r else r * 0.72f
            val a = i * 22.5 * PI / 180.0
            val px = cx + (rad * cos(a)).toFloat()
            val py = cy + (rad * sin(a)).toFloat()
            if (i == 0) path.moveTo(px, py) else path.lineTo(px, py)
          }
          path.close()
        }
        DiscDenom.ONE_K -> {
          // Hexagon
          for (i in 0 until 6) {
            val a = i * 60.0 * PI / 180.0
            val px = cx + (r * cos(a)).toFloat()
            val py = cy + (r * 0.85f * sin(a)).toFloat()
            if (i == 0) path.moveTo(px, py) else path.lineTo(px, py)
          }
          path.close()
        }
        DiscDenom.HUNDRED -> {
          // Rectangle / square
          val half = r * 0.86f
          drawRect(denom.fill.copy(alpha = 0.2f), Offset(cx - half, cy - half * 0.8f), Size(half * 2, half * 1.6f))
          drawRect(denom.fill, Offset(cx - half, cy - half * 0.8f), Size(half * 2, half * 1.6f), style = stroke)
          return@Canvas
        }
        DiscDenom.TEN -> {
          // Trapezoid
          path.moveTo(cx - r * 0.58f, cy - r * 0.65f)
          path.lineTo(cx + r * 0.58f, cy - r * 0.65f)
          path.lineTo(cx + r * 0.92f, cy + r * 0.65f)
          path.lineTo(cx - r * 0.92f, cy + r * 0.65f)
          path.close()
        }
        DiscDenom.ONE -> {
          drawCircle(denom.fill.copy(alpha = 0.2f), radius = r * 0.82f, center = Offset(cx, cy))
          drawCircle(denom.fill, radius = r * 0.82f, center = Offset(cx, cy), style = stroke)
          return@Canvas
        }
      }
      drawPath(path, color = denom.fill.copy(alpha = 0.2f), style = Fill)
      drawPath(path, color = denom.fill, style = stroke)
    }
    Text(
      text = denom.label,
      fontSize = when (denom) {
        DiscDenom.FIFTY_K, DiscDenom.TEN_K -> 10.sp
        DiscDenom.ONE_K -> 11.sp
        else -> 13.sp
      },
      fontWeight = FontWeight.Bold,
      color = InkDark,
    )
  }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DiscCollectionTray(
  counts: DiscCounts,
  modifier: Modifier = Modifier,
) {
  FlowRow(
    modifier = modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.spacedBy(6.dp),
    verticalArrangement = Arrangement.spacedBy(6.dp),
  ) {
    repeat(counts.fiftyThousands) { NumberDiscView(DiscDenom.FIFTY_K) }
    repeat(counts.tenThousands) { NumberDiscView(DiscDenom.TEN_K) }
    repeat(counts.thousands) { NumberDiscView(DiscDenom.ONE_K) }
    repeat(counts.hundreds) { NumberDiscView(DiscDenom.HUNDRED) }
    repeat(counts.tens) { NumberDiscView(DiscDenom.TEN) }
    repeat(counts.ones) { NumberDiscView(DiscDenom.ONE) }
  }
}

@Composable
fun PatternTokenBadge(
  token: PatternToken,
  isHighlighted: Boolean = false,
  modifier: Modifier = Modifier,
) {
  when (token.kind) {
    PatternTokenKind.TEXT -> {
      Text(
        text = token.text,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        color = InkDark,
        modifier = modifier.padding(horizontal = 6.dp, vertical = 4.dp),
      )
    }
    PatternTokenKind.BLANK -> {
      Text(
        text = if (token.text.isBlank()) "?" else token.text,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        color = if (isHighlighted) BagacayBlue else CoralTangerine,
        modifier = modifier.padding(horizontal = 8.dp, vertical = 4.dp),
      )
    }
    else -> {
      Canvas(modifier = modifier.size(30.dp)) {
        val w = size.width
        val h = size.height
        val cx = w / 2f
        val cy = h / 2f
        val r = minOf(w, h) * 0.4f
        val stroke = Stroke(width = 2.4.dp.toPx())
        when (token.kind) {
          PatternTokenKind.FILLED_TRIANGLE, PatternTokenKind.OUTLINE_TRIANGLE -> {
            val p = Path().apply {
              moveTo(cx, cy - r)
              lineTo(cx + r, cy + r * 0.85f)
              lineTo(cx - r, cy + r * 0.85f)
              close()
            }
            if (token.kind == PatternTokenKind.FILLED_TRIANGLE) drawPath(p, BagacayBlue)
            else drawPath(p, BagacayBlue, style = stroke)
          }
          PatternTokenKind.FILLED_SQUARE, PatternTokenKind.OUTLINE_SQUARE -> {
            val tl = Offset(cx - r * 0.85f, cy - r * 0.85f)
            val sz = Size(r * 1.7f, r * 1.7f)
            if (token.kind == PatternTokenKind.FILLED_SQUARE) drawRect(CoralTangerine, tl, sz)
            else drawRect(CoralTangerine, tl, sz, style = stroke)
          }
          PatternTokenKind.FILLED_STAR, PatternTokenKind.OUTLINE_STAR -> {
            val p = Path()
            for (i in 0 until 10) {
              val rad = if (i % 2 == 0) r else r * 0.44f
              val a = (i * 36.0 - 90.0) * PI / 180.0
              val px = cx + (rad * cos(a)).toFloat()
              val py = cy + (rad * sin(a)).toFloat()
              if (i == 0) p.moveTo(px, py) else p.lineTo(px, py)
            }
            p.close()
            if (token.kind == PatternTokenKind.FILLED_STAR) drawPath(p, MangoGold)
            else drawPath(p, MangoGold, style = stroke)
          }
          PatternTokenKind.APPLE -> {
            drawCircle(CrimsonRed, r * 0.78f, Offset(cx, cy + r * 0.1f))
            drawLine(LeafEmerald, Offset(cx, cy - r * 0.65f), Offset(cx + r * 0.3f, cy - r * 0.95f), strokeWidth = 2.5.dp.toPx())
          }
          PatternTokenKind.COOKIE -> {
            drawCircle(Color(0xFFD7A86E), r * 0.82f, Offset(cx, cy))
            drawCircle(Color(0xFF5D4037), r * 0.14f, Offset(cx - r * 0.3f, cy - r * 0.2f))
            drawCircle(Color(0xFF5D4037), r * 0.14f, Offset(cx + r * 0.28f, cy + r * 0.2f))
            drawCircle(Color(0xFF5D4037), r * 0.14f, Offset(cx - r * 0.15f, cy + r * 0.32f))
          }
          PatternTokenKind.FILLED_HEART, PatternTokenKind.OUTLINE_HEART -> {
            val p = Path().apply {
              moveTo(cx, cy + r * 0.75f)
              cubicTo(cx - r * 1.2f, cy, cx - r * 0.9f, cy - r * 0.9f, cx, cy - r * 0.25f)
              cubicTo(cx + r * 0.9f, cy - r * 0.9f, cx + r * 1.2f, cy, cx, cy + r * 0.75f)
            }
            if (token.kind == PatternTokenKind.FILLED_HEART) drawPath(p, CrimsonRed)
            else drawPath(p, CrimsonRed, style = stroke)
          }
          PatternTokenKind.FLOWER -> {
            for (i in 0 until 6) {
              val a = i * 60.0 * PI / 180.0
              drawCircle(BerryPurple.copy(alpha = 0.7f), r * 0.34f, Offset(cx + (r * 0.48f * cos(a)).toFloat(), cy + (r * 0.48f * sin(a)).toFloat()))
            }
            drawCircle(MangoGold, r * 0.28f, Offset(cx, cy))
          }
          PatternTokenKind.LEAF -> {
            val p = Path().apply {
              moveTo(cx - r * 0.7f, cy + r * 0.6f)
              quadraticTo(cx - r * 0.5f, cy - r * 0.8f, cx + r * 0.8f, cy - r * 0.7f)
              quadraticTo(cx + r * 0.6f, cy + r * 0.7f, cx - r * 0.7f, cy + r * 0.6f)
            }
            drawPath(p, LeafEmerald)
          }
          PatternTokenKind.OUTLINE_CIRCLE -> {
            drawCircle(TealOcean, r * 0.8f, Offset(cx, cy), style = stroke)
          }
          else -> {}
        }
      }
    }
  }
}
