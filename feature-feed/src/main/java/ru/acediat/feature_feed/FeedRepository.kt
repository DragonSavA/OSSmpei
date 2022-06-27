package ru.acediat.feature_feed

import io.reactivex.rxjava3.schedulers.Schedulers

class FeedRepository(
    private val feedApi: FeedApi
) {

    fun getPosts() = feedApi.getPosts()
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())

    fun getEvents() = feedApi.getEvents()
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())

}