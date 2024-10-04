package com.ranking.trivia.latam.domain.usecases

import com.ranking.trivia.latam.domain.models.Ranking
import kotlinx.coroutines.flow.Flow

interface IFirebaseRepository {

    fun gameHasNewerVersion(currentVersion: Int): Flow<Boolean>

    fun getRankingList(): Flow<List<Ranking>>

    fun saveNewRecord(score: Int, name: String, countryId: String)
}
