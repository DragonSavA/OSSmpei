package ru.acediat.feature_map.placemarks

import com.yandex.mapkit.geometry.Point

open class Placemark(
    val point: Point,
    val info: PlacemarkInfo
){
    constructor(
        point: Point,
        name: String,
        description: String,
        imageUrl: String?
    ) : this(point, PlacemarkInfo(name, description, imageUrl))
}

data class PlacemarkInfo(
    val name: String,
    val description: String,
    val imageUrl: String?
)