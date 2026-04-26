package com.tranzo.app.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tranzo.app.util.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class SplashNavigation {
    ONBOARDING,
    HOME,
}

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val sessionManager: SessionManager,
) : ViewModel() {

    private val _navigation = MutableStateFlow<SplashNavigation?>(null)
    val navigation = _navigation.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        checkUserSession()
    }

    private fun checkUserSession() {
        viewModelScope.launch {
            // Minimum splash screen delay for branding (2 seconds)
            delay(2000)

            // Check if user has valid session
            val isLoggedIn = sessionManager.isLoggedIn()

            _navigation.value = if (isLoggedIn) {
                SplashNavigation.HOME
            } else {
                SplashNavigation.ONBOARDING
            }

            _isLoading.value = false
        }
    }
}
