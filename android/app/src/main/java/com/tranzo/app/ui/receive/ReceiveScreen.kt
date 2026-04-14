package com.tranzo.app.ui.receive

import android.graphics.Bitmap
import android.graphics.Color as AndroidColor
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.tranzo.app.ui.components.TranzoSecondaryButton
import com.tranzo.app.ui.theme.TranzoColors

/**
 * Receive screen — QR code + address display.
 *
 * Layout:
 * - Back button + "Receive" title
 * - Network selector tabs (Polygon / Base)
 * - QR code card (white surface, rounded)
 * - Wallet address (truncated + copy button)
 * - Share button
 */
@Composable
fun ReceiveScreen(
    walletAddress: String = "0x7a3bC9D76f4E8A2c1B5d3F40eE9A8b6c12D4f5E6",
    onBack: () -> Unit = {},
) {
    var selectedNetwork by remember { mutableStateOf("Polygon") }
    var showCopied by remember { mutableStateOf(false) }
    val clipboardManager = LocalClipboardManager.current

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
                text = "Receive",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.weight(1f),
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // ── Network Tabs ─────────────────────────────────────
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(TranzoColors.LightGray)
                    .padding(4.dp),
            ) {
                listOf("Polygon", "Base").forEach { network ->
                    val isSelected = selectedNetwork == network
                    Surface(
                        onClick = { selectedNetwork = network },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp),
                        color = if (isSelected) TranzoColors.CardSurface else TranzoColors.LightGray,
                        tonalElevation = if (isSelected) 2.dp else 0.dp,
                    ) {
                        Text(
                            text = network,
                            style = MaterialTheme.typography.labelLarge,
                            color = if (isSelected) TranzoColors.PrimaryGreen else TranzoColors.TextSecondary,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(vertical = 10.dp),
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // ── QR Code Card ─────────────────────────────────────
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = TranzoColors.CardSurface,
                tonalElevation = 2.dp,
            ) {
                Column(
                    modifier = Modifier.padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    // Generate QR code
                    val qrBitmap = remember(walletAddress) {
                        generateQRCode(walletAddress)
                    }

                    if (qrBitmap != null) {
                        Image(
                            bitmap = qrBitmap.asImageBitmap(),
                            contentDescription = "Wallet QR Code",
                            modifier = Modifier
                                .size(220.dp)
                                .clip(RoundedCornerShape(12.dp)),
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .size(220.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(TranzoColors.LightGray),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text("QR Code", color = TranzoColors.TextSecondary)
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "Scan to send $selectedNetwork tokens",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TranzoColors.TextSecondary,
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ── Wallet Address ────────────────────────────────────
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = TranzoColors.LightGray,
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "${walletAddress.take(12)}...${walletAddress.takeLast(8)}",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.weight(1f),
                    )
                    IconButton(onClick = {
                        clipboardManager.setText(AnnotatedString(walletAddress))
                        showCopied = true
                    }) {
                        Icon(
                            imageVector = if (showCopied)
                                Icons.Outlined.CheckCircle
                            else Icons.Outlined.ContentCopy,
                            contentDescription = "Copy Address",
                            tint = TranzoColors.PrimaryGreen,
                            modifier = Modifier.size(20.dp),
                        )
                    }
                }
            }

            if (showCopied) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Address copied!",
                    style = MaterialTheme.typography.bodySmall,
                    color = TranzoColors.PrimaryGreen,
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // ── Share Button ─────────────────────────────────────
            TranzoSecondaryButton(
                text = "Share Address",
                onClick = { /* share intent */ },
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

/**
 * Generate a QR code bitmap from a string using ZXing.
 */
private fun generateQRCode(content: String, size: Int = 512): Bitmap? {
    return try {
        val writer = QRCodeWriter()
        val bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, size, size)
        val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        for (x in 0 until size) {
            for (y in 0 until size) {
                bitmap.setPixel(
                    x, y,
                    if (bitMatrix[x, y]) AndroidColor.parseColor("#1A1A2E")
                    else AndroidColor.WHITE
                )
            }
        }
        bitmap
    } catch (e: Exception) {
        null
    }
}
