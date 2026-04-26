package com.tranzo.app.ui.card

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tranzo.app.ui.components.*
import com.tranzo.app.ui.theme.TranzoColors

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
                .verticalScroll(rememberScrollState()),
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // ── Header (True Minimal) ──
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier.size(40.dp).clip(CircleShape).clickable {}.background(TranzoColors.ClayBackgroundAlt),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.AutoMirrored.Outlined.ArrowBack, contentDescription = null, tint = TranzoColors.TextPrimary, modifier = Modifier.size(20.dp))
                }
                Text(
                    "Card",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = TranzoColors.TextPrimary,
                )
                Box(modifier = Modifier.size(40.dp)) // Spacer for alignment
            }

            Spacer(modifier = Modifier.height(40.dp))

            // ── Minimal Card Visual ──
            // Just a clean dark rounded rectangle, no gradients or noise
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .height(220.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(TranzoColors.ClayCard) // Zerion Dark Card
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    verticalArrangement = Arrangement.SpaceBetween,
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(
                            "Tranzo",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            letterSpacing = 0.5.sp,
                        )
                        Text(
                            "VISA",
                            color = Color.White,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 20.sp,
                            letterSpacing = 1.sp,
                        )
                    }

                    Text(
                        uiState.card?.maskedPan ?: "•••• •••• •••• 4242",
                        color = Color.White,
                        fontWeight = FontWeight.Medium,
                        fontSize = 24.sp,
                        letterSpacing = 2.sp,
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(
                            uiState.card?.cardholderName ?: "YOUR NAME",
                            color = Color.White,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp,
                            letterSpacing = 1.sp,
                        )
                        Text(
                            uiState.card?.expiry ?: "12/28",
                            color = Color.White,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp,
                            letterSpacing = 1.sp,
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            // ── Card Management (List without card wrapper) ──
            Text(
                "Management",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = TranzoColors.TextPrimary,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
            )
            
            Spacer(modifier = Modifier.height(8.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                MinimalActionRow(Icons.Outlined.AcUnit, "Freeze Card", "Temporarily disable", { viewModel.toggleFreeze() })
                HorizontalDivider(color = TranzoColors.ClayBackgroundAlt, modifier = Modifier.padding(horizontal = 24.dp))
                MinimalActionRow(Icons.Outlined.Visibility, "Show Details", "View card number & CVV", {})
                HorizontalDivider(color = TranzoColors.ClayBackgroundAlt, modifier = Modifier.padding(horizontal = 24.dp))
                MinimalActionRow(Icons.Outlined.Settings, "Card Settings", "Limits and security", {})
            }

            Spacer(modifier = Modifier.height(40.dp))

            // ── Card Info ──
            Text(
                "Details",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = TranzoColors.TextPrimary,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
            )
            
            Spacer(modifier = Modifier.height(8.dp))

            Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp), verticalArrangement = Arrangement.spacedBy(24.dp)) {
                InfoRow("Status", uiState.card?.status?.replaceFirstChar { it.uppercase() } ?: "Active")
                InfoRow("Spending Limit", "$5,000 / month")
                InfoRow("Network", uiState.card?.network ?: "Visa")
            }

            if (uiState.card == null) {
                Spacer(modifier = Modifier.height(48.dp))
                ClayButton(
                    text = "Order Physical Card",
                    onClick = onOrderCard,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
            }

            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

@Composable
private fun MinimalActionRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick).padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(48.dp).clip(CircleShape).background(TranzoColors.ClayBackgroundAlt), contentAlignment = Alignment.Center) {
                Icon(icon, null, tint = TranzoColors.TextPrimary, modifier = Modifier.size(20.dp))
            }
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(title, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium, color = TranzoColors.TextPrimary)
                Text(subtitle, style = MaterialTheme.typography.bodySmall, color = TranzoColors.TextSecondary)
            }
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(label, style = MaterialTheme.typography.bodyLarge, color = TranzoColors.TextSecondary)
        Text(value, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium, color = TranzoColors.TextPrimary)
    }
}
