package ru.acediat.feature_profile.model

import android.content.SharedPreferences
import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.disposables.Disposable
import ru.acediat.core_android.BaseViewModel
import ru.acediat.core_android.PROFILE_ID
import ru.acediat.feature_profile.model.dtos.TaskDTO
import javax.inject.Inject

class TakenTasksViewModel: BaseViewModel() {

    @Inject lateinit var repository: TakenTasksRepository
    @Inject lateinit var preferences: SharedPreferences

    private val takenTasks = MutableLiveData<ArrayList<TaskDTO>>()
    private val inCheckTasks = MutableLiveData<ArrayList<TaskDTO>>()
    private val acceptedTasks = MutableLiveData<ArrayList<TaskDTO>>()
    private val refusedTasks = MutableLiveData<ArrayList<TaskDTO>>()
    private val error = MutableLiveData<Throwable>()

    fun setTakenTasksObserver(
        lifecycleOwner: LifecycleOwner,
        observer: (ArrayList<TaskDTO>) -> Unit
    ) = takenTasks.observe(lifecycleOwner, observer)

    fun setInCheckTasksObserver(
        lifecycleOwner: LifecycleOwner,
        observer: (ArrayList<TaskDTO>) -> Unit
    ) = inCheckTasks.observe(lifecycleOwner, observer)

    fun setAcceptedTasksObserver(
        lifecycleOwner: LifecycleOwner,
        observer: (ArrayList<TaskDTO>) -> Unit
    ) = acceptedTasks.observe(lifecycleOwner, observer)

    fun setRefusedTasksObserver(
        lifecycleOwner: LifecycleOwner,
        observer: (ArrayList<TaskDTO>) -> Unit
    ) = refusedTasks.observe(lifecycleOwner, observer)

    fun setErrorObserver(
        lifecycleOwner: LifecycleOwner,
        observer: (Throwable) -> Unit
    ) = error.observe(lifecycleOwner, observer)

    fun getAllTasks(){
        getTakenTasks()
        getInCheckTasks()
        getAcceptedTasks()
        getRefusedTasks()
    }

    override fun restoreData(savedInstanceState: Bundle) = getAllTasks()

    private fun getTakenTasks(): Disposable = repository.getTakenTasks(getUserId())
        .subscribe({
            takenTasks.postValue(it as ArrayList<TaskDTO>)
        }, {
            error.postValue(it)
        })

    private fun getInCheckTasks(): Disposable = repository.getTasksInCheck(getUserId())
        .subscribe({
            inCheckTasks.postValue(it as ArrayList<TaskDTO>)
        }, {
            error.postValue(it)
        })

    private fun getAcceptedTasks(): Disposable = repository.getAcceptedTasks(getUserId())
        .subscribe({
            acceptedTasks.postValue(it as ArrayList<TaskDTO>)
        }, {
            error.postValue(it)
        })

    private fun getRefusedTasks(): Disposable = repository.getRefusedTasks(getUserId())
        .subscribe({
            refusedTasks.postValue(it as ArrayList<TaskDTO>)
        }, {
            error.postValue(it)
        })

    private fun getUserId() = preferences.getInt(PROFILE_ID, 0)//TODO: Перенести юзер айди в di потому что ну это пиздец


}