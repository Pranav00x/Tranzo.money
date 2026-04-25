package com.tranzo.app.ui.settings

import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tranzo.app.ui.components.ClayCard
import com.tranzo.app.ui.components.ClayButton
import com.tranzo.app.ui.theme.TranzoColors
import com.tranzo.app.util.ThemeManager

/**
 * Claymorphism Settings Screen - Premium, organized layout
 * Soft cards, gradient accents, trust-focused design
 */
@Composable
fun SettingsScreenProClay(
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
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        TranzoColors.BackgroundLight,
                        TranzoColors.SurfaceLight.copy(alpha = 0.5f)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Settings",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = TranzoColors.TextPrimary,
                    fontSize = 28.sp
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                // Profile section
                ProfileSectionClay()

                Spacer(modifier = Modifier.height(32.dp))

                // Settings sections
                SettingsSectionClay(title = "Appearance") {
                    SettingItemClay(
                        icon = Icons.Outlined.Palette,
                        label = "Theme",
                        description = "Appearance and colors",
                        onClick = { showThemeSelector = !showThemeSelector },
                        gradientStart = TranzoColors.PrimaryBlue,
                        gradientEnd = TranzoColors.PrimaryPurple,
                    )

                    if (showThemeSelector) {
                        Spacer(modifier = Modifier.height(12.dp))
                        ThemeSelectorCompactClay(
                            currentThemeId = currentThemeId,
                            availableThemes = availableThemes,
                            onThemeSelected = { themeId ->
                                themeManager.setTheme(themeId)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                SettingsSectionClay(title = "Security & Account") {
                    SettingItemClay(
                        icon = Icons.Outlined.Lock,
                        label = "Security",
                        description = "Biometric, PIN, and authentication",
                        onClick = onSecurity,
                        gradientStart = TranzoColors.PrimaryGreen,
                        gradientEnd = TranzoColors.AccentEmerald,
                    )

                    SettingItemClay(
                        icon = Icons.Outlined.PrivacyTip,
                        label = "Privacy",
                        description = "Data and privacy settings",
                        onClick = {},
                        gradientStart = TranzoColors.PrimaryPurple,
                        gradientEnd = TranzoColors.PinkLight,
                    )

                    SettingItemClay(
                        icon = Icons.Outlined.Info,
                        label = "About Tranzo",
                        description = "Version 1.0.0 • ZeroDev Kernel",
                        onClick = {},
                        gradientStart = TranzoColors.PrimaryYellow,
                        gradientEnd = TranzoColors.PrimaryOrange,
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Logout button
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                ) {
                    Button(
                        onClick = onLogout,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .shadow(
                                elevation = 8.dp,
                                shape = RoundedCornerShape(24.dp),
                                ambientColor = TranzoColors.Error.copy(alpha = 0.15f)
                            ),
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = TranzoColors.Error.copy(alpha = 0.9f),
                            contentColor = Color.White,
                        ),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
                    ) {
                        Text(
                            "Logout",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

/**
 * Claymorphism Profile Section
 */
@Composable
private fun ProfileSectionClay() {
    ClayCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        backgroundGradient = listOf(
            Color.White,
            TranzoColors.BackgroundLight.copy(alpha = 0.8f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar with gradient
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                TranzoColors.PrimaryBlue,
                                TranzoColors.PrimaryPurple
                            )
                        ),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .shadow(
                        elevation = 6.dp,
                        shape = RoundedCornerShape(20.dp),
                        ambientColor = TranzoColors.PrimaryBlue.copy(alpha = 0.2f)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "P",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
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

/**
 * Claymorphism Settings Section
 */
@Composable
private fun SettingsSectionClay(
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

        ClayCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            backgroundGradient = listOf(
                Color.White,
                TranzoColors.BackgroundLight.copy(alpha = 0.7f)
            )
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                content = content
            )
        }
    }
}

/**
 * Claymorphism Setting Item
 */
@Composable
private fun ColumnScope.SettingItemClay(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    description: String,
    onClick: () -> Unit,
    gradientStart: Color = TranzoColors.PrimaryBlue,
    gradientEnd: Color = TranzoColors.BlueLight,
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
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon box with gradient
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(gradientStart, gradientEnd)
                        ),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .shadow(
                        elevation = 4.dp,
                        shape = RoundedCornerShape(16.dp),
                        ambientColor = gradientStart.copy(alpha = 0.1f)
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    label,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TranzoColors.TextPrimary,
                    fontWeight = FontWeight.SemiBold
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

/**
 * Claymorphism Theme Selector
 */
@Composable
private fun ThemeSelectorCompactClay(
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
            ThemeOptionCompactClay(
                theme = theme,
                isSelected = theme.id == currentThemeId,
                onClick = { onThemeSelected(theme.id) }
            )
        }
    }
}

/**
 * Claymorphism Theme Option
 */
@Composable
private fun ThemeOptionCompactClay(
    theme: ThemeManager.ThemeOption,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    ClayCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clickable(onClick = onClick),
        backgroundGradient = if (isSelected)
            listOf(
                TranzoColors.PrimaryBlue.copy(alpha = 0.08f),
                TranzoColors.PrimaryPurple.copy(alpha = 0.05f)
            )
        else
            listOf(Color.White, TranzoColors.BackgroundLight.copy(alpha = 0.5f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                theme.displayName,
                style = MaterialTheme.typography.labelMedium,
                color = TranzoColors.TextPrimary,
                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium
            )

            if (isSelected) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    TranzoColors.PrimaryBlue,
                                    TranzoColors.PrimaryPurple
                                )
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = Icons.Outlined.CheckCircle,
                        contentDescription = "Selected",
                        tint = Color.White,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
        }
    }
}
