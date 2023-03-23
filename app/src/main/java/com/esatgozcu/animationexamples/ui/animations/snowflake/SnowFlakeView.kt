package com.esatgozcu.animationexamples.ui.animations.snowflake

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.esatgozcu.animationexamples.R
import com.esatgozcu.animationexamples.helper.ScreenSize

@Composable
fun SnowFlakeView(viewModel: SnowFlakeVM = viewModel()){

    Box(modifier = Modifier.fillMaxSize()) {
        for (i in 0 until viewModel.itemSize) {
            Image(
                modifier = Modifier
                    .size(viewModel.mRandomSize[i].dp)
                    .offset(
                        x = viewModel.mRandomXList[i].dp,
                        y = animateDpAsState(
                            targetValue = if (viewModel.action.value) ScreenSize.screenHeight.dp else -viewModel.mRandomSize[i].dp,
                            animationSpec = repeatable(
                                10,
                                animation = tween(
                                    durationMillis = viewModel.mRandomDurationMillis[i],
                                    delayMillis = viewModel.mDelayMillis[i],
                                    easing = LinearEasing
                                ),
                                repeatMode = RepeatMode.Restart
                            )
                        ).value
                    ),
                painter = painterResource(id = R.drawable.ic_snow_flake),
                contentDescription = ""
            )
        }
    }
    LaunchedEffect(Unit){
        viewModel.action.value = true
    }
}