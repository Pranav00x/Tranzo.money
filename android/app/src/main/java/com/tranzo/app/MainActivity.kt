package com.tranzo.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tranzo.app.ui.card.CardScreenProClay
import com.tranzo.app.ui.card.OrderCardScreen
import com.tranzo.app.ui.auth.OtpScreenPro
import com.tranzo.app.ui.auth.ProfileSetupScreenPro
import com.tranzo.app.ui.auth.WalletCreationScreenPro
import com.tranzo.app.ui.auth.WelcomeScreenPro
import com.tranzo.app.ui.dripper.CreateStreamScreen
import com.tranzo.app.ui.dripper.DripperDashboardScreen
import com.tranzo.app.ui.dripper.StreamDetailScreen
import com.tranzo.app.ui.history.TransactionHistoryScreenClay
import com.tranzo.app.ui.home.HomeScreenProClay
import com.tranzo.app.ui.security.BiometricSetupScreen
import com.tranzo.app.ui.security.PinMode
import com.tranzo.app.ui.security.PinScreen
import com.tranzo.app.ui.navigation.Screen
import com.tranzo.app.ui.navigation.TranzoBottomBar
import com.tranzo.app.ui.onboarding.OnboardingScreen
import com.tranzo.app.ui.profile.ProfileScreen
import com.tranzo.app.ui.receive.ReceiveScreen
import com.tranzo.app.ui.send.SendConfirmationScreen
import com.tranzo.app.ui.send.SendScreenProClay
import com.tranzo.app.ui.settings.SettingsScreenProClay
import com.tranzo.app.ui.settings.ThemeSelectorScreen
import com.tranzo.app.ui.splash.SplashScreen
import com.tranzo.app.ui.swap.SwapScreenProClay
import com.tranzo.app.ui.theme.TranzoTheme
import com.tranzo.app.ui.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.hilt.navigation.compose.hiltViewModel

