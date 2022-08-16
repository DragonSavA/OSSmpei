package ru.acediat.feature_profile.model

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.acediat.feature_profile.apis.AVAILABLE
import ru.acediat.feature_profile.apis.TasksApi
import ru.acediat.feature_profile.model.dtos.TaskDTO
import javax.inject.Inject

class NewTasksRepository @Inject constructor(
    private val api: TasksApi
) {

    fun getAvailableTasks(): Single<List<TaskDTO>> = api.get("-1", AVAILABLE)
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())
}