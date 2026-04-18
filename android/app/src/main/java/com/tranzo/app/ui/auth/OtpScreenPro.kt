package com.tranzo.app.ui.auth

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tranzo.app.ui.components.TranzoButton
import com.tranzo.app.ui.theme.TranzoColors

/**
 * Bold OTP verification screen - Modern, fast-paced design
 */
@Composable
fun OtpScreenPro(
    email: String,
    viewModel: AuthViewModel = hiltViewModel(),
    onNavigateToHome: (isNewUser: Boolean) -> Unit = {},
    onResend: () -> Unit = {},
    onSkip: () -> Unit = {},
) {
    val state by viewModel.state.collectAsState()
    var otp by remember { mutableStateOf("") }
    var showSuccess by remember { mutableStateOf(false) }

    // Loading animation
    val loadingScale by animateFloatAsState(
        targetValue = if (state.isLoading) 1.1f else 1f,
        animationSpec = tween(200)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(TranzoColors.BackgroundLight)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            // Header with back action
            Text(
                "Enter code",
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.ExtraBold,
                color = TranzoColors.TextPrimary
            )

            // Email confirmation
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.Email,
                    contentDescription = null,
                    tint = TranzoColors.TextSecondary,
                    modifier = Modifier.size(20.dp)
                )
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        "Code sent to",
                        style = MaterialTheme.typography.labelSmall,
                        color = TranzoColors.TextTertiary
                    )
                    Text(
                        email,
                        style = MaterialTheme.typography.bodyMedium,
                        color = TranzoColors.TextPrimary,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                if (showSuccess) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = "Verified",
                        tint = TranzoColors.Success,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // OTP Input - Large, bold display
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 2.dp,
                        color = if (otp.length == 6) TranzoColors.Success else TranzoColors.TextTertiary,
                        shape = RoundedCornerShape(16.dp)
                    ),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = if (otp.isEmpty()) 0.dp else 4.dp
                )
            ) {
                TextField(
                    value = otp,
                    onValueChange = { newValue ->
                        if (newValue.length <= 6 && newValue.all { it.isDigit() }) {
                            otp = newValue
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 20.dp),
                    textStyle = MaterialTheme.typography.displayMedium.copy(
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        color = TranzoColors.TextPrimary,
                        letterSpacing = 8.sp
                    ),
                    placeholder = {
                        Text(
                            "000000",
                            style = MaterialTheme.typography.displayMedium,
                            textAlign = TextAlign.Center,
                            color = TranzoColors.TextTertiary.copy(alpha = 0.3f),
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 8.sp,
                            modifier = Modifier.fillMaxWidth()
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent
                    ),
                    singleLine = true
                )
            }

            // Progress indicator - shows code length
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                repeat(6) { index ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(4.dp)
                            .background(
                                color = if (index < otp.length) TranzoColors.PrimaryBlue else TranzoColors.SurfaceLight,
                                shape = RoundedCornerShape(2.dp)
                            )
                    )
                }
            }

            // Error message
            if (state.error != null) {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    color = TranzoColors.Error.copy(alpha = 0.1f)
                ) {
                    Text(
                        state.error!!,
                        style = MaterialTheme.typography.bodySmall,
                        color = TranzoColors.Error,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Verify button
            TranzoButton(
                text = if (state.isLoading) "Verifying..." else "Verify Code",
                onClick = {
                    if (otp.length == 6) {
                        viewModel.verifyOtp(email, otp)
                    }
                },
                enabled = otp.length == 6 && !state.isLoading,
                isLoading = state.isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .graphicsLayer(scaleX = loadingScale, scaleY = loadingScale)
            )

            // Resend option
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Didn't receive a code?",
                    style = MaterialTheme.typography.bodySmall,
                    color = TranzoColors.TextSecondary
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    "Resend",
                    style = MaterialTheme.typography.bodySmall,
                    color = TranzoColors.PrimaryBlue,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.clickable { onResend() }
                )
            }

            Spacer(modifier = Modifier.height(48.dp))
        }
    }

    // Launch verification when OTP is complete
    LaunchedEffect(state.isAuthenticated) {
        if (state.isAuthenticated) {
            showSuccess = true
            onNavigateToHome(state.isNewUser)
        }
    }
}

import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.material3.TextField
import androidx.compose.ui.unit.sp
