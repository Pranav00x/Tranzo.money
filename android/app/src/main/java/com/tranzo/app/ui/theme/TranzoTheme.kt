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
    // ─── MINIMAL CORE SURFACES ───────────────────────────
    val ClayBackground = Color(0xFFF9F9F9)       // Pure light gray/white background
    val ClayBackgroundAlt = Color(0xFFF3F3F3)    // Slightly darker background
    val ClayCard = Color(0xFFFFFFFF)             // Pure white cards
    val ClayCardPressed = Color(0xFFF5F5F5)      // Pressed state

    val ClayShadowDark = Color(0x08000000)       // Barely visible shadow
    val ClayShadowLight = Color(0x00FFFFFF)       // No highlight
    val ClayShadowBlue = Color(0x08000000)       
    val ClayShadowGreen = Color(0x08000000)      
    val ClayShadowPurple = Color(0x08000000)     

    // ─── PRIMARY ACCENT PALETTE ──────────────────────────
    // Minimalist means black is the primary accent
    val ClayBlue = Color(0xFF111111)             // Near Black
    val ClayBlueMuted = Color(0xFF333333)        // Dark Gray
    val ClayBlueSoft = Color(0xFFF0F0F0)         // Light Gray Tint

    // Vibrant green — keep for success/receives but cleaner
    val ClayGreen = Color(0xFF00C853)            
    val ClayGreenMuted = Color(0xFF69F0AE)       
    val ClayGreenSoft = Color(0xFFE8F5E9)        

    // Warm coral — keep for errors/sends
    val ClayCoral = Color(0xFFD50000)            
    val ClayCoralMuted = Color(0xFFFF5252)        
    val ClayCoralSoft = Color(0xFFFFEBEE)        

    // Purple -> mapped to black/gray for minimal consistency
    val ClayPurple = Color(0xFF111111)           
    val ClayPurpleMuted = Color(0xFF666666)      
    val ClayPurpleSoft = Color(0xFFF5F5F5)       

    // Amber -> warnings
    val ClayAmber = Color(0xFFFFAB00)            
    val ClayAmberMuted = Color(0xFFFFD740)       
    val ClayAmberSoft = Color(0xFFFFF8E1)        

    val ClayTeal = Color(0xFF111111)             
    val ClayTealSoft = Color(0xFFF5F5F5)         

    // ─── INPUT FIELD COLORS ──────────────────────────────
    val ClayInputBg = Color(0xFFFFFFFF)          
    val ClayInputBorder = Color(0xFFE0E0E0)      
    val ClayInputFocusBorder = Color(0xFF111111) 

    // ─── TEXT HIERARCHY ──────────────────────────────────
    val TextPrimary = Color(0xFF111111)          // Near Black
    val TextSecondary = Color(0xFF757575)         // Medium Gray
    val TextTertiary = Color(0xFFBDBDBD)          // Light Gray
    val TextDisabled = Color(0xFFE0E0E0)          
    val TextOnAccent = Color(0xFFFFFFFF)         

    // ─── LEGACY COMPAT ───────────────────────────────────
    val White = Color(0xFFFFFFFF)
    val BackgroundLight = ClayBackground
    val SurfaceLight = Color(0xFFFFFFFF)
    val SurfaceAlt = Color(0xFFF5F5F5)
    val DividerGray = Color(0xFFEBEBEB)

    val PrimaryBlue = ClayBlue
    val PrimaryPurple = ClayPurple
    val PrimaryPink = ClayCoral
    val PrimaryGreen = ClayGreen
    val PrimaryYellow = ClayAmber
    val PrimaryOrange = Color(0xFFFF6D00)
    val BlueLight = ClayBlueMuted
    val PurpleLight = ClayPurpleMuted
    val PinkLight = ClayCoralMuted
    val AccentEmerald = ClayGreen
    val AccentCyan = ClayTeal

    val BackgroundDark = Color(0xFF111111)
    val SurfaceDark = Color(0xFF222222)
    val TextDarkPrimary = Color(0xFFFFFFFF)
    val TextDarkSecondary = Color(0xFF9E9E9E)

    val Success = ClayGreen
    val Error = ClayCoral
    val ErrorLight = ClayCoralSoft
    val Warning = ClayAmber
    val Info = ClayBlue

    val NavActive = Color(0xFF111111)
    val NavInactive = Color(0xFF9E9E9E)
    val NavBackground = Color(0xFFFFFFFF)
    val NavIndicator = Color(0xFFF5F5F5)
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
