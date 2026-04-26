package com.tranzo.app.ui.home

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tranzo.app.ui.theme.TranzoColors

// ─── ZERION DARK THEME COLORS ───
private val ZBg = Color(0xFF1C1C1E) // Main background
private val ZCard = Color(0xFF232325) // Slightly lighter card
private val ZBorder = Color(0xFF38383A) // Borders
private val ZTextPrimary = Color.White
private val ZTextSecondary = Color(0xFFA0A0A5)
private val ZRed = Color(0xFFFF5252)
private val ZGreen = Color(0xFF69F0AE)

@Composable
fun HomeScreenProClay(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToTransfer: () -> Unit = {},
    onNavigateToSwap: () -> Unit = {},
    onNavigateToCard: () -> Unit = {},
    onNavigateToSettings: () -> Unit = {},
) {
    val uiState by viewModel.state.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ZBg)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // ── Top Bar ──
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    "tranzo",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = ZTextPrimary,
                    letterSpacing = (-1).sp
                )

                Row(horizontalArrangement = Arrangement.spacedBy(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Outlined.Search, contentDescription = null, tint = ZTextPrimary, modifier = Modifier.size(26.dp))
                    Icon(Icons.Outlined.QrCodeScanner, contentDescription = null, tint = ZTextPrimary, modifier = Modifier.size(26.dp))
                    Icon(Icons.Outlined.Settings, contentDescription = null, tint = ZTextPrimary, modifier = Modifier.size(26.dp).clickable { onNavigateToSettings() })
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ── Portfolio Card ──
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(ZCard)
                    .border(1.dp, ZBorder, RoundedCornerShape(24.dp))
                    .padding(20.dp)
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Portfolio", style = MaterialTheme.typography.bodyLarge, color = ZTextSecondary)
                        Icon(Icons.Outlined.ChevronRight, null, tint = ZTextSecondary, modifier = Modifier.size(20.dp))
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            "$${String.format("%.2f", uiState.totalUsdBalance)}",
                            style = MaterialTheme.typography.displayMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = ZTextPrimary,
                            letterSpacing = (-1).sp
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Icon(Icons.Outlined.VisibilityOff, null, tint = ZTextSecondary, modifier = Modifier.size(24.dp))
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text("-0.99% ($0.096) Today", style = MaterialTheme.typography.bodyMedium, color = ZRed)

                    Spacer(modifier = Modifier.height(24.dp))

                    // Action Buttons
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        ZerionActionButton(icon = Icons.Outlined.Download, text = "Fund", onClick = {}, modifier = Modifier.weight(1f))
                        ZerionActionButton(icon = Icons.Outlined.Send, text = "Send", onClick = onNavigateToTransfer, modifier = Modifier.weight(1f))
                        ZerionActionButton(icon = Icons.Outlined.SwapHoriz, text = "Swap", onClick = onNavigateToSwap, isPrimary = true, modifier = Modifier.weight(1f))
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // ── My Wallets ──
            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("My Wallets", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Medium, color = ZTextPrimary)
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Icon(Icons.Outlined.Edit, null, tint = ZTextSecondary, modifier = Modifier.size(20.dp))
                    Icon(Icons.Outlined.Add, null, tint = ZTextSecondary, modifier = Modifier.size(24.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(modifier = Modifier.padding(horizontal = 20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                ZerionWalletCard(
                    name = "Base Smart Account",
                    address = "0xeBcE...F4Fc",
                    balance = "$${String.format("%.2f", uiState.totalUsdBalance)}",
                    change = "-1.05%",
                    isUp = false,
                    color = Color(0xFF4A5AE8)
                )
                ZerionWalletCard(
                    name = "Main Vault",
                    address = "0x5E9A...2A74",
                    balance = "$0.30",
                    change = "+0.54%",
                    isUp = true,
                    color = Color(0xFF8E5DD5)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // ── Hot In Portfolio ──
            Text("Hot In Portfolio", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Medium, color = ZTextPrimary, modifier = Modifier.padding(horizontal = 20.dp))
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .clip(RoundedCornerShape(32.dp))
                    .background(ZCard)
                    .padding(vertical = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("Your tokens are chilling—for now!", style = MaterialTheme.typography.bodyMedium, color = ZTextSecondary)
            }

            Spacer(modifier = Modifier.height(32.dp))

            // ── Activity ──
            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("Activity", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Medium, color = ZTextPrimary)
                Icon(Icons.Outlined.ChevronRight, null, tint = ZTextSecondary, modifier = Modifier.size(20.dp))
            }
            
            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(40.dp).clip(CircleShape).background(Color(0xFF2C3B8D)), contentAlignment = Alignment.Center) {
                        Icon(Icons.Outlined.Send, null, tint = Color.White, modifier = Modifier.size(18.dp))
                    }
                    Text("Send", style = MaterialTheme.typography.bodyLarge, color = ZTextPrimary, fontWeight = FontWeight.Medium)
                }
                Text("-65 USDC", style = MaterialTheme.typography.bodyLarge, color = ZTextSecondary, fontWeight = FontWeight.Medium)
            }

            Spacer(modifier = Modifier.height(120.dp)) // Nav bar padding
        }
    }
}

@Composable
private fun ZerionActionButton(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit,
    isPrimary: Boolean = false,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(48.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(if (isPrimary) Color.White else ZBorder)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, null, tint = if (isPrimary) Color.Black else Color.White, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(text, style = MaterialTheme.typography.labelLarge, color = if (isPrimary) Color.Black else Color.White, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun ZerionWalletCard(
    name: String,
    address: String,
    balance: String,
    change: String,
    isUp: Boolean,
    color: Color
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(ZCard)
            .border(1.dp, ZBorder, RoundedCornerShape(20.dp))
            .clickable {}
            .padding(16.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp), verticalAlignment = Alignment.CenterVertically) {
                // Avatar representation
                Box(modifier = Modifier.size(48.dp).clip(RoundedCornerShape(12.dp)).background(color), contentAlignment = Alignment.Center) {
                    Icon(Icons.Outlined.Wallet, null, tint = Color.White)
                }
                
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(name, style = MaterialTheme.typography.bodyMedium, color = ZTextSecondary)
                        Text("•", style = MaterialTheme.typography.bodyMedium, color = ZTextSecondary)
                        Text(address, style = MaterialTheme.typography.bodyMedium, color = ZTextSecondary)
                    }
                    Text(balance, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Medium, color = ZTextPrimary)
                }
            }
            
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(change, style = MaterialTheme.typography.bodyMedium, color = if (isUp) ZGreen else ZRed)
                Icon(Icons.Outlined.ChevronRight, null, tint = ZTextSecondary, modifier = Modifier.size(20.dp))
            }
        }
    }
}
