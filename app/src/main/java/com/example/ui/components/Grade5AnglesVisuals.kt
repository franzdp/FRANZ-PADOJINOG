package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.hypot
import kotlin.math.sin

@Composable
fun Grade5AngleDiagram(
  diagramId: Int,
  highlightIndex: Int = 0,
  modifier: Modifier = Modifier,
) {
  val measurer = rememberTextMeasurer()
  val boxHeight = when {
    diagramId in 3107..3118 -> 165.dp
    diagramId in 3001..3009 -> 185.dp
    else -> 220.dp
  }
  Canvas(
    modifier = modifier
      .fillMaxWidth()
      .height(boxHeight)
      .clip(RoundedCornerShape(18.dp))
      .background(Color(0xFFFFFDF8))
      .border(1.5.dp, OutlineSoft, RoundedCornerShape(18.dp)),
  ) {
    val w = size.width
    val h = size.height
    when (diagramId) {
      // WS 30
      3001 -> {
        val v = Offset(w * 0.25f, h * 0.72f)
        val cPt = Offset(w * 0.62f, h * 0.20f)
        val ePt = Offset(w * 0.78f, h * 0.72f)
        drawAngleArc(v, ePt, cPt, radius = 34.dp.toPx(), color = BerryPurple.copy(alpha = 0.22f))
        drawAngleRay(v, cPt)
        drawAngleRay(v, ePt)
        drawLabeledDot(measurer, pointAlong(v, cPt, 0.76f), "C", Offset(-22f, -14f))
        drawLabeledDot(measurer, v, "H", Offset(-22f, -4f))
        drawLabeledDot(measurer, pointAlong(v, ePt, 0.76f), "E", Offset(-6f, 12f))
      }
      3002 -> {
        val v = Offset(w * 0.24f, h * 0.72f)
        val iPt = Offset(w * 0.66f, h * 0.22f)
        val kPt = Offset(w * 0.80f, h * 0.72f)
        drawAngleArc(v, kPt, iPt, radius = 36.dp.toPx(), color = BagacayBlue.copy(alpha = 0.22f))
        drawAngleRay(v, iPt)
        drawAngleRay(v, kPt)
        drawLabeledDot(measurer, pointAlong(v, iPt, 0.76f), "I", Offset(-20f, -16f))
        drawLabeledDot(measurer, v, "C", Offset(-22f, -4f))
        drawLabeledDot(measurer, pointAlong(v, kPt, 0.76f), "K", Offset(-6f, 12f))
        drawInteriorLabel(measurer, v + Offset(56.dp.toPx(), -18.dp.toPx()), "1", CoralTangerine)
      }
      3003 -> {
        val t = Offset(w * 0.22f, h * 0.76f)
        val s = Offset(w * 0.45f, h * 0.16f)
        val p = Offset(w * 0.75f, h * 0.34f)
        val hPt = Offset(w * 0.82f, h * 0.76f)
        drawAngleArc(t, p, s, radius = 38.dp.toPx(), color = CoralTangerine.copy(alpha = 0.22f))
        drawAngleRay(t, s)
        drawAngleRay(t, p)
        drawAngleRay(t, hPt)
        drawLabeledDot(measurer, pointAlong(t, s, 0.78f), "S", Offset(-22f, -10f))
        drawLabeledDot(measurer, t, "T", Offset(-22f, -4f))
        drawLabeledDot(measurer, pointAlong(t, p, 0.78f), "P", Offset(-4f, -24f))
        drawLabeledDot(measurer, pointAlong(t, hPt, 0.78f), "H", Offset(-6f, 12f))
        drawInteriorLabel(measurer, t + Offset(42.dp.toPx(), -38.dp.toPx()), "3", BerryPurple)
      }
      3004 -> {
        val q = Offset(w * 0.24f, h * 0.68f)
        val s = Offset(w * 0.72f, h * 0.22f)
        val r = Offset(w * 0.80f, h * 0.68f)
        drawAngleArc(q, r, s, radius = 34.dp.toPx(), color = LeafEmerald.copy(alpha = 0.22f))
        drawAngleRay(q, s)
        drawAngleRay(q, r)
        drawLabeledDot(measurer, pointAlong(q, s, 0.78f), "S", Offset(-18f, -20f))
        drawLabeledDot(measurer, q, "Q", Offset(-22f, -4f))
        drawLabeledDot(measurer, pointAlong(q, r, 0.78f), "R", Offset(-6f, 12f))
      }
      3005 -> {
        val x = Offset(w * 0.76f, h * 0.72f)
        val z = Offset(w * 0.26f, h * 0.26f)
        val y = Offset(w * 0.20f, h * 0.72f)
        drawAngleArc(x, z, y, radius = 34.dp.toPx(), color = MangoGold.copy(alpha = 0.30f))
        drawAngleRay(x, z)
        drawAngleRay(x, y)
        drawLabeledDot(measurer, pointAlong(x, z, 0.76f), "Z", Offset(8f, -22f))
        drawLabeledDot(measurer, x, "X", Offset(12f, -4f))
        drawLabeledDot(measurer, pointAlong(x, y, 0.76f), "Y", Offset(-6f, 12f))
        drawInteriorLabel(measurer, x + Offset(-54.dp.toPx(), -18.dp.toPx()), "6", BerryPurple)
      }
      3006 -> {
        val p = Offset(w * 0.22f, h * 0.76f)
        val n = Offset(w * 0.36f, h * 0.16f)
        val r = Offset(w * 0.72f, h * 0.32f)
        val q = Offset(w * 0.82f, h * 0.76f)
        val hl1 = highlightIndex == 1
        val hl2 = highlightIndex == 2
        drawAngleArc(p, r, n, radius = 36.dp.toPx(), color = if (hl1) CoralTangerine.copy(alpha = 0.32f) else InkMuted.copy(alpha = 0.12f))
        drawAngleArc(p, q, r, radius = 44.dp.toPx(), color = if (hl2) BagacayBlue.copy(alpha = 0.32f) else InkMuted.copy(alpha = 0.12f))
        drawAngleRay(p, n)
        drawAngleRay(p, r)
        drawAngleRay(p, q)
        drawLabeledDot(measurer, pointAlong(p, n, 0.78f), "N", Offset(-22f, -10f))
        drawLabeledDot(measurer, p, "P", Offset(-22f, -4f))
        drawLabeledDot(measurer, pointAlong(p, r, 0.78f), "R", Offset(-4f, -24f))
        drawLabeledDot(measurer, pointAlong(p, q, 0.78f), "Q", Offset(-6f, 12f))
        drawInteriorLabel(measurer, p + Offset(36.dp.toPx(), -42.dp.toPx()), "1", if (hl1) CoralTangerine else InkDark)
        drawInteriorLabel(measurer, p + Offset(56.dp.toPx(), -16.dp.toPx()), "2", if (hl2) BagacayBlue else InkDark)
      }
      3007 -> {
        val u = Offset(w * 0.42f, h * 0.72f)
        val wPt = Offset(w * 0.18f, h * 0.22f)
        val v = Offset(w * 0.84f, h * 0.72f)
        drawAngleArc(u, v, wPt, radius = 36.dp.toPx(), color = BerryPurple.copy(alpha = 0.22f))
        drawAngleRay(u, wPt)
        drawAngleRay(u, v)
        drawLabeledDot(measurer, pointAlong(u, wPt, 0.76f), "W", Offset(10f, -16f))
        drawLabeledDot(measurer, u, "U", Offset(-18f, 8f))
        drawLabeledDot(measurer, pointAlong(u, v, 0.76f), "V", Offset(-6f, 12f))
      }
      3008 -> {
        val b = Offset(w * 0.44f, h * 0.72f)
        val a = Offset(w * 0.16f, h * 0.22f)
        val c = Offset(w * 0.84f, h * 0.72f)
        drawAngleArc(b, c, a, radius = 36.dp.toPx(), color = TealOcean.copy(alpha = 0.22f))
        drawAngleRay(b, a)
        drawAngleRay(b, c)
        drawLabeledDot(measurer, pointAlong(b, a, 0.76f), "A", Offset(10f, -16f))
        drawLabeledDot(measurer, b, "B", Offset(-16f, 8f))
        drawLabeledDot(measurer, pointAlong(b, c, 0.76f), "C", Offset(-6f, 12f))
        drawInteriorLabel(measurer, b + Offset(8.dp.toPx(), -42.dp.toPx()), "9", TealOcean)
      }
      3009 -> {
        val m = Offset(w * 0.46f, h * 0.76f)
        val l = Offset(w * 0.16f, h * 0.26f)
        val o = Offset(w * 0.68f, h * 0.18f)
        val n = Offset(w * 0.84f, h * 0.76f)
        val hl3 = highlightIndex == 1
        val hl4 = highlightIndex == 2
        drawAngleArc(m, o, l, radius = 38.dp.toPx(), color = if (hl3) CoralTangerine.copy(alpha = 0.30f) else InkMuted.copy(alpha = 0.12f))
        drawAngleArc(m, n, o, radius = 46.dp.toPx(), color = if (hl4) BagacayBlue.copy(alpha = 0.30f) else InkMuted.copy(alpha = 0.12f))
        drawAngleRay(m, l)
        drawAngleRay(m, o)
        drawAngleRay(m, n)
        drawLabeledDot(measurer, pointAlong(m, l, 0.76f), "L", Offset(-22f, 2f))
        drawLabeledDot(measurer, m, "M", Offset(-8f, 12f))
        drawLabeledDot(measurer, pointAlong(m, o, 0.76f), "O", Offset(10f, -8f))
        drawLabeledDot(measurer, pointAlong(m, n, 0.76f), "N", Offset(-6f, 12f))
        drawInteriorLabel(measurer, m + Offset(-12.dp.toPx(), -42.dp.toPx()), "3", if (hl3) CoralTangerine else InkDark)
        drawInteriorLabel(measurer, m + Offset(40.dp.toPx(), -26.dp.toPx()), "4", if (hl4) BagacayBlue else InkDark)
      }

      // WS 31 K-E-N-I at vertex R
      3100 -> {
        val r = Offset(w * 0.20f, h * 0.80f)
        val k = Offset(w * 0.24f, h * 0.12f)
        val e = Offset(w * 0.56f, h * 0.18f)
        val n = Offset(w * 0.82f, h * 0.44f)
        val i = Offset(w * 0.86f, h * 0.80f)
        when (highlightIndex) {
          1 -> drawAngleArc(r, e, k, 40.dp.toPx(), BagacayBlue.copy(alpha = 0.30f))
          2 -> drawAngleArc(r, n, e, 46.dp.toPx(), CoralTangerine.copy(alpha = 0.30f))
          3 -> drawAngleArc(r, i, n, 52.dp.toPx(), LeafEmerald.copy(alpha = 0.30f))
          4 -> drawAngleArc(r, n, k, 58.dp.toPx(), BerryPurple.copy(alpha = 0.26f))
          5 -> drawAngleArc(r, i, e, 64.dp.toPx(), TealOcean.copy(alpha = 0.26f))
          6 -> drawAngleArc(r, i, k, 70.dp.toPx(), MangoGold.copy(alpha = 0.32f))
        }
        drawAngleRay(r, k)
        drawAngleRay(r, e)
        drawAngleRay(r, n)
        drawAngleRay(r, i)
        drawLabeledDot(measurer, pointAlong(r, k, 0.78f), "K", Offset(-22f, -10f))
        drawLabeledDot(measurer, pointAlong(r, e, 0.78f), "E", Offset(-20f, -18f))
        drawLabeledDot(measurer, pointAlong(r, n, 0.78f), "N", Offset(-8f, -24f))
        drawLabeledDot(measurer, pointAlong(r, i, 0.78f), "I", Offset(-6f, 12f))
        drawLabeledDot(measurer, r, "R", Offset(-22f, -4f))
        drawInteriorLabel(measurer, r + Offset(42.dp.toPx(), -42.dp.toPx()), "4", CoralTangerine)
        drawInteriorLabel(measurer, r + Offset(64.dp.toPx(), -14.dp.toPx()), "5", LeafEmerald)
      }

      // WS 31 Single angle classification (#7..#18 -> diagramId 3107..3118)
      in 3107..3118 -> drawSingleClassifyAngle(diagramId, w, h)

      // WS 31 WYUFMC intersecting diagram (#19..#27 -> diagramId 3119)
      3119 -> {
        val e = Offset(w * 0.28f, h * 0.68f)
        val y = Offset(w * 0.68f, h * 0.68f)
        val wPt = Offset(w * 0.08f, h * 0.68f)
        val u = Offset(w * 0.90f, h * 0.68f)
        val r = Offset(w * 0.50f, h * 0.34f)
        val c = Offset(w * 0.80f, h * 0.14f)
        val f = Offset(w * 0.32f, h * 0.14f)
        val m = Offset(w * 0.82f, h * 0.86f)
        // Line W-E-Y-U (horizontal)
        drawLineWithArrowEnds(wPt, u)
        // Line E-R-C (slanted up-right) — make E, R, C collinear: extend from below E to C
        val ecStart = Offset(e.x - (r.x - e.x) * 0.45f, e.y - (r.y - e.y) * 0.45f)
        val ecEnd = Offset(r.x + (r.x - e.x) * 1.15f, r.y + (r.y - e.y) * 1.15f)
        drawLineWithArrowEnds(ecStart, ecEnd)
        // Perpendicular line through R & Y (slope perpendicular to E->R):
        val dx = r.x - e.x
        val dy = r.y - e.y
        val len = hypot(dx, dy).coerceAtLeast(1f)
        val px = -dy / len
        val py = dx / len
        // Align Y on horizontal line W-U where M-R-Y-F crosses y = e.y
        val tY = (e.y - r.y) / py
        val yCross = Offset(r.x + px * tY, e.y)
        val fTop = Offset(r.x - px * 58.dp.toPx(), r.y - py * 58.dp.toPx())
        val mBot = Offset(yCross.x + px * 32.dp.toPx(), yCross.y + py * 32.dp.toPx())
        drawLineWithArrowEnds(fTop, mBot)

        // Right-angle square at R between R->C and R->Y
        drawRightAngleMarker(
          vertex = r,
          dirA = Offset(dx / len, dy / len),
          dirB = Offset(px, py),
          sizePx = 13.dp.toPx(),
        )

        drawLabeledDot(measurer, Offset(w * 0.14f, e.y), "W", Offset(-6f, 10f))
        drawLabeledDot(measurer, e, "E", Offset(-18f, 10f))
        drawLabeledDot(measurer, yCross, "Y", Offset(8f, 10f))
        drawLabeledDot(measurer, Offset(w * 0.86f, e.y), "U", Offset(-4f, 10f))
        drawLabeledDot(measurer, r, "R", Offset(-22f, -12f))
        drawLabeledDot(measurer, pointAlong(r, ecEnd, 0.72f), "C", Offset(6f, -14f))
        drawLabeledDot(measurer, pointAlong(r, fTop, 0.75f), "F", Offset(-20f, -10f))
        drawLabeledDot(measurer, pointAlong(yCross, mBot, 0.72f), "M", Offset(8f, -6f))
      }

      // WS 32 Figure 1: APSDBVQT (all lines intersect at S)
      3201 -> {
        val s = Offset(w * 0.50f, h * 0.52f)
        val rx = w * 0.36f
        val ry = h * 0.38f
        drawLineWithArrowEnds(Offset(s.x - rx, s.y), Offset(s.x + rx, s.y)) // P-S-Q
        drawLineWithArrowEnds(Offset(s.x, s.y - ry), Offset(s.x, s.y + ry)) // T-S-V
        drawLineWithArrowEnds(Offset(s.x - rx * 0.75f, s.y - ry * 0.85f), Offset(s.x + rx * 0.75f, s.y + ry * 0.85f)) // A-S-C
        drawLineWithArrowEnds(Offset(s.x + rx * 0.75f, s.y - ry * 0.85f), Offset(s.x - rx * 0.75f, s.y + ry * 0.85f)) // B-S-D
        drawRightAngleMarker(s, Offset(1f, 0f), Offset(0f, 1f), 13.dp.toPx())
        drawLabeledDot(measurer, Offset(s.x - rx * 0.82f, s.y), "P", Offset(-8f, 10f))
        drawLabeledDot(measurer, Offset(s.x + rx * 0.82f, s.y), "Q", Offset(-4f, 10f))
        drawLabeledDot(measurer, Offset(s.x, s.y - ry * 0.82f), "T", Offset(8f, -10f))
        drawLabeledDot(measurer, Offset(s.x, s.y + ry * 0.82f), "V", Offset(8f, -8f))
        drawLabeledDot(measurer, Offset(s.x - rx * 0.60f, s.y - ry * 0.68f), "A", Offset(-20f, -12f))
        drawLabeledDot(measurer, Offset(s.x + rx * 0.60f, s.y + ry * 0.68f), "C", Offset(8f, -8f))
        drawLabeledDot(measurer, Offset(s.x + rx * 0.60f, s.y - ry * 0.68f), "B", Offset(8f, -14f))
        drawLabeledDot(measurer, Offset(s.x - rx * 0.60f, s.y + ry * 0.68f), "D", Offset(-22f, -6f))
        drawLabeledDot(measurer, s, "S", Offset(-20f, 8f))
      }

      // WS 32 Figure 2: TPQDSABV
      3202 -> {
        val s = Offset(w * 0.50f, h * 0.52f)
        val rx = w * 0.36f
        val ry = h * 0.38f
        drawLineWithArrowEnds(Offset(s.x - rx, s.y), Offset(s.x + rx, s.y)) // P-S-Q
        drawAngleRay(s, Offset(s.x, s.y + ry)) // S-V downward
        drawLineWithArrowEnds(Offset(s.x - rx * 0.72f, s.y - ry * 0.85f), Offset(s.x + rx * 0.72f, s.y + ry * 0.85f)) // D-S-A
        drawLineWithArrowEnds(Offset(s.x + rx * 0.72f, s.y - ry * 0.85f), Offset(s.x - rx * 0.72f, s.y + ry * 0.85f)) // B-S-T
        drawRightAngleMarker(s, Offset(1f, 0f), Offset(0f, 1f), 13.dp.toPx())
        drawLabeledDot(measurer, Offset(s.x - rx * 0.82f, s.y), "P", Offset(-8f, -22f))
        drawLabeledDot(measurer, Offset(s.x + rx * 0.82f, s.y), "Q", Offset(-4f, -22f))
        drawLabeledDot(measurer, Offset(s.x, s.y + ry * 0.82f), "V", Offset(8f, -8f))
        drawLabeledDot(measurer, Offset(s.x - rx * 0.58f, s.y - ry * 0.68f), "D", Offset(-20f, -12f))
        drawLabeledDot(measurer, Offset(s.x + rx * 0.58f, s.y + ry * 0.68f), "A", Offset(8f, -8f))
        drawLabeledDot(measurer, Offset(s.x + rx * 0.58f, s.y - ry * 0.68f), "B", Offset(8f, -14f))
        drawLabeledDot(measurer, Offset(s.x - rx * 0.58f, s.y + ry * 0.68f), "T", Offset(-22f, -6f))
        drawLabeledDot(measurer, s, "S", Offset(-20f, -22f))
      }

      // WS 32 Figure 3: EPQABMRD
      3203 -> {
        val a = Offset(w * 0.42f, h * 0.68f)
        val r = Offset(w * 0.58f, h * 0.40f)
        val dx = r.x - a.x
        val dy = r.y - a.y
        val len = hypot(dx, dy).coerceAtLeast(1f)
        val ux = dx / len
        val uy = dy / len
        val px = -uy
        val py = ux
        val mStart = Offset(a.x - ux * 34.dp.toPx(), a.y - uy * 34.dp.toPx())
        val bEnd = Offset(r.x + ux * 58.dp.toPx(), r.y + uy * 58.dp.toPx())
        val eStart = Offset(r.x - px * 62.dp.toPx(), r.y - py * 62.dp.toPx())
        val dEnd = Offset(r.x + px * 62.dp.toPx(), r.y + py * 62.dp.toPx())
        drawLineWithArrowEnds(Offset(w * 0.10f, a.y), Offset(w * 0.90f, a.y)) // P-A-Q
        drawLineWithArrowEnds(mStart, bEnd) // M-A-R-B
        drawLineWithArrowEnds(eStart, dEnd) // E-R-D perp at R
        drawRightAngleMarker(r, Offset(ux, uy), Offset(-px, -py), 13.dp.toPx())
        drawLabeledDot(measurer, Offset(w * 0.18f, a.y), "P", Offset(-6f, 10f))
        drawLabeledDot(measurer, a, "A", Offset(10f, 8f))
        drawLabeledDot(measurer, Offset(w * 0.82f, a.y), "Q", Offset(-6f, 10f))
        drawLabeledDot(measurer, pointAlong(a, mStart, 0.72f), "M", Offset(-22f, -6f))
        drawLabeledDot(measurer, r, "R", Offset(-20f, -14f))
        drawLabeledDot(measurer, pointAlong(r, bEnd, 0.72f), "B", Offset(6f, -14f))
        drawLabeledDot(measurer, pointAlong(r, eStart, 0.72f), "E", Offset(-20f, -10f))
        drawLabeledDot(measurer, pointAlong(r, dEnd, 0.72f), "D", Offset(8f, -6f))
      }

      // WS 32 Figure 4: HKLTGSNJ
      3204 -> {
        val t = Offset(w * 0.36f, h * 0.68f)
        val s = Offset(w * 0.56f, h * 0.38f)
        val dx = s.x - t.x
        val dy = s.y - t.y
        val len = hypot(dx, dy).coerceAtLeast(1f)
        val ux = dx / len
        val uy = dy / len
        val px = -uy
        val py = ux
        val gStart = Offset(t.x - ux * 32.dp.toPx(), t.y - uy * 32.dp.toPx())
        val nEnd = Offset(s.x + ux * 58.dp.toPx(), s.y + uy * 58.dp.toPx())
        val hStart = Offset(s.x - px * 62.dp.toPx(), s.y - py * 62.dp.toPx())
        val jEnd = Offset(s.x + px * 62.dp.toPx(), s.y + py * 62.dp.toPx())
        drawLineWithArrowEnds(Offset(w * 0.10f, t.y), Offset(w * 0.90f, t.y)) // K-T-L
        drawLineWithArrowEnds(gStart, nEnd) // G-T-S-N
        drawLineWithArrowEnds(hStart, jEnd) // H-S-J perp at S
        drawRightAngleMarker(s, Offset(ux, uy), Offset(-px, -py), 13.dp.toPx())
        drawLabeledDot(measurer, Offset(w * 0.16f, t.y), "K", Offset(-6f, 10f))
        drawLabeledDot(measurer, t, "T", Offset(10f, 8f))
        drawLabeledDot(measurer, Offset(w * 0.84f, t.y), "L", Offset(-6f, 10f))
        drawLabeledDot(measurer, pointAlong(t, gStart, 0.72f), "G", Offset(-22f, -6f))
        drawLabeledDot(measurer, s, "S", Offset(-20f, -14f))
        drawLabeledDot(measurer, pointAlong(s, nEnd, 0.72f), "N", Offset(6f, -14f))
        drawLabeledDot(measurer, pointAlong(s, hStart, 0.72f), "H", Offset(-20f, -10f))
        drawLabeledDot(measurer, pointAlong(s, jEnd, 0.72f), "J", Offset(8f, -6f))
      }
    }
  }
}

