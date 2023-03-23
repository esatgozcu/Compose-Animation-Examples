package com.esatgozcu.animationexamples.ui.animations.fireworkcenter

import androidx.compose.ui.graphics.*
import androidx.lifecycle.ViewModel
import com.esatgozcu.animationexamples.helper.color
import com.esatgozcu.animationexamples.ui.animations.confetticenter.SHAPES

/// - Parameters:
///  - pieceCount: amount of confetti
///  - colors: list of colors that is applied to the default shapes
///  - pieceSize: size that confetti and emojis are scaled to
///  - radius: explosion radius
///  - repetitions: number of repetitions of the explosion
///  - repetitionInterval: duration between the repetitions
class FireworkCenterVM(
    var pieceCount: Int = 20,
    var pieceType: List<FireworkTypes> = listOf(
        FireworkTypes.Shape(SHAPES.CIRCLE),
    ),
    var colors: List<Color> = listOf(
        "#f88f22".color,
        "#9c1d08".color,
        "#ce7117".color,
        "#f24d24".color,
        "#113bc6".color,
        "#c54a85".color,
        "#92af96".color,
        "#d23508".color,
    ),
    var pieceSize: Float = 5.0f,
    var radius: Float = 100f,
    var repetitions: Int = 0,
    var repetitionInterval: Double = 1000.0,
    var explosionAnimDuration: Double = 1200.0,
    var launchAnimDuration: Double = 3000.0,
): ViewModel() {
    fun getAnimDuration(): Double {
        return explosionAnimDuration + launchAnimDuration
    }
}

sealed interface FireworkTypes {
    data class Image(val value: Int) : FireworkTypes
    data class Text(val value: String) : FireworkTypes
    data class Shape(val value: SHAPES) : FireworkTypes
}