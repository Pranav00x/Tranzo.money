package com.tranzo.app.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tranzo.app.ui.theme.TranzoColors

// ═══════════════════════════════════════════════════════════════
// CLAYMORPHISM DESIGN SYSTEM v2 — PREMIUM TACTILE UI
//
// Design DNA: Sculpted matte clay surfaces with 3D depth.
// Every element looks like it was pressed into soft material.
// Inner glow at top-left + colored shadow at bottom-right.
// ═══════════════════════════════════════════════════════════════

/**
 * Modifier extension for the signature claymorphism look:
 * - Outer shadow (bottom-right, colored)
 * - Inner highlight (top-left, white glow)
 * - Rounded, puffy shape
 */
fun Modifier.clayEffect(
    cornerRadius: Dp = 28.dp,
    shadowColor: Color = TranzoColors.ClayShadowDark,
    elevation: Dp = 12.dp,
): Modifier = this
    .shadow(
        elevation = elevation,
        shape = RoundedCornerShape(cornerRadius),
        ambientColor = shadowColor,
        spotColor = shadowColor,
    )
    .clip(RoundedCornerShape(cornerRadius))

/**
 * Clay Button — Puffy pill with colored shadow matching the button color.
 * Feels like a squishy toy button. Press feedback with scale.
 */
@Composable
fun ClayButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    containerColor: Color = TranzoColors.ClayBlue,
    gradientStart: Color = containerColor,
    gradientEnd: Color = containerColor,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val shadowElevation by animateDpAsState(
        targetValue = if (isPressed) 4.dp else 14.dp,
        animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
        label = "button shadow"
    )

    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(58.dp)
            .shadow(
                elevation = shadowElevation,
                shape = RoundedCornerShape(22.dp),
                ambientColor = containerColor.copy(alpha = 0.4f),
                spotColor = containerColor.copy(alpha = 0.35f),
            ),
        interactionSource = interactionSource,
        enabled = enabled && !isLoading,
        shape = RoundedCornerShape(22.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = Color.White,
            disabledContainerColor = containerColor.copy(alpha = 0.35f),
            disabledContentColor = Color.White.copy(alpha = 0.5f),
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
        ),
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(22.dp),
                color = Color.White,
                strokeWidth = 2.5.dp,
            )
        } else {
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                letterSpacing = 0.3.sp,
            )
        }
    }
}

/**
 * Clay Card — The signature element.
 * Off-white surface with multi-layered shadow for 3D depth.
 * Top-left inner edge catches "light", bottom-right casts shadow.
 */
@Composable
fun ClayCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    cornerRadius: Dp = 24.dp,
    shadowElevation: Dp = 10.dp,
    backgroundGradient: List<Color> = listOf(TranzoColors.ClayCard, TranzoColors.ClayCard),
    content: @Composable () -> Unit,
) {
    val cardModifier = modifier
        .shadow(
            elevation = shadowElevation,
            shape = RoundedCornerShape(cornerRadius),
            ambientColor = TranzoColors.ClayShadowDark,
            spotColor = TranzoColors.ClayShadowDark,
        )
        .clip(RoundedCornerShape(cornerRadius))
        .background(
            brush = Brush.linearGradient(
                colors = listOf(
                    Color(0xFFFEFCFF),  // Slight warm highlight at top
                    TranzoColors.ClayCard,  // Base color
                ),
                start = Offset(0f, 0f),
                end = Offset(Float.MAX_VALUE, Float.MAX_VALUE),
            )
        )
        // Inner highlight line at the top for the "lit edge" effect
        .drawBehind {
            // Top inner glow
            drawRoundRect(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.6f),
                        Color.Transparent,
                    ),
                    startY = 0f,
                    endY = size.height * 0.08f,
                ),
                cornerRadius = CornerRadius(cornerRadius.toPx()),
                size = Size(size.width, size.height * 0.08f),
            )
        }

    val finalModifier = if (onClick != null) {
        cardModifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = onClick,
        )
    } else {
        cardModifier
    }

    Box(
        modifier = finalModifier,
        content = { content() },
    )
}

