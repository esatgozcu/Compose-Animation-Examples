package com.esatgozcu.animationexamples.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.esatgozcu.animationexamples.ui.animations.blinkcircle.BlinkCircleView
import com.esatgozcu.animationexamples.ui.animations.circlerotation.CircleRotationView
import com.esatgozcu.animationexamples.ui.animations.confetticenter.ConfettiCenterView
import com.esatgozcu.animationexamples.ui.animations.fireworkcenter.FireworkCenterView
import com.esatgozcu.animationexamples.ui.animations.paperplane.PaperPlaneView
import com.esatgozcu.animationexamples.ui.animations.snowflake.SnowFlakeView
import com.esatgozcu.animationexamples.ui.view.*

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
        composable(route = Screens.ConfettiCenter.route) {
            ConfettiCenterView()
        }
        composable(route = Screens.FireworkCenter.route) {
            FireworkCenterView()
        }
    }
}

sealed class Screens(val route: String) {
    object HomePage: Screens("HomePage")
    object SnowFlake: Screens("SnowFlake")
    object PaperPlane: Screens("PaperPlane")
    object BlinkCircle: Screens("BlinkCircle")
    object CircleRotation: Screens("CircleRotation")
    object ConfettiCenter: Screens("ConfettiCenter")
    object FireworkCenter: Screens("FireworkCenter")
}