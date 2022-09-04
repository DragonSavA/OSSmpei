package ru.acediat.feature_map

import com.yandex.mapkit.geometry.Point

data class Placemark(
    val point: Point,
    val name: String,
    val description: String,
    val imageUrl: String?
)