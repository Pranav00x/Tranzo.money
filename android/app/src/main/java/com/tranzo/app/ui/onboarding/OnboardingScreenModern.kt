package com.tranzo.app.ui.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OnboardingScreenModern(onGetStarted: () -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            item {
                // Logo
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color.Black, RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        "T",
                        fontSize = 60.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White,
                    )
                }

                Spacer(modifier = Modifier.height(48.dp))

                // Title
                Text(
                    "Welcome to Tranzo",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.Black,
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Subtitle
                Text(
                    "Your crypto. Your control. No middleman. Just pure financial freedom.",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black,
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(modifier = Modifier.height(56.dp))

                // Features
                OnboardingFeature(
                    icon = Icons.Outlined.Lock,
                    title = "Non-Custodial",
                    description = "You hold your keys. Always.",
                )
                Spacer(modifier = Modifier.height(24.dp))

                OnboardingFeature(
                    icon = Icons.Outlined.CreditCard,
                    title = "Crypto Card",
                    description = "Spend crypto anywhere cards are accepted.",
                )
                Spacer(modifier = Modifier.height(24.dp))

                OnboardingFeature(
                    icon = Icons.Outlined.WaterDrop,
                    title = "Salary Streaming",
                    description = "Automate your crypto payments.",
                )

                Spacer(modifier = Modifier.height(64.dp))

                // Get Started Button
                Button(
                    onClick = onGetStarted,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                    shape = RoundedCornerShape(12.dp),
                ) {
                    Text(
                        "Get Started",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White,
                    )
                }
            }
        }
    }
}

@Composable
private fun OnboardingFeature(
    icon: ImageVector,
    title: String,
    description: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .background(Color.Black, RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                icon,
                contentDescription = title,
                tint = Color.White,
                modifier = Modifier.size(24.dp),
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Black,
                color = Color.Black,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                description,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Black,
            )
        }
    }
}
