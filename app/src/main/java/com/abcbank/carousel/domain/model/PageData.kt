package com.abcbank.carousel.domain.model

// either Serialized name should be used for fields on prod-level, or @Keep of Proguard

data class PageData(
    val id: Int,
    val imageRes: Int,
    val items: List<ListItem>
)
