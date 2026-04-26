package com.tranzo.app.ui.security

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Fingerprint
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.tranzo.app.ui.components.ClayButton
import com.tranzo.app.ui.theme.TranzoColors

@Composable
fun BiometricSetupScreen(
    onEnable: () -> Unit = {},
    onSkip: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .systemBarsPadding()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(80.dp))

        // Fingerprint icon
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(TranzoColors.ClayBackgroundAlt),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Outlined.Fingerprint,
                contentDescription = null,
                tint = TranzoColors.TextPrimary,
                modifier = Modifier.size(40.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            "Enable Biometric",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = TranzoColors.TextPrimary,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "Use fingerprint or face ID to quickly\nunlock your wallet",
            style = MaterialTheme.typography.bodyMedium,
            color = TranzoColors.TextSecondary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(48.dp))

        // Benefits
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            BenefitRow(
                icon = Icons.Outlined.Fingerprint,
                text = "Instant unlock with your fingerprint"
            )
            BenefitRow(
                icon = Icons.Outlined.Shield,
                text = "Hardware-backed secure element"
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Enable button using minimal system
        ClayButton(
            text = "Enable Biometric",
            onClick = onEnable,
        )

        Spacer(modifier = Modifier.height(12.dp))

        TextButton(onClick = onSkip) {
            Text(
                "Maybe later",
                style = MaterialTheme.typography.bodyMedium,
                color = TranzoColors.TextSecondary
            )
        }
    }
}

@Composable
private fun BenefitRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        color = TranzoColors.ClayBackgroundAlt
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    icon,
                    contentDescription = null,
                    tint = TranzoColors.TextPrimary,
                    modifier = Modifier.size(18.dp)
                )
            }
            Text(
                text,
                style = MaterialTheme.typography.bodyMedium,
                color = TranzoColors.TextPrimary,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