private fun DrawScope.drawSingleClassifyAngle(diagramId: Int, w: Float, h: Float) {
  // 7,9,10,12,15,16,18 = Acute; 8,13,17 = Right; 11,14 = Obtuse
  val (deg, rotateBase, isRight) = when (diagramId) {
    3107 -> Triple(42f, 0f, false)
    3108 -> Triple(90f, 0f, true)
    3109 -> Triple(56f, -15f, false)
    3110 -> Triple(74f, 0f, false)
    3111 -> Triple(128f, 0f, false)
    3112 -> Triple(35f, 10f, false)
    3113 -> Triple(90f, -20f, true)
    3114 -> Triple(142f, -10f, false)
    3115 -> Triple(64f, 180f, false)
    3116 -> Triple(78f, -12f, false)
    3117 -> Triple(90f, 15f, true)
    else -> Triple(28f, 0f, false)
  }
  val vertex = when {
    deg > 100f -> Offset(w * 0.44f, h * 0.74f)
    rotateBase >= 120f -> Offset(w * 0.70f, h * 0.66f)
    else -> Offset(w * 0.30f, h * 0.74f)
  }
  val armLen = minOf(w * 0.42f, h * 0.56f)
  val radA = Math.toRadians(rotateBase.toDouble())
  val radB = Math.toRadians((rotateBase - deg).toDouble())
  val endA = Offset(vertex.x + (armLen * cos(radA)).toFloat(), vertex.y + (armLen * sin(radA)).toFloat())
  val endB = Offset(vertex.x + (armLen * cos(radB)).toFloat(), vertex.y + (armLen * sin(radB)).toFloat())

  if (isRight) {
    drawRightAngleMarker(
      vertex = vertex,
      dirA = Offset(cos(radA).toFloat(), sin(radA).toFloat()),
      dirB = Offset(cos(radB).toFloat(), sin(radB).toFloat()),
      sizePx = 16.dp.toPx(),
    )
  } else {
    drawAngleArc(vertex, endA, endB, radius = 28.dp.toPx(), color = BerryPurple.copy(alpha = 0.25f))
  }
  drawAngleRay(vertex, endA)
  drawAngleRay(vertex, endB)
  drawCircle(InkDark, 4.5.dp.toPx(), vertex)
}

