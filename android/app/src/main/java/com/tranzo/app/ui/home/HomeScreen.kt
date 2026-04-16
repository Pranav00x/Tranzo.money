package com.tranzo.app.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tranzo.app.ui.components.*
import com.tranzo.app.ui.theme.TranzoColors

data class QuickAction(
    val label: String,
    val icon: androidx.compose.material.icons.Icons,
    val onClick: () -> Unit
)

data class TokenItem(
    val symbol: String,
    val name: String,
    val balance: String,
    val usdValue: String,
    val change: String,
    val isPositive: Boolean
)

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
        QuickAction("Dripper", Icons.Outlined.WaterDrop, onDripper),
    )

    val tokens = state.balances.map { balance ->
        TokenItem(
            symbol = balance.symbol,
            name = balance.symbol,
            balance = balance.formatted,
            usdValue = if (balance.symbol == "USDC") "$${balance.formatted}" else "---",
            change = "",
            isPositive = true
        )
    }

    // Error state
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
                    text = "Oops!",
                    style = MaterialTheme.typography.headlineLarge,
                    color = TranzoColors.TextPrimary,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = state.error.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = TranzoColors.TextSecondary,
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(24.dp))
                TranzoButton(
                    text = "Retry",
                    onClick = { viewModel.refresh() }
                )
            }
        }
        return
    }

    // Loading state
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

                    Spacer(modifier = Modifier.height(16.dp))

                    if (hasCard) {
                        MiniCardHero(onClick = onCard)
                        Spacer(modifier = Modifier.height(20.dp))
                    }

                    QuickActionBar(quickActions)
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }

        item {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(
                    text = "Your Assets",
                    style = MaterialTheme.typography.headlineSmall,
                    color = TranzoColors.TextPrimary,
                )
            }
        }

        items(tokens) { token ->
            TokenRow(
                token = token,
                onClick = { /* TODO: Handle token click */ }
            )
        }
    }
}
