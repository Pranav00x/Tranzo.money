package com.tranzo.app.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tranzo.app.ui.components.TranzoButton
import com.tranzo.app.ui.components.TranzoTextField
import com.tranzo.app.ui.theme.TranzoColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactFormScreen(
    onBackClick: () -> Unit = {},
    onSubmit: (subject: String, message: String, email: String) -> Unit = { _, _, _ -> },
) {
    var email by remember { mutableStateOf("") }
    var subject by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var isSubmitting by remember { mutableStateOf(false) }
    var submitSuccess by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(TranzoColors.BackgroundLight),
    ) {
        // Top bar
        TopAppBar(
            title = {
                Text(
                    text = "Contact Us",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                )
            },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                        contentDescription = "Back",
                        tint = TranzoColors.TextPrimary,
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = TranzoColors.BackgroundLight,
            ),
        )

        if (submitSuccess) {
            // Success state
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = "✅ Message Sent!",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = TranzoColors.TextPrimary,
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Thanks for reaching out. Our team will get back to you soon.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TranzoColors.TextSecondary,
                )
                Spacer(modifier = Modifier.height(24.dp))
                TranzoButton(
                    text = "Back to Settings",
                    onClick = onBackClick,
                )
            }
        } else {
            // Form state
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp)
                    .padding(top = 12.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Text(
                    text = "We're here to help",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TranzoColors.TextSecondary,
                )

                TranzoTextField(
                    value = email,
                    onValueChange = { email = it.trim() },
                    label = "Email Address",
                    placeholder = "you@example.com",
                )

                TranzoTextField(
                    value = subject,
                    onValueChange = { subject = it },
                    label = "Subject",
                    placeholder = "e.g., Account Issue, Feature Request",
                )

                TranzoTextField(
                    value = message,
                    onValueChange = { message = it },
                    label = "Message",
                    placeholder = "Tell us what we can help with...",
                    singleLine = false,
                )

                Text(
                    text = "💡 Tip: Be specific so we can help faster",
                    style = MaterialTheme.typography.labelSmall,
                    color = TranzoColors.TextTertiary,
                )

                Spacer(modifier = Modifier.height(24.dp))

                TranzoButton(
                    text = "Send Message",
                    onClick = {
                        if (email.isNotBlank() && subject.isNotBlank() && message.isNotBlank()) {
                            isSubmitting = true
                            onSubmit(subject, message, email)
                            // Simulate submission delay
                            submitSuccess = true
                        }
                    },
                    enabled = email.contains("@") && subject.isNotBlank() && message.isNotBlank() && !isSubmitting,
                    isLoading = isSubmitting,
                )

                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}
