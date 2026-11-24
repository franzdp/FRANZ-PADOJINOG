package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.CongruentSubType
import com.example.model.GeoFigureType
import com.example.ui.theme.*
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun GeoFigureCanvas(
  figure: GeoFigureType,
  modifier: Modifier = Modifier,
) {
  val measurer = rememberTextMeasurer()
  Canvas(
    modifier = modifier
      .fillMaxWidth()
      .height(140.dp)
      .clip(RoundedCornerShape(18.dp))
      .background(Color(0xFFFFFDF8))
      .border(1.5.dp, OutlineSoft, RoundedCornerShape(18.dp)),
  ) {
    val w = size.width
    val h = size.height
    val cx = w / 2f
    val cy = h / 2f
    val dx = w * 0.28f
    val dy = h * 0.26f

    when (figure) {
      GeoFigureType.POINT_D -> drawPointWithLabel(measurer, Offset(cx, cy), "D", Offset(-6f, 16f))
      GeoFigureType.POINT_A -> drawPointWithLabel(measurer, Offset(cx, cy), "A", Offset(-6f, 16f))
      GeoFigureType.POINT_F -> drawPointWithLabel(measurer, Offset(cx, cy), "F", Offset(-6f, -26f))
      GeoFigureType.POINT_P -> drawPointWithLabel(measurer, Offset(cx, cy), "P", Offset(14f, -10f))
      GeoFigureType.RAY_RIGHT -> drawRay(Offset(cx - dx, cy), Offset(cx + dx, cy))
      GeoFigureType.SEGMENT_HORIZ -> drawSegment(Offset(cx - dx, cy), Offset(cx + dx, cy))
      GeoFigureType.LINE_HORIZ -> drawLineWithArrows(Offset(cx - dx, cy), Offset(cx + dx, cy))
      GeoFigureType.RAY_DOWN_LEFT -> drawRay(Offset(cx + dx * 0.7f, cy - dy), Offset(cx - dx * 0.7f, cy + dy))
      GeoFigureType.SEGMENT_TILTED -> drawSegment(Offset(cx - dx * 0.35f, cy - dy), Offset(cx + dx * 0.35f, cy + dy))
      GeoFigureType.LINE_DIAG -> drawLineWithArrows(Offset(cx - dx * 0.75f, cy + dy), Offset(cx + dx * 0.75f, cy - dy))
      GeoFigureType.RAY_MN -> {
        val a = Offset(cx - dx * 0.75f, cy + dy * 0.8f)
        val b = Offset(cx + dx * 0.75f, cy - dy * 0.8f)
        drawRay(a, b)
        drawPointWithLabel(measurer, a, "M", Offset(-22f, -6f))
        drawPointWithLabel(measurer, Offset((a.x + b.x) / 2f, (a.y + b.y) / 2f), "N", Offset(-12f, -26f))
      }
      GeoFigureType.LINE_QR -> {
        val a = Offset(cx - dx, cy)
        val b = Offset(cx + dx, cy)
        drawLineWithArrows(a, b)
        drawPointWithLabel(measurer, Offset(cx - dx * 0.45f, cy), "Q", Offset(-6f, -26f))
        drawPointWithLabel(measurer, Offset(cx + dx * 0.45f, cy), "R", Offset(-6f, -26f))
      }
      GeoFigureType.SEGMENT_ST -> {
        val a = Offset(cx - dx * 0.8f, cy - dy * 0.4f)
        val b = Offset(cx + dx * 0.8f, cy + dy * 0.4f)
        drawSegment(a, b)
        drawPointWithLabel(measurer, a, "S", Offset(-20f, -12f))
        drawPointWithLabel(measurer, b, "T", Offset(8f, -24f))
      }
      GeoFigureType.PAIR_PARALLEL_TILT_DOWN -> {
        drawLineWithArrows(Offset(cx - dx, cy - 28f), Offset(cx + dx, cy - 4f))
        drawLineWithArrows(Offset(cx - dx, cy + 4f), Offset(cx + dx, cy + 28f))
      }
      GeoFigureType.PAIR_NOT_PARALLEL_1 -> {
        drawLineWithArrows(Offset(cx - dx, cy - 24f), Offset(cx + dx, cy - 24f))
        drawLineWithArrows(Offset(cx - dx, cy + 28f), Offset(cx + dx, cy + 4f))
      }
      GeoFigureType.PAIR_NOT_PARALLEL_2 -> {
        drawLineWithArrows(Offset(cx - dx * 0.9f, cy + dy), Offset(cx - 12f, cy - dy))
        drawLineWithArrows(Offset(cx + 12f, cy - dy), Offset(cx + dx * 0.9f, cy + dy))
      }
      GeoFigureType.PAIR_PARALLEL_TILT_UP -> {
        drawLineWithArrows(Offset(cx - dx, cy + 4f), Offset(cx + dx, cy - 28f))
        drawLineWithArrows(Offset(cx - dx, cy + 32f), Offset(cx + dx, cy))
      }
      GeoFigureType.PAIR_INTERSECT_X -> {
        drawLineWithArrows(Offset(cx - dx, cy - dy), Offset(cx + dx, cy + dy))
        drawLineWithArrows(Offset(cx - dx, cy + dy), Offset(cx + dx, cy - dy))
      }
      GeoFigureType.PAIR_PERP_CROSS -> {
        drawLineWithArrows(Offset(cx - dx, cy), Offset(cx + dx, cy))
        drawLineWithArrows(Offset(cx - 24f, cy - dy * 1.15f), Offset(cx - 24f, cy + dy * 1.15f))
      }
      GeoFigureType.PAIR_PARALLEL_HORIZ -> {
        drawLineWithArrows(Offset(cx - dx, cy - 20f), Offset(cx + dx, cy - 20f))
        drawLineWithArrows(Offset(cx - dx, cy + 20f), Offset(cx + dx, cy + 20f))
      }
      GeoFigureType.PAIR_PARALLEL_VERT -> {
        drawLineWithArrows(Offset(cx - 34f, cy - dy * 1.15f), Offset(cx - 34f, cy + dy * 1.15f))
        drawLineWithArrows(Offset(cx + 34f, cy - dy * 1.15f), Offset(cx + 34f, cy + dy * 1.15f))
      }
      GeoFigureType.PAIR_INTERSECT_SLANT -> {
        drawLineWithArrows(Offset(cx - dx, cy), Offset(cx + dx, cy))
        drawLineWithArrows(Offset(cx - 32f, cy - dy * 1.15f), Offset(cx + 32f, cy + dy * 1.15f))
      }
      GeoFigureType.PAIR_PERP_T -> {
        drawLineWithArrows(Offset(cx - dx, cy + 24f), Offset(cx + dx, cy + 24f))
        drawLineWithArrows(Offset(cx, cy - dy * 1.15f), Offset(cx, cy + 24f))
      }
    }
  }
}

