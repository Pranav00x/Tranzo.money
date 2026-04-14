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
import com.tranzo.app.ui.card.CardScreen
import com.tranzo.app.ui.card.OrderCardScreen
import com.tranzo.app.ui.auth.OtpScreen
import com.tranzo.app.ui.auth.WalletCreationScreen
import com.tranzo.app.ui.auth.WelcomeScreen
import com.tranzo.app.ui.dripper.CreateStreamScreen
import com.tranzo.app.ui.dripper.DripperDashboardScreen
import com.tranzo.app.ui.dripper.StreamDetailScreen
import com.tranzo.app.ui.history.TransactionHistoryScreen
import com.tranzo.app.ui.home.HomeScreen
import com.tranzo.app.ui.security.PinMode
import com.tranzo.app.ui.security.PinScreen
import com.tranzo.app.ui.navigation.Screen
import com.tranzo.app.ui.navigation.TranzoBottomBar
import com.tranzo.app.ui.onboarding.OnboardingScreen
import com.tranzo.app.ui.receive.ReceiveScreen
import com.tranzo.app.ui.send.SendConfirmationScreen
import com.tranzo.app.ui.send.SendScreen
import com.tranzo.app.ui.settings.SettingsScreen
import com.tranzo.app.ui.splash.SplashScreen
import com.tranzo.app.ui.swap.SwapScreen
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
                        // ── Auth Flow (Non-Custodial) ───────────────
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
                            WelcomeScreen(
                                onContinue = { _ ->
                                    // Non-custodial: go straight to wallet creation
                                    // (passkey registration + local key generation)
                                    navController.navigate(Screen.WalletCreation.route) {
                                        popUpTo(Screen.Welcome.route) { inclusive = true }
                                    }
                                },
                                onCreateWallet = {
                                    navController.navigate(Screen.WalletCreation.route) {
                                        popUpTo(Screen.Welcome.route) { inclusive = true }
                                    }
                                },
                                onPasskeyLogin = {
                                    // Returning user: authenticate with passkey → home
                                    navController.navigate(Screen.Home.route) {
                                        popUpTo(0) { inclusive = true }
                                    }
                                },
                                onImportWallet = {
                                    // Import existing wallet flow
                                    // TODO: Add import wallet screen
                                    navController.navigate(Screen.WalletCreation.route)
                                },
                            )
                        }

                        // OTP screen — optional email linking (NOT primary auth)
                        composable(Screen.Otp.route) { backStackEntry ->
                            val email = backStackEntry.arguments?.getString("email") ?: ""
                            OtpScreen(
                                email = email,
                                onVerify = { _ ->
                                    navController.navigate(Screen.Home.route) {
                                        popUpTo(0) { inclusive = true }
                                    }
                                },
                                onResend = { /* re-call sendOTP */ },
                                onSkip = {
                                    navController.navigate(Screen.Home.route) {
                                        popUpTo(0) { inclusive = true }
                                    }
                                },
                            )
                        }

                        composable(Screen.WalletCreation.route) {
                            WalletCreationScreen(
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
                                    navController.navigate(Screen.Home.route) {
                                        popUpTo(0) { inclusive = true }
                                    }
                                },
                                onBack = { navController.popBackStack() }
                            )
                        }

                        composable(Screen.PinEnter.route) {
                            PinScreen(
                                mode = PinMode.ENTER,
                                onSuccess = { _ ->
                                    navController.popBackStack()
                                },
                                onUseBiometric = {
                                    navController.popBackStack()
                                },
                                onBack = { navController.popBackStack() }
                            )
                        }

                        // ── Main Screens ─────────────────────────────
                        composable(Screen.Home.route) {
                            HomeScreen(
                                onSend = { navController.navigate(Screen.Send.route) },
                                onReceive = { navController.navigate(Screen.Receive.route) },
                                onSwap = { navController.navigate(Screen.Swap.route) },
                                onDripper = { navController.navigate(Screen.DripperDashboard.route) },
                                onCard = { navController.navigate(Screen.Card.route) },
                                onOrderCard = { navController.navigate(Screen.OrderCard.route) },
                            )
                        }

                        // ── Send Flow ────────────────────────────────
                        composable(Screen.Send.route) {
                            SendScreen(
                                onBack = { navController.popBackStack() },
                                onReview = { to, token, amount ->
                                    navController.navigate("send_confirm/$to/$token/$amount")
                                },
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
                                onConfirm = { /* call API */ },
                                onCancel = {
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
                            SwapScreen(
                                onBack = { navController.popBackStack() },
                                onSwap = { navController.popBackStack() },
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
                                onWithdraw = { /* call API */ },
                                onCancel = { navController.popBackStack() },
                            )
                        }

                        composable(Screen.CreateStream.route) {
                            CreateStreamScreen(
                                onBack = { navController.popBackStack() },
                                onCreate = {
                                    navController.popBackStack()
                                },
                            )
                        }

                        // ── Transaction History ──────────────────────
                        composable(Screen.TransactionHistory.route) {
                            TransactionHistoryScreen(
                                onBack = { navController.popBackStack() },
                            )
                        }

                        // ── Settings ─────────────────────────────────
                        composable(Screen.Settings.route) {
                            SettingsScreen(
                                onLogout = {
                                    navController.navigate(Screen.Welcome.route) {
                                        popUpTo(0) { inclusive = true }
                                    }
                                },
                                onTransactionHistory = {
                                    navController.navigate(Screen.TransactionHistory.route)
                                },
                                onSecurity = {
                                    navController.navigate(Screen.Security.route)
                                },
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
                            CardScreen(
                                onBack = { navController.popBackStack() },
                                onOrderCard = { navController.navigate(Screen.OrderCard.route) },
                                onCardDetails = { cardId ->
                                    navController.navigate(Screen.CardDetails.createRoute(cardId))
                                },
                                onManageLimits = { /* Future */ }
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
