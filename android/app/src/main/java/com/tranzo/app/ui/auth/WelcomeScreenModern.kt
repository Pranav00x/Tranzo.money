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
    var isSignUp by remember { mutableStateOf(true) }

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
        ) {
            item {
                Spacer(modifier = Modifier.height(32.dp))

                // Logo
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .background(Color.Black, RoundedCornerShape(20.dp)),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        "T",
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White,
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    "Tranzo",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.Black,
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    "Self-custody smart wallet",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray,
                )

                Spacer(modifier = Modifier.height(40.dp))

                // Sign Up / Sign In Tabs
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Button(
                        onClick = { isSignUp = true },
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isSignUp) Color.Black else Color.White,
                        ),
                        shape = RoundedCornerShape(12.dp),
                        border = if (!isSignUp) androidx.compose.foundation.BorderStroke(1.dp, Color.Black) else null,
                    ) {
                        Text(
                            "Sign Up",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isSignUp) Color.White else Color.Black,
                        )
                    }

                    Button(
                        onClick = { isSignUp = false },
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (!isSignUp) Color.Black else Color.White,
                        ),
                        shape = RoundedCornerShape(12.dp),
                        border = if (isSignUp) androidx.compose.foundation.BorderStroke(1.dp, Color.Black) else null,
                    ) {
                        Text(
                            "Sign In",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (!isSignUp) Color.White else Color.Black,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    if (isSignUp) "Create a new smart wallet" else "Access your wallet",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray,
                )

                Spacer(modifier = Modifier.height(28.dp))

                // Social Auth Options
                SocialAuthButton(
                    label = "Continue with Google",
                    icon = Icons.Outlined.Circle,
                    onClick = { onAuthenticationSuccess(true) }
                )
                Spacer(modifier = Modifier.height(12.dp))

                SocialAuthButton(
                    label = "Continue with X",
                    icon = Icons.Outlined.Circle,
                    onClick = { onAuthenticationSuccess(true) }
                )
                Spacer(modifier = Modifier.height(12.dp))

                SocialAuthButton(
                    label = "Continue with Email",
                    icon = Icons.Outlined.Email,
                    onClick = { onNavigateToOtp("") }
                )
                Spacer(modifier = Modifier.height(12.dp))

                SocialAuthButton(
                    label = "Continue with Passkey",
                    icon = Icons.Outlined.Circle,
                    onClick = { onAuthenticationSuccess(true) }
                )

                Spacer(modifier = Modifier.height(40.dp))

                // Footer
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        "256-bit encrypted · Self-custody",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Gray,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            "By continuing, you agree to our ",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.Gray,
                        )
                        Text(
                            "Terms & Privacy Policy",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            modifier = Modifier.clickable { /* Terms dialog */ }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SocialAuthButton(
    label: String,
    icon: ImageVector,
    onClick: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF5F5F5), RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(16.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f),
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color.Black, RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        icon,
                        contentDescription = label,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp),
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    label,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black,
                )
            }
            Icon(
                Icons.Outlined.ChevronRight,
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.size(20.dp),
            )
        }
    }
}
