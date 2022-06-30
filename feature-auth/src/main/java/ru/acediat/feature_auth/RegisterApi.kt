package ru.acediat.feature_auth

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import ru.acediat.core_network.EndpointUrl
import ru.acediat.core_network.OSS_URL

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
    ): Call<ResponseBody>

}
