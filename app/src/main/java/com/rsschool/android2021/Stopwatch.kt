package com.rsschool.android2021

data class Stopwatch(
    val id: Int,
    var currentMs: Long,
    var isStarted: Boolean
)