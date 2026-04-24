package com.tranzo.app.ui.auth

import android.app.Activity
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tranzo.app.ui.components.TranzoButton
import com.tranzo.app.ui.components.TranzoSecondaryButton
import com.tranzo.app.ui.components.TranzoTextField
import com.tranzo.app.ui.theme.TranzoColors
import com.tranzo.app.util.GoogleSignInHelper
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.launch
import javax.inject.Inject

@EntryPoint
@InstallIn(SingletonComponent::class)
interface GoogleSignInEntryPointPro {
    fun googleSignInHelper(): GoogleSignInHelper
}

/**
 * Professional Welcome Screen - Clean, minimal design
 * Supports: Email OTP, Google OAuth, Biometric, Passkey
 */
@Composable
fun WelcomeScreenPro(
    viewModel: AuthViewModel = hiltViewModel(),
    onNavigateToOtp: (String) -> Unit = {},
    onAuthenticationSuccess: (isNewUser: Boolean) -> Unit = {},
) {
    val state by viewModel.state.collectAsState()
    var email by remember { mutableStateOf("") }
    var showEmailInput by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val googleSignInHelper = remember {
        EntryPointAccessors.fromApplication(
            context,
            GoogleSignInEntryPointPro::class.java
        ).googleSignInHelper()
    }

    // Animation state
    var showContent by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        showContent = true
    }

    val contentAlpha by animateFloatAsState(
        targetValue = if (showContent) 1f else 0f,
        animationSpec = tween(800),
        label = "content fade in"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(TranzoColors.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .alpha(contentAlpha)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header space
            Spacer(modifier = Modifier.height(48.dp))

            // Logo & Branding
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(TranzoColors.PrimaryBlue),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "₮",
                        style = MaterialTheme.typography.displayMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    "Tranzo",
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Bold,
                    color = TranzoColors.TextPrimary
                )

                Text(
                    "Crypto card + smart wallet",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TranzoColors.TextSecondary
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            if (!showEmailInput) {
                // Auth method selection
                Text(
                    "How do you want to login?",
                    style = MaterialTheme.typography.headlineSmall,
                    color = TranzoColors.TextPrimary,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Email option
                AuthMethodCard(
                    icon = Icons.Outlined.Email,
                    title = "Email",
                    description = "One-time code",
                    onClick = { showEmailInput = true }
                )

                // Google option
                AuthMethodCard(
                    icon = Icons.Outlined.Lock,
                    title = "Google",
                    description = "Quick sign-in",
                    onClick = {
                        coroutineScope.launch {
                            val idToken = googleSignInHelper.signIn(
                                context as? Activity ?: return@launch
                            )
                            if (idToken != null) {
                                viewModel.loginWithGoogle(idToken)
                            }
                        }
                    }
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Trust indicators
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Lock,
                        contentDescription = null,
                        tint = TranzoColors.TextTertiary,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        "Your funds are always in your control",
                        style = MaterialTheme.typography.labelSmall,
                        color = TranzoColors.TextTertiary
                    )
                }
            } else {
                // Email input screen
                Text(
                    "Enter your email",
                    style = MaterialTheme.typography.headlineSmall,
                    color = TranzoColors.TextPrimary,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(20.dp))

                TranzoTextField(
                    value = email,
                    onValueChange = { email = it.trim() },
                    label = "Email",
                    placeholder = "you@example.com",
                    keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                        keyboardType = KeyboardType.Email
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                // Terms checkbox
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = true,
                        onCheckedChange = { },
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        "I agree to Tranzo's Terms and Privacy Policy",
                        style = MaterialTheme.typography.bodySmall,
                        color = TranzoColors.TextSecondary
                    )
                }

                // Error message
                if (state.error != null) {
                    Text(
                        state.error!!,
                        style = MaterialTheme.typography.bodySmall,
                        color = TranzoColors.Error,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                TranzoButton(
                    text = "Send Code",
                    onClick = {
                        if (email.contains("@")) {
                            viewModel.sendOtp(email.trim())
                            onNavigateToOtp(email.trim())
                        }
                    },
                    enabled = email.contains("@") && !state.isLoading,
                    isLoading = state.isLoading,
                    modifier = Modifier.fillMaxWidth()
                )

                TranzoSecondaryButton(
                    text = "Back",
                    onClick = { showEmailInput = false },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun AuthMethodCard(
    icon: ImageVector,
    title: String,
    description: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = TranzoColors.SurfaceLight,
            contentColor = TranzoColors.TextPrimary
        ),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = TranzoColors.PrimaryBlue,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        description,
                        style = MaterialTheme.typography.labelSmall,
                        color = TranzoColors.TextSecondary
                    )
                }
            }

            // Icon placeholder - chevron right will be added later
            Text("→", style = MaterialTheme.typography.bodyLarge, color = TranzoColors.TextTertiary)
        }
    }
}