package com.tranzo.app.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tranzo.app.ui.theme.TranzoColors

// ═══════════════════════════════════════════════════════════════
// CLAYMORPHISM DESIGN SYSTEM
// Inspired by: Soft, puffy, baby-blue clay aesthetic
// Key traits: Solid colors, large rounded corners, puffy shadows
// ═══════════════════════════════════════════════════════════════

/**
 * Clay Button — Solid royal blue pill, puffy colored shadow
 * NO gradients. Clean, bold, trustworthy.
 */
@Composable
fun ClayButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    containerColor: Color = TranzoColors.ClayBlue,
    // Keep gradient params for backward compat, but ignore if containerColor is set
    gradientStart: Color = TranzoColors.ClayBlue,
    gradientEnd: Color = TranzoColors.ClayBlue,
) {
    val buttonColor = containerColor
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .shadow(
                elevation = 16.dp,
                shape = RoundedCornerShape(28.dp),
                ambientColor = buttonColor.copy(alpha = 0.3f),
                spotColor = buttonColor.copy(alpha = 0.25f),
            ),
        enabled = enabled && !isLoading,
        shape = RoundedCornerShape(28.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor,
            contentColor = Color.White,
            disabledContainerColor = buttonColor.copy(alpha = 0.4f),
            disabledContentColor = Color.White.copy(alpha = 0.6f),
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 0.dp,
            pressedElevation = 2.dp,
        ),
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = Color.White,
                strokeWidth = 2.dp,
            )
        } else {
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
            )
        }
    }
}

/**
 * Clay Card — Pure white, puffy shadow, very rounded
 * The signature element of claymorphism.
 */
@Composable
fun ClayCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    cornerRadius: Dp = 28.dp,
    shadowElevation: Dp = 12.dp,
    // Keep for backward compat
    backgroundGradient: List<Color> = listOf(Color.White, Color.White),
    content: @Composable () -> Unit,
) {
    val cardModifier = modifier
        .shadow(
            elevation = shadowElevation,
            shape = RoundedCornerShape(cornerRadius),
            ambientColor = TranzoColors.ClayShadowDark,
            spotColor = TranzoColors.ClayShadowBlue,
        )
        .clip(RoundedCornerShape(cornerRadius))
        .background(Color.White)

    val finalModifier = if (onClick != null) {
        cardModifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = onClick
        )
    } else {
        cardModifier
    }

    Box(
        modifier = finalModifier,
        content = { content() }
    )
}

/**
 * Clay Gradient Card — For accent elements (balance cards, hero sections)
 * Uses gradient background with matching colored shadow.
 */
@Composable
fun ClayGradientCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    gradientStart: Color = TranzoColors.ClayBlue,
    gradientEnd: Color = TranzoColors.PrimaryPurple,
    content: @Composable () -> Unit,
) {
    val cardModifier = modifier
        .shadow(
            elevation = 16.dp,
            shape = RoundedCornerShape(28.dp),
            ambientColor = gradientStart.copy(alpha = 0.25f),
            spotColor = gradientStart.copy(alpha = 0.2f),
        )
        .clip(RoundedCornerShape(28.dp))
        .background(
            brush = Brush.linearGradient(
                colors = listOf(gradientStart, gradientEnd),
            ),
        )

    val finalModifier = if (onClick != null) {
        cardModifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = onClick
        )
    } else {
        cardModifier
    }

    Box(
        modifier = finalModifier,
        content = { content() }
    )
}

/**
 * Clay Text Field — Light gray background, subtle border, clean
 */
@Composable
fun ClayTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    singleLine: Boolean = true,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        placeholder = {
            Text(
                text = placeholder,
                color = TranzoColors.TextTertiary,
                style = MaterialTheme.typography.bodyMedium,
            )
        },
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        keyboardOptions = keyboardOptions,
        singleLine = singleLine,
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = TranzoColors.ClayBlue,
            unfocusedBorderColor = TranzoColors.ClayInputBorder,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = TranzoColors.ClayInputBg,
            cursorColor = TranzoColors.ClayBlue,
        ),
    )
}

