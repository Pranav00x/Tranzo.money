package com.tranzo.app.ui.receive

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material.icons.outlined.QrCode
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tranzo.app.ui.components.ClayActionButton
import com.tranzo.app.ui.components.ClayCard
import com.tranzo.app.ui.theme.TranzoColors

/**
 * Claymorphism Receive Screen
 */
@Composable
fun ReceiveScreenClay() {
    val walletAddress = "0x742d35Cc6634C0532925a3b844Bc59e94b63bDA1"
    var copied by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = TranzoColors.ClayBackground
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // Header
            Text(
                "Receive Funds",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = TranzoColors.TextPrimary,
                fontSize = 28.sp
            )

            Text(
                "Share your wallet address to receive crypto",
                style = MaterialTheme.typography.bodyMedium,
                color = TranzoColors.TextSecondary
            )

            Spacer(modifier = Modifier.height(16.dp))

            // QR Code placeholder
            ClayCard(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(200.dp),
                backgroundGradient = listOf(
                    Color.White,
                    TranzoColors.BackgroundLight.copy(alpha = 0.8f)
                )
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Outlined.QrCode,
                        contentDescription = "QR Code",
                        tint = TranzoColors.PrimaryBlue,
                        modifier = Modifier.size(100.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Address display
            Text(
                "Wallet Address",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.SemiBold,
                color = TranzoColors.TextTertiary
            )

            ClayCard(
                modifier = Modifier.fillMaxWidth(),
                backgroundGradient = listOf(Color.White, TranzoColors.BackgroundLight.copy(alpha = 0.7f))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        walletAddress,
                        style = MaterialTheme.typography.bodySmall,
                        color = TranzoColors.TextPrimary,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.sp
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(TranzoColors.SurfaceLight)
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Outlined.ContentCopy,
                            contentDescription = "Copy",
                            tint = TranzoColors.PrimaryBlue,
                            modifier = Modifier.size(16.dp)
                        )

                        Text(
                            if (copied) "Copied!" else "Copy Address",
                            style = MaterialTheme.typography.labelSmall,
                            color = TranzoColors.PrimaryBlue,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Quick actions
            Text(
                "Share",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.SemiBold,
                color = TranzoColors.TextTertiary
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ClayActionButton(
                    label = "Share",
                    onClick = { /* action */ },
                    modifier = Modifier.weight(1f),
                    icon = {
                        Icon(
                            Icons.Outlined.Share,
                            contentDescription = "Share",
                            tint = TranzoColors.PrimaryBlue,
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    backgroundColor = TranzoColors.PrimaryBlue.copy(alpha = 0.12f)
                )

                ClayActionButton(
                    label = "Copy",
                    onClick = { copied = true },
                    modifier = Modifier.weight(1f),
                    icon = {
                        Icon(
                            Icons.Outlined.ContentCopy,
                            contentDescription = "Copy",
                            tint = TranzoColors.PrimaryGreen,
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    backgroundColor = TranzoColors.PrimaryGreen.copy(alpha = 0.12f)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}


