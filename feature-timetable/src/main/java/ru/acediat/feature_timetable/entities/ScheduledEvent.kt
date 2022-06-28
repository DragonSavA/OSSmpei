package ru.acediat.feature_timetable.entities

class ScheduledEvent(
    id : Int,
    time : String,
    dayOfWeek: Int,
    name : String,
) : Event(id, time, dayOfWeek, name) {
}