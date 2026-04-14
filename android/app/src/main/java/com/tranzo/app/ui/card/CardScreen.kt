package com.tranzo.app.ui.card

import androidx.compose.animation.core.*
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tranzo.app.ui.theme.TranzoColors

/**
 * Crypto Card Dashboard — the centerpiece of Tranzo's spend story.
 *
 * Partners:
 *  • Kulipa   — Virtual/Physical Visa card issuance
 *  • Reap     — Card-to-crypto settlement infrastructure
 *  • Immersve — Decentralized card protocol (self-custody spend)
 *
 * Flow: User taps "Get Card" → KYC (if needed) → Virtual card issued →
 *       Add to Apple/Google Pay → Spend anywhere Visa is accepted.
 *       Settlement: crypto → stablecoin → fiat → merchant in real-time.
 *
 * Key design principle: User's funds NEVER leave their smart account
 * until the moment of purchase. True self-custody spending.
 */
@Composable
fun CardScreen(
    onBack: () -> Unit = {},
    onOrderCard: () -> Unit = {},
    onCardDetails: (String) -> Unit = {},
    onManageLimits: () -> Unit = {},
) {
    var hasCard by remember { mutableStateOf(true) }

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
                text = "Tranzo Card",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.weight(1f),
            )
            // Freeze / Settings
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Outlined.Settings,
                    contentDescription = "Card Settings",
                    tint = TranzoColors.TextSecondary,
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            if (hasCard) {
                // ── Virtual Card Display ─────────────────────────
                CardVisual()

                Spacer(modifier = Modifier.height(24.dp))

                // ── Quick Actions ────────────────────────────────
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    CardQuickAction(
                        icon = Icons.Outlined.Visibility,
                        label = "Details",
                        onClick = { onCardDetails("card_1") },
                    )
                    CardQuickAction(
                        icon = Icons.Outlined.AcUnit,
                        label = "Freeze",
                        onClick = {},
                    )
                    CardQuickAction(
                        icon = Icons.Outlined.Tune,
                        label = "Limits",
                        onClick = onManageLimits,
                    )
                    CardQuickAction(
                        icon = Icons.Outlined.Contactless,
                        label = "Apple Pay",
                        onClick = {},
                    )
                }

                Spacer(modifier = Modifier.height(28.dp))

                // ── Spending Limits ──────────────────────────────
                Text(
                    text = "Spending Limits",
                    style = MaterialTheme.typography.headlineSmall,
                )
                Spacer(modifier = Modifier.height(12.dp))

                SpendingLimitCard(
                    label = "Daily",
                    spent = 120.0,
                    limit = 1000.0,
                    currency = "USD",
                )
                Spacer(modifier = Modifier.height(8.dp))
                SpendingLimitCard(
                    label = "Monthly",
                    spent = 2400.0,
                    limit = 10000.0,
                    currency = "USD",
                )

                Spacer(modifier = Modifier.height(28.dp))

                // ── Recent Card Transactions ─────────────────────
                Text(
                    text = "Recent Transactions",
                    style = MaterialTheme.typography.headlineSmall,
                )
                Spacer(modifier = Modifier.height(12.dp))

                CardTransactionRow(
                    merchant = "Starbucks",
                    category = "Food & Drink",
                    amount = "-$4.50",
                    time = "2 min ago",
                    icon = Icons.Outlined.LocalCafe,
                )
                CardTransactionRow(
                    merchant = "Uber",
                    category = "Transport",
                    amount = "-$12.80",
                    time = "1 hr ago",
                    icon = Icons.Outlined.DirectionsCar,
                )
                CardTransactionRow(
                    merchant = "Amazon",
                    category = "Shopping",
                    amount = "-$67.99",
                    time = "Yesterday",
                    icon = Icons.Outlined.ShoppingCart,
                )
                CardTransactionRow(
                    merchant = "Netflix",
                    category = "Entertainment",
                    amount = "-$15.99",
                    time = "3 days ago",
                    icon = Icons.Outlined.Tv,
                )

                Spacer(modifier = Modifier.height(28.dp))

                // ── Partners Banner ──────────────────────────────
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = TranzoColors.PaleTeal,
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                    ) {
                        Text(
                            text = "True Self-Custody Spending",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.SemiBold,
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Your crypto stays in your smart account until " +
                                    "the exact moment of purchase. Settled via " +
                                    "Kulipa, Reap & Immersve infrastructure.",
                            style = MaterialTheme.typography.bodySmall,
                            color = TranzoColors.TextSecondary,
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            PartnerChip("Kulipa")
                            PartnerChip("Reap")
                            PartnerChip("Immersve")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(28.dp))

                // ── Order Physical Card CTA ──────────────────────
                Surface(
                    onClick = onOrderCard,
                    shape = RoundedCornerShape(16.dp),
                    color = TranzoColors.CardSurface,
                    shadowElevation = 1.dp,
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(TranzoColors.PaleTeal),
                            contentAlignment = Alignment.Center,
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.CreditCard,
                                contentDescription = null,
                                tint = TranzoColors.PrimaryGreen,
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Get Physical Card",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.SemiBold,
                            )
                            Text(
                                text = "Metal Visa card • Free shipping",
                                style = MaterialTheme.typography.bodySmall,
                                color = TranzoColors.TextSecondary,
                            )
                        }
                        Icon(
                            imageVector = Icons.Outlined.ChevronRight,
                            contentDescription = null,
                            tint = TranzoColors.TextTertiary,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))
            } else {
                // ── No Card — Get Started ────────────────────────
                NoCardState(onOrderCard = onOrderCard)
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════
// ── Composables ──────────────────────────────────────────────
// ═══════════════════════════════════════════════════════════════

/**
 * Virtual card visual — dark gradient with Tranzo branding,
 * card number, expiry, and Visa logo placeholder.
 */
@Composable
private fun CardVisual() {
    val infiniteTransition = rememberInfiniteTransition(label = "card_shimmer")
    val shimmerAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(8000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        ),
        label = "shimmer_rotation",
    )

    Surface(
        shape = RoundedCornerShape(20.dp),
        shadowElevation = 8.dp,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(210.dp)
                .background(
                    Brush.linearGradient(
                        colors = listOf(
                            TranzoColors.Navy,
                            TranzoColors.GradientMid,
                            TranzoColors.DarkTeal,
                        ),
                    ),
                )
                .padding(24.dp),
        ) {
            // Top row: Tranzo logo + chip
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top,
            ) {
                Column {
                    Text(
                        text = "TRANZO",
                        style = MaterialTheme.typography.labelLarge,
                        color = TranzoColors.LightTeal,
                        letterSpacing = 4.sp,
                    )
                    Text(
                        text = "Self-Custody Card",
                        style = MaterialTheme.typography.labelSmall,
                        color = TranzoColors.TextOnDarkMuted,
                    )
                }
                // Contactless icon
                Icon(
                    imageVector = Icons.Outlined.Contactless,
                    contentDescription = null,
                    tint = TranzoColors.TextOnDarkMuted,
                    modifier = Modifier
                        .size(28.dp)
                        .rotate(90f),
                )
            }

            // Card number
            Text(
                text = "•••• •••• •••• 4291",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                letterSpacing = 3.sp,
                modifier = Modifier.align(Alignment.CenterStart),
            )

            // Bottom row: cardholder + expiry + Visa
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom,
            ) {
                Column {
                    Text(
                        text = "CARDHOLDER",
                        style = MaterialTheme.typography.labelSmall,
                        color = TranzoColors.TextOnDarkMuted,
                        fontSize = 9.sp,
                    )
                    Text(
                        text = "PRANAV",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Medium,
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "VALID THRU",
                        style = MaterialTheme.typography.labelSmall,
                        color = TranzoColors.TextOnDarkMuted,
                        fontSize = 9.sp,
                    )
                    Text(
                        text = "12/28",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Medium,
                    )
                }
                // Visa placeholder
                Text(
                    text = "VISA",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White.copy(alpha = 0.8f),
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                )
            }
        }
    }
}

