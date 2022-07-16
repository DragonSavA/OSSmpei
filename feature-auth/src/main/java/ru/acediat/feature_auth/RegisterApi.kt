package ru.acediat.feature_auth

import io.reactivex.rxjava3.core.Single
import okhttp3.ResponseBody
import retrofit2.http.*
import ru.acediat.core_network.EndpointUrl
import ru.acediat.core_network.OSS_URL
import ru.acediat.feature_auth.dtos.GroupValidDTO

@EndpointUrl(OSS_URL)
interface RegisterApi {

    @FormUrlEncoded
    @POST("Android/register.php")
    fun register(
        @Field("email") email: String,
        @Field("name") name: String,
        @Field("surname") surname: String,
        @Field("gender") gender: String,
        @Field("group") group: String,
        @Field("password") password: String
    ): Single<ResponseBody>

    @GET("Android/is_group_valid.php")
    fun isGroupValid(
        @Query("group") group : String
    ) : Single<GroupValidDTO>
}
