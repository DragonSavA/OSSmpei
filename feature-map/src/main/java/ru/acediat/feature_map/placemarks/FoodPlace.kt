package ru.acediat.feature_map.placemarks

import com.yandex.mapkit.geometry.Point

class FoodPlace(
    point: Point,
    name: String,
    description: String,
) : Placemark(point, name, description, "ic_food.png")