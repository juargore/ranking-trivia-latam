package com.ranking.trivia.latam.domain.usecases

import javax.inject.Inject

class FirebaseUseCase @Inject constructor(
    private val firebaseRepository: IFirebaseRepository
) {

    fun gameHasNewerVersion(currentVersion: Int) = firebaseRepository.gameHasNewerVersion(currentVersion)
}
