package com.esatgozcu.animationexamples.ui.animations.fireworkcenter

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.random.Random
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import com.esatgozcu.animationexamples.helper.random
import com.esatgozcu.animationexamples.ui.animations.confetticenter.ConfettiTypes

@Composable
fun FireworkCenterView(viewModel: FireworkCenterVM = viewModel()) {

    val counter = remember { mutableStateOf(0) }

    Box(
        modifier = Modifier.fillMaxSize().background(Color.Black),
        contentAlignment = Alignment.BottomCenter
    ) {
        Button(
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
            onClick = {
                counter.value += 1
            }) {
            Text(text = "\uD83E\uDDE8")
        }
        viewModel.repetitions = 100
        viewModel.repetitionInterval = 500.0
        FireworkView(counter, viewModel)
    }
}

@Composable
fun FireworkView(currentValue: MutableState<Int>, viewModel: FireworkCenterVM) {

    val animated = remember { mutableStateOf(0) }
    val firstAppear = remember { mutableStateOf(false) }

    val repeatInterval = viewModel.repetitionInterval
    val repeatCount = viewModel.repetitions

    Box {
        for (i in 0 until animated.value) {
            FireworkContainer(viewModel)
        }
    }

    LaunchedEffect(currentValue.value) {
        if (firstAppear.value) {
            for (i in 0..repeatCount) {
                launch {
                    delay((repeatInterval * i).toLong())
                    animated.value += 1
                }
            }
        }
        firstAppear.value = true
    }
}

@Composable
fun FireworkContainer(viewModel: FireworkCenterVM) {

    val scope = rememberCoroutineScope()
    val piecesNumber = remember { mutableStateOf(viewModel.pieceCount) }
    val animateY = remember { Animatable(0.0f) }
    val animateX = remember { Animatable(0.0f) }
    val randomX = remember { (-100..100).random() }
    val randomY = remember { (150..650).random() }

    Box(
        modifier = Modifier
            .offset(
                x = animateX.value.dp,
                y = animateY.value.dp
            )
    ) {
        for (i in 0 until piecesNumber.value) {
            FireworkFrame(viewModel, i, launchHeight = randomY.toFloat())
        }
    }

    LaunchedEffect(Unit) {
        scope.launch {
            animateY.animateTo(
                targetValue = -randomY.toFloat(),
                animationSpec = repeatable(
                    iterations = 1,
                    animation = tween(
                        durationMillis = viewModel.launchAnimDuration.toInt(),
                        easing = CubicBezierEasing(0.075f, 0.690f, 0.330f, 0.870f)
                    )
                )
            )
        }
        scope.launch {
            animateX.animateTo(
                targetValue = randomX.toFloat(),
                animationSpec = repeatable(
                    iterations = 1,
                    animation = tween(
                        durationMillis = viewModel.launchAnimDuration.toInt(),
                        easing = CubicBezierEasing(0.075f, 0.690f, 0.330f, 0.870f)
                    )
                )
            )
        }
        delay(viewModel.getAnimDuration().toLong())
        //Clear animated
        piecesNumber.value = 0
    }
}