/**
 * Clay Stat Card — For displaying metrics/balances
 * Uses gradient for visual emphasis (these are accent elements)
 */
@Composable
fun ClayStatCard(
    label: String,
    value: String,
    unit: String = "",
    modifier: Modifier = Modifier,
    gradientStart: Color = TranzoColors.ClayBlue,
    gradientEnd: Color = TranzoColors.PrimaryPurple,
) {
    ClayGradientCard(
        modifier = modifier.height(120.dp),
        gradientStart = gradientStart,
        gradientEnd = gradientEnd,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = Color.White.copy(alpha = 0.8f),
                fontWeight = FontWeight.Medium,
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    text = value,
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp,
                )
                if (unit.isNotEmpty()) {
                    Text(
                        text = unit,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White.copy(alpha = 0.8f),
                        fontWeight = FontWeight.Medium,
                    )
                }
            }
        }
    }
}

/**
 * Clay Action Button — SOLID colored icon square + label
 * Like the Share/Send/Buy buttons in the reference image.
 * Icon backgrounds are SOLID color, not tinted/transparent.
 */
@Composable
fun ClayActionButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: @Composable (() -> Unit)? = null,
    backgroundColor: Color = TranzoColors.ClayBlue,
) {
    Column(
        modifier = modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = onClick,
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(18.dp),
                    ambientColor = backgroundColor.copy(alpha = 0.3f),
                    spotColor = backgroundColor.copy(alpha = 0.25f),
                )
                .clip(RoundedCornerShape(18.dp))
                .background(backgroundColor),
            contentAlignment = Alignment.Center,
        ) {
            if (icon != null) {
                icon()
            }
        }

        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = TranzoColors.TextPrimary,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp,
        )
    }
}

/**
 * Clay Auth Method Card — Used on Welcome/Login screen
 * White card with icon pill and chevron
 */
@Composable
fun ClayAuthMethodCard(
    icon: ImageVector,
    title: String,
    description: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    iconColor: Color = TranzoColors.ClayBlue,
    // Keep for backward compat
    gradientStart: Color = TranzoColors.ClayBlue,
    gradientEnd: Color = TranzoColors.ClayBlue,
) {
    ClayCard(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp),
        onClick = onClick,
        shadowElevation = 8.dp,
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Solid colored icon square
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(iconColor),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp),
                )
            }

            // Text
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp),
            ) {
                Text(
                    title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = TranzoColors.TextPrimary,
                )
                Text(
                    description,
                    style = MaterialTheme.typography.labelSmall,
                    color = TranzoColors.TextSecondary,
                )
            }

            // Arrow
            Text(
                "\u2192",
                style = MaterialTheme.typography.titleMedium,
                color = TranzoColors.TextTertiary,
            )
        }
    }
}

/**
 * Clay Success Indicator — Checkmark in circle
 */
@Composable
fun ClaySuccessCheckmark(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(64.dp)
            .shadow(
                elevation = 12.dp,
                shape = RoundedCornerShape(24.dp),
                ambientColor = TranzoColors.Success.copy(alpha = 0.3f),
            )
            .clip(RoundedCornerShape(24.dp))
            .background(TranzoColors.Success),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = Icons.Outlined.Check,
            contentDescription = "Success",
            modifier = Modifier.size(32.dp),
            tint = Color.White,
        )
    }
}

/**
 * Clay Badge — Small label pill
 */
@Composable
fun ClayBadge(
    text: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = TranzoColors.ClayBlue,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor.copy(alpha = 0.12f))
            .padding(horizontal = 12.dp, vertical = 6.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            color = backgroundColor,
            fontWeight = FontWeight.SemiBold,
            fontSize = 11.sp,
        )
    }
}


