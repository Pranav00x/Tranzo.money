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
import com.tranzo.app.ui.auth.OtpScreen
import com.tranzo.app.ui.auth.WalletCreationScreen
import com.tranzo.app.ui.auth.WelcomeScreen
import com.tranzo.app.ui.home.HomeScreen
import com.tranzo.app.ui.navigation.Screen
import com.tranzo.app.ui.navigation.TranzoBottomBar
import com.tranzo.app.ui.onboarding.OnboardingScreen
import com.tranzo.app.ui.settings.SettingsScreen
import com.tranzo.app.ui.splash.SplashScreen
import com.tranzo.app.ui.theme.TranzoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            TranzoTheme {
                val navController = rememberNavController()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = { TranzoBottomBar(navController) },
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Splash.route,
                    ) {
                        // Splash
                        composable(Screen.Splash.route) {
                            SplashScreen(
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

                        // Onboarding
                        composable(Screen.Onboarding.route) {
                            OnboardingScreen(
                                onGetStarted = {
                                    navController.navigate(Screen.Welcome.route) {
                                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                                    }
                                },
                            )
                        }

                        // Welcome (email input)
                        composable(Screen.Welcome.route) {
                            WelcomeScreen(
                                onContinue = { email ->
                                    navController.navigate(Screen.Otp.createRoute(email))
                                },
                            )
                        }

                        // OTP Verification
                        composable(Screen.Otp.route) { backStackEntry ->
                            val email = backStackEntry.arguments?.getString("email") ?: ""
                            OtpScreen(
                                email = email,
                                onVerify = { _ ->
                                    navController.navigate(Screen.WalletCreation.route) {
                                        popUpTo(Screen.Welcome.route) { inclusive = true }
                                    }
                                },
                                onResend = { /* re-call sendOTP */ },
                            )
                        }

                        // Wallet Creation (loading)
                        composable(Screen.WalletCreation.route) {
                            WalletCreationScreen(
                                onComplete = {
                                    navController.navigate(Screen.Home.route) {
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

                        // Home
                        composable(Screen.Home.route) {
                            HomeScreen(
                                onSend = { navController.navigate(Screen.Send.route) },
                                onReceive = { navController.navigate(Screen.Receive.route) },
                                onSwap = { navController.navigate(Screen.Swap.route) },
                                onDripper = { navController.navigate(Screen.DripperDashboard.route) },
                            )
                        }

                        // Settings
                        composable(Screen.Settings.route) {
                            SettingsScreen(
                                onLogout = {
                                    navController.navigate(Screen.Welcome.route) {
                                        popUpTo(0) { inclusive = true }
                                    }
                                },
                            )
                        }

                        // Placeholder screens for remaining tabs
                        composable(Screen.Send.route) {
                            // TODO: SendScreen
                            HomeScreen()
                        }
                        composable(Screen.Receive.route) {
                            // TODO: ReceiveScreen
                            HomeScreen()
                        }
                        composable(Screen.Swap.route) {
                            // TODO: SwapScreen
                            HomeScreen()
                        }
                        composable(Screen.DripperDashboard.route) {
                            // TODO: DripperDashboardScreen
                            HomeScreen()
                        }
                        composable(Screen.TransactionHistory.route) {
                            // TODO: TransactionHistoryScreen
                            HomeScreen()
                        }
                    }
                }
            }
        }
    }
}
