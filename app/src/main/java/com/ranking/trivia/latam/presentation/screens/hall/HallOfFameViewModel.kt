package com.ranking.trivia.latam.presentation.screens.hall

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ranking.trivia.latam.domain.models.Question
import com.ranking.trivia.latam.domain.models.QuestionLevel
import com.ranking.trivia.latam.domain.models.QuestionLevel.I
import com.ranking.trivia.latam.domain.models.QuestionLevel.II
import com.ranking.trivia.latam.domain.models.QuestionLevel.III
import com.ranking.trivia.latam.domain.models.QuestionLevel.IV
import com.ranking.trivia.latam.domain.models.QuestionLevel.IX
import com.ranking.trivia.latam.domain.models.QuestionLevel.V
import com.ranking.trivia.latam.domain.models.QuestionLevel.VI
import com.ranking.trivia.latam.domain.models.QuestionLevel.VII
import com.ranking.trivia.latam.domain.models.QuestionLevel.VIII
import com.ranking.trivia.latam.domain.models.QuestionLevel.X
import com.ranking.trivia.latam.domain.models.QuestionLevel.XI
import com.ranking.trivia.latam.domain.models.QuestionLevel.XII
import com.ranking.trivia.latam.domain.models.QuestionLevel.XIII
import com.ranking.trivia.latam.domain.models.Ranking
import com.ranking.trivia.latam.domain.models.TriviaFlag
import com.ranking.trivia.latam.domain.models.getFlagId
import com.ranking.trivia.latam.domain.usecases.FirebaseUseCase
import com.ranking.trivia.latam.domain.usecases.GameUseCase
import com.ranking.trivia.latam.domain.usecases.SharedPrefsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HallOfFameViewModel @Inject constructor(
    private val firebaseUseCase: FirebaseUseCase,
    private val prefsUseCase: SharedPrefsUseCase,
    private val gameUseCase: GameUseCase
): ViewModel() {

    private val _ranking = MutableStateFlow<List<Ranking>>(emptyList())
    val ranking: StateFlow<List<Ranking>>
        get() = _ranking

    init {
        getTop20RankingList()
    }

    private fun getTop20RankingList() {
        viewModelScope.launch(Dispatchers.IO) {
            firebaseUseCase.getTop20RankingList().collectLatest { list ->
                list.forEach { item ->
                    getFlagId(item.country_id)?.let { flagId ->
                        item.flag = gameUseCase.getFlagById(flagId)
                    }
                }
                _ranking.value = list
            }
        }
    }

    fun incrementScore(question: Question, isCorrect: Boolean) {
        val points = if (isCorrect) {
            getPointsForCorrectResponse(question.level)
        } else {
            getPointsForIncorrectResponse(question.level)
        }
        prefsUseCase.incrementScore(points)
    }

    fun getPointsToAnimate(question: Question, isCorrect: Boolean): Int {
        return if (isCorrect) {
            getPointsForCorrectResponse(question.level)
        } else {
            getPointsForIncorrectResponse(question.level)
        }
    }

    private fun getPointsForIncorrectResponse(level: QuestionLevel): Int {
        return when (level) {
            I -> -1
            II -> -2
            III -> -3
            IV -> -3
            V -> -3
            VI -> -4
            VII -> -4
            VIII -> -5
            IX -> -5
            X -> -6
            XI -> -7
            XII -> -8
            XIII -> -9
        }
    }

    private fun getPointsForCorrectResponse(level: QuestionLevel): Int {
        return when (level) {
            I -> 5
            II -> 5
            III -> 6
            IV -> 6
            V -> 6
            VI -> 7
            VII -> 7
            VIII -> 8
            IX -> 8
            X -> 9
            XI -> 10
            XII -> 11
            XIII -> 12 // -> Max 700 pts
        }
    }

    fun getTotalScore(): Int = prefsUseCase.getTotalScore()

    fun getAllTriviaFlags(): List<TriviaFlag> {
        return gameUseCase.getAllTriviaFlags()
    }

    private fun resetScore() {
        prefsUseCase.resetScore()
    }

    fun saveNewRecord(flag: TriviaFlag?, name: String) {
        flag?.id?.name?.let { countryId ->
            viewModelScope.launch(Dispatchers.IO) {
                firebaseUseCase.saveNewRecord(getTotalScore(), name, countryId)
                resetScore()
            }
        }
    }
}
