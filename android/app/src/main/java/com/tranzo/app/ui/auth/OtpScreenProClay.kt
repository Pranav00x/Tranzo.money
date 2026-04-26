package com.tranzo.app.ui.auth

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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tranzo.app.ui.components.*
import com.tranzo.app.ui.theme.TranzoColors

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
    val contentAlpha by animateFloatAsState(targetValue = if (showContent) 1f else 0f, animationSpec = tween(800), label = "fade")

    Box(Modifier.fillMaxSize().background(TranzoColors.ClayBackground)) {
        Canvas(Modifier.fillMaxSize()) {
            drawCircle(TranzoColors.ClayBlue.copy(alpha = 0.06f), 220f, Offset(size.width * 0.8f, size.height * 0.15f))
            drawCircle(TranzoColors.ClayPurple.copy(alpha = 0.05f), 180f, Offset(size.width * 0.15f, size.height * 0.6f))
        }

        Column(
            Modifier.fillMaxSize().verticalScroll(rememberScrollState()).alpha(contentAlpha).padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(Modifier.height(60.dp))

            Box(Modifier.align(Alignment.CenterHorizontally)) {
                ClayIconPill(color = TranzoColors.ClayBlue, size = 76.dp, cornerRadius = 26.dp) {
                    Icon(Icons.Outlined.Email, null, tint = Color.White, modifier = Modifier.size(36.dp))
                }
            }

            Spacer(Modifier.height(16.dp))

            Text("Verify Your Email", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.ExtraBold,
                color = TranzoColors.TextPrimary, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
            Text("We sent a 6-digit code to\n$email", style = MaterialTheme.typography.bodyMedium,
                color = TranzoColors.TextSecondary, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())

            Spacer(Modifier.height(24.dp))

            ClayCard(Modifier.fillMaxWidth(), cornerRadius = 22.dp) {
                Column(Modifier.fillMaxWidth().padding(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    Text("VERIFICATION CODE", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold,
                        color = TranzoColors.TextTertiary, letterSpacing = 1.5.sp)
                    ClayTextField(value = otp, onValueChange = { if (it.length <= 6) otp = it }, placeholder = "000000")
                }
            }

            if (state.error != null) {
                Box(Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)).background(TranzoColors.ClayCoralSoft).padding(12.dp)) {
                    Text(state.error!!, style = MaterialTheme.typography.bodySmall, color = TranzoColors.ClayCoral, fontWeight = FontWeight.SemiBold, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
                }
            }

            Spacer(Modifier.height(16.dp))
            ClayButton(text = "Verify Code", onClick = { viewModel.verifyOtp(email, otp); onOtpVerified() },
                enabled = otp.length == 6 && !state.isLoading, isLoading = state.isLoading)

            TextButton(onClick = { viewModel.sendOtp(email) }, modifier = Modifier.fillMaxWidth()) {
                Text(if (showResend) "Resend Code" else "Resend in 30s", style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold, color = if (showResend) TranzoColors.ClayBlue else TranzoColors.TextTertiary)
            }
            Spacer(Modifier.height(32.dp))
        }
    }
}
