package com.tranzo.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val TranzoColorScheme = lightColorScheme(
    primary = TranzoColors.PrimaryBlack,
    onPrimary = TranzoColors.White,
    primaryContainer = TranzoColors.PaleTeal,
    onPrimaryContainer = TranzoColors.DarkGray,

    secondary = TranzoColors.LightTeal,
    onSecondary = TranzoColors.White,

    background = TranzoColors.Background,
    onBackground = TranzoColors.TextPrimary,

    surface = TranzoColors.CardSurface,
    onSurface = TranzoColors.TextPrimary,
    surfaceVariant = TranzoColors.LightGray,
    onSurfaceVariant = TranzoColors.TextSecondary,

    outline = TranzoColors.BorderGray,
    outlineVariant = TranzoColors.DividerGray,

    error = TranzoColors.Error,
    onError = TranzoColors.White,
)

@Composable
fun TranzoTheme(
    themeId: String = "default_dark",
    content: @Composable () -> Unit,
) {
    // Get the color scheme based on theme ID
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
        content = content,
    )
}
