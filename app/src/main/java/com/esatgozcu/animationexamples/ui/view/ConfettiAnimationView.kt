package com.esatgozcu.animationexamples.ui.view

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.esatgozcu.animationexamples.R
import com.esatgozcu.animationexamples.ui.viewModel.ConfettiAnimationVM
import com.esatgozcu.animationexamples.ui.viewModel.ConfettiTypes
import com.esatgozcu.animationexamples.ui.viewModel.SHAPES
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.random.Random

@Composable
fun ConfettiAnimationView(viewModel: ConfettiAnimationVM = viewModel()) {

    val counter = remember { mutableStateOf(0) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
            onClick = {
                counter.value += 1
            }) {
            Text(text = "\uD83C\uDF89")
        }
        //viewModel.confettiNumber = 50
        /*viewModel.confettiTypes = listOf(ConfettiTypes.Text("Text"),
            ConfettiTypes.Image(R.drawable.ic_snow_flake),
            ConfettiTypes.Shape(SHAPES.PARALLELOGRAM))*/
        //viewModel.openingAngle = 0.0
        //viewModel.closingAngle = 360.0
        ConfettiView(counter, viewModel)
    }
}

@Composable
fun ConfettiView(currentValue: MutableState<Int>, viewModel: ConfettiAnimationVM) {

    val animated = remember { mutableStateOf(0) }
    val firstAppear = remember { mutableStateOf(false) }

    val repeatInterval = viewModel.repetitionInterval
    val repeatCount = viewModel.repetitions

    Box {
        for (i in 0 until animated.value) {
            ConfettiContainer(viewModel)
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
fun ConfettiContainer(viewModel: ConfettiAnimationVM) {

    val confettiNumber = remember { mutableStateOf(viewModel.confettiNumber) }
    val animDuration = viewModel.getAnimDuration()

    Box {
        for (i in 0 until confettiNumber.value) {
            ConfettiFrame(viewModel)
        }
    }

    LaunchedEffect(Unit) {
        delay(animDuration.toLong())
        //Clear animated
        confettiNumber.value = 0
    }
}

@Composable
fun ConfettiFrame(viewModel: ConfettiAnimationVM) {

    val type = remember { viewModel.confettiTypes.random() }
    val color = remember { viewModel.colors.random() }

    val animateY = remember { Animatable(0.0f) }
    val animateX = remember { Animatable(0.0f) }
    val opacity = remember { Animatable(0.0f) }

    val scope = rememberCoroutineScope()
    val openAngle = viewModel.openingAngle
    val closeAngle = viewModel.closingAngle
    val explosionAnimationDuration = viewModel.explosionAnimDuration
    val dropAnimationDuration = viewModel.dropAnimationDuration
    val radius = viewModel.radius
    val dropHeight = viewModel.dropHeight

    fun getDelayBeforeDropAnimation(): Double {
        return explosionAnimationDuration * 0.0001
    }
    fun getRandomExplosionTimeVariation(): Float {
        return ((0..999).random().toFloat() * 0.0005).toFloat()
    }
    fun getAnimationDuration(): Double {
        return explosionAnimationDuration + getRandomExplosionTimeVariation()
    }
    fun getRandomAngle(): Float {
        return if (closeAngle == openAngle) {
            closeAngle.toFloat()
        } else if (closeAngle > openAngle) {
            Random.nextDouble(openAngle, closeAngle).toFloat()
        } else {
            Random.nextDouble(openAngle, closeAngle + 360).rem(360.0).toFloat()
        }
    }
    fun getDistance(): Float {
        return (Random.nextDouble(0.01, 1.0).pow(0.25) * radius).toFloat()
    }
    fun deg2rad(number: Float): Float {
        return (number * PI / 180.0).toFloat()
    }

    Box(
        modifier = Modifier
            .offset(
                x = animateX.value.dp,
                y = animateY.value.dp
            ),
    ) {
        ConfettiItem(color = color,
            type = type,
            alpha = opacity.value,
            size = viewModel.confettiSize)
    }

    LaunchedEffect(Unit) {
        scope.launch {
            animateX.animateTo(
                targetValue = getDistance() * cos(deg2rad(getRandomAngle())),
                animationSpec = repeatable(
                    iterations = 1,
                    animation = tween(
                        durationMillis = getAnimationDuration().toInt(),
                        easing = CubicBezierEasing(0.1f, 1.0f, 0.0f, 1.0f)
                    )
                )
            )
        }
        scope.launch {
            opacity.animateTo(
                targetValue = viewModel.opacity.toFloat(),
                animationSpec = repeatable(
                    iterations = 1,
                    animation = tween(
                        durationMillis = getAnimationDuration().toInt(),
                        easing = CubicBezierEasing(0.1f, 1.0f, 0.0f, 1.0f)
                    )
                )
            )
            opacity.animateTo(
                targetValue = if (viewModel.fadesOut) 0f else viewModel.opacity.toFloat(),
                animationSpec = repeatable(
                    iterations = 1,
                    animation = tween(
                        durationMillis = dropAnimationDuration.toInt(),
                        delayMillis = getDelayBeforeDropAnimation().toInt(),
                        easing = LinearEasing
                    )
                )
            )
        }
        scope.launch {
            animateY.animateTo(
                targetValue = -getDistance() * sin(deg2rad(getRandomAngle())),
                animationSpec = repeatable(
                    iterations = 1,
                    animation = tween(
                        durationMillis = getAnimationDuration().toInt(),
                        easing = CubicBezierEasing(0.1f, 1.0f, 0.0f, 1.0f)
                    )
                )
            )
            animateY.animateTo(
                targetValue = dropHeight,
                animationSpec = repeatable(
                    iterations = 1,
                    animation = tween(
                        durationMillis = dropAnimationDuration.toInt(),
                        delayMillis = getDelayBeforeDropAnimation().toInt(),
                        easing = CubicBezierEasing(0.12f, 0f, 0.39f, 0f)
                    )
                )
            )
        }
    }
}

@Composable
fun ConfettiItem(alpha: Float, color: Color, type: ConfettiTypes, size: Float) {

    val infiniteTransition = rememberInfiniteTransition()
    val randomAnchorX = remember { (0..1).random() }
    val randomAnchorY = remember { (0..1).random() }
    val spinDirX = remember { (0..1).random() * 2 - 1 }
    val spinDirZ = remember { (0..1).random() * 2 - 1 }
    val speed = remember { (500..2000).random() }

    val angle by infiniteTransition.animateFloat(
        initialValue = 0F,
        targetValue = 360F,
        animationSpec = infiniteRepeatable(
            animation = tween(speed, easing = LinearEasing)
        )
    )

    when (type) {
        is ConfettiTypes.Shape ->
            Box(
                modifier = Modifier
                    .graphicsLayer(
                        rotationX = spinDirX * angle,
                    )
                    .graphicsLayer(
                        transformOrigin = TransformOrigin(
                            pivotFractionX = randomAnchorX.toFloat(),
                            pivotFractionY = randomAnchorY.toFloat(),
                        ),
                        rotationZ = spinDirZ * angle,
                    )
                    .size(size.dp)
                    .alpha(alpha)
                    .clip(type.value.shape)
                    .background(color)
            )
        is ConfettiTypes.Image ->
            Image(
                painter = painterResource(id = type.value), "",
                modifier = Modifier
                    .graphicsLayer(
                        rotationX = spinDirX * angle,
                    )
                    .graphicsLayer(
                        transformOrigin = TransformOrigin(
                            pivotFractionX = randomAnchorX.toFloat(),
                            pivotFractionY = randomAnchorY.toFloat(),
                        ),
                        rotationZ = spinDirZ * angle,
                    )
                    .size(size.dp)
                    .alpha(alpha)
            )
        is ConfettiTypes.Text ->
            Text(
                text = type.value,
                color = color,
                modifier = Modifier
                    .graphicsLayer(
                        rotationX = spinDirX * angle,
                    )
                    .graphicsLayer(
                        transformOrigin = TransformOrigin(
                            pivotFractionX = randomAnchorX.toFloat(),
                            pivotFractionY = randomAnchorY.toFloat(),
                        ),
                        rotationZ = spinDirZ * angle,
                    )
                    .alpha(alpha),
                fontSize = size.sp,
            )
    }
}
