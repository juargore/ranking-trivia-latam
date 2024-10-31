package com.ranking.trivia.latam.presentation.screens.play

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
import com.ranking.trivia.latam.domain.models.QuestionLevel.I
import com.ranking.trivia.latam.domain.models.QuestionLevel.II
import com.ranking.trivia.latam.domain.models.QuestionLevel.III
import com.ranking.trivia.latam.domain.models.QuestionLevel.IV
import com.ranking.trivia.latam.domain.models.QuestionLevel.V
import com.ranking.trivia.latam.domain.models.QuestionLevel.VI
import com.ranking.trivia.latam.domain.models.QuestionLevel.VII
import com.ranking.trivia.latam.domain.models.QuestionLevel.VIII
import com.ranking.trivia.latam.domain.models.QuestionLevel.IX
import com.ranking.trivia.latam.domain.models.QuestionLevel.X
import com.ranking.trivia.latam.domain.models.QuestionLevel.XI
import com.ranking.trivia.latam.domain.models.QuestionLevel.XII
import com.ranking.trivia.latam.domain.models.QuestionLevel.XIII

@HiltViewModel
class PlayViewModel @Inject constructor(
    private val gameUseCase: GameUseCase,
    private val prefsUseCase: SharedPrefsUseCase
): ViewModel() {

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
        if (lastQuestionIdPlayed > 0) {
            val lastQuestionPlayed: Question = gameUseCase.getQuestionById(lastQuestionIdPlayed)
            val idsAlreadyPlayedByLevel: List<Int> = prefsUseCase.getIdsOfQuestionsAlreadyPlayedByQuestionLevel(lastQuestionPlayed.level)
            val nextQuestion: Question? = gameUseCase.getQuestionByLevelAndExcludeTheOnesAlreadyPlayed(lastQuestionPlayed.level, idsAlreadyPlayedByLevel)
            if (nextQuestion != null) {
                // at this level, there are still pending questions
                _question.value = nextQuestion
                getEmptySpacesByLevel(nextQuestion)
            } else {
                // the level is complete -> continue to next level if exists
                //println("AQUI: the level is complete -> continue to next level if exists")
                val nextLevel: QuestionLevel? = gameUseCase.getNextQuestionLevel(lastQuestionPlayed.level)
                if (nextLevel == null) {
                    // no more levels available -> user has completed the game!!
                    //println("AQUI: no more levels available -> user has completed the game!!")
                    _gameCompleted.value = true
                } else {
                    // there are still pending levels -> get the first random question of nextLevel
                    //println("AQUI: there are still pending levels -> get the first random question of nextLevel")
                    val newQuestion: Question? = gameUseCase.getQuestionByLevelAndExcludeTheOnesAlreadyPlayed(nextLevel, listOf())
                    _question.value = newQuestion
                    newQuestion?.let { getEmptySpacesByLevel(it) }
                }
            }
        } else {
            // no question stored in shared preferences -> user just started to play!
            //println("AQUI: no question stored in shared preferences -> user just started to play!")
            val newQuestion: Question? = gameUseCase.getQuestionByLevelAndExcludeTheOnesAlreadyPlayed(I, listOf())
            _question.value = newQuestion
            newQuestion?.let { getEmptySpacesByLevel(it) }
        }
    }

    private fun getEmptySpacesByLevel(question: Question) {
        _spaces.value = gameUseCase.getEmptySpacesByLevel(question.level)
        getFlagsByQuestion(question)
    }

    private fun getFlagsByQuestion(question: Question) {
        val mFlags = mutableListOf<TriviaFlag>()
        question.gameFlags?.forEach {
            val flag: TriviaFlag = gameUseCase.getFlagById(it)
            mFlags.add(flag)
        }
        _flags.value = mFlags
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
            //println("AQUI: Se guarda ${question.id} como útlima jugada")
            prefsUseCase.saveQuestionAlreadyPlayed(question)
        }
    }

    fun shouldPlaySound(): Boolean = prefsUseCase.getIsSoundEnabled()

    fun incrementCounterOfErrors() {
        prefsUseCase.incrementCounterOfErrors()
    }

    fun shouldDisplayAd(): Boolean {
        val totalErrors = prefsUseCase.getTotalErrors()
        //println("AQUI: TotalErrors stored = $totalErrors")
        return totalErrors >= 4
    }

    fun shouldDisplayAdAtStart(): Boolean {
        val totalErrors = prefsUseCase.getTotalErrors()
        //println("AQUI: TotalErrors stored max = $totalErrors")
        return totalErrors >= 6
    }

    fun resetErrors() {
        prefsUseCase.resetErrors()
    }

    fun shouldShowHintDialog(): Boolean {
        val showDialog = prefsUseCase.getShowHintDialog()
        println("AQUI: shouldShowHintDialog = $showDialog")
        return showDialog
    }

    fun saveShouldShowHintDialog(show: Boolean) {
        prefsUseCase.saveShowHintDialog(show)
    }

    fun discoverPositionOnFlag() {
        var updated = false
        _flags.value = _flags.value.map {
            if (!updated && !it.alreadyPlayed && !it.showPosition) {
                updated = true
                it.copy(
                    showPosition = true,
                    position = getFlagPosition(it)
                )
            } else {
                it
            }
        }
    }

    private fun getFlagPosition(flag: TriviaFlag): Int {
        _question.value?.answerFlags?.forEachIndexed { index, flagId ->
            if (flag.id == flagId) {
                return index + 1
            }
        }
        return 0
    }

    fun getTimeAccordingLevel(level: QuestionLevel): Long {
        return when (level) {
            I, II, III -> 60000L
            IV, V -> 50000L
            VI, VII -> 45000L
            VIII, IX, X -> 40000L
            XI -> 30000L
            XII -> 20000L
            XIII -> 15000L
        }
    }
}
