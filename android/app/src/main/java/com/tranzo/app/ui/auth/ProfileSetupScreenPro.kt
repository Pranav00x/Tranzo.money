package com.tranzo.app.ui.auth

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

/**
 * CheQ-inspired profile setup — monochrome, clean form fields.
 * Shown for new users after authentication.
 */
@Composable
fun ProfileSetupScreenPro(
    prefilledEmail: String = "",
    viewModel: AuthViewModel = hiltViewModel(),
    onContinue: (firstName: String, lastName: String, email: String, phone: String, language: String) -> Unit = { _, _, _, _, _ -> },
    onSkip: () -> Unit = {},
    isLoading: Boolean = false,
) {
    val state by viewModel.state.collectAsState()
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf(prefilledEmail) }
    var phone by remember { mutableStateOf("") }
    var language by remember { mutableStateOf("en") }

    val firstNameValid = firstName.length >= 2
    val lastNameValid = lastName.length >= 2
    val emailValid = email.contains("@")
    val allValid = firstNameValid && lastNameValid && emailValid

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .systemBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 32.dp)
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            // Avatar circle with initials
            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF1A1A1A)),
                contentAlignment = Alignment.Center
            ) {
                val initials = "${firstName.firstOrNull()?.uppercase() ?: ""}${lastName.firstOrNull()?.uppercase() ?: ""}"
                if (initials.isNotBlank()) {
                    Text(
                        initials,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                } else {
                    Icon(
                        Icons.Outlined.Person,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                "Create your profile",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1A1A1A),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                "Tell us a bit about yourself",
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF999999),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(36.dp))

            // Form fields
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                MonoTextField(
                    value = firstName,
                    onValueChange = { firstName = it },
                    label = "First name",
                    placeholder = "John",
                    isValid = firstNameValid && firstName.isNotEmpty()
                )

                MonoTextField(
                    value = lastName,
                    onValueChange = { lastName = it },
                    label = "Last name",
                    placeholder = "Doe",
                    isValid = lastNameValid && lastName.isNotEmpty()
                )

                MonoTextField(
                    value = email,
                    onValueChange = { if (prefilledEmail.isEmpty()) email = it },
                    label = "Email",
                    placeholder = "you@example.com",
                    enabled = prefilledEmail.isEmpty(),
                    isValid = emailValid && email.isNotEmpty(),
                    keyboardType = KeyboardType.Email
                )

                MonoTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = "Phone (optional)",
                    placeholder = "+1 (555) 000-0000",
                    isValid = true,
                    keyboardType = KeyboardType.Phone
                )
            }

            // Error message
            state.error?.let { err ->
                Spacer(modifier = Modifier.height(16.dp))
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFFFFF0F0)
                ) {
                    Text(
                        err,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFFCC0000),
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }
        }

        // Bottom buttons
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { onContinue(firstName, lastName, email, phone, language) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = allValid && !isLoading,
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1A1A1A),
                    contentColor = Color.White,
                    disabledContainerColor = Color(0xFFE0E0E0),
                    disabledContentColor = Color(0xFF999999)
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Continue", fontWeight = FontWeight.SemiBold)
                }
            }

            TextButton(
                onClick = onSkip,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    "Skip for now",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF999999)
                )
            }
        }
    }
}

@Composable
private fun MonoTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    isValid: Boolean = false,
    enabled: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(label) },
        placeholder = { Text(placeholder) },
        singleLine = true,
        enabled = enabled,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        shape = RoundedCornerShape(14.dp),
        trailingIcon = {
            if (isValid && value.isNotEmpty()) {
                Icon(
                    Icons.Filled.Check,
                    contentDescription = "Valid",
                    tint = Color(0xFF22C55E),
                    modifier = Modifier.size(18.dp)
                )
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFF1A1A1A),
            unfocusedBorderColor = Color(0xFFE0E0E0),
            disabledBorderColor = Color(0xFFE0E0E0),
            focusedLabelColor = Color(0xFF1A1A1A),
            unfocusedLabelColor = Color(0xFF999999),
            cursorColor = Color(0xFF1A1A1A),
        )
    )
}