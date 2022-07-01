package ru.acediat.feature_timetable.entities

class ScheduledEvent(
    id : Int,
    startTime : String,
    endTime : String,
    dayOfWeek: Int,
    name : String,
) : Event(id, startTime, endTime, dayOfWeek, name)