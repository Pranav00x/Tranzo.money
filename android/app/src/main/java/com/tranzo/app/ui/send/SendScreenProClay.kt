package com.tranzo.app.ui.send

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tranzo.app.ui.components.*
import com.tranzo.app.ui.theme.TranzoColors

@Composable
fun SendScreenProClay(
    viewModel: SendViewModel = hiltViewModel(),
    onConfirm: () -> Unit = {},
) {
    var recipient by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var selectedToken by remember { mutableStateOf("USDC") }
    val uiState by viewModel.state.collectAsState()

    var showContent by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { showContent = true }

    LaunchedEffect(uiState.isSent) {
        if (uiState.isSent) { onConfirm(); viewModel.reset() }
    }

    val contentAlpha by animateFloatAsState(
        targetValue = if (showContent) 1f else 0f,
        animationSpec = tween(800), label = "fade"
    )

    Box(Modifier.fillMaxSize().background(TranzoColors.ClayBackground)) {
        Column(
            Modifier.fillMaxSize().verticalScroll(rememberScrollState()).alpha(contentAlpha),
        ) {
            Spacer(Modifier.height(24.dp))

            // ── Header (Minimal) ──
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier.size(40.dp).clip(CircleShape).clickable {}.background(TranzoColors.ClayBackgroundAlt),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Outlined.Close, contentDescription = null, tint = TranzoColors.TextPrimary, modifier = Modifier.size(20.dp))
                }
                Text(
                    "Send",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = TranzoColors.TextPrimary,
                )
                Box(modifier = Modifier.size(40.dp))
            }

            Spacer(Modifier.height(48.dp))

            // ── Big Amount Input ──
            Column(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier.clip(RoundedCornerShape(20.dp)).background(TranzoColors.ClayBackgroundAlt).clickable {}.padding(horizontal = 16.dp, vertical = 8.dp),
                ) {
                    Text(selectedToken, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, color = TranzoColors.TextPrimary)
                }
                
                Spacer(modifier = Modifier.height(24.dp))

                TextField(
                    value = amount, 
                    onValueChange = { amount = it }, 
                    placeholder = { Text("$0", color = TranzoColors.TextDisabled, style = MaterialTheme.typography.displayLarge.copy(fontSize = 72.sp, fontWeight = FontWeight.Medium), textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth()) },
                    keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    textStyle = MaterialTheme.typography.displayLarge.copy(fontSize = 72.sp, fontWeight = FontWeight.Medium, color = TranzoColors.TextPrimary, textAlign = TextAlign.Center),
                    colors = TextFieldDefaults.colors(focusedContainerColor = Color.Transparent, unfocusedContainerColor = Color.Transparent, focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Text("Available balance: $8,950", style = MaterialTheme.typography.bodyMedium, color = TranzoColors.TextSecondary)
            }

            Spacer(Modifier.height(48.dp))

            // ── Recipient Input (Clean line) ──
            Column(Modifier.fillMaxWidth().padding(horizontal = 24.dp)) {
                Text("To", style = MaterialTheme.typography.labelLarge, color = TranzoColors.TextSecondary)
                Spacer(Modifier.height(8.dp))
                TextField(
                    value = recipient,
                    onValueChange = { recipient = it },
                    placeholder = { Text("Name, @username, or address", color = TranzoColors.TextTertiary, style = MaterialTheme.typography.titleLarge) },
                    textStyle = MaterialTheme.typography.titleLarge.copy(color = TranzoColors.TextPrimary, fontWeight = FontWeight.Medium),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent, 
                        unfocusedContainerColor = Color.Transparent, 
                        focusedIndicatorColor = TranzoColors.TextPrimary, 
                        unfocusedIndicatorColor = TranzoColors.DividerGray
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            if (uiState.error != null) {
                Spacer(Modifier.height(24.dp))
                Text(uiState.error!!, style = MaterialTheme.typography.bodyMedium, color = TranzoColors.ClayCoral, fontWeight = FontWeight.Medium, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
            }

            Spacer(Modifier.height(48.dp))

            ClayButton(
                text = "Continue", 
                onClick = { viewModel.sendToken(recipient, selectedToken, amount) },
                enabled = recipient.isNotBlank() && amount.isNotBlank() && !uiState.isLoading, 
                isLoading = uiState.isLoading,
                modifier = Modifier.padding(horizontal = 24.dp)
            )

            Spacer(Modifier.height(100.dp))
        }
    }
}
