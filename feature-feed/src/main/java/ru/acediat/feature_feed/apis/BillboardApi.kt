package ru.acediat.feature_feed.apis

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.POST
import ru.acediat.core_network.EndpointUrl
import ru.acediat.core_network.OSS_URL
import ru.acediat.feature_feed.entities.Event

@EndpointUrl(OSS_URL)
interface BillboardApi {

    @GET("Android/afisha.php")
    fun getEvents() : Single<List<Event>>

    @POST("")
    fun addFavorite() : Completable

}