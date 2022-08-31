package ru.acediat.feature_profile.model

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import ru.acediat.feature_profile.apis.SettingsApi
import ru.acediat.feature_profile.model.dtos.GroupDTO
import ru.acediat.feature_profile.model.dtos.GroupValidDTO
import javax.inject.Inject

class SettingsRepository @Inject constructor(
    private val api: SettingsApi
) {

    fun saveGroup(userId: Int, group: String): Completable = api.saveGroup(GroupDTO(userId, group))
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())

    fun getFavoriteGroups(userId: Int): Single<List<GroupDTO>> = api.getFavoriteGroups(userId)
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())

    fun isGroupValid(group: String): Single<GroupValidDTO> = api.isGroupValid(group)
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())

    fun updateAvatar(
        userId: Int,
        imageName: String,
        image: MultipartBody.Part?
    ): Completable = api.updateAvatar(
        userId.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull()),
        imageName.toRequestBody("multipart/form-data".toMediaTypeOrNull()),
        image
    ).subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())

}