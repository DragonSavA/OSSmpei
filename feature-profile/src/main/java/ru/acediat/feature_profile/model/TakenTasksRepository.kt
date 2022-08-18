package ru.acediat.feature_profile.model

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.acediat.feature_profile.apis.*
import ru.acediat.feature_profile.model.dtos.TaskDTO
import javax.inject.Inject

class TakenTasksRepository @Inject constructor(
    private val api: TasksApi
) {

    fun getTakenTasks(userId: Int): Single<List<TaskDTO>> = api.get(userId.toString(), TAKEN)
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())

    fun getTasksInCheck(userId: Int): Single<List<TaskDTO>> = api.get(userId.toString(), IN_CHECK)
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())

    fun getAcceptedTasks(userId: Int): Single<List<TaskDTO>> = api.get(userId.toString(), ACCEPTED)
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())

    fun getRefusedTasks(userId: Int): Single<List<TaskDTO>> = api.get(userId.toString(), REFUSED)
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())

}