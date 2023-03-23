package com.esatgozcu.animationexamples.ui.animations.blinkcircle

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.esatgozcu.animationexamples.helper.ScreenSize

class BlinkCircleVM: ViewModel() {

    val smallSize = 0.2
    val normalSize = 1.0
    val animTime = 500
    val rowCount = mutableStateOf(2)
    val currentItem = mutableStateOf(rowCount.value*rowCount.value)

    fun calculateOffsetX(i: Int): Double {
        val onePiece = ScreenSize.screensWidth.div(rowCount.value)
        return onePiece * (i.rem(rowCount.value))
    }
    fun calculateOffsetY(i: Int): Double {
        val onePiece = ScreenSize.screensWidth.div(rowCount.value)
        val result = onePiece*(i.div(rowCount.value))
        val startPosition = ScreenSize.screenHeight.div(2) - ScreenSize.screensWidth.div(2)
        return result.plus(startPosition)
    }
}