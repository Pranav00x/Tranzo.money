package com.tranzo.app.ui.send

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.outlined.ArrowUpward
import androidx.compose.material.icons.outlined.AttachMoney
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tranzo.app.ui.components.*
import com.tranzo.app.ui.theme.TranzoColors

@Composable
fun SendScreenProClay(
    viewModel: SendViewModel = hiltViewModel(),
    onConfirm: () -> Unit = {},
) {
    var recipient by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var selectedToken by remember { mutableStateOf("USDC") }
    val uiState by viewModel.state.collectAsState()

    var showContent by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { showContent = true }

    LaunchedEffect(uiState.isSent) {
        if (uiState.isSent) { onConfirm(); viewModel.reset() }
    }

    val contentAlpha by animateFloatAsState(
        targetValue = if (showContent) 1f else 0f,
        animationSpec = tween(800), label = "fade"
    )

    Box(Modifier.fillMaxSize().background(TranzoColors.ClayBackground)) {
        Column(
            Modifier.fillMaxSize().verticalScroll(rememberScrollState()).alpha(contentAlpha),
        ) {
            Spacer(Modifier.height(24.dp))

            // ── Header ──
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(14.dp),
            ) {
                ClayIconPill(color = TranzoColors.ClayBlue, size = 48.dp, cornerRadius = 16.dp) {
                    Icon(Icons.Outlined.ArrowUpward, null, tint = Color.White, modifier = Modifier.size(24.dp))
                }
                Column {
                    Text("Send Crypto", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.ExtraBold, color = TranzoColors.TextPrimary)
                    Text("Transfer tokens securely", style = MaterialTheme.typography.bodySmall, color = TranzoColors.TextSecondary)
                }
            }

            Spacer(Modifier.height(32.dp))

            // ── Asset Selection ──
            Text("ASSET", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = TranzoColors.TextTertiary, letterSpacing = 1.5.sp, modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp))

            ClayCard(Modifier.fillMaxWidth().padding(horizontal = 24.dp), cornerRadius = 22.dp) {
                Row(Modifier.fillMaxWidth().clickable {}.padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Row(horizontalArrangement = Arrangement.spacedBy(14.dp), verticalAlignment = Alignment.CenterVertically) {
                        ClayIconPill(color = TranzoColors.ClayBlue, size = 44.dp, cornerRadius = 15.dp) {
                            Text("$", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        }
                        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                            Text(selectedToken, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = TranzoColors.TextPrimary)
                            Text("Base Sepolia", style = MaterialTheme.typography.labelSmall, color = TranzoColors.TextTertiary)
                        }
                    }
                    Icon(Icons.AutoMirrored.Outlined.ArrowForward, null, tint = TranzoColors.TextTertiary, modifier = Modifier.size(18.dp))
                }
            }

            Spacer(Modifier.height(24.dp))

            // ── Transfer Details ──
            Text("TRANSFER DETAILS", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = TranzoColors.TextTertiary, letterSpacing = 1.5.sp, modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp))

            ClayCard(Modifier.fillMaxWidth().padding(horizontal = 24.dp), cornerRadius = 22.dp) {
                Column(Modifier.fillMaxWidth().padding(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    
                    ClayTextField(
                        value = recipient, 
                        onValueChange = { recipient = it }, 
                        placeholder = "Recipient Address (0x...)",
                        leadingIcon = { Icon(Icons.Outlined.Person, null, tint = TranzoColors.ClayBlue, modifier = Modifier.size(20.dp)) }
                    )

                    HorizontalDivider(color = TranzoColors.DividerGray)

                    ClayTextField(
                        value = amount, 
                        onValueChange = { amount = it }, 
                        placeholder = "Amount (0.00)",
                        keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        leadingIcon = { Icon(Icons.Outlined.AttachMoney, null, tint = TranzoColors.ClayGreen, modifier = Modifier.size(20.dp)) }
                    )

                    // Quick Amounts
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        listOf("25", "50", "100", "MAX").forEach { q ->
                            AssistChip(
                                onClick = { amount = if(q == "MAX") "500" else q }, 
                                label = { Text(if(q == "MAX") q else "$$q", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold) },
                                modifier = Modifier.weight(1f), 
                                shape = RoundedCornerShape(12.dp),
                                colors = AssistChipDefaults.assistChipColors(containerColor = TranzoColors.ClayBackgroundAlt, labelColor = TranzoColors.TextPrimary),
                                border = AssistChipDefaults.assistChipBorder(enabled = true, borderColor = Color.Transparent)
                            )
                        }
                    }
                }
            }

            if (uiState.error != null) {
                Spacer(Modifier.height(16.dp))
                Box(Modifier.fillMaxWidth().padding(horizontal = 24.dp).clip(RoundedCornerShape(12.dp)).background(TranzoColors.ClayCoralSoft).padding(16.dp)) {
                    Text(uiState.error!!, style = MaterialTheme.typography.bodySmall, color = TranzoColors.ClayCoral, fontWeight = FontWeight.SemiBold)
                }
            }

            Spacer(Modifier.height(32.dp))

            ClayButton(
                text = "Review Transfer", 
                onClick = { viewModel.sendToken(recipient, selectedToken, amount) },
                enabled = recipient.isNotBlank() && amount.isNotBlank() && !uiState.isLoading, 
                isLoading = uiState.isLoading,
                modifier = Modifier.padding(horizontal = 24.dp)
            )

            Spacer(Modifier.height(100.dp))
        }
    }
}
