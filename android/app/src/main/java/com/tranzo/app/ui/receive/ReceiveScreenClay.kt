package com.tranzo.app.ui.receive

import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
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
        Canvas(Modifier.fillMaxSize()) {
            drawCircle(TranzoColors.ClayGreen.copy(alpha = 0.06f), 230f, Offset(size.width * 0.8f, size.height * 0.12f))
            drawCircle(TranzoColors.ClayPurple.copy(alpha = 0.04f), 180f, Offset(size.width * 0.15f, size.height * 0.6f))
        }

        Column(
            Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.height(24.dp))

            // Header
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                ClayIconPill(color = TranzoColors.ClayGreen, size = 52.dp, cornerRadius = 18.dp) {
                    Icon(Icons.Outlined.ArrowDownward, null, tint = Color.White, modifier = Modifier.size(26.dp))
                }
                Column {
                    Text("Receive Crypto", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.ExtraBold, color = TranzoColors.TextPrimary)
                    Text("Share your address to receive tokens", style = MaterialTheme.typography.bodySmall, color = TranzoColors.TextSecondary)
                }
            }

            Spacer(Modifier.height(32.dp))

            // QR Code placeholder — large clay card with illustrated QR
            ClayCard(Modifier.size(260.dp), cornerRadius = 28.dp, shadowElevation = 14.dp) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    // Draw a decorative QR-like pattern
                    Canvas(Modifier.size(160.dp)) {
                        val cellSize = size.width / 8f
                        val pattern = listOf(
                            listOf(1,1,1,0,0,1,1,1), listOf(1,0,1,0,1,1,0,1),
                            listOf(1,1,1,0,1,1,1,1), listOf(0,0,0,1,0,0,0,0),
                            listOf(1,0,1,0,1,0,1,0), listOf(1,1,0,1,0,1,1,1),
                            listOf(1,0,1,1,0,1,0,1), listOf(1,1,1,0,1,1,1,1),
                        )
                        pattern.forEachIndexed { row, cols ->
                            cols.forEachIndexed { col, filled ->
                                if (filled == 1) {
                                    drawRoundRect(
                                        color = TranzoColors.ClayBlue.copy(alpha = 0.8f),
                                        topLeft = Offset(col * cellSize + 2f, row * cellSize + 2f),
                                        size = Size(cellSize - 4f, cellSize - 4f),
                                        cornerRadius = CornerRadius(3f),
                                    )
                                }
                            }
                        }
                    }
                    // Center logo overlay
                    Box(
                        Modifier.size(44.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(TranzoColors.ClayBlue),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text("T", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 22.sp)
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            // Address card
            ClayCard(Modifier.fillMaxWidth(), cornerRadius = 20.dp) {
                Column(Modifier.fillMaxWidth().padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("YOUR WALLET ADDRESS", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = TranzoColors.TextTertiary, letterSpacing = 1.5.sp)
                    Text(walletAddress, style = MaterialTheme.typography.bodySmall, color = TranzoColors.TextPrimary, fontWeight = FontWeight.Medium, textAlign = TextAlign.Center)
                    Box(Modifier.clip(RoundedCornerShape(8.dp)).background(TranzoColors.ClayBlueSoft).padding(horizontal = 10.dp, vertical = 4.dp)) {
                        Text("Base Sepolia Network", style = MaterialTheme.typography.labelSmall, color = TranzoColors.ClayBlue, fontWeight = FontWeight.Bold, fontSize = 10.sp)
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            // Action buttons
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(
                    onClick = {}, modifier = Modifier.weight(1f).height(50.dp)
                        .shadow(10.dp, RoundedCornerShape(16.dp), ambientColor = TranzoColors.ClayBlue.copy(alpha = 0.3f)),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = TranzoColors.ClayBlue, contentColor = Color.White),
                    elevation = ButtonDefaults.buttonElevation(0.dp),
                ) {
                    Icon(Icons.Outlined.ContentCopy, null, Modifier.size(18.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("Copy", fontWeight = FontWeight.Bold)
                }
                Button(
                    onClick = {}, modifier = Modifier.weight(1f).height(50.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = TranzoColors.ClayCard, contentColor = TranzoColors.ClayBlue),
                    elevation = ButtonDefaults.buttonElevation(0.dp),
                ) {
                    Icon(Icons.Outlined.Share, null, Modifier.size(18.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("Share", fontWeight = FontWeight.Bold)
                }
            }

            Spacer(Modifier.height(100.dp))
        }
    }
}
