package com.github.tarcv.orderme.app

import androidx.test.espresso.idling.CountingIdlingResource
import java.text.SimpleDateFormat
import java.util.Calendar

object Utils {

    val countingIdlingResource = CountingIdlingResource("CountingIdlingResource")

    fun getDate(date: Long): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        return dateFormat.format(date)
    }

    fun getTime(time: Long): String {
        val timeFormat = SimpleDateFormat("HH:mm:ss.SSS")
        return timeFormat.format(time)
    }

    fun getFullDate(date: Long): String {
        return getDate(date) + "T" + getTime(date) + "+0000"
    }

    fun laterThanNow(d: String): Boolean {
        var cur_date = d.replaceFirst("T", " ", false)

        cur_date = cur_date.replaceFirst("Z", "", false)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
//        Timber.i(cur_date)
        val date = dateFormat.parse(cur_date)
        var today = Calendar.getInstance().time
        return date.after(today)
    }

    fun getDateFromFullDate(date: String): String {
        return date.substring(0, 10)
    }

    fun getTimeFromFullDate(date: String): String {
        return date.substring(11, 16)
    }
}