package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.GroupingSceneType
import com.example.model.PictureSceneType
import com.example.model.ShadedFigureType
import com.example.ui.theme.*
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun FractionComparisonBars(
  leftNum: Int,
  leftDen: Int,
  rightNum: Int,
  rightDen: Int,
  modifier: Modifier = Modifier,
) {
  Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
    FractionBarRow(label = "Left", num = leftNum, den = leftDen, color = BagacayBlue)
    FractionBarRow(label = "Right", num = rightNum, den = rightDen, color = CoralTangerine)
  }
}

@Composable
private fun FractionBarRow(label: String, num: Int, den: Int, color: Color) {
  val safeDen = den.coerceAtLeast(1)
  val ratio = (num.toFloat() / safeDen).coerceIn(0f, 1.5f)
  Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
    Text(label, style = MaterialTheme.typography.labelMedium, color = InkMuted, modifier = Modifier.width(42.dp))
    Box(
      modifier = Modifier
        .weight(1f)
        .height(22.dp)
        .clip(RoundedCornerShape(11.dp))
        .background(color.copy(alpha = 0.12f))
        .border(1.dp, color.copy(alpha = 0.35f), RoundedCornerShape(11.dp)),
    ) {
      val fillFrac = (ratio / maxOf(1f, ratio)).coerceIn(0f, 1f)
      Box(
        modifier = Modifier
          .fillMaxHeight()
          .fillMaxWidth(fillFrac)
          .clip(RoundedCornerShape(11.dp))
          .background(color),
      )
    }
  }
}

@Composable
fun ShadedFigureCanvas(
  figureType: ShadedFigureType,
  modifier: Modifier = Modifier,
) {
  Canvas(modifier = modifier.size(168.dp)) {
    val w = size.width
    val h = size.height
    val cx = w / 2f
    val cy = h / 2f
    val stroke = Stroke(width = 2.5.dp.toPx())
    val shade = Color(0xFF7B879E)

    when (figureType) {
      ShadedFigureType.DIAMOND_4 -> {
        val r = minOf(w, h) * 0.44f
        val top = Offset(cx, cy - r)
        val right = Offset(cx + r, cy)
        val bottom = Offset(cx, cy + r)
        val left = Offset(cx - r, cy)
        val center = Offset(cx, cy)
        val tris = listOf(
          listOf(center, top, right),
          listOf(center, right, bottom),
          listOf(center, bottom, left),
          listOf(center, left, top),
        )
        tris.forEachIndexed { idx, pts ->
          val p = Path().apply { moveTo(pts[0].x, pts[0].y); lineTo(pts[1].x, pts[1].y); lineTo(pts[2].x, pts[2].y); close() }
          if (idx == 0) drawPath(p, shade)
          drawPath(p, InkDark, style = stroke)
        }
      }
      ShadedFigureType.TRIANGLES_3 -> {
        val bw = w * 0.28f
        val th = h * 0.56f
        val baseY = cy + th / 2f
        val topY = cy - th / 2f
        val startX = cx - bw * 1.5f
        // Tri 0 (up), Tri 1 (down, shaded), Tri 2 (up)
        val p0 = Path().apply { moveTo(startX, baseY); lineTo(startX + bw, baseY); lineTo(startX + bw * 0.5f, topY); close() }
        val p1 = Path().apply { moveTo(startX + bw * 0.5f, topY); lineTo(startX + bw * 1.5f, topY); lineTo(startX + bw, baseY); close() }
        val p2 = Path().apply { moveTo(startX + bw, baseY); lineTo(startX + bw * 2f, baseY); lineTo(startX + bw * 1.5f, topY); close() }
        val p3 = Path().apply { moveTo(startX + bw * 1.5f, topY); lineTo(startX + bw * 2.5f, topY); lineTo(startX + bw * 2f, baseY); close() }
        // PDF has 3 triangles: up, down (shaded), up
        drawPath(p0, InkDark, style = stroke)
        drawPath(p1, shade)
        drawPath(p1, InkDark, style = stroke)
        drawPath(p2, InkDark, style = stroke)
      }
      ShadedFigureType.RECT_4 -> {
        val rw = w * 0.8f
        val rh = h * 0.65f
        val left = cx - rw / 2f
        val top = cy - rh / 2f
        val stripW = rw / 4f
        for (i in 0 until 4) {
          val x = left + i * stripW
          if (i < 3) drawRect(shade, Offset(x, top), Size(stripW, rh))
          drawRect(InkDark, Offset(x, top), Size(stripW, rh), style = stroke)
        }
      }
      ShadedFigureType.HEXAGON_6 -> {
        val r = minOf(w, h) * 0.42f
        val verts = List(6) { i ->
          val a = (i * 60.0 - 60.0) * PI / 180.0
          Offset(cx + (r * cos(a)).toFloat(), cy + (r * sin(a)).toFloat())
        }
        for (i in 0 until 6) {
          val p = Path().apply {
            moveTo(cx, cy)
            lineTo(verts[i].x, verts[i].y)
            lineTo(verts[(i + 1) % 6].x, verts[(i + 1) % 6].y)
            close()
          }
          if (i == 0 || i == 1) drawPath(p, shade)
          drawPath(p, InkDark, style = stroke)
        }
      }
      ShadedFigureType.CIRCLE_8 -> {
        val r = minOf(w, h) * 0.42f
        val tl = Offset(cx - r, cy - r)
        val sz = Size(r * 2, r * 2)
        for (i in 0 until 8) {
          val start = -90f + i * 45f
          if (i == 1 || i == 2 || i == 5) {
            drawArc(shade, start, 45f, useCenter = true, topLeft = tl, size = sz)
          }
          drawArc(InkDark, start, 45f, useCenter = true, topLeft = tl, size = sz, style = stroke)
        }
      }
    }
  }
}

