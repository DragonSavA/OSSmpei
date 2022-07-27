package ru.acediat.feature_auth

import io.reactivex.rxjava3.core.Completable
import retrofit2.http.GET
import retrofit2.http.Query
import ru.acediat.core_network.EndpointUrl
import ru.acediat.core_network.OSS_URL

@EndpointUrl(OSS_URL)
interface RestorePassApi {

    @GET("Android/restore_pass2.php")
    fun restorePass(
        @Query("email") email : String
    ): Completable

}