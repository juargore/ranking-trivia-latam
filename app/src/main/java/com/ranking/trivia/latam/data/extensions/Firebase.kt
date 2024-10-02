package com.ranking.trivia.latam.data.extensions

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestoreException
import com.ranking.trivia.latam.domain.models.FirebaseModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun <T: FirebaseModel> DocumentReference.documentListenerFlow(
    dataType: Class<T>
): Flow<T?> = callbackFlow {
    val listener = object : EventListener<DocumentSnapshot> {
        override fun onEvent(snapshot: DocumentSnapshot?, exception: FirebaseFirestoreException?) {
            if (exception != null) {
                trySend(null) // send null in case of exception
                cancel(CancellationException("FirebaseFirestoreException occurred", exception))
                return
            }

            if (snapshot != null && snapshot.exists()) {
                try {
                    val data = snapshot.toObject(dataType)
                    if (data != null) {
                        data.id = snapshot.id
                        trySend(data)
                    }
                } catch (e: Exception) {
                    println("AQUI: Error al serializar firebase obj: ${e.message}")
                }
            } else {
                trySend(null)
            }
        }
    }

    val registration = addSnapshotListener(listener)
    awaitClose { registration.remove() }
}