/**
 * Clay Gradient Card — For hero/accent elements.
 * Uses gradient background + matching colored shadow for a glowing clay effect.
 */
@Composable
fun ClayGradientCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    gradientStart: Color = TranzoColors.ClayBlue,
    gradientEnd: Color = TranzoColors.ClayPurple,
    content: @Composable () -> Unit,
) {
    val cardModifier = modifier
        .shadow(
            elevation = 18.dp,
            shape = RoundedCornerShape(24.dp),
            ambientColor = gradientStart.copy(alpha = 0.35f),
            spotColor = gradientEnd.copy(alpha = 0.25f),
        )
        .clip(RoundedCornerShape(24.dp))
        .background(
            brush = Brush.linearGradient(
                colors = listOf(gradientStart, gradientEnd),
                start = Offset(0f, 0f),
                end = Offset(Float.MAX_VALUE, Float.MAX_VALUE),
            ),
        )
        .drawBehind {
            // Glossy top highlight for 3D roundness
            drawRoundRect(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.25f),
                        Color.Transparent,
                    ),
                    startY = 0f,
                    endY = size.height * 0.35f,
                ),
                cornerRadius = CornerRadius(24.dp.toPx()),
                size = Size(size.width, size.height * 0.35f),
            )
        }

    val finalModifier = if (onClick != null) {
        cardModifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = onClick,
        )
    } else {
        cardModifier
    }

    Box(
        modifier = finalModifier,
        content = { content() },
    )
}

/**
 * Clay Text Field — Inset input with inner shadow (pressed-into-clay look).
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
        shape = RoundedCornerShape(18.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = TranzoColors.ClayBlue,
            unfocusedBorderColor = TranzoColors.ClayInputBorder,
            focusedContainerColor = TranzoColors.ClayInputBg,
            unfocusedContainerColor = TranzoColors.ClayInputBg,
            cursorColor = TranzoColors.ClayBlue,
        ),
    )
}

/**
 * Clay Stat Card — Gradient hero metric with glossy highlight.
 * Used for balances, stats, KPIs.
 */
@Composable
fun ClayStatCard(
    label: String,
    value: String,
    unit: String = "",
    modifier: Modifier = Modifier,
    gradientStart: Color = TranzoColors.ClayBlue,
    gradientEnd: Color = TranzoColors.ClayPurple,
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
                color = Color.White.copy(alpha = 0.85f),
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.8.sp,
            )

            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                Text(
                    text = value,
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 30.sp,
                )
                if (unit.isNotEmpty()) {
                    Text(
                        text = unit,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White.copy(alpha = 0.75f),
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(bottom = 4.dp),
                    )
                }
            }
        }
    }
}

/**
 * Clay Action Button — Puffy colored icon pill + label.
 * Each icon bg casts its own colored shadow.
 */
@Composable
fun ClayActionButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: @Composable (() -> Unit)? = null,
    backgroundColor: Color = TranzoColors.ClayBlue,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val elevation by animateDpAsState(
        targetValue = if (isPressed) 4.dp else 12.dp,
        animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
        label = "action elevation"
    )

    Column(
        modifier = modifier.clickable(
            interactionSource = interactionSource,
            indication = null,
            onClick = onClick,
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .shadow(
                    elevation = elevation,
                    shape = RoundedCornerShape(20.dp),
                    ambientColor = backgroundColor.copy(alpha = 0.4f),
                    spotColor = backgroundColor.copy(alpha = 0.3f),
                )
                .clip(RoundedCornerShape(20.dp))
                .background(backgroundColor)
                .drawBehind {
                    // Glossy top highlight
                    drawRoundRect(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.White.copy(alpha = 0.3f),
                                Color.Transparent,
                            ),
                            startY = 0f,
                            endY = size.height * 0.45f,
                        ),
                        cornerRadius = CornerRadius(20.dp.toPx()),
                        size = Size(size.width, size.height * 0.45f),
                    )
                },
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
 * Clay Auth Method Card — Welcome/Login screen auth option.
 * White card with colored icon pill and arrow.
 */