@Composable
fun PictureFractionScene(
  scene: PictureSceneType,
  modifier: Modifier = Modifier,
) {
  Box(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(18.dp))
      .background(Color(0xFFFFFDF8))
      .border(1.5.dp, OutlineSoft, RoundedCornerShape(18.dp))
      .padding(12.dp),
    contentAlignment = Alignment.Center,
  ) {
    when (scene) {
      PictureSceneType.HALLOWEEN_GROUP -> EmojiGrid(
        rows = listOf(listOf("🧛", "👻", "💀"), listOf("👻", "🧙", "💀"), listOf("💀", "🧙", "🧛")),
      )
      PictureSceneType.TENNIS_RACKETS -> Canvas(Modifier.fillMaxWidth().height(92.dp)) {
        val step = size.width / 6f
        for (i in 0 until 5) {
          val cx = step * (i + 1)
          val upsideDown = (i == 1 || i == 3)
          drawTennisRacket(cx, size.height / 2f, upsideDown)
        }
      }
      PictureSceneType.BASKETBALLS -> EmojiGrid(
        rows = listOf(listOf("⚽", "⚽", "🏀", "⚽"), listOf("🏀", "🏀", "⚽", "⚽")),
      )
      PictureSceneType.OWLS_READING -> EmojiGrid(
        rows = listOf(listOf("🦉", "🦉", "🦉"), listOf("🦉", "🦉📖", "🦉"), listOf("🦉", "🦉", "🦉")),
        fontSize = 24,
      )
      PictureSceneType.PAINT_BRUSHES -> Canvas(Modifier.fillMaxWidth().height(110.dp)) {
        val rowH = size.height / 6f
        for (i in 0 until 5) {
          val y = rowH * (i + 1)
          val fat = i >= 3
          drawBrush(size.width * 0.15f, y, size.width * 0.7f, fat)
        }
      }
      PictureSceneType.SMALL_PENCILS -> Canvas(Modifier.fillMaxWidth().height(106.dp)) {
        val y1 = size.height * 0.22f
        val y2 = size.height * 0.44f
        val y3 = size.height * 0.66f
        val y4 = size.height * 0.86f
        val startX = size.width * 0.14f
        val fullW = size.width * 0.72f
        val shortW = fullW * 0.44f
        // 4 small pencils + 2 long pencils
        drawPencil(startX + fullW * 0.08f, y1, shortW, CoralTangerine)
        drawPencil(startX + fullW * 0.56f, y1, shortW, CoralTangerine)
        drawPencil(startX + fullW * 0.08f, y2, shortW, MangoGold)
        drawPencil(startX + fullW * 0.56f, y2, shortW, MangoGold)
        drawPencil(startX, y3, fullW, BagacayBlue)
        drawPencil(startX, y4, fullW, LeafEmerald)
      }
      PictureSceneType.SHAPES_GRID_A -> ShapesGridA()
      PictureSceneType.SHAPES_GRID_B -> ShapesGridB()
      PictureSceneType.PETS_GROUP -> EmojiGrid(
        rows = listOf(listOf("🐰", "🐱", "🐶", "🐕"), listOf("🐰", "🐕", "🐱", "🐰")),
      )
      PictureSceneType.TARSIERS_GROUP -> TarsiersScene()
      PictureSceneType.DRESSES_RIBBON -> EmojiGrid(
        rows = listOf(listOf("👗", "👗🎀", "👗🎀", "👗", "👗🎀", "👗🎀")),
        fontSize = 24,
      )
      PictureSceneType.BALLOONS_LIGHT -> Canvas(Modifier.fillMaxWidth().height(84.dp)) {
        val step = size.width / 9f
        for (i in 0 until 8) {
          val light = (i == 1 || i == 5)
          val cx = step * (i + 1)
          val cy = size.height * 0.4f
          drawOval(
            color = if (light) Color(0xFFFFF8E1) else Color(0xFF263238),
            topLeft = Offset(cx - 14.dp.toPx(), cy - 18.dp.toPx()),
            size = Size(28.dp.toPx(), 34.dp.toPx()),
          )
          drawOval(
            color = InkDark,
            topLeft = Offset(cx - 14.dp.toPx(), cy - 18.dp.toPx()),
            size = Size(28.dp.toPx(), 34.dp.toPx()),
            style = Stroke(1.8.dp.toPx()),
          )
          drawLine(InkMuted, Offset(cx, cy + 16.dp.toPx()), Offset(cx, size.height * 0.9f), strokeWidth = 1.5.dp.toPx())
        }
      }
      PictureSceneType.WHITE_CAPS -> EmojiGrid(
        rows = listOf(listOf("🧢⚪", "🧢⚪", "🧢⚫", "🧢⚪", "🧢⚫")),
        fontSize = 22,
      )
      PictureSceneType.HEART_SHAPES -> EmojiGrid(
        rows = listOf(listOf("❤️", "❤️", "🍀", "➕"), listOf("✖️", "⭐", "❤️", "❤️")),
      )
      PictureSceneType.UNLOCKED_PADLOCKS -> EmojiGrid(
        rows = listOf(listOf("🔓", "🔓", "🔓", "🔓", "🔓")),
      )
    }
  }
}

