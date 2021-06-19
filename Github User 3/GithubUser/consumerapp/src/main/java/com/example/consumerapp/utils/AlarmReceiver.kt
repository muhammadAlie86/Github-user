package com.example.consumerapp.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.consumerapp.R
import com.example.consumerapp.view.activity.SettingActivity


class AlarmReceiver : BroadcastReceiver() {

    companion object{
        const val ID_REPEATING = 101
        const val CHANNEL_ID = "Channel_1"
        const val CHANNEL_NAME = "AlarmManager channel"

    }
    override fun onReceive(context: Context, intent: Intent) {
        val title = context.resources.getString(R.string.app_name)
        val message = context.resources.getString(R.string.message)

        val notificationManagerCompat = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val mIntent = Intent(context, SettingActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        val pendingIntent  = PendingIntent.getActivity(context, ID_REPEATING,mIntent,PendingIntent.FLAG_UPDATE_CURRENT)

        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_alarm)
            .setContentTitle(title)
            .setContentText(message)
            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setSound(alarmSound)
            .setAutoCancel(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            channel.description = CHANNEL_NAME
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)
            builder.setChannelId(CHANNEL_ID)

            if (builder != null){
                notificationManagerCompat.createNotificationChannel(channel)
            }
        }
        val notification = builder.build()
        if (builder != null){
            notificationManagerCompat.notify(ID_REPEATING, notification)
        }
    }
}






