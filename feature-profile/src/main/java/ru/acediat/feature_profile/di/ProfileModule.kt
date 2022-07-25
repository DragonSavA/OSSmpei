package ru.acediat.feature_profile.di

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.acediat.core_network.NetworkModule
import ru.acediat.core_network.buildApi
import ru.acediat.feature_profile.apis.ProfileApi
import ru.acediat.feature_profile.ProfileRepository
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
class ProfileModule {

    @Provides
    @Singleton
    fun provideProfileApi(builder : Retrofit.Builder) = builder.buildApi<ProfileApi>()

    @Provides
    @Singleton
    fun provideProfileRepository(api : ProfileApi) = ProfileRepository(api)

}