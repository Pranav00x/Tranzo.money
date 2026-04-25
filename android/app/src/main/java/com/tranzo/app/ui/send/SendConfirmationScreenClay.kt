package com.tranzo.app.ui.send

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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tranzo.app.ui.components.ClayButton
import com.tranzo.app.ui.components.ClayCard
import com.tranzo.app.ui.theme.TranzoColors

/**
 * Claymorphism Send Confirmation — Baby blue bg, white summary card, solid blue confirm.
 */
@Composable
fun SendConfirmationScreenClay(
    recipient: String = "0x742d...",
    amount: String = "500",
    token: String = "USDC",
    viewModel: SendViewModel = hiltViewModel(),
    onConfirm: () -> Unit = {},
    onCancel: () -> Unit = {},
) {
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
            Spacer(modifier = Modifier.height(40.dp))

            Text(
                "Confirm Transfer",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = TranzoColors.TextPrimary,
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Summary card
            ClayCard(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    // Amount
                    Text(
                        amount,
                        style = MaterialTheme.typography.displayMedium,
                        fontWeight = FontWeight.Bold,
                        color = TranzoColors.TextPrimary,
                        fontSize = 48.sp,
                    )
                    Text(
                        token,
                        style = MaterialTheme.typography.bodyMedium,
                        color = TranzoColors.TextSecondary,
                    )

                    HorizontalDivider(color = TranzoColors.DividerGray)

                    // Details
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text("To", style = MaterialTheme.typography.labelSmall, color = TranzoColors.TextTertiary)
                        Text(recipient, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.SemiBold, color = TranzoColors.TextPrimary)
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text("Network Fee", style = MaterialTheme.typography.labelSmall, color = TranzoColors.TextTertiary)
                        Text("< $0.10", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.SemiBold, color = TranzoColors.ClayGreen)
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text("Total", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = TranzoColors.TextPrimary)
                        Text(
                            "~ ${amount.toDoubleOrNull()?.plus(0.05) ?: amount}",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = TranzoColors.ClayBlue,
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            ClayButton(
                text = "Confirm & Send",
                onClick = {
                    viewModel.sendToken(recipient, token, amount)
                    onConfirm()
                },
            )

            // Cancel
            TextButton(
                onClick = onCancel,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    "Cancel",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = TranzoColors.TextSecondary,
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
