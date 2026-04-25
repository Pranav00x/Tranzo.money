package com.tranzo.app.ui.auth

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tranzo.app.ui.components.ClayButton
import com.tranzo.app.ui.components.ClayCard
import com.tranzo.app.ui.components.ClayTextField
import com.tranzo.app.ui.theme.TranzoColors

/**
 * Claymorphism OTP Screen — Baby blue bg, white card for OTP input,
 * solid blue verify button.
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            // Email icon in clay circle
            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
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
                Icon(
                    Icons.Outlined.Email,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(32.dp),
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "Verify Your Email",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = TranzoColors.TextPrimary,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )

            Text(
                "We sent a 6-digit code to\n$email",
                style = MaterialTheme.typography.bodyMedium,
                color = TranzoColors.TextSecondary,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(24.dp))

            // OTP Input in a white card
            ClayCard(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    Text(
                        "Enter verification code",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = TranzoColors.TextSecondary,
                    )
                    ClayTextField(
                        value = otp,
                        onValueChange = { if (it.length <= 6) otp = it },
                        placeholder = "000000",
                    )
                }
            }

            if (state.error != null) {
                Text(
                    state.error!!,
                    style = MaterialTheme.typography.bodySmall,
                    color = TranzoColors.Error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            ClayButton(
                text = "Verify Code",
                onClick = {
                    viewModel.verifyOtp(email, otp)
                    onOtpVerified()
                },
                enabled = otp.length == 6 && !state.isLoading,
                isLoading = state.isLoading,
            )

            // Resend
            TextButton(
                onClick = { viewModel.sendOtp(email) },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    if (showResend) "Resend Code" else "Resend in 30s",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = if (showResend) TranzoColors.ClayBlue else TranzoColors.TextTertiary,
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
