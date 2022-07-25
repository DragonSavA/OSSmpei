package ru.acediat.feature_profile.apis

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query
import ru.acediat.core_network.EndpointUrl
import ru.acediat.core_network.OSS_URL
import ru.acediat.feature_profile.model.Profile

@EndpointUrl(OSS_URL)
interface ProfileApi {

    @GET("Android/lk.php")
    fun authorize(
        @Query("id") id: String,
        @Query("pass") pass: String
    ): Single<Profile>

}