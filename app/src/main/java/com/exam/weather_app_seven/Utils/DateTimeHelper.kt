package com.exam.weather_app_seven.Utils

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object DateTimeHelper {
    /**
     * Convert a time in milliseconds to a formatted date string like "Sep 24, 2025"
     *
     * @param millis  epoch time in milliseconds
     * @param locale  optional Locale, default is system Locale
     */
    fun toReadableDate(millis: Long?, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat("MMM dd, yyyy | hh:mm a", locale)
        return formatter.format(Date(millis!!))
    }

    fun toWeek(millis: Long?, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat("EEEE", locale)
        return formatter.format(Date(millis!!))
    }

    fun toReadableTime(millis: Long?, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat("hh:mm a", locale)
        return formatter.format(Date(millis!!))
    }


    @OptIn(ExperimentalMaterial3Api::class)
    fun to12HourFormat(timePickerState: TimePickerState): String {
        val hour = timePickerState.hour   // always 0–23
        val minute = timePickerState.minute
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
        }
        return SimpleDateFormat("hh:mm a", Locale.getDefault()).format(calendar.time)

    }
}
