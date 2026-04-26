package com.tranzo.app.ui.card

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CardScreenModern() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFB))
            .systemBarsPadding(),
    ) {
        Text("Your Card", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF111827), modifier = Modifier.padding(24.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .background(
                    Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF0D9488),
                            Color(0xFF06B6D4),
                        ),
                    ),
                    RoundedCornerShape(20.dp),
                )
                .padding(24.dp),
        ) {
            Column(modifier = Modifier.align(Alignment.BottomStart)) {
                Text("Tranzo Visa Card", fontSize = 14.sp, color = Color(0xFFCEFCE8))
                Spacer(modifier = Modifier.height(12.dp))
                Text("Tap to manage", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
            }
        }
    }
}
