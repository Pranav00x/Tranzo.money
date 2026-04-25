package com.tranzo.app.ui.auth

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tranzo.app.ui.components.ClayButton
import com.tranzo.app.ui.components.ClayTextField
import com.tranzo.app.ui.theme.TranzoColors

/**
 * Claymorphism OTP Verification Screen
 */
@Composable
fun OtpScreenProClay(
    email: String = "",
    viewModel: AuthViewModel = hiltViewModel(),
    onOtpVerified: () -> Unit = {},
) {
    var otp by remember { mutableStateOf("") }
    var showResend by remember { mutableStateOf(false) }
    val state by viewModel.state.collectAsState()

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
            Spacer(modifier = Modifier.height(40.dp))

            // Header
            Text(
                "Verify Your Email",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = TranzoColors.TextPrimary,
                fontSize = 28.sp
            )

            Text(
                "We sent a code to $email",
                style = MaterialTheme.typography.bodyMedium,
                color = TranzoColors.TextSecondary
            )

            Spacer(modifier = Modifier.height(24.dp))

            // OTP Input
            ClayTextField(
                value = otp,
                onValueChange = { if (it.length <= 6) otp = it },
                placeholder = "000000",
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Verify Button
            ClayButton(
                text = "Verify Code",
                onClick = {
                    viewModel.verifyOtp(email, otp)
                    onOtpVerified()
                },
                gradientStart = TranzoColors.PrimaryBlue,
                gradientEnd = TranzoColors.PrimaryPurple,
            )

            // Resend
            if (showResend) {
                Button(
                    onClick = { viewModel.sendOtp(email) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = TranzoColors.SurfaceLight,
                        contentColor = TranzoColors.PrimaryBlue,
                    ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
                    shape = RoundedCornerShape(24.dp),
                ) {
                    Text(
                        "Resend Code",
                        fontWeight = FontWeight.SemiBold,
                    )
                }
            } else {
                Text(
                    "Resend in 30s",
                    style = MaterialTheme.typography.labelSmall,
                    color = TranzoColors.TextTertiary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
