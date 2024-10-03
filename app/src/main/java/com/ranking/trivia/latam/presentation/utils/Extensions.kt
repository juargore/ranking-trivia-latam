package com.ranking.trivia.latam.presentation.utils

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.sidePadding(size: Dp = 16.dp) : Modifier {
    return padding(horizontal = size)
}
