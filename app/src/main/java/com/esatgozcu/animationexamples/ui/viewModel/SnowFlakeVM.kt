package com.esatgozcu.animationexamples.ui.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.esatgozcu.animationexamples.helper.ScreenSize

class SnowFlakeVM: ViewModel() {

    val itemSize = 20
    var action = mutableStateOf(false)
    //Lists
    var mRandomXList = List(itemSize)           { (-20..(ScreenSize.screensWidth)+20).random() }
    var mRandomSize = List(itemSize)            { (30..90).random() }
    var mRandomDurationMillis = List(itemSize)  { (2000..3000).random() }
    var mDelayMillis = List(itemSize)           { (0..3000).random() }
}