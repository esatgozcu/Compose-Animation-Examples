package com.esatgozcu.animationexamples.helper

import androidx.compose.ui.graphics.Color
import kotlin.random.Random

fun Color.Companion.random() : Color {
    val red = Random.nextInt(256)
    val green = Random.nextInt(256)
    val blue = Random.nextInt(256)
    return Color(red, green, blue)
}

val String.color
    get() = Color(android.graphics.Color.parseColor(this))
