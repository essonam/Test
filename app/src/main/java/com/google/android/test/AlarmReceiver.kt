package com.google.android.test


import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var notificationManager : NotificationManagerCompat

    @Inject
    lateinit var notificationBuilder: NotificationCompat.Builder

    @Inject
    lateinit var mainViewModel: MainViewModel

    override fun onReceive(context: Context?, intent: Intent?) {

        val id = intent?.getIntExtra("EXTRA_ID_NOTIFICATION",1)
        val title = intent?.getStringExtra("EXTRA_TITLE_NOTIFICATION") ?: ""
        val content = intent?.getStringExtra("EXTRA_CONTENT_NOTIFICATION") ?: "Il est l'heure de commencer votre activite"


        Log.d("id","$id")
        /*
        provideNotificationManager(context = context!!).notify(id!!,provideNotificationBuilder(context = context)
            .setContentTitle(title)
            .setContentText(content)
            .build())

         */
        notificationManager.notify(1,notificationBuilder
            .setContentTitle(title)
            .setContentText(content)
            .build())

        mainViewModel.onPurchaseClick()

    /*
        val scheduler = AlarmSchedulerImpl(context!!)

        //condition avant la programmatio de l'arame suivant qui est de savoir si on a atteint 31 jours on injecte le repository et on calcule la taille de la liste
        val alarmItem = AlarmItem(
            id = 12,
            time = LocalDateTime.now().plusSeconds(calculateStartTime(date = "2023-03-01 20:50:00", hour = 20, minutes = 50).toLong()),
            title = "2 eme test d'alarm",
            content = "2 eme test d'alarm avec succes",
        )
        alarmItem.let(scheduler::schedule)


     */
    }

    @SuppressLint("SimpleDateFormat")
    fun calculateStartTime(date: String, hour: Int, minutes: Int) : Int{
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val firstTime = sdf.parse(date)

        val calendar = Calendar.getInstance()
        val dueDate = Calendar.getInstance()
        val current = calendar.time


        return if (current.after(firstTime)){
            dueDate.set(Calendar.HOUR_OF_DAY, hour)
            dueDate.set(Calendar.MINUTE, minutes)
            dueDate.set(Calendar.SECOND, 0)

            dueDate.add(Calendar.HOUR_OF_DAY, 24)
            println("${ ((dueDate.timeInMillis - calendar.timeInMillis) / 1000).toInt()}")
            println("${ dueDate.time}")
            ((dueDate.timeInMillis - calendar.timeInMillis) / 1000).toInt()
        }else{
            Log.d("Time he","${((firstTime!!.time / 1000).minus(current.time/1000)).toInt().toLong()}")
            ((firstTime.time / 1000).minus(current.time/1000)).toInt()
        }
    }



}