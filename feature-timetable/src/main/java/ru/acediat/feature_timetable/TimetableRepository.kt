package ru.acediat.feature_timetable

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.acediat.feature_timetable.dtos.GroupValidDTO
import ru.acediat.feature_timetable.dtos.LessonDTO
import javax.inject.Inject

class TimetableRepository @Inject constructor(
    private val api : TimetableApi
) {

    fun getGroupLessons(
        group : String,
        from : String, to : String
    ) : Observable<List<LessonDTO>> = api.getGroupLessons(group, from, to)
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())

    fun getUserGroup(userId: Int) = api.getUserGroup(userId)
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())

    fun isGroupValid(group: String) = api.isGroupValid(group)
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())
}