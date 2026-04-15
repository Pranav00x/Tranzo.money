package com.tranzo.app.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tranzo.app.ui.components.SecurityBadges
import com.tranzo.app.ui.components.TranzoButton
import com.tranzo.app.ui.components.TranzoSecondaryButton
import com.tranzo.app.ui.components.TranzoTextField
import com.tranzo.app.ui.theme.TranzoColors

@Composable
fun WelcomeScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    isReturningUser: Boolean = false,
    userName: String = "",
    userPhone: String = "",
    onNavigateToOtp: (String) -> Unit = {},
    onLoginWithAnotherNumber: () -> Unit = {},
    onCreateWallet: () -> Unit = {},
    onPasskeyLogin: () -> Unit = {},
    onImportWallet: () -> Unit = {},
) {
    val state by viewModel.state.collectAsState()
    var email by remember { mutableStateOf("") }
    var acceptedTerms by remember { mutableStateOf(true) }
    var submittedEmail by remember { mutableStateOf("") }

    LaunchedEffect(state.otpSent, submittedEmail) {
        if (state.otpSent && submittedEmail.isNotBlank()) {
            onNavigateToOtp(submittedEmail)
            submittedEmail = ""
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(TranzoColors.White)
            .systemBarsPadding()
            .navigationBarsPadding(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(210.dp)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFF050505), Color(0xFF202020), Color(0xFF050505))
                        ),
                        shape = RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp),
                    )
                    .padding(horizontal = 22.dp, vertical = 20.dp),
            ) {
                Column {
                    Box(
                        modifier = Modifier
                            .background(Color(0x33FFFFFF), CircleShape)
                            .padding(horizontal = 10.dp, vertical = 4.dp),
                    ) {
                        Text(
                            text = "Tranzo Secure Login",
                            style = MaterialTheme.typography.labelMedium,
                            color = TranzoColors.White,
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = if (isReturningUser) "Welcome Back" else "Email Verification",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = TranzoColors.White,
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = if (isReturningUser) {
                            "Continue with your account details."
                        } else {
                            "Enter your email and we will send a one-time passcode."
                        },
                        style = MaterialTheme.typography.bodyMedium,
                        color = TranzoColors.White.copy(alpha = 0.86f),
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = TranzoColors.CardSurface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                ) {
                    if (isReturningUser) {
                        ReadOnlyField(label = "Name", value = userName.ifBlank { "-" })
                        ReadOnlyField(label = "Phone", value = userPhone.ifBlank { "-" })

                        TranzoButton(
                            text = "Continue",
                            onClick = onCreateWallet,
                        )

                        TranzoSecondaryButton(
                            text = "Use Another Number",
                            onClick = onLoginWithAnotherNumber,
                        )
                    } else {
                        TranzoTextField(
                            value = email,
                            onValueChange = { email = it.trim() },
                            label = "Email Address",
                            placeholder = "you@example.com",
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        )

                        Row(verticalAlignment = Alignment.Top) {
                            Checkbox(
                                checked = acceptedTerms,
                                onCheckedChange = { acceptedTerms = it },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = TranzoColors.PrimaryBlack,
                                    checkmarkColor = TranzoColors.White,
                                ),
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "I authorize Tranzo to create a smart wallet account on my behalf.",
                                style = MaterialTheme.typography.bodySmall,
                                color = TranzoColors.TextSecondary,
                                modifier = Modifier.padding(top = 11.dp),
                            )
                        }

                        HorizontalDivider(color = TranzoColors.DividerGray)

                        Text(
                            text = "By continuing you agree to Terms and Privacy Policy.",
                            style = MaterialTheme.typography.bodySmall,
                            color = TranzoColors.TextTertiary,
                        )

                        state.error?.let {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.bodySmall,
                                color = TranzoColors.Error,
                            )
                        }

                        TranzoButton(
                            text = "Get OTP",
                            onClick = {
                                val cleanEmail = email.trim()
                                submittedEmail = cleanEmail
                                viewModel.sendOtp(cleanEmail)
                            },
                            enabled = email.contains("@") && acceptedTerms,
                            isLoading = state.isLoading,
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))
            SecurityBadges()
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun ReadOnlyField(label: String, value: String) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = TranzoColors.TextSecondary,
        )
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            color = TranzoColors.LightGray,
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                color = TranzoColors.TextPrimary,
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 13.dp),
            )
        }
    }
}
