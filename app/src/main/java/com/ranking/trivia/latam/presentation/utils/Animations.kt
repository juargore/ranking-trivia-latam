package com.ranking.trivia.latam.presentation.utils

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale

@Composable
fun getSwingAnimation(
    angleOffset: Float,
    duration: Int
): Float {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val angle by infiniteTransition.animateFloat(
        initialValue = -angleOffset,
        targetValue = angleOffset,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = duration,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse,
        ),
        label = ""
    )
    return angle
}

@Composable
fun getSwingAnimation(
    angleOffset: Float,
    duration: Int,
    onFinished: () -> Unit = {}
): Float {
    val angle = remember { Animatable(0f) }

    // LaunchedEffect is used to launch a coroutine and perform the animation.
    LaunchedEffect(key1 = angleOffset) {
        angle.animateTo(
            targetValue = angleOffset,
            animationSpec = tween(
                durationMillis = duration / 2, // Half duration for the first half of the swing
                easing = FastOutSlowInEasing
            )
        )
        angle.animateTo(
            targetValue = -angleOffset,
            animationSpec = tween(
                durationMillis = duration / 2, // Second half of the swing
                easing = FastOutSlowInEasing
            )
        )
        // Trigger the callback when the animation is done
        onFinished()
    }

    return angle.value
}

@Suppress("InfiniteTransitionLabel", "InfinitePropertiesLabel")
@Composable
fun Pulsating(
    duration: Int = 1000,
    pulseFraction: Float = 1.2f,
    content: @Composable () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition()

    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = pulseFraction,
        animationSpec = infiniteRepeatable(
            animation = tween(duration),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(modifier = Modifier.scale(scale)) {
        content()
    }
}
