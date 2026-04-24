package com.tranzo.app.ui.auth

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.Wallet
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.delay

/**
 * CheQ-inspired wallet creation — monochrome step progress.
 * Simulates ERC-4337 smart account deployment.
 */
@Composable
fun WalletCreationScreenPro(
    viewModel: AuthViewModel = hiltViewModel(),
    onComplete: () -> Unit = {},
    onSkip: () -> Unit = {},
) {
    var step by remember { mutableStateOf(0) }
    val isCreating = remember { mutableStateOf(false) }

    // Auto-start creation
    LaunchedEffect(Unit) {
        isCreating.value = true
    }

    // Step progression
    LaunchedEffect(isCreating.value, step) {
        if (isCreating.value && step < 3) {
            delay(1500)
            step++
            if (step >= 3) {
                isCreating.value = false
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .systemBarsPadding()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(60.dp))

        // Icon
        Box(
            modifier = Modifier
                .size(72.dp)
                .clip(CircleShape)
                .background(Color(0xFF1A1A1A)),
            contentAlignment = Alignment.Center
        ) {
            if (step >= 3) {
                Icon(
                    Icons.Filled.Check,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            } else {
                Icon(
                    Icons.Outlined.Wallet,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            if (step >= 3) "Wallet ready" else "Creating your wallet",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1A1A1A),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            if (step >= 3) "Your ERC-4337 smart account is live"
            else "Deploying your smart account on-chain",
            style = MaterialTheme.typography.bodySmall,
            color = Color(0xFF999999),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(48.dp))

        // Steps
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StepRow(
                number = 1,
                title = "Initializing account",
                description = "Setting up your blockchain identity",
                completed = step > 0,
                active = step == 0 && isCreating.value
            )
            StepRow(
                number = 2,
                title = "Generating keys",
                description = "Creating your secure wallet keys",
                completed = step > 1,
                active = step == 1 && isCreating.value
            )
            StepRow(
                number = 3,
                title = "Registering on-chain",
                description = "Deploying your smart contract account",
                completed = step > 2,
                active = step == 2 && isCreating.value
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Continue button
        Button(
            onClick = onComplete,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            enabled = step >= 3,
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1A1A1A),
                contentColor = Color.White,
                disabledContainerColor = Color(0xFFE0E0E0),
                disabledContentColor = Color(0xFF999999)
            ),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
        ) {
            if (step < 3) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = Color(0xFF999999),
                    strokeWidth = 2.dp
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text("Creating...", fontWeight = FontWeight.SemiBold)
            } else {
                Text("Continue", fontWeight = FontWeight.SemiBold)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (step < 3) {
            TextButton(onClick = onSkip) {
                Text(
                    "Skip for now",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF999999)
                )
            }
        }
    }
}

@Composable
private fun StepRow(
    number: Int,
    title: String,
    description: String,
    completed: Boolean,
    active: Boolean,
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        color = when {
            completed -> Color(0xFFF0FFF0)
            active -> Color(0xFFF5F5F5)
            else -> Color(0xFFFAFAFA)
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Step indicator
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(
                        when {
                            completed -> Color(0xFF22C55E)
                            active -> Color(0xFF1A1A1A)
                            else -> Color(0xFFE0E0E0)
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (completed) {
                    Icon(
                        Icons.Filled.Check,
                        contentDescription = "Done",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                } else if (active) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(18.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        "$number",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF999999)
                    )
                }
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    title,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = if (completed || active) Color(0xFF1A1A1A) else Color(0xFF999999)
                )
                Text(
                    description,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color(0xFF999999)
                )
            }
        }
    }
}