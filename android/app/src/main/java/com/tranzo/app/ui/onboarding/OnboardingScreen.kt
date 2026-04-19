
package com.tranzo.app.ui.onboarding

import androidx.compose.animation.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

data class OnboardingPage(
    val icon: ImageVector,
    val headline: String,
    val subtitle: String,
)

private val pages = listOf(
    OnboardingPage(
        icon = Icons.Outlined.CreditCard,
        headline = "Spend Crypto\nAnywhere",
        subtitle = "Get a Tranzo Visa card and spend your crypto at 80M+ merchants worldwide.",
    ),
    OnboardingPage(
        icon = Icons.Outlined.AccountBalanceWallet,
        headline = "Your Smart\nWallet",
        subtitle = "One wallet for all your crypto. Send, receive, swap — gasless and instant.",
    ),
    OnboardingPage(
        icon = Icons.Outlined.WaterDrop,
        headline = "Get Paid in\nReal-Time",
        subtitle = "Stream salary every second with Dripper. Withdraw whenever you want.",
    ),
)

/**
 * CheQ-inspired 3-page onboarding — monochrome, minimal.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(
    onGetStarted: () -> Unit,
) {
    val pagerState = rememberPagerState(pageCount = { pages.size })

    // Auto-scroll every 3 seconds
    LaunchedEffect(pagerState) {
        while (pagerState.currentPage < pages.lastIndex) {
            kotlinx.coroutines.delay(3000)
            val next = pagerState.currentPage + 1
            if (next <= pages.lastIndex) {
                pagerState.animateScrollToPage(next)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .systemBarsPadding(),
    ) {
        // Pager
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
        ) { pageIndex ->
            val page = pages[pageIndex]

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 32.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                // Icon circle
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFF5F5F5)),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = page.icon,
                        contentDescription = null,
                        modifier = Modifier.size(52.dp),
                        tint = Color(0xFF1A1A1A),
                    )
                }

                Spacer(modifier = Modifier.height(48.dp))

                Text(
                    text = page.headline,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A1A1A),
                    textAlign = TextAlign.Center,
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = page.subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF999999),
                    textAlign = TextAlign.Center,
                )
            }
        }

        // Pager dots
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            repeat(pages.size) { index ->
                val isSelected = pagerState.currentPage == index
                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .size(
                            width = if (isSelected) 24.dp else 8.dp,
                            height = 8.dp,
                        )
                        .clip(CircleShape)
                        .background(
                            if (isSelected) Color(0xFF1A1A1A)
                            else Color(0xFFE0E0E0)
                        )
                        .animateContentSize(),
                )
            }
        }

        // Bottom button
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .padding(bottom = 24.dp),
        ) {
            Button(
                onClick = onGetStarted,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (pagerState.currentPage == pages.lastIndex)
                        Color(0xFF1A1A1A) else Color.Transparent,
                    contentColor = if (pagerState.currentPage == pages.lastIndex)
                        Color.White else Color(0xFF999999)
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
            ) {
                Text(
                    if (pagerState.currentPage == pages.lastIndex) "Get Started" else "Skip",
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}
