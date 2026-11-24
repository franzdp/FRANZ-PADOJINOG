package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import com.example.model.CountObjectKind
import com.example.model.G2FractionFigureKind
import com.example.model.OrdinalShapeKind
import com.example.model.PartitionShapeKind
import com.example.ui.theme.*
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

fun CountObjectKind.displaySymbol(): String = when (this) {
  CountObjectKind.HEART -> "❤️"
  CountObjectKind.MOON -> "🌙"
  CountObjectKind.STAR -> "⭐"
  CountObjectKind.SMILEY -> "😊"
  CountObjectKind.BABY -> "👶"
  CountObjectKind.PARROT -> "🦜"
  CountObjectKind.LEAF -> "🍃"
  CountObjectKind.FLOWER -> "🌸"
  CountObjectKind.TREE -> "🌳"
  CountObjectKind.CLOUD -> "☁️"
  CountObjectKind.DOG -> "🐶"
  CountObjectKind.BANANA -> "🍌"
  CountObjectKind.TRUCK -> "🚚"
  CountObjectKind.CHICKEN -> "🐓"
  CountObjectKind.BEACHBALL -> "🏐"
  CountObjectKind.CARABAO -> "🐃"
  CountObjectKind.DUCK -> "🦆"
  CountObjectKind.BASKETBALL -> "🏀"
  CountObjectKind.PENCIL -> "✏️"
  CountObjectKind.CAMERA -> "📷"
  CountObjectKind.MONKEY -> "🐒"
  CountObjectKind.BOOK -> "📘"
  CountObjectKind.TOMATO -> "🍅"
  CountObjectKind.RIBBON -> "🎀"
  CountObjectKind.FAN -> "🪭"
  CountObjectKind.CRAYON -> "🖍️"
  CountObjectKind.BALLOON -> "🎈"
  CountObjectKind.CUP -> "☕"
  CountObjectKind.PEN -> "🖊️"
  CountObjectKind.WATCH -> "⌚"
  CountObjectKind.CHILD -> "🧒"
  CountObjectKind.CANDY -> "🍬"
  CountObjectKind.BUTTERFLY -> "🦋"
  CountObjectKind.TELEPHONE -> "☎️"
  CountObjectKind.ERASER -> "🧼"
  CountObjectKind.CAKE -> "🎂"
  CountObjectKind.CAR -> "🚗"
  CountObjectKind.GRASSHOPPER -> "🦗"
  CountObjectKind.TULIP -> "🌷"
  CountObjectKind.ICECREAM -> "🍦"
  CountObjectKind.CAT -> "🐱"
  CountObjectKind.GLASS -> "🥛"
  CountObjectKind.BOY_SCOUT -> "⚜️"
  CountObjectKind.TENT -> "⛺"
  CountObjectKind.SNAKE -> "🐍"
  CountObjectKind.FLASHLIGHT -> "🔦"
  CountObjectKind.MATCHBOX -> "🔥"
  CountObjectKind.JACKET -> "🧥"
  CountObjectKind.BAT -> "🦇"
  CountObjectKind.WATER_JUG -> "🧴"
  CountObjectKind.EGG -> "🥚"
  CountObjectKind.MILK_BOTTLE -> "🍼"
  CountObjectKind.BASKET -> "🧺"
  CountObjectKind.GIRL -> "👧"
  CountObjectKind.PANCAKE -> "🥞"
  CountObjectKind.COOKIE -> "🍪"
  CountObjectKind.CANE -> "🦯"
  CountObjectKind.WOMAN -> "👩"
  CountObjectKind.MANGO -> "🥭"
  CountObjectKind.UMBRELLA -> "☂️"
  CountObjectKind.PEAR -> "🍐"
  CountObjectKind.TSHIRT -> "👕"
  CountObjectKind.DIAMOND -> "💎"
}

