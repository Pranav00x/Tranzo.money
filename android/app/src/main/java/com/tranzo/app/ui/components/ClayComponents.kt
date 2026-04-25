package com.tranzo.app.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tranzo.app.ui.theme.TranzoColors

/**
 * Clay Button - Premium gradient pill button with soft shadow
 */
@Composable
fun ClayButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    gradientStart: Color = TranzoColors.PrimaryBlue,
    gradientEnd: Color = TranzoColors.PrimaryPurple,
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .shadow(
                elevation = 12.dp,
                shape = RoundedCornerShape(24.dp),
                ambientColor = gradientStart.copy(alpha = 0.15f)
            ),
        enabled = enabled && !isLoading,
        shape = RoundedCornerShape(24.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = TranzoColors.White,
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
        contentPadding = PaddingValues(0.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(gradientStart, gradientEnd),
                        start = androidx.compose.ui.geometry.Offset(0f, 0f),
                        end = androidx.compose.ui.geometry.Offset(500f, 500f)
                    ),
                    shape = RoundedCornerShape(24.dp)
                ),
            contentAlignment = Alignment.Center,
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
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                )
            }
        }
    }
}

/**
 * Clay Card - Soft rounded container with subtle gradient and shadow
 */
@Composable
fun ClayCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    backgroundGradient: List<Color> = listOf(
        TranzoColors.BackgroundLight,
        TranzoColors.SurfaceLight
    ),
    content: @Composable () -> Unit,
) {
    val baseModifier = modifier
        .clip(RoundedCornerShape(28.dp))
        .background(
            brush = Brush.linearGradient(
                colors = backgroundGradient,
                start = androidx.compose.ui.geometry.Offset(0f, 0f),
                end = androidx.compose.ui.geometry.Offset(300f, 300f)
            ),
            shape = RoundedCornerShape(28.dp)
        )
        .shadow(
            elevation = 8.dp,
            shape = RoundedCornerShape(28.dp),
            ambientColor = Color.Black.copy(alpha = 0.08f)
        )

    val finalModifier = if (onClick != null) {
        baseModifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = onClick
        )
    } else {
        baseModifier
    }

    Box(
        modifier = finalModifier,
        content = { content() }
    )
}

/**
 * Clay Gradient Card - Card with vibrant gradient background
 */
@Composable
fun ClayGradientCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    gradientStart: Color = TranzoColors.PrimaryBlue,
    gradientEnd: Color = TranzoColors.PrimaryPurple,
    content: @Composable () -> Unit,
) {
    ClayCard(
        modifier = modifier,
        onClick = onClick,
        backgroundGradient = listOf(gradientStart, gradientEnd),
        content = content
    )
}

/**
 * Clay Input Field - Soft rounded input with subtle shadow
 */
@Composable
fun ClayTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(24.dp),
                ambientColor = Color.Black.copy(alpha = 0.05f)
            ),
        placeholder = {
            Text(
                text = placeholder,
                color = TranzoColors.TextTertiary,
                style = MaterialTheme.typography.bodyMedium,
            )
        },
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        shape = RoundedCornerShape(24.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = TranzoColors.PrimaryBlue,
            unfocusedBorderColor = TranzoColors.DividerGray,
            focusedContainerColor = TranzoColors.White,
            unfocusedContainerColor = TranzoColors.BackgroundLight,
        ),
        singleLine = true,
        textStyle = MaterialTheme.typography.bodyMedium,
    )
}

/**
 * Clay Stat Card - For displaying metrics/balances
 */
@Composable
fun ClayStatCard(
    label: String,
    value: String,
    unit: String = "",
    modifier: Modifier = Modifier,
    gradientStart: Color = TranzoColors.PrimaryBlue,
    gradientEnd: Color = TranzoColors.BlueLight,
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
                color = TranzoColors.White.copy(alpha = 0.8f),
                fontWeight = FontWeight.Medium,
            )

            Row(
                verticalAlignment = Alignment.Baseline,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    text = value,
                    style = MaterialTheme.typography.headlineMedium,
                    color = TranzoColors.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp,
                )
                Text(
                    text = unit,
                    style = MaterialTheme.typography.labelSmall,
                    color = TranzoColors.White.copy(alpha = 0.8f),
                    fontWeight = FontWeight.Medium,
                )
            }
        }
    }
}

/**
 * Clay Action Button - Icon + text, soft hover effect
 */
@Composable
fun ClayActionButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: @Composable (() -> Unit)? = null,
    backgroundColor: Color = TranzoColors.SurfaceLight,
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
        if (icon != null) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        color = backgroundColor,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .shadow(
                        elevation = 6.dp,
                        shape = RoundedCornerShape(20.dp),
                        ambientColor = Color.Black.copy(alpha = 0.08f)
                    ),
                contentAlignment = Alignment.Center,
            ) {
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
 * Clay Success Indicator - Checkmark in circle with gradient
 */
@Composable
fun ClaySuccessCheckmark(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(64.dp)
            .clip(RoundedCornerShape(28.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        TranzoColors.Success.copy(alpha = 0.2f),
                        TranzoColors.PrimaryGreen.copy(alpha = 0.15f)
                    )
                ),
                shape = RoundedCornerShape(28.dp)
            )
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(28.dp),
                ambientColor = TranzoColors.Success.copy(alpha = 0.15f)
            ),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = Icons.Outlined.Check,
            contentDescription = "Success",
            modifier = Modifier.size(32.dp),
            tint = TranzoColors.Success,
        )
    }
}

/**
 * Clay Badge - Small label with gradient background
 */
@Composable
fun ClayBadge(
    text: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = TranzoColors.PrimaryBlue,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(
                color = backgroundColor.copy(alpha = 0.15f),
                shape = RoundedCornerShape(12.dp)
            )
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
