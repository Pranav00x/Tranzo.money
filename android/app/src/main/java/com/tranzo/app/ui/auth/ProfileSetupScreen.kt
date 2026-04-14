package com.tranzo.app.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.tranzo.app.ui.components.SecurityBadges
import com.tranzo.app.ui.components.TranzoButton
import com.tranzo.app.ui.components.TranzoTextField
import com.tranzo.app.ui.theme.TranzoColors

/**
 * Profile setup screen — shown after OTP verification for new users,
 * before wallet creation.
 *
 * Layout (CheQ "Share Your Details" style):
 * - "Share Your Details" headline
 * - "Get a Tranzo experience personalised for you" subtitle
 * - First name field
 * - Last name field
 * - Email field (pre-filled + read-only if coming from email OTP flow)
 * - Green pill "Continue" CTA
 * - "Skip for now" secondary option
 */
@Composable
fun ProfileSetupScreen(
    /** Pre-filled email from OTP flow — shown read-only. Pass empty to show editable field. */
    prefilledEmail: String = "",
    /** Called with (firstName, lastName, email) when user taps Continue. */
    onContinue: (firstName: String, lastName: String, email: String) -> Unit = { _, _, _ -> },
    /** Called when user taps "Skip for now". */
    onSkip: () -> Unit = {},
    /** Whether the parent is executing a save operation. */
    isLoading: Boolean = false,
) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf(prefilledEmail) }

    var firstNameError by remember { mutableStateOf<String?>(null) }
    var lastNameError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }

    val focusManager = LocalFocusManager.current

    fun validate(): Boolean {
        firstNameError = if (firstName.isBlank()) "Please enter your first name" else null
        lastNameError = if (lastName.isBlank()) "Please enter your last name" else null
        emailError = when {
            email.isBlank()         -> "Please enter your email"
            !email.contains("@")   -> "Enter a valid email address"
            else                    -> null
        }
        return firstNameError == null && lastNameError == null && emailError == null
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(TranzoColors.White)
            .systemBarsPadding(),
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
        ) {
            Spacer(modifier = Modifier.height(48.dp))

            // ── Headline ─────────────────────────────────────────────
            Text(
                text = "Share Your Details",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = TranzoColors.TextPrimary,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Get a Tranzo experience personalised for you",
                style = MaterialTheme.typography.bodyLarge,
                color = TranzoColors.TextSecondary,
            )

            Spacer(modifier = Modifier.height(40.dp))

            // ── First Name ───────────────────────────────────────────
            TranzoTextField(
                value = firstName,
                onValueChange = {
                    firstName = it
                    if (firstNameError != null) firstNameError = null
                },
                label = "First Name",
                placeholder = "e.g. Pranav",
                isError = firstNameError != null,
                errorMessage = firstNameError,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Person,
                        contentDescription = null,
                        tint = TranzoColors.TextTertiary,
                        modifier = Modifier.size(20.dp),
                    )
                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) },
                ),
            )

            Spacer(modifier = Modifier.height(20.dp))

            // ── Last Name ────────────────────────────────────────────
            TranzoTextField(
                value = lastName,
                onValueChange = {
                    lastName = it
                    if (lastNameError != null) lastNameError = null
                },
                label = "Last Name",
                placeholder = "e.g. Jha",
                isError = lastNameError != null,
                errorMessage = lastNameError,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Person,
                        contentDescription = null,
                        tint = TranzoColors.TextTertiary,
                        modifier = Modifier.size(20.dp),
                    )
                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) },
                ),
            )

            Spacer(modifier = Modifier.height(20.dp))

            // ── Email ────────────────────────────────────────────────
            if (prefilledEmail.isNotEmpty()) {
                // Read-only display when email came from OTP flow
                Column {
                    Text(
                        text = "Email",
                        style = MaterialTheme.typography.labelMedium,
                        color = TranzoColors.TextSecondary,
                        modifier = Modifier.padding(start = 4.dp, bottom = 8.dp),
                    )
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = TranzoColors.LightGray,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Email,
                                contentDescription = null,
                                tint = TranzoColors.TextTertiary,
                                modifier = Modifier.size(20.dp),
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = prefilledEmail,
                                style = MaterialTheme.typography.bodyLarge,
                                color = TranzoColors.TextSecondary,
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Icon(
                                imageVector = Icons.Outlined.CheckCircle,
                                contentDescription = "Verified",
                                tint = TranzoColors.PrimaryGreen,
                                modifier = Modifier.size(18.dp),
                            )
                        }
                    }
                    Text(
                        text = "Verified via OTP",
                        style = MaterialTheme.typography.labelSmall,
                        color = TranzoColors.PrimaryGreen,
                        modifier = Modifier.padding(start = 4.dp, top = 4.dp),
                    )
                }
            } else {
                TranzoTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        if (emailError != null) emailError = null
                    },
                    label = "Email Address",
                    placeholder = "you@example.com",
                    isError = emailError != null,
                    errorMessage = emailError,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Email,
                            contentDescription = null,
                            tint = TranzoColors.TextTertiary,
                            modifier = Modifier.size(20.dp),
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Done,
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                            if (validate()) onContinue(firstName.trim(), lastName.trim(), email.trim())
                        },
                    ),
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Privacy reassurance note
            Row(
                verticalAlignment = Alignment.Top,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
            ) {
                Icon(
                    imageVector = Icons.Outlined.Lock,
                    contentDescription = null,
                    tint = TranzoColors.TextTertiary,
                    modifier = Modifier
                        .size(16.dp)
                        .padding(top = 1.dp),
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Your details are encrypted and never shared with third parties.",
                    style = MaterialTheme.typography.bodySmall,
                    color = TranzoColors.TextTertiary,
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        // ── CTA Section ──────────────────────────────────────────────
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(TranzoColors.White)
                .padding(horizontal = 24.dp)
                .padding(bottom = 16.dp),
        ) {
            TranzoButton(
                text = "Continue",
                onClick = {
                    focusManager.clearFocus()
                    if (validate()) {
                        onContinue(firstName.trim(), lastName.trim(), email.trim())
                    }
                },
                enabled = firstName.isNotBlank() && lastName.isNotBlank() &&
                        (prefilledEmail.isNotEmpty() || email.contains("@")),
                isLoading = isLoading,
            )

            Spacer(modifier = Modifier.height(12.dp))

            TextButton(
                onClick = onSkip,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = "Skip for now",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TranzoColors.TextSecondary,
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            SecurityBadges()

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
