package ru.acediat.feature_timetable

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

    private var days = getRUZDates(getCurrentDate())

    fun getCurrentDate() = Time.currentDate()

    fun setDates(date: LocalDateTime){
        days = getRUZDates(date)
    }

    fun getDatePositionInWeek(date : LocalDateTime) = date.dayOfWeek.value

    fun getTimetableFormatDate(dayNumber : Int) : String
            = "${getDayName(dayNumber)}\n${LocalDate.parse(days[dayNumber], RUZ).dayOfMonth}\n"

    fun getDate(position : Int) = days[position]

    private fun getDayName(dayNumber : Int) : String = dayNames[dayNumber]

    private fun getMonthName(monthNumber : Int) : String = monthNames[monthNumber]

    private fun getRUZDates(date: LocalDateTime) : ArrayList<String> = Time.getDates(
            Time.mondayDate(date),
            amountOfDays.toLong(), RUZ
        )
}