package com.esatgozcu.animationexamples

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.navigation.compose.rememberNavController
import com.esatgozcu.animationexamples.helper.ScreenSize
import com.esatgozcu.animationexamples.navigation.NavGraph
import com.esatgozcu.animationexamples.ui.theme.AnimationExamplesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnimationExamplesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ScreenSize.screensWidth = LocalConfiguration.current.screenWidthDp.toDouble()
                    ScreenSize.screenHeight = LocalConfiguration.current.screenHeightDp.toDouble()
                    NavGraph(navController = rememberNavController())
                }
            }
        }
    }
}