package com.project.servicetutorial.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.widget.Toast
import com.project.servicetutorial.MainActivity
import com.project.servicetutorial.R
import com.project.servicetutorial.media.AppMediaPlayer
import com.project.servicetutorial.utils.UtilsClass.Companion.NOTIFICATION_ID
import com.project.servicetutorial.utils.UtilsClass.Companion.CHANNEL_ID
import com.project.servicetutorial.utils.UtilsClass.Companion.getRandomColor

class LocalAppService : Service() {

    // Binder given to clients
    private val binder = LocalBinder()
    private val mediaPlayer by lazy { AppMediaPlayer().createMediaPlayer(this) }

    /**
     * Class used for the client Binder. Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    inner class LocalBinder : Binder() {
        // Return this instance of LocalService so clients can call public methods
        fun getService(): LocalAppService = this@LocalAppService
    }

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }

    override fun onCreate() {
        super.onCreate()
        createNotification()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Toast.makeText(this, "Service started by user.", Toast.LENGTH_LONG).show()
        startPlayingMediaPlayer()
        showNotification()
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "Service destroyed by user.", Toast.LENGTH_LONG).show()
        stopPlayingMediaPlayer()
    }

    private fun startPlayingMediaPlayer() {
        if (!mediaPlayer.isPlaying) {
            mediaPlayer.start()
        }
    }

    private fun stopPlayingMediaPlayer() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
        }
        mediaPlayer.release()
    }

    private fun createNotification() {

        val notificationChannel = NotificationChannel(
            CHANNEL_ID,
            getString(R.string.media_notification),
            NotificationManager.IMPORTANCE_DEFAULT
        )

        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(notificationChannel)
    }

    private fun showNotification() {
        val pendingIntent = Intent(this, MainActivity::class.java).let {
            PendingIntent.getActivity(this, NOTIFICATION_ID, it, PendingIntent.FLAG_IMMUTABLE)
        }

        val notification: Notification = Notification.Builder(this, CHANNEL_ID)
            .setColor(getRandomColor())
            .setContentTitle(getString(R.string.media_notification))
            .setContentText(getString(R.string.mediaplayer_playing))
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentIntent(pendingIntent)
            .build()

        startForeground(NOTIFICATION_ID, notification)
    }
}