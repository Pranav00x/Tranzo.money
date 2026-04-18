package com.tranzo.app.ui.navigation

/**
 * All navigation routes in the app.
 */
sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object Onboarding : Screen("onboarding")
    data object Welcome : Screen("welcome")
    data object Otp : Screen("otp/{email}") {
        fun createRoute(email: String) = "otp/$email"
    }
    data object ProfileSetup : Screen("profile_setup/{email}") {
        fun createRoute(email: String) = "profile_setup/$email"
    }
    data object BiometricSetup : Screen("biometric_setup")
    data object WalletCreation : Screen("wallet_creation")
    data object Home : Screen("home")
    data object Send : Screen("send")
    data object Receive : Screen("receive")
    data object Swap : Screen("swap")
    data object DripperDashboard : Screen("dripper")
    data object StreamDetail : Screen("stream/{streamId}") {
        fun createRoute(streamId: String) = "stream/$streamId"
    }
    data object CreateStream : Screen("create_stream")
    data object Profile : Screen("profile")
    data object Settings : Screen("settings")
    data object Security : Screen("security")
    data object TransactionHistory : Screen("transaction_history")
    data object Card : Screen("card")
    data object CardDetails : Screen("card_details/{cardId}") {
        fun createRoute(cardId: String) = "card_details/$cardId"
    }
    data object OrderCard : Screen("order_card")
    
    // Security
    data object PinSetup : Screen("pin_setup")
    data object PinEnter : Screen("pin_enter")
}