@Composable
fun GeoBoxDiagram(modifier: Modifier = Modifier) {
  val measurer = rememberTextMeasurer()
  Canvas(
    modifier = modifier
      .fillMaxWidth()
      .height(190.dp)
      .clip(RoundedCornerShape(18.dp))
      .background(Color(0xFFFFFDF8))
      .border(1.5.dp, OutlineSoft, RoundedCornerShape(18.dp)),
  ) {
    val w = size.width
    val h = size.height
    drawPointWithLabel(measurer, Offset(w * 0.18f, h * 0.22f), "A", Offset(10f, -10f))
    // Line BC
    drawLineWithArrows(Offset(w * 0.34f, h * 0.18f), Offset(w * 0.72f, h * 0.18f))
    drawPointWithLabel(measurer, Offset(w * 0.43f, h * 0.18f), "B", Offset(-6f, -24f))
    drawPointWithLabel(measurer, Offset(w * 0.62f, h * 0.18f), "C", Offset(-6f, -24f))
    // Ray ED
    drawRay(Offset(w * 0.78f, h * 0.44f), Offset(w * 0.90f, h * 0.14f))
    drawPointWithLabel(measurer, Offset(w * 0.78f, h * 0.44f), "E", Offset(10f, -6f))
    drawPointWithLabel(measurer, Offset(w * 0.84f, h * 0.29f), "D", Offset(10f, -6f))
    // Point J
    drawPointWithLabel(measurer, Offset(w * 0.70f, h * 0.42f), "J", Offset(-6f, -24f))
    // Line FG
    drawLineWithArrows(Offset(w * 0.12f, h * 0.34f), Offset(w * 0.22f, h * 0.74f))
    drawPointWithLabel(measurer, Offset(w * 0.15f, h * 0.46f), "F", Offset(10f, -10f))
    drawPointWithLabel(measurer, Offset(w * 0.19f, h * 0.62f), "G", Offset(-22f, -4f))
    // Segment HI
    drawSegment(Offset(w * 0.33f, h * 0.44f), Offset(w * 0.56f, h * 0.46f))
    drawPointWithLabel(measurer, Offset(w * 0.33f, h * 0.44f), "H", Offset(-6f, -24f))
    drawPointWithLabel(measurer, Offset(w * 0.56f, h * 0.46f), "I", Offset(-4f, -24f))
    // Segment KL
    drawSegment(Offset(w * 0.50f, h * 0.72f), Offset(w * 0.70f, h * 0.76f))
    drawPointWithLabel(measurer, Offset(w * 0.50f, h * 0.72f), "K", Offset(-6f, -24f))
    drawPointWithLabel(measurer, Offset(w * 0.70f, h * 0.76f), "L", Offset(-4f, -24f))
    // Segment NO
    drawSegment(Offset(w * 0.84f, h * 0.58f), Offset(w * 0.84f, h * 0.86f))
    drawPointWithLabel(measurer, Offset(w * 0.84f, h * 0.58f), "N", Offset(10f, -10f))
    drawPointWithLabel(measurer, Offset(w * 0.84f, h * 0.86f), "O", Offset(10f, -10f))
    // Point M
    drawPointWithLabel(measurer, Offset(w * 0.33f, h * 0.84f), "M", Offset(10f, -10f))
  }
}

