package com.project.servicetutorial.media

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.util.Log
import com.project.servicetutorial.R
import com.project.servicetutorial.utils.UtilsClass.Companion.RADIO_URL

class AppMediaPlayer {
    private val mediaPlayer by lazy { MediaPlayer() }

    fun createMediaPlayer(context: Context): MediaPlayer {
        try {
            mediaPlayer.setDataSource(RADIO_URL)
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
            mediaPlayer.setVolume(0.10f, 0.10f)
            mediaPlayer.prepare()
        } catch (e: Exception) {
            Log.d(context.getString(R.string.exception), "Exception: -> ${e.toString()}")
            mediaPlayer.release()
        }

        return mediaPlayer
    }

}