package ru.acediat.feature_timetable.entities

abstract class Event(
    var id : Int,
    val time : String,
    val dayOfWeek: Int,
    val name : String,
)