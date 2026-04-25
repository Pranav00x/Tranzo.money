package com.tranzo.app.ui.auth

import android.app.Activity
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tranzo.app.ui.components.ClayButton
import com.tranzo.app.ui.components.ClayCard
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
 * Claymorphism Welcome Screen - Premium, playful design for Gen Z
 * Soft gradients, generous spacing, trust-focused
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
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        TranzoColors.BackgroundLight,
                        TranzoColors.SurfaceLight.copy(alpha = 0.7f)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .alpha(contentAlpha)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Header space
            Spacer(modifier = Modifier.height(40.dp))

            // Logo & Branding - Claymorphism style
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(RoundedCornerShape(28.dp))
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    TranzoColors.PrimaryBlue,
                                    TranzoColors.PrimaryPurple
                                )
                            )
                        )
                        .shadow(
                            elevation = 12.dp,
                            shape = RoundedCornerShape(28.dp),
                            ambientColor = TranzoColors.PrimaryBlue.copy(alpha = 0.25f)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "₮",
                        style = MaterialTheme.typography.displayMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 40.sp
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    "Tranzo",
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Bold,
                    color = TranzoColors.TextPrimary,
                    fontSize = 32.sp
                )

                Text(
                    "Crypto card + smart wallet",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TranzoColors.TextSecondary
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            if (!showEmailInput) {
                // Auth method selection
                Text(
                    "How do you want to login?",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = TranzoColors.TextPrimary
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Email option - Clay Card
                ClayAuthMethodCard(
                    icon = Icons.Outlined.Email,
                    title = "Email",
                    description = "One-time code",
                    onClick = { showEmailInput = true },
                    gradientStart = TranzoColors.PrimaryBlue,
                    gradientEnd = TranzoColors.BlueLight,
                )

                // Google option
                ClayAuthMethodCard(
                    icon = Icons.Outlined.Lock,
                    title = "Google",
                    description = "Sign in with Google",
                    onClick = {
                        coroutineScope.launch {
                            val idToken = googleSignInHelper.signIn(context as? Activity ?: return@launch)
                            if (idToken != null) {
                                viewModel.loginWithGoogle(idToken)
                            }
                        }
                    },
                    gradientStart = TranzoColors.PrimaryPink,
                    gradientEnd = TranzoColors.PinkLight,
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Trust & Security message
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(TranzoColors.Success.copy(alpha = 0.08f))
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Lock,
                        contentDescription = "Secure",
                        tint = TranzoColors.Success,
                        modifier = Modifier.size(20.dp)
                    )

                    Text(
                        "Your assets are protected by account abstraction & Kernel validators",
                        style = MaterialTheme.typography.labelSmall,
                        color = TranzoColors.TextSecondary,
                        fontWeight = FontWeight.Medium
                    )
                }
            } else {
                // Email input view
                Text(
                    "Sign in with Email",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = TranzoColors.TextPrimary
                )

                Spacer(modifier = Modifier.height(24.dp))

                ClayTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = "Enter your email",
                    leadingIcon = {
                        Icon(
                            Icons.Outlined.Email,
                            contentDescription = null,
                            tint = TranzoColors.PrimaryBlue,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                ClayButton(
                    text = "Send Code",
                    onClick = {
                        coroutineScope.launch {
                            viewModel.sendOtp(email)
                            onNavigateToOtp(email)
                        }
                    },
                    gradientStart = TranzoColors.PrimaryBlue,
                    gradientEnd = TranzoColors.PrimaryPurple,
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { showEmailInput = false },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = TranzoColors.PrimaryBlue,
                    ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
                ) {
                    Text(
                        "Back",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

/**
 * Claymorphism Auth Method Card
 */
@Composable
private fun ClayAuthMethodCard(
    icon: ImageVector,
    title: String,
    description: String,
    onClick: () -> Unit,
    gradientStart: Color = TranzoColors.PrimaryBlue,
    gradientEnd: Color = TranzoColors.BlueLight,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(Color.White)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(24.dp),
                ambientColor = Color.Black.copy(alpha = 0.08f)
            )
            .clickable(onClick = onClick)
            .padding(16.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon box with gradient
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(gradientStart, gradientEnd)
                        ),
                        shape = RoundedCornerShape(20.dp)
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }

            // Text content
            Column(
                modifier = Modifier
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = TranzoColors.TextPrimary
                )
                Text(
                    description,
                    style = MaterialTheme.typography.labelSmall,
                    color = TranzoColors.TextSecondary
                )
            }

            // Arrow indicator
            Icon(
                imageVector = Icons.Outlined.Email, // TODO: Use ChevronRight
                contentDescription = null,
                tint = TranzoColors.TextTertiary,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
