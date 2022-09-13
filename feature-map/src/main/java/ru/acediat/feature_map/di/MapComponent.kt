package ru.acediat.feature_map.di

import android.content.Context
import dagger.Component
import ru.acediat.core_android.di.AndroidModule
import ru.acediat.feature_map.MapFragment
import ru.acediat.feature_map.MapViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [
    MapModule::class,
    AndroidModule::class
])
interface MapComponent {

    fun inject(fragment: MapFragment)

    fun inject(model: MapViewModel)

    companion object{
        fun init(context: Context) : MapComponent = DaggerMapComponent.builder()
            .mapModule(MapModule())
            .androidModule(AndroidModule(context))
            .build()
    }
}