package com.tranzo.app.ui.dripper

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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tranzo.app.data.model.StreamDetail
import com.tranzo.app.ui.components.StatusBadge
import com.tranzo.app.ui.theme.TranzoColors
import java.math.BigDecimal
import java.time.Duration
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun DripperDashboardScreen(
    viewModel: DripperViewModel = hiltViewModel(),
    onCreateStream: () -> Unit = {},
    onStreamClick: (String) -> Unit = {},
) {
    val state by viewModel.state.collectAsState()
    val activeStreams = state.streams.filter { it.status.equals("ACTIVE", true) }
    val totalEarned = state.streams.sumOf { it.totalWithdrawn.toBigDecimalSafe() }

    LaunchedEffect(Unit) {
        viewModel.loadStreams()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (state.isLoading && state.streams.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = TranzoColors.TextPrimary)
            }
            return@Box
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(TranzoColors.BackgroundLight),
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    TranzoColors.PrimaryBlue,
                                    TranzoColors.PrimaryPurple,
                                    TranzoColors.AccentCyan,
                                ),
                            ),
                        )
                        .statusBarsPadding()
                        .padding(24.dp),
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = "Dripper",
                                style = MaterialTheme.typography.headlineMedium,
                                color = TranzoColors.TextDarkPrimary,
                                fontWeight = FontWeight.Bold,
                            )
                            Icon(
                                imageVector = Icons.Outlined.WaterDrop,
                                contentDescription = null,
                                tint = TranzoColors.AccentEmerald,
                                modifier = Modifier.size(28.dp),
                            )
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        Text(
                            text = "Total Withdrawn",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TranzoColors.TextDarkSecondary,
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "$${totalEarned.formatMoney()}",
                            style = MaterialTheme.typography.displayLarge,
                            color = TranzoColors.TextDarkPrimary,
                            fontWeight = FontWeight.Bold,
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "${activeStreams.size} active stream(s)",
                            style = MaterialTheme.typography.bodySmall,
                            color = TranzoColors.AccentEmerald,
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Button(
                            onClick = { activeStreams.firstOrNull()?.let { onStreamClick(it.id) } },
                            enabled = activeStreams.isNotEmpty(),
                            shape = RoundedCornerShape(28.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = TranzoColors.TextPrimary,
                            ),
                            modifier = Modifier.fillMaxWidth(0.6f),
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.AccountBalanceWallet,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp),
                            )
                            Text(" Withdraw")
                        }
                    }
                }
            }

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = (-20).dp)
                        .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                        .background(TranzoColors.BackgroundLight)
                        .padding(horizontal = 20.dp),
                ) {
                    Column {
                        Spacer(modifier = Modifier.height(24.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = "Your Streams",
                                style = MaterialTheme.typography.headlineMedium,
                            )
                            StatusBadge(text = "${state.activeCount} Active")
                        }

                        state.error?.let {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = it,
                                color = TranzoColors.Error,
                                style = MaterialTheme.typography.bodySmall,
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }

            if (state.streams.isEmpty()) {
                item {
                    Text(
                        text = "No streams yet. Create your first stream.",
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        color = TranzoColors.TextSecondary,
                    )
                }
            } else {
                items(state.streams, key = { it.id }) { stream ->
                    StreamCard(
                        stream = stream,
                        onClick = { onStreamClick(stream.id) },
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(100.dp)) }
        }

        FloatingActionButton(
            onClick = onCreateStream,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 24.dp, bottom = 100.dp),
            containerColor = TranzoColors.TextPrimary,
            contentColor = TranzoColors.White,
            shape = CircleShape,
        ) {
            Icon(Icons.Filled.Add, contentDescription = "Create Stream")
        }
    }
}

@Composable
private fun StreamCard(
    stream: StreamDetail,
    onClick: () -> Unit,
) {
    val now = Instant.now()
    val start = stream.startTime.toInstantSafe()
    val end = stream.endTime.toInstantSafe()
    val progress = if (start != null && end != null) {
        val total = Duration.between(start, end).seconds.toFloat().coerceAtLeast(1f)
        val elapsed = Duration.between(start, now).seconds.toFloat().coerceAtLeast(0f)
        (elapsed / total).coerceIn(0f, 1f)
    } else {
        0f
    }

    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 6.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = TranzoColors.SurfaceLight),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column {
                    Text(
                        text = stream.employeeAddress.shortAddress(),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Text(
                        text = "${stream.amountPerSecond} / sec",
                        style = MaterialTheme.typography.bodySmall,
                        color = TranzoColors.TextSecondary,
                    )
                }
                StatusBadge(
                    text = stream.status.lowercase().replaceFirstChar { it.uppercase() },
                    isError = stream.status.equals("cancelled", true),
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp),
                shape = RoundedCornerShape(3.dp),
                color = TranzoColors.BackgroundLight,
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(progress)
                        .height(6.dp)
                        .background(TranzoColors.TextPrimary),
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Ends ${stream.endTime.toDisplayDate()}",
                style = MaterialTheme.typography.labelSmall,
                color = TranzoColors.TextTertiary,
            )
        }
    }
}

private fun String.toInstantSafe(): Instant? = runCatching { Instant.parse(this) }.getOrNull()

private fun String.toDisplayDate(): String {
    val instant = toInstantSafe() ?: return this
    val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy", Locale.US)
    return formatter.format(instant.atZone(ZoneId.systemDefault()))
}

private fun String.shortAddress(): String {
    return if (length > 12) "${take(8)}...${takeLast(4)}" else this
}

private fun String.toBigDecimalSafe(): BigDecimal = runCatching { toBigDecimal() }.getOrDefault(BigDecimal.ZERO)

private fun BigDecimal.formatMoney(): String = runCatching {
    setScale(2, java.math.RoundingMode.HALF_UP).toPlainString()
}.getOrDefault("0.00")
