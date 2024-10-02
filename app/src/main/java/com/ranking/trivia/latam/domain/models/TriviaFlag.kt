package com.ranking.trivia.latam.domain.models

data class TriviaFlag(
    val id: FlagId,
    val name: String,
    val image: Int,
    val alreadyPlayed: Boolean = false
)
