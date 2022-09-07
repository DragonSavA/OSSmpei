package ru.acediat.feature_profile.apis

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*
import ru.acediat.core_network.EndpointUrl
import ru.acediat.core_network.OSS_URL
import ru.acediat.feature_profile.model.dtos.GroupDTO
import ru.acediat.feature_profile.model.dtos.GroupValidDTO

@EndpointUrl(OSS_URL)
interface SettingsApi {

    @POST("/Android/save_group.php")
    fun saveGroup(
        @Body group: GroupDTO
    ): Completable

    @GET("/Android/get_saved_groups.php")
    fun getFavoriteGroups(
        @Query("user_id") userId: Int
    ): Single<List<GroupDTO>>

    @POST("Android/delete_saved_group.php")
    fun deleteFavoriteGroup(
        @Body group: GroupDTO
    ): Completable

    @GET("/Android/is_group_valid.php")
    fun isGroupValid(
        @Query("group") group: String
    ): Single<GroupValidDTO>

    @Multipart
    @POST("/Android/update_avatar.php")
    fun updateAvatar(
        @Part("user_id") userId: RequestBody,
        @Part("image_name") imageName: RequestBody,
        @Part image: MultipartBody.Part?
    ): Completable


}