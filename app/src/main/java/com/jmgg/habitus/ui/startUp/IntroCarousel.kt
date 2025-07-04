package com.jmgg.habitus.ui.startUp

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import kotlinx.coroutines.launch
import com.jmgg.habitus.HabitusApp
import com.jmgg.habitus.auth.AuthPagerPage
import com.jmgg.habitus.ui.startUp.FeaturesScreen.Components.CustomHorizontalPagerIndicator
import com.jmgg.habitus.ui.startUp.FeaturesScreens.*



@Composable
fun IntroCarousel(
    onCarouselEnd: () -> Unit
) {
    val viewModel = HabitusApp.authViewModel
    val user by viewModel.currentUser.collectAsState()
    val isPremium = user?.isPremium == true

    val basePages = 5
    val extraPages = if (isPremium) 1 else 2
    val totalPages = basePages + extraPages
    val authPageIndex = basePages
    val offerPageIndex = basePages + 1
    val lastPageIndex = totalPages - 1

    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { totalPages }
    )
    val coroutineScope = rememberCoroutineScope()


    var shouldNavigateToOffer by remember { mutableStateOf(false) }

    val pages = remember(isPremium) {
        buildList<@Composable () -> Unit> {
            add { WelcomeScreen() }
            add { FirstFeatureScreen() }
            add { SecondFeatureScreen() }
            add { ThirdFeatureScreen() }
            add { FourthFeatureScreen() }
            add {
                AuthPagerPage(
                    onLoggedIn = {
                        shouldNavigateToOffer = true
                    }
                )
            }
            if (!isPremium) {

                add { PremiumVersionOfferScreen(onContinue = onCarouselEnd) }
            }
        }
    }

    val atAuthPage = pagerState.currentPage == authPageIndex
    val atOfferPage = pagerState.currentPage == lastPageIndex && !isPremium
    val lockSwipeAuth = atAuthPage && user == null
    val lockSwipeOffer = false
    val userScrollEnabled = !(lockSwipeAuth || lockSwipeOffer)


    LaunchedEffect(shouldNavigateToOffer) {
        if (shouldNavigateToOffer && !isPremium) {
            coroutineScope.launch {
                pagerState.animateScrollToPage(offerPageIndex)
            }
        }
    }

    Box(Modifier.fillMaxSize()) {
        HorizontalPager(
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
                pageCount = totalPages,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                activeColor = Color(0xFF6366F1),
                inactiveColor = Color(0xFF94A3B8)
            )

            Spacer(Modifier.height(8.dp))

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(
                    enabled = pagerState.currentPage > 0 && !atOfferPage,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage - 1)
                        }
                    }
                ) {
                    Text("Back", color = Color.White)
                }

                val isLast = pagerState.currentPage == lastPageIndex
                val canShowContinue = !(atAuthPage && user == null)

                if (canShowContinue) {
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                if (!isLast) {
                                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                } else {
                                    onCarouselEnd()
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6366F1))
                    ) {
                        Text(
                            if (!isLast) "Continue" else "Start",
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

