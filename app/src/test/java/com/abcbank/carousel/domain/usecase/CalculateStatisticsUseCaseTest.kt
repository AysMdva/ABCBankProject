package com.abcbank.carousel.domain.usecase

import com.abcbank.carousel.domain.model.ListItem
import com.abcbank.carousel.domain.model.PageData
import org.junit.Assert.assertEquals
import org.junit.Test

class CalculateStatisticsUseCaseTest {

    private val useCase = CalculateStatisticsUseCase()

    @Test
    fun `returns one PageStatistics per page with correct item count`() {
        val page1 = PageData(0, 1, listOf(
            ListItem("1", "A", "aa", 1),
            ListItem("2", "B", "bb", 1)
        ))
        val page2 = PageData(1, 1, listOf(
            ListItem("3", "C", "cc", 1)
        ))
        val pages = listOf(page1, page2)

        val result = useCase(pages)

        assertEquals(2, result.size)
        assertEquals(1, result[0].pageNumber)
        assertEquals(2, result[0].itemCount)
        assertEquals(2, result[1].pageNumber)
        assertEquals(1, result[1].itemCount)
    }

    @Test
    fun `top characters are letters only sorted by count descending then by char`() {
        val page = PageData(0, 1, listOf(
            ListItem("1", "aab", "ccc", 1)
        ))
        val result = useCase(listOf(page))

        assertEquals(1, result.size)
        val top = result[0].topCharacters
        assertEquals(3, top.size)
        assertEquals('c', top[0].character)
        assertEquals(3, top[0].count)
        assertEquals('a', top[1].character)
        assertEquals(2, top[1].count)
    }

    @Test
    fun `empty pages yield empty top characters`() {
        val page = PageData(0, 1, emptyList())
        val result = useCase(listOf(page))

        assertEquals(1, result.size)
        assertEquals(0, result[0].itemCount)
        assertEquals(0, result[0].topCharacters.size)
    }
}
