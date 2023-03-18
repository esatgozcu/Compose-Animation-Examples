package com.esatgozcu.animationexamples.ui.view

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.esatgozcu.animationexamples.ui.viewModel.BlinkCircleVM
import androidx.lifecycle.viewmodel.compose.viewModel
import com.esatgozcu.animationexamples.R
import com.esatgozcu.animationexamples.helper.ScreenSize
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime

@Composable
fun BlinkCircleView(viewModel: BlinkCircleVM = viewModel()) {

    @Composable
    fun scaleAnim(i: Int): Float {
        val itemTarget = remember { Animatable(viewModel.smallSize.toFloat()) }
        LaunchedEffect(key1 = viewModel.currentItem.value) {
            delay((viewModel.animTime * i).toLong())
            itemTarget.animateTo(
                targetValue = viewModel.normalSize.toFloat(),
                animationSpec = repeatable(
                    iterations = 1,
                    animation = tween(
                        durationMillis = viewModel.animTime / 2,
                        easing = LinearEasing
                    ),
                    repeatMode = RepeatMode.Restart,
                )
            )
            itemTarget.animateTo(
                targetValue = viewModel.smallSize.toFloat(),
                animationSpec = repeatable(
                    iterations = 1,
                    animation = tween(
                        durationMillis = viewModel.animTime / 2,
                        easing = LinearEasing
                    ),
                    repeatMode = RepeatMode.Restart,
                )
            )
            if (viewModel.currentItem.value - 1 == i) {
                viewModel.rowCount.value += 1
                viewModel.currentItem.value = viewModel.rowCount.value * viewModel.rowCount.value
            }
        }
        return itemTarget.value
    }

    Box(modifier = Modifier.fillMaxSize()) {
        (0 until viewModel.currentItem.value).forEachIndexed { i, _ ->
            Box(
                modifier = Modifier
                    .offset(
                        x = viewModel.calculateOffsetX(i).dp,
                        y = viewModel.calculateOffsetY(i).dp
                    )
                    .size(ScreenSize.screensWidth.div(viewModel.rowCount.value).dp)
                    .scale(
                        scaleAnim(i = i)
                    )
                    .clip(CircleShape)
                    .background(Color.Black.copy(scaleAnim(i = i)))
            )
        }
    }
}