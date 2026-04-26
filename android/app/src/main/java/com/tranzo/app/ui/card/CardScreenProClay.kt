package com.tranzo.app.ui.card

import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tranzo.app.ui.components.*
import com.tranzo.app.ui.theme.TranzoColors

/**
 * Claymorphism Card Screen — Premium virtual card display with 3D depth.
 * Features a realistic card visual with chip illustration, action pills,
 * and organized detail sections.
 */
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
        // Background blobs
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                color = TranzoColors.ClayBlue.copy(alpha = 0.06f),
                radius = 250f,
                center = Offset(size.width * 0.9f, size.height * 0.15f),
            )
            drawCircle(
                color = TranzoColors.ClayPurple.copy(alpha = 0.05f),
                radius = 180f,
                center = Offset(size.width * 0.1f, size.height * 0.6f),
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
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
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "Virtual debit card",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TranzoColors.TextSecondary,
                    )
                }
                ClayIconPill(color = TranzoColors.ClayBlue, size = 44.dp) {
                    Icon(
                        Icons.Outlined.CreditCard,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(22.dp),
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // ── Card Visual ─────────────────────────────────
            ClayGradientCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(210.dp),
                gradientStart = Color(0xFF4A5AE8),
                gradientEnd = Color(0xFF7B5CE8),
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    // Decorative elements on card
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        // Chip rectangle
                        drawRoundRect(
                            color = Color(0xFFFFD700).copy(alpha = 0.6f),
                            topLeft = Offset(60f, size.height * 0.35f),
                            size = Size(100f, 70f),
                            cornerRadius = CornerRadius(12f),
                        )
                        // Chip lines
                        drawLine(
                            color = Color(0xFFFFD700).copy(alpha = 0.3f),
                            start = Offset(70f, size.height * 0.35f + 35f),
                            end = Offset(150f, size.height * 0.35f + 35f),
                            strokeWidth = 1f,
                        )
                        // Decorative circles
                        drawCircle(
                            color = Color.White.copy(alpha = 0.05f),
                            radius = 140f,
                            center = Offset(size.width * 0.85f, size.height * 0.25f),
                        )
                        drawCircle(
                            color = Color.White.copy(alpha = 0.03f),
                            radius = 100f,
                            center = Offset(size.width * 0.75f, size.height * 0.8f),
                        )
                    }

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

                        // Card number area (below chip)
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
            }

            Spacer(modifier = Modifier.height(4.dp))

            // ── Card Actions ─────────────────────────────
            ClayCard(
                modifier = Modifier.fillMaxWidth(),
                shadowElevation = 6.dp,
                cornerRadius = 20.dp,
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    ClayActionButton(
                        label = "Freeze",
                        onClick = { viewModel.toggleFreeze() },
                        backgroundColor = TranzoColors.ClayBlue,
                        icon = {
                            Icon(Icons.Outlined.Lock, contentDescription = null, tint = Color.White, modifier = Modifier.size(24.dp))
                        },
                    )
                    ClayActionButton(
                        label = "Details",
                        onClick = {},
                        backgroundColor = TranzoColors.ClayPurple,
                        icon = {
                            Icon(Icons.Outlined.Visibility, contentDescription = null, tint = Color.White, modifier = Modifier.size(24.dp))
                        },
                    )
                    ClayActionButton(
                        label = "Settings",
                        onClick = {},
                        backgroundColor = TranzoColors.ClayAmber,
                        icon = {
                            Icon(Icons.Outlined.Settings, contentDescription = null, tint = Color.White, modifier = Modifier.size(24.dp))
                        },
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // ── Card Details ─────────────────────────────
            Text(
                "Card Details",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.ExtraBold,
                color = TranzoColors.TextPrimary,
                letterSpacing = 0.5.sp,
            )

            ClayCard(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    CardDetailRow("Status", uiState.card?.status?.replaceFirstChar { it.uppercase() } ?: "Active", TranzoColors.ClayGreen)
                    CardDetailRow("Spending Limit", "$5,000 / month", TranzoColors.ClayBlue)
                    CardDetailRow("Network", uiState.card?.network ?: "Visa", TranzoColors.ClayPurple)
                    CardDetailRow("Type", uiState.card?.type?.replaceFirstChar { it.uppercase() } ?: "Virtual", TranzoColors.ClayAmber)
                }
            }

            if (uiState.card == null) {
                Spacer(modifier = Modifier.height(16.dp))
                ClayButton(
                    text = "Order Your Card",
                    onClick = onOrderCard,
                )
            }

            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

@Composable
private fun CardDetailRow(label: String, value: String, accentColor: Color = TranzoColors.ClayBlue) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(accentColor.copy(alpha = 0.5f)),
            )
            Text(
                label,
                style = MaterialTheme.typography.labelSmall,
                color = TranzoColors.TextTertiary,
                fontWeight = FontWeight.Medium,
            )
        }
        Text(
            value,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = TranzoColors.TextPrimary,
        )
    }
}
