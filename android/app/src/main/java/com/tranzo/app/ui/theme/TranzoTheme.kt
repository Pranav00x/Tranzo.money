package com.tranzo.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// ═══════════════════════════════════════════════════════════════
// CLAYMORPHISM DESIGN SYSTEM — PREMIUM CRYPTO WALLET
// 
// Design DNA: Soft, tactile, 3D toy-like surfaces with warm depth.
// Think: Sculpted matte clay with gentle lighting from top-left.
// Palette: Warm neutrals + saturated pastel accents.
// ═══════════════════════════════════════════════════════════════
object TranzoColors {
    // ─── CLAYMORPHISM CORE SURFACES ──────────────────────
    // The background is a warm off-white with a peach/lavender tint — NOT generic blue
    val ClayBackground = Color(0xFFF0EBF4)       // Warm lavender-tinted background
    val ClayBackgroundAlt = Color(0xFFE8E2EE)    // Slightly darker for depth
    val ClayCard = Color(0xFFFCFAFE)             // Off-white card (warm tint)
    val ClayCardPressed = Color(0xFFF5F2FA)      // Pressed state

    // 3D Shadow colors — the magic of claymorphism
    val ClayShadowDark = Color(0x33B8AECC)       // Bottom-right shadow (purple-tinted)
    val ClayShadowLight = Color(0x66FFFFFF)       // Top-left highlight
    val ClayShadowBlue = Color(0x264A5AE8)       // Accent shadow for blue elements
    val ClayShadowGreen = Color(0x2622C97A)      // Accent shadow for green elements
    val ClayShadowPurple = Color(0x268E5DD5)     // Accent shadow for purple elements

    // ─── PRIMARY ACCENT PALETTE ──────────────────────────
    // Royal indigo — premium, trustworthy, not generic
    val ClayBlue = Color(0xFF4A5AE8)             // Deep indigo-blue (primary CTA)
    val ClayBlueMuted = Color(0xFF7B88F0)        // Lighter indigo
    val ClayBlueSoft = Color(0xFFDDD9F7)         // Tinted surface

    // Vibrant green — success, receives, growth
    val ClayGreen = Color(0xFF22C97A)            // Rich emerald green
    val ClayGreenMuted = Color(0xFF5EDDA0)       // Lighter green
    val ClayGreenSoft = Color(0xFFD4F5E4)        // Tinted surface

    // Warm coral — alerts, sends, warmth
    val ClayCoral = Color(0xFFFF6B7A)            // Warm coral-red
    val ClayCoralMuted = Color(0xFFFF9EA8)        // Lighter coral
    val ClayCoralSoft = Color(0xFFFFE5E8)        // Tinted surface

    // Soft purple — secondary accent, premium
    val ClayPurple = Color(0xFF8E5DD5)           // Rich lavender-purple
    val ClayPurpleMuted = Color(0xFFB08BE8)      // Lighter purple
    val ClayPurpleSoft = Color(0xFFEDE4F9)       // Tinted surface

    // Warm amber — warnings, highlights
    val ClayAmber = Color(0xFFF5A623)            // Rich amber-gold
    val ClayAmberMuted = Color(0xFFFFC86B)       // Lighter amber
    val ClayAmberSoft = Color(0xFFFFF3DB)        // Tinted surface

    // Teal accent — info, links
    val ClayTeal = Color(0xFF2ABFBF)             // Vibrant teal
    val ClayTealSoft = Color(0xFFD4F4F4)         // Tinted surface

    // ─── INPUT FIELD COLORS ──────────────────────────────
    val ClayInputBg = Color(0xFFF7F5FA)          // Warm input background
    val ClayInputBorder = Color(0xFFE4DFF0)      // Subtle purple-tinted border
    val ClayInputFocusBorder = Color(0xFF4A5AE8) // Focused = primary

    // ─── TEXT HIERARCHY ──────────────────────────────────
    val TextPrimary = Color(0xFF1E1B2E)          // Deep dark indigo (not pure black)
    val TextSecondary = Color(0xFF6B6580)         // Warm slate
    val TextTertiary = Color(0xFF9E97B0)          // Light warm slate
    val TextDisabled = Color(0xFFC8C2D6)          // Very light slate
    val TextOnAccent = Color(0xFFFEFEFE)         // White text on colored surfaces

    // ─── LEGACY COMPAT (referenced across codebase) ──────
    val White = Color(0xFFFEFEFE)
    val BackgroundLight = ClayBackground
    val SurfaceLight = Color(0xFFF7F5FA)
    val SurfaceAlt = Color(0xFFEDE8F5)
    val DividerGray = Color(0xFFE8E2F0)

    // Legacy names used by some screens
    val PrimaryBlue = ClayBlue
    val PrimaryPurple = ClayPurple
    val PrimaryPink = ClayCoral
    val PrimaryGreen = ClayGreen
    val PrimaryYellow = ClayAmber
    val PrimaryOrange = Color(0xFFFF8F5C)
    val BlueLight = ClayBlueMuted
    val PurpleLight = ClayPurpleMuted
    val PinkLight = ClayCoralMuted
    val AccentEmerald = Color(0xFF34D399)
    val AccentCyan = ClayTeal

    // Dark mode (not currently used in clay, but kept for compat)
    val BackgroundDark = Color(0xFF0F0F1E)
    val SurfaceDark = Color(0xFF1A1A2E)
    val TextDarkPrimary = Color(0xFFF5F3FF)
    val TextDarkSecondary = Color(0xFFB8BFDB)

    // Status colors
    val Success = ClayGreen
    val Error = ClayCoral
    val ErrorLight = ClayCoralSoft
    val Warning = ClayAmber
    val Info = ClayBlue

    // Navigation colors — clay-styled
    val NavActive = ClayBlue
    val NavInactive = Color(0xFFB5AEC5)
    val NavBackground = Color(0xFFFCFAFE)
    val NavIndicator = ClayBlueSoft
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
