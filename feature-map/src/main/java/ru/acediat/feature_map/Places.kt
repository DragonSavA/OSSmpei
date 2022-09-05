package ru.acediat.feature_map

import com.yandex.mapkit.geometry.Point
import ru.acediat.feature_map.placemarks.Building
import ru.acediat.feature_map.placemarks.FoodPlace

val BUILDINGS = arrayListOf(
    Building(
        Point(55.755201, 37.707395),
        "Корпус Б",null
    ),
    Building(
        Point(55.755236, 37.708599),
        "Корпус В", null
    ),
    Building(
        Point(55.754638, 37.708967),
        "Корпус Г",null
    ),
    Building(
        Point(55.755994, 37.707712),
        "Корпус А",null
    ),
    Building(
        Point(55.755197, 37.710105),
        "Корпус Д",null
    ),
    Building(
        Point(55.755982854871554, 37.708284919317),
        "Корпус Х", null
    ),
    Building(
        Point(55.756323961713896, 37.709111039696054),
        "Корпус Т",null
    ),
    Building(
        Point(55.754353, 37.706652),
        "Корпус З",null
    ),
    Building(
        Point(55.754401, 37.707456),
        "Корпус Ж",null
    ),
    Building(
        Point(55.753845, 37.708241),
        "Корпус И",null
    ),
    Building(
        Point(55.753738, 37.709396),
        "Корпус К",null
    ),
    Building(
        Point(55.753463, 37.709783),
        "Корпус Л",null
    ),
    Building(
        Point(55.75375022598407, 37.706759900150274),
        "Корпус Р",null
    ),
    Building(
        Point(55.75628695899076, 37.704254885815466),
        "Корпус М",null
    ),
    Building(
        Point(55.756472159368315, 37.70409251967778),
        "Корпус Э",null
    ),
    Building(
        Point(55.75717344349989, 37.703938930091795),
        "Корпус Н",null
    ),
    Building(
        Point(55.75669933729711, 37.70327630071533),
        "Корпус Е",null
    ),
    Building(
        Point(55.757785822151874, 37.70558892112334),
        "Корпус Ф",null
    ),
    Building(
        Point(55.75752408084265, 37.70239864588783),
        "Корпус С",null
    ),
    Building(
        Point(55.758427634364715, 37.70442571913155),
        "Бассейн МЭИ","ic_pool.png"
    ),
    Building(
        Point(55.76284625314187, 37.701573616804225),
        "Стадион \"Энергия\"",null
    )
)

val FOOD = arrayListOf(
    FoodPlace(
        Point(55.75507394196282, 37.7082797230825),
        "Столовая №1", "Подвал корпуса В. Вход под лестницей"
    ),
    FoodPlace(
        Point(55.75517425267651, 37.707755437934225),
        "Буфет корпуса Б", "Корпус Б, второй этаж"
    ),
    FoodPlace(
        Point(55.75481136269668, 37.70898750803266),
        "Блинная", "Корпус Г, третий этаж"
    ),
    FoodPlace(
        Point(55.755959027608455, 37.704342341555794),
        "Harvard Cafe", "Красноказарменная 13с3"
    ),
)