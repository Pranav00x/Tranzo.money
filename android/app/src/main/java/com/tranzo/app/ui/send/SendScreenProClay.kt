package com.tranzo.app.ui.send

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowUpward
import androidx.compose.material.icons.outlined.AttachMoney
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
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
        Canvas(Modifier.fillMaxSize()) {
            drawCircle(TranzoColors.ClayBlue.copy(alpha = 0.06f), 220f, Offset(size.width * 0.85f, size.height * 0.1f))
            drawCircle(TranzoColors.ClayGreen.copy(alpha = 0.04f), 180f, Offset(size.width * 0.1f, size.height * 0.7f))
        }

        Column(
            Modifier.fillMaxSize().verticalScroll(rememberScrollState()).alpha(contentAlpha).padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(Modifier.height(24.dp))

            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                ClayIconPill(color = TranzoColors.ClayBlue, size = 52.dp, cornerRadius = 18.dp) {
                    Icon(Icons.Outlined.ArrowUpward, null, tint = Color.White, modifier = Modifier.size(26.dp))
                }
                Column {
                    Text("Send Crypto", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.ExtraBold, color = TranzoColors.TextPrimary)
                    Text("Transfer tokens securely", style = MaterialTheme.typography.bodySmall, color = TranzoColors.TextSecondary)
                }
            }

            Spacer(Modifier.height(8.dp))

            // Token selector
            ClayCard(Modifier.fillMaxWidth(), cornerRadius = 20.dp) {
                Row(Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                        ClayIconPill(color = TranzoColors.ClayBlue, size = 44.dp, cornerRadius = 15.dp) {
                            Text("$", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        }
                        Column {
                            Text(selectedToken, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = TranzoColors.TextPrimary)
                            Text("Base Sepolia", style = MaterialTheme.typography.labelSmall, color = TranzoColors.TextTertiary)
                        }
                    }
                    Box(Modifier.clip(RoundedCornerShape(10.dp)).background(TranzoColors.ClayBlueSoft).padding(horizontal = 12.dp, vertical = 6.dp)) {
                        Text("Change", style = MaterialTheme.typography.labelSmall, color = TranzoColors.ClayBlue, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                    }
                }
            }

            Text("RECIPIENT", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = TranzoColors.TextTertiary, letterSpacing = 1.5.sp)

            ClayTextField(value = recipient, onValueChange = { recipient = it }, placeholder = "Wallet address (0x...)",
                leadingIcon = { Icon(Icons.Outlined.Person, null, tint = TranzoColors.ClayBlue, modifier = Modifier.size(20.dp)) })

            Text("AMOUNT", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = TranzoColors.TextTertiary, letterSpacing = 1.5.sp)

            ClayTextField(value = amount, onValueChange = { amount = it }, placeholder = "0.00",
                keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = KeyboardType.Decimal),
                leadingIcon = { Icon(Icons.Outlined.AttachMoney, null, tint = TranzoColors.ClayGreen, modifier = Modifier.size(20.dp)) })

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("25", "50", "100", "500").forEach { q ->
                    AssistChip(onClick = { amount = q }, label = { Text("$$q", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold) },
                        modifier = Modifier.weight(1f), shape = RoundedCornerShape(14.dp),
                        colors = AssistChipDefaults.assistChipColors(containerColor = TranzoColors.ClayCard, labelColor = TranzoColors.TextPrimary),
                        border = AssistChipDefaults.assistChipBorder(enabled = true, borderColor = TranzoColors.ClayInputBorder))
                }
            }

            if (uiState.error != null) {
                Box(Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)).background(TranzoColors.ClayCoralSoft).padding(12.dp)) {
                    Text(uiState.error!!, style = MaterialTheme.typography.bodySmall, color = TranzoColors.ClayCoral, fontWeight = FontWeight.SemiBold)
                }
            }

            Spacer(Modifier.height(24.dp))

            ClayButton(text = "Review Transfer", onClick = { viewModel.sendToken(recipient, selectedToken, amount) },
                enabled = recipient.isNotBlank() && amount.isNotBlank() && !uiState.isLoading, isLoading = uiState.isLoading)

            Spacer(Modifier.height(32.dp))
        }
    }
}
