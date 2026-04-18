package com.tranzo.app.ui.send

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tranzo.app.ui.components.TranzoButton
import com.tranzo.app.ui.components.TranzoTextField
import com.tranzo.app.ui.theme.TranzoColors

@Composable
fun SendScreenPro(
    onBack: () -> Unit = {},
    onReview: (to: String, token: String, amount: String) -> Unit = { _, _, _ -> },
) {
    var recipientAddress by remember { mutableStateOf("") }
    var selectedToken by remember { mutableStateOf("USDC") }
    var amount by remember { mutableStateOf("") }

    val isValid = recipientAddress.length > 20 && amount.toDoubleOrNull() ?: 0.0 > 0

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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack, modifier = Modifier.size(40.dp)) {
                    Icon(
                        imageVector = Icons.Outlined.ArrowBack,
                        contentDescription = "Back",
                        tint = TranzoColors.TextSecondary,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Text(
                    "Send",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = TranzoColors.TextPrimary
                )
                Box(modifier = Modifier.size(40.dp))
            }

            // Token selector
            Text(
                "Token",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = TranzoColors.TextPrimary
            )

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { },
                shape = RoundedCornerShape(12.dp),
                color = Color.White
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(TranzoColors.PrimaryBlue.copy(alpha = 0.1f), shape = CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                selectedToken.take(1),
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = TranzoColors.PrimaryBlue
                            )
                        }
                        Column {
                            Text(
                                selectedToken,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = TranzoColors.TextPrimary
                            )
                            Text(
                                "Available: \$2,500.00",
                                style = MaterialTheme.typography.labelSmall,
                                color = TranzoColors.TextTertiary
                            )
                        }
                    }
                    Icon(
                        imageVector = Icons.Outlined.KeyboardArrowDown,
                        contentDescription = null,
                        tint = TranzoColors.TextTertiary
                    )
                }
            }

            // Recipient address
            Text(
                "Recipient Address",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = TranzoColors.TextPrimary
            )

            TranzoTextField(
                value = recipientAddress,
                onValueChange = { recipientAddress = it },
                placeholder = "0x1234...abcd",
                label = "Wallet address",
                modifier = Modifier.fillMaxWidth()
            )

            // Amount
            Text(
                "Amount",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = TranzoColors.TextPrimary
            )

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                color = Color.White
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    TextField(
                        value = amount,
                        onValueChange = { if (it.all { c -> c.isDigit() || c == '.' }) amount = it },
                        modifier = Modifier.weight(1f),
                        placeholder = { Text("0.00") },
                        textStyle = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = TranzoColors.TextPrimary
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent
                        ),
                        singleLine = true
                    )
                    Text(
                        "MAX",
                        style = MaterialTheme.typography.labelMedium,
                        color = TranzoColors.PrimaryBlue,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable { amount = "2500" }
                    )
                }
            }

            // Fee info
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                color = TranzoColors.SurfaceLight
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Network fee", style = MaterialTheme.typography.bodySmall, color = TranzoColors.TextSecondary)
                        Text("< $1.00", style = MaterialTheme.typography.bodySmall, color = TranzoColors.TextPrimary, fontWeight = FontWeight.SemiBold)
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("You send", style = MaterialTheme.typography.bodySmall, color = TranzoColors.TextSecondary)
                        Text("$${amount.ifBlank { "0.00" }}", style = MaterialTheme.typography.bodySmall, color = TranzoColors.TextPrimary, fontWeight = FontWeight.SemiBold)
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            TranzoButton(
                text = "Review Transaction",
                onClick = {
                    if (isValid) onReview(recipientAddress, selectedToken, amount)
                },
                enabled = isValid,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