@Composable
fun NamedLinesDiagram(diagramId: Int, modifier: Modifier = Modifier) {
  val measurer = rememberTextMeasurer()
  Canvas(
    modifier = modifier
      .fillMaxWidth()
      .height(170.dp)
      .clip(RoundedCornerShape(18.dp))
      .background(Color(0xFFFFFDF8))
      .border(1.5.dp, OutlineSoft, RoundedCornerShape(18.dp)),
  ) {
    val w = size.width
    val h = size.height
    if (diagramId == 1) {
      drawLineWithArrows(Offset(w * 0.14f, h * 0.28f), Offset(w * 0.56f, h * 0.28f))
      drawLineWithArrows(Offset(w * 0.32f, h * 0.10f), Offset(w * 0.32f, h * 0.86f))
      drawPointWithLabel(measurer, Offset(w * 0.21f, h * 0.28f), "K", Offset(-6f, 10f))
      drawPointWithLabel(measurer, Offset(w * 0.45f, h * 0.28f), "L", Offset(-4f, 10f))
      drawPointWithLabel(measurer, Offset(w * 0.32f, h * 0.17f), "M", Offset(10f, -8f))
      drawPointWithLabel(measurer, Offset(w * 0.32f, h * 0.74f), "N", Offset(-22f, -8f))

      drawLineWithArrows(Offset(w * 0.50f, h * 0.54f), Offset(w * 0.90f, h * 0.54f))
      drawLineWithArrows(Offset(w * 0.86f, h * 0.36f), Offset(w * 0.62f, h * 0.86f))
      drawPointWithLabel(measurer, Offset(w * 0.57f, h * 0.54f), "P", Offset(-6f, 10f))
      drawPointWithLabel(measurer, Offset(w * 0.83f, h * 0.54f), "Q", Offset(-4f, 10f))
      drawPointWithLabel(measurer, Offset(w * 0.82f, h * 0.44f), "O", Offset(-20f, -18f))
      drawPointWithLabel(measurer, Offset(w * 0.67f, h * 0.76f), "R", Offset(10f, -6f))
    } else {
      drawLineWithArrows(Offset(w * 0.12f, h * 0.70f), Offset(w * 0.88f, h * 0.70f))
      drawLineWithArrows(Offset(w * 0.40f, h * 0.12f), Offset(w * 0.40f, h * 0.90f))
      drawLineWithArrows(Offset(w * 0.26f, h * 0.16f), Offset(w * 0.82f, h * 0.82f))
      drawPointWithLabel(measurer, Offset(w * 0.20f, h * 0.70f), "A", Offset(-6f, -24f))
      drawPointWithLabel(measurer, Offset(w * 0.74f, h * 0.70f), "E", Offset(-6f, -26f))
      drawPointWithLabel(measurer, Offset(w * 0.40f, h * 0.80f), "B", Offset(-22f, -8f))
      drawPointWithLabel(measurer, Offset(w * 0.31f, h * 0.22f), "C", Offset(8f, -16f))
      drawPointWithLabel(measurer, Offset(w * 0.40f, h * 0.44f), "D", Offset(10f, -10f))
    }
  }
}

