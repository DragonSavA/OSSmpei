package ru.acediat.feature_map.placemarks

import com.yandex.mapkit.geometry.Point

class Building(
    point: Point,
    name: String,
    imageUrl: String?
) : Placemark(point, name, "", imageUrl)