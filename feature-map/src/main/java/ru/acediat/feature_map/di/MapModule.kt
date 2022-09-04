package ru.acediat.feature_map.di

import dagger.Module
import dagger.Provides
import ru.acediat.core_network.NetworkModule
import ru.acediat.feature_map.MapRepository
import ru.acediat.feature_map.PlacemarksApi
import ru.acediat.feature_map.PlacemarksApiImpl

@Module(includes = [NetworkModule::class])
class MapModule {

    @Provides
    fun providePlacemarksApi(): PlacemarksApi = PlacemarksApiImpl()

    @Provides
    fun provideMapRepository(api: PlacemarksApi) = MapRepository(api)
}