package ru.acediat.feature_timetable

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.disposables.Disposable
import ru.acediat.core_android.BaseViewModel
import ru.acediat.core_android.CURRENT_GROUP
import ru.acediat.feature_timetable.entities.Lesson
import java.time.LocalDateTime
import javax.inject.Inject

class TimetableViewModel : BaseViewModel() {

    @Inject lateinit var repository : TimetableRepository
    @Inject lateinit var preferences: SharedPreferences

    private val timetable = MutableLiveData<Timetable>()
    private val error = MutableLiveData<Throwable>()

    fun setTimetableObserver(owner : LifecycleOwner, observer : (Timetable) -> Unit) =
        timetable.observe(owner, observer)

    fun setErrorObserver(owner : LifecycleOwner, observer : (Throwable) -> Unit) =
        error.observe(owner, observer)

    fun observeLessons(from : String, to : String): Disposable = repository
        .getGroupLessons(getCurrentGroup(), from, to)
        .subscribe({ list ->
            val lessonList = ArrayList<Lesson>()
            list.forEach { lessonList.add(Lesson.buildFromDTO(it)) }
            timetable.postValue(Timetable.build(lessonList, 6))
        }, {
            error.postValue(it)
        })

    fun getCurrentGroup() = preferences.getString(CURRENT_GROUP, "") ?: ""

    fun buildDateText(context: Context, date : LocalDateTime) =
        "${DateNameBuilder.dayName(context, date)}, " +
                "${date.dayOfMonth} ${DateNameBuilder.ofMonthName(context, date)}"
}