@Composable
private fun EmojiGrid(rows: List<List<String>>, fontSize: Int = 28) {
  Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
    rows.forEach { row ->
      Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
        row.forEach { Text(it, fontSize = fontSize.sp) }
      }
    }
  }
}

@Composable
private fun TarsiersScene() {
  Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(6.dp)) {
    Row(horizontalArrangement = Arrangement.spacedBy(20.dp), verticalAlignment = Alignment.CenterVertically) {
      Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("🌿🐒", fontSize = 26.sp)
        Text("on branch →", fontSize = 10.sp, color = InkMuted)
      }
      Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("🐒", fontSize = 28.sp)
        Text("front", fontSize = 10.sp, color = InkMuted)
      }
    }
    Row(horizontalArrangement = Arrangement.spacedBy(18.dp), verticalAlignment = Alignment.CenterVertically) {
      Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("🌿🐒🕶️", fontSize = 24.sp)
        Text("branch + goggles", fontSize = 10.sp, color = InkMuted)
      }
      Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("🐒⬅️", fontSize = 24.sp)
        Text("facing left", fontSize = 10.sp, color = InkMuted)
      }
      Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("🐒⬅️", fontSize = 24.sp)
        Text("facing left", fontSize = 10.sp, color = InkMuted)
      }
    }
  }
}

@Composable
private fun ShapesGridA() {
  // 12 shapes: 4 with 4 sides, 2 with >6 sides, 2 with 0 sides, 4 others (3/6 sides)
  Canvas(Modifier.fillMaxWidth().height(110.dp)) {
    val cols = 6
    val dx = size.width / (cols + 1)
    val y1 = size.height * 0.28f
    val y2 = size.height * 0.72f
    val r = 16.dp.toPx()
    val stroke = Stroke(2.dp.toPx())
    // Row 1: 3-point concave (6 sides), triangle (3), oval (0), 8-pt burst (>6), parallelogram (4), trapezoid (4)
    drawConcaveBoomerang(Offset(dx * 1, y1), r)
    drawPolygon(Offset(dx * 2, y1), r, 3, true)
    drawOval(InkDark, Offset(dx * 3 - r * 0.65f, y1 - r), Size(r * 1.3f, r * 2))
    drawStarBurst(Offset(dx * 4, y1), r, 8)
    drawParallelogram(Offset(dx * 5, y1), r)
    drawTrapezoid(Offset(dx * 6, y1), r)
    // Row 2: L-step (6 sides), hexagon (6), circle (0), square (4), tilted rect (4), 10-pt sun (>6)
    drawLShape(Offset(dx * 1, y2), r)
    drawPolygon(Offset(dx * 2, y2), r, 6, true)
    drawCircle(InkDark, r * 0.85f, Offset(dx * 3, y2))
    drawRect(Color(0xFF90A4AE), Offset(dx * 4 - r * 0.75f, y2 - r * 0.75f), Size(r * 1.5f, r * 1.5f))
    drawRect(InkDark, Offset(dx * 4 - r * 0.75f, y2 - r * 0.75f), Size(r * 1.5f, r * 1.5f), style = stroke)
    drawRect(Color(0xFFB0BEC5), Offset(dx * 5 - r * 0.8f, y2 - r * 0.55f), Size(r * 1.6f, r * 1.1f))
    drawRect(InkDark, Offset(dx * 5 - r * 0.8f, y2 - r * 0.55f), Size(r * 1.6f, r * 1.1f), style = stroke)
    drawStarBurst(Offset(dx * 6, y2), r, 10)
  }
}

