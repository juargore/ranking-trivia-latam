package com.ranking.trivia.latam.data.repositories

import com.google.firebase.firestore.FirebaseFirestore
import com.ranking.trivia.latam.data.common.RANKING
import com.ranking.trivia.latam.data.common.SETTINGS
import com.ranking.trivia.latam.data.extensions.collectionListenerFlow
import com.ranking.trivia.latam.data.extensions.documentListenerFlow
import com.ranking.trivia.latam.domain.models.AndroidBuildVersion
import com.ranking.trivia.latam.domain.models.Ranking
import com.ranking.trivia.latam.domain.usecases.IFirebaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FirebaseRepositoryImpl(
    private val firestore: FirebaseFirestore,
): IFirebaseRepository {

    override fun gameHasNewerVersion(currentVersion: Int): Flow<Boolean> {
        val collection = firestore.collection(SETTINGS).document("JQLvfCyWopfOC1gxKfC8")
        return collection.documentListenerFlow(AndroidBuildVersion::class.java).map { serverVersion ->
            return@map currentVersion >= (serverVersion?.android_build_number ?: 0)
        }
    }

    override fun getRankingList(): Flow<List<Ranking>> {
        val serviceEventsCollection = firestore.collection(RANKING)
        return serviceEventsCollection.collectionListenerFlow(Ranking::class.java)
    }

    override fun saveNewRecord(score: Int, name: String, countryId: String) {
        val data = mapOf(
            "country_id" to countryId,
            "score" to score,
            "user_name" to name
        )
        firestore.collection(RANKING)
            .add(data)
            .addOnSuccessListener { documentReference ->
                println("AQUI: DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                println("AQUI: Error adding document: $e")
            }
    }
}
