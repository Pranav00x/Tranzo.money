package com.tranzo.app.ui.theme

import androidx.compose.ui.graphics.Color

/**
 * Tranzo color palette — CheQ-inspired.
 *
 * Dark teal gradient headers, vivid green CTAs,
 * clean white surfaces with subtle gray cards.
 */
object TranzoColors {
    // ── Primary ──────────────────────────────────────────────
    val PrimaryGreen      = Color(0xFF1D9E75)
    val PrimaryGreenDark  = Color(0xFF178A65)
    val LightTeal         = Color(0xFF5DCAA5)
    val PaleTeal          = Color(0xFFE0F5ED) // Light green tinted background

    // ── Gradient (Header / Dashboard) ────────────────────────
    val Navy              = Color(0xFF0B1D2E)
    val DarkTeal          = Color(0xFF0F3D3E)
    val GradientMid       = Color(0xFF143833)

    // ── Surfaces ─────────────────────────────────────────────
    val White             = Color(0xFFFFFFFE) // Avoid pure white
    val Background        = Color(0xFFF8FAFB)
    val CardSurface       = Color(0xFFFFFFFF)
    val LightGray         = Color(0xFFF5F7FA)
    val BorderGray        = Color(0xFFE5E8EB)
    val DividerGray       = Color(0xFFEEF0F2)

    // ── Text ─────────────────────────────────────────────────
    val TextPrimary       = Color(0xFF1A1A2E)
    val TextSecondary     = Color(0xFF6B7280)
    val TextTertiary      = Color(0xFF9CA3AF)
    val TextOnDark        = Color(0xFFFFFFFF)
    val TextOnDarkMuted   = Color(0xFFB0C4C8)
    val TextOnGreen       = Color(0xFFFFFFFF)

    // ── Status ───────────────────────────────────────────────
    val Success           = Color(0xFF1D9E75) // Same as primary
    val Error             = Color(0xFFEF4444)
    val ErrorLight        = Color(0xFFFEE2E2)
    val Warning           = Color(0xFFF59E0B)
    val WarningLight      = Color(0xFFFEF3C7)
    val Info              = Color(0xFF3B82F6)

    // ── Badges ───────────────────────────────────────────────
    val BadgeGreen        = Color(0xFF1D9E75)
    val BadgeGreenBg      = Color(0xFFDCFCE7)
    val BadgeRed          = Color(0xFFEF4444)
    val BadgeRedBg        = Color(0xFFFEE2E2)
    val BadgeNew          = Color(0xFF1D9E75)

    // ── Skip Button ──────────────────────────────────────────
    val SkipButtonBg      = Color(0xFFE0F5ED) // Light green tint
    val SkipButtonText    = Color(0xFF1D9E75)

    // ── Bottom Nav ───────────────────────────────────────────
    val NavActive         = Color(0xFF1D9E75)
    val NavInactive       = Color(0xFF9CA3AF)

    // ── Shimmer ──────────────────────────────────────────────
    val ShimmerBase       = Color(0xFFE5E8EB)
    val ShimmerHighlight  = Color(0xFFF5F7FA)
}
