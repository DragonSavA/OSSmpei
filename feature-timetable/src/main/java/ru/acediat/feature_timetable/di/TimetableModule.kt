package ru.acediat.feature_timetable.di

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.acediat.core_network.NetworkModule
import ru.acediat.core_network.buildApi
import ru.acediat.feature_timetable.TimetableApi
import ru.acediat.feature_timetable.TimetableRepository
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
class TimetableModule {

    @Provides
    @Singleton
    fun provideTimetableApi(builder : Retrofit.Builder) = builder.buildApi<TimetableApi>()

    @Provides
    @Singleton
    fun provideTimetableRepository(api : TimetableApi) = TimetableRepository(api)
}