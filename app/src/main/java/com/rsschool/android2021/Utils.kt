package com.rsschool.android2021

const val START_TIME = "00:00:00"
const val UNIT_TEN_MS = 1000L

const val INTERVAL = 1000L
const val PERIOD = 1000L * 10 // 10 sec

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