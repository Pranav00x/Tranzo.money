package com.tranzo.app.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.tranzo.app.ui.theme.TranzoColors

/**
 * Primary CTA — Full-width green pill button.
 * Matches CheQ's "Get OTP", "Continue", "+ Add New Card" style.
 */
@Composable
fun TranzoButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false,
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        enabled = enabled && !isLoading,
        shape = RoundedCornerShape(28.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = TranzoColors.PrimaryGreen,
            contentColor = TranzoColors.TextOnGreen,
            disabledContainerColor = TranzoColors.PrimaryGreen.copy(alpha = 0.4f),
            disabledContentColor = TranzoColors.TextOnGreen.copy(alpha = 0.6f),
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
        ),
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = TranzoColors.TextOnGreen,
                strokeWidth = 2.dp,
            )
        } else {
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge,
            )
        }
    }
}

/**
 * Secondary button — light green tinted pill with green text.
 * Matches CheQ's "Skip" button style.
 */
@Composable
fun TranzoSecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = RoundedCornerShape(28.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = TranzoColors.SkipButtonBg,
            contentColor = TranzoColors.SkipButtonText,
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 0.dp,
        ),
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
        )
    }
}

/**
 * Outlined text field with floating label.
 * Matches CheQ's "Mobile Number" input style.
 */
@Composable
fun TranzoTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    isError: Boolean = false,
    errorMessage: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    singleLine: Boolean = true,
) {
    Column(modifier = modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(label) },
            placeholder = if (placeholder.isNotEmpty()) {
                { Text(placeholder, color = TranzoColors.TextTertiary) }
            } else null,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            isError = isError,
            singleLine = singleLine,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = TranzoColors.PrimaryGreen,
                unfocusedBorderColor = TranzoColors.BorderGray,
                errorBorderColor = TranzoColors.Error,
                focusedLabelColor = TranzoColors.PrimaryGreen,
                unfocusedLabelColor = TranzoColors.TextSecondary,
                cursorColor = TranzoColors.PrimaryGreen,
            ),
        )

        if (isError && errorMessage != null) {
            Text(
                text = errorMessage,
                color = TranzoColors.Error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp),
            )
        }
    }
}

/**
 * Content card — white with rounded corners.
 * Matches CheQ's feature cards (Education Fee, Utilities, etc.)
 */
@Composable
fun TranzoCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    if (onClick != null) {
        Card(
            onClick = onClick,
            modifier = modifier,
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = TranzoColors.CardSurface,
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 1.dp,
            ),
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                content = content,
            )
        }
    } else {
        Card(
            modifier = modifier,
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = TranzoColors.CardSurface,
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 1.dp,
            ),
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                content = content,
            )
        }
    }
}

/**
 * Security badges row — "100% Secure" with shield icons.
 * Matches CheQ's splash/auth screen bottom section.
 */
@Composable
fun SecurityBadges(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 8.dp),
        ) {
            Text(
                text = "🔒 256-bit encrypted",
                style = MaterialTheme.typography.labelSmall,
                color = TranzoColors.TextTertiary,
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "🛡️ Self-custody",
                style = MaterialTheme.typography.labelSmall,
                color = TranzoColors.TextTertiary,
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            HorizontalDivider(
                modifier = Modifier.width(40.dp),
                color = TranzoColors.DividerGray,
            )
            Text(
                text = "  100% Secure  ",
                style = MaterialTheme.typography.labelMedium,
                color = TranzoColors.TextSecondary,
            )
            HorizontalDivider(
                modifier = Modifier.width(40.dp),
                color = TranzoColors.DividerGray,
            )
        }
    }
}

/**
 * Green status badge — "50% Complete", "New", "Popular"
 */
@Composable
fun StatusBadge(
    text: String,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
) {
    val bgColor = if (isError) TranzoColors.BadgeRedBg else TranzoColors.BadgeGreenBg
    val textColor = if (isError) TranzoColors.BadgeRed else TranzoColors.BadgeGreen

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(6.dp))
            .background(bgColor)
            .padding(horizontal = 8.dp, vertical = 4.dp),
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
            color = textColor,
        )
    }
}
