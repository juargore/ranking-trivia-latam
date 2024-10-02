package com.ranking.trivia.latam.presentation.utils

import android.content.Context
import android.media.MediaPlayer

fun playSound(context: Context, resId: Int) {
    val mediaPlayer = MediaPlayer.create(context, resId)
    mediaPlayer?.setOnPreparedListener { player ->
        player.isLooping = false
        player.start()
    }

    mediaPlayer?.setOnCompletionListener {
        it.release()
    }
}
