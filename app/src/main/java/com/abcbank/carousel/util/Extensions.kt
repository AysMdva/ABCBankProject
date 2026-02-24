package com.abcbank.carousel.util

import com.abcbank.carousel.domain.model.CharacterCount

fun List<CharacterCount>.toDisplayText(): String {
    return joinToString(separator = ", ") { "${it.character} = ${it.count}" }
}
