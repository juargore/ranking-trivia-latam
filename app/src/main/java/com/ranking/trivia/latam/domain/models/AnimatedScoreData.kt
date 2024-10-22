package com.ranking.trivia.latam.domain.models

data class AnimatedScoreData(
    val visible: Boolean,
    val isCorrect: Boolean,
    val question: Question
)

