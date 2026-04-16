package com.tranzo.app.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import com.tranzo.app.ui.components.StatusBadge
import com.tranzo.app.ui.components.TranzoLogo
import com.tranzo.app.ui.components.TranzoButton
import com.tranzo.app.ui.components.TranzoCard
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.tranzo.app.data.model.TokenBalance
import com.tranzo.app.ui.home.HomeViewModel
import java.util.Locale
import com.tranzo.app.ui.theme.TranzoColors

data class QuickAction(
    val label: String,
    val icon: ImageVector,
    val onClick: () -> Unit,
)

data class TokenItem(
    val symbol: String,
    val name: String,
    val balance: String,
    val usdValue: String,
    val change: String,
    val isPositive: Boolean,
)

/**
 * Home Dashboard — Card-first, CheQ-inspired.
 *
 * Layout:
 * 1. Dark navy-to-teal gradient header
 *    - Tranzo logo + notification bell
 *    - Mini card visual (hero, like CheQ's credit card section)
 *    - "+ Add New Card" green pill CTA (if no card)
 * 2. White section with rounded top corners
 *    - Quick actions row (Send, Receive, Swap, Dripper)
 *    - Feature grid cards
 *    - Token balances
 */
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onSend: () -> Unit = {},
    onReceive: () -> Unit = {},
    onSwap: () -> Unit = {},
    onDripper: () -> Unit = {},
    onCard: () -> Unit = {},
    onOrderCard: () -> Unit = {},
) {
    val state by viewModel.state.collectAsState()
    val hasCard = true // Local flag for UI state
    
    val quickActions = listOf(
        QuickAction("Send", Icons.Outlined.ArrowOutward, onSend),
        QuickAction("Receive", Icons.Outlined.ArrowDownward, onReceive),
        QuickAction("Swap", Icons.Outlined.SwapHoriz, onSwap),

    if (state.error != null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(TranzoColors.White),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(24.dp)
            ) {
                Text(
                    text = "Error Loading Dashboard",
                    style = MaterialTheme.typography.headlineLarge,
                )
                Spacer(modifier = Modifier.height(16.dp))
                TranzoButton(
                    text = "Retry",
                    onClick = { viewModel.refresh() }
                )
            }
        }
        return
    }

    if (state.isLoading && state.balances.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(TranzoColors.White),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(TranzoColors.Background),
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                TranzoColors.Navy,
                                TranzoColors.GradientMid,
                                TranzoColors.DarkTeal,
                            ),
                        ),
                    )
                    .statusBarsPadding()
                    .padding(horizontal = 24.dp),
            ) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp, bottom = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            TranzoLogo(size = 32.dp)
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "Tranzo",
                                style = MaterialTheme.typography.headlineSmall,
                                color = TranzoColors.TextOnDark,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                        IconButton(
                            onClick = {},
                            modifier = Modifier.size(48.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Notifications,
                                contentDescription = "Notifications",
                                tint = TranzoColors.TextOnDark,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "Assets",
                        style = MaterialTheme.typography.headlineSmall,
                        color = TranzoColors.TextOnDark,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }
            }
        }

        items(tokens) { token ->
            TokenBalanceItem(
                symbol = token.symbol,
                balance = token.balance,
                usdValue = token.usdValue,
                onClick = {}
            )
        }
    }
}

@Composable
private fun TokenBalanceItem(
    symbol: String,
    balance: String,
    usdValue: String,
    onClick: () -> Unit
) {
    TranzoCard(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = symbol,
                    style = MaterialTheme.typography.titleMedium,
                    color = TranzoColors.TextPrimary,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = balance,
                    style = MaterialTheme.typography.bodySmall,
                    color = TranzoColors.TextSecondary
                )
            }
            Text(
                text = usdValue,
                style = MaterialTheme.typography.titleSmall,
                color = TranzoColors.TextPrimary,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
