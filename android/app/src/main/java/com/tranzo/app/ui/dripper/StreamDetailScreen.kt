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
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tranzo.app.ui.components.TranzoButton
import com.tranzo.app.ui.components.TranzoSecondaryButton
import com.tranzo.app.ui.theme.TranzoColors
import kotlinx.coroutines.delay
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.Duration
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun StreamDetailScreen(
    streamId: String,
    viewModel: DripperViewModel = hiltViewModel(),
    onBack: () -> Unit = {},
) {
    val state by viewModel.state.collectAsState()
    var currentTime by remember { mutableStateOf(Instant.now()) }

    LaunchedEffect(Unit) {
        if (state.streams.none { it.id == streamId }) {
            viewModel.loadStreams()
        }
    }

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            currentTime = Instant.now()
        }
    }

    val stream = state.streams.firstOrNull { it.id == streamId }

    if (stream == null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(TranzoColors.BackgroundLight),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = if (state.isLoading) "Loading stream..." else "Stream not found",
                style = MaterialTheme.typography.bodyLarge,
                color = TranzoColors.TextSecondary,
            )
        }
        return
    }

    val start = runCatching { Instant.parse(stream.startTime) }.getOrNull()
    val end = runCatching { Instant.parse(stream.endTime) }.getOrNull()
    val amountPerSecond = stream.amountPerSecond.toBigDecimalSafe()
    val totalWithdrawn = stream.totalWithdrawn.toBigDecimalSafe()
    val elapsedSeconds = if (start != null) Duration.between(start, currentTime).seconds.coerceAtLeast(0) else 0
    val streamedAmount = amountPerSecond.multiply(elapsedSeconds.toBigDecimal())
    val earned = streamedAmount.max(totalWithdrawn)
    val totalDuration = if (start != null && end != null) Duration.between(start, end).seconds.coerceAtLeast(1) else 1
    val totalAmount = amountPerSecond.multiply(totalDuration.toBigDecimal())
    val progress = if (totalAmount > BigDecimal.ZERO) earned.divide(totalAmount, 6, RoundingMode.HALF_UP).toFloat().coerceIn(0f, 1f) else 0f
    val dailyRate = amountPerSecond.multiply(BigDecimal("86400"))

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
                        colors = listOf(
                            TranzoColors.PrimaryBlue,
                            TranzoColors.AccentCyan,
                        ),
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
                        text = "Stream #${stream.id}",
                        style = MaterialTheme.typography.headlineMedium,
                        color = TranzoColors.TextDarkPrimary,
                        modifier = Modifier.weight(1f),
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "To ${stream.employeeAddress.shortAddress()}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TranzoColors.TextDarkSecondary,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "$${earned.toMoney()}",
                    style = MaterialTheme.typography.displayLarge,
                    color = TranzoColors.TextDarkPrimary,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "$${dailyRate.toMoney()} / day",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TranzoColors.AccentEmerald,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = (-20).dp)
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .background(TranzoColors.BackgroundLight)
                .padding(24.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = "Progress",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TranzoColors.TextSecondary,
                )
                Text(
                    text = "${(progress * 100).toInt()}%",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TranzoColors.TextPrimary,
                    fontWeight = FontWeight.SemiBold,
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = TranzoColors.TextPrimary,
                trackColor = TranzoColors.BackgroundLight,
            )

            Spacer(modifier = Modifier.height(24.dp))

            Surface(
                shape = RoundedCornerShape(16.dp),
                color = TranzoColors.SurfaceLight,
                tonalElevation = 1.dp,
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    DetailRow("Status", stream.status)
                    RowLine()
                    DetailRow("Recipient", stream.employeeAddress.shortAddress())
                    RowLine()
                    DetailRow("Rate", "${stream.amountPerSecond} / sec")
                    RowLine()
                    DetailRow("Earned", "$${earned.toMoney()}")
                    RowLine()
                    DetailRow("Withdrawn", "$${totalWithdrawn.toMoney()}")
                    RowLine()
                    DetailRow("Start", stream.startTime.toDisplayDate())
                    RowLine()
                    DetailRow("End", stream.endTime.toDisplayDate())
                }
            }

            state.error?.let {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = it,
                    color = TranzoColors.Error,
                    style = MaterialTheme.typography.bodySmall,
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            if (stream.status.equals("ACTIVE", true)) {
                TranzoButton(
                    text = "Withdraw",
                    onClick = { viewModel.withdrawFromStream(stream.id) },
                )
                Spacer(modifier = Modifier.height(12.dp))
                TranzoSecondaryButton(
                    text = "Cancel Stream",
                    onClick = { viewModel.cancelStream(stream.id) },
                )
            }
        }
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
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

@Composable
private fun RowLine() {
    HorizontalDivider(
        modifier = Modifier.padding(vertical = 12.dp),
        color = TranzoColors.DividerGray,
    )
}

private fun String.toBigDecimalSafe(): BigDecimal = runCatching { toBigDecimal() }.getOrDefault(BigDecimal.ZERO)

private fun BigDecimal.toMoney(): String = setScale(2, RoundingMode.HALF_UP).toPlainString()

private fun String.shortAddress(): String = if (length > 12) "${take(8)}...${takeLast(4)}" else this

private fun String.toDisplayDate(): String {
    val instant = runCatching { Instant.parse(this) }.getOrNull() ?: return this
    val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy", Locale.US)
    return formatter.format(instant.atZone(ZoneId.systemDefault()))
}
