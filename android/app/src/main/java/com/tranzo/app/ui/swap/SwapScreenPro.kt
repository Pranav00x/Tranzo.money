package com.tranzo.app.ui.swap

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tranzo.app.ui.components.TranzoButton
import com.tranzo.app.ui.theme.TranzoColors

@Composable
fun SwapScreenPro(
    onBack: () -> Unit = {},
    onSwapComplete: () -> Unit = {},
) {
    var fromToken by remember { mutableStateOf("USDC") }
    var toToken by remember { mutableStateOf("ETH") }
    var fromAmount by remember { mutableStateOf("100") }
    var toAmount by remember { mutableStateOf("0.058") }
    var isSwapping by remember { mutableStateOf(false) }

    val swapRotation by animateFloatAsState(
        targetValue = if (isSwapping) 180f else 0f,
        animationSpec = spring(dampingRatio = 0.6f)
    )

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
                Text("Swap", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = TranzoColors.TextPrimary)
                Box(modifier = Modifier.size(40.dp))
            }

            // From token
            Text("From", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, color = TranzoColors.TextPrimary)
            SwapTokenCard(
                token = fromToken,
                amount = fromAmount,
                onAmountChange = { fromAmount = it },
                onTokenClick = { },
                usdValue = "$${fromAmount.toDoubleOrNull() ?: 0.0}"
            )

            // Swap button
            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(48.dp)
                    .background(TranzoColors.PrimaryBlue, shape = CircleShape)
                    .clickable { isSwapping = !isSwapping },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.SwapVert,
                    contentDescription = "Swap",
                    tint = Color.White,
                    modifier = Modifier
                        .size(24.dp)
                        .rotate(swapRotation)
                )
            }

            // To token
            Text("To", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, color = TranzoColors.TextPrimary)
            SwapTokenCard(
                token = toToken,
                amount = toAmount,
                onAmountChange = { },
                onTokenClick = { },
                usdValue = "$${toAmount.toDoubleOrNull()?.let { it * 1724 } ?: 0.0}",
                enabled = false
            )

            // Exchange rate
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
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Exchange rate", style = MaterialTheme.typography.bodySmall, color = TranzoColors.TextSecondary)
                        Text("1 USDC = 0.000058 ETH", style = MaterialTheme.typography.bodySmall, color = TranzoColors.TextPrimary, fontWeight = FontWeight.SemiBold)
                    }
                    Divider(thickness = 1.dp, color = TranzoColors.TextTertiary.copy(alpha = 0.2f))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Price impact", style = MaterialTheme.typography.bodySmall, color = TranzoColors.TextSecondary)
                        Text("0.23%", style = MaterialTheme.typography.bodySmall, color = TranzoColors.Success, fontWeight = FontWeight.SemiBold)
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            TranzoButton(
                text = if (isSwapping) "Processing..." else "Review Swap",
                onClick = { onSwapComplete() },
                enabled = !isSwapping,
                isLoading = isSwapping,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun SwapTokenCard(
    token: String,
    amount: String,
    onAmountChange: (String) -> Unit,
    onTokenClick: () -> Unit,
    usdValue: String,
    enabled: Boolean = true,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(),
        shape = RoundedCornerShape(12.dp),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(enabled = enabled) { onTokenClick() },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .background(TranzoColors.PrimaryBlue.copy(alpha = 0.1f), shape = CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            token.take(1),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = TranzoColors.PrimaryBlue
                        )
                    }
                    Text(
                        token,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = TranzoColors.TextPrimary
                    )
                }
                if (enabled) {
                    Icon(Icons.Outlined.KeyboardArrowDown, null, tint = TranzoColors.TextTertiary)
                }
            }

            if (enabled) {
                TextField(
                    value = amount,
                    onValueChange = { if (it.all { c -> c.isDigit() || c == '.' }) onAmountChange(it) },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("0.00") },
                    textStyle = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold, color = TranzoColors.TextPrimary),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent
                    ),
                    singleLine = true
                )
            } else {
                Text(
                    amount,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = TranzoColors.TextPrimary
                )
            }

            Text(
                usdValue,
                style = MaterialTheme.typography.bodySmall,
                color = TranzoColors.TextSecondary
            )
        }
    }
}

import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material3.Divider
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
