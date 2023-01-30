package com.stocard.core.util

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created By Rafiqul Hasan
 */

fun getHourlyInfoEpochTimeToDateTime(epochTime: Long): String {
    return try {
        if (epochTime == 0L)
            ""
        else {
            val simpleDateFormat = SimpleDateFormat("hh:mma\nMM/dd", Locale.getDefault())
            val epochDate = Date(epochTime * 1000)
            simpleDateFormat.format(epochDate)
        }
    } catch (e: Exception) {
        ""
    }
}

fun getDailyInfoEpochTimeToDateTime(epochTime: Long): String {
    return try {
        if (epochTime == 0L)
            ""
        else {
            val simpleDateFormat = SimpleDateFormat("EEE, MMM dd", Locale.getDefault())
            val epochDate = Date(epochTime * 1000)
            simpleDateFormat.format(epochDate)
        }
    } catch (e: Exception) {
        ""
    }
}