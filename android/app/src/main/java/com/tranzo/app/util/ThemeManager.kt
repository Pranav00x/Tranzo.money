package com.tranzo.app.util

import android.content.Context
import androidx.annotation.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * ThemeManager - Handles user theme preference
 * Persists theme selection and provides observable theme state
 */
@Singleton
class ThemeManager @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    private val prefs = context.getSharedPreferences("tranzo_theme", Context.MODE_PRIVATE)

    private val _currentThemeId = MutableStateFlow(loadThemeId())
    val currentThemeId: StateFlow<String> = _currentThemeId.asStateFlow()

    // ─────────────────────────────────────────────────────────
    // Theme Persistence
    // ─────────────────────────────────────────────────────────

    fun setTheme(themeId: String) {
        prefs.edit()
            .putString("theme_id", themeId)
            .apply()
        _currentThemeId.value = themeId
    }

    fun getThemeId(): String = _currentThemeId.value

    private fun loadThemeId(): String {
        return prefs.getString("theme_id", "default_dark") ?: "default_dark"
    }

    fun resetToDefaultTheme() {
        setTheme("default_dark")
    }

    // ─────────────────────────────────────────────────────────
    // Available Themes
    // ─────────────────────────────────────────────────────────

    fun getAvailableThemes() = listOf(
        ThemeOption("default_dark", "Dark (Default)"),
        ThemeOption("purple_night", "Purple Night"),
        ThemeOption("ocean", "Ocean Green"),
        ThemeOption("sunset", "Sunset Orange"),
        ThemeOption("mint", "Mint Fresh"),
        ThemeOption("pink", "Pink Neon"),
        ThemeOption("gold", "Gold Luxe"),
        ThemeOption("cyberpunk", "Cyberpunk"),
    )

    data class ThemeOption(
        val id: String,
        val displayName: String,
    )
}
