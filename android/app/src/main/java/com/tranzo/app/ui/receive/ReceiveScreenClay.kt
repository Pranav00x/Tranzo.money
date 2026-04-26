package com.tranzo.app.ui.receive

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tranzo.app.ui.components.*
import com.tranzo.app.ui.theme.TranzoColors

@Composable
fun ReceiveScreenClay() {
    val walletAddress = "0x742d35Cc6634C0532925a3b844Bc9e7595f2bD04"

    Box(Modifier.fillMaxSize().background(TranzoColors.ClayBackground)) {
        Column(
            Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
        ) {
            Spacer(Modifier.height(24.dp))

            // ── Header ──
            Row(Modifier.fillMaxWidth().padding(horizontal = 24.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                ClayIconPill(color = TranzoColors.ClayGreen, size = 48.dp, cornerRadius = 16.dp) {
                    Icon(Icons.Outlined.ArrowDownward, null, tint = Color.White, modifier = Modifier.size(24.dp))
                }
                Column {
                    Text("Receive Crypto", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.ExtraBold, color = TranzoColors.TextPrimary)
                    Text("Share your address to receive tokens", style = MaterialTheme.typography.bodySmall, color = TranzoColors.TextSecondary)
                }
            }

            Spacer(Modifier.height(32.dp))

            // ── QR & Address Details ──
            Text("WALLET DETAILS", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = TranzoColors.TextTertiary, letterSpacing = 1.5.sp, modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp))

            ClayCard(Modifier.fillMaxWidth().padding(horizontal = 24.dp), cornerRadius = 22.dp) {
                Column(Modifier.fillMaxWidth().padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(24.dp)) {
                    
                    // Simple placeholder for QR to keep it clean
                    Box(
                        Modifier.size(200.dp)
                            .clip(RoundedCornerShape(24.dp))
                            .background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Outlined.QrCode, contentDescription = null, tint = TranzoColors.TextSecondary, modifier = Modifier.size(100.dp))
                    }

                    HorizontalDivider(color = TranzoColors.DividerGray)

                    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(walletAddress, style = MaterialTheme.typography.bodyMedium, color = TranzoColors.TextPrimary, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                        Box(Modifier.clip(RoundedCornerShape(8.dp)).background(TranzoColors.ClayBlueSoft).padding(horizontal = 10.dp, vertical = 4.dp)) {
                            Text("Base Sepolia Network", style = MaterialTheme.typography.labelSmall, color = TranzoColors.ClayBlue, fontWeight = FontWeight.Bold, fontSize = 10.sp)
                        }
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            // Action buttons
            Row(Modifier.fillMaxWidth().padding(horizontal = 24.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                ClayButton(text = "Copy Address", onClick = {}, modifier = Modifier.weight(1f), containerColor = TranzoColors.ClayBlue)
                Button(
                    onClick = {}, modifier = Modifier.weight(1f).height(58.dp),
                    shape = RoundedCornerShape(22.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = TranzoColors.ClayCard, contentColor = TranzoColors.ClayBlue),
                    elevation = ButtonDefaults.buttonElevation(0.dp),
                ) {
                    Text("Share", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            }

            Spacer(Modifier.height(100.dp))
        }
    }
}
