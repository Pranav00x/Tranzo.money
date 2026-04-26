package com.tranzo.app.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tranzo.app.ui.theme.TranzoColors

// ═══════════════════════════════════════════════════════════════
// MINIMAL DESIGN SYSTEM — Clean, Flat, Bordered
// Replaced Claymorphism with a strict minimalist aesthetic.
// ═══════════════════════════════════════════════════════════════

fun Modifier.minimalEffect(
    cornerRadius: Dp = 16.dp,
    borderColor: Color = TranzoColors.DividerGray,
    borderWidth: Dp = 1.dp
): Modifier = this
    .clip(RoundedCornerShape(cornerRadius))
    .border(borderWidth, borderColor, RoundedCornerShape(cornerRadius))

@Composable
fun ClayButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    containerColor: Color = TranzoColors.TextPrimary, // Minimal black default
    gradientStart: Color = containerColor,
    gradientEnd: Color = containerColor,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.98f else 1f,
        animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
        label = "button scale"
    )

    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            },
        interactionSource = interactionSource,
        enabled = enabled && !isLoading,
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = Color.White,
            disabledContainerColor = containerColor.copy(alpha = 0.5f),
            disabledContentColor = Color.White.copy(alpha = 0.5f),
        ),
        elevation = ButtonDefaults.buttonElevation(0.dp, 0.dp),
    ) {
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.size(22.dp), color = Color.White, strokeWidth = 2.5.dp)
        } else {
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                letterSpacing = 0.5.sp,
            )
        }
    }
}

@Composable
fun ClayCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    cornerRadius: Dp = 16.dp,
    shadowElevation: Dp = 0.dp, // No shadow in minimal
    backgroundGradient: List<Color> = listOf(Color.White, Color.White),
    content: @Composable () -> Unit,
) {
    val cardModifier = modifier
        .minimalEffect(cornerRadius = cornerRadius, borderColor = TranzoColors.DividerGray)
        .background(Color.White)
        .let { if (onClick != null) it.clickable(onClick = onClick) else it }

    Box(modifier = cardModifier) {
        content()
    }
}

@Composable
fun ClayGradientCard(
    modifier: Modifier = Modifier,
    gradientStart: Color,
    gradientEnd: Color,
    cornerRadius: Dp = 16.dp,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    val cardModifier = modifier
        .clip(RoundedCornerShape(cornerRadius))
        .background(Brush.linearGradient(listOf(gradientStart, gradientEnd)))
        .let { if (onClick != null) it.clickable(onClick = onClick) else it }

    Box(modifier = cardModifier) {
        content()
    }
}

@Composable
fun ClayStatCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier,
    gradientStart: Color = TranzoColors.ClayBackgroundAlt,
    gradientEnd: Color = TranzoColors.ClayBackgroundAlt,
) {
    Box(
        modifier = modifier
            .minimalEffect(cornerRadius = 16.dp)
            .background(TranzoColors.ClayBackgroundAlt)
            .padding(16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Column {
            Text(title, style = MaterialTheme.typography.labelSmall, color = TranzoColors.TextSecondary, fontWeight = FontWeight.Medium)
            Spacer(Modifier.height(4.dp))
            Text(value, style = MaterialTheme.typography.headlineSmall, color = TranzoColors.TextPrimary, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun ClayIconPill(
    color: Color = TranzoColors.TextPrimary,
    size: Dp = 48.dp,
    cornerRadius: Dp = 12.dp,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = Modifier
            .size(size)
            .clip(RoundedCornerShape(cornerRadius))
            .background(color.copy(alpha = 0.1f)), // Light background tint for minimal icon
        contentAlignment = Alignment.Center
    ) {
        CompositionLocalProvider(LocalContentColor provides color) {
            content()
        }
    }
}

@Composable
fun ClayTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    isError: Boolean = false,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth().height(56.dp),
        placeholder = { Text(placeholder, color = TranzoColors.TextDisabled, style = MaterialTheme.typography.bodyLarge) },
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        keyboardOptions = keyboardOptions,
        isError = isError,
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedBorderColor = TranzoColors.TextPrimary,
            unfocusedBorderColor = TranzoColors.DividerGray,
            errorBorderColor = TranzoColors.ClayCoral,
            cursorColor = TranzoColors.TextPrimary,
        ),
        textStyle = MaterialTheme.typography.bodyLarge.copy(color = TranzoColors.TextPrimary, fontWeight = FontWeight.Medium)
    )
}

@Composable
fun ClayDecoBlob(
    color: Color,
    size: Dp,
    modifier: Modifier = Modifier,
) {
    // Hidden in minimal design
}

@Composable
fun ClayAuthMethodCard(
    icon: ImageVector,
    title: String,
    description: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    iconColor: Color = TranzoColors.TextPrimary
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .minimalEffect(cornerRadius = 14.dp)
            .background(Color.White)
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier.size(40.dp).clip(CircleShape).background(TranzoColors.ClayBackgroundAlt),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = TranzoColors.TextPrimary, modifier = Modifier.size(20.dp))
        }
        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(title, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold, color = TranzoColors.TextPrimary)
            Text(description, style = MaterialTheme.typography.bodySmall, color = TranzoColors.TextSecondary)
        }
    }
}
