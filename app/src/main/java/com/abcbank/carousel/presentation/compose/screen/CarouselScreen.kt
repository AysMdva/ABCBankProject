@file:OptIn(ExperimentalFoundationApi::class)
package com.abcbank.carousel.presentation.compose.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abcbank.carousel.R
import com.abcbank.carousel.domain.model.PageStatistics
import com.abcbank.carousel.presentation.MainViewModel
import com.abcbank.carousel.presentation.compose.component.ImageCarousel
import com.abcbank.carousel.presentation.compose.component.ItemList
import com.abcbank.carousel.presentation.compose.component.SearchBar
import com.abcbank.carousel.presentation.compose.component.StatisticsBottomSheet
import com.abcbank.carousel.presentation.compose.state.CarouselScreenState

@Composable
fun CarouselScreenRoute(
    viewModel: MainViewModel
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    CarouselScreen(
        state = state,
        statistics = viewModel.getStatistics(),
        onPageChange = viewModel::onPageChanged,
        onSearchQueryChange = viewModel::onSearchQueryChanged
    )
}

@Composable
fun CarouselScreen(
    state: CarouselScreenState,
    statistics: List<PageStatistics>,
    onPageChange: (Int) -> Unit,
    onSearchQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(pageCount = { state.pageCount })
    val listState = rememberLazyListState()
    var showBottomSheet by rememberSaveable { mutableStateOf(false) }

    DisposableEffect(Unit) {
        onDispose {}
    }

    LaunchedEffect(pagerState.currentPage) {
        if (state.pageCount > 0 && pagerState.currentPage != state.currentPage) {
            onPageChange(pagerState.currentPage)
            listState.scrollToItem(0)
        }
    }

    LaunchedEffect(state.currentPage, state.pageCount) {
        if (state.pageCount > 0 && pagerState.currentPage != state.currentPage) {
            pagerState.animateScrollToPage(state.currentPage)
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(onClick = { showBottomSheet = true }) {
                Icon(
                    painter = painterResource(id = android.R.drawable.ic_menu_info_details),
                    contentDescription = stringResource(R.string.statistics)
                )
            }
        }
    ) { paddingValues ->
        ItemList(
            items = state.filteredItems,
            listState = listState,
            topContent = {
                if (state.pageCount > 0) {
                    ImageCarousel(
                        pages = state.pages,
                        pagerState = pagerState
                    )
                }
            },
            stickySearchContent = {
                SearchBar(
                    query = state.searchQuery,
                    onQueryChange = onSearchQueryChange,
                    modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                )
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(PaddingValues(bottom = 76.dp))
        )
    }

    if (showBottomSheet) {
        StatisticsBottomSheet(
            statistics = statistics,
            onDismiss = { showBottomSheet = false }
        )
    }
}
