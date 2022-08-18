package ru.acediat.feature_profile.model

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.acediat.feature_profile.apis.TasksApi
import javax.inject.Inject

class TasksRepository @Inject constructor(
    private val api: TasksApi
) {

    fun takeTask(taskId: Int, userId: Int): Completable =
        api.take(taskId, userId)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())

}