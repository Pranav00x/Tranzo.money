package com.tranzo.app.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/**
 * Tranzo Typography — using system default sans-serif.
 * Replace with DM Sans once font files are added to res/font/.
 *
 * To add DM Sans:
 * 1. Download from https://fonts.google.com/specimen/DM+Sans
 * 2. Place .ttf files in android/app/src/main/res/font/
 * 3. Uncomment the DmSans FontFamily below and use it in styles.
 */

// Uncomment when font files are added:
// val DmSans = FontFamily(
//     Font(R.font.dm_sans_regular, FontWeight.Normal),
//     Font(R.font.dm_sans_medium, FontWeight.Medium),
//     Font(R.font.dm_sans_semibold, FontWeight.SemiBold),
//     Font(R.font.dm_sans_bold, FontWeight.Bold),
// )

private val AppFontFamily = FontFamily.Default

val TranzoTypography = Typography(
    // Balance display — hero numbers
    displayLarge = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp,
        lineHeight = 44.sp,
        letterSpacing = (-0.5).sp,
    ),
    // Screen titles — "Getting Started", "Home"
    headlineLarge = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 26.sp,
        lineHeight = 34.sp,
    ),
    // Section headers — "Your Assets", "Recent Activity"
    headlineMedium = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 28.sp,
    ),
    // Card titles
    headlineSmall = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = 26.sp,
    ),
    // Body text
    bodyLarge = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
    ),
    // Secondary body text
    bodyMedium = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
    ),
    // Small text
    bodySmall = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
    ),
    // Button labels
    labelLarge = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
    ),
    // Small labels, badges
    labelMedium = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
    ),
    // Timestamps, captions
    labelSmall = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 11.sp,
        lineHeight = 14.sp,
    ),
)
