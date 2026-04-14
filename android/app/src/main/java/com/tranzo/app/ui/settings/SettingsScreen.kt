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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tranzo.app.ui.components.StatusBadge
import com.tranzo.app.ui.theme.TranzoColors

/**
 * Profile/Settings screen — CheQ-style.
 *
 * Dark gradient header with avatar + name,
 * white section with menu items (icons + labels + chevrons),
 * logout confirmation dialog.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    userName: String = "Pranav Jha",
    userPhone: String = "+91 7377286823",
    userEmail: String = "pranav@tranzo.money",
    onLogout: () -> Unit = {},
    onPersonalDetails: () -> Unit = {},
    onTransactionHistory: () -> Unit = {},
    onWallet: () -> Unit = {},
    onSecurity: () -> Unit = {},
    onReferEarn: () -> Unit = {},
    onHelp: () -> Unit = {},
) {
    var showLogoutDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(TranzoColors.Background),
    ) {
        // ── Gradient Header with Avatar ─────────────────────────
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
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Avatar circle with initials
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(TranzoColors.PrimaryGreen),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = userName.split(" ")
                            .mapNotNull { it.firstOrNull()?.uppercase() }
                            .take(2)
                            .joinToString(""),
                        style = MaterialTheme.typography.headlineSmall,
                        color = TranzoColors.TextOnGreen,
                        fontWeight = FontWeight.Bold,
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = userName,
                        style = MaterialTheme.typography.headlineSmall,
                        color = TranzoColors.TextOnDark,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = userPhone,
                        style = MaterialTheme.typography.bodyMedium,
                        color = TranzoColors.TextOnDarkMuted,
                    )
                }
            }
        }

        // ── White Content with Rounded Top ──────────────────────
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
            ProfileMenuItem(
                icon = Icons.Outlined.Person,
                label = "Personal Details",
                badge = "50% Complete",
                onClick = onPersonalDetails,
            )

            ProfileMenuItem(
                icon = Icons.Outlined.Receipt,
                label = "Transaction History",
                onClick = onTransactionHistory,
            )

            ProfileMenuItem(
                icon = Icons.Outlined.AccountBalanceWallet,
                label = "Wallet",
                onClick = onWallet,
            )

            ProfileMenuItem(
                icon = Icons.Outlined.Shield,
                label = "Security & Recovery",
                onClick = onSecurity,
            )

            ProfileMenuItem(
                icon = Icons.Outlined.CreditCard,
                label = "Manage Card",
            )

            ProfileMenuItem(
                icon = Icons.Outlined.Share,
                label = "Refer & Earn",
                badge = "New",
                onClick = onReferEarn,
            )

            ProfileMenuItem(
                icon = Icons.Outlined.HelpOutline,
                label = "Help & Support",
                onClick = onHelp,
            )

            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
                color = TranzoColors.DividerGray,
            )

            // Logout
            ProfileMenuItem(
                icon = Icons.AutoMirrored.Outlined.ExitToApp,
                label = "Logout",
                showChevron = false,
                onClick = { showLogoutDialog = true },
            )

            // Terms + Version
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
                        color = TranzoColors.PrimaryGreen,
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

    // ── Logout Confirmation Dialog (CheQ-style bottom sheet) ────
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

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    // Confirm — outlined / light
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
                            contentColor = TranzoColors.PrimaryGreen,
                        ),
                    ) {
                        Text(
                            text = "Confirm",
                            style = MaterialTheme.typography.labelLarge,
                        )
                    }

                    // Cancel — filled green
                    Button(
                        onClick = { showLogoutDialog = false },
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp),
                        shape = RoundedCornerShape(28.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = TranzoColors.PrimaryGreen,
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
private fun ProfileMenuItem(
    icon: ImageVector,
    label: String,
    badge: String? = null,
    isBadgeError: Boolean = false,
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

            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f),
            )

            if (badge != null) {
                StatusBadge(
                    text = badge,
                    isError = isBadgeError,
                )
                Spacer(modifier = Modifier.width(8.dp))
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