@Composable
private fun CardQuickAction(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Surface(
            onClick = onClick,
            shape = CircleShape,
            color = TranzoColors.PaleTeal,
            modifier = Modifier.size(52.dp),
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = TranzoColors.PrimaryGreen,
                    modifier = Modifier.size(24.dp),
                )
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = TranzoColors.TextSecondary,
        )
    }
}

@Composable
private fun SpendingLimitCard(
    label: String,
    spent: Double,
    limit: Double,
    currency: String,
) {
    val progress = (spent / limit).coerceIn(0.0, 1.0).toFloat()
    val progressColor = when {
        progress > 0.9 -> TranzoColors.Error
        progress > 0.7 -> TranzoColors.Warning
        else -> TranzoColors.PrimaryGreen
    }

    Surface(
        shape = RoundedCornerShape(12.dp),
        color = TranzoColors.CardSurface,
        shadowElevation = 1.dp,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = "$label Limit",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                )
                Text(
                    text = "$${String.format("%.0f", spent)} / $${String.format("%.0f", limit)}",
                    style = MaterialTheme.typography.bodySmall,
                    color = TranzoColors.TextSecondary,
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = progressColor,
                trackColor = TranzoColors.LightGray,
            )
        }
    }
}

@Composable
private fun CardTransactionRow(
    merchant: String,
    category: String,
    amount: String,
    time: String,
    icon: ImageVector,
) {
    Surface(
        color = TranzoColors.CardSurface,
        modifier = Modifier.padding(vertical = 2.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(TranzoColors.PaleTeal),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = TranzoColors.PrimaryGreen,
                    modifier = Modifier.size(20.dp),
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = merchant,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                )
                Text(
                    text = category,
                    style = MaterialTheme.typography.labelSmall,
                    color = TranzoColors.TextTertiary,
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = amount,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = TranzoColors.TextPrimary,
                )
                Text(
                    text = time,
                    style = MaterialTheme.typography.labelSmall,
                    color = TranzoColors.TextTertiary,
                )
            }
        }
    }
}

