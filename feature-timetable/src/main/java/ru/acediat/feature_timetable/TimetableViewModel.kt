package ru.acediat.feature_timetable

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.acediat.core_android.HasId
import ru.acediat.feature_timetable.di.TimetableComponent
import javax.inject.Inject

class TimetableViewModel : ViewModel() {

    @Inject lateinit var repository : TimetableRepository

    private val items = MutableLiveData<ArrayList<HasId>>()
    private val error = MutableLiveData<Throwable>()

    init{
        TimetableComponent.init().inject(this)
    }

    fun setItemsObserver(owner : LifecycleOwner, observer : (ArrayList<HasId>) -> Unit) =
        items.observe(owner, observer)

    fun setErrorObserver(owner : LifecycleOwner, observer : (Throwable) -> Unit) =
        error.observe(owner, observer)

    //fun observeLessons() = repository.getGroupLessons(group, )
}