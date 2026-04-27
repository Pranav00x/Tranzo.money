package com.tranzo.app.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SettingsScreenModern(
    onLogout: () -> Unit = {},
    onSecurity: () -> Unit = {},
    onTheme: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                "Settings",
                fontSize = 28.sp,
                fontWeight = FontWeight.Black,
                color = Color.Black,
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 16.dp),
        ) {
            // Account Section
            item {
                Text(
                    "ACCOUNT",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.Black,
                )
                Spacer(modifier = Modifier.height(12.dp))

                SettingItem(label = "Profile", icon = Icons.Outlined.Person, onClick = { /* TODO */ })
                Spacer(modifier = Modifier.height(8.dp))
                SettingItem(label = "Notifications", icon = Icons.Outlined.Notifications, onClick = { /* TODO */ })

                Spacer(modifier = Modifier.height(24.dp))
            }

            // Security Section
            item {
                Text(
                    "SECURITY",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.Black,
                )
                Spacer(modifier = Modifier.height(12.dp))

                SettingItem(label = "Security", icon = Icons.Outlined.Security, onClick = onSecurity)
                Spacer(modifier = Modifier.height(8.dp))
                SettingItem(label = "Privacy", icon = Icons.Outlined.Lock, onClick = { /* TODO */ })

                Spacer(modifier = Modifier.height(24.dp))
            }

            // Display Section
            item {
                Text(
                    "DISPLAY",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.Black,
                )
                Spacer(modifier = Modifier.height(12.dp))

                SettingItem(label = "Theme", icon = Icons.Outlined.Settings, onClick = onTheme)
                Spacer(modifier = Modifier.height(8.dp))
                SettingItem(label = "Language", icon = Icons.Outlined.Language, onClick = { /* TODO */ })

                Spacer(modifier = Modifier.height(24.dp))
            }

            // App Section
            item {
                Text(
                    "APP",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.Black,
                )
                Spacer(modifier = Modifier.height(12.dp))

                SettingItem(label = "About", icon = Icons.Outlined.Info, onClick = { /* TODO */ })
                Spacer(modifier = Modifier.height(8.dp))
                SettingItem(label = "Version", icon = Icons.Outlined.Circle, trailing = "1.0.0")

                Spacer(modifier = Modifier.height(32.dp))
            }

            // Logout Button
            item {
                Button(
                    onClick = onLogout,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    shape = RoundedCornerShape(8.dp),
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, Color.Black),
                ) {
                    Icon(
                        Icons.Outlined.Logout,
                        contentDescription = "Logout",
                        tint = Color.Black,
                        modifier = Modifier.size(18.dp),
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "Logout",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.Black,
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
internal fun SettingItem(
    label: String,
    icon: ImageVector,
    onClick: () -> Unit = {},
    trailing: String = "",
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(8.dp))
            .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .padding(12.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f),
            ) {
                Icon(
                    icon,
                    contentDescription = label,
                    tint = Color.Black,
                    modifier = Modifier.size(20.dp),
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    label,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                )
            }

            if (trailing.isNotEmpty()) {
                Text(
                    trailing,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black,
                )
            } else {
                Icon(
                    Icons.Outlined.ChevronRight,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(20.dp),
                )
            }
        }
    }
}
