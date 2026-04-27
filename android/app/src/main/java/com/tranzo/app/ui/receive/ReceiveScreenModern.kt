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
            .background(Color.White),
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                "Receive Crypto",
                fontSize = 24.sp,
                fontWeight = FontWeight.Black,
                color = Color.Black,
            )
            IconButton(onClick = onBack) {
                Icon(
                    Icons.Outlined.Close,
                    contentDescription = "Close",
                    tint = Color.Black,
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
            // QR Code
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Box(
                    modifier = Modifier
                        .size(220.dp)
                        .background(Color.White, RoundedCornerShape(12.dp))
                        .border(2.dp, Color.Black, RoundedCornerShape(12.dp))
                        .padding(16.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Box(
                        modifier = Modifier
                            .size(180.dp)
                            .background(Color.Black, RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            Icons.Outlined.QrCode,
                            contentDescription = "QR Code",
                            tint = Color.White,
                            modifier = Modifier.size(80.dp),
                        )
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }

            // Token Selection
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White, RoundedCornerShape(8.dp))
                        .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
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
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Black,
                                color = Color.Black,
                            )
                            Text(
                                "Polygon Network",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Normal,
                                color = Color.Black,
                            )
                        }
                        Icon(
                            Icons.Outlined.ExpandMore,
                            contentDescription = "Select",
                            tint = Color.Black,
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
            }

            // Wallet Address
            item {
                Text(
                    "Your Wallet Address",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                )
                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White, RoundedCornerShape(8.dp))
                        .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
                        .padding(12.dp),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(
                            "0x1234...abcd",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                        )
                        Button(
                            onClick = {},
                            modifier = Modifier.height(32.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                            shape = RoundedCornerShape(6.dp),
                        ) {
                            Text(
                                "Copy",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
            }

            // Info Banner
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White, RoundedCornerShape(8.dp))
                        .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
                        .padding(12.dp),
                ) {
                    Row(
                        verticalAlignment = Alignment.Top,
                    ) {
                        Icon(
                            Icons.Outlined.Info,
                            contentDescription = "Info",
                            tint = Color.Black,
                            modifier = Modifier
                                .size(16.dp)
                                .padding(end = 8.dp),
                        )
                        Text(
                            "Only send USDC on Polygon to this address. Sending other tokens or using different networks may result in loss of funds.",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.Black,
                        )
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}
