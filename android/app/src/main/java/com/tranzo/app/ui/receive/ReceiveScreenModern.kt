package com.tranzo.app.ui.receive

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ReceiveScreenModern(onBack: () -> Unit = {}) {
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
                "Receive Crypto",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF111827),
            )
            IconButton(onClick = onBack) {
                Icon(
                    Icons.Outlined.Close,
                    contentDescription = "Close",
                    tint = Color(0xFF111827),
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // QR Code Box
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Box(
                    modifier = Modifier
                        .size(240.dp)
                        .background(Color.White, RoundedCornerShape(16.dp))
                        .border(1.dp, Color(0xFFE5E7EB), RoundedCornerShape(16.dp))
                        .padding(16.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    // Placeholder QR code
                    Box(
                        modifier = Modifier
                            .size(200.dp)
                            .background(Color(0xFFF3F4F6)),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            Icons.Outlined.QrCode,
                            contentDescription = "QR Code",
                            tint = Color(0xFF0D9488),
                            modifier = Modifier.size(80.dp),
                        )
                    }
                }
            }

            // Token Selection
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White, RoundedCornerShape(12.dp))
                        .border(1.dp, Color(0xFFE5E7EB), RoundedCornerShape(12.dp))
                        .padding(16.dp),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Column {
                            Text(
                                "USDC on Polygon",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF111827),
                            )
                            Text(
                                "Polygon Network",
                                fontSize = 12.sp,
                                color = Color(0xFF6B7280),
                            )
                        }
                        Icon(
                            Icons.Outlined.ExpandMore,
                            contentDescription = "Select",
                            tint = Color(0xFF6B7280),
                        )
                    }
                }
            }

            // Wallet Address
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    "Your Wallet Address",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF111827),
                )
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White, RoundedCornerShape(12.dp))
                        .border(1.dp, Color(0xFFE5E7EB), RoundedCornerShape(12.dp))
                        .padding(16.dp),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Column {
                            Text(
                                "0x1234...abcd",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF111827),
                            )
                        }
                        Button(
                            onClick = {},
                            modifier = Modifier.height(36.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFF3F4F6),
                            ),
                            shape = RoundedCornerShape(8.dp),
                        ) {
                            Text(
                                "Copy",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF0D9488),
                            )
                        }
                    }
                }
            }

            // Info Banner
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFEFF6FF), RoundedCornerShape(12.dp))
                        .border(1.dp, Color(0xFFBFDBFE), RoundedCornerShape(12.dp))
                        .padding(16.dp),
                ) {
                    Row(
                        verticalAlignment = Alignment.Top,
                    ) {
                        Icon(
                            Icons.Outlined.Info,
                            contentDescription = "Info",
                            tint = Color(0xFF3B82F6),
                            modifier = Modifier
                                .size(20.dp)
                                .padding(end = 12.dp),
                        )
                        Text(
                            "Only send USDC on Polygon to this address. Sending other tokens or using different networks may result in loss of funds.",
                            fontSize = 12.sp,
                            color = Color(0xFF111827),
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}
