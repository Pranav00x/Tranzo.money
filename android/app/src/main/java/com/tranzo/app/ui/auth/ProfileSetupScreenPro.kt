package com.tranzo.app.ui.auth

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tranzo.app.ui.components.TranzoButton
import com.tranzo.app.ui.components.TranzoSecondaryButton
import com.tranzo.app.ui.components.TranzoTextField
import com.tranzo.app.ui.theme.TranzoColors

/**
 * Bold profile setup screen - Modern form with confidence
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
    var currentStep by remember { mutableStateOf(1) }

    // Validation
    val firstNameValid = firstName.length >= 2
    val lastNameValid = lastName.length >= 2
    val emailValid = email.contains("@")
    val allValid = firstNameValid && lastNameValid && emailValid

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(TranzoColors.BackgroundLight)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Progress header
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            "Create your profile",
                            style = MaterialTheme.typography.displaySmall,
                            fontWeight = FontWeight.ExtraBold,
                            color = TranzoColors.TextPrimary
                        )
                        Text(
                            "Step 1 of 2",
                            style = MaterialTheme.typography.labelSmall,
                            color = TranzoColors.TextTertiary
                        )
                    }
                    Surface(
                        modifier = Modifier.size(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        color = TranzoColors.PrimaryBlue.copy(alpha = 0.1f)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "1/2",
                                style = MaterialTheme.typography.labelLarge,
                                color = TranzoColors.PrimaryBlue,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                // Progress bar
                LinearProgressIndicator(
                    progress = { 0.5f },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(3.dp),
                    color = TranzoColors.PrimaryBlue,
                    trackColor = TranzoColors.SurfaceLight
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Form fields with animation
            AnimatedVisibility(
                visible = true,
                enter = slideInVertically()
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // First Name
                    ProfileInputField(
                        label = "First name",
                        placeholder = "John",
                        value = firstName,
                        onValueChange = { firstName = it },
                        icon = Icons.Outlined.Person,
                        isValid = firstNameValid,
                        isEditing = firstName.isNotEmpty()
                    )

                    // Last Name
                    ProfileInputField(
                        label = "Last name",
                        placeholder = "Doe",
                        value = lastName,
                        onValueChange = { lastName = it },
                        icon = Icons.Outlined.Person,
                        isValid = lastNameValid,
                        isEditing = lastName.isNotEmpty()
                    )

                    // Email (read-only if prefilled)
                    ProfileInputField(
                        label = "Email",
                        placeholder = "you@example.com",
                        value = email,
                        onValueChange = { if (prefilledEmail.isEmpty()) email = it },
                        icon = Icons.Outlined.Email,
                        isValid = emailValid,
                        isEditing = email.isNotEmpty(),
                        enabled = prefilledEmail.isEmpty(),
                        keyboardType = KeyboardType.Email
                    )

                    // Phone (optional)
                    ProfileInputField(
                        label = "Phone (optional)",
                        placeholder = "+1 (555) 000-0000",
                        value = phone,
                        onValueChange = { phone = it },
                        icon = Icons.Outlined.Phone,
                        isValid = true,
                        isEditing = phone.isNotEmpty(),
                        keyboardType = KeyboardType.Phone
                    )

                    // Language selector
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(2.dp, TranzoColors.SurfaceLight, RoundedCornerShape(12.dp)),
                        shape = RoundedCornerShape(12.dp),
                        color = Color.White
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Language,
                                    contentDescription = null,
                                    tint = TranzoColors.PrimaryBlue,
                                    modifier = Modifier.size(20.dp)
                                )
                                Column {
                                    Text(
                                        "Language",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = TranzoColors.TextTertiary
                                    )
                                    Text(
                                        "English",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = TranzoColors.TextPrimary,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }
                            Icon(
                                imageVector = Icons.Outlined.ChevronRight,
                                contentDescription = null,
                                tint = TranzoColors.TextTertiary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }

            // Error message
            if (state.error != null) {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    color = TranzoColors.Error.copy(alpha = 0.1f)
                ) {
                    Text(
                        state.error!!,
                        style = MaterialTheme.typography.bodySmall,
                        color = TranzoColors.Error,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Action buttons
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                TranzoButton(
                    text = "Continue",
                    onClick = {
                        onContinue(firstName, lastName, email, phone, language)
                    },
                    enabled = allValid && !isLoading,
                    isLoading = isLoading,
                    modifier = Modifier.fillMaxWidth()
                )

                TranzoSecondaryButton(
                    text = "Skip for now",
                    onClick = onSkip,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun ProfileInputField(
    label: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    icon: ImageVector,
    isValid: Boolean,
    isEditing: Boolean,
    enabled: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                2.dp,
                color = when {
                    !enabled -> TranzoColors.TextDisabled
                    isValid && isEditing -> TranzoColors.Success
                    isEditing -> TranzoColors.Error
                    else -> TranzoColors.SurfaceLight
                },
                shape = RoundedCornerShape(12.dp)
            ),
        shape = RoundedCornerShape(12.dp),
        color = if (enabled) Color.White else TranzoColors.SurfaceLight.copy(alpha = 0.5f)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = TranzoColors.PrimaryBlue,
                modifier = Modifier.size(20.dp)
            )

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    label,
                    style = MaterialTheme.typography.labelSmall,
                    color = TranzoColors.TextTertiary
                )

                TextField(
                    value = value,
                    onValueChange = onValueChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    placeholder = { Text(placeholder) },
                    textStyle = MaterialTheme.typography.bodyMedium,
                    enabled = enabled,
                    keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent
                    ),
                    singleLine = true
                )
            }

            if (isEditing && isValid) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = "Valid",
                    tint = TranzoColors.Success,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}