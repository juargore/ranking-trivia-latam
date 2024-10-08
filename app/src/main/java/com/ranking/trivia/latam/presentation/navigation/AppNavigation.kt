package com.ranking.trivia.latam.presentation.navigation

enum class Screen {
    HOME,
    PLAY,
    HALL_OF_FAME
}

sealed class NavigationItem(val route: String) {
    data object Home : NavigationItem(Screen.HOME.name)
    data object Play : NavigationItem(Screen.PLAY.name)
    data object HallOfFame : NavigationItem(Screen.HALL_OF_FAME.name)
}