@Composable
fun ObjectCollectionTray(
  kind: CountObjectKind,
  count: Int,
  modifier: Modifier = Modifier,
  maxPerRow: Int = 6,
  brokenCount: Int = 0,
  chipBg: Color = Color(0xFFFFFDF8),
) {
  val intact = (count - brokenCount).coerceAtLeast(0)
  Surface(
    color = chipBg,
    shape = RoundedCornerShape(18.dp),
    modifier = modifier
      .fillMaxWidth()
      .border(1.5.dp, OutlineSoft, RoundedCornerShape(18.dp)),
  ) {
    @OptIn(ExperimentalLayoutApi::class)
    FlowRow(
      modifier = Modifier
        .fillMaxWidth()
        .padding(12.dp),
      horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
      verticalArrangement = Arrangement.spacedBy(8.dp),
      maxItemsInEachRow = maxPerRow,
    ) {
      repeat(intact) {
        Box(
          modifier = Modifier
            .size(38.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(BagacayBlueLight.copy(alpha = 0.45f)),
          contentAlignment = Alignment.Center,
        ) {
          Text(kind.displaySymbol(), fontSize = 22.sp)
        }
      }
      repeat(brokenCount.coerceAtLeast(0)) {
        Box(
          modifier = Modifier
            .size(38.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(CrimsonLight.copy(alpha = 0.7f))
            .border(1.dp, CrimsonRed.copy(alpha = 0.5f), RoundedCornerShape(10.dp)),
          contentAlignment = Alignment.Center,
        ) {
          Text("✖️", fontSize = 16.sp)
        }
      }
    }
  }
}

@Composable
fun OrdinalShapeGlyph(
  kind: OrdinalShapeKind,
  modifier: Modifier = Modifier,
  size: Dp = 36.dp,
  fill: Color = BagacayBlueLight,
  stroke: Color = InkDark,
) {
  Canvas(modifier = modifier.size(size)) {
    val w = this.size.width
    val h = this.size.height
    val sWidth = w * 0.08f
    when (kind) {
      OrdinalShapeKind.CIRCLE -> {
        val r = minOf(w, h) * 0.42f
        drawCircle(fill, r, Offset(w / 2f, h / 2f))
        drawCircle(stroke, r, Offset(w / 2f, h / 2f), style = Stroke(sWidth))
      }
      OrdinalShapeKind.OVAL -> {
        drawOval(fill, Offset(w * 0.08f, h * 0.22f), Size(w * 0.84f, h * 0.56f))
        drawOval(stroke, Offset(w * 0.08f, h * 0.22f), Size(w * 0.84f, h * 0.56f), style = Stroke(sWidth))
      }
      OrdinalShapeKind.SQUARE -> {
        val side = minOf(w, h) * 0.76f
        val left = (w - side) / 2f
        val top = (h - side) / 2f
        drawRect(fill, Offset(left, top), Size(side, side))
        drawRect(stroke, Offset(left, top), Size(side, side), style = Stroke(sWidth))
      }
      OrdinalShapeKind.RECTANGLE -> {
        drawRect(fill, Offset(w * 0.08f, h * 0.22f), Size(w * 0.84f, h * 0.56f))
        drawRect(stroke, Offset(w * 0.08f, h * 0.22f), Size(w * 0.84f, h * 0.56f), style = Stroke(sWidth))
      }
      OrdinalShapeKind.TRIANGLE -> {
        val p = Path().apply {
          moveTo(w / 2f, h * 0.12f)
          lineTo(w * 0.88f, h * 0.86f)
          lineTo(w * 0.12f, h * 0.86f)
          close()
        }
        drawPath(p, fill)
        drawPath(p, stroke, style = Stroke(sWidth))
      }
      OrdinalShapeKind.DIAMOND -> {
        val p = Path().apply {
          moveTo(w / 2f, h * 0.1f)
          lineTo(w * 0.88f, h / 2f)
          lineTo(w / 2f, h * 0.9f)
          lineTo(w * 0.12f, h / 2f)
          close()
        }
        drawPath(p, fill)
        drawPath(p, stroke, style = Stroke(sWidth))
      }
      OrdinalShapeKind.CLOUD -> {
        drawCircle(fill, w * 0.22f, Offset(w * 0.34f, h * 0.54f))
        drawCircle(fill, w * 0.25f, Offset(w * 0.50f, h * 0.42f))
        drawCircle(fill, w * 0.22f, Offset(w * 0.68f, h * 0.54f))
        drawOval(fill, Offset(w * 0.18f, h * 0.46f), Size(w * 0.64f, h * 0.32f))
        drawOval(stroke, Offset(w * 0.16f, h * 0.32f), Size(w * 0.68f, h * 0.46f), style = Stroke(sWidth))
      }
      OrdinalShapeKind.SMILEY -> {
        val r = minOf(w, h) * 0.42f
        drawCircle(MangoGoldLight, r, Offset(w / 2f, h / 2f))
        drawCircle(stroke, r, Offset(w / 2f, h / 2f), style = Stroke(sWidth))
        drawCircle(stroke, r * 0.11f, Offset(w * 0.37f, h * 0.42f))
        drawCircle(stroke, r * 0.11f, Offset(w * 0.63f, h * 0.42f))
        drawArc(stroke, 20f, 140f, false, Offset(w * 0.30f, h * 0.38f), Size(w * 0.40f, h * 0.36f), style = Stroke(sWidth, cap = StrokeCap.Round))
      }
      OrdinalShapeKind.HEART -> {
        val p = Path().apply {
          moveTo(w / 2f, h * 0.86f)
          cubicTo(w * 0.08f, h * 0.56f, w * 0.10f, h * 0.18f, w * 0.34f, h * 0.18f)
          cubicTo(w * 0.44f, h * 0.18f, w / 2f, h * 0.28f, w / 2f, h * 0.34f)
          cubicTo(w / 2f, h * 0.28f, w * 0.56f, h * 0.18f, w * 0.66f, h * 0.18f)
          cubicTo(w * 0.90f, h * 0.18f, w * 0.92f, h * 0.56f, w / 2f, h * 0.86f)
          close()
        }
        drawPath(p, CoralLight)
        drawPath(p, stroke, style = Stroke(sWidth))
      }
    }
  }
}

@Composable
fun PartitionPreviewCanvas(
  shape: PartitionShapeKind,
  parts: Int,
  equalCut: Boolean,
  modifier: Modifier = Modifier,
) {
  Canvas(modifier = modifier.size(72.dp)) {
    val w = size.width
    val h = size.height
    val cx = w / 2f
    val cy = h / 2f
    val fill = BagacayBlueLight.copy(alpha = 0.5f)
    val stroke = Stroke(2.5.dp.toPx())
    val cutStroke = Stroke(2.dp.toPx())

    when (shape) {
      PartitionShapeKind.SQUARE -> {
        drawRect(fill, Offset(w * 0.15f, h * 0.15f), Size(w * 0.7f, h * 0.7f))
        drawRect(InkDark, Offset(w * 0.15f, h * 0.15f), Size(w * 0.7f, h * 0.7f), style = stroke)
      }
      PartitionShapeKind.CIRCLE -> {
        drawCircle(fill, w * 0.38f, Offset(cx, cy))
        drawCircle(InkDark, w * 0.38f, Offset(cx, cy), style = stroke)
      }
      PartitionShapeKind.RECTANGLE -> {
        drawRect(fill, Offset(w * 0.08f, h * 0.26f), Size(w * 0.84f, h * 0.48f))
        drawRect(InkDark, Offset(w * 0.08f, h * 0.26f), Size(w * 0.84f, h * 0.48f), style = stroke)
      }
      PartitionShapeKind.HEXAGON -> {
        val r = w * 0.38f
        val p = Path()
        for (i in 0 until 6) {
          val a = (PI / 3.0) * i
          val px = cx + r * cos(a).toFloat()
          val py = cy + r * sin(a).toFloat()
          if (i == 0) p.moveTo(px, py) else p.lineTo(px, py)
        }
        p.close()
        drawPath(p, fill)
        drawPath(p, InkDark, style = stroke)
      }
      PartitionShapeKind.HEART -> {
        val p = Path().apply {
          moveTo(cx, h * 0.84f)
          cubicTo(w * 0.10f, h * 0.55f, w * 0.12f, h * 0.18f, w * 0.35f, h * 0.18f)
          cubicTo(w * 0.45f, h * 0.18f, cx, h * 0.28f, cx, h * 0.34f)
          cubicTo(cx, h * 0.28f, w * 0.55f, h * 0.18f, w * 0.65f, h * 0.18f)
          cubicTo(w * 0.88f, h * 0.18f, w * 0.90f, h * 0.55f, cx, h * 0.84f)
          close()
        }
        drawPath(p, fill)
        drawPath(p, InkDark, style = stroke)
      }
    }

    // Division lines
    val cutX = if (equalCut) cx else w * 0.33f
    if (parts >= 2) {
      drawLine(BagacayBlue, Offset(cutX, h * 0.15f), Offset(cutX, h * 0.85f), strokeWidth = cutStroke.width)
    }
    if (parts == 4) {
      val cutY = if (equalCut) cy else h * 0.34f
      drawLine(BagacayBlue, Offset(w * 0.12f, cutY), Offset(w * 0.88f, cutY), strokeWidth = cutStroke.width)
    }
  }
}

@Composable
fun InteractiveG2FractionFigure(
  figureKind: G2FractionFigureKind,
  shadedParts: Set<Int>,
  interactive: Boolean,
  onTogglePart: (Int) -> Unit = {},
  modifier: Modifier = Modifier,
) {
  val shadeColor = CoralTangerine.copy(alpha = 0.78f)
  val baseColor = Color(0xFFFFFDF8)

  @Composable
  fun PartButtonRow(partCount: Int) {
    Row(
      horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally),
      modifier = Modifier.fillMaxWidth(),
    ) {
      for (i in 0 until partCount) {
        val active = i in shadedParts
        Surface(
          color = if (active) CoralTangerine else WarmSurfaceVariant,
          shape = RoundedCornerShape(10.dp),
          modifier = Modifier
            .defaultMinSize(minWidth = 54.dp, minHeight = 48.dp)
            .border(1.5.dp, if (active) MangoDark else OutlineSoft, RoundedCornerShape(10.dp))
            .clickable(enabled = interactive) { onTogglePart(i) },
        ) {
          Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(horizontal = 10.dp)) {
            Text(
              text = if (active) "Part ${i + 1} ✓" else "Part ${i + 1}",
              color = if (active) Color.White else InkDark,
              fontWeight = FontWeight.Bold,
              fontSize = 12.sp,
            )
          }
        }
      }
    }
  }

  val partsTotal = when (figureKind) {
    G2FractionFigureKind.CIRCLE_2_VERT,
    G2FractionFigureKind.TRIANGLE_2_VERT,
    G2FractionFigureKind.HEXAGON_2_VERT,
    G2FractionFigureKind.RECT_2_DIAG,
    G2FractionFigureKind.RECT_2_VERT,
    G2FractionFigureKind.RECT_2_HORIZ,
    G2FractionFigureKind.CROSS_2_VERT -> 2
    else -> 4
  }

  Column(
    modifier = modifier.fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(8.dp),
  ) {
    Canvas(modifier = Modifier.size(150.dp)) {
      val w = size.width
      val h = size.height
      val cx = w / 2f
      val cy = h / 2f
      val border = Stroke(3.dp.toPx())

      fun c(idx: Int) = if (idx in shadedParts) shadeColor else baseColor

      when (figureKind) {
        G2FractionFigureKind.CIRCLE_2_VERT -> {
          val r = w * 0.42f
          val tl = Offset(cx - r, cy - r)
          val sz = Size(r * 2, r * 2)
          drawArc(c(0), 90f, 180f, true, tl, sz)
          drawArc(c(1), -90f, 180f, true, tl, sz)
          drawCircle(InkDark, r, Offset(cx, cy), style = border)
          drawLine(InkDark, Offset(cx, cy - r), Offset(cx, cy + r), strokeWidth = border.width)
        }
        G2FractionFigureKind.CIRCLE_4_ORTHO -> {
          val r = w * 0.42f
          val tl = Offset(cx - r, cy - r)
          val sz = Size(r * 2, r * 2)
          drawArc(c(0), 180f, 90f, true, tl, sz)
          drawArc(c(1), 270f, 90f, true, tl, sz)
          drawArc(c(2), 0f, 90f, true, tl, sz)
          drawArc(c(3), 90f, 90f, true, tl, sz)
          drawCircle(InkDark, r, Offset(cx, cy), style = border)
          drawLine(InkDark, Offset(cx, cy - r), Offset(cx, cy + r), strokeWidth = border.width)
          drawLine(InkDark, Offset(cx - r, cy), Offset(cx + r, cy), strokeWidth = border.width)
        }
        G2FractionFigureKind.CIRCLE_4_DIAG -> {
          val r = w * 0.42f
          val tl = Offset(cx - r, cy - r)
          val sz = Size(r * 2, r * 2)
          drawArc(c(0), 135f, 90f, true, tl, sz)
          drawArc(c(1), 225f, 90f, true, tl, sz)
          drawArc(c(2), 315f, 90f, true, tl, sz)
          drawArc(c(3), 45f, 90f, true, tl, sz)
          drawCircle(InkDark, r, Offset(cx, cy), style = border)
          val d = r * 0.707f
          drawLine(InkDark, Offset(cx - d, cy - d), Offset(cx + d, cy + d), strokeWidth = border.width)
          drawLine(InkDark, Offset(cx + d, cy - d), Offset(cx - d, cy + d), strokeWidth = border.width)
        }
        G2FractionFigureKind.TRIANGLE_2_VERT -> {
          val pLeft = Path().apply { moveTo(cx, h * 0.12f); lineTo(cx, h * 0.88f); lineTo(w * 0.12f, h * 0.88f); close() }
          val pRight = Path().apply { moveTo(cx, h * 0.12f); lineTo(w * 0.88f, h * 0.88f); lineTo(cx, h * 0.88f); close() }
          drawPath(pLeft, c(0)); drawPath(pRight, c(1))
          drawPath(pLeft, InkDark, style = border); drawPath(pRight, InkDark, style = border)
        }
        G2FractionFigureKind.SQUARE_4_GRID -> {
          val s = w * 0.36f
          val rects = listOf(Offset(cx - s, cy - s), Offset(cx, cy - s), Offset(cx, cy), Offset(cx - s, cy))
          rects.forEachIndexed { idx, o ->
            drawRect(c(idx), o, Size(s, s))
            drawRect(InkDark, o, Size(s, s), style = border)
          }
        }
        G2FractionFigureKind.SQUARE_4_DIAG -> {
          val l = w * 0.14f; val r = w * 0.86f; val t = h * 0.14f; val b = h * 0.86f
          val tris = listOf(
            Path().apply { moveTo(l, t); lineTo(r, t); lineTo(cx, cy); close() },
            Path().apply { moveTo(r, t); lineTo(r, b); lineTo(cx, cy); close() },
            Path().apply { moveTo(r, b); lineTo(l, b); lineTo(cx, cy); close() },
            Path().apply { moveTo(l, b); lineTo(l, t); lineTo(cx, cy); close() },
          )
          tris.forEachIndexed { i, path -> drawPath(path, c(i)); drawPath(path, InkDark, style = border) }
        }
        G2FractionFigureKind.DIAMOND_4_GRID -> {
          val r = w * 0.38f
          val pts = listOf(Offset(cx, cy - r), Offset(cx + r, cy), Offset(cx, cy + r), Offset(cx - r, cy))
          val quads = listOf(
            listOf(pts[0], Offset((pts[0].x + pts[1].x) / 2, (pts[0].y + pts[1].y) / 2), Offset(cx, cy), Offset((pts[0].x + pts[3].x) / 2, (pts[0].y + pts[3].y) / 2)),
            listOf(Offset((pts[0].x + pts[1].x) / 2, (pts[0].y + pts[1].y) / 2), pts[1], Offset((pts[1].x + pts[2].x) / 2, (pts[1].y + pts[2].y) / 2), Offset(cx, cy)),
            listOf(Offset(cx, cy), Offset((pts[1].x + pts[2].x) / 2, (pts[1].y + pts[2].y) / 2), pts[2], Offset((pts[2].x + pts[3].x) / 2, (pts[2].y + pts[3].y) / 2)),
            listOf(Offset((pts[0].x + pts[3].x) / 2, (pts[0].y + pts[3].y) / 2), Offset(cx, cy), Offset((pts[2].x + pts[3].x) / 2, (pts[2].y + pts[3].y) / 2), pts[3]),
          )
          quads.forEachIndexed { idx, q ->
            val p = Path().apply { moveTo(q[0].x, q[0].y); lineTo(q[1].x, q[1].y); lineTo(q[2].x, q[2].y); lineTo(q[3].x, q[3].y); close() }
            drawPath(p, c(idx)); drawPath(p, InkDark, style = border)
          }
        }
        G2FractionFigureKind.HEXAGON_2_VERT -> {
          val r = w * 0.40f
          val pL = Path().apply { moveTo(cx, cy - r); lineTo(cx, cy + r); lineTo(cx - r * 0.86f, cy + r * 0.5f); lineTo(cx - r * 0.86f, cy - r * 0.5f); close() }
          val pR = Path().apply { moveTo(cx, cy - r); lineTo(cx + r * 0.86f, cy - r * 0.5f); lineTo(cx + r * 0.86f, cy + r * 0.5f); lineTo(cx, cy + r); close() }
          drawPath(pL, c(0)); drawPath(pR, c(1))
          drawPath(pL, InkDark, style = border); drawPath(pR, InkDark, style = border)
        }
        G2FractionFigureKind.HEXAGON_4_GRID -> {
          val r = w * 0.40f
          val q0 = Path().apply { moveTo(cx, cy - r); lineTo(cx, cy); lineTo(cx - r * 0.86f, cy); lineTo(cx - r * 0.86f, cy - r * 0.5f); close() }
          val q1 = Path().apply { moveTo(cx, cy - r); lineTo(cx + r * 0.86f, cy - r * 0.5f); lineTo(cx + r * 0.86f, cy); lineTo(cx, cy); close() }
          val q2 = Path().apply { moveTo(cx, cy); lineTo(cx + r * 0.86f, cy); lineTo(cx + r * 0.86f, cy + r * 0.5f); lineTo(cx, cy + r); close() }
          val q3 = Path().apply { moveTo(cx - r * 0.86f, cy); lineTo(cx, cy); lineTo(cx, cy + r); lineTo(cx - r * 0.86f, cy + r * 0.5f); close() }
          listOf(q0, q1, q2, q3).forEachIndexed { i, p -> drawPath(p, c(i)); drawPath(p, InkDark, style = border) }
        }
        G2FractionFigureKind.RECT_2_DIAG -> {
          val p0 = Path().apply { moveTo(w * 0.10f, h * 0.24f); lineTo(w * 0.90f, h * 0.24f); lineTo(w * 0.90f, h * 0.76f); close() }
          val p1 = Path().apply { moveTo(w * 0.10f, h * 0.24f); lineTo(w * 0.90f, h * 0.76f); lineTo(w * 0.10f, h * 0.76f); close() }
          drawPath(p0, c(0)); drawPath(p1, c(1))
          drawPath(p0, InkDark, style = border); drawPath(p1, InkDark, style = border)
        }
        G2FractionFigureKind.RECT_2_VERT -> {
          drawRect(c(0), Offset(w * 0.14f, h * 0.18f), Size(w * 0.36f, h * 0.64f))
          drawRect(c(1), Offset(w * 0.50f, h * 0.18f), Size(w * 0.36f, h * 0.64f))
          drawRect(InkDark, Offset(w * 0.14f, h * 0.18f), Size(w * 0.72f, h * 0.64f), style = border)
          drawLine(InkDark, Offset(cx, h * 0.18f), Offset(cx, h * 0.82f), strokeWidth = border.width)
        }
        G2FractionFigureKind.RECT_2_HORIZ -> {
          drawRect(c(0), Offset(w * 0.08f, h * 0.24f), Size(w * 0.42f, h * 0.52f))
          drawRect(c(1), Offset(w * 0.50f, h * 0.24f), Size(w * 0.42f, h * 0.52f))
          drawRect(InkDark, Offset(w * 0.08f, h * 0.24f), Size(w * 0.84f, h * 0.52f), style = border)
          drawLine(InkDark, Offset(cx, h * 0.24f), Offset(cx, h * 0.76f), strokeWidth = border.width)
        }
        G2FractionFigureKind.RECT_4_STRIP -> {
          val cellW = w * 0.21f
          for (i in 0 until 4) {
            val left = w * 0.08f + i * cellW
            drawRect(c(i), Offset(left, h * 0.28f), Size(cellW, h * 0.44f))
            drawRect(InkDark, Offset(left, h * 0.28f), Size(cellW, h * 0.44f), style = border)
          }
        }
        G2FractionFigureKind.CROSS_2_VERT -> {
          val pL = Path().apply {
            moveTo(cx, h * 0.12f); lineTo(cx, h * 0.88f); lineTo(w * 0.36f, h * 0.88f)
            lineTo(w * 0.36f, h * 0.64f); lineTo(w * 0.12f, h * 0.64f); lineTo(w * 0.12f, h * 0.36f)
            lineTo(w * 0.36f, h * 0.36f); lineTo(w * 0.36f, h * 0.12f); close()
          }
          val pR = Path().apply {
            moveTo(cx, h * 0.12f); lineTo(w * 0.64f, h * 0.12f); lineTo(w * 0.64f, h * 0.36f)
            lineTo(w * 0.88f, h * 0.36f); lineTo(w * 0.88f, h * 0.64f); lineTo(w * 0.64f, h * 0.64f)
            lineTo(w * 0.64f, h * 0.88f); lineTo(cx, h * 0.88f); close()
          }
          drawPath(pL, c(0)); drawPath(pR, c(1))
          drawPath(pL, InkDark, style = border); drawPath(pR, InkDark, style = border)
        }
      }
    }

    if (interactive) {
      PartButtonRow(partsTotal)
    }
  }
}
