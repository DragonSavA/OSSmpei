package ru.acediat.feature_timetable

import ru.acediat.core_utils.Time
import ru.acediat.feature_timetable.entities.Lesson
import java.time.LocalDate

class Timetable(
    private var lessonList : List<Lesson>,
    private val amountOfDays : Int
) {

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

    private val timetable = ArrayList<ArrayList<Lesson>>()

    fun getDayTimetable(dayNumber : Int) : ArrayList<Lesson> = timetable[dayNumber]

    fun getTimetableFormatDates(dayNumber : Int) : String
            = "${getDayName(dayNumber)}\n${(Time.RUZ.parse(days[dayNumber]) as LocalDate).dayOfMonth}"

    fun appendDayTimetable(day : ArrayList<Lesson>) = timetable.addAll(listOf(day))

    private fun getDayName(dayNumber : Int) : String = dayNames[dayNumber]

    private fun getMonthName(monthNumber : Int) : String = monthNames[monthNumber]

    private fun addDayToTimetable(day : ArrayList<Lesson>){
        timetable.add(day.clone() as ArrayList<Lesson>)
        day.clear()
    }

    private fun getDayLessons(lessonList: List<Lesson>, day : Int) : ArrayList<Lesson> {
        val lessons = ArrayList<Lesson>()
        for(lesson in lessonList){
            if(lesson.dayOfWeek == day)
                lessons.add(lesson)
        }
        return lessons
    }

    private fun getDayNumbers(lessonList : List<Lesson>) : ArrayList<Int>{
        if(lessonList.isEmpty())
            return arrayListOf(0)

        val days = arrayListOf<Int>()
        for(lesson in lessonList){
            if(!days.contains(lesson.dayOfWeek))
                days.add(lesson.dayOfWeek)
        }

        return days
    }

    inner class Builder{

        fun build() : Timetable{
            val dayNumbers = getDayNumbers(lessonList)
            for(i in 1..amountOfDays){
                if(dayNumbers.contains(i)){
                    addDayToTimetable(getDayLessons(lessonList, i))
                }else{
                    addDayToTimetable(arrayListOf())
                }
            }
            return this@Timetable
        }

    }


}