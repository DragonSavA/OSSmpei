package ru.acediat.feature_feed

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.POST
import ru.acediat.core_network.EndpointUrl
import ru.acediat.core_network.OSS_URL
import ru.acediat.feature_feed.entities.Event
import ru.acediat.feature_feed.entities.Post

@EndpointUrl(OSS_URL)
interface FeedApi {

    @GET("Android/news.php")
    fun getPosts() : Single<List<Post>>

    @GET("Android/afisha.php")
    fun getEvents() : Single<List<Event>>

    @POST("")
    fun addFavorite() : Completable

}