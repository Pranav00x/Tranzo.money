package com.tranzo.app.ui.dripper

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.weight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.ContentPaste
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tranzo.app.ui.components.TranzoButton
import com.tranzo.app.ui.components.TranzoTextField
import com.tranzo.app.ui.theme.TranzoColors

@Composable
fun CreateStreamScreen(
    viewModel: DripperViewModel = hiltViewModel(),
    onBack: () -> Unit = {},
    onCreateSuccess: () -> Unit = {},
) {
    val state by viewModel.state.collectAsState()
    var recipientAddress by remember { mutableStateOf("") }
    var totalAmount by remember { mutableStateOf("") }
    var durationDays by remember { mutableStateOf("") }
    var selectedToken by remember { mutableStateOf("USDC") }
    var createRequested by remember { mutableStateOf(false) }

    val tokenAddressMap = mapOf(
        "USDC" to "0x036CbD53842c5426634e7929541eC2318f3dCF7e",
        "USDT" to "0x0000000000000000000000000000000000000000",
    )

    val ratePerDay = if (totalAmount.isNotEmpty() && durationDays.isNotEmpty()) {
        val total = totalAmount.toDoubleOrNull()
        val days = durationDays.toIntOrNull()
        if (total != null && days != null && days > 0) String.format("%.2f", total / days) else "-"
    } else "-"

    val ratePerSecond = if (totalAmount.isNotEmpty() && durationDays.isNotEmpty()) {
        val total = totalAmount.toDoubleOrNull()
        val seconds = durationDays.toIntOrNull()?.times(86400)
        if (total != null && seconds != null && seconds > 0) String.format("%.8f", total / seconds) else "-"
    } else "-"

    LaunchedEffect(state.isLoading, state.error, createRequested) {
        if (createRequested && !state.isLoading) {
            if (state.error == null) {
                onCreateSuccess()
            }
            createRequested = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(TranzoColors.Background)
            .systemBarsPadding(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                    contentDescription = "Back",
                )
            }
            Text(
                text = "Create Stream",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.weight(1f),
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = TranzoColors.PaleTeal.copy(alpha = 0.5f),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Info,
                        contentDescription = null,
                        tint = TranzoColors.PrimaryBlack,
                        modifier = Modifier.size(20.dp),
                    )
                    Text(
                        text = "Create a stream. Recipient can withdraw anytime.",
                        style = MaterialTheme.typography.bodySmall,
                        color = TranzoColors.TextSecondary,
                        modifier = Modifier.padding(start = 12.dp),
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            TranzoTextField(
                value = recipientAddress,
                onValueChange = { recipientAddress = it },
                label = "Recipient Address",
                placeholder = "0x... smart account address",
                trailingIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Outlined.ContentPaste,
                            contentDescription = "Paste",
                            tint = TranzoColors.PrimaryBlack,
                            modifier = Modifier.size(20.dp),
                        )
                    }
                },
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Token",
                style = MaterialTheme.typography.labelMedium,
                color = TranzoColors.TextSecondary,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("USDC", "USDT").forEach { token ->
                    FilterChip(
                        selected = selectedToken == token,
                        onClick = { selectedToken = token },
                        label = { Text(token) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = TranzoColors.PrimaryBlack,
                            selectedLabelColor = TranzoColors.White,
                        ),
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            TranzoTextField(
                value = totalAmount,
                onValueChange = { if (it.all { c -> c.isDigit() || c == '.' }) totalAmount = it },
                label = "Total Amount ($selectedToken)",
                placeholder = "e.g. 12000",
            )

            Spacer(modifier = Modifier.height(20.dp))

            TranzoTextField(
                value = durationDays,
                onValueChange = { if (it.all { c -> c.isDigit() }) durationDays = it },
                label = "Duration (days)",
                placeholder = "e.g. 60",
            )

            Spacer(modifier = Modifier.height(24.dp))

            if (ratePerDay != "-") {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = TranzoColors.CardSurface,
                    tonalElevation = 1.dp,
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text(
                            text = "Stream Preview",
                            style = MaterialTheme.typography.headlineSmall,
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        PreviewRow("Rate per day", "$ratePerDay $selectedToken")
                        Spacer(modifier = Modifier.height(8.dp))
                        PreviewRow("Rate per second", "$ratePerSecond $selectedToken")
                        Spacer(modifier = Modifier.height(8.dp))
                        PreviewRow("Duration", "$durationDays days")
                        Spacer(modifier = Modifier.height(8.dp))
                        PreviewRow("Total", "$totalAmount $selectedToken")

                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 12.dp),
                            color = TranzoColors.DividerGray,
                        )

                        PreviewRow("Gas Fee", "Sponsored")
                    }
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
        }

        TranzoButton(
            text = "Create Stream",
            onClick = {
                val days = durationDays.toIntOrNull()
                val tokenAddress = tokenAddressMap[selectedToken]
                if (days != null && days > 0 && tokenAddress != null) {
                    createRequested = true
                    viewModel.createStream(
                        recipientAddress = recipientAddress,
                        tokenAddress = tokenAddress,
                        totalAmount = totalAmount,
                        durationDays = days,
                    )
                }
            },
            enabled = recipientAddress.startsWith("0x") &&
                totalAmount.isNotBlank() &&
                durationDays.isNotBlank() &&
                !state.isLoading,
            isLoading = state.isLoading && createRequested,
            modifier = Modifier.padding(24.dp),
        )
    }
}

@Composable
private fun PreviewRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
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
        )
    }
}
