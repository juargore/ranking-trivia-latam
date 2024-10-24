package com.ranking.trivia.latam.data.repositories

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.ranking.trivia.latam.data.common.ENABLE_SOUND
import com.ranking.trivia.latam.data.common.FIELD_LIST_OF_IDS_ALREADY_PLAYED
import com.ranking.trivia.latam.data.common.GAME_COMPLETED
import com.ranking.trivia.latam.data.common.LAST_QUESTION_PLAYED
import com.ranking.trivia.latam.data.common.SELECTED_LANGUAGE
import com.ranking.trivia.latam.data.common.SHARED_PREFS_NAME
import com.ranking.trivia.latam.data.common.TOTAL_ERRORS
import com.ranking.trivia.latam.data.common.TOTAL_SCORE
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
import com.ranking.trivia.latam.domain.usecases.ISharedPrefsRepository
import com.ranking.trivia.latam.presentation.ui.dialogs.AppLanguage

class SharedPrefsRepositoryImpl(context: Context): ISharedPrefsRepository {

    private val gson = Gson()
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)

    override fun saveQuestionAlreadyPlayed(question: Question) {
        val listIdsAlreadyPlayed = sharedPreferences.getString(FIELD_LIST_OF_IDS_ALREADY_PLAYED, null)
        val newList: String = if (listIdsAlreadyPlayed != null) {
            // user already played at least one level
            val dataList = gson.fromJson(listIdsAlreadyPlayed, Array<Int>::class.java).toMutableList()
            if (!dataList.any { it == question.id}) {
                dataList.add(question.id)
            }
            gson.toJson(dataList)
        } else {
            // user hasn't played any level yet
            gson.toJson(arrayOf(question.id))
        }
        sharedPreferences.edit().putString(FIELD_LIST_OF_IDS_ALREADY_PLAYED, newList).apply()
        saveLastQuestionPlayed(question)
    }

    private fun saveLastQuestionPlayed(question: Question) {
        sharedPreferences.edit().putInt(LAST_QUESTION_PLAYED, question.id).apply()
    }

    override fun getLastQuestionIdPlayed(): Int {
        return sharedPreferences.getInt(LAST_QUESTION_PLAYED, -1)
    }

    override fun getIdsOfQuestionsAlreadyPlayedByQuestionLevel(level: QuestionLevel): List<Int> {
        val currentData = sharedPreferences.getString(FIELD_LIST_OF_IDS_ALREADY_PLAYED, null)
        return if (currentData != null) {
            val list = gson.fromJson(currentData, Array<Int>::class.java).toList()
            val range = when (level) {
                I -> 0..19
                II -> 20..29
                III -> 30..39
                IV -> 40..49
                V -> 50..59
                VI -> 60..69
                VII -> 70..79
                VIII -> 80..89
                IX -> 90..99
                X -> 100..109
                XI -> 110..119
                XII -> 120..129
                XIII -> 130..139
            }
            val mList = list.filter { it in range }
            return mList
        } else {
            emptyList()
        }
    }

    override fun saveEnableSound(enable: Boolean) {
        sharedPreferences.edit().putBoolean(ENABLE_SOUND, enable).apply()
    }

    override fun getIsSoundEnabled(): Boolean {
        return sharedPreferences.getBoolean(ENABLE_SOUND, true)
    }

    override fun incrementCounterOfErrors() {
        val totalErrors = sharedPreferences.getInt(TOTAL_ERRORS, 0)
        val newTotalErrors = totalErrors + 1
        sharedPreferences.edit().putInt(TOTAL_ERRORS, newTotalErrors).apply()
    }

    override fun getTotalErrors(): Int {
        return sharedPreferences.getInt(TOTAL_ERRORS, 0)
    }

    override fun resetErrors() {
        sharedPreferences.edit().putInt(TOTAL_ERRORS, 0).apply()
    }

    override fun incrementScore(points: Int) {
        val storedScore = sharedPreferences.getInt(TOTAL_SCORE, 0)
        val newTotalScore = storedScore + points
        sharedPreferences.edit().putInt(TOTAL_SCORE, newTotalScore).apply()
    }

    override fun getTotalScore(): Int {
        return sharedPreferences.getInt(TOTAL_SCORE, 0)
    }

    override fun resetScore() {
        sharedPreferences.edit().putInt(TOTAL_SCORE, 0).apply()
    }

    override fun setUserCompletedGame() {
        sharedPreferences.edit().putBoolean(GAME_COMPLETED, true).apply()
    }

    override fun getUserCompletedGame(): Boolean {
        return sharedPreferences.getBoolean(GAME_COMPLETED, false)
    }

    override fun saveAppLanguage(appLanguage: AppLanguage) {
        val selectedLanguage = when (appLanguage) {
            AppLanguage.ES -> "es"
            AppLanguage.EN -> "en"
            AppLanguage.BR -> "br"
        }
        sharedPreferences.edit().putString(SELECTED_LANGUAGE, selectedLanguage).apply()
    }

    override fun getAppLanguage(): AppLanguage {
        val storedLanguage = sharedPreferences.getString(SELECTED_LANGUAGE, "es")
        return when (storedLanguage) {
            "en" -> AppLanguage.EN
            "br" -> AppLanguage.BR
            else -> AppLanguage.ES
        }
    }

    override fun resetAllData() {
        sharedPreferences.edit().remove(FIELD_LIST_OF_IDS_ALREADY_PLAYED).apply()
        sharedPreferences.edit().remove(LAST_QUESTION_PLAYED).apply()
        sharedPreferences.edit().remove(TOTAL_ERRORS).apply()
        sharedPreferences.edit().remove(TOTAL_SCORE).apply()
        sharedPreferences.edit().remove(GAME_COMPLETED).apply()
    }

}
