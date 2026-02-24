package com.abcbank.carousel.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abcbank.carousel.domain.model.PageData
import com.abcbank.carousel.domain.usecase.CalculateStatisticsUseCase
import com.abcbank.carousel.domain.usecase.FilterItemsUseCase
import com.abcbank.carousel.presentation.compose.state.CarouselScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Single source of truth for carousel + list + search. Consumed by both XML and Compose UIs.
 * State is immutable; all updates go through [state] Flow.
 */
class MainViewModel(
    private val calculateStatisticsUseCase: CalculateStatisticsUseCase = CalculateStatisticsUseCase(),
    private val filterItemsUseCase: FilterItemsUseCase = FilterItemsUseCase()
) : ViewModel() {

    private val allPages = generateSampleData()
    private val statistics = calculateStatisticsUseCase(allPages)

    private val _state = MutableStateFlow(CarouselScreenState.Empty.copy(isLoading = true))
    val state: StateFlow<CarouselScreenState> = _state.asStateFlow()

    init {
        loadInitialData()
    }

    fun onPageChanged(page: Int) {
        if (page !in allPages.indices) return

        viewModelScope.launch {
            _state.update { currentState ->
                val pageItems = allPages[page].items
                currentState.copy(
                    currentPage = page,
                    currentItems = pageItems,
                    filteredItems = filterItemsUseCase(pageItems, currentState.searchQuery)
                )
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        viewModelScope.launch {
            _state.update { currentState ->
                currentState.copy(
                    searchQuery = query,
                    filteredItems = filterItemsUseCase(currentState.currentItems, query)
                )
            }
        }
    }

    fun getStatistics() = statistics

    private fun loadInitialData() {
        val firstPageItems = allPages.firstOrNull().orEmptyItems()

        _state.update {
            it.copy(
                pages = allPages,
                currentPage = 0,
                currentItems = firstPageItems,
                filteredItems = firstPageItems,
                isLoading = false
            )
        }
    }

    private fun generateSampleData(): List<PageData> {
        val fruitsByPage = listOf(
            listOf("apple", "banana", "orange", "blueberry"),
            listOf("cherry", "grape", "melon", "strawberry"),
            listOf("kiwi", "lemon", "mango", "pineapple"),
            listOf("peach", "plum", "pear", "watermelon"),
            listOf("apricot", "coconut", "papaya", "guava")
        )
        val itemCounts = listOf(25, 30, 20, 15, 28)
        val imageRes = listOf(
            android.R.drawable.ic_menu_gallery,
            android.R.drawable.ic_menu_camera,
            android.R.drawable.ic_menu_compass,
            android.R.drawable.ic_menu_edit,
            android.R.drawable.ic_menu_mapmode
        )

        return fruitsByPage.mapIndexed { pageIndex, fruits ->
            PageData(
                id = pageIndex,
                imageRes = imageRes[pageIndex],
                items = List(itemCounts[pageIndex]) { itemIndex ->
                    com.abcbank.carousel.domain.model.ListItem(
                        id = "${pageIndex}_$itemIndex",
                        title = "Page ${pageIndex + 1} - Item ${itemIndex + 1}",
                        subtitle = fruits.joinToString(" "),
                        thumbnailRes = imageRes[pageIndex]
                    )
                }
            )
        }
    }

    private fun PageData?.orEmptyItems() = this?.items.orEmpty()
}
