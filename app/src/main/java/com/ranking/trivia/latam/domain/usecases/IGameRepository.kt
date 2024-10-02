package com.ranking.trivia.latam.domain.usecases

import com.ranking.trivia.latam.domain.models.EmptySpace
import com.ranking.trivia.latam.domain.models.FlagId
import com.ranking.trivia.latam.domain.models.Question
import com.ranking.trivia.latam.domain.models.QuestionLevel
import com.ranking.trivia.latam.domain.models.TriviaFlag

interface IGameRepository {

    fun getNextQuestionLevel(currentLevel: QuestionLevel): QuestionLevel?

    fun getQuestionById(id: Int): Question

    fun getQuestionByLevelAndExcludeTheOnesAlreadyPlayed(level: QuestionLevel, idsAlreadyPlayedByLevel: List<Int>): Question?

    fun getTriviaFlagById(flagId: FlagId): TriviaFlag

    fun getEmptySpacesByLevel(level: QuestionLevel): List<EmptySpace>

    fun verifyIfListIsCorrect(userResponse: List<FlagId>, question: Question): Boolean
}