private fun pointAlong(a: Offset, b: Offset, t: Float): Offset =
  Offset(a.x + (b.x - a.x) * t, a.y + (b.y - a.y) * t)

private fun DrawScope.drawLabeledDot(measurer: TextMeasurer, pt: Offset, label: String, offset: Offset) {
  drawCircle(InkDark, 4.5.dp.toPx(), pt)
  drawText(
    textMeasurer = measurer,
    text = label,
    topLeft = pt + offset,
    style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold, color = BagacayBlue),
  )
}

private fun DrawScope.drawInteriorLabel(measurer: TextMeasurer, pos: Offset, label: String, color: Color) {
  drawText(
    textMeasurer = measurer,
    text = label,
    topLeft = pos,
    style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Bold, color = color),
  )
}

private fun DrawScope.drawAngleRay(start: Offset, end: Offset) {
  drawLine(InkDark, start, end, strokeWidth = 2.8.dp.toPx(), cap = StrokeCap.Round)
  drawCircle(InkDark, 4.5.dp.toPx(), start)
  drawArrowHead(start, end)
}

private fun DrawScope.drawLineWithArrowEnds(a: Offset, b: Offset) {
  drawLine(InkDark, a, b, strokeWidth = 2.6.dp.toPx(), cap = StrokeCap.Round)
  drawArrowHead(a, b)
  drawArrowHead(b, a)
}

