package com.tranzo.app.ui.auth

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tranzo.app.ui.components.ClayButton
import com.tranzo.app.ui.components.ClayCard
import com.tranzo.app.ui.components.ClayTextField
import com.tranzo.app.ui.theme.TranzoColors

/**
 * Claymorphism Profile Setup Screen
 */
@Composable
fun ProfileSetupScreenProClay(
    viewModel: AuthViewModel = hiltViewModel(),
    onProfileCreated: () -> Unit = {},
) {
    var fullName by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    val uiState by viewModel.state.collectAsState()

    var showContent by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        showContent = true
    }

    val contentAlpha by animateFloatAsState(
        targetValue = if (showContent) 1f else 0f,
        animationSpec = tween(800),
        label = "content fade in"
    )

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
                .alpha(contentAlpha)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            // Header
            Text(
                "Create Your Profile",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = TranzoColors.TextPrimary,
                fontSize = 28.sp
            )

            Text(
                "Set up your Tranzo account",
                style = MaterialTheme.typography.bodyMedium,
                color = TranzoColors.TextSecondary
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Avatar preview
            ClayCard(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(80.dp),
                backgroundGradient = listOf(
                    TranzoColors.PrimaryBlue,
                    TranzoColors.PrimaryPurple
                )
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        fullName.firstOrNull()?.uppercaseChar()?.toString() ?: "?",
                        style = MaterialTheme.typography.displayMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 36.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Form fields
            ClayTextField(
                value = fullName,
                onValueChange = { fullName = it },
                placeholder = "Full Name",
                leadingIcon = {
                    androidx.compose.material3.Icon(
                        Icons.Outlined.Person,
                        contentDescription = null,
                        tint = TranzoColors.PrimaryBlue,
                        modifier = Modifier.size(20.dp)
                    )
                }
            )

            ClayTextField(
                value = username,
                onValueChange = { username = it },
                placeholder = "Username",
                leadingIcon = {
                    Text("@", color = TranzoColors.TextTertiary, fontWeight = FontWeight.Bold)
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            ClayButton(
                text = "Continue",
                onClick = {
                    val names = fullName.trim().split(" ")
                    val firstName = names.firstOrNull() ?: ""
                    val lastName = if (names.size > 1) names.drop(1).joinToString(" ") else ""
                    
                    viewModel.saveProfile(
                        firstName = firstName,
                        lastName = lastName,
                        email = uiState.lastEmail ?: ""
                    )
                    onProfileCreated()
                },
                enabled = fullName.isNotBlank() && username.isNotBlank(),
                gradientStart = TranzoColors.PrimaryBlue,
                gradientEnd = TranzoColors.PrimaryPurple,
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}


