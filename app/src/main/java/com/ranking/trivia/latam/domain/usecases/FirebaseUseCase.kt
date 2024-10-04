package com.ranking.trivia.latam.domain.usecases

import com.ranking.trivia.latam.domain.models.Ranking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FirebaseUseCase @Inject constructor(
    private val firebaseRepository: IFirebaseRepository,
    private val prefsRepository: ISharedPrefsRepository
) {
    fun gameHasNewerVersion(currentVersion: Int) = firebaseRepository.gameHasNewerVersion(currentVersion)

    fun getTop20RankingList(): Flow<List<Ranking>> {
        return firebaseRepository.getRankingList().map { list ->
            val nList = list.sortedByDescending { it.score }
            return@map nList.take(20)
        }
    }

    fun saveNewRecord(score: Int, name: String, countryId: String) {
        firebaseRepository.saveNewRecord(score, name, countryId)
        prefsRepository.setUserCompletedGame()
    }
}
