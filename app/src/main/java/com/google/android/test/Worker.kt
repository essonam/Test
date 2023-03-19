package com.google.android.test

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.android.test.util.Constant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime

class Worker(private val context: Context, workerParameters: WorkerParameters) : CoroutineWorker(context,workerParameters) {

    override suspend fun doWork(): Result  {

            val scheduler = AlarmSchedulerImpl(applicationContext)


            val alarmItem = AlarmItem(
                id = 12,
                time = LocalDateTime.now().plusSeconds("10".toLong()),
                title = "je suis votre alaram",
                content = "Votre activite commence dans 15min",
            )
            alarmItem.let(scheduler::schedule)

        return  Result.success()

    }


    private fun showNotification(context: Context){
        provideNotificationManager(context = context).notify(1,provideNotificationBuilder(context = context)
            .setContentTitle("Test")
            .setContentText("on fait un test")
            .build())
    }

    private fun  provideNotificationBuilder(context: Context) : NotificationCompat.Builder{
        val flag = PendingIntent.FLAG_IMMUTABLE

        val clickIntent = Intent(
            Intent.ACTION_VIEW,
            Constant.MY_URI.toUri(),
            context,
            MainActivity::class.java
        )
        val clickPendingIntent: PendingIntent = android.app.TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(clickIntent)
            getPendingIntent(1, flag)
        }
        return NotificationCompat.Builder(context, Constant.CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setContentIntent(clickPendingIntent)
    }


    private fun  provideNotificationManager(context: Context) : NotificationManagerCompat {
        val notificationManager = NotificationManagerCompat.from(context)
        val channel = NotificationChannel(
            Constant.CHANNEL_ID,
            Constant.CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(channel)
        return notificationManager
    }

}