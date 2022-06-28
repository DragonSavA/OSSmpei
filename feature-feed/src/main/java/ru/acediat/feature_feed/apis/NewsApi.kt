package ru.acediat.feature_feed.apis

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import ru.acediat.core_network.EndpointUrl
import ru.acediat.core_network.OSS_URL
import ru.acediat.feature_feed.entities.Post

@EndpointUrl(OSS_URL)
interface NewsApi {

    @GET("Android/news.php")
    fun getPosts() : Single<List<Post>>

}