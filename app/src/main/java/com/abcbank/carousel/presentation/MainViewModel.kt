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

class MainViewModel(
    private val calculateStatisticsUseCase: CalculateStatisticsUseCase = CalculateStatisticsUseCase(),
    private val filterItemsUseCase: FilterItemsUseCase = FilterItemsUseCase()
) : ViewModel() {

    private val pages = generateSampleData()
    private val stats = calculateStatisticsUseCase(pages)

    private val _state = MutableStateFlow(CarouselScreenState.Empty.copy(isLoading = true))
    val state: StateFlow<CarouselScreenState> = _state.asStateFlow()

    init {
        loadData()
    }

    fun onPageChanged(page: Int) {
        if (page !in pages.indices) return
        viewModelScope.launch {
            _state.update {
                val items = pages[page].items
                it.copy(
                    currentPage = page,
                    currentItems = items,
                    filteredItems = filterItemsUseCase(items, it.searchQuery)
                )
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    searchQuery = query,
                    filteredItems = filterItemsUseCase(it.currentItems, query)
                )
            }
        }
    }

    fun getStatistics() = stats

    private fun loadData() {
        val firstPage = pages.firstOrNull()?.items.orEmpty()
        _state.update {
            it.copy(
                pages = pages,
                currentPage = 0,
                currentItems = firstPage,
                filteredItems = firstPage,
                isLoading = false
            )
        }
    }

    // just sample data
    private fun generateSampleData(): List<PageData> {
        val fruits = listOf(
            listOf("apple", "banana", "orange", "blueberry"),
            listOf("cherry", "grape", "melon", "strawberry"),
            listOf("kiwi", "lemon", "mango", "pineapple"),
            listOf("peach", "plum", "pear", "watermelon"),
            listOf("apricot", "coconut", "papaya", "guava")
        )
        val counts = listOf(25, 30, 20, 15, 28)
        val icons = listOf(
            android.R.drawable.ic_menu_gallery,
            android.R.drawable.ic_menu_camera,
            android.R.drawable.ic_menu_compass,
            android.R.drawable.ic_menu_edit,
            android.R.drawable.ic_menu_mapmode
        )

        return fruits.mapIndexed { i, tags ->
            PageData(
                id = i,
                imageRes = icons[i],
                items = List(counts[i]) { j ->
                    com.abcbank.carousel.domain.model.ListItem(
                        id = "${i}_$j",
                        title = "Page ${i + 1} - Item ${j + 1}",
                        subtitle = tags.joinToString(" "),
                        thumbnailRes = icons[i]
                    )
                }
            )
        }
    }
}

