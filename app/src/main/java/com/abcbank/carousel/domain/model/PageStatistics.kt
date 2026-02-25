package com.abcbank.carousel.domain.model

// either Serialized name should be used for fields on prod-level, or @Keep of Proguard

data class CharacterCount(
    val character: Char,
    val count: Int
)

data class PageStatistics(
    val pageNumber: Int,
    val itemCount: Int,
    val topCharacters: List<CharacterCount>
)
