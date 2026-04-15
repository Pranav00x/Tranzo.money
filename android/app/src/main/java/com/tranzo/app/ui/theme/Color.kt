package com.tranzo.app.ui.theme

import androidx.compose.ui.graphics.Color

/**
 * Tranzo color palette — CheQ-inspired.
 *
 * Dark teal gradient headers, vivid green CTAs,
 * clean white surfaces with subtle gray cards.
 */
object TranzoColors {
    // ── Primary (Monochrome) ──────────────────────────────
    val PrimaryBlack      = Color(0xFF000000)
    val PrimaryWhite      = Color(0xFFFFFFFF)
    val DarkGray          = Color(0xFF1A1A1A)
    val MediumGray        = Color(0xFF666666)
    val LightGray         = Color(0xFFF5F5F5)
    
    // ── Brand Mapping (Converted to B&W) ─────────────────────
    val PrimaryGreen      = Color(0xFF000000)
    val PrimaryGreenDark  = Color(0xFF1A1A1A)
    val LightTeal         = Color(0xFF888888)
    val PaleTeal          = Color(0xFFF9F9F9)

    // ── Header / Gradients (Flat Black) ──────────────────────
    val Navy              = Color(0xFF000000)
    val DarkTeal          = Color(0xFF0D0D0D)
    val GradientMid       = Color(0xFF000000)

    // ── Surfaces ─────────────────────────────────────────────
    val White             = Color(0xFFFFFFFF)
    val Background        = Color(0xFFFFFFFF)
    val CardSurface       = Color(0xFFFFFFFF)
    val BorderGray        = Color(0xFFE0E0E0)
    val DividerGray       = Color(0xFFEEEEEE)

    // ── Text ─────────────────────────────────────────────────
    val TextPrimary       = Color(0xFF000000)
    val TextSecondary     = Color(0xFF555555)
    val TextTertiary      = Color(0xFF999999)
    val TextOnDark        = Color(0xFFFFFFFF)
    val TextOnDarkMuted   = Color(0xFFAAAAAA)
    val TextOnGreen       = Color(0xFFFFFFFF)

    // ── Status (Monochrome) ──────────────────────────────────
    val Success           = Color(0xFF000000)
    val Error             = Color(0xFF000000)
    val ErrorLight        = Color(0xFFF5F5F5)
    val Warning           = Color(0xFF555555)
    val WarningLight      = Color(0xFFFAFAFA)
    val Info              = Color(0xFF000000)

    // ── Badges ───────────────────────────────────────────────
    val BadgeGreen        = Color(0xFFFFFFFF)
    val BadgeGreenBg      = Color(0xFF000000)
    val BadgeRed          = Color(0xFF000000)
    val BadgeRedBg        = Color(0xFFEEEEEE)
    val BadgeNew          = Color(0xFF000000)

    // ── Skip Button ──────────────────────────────────────────
    val SkipButtonBg      = Color(0xFFEEEEEE)
    val SkipButtonText    = Color(0xFF000000)

    // ── Bottom Nav ───────────────────────────────────────────
    val NavActive         = Color(0xFF000000)
    val NavInactive       = Color(0xFFAAAAAA)

    // ── Shimmer ──────────────────────────────────────────────
    val ShimmerBase       = Color(0xFFF0F0F0)
    val ShimmerHighlight  = Color(0xFFFFFFFF)
}
