package com.abcbank.carousel.presentation.compose.state

import androidx.compose.runtime.Stable
import com.abcbank.carousel.domain.model.PageStatistics

// UI state for statistics screen

@Stable // rather than Immutable, we cant ensure that this data wont change, probably will change
data class StatisticsState(
    val pages: List<PageStatistics>,
    val isVisible: Boolean
) {
    companion object {
        val EMPTY = StatisticsState(
            pages = emptyList(),
            isVisible = false
        )
    }
}
