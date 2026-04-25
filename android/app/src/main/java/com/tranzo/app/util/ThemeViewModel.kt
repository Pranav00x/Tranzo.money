package com.tranzo.app.util

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val themeManager: ThemeManager
) : ViewModel() {
    val currentThemeId: StateFlow<String> = themeManager.currentThemeId
    
    fun setTheme(themeId: String) = themeManager.setTheme(themeId)
    
    fun getAvailableThemes() = themeManager.getAvailableThemes()
}
