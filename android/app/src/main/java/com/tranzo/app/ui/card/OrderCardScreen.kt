package com.tranzo.app.ui.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.tranzo.app.ui.components.TranzoButton
import com.tranzo.app.ui.theme.TranzoColors

/**
 * Order Card screen — KYC verification + card type selection.
 *
 * Flow: Choose card type → KYC (identity verification) → Confirm →
 *       Virtual card issued instantly → Physical card shipped.
 */
@Composable
fun OrderCardScreen(
    viewModel: CardViewModel = hiltViewModel(),
    onBack: () -> Unit = {},
    onOrder: () -> Unit = {},
) {
    val state by viewModel.state.collectAsState()
    var selectedType by remember { mutableStateOf("virtual") }

    // Navigate back or to card screen on success
    LaunchedEffect(state.orderSuccess) {
        if (state.orderSuccess) {
            onOrder()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(TranzoColors.Background)
            .systemBarsPadding(),
    ) {
        // ── Top Bar ──────────────────────────────────────────────
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
                text = "Get Your Card",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.weight(1f),
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // ── Card Type Selection ──────────────────────────────
            Text(
                text = "Choose Your Card",
                style = MaterialTheme.typography.headlineSmall,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Your crypto, your keys — now spendable everywhere",
                style = MaterialTheme.typography.bodySmall,
                color = TranzoColors.TextSecondary,
            )
            Spacer(modifier = Modifier.height(20.dp))

            // Virtual card option
            CardTypeOption(
                title = "Virtual Card",
                subtitle = "Instant • Free • Apple Pay ready",
                icon = Icons.Outlined.Smartphone,
                features = listOf(
                    "Issued in under 60 seconds",
                    "Add to Apple Pay / Google Pay",
                    "Online purchases worldwide",
                    "No monthly fees",
                ),
                isSelected = selectedType == "virtual",
                onClick = { selectedType = "virtual" },
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Physical card option
            CardTypeOption(
                title = "Physical Metal Card",
                subtitle = "Premium • Free shipping • Tap to pay",
                icon = Icons.Outlined.CreditCard,
                features = listOf(
                    "Premium metal Visa card",
                    "Contactless NFC payments",
                    "ATM withdrawals worldwide",
                    "Free shipping to 100+ countries",
                ),
                isSelected = selectedType == "physical",
                onClick = { selectedType = "physical" },
            )

            Spacer(modifier = Modifier.height(28.dp))

            // ── How Settlement Works ─────────────────────────────
            Text(
                text = "How It Works",
                style = MaterialTheme.typography.headlineSmall,
            )
            Spacer(modifier = Modifier.height(12.dp))

            Surface(
                shape = RoundedCornerShape(16.dp),
                color = TranzoColors.PaleTeal,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                ) {
                    SettlementStep(
                        step = "1",
                        title = "You swipe your card",
                        description = "At any Visa merchant — coffee, groceries, flights",
                    )
                    SettlementStep(
                        step = "2",
                        title = "Smart contract authorizes",
                        description = "Your ERC-4337 account approves the spend amount",
                    )
                    SettlementStep(
                        step = "3",
                        title = "Instant crypto-to-fiat",
                        description = "USDC/USDT → fiat via Kulipa & Reap rails",
                    )
                    SettlementStep(
                        step = "4",
                        title = "Merchant gets paid",
                        description = "Settled in real-time. You keep your keys.",
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // ── KYC Requirement Badge ────────────────────────────
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = TranzoColors.WarningLight,
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = Icons.Outlined.VerifiedUser,
                        contentDescription = null,
                        tint = TranzoColors.Warning,
                        modifier = Modifier.size(24.dp),
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Identity Verification Required",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                        )
                        Text(
                            text = "Quick KYC — takes under 2 minutes",
                            style = MaterialTheme.typography.bodySmall,
                            color = TranzoColors.TextSecondary,
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }

        // ── CTA Button ───────────────────────────────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp),
        ) {
            TranzoButton(
                text = if (selectedType == "virtual")
                    "Get Virtual Card — Free"
                else
                    "Order Physical Card — Free",
                isLoading = state.isOrdering,
                onClick = { 
                    viewModel.orderCard(type = selectedType, cardholderName = "USER") 
                },
            )
        }
    }
}

@Composable
private fun CardTypeOption(
    title: String,
    subtitle: String,
    icon: ImageVector,
    features: List<String>,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    val borderColor = if (isSelected) TranzoColors.PrimaryBlack else TranzoColors.BorderGray

    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        color = TranzoColors.CardSurface,
        border = ButtonDefaults.outlinedButtonBorder(enabled = true).copy(
            width = if (isSelected) 2.dp else 1.dp,
            brush = androidx.compose.ui.graphics.SolidColor(borderColor),
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(
                            if (isSelected) TranzoColors.PaleTeal
                            else TranzoColors.LightGray
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = if (isSelected) TranzoColors.PrimaryBlack
                        else TranzoColors.TextSecondary,
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = TranzoColors.TextSecondary,
                    )
                }
                if (isSelected) {
                    Icon(
                        imageVector = Icons.Outlined.CheckCircle,
                        contentDescription = null,
                        tint = TranzoColors.PrimaryBlack,
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            features.forEach { feature ->
                Row(
                    modifier = Modifier.padding(vertical = 3.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Check,
                        contentDescription = null,
                        tint = TranzoColors.PrimaryBlack,
                        modifier = Modifier.size(16.dp),
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = feature,
                        style = MaterialTheme.typography.bodySmall,
                        color = TranzoColors.TextSecondary,
                    )
                }
            }
        }
    }
}

@Composable
private fun SettlementStep(
    step: String,
    title: String,
    description: String,
) {
    Row(
        modifier = Modifier.padding(vertical = 8.dp),
        verticalAlignment = Alignment.Top,
    ) {
        Box(
            modifier = Modifier
                .size(28.dp)
                .clip(CircleShape)
                .background(TranzoColors.PrimaryBlack),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = step,
                style = MaterialTheme.typography.labelSmall,
                color = TranzoColors.White,
                fontWeight = FontWeight.Bold,
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = TranzoColors.TextSecondary,
            )
        }
    }
}
