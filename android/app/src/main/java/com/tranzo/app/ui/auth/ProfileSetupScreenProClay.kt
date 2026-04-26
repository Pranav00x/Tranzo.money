package com.tranzo.app.ui.auth

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tranzo.app.ui.components.*
import com.tranzo.app.ui.theme.TranzoColors

@Composable
fun ProfileSetupScreenProClay(
    viewModel: AuthViewModel = hiltViewModel(),
    onProfileCreated: () -> Unit = {},
) {
    var fullName by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    val uiState by viewModel.state.collectAsState()

    var showContent by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { showContent = true }
    val contentAlpha by animateFloatAsState(targetValue = if (showContent) 1f else 0f, animationSpec = tween(800), label = "fade")

    Box(Modifier.fillMaxSize().background(TranzoColors.ClayBackground)) {
        Canvas(Modifier.fillMaxSize()) {
            drawCircle(TranzoColors.ClayPurple.copy(alpha = 0.06f), 240f, Offset(size.width * 0.85f, size.height * 0.1f))
            drawCircle(TranzoColors.ClayBlue.copy(alpha = 0.05f), 180f, Offset(size.width * 0.1f, size.height * 0.5f))
        }

        Column(
            Modifier.fillMaxSize().verticalScroll(rememberScrollState()).alpha(contentAlpha).padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(Modifier.height(60.dp))

            Text("Create Your Profile", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.ExtraBold, color = TranzoColors.TextPrimary)
            Text("Set up your Tranzo identity", style = MaterialTheme.typography.bodyMedium, color = TranzoColors.TextSecondary)

            Spacer(Modifier.height(16.dp))

            // Avatar
            Box(Modifier.align(Alignment.CenterHorizontally)) {
                Box(
                    Modifier.size(88.dp)
                        .shadow(18.dp, RoundedCornerShape(28.dp), ambientColor = TranzoColors.ClayPurple.copy(alpha = 0.35f))
                        .clip(RoundedCornerShape(28.dp))
                        .background(Brush.linearGradient(listOf(TranzoColors.ClayPurple, TranzoColors.ClayBlue)))
                        .drawBehind {
                            drawRoundRect(
                                Brush.verticalGradient(listOf(Color.White.copy(alpha = 0.25f), Color.Transparent), startY = 0f, endY = size.height * 0.35f),
                                cornerRadius = CornerRadius(28.dp.toPx()), size = Size(size.width, size.height * 0.35f)
                            )
                        },
                    contentAlignment = Alignment.Center,
                ) {
                    Text(fullName.firstOrNull()?.uppercaseChar()?.toString() ?: "?",
                        style = MaterialTheme.typography.displaySmall, color = Color.White, fontWeight = FontWeight.ExtraBold)
                }
            }

            Spacer(Modifier.height(24.dp))

            // Form card
            ClayCard(Modifier.fillMaxWidth(), cornerRadius = 22.dp) {
                Column(Modifier.fillMaxWidth().padding(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    ClayTextField(value = fullName, onValueChange = { fullName = it }, placeholder = "Full Name",
                        leadingIcon = { Icon(Icons.Outlined.Person, null, tint = TranzoColors.ClayBlue, modifier = Modifier.size(20.dp)) })
                    ClayTextField(value = username, onValueChange = { username = it }, placeholder = "Username",
                        leadingIcon = { Text("@", color = TranzoColors.ClayPurple, fontWeight = FontWeight.Bold) })
                }
            }

            Spacer(Modifier.height(24.dp))

            ClayButton(text = "Continue", onClick = {
                val names = fullName.trim().split(" ")
                val firstName = names.firstOrNull() ?: ""
                val lastName = if (names.size > 1) names.drop(1).joinToString(" ") else ""
                viewModel.saveProfile(firstName = firstName, lastName = lastName, email = uiState.lastEmail ?: "")
                onProfileCreated()
            }, enabled = fullName.isNotBlank() && username.isNotBlank())

            Spacer(Modifier.height(32.dp))
        }
    }
}
