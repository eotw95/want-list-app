package com.eotw95.wantnote.common.composable

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerScope
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.pagerTabIndicatorOffset

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BasicHorizontalPager(
    pagerState: PagerState,
    pageContent: @Composable PagerScope.(page: Int) -> Unit
) {
    HorizontalPager(
        state = pagerState,
        pageContent = pageContent,
        flingBehavior = PagerDefaults.flingBehavior(
            state = pagerState,
            snapPositionalThreshold = 0.2f
        )
    )
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalPagerApi::class)
@Composable
fun BasicHorizontalIndicator(state: PagerState, indicators: List<String>) {
    ScrollableTabRow(
        selectedTabIndex = state.currentPage,
        indicator = { tabPositions ->  
            TabRowDefaults.Indicator(Modifier.pagerTabIndicatorOffset(state, tabPositions))
        },
        edgePadding = 0.dp
    ) {
        indicators.forEachIndexed { index, title ->
            Tab(
                text = { Text(title) },
                selected = state.currentPage == index,
                onClick = { /* TODO */ },
            )
        }
    }
}