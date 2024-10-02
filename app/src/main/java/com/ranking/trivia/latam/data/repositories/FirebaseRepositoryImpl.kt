package com.ranking.trivia.latam.data.repositories

import com.google.firebase.firestore.FirebaseFirestore
import com.ranking.trivia.latam.data.common.SETTINGS
import com.ranking.trivia.latam.data.extensions.documentListenerFlow
import com.ranking.trivia.latam.domain.models.AndroidBuildVersion
import com.ranking.trivia.latam.domain.usecases.IFirebaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FirebaseRepositoryImpl(
    private val firestore: FirebaseFirestore,
): IFirebaseRepository {

    override fun gameHasNewerVersion(currentVersion: Int): Flow<Boolean> {
        //JQLvfCyWopfOC1gxKfC8
        val collection = firestore.collection(SETTINGS).document("JQLvfCyWopfOC1gxKfC8")
        return collection.documentListenerFlow(AndroidBuildVersion::class.java).map { serverVersion ->
            return@map currentVersion >= (serverVersion?.android_build_number ?: 0)
        }
    }
}
