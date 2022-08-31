package ru.acediat.feature_profile.apis

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
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

    @GET("/Android/is_group_valid.php")
    fun isGroupValid(
        @Query("group") group: String
    ): Single<GroupValidDTO>
}