package com.esatgozcu.animationexamples.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.esatgozcu.animationexamples.ui.view.*
import com.esatgozcu.animationexamples.ui.viewModel.CircleRotationVM
import com.esatgozcu.animationexamples.ui.viewModel.SnowFlakeVM

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screens.HomePage.route
    ) {
        composable(route = Screens.HomePage.route) {
            HomePage(navController)
        }
        composable(route = Screens.SnowFlake.route) {
            SnowFlakeView()
        }
        composable(route = Screens.PaperPlane.route) {
            PaperPlaneView()
        }
        composable(route = Screens.BlinkCircle.route) {
            BlinkCircleView()
        }
        composable(route = Screens.CircleRotation.route) {
            CircleRotationView()
        }
    }
}

sealed class Screens(val route: String) {
    object HomePage: Screens("HomePage")
    object SnowFlake: Screens("SnowFlake")
    object PaperPlane: Screens("PaperPlane")
    object BlinkCircle: Screens("BlinkCircle")
    object CircleRotation: Screens("CircleRotation")
}