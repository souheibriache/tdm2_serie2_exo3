package com.example.exo3

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class AdhanService: Service() {

    var mMediaPlayer: MediaPlayer? = null
    var mAudioManager: AudioManager? = null
    private val channelId = "channel-01"
    private val channelName = "SIL Channel"
    @RequiresApi(Build.VERSION_CODES.N)
    private val importance = NotificationManager.IMPORTANCE_HIGH

    @SuppressLint("NewApi")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {





         return START_STICKY
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()

        var date =  LocalDateTime.now()
        var time: String
        var adan = listOf("06:12","12:44","16:32","19:43","21:16")

            mAudioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
            mMediaPlayer = MediaPlayer.create(this, R.raw.adhan)

             time = date.format(DateTimeFormatter.ofPattern("HH:mm"))
            while (!adan.contains(time)) {
                time = date.format(DateTimeFormatter.ofPattern("HH:mm"))

            }
        createNotification()
        mMediaPlayer!!.start()
        mMediaPlayer!!.setOnCompletionListener { mMediaPlayer?.release() }


    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onDestroy() {
        super.onDestroy()


        mMediaPlayer?.release()

    }


    @SuppressLint("NewApi")
    @RequiresApi(Build.VERSION_CODES.M)
    fun createNotification() {
        // notification principale

        val testIntent = Intent(this, MainActivity::class.java)
        val pTestIntent = PendingIntent.getActivity(this, System.currentTimeMillis().toInt(), testIntent, 0)
        val notifIntent1 = Intent(this, MainActivity::class.java)
        notifIntent1.putExtra("data", "detail")
        val pNotifIntent1 = PendingIntent.getActivity(this, System.currentTimeMillis().toInt(), notifIntent1, 0)


        // Icones
        val icon1 = Icon.createWithResource(this, android.R.drawable.btn_minus)


        val action1 = Notification.Action.Builder(icon1, "detail", pNotifIntent1).build()





        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val mChannel = NotificationChannel(
                channelId, channelName, importance)
            notificationManager.createNotificationChannel(mChannel)
        }

        val noti = Notification.Builder(this, channelId)
            .setContentTitle("Nouvelle Notification")
            .setContentText("Je viens de recevioir une notification !")
            .setSmallIcon(android.R.drawable.btn_dialog)
            .setContentIntent(pTestIntent)
            .addAction(action1)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(0, noti)
    }

    override fun onBind(intent: Intent): IBinder? {
    return null
    }






}
