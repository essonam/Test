package com.google.android.test

import java.time.LocalDateTime


data class AlarmItem(
    val id: Int,
    val time: LocalDateTime,
    val title: String,
    val content: String
)
