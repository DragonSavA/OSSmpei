package ru.acediat.feature_map.di

import dagger.Component
import ru.acediat.feature_map.MapFragment
import ru.acediat.feature_map.MapViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [MapModule::class])
interface MapComponent {

    fun inject(fragment: MapFragment)

    fun inject(model: MapViewModel)

    companion object{
        fun init() : MapComponent = DaggerMapComponent.builder()
            .mapModule(MapModule())
            .build()
    }
}