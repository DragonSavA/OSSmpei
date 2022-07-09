package ru.acediat.feature_timetable.di

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.acediat.core_android.ViewHoldersManager
import ru.acediat.core_network.NetworkModule
import ru.acediat.core_network.buildApi
import ru.acediat.feature_timetable.*
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
class TimetableModule {

    @Provides
    @Singleton
    fun provideTimetableApi(builder : Retrofit.Builder) = builder.buildApi<TimetableApi>()

    @Provides
    @Singleton
    fun provideTimetableRepository(api : TimetableApi) = TimetableRepository(api)

    @Provides
    fun provideViewHoldersManager() : ViewHoldersManager = ViewHoldersManagerImpl().apply{
        registerViewHolder(ItemTypes.LESSON, LessonViewHolder())
    }

    @Provides
    fun provideDatePicker() : DatePicker = DatePicker(6)
}