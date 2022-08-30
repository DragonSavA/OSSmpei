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

    val indicatorId : Int by lazy {
        when(type){
            LECTURE -> R.drawable.shape_indicator_red
            PRACTICE -> R.drawable.shape_indicator_yellow
            LABORATORY -> R.drawable.shape_indicator_green
            else -> R.drawable.shape_indicator_blue
        }
    }

    companion object {

        const val LECTURE = "Лекция"
        const val PRACTICE = "Практическое занятие"
        const val LABORATORY = "Лабораторная работа"

        const val VACANT = "!Не определена !Вакансия "

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