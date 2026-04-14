package com.tranzo.app.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.tranzo.app.ui.components.SecurityBadges
import com.tranzo.app.ui.components.TranzoButton
import com.tranzo.app.ui.components.TranzoTextField
import com.tranzo.app.ui.theme.TranzoColors

/**
 * Welcome / Getting Started screen.
 * Matches CheQ's "Getting Started" → email input → "Get OTP" pattern.
 */
@Composable
fun WelcomeScreen(
    onContinue: (String) -> Unit,
) {
    var email by remember { mutableStateOf("") }
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

        // Title
        Text(
            text = "Getting Started",
            style = MaterialTheme.typography.headlineLarge,
            color = TranzoColors.TextPrimary,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Enter your email address",
            style = MaterialTheme.typography.bodyLarge,
            color = TranzoColors.TextSecondary,
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Email input — CheQ-style outlined with floating label
        TranzoTextField(
            value = email,
            onValueChange = { email = it },
            label = "Email Address",
            placeholder = "you@example.com",
        )

        Spacer(modifier = Modifier.weight(1f))

        // Terms checkbox
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
                text = "I authorize Tranzo to create a self-custody smart account on my behalf",
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

        Spacer(modifier = Modifier.height(16.dp))

        SecurityBadges()

        Spacer(modifier = Modifier.height(24.dp))
    }
}
