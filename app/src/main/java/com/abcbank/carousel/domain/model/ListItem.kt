package com.abcbank.carousel.domain.model

// either Serialized name should be used for fields on prod-level, or @Keep of Proguard

data class ListItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val thumbnailRes: Int
)
