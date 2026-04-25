package com.tranzo.app.ui.send

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AttachMoney
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tranzo.app.ui.components.ClayButton
import com.tranzo.app.ui.components.ClayCard
import com.tranzo.app.ui.components.ClayTextField
import com.tranzo.app.ui.theme.TranzoColors

/**
 * Claymorphism Send Screen — Baby blue bg, white form cards, solid blue CTA.
 */
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
        if (uiState.isSent) {
            onConfirm()
            viewModel.reset()
        }
    }

    val contentAlpha by animateFloatAsState(
        targetValue = if (showContent) 1f else 0f,
        animationSpec = tween(800),
        label = "content fade in"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(TranzoColors.ClayBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .alpha(contentAlpha)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // Header with icon
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .shadow(
                            elevation = 12.dp,
                            shape = RoundedCornerShape(16.dp),
                            ambientColor = TranzoColors.ClayBlue.copy(alpha = 0.3f),
                        )
                        .clip(RoundedCornerShape(16.dp))
                        .background(TranzoColors.ClayBlue),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        Icons.Outlined.Send,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(22.dp),
                    )
                }
                Text(
                    "Send Crypto",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = TranzoColors.TextPrimary,
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Token selector
            ClayCard(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(14.dp))
                                .background(TranzoColors.ClayBlue),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                "$",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                            )
                        }
                        Column {
                            Text(
                                selectedToken,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = TranzoColors.TextPrimary,
                            )
                            Text(
                                "Base Sepolia",
                                style = MaterialTheme.typography.labelSmall,
                                color = TranzoColors.TextTertiary,
                            )
                        }
                    }
                    Text(
                        "Change",
                        style = MaterialTheme.typography.labelSmall,
                        color = TranzoColors.ClayBlue,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
            }

            // Recipient
            Text(
                "Recipient",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.SemiBold,
                color = TranzoColors.TextSecondary,
            )

            ClayTextField(
                value = recipient,
                onValueChange = { recipient = it },
                placeholder = "Wallet address (0x...)",
                leadingIcon = {
                    Icon(
                        Icons.Outlined.Person,
                        contentDescription = null,
                        tint = TranzoColors.ClayBlue,
                        modifier = Modifier.size(20.dp),
                    )
                },
            )

            // Amount
            Text(
                "Amount",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.SemiBold,
                color = TranzoColors.TextSecondary,
            )

            ClayTextField(
                value = amount,
                onValueChange = { amount = it },
                placeholder = "0.00",
                keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                    keyboardType = KeyboardType.Decimal
                ),
                leadingIcon = {
                    Icon(
                        Icons.Outlined.AttachMoney,
                        contentDescription = null,
                        tint = TranzoColors.ClayGreen,
                        modifier = Modifier.size(20.dp),
                    )
                },
            )

            // Quick amounts
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                listOf("25", "50", "100", "500").forEach { quickAmount ->
                    AssistChip(
                        onClick = { amount = quickAmount },
                        label = {
                            Text(
                                "$$quickAmount",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.SemiBold,
                            )
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(14.dp),
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = Color.White,
                            labelColor = TranzoColors.TextPrimary,
                        ),
                        border = AssistChipDefaults.assistChipBorder(
                            enabled = true,
                            borderColor = TranzoColors.ClayInputBorder,
                        ),
                    )
                }
            }

            // Error
            if (uiState.error != null) {
                Text(
                    uiState.error!!,
                    style = MaterialTheme.typography.bodySmall,
                    color = TranzoColors.Error,
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            ClayButton(
                text = "Review Transfer",
                onClick = {
                    viewModel.sendToken(recipient, selectedToken, amount)
                },
                enabled = recipient.isNotBlank() && amount.isNotBlank() && !uiState.isLoading,
                isLoading = uiState.isLoading,
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
