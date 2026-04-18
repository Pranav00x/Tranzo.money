package com.tranzo.app.ui.settings

import androidx.compose.material.icons.AutoMirroredIcons
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tranzo.app.ui.theme.TranzoColors
import com.tranzo.app.util.ThemeManager

/**
 * Theme Selector Screen - Let users choose from 8 themes
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeSelectorScreen(
    themeManager: ThemeManager = hiltViewModel(),
    onBack: () -> Unit = {},
) {
    val currentThemeId by themeManager.currentThemeId.collectAsState()
    val availableThemes = themeManager.getAvailableThemes()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Choose Theme") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = TranzoColors.White,
                    titleContentColor = TranzoColors.TextPrimary,
                ),
            )
        },
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(TranzoColors.White)
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Select your favorite theme",
                    style = MaterialTheme.typography.bodySmall,
                    color = TranzoColors.TextSecondary,
                )
            }

            items(availableThemes) { theme ->
                ThemeCard(
                    theme = theme,
                    isSelected = theme.id == currentThemeId,
                    onClick = { themeManager.setTheme(theme.id) },
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun ThemeCard(
    theme: ThemeManager.ThemeOption,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color(0xFFF5F5F5) else TranzoColors.LightGray,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isSelected) 4.dp else 0.dp,
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = theme.displayName,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = TranzoColors.TextPrimary,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Theme ID: ${theme.id}",
                    style = MaterialTheme.typography.labelSmall,
                    color = TranzoColors.TextTertiary,
                )
            }

            if (isSelected) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(TranzoColors.PrimaryBlack, CircleShape),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Check,
                        contentDescription = "Selected",
                        tint = TranzoColors.White,
                        modifier = Modifier.size(20.dp),
                    )
                }
            }
        }
    }
}
