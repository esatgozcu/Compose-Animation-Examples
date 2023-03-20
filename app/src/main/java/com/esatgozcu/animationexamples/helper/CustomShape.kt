package com.esatgozcu.animationexamples.helper

import androidx.compose.foundation.shape.GenericShape
import kotlin.math.tan

class CustomShape {
    object Shapes{
        val Triangle = GenericShape { size, _ ->

            moveTo(size.width / 2f, 0f)
            lineTo(size.width, size.height)
            lineTo(0f, size.height)
        }
        val Parallelogram = GenericShape { size, _ ->

            val radian = (90 - 60) * Math.PI / 180
            val xOnOpposite = (size.height * tan(radian)).toFloat()
            moveTo(0f, size.height)
            lineTo(x = xOnOpposite, y = 0f)
            lineTo(x = size.width, y = 0f)
            lineTo(x = size.width - xOnOpposite, y = size.height)
            lineTo(x = xOnOpposite, y = size.height)
        }
    }
}

