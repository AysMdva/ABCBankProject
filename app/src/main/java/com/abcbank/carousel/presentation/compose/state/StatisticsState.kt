package com.abcbank.carousel.presentation.compose.state

import androidx.compose.runtime.Stable
import com.abcbank.carousel.domain.model.PageStatistics

@Stable
data class StatisticsState(
    val pages: List<PageStatistics>,
    val isVisible: Boolean
) {
    companion object {
        val Hidden = StatisticsState(
            pages = emptyList(),
            isVisible = false
        )
    }
}