private fun DrawScope.drawArrowHead(from: Offset, to: Offset) {
  val angle = atan2((to.y - from.y).toDouble(), (to.x - from.x).toDouble())
  val arrowLen = 10.dp.toPx()
  val spread = 0.44
  val p1 = Offset(to.x - (arrowLen * cos(angle - spread)).toFloat(), to.y - (arrowLen * sin(angle - spread)).toFloat())
  val p2 = Offset(to.x - (arrowLen * cos(angle + spread)).toFloat(), to.y - (arrowLen * sin(angle + spread)).toFloat())
  val path = Path().apply {
    moveTo(to.x, to.y)
    lineTo(p1.x, p1.y)
    lineTo(p2.x, p2.y)
    close()
  }
  drawPath(path, InkDark)
}

private fun DrawScope.drawRightAngleMarker(vertex: Offset, dirA: Offset, dirB: Offset, sizePx: Float) {
  val p1 = vertex + dirA * sizePx
  val p2 = p1 + dirB * sizePx
  val p3 = vertex + dirB * sizePx
  val boxPath = Path().apply {
    moveTo(vertex.x, vertex.y)
    lineTo(p1.x, p1.y)
    lineTo(p2.x, p2.y)
    lineTo(p3.x, p3.y)
    close()
  }
  drawPath(boxPath, LeafEmerald.copy(alpha = 0.22f))
  drawLine(LeafEmerald, p1, p2, strokeWidth = 2.dp.toPx())
  drawLine(LeafEmerald, p2, p3, strokeWidth = 2.dp.toPx())
}

private fun DrawScope.drawAngleArc(vertex: Offset, pStart: Offset, pEnd: Offset, radius: Float, color: Color) {
  var a1 = Math.toDegrees(atan2((pStart.y - vertex.y).toDouble(), (pStart.x - vertex.x).toDouble())).toFloat()
  var a2 = Math.toDegrees(atan2((pEnd.y - vertex.y).toDouble(), (pEnd.x - vertex.x).toDouble())).toFloat()
  var sweep = a2 - a1
  while (sweep > 180f) sweep -= 360f
  while (sweep < -180f) sweep += 360f
  val topLeft = Offset(vertex.x - radius, vertex.y - radius)
  val arcSize = Size(radius * 2f, radius * 2f)
  drawArc(color = color, startAngle = a1, sweepAngle = sweep, useCenter = true, topLeft = topLeft, size = arcSize)
  drawArc(color = color.copy(alpha = 0.85f), startAngle = a1, sweepAngle = sweep, useCenter = false, topLeft = topLeft, size = arcSize, style = Stroke(2.dp.toPx()))
}
