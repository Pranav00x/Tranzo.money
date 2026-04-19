package com.tranzo.app.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.tranzo.app.ui.theme.TranzoColors

/**
 * Tranzo Logo — White rounded square with a black diamond.
 * Matches the uploaded app icon design.
 */
@Composable
fun TranzoLogo(
    modifier: Modifier = Modifier,
    size: androidx.compose.ui.unit.Dp = 48.dp,
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(RoundedCornerShape(size * 0.22f))
            .background(TranzoColors.White)
            .padding(size * 0.25f),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .rotate(45f)
                .clip(RoundedCornerShape(size * 0.05f))
                .background(TranzoColors.TextPrimary)
        )
    }
}

/**
 * Primary CTA — Full-width pill button.
 */
@Composable
fun TranzoButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    btnColor: Color = TranzoColors.TextPrimary,
    textColor: Color = TranzoColors.White,
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        enabled = enabled && !isLoading,
        shape = RoundedCornerShape(28.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = btnColor,
            contentColor = textColor,
            disabledContainerColor = btnColor.copy(alpha = 0.4f),
            disabledContentColor = textColor.copy(alpha = 0.6f),
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
        ),
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = TranzoColors.White,
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
 * Secondary button — light tinted pill.
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
            containerColor = TranzoColors.SurfaceLight,
            contentColor = TranzoColors.TextPrimary,
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
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
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
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = TranzoColors.TextPrimary,
                unfocusedBorderColor = TranzoColors.DividerGray,
                errorBorderColor = TranzoColors.Error,
                focusedLabelColor = TranzoColors.TextPrimary,
                unfocusedLabelColor = TranzoColors.TextSecondary,
                cursorColor = TranzoColors.TextPrimary,
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
                containerColor = TranzoColors.SurfaceLight,
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
                containerColor = TranzoColors.SurfaceLight,
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
 * Security badges row — clean, no emojis.
 * Uses Material icons for a professional look.
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
            Icon(
                imageVector = Icons.Outlined.Lock,
                contentDescription = null,
                modifier = Modifier.size(14.dp),
                tint = TranzoColors.TextTertiary,
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "256-bit encrypted",
                style = MaterialTheme.typography.labelSmall,
                color = TranzoColors.TextTertiary,
            )
            Spacer(modifier = Modifier.width(16.dp))
            Icon(
                imageVector = Icons.Outlined.Shield,
                contentDescription = null,
                modifier = Modifier.size(14.dp),
                tint = TranzoColors.TextTertiary,
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Self-custody",
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
 * Status badge
 */
@Composable
fun StatusBadge(
    text: String,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
) {
    val bgColor = if (isError) TranzoColors.Error.copy(alpha = 0.1f) else TranzoColors.Success.copy(alpha = 0.1f)
    val textColor = if (isError) TranzoColors.Error else TranzoColors.Success

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
