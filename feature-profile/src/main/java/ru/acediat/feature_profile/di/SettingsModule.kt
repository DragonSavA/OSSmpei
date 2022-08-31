package ru.acediat.feature_profile.di

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.acediat.core_network.NetworkModule
import ru.acediat.core_network.buildApi
import ru.acediat.feature_profile.apis.SettingsApi
import ru.acediat.feature_profile.model.SettingsRepository
import ru.acediat.feature_profile.ui.adapters.GroupsAdapter
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
class SettingsModule {

    @Provides
    @Singleton
    fun provideSettingsApi(builder : Retrofit.Builder) = builder.buildApi<SettingsApi>()

    @Provides
    @Singleton
    fun provideSettingsRepository(api : SettingsApi) = SettingsRepository(api)

    @Provides
    fun provideGroupsAdapter() = GroupsAdapter()
}