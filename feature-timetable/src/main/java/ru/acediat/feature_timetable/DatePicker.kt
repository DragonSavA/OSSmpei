package ru.acediat.feature_timetable

import ru.acediat.core_utils.Time
import ru.acediat.feature_timetable.entities.Lesson
import java.time.LocalDate

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

    private val days = Time.getDates(
        Time.mondayDate(Time.currentDate()),
        amountOfDays.toLong(),
        Time.RUZ
    )

    fun getFormatDate(dayNumber : Int) : String
            = "${getDayName(dayNumber)}\n${(Time.RUZ.parse(days[dayNumber]) as LocalDate).dayOfMonth}"

    private fun getDayName(dayNumber : Int) : String = dayNames[dayNumber]

    private fun getMonthName(monthNumber : Int) : String = monthNames[monthNumber]
}