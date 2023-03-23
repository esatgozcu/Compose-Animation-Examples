package com.esatgozcu.animationexamples.ui.animations.circlerotation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.esatgozcu.animationexamples.helper.ScreenSize

class CircleRotationVM: ViewModel() {

    val itemSize = mutableStateOf(ScreenSize.screensWidth/2.0)
    var moveInOut = mutableStateOf(false)
    var itemCount = 3
    var degree =  (360.0).div(itemCount)
}