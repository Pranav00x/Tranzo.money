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
import androidx.hilt.navigation.compose.hiltViewModel
import com.tranzo.app.ui.components.TranzoButton
import com.tranzo.app.ui.theme.TranzoColors
import com.tranzo.app.util.ThemeManager

@Composable
fun SettingsScreenProMax(
    themeManager: ThemeManager = hiltViewModel(),
    onLogout: () -> Unit = {},
    onSecurity: () -> Unit = {},
    onTheme: () -> Unit = {},
) {
    val currentThemeId by themeManager.currentThemeId.collectAsState()
    var showThemeSelector by remember { mutableStateOf(false) }
    val availableThemes = themeManager.getAvailableThemes()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(TranzoColors.BackgroundLight)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Settings",
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.ExtraBold,
                    color = TranzoColors.TextPrimary
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                // Profile section
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape)
                                .background(TranzoColors.PrimaryBlue),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "P",
                                style = MaterialTheme.typography.displaySmall,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                "Pranav",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = TranzoColors.TextPrimary
                            )
                            Text(
                                "pranav@tranzo.app",
                                style = MaterialTheme.typography.bodySmall,
                                color = TranzoColors.TextSecondary
                            )
                            Text(
                                "Smart Account Active",
                                style = MaterialTheme.typography.labelSmall,
                                color = TranzoColors.Success,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // App Settings section
                SettingsSectionHeader("App Settings")

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        // Theme setting
                        SettingItemRow(
                            icon = Icons.Outlined.Palette,
                            label = "Theme",
                            description = "Light, Dark, Auto",
                            onClick = { showThemeSelector = !showThemeSelector }
                        )

                        if (showThemeSelector) {
                            Divider(thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))
                            Spacer(modifier = Modifier.height(12.dp))
                            ThemeSelectorInline(
                                currentThemeId = currentThemeId,
                                availableThemes = availableThemes,
                                onThemeSelected = { themeId -> themeManager.setTheme(themeId) },
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                            )
                        }

                        Divider(thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))

                        // Notifications
                        SettingItemRow(
                            icon = Icons.Outlined.NotificationsActive,
                            label = "Notifications",
                            description = "Push and email alerts",
                            onClick = { }
                        )

                        Divider(thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))

                        // Language
                        SettingItemRow(
                            icon = Icons.Outlined.Language,
                            label = "Language",
                            description = "English",
                            onClick = { }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Security & Account section
                SettingsSectionHeader("Security & Account")

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        SettingItemRow(
                            icon = Icons.Outlined.Lock,
                            label = "Security",
                            description = "Biometric, PIN",
                            onClick = onSecurity
                        )

                        Divider(thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))

                        SettingItemRow(
                            icon = Icons.Outlined.PrivacyTip,
                            label = "Privacy",
                            description = "Data and permissions",
                            onClick = { }
                        )

                        Divider(thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))

                        SettingItemRow(
                            icon = Icons.Outlined.Info,
                            label = "About Tranzo",
                            description = "Version 1.0.0",
                            onClick = { }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Logout button
                TranzoButton(
                    text = "Logout",
                    onClick = onLogout,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    containerColor = TranzoColors.Error
                )

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
private fun SettingsSectionHeader(title: String) {
    Text(
        title,
        style = MaterialTheme.typography.labelMedium,
        fontWeight = FontWeight.Bold,
        color = TranzoColors.TextTertiary,
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
    )
}

@Composable
private fun SettingItemRow(
    icon: ImageVector,
    label: String,
    description: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = TranzoColors.PrimaryBlue,
                modifier = Modifier.size(24.dp)
            )

            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(
                    label,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TranzoColors.TextPrimary,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    description,
                    style = MaterialTheme.typography.labelSmall,
                    color = TranzoColors.TextTertiary
                )
            }
        }

        Icon(
            imageVector = Icons.AutoMirrored.Outlined.ArrowForward,
            contentDescription = null,
            tint = TranzoColors.TextTertiary,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
private fun ThemeSelectorInline(
    currentThemeId: String,
    availableThemes: List<ThemeManager.ThemeOption>,
    onThemeSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)
    ) {
        items(availableThemes) { theme ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onThemeSelected(theme.id) },
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (theme.id == currentThemeId) TranzoColors.SurfaceLight else Color.White
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = if (theme.id == currentThemeId) 2.dp else 0.dp
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        theme.displayName,
                        style = MaterialTheme.typography.labelMedium,
                        color = TranzoColors.TextPrimary
                    )

                    if (theme.id == currentThemeId) {
                        Icon(
                            imageVector = Icons.Outlined.CheckCircle,
                            contentDescription = "Selected",
                            tint = TranzoColors.PrimaryBlue,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    }
}
