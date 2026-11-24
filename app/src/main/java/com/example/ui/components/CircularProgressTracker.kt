package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.SheetCoordinateHelper
import com.example.ui.theme.LeafEmerald
import com.example.ui.theme.MangoGold

@Composable
fun CircularProgressTracker(
  completedCount: Int,
  totalCount: Int = SheetCoordinateHelper.TOTAL_ACTIVITIES,
  size: Dp = 116.dp,
  strokeWidth: Dp = 12.dp,
  trackColor: Color = Color.White.copy(alpha = 0.22f),
  textColor: Color = Color.White,
  subtitleColor: Color = Color.White.copy(alpha = 0.88f),
) {
  val targetFraction = if (totalCount > 0) (completedCount.toFloat() / totalCount.toFloat()).coerceIn(0f, 1f) else 0f
  val animatedFraction by animateFloatAsState(
    targetValue = targetFraction,
    animationSpec = tween(durationMillis = 700, easing = FastOutSlowInEasing),
    label = "circular_progress_sweep",
  )
  val percent = (targetFraction * 100f).toInt()

  Box(
    modifier = Modifier
      .size(size)
      .testTag("circular_progress_tracker"),
    contentAlignment = Alignment.Center,
  ) {
    Canvas(modifier = Modifier.size(size)) {
      val strokePx = strokeWidth.toPx()
      val diameter = this.size.minDimension - strokePx
      val topLeft = Offset(strokePx / 2f, strokePx / 2f)
      val arcSize = Size(diameter, diameter)

      drawArc(
        color = trackColor,
        startAngle = 0f,
        sweepAngle = 360f,
        useCenter = false,
        topLeft = topLeft,
        size = arcSize,
        style = Stroke(width = strokePx, cap = StrokeCap.Round),
      )

      if (animatedFraction > 0f) {
        drawArc(
          brush = Brush.sweepGradient(
            colors = listOf(MangoGold, Color(0xFFFFD54F), LeafEmerald, MangoGold),
          ),
          startAngle = -90f,
          sweepAngle = (animatedFraction * 360f).coerceAtLeast(6f),
          useCenter = false,
          topLeft = topLeft,
          size = arcSize,
          style = Stroke(width = strokePx, cap = StrokeCap.Round),
        )
      }
    }

    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center,
    ) {
      Text(
        text = "$completedCount/$totalCount",
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold,
        color = textColor,
      )
      Text(
        text = "$percent% Done",
        style = MaterialTheme.typography.labelMedium,
        fontWeight = FontWeight.SemiBold,
        color = subtitleColor,
      )
    }
  }
}
