package com.tranzo.app.ui.security

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tranzo.app.ui.theme.TranzoColors
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Security & Recovery screen.
 *
 * Layout:
 * - Back + title
 * - Biometric toggle
 * - Recovery setup section
 * - Session management
 * - Advanced security options
 */
@Composable
fun SecurityScreen(
    viewModel: SecurityViewModel = hiltViewModel(),
    onBack: () -> Unit = {},
) {
    val state by viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(TranzoColors.Background)
            .systemBarsPadding(),
    ) {
        // ── Top Bar ──────────────────────────────────────────────
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                    contentDescription = "Back",
                )
            }
            Text(
                text = "Security",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.weight(1f),
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // ── Security Score Card ──────────────────────────────
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = TranzoColors.BackgroundLight,
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .background(TranzoColors.TextPrimary),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Shield,
                            contentDescription = null,
                            tint = TranzoColors.White,
                            modifier = Modifier.size(28.dp),
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = "Security Score: 85/100",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.SemiBold,
                        )
                        Text(
                            text = "Set up recovery to reach 100",
                            style = MaterialTheme.typography.bodySmall,
                            color = TranzoColors.TextSecondary,
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ── Authentication ───────────────────────────────────
            Text(
                text = "Authentication",
                style = MaterialTheme.typography.headlineSmall,
            )
            Spacer(modifier = Modifier.height(12.dp))

            SecurityToggleRow(
                icon = Icons.Outlined.Fingerprint,
                title = "Biometric Login",
                subtitle = "Use fingerprint or face to unlock",
                checked = state.isBiometricEnabled,
                onCheckedChange = { viewModel.setBiometricEnabled(it) },
            )

            SecurityToggleRow(
                icon = Icons.Outlined.Lock,
                title = "Transaction Lock",
                subtitle = "Require biometric for every transfer",
                checked = state.isTransactionLockEnabled,
                onCheckedChange = { viewModel.setTransactionLockEnabled(it) },
            )

            SecurityToggleRow(
                icon = Icons.Outlined.Timer,
                title = "Auto-Lock",
                subtitle = "Lock after 5 minutes of inactivity",
                checked = state.isAutoLockEnabled,
                onCheckedChange = { viewModel.setAutoLockEnabled(it) },
            )

            Spacer(modifier = Modifier.height(24.dp))

            // ── Recovery ─────────────────────────────────────────
            Text(
                text = "Recovery",
                style = MaterialTheme.typography.headlineSmall,
            )
            Spacer(modifier = Modifier.height(12.dp))

            SecurityActionRow(
                icon = Icons.Outlined.Group,
                title = "Social Recovery",
                subtitle = "Add guardians to recover your account",
                badge = "Not Set Up",
                badgeIsError = true,
                onClick = {},
            )

            SecurityActionRow(
                icon = Icons.Outlined.Key,
                title = "Backup Keys",
                subtitle = "Export encrypted backup of your keys",
                onClick = {},
            )

            Spacer(modifier = Modifier.height(24.dp))

            // ── Sessions ─────────────────────────────────────────
            Text(
                text = "Sessions",
                style = MaterialTheme.typography.headlineSmall,
            )
            Spacer(modifier = Modifier.height(12.dp))

            SecurityActionRow(
                icon = Icons.Outlined.Devices,
                title = "Active Sessions",
                subtitle = "1 active session",
                onClick = {},
            )

            SecurityActionRow(
                icon = Icons.Outlined.DeleteForever,
                title = "Revoke All Sessions",
                subtitle = "Log out from all devices",
                onClick = {},
            )
        }
    }
}

@Composable
private fun SecurityToggleRow(
    icon: ImageVector,
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    Surface(
        color = TranzoColors.Background,
        modifier = Modifier.padding(vertical = 4.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
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
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = TranzoColors.TextSecondary,
                )
            }
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(
                    checkedTrackColor = TranzoColors.TextPrimary,
                    checkedThumbColor = TranzoColors.White,
                ),
            )
        }
    }
}

@Composable
private fun SecurityActionRow(
    icon: ImageVector,
    title: String,
    subtitle: String,
    badge: String? = null,
    badgeIsError: Boolean = false,
    onClick: () -> Unit,
) {
    Surface(
        onClick = onClick,
        color = TranzoColors.Background,
        modifier = Modifier.padding(vertical = 4.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
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
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = TranzoColors.TextSecondary,
                )
            }
            if (badge != null) {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = if (badgeIsError) TranzoColors.ErrorLight else TranzoColors.BadgeGreenBg,
                ) {
                    Text(
                        text = badge,
                        style = MaterialTheme.typography.labelSmall,
                        color = if (badgeIsError) TranzoColors.Error else TranzoColors.TextPrimary,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    )
                }
            } else {
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
