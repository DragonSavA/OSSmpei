package ru.acediat.feature_profile.model

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.disposables.Disposable
import ru.acediat.core_android.BaseViewModel
import ru.acediat.core_android.Logger
import ru.acediat.core_android.OSS_TAG
import ru.acediat.feature_profile.model.dtos.TaskDTO
import javax.inject.Inject

class NewTasksViewModel: BaseViewModel() {

    @Inject lateinit var repository: NewTasksRepository

    private val tasks = MutableLiveData<ArrayList<TaskDTO>>()
    private val error = MutableLiveData<Throwable>()

    fun setTasksObserver(lifecycleOwner: LifecycleOwner, observer: (ArrayList<TaskDTO>) -> Unit){
        tasks.observe(lifecycleOwner, observer)
    }

    fun setErrorObserver(lifecycleOwner: LifecycleOwner, observer: (Throwable) -> Unit){
        error.observe(lifecycleOwner, observer)
    }

    fun getTasks(): Disposable = repository.getAvailableTasks()
        .subscribe({
            tasks.postValue(it as ArrayList<TaskDTO>)
            Logger.dArray(OSS_TAG, it.toArray())
        }, {
            error.postValue(it)
            Logger.e(OSS_TAG, "error", it)
        })

    override fun restoreData(savedInstanceState: Bundle){
        getTasks()
    }
}