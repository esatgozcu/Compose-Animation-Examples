package com.esatgozcu.animationexamples.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.esatgozcu.animationexamples.ui.view.HomePage

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screens.HomePage.route
    ) {
        composable(route = Screens.HomePage.route) {
            HomePage(navController)
        }
    }
}

sealed class Screens(val route: String) {
    object HomePage: Screens("HomePage")
}