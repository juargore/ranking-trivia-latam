package com.ranking.trivia.latam.domain.usecases

import com.ranking.trivia.latam.domain.models.Question
import com.ranking.trivia.latam.domain.models.QuestionLevel
import javax.inject.Inject

class SharedPrefsUseCase @Inject constructor(
    private val prefsRepository: ISharedPrefsRepository
) {

    fun saveQuestionAlreadyPlayed(question: Question) {
        prefsRepository.saveQuestionAlreadyPlayed(question)
    }

    fun getLastQuestionIdPlayed() = prefsRepository.getLastQuestionIdPlayed()

    fun getIdsOfQuestionsAlreadyPlayedByQuestionLevel(level: QuestionLevel): List<Int> {
        return prefsRepository.getIdsOfQuestionsAlreadyPlayedByQuestionLevel(level).sorted()
    }

    fun saveEnableSound(enable: Boolean) = prefsRepository.saveEnableSound(enable)

    fun getIsSoundEnabled() = prefsRepository.getIsSoundEnabled()
}