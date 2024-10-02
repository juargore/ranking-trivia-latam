package com.ranking.trivia.latam.domain.usecases

import kotlinx.coroutines.flow.Flow

interface IFirebaseRepository {

    fun gameHasNewerVersion(currentVersion: Int): Flow<Boolean>
}
