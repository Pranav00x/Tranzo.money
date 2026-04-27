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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
            .background(Color.White),
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                "Tranzo",
                fontSize = 24.sp,
                fontWeight = FontWeight.Black,
                color = Color.Black,
            )
            IconButton(onClick = onNavigateToSettings) {
                Icon(
                    Icons.Outlined.Settings,
                    contentDescription = "Settings",
                    tint = Color.Black,
                    modifier = Modifier.size(24.dp),
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 16.dp),
        ) {
            // Balance Section
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Black, RoundedCornerShape(12.dp))
                        .padding(20.dp),
                ) {
                    Column {
                        Text(
                            "Total Balance",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.White,
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "$2,450.00",
                            fontSize = 40.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.White,
                        )
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }

            // Quick Actions
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    QuickActionButton("Send", Icons.Outlined.ArrowUpward, onClick = onNavigateToTransfer, modifier = Modifier.weight(1f))
                    QuickActionButton("Receive", Icons.Outlined.ArrowDownward, modifier = Modifier.weight(1f))
                    QuickActionButton("Swap", Icons.Outlined.SwapHoriz, onClick = onNavigateToSwap, modifier = Modifier.weight(1f))
                    QuickActionButton("Dripper", Icons.Outlined.WaterDrop, modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(24.dp))
            }

            // Assets Section
            item {
                Text(
                    "Assets",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.Black,
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            items(3) {
                AssetCard("USDC", "1,250.00", "$1,250.00")
                Spacer(modifier = Modifier.height(10.dp))
            }

            // Card Section
            item {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    "Your Card",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.Black,
                )
                Spacer(modifier = Modifier.height(12.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .background(Color.Black, RoundedCornerShape(12.dp))
                        .padding(16.dp)
                        .clickable(onClick = onNavigateToCard),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomStart),
                    ) {
                        Text(
                            "•••• •••• •••• 4242",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            "Tap to manage",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.White,
                        )
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
private fun QuickActionButton(
    label: String,
    icon: ImageVector,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .background(Color.Black, RoundedCornerShape(8.dp))
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            icon,
            contentDescription = label,
            tint = Color.White,
            modifier = Modifier.size(28.dp),
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            label,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
        )
    }
}

@Composable
private fun AssetCard(
    symbol: String,
    balance: String,
    usdValue: String,
) {
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
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column {
                Text(
                    symbol,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.Black,
                )
                Text(
                    balance,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black,
                )
            }
            Text(
                usdValue,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
            )
        }
    }
}
