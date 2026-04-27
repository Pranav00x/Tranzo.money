package com.tranzo.app.ui.send

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
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SendScreenModern(
    onBack: () -> Unit = {},
    viewModel: SendViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
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
                "Send Crypto",
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
        ) {
            // Amount Section
            item {
                Text(
                    "Amount",
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
                        .padding(16.dp),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Column {
                            Text(
                                "250.00",
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Black,
                                color = Color.Black,
                            )
                            Text(
                                "≈ $250.00",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Normal,
                                color = Color.Black,
                            )
                        }
                        Button(
                            onClick = {},
                            modifier = Modifier.height(36.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                            shape = RoundedCornerShape(6.dp),
                        ) {
                            Text(
                                "Max",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
            }

            // Token Selection
            item {
                Text(
                    "Token",
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
                        .padding(16.dp),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Column {
                            Text(
                                "USDC",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Black,
                                color = Color.Black,
                            )
                            Text(
                                "Balance: 1,250.00",
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

            // Recipient
            item {
                Text(
                    "Recipient Address",
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
                        .padding(16.dp),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(
                            "0x...",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.Black,
                        )
                        Icon(
                            Icons.Outlined.QrCode,
                            contentDescription = "QR",
                            tint = Color.Black,
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
            }

            // Fee Section
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White, RoundedCornerShape(8.dp))
                        .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
                        .padding(12.dp),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(
                            "Network Fee",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                        )
                        Text(
                            "FREE",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.Black,
                        )
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
            }

            // Send Button
            item {
                Button(
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                    shape = RoundedCornerShape(8.dp),
                ) {
                    Text(
                        "Review & Send",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White,
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}