@Composable
fun ClayAuthMethodCard(
    icon: ImageVector,
    title: String,
    description: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    iconColor: Color = TranzoColors.ClayBlue,
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
            // Solid colored icon with clay shadow
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(16.dp),
                        ambientColor = iconColor.copy(alpha = 0.35f),
                        spotColor = iconColor.copy(alpha = 0.25f),
                    )
                    .clip(RoundedCornerShape(16.dp))
                    .background(iconColor)
                    .drawBehind {
                        drawRoundRect(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.White.copy(alpha = 0.25f),
                                    Color.Transparent,
                                ),
                                startY = 0f,
                                endY = size.height * 0.4f,
                            ),
                            cornerRadius = CornerRadius(16.dp.toPx()),
                            size = Size(size.width, size.height * 0.4f),
                        )
                    },
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

            // Arrow in a subtle pill
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(TranzoColors.ClayBlueSoft),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    "\u2192",
                    style = MaterialTheme.typography.titleMedium,
                    color = TranzoColors.ClayBlue,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}

/**
 * Clay Success Checkmark — Puffy green circle with check.
 */
@Composable
fun ClaySuccessCheckmark(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(72.dp)
            .shadow(
                elevation = 16.dp,
                shape = RoundedCornerShape(24.dp),
                ambientColor = TranzoColors.ClayGreen.copy(alpha = 0.4f),
            )
            .clip(RoundedCornerShape(24.dp))
            .background(TranzoColors.ClayGreen)
            .drawBehind {
                drawRoundRect(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.3f),
                            Color.Transparent,
                        ),
                        startY = 0f,
                        endY = size.height * 0.4f,
                    ),
                    cornerRadius = CornerRadius(24.dp.toPx()),
                    size = Size(size.width, size.height * 0.4f),
                )
            },
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = Icons.Outlined.Check,
            contentDescription = "Success",
            modifier = Modifier.size(36.dp),
            tint = Color.White,
        )
    }
}

/**
 * Clay Badge — Small tinted label pill with subtle shadow.
 */
@Composable
fun ClayBadge(
    text: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = TranzoColors.ClayBlue,
) {
    Box(
        modifier = modifier
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(12.dp),
                ambientColor = backgroundColor.copy(alpha = 0.15f),
            )
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor.copy(alpha = 0.1f))
            .padding(horizontal = 14.dp, vertical = 6.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            color = backgroundColor,
            fontWeight = FontWeight.Bold,
            fontSize = 11.sp,
        )
    }
}

/**
 * Clay Icon Pill — Small colored icon container used in list items.
 * Consistent 3D puffy look with colored shadow.
 */
@Composable
fun ClayIconPill(
    modifier: Modifier = Modifier,
    color: Color = TranzoColors.ClayBlue,
    size: Dp = 44.dp,
    cornerRadius: Dp = 15.dp,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(
        modifier = modifier
            .size(size)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(cornerRadius),
                ambientColor = color.copy(alpha = 0.35f),
                spotColor = color.copy(alpha = 0.25f),
            )
            .clip(RoundedCornerShape(cornerRadius))
            .background(color)
            .drawBehind {
                drawRoundRect(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.25f),
                            Color.Transparent,
                        ),
                        startY = 0f,
                        endY = this.size.height * 0.4f,
                    ),
                    cornerRadius = CornerRadius(cornerRadius.toPx()),
                    size = Size(this.size.width, this.size.height * 0.4f),
                )
            },
        contentAlignment = Alignment.Center,
        content = content,
    )
}

/**
 * Decorative floating blob shape drawn on backgrounds.
 * Creates organic, playful depth behind content.
 */
@Composable
fun ClayDecoBlob(
    modifier: Modifier = Modifier,
    color: Color = TranzoColors.ClayBlue.copy(alpha = 0.08f),
    offsetX: Float = 0f,
    offsetY: Float = 0f,
    radius: Float = 200f,
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        drawCircle(
            color = color,
            radius = radius,
            center = Offset(
                x = size.width * offsetX,
                y = size.height * offsetY,
            ),
        )
    }
}