import androidx.fragment.app.FragmentActivity
import com.tranzo.app.util.BiometricHelper
import com.tranzo.app.util.ThemeManager
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    @Inject lateinit var biometricHelper: BiometricHelper
    @Inject lateinit var themeManager: ThemeManager
    @Inject lateinit var sessionManager: com.tranzo.app.util.SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val themeId by themeManager.currentThemeId.collectAsState()

            TranzoTheme(themeId = themeId) {
                val navController = rememberNavController()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = { TranzoBottomBar(navController) },
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Splash.route,
                    ) {
                        // ── Auth Flow (Non-Custodial) ───────────────
                        composable(Screen.Splash.route) {
                            SplashScreen(
                                isLoggedIn = sessionManager.isLoggedIn(),
                                onNavigateToOnboarding = {
                                    navController.navigate(Screen.Onboarding.route) {
                                        popUpTo(Screen.Splash.route) { inclusive = true }
                                    }
                                },
                                onNavigateToHome = {
                                    navController.navigate(Screen.Home.route) {
                                        popUpTo(Screen.Splash.route) { inclusive = true }
                                    }
                                },
                            )
                        }

                        composable(Screen.Onboarding.route) {
                            OnboardingScreen(
                                onGetStarted = {
                                    navController.navigate(Screen.Welcome.route) {
                                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                                    }
                                },
                            )
                        }

                        composable(Screen.Welcome.route) {
                            WelcomeScreenPro(
                                onNavigateToOtp = { email ->
                                    navController.navigate(Screen.Otp.createRoute(email))
                                },
                                onAuthenticationSuccess = { isNewUser ->
                                    if (isNewUser) {
                                        navController.navigate(Screen.ProfileSetup.createRoute("")) {
                                            popUpTo(0) { inclusive = true }
                                        }
                                    } else {
                                        navController.navigate(Screen.WalletCreation.route) {
                                            popUpTo(0) { inclusive = true }
                                        }
                                    }
                                },
                            )
                        }

                        composable(Screen.Otp.route) { backStackEntry ->
                            val email = backStackEntry.arguments?.getString("email") ?: ""
                            OtpScreenPro(
                                email = email,
                                onNavigateToHome = { isNewUser ->
                                    if (isNewUser) {
                                        // New user: route to profile setup
                                        navController.navigate(Screen.ProfileSetup.createRoute(email)) {
                                            popUpTo(0) { inclusive = true }
                                        }
                                    } else {
                                        // Returning user: skip to wallet creation
                                        navController.navigate(Screen.WalletCreation.route) {
                                            popUpTo(0) { inclusive = true }
                                        }
                                    }
                                },
                                onResend = { /* Handled in VM */ },
                                onSkip = {
                                    navController.navigate(Screen.Home.route) {
                                        popUpTo(0) { inclusive = true }
                                    }
                                },
                            )
                        }

                        composable(Screen.ProfileSetup.route) { backStackEntry ->
                            val email = backStackEntry.arguments?.getString("email") ?: ""
                            val authViewModel = hiltViewModel<AuthViewModel>()
                            val authState by authViewModel.state.collectAsState()

                            // Navigate when profile save completes
                            LaunchedEffect(authState.isProfileSaved) {
                                if (authState.isProfileSaved) {
                                    navController.navigate(Screen.WalletCreation.route) {
                                        popUpTo(Screen.ProfileSetup.route) { inclusive = true }
                                    }
                                }
                            }

                            ProfileSetupScreenPro(
                                prefilledEmail = email,
                                viewModel = authViewModel,
                                onContinue = { firstName, lastName, emailAddr, phone, language ->
                                    // Save profile to backend
                                    authViewModel.saveProfile(
                                        firstName = firstName,
                                        lastName = lastName,
                                        email = emailAddr,
                                        phone = phone,
                                        language = language,
                                    )
                                },
                                onSkip = {
                                    // Skip profile setup, go straight to wallet
                                    navController.navigate(Screen.WalletCreation.route) {
                                        popUpTo(Screen.ProfileSetup.route) { inclusive = true }
                                    }
                                },
                                isLoading = authState.isLoading,
                            )
                        }

                        composable(Screen.WalletCreation.route) {
                            WalletCreationScreenPro(
                                onComplete = {
                                    navController.navigate(Screen.PinSetup.route) {
                                        popUpTo(0) { inclusive = true }
                                    }
                                },
                                onSkip = {
                                    navController.navigate(Screen.Home.route) {
                                        popUpTo(0) { inclusive = true }
                                    }
                                },
                            )
                        }

                        // ── Security Flow ────────────────────────────
                        composable(Screen.PinSetup.route) {
                            PinScreen(
                                mode = PinMode.SETUP,
                                onSuccess = { _ ->
                                    navController.navigate(Screen.BiometricSetup.route) {
                                        popUpTo(Screen.PinSetup.route) { inclusive = true }
                                    }
                                },
                                onBack = { navController.popBackStack() }
                            )
                        }

                        composable(Screen.BiometricSetup.route) {
                            BiometricSetupScreen(
                                onEnable = {
                                    biometricHelper.showPrompt(
                                        activity = this@MainActivity,
                                        onSuccess = {
                                            navController.navigate(Screen.Home.route) {
                                                popUpTo(0) { inclusive = true }
                                            }
                                        },
                                        onError = {
                                            navController.navigate(Screen.Home.route) {
                                                popUpTo(0) { inclusive = true }
                                            }
                                        }
                                    )
                                },
                                onSkip = {
                                    navController.navigate(Screen.Home.route) {
                                        popUpTo(0) { inclusive = true }
                                    }
                                }
                            )
                        }

                        composable(Screen.PinEnter.route) {
                            PinScreen(
                                mode = PinMode.ENTER,
                                onSuccess = { _ ->
                                    navController.popBackStack()
                                },
                                onUseBiometric = {
                                    biometricHelper.showPrompt(
                                        activity = this@MainActivity,
                                        onSuccess = {
                                            navController.popBackStack()
                                        },
                                        onError = { /* Handle error */ }
                                    )
                                },
                                onBack = { navController.popBackStack() }
                            )
                        }

                        // ── Main Screens ─────────────────────────────
                        composable(Screen.Home.route) {
                            HomeScreenProClay(
                                onNavigateToTransfer = { navController.navigate(Screen.Send.route) },
                                onNavigateToSwap = { navController.navigate(Screen.Swap.route) },
                                onNavigateToCard = { navController.navigate(Screen.Card.route) },
                                onNavigateToSettings = { navController.navigate(Screen.Settings.route) },
                            )
                        }

                        // ── Send Flow ────────────────────────────────
                        composable(Screen.Send.route) {
                            SendScreenProClay(
                                onConfirm = { navController.popBackStack() },
                            )
                        }

                        composable("send_confirm/{to}/{token}/{amount}") { backStackEntry ->
                            val to = backStackEntry.arguments?.getString("to") ?: ""
                            val token = backStackEntry.arguments?.getString("token") ?: ""
                            val amount = backStackEntry.arguments?.getString("amount") ?: ""
                            SendConfirmationScreen(
                                recipientAddress = to,
                                tokenSymbol = token,
                                amount = amount,
                                onBack = {
                                    navController.navigate(Screen.Home.route) {
                                        popUpTo(Screen.Home.route) { inclusive = false }
                                    }
                                },
                            )
                        }

                        // ── Receive ──────────────────────────────────
                        composable(Screen.Receive.route) {
                            ReceiveScreen(
                                onBack = { navController.popBackStack() },
                            )
                        }

                        // ── Swap ─────────────────────────────────────
                        composable(Screen.Swap.route) {
                            SwapScreenProClay(
                                onSwapInitiated = { navController.popBackStack() },
                            )
                        }

                        // ── Dripper ──────────────────────────────────
                        composable(Screen.DripperDashboard.route) {
                            DripperDashboardScreen(
                                onCreateStream = {
                                    navController.navigate(Screen.CreateStream.route)
                                },
                                onStreamClick = { streamId ->
                                    navController.navigate(Screen.StreamDetail.createRoute(streamId))
                                },
                            )
                        }

                        composable(Screen.StreamDetail.route) { backStackEntry ->
                            val streamId = backStackEntry.arguments?.getString("streamId") ?: ""
                            StreamDetailScreen(
                                streamId = streamId,
                                onBack = { navController.popBackStack() },
                            )
                        }

                        composable(Screen.CreateStream.route) {
                            CreateStreamScreen(
                                onBack = { navController.popBackStack() },
                                onCreateSuccess = {
                                    navController.popBackStack()
                                },
                            )
                        }

                        // ── Transaction History ──────────────────────
                        composable(Screen.TransactionHistory.route) {
                            TransactionHistoryScreenClay()
                        }

                        // ── Profile ──────────────────────────────────
                        composable(Screen.Profile.route) {
                            ProfileScreen(
                                onBack = { navController.popBackStack() },
                                onEdit = { /* Edit profile feature */ },
                            )
                        }

                        // ── Settings ─────────────────────────────────
                        composable(Screen.Settings.route) {
                            SettingsScreenProClay(
                                onLogout = {
                                    navController.navigate(Screen.Welcome.route) {
                                        popUpTo(0) { inclusive = true }
                                    }
                                },
                                onSecurity = {
                                    navController.navigate(Screen.Security.route)
                                },
                                onTheme = {
                                    navController.navigate(Screen.ThemeSelector.route)
                                },
                            )
                        }

                        // ── Theme Selector ──────────────────────────────
                        composable(Screen.ThemeSelector.route) {
                            ThemeSelectorScreen(
                                themeManager = themeManager,
                                onBack = { navController.popBackStack() },
                            )
                        }

                        // ── Security ─────────────────────────────────
                        composable(Screen.Security.route) {
                            com.tranzo.app.ui.security.SecurityScreen(
                                onBack = { navController.popBackStack() },
                            )
                        }

                        // ── Card Flow ────────────────────────────────
                        composable(Screen.Card.route) {
                            CardScreenProClay(
                                onOrderCard = { navController.navigate(Screen.OrderCard.route) },
                            )
                        }

                        composable(Screen.OrderCard.route) {
                            OrderCardScreen(
                                onBack = { navController.popBackStack() },
                                onOrder = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}
