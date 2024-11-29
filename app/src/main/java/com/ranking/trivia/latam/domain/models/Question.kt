package com.ranking.trivia.latam.domain.models

data class Question(
    val id: Int,
    val level: QuestionLevel, // indicates the level where this question should be placed on game
    val description: String, // question to show at the top header
    val answerFlags: List<FlagId>, // correct answers ordered by position
    val info: String, // more info given after a correct answer
    var gameFlags: List<FlagId>? = null,// correct answers ordered by position + random flags according to level
)
