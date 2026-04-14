package com.tranzo.app.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ExitToApp
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
 * white section with menu items (icons + labels + chevrons).
 */
@Composable
fun SettingsScreen(
    userName: String = "Pranav Jha",
    userEmail: String = "pranav@tranzo.money",
    onLogout: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(TranzoColors.Background),
    ) {
        // ── Gradient Header with Avatar ──────────────────────────
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
                        text = userEmail,
                        style = MaterialTheme.typography.bodyMedium,
                        color = TranzoColors.TextOnDarkMuted,
                    )
                }
            }
        }

        // ── White Content with Rounded Top ───────────────────────
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = (-16).dp)
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .background(TranzoColors.Background)
                .padding(top = 8.dp),
        ) {
            ProfileMenuItem(
                icon = Icons.Outlined.Person,
                label = "Personal Details",
                badge = "50% Complete",
            )

            ProfileMenuItem(
                icon = Icons.Outlined.Receipt,
                label = "Transaction History",
            )

            ProfileMenuItem(
                icon = Icons.Outlined.AccountBalanceWallet,
                label = "Wallet",
            )

            ProfileMenuItem(
                icon = Icons.Outlined.Shield,
                label = "Security & Recovery",
            )

            ProfileMenuItem(
                icon = Icons.Outlined.WaterDrop,
                label = "Dripper Streams",
            )

            ProfileMenuItem(
                icon = Icons.Outlined.Share,
                label = "Refer & Earn",
                badge = "New",
            )

            ProfileMenuItem(
                icon = Icons.Outlined.HelpOutline,
                label = "Help & Support",
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
                onClick = onLogout,
            )

            // Terms + Version
            Spacer(modifier = Modifier.weight(1f))

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
