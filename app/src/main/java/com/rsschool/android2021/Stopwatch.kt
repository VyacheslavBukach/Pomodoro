package com.rsschool.android2021

data class Stopwatch(
    val id: Int,
    var currentMs: Long,
    var isStarted: Boolean,
    var time: Long = 0L,
    var isFinish: Boolean = false
)