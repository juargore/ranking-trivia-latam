package com.ranking.trivia.latam.data.module

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.ranking.trivia.latam.data.repositories.FirebaseRepositoryImpl
import com.ranking.trivia.latam.data.repositories.GameRepositoryImpl
import com.ranking.trivia.latam.data.repositories.SharedPrefsRepositoryImpl
import com.ranking.trivia.latam.domain.usecases.IFirebaseRepository
import com.ranking.trivia.latam.domain.usecases.IGameRepository
import com.ranking.trivia.latam.domain.usecases.ISharedPrefsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoriesModule {

    @Provides
    fun provideContext(@ApplicationContext appContext: Context): Context {
        return appContext
    }

    @Singleton
    @Provides
    fun provideFirebase(): FirebaseFirestore {
        val firestore = FirebaseFirestore.getInstance()
        val settings = FirebaseFirestoreSettings
            .Builder()
            .setPersistenceEnabled(true)
            .build()
        firestore.firestoreSettings = settings
        return firestore
    }

    @Singleton
    @Provides
    fun provideGoogleRepository(
        context: Context
    ): IGameRepository {
        return GameRepositoryImpl(context)
    }

    @Singleton
    @Provides
    fun provideSharedPrefsRepository(
        context: Context
    ): ISharedPrefsRepository {
        return SharedPrefsRepositoryImpl(context)
    }

    @Singleton
    @Provides
    fun provideFirebaseRepository(
        firestore: FirebaseFirestore
    ): IFirebaseRepository {
        return FirebaseRepositoryImpl(firestore)
    }
}
