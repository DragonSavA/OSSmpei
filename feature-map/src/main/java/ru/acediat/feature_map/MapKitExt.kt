package ru.acediat.feature_map

import android.view.View
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.ui_view.ViewProvider
import ru.acediat.core_android.ext.getDrawableFromAssets

fun MapView.addPlacemark(placemark: Placemark) = placemark.imageUrl?.let{
    val view = View(context).apply {
        background = context.getDrawableFromAssets(placemark.imageUrl)
    }
    map.mapObjects.addPlacemark(placemark.point, ViewProvider(view))
    map.mapObjects.addTapListener { mapObject, point ->
        return@addTapListener true
    }
} ?: run{
    map.mapObjects.addPlacemark(placemark.point)
}