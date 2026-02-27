package com.abcbank.carousel.presentation.compose.state

import androidx.compose.runtime.Stable
import com.abcbank.carousel.domain.model.ListItem
import com.abcbank.carousel.domain.model.PageData

// UI state for carousel screen; hoisted so Composables stay pure (state down, events up)
@Stable
data class CarouselScreenState(
    val pages: List<PageData>,
    val currentPage: Int,
    val currentItems: List<ListItem>,
    val filteredItems: List<ListItem>,
    val searchQuery: String,
    val isLoading: Boolean
) {
    companion object {
        val Empty = CarouselScreenState(
            pages = emptyList(),
            currentPage = 0,
            currentItems = emptyList(),
            filteredItems = emptyList(),
            searchQuery = "",
            isLoading = false
        )
    }

    val hasSearchQuery: Boolean
        get() = searchQuery.isNotBlank()

    val pageCount: Int
        get() = pages.size
}
