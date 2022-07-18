package ru.acediat.feature_timetable

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.acediat.core_android.Logger
import ru.acediat.core_android.OSS_TAG
import ru.acediat.feature_timetable.di.TimetableComponent
import ru.acediat.feature_timetable.entities.Lesson
import javax.inject.Inject

class TimetableViewModel : ViewModel() {

    @Inject lateinit var repository : TimetableRepository

    private val timetable = MutableLiveData<Timetable>()
    private val error = MutableLiveData<Throwable>()

    private var currentGroup : String = ""

    init{
        TimetableComponent.init().inject(this)
    }

    fun setCurrentGroup(group : String) {
        currentGroup = group
    }

    fun setTimetableObserver(owner : LifecycleOwner, observer : (Timetable) -> Unit) =
        timetable.observe(owner, observer)

    fun setErrorObserver(owner : LifecycleOwner, observer : (Throwable) -> Unit) =
        error.observe(owner, observer)

    fun observeLessons(from : String, to : String) = repository.getGroupLessons(currentGroup, from, to)
        .subscribe({ list ->
            Logger.d(OSS_TAG, "lessons get: ${list.size}")
            val lessonList = ArrayList<Lesson>()
            list.forEach { lessonList.add(Lesson.buildFromDTO(it)) }
            timetable.postValue(Timetable.build(lessonList, 6))
        }, {
            error.postValue(it)
        })
}