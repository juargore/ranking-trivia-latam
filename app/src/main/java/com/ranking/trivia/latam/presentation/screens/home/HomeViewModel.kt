package com.ranking.trivia.latam.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ranking.trivia.latam.domain.usecases.FirebaseUseCase
import com.ranking.trivia.latam.domain.usecases.SharedPrefsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val firebaseUseCase: FirebaseUseCase,
    private val prefsUseCase: SharedPrefsUseCase
): ViewModel() {

    fun gameHasNewerVersion(currentVersion: Int, onResponse: (Boolean) -> Unit) {
        viewModelScope.launch {
            firebaseUseCase.gameHasNewerVersion(currentVersion).collectLatest {
                onResponse(it)
            }
        }
    }

    fun saveEnableSound(enable: Boolean) {
        prefsUseCase.saveEnableSound(enable)
    }

    fun shouldPlaySound() : Boolean = prefsUseCase.getIsSoundEnabled()

    fun userCompletedGame() : Boolean = prefsUseCase.getUserCompletedGame()
}
