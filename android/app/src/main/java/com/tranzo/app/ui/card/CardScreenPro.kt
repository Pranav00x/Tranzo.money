package com.tranzo.app.ui.card

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material3.*
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tranzo.app.ui.components.TranzoButton
import com.tranzo.app.ui.theme.TranzoColors

@Composable
fun CardScreenPro(
    onBack: () -> Unit = {},
    onOrderCard: () -> Unit = {},
    onCardDetails: (String) -> Unit = { },
    onManageLimits: () -> Unit = {},
) {
    var cardFrozen by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(TranzoColors.BackgroundLight)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack, modifier = Modifier.size(40.dp)) {
                    Icon(Icons.Outlined.ArrowBack, null, tint = TranzoColors.TextSecondary, modifier = Modifier.size(24.dp))
                }
                Text("Your Card", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = TranzoColors.TextPrimary)
                Box(modifier = Modifier.size(40.dp))
            }

            // Card display
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = TranzoColors.PrimaryBlue)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(TranzoColors.PrimaryBlue)
                        .padding(24.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "TRANZO",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                            Icon(
                                imageVector = Icons.Outlined.CreditCard,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        Column {
                            Text(
                                "**** **** **** 4242",
                                style = MaterialTheme.typography.headlineSmall,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 2.dp
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Text("CARDHOLDER", style = MaterialTheme.typography.labelSmall, color = Color.White.copy(alpha = 0.7f))
                                    Text("John Doe", style = MaterialTheme.typography.labelMedium, color = Color.White, fontWeight = FontWeight.SemiBold)
                                }
                                Column {
                                    Text("EXPIRES", style = MaterialTheme.typography.labelSmall, color = Color.White.copy(alpha = 0.7f))
                                    Text("12/26", style = MaterialTheme.typography.labelMedium, color = Color.White, fontWeight = FontWeight.SemiBold)
                                }
                            }
                        }
                    }
                }
            }

            // Card status
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                color = TranzoColors.SurfaceLight
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Status", style = MaterialTheme.typography.labelSmall, color = TranzoColors.TextTertiary)
                            Text("Active", style = MaterialTheme.typography.bodyMedium, color = TranzoColors.Success, fontWeight = FontWeight.SemiBold)
                        }
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .background(TranzoColors.Success.copy(alpha = 0.1f), shape = RoundedCornerShape(8.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Outlined.Check, null, tint = TranzoColors.Success, modifier = Modifier.size(16.dp))
                        }
                    }
                }
            }

            // Spending limit
            Text("Spending Limit", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, color = TranzoColors.TextPrimary)
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onManageLimits() },
                shape = RoundedCornerShape(12.dp),
                color = Color.White
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Daily limit", style = MaterialTheme.typography.labelSmall, color = TranzoColors.TextTertiary)
                        Text("$5,000", style = MaterialTheme.typography.titleMedium, color = TranzoColors.TextPrimary, fontWeight = FontWeight.Bold)
                    }
                    Icon(Icons.Outlined.ChevronRight, null, tint = TranzoColors.TextTertiary)
                }
            }

            // Quick actions
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                TranzoButton(
                    text = if (cardFrozen) "Unfreeze Card" else "Freeze Card",
                    onClick = { cardFrozen = !cardFrozen },
                    modifier = Modifier.fillMaxWidth(),
                    containerColor = if (cardFrozen) TranzoColors.Warning else TranzoColors.Error
                )

                TranzoButton(
                    text = "View Transactions",
                    onClick = { },
                    modifier = Modifier.fillMaxWidth(),
                    containerColor = TranzoColors.PrimaryBlue
                )
            }

            // Order new card
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onOrderCard() },
                shape = RoundedCornerShape(12.dp),
                color = TranzoColors.SurfaceLight
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Outlined.Add, null, tint = TranzoColors.PrimaryBlue, modifier = Modifier.size(24.dp))
                        Column {
                            Text("Order new card", style = MaterialTheme.typography.titleMedium, color = TranzoColors.TextPrimary, fontWeight = FontWeight.SemiBold)
                            Text("Virtual or physical", style = MaterialTheme.typography.labelSmall, color = TranzoColors.TextTertiary)
                        }
                    }
                    Icon(Icons.Outlined.ChevronRight, null, tint = TranzoColors.TextTertiary)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
