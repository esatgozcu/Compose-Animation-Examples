package com.esatgozcu.animationexamples.ui.view

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.esatgozcu.animationexamples.ui.viewModel.CircleRotationVM

@Composable
fun CircleRotationView(viewModel: CircleRotationVM = viewModel()) {

    @Composable
    fun infiniteRepeatable(): InfiniteRepeatableSpec<Float>{
        return infiniteRepeatable(
            animation = tween(
                durationMillis = 2000,
                easing = LinearOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .scale(
                    animateFloatAsState(
                        targetValue =
                        if (viewModel.moveInOut.value)
                            1.0.toFloat()
                        else
                            (1.0 / 4.0).toFloat(),
                        animationSpec = infiniteRepeatable()
                    ).value
                )
                .rotate(
                    animateFloatAsState(
                        targetValue =
                        if (viewModel.moveInOut.value)
                            180.toFloat()
                        else
                            0.toFloat(),
                        animationSpec = infiniteRepeatable()
                    ).value
                ),
        ) {
            (0 until viewModel.itemCount).forEachIndexed { i, _ ->
                Box(
                    modifier = Modifier
                        .rotate(
                            degrees = animateFloatAsState(
                                targetValue =
                                if (viewModel.moveInOut.value)
                                    (viewModel.degree * i).toFloat()
                                else
                                    0.toFloat(),
                                animationSpec = infiniteRepeatable()
                            ).value
                        )
                ) {
                    Box(
                        modifier = Modifier
                            .size(viewModel.itemSize.value.dp)
                            .offset(
                                y = animateFloatAsState(
                                    targetValue =
                                    if (viewModel.moveInOut.value)
                                        -viewModel.itemSize.value.div(2).toFloat()
                                    else
                                        0.toFloat(),
                                    animationSpec = infiniteRepeatable()
                                ).value.dp
                            )
                            .clip(CircleShape)
                            .alpha(0.5f)
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Red,
                                        Color.White
                                    )
                                ), alpha = 0.5f
                            )
                    )
                    Box(
                        modifier = Modifier
                            .size(viewModel.itemSize.value.dp)
                            .offset(
                                y = animateFloatAsState(
                                    targetValue =
                                    if (viewModel.moveInOut.value)
                                        +viewModel.itemSize.value.div(2).toFloat()
                                    else
                                        0.toFloat(),
                                    animationSpec = infiniteRepeatable()
                                ).value.dp
                            )
                            .clip(CircleShape)
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color.White,
                                        Color.Blue
                                    )
                                ), alpha = 0.5f
                            )
                    )
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.moveInOut.value = true
    }
}