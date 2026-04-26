package com.tranzo.app.ui.home

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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Modern Home Screen Redesign
 * - Vibrant teal gradient header
 * - Quick action buttons (Send, Receive, Swap, Dripper)
 * - Clean token balance cards
 * - Card preview section
 * - Minimal brutal aesthetic
 */
@Composable
fun HomeScreenModern(
    onNavigateToTransfer: () -> Unit = {},
    onNavigateToSwap: () -> Unit = {},
    onNavigateToCard: () -> Unit = {},
    onNavigateToSettings: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFB))
            .systemBarsPadding(),
    ) {
        // Gradient Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF0D9488),  // Teal
                            Color(0xFF06B6D4),  // Cyan
                        ),
                    ),
                )
                .padding(24.dp),
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        "Tranzo",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                    )
                    IconButton(onClick = onNavigateToSettings) {
                        Icon(
                            Icons.Outlined.Notifications,
                            contentDescription = "Notifications",
                            tint = Color.White,
                        )
                    }
                }

                Text(
                    "Total Balance",
                    fontSize = 14.sp,
                    color = Color(0xFFCEFCE8),
                )
                Text(
                    "$2,450.00",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 16.dp),
        ) {
            // Quick Actions
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    QuickActionButtonModern("Send", Icons.Outlined.ArrowOutward, onClick = onNavigateToTransfer)
                    QuickActionButtonModern("Receive", Icons.Outlined.ArrowDownward)
                    QuickActionButtonModern("Swap", Icons.Outlined.SwapHoriz, onClick = onNavigateToSwap)
                    QuickActionButtonModern("Dripper", Icons.Outlined.WaterDrop)
                }
            }

            // Assets Section
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    "Assets",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF111827),
                    modifier = Modifier.padding(vertical = 12.dp),
                )
            }

            // Token Cards
            items(3) {
                TokenCardModern("USDC", "1,250.00", "$1,250.00")
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Card Section
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    "Your Card",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF111827),
                    modifier = Modifier.padding(vertical = 12.dp),
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(
                            Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFF0D9488),
                                    Color(0xFF06B6D4),
                                ),
                            ),
                            RoundedCornerShape(20.dp),
                        )
                        .padding(24.dp),
                ) {
                    Column(
                        modifier = Modifier.align(Alignment.BottomStart),
                    ) {
                        Text(
                            "Tranzo Visa Card",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color(0xFFCEFCE8),
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            "Tap to manage",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White,
                        )
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun QuickActionButtonModern(
    label: String,
    icon: ImageVector,
    onClick: () -> Unit = {},
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(70.dp)
            .let {
                if (label != "Dripper") {
                    it.clickable(enabled = true, onClick = onClick)
                } else {
                    it
                }
            },
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .background(Color(0xFFF3F4F6), RoundedCornerShape(14.dp)),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                icon,
                contentDescription = label,
                tint = Color(0xFF0D9488),
                modifier = Modifier.size(24.dp),
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF111827),
        )
    }
}

@Composable
private fun TokenCardModern(
    symbol: String,
    balance: String,
    usdValue: String,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(16.dp))
            .border(1.dp, Color(0xFFE5E7EB), RoundedCornerShape(16.dp))
            .padding(16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column {
                Text(
                    symbol,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF111827),
                )
                Text(
                    balance,
                    fontSize = 12.sp,
                    color = Color(0xFF6B7280),
                )
            }
            Text(
                usdValue,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF111827),
            )
        }
    }
}
