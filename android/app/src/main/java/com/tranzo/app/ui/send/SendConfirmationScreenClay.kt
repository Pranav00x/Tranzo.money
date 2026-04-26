package com.tranzo.app.ui.send

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tranzo.app.ui.components.*
import com.tranzo.app.ui.theme.TranzoColors

@Composable
fun SendConfirmationScreenClay(
    recipient: String = "0x742d...",
    amount: String = "500",
    token: String = "USDC",
    viewModel: SendViewModel = hiltViewModel(),
    onConfirm: () -> Unit = {},
    onCancel: () -> Unit = {},
) {
    Box(Modifier.fillMaxSize().background(TranzoColors.ClayBackground)) {
        Column(
            Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(Modifier.height(40.dp))

            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                ClayIconPill(color = TranzoColors.ClayBlue, size = 48.dp, cornerRadius = 16.dp) {
                    Icon(Icons.Outlined.CheckCircle, null, tint = Color.White, modifier = Modifier.size(24.dp))
                }
                Text("Confirm Transfer", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.ExtraBold, color = TranzoColors.TextPrimary)
            }

            Spacer(Modifier.height(16.dp))

            // Summary card
            ClayCard(Modifier.fillMaxWidth(), cornerRadius = 22.dp) {
                Column(Modifier.fillMaxWidth().padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    Text(amount, style = MaterialTheme.typography.displayMedium, fontWeight = FontWeight.ExtraBold, color = TranzoColors.TextPrimary, fontSize = 48.sp)
                    Box(Modifier.clip(RoundedCornerShape(10.dp)).background(TranzoColors.ClayBlueSoft).padding(horizontal = 14.dp, vertical = 6.dp)) {
                        Text(token, style = MaterialTheme.typography.labelMedium, color = TranzoColors.ClayBlue, fontWeight = FontWeight.Bold)
                    }

                    HorizontalDivider(color = TranzoColors.DividerGray)

                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("To", style = MaterialTheme.typography.labelSmall, color = TranzoColors.TextTertiary)
                        Text(recipient, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = TranzoColors.TextPrimary)
                    }
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Network Fee", style = MaterialTheme.typography.labelSmall, color = TranzoColors.TextTertiary)
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Box(Modifier.clip(RoundedCornerShape(6.dp)).background(TranzoColors.ClayGreenSoft).padding(horizontal = 6.dp, vertical = 2.dp)) {
                                Text("GASLESS", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = TranzoColors.ClayGreen, fontSize = 8.sp)
                            }
                            Text("< $0.10", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = TranzoColors.ClayGreen)
                        }
                    }
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Total", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.ExtraBold, color = TranzoColors.TextPrimary)
                        Text("~ ${amount.toDoubleOrNull()?.plus(0.05) ?: amount}", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.ExtraBold, color = TranzoColors.ClayBlue)
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            ClayButton(text = "Confirm & Send", onClick = { viewModel.sendToken(recipient, token, amount); onConfirm() })

            TextButton(onClick = onCancel, modifier = Modifier.fillMaxWidth()) {
                Text("Cancel", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.SemiBold, color = TranzoColors.TextSecondary)
            }

            Spacer(Modifier.height(32.dp))
        }
    }
}
