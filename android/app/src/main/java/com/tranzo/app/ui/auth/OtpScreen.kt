package com.tranzo.app.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tranzo.app.ui.components.SecurityBadges
import com.tranzo.app.ui.components.TranzoButton
import com.tranzo.app.ui.theme.TranzoColors
import kotlinx.coroutines.delay

@Composable
fun OtpScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    email: String,
    onNavigateToHome: () -> Unit,
    onResend: () -> Unit,
    onSkip: (() -> Unit)? = null,
) {
    val state by viewModel.state.collectAsState()
    var otpValue by remember { mutableStateOf("") }
    var resendTimer by remember { mutableIntStateOf(30) }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(state.isAuthenticated) {
        if (state.isAuthenticated) onNavigateToHome()
    }

    LaunchedEffect(resendTimer) {
        if (resendTimer > 0) {
            delay(1000)
            resendTimer--
        }
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(TranzoColors.White)
            .systemBarsPadding()
            .navigationBarsPadding(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFF0A0A0A), Color(0xFF232323), Color(0xFF0A0A0A))
                        ),
                        shape = RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp),
                    )
                    .padding(horizontal = 22.dp, vertical = 20.dp),
            ) {
                Column {
                    Box(
                        modifier = Modifier
                            .background(Color(0x33FFFFFF), CircleShape)
                            .padding(horizontal = 10.dp, vertical = 4.dp),
                    ) {
                        Text(
                            text = "One-Time Password",
                            style = MaterialTheme.typography.labelMedium,
                            color = TranzoColors.White,
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Verify Email",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = TranzoColors.White,
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Code sent to ${maskEmail(email)}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TranzoColors.White.copy(alpha = 0.86f),
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = TranzoColors.CardSurface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    BasicTextField(
                        value = otpValue,
                        onValueChange = { updated ->
                            if (updated.length <= 6 && updated.all { it.isDigit() }) {
                                otpValue = updated
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequester),
                        decorationBox = {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                repeat(6) { index ->
                                    val char = otpValue.getOrNull(index)?.toString().orEmpty()
                                    val selected = otpValue.length == index
                                    Box(
                                        modifier = Modifier
                                            .size(46.dp)
                                            .background(
                                                color = if (char.isEmpty()) TranzoColors.LightGray else TranzoColors.PaleTeal,
                                                shape = RoundedCornerShape(12.dp),
                                            ),
                                        contentAlignment = Alignment.Center,
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .matchParentSize()
                                                .padding(1.5.dp)
                                                .background(
                                                    if (selected) TranzoColors.White else Color.Transparent,
                                                    RoundedCornerShape(11.dp),
                                                ),
                                        )
                                        Text(
                                            text = char,
                                            color = TranzoColors.TextPrimary,
                                            style = MaterialTheme.typography.headlineSmall,
                                            textAlign = TextAlign.Center,
                                        )
                                    }
                                }
                            }
                        },
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    if (resendTimer > 0) {
                        Text(
                            text = "Resend OTP in ${resendTimer}s",
                            style = MaterialTheme.typography.bodySmall,
                            color = TranzoColors.TextSecondary,
                        )
                    } else {
                        Text(
                            text = "Resend OTP",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TranzoColors.PrimaryBlack,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.clickable {
                                resendTimer = 30
                                otpValue = ""
                                viewModel.sendOtp(email)
                                onResend()
                            },
                        )
                    }

                    state.error?.let {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodySmall,
                            color = TranzoColors.Error,
                            textAlign = TextAlign.Center,
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    TranzoButton(
                        text = if (state.isLoading) "Verifying..." else "Verify OTP",
                        onClick = { viewModel.verifyOtp(email, otpValue) },
                        enabled = otpValue.length == 6,
                        isLoading = state.isLoading,
                    )

                    if (state.isLoading) {
                        Spacer(modifier = Modifier.height(12.dp))
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = TranzoColors.PrimaryBlack,
                            strokeWidth = 2.dp,
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))
            SecurityBadges()
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

private fun maskEmail(email: String): String {
    val parts = email.split("@")
    if (parts.size != 2) return email
    val user = parts[0]
    val maskedUser = when {
        user.length <= 2 -> "${user.firstOrNull() ?: '*'}*"
        else -> "${user.take(2)}***"
    }
    return "$maskedUser@${parts[1]}"
}
