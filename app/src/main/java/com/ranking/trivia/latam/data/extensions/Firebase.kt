package com.ranking.trivia.latam.data.extensions

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
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
                    //println("AQUI: Error al serializar firebase obj: ${e.message}")
                }
            } else {
                trySend(null)
            }
        }
    }

    val registration = addSnapshotListener(listener)
    awaitClose { registration.remove() }
}

fun <T : FirebaseModel> CollectionReference.collectionListenerFlow(
    dataType: Class<T>,
    query: com.google.firebase.firestore.Query? = null
): Flow<List<T>> = callbackFlow {
    val listener = object : EventListener<QuerySnapshot> {
        override fun onEvent(querySnapshot: QuerySnapshot?, exception: FirebaseFirestoreException?) {
            if (exception != null) {
                cancel()
                return
            }

            val dataList = mutableListOf<T>()
            for (documentSnapshot in querySnapshot!!.documents) {
                if (documentSnapshot.exists()) {
                    try {
                        val data = documentSnapshot.toObject(dataType)
                        if (data != null) {
                            data.id = documentSnapshot.id
                            dataList.add(data)
                        }
                    }  catch (e: Exception) {
                        println("AQUI: Error al serializar firebase obj: ${e.message}")
                    }
                }
            }
            trySend(dataList)
        }
    }

    val registration = query?.addSnapshotListener(listener) ?: addSnapshotListener(listener)
    awaitClose { registration.remove() }
}
