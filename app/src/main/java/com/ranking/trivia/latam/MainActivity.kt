package com.ranking.trivia.latam

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.ranking.trivia.latam.presentation.navigation.AppNavHost
import com.ranking.trivia.latam.presentation.theme.RankingTriviaLatamTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        window.decorView.apply {
            systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
        }

        setContent {
            val navController = rememberNavController()

            RankingTriviaLatamTheme {
                AppNavHost(navController = navController)
            }
        }
    }
}
