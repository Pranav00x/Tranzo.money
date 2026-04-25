package com.tranzo.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Claymorphism premium crypto palette for Gen Z
// Trust-focused with vibrant, playful surfaces
object TranzoColors {
    // Primary gradient colors - vibrant, sophisticated
    val PrimaryBlue = Color(0xFF5B8DEF)        // Warm blue (primary)
    val PrimaryPurple = Color(0xFF9D6BD5)      // Soft purple
    val PrimaryPink = Color(0xFFFF6B9D)        // Vibrant pink (accent)
    val PrimaryGreen = Color(0xFF4ECCA3)       // Mint green
    val PrimaryYellow = Color(0xFFFFD166)      // Soft gold

    // Secondary gradient stops for smooth transitions
    val BlueLight = Color(0xFF7BA8F7)          // Light blue
    val PurpleLight = Color(0xFFB89FE0)        // Light purple
    val PinkLight = Color(0xFFFF8FB3)          // Light pink

    // Neutral palette - refined, warm-tinted
    val TextPrimary = Color(0xFF1A1A2E)        // Deep navy
    val TextSecondary = Color(0xFF5A5F7F)      // Slate
    val TextTertiary = Color(0xFF9BA3B8)       // Light slate
    val TextDisabled = Color(0xFFD0D4E8)       // Lighter slate

    val White = Color(0xFFFEFEFE)
    val BackgroundLight = Color(0xFFF8F6FF)    // Soft lavender-white
    val SurfaceLight = Color(0xFFF0EDFF)       // Light lavender
    val SurfaceAlt = Color(0xFFE8E5FF)         // Secondary surface
    val DividerGray = Color(0xFFE8E5FF)        // Divider

    // Dark mode variants
    val BackgroundDark = Color(0xFF0F0F1E)
    val SurfaceDark = Color(0xFF1A1A2E)
    val TextDarkPrimary = Color(0xFFF5F3FF)
    val TextDarkSecondary = Color(0xFFB8BFDB)

    // Status colors with claymorphism tint
    val Success = Color(0xFF4ECCA3)
    val Error = Color(0xFFFF6B6B)
    val ErrorLight = Color(0xFFFFF0F0)
    val Warning = Color(0xFFFFB84D)
    val Info = Color(0xFF5B8DEF)

    // Navigation colors
    val NavActive = Color(0xFF5B8DEF)
    val NavInactive = Color(0xFFB8BFDB)
}

@Composable
fun TranzoTheme(
    themeId: String = "default_dark",
    content: @Composable () -> Unit
) {
    // Get the color scheme based on theme ID from AppThemes.kt
    val colorScheme = when (themeId) {
        "default_dark" -> DarkThemeColors
        "purple_night" -> PurpleThemeColors
        "ocean" -> OceanThemeColors
        "sunset" -> SunsetThemeColors
        "mint" -> MintThemeColors
        "pink" -> PinkThemeColors
        "gold" -> GoldThemeColors
        "cyberpunk" -> CyberpunkThemeColors
        else -> DarkThemeColors
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = TranzoTypography,
        shapes = TranzoShapes,
        content = content
    )
}
