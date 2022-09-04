package ru.acediat.feature_map

import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class MapRepository @Inject constructor(
    private val api: PlacemarksApi
) {

    fun getBuidings() = api.getBuildings()
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())

    fun getFood() = api.getFood()
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())

    fun getPlaces() = api.getPlaces()
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())

    fun getHostels() = api.getHostels()
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())
}