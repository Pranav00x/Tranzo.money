package com.tranzo.app.ui.auth

import android.app.Activity
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronRight
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
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
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
interface GoogleSignInEntryPointPro {
    fun googleSignInHelper(): GoogleSignInHelper
}

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

            // Logo & Branding
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "₮",
                        style = MaterialTheme.typography.displayMedium,
                        color = Color.Black,
                        fontWeight = FontWeight.ExtraBold
                    )
                }

                Spacer(modifier = Modifier.height(28.dp))

                Text(
                    "Tranzo",
                    style = MaterialTheme.typography.displayMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = TranzoColors.TextPrimary,
                    letterSpacing = (-1.5).sp
                )

                Text(
                    "Crypto card + smart wallet",
                    style = MaterialTheme.typography.titleMedium,
                    color = TranzoColors.TextSecondary,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            if (!showEmailInput) {
                Text(
                    "Get started",
                    style = MaterialTheme.typography.headlineSmall,
                    color = TranzoColors.TextPrimary,
                    fontWeight = FontWeight.Bold
                )

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

                Spacer(modifier = Modifier.height(32.dp))

                // Trust indicators
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Lock,
                        contentDescription = null,
                        tint = TranzoColors.TextTertiary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "Your funds are always in your control",
                        style = MaterialTheme.typography.labelMedium,
                        color = TranzoColors.TextTertiary,
                        fontWeight = FontWeight.Medium
                    )
                }
            } else {
                Text(
                    "Enter your email",
                    style = MaterialTheme.typography.headlineSmall,
                    color = TranzoColors.TextPrimary,
                    fontWeight = FontWeight.Bold
                )

                ClayTextField(
                    value = email,
                    onValueChange = { email = it.trim() },
                    placeholder = "you@example.com",
                    keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                        keyboardType = KeyboardType.Email
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                if (state.error != null) {
                    Text(
                        state.error!!,
                        style = MaterialTheme.typography.bodySmall,
                        color = TranzoColors.ClayCoral,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                ClayButton(
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

                TextButton(
                    onClick = { showEmailInput = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Back", color = TranzoColors.TextSecondary, fontWeight = FontWeight.Bold)
                }
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
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(TranzoColors.ClayCard)
            .border(1.dp, TranzoColors.DividerGray, RoundedCornerShape(20.dp))
            .clickable(onClick = onClick)
            .padding(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(TranzoColors.ClayBackgroundAlt),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(22.dp)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = TranzoColors.TextPrimary
                    )
                    Text(
                        description,
                        style = MaterialTheme.typography.labelMedium,
                        color = TranzoColors.TextSecondary
                    )
                }
            }

            Icon(
                Icons.Outlined.ChevronRight,
                null,
                tint = TranzoColors.TextTertiary,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}