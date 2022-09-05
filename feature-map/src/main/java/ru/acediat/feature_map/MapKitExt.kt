package ru.acediat.feature_map

import android.content.Context
import android.view.View
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.MapObject
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.ui_view.ViewProvider
import ru.acediat.core_android.ext.getDrawableFromAssets

fun MapView.addPlacemark(
    placemark: Placemark,
    onPlacemarkTap: (MapObject, Point) -> Unit
) = placemark.imageUrl?.let{
    map.mapObjects.addPlacemark(
        placemark.point,
        createViewProviderFromAsset(context, placemark.imageUrl)
    )
    map.mapObjects.addTapListener { mapObject, point ->
        onPlacemarkTap(mapObject, point)
        return@addTapListener true
    }
} ?: run{
    map.mapObjects.addPlacemark(placemark.point)
}

fun MapView.addPlacemarks(
    placemarks: List<Placemark>,
    onPlacemarkTap: (MapObject, Point) -> Unit
) = placemarks.forEach { addPlacemark(it, onPlacemarkTap) }

fun MapView.setPlacemarks(placemarks: List<Placemark>, onPlacemarkTap: (MapObject, Point) -> Unit){
    clearMap()
    addPlacemarks(placemarks, onPlacemarkTap)
}

fun MapView.clearMap() = map.mapObjects.clear()

fun Point.equals(other: Point): Boolean{
     return this.latitude == other.latitude && this.longitude == other.longitude
}

private fun createViewProviderFromAsset(context: Context, filename: String) = ViewProvider(
    View(context).apply {
        background = context.getDrawableFromAssets(filename)
    }
)