package com.sefir.learnenglishwords.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.IBinder


class NotificationService : Service() {

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {


        val intent = Intent(applicationContext, NotificationReceiver::class.java)
        val pendingIntent: PendingIntent =
            PendingIntent.getBroadcast(this, 123, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis(),
            1000 * 360 * 12,
            pendingIntent
        )

        return START_STICKY
    }

}