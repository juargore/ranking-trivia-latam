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

    fun incrementCounterOfErrors() = prefsRepository.incrementCounterOfErrors()

    fun getTotalErrors() = prefsRepository.getTotalErrors()

    fun resetErrors() = prefsRepository.resetErrors()

    fun incrementScore(points: Int) = prefsRepository.incrementScore(points)

    fun getTotalScore(): Int = prefsRepository.getTotalScore()

    fun resetScore() = prefsRepository.resetScore()

    fun getUserCompletedGame() = prefsRepository.getUserCompletedGame()
}
