package com.tranzo.app.ui.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreenModern() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFB))
            .systemBarsPadding(),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF0D9488),
                            Color(0xFF06B6D4),
                        ),
                    ),
                )
                .padding(24.dp),
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        "Tranzo",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                    )
                }
                Text("Total Balance", fontSize = 14.sp, color = Color(0xFFCEFCE8))
                Text("\$2,450.00", fontSize = 36.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 16.dp),
        ) {
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Text("Assets", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF111827))
            }
        }
    }
}
