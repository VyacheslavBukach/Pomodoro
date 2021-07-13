package com.rsschool.android2021

const val START_TIME = "00:00:00"
const val INTERVAL = 1_000L
const val PERIOD = 1_000L * 10 // 10 sec
const val MINUTE_IN_MILLIS = 60_000L

fun Long.displayTime(): String {
    if (this <= 0L) {
        return START_TIME
    }
    val h = this / 1000 / 3600
    val m = this / 1000 % 3600 / 60
    val s = this / 1000 % 60

    return "${displaySlot(h)}:${displaySlot(m)}:${displaySlot(s)}"
}

fun displaySlot(count: Long): String {
    return if (count / 10L > 0) {
        "$count"
    } else {
        "0$count"
    }
}