@Composable
fun FireworkFrame(viewModel: FireworkCenterVM, i: Int, launchHeight: Float) {

    val scope = rememberCoroutineScope()

    val type = remember { viewModel.pieceType.random() }
    val color = remember { viewModel.colors.random() }

    val animateY = remember { Animatable(0.0f) }
    val animateX = remember { Animatable(0.0f) }
    val opacity = remember { Animatable(1.0f) }
    val strokeAnimate = remember { Animatable(2.0f) }

    val radius = viewModel.radius + (launchHeight / 10)

    fun getRandomExplosionTimeVariation(): Float {
        return ((0..999).random().toFloat() * 0.0005).toFloat()
    }

    fun getAnimationDuration(): Double {
        return viewModel.explosionAnimDuration + getRandomExplosionTimeVariation()
    }

    fun getRandomAngle(): Float {
        return (360 / viewModel.pieceCount * (i)).toFloat()
    }

    fun getDistance(): Float {
        return radius
    }

    fun deg2rad(number: Float): Float {
        return (number * PI / 180).toFloat()
    }

    val randomAngle = remember {
        getRandomAngle()
    }

    val path = remember {
        Path()
    }

    Box(modifier = Modifier, contentAlignment = Alignment.Center){
        Box(
            modifier = Modifier
                .offset(
                    x = animateX.value.dp,
                    y = animateY.value.dp
                ),
        ) {
            FireworkItem(
                color = color,
                alpha = opacity.value,
                size = viewModel.pieceSize,
                type = type,
                viewModel = viewModel
            )
        }
        Canvas(modifier = Modifier) {
            path.lineTo((animateX.value*2.6).toFloat(), (animateY.value*2.6).toFloat())
            drawPath(
                color = color,
                path = path,
                style = Stroke(
                    width = strokeAnimate.value.dp.toPx(),
                    cap = StrokeCap.Round,
                    join = StrokeJoin.Round
                    ),
                alpha = opacity.value
            )
        }
    }

    LaunchedEffect(Unit) {
        delay(viewModel.launchAnimDuration.toLong())
        scope.launch {
            animateX.animateTo(
                targetValue = getDistance() * cos(deg2rad(randomAngle)),
                animationSpec = repeatable(
                    iterations = 1,
                    animation = tween(
                        durationMillis = getAnimationDuration().toInt(),
                        easing =
                        LinearEasing
                    )
                )
            )
        }
        scope.launch {
            opacity.animateTo(
                targetValue = 0.0f,
                animationSpec = repeatable(
                    iterations = 1,
                    animation = tween(
                        durationMillis = getAnimationDuration().toInt(),
                        easing = CubicBezierEasing(0.8f, 0.2f, 1.0f, 1.0f)
                    )
                )
            )
        }
        scope.launch {
            animateY.animateTo(
                targetValue = -getDistance() * sin(deg2rad(randomAngle)),
                animationSpec = repeatable(
                    iterations = 1,
                    animation = tween(
                        durationMillis = getAnimationDuration().toInt(),
                        easing = if (randomAngle < 180)
                            CubicBezierEasing(0.0f, 1.0f, 1.0f, 1.0f)
                        else
                            CubicBezierEasing(1f-((650-launchHeight)/1000), (650-launchHeight)/1000, 1.0f, 1.0f)
                    )
                )
            )
        }
        scope.launch {
            strokeAnimate.animateTo(
                targetValue = 1.0f,
                animationSpec = repeatable(
                    iterations = 1,
                    animation = tween(
                        durationMillis = getAnimationDuration().toInt(),
                        easing =  LinearEasing
                    )
                )
            )
        }
    }
}

@Composable
fun FireworkItem(
    alpha: Float,
    color: Color,
    size: Float,
    type: FireworkTypes,
    viewModel: FireworkCenterVM
) {
    val scope = rememberCoroutineScope()
    val animateSize = remember { Animatable(size) }

    when (type) {
        is FireworkTypes.Shape ->
            Box(
                modifier = Modifier
                    .size(animateSize.value.dp)
                    .alpha(alpha)
                    .clip(type.value.shape)
                    .background(color)
            )
        is FireworkTypes.Image ->
            Image(
                painter = painterResource(id = type.value), "",
                modifier = Modifier
                    .size(animateSize.value.dp)
                    .alpha(alpha)
            )
        is FireworkTypes.Text ->
            Text(
                text = type.value,
                color = color,
                modifier = Modifier
                    .alpha(alpha),
                fontSize = animateSize.value.sp,
            )
    }

    LaunchedEffect(Unit) {
        scope.launch {
            animateSize.animateTo(
                targetValue = 0.0f,
                animationSpec = repeatable(
                    iterations = 1,
                    animation = tween(
                        durationMillis = viewModel.explosionAnimDuration.toInt(),
                        delayMillis = viewModel.launchAnimDuration.toInt(),
                        easing = LinearEasing
                    )
                )
            )
        }
    }
}
