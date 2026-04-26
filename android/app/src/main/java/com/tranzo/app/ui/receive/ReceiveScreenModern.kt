package com.tranzo.app.ui.receive

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.compose.ui.graphics.Color
@Composable
fun ReceiveScreenModern() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFB))
            .systemBarsPadding(),
    ) {
        Text("Receive Crypto", fontSize = 24.sp, modifier = Modifier.padding(24.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Box(
                    modifier = Modifier
                        .size(240.dp)
                        .background(Color.White, RoundedCornerShape(16.dp))
                        .border(1.dp, Color(0xFFE5E7EB), RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.Center,
                ) {
                    Text("QR Code", fontSize = 16.sp)
                }
            }
        }
    }
}
