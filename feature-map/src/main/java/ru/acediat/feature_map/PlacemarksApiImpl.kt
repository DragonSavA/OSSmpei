package ru.acediat.feature_map

import com.yandex.mapkit.geometry.Point
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleObserver

class PlacemarksApiImpl : PlacemarksApi {

    private val BUILDINGS = arrayListOf(
        Placemark(
            Point(55.755201, 37.707395),
            "Корпус Б", "", null
        ),
        Placemark(
            Point(55.755236, 37.708599),
            "Корпус В", "", null
        ),
        Placemark(
            Point(55.754638, 37.708967),
            "Корпус Г", "", null
        ),
        Placemark(
            Point(55.755994, 37.707712),
            "Корпус А", "", null
        ),
        Placemark(
            Point(55.755197, 37.710105),
            "Корпус Д", "", null
        ),
        Placemark(
            Point(55.755982854871554, 37.708284919317),
            "Корпус Х","", null
        ),
        Placemark(
            Point(55.756323961713896, 37.709111039696054),
            "Корпус Т","", null
        ),
        Placemark(
            Point(55.754353, 37.706652),
            "Корпус З","", null
        ),
        Placemark(
            Point(55.754401, 37.707456),
            "Корпус Ж","", null
        ),
        Placemark(
            Point(55.753845, 37.708241),
            "Корпус И","", null
        ),
        Placemark(
            Point(55.753738, 37.709396),
            "Корпус К","", null
        ),
        Placemark(
            Point(55.753463, 37.709783),
            "Корпус Л","", null
        ),
        Placemark(
            Point(55.75375022598407, 37.706759900150274),
            "Корпус Р","", null
        ),
        Placemark(
            Point(55.75628695899076, 37.704254885815466),
            "Корпус М","", null
        ),
        Placemark(
            Point(55.756472159368315, 37.70409251967778),
            "Корпус Э","", null
        ),
        Placemark(
            Point(55.75717344349989, 37.703938930091795),
            "Корпус Н","", null
        ),
        Placemark(
            Point(55.75669933729711, 37.70327630071533),
            "Корпус Е","", null
        ),
        Placemark(
            Point(55.757785822151874, 37.70558892112334),
            "Корпус Ф","", null
        ),
        Placemark(
            Point(55.75752408084265, 37.70239864588783),
            "Корпус С","", null
        ),
        Placemark(
            Point(55.758427634364715, 37.70442571913155),
            "Бассейн МЭИ","", "ic_pool.png"
        ),
        Placemark(
            Point(55.76284625314187, 37.701573616804225),
            "Стадион \"Энергия\"","", null
        ),
    )

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
                observer.onSuccess(arrayListOf())
            }
        }

    override fun getHostels(): Single<ArrayList<Placemark>> =
        object : Single<ArrayList<Placemark>>(){
            override fun subscribeActual(observer: SingleObserver<in ArrayList<Placemark>>) {
                observer.onSuccess(BUILDINGS)
            }
        }
}