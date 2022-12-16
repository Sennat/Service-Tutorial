package com.project.servicetutorial.utils

import android.graphics.Color
import java.util.*

class UtilsClass {

    companion object {
        const val RADIO_URL = "https://s4.radio.co/s325d5f1a1/listen"
        const val CHANNEL_ID = "media_player_channel"
        const val NOTIFICATION_ID = 123
        fun getRandomColor(): Int {
            val rnd = Random()
            return Color.argb(
                rnd.nextInt(50),
                rnd.nextInt(256),
                rnd.nextInt(256),
                rnd.nextInt(256)
            )
        }
    }
}