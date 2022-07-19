package ru.acediat.feature_timetable.entities

import ru.acediat.core_res.R
import ru.acediat.feature_timetable.dtos.LessonDTO

class Lesson(
    id : Int,
    startTime : String,
    endTime : String,
    dayOfWeek: Int,
    name : String,
    place : String,
    val type : String,
    val teacher : String,
) : Event(id, startTime, endTime, dayOfWeek, name, place){

    val colorId : Int by lazy {
        when(name){
            "Лекция" -> R.color.red_indicator
            "Практическое занятие" -> R.color.yellow_indicator
            "Лабораторная работа" -> R.color.green_indicator
            else -> R.color.blue_indicator
        }
    }

    companion object {

        @JvmStatic
        fun buildFromDTO(dto : LessonDTO) : Lesson = with(dto){
            return@with Lesson(
                lessonOId, beginLesson, endLesson,
                dayOfWeek, discipline, auditorium,
                kindOfWork, lecturer
            )
        }
    }

}