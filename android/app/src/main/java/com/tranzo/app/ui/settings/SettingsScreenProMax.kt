package com.tranzo.app.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tranzo.app.ui.home.HomeViewModel
import com.tranzo.app.util.ThemeManager

/**
 * CheQ-inspired Profile/Settings screen — monochrome, clean list layout.
 * Pulls user data from HomeViewModel.
 */
@Composable
fun SettingsScreenProMax(
    homeViewModel: HomeViewModel = hiltViewModel(),
    onLogout: () -> Unit = {},
    onSecurity: () -> Unit = {},
    onTheme: () -> Unit = {},
) {
    val homeState by homeViewModel.state.collectAsState()
    val user = homeState.user

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // ── Profile header (dark) ────────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF1A1A1A))
                    .statusBarsPadding()
                    .padding(24.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Avatar
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF444444)),
                        contentAlignment = Alignment.Center
                    ) {
                        val initials = user?.let {
                            val f = it.firstName?.firstOrNull() ?: it.email?.firstOrNull() ?: 'T'
                            val l = it.lastName?.firstOrNull()
                            if (l != null) "${f.uppercaseChar()}${l.uppercaseChar()}" else "${f.uppercaseChar()}"
                        } ?: "T"
                        Text(
                            initials,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    }

                    Column {
                        val name = user?.let {
                            val full = listOfNotNull(it.firstName, it.lastName).joinToString(" ").trim()
                            full.ifEmpty { it.displayName ?: it.email ?: "User" }
                        } ?: "User"
                        Text(
                            name,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            user?.email ?: "Not connected",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF999999)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ── Menu sections ────────────────────────────────────

            // Account section
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(16.dp),
                color = Color.White
            ) {
                Column {
                    SettingItemRow(
                        icon = Icons.Outlined.AccountCircle,
                        label = "Wallet Details",
                        trailingText = user?.smartAccount?.let {
                            "${it.take(6)}...${it.takeLast(4)}"
                        },
                        onClick = { }
                    )
                    Divider16()
                    SettingItemRow(
                        icon = Icons.Outlined.History,
                        label = "Transaction History",
                        onClick = { }
                    )
                    Divider16()
                    SettingItemRow(
                        icon = Icons.Outlined.CreditCard,
                        label = "Manage Card",
                        onClick = { }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Security section
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(16.dp),
                color = Color.White
            ) {
                Column {
                    SettingItemRow(
                        icon = Icons.Outlined.Lock,
                        label = "Security",
                        trailingText = "Biometric, PIN",
                        onClick = onSecurity
                    )
                    Divider16()
                    SettingItemRow(
                        icon = Icons.Outlined.Shield,
                        label = "Privacy Policy",
                        onClick = { }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Support section
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(16.dp),
                color = Color.White
            ) {
                Column {
                    SettingItemRow(
                        icon = Icons.Outlined.HelpOutline,
                        label = "Help & Support",
                        onClick = { }
                    )
                    Divider16()
                    SettingItemRow(
                        icon = Icons.Outlined.Info,
                        label = "About Tranzo",
                        trailingText = "v1.0.1",
                        onClick = { }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Logout button
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clickable(onClick = onLogout),
                shape = RoundedCornerShape(16.dp),
                color = Color.White
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Icon(
                        Icons.Outlined.Logout,
                        contentDescription = "Logout",
                        tint = Color(0xFFCC0000),
                        modifier = Modifier.size(22.dp)
                    )
                    Text(
                        "Logout",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFFCC0000)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Footer
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 100.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Terms & Policies",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color(0xFF999999)
                )
                Text(
                    "Build v1.0.1",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color(0xFFCCCCCC)
                )
            }
        }
    }
}

// ── Reusable row composable ──────────────────────────────────────
@Composable
private fun SettingItemRow(
    icon: ImageVector,
    label: String,
    trailingText: String? = null,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFFF5F5F5)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color(0xFF1A1A1A),
                    modifier = Modifier.size(20.dp)
                )
            }

            Text(
                label,
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF1A1A1A),
                fontWeight = FontWeight.Medium
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (trailingText != null) {
                Text(
                    trailingText,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color(0xFF999999)
                )
            }
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.ArrowForward,
                contentDescription = null,
                tint = Color(0xFFCCCCCC),
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Composable
private fun Divider16() {
    HorizontalDivider(
        thickness = 0.5.dp,
        color = Color(0xFFF0F0F0),
        modifier = Modifier.padding(horizontal = 16.dp)
    )
}
