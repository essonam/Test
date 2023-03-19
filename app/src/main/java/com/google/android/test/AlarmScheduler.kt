package com.google.android.test



interface AlarmScheduler {
    fun schedule(item : AlarmItem)
    fun cancel(item: AlarmItem)
}