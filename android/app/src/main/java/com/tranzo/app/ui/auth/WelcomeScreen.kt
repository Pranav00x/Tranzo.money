package com.tranzo.app.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.tranzo.app.ui.components.SecurityBadges
import com.tranzo.app.ui.components.TranzoButton
import com.tranzo.app.ui.components.TranzoSecondaryButton
import com.tranzo.app.ui.components.TranzoTextField
import com.tranzo.app.ui.theme.TranzoColors

/**
 * Welcome / Getting Started screen.
 *
 * Auth flow like Avi/Ready — simple email signup.
 * Under the hood: Openfort creates an embedded smart account.
 * User never sees keys, mnemonics, or passkeys.
 *
 * Modes:
 * - New user: "Getting Started" → email → OTP → wallet creation
 * - Returning user: "Welcome Back!" → pre-filled name + phone → Login
 */
@Composable
fun WelcomeScreen(
    isReturningUser: Boolean = false,
    userName: String = "",
    userPhone: String = "",
    onContinue: (String) -> Unit = {},
    onLoginWithAnotherNumber: () -> Unit = {},
    // Unused — kept for nav compat
    onCreateWallet: () -> Unit = {},
    onPasskeyLogin: () -> Unit = {},
    onImportWallet: () -> Unit = {},
) {
    var email by remember { mutableStateOf("") }
    var name by remember { mutableStateOf(userName) }
    var phone by remember { mutableStateOf(userPhone) }
    var isLoading by remember { mutableStateOf(false) }
    var acceptedTerms by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(TranzoColors.White)
            .systemBarsPadding()
            .padding(horizontal = 24.dp),
    ) {
        Spacer(modifier = Modifier.height(48.dp))

        if (isReturningUser) {
            // ── Returning User (CheQ "Welcome Back!" style) ─────
            Text(
                text = "Welcome Back!",
                style = MaterialTheme.typography.headlineLarge,
                color = TranzoColors.TextPrimary,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Confirm your details before jumping back in",
                style = MaterialTheme.typography.bodyLarge,
                color = TranzoColors.TextSecondary,
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Name (pre-filled, read-only style)
            Text(
                text = "Name",
                style = MaterialTheme.typography.labelMedium,
                color = TranzoColors.TextSecondary,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = TranzoColors.LightGray,
            ) {
                Text(
                    text = name.ifEmpty { "Pranav Jha" },
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Phone (pre-filled, read-only style)
            Text(
                text = "Phone Number",
                style = MaterialTheme.typography.labelMedium,
                color = TranzoColors.TextSecondary,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = TranzoColors.LightGray,
            ) {
                Text(
                    text = phone.ifEmpty { "+91 7377286823" },
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Login button
            TranzoButton(
                text = "Login",
                onClick = {
                    isLoading = true
                    onContinue(phone.ifEmpty { userPhone })
                },
                isLoading = isLoading,
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Login with another number
            TranzoSecondaryButton(
                text = "Login with another Number",
                onClick = onLoginWithAnotherNumber,
            )
        } else {
            // ── New User ────────────────────────────────────────
            Text(
                text = "Getting Started",
                style = MaterialTheme.typography.headlineLarge,
                color = TranzoColors.TextPrimary,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Enter your email to create your wallet",
                style = MaterialTheme.typography.bodyLarge,
                color = TranzoColors.TextSecondary,
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Email input — CheQ-style
            TranzoTextField(
                value = email,
                onValueChange = { email = it },
                label = "Email Address",
                placeholder = "you@example.com",
            )

            Spacer(modifier = Modifier.weight(1f))

            // Terms
            Row(
                verticalAlignment = Alignment.Top,
                modifier = Modifier.padding(bottom = 12.dp),
            ) {
                Checkbox(
                    checked = acceptedTerms,
                    onCheckedChange = { acceptedTerms = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = TranzoColors.PrimaryGreen,
                        checkmarkColor = TranzoColors.TextOnGreen,
                    ),
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "I authorize Tranzo to create a smart wallet account on my behalf",
                    style = MaterialTheme.typography.bodySmall,
                    color = TranzoColors.TextSecondary,
                    modifier = Modifier.padding(top = 12.dp),
                )
            }

            // Terms links
            Row(modifier = Modifier.padding(bottom = 16.dp)) {
                Text(
                    text = "By proceeding, you agree to Tranzo's ",
                    style = MaterialTheme.typography.bodySmall,
                    color = TranzoColors.TextTertiary,
                )
                Text(
                    text = "terms-of-service",
                    style = MaterialTheme.typography.bodySmall,
                    color = TranzoColors.PrimaryGreen,
                )
                Text(
                    text = " and ",
                    style = MaterialTheme.typography.bodySmall,
                    color = TranzoColors.TextTertiary,
                )
                Text(
                    text = "privacy policy",
                    style = MaterialTheme.typography.bodySmall,
                    color = TranzoColors.PrimaryGreen,
                )
            }

            // Get OTP button
            TranzoButton(
                text = "Get OTP",
                onClick = {
                    isLoading = true
                    onContinue(email)
                },
                enabled = email.contains("@") && acceptedTerms,
                isLoading = isLoading,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        SecurityBadges()

        Spacer(modifier = Modifier.height(24.dp))
    }
}
