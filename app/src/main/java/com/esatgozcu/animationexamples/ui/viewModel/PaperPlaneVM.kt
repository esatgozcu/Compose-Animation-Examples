package com.esatgozcu.animationexamples.ui.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.esatgozcu.animationexamples.helper.ScreenSize

class PaperPlaneVM: ViewModel() {

    val itemSize = 20
    var action = mutableStateOf(false)
    //Lists
    var mRandomYList = List(itemSize)           { (100..(ScreenSize.screenHeight)).random() }
    var mRandomSize = List(itemSize)            { (20..100).random() }
    var mRandomDurationMillis = List(itemSize)  { (400..1200).random() }
    var mDelayMillis = List(itemSize)           { (0..1000).random() }
}