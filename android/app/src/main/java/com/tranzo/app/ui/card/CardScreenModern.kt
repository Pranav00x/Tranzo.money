package com.tranzo.app.ui.card

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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

@Composable
fun CardScreenModern() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFB))
            .systemBarsPadding(),
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                "Your Card",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF111827),
            )
            IconButton(onClick = {}) {
                Icon(
                    Icons.Outlined.MoreVert,
                    contentDescription = "More",
                    tint = Color(0xFF111827),
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 16.dp),
        ) {
            // Card Display
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
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
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(
                            "Tranzo",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                        )
                        Column {
                            Text(
                                "Card Number",
                                fontSize = 12.sp,
                                color = Color(0xFFCEFCE8),
                            )
                            Text(
                                "•••• •••• •••• 4242",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White,
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                Column {
                                    Text(
                                        "Valid Thru",
                                        fontSize = 10.sp,
                                        color = Color(0xFFCEFCE8),
                                    )
                                    Text(
                                        "12/28",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White,
                                    )
                                }
                                Column {
                                    Text(
                                        "CVV",
                                        fontSize = 10.sp,
                                        color = Color(0xFFCEFCE8),
                                    )
                                    Text(
                                        "•••",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White,
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Status Badge
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Box(
                    modifier = Modifier
                        .background(Color(0xFFDEFAF1), RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            Icons.Outlined.CheckCircle,
                            contentDescription = "Active",
                            tint = Color(0xFF10B981),
                            modifier = Modifier.size(16.dp),
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "Card Active",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF10B981),
                        )
                    }
                }
            }

            // Stats
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White, RoundedCornerShape(16.dp))
                        .border(1.dp, Color(0xFFE5E7EB), RoundedCornerShape(16.dp))
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    StatItem("Balance", "$2,450.00")
                    StatItem("Monthly\nLimit", "$10,000")
                    StatItem("Transactions", "47")
                }
            }

            // Actions
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Column(modifier = Modifier.fillMaxWidth()) {
                    ActionButton("View Details", Icons.Outlined.Info)
                    Spacer(modifier = Modifier.height(12.dp))
                    ActionButton("Manage Limits", Icons.Outlined.Settings)
                    Spacer(modifier = Modifier.height(12.dp))
                    ActionButton("Block Card", Icons.Outlined.Block, isDestructive = true)
                }
            }

            item {
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
private fun StatItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            label,
            fontSize = 12.sp,
            color = Color(0xFF6B7280),
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            value,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF111827),
        )
    }
}

@Composable
private fun ActionButton(
    label: String,
    icon: ImageVector,
    isDestructive: Boolean = false,
) {
    Button(
        onClick = {},
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isDestructive) Color(0xFFFEE2E2) else Color.White,
        ),
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (isDestructive) Color(0xFFEF4444) else Color(0xFFE5E7EB),
        ),
    ) {
        Icon(
            icon,
            contentDescription = label,
            tint = if (isDestructive) Color(0xFFEF4444) else Color(0xFF0D9488),
            modifier = Modifier
                .size(20.dp)
                .padding(end = 8.dp),
        )
        Text(
            label,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = if (isDestructive) Color(0xFFEF4444) else Color(0xFF111827),
        )
    }
}
