package com.tranzo.app.ui.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tranzo.app.ui.components.ClayActionButton
import com.tranzo.app.ui.components.ClayButton
import com.tranzo.app.ui.components.ClayCard
import com.tranzo.app.ui.components.ClayGradientCard
import com.tranzo.app.ui.theme.TranzoColors

/**
 * Claymorphism Card Screen — Baby blue bg, gradient card visual, white detail cards.
 */
@Composable
fun CardScreenProClay(
    viewModel: CardViewModel = hiltViewModel(),
    onOrderCard: () -> Unit = {},
) {
    val uiState by viewModel.state.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(TranzoColors.ClayBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                "Your Card",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = TranzoColors.TextPrimary,
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Card visual
            ClayGradientCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                gradientStart = TranzoColors.ClayBlue,
                gradientEnd = Color(0xFF6C8DFF),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.SpaceBetween,
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text("Tranzo", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Text("VISA", color = Color.White.copy(alpha = 0.8f), fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    }

                    Text(
                        uiState.card?.maskedPan ?: "**** **** **** 4242",
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp,
                        letterSpacing = 3.sp,
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Column {
                            Text("Card Holder", color = Color.White.copy(alpha = 0.7f), fontSize = 10.sp)
                            Text(
                                uiState.card?.cardholderName ?: "YOUR NAME",
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 14.sp,
                            )
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text("Expires", color = Color.White.copy(alpha = 0.7f), fontSize = 10.sp)
                            Text(
                                uiState.card?.expiry ?: "12/28",
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 14.sp,
                            )
                        }
                    }
                }
            }

            // Card actions
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                ClayActionButton(
                    label = "Freeze",
                    onClick = { viewModel.toggleFreeze() },
                    backgroundColor = TranzoColors.ClayBlue,
                    icon = {
                        Icon(Icons.Outlined.Lock, contentDescription = null, tint = Color.White, modifier = Modifier.size(24.dp))
                    },
                )
                ClayActionButton(
                    label = "Details",
                    onClick = {},
                    backgroundColor = TranzoColors.ClayBlue,
                    icon = {
                        Icon(Icons.Outlined.Info, contentDescription = null, tint = Color.White, modifier = Modifier.size(24.dp))
                    },
                )
                ClayActionButton(
                    label = "Settings",
                    onClick = {},
                    backgroundColor = TranzoColors.ClayBlue,
                    icon = {
                        Icon(Icons.Outlined.Settings, contentDescription = null, tint = Color.White, modifier = Modifier.size(24.dp))
                    },
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Card details
            Text(
                "Card Details",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = TranzoColors.TextPrimary,
            )

            ClayCard(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    CardDetailRow("Status", uiState.card?.status?.replaceFirstChar { it.uppercase() } ?: "Active")
                    CardDetailRow("Spending Limit", "$5,000 / month")
                    CardDetailRow("Network", uiState.card?.network ?: "Visa")
                    CardDetailRow("Type", uiState.card?.type?.replaceFirstChar { it.uppercase() } ?: "Virtual")
                }
            }

            if (uiState.card == null) {
                Spacer(modifier = Modifier.height(16.dp))
                ClayButton(
                    text = "Order Your Card",
                    onClick = onOrderCard,
                )
            }

            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

@Composable
private fun CardDetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(label, style = MaterialTheme.typography.labelSmall, color = TranzoColors.TextTertiary)
        Text(value, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.SemiBold, color = TranzoColors.TextPrimary)
    }
}
