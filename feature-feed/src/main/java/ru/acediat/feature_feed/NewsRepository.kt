package ru.acediat.feature_feed

import io.reactivex.rxjava3.schedulers.Schedulers
import ru.acediat.feature_feed.apis.NewsApi

class NewsRepository(
    private val newsApi: NewsApi
) {

    fun getPosts() = newsApi.getPosts()
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())

}