package ru.acediat.feature_auth

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query
import ru.acediat.core_network.EndpointUrl
import ru.acediat.core_network.OSS_URL

@EndpointUrl(OSS_URL)
interface AuthApi {

    @GET("Android/auth.php")
    fun authenticate(
        @Query("email") email: String,
        @Query("password") password: String
    )//: Single<ParamsItem>

    @GET("Android/lk.php")
    fun authorize(
        @Query("id") id: String,
        @Query("pass") pass: String
    )//: Single<ProfileItem>

}