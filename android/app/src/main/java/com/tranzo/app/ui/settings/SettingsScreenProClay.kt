package com.tranzo.app.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tranzo.app.ui.components.ClayCard
import com.tranzo.app.ui.theme.TranzoColors
import com.tranzo.app.util.ThemeManager
import com.tranzo.app.util.ThemeViewModel

/**
 * Claymorphism Settings — Baby blue bg, white section cards, solid colored icons.
 */
@Composable
fun SettingsScreenProClay(
    themeViewModel: ThemeViewModel = hiltViewModel(),
    onLogout: () -> Unit = {},
    onSecurity: () -> Unit = {},
    onTheme: () -> Unit = {},
) {
    val currentThemeId by themeViewModel.currentThemeId.collectAsState()
    var showThemeSelector by remember { mutableStateOf(false) }
    val availableThemes = themeViewModel.getAvailableThemes()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(TranzoColors.ClayBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // Header
            Text(
                "Settings",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = TranzoColors.TextPrimary,
                modifier = Modifier.padding(horizontal = 24.dp),
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Profile card
            ClayCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .shadow(
                                elevation = 10.dp,
                                shape = RoundedCornerShape(18.dp),
                                ambientColor = TranzoColors.ClayBlue.copy(alpha = 0.3f),
                            )
                            .clip(RoundedCornerShape(18.dp))
                            .background(TranzoColors.ClayBlue),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            "P",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                        )
                    }

                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        Text(
                            "Pranav",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = TranzoColors.TextPrimary,
                        )
                        Text(
                            "pranav@tranzo.app",
                            style = MaterialTheme.typography.bodySmall,
                            color = TranzoColors.TextSecondary,
                        )
                    }

                    Text(
                        "\u2192",
                        style = MaterialTheme.typography.titleMedium,
                        color = TranzoColors.TextTertiary,
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Appearance
            SettingsSectionClay(title = "Appearance") {
                SettingItemClay(
                    icon = Icons.Outlined.Palette,
                    label = "Theme",
                    description = "Appearance and colors",
                    onClick = { showThemeSelector = !showThemeSelector },
                    iconColor = TranzoColors.ClayBlue,
                )

                if (showThemeSelector) {
                    Spacer(modifier = Modifier.height(4.dp))
                    ThemeSelectorClay(
                        currentThemeId = currentThemeId,
                        availableThemes = availableThemes,
                        onThemeSelected = { themeViewModel.setTheme(it) },
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Security & Account
            SettingsSectionClay(title = "Security & Account") {
                SettingItemClay(
                    icon = Icons.Outlined.Lock,
                    label = "Security",
                    description = "Biometric, PIN, and auth",
                    onClick = onSecurity,
                    iconColor = TranzoColors.ClayGreen,
                )
                SettingItemClay(
                    icon = Icons.Outlined.PrivacyTip,
                    label = "Privacy",
                    description = "Data and privacy settings",
                    onClick = {},
                    iconColor = TranzoColors.PrimaryPurple,
                )
                SettingItemClay(
                    icon = Icons.Outlined.Info,
                    label = "About Tranzo",
                    description = "Version 1.0.0",
                    onClick = {},
                    iconColor = TranzoColors.PrimaryOrange,
                    showDivider = false,
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Logout
            Button(
                onClick = onLogout,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .height(52.dp)
                    .shadow(
                        elevation = 10.dp,
                        shape = RoundedCornerShape(24.dp),
                        ambientColor = TranzoColors.Error.copy(alpha = 0.25f),
                    ),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = TranzoColors.Error,
                    contentColor = Color.White,
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
            ) {
                Text(
                    "Logout",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                )
            }

            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

@Composable
private fun SettingsSectionClay(
    title: String,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column {
        Text(
            title,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
            color = TranzoColors.TextPrimary,
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
        )
        ClayCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                content = content,
            )
        }
    }
}

@Composable
private fun ColumnScope.SettingItemClay(
    icon: ImageVector,
    label: String,
    description: String,
    onClick: () -> Unit,
    iconColor: Color = TranzoColors.ClayBlue,
    gradientStart: Color = TranzoColors.ClayBlue,
    gradientEnd: Color = TranzoColors.ClayBlue,
    showDivider: Boolean = true,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
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
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(20.dp),
            )
        }

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            Text(
                label,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = TranzoColors.TextPrimary,
            )
            Text(
                description,
                style = MaterialTheme.typography.labelSmall,
                color = TranzoColors.TextTertiary,
            )
        }

        Icon(
            Icons.AutoMirrored.Outlined.ArrowForward,
            contentDescription = null,
            tint = TranzoColors.TextTertiary,
            modifier = Modifier.size(18.dp),
        )
    }

    if (showDivider) {
        HorizontalDivider(
            color = TranzoColors.DividerGray,
            modifier = Modifier.padding(horizontal = 16.dp),
        )
    }
}

@Composable
private fun ThemeSelectorClay(
    currentThemeId: String,
    availableThemes: List<ThemeManager.ThemeOption>,
    onThemeSelected: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        availableThemes.forEach { theme ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(
                        if (theme.id == currentThemeId)
                            TranzoColors.ClayBlue.copy(alpha = 0.08f)
                        else Color.Transparent
                    )
                    .clickable { onThemeSelected(theme.id) }
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    theme.displayName,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = if (theme.id == currentThemeId) FontWeight.Bold else FontWeight.Medium,
                    color = TranzoColors.TextPrimary,
                )

                if (theme.id == currentThemeId) {
                    Box(
                        modifier = Modifier
                            .size(22.dp)
                            .clip(RoundedCornerShape(11.dp))
                            .background(TranzoColors.ClayBlue),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            Icons.Outlined.Check,
                            contentDescription = "Selected",
                            tint = Color.White,
                            modifier = Modifier.size(14.dp),
                        )
                    }
                }
            }
        }
    }
}
