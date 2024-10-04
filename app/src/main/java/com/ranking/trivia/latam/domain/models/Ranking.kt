@file:Suppress("PropertyName")

package com.ranking.trivia.latam.domain.models

data class Ranking(
    override var id: String,
    var position: Int?,
    val country_id: String,
    val user_name: String,
    val score: Int,
    var flag: TriviaFlag?
) : FirebaseModel {
    constructor(): this(
        id = "",
        position = null,
        country_id = "",
        user_name = "",
        score = 0,
        flag = null
    )
}
