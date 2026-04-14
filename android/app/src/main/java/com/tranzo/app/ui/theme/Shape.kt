package com.tranzo.app.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val TranzoShapes = Shapes(
    // Buttons — pill shape (full round)
    small = RoundedCornerShape(12.dp),
    // Cards, inputs
    medium = RoundedCornerShape(16.dp),
    // Bottom sheet top corners, gradient overlay
    large = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
    // Full pill buttons
    extraLarge = RoundedCornerShape(28.dp),
)
