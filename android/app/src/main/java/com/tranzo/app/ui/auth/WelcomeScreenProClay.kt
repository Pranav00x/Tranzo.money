package com.tranzo.app.ui.auth

import android.app.Activity
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tranzo.app.ui.components.*
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
        EntryPointAccessors.fromApplication(context, GoogleSignInEntryPointProClay::class.java).googleSignInHelper()
    }

    var showContent by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { showContent = true }

    val contentAlpha by animateFloatAsState(
        targetValue = if (showContent) 1f else 0f, animationSpec = tween(800), label = "fade"
    )

    Box(Modifier.fillMaxSize().background(TranzoColors.ClayBackground)) {
        Column(
            Modifier.fillMaxSize().verticalScroll(rememberScrollState()).alpha(contentAlpha).padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Spacer(Modifier.height(60.dp))

            // Logo + Welcome
            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    Modifier.size(80.dp)
                        .shadow(20.dp, RoundedCornerShape(26.dp), ambientColor = TranzoColors.ClayBlue.copy(alpha = 0.4f))
                        .clip(RoundedCornerShape(26.dp))
                        .background(Brush.linearGradient(listOf(TranzoColors.ClayBlue, Color(0xFF7B5CE8))))
                        .drawBehind {
                            drawRoundRect(
                                Brush.verticalGradient(listOf(Color.White.copy(alpha = 0.3f), Color.Transparent), startY = 0f, endY = size.height * 0.4f),
                                cornerRadius = CornerRadius(26.dp.toPx()), size = Size(size.width, size.height * 0.4f)
                            )
                        },
                    contentAlignment = Alignment.Center,
                ) {
                    Text("T", style = MaterialTheme.typography.displayMedium, color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 40.sp)
                }

                Spacer(Modifier.height(28.dp))
                Text("Welcome to Tranzo", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.ExtraBold, color = TranzoColors.TextPrimary)
                Spacer(Modifier.height(6.dp))
                Text("Your smart crypto wallet", style = MaterialTheme.typography.bodyLarge, color = TranzoColors.TextSecondary)
            }

            Spacer(Modifier.height(32.dp))

            if (!showEmailInput) {
                // Auth method cards
                ClayAuthMethodCard(
                    icon = Icons.Outlined.Email, title = "Email", description = "Sign in with OTP code",
                    onClick = { showEmailInput = true }, iconColor = TranzoColors.ClayBlue,
                )
                ClayAuthMethodCard(
                    icon = Icons.Outlined.Lock, title = "Google", description = "Quick sign in",
                    onClick = {
                        coroutineScope.launch {
                            val idToken = googleSignInHelper.signIn(context as? Activity ?: return@launch)
                            if (idToken != null) viewModel.loginWithGoogle(idToken)
                        }
                    },
                    iconColor = TranzoColors.ClayPurple,
                )

                Spacer(Modifier.height(24.dp))

                // Trust badge
                ClayCard(Modifier.fillMaxWidth(), cornerRadius = 18.dp, shadowElevation = 4.dp) {
                    Row(Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                        ClayIconPill(color = TranzoColors.ClayGreen, size = 36.dp, cornerRadius = 12.dp) {
                            Icon(Icons.Outlined.Lock, null, tint = Color.White, modifier = Modifier.size(18.dp))
                        }
                        Text("Protected by account abstraction & Kernel validators", style = MaterialTheme.typography.labelSmall, color = TranzoColors.TextSecondary, fontWeight = FontWeight.Medium)
                    }
                }
            } else {
                // Email input
                Text("Enter your email", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.ExtraBold, color = TranzoColors.TextPrimary)
                Spacer(Modifier.height(8.dp))
                ClayTextField(value = email, onValueChange = { email = it }, placeholder = "Username or email",
                    leadingIcon = { Icon(Icons.Outlined.Email, null, tint = TranzoColors.ClayBlue, modifier = Modifier.size(20.dp)) })

                if (state.error != null) {
                    Box(Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)).background(TranzoColors.ClayCoralSoft).padding(12.dp)) {
                        Text(state.error!!, style = MaterialTheme.typography.bodySmall, color = TranzoColors.ClayCoral, fontWeight = FontWeight.SemiBold)
                    }
                }

                Spacer(Modifier.height(16.dp))
                ClayButton(text = "Sign In", onClick = { coroutineScope.launch { viewModel.sendOtp(email); onNavigateToOtp(email) } },
                    enabled = email.contains("@") && !state.isLoading, isLoading = state.isLoading)

                Spacer(Modifier.height(8.dp))
                TextButton(onClick = { showEmailInput = false }, modifier = Modifier.fillMaxWidth()) {
                    Text("Back", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.SemiBold, color = TranzoColors.ClayBlue)
                }
            }

            Spacer(Modifier.height(32.dp))
        }
    }
}
