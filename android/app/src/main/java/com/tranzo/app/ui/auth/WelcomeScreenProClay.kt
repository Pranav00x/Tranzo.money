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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tranzo.app.ui.components.ClayAuthMethodCard
import com.tranzo.app.ui.components.ClayButton
import com.tranzo.app.ui.components.ClayTextField
import com.tranzo.app.ui.theme.TranzoColors
import com.tranzo.app.util.GoogleSignInHelper
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.launch

@EntryPoint
@InstallIn(SingletonComponent::class)
interface GoogleSignInEntryPointProClay {
    fun googleSignInHelper(): GoogleSignInHelper
}

/**
 * Claymorphism Welcome Screen — Baby blue background, white cards,
 * solid blue CTA. Matches reference image login screen (right panel).
 */
@Composable
fun WelcomeScreenProClay(
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
            GoogleSignInEntryPointProClay::class.java
        ).googleSignInHelper()
    }

    var showContent by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { showContent = true }

    val contentAlpha by animateFloatAsState(
        targetValue = if (showContent) 1f else 0f,
        animationSpec = tween(800),
        label = "content fade in"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(TranzoColors.ClayBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .alpha(contentAlpha)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            // ── Logo ────────────────────────────────────────
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                // Clay-style logo
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .shadow(
                            elevation = 16.dp,
                            shape = RoundedCornerShape(24.dp),
                            ambientColor = TranzoColors.ClayBlue.copy(alpha = 0.3f),
                        )
                        .clip(RoundedCornerShape(24.dp))
                        .background(TranzoColors.ClayBlue),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        "T",
                        style = MaterialTheme.typography.displayMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 36.sp,
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    "Hello, Stranger!",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = TranzoColors.TextPrimary,
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    "Let's sign you in",
                    style = MaterialTheme.typography.bodyLarge,
                    color = TranzoColors.TextSecondary,
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            if (!showEmailInput) {
                // ── Auth Methods ─────────────────────────────
                ClayAuthMethodCard(
                    icon = Icons.Outlined.Email,
                    title = "Email",
                    description = "Sign in with OTP code",
                    onClick = { showEmailInput = true },
                    iconColor = TranzoColors.ClayBlue,
                )

                ClayAuthMethodCard(
                    icon = Icons.Outlined.Lock,
                    title = "Google",
                    description = "Quick sign in",
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
                    iconColor = TranzoColors.PrimaryPurple,
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Trust badge
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White.copy(alpha = 0.7f))
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        Icons.Outlined.Lock,
                        contentDescription = "Secure",
                        tint = TranzoColors.ClayGreen,
                        modifier = Modifier.size(20.dp),
                    )
                    Text(
                        "Protected by account abstraction & Kernel validators",
                        style = MaterialTheme.typography.labelSmall,
                        color = TranzoColors.TextSecondary,
                        fontWeight = FontWeight.Medium,
                    )
                }
            } else {
                // ── Email Input ──────────────────────────────
                Text(
                    "Enter your email",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = TranzoColors.TextPrimary,
                )

                Spacer(modifier = Modifier.height(8.dp))

                ClayTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = "Username or email",
                    leadingIcon = {
                        Icon(
                            Icons.Outlined.Email,
                            contentDescription = null,
                            tint = TranzoColors.ClayBlue,
                            modifier = Modifier.size(20.dp),
                        )
                    },
                )

                if (state.error != null) {
                    Text(
                        state.error!!,
                        style = MaterialTheme.typography.bodySmall,
                        color = TranzoColors.Error,
                        modifier = Modifier.padding(vertical = 4.dp),
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                ClayButton(
                    text = "Sign In",
                    onClick = {
                        coroutineScope.launch {
                            viewModel.sendOtp(email)
                            onNavigateToOtp(email)
                        }
                    },
                    enabled = email.contains("@") && !state.isLoading,
                    isLoading = state.isLoading,
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(
                    onClick = { showEmailInput = false },
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        "Back",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = TranzoColors.ClayBlue,
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}


