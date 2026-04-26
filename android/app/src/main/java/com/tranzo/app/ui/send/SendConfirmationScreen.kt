package com.tranzo.app.ui.send

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tranzo.app.ui.components.TranzoButton
import com.tranzo.app.ui.components.TranzoSecondaryButton
import com.tranzo.app.ui.theme.TranzoColors

@Composable
fun SendConfirmationScreen(
    viewModel: SendViewModel = hiltViewModel(),
    fromAddress: String = "0xYour...Wallet",
    recipientAddress: String = "0x7a3b...f4c2",
    tokenSymbol: String = "USDC",
    amount: String = "100.00",
    usdValue: String = "$100.00",
    onBack: () -> Unit = {},
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.reset()
    }

    if (state.isSent) {
        SuccessContent(
            amount = amount,
            tokenSymbol = tokenSymbol,
            recipientAddress = recipientAddress,
            txHash = state.txHash,
            onDone = onBack,
        )
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(TranzoColors.BackgroundLight)
            .systemBarsPadding(),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(TranzoColors.PrimaryBlue, TranzoColors.AccentCyan),
                    ),
                )
                .padding(horizontal = 24.dp)
                .padding(top = 8.dp, bottom = 32.dp),
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                            contentDescription = "Back",
                            tint = TranzoColors.TextDarkPrimary,
                        )
                    }
                    Text(
                        text = "Review Transfer",
                        style = MaterialTheme.typography.headlineMedium,
                        color = TranzoColors.TextDarkPrimary,
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "$amount $tokenSymbol",
                    style = MaterialTheme.typography.displayMedium,
                    color = TranzoColors.TextDarkPrimary,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = usdValue,
                    style = MaterialTheme.typography.bodyLarge,
                    color = TranzoColors.AccentEmerald,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                )
            }
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .offset(y = (-20).dp)
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .background(TranzoColors.BackgroundLight)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
                .padding(top = 28.dp),
        ) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = TranzoColors.SurfaceLight,
                tonalElevation = 1.dp,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    SummaryRow(label = "From", value = formatAddress(fromAddress))
                    RowDivider()
                    SummaryRow(label = "To", value = formatAddress(recipientAddress))
                    RowDivider()
                    SummaryRow(label = "Token", value = tokenSymbol)
                    RowDivider()
                    SummaryRow(label = "Amount", value = "$amount $tokenSymbol")
                    RowDivider()
                    SummaryRow(label = "USD Value", value = usdValue)
                    RowDivider()
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "Network Fee",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TranzoColors.TextSecondary,
                        )
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = TranzoColors.BackgroundLight,
                        ) {
                            Text(
                                text = "Sponsored",
                                style = MaterialTheme.typography.labelMedium,
                                color = TranzoColors.TextPrimary,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = TranzoColors.BackgroundLight.copy(alpha = 0.5f),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.Top,
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Shield,
                        contentDescription = null,
                        tint = TranzoColors.TextPrimary,
                        modifier = Modifier
                            .size(18.dp)
                            .padding(top = 1.dp),
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Transaction is secured by your smart account.",
                        style = MaterialTheme.typography.bodySmall,
                        color = TranzoColors.TextSecondary,
                        modifier = Modifier.padding(start = 10.dp),
                    )
                }
            }

            state.error?.let {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = it,
                    color = TranzoColors.Error,
                    style = MaterialTheme.typography.bodySmall,
                )
            }

            Spacer(modifier = Modifier.height(28.dp))
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(TranzoColors.BackgroundLight)
                .padding(horizontal = 24.dp)
                .padding(bottom = 24.dp, top = 8.dp),
        ) {
            TranzoButton(
                text = "Confirm & Send",
                onClick = {
                    viewModel.sendToken(recipientAddress, tokenSymbol, amount)
                },
                isLoading = state.isLoading,
                enabled = !state.isLoading,
            )

            Spacer(modifier = Modifier.height(12.dp))

            TranzoSecondaryButton(
                text = "Cancel",
                onClick = onBack,
            )
        }
    }
}

@Composable
private fun SuccessContent(
    amount: String,
    tokenSymbol: String,
    recipientAddress: String,
    txHash: String?,
    onDone: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(TranzoColors.BackgroundLight),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.Outlined.CheckCircle,
                contentDescription = null,
                modifier = Modifier.size(60.dp),
                tint = TranzoColors.TextPrimary,
            )
        }

        Spacer(modifier = Modifier.height(28.dp))

        Text(
            text = "Transfer Sent",
            style = MaterialTheme.typography.headlineLarge,
            color = TranzoColors.TextPrimary,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "$amount $tokenSymbol sent to ${formatAddress(recipientAddress)}",
            style = MaterialTheme.typography.bodyLarge,
            color = TranzoColors.TextSecondary,
            textAlign = TextAlign.Center,
        )

        txHash?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Tx: ${formatAddress(it)}",
                style = MaterialTheme.typography.bodySmall,
                color = TranzoColors.TextTertiary,
                textAlign = TextAlign.Center,
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Surface(
            shape = RoundedCornerShape(20.dp),
            color = TranzoColors.BackgroundLight,
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Outlined.AutoAwesome,
                    contentDescription = null,
                    tint = TranzoColors.TextPrimary,
                    modifier = Modifier.size(16.dp),
                )
                Text(
                    text = " Zero gas fees paid",
                    style = MaterialTheme.typography.labelMedium,
                    color = TranzoColors.TextPrimary,
                    fontWeight = FontWeight.SemiBold,
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        TranzoButton(
            text = "Done",
            onClick = onDone,
        )
    }
}

@Composable
private fun RowDivider() {
    HorizontalDivider(
        modifier = Modifier.padding(vertical = 12.dp),
        color = TranzoColors.DividerGray,
    )
}

@Composable
private fun SummaryRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = TranzoColors.TextSecondary,
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = TranzoColors.TextPrimary,
        )
    }
}

private fun formatAddress(address: String): String {
    return if (address.length > 12) {
        "${address.take(8)}...${address.takeLast(6)}"
    } else {
        address
    }
}
