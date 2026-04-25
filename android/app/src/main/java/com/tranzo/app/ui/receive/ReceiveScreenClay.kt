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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tranzo.app.ui.components.ClayButton
import com.tranzo.app.ui.components.ClayCard
import com.tranzo.app.ui.theme.TranzoColors

/**
 * Claymorphism Receive Screen — Baby blue bg, white QR card, address copy.
 */
@Composable
fun ReceiveScreenClay() {
    val walletAddress = "0x742d35Cc6634C0532925a3b844Bc9e7595f2bD04"

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(TranzoColors.ClayBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                "Receive Crypto",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = TranzoColors.TextPrimary,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                "Share your address to receive tokens",
                style = MaterialTheme.typography.bodyMedium,
                color = TranzoColors.TextSecondary,
            )

            Spacer(modifier = Modifier.height(32.dp))

            // QR placeholder card
            ClayCard(
                modifier = Modifier.size(240.dp),
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        Icons.Outlined.QrCode,
                        contentDescription = "QR Code",
                        tint = TranzoColors.ClayBlue,
                        modifier = Modifier.size(120.dp),
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Address card
            ClayCard(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Text(
                        "Your Wallet Address",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = TranzoColors.TextSecondary,
                    )
                    Text(
                        walletAddress,
                        style = MaterialTheme.typography.bodySmall,
                        color = TranzoColors.TextPrimary,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center,
                    )
                    Text(
                        "Base Sepolia Network",
                        style = MaterialTheme.typography.labelSmall,
                        color = TranzoColors.ClayBlue,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Actions
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Button(
                    onClick = {},
                    modifier = Modifier.weight(1f).height(48.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = TranzoColors.ClayBlue,
                        contentColor = Color.White,
                    ),
                ) {
                    Icon(Icons.Outlined.ContentCopy, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("Copy", fontWeight = FontWeight.SemiBold)
                }

                Button(
                    onClick = {},
                    modifier = Modifier.weight(1f).height(48.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = TranzoColors.ClayBlue,
                    ),
                ) {
                    Icon(Icons.Outlined.Share, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("Share", fontWeight = FontWeight.SemiBold)
                }
            }

            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}
