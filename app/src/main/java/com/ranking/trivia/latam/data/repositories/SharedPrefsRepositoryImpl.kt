package com.ranking.trivia.latam.data.repositories

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.ranking.trivia.latam.data.common.ENABLE_SOUND
import com.ranking.trivia.latam.data.common.FIELD_LIST_OF_IDS_ALREADY_PLAYED
import com.ranking.trivia.latam.data.common.LAST_QUESTION_PLAYED
import com.ranking.trivia.latam.data.common.SHARED_PREFS_NAME
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
            println("AQUI: Se guarda en lista previa")
            gson.toJson(dataList)
        } else {
            // user hasn't played any level yet
            println("AQUI: Se guarda solo")
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
        println("AQUI: Se guarda enable = $enable")
        sharedPreferences.edit().putBoolean(ENABLE_SOUND, enable).apply()
    }

    override fun getIsSoundEnabled(): Boolean {
        val enable = sharedPreferences.getBoolean(ENABLE_SOUND, true)
        println("AQUI: Se obtiene enable = $enable")
        return enable
    }
}