package com.example.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Backspace
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.ui.theme.*
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

@Composable
fun KidNumberPad(
  onDigit: (String) -> Unit,
  onBackspace: () -> Unit,
  onClear: () -> Unit,
  onSubmit: () -> Unit,
  showSlash: Boolean = false,
  showDecimal: Boolean = false,
  submitEnabled: Boolean = true,
  accentColor: Color = BagacayBlue,
  modifier: Modifier = Modifier,
) {
  Column(
    modifier = modifier.fillMaxWidth(),
    verticalArrangement = Arrangement.spacedBy(8.dp),
  ) {
    val rows = listOf(
      listOf("1", "2", "3", "4", "5"),
      listOf("6", "7", "8", "9", "0"),
    )
    rows.forEach { row ->
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
      ) {
        row.forEach { key ->
          FilledTonalButton(
            onClick = { onDigit(key) },
            modifier = Modifier
              .weight(1f)
              .height(52.dp)
              .testTag("keypad_$key"),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.filledTonalButtonColors(
              containerColor = MaterialTheme.colorScheme.surfaceVariant,
              contentColor = MaterialTheme.colorScheme.onSurface,
            ),
            contentPadding = PaddingValues(0.dp),
          ) {
            Text(
              text = key,
              style = MaterialTheme.typography.headlineSmall,
              fontWeight = FontWeight.Bold,
            )
          }
        }
      }
    }
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
      if (showSlash) {
        FilledTonalButton(
          onClick = { onDigit("/") },
          modifier = Modifier
            .weight(0.9f)
            .height(52.dp)
            .testTag("keypad_slash"),
          shape = RoundedCornerShape(16.dp),
          colors = ButtonDefaults.filledTonalButtonColors(
            containerColor = MangoGoldLight,
            contentColor = InkDark,
          ),
        ) {
          Text("/", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        }
      } else if (showDecimal) {
        FilledTonalButton(
          onClick = { onDigit(".") },
          modifier = Modifier
            .weight(0.9f)
            .height(52.dp)
            .testTag("keypad_dot"),
          shape = RoundedCornerShape(16.dp),
          colors = ButtonDefaults.filledTonalButtonColors(
            containerColor = MangoGoldLight,
            contentColor = InkDark,
          ),
        ) {
          Text(".", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }
      }
      OutlinedButton(
        onClick = onClear,
        modifier = Modifier
          .weight(1f)
          .height(52.dp)
          .testTag("keypad_clear"),
        shape = RoundedCornerShape(16.dp),
      ) {
        Icon(Icons.Default.Clear, contentDescription = "Clear", modifier = Modifier.size(18.dp))
        Spacer(Modifier.width(4.dp))
        Text("Clear", fontWeight = FontWeight.Bold)
      }
      OutlinedButton(
        onClick = onBackspace,
        modifier = Modifier
          .weight(0.9f)
          .height(52.dp)
          .testTag("keypad_backspace"),
        shape = RoundedCornerShape(16.dp),
      ) {
        Icon(Icons.AutoMirrored.Filled.Backspace, contentDescription = "Backspace")
      }
      Button(
        onClick = onSubmit,
        enabled = submitEnabled,
        modifier = Modifier
          .weight(1.4f)
          .height(52.dp)
          .testTag("submit_answer_button"),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = accentColor),
      ) {
        Icon(Icons.Default.CheckCircle, contentDescription = null, modifier = Modifier.size(20.dp))
        Spacer(Modifier.width(6.dp))
        Text("Check", fontWeight = FontWeight.Bold, fontSize = 16.sp)
      }
    }
  }
}

