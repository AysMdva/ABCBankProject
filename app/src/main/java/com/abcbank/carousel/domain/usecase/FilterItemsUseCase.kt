package com.abcbank.carousel.domain.usecase

import com.abcbank.carousel.domain.model.ListItem

/** Case-insensitive filter by title or subtitle; empty query returns all items. */
class FilterItemsUseCase {

    operator fun invoke(items: List<ListItem>, query: String): List<ListItem> {
        val normalizedQuery = query.trim()
        if (normalizedQuery.isEmpty()) return items

        return items.filter { item ->
            item.title.contains(normalizedQuery, ignoreCase = true) ||
                item.subtitle.contains(normalizedQuery, ignoreCase = true)
        }
    }
}
