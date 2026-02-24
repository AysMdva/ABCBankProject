package com.abcbank.carousel.domain.usecase

import com.abcbank.carousel.domain.model.ListItem
import org.junit.Assert.assertEquals
import org.junit.Test

class FilterItemsUseCaseTest {

    private val useCase = FilterItemsUseCase()

    @Test
    fun `returns all items when query is blank`() {
        val items = listOf(
            ListItem("1", "Savings", "apple banana", 1),
            ListItem("2", "Insurance", "orange grape", 1)
        )

        val result = useCase(items, " ")

        assertEquals(items, result)
    }

    @Test
    fun `filters by title or subtitle ignoring case`() {
        val items = listOf(
            ListItem("1", "Savings", "apple banana", 1),
            ListItem("2", "Insurance", "orange grape", 1)
        )

        val result = useCase(items, "APpLe")

        assertEquals(listOf(items[0]), result)
    }
}
