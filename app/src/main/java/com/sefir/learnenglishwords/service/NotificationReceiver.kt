package com.sefir.learnenglishwords.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import com.sefir.learnenglishwords.R
import com.sefir.learnenglishwords.view.Home
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class NotificationReceiver : BroadcastReceiver() {


    private val CHANNEL_ID = "com.sefir.learnenglishwords"
    private val NOTIFICATION_ID = 123
    private val job = SupervisorJob()
    private val scope = CoroutineScope(job + Dispatchers.Main)


    override fun onReceive(context: Context?, intent: Intent?) {


        scope.launch {
            var type: String? = ""
            var uuid = 0
            intent.let {
                type = intent!!.getStringExtra("type")
                uuid = intent.getIntExtra("uuid", 0)
            }



            if (type == "yes" && uuid != 0) {
                var dao = WordDatabase(context!!).wordDao()
                dao.deleteOneElement(uuid)
                val notificationManager: NotificationManager = getSystemService(
                    context!!,
                    NotificationManager::class.java
                ) as NotificationManager

                notificationManager.cancel(NOTIFICATION_ID)
            } else if (type == "no") {
                val notificationManager: NotificationManager = getSystemService(
                    context!!,
                    NotificationManager::class.java
                ) as NotificationManager

                notificationManager.cancel(NOTIFICATION_ID)
            } else {
                try {
                    var dao = WordDatabase(context!!).wordDao()

                    var word = dao.getOneOwnWord()
                    word.let {

                        val memorizedIntent1 = Intent(context, NotificationReceiver::class.java)
                        memorizedIntent1.action = Intent.ACTION_CLOSE_SYSTEM_DIALOGS
                        memorizedIntent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        memorizedIntent1.putExtra("type", "yes")
                        memorizedIntent1.putExtra("uuid", word.uuid)

                        val memorizedPendingIntent1 = PendingIntent.getBroadcast(
                            context,
                            0,
                            memorizedIntent1,
                            PendingIntent.FLAG_CANCEL_CURRENT
                        )


                        val memorizedIntent2 = Intent(context, NotificationReceiver::class.java)
                        memorizedIntent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        memorizedIntent2.putExtra("type", "no")
                        val memorizedPendingIntent2 = PendingIntent.getBroadcast(
                            context,
                            1,
                            memorizedIntent2,
                            PendingIntent.FLAG_CANCEL_CURRENT
                        )

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            val name = "Notification Title"
                            val descriptionText = "Notification Description"
                            val importance = NotificationManager.IMPORTANCE_DEFAULT
                            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                                description = descriptionText
                            }
                            val notificationManager: NotificationManager = getSystemService(
                                context!!,
                                NotificationManager::class.java
                            ) as NotificationManager
                            notificationManager.createNotificationChannel(channel)
                        }

                        val intent = Intent(context, Home::class.java).apply {
                            var flags =
                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        }

                        val pendingIntent: PendingIntent =
                            PendingIntent.getActivity(context, 0, intent, 0)

                        val bitmapLargeIcon = BitmapFactory.decodeResource(
                            context?.resources,
                            R.drawable.ic_small_icon
                        )

                        var str =
                            "Name : " + word.wordName + "\nTranslation : " + word.translated + "\nMean : " + word.mean + "\nExample : " + word.example

                        val builder = NotificationCompat.Builder(context!!, CHANNEL_ID)
                            .setSmallIcon(R.drawable.ic_small_icon)
                            .setContentTitle("Word Reminder")
                            .setContentText(str)
                            .setStyle(NotificationCompat.BigTextStyle().bigText(str))
                            .setAutoCancel(true)
                            .setOngoing(false)
                            .setContentIntent(pendingIntent)
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .addAction(
                                R.drawable.ic_launcher_foreground,
                                "Memorized",
                                memorizedPendingIntent1
                            )
                            .addAction(
                                R.drawable.ic_launcher_foreground,
                                "Remind Later",
                                memorizedPendingIntent2
                            )

                        with(NotificationManagerCompat.from(context)) {
                            notify(NOTIFICATION_ID, builder.build())
                        }
                    }
                } catch (e: NullPointerException) {
                    Log.e("Brodcast", e.printStackTrace().toString())
                }
            }
        }
    }
}