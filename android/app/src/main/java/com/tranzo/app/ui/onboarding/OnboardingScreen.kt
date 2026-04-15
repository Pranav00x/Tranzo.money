package com.tranzo.app.ui.onboarding

import androidx.compose.animation.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.tranzo.app.ui.components.TranzoButton
import com.tranzo.app.ui.components.TranzoSecondaryButton
import com.tranzo.app.ui.theme.TranzoColors

data class OnboardingPage(
    val icon: ImageVector,
    val headline: String,
    val greenWords: List<String>,
    val subtitle: String,
)

private val pages = listOf(
    OnboardingPage(
        icon = Icons.Outlined.CreditCard,
        headline = "Spend Crypto Anywhere",
        greenWords = listOf("Anywhere"),
        subtitle = "Get a Tranzo Visa card and spend your crypto at 80M+ merchants worldwide.",
    ),
    OnboardingPage(
        icon = Icons.Outlined.AccountBalanceWallet,
        headline = "Your Smart Wallet",
        greenWords = listOf("Smart Wallet"),
        subtitle = "One wallet for all your crypto. Send, receive, swap — gasless and instant.",
    ),
    OnboardingPage(
        icon = Icons.Outlined.WaterDrop,
        headline = "Get Paid in Real-Time",
        greenWords = listOf("Real-Time"),
        subtitle = "Stream salary every second with Dripper. Withdraw whenever you want.",
    ),
)

/**
 * 3-page onboarding — Card-first, clean CheQ-style.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(
    onGetStarted: () -> Unit,
) {
    val pagerState = rememberPagerState(pageCount = { pages.size })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(TranzoColors.White)
            .systemBarsPadding(),
    ) {
        // Pager — occupies the top ~70%
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
                // Large icon illustration
                Box(
                    modifier = Modifier
                        .size(140.dp)
                        .clip(CircleShape)
                        .background(TranzoColors.PaleTeal),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = page.icon,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = TranzoColors.PrimaryBlack,
                    )
                }

                Spacer(modifier = Modifier.height(48.dp))

                // Headline with green keywords
                val annotatedHeadline = buildAnnotatedString {
                    val words = page.headline.split(" ")
                    words.forEachIndexed { index, word ->
                        val isGreen = page.greenWords.any { it.contains(word) }
                        if (isGreen) {
                            withStyle(SpanStyle(color = TranzoColors.PrimaryBlack)) {
                                append(word)
                            }
                        } else {
                            withStyle(SpanStyle(color = TranzoColors.TextPrimary)) {
                                append(word)
                            }
                        }
                        if (index < words.lastIndex) append(" ")
                    }
                }

                Text(
                    text = annotatedHeadline,
                    style = MaterialTheme.typography.headlineLarge,
                    textAlign = TextAlign.Center,
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = page.subtitle,
                    style = MaterialTheme.typography.bodyLarge,
                    color = TranzoColors.TextSecondary,
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
                            if (isSelected) TranzoColors.PrimaryBlack
                            else TranzoColors.BorderGray
                        )
                        .animateContentSize(),
                )
            }
        }

        // Bottom buttons
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 24.dp),
        ) {
            if (pagerState.currentPage == pages.lastIndex) {
                TranzoButton(
                    text = "Get Started",
                    onClick = onGetStarted,
                )
            } else {
                TranzoSecondaryButton(
                    text = "Skip",
                    onClick = onGetStarted,
                )
            }
        }
    }
}
