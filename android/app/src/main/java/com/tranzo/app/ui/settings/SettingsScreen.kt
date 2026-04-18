package com.tranzo.app.ui.settings

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ExitToApp
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
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
 * Settings screen — modern, polished design.
 *
 * Layout:
 * - Gradient header with user profile card
 * - Settings sections (Account, Security, Activity, Network)
 * - Legal links row
 * - Logout button with confirmation dialog
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onLogout: () -> Unit = {},
    onTransactionHistory: () -> Unit = {},
    onProfile: () -> Unit = {},
    onWallet: () -> Unit = {},
    onSecurity: () -> Unit = {},
    onHelp: () -> Unit = {},
    onContact: () -> Unit = {},
) {
    val state by viewModel.state.collectAsState()
    var showLogoutDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    fun openUrl(url: String) {
        context.startActivity(
            Intent(Intent.ACTION_VIEW, Uri.parse(url))
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(TranzoColors.Background),
    ) {
        // ── Premium Gradient Header ──────────────────────────────
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
                text = "Settings",
                style = MaterialTheme.typography.headlineLarge,
                color = TranzoColors.TextOnDark,
                fontWeight = FontWeight.Bold,
            )
        }

        // ── Scrollable Content ───────────────────────────────────
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .offset(y = (-16).dp)
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .background(TranzoColors.Background)
                .verticalScroll(rememberScrollState())
                .padding(top = 24.dp, bottom = 32.dp),
        ) {
            // ── User Profile Card ────────────────────────────────
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = TranzoColors.CardSurface,
                tonalElevation = 2.dp,
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 24.dp),
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    // Avatar with initials
                    Surface(
                        shape = CircleShape,
                        color = TranzoColors.Navy,
                        modifier = Modifier.size(64.dp),
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize(),
                        ) {
                            Text(
                                text = getInitials(state.user?.displayName ?: "User"),
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                color = TranzoColors.TextOnDark,
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = state.user?.displayName ?: "User",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold,
                    )

                    Text(
                        text = state.user?.email ?: "",
                        style = MaterialTheme.typography.bodySmall,
                        color = TranzoColors.TextSecondary,
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    if (!state.user?.smartAccount.isNullOrEmpty()) {
                        Text(
                            text = "${state.user!!.smartAccount!!.take(8)}...${state.user!!.smartAccount!!.takeLast(6)}",
                            style = MaterialTheme.typography.labelSmall,
                            color = TranzoColors.TextTertiary,
                        )
                    }
                }
            }

            // ── Account Section ──────────────────────────────────
            SettingsSectionTitle("Account")

            SettingsAction(
                icon = Icons.Outlined.Person,
                label = "My Profile",
                subtitle = "View and manage account",
                onClick = onProfile,
            )

            SettingsAction(
                icon = Icons.Outlined.AccountBalanceWallet,
                label = "Wallet",
                subtitle = state.user?.smartAccount?.let {
                    "${it.take(6)}...${it.takeLast(4)}"
                } ?: "Address, backup, export",
                onClick = onWallet,
            )

            Spacer(modifier = Modifier.height(20.dp))

            // ── Security & Privacy Section ───────────────────────
            SettingsSectionTitle("Security & Privacy")

            SettingsAction(
                icon = Icons.Outlined.Shield,
                label = "Security",
                subtitle = "PIN, biometric, recovery",
                onClick = onSecurity,
            )

            SettingsAction(
                icon = Icons.Outlined.Lock,
                label = "Two-Factor Auth",
                subtitle = "Enable 2FA for extra security",
                onClick = {},
            )

            Spacer(modifier = Modifier.height(20.dp))

            // ── Activity Section ─────────────────────────────────
            SettingsSectionTitle("Activity")

            SettingsAction(
                icon = Icons.Outlined.Receipt,
                label = "Transaction History",
                subtitle = "View all on-chain activity",
                onClick = onTransactionHistory,
            )

            Spacer(modifier = Modifier.height(20.dp))

            // ── Network Section ──────────────────────────────────
            SettingsSectionTitle("Network")

            SettingsInfo(
                icon = Icons.Outlined.Language,
                label = "Current Network",
                value = "Base Sepolia (Testnet)",
            )

            Spacer(modifier = Modifier.height(20.dp))

            // ── Help & Support Section ───────────────────────────
            SettingsSectionTitle("Help & Support")

            SettingsAction(
                icon = Icons.Outlined.HelpOutline,
                label = "Help & Support",
                subtitle = "Contact us anytime",
                onClick = onContact,
            )

            SettingsAction(
                icon = Icons.Outlined.Info,
                label = "About",
                subtitle = "Version 1.0.0",
                onClick = {},
            )

            Spacer(modifier = Modifier.height(24.dp))

            // ── Legal Section ────────────────────────────────────
            Text(
                text = "Legal",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.SemiBold,
                color = TranzoColors.TextSecondary,
                modifier = Modifier.padding(horizontal = 24.dp),
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                LegalButton(
                    text = "Privacy",
                    onClick = { openUrl("https://www.tranzo.money/privacy") },
                    modifier = Modifier.weight(1f),
                )
                LegalButton(
                    text = "Terms",
                    onClick = { openUrl("https://www.tranzo.money/terms") },
                    modifier = Modifier.weight(1f),
                )
                LegalButton(
                    text = "Manifesto",
                    onClick = { openUrl("https://www.tranzo.money/manifesto.html") },
                    modifier = Modifier.weight(1f),
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ── Logout Button ────────────────────────────────────
            Button(
                onClick = { showLogoutDialog = true },
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(52.dp)
                    .align(Alignment.CenterHorizontally),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = TranzoColors.Error,
                ),
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ExitToApp,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Logout",
                    style = MaterialTheme.typography.labelLarge,
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
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
private fun SettingsSectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.labelLarge,
        fontWeight = FontWeight.SemiBold,
        color = TranzoColors.TextSecondary,
        modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
    )
}

@Composable
private fun SettingsAction(
    icon: ImageVector,
    label: String,
    subtitle: String? = null,
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
                contentDescription = null,
                tint = TranzoColors.TextSecondary,
                modifier = Modifier.size(24.dp),
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                )
                if (subtitle != null) {
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = TranzoColors.TextTertiary,
                    )
                }
            }

            Icon(
                imageVector = Icons.Outlined.ChevronRight,
                contentDescription = null,
                tint = TranzoColors.TextTertiary,
                modifier = Modifier.size(20.dp),
            )
        }
    }
}

@Composable
private fun SettingsInfo(
    icon: ImageVector,
    label: String,
    value: String,
) {
    Surface(
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
                contentDescription = null,
                tint = TranzoColors.TextSecondary,
                modifier = Modifier.size(24.dp),
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodySmall,
                    color = TranzoColors.TextTertiary,
                )
                Text(
                    text = value,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                )
            }
        }
    }
}

@Composable
private fun LegalButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.height(36.dp),
        shape = RoundedCornerShape(12.dp),
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
        )
    }
}

private fun getInitials(name: String): String {
    val parts = name.trim().split(" ")
    return when {
        parts.size >= 2 -> "${parts[0].first()}${parts[1].first()}"
        parts.size == 1 -> parts[0].take(2)
        else -> "U"
    }.uppercase()
}
