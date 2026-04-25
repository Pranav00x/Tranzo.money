package com.tranzo.app.ui.security

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tranzo.app.ui.components.ClayCard
import com.tranzo.app.ui.theme.TranzoColors

/**
 * Claymorphism Security Screen
 */
@Composable
fun SecurityScreenClay(
    viewModel: SecurityViewModel = hiltViewModel(),
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        TranzoColors.BackgroundLight,
                        TranzoColors.SurfaceLight.copy(alpha = 0.7f)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                "Security",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = TranzoColors.TextPrimary,
                fontSize = 28.sp
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Security status card
            ClayCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                backgroundGradient = listOf(
                    TranzoColors.Success.copy(alpha = 0.08f),
                    TranzoColors.PrimaryGreen.copy(alpha = 0.05f)
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Outlined.Shield,
                        contentDescription = "Secure",
                        tint = TranzoColors.Success,
                        modifier = Modifier.size(40.dp)
                    )

                    Column {
                        Text(
                            "Account Secure",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = TranzoColors.TextPrimary
                        )
                        Text(
                            "All security features enabled",
                            style = MaterialTheme.typography.labelSmall,
                            color = TranzoColors.TextSecondary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Authentication methods
            Text(
                "Authentication",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.SemiBold,
                color = TranzoColors.TextTertiary
            )

            Spacer(modifier = Modifier.height(12.dp))

            SecurityItemClay(
                icon = Icons.Outlined.Fingerprint,
                title = "Biometric",
                description = "Face ID / Fingerprint",
                isEnabled = true,
                gradientStart = TranzoColors.PrimaryBlue,
                gradientEnd = TranzoColors.BlueLight
            )

            SecurityItemClay(
                icon = Icons.Outlined.Lock,
                title = "PIN Code",
                description = "6-digit security PIN",
                isEnabled = true,
                gradientStart = TranzoColors.PrimaryGreen,
                gradientEnd = TranzoColors.AccentEmerald
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Session management
            Text(
                "Sessions & Devices",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.SemiBold,
                color = TranzoColors.TextTertiary
            )

            Spacer(modifier = Modifier.height(12.dp))

            ClayCard(
                modifier = Modifier.fillMaxWidth(),
                backgroundGradient = listOf(Color.White, TranzoColors.BackgroundLight.copy(alpha = 0.7f))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Outlined.Smartphone,
                                contentDescription = "Device",
                                tint = TranzoColors.PrimaryBlue,
                                modifier = Modifier.size(24.dp)
                            )

                            Column {
                                Text(
                                    "iPhone 15",
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.SemiBold,
                                    color = TranzoColors.TextPrimary
                                )
                                Text(
                                    "Current session",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = TranzoColors.TextTertiary
                                )
                            }
                        }

                        Text(
                            "This device",
                            style = MaterialTheme.typography.labelSmall,
                            color = TranzoColors.PrimaryGreen,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun SecurityItemClay(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    description: String,
    isEnabled: Boolean,
    gradientStart: Color = TranzoColors.PrimaryBlue,
    gradientEnd: Color = TranzoColors.BlueLight
) {
    ClayCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        backgroundGradient = listOf(Color.White, TranzoColors.BackgroundLight.copy(alpha = 0.7f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(gradientStart, gradientEnd)
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        icon,
                        contentDescription = title,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Column {
                    Text(
                        title,
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.SemiBold,
                        color = TranzoColors.TextPrimary
                    )
                    Text(
                        description,
                        style = MaterialTheme.typography.labelSmall,
                        color = TranzoColors.TextTertiary
                    )
                }
            }

            Switch(
                checked = isEnabled,
                onCheckedChange = {},
                colors = SwitchDefaults.colors(
                    checkedThumbColor = TranzoColors.Success,
                    checkedTrackColor = TranzoColors.Success.copy(alpha = 0.3f)
                )
            )
        }
    }
}
