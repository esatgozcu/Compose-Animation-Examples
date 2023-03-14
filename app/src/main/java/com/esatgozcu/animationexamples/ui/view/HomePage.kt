package com.esatgozcu.animationexamples.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.esatgozcu.animationexamples.ui.theme.AnimationExamplesTheme

@Composable
fun HomePage(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "SnowFlake")
    }
}

@Preview(showBackground = true)
@Composable
fun HomePagePreview() {
    AnimationExamplesTheme {
        HomePage(rememberNavController())
    }
}