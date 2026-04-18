package com.tranzo.app.ui.auth

import android.app.Activity
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Fingerprint
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.tranzo.app.ui.components.SecurityBadges
import com.tranzo.app.ui.components.TranzoButton
import com.tranzo.app.ui.components.TranzoSecondaryButton
import com.tranzo.app.ui.components.TranzoTextField
import com.tranzo.app.ui.theme.TranzoColors
import com.tranzo.app.util.BiometricHelper
import com.tranzo.app.util.GoogleSignInHelper
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.launch
import javax.inject.Inject

@EntryPoint
@InstallIn(SingletonComponent::class)
interface GoogleSignInEntryPoint {
    fun googleSignInHelper(): GoogleSignInHelper
}

@EntryPoint
@InstallIn(SingletonComponent::class)
interface BiometricEntryPoint {
    fun biometricHelper(): BiometricHelper
}

@Composable
fun WelcomeScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    isReturningUser: Boolean = false,
    userName: String = "",
    userPhone: String = "",
    onNavigateToOtp: (String) -> Unit = {},
    onLoginWithAnotherNumber: () -> Unit = {},
    onCreateWallet: () -> Unit = {},
    onAuthenticationSuccess: (isNewUser: Boolean) -> Unit = {},
    onPasskeyLogin: () -> Unit = {},
    onBiometricLogin: () -> Unit = {},
    onGoogleLogin: () -> Unit = {},
    onImportWallet: () -> Unit = {},
) {
    val state by viewModel.state.collectAsState()
    var email by remember { mutableStateOf("") }
    var acceptedTerms by remember { mutableStateOf(true) }
    var submittedEmail by remember { mutableStateOf("") }
    var showEmailOption by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val googleSignInHelper = remember {
        EntryPointAccessors.fromApplication(
            context,
            GoogleSignInEntryPoint::class.java
        ).googleSignInHelper()
    }
    val biometricHelper = remember {
        EntryPointAccessors.fromApplication(
            context,
            BiometricEntryPoint::class.java
        ).biometricHelper()
    }

    // Get last authenticated email for biometric fallback
    val lastEmail = remember { viewModel.getLastEmail() }

    LaunchedEffect(state.otpSent, submittedEmail) {
        if (state.otpSent && submittedEmail.isNotBlank()) {
            onNavigateToOtp(submittedEmail)
            submittedEmail = ""
        }
    }

    LaunchedEffect(state.isAuthenticated) {
        if (state.isAuthenticated) {
            onAuthenticationSuccess(state.isNewUser)
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
                    .height(180.dp)
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
                        Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Outlined.Lock,
                                contentDescription = null,
                                tint = TranzoColors.White,
                                modifier = Modifier.size(14.dp),
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Non-Custodial Wallet",
                                style = MaterialTheme.typography.labelMedium,
                                color = TranzoColors.White,
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = if (isReturningUser) "Welcome Back" else "Secure Login",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = TranzoColors.White,
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = if (isReturningUser) {
                            "You control your wallet. Choose your login method."
                        } else {
                            "Choose how you want to secure your account."
                        },
                        style = MaterialTheme.typography.bodySmall,
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
                    verticalArrangement = Arrangement.spacedBy(12.dp),
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
                    } else if (!showEmailOption) {
                        // Auth method selection
                        Text(
                            text = "Choose Login Method",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = TranzoColors.TextPrimary,
                        )

                        AuthMethodButtonWithIcon(
                            icon = Icons.Outlined.Fingerprint,
                            title = "Biometric",
                            subtitle = "Face ID / Fingerprint",
                            onClick = {
                                if (lastEmail.isNullOrEmpty()) {
                                    viewModel.clearError()
                                    // No previous login, show error
                                    return@AuthMethodButtonWithIcon
                                }
                                coroutineScope.launch {
                                    biometricHelper.showPrompt(
                                        activity = context as? Activity ?: return@launch,
                                        title = "Biometric Authentication",
                                        subtitle = "Use your biometric to log in",
                                        onSuccess = {
                                            // Biometric succeeded, attempt login
                                            viewModel.biometricLogin(lastEmail!!)
                                        },
                                        onError = { errorMsg ->
                                            viewModel.clearError()
                                        },
                                    )
                                }
                            },
                        )

                        AuthMethodButtonWithIcon(
                            icon = Icons.Outlined.Lock,
                            title = "Passkey",
                            subtitle = "WebAuthn / FIDO2",
                            onClick = onPasskeyLogin,
                        )

                        AuthMethodButtonWithIcon(
                            icon = Icons.Outlined.Email,
                            title = "Google",
                            subtitle = "Sign in with Google",
                            onClick = {
                                coroutineScope.launch {
                                    val idToken = googleSignInHelper.signIn(
                                        context as? Activity ?: return@launch
                                    )
                                    if (idToken != null) {
                                        viewModel.loginWithGoogle(idToken)
                                    }
                                }
                            },
                        )

                        HorizontalDivider(color = TranzoColors.DividerGray, modifier = Modifier.padding(vertical = 8.dp))

                        AuthMethodButtonWithIcon(
                            icon = Icons.Outlined.Email,
                            title = "Email OTP",
                            subtitle = "One-time passcode",
                            onClick = { showEmailOption = true },
                        )
                    } else {
                        // Email OTP option
                        TranzoSecondaryButton(
                            text = "Back to Methods",
                            onClick = { showEmailOption = false },
                        )

                        Spacer(modifier = Modifier.height(8.dp))

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
                                text = "I authorize Tranzo to create a smart wallet on my behalf.",
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
                            text = "Send OTP",
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
private fun AuthMethodButtonWithIcon(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = TranzoColors.LightGray),
        onClick = onClick,
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = TranzoColors.TextSecondary,
                    modifier = Modifier.size(24.dp),
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = TranzoColors.TextPrimary,
                    )
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.labelSmall,
                        color = TranzoColors.TextSecondary,
                    )
                }
            }
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.ArrowForward,
                contentDescription = null,
                tint = TranzoColors.TextTertiary,
                modifier = Modifier.size(20.dp),
            )
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