@Composable
private fun ShapesGridB() {
  // 10 shapes: 6 triangles (3 sides) and 4 non-triangles
  Canvas(Modifier.fillMaxWidth().height(106.dp)) {
    val cols = 5
    val dx = size.width / (cols + 1)
    val y1 = size.height * 0.28f
    val y2 = size.height * 0.72f
    val r = 16.dp.toPx()
    // Row 1: tri, wedge, pentagon, tri, tri
    drawPolygon(Offset(dx * 1, y1), r, 3, true)
    drawArc(InkDark, 0f, 110f, true, Offset(dx * 2 - r, y1 - r), Size(r * 2, r * 2))
    drawPolygon(Offset(dx * 3, y1), r, 5, true)
    drawPolygon(Offset(dx * 4, y1), r, 3, true)
    drawPolygon(Offset(dx * 5, y1), r, 3, true)
    // Row 2: tri, arc-wedge, tri, square, tri
    drawPolygon(Offset(dx * 1, y2), r, 3, true)
    drawArc(InkDark, 200f, 100f, true, Offset(dx * 2 - r, y2 - r), Size(r * 2, r * 2))
    drawPolygon(Offset(dx * 3, y2), r, 3, true)
    drawRect(Color(0xFF90A4AE), Offset(dx * 4 - r * 0.75f, y2 - r * 0.75f), Size(r * 1.5f, r * 1.5f))
    drawPolygon(Offset(dx * 5, y2), r, 3, true)
  }
}

private fun DrawScope.drawPolygon(c: Offset, r: Float, sides: Int, filled: Boolean) {
  val p = Path()
  for (i in 0 until sides) {
    val a = (i * 360.0 / sides - 90.0) * PI / 180.0
    val x = c.x + (r * cos(a)).toFloat()
    val y = c.y + (r * sin(a)).toFloat()
    if (i == 0) p.moveTo(x, y) else p.lineTo(x, y)
  }
  p.close()
  if (filled) drawPath(p, Color(0xFF37474F))
  drawPath(p, InkDark, style = Stroke(1.8.dp.toPx()))
}

private fun DrawScope.drawStarBurst(c: Offset, r: Float, points: Int) {
  val p = Path()
  for (i in 0 until points * 2) {
    val rad = if (i % 2 == 0) r else r * 0.45f
    val a = (i * 180.0 / points - 90.0) * PI / 180.0
    val x = c.x + (rad * cos(a)).toFloat()
    val y = c.y + (rad * sin(a)).toFloat()
    if (i == 0) p.moveTo(x, y) else p.lineTo(x, y)
  }
  p.close()
  drawPath(p, InkDark)
}

private fun DrawScope.drawConcaveBoomerang(c: Offset, r: Float) {
  val p = Path().apply {
    moveTo(c.x, c.y - r)
    lineTo(c.x + r * 0.25f, c.y)
    lineTo(c.x + r * 0.9f, c.y + r * 0.8f)
    lineTo(c.x, c.y + r * 0.3f)
    lineTo(c.x - r * 0.9f, c.y + r * 0.8f)
    lineTo(c.x - r * 0.25f, c.y)
    close()
  }
  drawPath(p, InkDark)
}

private fun DrawScope.drawParallelogram(c: Offset, r: Float) {
  val p = Path().apply {
    moveTo(c.x - r * 0.5f, c.y - r * 0.6f)
    lineTo(c.x + r * 0.9f, c.y - r * 0.6f)
    lineTo(c.x + r * 0.5f, c.y + r * 0.6f)
    lineTo(c.x - r * 0.9f, c.y + r * 0.6f)
    close()
  }
  drawPath(p, Color(0xFF455A64))
}

