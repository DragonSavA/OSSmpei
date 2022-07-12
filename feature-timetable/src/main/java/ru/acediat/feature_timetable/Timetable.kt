package ru.acediat.feature_timetable

import ru.acediat.feature_timetable.entities.Lesson

class Timetable(
    private var lessonList : List<Lesson>,
    private val amountOfDays : Int
) {

    private val timetable = ArrayList<ArrayList<Lesson>>()

    fun getDayTimetable(dayNumber : Int) : ArrayList<Lesson> = timetable[dayNumber]

    fun appendDayTimetable(day : ArrayList<Lesson>) = timetable.addAll(listOf(day))

    fun isEmpty() : Boolean = lessonList.isEmpty()

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