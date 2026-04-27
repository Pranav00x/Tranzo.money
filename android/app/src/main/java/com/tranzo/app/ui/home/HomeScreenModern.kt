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
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HomeScreenModern(
    onNavigateToTransfer: () -> Unit = {},
    onNavigateToSwap: () -> Unit = {},
    onNavigateToCard: () -> Unit = {},
    onNavigateToSettings: () -> Unit = {},
    viewModel: HomeViewModel = hiltViewModel(),
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

        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator(color = Color.Black)
            }
        } else if (state.error != null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                contentAlignment = Alignment.Center,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(24.dp),
                ) {
                    Text(
                        "Error",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.Black,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        state.error ?: "Unknown error",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black,
                        modifier = Modifier.padding(16.dp),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { viewModel.loadDashboard() },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                        modifier = Modifier
                            .height(48.dp)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                    ) {
                        Text("Retry", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }
        } else {
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
                                "$${String.format("%.2f", state.totalUsdBalance)}",
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
                if (state.balances.isNotEmpty()) {
                    item {
                        Text(
                            "Assets",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.Black,
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    items(state.balances.size) { index ->
                        val balance = state.balances[index]
                        AssetCard(balance.symbol, balance.balance, balance.formatted)
                        Spacer(modifier = Modifier.height(10.dp))
                    }
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
