package com.ranking.trivia.latam.presentation.screens.play

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.ranking.trivia.latam.domain.models.EmptySpace
import com.ranking.trivia.latam.domain.models.FlagId
import com.ranking.trivia.latam.domain.models.Question
import com.ranking.trivia.latam.domain.models.QuestionLevel
import com.ranking.trivia.latam.domain.models.TriviaFlag
import com.ranking.trivia.latam.domain.usecases.GameUseCase
import com.ranking.trivia.latam.domain.usecases.SharedPrefsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class PlayViewModel @Inject constructor(
    private val gameUseCase: GameUseCase,
    private val prefsUseCase: SharedPrefsUseCase
): ViewModel() {

    var currentDropTarget: Int? by mutableStateOf(null)

    private val _gameCompleted = MutableStateFlow(false)
    val gameCompleted: StateFlow<Boolean>
        get() = _gameCompleted

    private val _question = MutableStateFlow<Question?>(null)
    val question: StateFlow<Question?>
        get() = _question

    private val _flags = MutableStateFlow<List<TriviaFlag>>(emptyList())
    val flags: StateFlow<List<TriviaFlag>>
        get() = _flags

    private val _spaces = MutableStateFlow<List<EmptySpace>>(emptyList())
    val spaces: StateFlow<List<EmptySpace>>
        get() = _spaces

    fun getQuestionToPlay() {
        val lastQuestionIdPlayed: Int = prefsUseCase.getLastQuestionIdPlayed() // questionId (Int) or -1
        println("AQUI: lastQuestionIdPlayed: $lastQuestionIdPlayed")
        if (lastQuestionIdPlayed > 0) {
            val lastQuestionPlayed: Question = gameUseCase.getQuestionById(lastQuestionIdPlayed)
            val idsAlreadyPlayedByLevel: List<Int> = prefsUseCase.getIdsOfQuestionsAlreadyPlayedByQuestionLevel(lastQuestionPlayed.level)
            println("AQUI: idsAlreadyPlayedByLevel ===")
            idsAlreadyPlayedByLevel.forEach {
                println("AQUI: $it")
            }
            val nextQuestion: Question? = gameUseCase.getQuestionByLevelAndExcludeTheOnesAlreadyPlayed(lastQuestionPlayed.level, idsAlreadyPlayedByLevel)
            if (nextQuestion != null) {
                // at this level, there are still pending questions
                println("AQUI: at this level, there are still pending questions")
                _question.value = nextQuestion
                getEmptySpacesByLevel(nextQuestion)
            } else {
                // the level is complete -> continue to next level if exists
                println("AQUI: the level is complete -> continue to next level if exists")
                val nextLevel: QuestionLevel? = gameUseCase.getNextQuestionLevel(lastQuestionPlayed.level)
                if (nextLevel == null) {
                    // no more levels available -> user has completed the game!!
                    println("AQUI: no more levels available -> user has completed the game!!")
                    _gameCompleted.value = true
                } else {
                    // there are still pending levels -> get the first random question of nextLevel
                    println("AQUI: there are still pending levels -> get the first random question of nextLevel")
                    val newQuestion: Question? = gameUseCase.getQuestionByLevelAndExcludeTheOnesAlreadyPlayed(nextLevel, listOf())
                    _question.value = newQuestion
                    newQuestion?.let { getEmptySpacesByLevel(it) }
                }
            }
        } else {
            // no question stored in shared preferences -> user just started to play!
            println("AQUI: no question stored in shared preferences -> user just started to play!")
            val newQuestion: Question? = gameUseCase.getQuestionByLevelAndExcludeTheOnesAlreadyPlayed(QuestionLevel.I, listOf())
            _question.value = newQuestion
            newQuestion?.let { getEmptySpacesByLevel(it) }
        }
    }

    private fun getEmptySpacesByLevel(question: Question) {
        _spaces.value = gameUseCase.getEmptySpacesByLevel(question.level)
        getFlagsByQuestion(question)
    }

    private fun getFlagsByQuestion(question: Question) {
        val mQuestions = mutableListOf<TriviaFlag>()
        question.gameFlags?.forEach {
            val flag: TriviaFlag = gameUseCase.getFlagById(it)
            mQuestions.add(flag)
        }
        _flags.value = mQuestions
    }

    fun verifyIfListIsCorrect(userResponse: List<FlagId>, question: Question): Boolean {
        return gameUseCase.verifyIfListIsCorrect(userResponse, question)
    }

    fun updateEmptySpaceUI(emptySpaceId: Int, flag: TriviaFlag?) {
        _spaces.value = _spaces.value.map { space ->
            if (space.id == emptySpaceId) {
                space.copy(flag = flag)
            } else {
                space
            }
        }
    }

    fun removeFlagFromList(flag: TriviaFlag?) {
        _flags.value = _flags.value.map {
            if (it == flag) {
                it.copy(alreadyPlayed = true)
            } else {
                it
            }
        }
    }

    fun addFlagToList(flag: TriviaFlag?) {
        _flags.value = _flags.value.map {
            if (it.id == flag?.id) {
                it.copy(alreadyPlayed = false)
            } else {
                it
            }
        }
    }

    fun isThisFlagAlreadyUsed(flag: TriviaFlag): Boolean {
        return _spaces.value.any { it.flag?.id == flag.id }
    }

    fun saveQuestionAlreadyPlayed(question: Question?) {
        if (question != null) {
            println("AQUI: Se guarda ${question.id} como útlima jugada")
            prefsUseCase.saveQuestionAlreadyPlayed(question)
        }
    }

    fun shouldPlaySound() : Boolean = prefsUseCase.getIsSoundEnabled()
}

