package ru.acediat.core_utils

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Time {

    val RUZ = DateTimeFormatter.ofPattern("yyyy.MM.dd")

    fun getDates(
        from : LocalDateTime,
        amountOfDays : Long
    ) : ArrayList<LocalDateTime> = ArrayList<LocalDateTime>().apply{
        for(date in 0 until amountOfDays)
            add(from.plusDays(date))
    }

    fun getDates(
        from: LocalDateTime,
        amountOfDays: Long,
        formatter: DateTimeFormatter
    ) : ArrayList<String> = ArrayList<String>().apply{
        getDates(from, amountOfDays).forEach { add(it.format(formatter)) }
    }

    fun getDates(
        from : String,
        amountOfDays: Long,
        formatter : DateTimeFormatter
    ) : ArrayList<String> = getDates(
            LocalDate.parse(from, formatter).atStartOfDay(),
            amountOfDays,
            formatter
        )

    fun currentDate() : LocalDateTime = LocalDateTime.now()

    fun currentDate(formatter : DateTimeFormatter) : String = currentDate().format(formatter)

    fun mondayDate(date : LocalDateTime) : LocalDateTime = with(date){
        var counter = 0L
        while(!isMonday(this.minusDays(counter)))
            counter++
        return this.minusDays(counter)
    }

    fun mondayDate(date : LocalDateTime, formatter : DateTimeFormatter) : String
        = mondayDate(date).format(formatter)

    fun nextMondayDate(date : LocalDateTime) : LocalDateTime = with(date){
        var counter = 1L
        while(!isMonday(this.plusDays(counter)))
            counter++
        return this.plusDays(counter)
    }

    fun nextMondayDate(date: LocalDateTime, formatter: DateTimeFormatter) : String
        = nextMondayDate(date).format(formatter)

    private fun isMonday(date : LocalDateTime) : Boolean = date.dayOfWeek.value == 1
}