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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun CardScreenModern(
    viewModel: CardViewModel = hiltViewModel(),
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
                "Your Card",
                fontSize = 24.sp,
                fontWeight = FontWeight.Black,
                color = Color.Black,
            )
            IconButton(onClick = {}) {
                Icon(
                    Icons.Outlined.MoreVert,
                    contentDescription = "More",
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
            // Card Display
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .background(Color.Black, RoundedCornerShape(12.dp))
                        .padding(20.dp),
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(
                            "Tranzo",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.White,
                        )
                        Column {
                            Text(
                                state.card?.maskedPan ?: "•••• •••• •••• ••••",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                Column {
                                    Text(
                                        "Valid Thru",
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Normal,
                                        color = Color.White,
                                    )
                                    Text(
                                        state.card?.expiry ?: "--/--",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White,
                                    )
                                }
                                Column {
                                    Text(
                                        "CVV",
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Normal,
                                        color = Color.White,
                                    )
                                    Text(
                                        "•••",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White,
                                    )
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
            }

            // Status Badge
            item {
                Box(
                    modifier = Modifier
                        .background(Color.Black, RoundedCornerShape(6.dp))
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            Icons.Outlined.CheckCircle,
                            contentDescription = "Active",
                            tint = Color.White,
                            modifier = Modifier.size(14.dp),
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            "Card Active",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
            }

            // Stats Grid
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White, RoundedCornerShape(8.dp))
                        .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    StatCard("Balance", "$2,450.00")
                    Divider(
                        modifier = Modifier
                            .width(1.dp)
                            .height(50.dp),
                        color = Color.Black,
                    )
                    StatCard("Monthly Limit", "$10,000")
                    Divider(
                        modifier = Modifier
                            .width(1.dp)
                            .height(50.dp),
                        color = Color.Black,
                    )
                    StatCard("Transactions", "47")
                }
                Spacer(modifier = Modifier.height(20.dp))
            }

            // Action Buttons
            item {
                Column(modifier = Modifier.fillMaxWidth()) {
                    ActionButton("View Details", Icons.Outlined.Info)
                    Spacer(modifier = Modifier.height(10.dp))
                    ActionButton("Manage Limits", Icons.Outlined.Settings)
                    Spacer(modifier = Modifier.height(10.dp))
                    ActionButton("Block Card", Icons.Outlined.Block, isDestructive = true)
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun StatCard(label: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            label,
            fontSize = 10.sp,
            fontWeight = FontWeight.Normal,
            color = Color.Black,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            value,
            fontSize = 13.sp,
            fontWeight = FontWeight.Black,
            color = Color.Black,
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
            .height(44.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isDestructive) Color.White else Color.Black,
        ),
        shape = RoundedCornerShape(8.dp),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            Color.Black,
        ),
    ) {
        Icon(
            icon,
            contentDescription = label,
            tint = if (isDestructive) Color.Black else Color.White,
            modifier = Modifier
                .size(16.dp)
                .padding(end = 8.dp),
        )
        Text(
            label,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = if (isDestructive) Color.Black else Color.White,
        )
    }
}
