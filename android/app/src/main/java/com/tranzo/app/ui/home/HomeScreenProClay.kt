package com.tranzo.app.ui.home

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tranzo.app.ui.components.*
import com.tranzo.app.ui.theme.TranzoColors

/**
 * Claymorphism Home Screen — Premium tactile design with:
 * - Decorative background blobs for organic depth
 * - Hero balance card with glossy gradient
 * - 3D puffy action buttons with colored shadows
 * - Illustrated empty states and rich transaction cards
 */
@Composable
fun HomeScreenProClay(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToTransfer: () -> Unit = {},
    onNavigateToSwap: () -> Unit = {},
    onNavigateToCard: () -> Unit = {},
    onNavigateToSettings: () -> Unit = {},
) {
    var showContent by remember { mutableStateOf(false) }
    val uiState by viewModel.state.collectAsState()

    LaunchedEffect(Unit) { showContent = true }

    val contentAlpha by animateFloatAsState(
        targetValue = if (showContent) 1f else 0f,
        animationSpec = tween(900, easing = FastOutSlowInEasing),
        label = "content fade in"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(TranzoColors.ClayBackground)
    ) {
        // ── Decorative background blobs ─────────────────
        Canvas(modifier = Modifier.fillMaxSize()) {
            // Large soft purple blob — top right
            drawCircle(
                color = TranzoColors.ClayPurple.copy(alpha = 0.06f),
                radius = 280f,
                center = Offset(size.width * 0.85f, size.height * 0.08f),
            )
            // Medium blue blob — left center
            drawCircle(
                color = TranzoColors.ClayBlue.copy(alpha = 0.05f),
                radius = 220f,
                center = Offset(size.width * 0.1f, size.height * 0.35f),
            )
            // Small green blob — bottom right
            drawCircle(
                color = TranzoColors.ClayGreen.copy(alpha = 0.05f),
                radius = 160f,
                center = Offset(size.width * 0.75f, size.height * 0.7f),
            )
            // Warm coral blob — bottom left
            drawCircle(
                color = TranzoColors.ClayCoral.copy(alpha = 0.04f),
                radius = 140f,
                center = Offset(size.width * 0.15f, size.height * 0.85f),
            )
        }

        if (uiState.isLoading && uiState.user == null) {
            // ── Loading State ───────────────────────────────
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    ClayIconPill(
                        color = TranzoColors.ClayBlue,
                        size = 56.dp,
                        cornerRadius = 18.dp,
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = Color.White,
                            strokeWidth = 2.5.dp,
                        )
                    }
                    Text(
                        "Loading your wallet...",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TranzoColors.TextSecondary,
                    )
                }
            }
        } else if (uiState.error != null && uiState.user == null) {
            // ── Error State ─────────────────────────────────
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                ClayIconPill(
                    color = TranzoColors.ClayCoral,
                    size = 64.dp,
                    cornerRadius = 22.dp,
                ) {
                    Icon(
                        Icons.Outlined.ErrorOutline,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(32.dp),
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    "Something went wrong",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = TranzoColors.TextPrimary,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    uiState.error ?: "Unknown error",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TranzoColors.TextSecondary,
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(28.dp))
                ClayButton(
                    text = "Try Again",
                    onClick = { viewModel.refresh() },
                    modifier = Modifier.width(200.dp),
                )
            }
        } else {
            // ── Main Content ────────────────────────────────
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .alpha(contentAlpha)
            ) {
                // ── Header ──────────────────────────────────
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column {
                        val userName = uiState.user?.firstName?.takeIf { it.isNotBlank() } ?: "User"
                        Text(
                            "Hello, $userName",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = TranzoColors.TextPrimary,
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            "Manage your crypto assets",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TranzoColors.TextSecondary,
                        )
                    }

                    // Profile avatar pill
                    val userName = uiState.user?.firstName?.takeIf { it.isNotBlank() } ?: "U"
                    ClayIconPill(
                        color = TranzoColors.ClayPurple,
                        size = 48.dp,
                        cornerRadius = 16.dp,
                    ) {
                        Text(
                            userName.first().uppercaseChar().toString(),
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                        )
                    }
                }

                // ── Hero Balance Card ───────────────────────
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                ) {
                    // Main balance — large gradient card
                    ClayGradientCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp),
                        gradientStart = TranzoColors.ClayBlue,
                        gradientEnd = Color(0xFF7B5CE8),
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            // Decorative circles on the card
                            Canvas(modifier = Modifier.fillMaxSize()) {
                                drawCircle(
                                    color = Color.White.copy(alpha = 0.06f),
                                    radius = 120f,
                                    center = Offset(size.width * 0.85f, size.height * 0.2f),
                                )
                                drawCircle(
                                    color = Color.White.copy(alpha = 0.04f),
                                    radius = 80f,
                                    center = Offset(size.width * 0.1f, size.height * 0.85f),
                                )
                            }

                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(24.dp),
                                verticalArrangement = Arrangement.SpaceBetween,
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Text(
                                        "TOTAL BALANCE",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = Color.White.copy(alpha = 0.8f),
                                        fontWeight = FontWeight.SemiBold,
                                        letterSpacing = 1.5.sp,
                                    )
                                    // Wallet type badge
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(Color.White.copy(alpha = 0.15f))
                                            .padding(horizontal = 10.dp, vertical = 4.dp),
                                    ) {
                                        Text(
                                            "Smart Wallet",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = Color.White.copy(alpha = 0.9f),
                                            fontWeight = FontWeight.SemiBold,
                                            fontSize = 10.sp,
                                        )
                                    }
                                }

                                Row(
                                    verticalAlignment = Alignment.Bottom,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                ) {
                                    Text(
                                        "$",
                                        style = MaterialTheme.typography.headlineMedium,
                                        color = Color.White.copy(alpha = 0.7f),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 28.sp,
                                        modifier = Modifier.padding(bottom = 4.dp),
                                    )
                                    Text(
                                        String.format("%.2f", uiState.totalUsdBalance),
                                        style = MaterialTheme.typography.displayMedium,
                                        color = Color.White,
                                        fontWeight = FontWeight.ExtraBold,
                                        fontSize = 40.sp,
                                    )
                                    Text(
                                        "USD",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = Color.White.copy(alpha = 0.7f),
                                        fontWeight = FontWeight.SemiBold,
                                        modifier = Modifier.padding(bottom = 8.dp),
                                    )
                                }
                            }
                        }
                    }

                    // Token balance cards — side by side
                    if (uiState.balances.isNotEmpty()) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                        ) {
                            val tokenColors = listOf(
                                Pair(TranzoColors.ClayGreen, Color(0xFF5AE8A8)),
                                Pair(TranzoColors.ClayPurple, Color(0xFFB08BE8)),
                            )
                            uiState.balances.take(2).forEachIndexed { index, balance ->
                                val (start, end) = tokenColors.getOrElse(index) {
                                    Pair(TranzoColors.ClayBlue, TranzoColors.ClayBlueMuted)
                                }
                                ClayStatCard(
                                    label = balance.symbol ?: "Token",
                                    value = balance.formatted ?: "0.00",
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(100.dp),
                                    gradientStart = start,
                                    gradientEnd = end,
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(28.dp))

                // ── Quick Actions ───────────────────────────
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    Text(
                        "Quick Actions",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.ExtraBold,
                        color = TranzoColors.TextPrimary,
                        letterSpacing = 0.5.sp,
                    )

                    // Action row inside a clay card for visual grouping
                    ClayCard(
                        modifier = Modifier.fillMaxWidth(),
                        shadowElevation = 8.dp,
                        cornerRadius = 22.dp,
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp, vertical = 20.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                        ) {
                            ClayActionButton(
                                label = "Send",
                                onClick = onNavigateToTransfer,
                                backgroundColor = TranzoColors.ClayBlue,
                                icon = {
                                    Icon(
                                        Icons.Outlined.ArrowUpward,
                                        contentDescription = "Send",
                                        tint = Color.White,
                                        modifier = Modifier.size(26.dp),
                                    )
                                },
                            )

                            ClayActionButton(
                                label = "Swap",
                                onClick = onNavigateToSwap,
                                backgroundColor = TranzoColors.ClayPurple,
                                icon = {
                                    Icon(
                                        Icons.Outlined.SwapVert,
                                        contentDescription = "Swap",
                                        tint = Color.White,
                                        modifier = Modifier.size(26.dp),
                                    )
                                },
                            )

                            ClayActionButton(
                                label = "Card",
                                onClick = onNavigateToCard,
                                backgroundColor = TranzoColors.ClayGreen,
                                icon = {
                                    Icon(
                                        Icons.Outlined.CreditCard,
                                        contentDescription = "Card",
                                        tint = Color.White,
                                        modifier = Modifier.size(26.dp),
                                    )
                                },
                            )

                            ClayActionButton(
                                label = "More",
                                onClick = onNavigateToSettings,
                                backgroundColor = TranzoColors.ClayAmber,
                                icon = {
                                    Icon(
                                        Icons.Outlined.MoreHoriz,
                                        contentDescription = "More",
                                        tint = Color.White,
                                        modifier = Modifier.size(26.dp),
                                    )
                                },
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(28.dp))

                // ── Recent Activity ─────────────────────────
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            "Recent Activity",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.ExtraBold,
                            color = TranzoColors.TextPrimary,
                            letterSpacing = 0.5.sp,
                        )
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(TranzoColors.ClayBlueSoft)
                                .padding(horizontal = 12.dp, vertical = 6.dp),
                        ) {
                            Text(
                                "View all",
                                style = MaterialTheme.typography.labelSmall,
                                color = TranzoColors.ClayBlue,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp,
                            )
                        }
                    }

                    if (uiState.isLoading && uiState.balances.isEmpty()) {
                        // Shimmer placeholders
                        repeat(2) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(76.dp)
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(
                                        Brush.linearGradient(
                                            colors = listOf(
                                                TranzoColors.ClayCard,
                                                TranzoColors.ClayBackgroundAlt,
                                                TranzoColors.ClayCard,
                                            ),
                                        )
                                    ),
                            )
                        }
                    } else {
                        // Transaction items
                        val txData = listOf(
                            Triple("Sent", "-$500 USDC", "2 hours ago"),
                            Triple("Received", "+$1,200 USDC", "5 hours ago"),
                            Triple("Swap", "ETH → USDC", "Yesterday"),
                        )
                        txData.forEach { (type, amount, time) ->
                            ClayTransactionCard(
                                type = type,
                                amount = amount,
                                timestamp = time,
                                status = "Completed",
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }
}

/**
 * Transaction card — Clay card with colored icon pill and details.
 */
@Composable
private fun ClayTransactionCard(
    type: String,
    amount: String,
    timestamp: String,
    status: String,
) {
    val (iconColor, icon) = when (type) {
        "Sent" -> Pair(TranzoColors.ClayCoral, Icons.Outlined.ArrowUpward)
        "Received" -> Pair(TranzoColors.ClayGreen, Icons.Outlined.ArrowDownward)
        "Swap" -> Pair(TranzoColors.ClayPurple, Icons.Outlined.SwapVert)
        else -> Pair(TranzoColors.ClayBlue, Icons.Outlined.Receipt)
    }

    ClayCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(76.dp),
        shadowElevation = 6.dp,
        cornerRadius = 20.dp,
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f),
            ) {
                // Colored icon pill
                ClayIconPill(color = iconColor, size = 44.dp, cornerRadius = 15.dp) {
                    Icon(
                        imageVector = icon,
                        contentDescription = type,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp),
                    )
                }

                Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                    Text(
                        type,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = TranzoColors.TextPrimary,
                    )
                    Text(
                        timestamp,
                        style = MaterialTheme.typography.labelSmall,
                        color = TranzoColors.TextTertiary,
                    )
                }
            }

            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(3.dp),
            ) {
                Text(
                    amount,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = when (type) {
                        "Received" -> TranzoColors.ClayGreen
                        "Sent" -> TranzoColors.ClayCoral
                        else -> TranzoColors.TextPrimary
                    },
                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(TranzoColors.ClayGreenSoft)
                        .padding(horizontal = 8.dp, vertical = 2.dp),
                ) {
                    Text(
                        status,
                        style = MaterialTheme.typography.labelSmall,
                        color = TranzoColors.ClayGreen,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 9.sp,
                    )
                }
            }
        }
    }
}


