package com.example.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

private val LightColorScheme = lightColorScheme(
  primary = BagacayBlue,
  onPrimary = Color.White,
  primaryContainer = BagacayBlueLight,
  onPrimaryContainer = InkDark,
  secondary = CoralTangerine,
  onSecondary = Color.White,
  secondaryContainer = MangoGoldLight,
  onSecondaryContainer = InkDark,
  tertiary = LeafEmerald,
  onTertiary = Color.White,
  tertiaryContainer = LeafEmeraldLight,
  onTertiaryContainer = InkDark,
  background = CreamBackground,
  onBackground = InkDark,
  surface = WarmSurface,
  onSurface = InkDark,
  surfaceVariant = WarmSurfaceVariant,
  onSurfaceVariant = InkMuted,
  outline = OutlineSoft,
  error = CrimsonRed,
  errorContainer = CrimsonLight,
  onErrorContainer = CrimsonRed,
)

private val DarkColorScheme = darkColorScheme(
  primary = Color(0xFF7BB8FF),
  onPrimary = Color(0xFF002E69),
  primaryContainer = Color(0xFF004596),
  onPrimaryContainer = Color(0xFFD6E9FF),
  secondary = Color(0xFFFFB74D),
  onSecondary = Color(0xFF4A2500),
  secondaryContainer = Color(0xFF6B3800),
  onSecondaryContainer = Color(0xFFFFE0B2),
  tertiary = Color(0xFF81C784),
  onTertiary = Color(0xFF003910),
  tertiaryContainer = Color(0xFF1B5E20),
  onTertiaryContainer = Color(0xFFC8E6C9),
  background = DarkBackground,
  onBackground = Color(0xFFF3F4F8),
  surface = DarkSurface,
  onSurface = Color(0xFFF3F4F8),
  surfaceVariant = DarkSurfaceVariant,
  onSurfaceVariant = Color(0xFFC2C8D8),
  outline = Color(0xFF3E4761),
)

val AralShapes = Shapes(
  small = RoundedCornerShape(12.dp),
  medium = RoundedCornerShape(20.dp),
  large = RoundedCornerShape(28.dp),
)

@Composable
fun AralMathTheme(
  darkTheme: Boolean = false,
  content: @Composable () -> Unit,
) {
  val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
  MaterialTheme(
    colorScheme = colorScheme,
    typography = AralTypography,
    shapes = AralShapes,
    content = content,
  )
}
