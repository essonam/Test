package com.google.android.test

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CompletedBoot : BroadcastReceiver() {

    @Inject
    lateinit var notificationManager : NotificationManagerCompat

    @Inject
    lateinit var notificationBuilder: NotificationCompat.Builder

    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent?.action == "android.intent.action.BOOT_COMPLETED"){

            Log.d("boot","redemmararge terminer")

            notificationManager.notify(1,notificationBuilder
                .setContentTitle("le redemarrage termine")
                .setContentText("nous avons reussi le redemarrage")
                .build())
        }
    }
}