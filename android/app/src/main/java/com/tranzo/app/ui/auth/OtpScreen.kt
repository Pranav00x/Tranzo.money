package com.tranzo.app.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.tranzo.app.ui.components.SecurityBadges
import com.tranzo.app.ui.theme.TranzoColors
import kotlinx.coroutines.delay

/**
 * OTP verification screen — 6 individual square boxes.
 * Matches CheQ's OTP input: auto-advance, resend countdown.
 */
@Composable
fun OtpScreen(
    email: String,
    onVerify: (String) -> Unit,
    onResend: () -> Unit,
    onSkip: (() -> Unit)? = null,
) {
    var otpValue by remember { mutableStateOf("") }
    var resendTimer by remember { mutableIntStateOf(30) }
    var isVerifying by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(resendTimer) {
        if (resendTimer > 0) {
            delay(1000)
            resendTimer--
        }
    }

    LaunchedEffect(otpValue) {
        if (otpValue.length == 6) {
            isVerifying = true
            onVerify(otpValue)
        }
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(TranzoColors.White)
            .systemBarsPadding()
            .padding(horizontal = 24.dp),
    ) {
        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = "Verify Your Email",
            style = MaterialTheme.typography.headlineLarge,
            color = TranzoColors.TextPrimary,
        )

        Spacer(modifier = Modifier.height(8.dp))

        val maskedEmail = email.take(3) + "***@" + email.substringAfter("@")
        Text(
            text = "An OTP has been sent to $maskedEmail",
            style = MaterialTheme.typography.bodyMedium,
            color = TranzoColors.TextSecondary,
        )

        Spacer(modifier = Modifier.height(48.dp))

        // OTP Input — 6 boxes
        Box(modifier = Modifier.fillMaxWidth()) {
            BasicTextField(
                value = otpValue,
                onValueChange = {
                    if (it.length <= 6 && it.all { c -> c.isDigit() }) {
                        otpValue = it
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                decorationBox = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                    ) {
                        repeat(6) { index ->
                            val char = otpValue.getOrNull(index)
                            val isFocused = otpValue.length == index

                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .border(
                                        width = if (isFocused) 2.dp else 1.dp,
                                        color = when {
                                            isFocused -> TranzoColors.PrimaryBlack
                                            char != null -> TranzoColors.PrimaryBlack.copy(alpha = 0.5f)
                                            else -> TranzoColors.BorderGray
                                        },
                                        shape = RoundedCornerShape(12.dp),
                                    )
                                    .background(
                                        color = if (char != null)
                                            TranzoColors.PaleTeal.copy(alpha = 0.3f)
                                        else TranzoColors.White,
                                        shape = RoundedCornerShape(12.dp),
                                    ),
                                contentAlignment = Alignment.Center,
                            ) {
                                Text(
                                    text = char?.toString() ?: "",
                                    style = MaterialTheme.typography.headlineMedium,
                                    color = TranzoColors.TextPrimary,
                                    textAlign = TextAlign.Center,
                                )
                            }
                        }
                    }
                },
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Resend timer
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            if (resendTimer > 0) {
                Text(
                    text = "Didn't receive? Resend in ${resendTimer}s",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TranzoColors.TextTertiary,
                )
            } else {
                TextButton(onClick = {
                    resendTimer = 30
                    onResend()
                }) {
                    Text(
                        text = "Resend OTP",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TranzoColors.PrimaryBlack,
                    )
                }
            }
        }

        if (isVerifying) {
            Spacer(modifier = Modifier.height(24.dp))
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth(),
                color = TranzoColors.PrimaryBlack,
                trackColor = TranzoColors.PaleTeal,
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        SecurityBadges()

        Spacer(modifier = Modifier.height(24.dp))
    }
}