@Composable
fun InteractiveGeometryDrawPad(
  sampleSegmentUnits: Int?,
  onMarkDone: () -> Unit,
  modifier: Modifier = Modifier,
) {
  val strokes = remember(sampleSegmentUnits) { mutableStateListOf<List<Offset>>() }
  var current by remember { mutableStateOf<List<Offset>>(emptyList()) }
  var showRuler by remember { mutableStateOf(true) }

  Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
    if (sampleSegmentUnits != null) {
      Canvas(
        modifier = Modifier
          .fillMaxWidth()
          .height(64.dp)
          .clip(RoundedCornerShape(14.dp))
          .background(BagacayBlueLight.copy(alpha = 0.45f))
          .padding(horizontal = 16.dp),
      ) {
        val unitPx = size.width / 8f
        val startX = size.width * 0.15f
        val endX = startX + sampleSegmentUnits * unitPx
        val y = size.height * 0.55f
        drawSegment(Offset(startX, y), Offset(endX, y))
        // Ruler ticks right below
        for (u in 0..6) {
          val tx = startX + u * unitPx
          drawLine(InkMuted, Offset(tx, size.height * 0.78f), Offset(tx, size.height * 0.94f), strokeWidth = 1.5.dp.toPx())
        }
      }
    }

    Box(
      modifier = Modifier
        .fillMaxWidth()
        .height(175.dp)
        .clip(RoundedCornerShape(18.dp))
        .background(Color(0xFFFFFDF8))
        .border(1.5.dp, OutlineSoft, RoundedCornerShape(18.dp))
        .pointerInput(Unit) {
          detectDragGestures(
            onDragStart = { current = listOf(it) },
            onDrag = { change, _ -> change.consume(); current = current + change.position },
            onDragEnd = {
              if (current.isNotEmpty()) {
                strokes.add(current)
                current = emptyList()
              }
            },
          )
        },
    ) {
      Canvas(Modifier.fillMaxSize()) {
        val unitPx = size.width / 8f
        if (showRuler) {
          // Draw numbered ruler guide across the top
          drawRect(MangoGoldLight.copy(alpha = 0.5f), Offset.Zero, Size(size.width, 28.dp.toPx()))
          for (u in 1..7) {
            val x = u * unitPx
            drawLine(InkMuted, Offset(x, 12.dp.toPx()), Offset(x, 28.dp.toPx()), strokeWidth = 1.5.dp.toPx())
          }
        }
        val style = Stroke(3.5.dp.toPx(), cap = StrokeCap.Round)
        (strokes + listOf(current)).forEach { pts ->
          if (pts.size == 1) drawCircle(BagacayBlue, 4.dp.toPx(), pts.first())
          else if (pts.size > 1) {
            val p = Path().apply { moveTo(pts.first().x, pts.first().y); pts.drop(1).forEach { lineTo(it.x, it.y) } }
            drawPath(p, BagacayBlue, style = style)
          }
        }
      }
    }

    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
      OutlinedButton(onClick = { showRuler = !showRuler }, modifier = Modifier.weight(1f)) {
        Text(if (showRuler) "Hide Ruler" else "Show Ruler")
      }
      OutlinedButton(onClick = { strokes.clear(); current = emptyList() }, modifier = Modifier.weight(1f)) {
        Text("Clear Pad")
      }
      Button(
        onClick = onMarkDone,
        enabled = strokes.isNotEmpty(),
        modifier = Modifier.weight(1.2f),
        colors = ButtonDefaults.buttonColors(containerColor = LeafEmerald),
      ) {
        Text("Done Drawing!")
      }
    }
  }
}

