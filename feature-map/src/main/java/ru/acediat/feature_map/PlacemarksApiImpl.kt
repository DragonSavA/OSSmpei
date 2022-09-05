package ru.acediat.feature_map

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleObserver
import ru.acediat.feature_map.placemarks.Placemark

class PlacemarksApiImpl : PlacemarksApi {

    override fun getBuildings(): Single<ArrayList<Placemark>> =
        object : Single<ArrayList<Placemark>>(){
            override fun subscribeActual(observer: SingleObserver<in ArrayList<Placemark>>) {
                observer.onSuccess(BUILDINGS)
            }
        }

    override fun getPlaces(): Single<ArrayList<Placemark>> =
        object : Single<ArrayList<Placemark>>(){
            override fun subscribeActual(observer: SingleObserver<in ArrayList<Placemark>>) {
                observer.onSuccess(BUILDINGS)
            }
        }

    override fun getFood(): Single<ArrayList<Placemark>> =
        object : Single<ArrayList<Placemark>>(){
            override fun subscribeActual(observer: SingleObserver<in ArrayList<Placemark>>) {
                observer.onSuccess(FOOD)
            }
        }

    override fun getHostels(): Single<ArrayList<Placemark>> =
        object : Single<ArrayList<Placemark>>(){
            override fun subscribeActual(observer: SingleObserver<in ArrayList<Placemark>>) {
                observer.onSuccess(BUILDINGS)
            }
        }
}