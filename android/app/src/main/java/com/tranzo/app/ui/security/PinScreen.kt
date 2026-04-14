package com.tranzo.app.ui.security

import androidx.compose.animation.*
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
import com.tranzo.app.ui.theme.TranzoColors
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Reusable PIN Entry / Setup Screen.
 * 
 * Supports: 
 * - Create PIN (first time)
 * - Confirm PIN (second time)
 * - Enter PIN (unlock app/transaction)
 */
@Composable
fun PinScreen(
    mode: PinMode = PinMode.ENTER,
    onSuccess: (String) -> Unit = {},
    onBack: () -> Unit = {},
    onUseBiometric: (() -> Unit)? = null,
) {
    var pin by remember { mutableStateOf("") }
    var confirmPin by remember { mutableStateOf("") }
    var isConfirming by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }
    
    val scope = rememberCoroutineScope()
    val pinLength = 4 // Tranzo uses 4-digit PIN for quick access

    LaunchedEffect(pin) {
        if (showError) showError = false
        
        if (pin.length == pinLength) {
            when (mode) {
                PinMode.SETUP -> {
                    if (!isConfirming) {
                        // Switch to confirm step
                        delay(200)
                        isConfirming = true
                        confirmPin = pin
                        pin = ""
                    } else {
                        // Validate confirm
                        if (pin == confirmPin) {
                            delay(200)
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
                    // TODO: Replace with real validation logic
                    // For mock, accept any 4 digits or logic. Let's just pass back for ViewModel to handle.
                    onSuccess(pin)
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(TranzoColors.Background)
            .systemBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // ── Top Bar ──────────────────────────────────────────────
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                    contentDescription = "Back",
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            if (mode == PinMode.ENTER && onUseBiometric != null) {
                IconButton(onClick = onUseBiometric) {
                    Icon(
                        imageVector = Icons.Outlined.Fingerprint,
                        contentDescription = "Use Biometric",
                        tint = TranzoColors.PrimaryGreen
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(0.5f))

        // ── Headers ──────────────────────────────────────────────
        val title = when {
            showError -> "PINs don't match"
            mode == PinMode.SETUP && !isConfirming -> "Set up your PIN"
            mode == PinMode.SETUP && isConfirming -> "Confirm your PIN"
            else -> "Enter your PIN"
        }
        val subtitle = when {
            showError -> "Please try setting up your PIN again"
            mode == PinMode.SETUP && !isConfirming -> "Create a 4-digit PIN to secure your wallet"
            mode == PinMode.SETUP && isConfirming -> "Enter the exact same PIN to confirm"
            else -> "Unlock Tranzo to access your funds"
        }

        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium,
            color = if (showError) TranzoColors.Error else TranzoColors.TextPrimary,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodyMedium,
            color = TranzoColors.TextSecondary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 32.dp)
        )

        Spacer(modifier = Modifier.height(48.dp))

        // ── PIN Indicators ───────────────────────────────────────
        Row(
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (i in 0 until pinLength) {
                val isFilled = i < pin.length
                val color = when {
                    showError -> TranzoColors.Error
                    isFilled -> TranzoColors.PrimaryGreen
                    else -> TranzoColors.BorderGray
                }
                
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .clip(CircleShape)
                        .background(color)
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // ── Numpad ───────────────────────────────────────────────
        Numpad(
            onNumberClick = { num ->
                if (pin.length < pinLength) {
                    pin += num
                }
            },
            onDeleteClick = {
                if (pin.isNotEmpty()) {
                    pin = pin.dropLast(1)
                }
            },
            biometricEnabled = (mode == PinMode.ENTER && onUseBiometric != null),
            onBiometricClick = { onUseBiometric?.invoke() }
        )

        Spacer(modifier = Modifier.height(48.dp))
    }
}

@Composable
private fun Numpad(
    onNumberClick: (String) -> Unit,
    onDeleteClick: () -> Unit,
    biometricEnabled: Boolean,
    onBiometricClick: () -> Unit
) {
    val rows = listOf(
        listOf("1", "2", "3"),
        listOf("4", "5", "6"),
        listOf("7", "8", "9"),
    )

    Column(
        modifier = Modifier.padding(horizontal = 48.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        rows.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                row.forEach { num ->
                    NumpadButton(text = num, onClick = { onNumberClick(num) })
                }
            }
        }
        
        // Bottom row: Biometric (if enabled) or empty, 0, Delete
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (biometricEnabled) {
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                        .clickable(onClick = onBiometricClick),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Fingerprint,
                        contentDescription = "Biometric",
                        tint = TranzoColors.PrimaryGreen,
                        modifier = Modifier.size(32.dp)
                    )
                }
            } else {
                Spacer(modifier = Modifier.size(72.dp))
            }

            NumpadButton(text = "0", onClick = { onNumberClick("0") })

            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape)
                    .clickable(onClick = onDeleteClick),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.Backspace,
                    contentDescription = "Delete",
                    tint = TranzoColors.TextPrimary,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}

@Composable
private fun NumpadButton(text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(72.dp)
            .clip(CircleShape)
            .background(TranzoColors.CardSurface)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.headlineLarge,
            color = TranzoColors.TextPrimary,
            fontWeight = FontWeight.Medium,
            fontSize = 28.sp
        )
    }
}

enum class PinMode {
    SETUP, ENTER
}
