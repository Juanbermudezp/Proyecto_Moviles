package com.jmgg.habitus.ui.startUp

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.background
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import kotlinx.coroutines.launch
import com.jmgg.habitus.HabitusApp
import com.jmgg.habitus.auth.AuthPagerPage
import com.jmgg.habitus.ui.startUp.FeaturesScreens.*


/*@OptIn(ExperimentalFoundationApi::class)
@Composable
fun IntroCarousel(
    onCarouselEnd: () -> Unit
) {
    val viewModel = HabitusApp.authViewModel
    val user by viewModel.currentUser.collectAsState()
    val isPremium = user?.isPremium == true
    val coroutineScope = rememberCoroutineScope()
    val authPageIndex = 5
    val pagerState = rememberPagerState(initialPage = 0){
        5
    }

    val pages = buildList<@Composable () -> Unit> {
        add { WelcomeScreen() }
        add { FirstFeatureScreen() }
        add { SecondFeatureScreen() }
        add { ThirdFeatureScreen() }
        add { FourthFeatureScreen() }
        add {
            AuthPagerPage(onLoggedIn = {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(authPageIndex + 1)
                }
            })
        }
        if (!isPremium) {
            add { PremiumVersionOfferScreen(onContinue = onCarouselEnd) }
        }
    }

    val lastPageIndex = pages.lastIndex
    val lockSwipeAuth = pagerState.currentPage == authPageIndex && user == null
    val lockSwipeOffer = pagerState.currentPage == lastPageIndex
    val userScrollEnabled = !(lockSwipeAuth || lockSwipeOffer)

    Box(Modifier.fillMaxSize()) {
        HorizontalPager(
            pageCount = pages.size,
            state = pagerState,
            userScrollEnabled = userScrollEnabled,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            pages[page]()
        }

        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Bottom
        ) {

            CustomHorizontalPagerIndicator(
                pagerState = pagerState,
                pageCount = pages.size,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                activeColor = Color(0xFF6366F1),
                inactiveColor = Color(0xFF94A3B8)
            )

            Spacer(Modifier.height(8.dp))

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                if (pagerState.currentPage != lastPageIndex) {
                    TextButton(
                        enabled = pagerState.currentPage > 0,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage - 1)
                            }
                        }
                    ) {
                        Text(
                            "Back",
                            color = Color.White,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                } else {
                    Spacer(modifier = Modifier.width(64.dp))
                }

                if (!lockSwipeAuth) {
                    val isLast = pagerState.currentPage == lastPageIndex
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                if (!isLast) {
                                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                } else {
                                    onCarouselEnd()
                                }
                            }
                        }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6366F1))
                    ) {
                        Text(
                            if (!isLast) "Continue" else "Start",
                            color = Color.White,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CustomHorizontalPagerIndicator(
    pagerState: PagerState,
    pageCount: Int,
    modifier: Modifier = Modifier,
    activeColor: Color = Color(0xFF6366F1),
    inactiveColor: Color = Color(0xFF94A3B8),
    indicatorSize: Dp = 8.dp,
    spacing: Dp = 8.dp
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(spacing),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(pageCount) { iteration ->
            val color = if (pagerState.currentPage == iteration) activeColor else inactiveColor
            Box(
                modifier = Modifier
                    .size(indicatorSize)
                    .clip(MaterialTheme.shapes.small)
                    .background(color)
            )
        }
    }
}*/