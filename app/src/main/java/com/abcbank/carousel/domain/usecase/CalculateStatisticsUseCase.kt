package com.abcbank.carousel.domain.usecase

import com.abcbank.carousel.domain.model.CharacterCount
import com.abcbank.carousel.domain.model.PageData
import com.abcbank.carousel.domain.model.PageStatistics

class CalculateStatisticsUseCase {

    operator fun invoke(pages: List<PageData>): List<PageStatistics> {
        return pages.mapIndexed { index, page ->
            val allText = page.items.joinToString(separator = "") { "${it.title}${it.subtitle}" }

            PageStatistics(
                pageNumber = index + 1,
                itemCount = page.items.size,
                topCharacters = calculateTopCharacters(allText)
            )
        }
    }

    private fun calculateTopCharacters(text: String): List<CharacterCount> {
        return text
            .filter(Char::isLetter)
            .lowercase()
            .groupingBy { it }
            .eachCount()
            .entries
            .sortedWith(compareByDescending<Map.Entry<Char, Int>> { it.value }.thenBy { it.key })
            .take(3)
            .map { (character, count) ->
                CharacterCount(character = character, count = count)
            }
    }
}
