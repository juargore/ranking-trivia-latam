package com.ranking.trivia.latam.domain.usecases

import com.ranking.trivia.latam.domain.models.Question
import com.ranking.trivia.latam.domain.models.QuestionLevel

interface ISharedPrefsRepository {

    fun saveQuestionAlreadyPlayed(question: Question)

    fun getLastQuestionIdPlayed(): Int

    fun getIdsOfQuestionsAlreadyPlayedByQuestionLevel(level: QuestionLevel): List<Int>

    fun saveEnableSound(enable: Boolean)

    fun getIsSoundEnabled(): Boolean
}