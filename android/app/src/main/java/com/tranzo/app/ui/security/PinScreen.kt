package com.tranzo.app.ui.security

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.Backspace
import androidx.compose.material.icons.outlined.Fingerprint
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.delay

/**
 * CheQ-inspired PIN screen — monochrome numpad, 4-digit dots.
 * Supports SETUP (create + confirm) and ENTER (unlock) modes.
 */
@Composable
fun PinScreen(
    viewModel: SecurityViewModel = hiltViewModel(),
    mode: PinMode = PinMode.ENTER,
    onSuccess: (String) -> Unit = {},
    onBack: () -> Unit = {},
    onUseBiometric: (() -> Unit)? = null,
) {
    val securityState by viewModel.state.collectAsState()
    var pin by remember { mutableStateOf("") }
    var confirmPin by remember { mutableStateOf("") }
    var isConfirming by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }
    val pinLength = 4

    LaunchedEffect(pin) {
        if (showError) showError = false

        if (pin.length == pinLength) {
            when (mode) {
                PinMode.SETUP -> {
                    if (!isConfirming) {
                        delay(200)
                        isConfirming = true
                        confirmPin = pin
                        pin = ""
                    } else {
                        if (pin == confirmPin) {
                            delay(200)
                            viewModel.setPin(pin)
                            onSuccess(pin)
                        } else {
                            showError = true
                            delay(500)
                            pin = ""
                            isConfirming = false
                            confirmPin = ""
                        }
                    }
                }
                PinMode.ENTER -> {
                    delay(200)
                    if (viewModel.validatePin(pin)) {
                        onSuccess(pin)
                    } else {
                        showError = true
                        delay(1000)
                        pin = ""
                        showError = false
                    }
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .systemBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // ── Top Bar ──────────────────────────────────────────
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    Icons.AutoMirrored.Outlined.ArrowBack,
                    contentDescription = "Back",
                    tint = Color(0xFF1A1A1A)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            if (mode == PinMode.ENTER && onUseBiometric != null) {
                IconButton(onClick = onUseBiometric) {
                    Icon(
                        Icons.Outlined.Fingerprint,
                        contentDescription = "Biometric",
                        tint = Color(0xFF1A1A1A)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(0.3f))

        // ── Header ───────────────────────────────────────────
        val title = when {
            showError -> "PINs don't match"
            mode == PinMode.SETUP && !isConfirming -> "Set your PIN"
            mode == PinMode.SETUP && isConfirming -> "Confirm PIN"
            else -> "Enter PIN"
        }
        val subtitle = when {
            showError -> "Please try again"
            mode == PinMode.SETUP && !isConfirming -> "Create a 4-digit PIN to secure your wallet"
            mode == PinMode.SETUP && isConfirming -> "Re-enter the same PIN to confirm"
            else -> "Unlock Tranzo"
        }

        Text(
            title,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = if (showError) Color(0xFFCC0000) else Color(0xFF1A1A1A),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            subtitle,
            style = MaterialTheme.typography.bodySmall,
            color = Color(0xFF999999),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 48.dp)
        )

        Spacer(modifier = Modifier.height(40.dp))

        // ── PIN dots ─────────────────────────────────────────
        Row(
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (i in 0 until pinLength) {
                val isFilled = i < pin.length
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .clip(CircleShape)
                        .background(
                            when {
                                showError -> Color(0xFFCC0000)
                                isFilled -> Color(0xFF1A1A1A)
                                else -> Color(0xFFE0E0E0)
                            }
                        )
                )
            }
        }

        Spacer(modifier = Modifier.weight(0.7f))

        // ── Numpad ───────────────────────────────────────────
        val rows = listOf(
            listOf("1", "2", "3"),
            listOf("4", "5", "6"),
            listOf("7", "8", "9"),
        )

        Column(
            modifier = Modifier.padding(horizontal = 48.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            rows.forEach { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    row.forEach { num ->
                        NumpadKey(text = num) {
                            if (pin.length < pinLength) pin += num
                        }
                    }
                }
            }

            // Bottom row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (mode == PinMode.ENTER && onUseBiometric != null) {
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape)
                            .clickable(onClick = onUseBiometric),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Outlined.Fingerprint,
                            contentDescription = "Biometric",
                            tint = Color(0xFF1A1A1A),
                            modifier = Modifier.size(28.dp)
                        )
                    }
                } else {
                    Spacer(modifier = Modifier.size(72.dp))
                }

                NumpadKey(text = "0") {
                    if (pin.length < pinLength) pin += "0"
                }

                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                        .clickable { if (pin.isNotEmpty()) pin = pin.dropLast(1) },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.AutoMirrored.Outlined.Backspace,
                        contentDescription = "Delete",
                        tint = Color(0xFF1A1A1A),
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(48.dp))
    }
}

@Composable
private fun NumpadKey(text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(72.dp)
            .clip(CircleShape)
            .background(Color(0xFFF5F5F5))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text,
            fontSize = 26.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF1A1A1A)
        )
    }
}

enum class PinMode {
    SETUP, ENTER
}
