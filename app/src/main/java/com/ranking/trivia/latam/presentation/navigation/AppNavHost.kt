package com.ranking.trivia.latam.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ranking.trivia.latam.presentation.screens.home.HomeScreen
import com.ranking.trivia.latam.presentation.screens.play.PlayScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = NavigationItem.Home.route,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {

        composable(NavigationItem.Home.route) {
            HomeScreen(
                onNavigateToPlayScreen = {
                    navController.navigate(NavigationItem.Play.route)
                }
            )
        }

        composable(NavigationItem.Play.route) {
            PlayScreen(
                onResetScreen = {
                    navController.popBackStack()
                    navController.navigate(NavigationItem.Play.route)
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
