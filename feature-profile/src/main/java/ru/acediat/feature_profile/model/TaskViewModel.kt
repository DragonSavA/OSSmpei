package ru.acediat.feature_profile.model

import android.content.SharedPreferences
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import ru.acediat.core_android.BaseViewModel
import ru.acediat.core_android.Logger
import ru.acediat.core_android.OSS_TAG
import ru.acediat.core_android.PROFILE_ID
import ru.acediat.core_network.OSS_URL
import ru.acediat.core_network.TASK_IMAGES_URL
import ru.acediat.feature_profile.model.dtos.TaskDTO
import javax.inject.Inject

class TaskViewModel: BaseViewModel() {

    @Inject lateinit var repository: TasksRepository
    @Inject lateinit var preferences: SharedPreferences

    private var task: TaskDTO? = null
    private var taskTaken = MutableLiveData<Boolean>()
    private var error = MutableLiveData<Throwable>()

    fun getTask() = task

    fun setTask(task: TaskDTO){
        Logger.d(OSS_TAG, task.toString())
        this.task = task
    }

    fun setTaskTakenObserver(lifecycleOwner: LifecycleOwner, observer: (Boolean) -> Unit) =
        taskTaken.observe(lifecycleOwner, observer)

    fun setErrorObserver(lifecycleOwner: LifecycleOwner, observer: (Throwable) -> Unit) =
        error.observe(lifecycleOwner, observer)

    fun getTaskShortDescription() = task!!.shortDescription

    fun getTaskDescription() = task!!.fullDescription

    fun getTaskDeadlines() = "${task!!.startDate} - ${task!!.endDate}"

    fun getTaskRefuseBeforeDate() = task!!.refuseBefore

    fun getTaskPlace() = task!!.place

    fun getTaskReward() = task!!.reward

    fun getTaskPenalty() = task!!.penalty

    fun getTaskStatus() = task!!.status

    fun getTaskAdminComment() = task!!.adminComment

    fun getTaskComment() = task!!.comment

    fun getImageUrl() = task!!.imageUrl?.let{ TASK_IMAGES_URL + it }

    fun takeTask() = task?.id?.let {
        repository.takeTask(it, getUserId()).subscribe({
            taskTaken.postValue(true)
        }, {
            error.postValue(it)
        })
    }

    fun refuseTask() = task?.let{
        repository.refuseTask(getUserId(), it.id!!)
            .subscribe({

            }, {
                Logger.e(OSS_TAG, "ERROR", it)
            })
    }

    private fun getUserId() = preferences.getInt(PROFILE_ID, 0)

}