package com.tranzo.app.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tranzo.app.ui.components.TranzoButton
import com.tranzo.app.ui.home.HomeViewModel
import com.tranzo.app.ui.theme.TranzoColors

/**
 * Dedicated Profile Screen — displays user information and account details.
 *
 * Layout:
 * - Header with gradient background
 * - Profile avatar (initials)
 * - Display name + email
 * - Smart account address card
 * - Profile info sections (Account Details, Verification, etc.)
 * - Edit profile button
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onBack: () -> Unit = {},
    onEdit: () -> Unit = {},
) {
    val state by viewModel.state.collectAsState()
    val user = state.user

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(TranzoColors.BackgroundLight),
    ) {
        // ── Header ────────────────────────────────────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            TranzoColors.PrimaryBlue,
                            TranzoColors.AccentCyan,
                        ),
                    ),
                )
                .statusBarsPadding()
                .padding(horizontal = 24.dp, vertical = 16.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(onClick = onBack, modifier = Modifier.size(32.dp)) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                        contentDescription = "Back",
                        tint = TranzoColors.TextDarkPrimary,
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Profile",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = TranzoColors.TextDarkPrimary,
                )
                Spacer(modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.size(32.dp))
            }
        }

        // ── Profile Content ──────────────────────────────────────
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .offset(y = (-24).dp)
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .background(TranzoColors.BackgroundLight)
                .verticalScroll(rememberScrollState())
                .padding(top = 24.dp, bottom = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // ── Profile Avatar ───────────────────────────────────
            Surface(
                shape = CircleShape,
                color = TranzoColors.PrimaryBlue,
                modifier = Modifier.size(80.dp),
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Text(
                        text = getInitials(user?.displayName ?: "User"),
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        color = TranzoColors.TextDarkPrimary,
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ── Display Name ─────────────────────────────────────
            Text(
                text = user?.displayName ?: "User",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = TranzoColors.TextPrimary,
            )

            Spacer(modifier = Modifier.height(6.dp))

            // ── Email ────────────────────────────────────────────
            Text(
                text = user?.email ?: "email@example.com",
                style = MaterialTheme.typography.bodyMedium,
                color = TranzoColors.TextSecondary,
            )

            Spacer(modifier = Modifier.height(28.dp))

            // ── Smart Account Card ───────────────────────────────
            if (!user?.smartAccount.isNullOrEmpty()) {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = TranzoColors.SurfaceLight,
                    tonalElevation = 2.dp,
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(horizontal = 24.dp),
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.AccountBalanceWallet,
                                contentDescription = null,
                                tint = TranzoColors.PrimaryBlue,
                                modifier = Modifier.size(20.dp),
                            )
                            Text(
                                text = "Smart Account",
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.SemiBold,
                                color = TranzoColors.TextPrimary,
                            )
                        }

                        Text(
                            text = user!!.smartAccount!!,
                            style = MaterialTheme.typography.bodySmall,
                            color = TranzoColors.TextSecondary,
                            modifier = Modifier.fillMaxWidth(),
                        )

                        Text(
                            text = "Non-custodial • Base Sepolia",
                            style = MaterialTheme.typography.labelSmall,
                            color = TranzoColors.TextPrimary,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(28.dp))
            }

            // ── Account Details Section ──────────────────────────
            ProfileSection(
                title = "Account Details",
                items = listOf(
                    ProfileItem(
                        icon = Icons.Outlined.VerifiedUser,
                        label = "Account Status",
                        value = "Verified",
                        badge = "✓",
                    ),
                    ProfileItem(
                        icon = Icons.Outlined.Security,
                        label = "Security Level",
                        value = "High",
                    ),
                    ProfileItem(
                        icon = Icons.Outlined.Language,
                        label = "Network",
                        value = "Base Sepolia",
                    ),
                ),
            )

            Spacer(modifier = Modifier.height(20.dp))

            // ── Verification Section ─────────────────────────────
            ProfileSection(
                title = "Verification",
                items = listOf(
                    ProfileItem(
                        icon = Icons.Outlined.Email,
                        label = "Email",
                        value = "Verified",
                        badge = "✓",
                    ),
                    ProfileItem(
                        icon = Icons.Outlined.PhoneAndroid,
                        label = "Phone",
                        value = "Not added",
                    ),
                ),
            )

            Spacer(modifier = Modifier.height(28.dp))

            // ── Edit Profile Button ──────────────────────────────
            TranzoButton(
                text = "Edit Profile",
                onClick = onEdit,
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(horizontal = 24.dp),
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

/**
 * Reusable section for profile information groups.
 */
@Composable
private fun ProfileSection(
    title: String,
    items: List<ProfileItem>,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.SemiBold,
            color = TranzoColors.TextSecondary,
            modifier = Modifier.padding(start = 4.dp),
        )

        items.forEach { item ->
            ProfileItemRow(item)
        }
    }
}

/**
 * Individual profile item row.
 */
@Composable
private fun ProfileItemRow(item: ProfileItem) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = TranzoColors.SurfaceLight,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f),
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = null,
                    tint = TranzoColors.TextSecondary,
                    modifier = Modifier.size(20.dp),
                )
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = item.label,
                        style = MaterialTheme.typography.labelSmall,
                        color = TranzoColors.TextSecondary,
                    )
                    Text(
                        text = item.value,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        color = TranzoColors.TextPrimary,
                    )
                }
            }

            if (item.badge != null) {
                Text(
                    text = item.badge!!,
                    style = MaterialTheme.typography.labelSmall,
                    color = TranzoColors.TextPrimary,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}

/**
 * Data class for profile items.
 */
data class ProfileItem(
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val label: String,
    val value: String,
    val badge: String? = null,
)

/**
 * Helper to get initials from a name.
 */
private fun getInitials(name: String): String {
    val parts = name.trim().split(" ")
    return when {
        parts.size >= 2 -> "${parts[0].first()}${parts[1].first()}"
        parts.size == 1 -> parts[0].take(2)
        else -> "U"
    }.uppercase()
}
