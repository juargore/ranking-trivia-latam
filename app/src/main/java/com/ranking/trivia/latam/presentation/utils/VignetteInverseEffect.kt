package com.ranking.trivia.latam.presentation.utils

import android.graphics.Shader
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb

@Composable
fun VignetteInverseEffect(modifier: Modifier = Modifier) {
    val centerColor = Color(0xFF000000) // black color
    val edgeColor = Color(0xFFFFFFFF) // white color

    Canvas(
        modifier = modifier.fillMaxSize() // full screen
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        val radius = kotlin.math.sqrt(canvasWidth * canvasWidth + canvasHeight * canvasHeight) / 2

        drawIntoCanvas { canvas ->
            val paint = android.graphics.Paint().apply {
                shader = android.graphics.RadialGradient(
                    canvasWidth / 2f,
                    canvasHeight / 2f,
                    radius,
                    intArrayOf(
                        centerColor.copy(alpha = 0.7f).toArgb(), // center color
                        edgeColor.copy(alpha = 0.2f).toArgb() // edge color
                    ),
                    floatArrayOf(0.0f, 1.0f), // transition between colors
                    Shader.TileMode.CLAMP // stop gradient on borders
                )
            }

            canvas.nativeCanvas.drawCircle(
                canvasWidth / 2f,
                canvasHeight / 2f,
                radius,
                paint
            )
        }
    }
}
