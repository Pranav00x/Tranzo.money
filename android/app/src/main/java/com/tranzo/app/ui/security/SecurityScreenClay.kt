package com.tranzo.app.ui.security

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tranzo.app.ui.components.ClayCard
import com.tranzo.app.ui.theme.TranzoColors

/**
 * Claymorphism Security Screen — Baby blue bg, white toggle cards.
 */
@Composable
fun SecurityScreenClay(
    viewModel: SecurityViewModel = hiltViewModel(),
) {
    val uiState by viewModel.state.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(TranzoColors.ClayBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                "Security",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = TranzoColors.TextPrimary,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                "Protect your wallet",
                style = MaterialTheme.typography.bodyMedium,
                color = TranzoColors.TextSecondary,
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Authentication section
            Text(
                "Authentication",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = TranzoColors.TextPrimary,
                modifier = Modifier.padding(bottom = 8.dp),
            )

            ClayCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    SecurityToggleItem(
                        icon = Icons.Outlined.Fingerprint,
                        title = "Biometric Unlock",
                        description = "Use fingerprint or face",
                        isEnabled = uiState.isBiometricEnabled,
                        onToggle = { viewModel.setBiometricEnabled(!uiState.isBiometricEnabled) },
                        iconColor = TranzoColors.ClayBlue,
                    )
                    HorizontalDivider(color = TranzoColors.DividerGray, modifier = Modifier.padding(horizontal = 16.dp))
                    SecurityToggleItem(
                        icon = Icons.Outlined.Lock,
                        title = "Transaction Lock",
                        description = "Require auth for transactions",
                        isEnabled = uiState.isTransactionLockEnabled,
                        onToggle = { viewModel.setTransactionLockEnabled(!uiState.isTransactionLockEnabled) },
                        iconColor = TranzoColors.PrimaryPurple,
                    )
                    HorizontalDivider(color = TranzoColors.DividerGray, modifier = Modifier.padding(horizontal = 16.dp))
                    SecurityToggleItem(
                        icon = Icons.Outlined.Notifications,
                        title = "Auto Lock",
                        description = "Lock app when inactive",
                        isEnabled = uiState.isAutoLockEnabled,
                        onToggle = { viewModel.setAutoLockEnabled(!uiState.isAutoLockEnabled) },
                        iconColor = TranzoColors.ClayGreen,
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Wallet info
            Text(
                "Wallet Info",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = TranzoColors.TextPrimary,
                modifier = Modifier.padding(bottom = 8.dp),
            )

            ClayCard(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Account Type", style = MaterialTheme.typography.labelSmall, color = TranzoColors.TextTertiary)
                        Text("ERC-4337 Smart Account", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.SemiBold, color = TranzoColors.TextPrimary)
                    }
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Kernel Version", style = MaterialTheme.typography.labelSmall, color = TranzoColors.TextTertiary)
                        Text("ZeroDev v5", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.SemiBold, color = TranzoColors.TextPrimary)
                    }
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Network", style = MaterialTheme.typography.labelSmall, color = TranzoColors.TextTertiary)
                        Text("Base Sepolia", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.SemiBold, color = TranzoColors.ClayBlue)
                    }
                }
            }

            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

@Composable
private fun SecurityToggleItem(
    icon: ImageVector,
    title: String,
    description: String,
    isEnabled: Boolean,
    onToggle: () -> Unit,
    iconColor: Color = TranzoColors.ClayBlue,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onToggle)
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .shadow(
                    elevation = 6.dp,
                    shape = RoundedCornerShape(14.dp),
                    ambientColor = iconColor.copy(alpha = 0.3f),
                )
                .clip(RoundedCornerShape(14.dp))
                .background(iconColor),
            contentAlignment = Alignment.Center,
        ) {
            Icon(icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
        }

        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(title, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold, color = TranzoColors.TextPrimary)
            Text(description, style = MaterialTheme.typography.labelSmall, color = TranzoColors.TextTertiary)
        }

        Switch(
            checked = isEnabled,
            onCheckedChange = { onToggle() },
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = TranzoColors.ClayBlue,
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = TranzoColors.DividerGray,
            ),
        )
    }
}
