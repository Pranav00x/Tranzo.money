package com.tranzo.app.ui.card

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tranzo.app.ui.components.*
import com.tranzo.app.ui.theme.TranzoColors

@Composable
fun CardScreenProClay(
    viewModel: CardViewModel = hiltViewModel(),
    onOrderCard: () -> Unit = {},
) {
    val uiState by viewModel.state.collectAsState()

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

            // ── Header ──
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column {
                    Text(
                        "Your Card",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = TranzoColors.TextPrimary,
                    )
                    Text(
                        "Virtual debit card",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TranzoColors.TextSecondary,
                    )
                }
                ClayIconPill(color = TranzoColors.ClayBlue, size = 48.dp, cornerRadius = 16.dp) {
                    Icon(Icons.Outlined.CreditCard, contentDescription = null, tint = Color.White, modifier = Modifier.size(24.dp))
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // ── Card Visual (Clean & Professional) ──
            ClayGradientCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .height(210.dp),
                gradientStart = Color(0xFF1E1B2E), // Premium Dark Card
                gradientEnd = Color(0xFF2D2A42),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.SpaceBetween,
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(
                            "Tranzo",
                            color = Color.White,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 20.sp,
                            letterSpacing = 1.sp,
                        )
                        Text(
                            "VISA",
                            color = Color.White.copy(alpha = 0.85f),
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 18.sp,
                            letterSpacing = 2.sp,
                        )
                    }

                    Text(
                        uiState.card?.maskedPan ?: "**** **** **** 4242",
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 22.sp,
                        letterSpacing = 4.sp,
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Column {
                            Text(
                                "CARD HOLDER",
                                color = Color.White.copy(alpha = 0.6f),
                                fontSize = 9.sp,
                                letterSpacing = 1.sp,
                                fontWeight = FontWeight.SemiBold,
                            )
                            Text(
                                uiState.card?.cardholderName ?: "YOUR NAME",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                            )
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                "EXPIRES",
                                color = Color.White.copy(alpha = 0.6f),
                                fontSize = 9.sp,
                                letterSpacing = 1.sp,
                                fontWeight = FontWeight.SemiBold,
                            )
                            Text(
                                uiState.card?.expiry ?: "12/28",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // ── Card Management (Structured List) ──
            Text(
                "CARD MANAGEMENT",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = TranzoColors.TextTertiary,
                letterSpacing = 1.5.sp,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
            )

            ClayCard(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                cornerRadius = 22.dp
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    ActionItemClay(Icons.Outlined.Lock, "Freeze Card", "Temporarily disable your card", { viewModel.toggleFreeze() }, TranzoColors.ClayBlue)
                    HorizontalDivider(color = TranzoColors.DividerGray, modifier = Modifier.padding(horizontal = 16.dp))
                    ActionItemClay(Icons.Outlined.Visibility, "Show Details", "View card number and CVV", {}, TranzoColors.ClayPurple)
                    HorizontalDivider(color = TranzoColors.DividerGray, modifier = Modifier.padding(horizontal = 16.dp))
                    ActionItemClay(Icons.Outlined.Settings, "Card Settings", "Limits and security", {}, TranzoColors.ClayAmber, showDivider = false)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ── Card Info ──
            Text(
                "DETAILS",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = TranzoColors.TextTertiary,
                letterSpacing = 1.5.sp,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
            )

            ClayCard(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                cornerRadius = 22.dp
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    InfoRow("Status", uiState.card?.status?.replaceFirstChar { it.uppercase() } ?: "Active")
                    HorizontalDivider(color = TranzoColors.DividerGray)
                    InfoRow("Spending Limit", "$5,000 / month")
                    HorizontalDivider(color = TranzoColors.DividerGray)
                    InfoRow("Network", uiState.card?.network ?: "Visa")
                }
            }

            if (uiState.card == null) {
                Spacer(modifier = Modifier.height(24.dp))
                ClayButton(
                    text = "Order Your Card",
                    onClick = onOrderCard,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
            }

            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

@Composable
private fun ActionItemClay(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    iconColor: Color,
    showDivider: Boolean = true
) {
    Row(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick).padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(14.dp), verticalAlignment = Alignment.CenterVertically) {
            ClayIconPill(color = iconColor, size = 44.dp, cornerRadius = 15.dp) {
                Icon(icon, null, tint = Color.White, modifier = Modifier.size(20.dp))
            }
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(title, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = TranzoColors.TextPrimary)
                Text(subtitle, style = MaterialTheme.typography.labelSmall, color = TranzoColors.TextTertiary)
            }
        }
        Icon(Icons.AutoMirrored.Outlined.ArrowForward, null, tint = TranzoColors.TextTertiary, modifier = Modifier.size(18.dp))
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            label,
            style = MaterialTheme.typography.bodyMedium,
            color = TranzoColors.TextSecondary,
            fontWeight = FontWeight.Medium,
        )
        Text(
            value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = TranzoColors.TextPrimary,
        )
    }
}