@Composable
fun CongruentFigureCanvas(
  subType: CongruentSubType,
  setIndex: Int,
  modifier: Modifier = Modifier,
) {
  val measurer = rememberTextMeasurer()
  Canvas(
    modifier = modifier
      .fillMaxWidth()
      .height(170.dp)
      .clip(RoundedCornerShape(18.dp))
      .background(Color(0xFFFFFDF8))
      .border(1.5.dp, OutlineSoft, RoundedCornerShape(18.dp)),
  ) {
    val w = size.width
    val h = size.height
    when (subType) {
      CongruentSubType.ADEG_EQUAL_OR_NOT -> {
        val a = Offset(w * 0.30f, h * 0.25f)
        val d = Offset(w * 0.68f, h * 0.25f)
        val e = Offset(w * 0.30f, h * 0.80f)
        val g = Offset(w * 0.68f, h * 0.80f)
        val o = Offset((a.x + g.x) / 2f, (a.y + g.y) / 2f)
        val p = Offset(d.x, (d.y + g.y) / 2f)
        drawLine(InkDark, a, d, 2.5.dp.toPx())
        drawLine(InkDark, e, g, 2.5.dp.toPx())
        drawLine(InkDark, a, e, 2.5.dp.toPx())
        drawLine(InkDark, d, g, 2.5.dp.toPx())
        drawRay(o, Offset(w * 0.18f, h * 0.12f)) // ray AJ
        drawRay(o, Offset(w * 0.78f, h * 0.12f)) // ray DM
        drawLine(InkDark, o, e, 2.dp.toPx())
        drawLine(InkDark, o, g, 2.dp.toPx())
        drawRay(p, Offset(w * 0.84f, p.y)) // ray PK
        drawPointWithLabel(measurer, a, "A", Offset(-4f, -24f))
        drawPointWithLabel(measurer, d, "D", Offset(-4f, -24f))
        drawPointWithLabel(measurer, e, "E", Offset(-22f, -6f))
        drawPointWithLabel(measurer, g, "G", Offset(10f, -6f))
        drawPointWithLabel(measurer, o, "O", Offset(-6f, -22f))
        drawPointWithLabel(measurer, p, "P", Offset(8f, -22f))
      }
      CongruentSubType.LNRQ_EQUAL_PAIRS, CongruentSubType.TUV_ALTITUDE_EQUAL, CongruentSubType.TUV_EQUAL_SEGMENTS -> {
        // Left: Diamond LNRQ with center S; Right: Triangle TUV with altitude TW
        val n = Offset(w * 0.25f, h * 0.18f)
        val q = Offset(w * 0.25f, h * 0.84f)
        val l = Offset(w * 0.10f, h * 0.51f)
        val r = Offset(w * 0.40f, h * 0.51f)
        val s = Offset(w * 0.25f, h * 0.51f)
        drawLine(InkDark, n, r, 2.5.dp.toPx())
        drawLine(InkDark, r, q, 2.5.dp.toPx())
        drawLine(InkDark, q, l, 2.5.dp.toPx())
        drawLine(InkDark, l, n, 2.5.dp.toPx())
        drawLine(BagacayBlue, n, q, 2.dp.toPx())
        drawLine(BagacayBlue, l, r, 2.dp.toPx())
        drawPointWithLabel(measurer, n, "N", Offset(-4f, -22f))
        drawPointWithLabel(measurer, q, "Q", Offset(-4f, 6f))
        drawPointWithLabel(measurer, l, "L", Offset(-18f, -10f))
        drawPointWithLabel(measurer, r, "R", Offset(8f, -10f))
        drawPointWithLabel(measurer, s, "S", Offset(6f, 4f))

        val t = Offset(w * 0.72f, h * 0.18f)
        val u = Offset(w * 0.54f, h * 0.84f)
        val v = Offset(w * 0.90f, h * 0.84f)
        val tw = Offset(w * 0.72f, h * 0.84f)
        drawLine(InkDark, t, u, 2.5.dp.toPx())
        drawLine(InkDark, t, v, 2.5.dp.toPx())
        drawLine(InkDark, u, v, 2.5.dp.toPx())
        drawLine(CoralTangerine, t, tw, 2.5.dp.toPx())
        drawPointWithLabel(measurer, t, "T", Offset(-4f, -22f))
        drawPointWithLabel(measurer, u, "U", Offset(-18f, -6f))
        drawPointWithLabel(measurer, v, "V", Offset(8f, -6f))
        drawPointWithLabel(measurer, tw, "W", Offset(-6f, 6f))
      }
      CongruentSubType.KLNM_CONGRUENT -> {
        val k = Offset(w * 0.20f, h * 0.22f)
        val l = Offset(w * 0.80f, h * 0.22f)
        val m = Offset(w * 0.20f, h * 0.80f)
        val n = Offset(w * 0.80f, h * 0.80f)
        val p = Offset(w * 0.50f, h * 0.22f)
        val q = Offset(w * 0.50f, h * 0.80f)
        val a = Offset(w * 0.20f, h * 0.51f)
        val b = Offset(w * 0.80f, h * 0.51f)
        val o = Offset(w * 0.50f, h * 0.51f)
        drawRect(InkDark, k, Size(l.x - k.x, m.y - k.y), style = Stroke(2.5.dp.toPx()))
        drawLine(BagacayBlue, p, q, 2.dp.toPx())
        drawLine(BagacayBlue, a, b, 2.dp.toPx())
        drawLine(CoralTangerine, k, n, 2.dp.toPx())
        drawLine(CoralTangerine, l, m, 2.dp.toPx())
        drawPointWithLabel(measurer, k, "K", Offset(-18f, -18f))
        drawPointWithLabel(measurer, l, "L", Offset(8f, -18f))
        drawPointWithLabel(measurer, m, "M", Offset(-20f, -4f))
        drawPointWithLabel(measurer, n, "N", Offset(8f, -4f))
        drawPointWithLabel(measurer, p, "P", Offset(-4f, -22f))
        drawPointWithLabel(measurer, q, "Q", Offset(-4f, 6f))
        drawPointWithLabel(measurer, a, "A", Offset(-18f, -10f))
        drawPointWithLabel(measurer, b, "B", Offset(8f, -10f))
        drawPointWithLabel(measurer, o, "O", Offset(8f, 4f))
      }
      CongruentSubType.COMPARE_THREE_SEGMENTS -> {
        val triples = listOf(
          Triple("AB" to 0.75f, "CD" to 0.48f, "EF" to 0.75f),
          Triple("GH" to 0.45f, "JK" to 0.45f, "LM" to 0.78f),
          Triple("NP" to 0.46f, "QR" to 0.76f, "ST" to 0.76f),
          Triple("UV" to 0.48f, "WX" to 0.78f, "YZ" to 0.48f),
          Triple("DE" to 0.78f, "FG" to 0.48f, "HI" to 0.48f),
        )
        val row = triples[setIndex.coerceIn(0, triples.lastIndex)]
        val ys = listOf(h * 0.25f, h * 0.52f, h * 0.79f)
        val entries = listOf(row.first, row.second, row.third)
        entries.forEachIndexed { idx, (name, frac) ->
          val sx = w * 0.18f
          val ex = sx + w * 0.72f * frac
          val y = ys[idx]
          drawSegment(Offset(sx, y), Offset(ex, y))
          drawText(measurer, name[0].toString(), Offset(sx - 24f, y - 14f), TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold, color = InkDark))
          drawText(measurer, name[1].toString(), Offset(ex + 12f, y - 14f), TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold, color = InkDark))
        }
      }
    }
  }
}

