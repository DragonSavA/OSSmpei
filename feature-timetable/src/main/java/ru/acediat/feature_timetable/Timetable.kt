package ru.acediat.feature_timetable

import ru.acediat.feature_timetable.entities.Lesson

class Timetable{

    private val timetable = ArrayList<ArrayList<Lesson>>()

    fun getDayTimetable(dayNumber : Int) : ArrayList<Lesson> = timetable[dayNumber]

    fun appendDayTimetable(day : ArrayList<Lesson>) = timetable.addAll(listOf(day))

    fun isEmpty() : Boolean = timetable.isEmpty()

    operator fun get(index : Int) = timetable[index]

    operator fun set(index : Int, dayTimetable : ArrayList<Lesson>) {
        timetable[index] = dayTimetable
    }

    companion object Builder{

        fun build(
            lessonList : List<Lesson>,
            amountOfDays : Int
        ) : Timetable = Timetable().apply{
            val dayNumbers = getDayNumbers(lessonList)
            for(i in 1..amountOfDays){
                if(dayNumbers.contains(i)){
                    addDayToTimetable(getDayLessons(lessonList, i), this.timetable)
                }else{
                    addDayToTimetable(arrayListOf(), this.timetable)
                }
            }
            return this
        }

        private fun addDayToTimetable(day : ArrayList<Lesson>, timetable : ArrayList<ArrayList<Lesson>>){
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

    }

}