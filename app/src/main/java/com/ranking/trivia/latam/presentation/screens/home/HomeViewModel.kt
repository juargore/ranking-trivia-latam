package com.ranking.trivia.latam.presentation.screens.home

import androidx.core.os.LocaleListCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ranking.trivia.latam.domain.usecases.FirebaseUseCase
import com.ranking.trivia.latam.domain.usecases.SharedPrefsUseCase
import com.ranking.trivia.latam.presentation.ui.dialogs.AppLanguage
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

    fun resetAllData() {
        prefsUseCase.resetAllData()
    }

    fun saveAppLanguage(language: LocaleListCompat) {
        val appLanguage = when (language) {
            LocaleListCompat.forLanguageTags("en") -> AppLanguage.EN
            LocaleListCompat.forLanguageTags("pt-BR") -> AppLanguage.BR
            else -> AppLanguage.ES
        }
        prefsUseCase.saveAppLanguage(appLanguage)
    }

    fun getStoredLocaleListCompat(): LocaleListCompat {
        val storedLanguage = getAppLanguage()
        return when (storedLanguage) {
            AppLanguage.ES -> LocaleListCompat.forLanguageTags("es")
            AppLanguage.EN -> LocaleListCompat.forLanguageTags("en")
            AppLanguage.BR -> LocaleListCompat.forLanguageTags("pt-BR")
        }
    }

    fun getInitialOptionForRB(): Int {
        val storedLanguage = getAppLanguage()
        return when (storedLanguage) {
            AppLanguage.ES -> 0
            AppLanguage.EN -> 1
            AppLanguage.BR -> 2
        }
    }

    private fun getAppLanguage(): AppLanguage {
        return prefsUseCase.getAppLanguage()
    }
}
