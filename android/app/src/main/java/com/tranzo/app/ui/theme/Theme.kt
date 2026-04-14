package com.tranzo.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val TranzoColorScheme = lightColorScheme(
    primary = TranzoColors.PrimaryGreen,
    onPrimary = TranzoColors.TextOnGreen,
    primaryContainer = TranzoColors.PaleTeal,
    onPrimaryContainer = TranzoColors.PrimaryGreenDark,

    secondary = TranzoColors.LightTeal,
    onSecondary = TranzoColors.TextOnGreen,

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
fun TranzoTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = TranzoColorScheme,
        typography = TranzoTypography,
        shapes = TranzoShapes,
        content = content,
    )
}
