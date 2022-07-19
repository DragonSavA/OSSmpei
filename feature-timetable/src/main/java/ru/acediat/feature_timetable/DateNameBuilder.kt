package ru.acediat.feature_timetable

import android.content.Context
import java.time.LocalDateTime

object DateNameBuilder {

    private val dayNames = arrayOf(
        R.string.monday, R.string.tuesday, R.string.wednesday, R.string.thursday,
        R.string.friday, R.string.saturday, R.string.sunday
    )

    private val ofMonthNames = arrayOf(
        R.string.of_january, R.string.of_february, R.string.of_march, R.string.of_april,
        R.string.of_may, R.string.of_june, R.string.of_jule, R.string.of_august,
        R.string.of_september, R.string.of_october, R.string.of_november, R.string.of_december
    )

    fun dayName(context : Context, date: LocalDateTime) =
        context.getString(dayNames[date.dayOfWeek.value-1])

    fun ofMonthName(context: Context, date: LocalDateTime) =
        context.getString(ofMonthNames[date.monthValue-1])
}