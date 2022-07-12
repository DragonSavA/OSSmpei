package ru.acediat.feature_timetable

import ru.acediat.core_android.Logger
import ru.acediat.core_android.OSS_TAG
import ru.acediat.core_utils.Time
import ru.acediat.core_utils.Time.RUZ
import java.time.LocalDate
import java.time.LocalDateTime

class DatePicker(val amountOfDays : Int) {

    private val dayNames = arrayOf(
        "ПН", "ВТ",
        "СР", "ЧТ",
        "ПТ", "СБ", "ВС"
    )

    private val monthNames = arrayOf(
        "янв", "фев", "мар", "апр",
        "май", "июн", "июл", "авг",
        "сен", "окт", "ноя", "дек"
    )

    private var days = getDates(Time.currentDate())

    fun setDates(date: LocalDateTime){
        days = getDates(date)
    }

    fun getFormatDate(dayNumber : Int) : String
            = "${getDayName(dayNumber)}\n${LocalDate.parse(days[dayNumber], RUZ).dayOfMonth}"

    private fun getDayName(dayNumber : Int) : String = dayNames[dayNumber]

    private fun getMonthName(monthNumber : Int) : String = monthNames[monthNumber]

    private fun getDates(date: LocalDateTime) : ArrayList<String> {
        Logger.d(OSS_TAG, Time.mondayDate(date).toString())
        return Time.getDates(
            Time.mondayDate(date),
            amountOfDays.toLong(), RUZ
        )
    }
}