package com.tranzo.app.ui.theme

import androidx.compose.foundation.isSystemInDarkMode
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Bold, modern crypto wallet color palette
object TranzoColors {
    // Primary brand colors - vibrant, distinctive
    val PrimaryBlue = Color(0xFF0052CC)
    val PrimaryPurple = Color(0xFF7C3AED)
    val PrimaryGreen = Color(0xFF10B981)
    val PrimaryOrange = Color(0xFFF97316)
    val PrimaryPink = Color(0xFFEC4899)

    // Accent colors for status and interactions
    val AccentCyan = Color(0xFF06B6D4)
    val AccentViolet = Color(0xFFA78BFA)
    val AccentEmerald = Color(0xFF34D399)

    // Neutral palette - refined, modern
    val TextPrimary = Color(0xFF0F172A)        // Deep slate
    val TextSecondary = Color(0xFF64748B)      // Muted slate
    val TextTertiary = Color(0xFFAEB9D0)       // Light slate
    val TextDisabled = Color(0xFFCBD5E1)       // Lighter slate

    val White = Color(0xFFFFFFFF)
    val BackgroundLight = Color(0xFFFAFBFE)    // Cool white
    val SurfaceLight = Color(0xFFF1F5F9)       // Light cool
    val SurfaceAlt = Color(0xFFE2E8F0)         // Secondary surface
    val DividerGray = Color(0xFFE2E8F0)        // Divider

    // Dark mode variants
    val BackgroundDark = Color(0xFF0F1117)
    val SurfaceDark = Color(0xFF161B22)
    val TextDarkPrimary = Color(0xFFF0F6FC)
    val TextDarkSecondary = Color(0xFF8B949E)

    // Status colors
    val Success = Color(0xFF10B981)
    val Error = Color(0xFFEF4444)
    val Warning = Color(0xFFFBBF24)
    val Info = Color(0xFF3B82F6)
}

private val LightColors = lightColorScheme(
    primary = TranzoColors.PrimaryBlue,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFDEE9FF),
    onPrimaryContainer = TranzoColors.PrimaryBlue,
    secondary = TranzoColors.PrimaryPurple,
    onSecondary = Color.White,
    tertiary = TranzoColors.PrimaryGreen,
    onTertiary = Color.White,
    error = TranzoColors.Error,
    onError = Color.White,
    background = TranzoColors.BackgroundLight,
    onBackground = TranzoColors.TextPrimary,
    surface = TranzoColors.SurfaceLight,
    onSurface = TranzoColors.TextPrimary,
    surfaceVariant = TranzoColors.SurfaceAlt,
    onSurfaceVariant = TranzoColors.TextSecondary,
    scrim = Color.Black.copy(alpha = 0.32f)
)

private val DarkColors = darkColorScheme(
    primary = TranzoColors.AccentCyan,
    onPrimary = Color.Black,
    primaryContainer = TranzoColors.PrimaryBlue,
    onPrimaryContainer = Color.White,
    secondary = TranzoColors.PrimaryPurple,
    onSecondary = Color.White,
    tertiary = TranzoColors.PrimaryGreen,
    onTertiary = Color.White,
    error = TranzoColors.Error,
    onError = Color.White,
    background = TranzoColors.BackgroundDark,
    onBackground = TranzoColors.TextDarkPrimary,
    surface = TranzoColors.SurfaceDark,
    onSurface = TranzoColors.TextDarkPrimary,
    surfaceVariant = Color(0xFF21262D),
    onSurfaceVariant = TranzoColors.TextDarkSecondary,
    scrim = Color.Black.copy(alpha = 0.32f)
)

@Composable
fun TranzoTheme(
    themeId: String = "default_dark",
    content: @Composable () -> Unit
) {
    // Apply theme based on themeId (for now, just use light/dark system theme)
    val isDarkMode = isSystemInDarkMode()
    val colorScheme = if (isDarkMode) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colorScheme,
        typography = TranzoTypography,
        content = content
    )
}
