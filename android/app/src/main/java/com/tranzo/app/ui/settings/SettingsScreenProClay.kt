package com.tranzo.app.ui.settings

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tranzo.app.ui.components.*
import com.tranzo.app.ui.theme.TranzoColors
import com.tranzo.app.util.ThemeManager
import com.tranzo.app.util.ThemeViewModel

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

    Box(Modifier.fillMaxSize().background(TranzoColors.ClayBackground)) {
        Canvas(Modifier.fillMaxSize()) {
            drawCircle(TranzoColors.ClayPurple.copy(alpha = 0.06f), 240f, Offset(size.width * 0.85f, size.height * 0.08f))
            drawCircle(TranzoColors.ClayBlue.copy(alpha = 0.05f), 200f, Offset(size.width * 0.1f, size.height * 0.4f))
            drawCircle(TranzoColors.ClayAmber.copy(alpha = 0.04f), 150f, Offset(size.width * 0.7f, size.height * 0.75f))
        }

        Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
            Spacer(Modifier.height(24.dp))

            Text("Settings", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.ExtraBold,
                color = TranzoColors.TextPrimary, modifier = Modifier.padding(horizontal = 24.dp))

            Spacer(Modifier.height(24.dp))

            // Profile card with gradient avatar
            ClayCard(Modifier.fillMaxWidth().padding(horizontal = 24.dp), cornerRadius = 22.dp) {
                Row(Modifier.fillMaxWidth().padding(20.dp), horizontalArrangement = Arrangement.spacedBy(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        Modifier.size(60.dp)
                            .shadow(12.dp, RoundedCornerShape(20.dp), ambientColor = TranzoColors.ClayPurple.copy(alpha = 0.35f))
                            .clip(RoundedCornerShape(20.dp))
                            .background(Brush.linearGradient(listOf(TranzoColors.ClayBlue, TranzoColors.ClayPurple)))
                            .drawBehind {
                                drawRoundRect(
                                    Brush.verticalGradient(listOf(Color.White.copy(alpha = 0.25f), Color.Transparent), startY = 0f, endY = size.height * 0.35f),
                                    cornerRadius = CornerRadius(20.dp.toPx()), size = Size(size.width, size.height * 0.35f)
                                )
                            },
                        contentAlignment = Alignment.Center,
                    ) {
                        Text("P", color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 24.sp)
                    }

                    Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text("Pranav", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = TranzoColors.TextPrimary)
                        Text("pranav@tranzo.app", style = MaterialTheme.typography.bodySmall, color = TranzoColors.TextSecondary)
                    }
                    Box(Modifier.size(32.dp).clip(RoundedCornerShape(10.dp)).background(TranzoColors.ClayBlueSoft), contentAlignment = Alignment.Center) {
                        Text("\u2192", style = MaterialTheme.typography.titleMedium, color = TranzoColors.ClayBlue, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(Modifier.height(32.dp))

            // Appearance
            SettingsSectionClay(title = "APPEARANCE") {
                SettingItemClay(Icons.Outlined.Palette, "Theme", "Appearance and colors", { showThemeSelector = !showThemeSelector }, TranzoColors.ClayBlue)
                if (showThemeSelector) {
                    Spacer(Modifier.height(4.dp))
                    ThemeSelectorClay(currentThemeId, availableThemes) { themeViewModel.setTheme(it) }
                }
            }

            Spacer(Modifier.height(16.dp))

            // Security & Account
            SettingsSectionClay(title = "SECURITY & ACCOUNT") {
                SettingItemClay(Icons.Outlined.Lock, "Security", "Biometric, PIN, and auth", onSecurity, TranzoColors.ClayGreen)
                SettingItemClay(Icons.Outlined.PrivacyTip, "Privacy", "Data and privacy settings", {}, TranzoColors.ClayPurple)
                SettingItemClay(Icons.Outlined.Info, "About Tranzo", "Version 1.0.0", {}, TranzoColors.ClayAmber, showDivider = false)
            }

            Spacer(Modifier.height(32.dp))

            // Logout
            Button(
                onClick = onLogout,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp).height(54.dp)
                    .shadow(12.dp, RoundedCornerShape(20.dp), ambientColor = TranzoColors.ClayCoral.copy(alpha = 0.35f)),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = TranzoColors.ClayCoral, contentColor = Color.White),
                elevation = ButtonDefaults.buttonElevation(0.dp),
            ) {
                Text("Logout", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }

            Spacer(Modifier.height(100.dp))
        }
    }
}

@Composable
private fun SettingsSectionClay(title: String, content: @Composable ColumnScope.() -> Unit) {
    Column {
        Text(title, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = TranzoColors.TextTertiary,
            letterSpacing = 1.5.sp, modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp))
        ClayCard(Modifier.fillMaxWidth().padding(horizontal = 24.dp), cornerRadius = 22.dp) {
            Column(Modifier.fillMaxWidth(), content = content)
        }
    }
}

@Composable
private fun ColumnScope.SettingItemClay(icon: ImageVector, label: String, description: String, onClick: () -> Unit,
    iconColor: Color = TranzoColors.ClayBlue, gradientStart: Color = TranzoColors.ClayBlue,
    gradientEnd: Color = TranzoColors.ClayBlue, showDivider: Boolean = true) {
    Row(Modifier.fillMaxWidth().clickable(onClick = onClick).padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp), verticalAlignment = Alignment.CenterVertically) {
        ClayIconPill(color = iconColor, size = 44.dp, cornerRadius = 15.dp) {
            Icon(icon, null, tint = Color.White, modifier = Modifier.size(22.dp))
        }
        Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(label, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = TranzoColors.TextPrimary)
            Text(description, style = MaterialTheme.typography.labelSmall, color = TranzoColors.TextTertiary)
        }
        Icon(Icons.AutoMirrored.Outlined.ArrowForward, null, tint = TranzoColors.TextTertiary, modifier = Modifier.size(18.dp))
    }
    if (showDivider) HorizontalDivider(color = TranzoColors.DividerGray, modifier = Modifier.padding(horizontal = 16.dp))
}

@Composable
private fun ThemeSelectorClay(currentThemeId: String, availableThemes: List<ThemeManager.ThemeOption>, onThemeSelected: (String) -> Unit) {
    Column(Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 8.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
        availableThemes.forEach { theme ->
            Row(Modifier.fillMaxWidth().clip(RoundedCornerShape(14.dp))
                .background(if (theme.id == currentThemeId) TranzoColors.ClayBlueSoft else Color.Transparent)
                .clickable { onThemeSelected(theme.id) }.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                Text(theme.displayName, style = MaterialTheme.typography.labelMedium,
                    fontWeight = if (theme.id == currentThemeId) FontWeight.Bold else FontWeight.Medium, color = TranzoColors.TextPrimary)
                if (theme.id == currentThemeId) {
                    ClayIconPill(color = TranzoColors.ClayBlue, size = 24.dp, cornerRadius = 12.dp) {
                        Icon(Icons.Outlined.Check, "Selected", tint = Color.White, modifier = Modifier.size(14.dp))
                    }
                }
            }
        }
    }
}