@Composable
private fun PartnerChip(name: String) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = TranzoColors.CardSurface,
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Medium,
            color = TranzoColors.PrimaryGreen,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
        )
    }
}

@Composable
private fun NoCardState(onOrderCard: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(TranzoColors.PaleTeal),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.Outlined.CreditCard,
                contentDescription = null,
                tint = TranzoColors.PrimaryGreen,
                modifier = Modifier.size(56.dp),
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Spend Crypto Anywhere",
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Get a Tranzo Visa card and spend your self-custody " +
                    "crypto at 80M+ merchants worldwide. Your keys, your spend.",
            style = MaterialTheme.typography.bodyMedium,
            color = TranzoColors.TextSecondary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp),
        )

        Spacer(modifier = Modifier.height(16.dp))

        // How it works
        HowItWorksStep("1", "Your crypto stays in your smart account")
        HowItWorksStep("2", "Swipe your card at any Visa merchant")
        HowItWorksStep("3", "Crypto → USDC → fiat settlement in real-time")
        HowItWorksStep("4", "Only debited at the moment of purchase")

        Spacer(modifier = Modifier.height(32.dp))

        // Partners
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            PartnerChip("Kulipa")
            PartnerChip("Reap")
            PartnerChip("Immersve")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onOrderCard,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(28.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = TranzoColors.PrimaryGreen,
                contentColor = TranzoColors.TextOnGreen,
            ),
        ) {
            Icon(
                imageVector = Icons.Outlined.CreditCard,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Get Your Card — Free",
                style = MaterialTheme.typography.labelLarge,
            )
        }
    }
}

@Composable
private fun HowItWorksStep(number: String, text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(28.dp)
                .clip(CircleShape)
                .background(TranzoColors.PrimaryGreen),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = number,
                style = MaterialTheme.typography.labelSmall,
                color = Color.White,
                fontWeight = FontWeight.Bold,
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = TranzoColors.TextSecondary,
        )
    }
}
