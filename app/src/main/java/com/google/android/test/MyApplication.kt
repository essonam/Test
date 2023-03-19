package com.google.android.test

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.work.*
import dagger.hilt.android.HiltAndroidApp
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*
import java.util.concurrent.TimeUnit

@HiltAndroidApp
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        //periodicWork()
        //oneRequest()

        val scheduler = AlarmSchedulerImpl(applicationContext)


        val alarmItem = AlarmItem(
            id = 12,
            time = LocalDateTime.now().plusSeconds(calculateStartTime(date = "2023-03-02 9:00:00", hour = 9, minutes = 0).toLong()),
            title = "Premier test d'alarm",
            content = "Premier test d'alarm avec succes",
        )
        //alarmItem.let(scheduler::schedule)
        Log.d("application","il est dans l'application")


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
            ((dueDate.timeInMillis - calendar.timeInMillis) / 1000).toInt()
        }else{
            Log.d("Time he","${((firstTime!!.time / 1000).minus(current.time/1000)).toInt().toLong()}")
            ((firstTime.time / 1000).minus(current.time/1000)).toInt()
        }
    }


    private fun oneRequest(){
        val request = OneTimeWorkRequestBuilder<Worker>()
            .setInitialDelay(1,TimeUnit.MINUTES)
            .build()

        WorkManager.getInstance(applicationContext).enqueue(request)
    }
    private fun periodicWork(){

        val request = PeriodicWorkRequestBuilder<Worker>(
            15,
            TimeUnit.MINUTES,
            5,
            TimeUnit.MINUTES
        ).build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork("app",ExistingPeriodicWorkPolicy.KEEP,request)
    }

}