package com.esatgozcu.animationexamples.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.esatgozcu.animationexamples.R
import com.esatgozcu.animationexamples.navigation.Screens
import com.esatgozcu.animationexamples.ui.theme.AnimationExamplesTheme

@Composable
fun HomePage(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        HomePageItem(title = "Snow Flake", image = R.drawable.ic_snow_flake){
            navController.navigate(Screens.SnowFlake.route)
        }
        HomePageItem(title = "Paper Plane", image = R.drawable.ic_paper_plane){
            navController.navigate(Screens.PaperPlane.route)
        }
        Row(verticalAlignment = CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .clickable {
                    navController.navigate(Screens.BlinkCircle.route)
                }
                .padding(10.dp),
        ){
            Text(text = "Blink Circle")
            Box(
                modifier = Modifier
                    .size(25.dp)
                    .clip(CircleShape)
                    .background(Color.Black)
            )
        }
        HomePageItem(title = "Circle Rotation", image = R.drawable.ic_rotate_3d){
            navController.navigate(Screens.CircleRotation.route)
        }
        Row(verticalAlignment = CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .clickable {
                    navController.navigate(Screens.ConfettiCenter.route)
                }
                .padding(10.dp),
        ){
            Text(text = "Confetti View")
            Text(
                text = "\uD83C\uDF89",
                modifier = Modifier
                    .size(25.dp)
            )
        }
        Row(verticalAlignment = CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .clickable {
                    navController.navigate(Screens.FireworkCenter.route)
                }
                .padding(10.dp),
        ){
            Text(text = "Firework View")
            Text(
                text = "\uD83C\uDF86",
                modifier = Modifier
                    .size(25.dp)
            )
        }
    }
}

@Composable
fun HomePageItem(title:String,
                 image:Int,
                 click: (()->Unit)){
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .clickable {
                click()
            }
            .padding(10.dp),
        verticalAlignment = CenterVertically
    ){
        Text(text = title)
        Image(
            modifier = Modifier.size(25.dp),
            painter = painterResource(id = image),
            contentDescription = ""
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomePagePreview() {
    AnimationExamplesTheme {
        HomePage(rememberNavController())
    }
}