package ru.acediat.feature_timetable

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.disposables.Disposable
import ru.acediat.core_android.*
import ru.acediat.feature_timetable.entities.Lesson
import java.time.LocalDateTime
import javax.inject.Inject

class TimetableViewModel : BaseViewModel() {

    @Inject lateinit var repository : TimetableRepository
    @Inject lateinit var preferences: SharedPreferences

    private val timetable = MutableLiveData<Timetable>()
    private val isGroupValid = MutableLiveData<Pair<String, Boolean>>()
    private val currentGroup = MutableLiveData<String>()
    private val favoriteGroups = MutableLiveData<ArrayList<String>>()
    private val error = MutableLiveData<Throwable>()

    fun setTimetableObserver(owner : LifecycleOwner, observer : (Timetable) -> Unit) =
        timetable.observe(owner, observer)

    fun setOnGroupValidObserver(owner: LifecycleOwner, observer: (Pair<String, Boolean>) -> Unit) =
        isGroupValid.observe(owner, observer)

    fun setCurrentGroupObserver(owner : LifecycleOwner, observer : (String) -> Unit) =
        currentGroup.observe(owner, observer)

    fun setFavoriteGroupsObserver(owner : LifecycleOwner, observer : (ArrayList<String>) -> Unit) =
        favoriteGroups.observe(owner, observer)

    fun setErrorObserver(owner : LifecycleOwner, observer : (Throwable) -> Unit) =
        error.observe(owner, observer)

    fun isCurrentGroupSet() = !currentGroup.value.isNullOrEmpty()

    fun getCurrentGroupValue(): String? = currentGroup.value

    fun observeLessons(from : String, to : String) = currentGroup.value?.let {
        repository.getGroupLessons(it, from, to)
            .subscribe({ list ->
                val lessonList = ArrayList<Lesson>()
                list.forEach { lessonList.add(Lesson.buildFromDTO(it)) }
                timetable.postValue(Timetable.build(lessonList, 6))
            }, {
                error.postValue(it)
            })
    }

    fun isGroupValid(group: String): Disposable = repository.isGroupValid(group)
        .subscribe({
            isGroupValid.postValue(group to it.isGroupValid)
        }, {
            error.postValue(it)//TODO: обработка ошибок
        })

    fun getCurrentGroup(){
        if(!preferences.getBoolean(IS_AUTH, false)) {
            currentGroup.postValue("")
        }

        val prefsGroup = preferences.getString(CURRENT_GROUP, "") ?: ""
        if(prefsGroup == "")
            repository.getUserGroup(getUserId())
                .subscribe({
                    currentGroup.postValue(it.group)
                }, {
                    error.postValue(it)
                })
        else
            currentGroup.postValue(prefsGroup)
    }

    fun getFavoriteGroups() = repository.getFavoriteGroups(getUserId())
        .subscribe({
            val list = ArrayList<String>()
            it.forEach {
                list.add(it.group)
            }
            favoriteGroups.postValue(list)
        }, {
            error.postValue(it)
        })

    fun setCurrentGroup(group: String) = preferences.edit()
        .putString(CURRENT_GROUP, group)
        .apply()

    fun buildDateText(context: Context, date : LocalDateTime) =
        "${DateNameBuilder.dayName(context, date)}, " +
                "${date.dayOfMonth} ${DateNameBuilder.ofMonthName(context, date)}"

    fun isAuth() = preferences.getBoolean(IS_AUTH, false)

    private fun getUserId() = preferences.getInt(PROFILE_ID, 0)
}