private fun DrawScope.drawTrapezoid(c: Offset, r: Float) {
  val p = Path().apply {
    moveTo(c.x - r * 0.5f, c.y - r * 0.65f)
    lineTo(c.x + r * 0.5f, c.y - r * 0.65f)
    lineTo(c.x + r * 0.9f, c.y + r * 0.65f)
    lineTo(c.x - r * 0.9f, c.y + r * 0.65f)
    close()
  }
  drawPath(p, Color(0xFF37474F))
}

private fun DrawScope.drawLShape(c: Offset, r: Float) {
  val p = Path().apply {
    moveTo(c.x - r * 0.8f, c.y - r * 0.8f)
    lineTo(c.x, c.y - r * 0.8f)
    lineTo(c.x, c.y)
    lineTo(c.x + r * 0.8f, c.y)
    lineTo(c.x + r * 0.8f, c.y + r * 0.8f)
    lineTo(c.x - r * 0.8f, c.y + r * 0.8f)
    close()
  }
  drawPath(p, Color(0xFF78909C))
}

private fun DrawScope.drawTennisRacket(cx: Float, cy: Float, upsideDown: Boolean) {
  val headY = if (upsideDown) cy + 16.dp.toPx() else cy - 16.dp.toPx()
  val handleStart = if (upsideDown) cy - 2.dp.toPx() else cy + 2.dp.toPx()
  val handleEnd = if (upsideDown) cy - 34.dp.toPx() else cy + 34.dp.toPx()
  drawOval(
    color = BagacayBlue,
    topLeft = Offset(cx - 12.dp.toPx(), headY - 16.dp.toPx()),
    size = Size(24.dp.toPx(), 32.dp.toPx()),
    style = Stroke(2.5.dp.toPx()),
  )
  drawLine(CoralTangerine, Offset(cx, handleStart), Offset(cx, handleEnd), strokeWidth = 4.dp.toPx())
}

private fun DrawScope.drawBrush(startX: Float, y: Float, width: Float, fatTip: Boolean) {
  val handleEnd = startX + width * 0.72f
  drawLine(InkDark, Offset(startX, y), Offset(handleEnd, y), strokeWidth = 5.dp.toPx())
  if (fatTip) {
    drawOval(CoralTangerine, Offset(handleEnd, y - 6.dp.toPx()), Size(width * 0.25f, 12.dp.toPx()))
  } else {
    drawLine(BagacayBlue, Offset(handleEnd, y), Offset(startX + width * 0.95f, y), strokeWidth = 2.5.dp.toPx())
  }
}

private fun DrawScope.drawPencil(startX: Float, y: Float, width: Float, bodyColor: Color) {
  drawRoundRect(
    color = bodyColor,
    topLeft = Offset(startX, y - 4.dp.toPx()),
    size = Size(width * 0.86f, 8.dp.toPx()),
    cornerRadius = CornerRadius(2.dp.toPx()),
  )
  val tip = Path().apply {
    moveTo(startX + width * 0.86f, y - 4.dp.toPx())
    lineTo(startX + width, y)
    lineTo(startX + width * 0.86f, y + 4.dp.toPx())
    close()
  }
  drawPath(tip, Color(0xFFFFD180))
}

@Composable
fun GroupingDivisionScene(
  scene: GroupingSceneType,
  total: Int,
  groupSize: Int,
  showGroups: Boolean,
  modifier: Modifier = Modifier,
) {
  val emoji = when (scene) {
    GroupingSceneType.COLOR_PENCILS -> "✏️"
    GroupingSceneType.DONUTS -> "🍩"
    GroupingSceneType.FLOWERS -> "🌸"
    GroupingSceneType.BALLOONS -> "🎈"
    GroupingSceneType.CANDIES -> "🍬"
    GroupingSceneType.BROOMS -> "🧹"
  }
  val groupCount = (total / groupSize).coerceAtLeast(1)
  Column(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(18.dp))
      .background(Color(0xFFFFFDF8))
      .border(1.5.dp, OutlineSoft, RoundedCornerShape(18.dp))
      .padding(12.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(8.dp),
  ) {
    if (showGroups) {
      @OptIn(ExperimentalLayoutApi::class)
      FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
      ) {
        repeat(groupCount) {
          Row(
            modifier = Modifier
              .clip(RoundedCornerShape(12.dp))
              .background(TealLight)
              .border(1.5.dp, TealOcean, RoundedCornerShape(12.dp))
              .padding(horizontal = 8.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(3.dp),
          ) {
            repeat(groupSize) { Text(emoji, fontSize = 20.sp) }
          }
        }
      }
    } else {
      @OptIn(ExperimentalLayoutApi::class)
      FlowRow(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
      ) {
        repeat(total) { Text(emoji, fontSize = 22.sp) }
      }
    }
  }
}
