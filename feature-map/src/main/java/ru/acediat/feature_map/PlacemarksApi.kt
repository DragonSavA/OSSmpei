package ru.acediat.feature_map

import io.reactivex.rxjava3.core.Single

interface PlacemarksApi {

    fun getBuildings(): Single<ArrayList<Placemark>>

    fun getPlaces(): Single<ArrayList<Placemark>>

    fun getFood(): Single<ArrayList<Placemark>>

    fun getHostels(): Single<ArrayList<Placemark>>
}