private fun DrawScope.drawPointWithLabel(measurer: TextMeasurer, pt: Offset, label: String, offset: Offset) {
  drawCircle(InkDark, 5.dp.toPx(), pt)
  drawText(
    textMeasurer = measurer,
    text = label,
    topLeft = pt + offset,
    style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold, color = BagacayBlue),
  )
}

private fun DrawScope.drawSegment(a: Offset, b: Offset) {
  drawLine(InkDark, a, b, strokeWidth = 3.dp.toPx(), cap = StrokeCap.Round)
  drawCircle(InkDark, 5.dp.toPx(), a)
  drawCircle(InkDark, 5.dp.toPx(), b)
}

private fun DrawScope.drawRay(start: Offset, end: Offset) {
  drawLine(InkDark, start, end, strokeWidth = 3.dp.toPx(), cap = StrokeCap.Round)
  drawCircle(InkDark, 5.dp.toPx(), start)
  drawArrowHead(start, end)
}

private fun DrawScope.drawLineWithArrows(a: Offset, b: Offset) {
  drawLine(InkDark, a, b, strokeWidth = 3.dp.toPx(), cap = StrokeCap.Round)
  drawArrowHead(a, b)
  drawArrowHead(b, a)
}

private fun DrawScope.drawArrowHead(from: Offset, to: Offset) {
  val angle = atan2((to.y - from.y).toDouble(), (to.x - from.x).toDouble())
  val arrowLen = 11.dp.toPx()
  val spread = 0.45
  val p1 = Offset(
    to.x - (arrowLen * cos(angle - spread)).toFloat(),
    to.y - (arrowLen * sin(angle - spread)).toFloat(),
  )
  val p2 = Offset(
    to.x - (arrowLen * cos(angle + spread)).toFloat(),
    to.y - (arrowLen * sin(angle + spread)).toFloat(),
  )
  val path = Path().apply {
    moveTo(to.x, to.y)
    lineTo(p1.x, p1.y)
    lineTo(p2.x, p2.y)
    close()
  }
  drawPath(path, InkDark)
}
