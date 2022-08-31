package ru.acediat.feature_profile.model

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import ru.acediat.feature_profile.apis.TasksApi
import ru.acediat.feature_profile.model.dtos.RefuseTaskDTO
import javax.inject.Inject

class TasksRepository @Inject constructor(
    private val api: TasksApi
) {

    fun takeTask(taskId: Int, userId: Int): Completable = api.take(taskId, userId)
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())

    fun sendReport(
        taskId: Int, userId: Int,
        comment: String,
        fileName: String,
        image: MultipartBody.Part?
    ): Completable = api.sendReport(
        comment.toRequestBody("multipart/form-data".toMediaTypeOrNull()),
        taskId.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull()),
        userId.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull()),
        fileName.toRequestBody("multipart/form-data".toMediaTypeOrNull()),
        image
    ).subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())

    fun refuseTask(userId: Int, taskId: Int) = api.refuseTask(RefuseTaskDTO(taskId, userId))
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())
}