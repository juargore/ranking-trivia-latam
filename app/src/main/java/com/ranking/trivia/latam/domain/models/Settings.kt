@file:Suppress("PropertyName")

package com.ranking.trivia.latam.domain.models

data class AndroidBuildVersion(
    override var id: String,
    val android_build_number: Int
) : FirebaseModel {
    constructor(): this(
        id = "",
        android_build_number = 0
    )
}