@Composable
fun ScratchpadDialog(
  onDismiss: () -> Unit,
) {
  val strokes = remember { mutableStateListOf<List<Offset>>() }
  var currentStroke by remember { mutableStateOf<List<Offset>>(emptyList()) }
  var penColor by remember { mutableStateOf(BagacayBlue) }

  Dialog(
    onDismissRequest = onDismiss,
    properties = DialogProperties(usePlatformDefaultWidth = false),
  ) {
    Surface(
      modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
        .windowInsetsPadding(WindowInsets.safeDrawing),
      shape = RoundedCornerShape(28.dp),
      color = WarmSurface,
      tonalElevation = 6.dp,
    ) {
      Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween,
        ) {
          Text(
            "Math Scratchpad",
            style = MaterialTheme.typography.headlineSmall,
            color = InkDark,
          )
          Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf(BagacayBlue, CoralTangerine, LeafEmerald, InkDark).forEach { color ->
              FilledIconButton(
                onClick = { penColor = color },
                colors = IconButtonDefaults.filledIconButtonColors(containerColor = color),
                modifier = Modifier.size(36.dp),
              ) {
                if (penColor == color) {
                  Box(
                    Modifier
                      .size(12.dp)
                      .clip(RoundedCornerShape(6.dp))
                      .background(Color.White),
                  )
                }
              }
            }
            IconButton(
              onClick = {
                strokes.clear()
                currentStroke = emptyList()
              },
            ) {
              Icon(Icons.Default.Delete, contentDescription = "Clear scratchpad", tint = CrimsonRed)
            }
            IconButton(onClick = onDismiss) {
              Icon(Icons.Default.Close, contentDescription = "Close scratchpad")
            }
          }
        }
        Text(
          "Use your finger to write solutions, carry numbers, or draw!",
          style = MaterialTheme.typography.bodyMedium,
          color = InkMuted,
        )
        Spacer(Modifier.height(10.dp))
        Box(
          modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFFFFFDF7))
            .pointerInput(Unit) {
              detectDragGestures(
                onDragStart = { offset -> currentStroke = listOf(offset) },
                onDrag = { change, _ ->
                  change.consume()
                  currentStroke = currentStroke + change.position
                },
                onDragEnd = {
                  if (currentStroke.isNotEmpty()) {
                    strokes.add(currentStroke)
                    currentStroke = emptyList()
                  }
                },
              )
            },
        ) {
          Canvas(modifier = Modifier.fillMaxSize()) {
            // Subtle notebook grid
            val step = 48.dp.toPx()
            var x = step
            while (x < size.width) {
              drawLine(Color(0xFFEAE2D0), Offset(x, 0f), Offset(x, size.height), strokeWidth = 1.dp.toPx())
              x += step
            }
            var y = step
            while (y < size.height) {
              drawLine(Color(0xFFEAE2D0), Offset(0f, y), Offset(size.width, y), strokeWidth = 1.dp.toPx())
              y += step
            }
            val strokeStyle = Stroke(width = 4.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
            (strokes + listOf(currentStroke)).forEach { pts ->
              if (pts.size > 1) {
                val p = Path().apply {
                  moveTo(pts.first().x, pts.first().y)
                  pts.drop(1).forEach { lineTo(it.x, it.y) }
                }
                drawPath(p, color = penColor, style = strokeStyle)
              }
            }
          }
        }
      }
    }
  }
}

private data class ConfettiParticle(
  val angle: Float,
  val speed: Float,
  val color: Color,
  val radius: Float,
)

@Composable
fun ConfettiOverlay(
  trigger: Int,
  modifier: Modifier = Modifier,
) {
  if (trigger == 0) return
  val progress = remember(trigger) { Animatable(0f) }
  val particles = remember(trigger) {
    val palette = listOf(MangoGold, BagacayBlue, LeafEmerald, CoralTangerine, BerryPurple, TealOcean)
    List(28) {
      ConfettiParticle(
        angle = Random.nextFloat() * 360f,
        speed = 0.35f + Random.nextFloat() * 0.65f,
        color = palette[it % palette.size],
        radius = 6f + Random.nextFloat() * 8f,
      )
    }
  }

  LaunchedEffect(trigger) {
    progress.snapTo(0f)
    progress.animateTo(1f, animationSpec = tween(durationMillis = 750))
  }

  if (progress.value < 1f) {
    Canvas(modifier = modifier.fillMaxSize()) {
      val cx = size.width / 2f
      val cy = size.height * 0.35f
      val maxDist = size.minDimension * 0.45f
      val alpha = (1f - progress.value).coerceIn(0f, 1f)
      particles.forEach { p ->
        val rad = Math.toRadians(p.angle.toDouble())
        val dist = maxDist * p.speed * progress.value
        val x = cx + (cos(rad) * dist).toFloat()
        val y = cy + (sin(rad) * dist).toFloat() + (progress.value * progress.value * 140f)
        drawCircle(
          color = p.color.copy(alpha = alpha),
          radius = p.radius,
          center = Offset(x, y),
        )
      }
    }
  }
}
