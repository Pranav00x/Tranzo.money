package com.tranzo.app.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ExitToApp
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.tranzo.app.ui.home.HomeViewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tranzo.app.ui.theme.TranzoColors

/**
 * Settings screen — clean, crypto-native.
 *
 * Only shows options relevant to a self-custody wallet:
 * - Wallet (address, export, backup)
 * - Security & Recovery (PIN, biometric, recovery phrase)
 * - Transaction History
 * - Network (chain selection)
 * - Help & Support
 * - Logout
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onLogout: () -> Unit = {},
    onTransactionHistory: () -> Unit = {},
    onWallet: () -> Unit = {},
    onSecurity: () -> Unit = {},
    onHelp: () -> Unit = {},
) {
    val state by viewModel.state.collectAsState()
    var showLogoutDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(TranzoColors.Background),
    ) {
        // ── Header ──────────────────────────────────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            TranzoColors.Navy,
                            TranzoColors.DarkTeal,
                        ),
                    ),
                )
                .statusBarsPadding()
                .padding(horizontal = 24.dp, vertical = 24.dp),
        ) {
                Text(
                    text = state.user?.displayName ?: "Settings",
                    style = MaterialTheme.typography.headlineMedium,
                    color = TranzoColors.TextOnDark,
                    fontWeight = FontWeight.Bold,
                )
                if (state.user != null) {
                    Text(
                        text = state.user?.email ?: "",
                        style = MaterialTheme.typography.bodySmall,
                        color = TranzoColors.TextOnDarkMuted,
                    )
                }
        }

        // ── Menu Items ──────────────────────────────────────────
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .offset(y = (-16).dp)
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .background(TranzoColors.Background)
                .verticalScroll(rememberScrollState())
                .padding(top = 8.dp),
        ) {
            SettingsMenuItem(
                icon = Icons.Outlined.AccountBalanceWallet,
                label = "Wallet",
                subtitle = state.user?.smartAccount?.let { 
                    "${it.take(6)}...${it.takeLast(4)}" 
                } ?: "Address, backup, export keys",
                onClick = onWallet,
            )

            SettingsMenuItem(
                icon = Icons.Outlined.Shield,
                label = "Security",
                subtitle = "PIN, biometric, recovery",
                onClick = onSecurity,
            )

            SettingsMenuItem(
                icon = Icons.Outlined.Receipt,
                label = "Transaction History",
                subtitle = "View all on-chain activity",
                onClick = onTransactionHistory,
            )

            SettingsMenuItem(
                icon = Icons.Outlined.Language,
                label = "Network",
                subtitle = "Base Sepolia (Testnet)",
            )

            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
                color = TranzoColors.DividerGray,
            )

            SettingsMenuItem(
                icon = Icons.Outlined.HelpOutline,
                label = "Help & Support",
                subtitle = "Contact us",
                onClick = onHelp,
            )

            SettingsMenuItem(
                icon = Icons.Outlined.Info,
                label = "About Tranzo",
                subtitle = "Version 1.0.0",
            )

            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
                color = TranzoColors.DividerGray,
            )

            // Logout
            SettingsMenuItem(
                icon = Icons.AutoMirrored.Outlined.ExitToApp,
                label = "Logout",
                showChevron = false,
                onClick = { showLogoutDialog = true },
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 100.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                TextButton(onClick = {}) {
                    Text(
                        text = "Terms & Policies",
                        style = MaterialTheme.typography.bodySmall,
                        color = TranzoColors.TextSecondary,
                    )
                }
                Text(
                    text = "Build V1.0.0",
                    style = MaterialTheme.typography.bodySmall,
                    color = TranzoColors.TextTertiary,
                    modifier = Modifier.padding(top = 12.dp),
                )
            }
        }
    }

    // ── Logout Confirmation Dialog ──────────────────────────────
    if (showLogoutDialog) {
        ModalBottomSheet(
            onDismissRequest = { showLogoutDialog = false },
            containerColor = TranzoColors.CardSurface,
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 32.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Are you sure you want to\nlogout?",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                    )
                    IconButton(onClick = { showLogoutDialog = false }) {
                        Icon(
                            imageVector = Icons.Outlined.Close,
                            contentDescription = "Close",
                            tint = TranzoColors.TextSecondary,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Your wallet is stored locally. Make sure you have backed up your recovery phrase before logging out.",
                    style = MaterialTheme.typography.bodySmall,
                    color = TranzoColors.TextSecondary,
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    OutlinedButton(
                        onClick = {
                            showLogoutDialog = false
                            onLogout()
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp),
                        shape = RoundedCornerShape(28.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = TranzoColors.Error,
                        ),
                    ) {
                        Text(
                            text = "Logout",
                            style = MaterialTheme.typography.labelLarge,
                        )
                    }

                    Button(
                        onClick = { showLogoutDialog = false },
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp),
                        shape = RoundedCornerShape(28.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = TranzoColors.PrimaryBlack,
                        ),
                    ) {
                        Text(
                            text = "Cancel",
                            style = MaterialTheme.typography.labelLarge,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SettingsMenuItem(
    icon: ImageVector,
    label: String,
    subtitle: String? = null,
    showChevron: Boolean = true,
    onClick: () -> Unit = {},
) {
    Surface(
        onClick = onClick,
        color = TranzoColors.Background,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = TranzoColors.TextSecondary,
                modifier = Modifier.size(24.dp),
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodyLarge,
                )
                if (subtitle != null) {
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = TranzoColors.TextTertiary,
                    )
                }
            }

            if (showChevron) {
                Icon(
                    imageVector = Icons.Outlined.ChevronRight,
                    contentDescription = null,
                    tint = TranzoColors.TextTertiary,
                    modifier = Modifier.size(20.dp),
                )
            }
        }
    }
}
