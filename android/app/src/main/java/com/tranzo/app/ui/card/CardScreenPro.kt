package com.tranzo.app.ui.card

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

/**
 * Card screen — CheQ-inspired monochrome look.
 * Displays card info from CardViewModel.
 */
@Composable
fun CardScreenPro(
    viewModel: CardViewModel = hiltViewModel(),
    onBack: () -> Unit = {},
    onOrderCard: () -> Unit = {},
) {
    val state by viewModel.state.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Header
            Text(
                "Card",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1A1A1A),
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(horizontal = 24.dp, vertical = 16.dp)
            )

            // Card visual
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .height(200.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A)),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    // Top row — logo and chip
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "TRANZO",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 2.sp
                        )
                        Icon(
                            imageVector = Icons.Outlined.CreditCard,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    // Card number
                    Column {
                        Text(
                            state.card?.maskedPan ?: "**** **** **** ----",
                            style = MaterialTheme.typography.headlineSmall,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 2.sp
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(
                                    "CARDHOLDER",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color.White.copy(alpha = 0.5f)
                                )
                                Text(
                                    state.card?.cardholderName ?: "YOUR NAME",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = Color.White,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            Column {
                                Text(
                                    "EXPIRES",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color.White.copy(alpha = 0.5f)
                                )
                                Text(
                                    state.card?.expiry ?: "--/--",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = Color.White,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Card controls
            if (state.card != null) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White
                ) {
                    Column {
                        CardControlRow(
                            icon = Icons.Outlined.Lock,
                            label = "Freeze Card",
                            subtitle = if (state.card?.status == "frozen") "Card is frozen" else "Card is active",
                            onClick = { viewModel.toggleFreeze() }
                        )
                        HorizontalDivider(
                            color = Color(0xFFF0F0F0),
                            thickness = 0.5.dp,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                        CardControlRow(
                            icon = Icons.Outlined.Visibility,
                            label = "Card Details",
                            subtitle = "View full card number",
                            onClick = { }
                        )
                        HorizontalDivider(
                            color = Color(0xFFF0F0F0),
                            thickness = 0.5.dp,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                        CardControlRow(
                            icon = Icons.Outlined.Receipt,
                            label = "Transactions",
                            subtitle = "View card spending",
                            onClick = { }
                        )
                    }
                }
            } else {
                // No card — Order CTA
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFF5F5F5)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Outlined.AddCard,
                                contentDescription = null,
                                tint = Color(0xFF1A1A1A),
                                modifier = Modifier.size(28.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            "Get a Tranzo Card",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1A1A1A)
                        )
                        Text(
                            "Spend crypto anywhere Visa is accepted",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF999999)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = onOrderCard,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF1A1A1A),
                                contentColor = Color.White
                            ),
                            elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
                        ) {
                            Text(
                                "Order Card",
                                modifier = Modifier.padding(vertical = 4.dp),
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }

            // Status section
            if (state.card != null) {
                Spacer(modifier = Modifier.height(16.dp))
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    shape = RoundedCornerShape(16.dp),
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
                            Text(
                                "Status",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color(0xFF999999)
                            )
                            Text(
                                state.card?.status?.replaceFirstChar { it.uppercaseChar() } ?: "Unknown",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = if (state.card?.status == "active") Color(0xFF22C55E) else Color(0xFF999999)
                            )
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                "Network",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color(0xFF999999)
                            )
                            Text(
                                state.card?.network ?: "Visa",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF1A1A1A)
                            )
                        }
                    }
                }
            }

            // Error
            state.error?.let { err ->
                Spacer(modifier = Modifier.height(12.dp))
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFFFFF0F0)
                ) {
                    Text(
                        err,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFFCC0000),
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

@Composable
private fun CardControlRow(
    icon: ImageVector,
    label: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFFF5F5F5)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color(0xFF1A1A1A),
                    modifier = Modifier.size(20.dp)
                )
            }
            Column {
                Text(
                    label,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF1A1A1A)
                )
                Text(
                    subtitle,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color(0xFF999999)
                )
            }
        }
        Icon(
            Icons.Outlined.ChevronRight,
            contentDescription = null,
            tint = Color(0xFFCCCCCC),
            modifier = Modifier.size(20.dp)
        )
    }
}
