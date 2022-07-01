package ru.acediat.feature_timetable.entities

import ru.acediat.core_network.DTO

abstract class Event(
    var id : Int,
    val startTime : String,
    val endTime : String,
    val dayOfWeek: Int,
    val name : String,
){
    val minutesFromMidnight : Int by lazy {
        var time = 0
        var i = 0
        while(startTime[i] != ':') {
            time += startTime[i].digitToInt() * 60 * (i + 1)
            i++
        }
        val minutes = StringBuilder("")
        for(j in i until startTime.length)
            minutes.append(startTime[i])
        time += minutes.toString().toInt()
        time
    }

    fun getFormatTime() : String = "$startTime - $endTime"

}