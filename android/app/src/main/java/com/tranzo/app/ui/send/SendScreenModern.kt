package com.tranzo.app.ui.send

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SendScreenModern() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFB))
            .systemBarsPadding(),
    ) {
        Text("Send Crypto", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF111827), modifier = Modifier.padding(24.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 16.dp),
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White, RoundedCornerShape(12.dp))
                        .border(1.dp, Color(0xFFE5E7EB), RoundedCornerShape(12.dp))
                        .padding(16.dp),
                ) {
                    Text("250.00", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color(0xFF111827))
                }
            }
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0D9488)),
                    shape = RoundedCornerShape(12.dp),
                ) {
                    Text("Review & Send", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                }
            }
        }
    }
}
