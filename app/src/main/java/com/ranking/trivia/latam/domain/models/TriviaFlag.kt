package com.ranking.trivia.latam.domain.models

data class TriviaFlag(
    val id: FlagId,
    val name: String,
    val image: Int,
    var position: Int? = null,
    var showPosition: Boolean = false,
    var alreadyPlayed: Boolean = false
)
