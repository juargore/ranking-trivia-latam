package com.ranking.trivia.latam.domain.usecases

import com.ranking.trivia.latam.domain.models.FlagId
import com.ranking.trivia.latam.domain.models.Question
import com.ranking.trivia.latam.domain.models.QuestionLevel
import javax.inject.Inject

class GameUseCase @Inject constructor(
    private val gameRepository: IGameRepository
) {

    fun getNextQuestionLevel(currentLevel: QuestionLevel) =
        gameRepository.getNextQuestionLevel(currentLevel)

    fun getQuestionById(id: Int) =
        gameRepository.getQuestionById(id)

    fun getQuestionByLevelAndExcludeTheOnesAlreadyPlayed(level: QuestionLevel, idsAlreadyPlayedByLevel: List<Int>) =
        gameRepository.getQuestionByLevelAndExcludeTheOnesAlreadyPlayed(level, idsAlreadyPlayedByLevel)

    fun getFlagById(flagId: FlagId) =
        gameRepository.getTriviaFlagById(flagId)

    fun getEmptySpacesByLevel(level: QuestionLevel) =
        gameRepository.getEmptySpacesByLevel(level)

    fun verifyIfListIsCorrect(userResponse: List<FlagId>, question: Question) =
        gameRepository.verifyIfListIsCorrect(userResponse, question)
}
