package com.tranzo.app.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
fun WelcomeScreenModern(
    onNavigateToOtp: (String) -> Unit = {},
    onAuthenticationSuccess: (Boolean) -> Unit = {},
) {
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
                        .size(80.dp)
                        .background(Color.Black, RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        "T",
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White,
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    "Welcome to Tranzo",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.Black,
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    "Your crypto. Your control.",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black,
                )

                Spacer(modifier = Modifier.height(48.dp))

                // Email Input
                AuthInputField(
                    label = "Email",
                    icon = Icons.Outlined.Email,
                    onValueChange = { /* TODO */ }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Continue Button
                Button(
                    onClick = { onNavigateToOtp("") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                    shape = RoundedCornerShape(8.dp),
                ) {
                    Text(
                        "Continue",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White,
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Divider
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(1.dp)
                            .background(Color.Black),
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        "or",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(1.dp)
                            .background(Color.Black),
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Social Auth Options
                AuthMethodButton("Google", Icons.Outlined.Circle)
                Spacer(modifier = Modifier.height(10.dp))
                AuthMethodButton("Apple", Icons.Outlined.Circle)
                Spacer(modifier = Modifier.height(10.dp))
                AuthMethodButton("Passkey", Icons.Outlined.Circle)

                Spacer(modifier = Modifier.height(32.dp))

                // Terms
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Text(
                        "By continuing, you agree to our ",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black,
                    )
                    Text(
                        "Terms",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.clickable { /* TODO */ }
                    )
                }
            }
        }
    }
}

@Composable
private fun AuthInputField(
    label: String,
    icon: ImageVector,
    onValueChange: (String) -> Unit = {},
) {
    Column {
        Text(
            label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
        )
        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(8.dp))
                .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
                .padding(12.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    icon,
                    contentDescription = label,
                    tint = Color.Black,
                    modifier = Modifier.size(20.dp),
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    "Enter your $label",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black,
                )
            }
        }
    }
}

@Composable
private fun AuthMethodButton(
    label: String,
    icon: ImageVector,
) {
    Button(
        onClick = { /* TODO */ },
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        shape = RoundedCornerShape(8.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color.Black),
    ) {
        Icon(
            icon,
            contentDescription = label,
            tint = Color.Black,
            modifier = Modifier.size(16.dp),
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            "Continue with $label",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
        )
    }
}
