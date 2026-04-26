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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tranzo.app.ui.components.TranzoButton
import com.tranzo.app.ui.theme.TranzoColors
import com.tranzo.app.util.ThemeManager
import com.tranzo.app.util.ThemeViewModel

/**
 * Professional Settings Screen - Clean, organized layout
 */
@Composable
fun SettingsScreenPro(
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
            .background(TranzoColors.BackgroundLight)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Settings",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = TranzoColors.TextPrimary
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                // Profile section
                ProfileSection()

                Spacer(modifier = Modifier.height(32.dp))

                // Settings sections
                SettingsSection(title = "App Settings") {
                    SettingItem(
                        icon = Icons.Outlined.Palette,
                        label = "Theme",
                        description = "Appearance and colors",
                        onClick = { showThemeSelector = !showThemeSelector }
                    )

                    if (showThemeSelector) {
                        Spacer(modifier = Modifier.height(12.dp))
                        ThemeSelectorCompact(
                            currentThemeId = currentThemeId,
                            availableThemes = availableThemes,
                            onThemeSelected = { themeId ->
                                themeViewModel.setTheme(themeId)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                SettingsSection(title = "Security & Account") {
                    SettingItem(
                        icon = Icons.Outlined.Lock,
                        label = "Security",
                        description = "Biometric, PIN, and authentication",
                        onClick = onSecurity
                    )

                    SettingItem(
                        icon = Icons.Outlined.PrivacyTip,
                        label = "Privacy",
                        description = "Data and privacy settings",
                        onClick = {}
                    )

                    SettingItem(
                        icon = Icons.Outlined.Info,
                        label = "About Tranzo",
                        description = "Version 1.0.0",
                        onClick = {}
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

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
private fun ProfileSection() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar placeholder
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(TranzoColors.PrimaryBlue),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "P",
                    style = MaterialTheme.typography.headlineSmall,
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
                    fontWeight = FontWeight.SemiBold,
                    color = TranzoColors.TextPrimary
                )
                Text(
                    "pranav@tranzo.app",
                    style = MaterialTheme.typography.bodySmall,
                    color = TranzoColors.TextSecondary
                )
            }
        }
    }
}

@Composable
private fun SettingsSection(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        Text(
            title,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.SemiBold,
            color = TranzoColors.TextTertiary,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                content = content
            )
        }
    }
}

@Composable
private fun ColumnScope.SettingItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
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

            Column(
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
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

    Divider(
        color = TranzoColors.DividerGray,
        thickness = 1.dp,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
}

@Composable
private fun ThemeSelectorCompact(
    currentThemeId: String,
    availableThemes: List<ThemeManager.ThemeOption>,
    onThemeSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(availableThemes) { theme ->
            ThemeOptionCompact(
                theme = theme,
                isSelected = theme.id == currentThemeId,
                onClick = { onThemeSelected(theme.id) }
            )
        }
    }
}

@Composable
private fun ThemeOptionCompact(
    theme: ThemeManager.ThemeOption,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) TranzoColors.SurfaceLight else Color.White,
            contentColor = TranzoColors.TextPrimary
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isSelected) 2.dp else 0.dp
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

            if (isSelected) {
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
