package com.tranzo.app.ui.auth

import android.app.Activity
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.*
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
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

/**
 * CheQ-inspired Welcome screen — monochrome, Sign In / Sign Up toggle,
 * supports: Email OTP, Google, X (Twitter), Passkey
 */
@Composable
fun WelcomeScreenPro(
    viewModel: AuthViewModel = hiltViewModel(),
    onNavigateToOtp: (String) -> Unit = {},
    onAuthenticationSuccess: (isNewUser: Boolean) -> Unit = {},
) {
    val state by viewModel.state.collectAsState()
    var isSignUp by remember { mutableStateOf(true) }
    var showEmailInput by remember { mutableStateOf(false) }
    var email by remember { mutableStateOf("") }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val googleSignInHelper = remember {
        EntryPointAccessors.fromApplication(
            context,
            GoogleSignInEntryPointPro::class.java
        ).googleSignInHelper()
    }

    // Fade in
    var showContent by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { showContent = true }
    val alpha by animateFloatAsState(
        targetValue = if (showContent) 1f else 0f,
        animationSpec = tween(600),
        label = "fade"
    )

    // Handle auth success
    LaunchedEffect(state.isAuthenticated) {
        if (state.isAuthenticated) {
            onAuthenticationSuccess(state.isNewUser)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .alpha(alpha)
                .verticalScroll(rememberScrollState())
                .systemBarsPadding()
        ) {
            if (!showEmailInput) {
                // ── Main auth selection ──────────────────────────
                Spacer(modifier = Modifier.height(60.dp))

                // Logo
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color(0xFF1A1A1A)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "T",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        "Tranzo",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1A1A1A)
                    )
                    Text(
                        "Self-custody smart wallet",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF999999)
                    )
                }

                Spacer(modifier = Modifier.height(48.dp))

                // Sign In / Sign Up toggle
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color(0xFFF5F5F5))
                        .padding(4.dp)
                ) {
                    ToggleTab(
                        text = "Sign Up",
                        isSelected = isSignUp,
                        onClick = { isSignUp = true },
                        modifier = Modifier.weight(1f)
                    )
                    ToggleTab(
                        text = "Sign In",
                        isSelected = !isSignUp,
                        onClick = { isSignUp = false },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    if (isSignUp) "Create a new smart wallet"
                    else "Access your existing wallet",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color(0xFF999999),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp)
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Auth methods
                Column(
                    modifier = Modifier.padding(horizontal = 32.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Google
                    AuthMethodRow(
                        icon = Icons.Outlined.AccountCircle,
                        label = "Continue with Google",
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

                    // X (Twitter)
                    AuthMethodRow(
                        icon = Icons.Outlined.AlternateEmail,
                        label = "Continue with X",
                        onClick = { /* X auth — to be implemented */ }
                    )

                    // Email OTP
                    AuthMethodRow(
                        icon = Icons.Outlined.Email,
                        label = "Continue with Email",
                        onClick = { showEmailInput = true }
                    )

                    // Passkey
                    AuthMethodRow(
                        icon = Icons.Outlined.Fingerprint,
                        label = "Continue with Passkey",
                        onClick = {
                            if (isSignUp) {
                                viewModel.registerPasskey(context)
                            } else {
                                // For login, we might need the email first or use a resident key
                                // For simplicity, we'll try to login with any available passkey
                                viewModel.loginWithPasskey(context, email)
                            }
                        }
                    )
                }

                // Error
                state.error?.let { err ->
                    Spacer(modifier = Modifier.height(16.dp))
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 32.dp),
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFFFFF0F0)
                    ) {
                        Text(
                            err,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFFCC0000),
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }

                // Loading overlay
                if (state.isLoading) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = Color(0xFF1A1A1A),
                            strokeWidth = 2.dp
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            "Authenticating...",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF666666)
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                // Footer
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp, vertical = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Outlined.Lock,
                            contentDescription = null,
                            tint = Color(0xFFCCCCCC),
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            "256-bit encrypted · Self-custody",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xFFCCCCCC)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "By continuing, you agree to our Terms & Privacy Policy",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFFCCCCCC),
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                // ── Email input screen ───────────────────────────
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { showEmailInput = false }) {
                        Icon(
                            Icons.AutoMirrored.Outlined.ArrowBack,
                            contentDescription = "Back",
                            tint = Color(0xFF1A1A1A)
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp)
                ) {
                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        if (isSignUp) "Create account" else "Welcome back",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1A1A1A)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "We'll send a verification code to your email",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF999999)
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // Email field
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it.trim() },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Email address") },
                        placeholder = { Text("you@example.com") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF1A1A1A),
                            unfocusedBorderColor = Color(0xFFE0E0E0),
                            focusedLabelColor = Color(0xFF1A1A1A),
                            unfocusedLabelColor = Color(0xFF999999),
                            cursorColor = Color(0xFF1A1A1A),
                        )
                    )

                    // Error
                    state.error?.let { err ->
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            err,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFFCC0000)
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Send code button
                    Button(
                        onClick = {
                            if (email.contains("@")) {
                                viewModel.sendOtp(email.trim())
                                onNavigateToOtp(email.trim())
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        enabled = email.contains("@") && !state.isLoading,
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF1A1A1A),
                            contentColor = Color.White,
                            disabledContainerColor = Color(0xFFE0E0E0),
                            disabledContentColor = Color(0xFF999999)
                        ),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
                    ) {
                        if (state.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = Color.White,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text(
                                "Send Verification Code",
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }
    }
}

// ── Toggle tab (Sign In / Sign Up) ──────────────────────────────
@Composable
private fun ToggleTab(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .height(44.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        color = if (isSelected) Color(0xFF1A1A1A) else Color.Transparent,
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) Color.White else Color(0xFF999999)
            )
        }
    }
}

// ── Auth method row ─────────────────────────────────────────────
@Composable
private fun AuthMethodRow(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(14.dp),
        color = Color(0xFFF5F5F5)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF1A1A1A)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
            }
            Text(
                label,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF1A1A1A),
                modifier = Modifier.weight(1f)
            )
            Icon(
                Icons.Outlined.ChevronRight,
                contentDescription = null,
                tint = Color(0xFFCCCCCC),
                modifier = Modifier.size(20.dp)
            )
        }
    }
}