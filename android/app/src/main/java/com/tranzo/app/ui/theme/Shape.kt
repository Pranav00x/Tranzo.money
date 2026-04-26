package com.tranzo.app.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

// Claymorphism shapes - soft, generous rounding for premium feel
val TranzoShapes = Shapes(
    // Small elements - buttons, chips, badges
    small = RoundedCornerShape(20.dp),
    // Cards, input fields, standard containers
    medium = RoundedCornerShape(28.dp),
    // Bottom sheet, modals, large containers
    large = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
    // Pill buttons, circular interactions
    extraLarge = RoundedCornerShape(32.dp),
)

// Additional claymorphism shapes for layered depth
object ClayShapes {
    val Pill = RoundedCornerShape(50.dp)
    val Card = RoundedCornerShape(28.dp)
    val Button = RoundedCornerShape(24.dp)
    val Input = RoundedCornerShape(24.dp)
}
