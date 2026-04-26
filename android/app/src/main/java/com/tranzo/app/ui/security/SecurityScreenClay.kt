package com.tranzo.app.ui.security

import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tranzo.app.ui.components.*
import com.tranzo.app.ui.theme.TranzoColors

@Composable
fun SecurityScreenClay(viewModel: SecurityViewModel = hiltViewModel()) {
    val uiState by viewModel.state.collectAsState()

    Box(Modifier.fillMaxSize().background(TranzoColors.ClayBackground)) {
        Canvas(Modifier.fillMaxSize()) {
            drawCircle(TranzoColors.ClayGreen.copy(alpha = 0.06f), 230f, Offset(size.width * 0.85f, size.height * 0.12f))
            drawCircle(TranzoColors.ClayPurple.copy(alpha = 0.04f), 180f, Offset(size.width * 0.15f, size.height * 0.5f))
        }

        Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal = 24.dp)) {
            Spacer(Modifier.height(24.dp))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Column {
                    Text("Security", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.ExtraBold, color = TranzoColors.TextPrimary)
                    Spacer(Modifier.height(4.dp))
                    Text("Protect your wallet", style = MaterialTheme.typography.bodyMedium, color = TranzoColors.TextSecondary)
                }
                ClayIconPill(color = TranzoColors.ClayGreen, size = 44.dp) {
                    Icon(Icons.Outlined.Shield, null, tint = Color.White, modifier = Modifier.size(22.dp))
                }
            }

            Spacer(Modifier.height(24.dp))

            Text("AUTHENTICATION", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = TranzoColors.TextTertiary, letterSpacing = 1.5.sp,
                modifier = Modifier.padding(bottom = 8.dp))

            ClayCard(Modifier.fillMaxWidth(), cornerRadius = 22.dp) {
                Column(Modifier.fillMaxWidth()) {
                    SecurityToggleItem(Icons.Outlined.Fingerprint, "Biometric Unlock", "Use fingerprint or face",
                        uiState.isBiometricEnabled, { viewModel.setBiometricEnabled(!uiState.isBiometricEnabled) }, TranzoColors.ClayBlue)
                    HorizontalDivider(color = TranzoColors.DividerGray, modifier = Modifier.padding(horizontal = 16.dp))
                    SecurityToggleItem(Icons.Outlined.Lock, "Transaction Lock", "Require auth for transactions",
                        uiState.isTransactionLockEnabled, { viewModel.setTransactionLockEnabled(!uiState.isTransactionLockEnabled) }, TranzoColors.ClayPurple)
                    HorizontalDivider(color = TranzoColors.DividerGray, modifier = Modifier.padding(horizontal = 16.dp))
                    SecurityToggleItem(Icons.Outlined.Notifications, "Auto Lock", "Lock app when inactive",
                        uiState.isAutoLockEnabled, { viewModel.setAutoLockEnabled(!uiState.isAutoLockEnabled) }, TranzoColors.ClayAmber)
                }
            }

            Spacer(Modifier.height(24.dp))

            Text("WALLET INFO", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = TranzoColors.TextTertiary, letterSpacing = 1.5.sp,
                modifier = Modifier.padding(bottom = 8.dp))

            ClayCard(Modifier.fillMaxWidth(), cornerRadius = 22.dp) {
                Column(Modifier.fillMaxWidth().padding(20.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    WalletInfoRow("Account Type", "ERC-4337 Smart Account", TranzoColors.ClayBlue)
                    WalletInfoRow("Kernel Version", "ZeroDev v5", TranzoColors.ClayPurple)
                    WalletInfoRow("Network", "Base Sepolia", TranzoColors.ClayGreen)
                }
            }

            Spacer(Modifier.height(100.dp))
        }
    }
}

@Composable
private fun SecurityToggleItem(icon: ImageVector, title: String, description: String, isEnabled: Boolean, onToggle: () -> Unit, iconColor: Color) {
    Row(Modifier.fillMaxWidth().clickable(onClick = onToggle).padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp), verticalAlignment = Alignment.CenterVertically) {
        ClayIconPill(color = iconColor, size = 44.dp, cornerRadius = 15.dp) {
            Icon(icon, null, tint = Color.White, modifier = Modifier.size(22.dp))
        }
        Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(title, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = TranzoColors.TextPrimary)
            Text(description, style = MaterialTheme.typography.labelSmall, color = TranzoColors.TextTertiary)
        }
        Switch(checked = isEnabled, onCheckedChange = { onToggle() },
            colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = TranzoColors.ClayBlue,
                uncheckedThumbColor = Color.White, uncheckedTrackColor = TranzoColors.DividerGray))
    }
}

@Composable
private fun WalletInfoRow(label: String, value: String, accentColor: Color) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(8.dp).clip(RoundedCornerShape(4.dp)).background(accentColor.copy(alpha = 0.5f)))
            Text(label, style = MaterialTheme.typography.labelSmall, color = TranzoColors.TextTertiary, fontWeight = FontWeight.Medium)
        }
        Text(value, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = TranzoColors.TextPrimary)
    }
}
