package com.tranzo.app.ui.dripper

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun DripperScreenModern(
    onCreateStream: () -> Unit = {},
    onStreamClick: (String) -> Unit = {},
    viewModel: DripperViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                "Dripper",
                fontSize = 28.sp,
                fontWeight = FontWeight.Black,
                color = Color.Black,
            )
            IconButton(onClick = onCreateStream) {
                Icon(
                    Icons.Outlined.Add,
                    contentDescription = "Create",
                    tint = Color.Black,
                    modifier = Modifier.size(28.dp),
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 16.dp),
        ) {
            // Info Box
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Black, RoundedCornerShape(8.dp))
                        .padding(16.dp),
                ) {
                    Column {
                        Text(
                            "Salary Streaming",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.White,
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            "Automate your crypto payments",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.White,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }

            // Active Streams
            item {
                Text(
                    "ACTIVE STREAMS",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.Black,
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            items(2) {
                StreamCard(
                    label = "Monthly Salary",
                    amount = "5,000 USDC",
                    progress = 0.65f,
                    onClick = { onStreamClick("stream_1") }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            // No Streams Placeholder
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White, RoundedCornerShape(8.dp))
                        .border(1.5.dp, Color.Black, RoundedCornerShape(8.dp))
                        .padding(24.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Icon(
                            Icons.Outlined.Water,
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(32.dp),
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            "No inactive streams",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.Black,
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "Create a new stream to get started",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.Black,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
private fun StreamCard(
    label: String,
    amount: String,
    progress: Float,
    onClick: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(8.dp))
            .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .padding(16.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        label,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.Black,
                    )
                    Text(
                        amount,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black,
                    )
                }
                Text(
                    "65%",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Progress Bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .background(Color(0xFFE5E5E5), RoundedCornerShape(2.dp)),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(progress)
                        .background(Color.Black, RoundedCornerShape(2.dp)),
                )
            }
        }
    }
}
