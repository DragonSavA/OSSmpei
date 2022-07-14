package ru.acediat.feature_auth.di

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.acediat.core_network.NetworkModule
import ru.acediat.core_network.buildApi
import ru.acediat.feature_auth.AuthApi
import ru.acediat.feature_auth.AuthRepository
import ru.acediat.feature_auth.RegisterApi

@Module(includes = [NetworkModule::class])
class AuthModule {

    @Provides
    fun provideAuthApi(builder : Retrofit.Builder) = builder.buildApi<AuthApi>()

    @Provides
    fun provideRegisterApi(builder : Retrofit.Builder) = builder.buildApi<RegisterApi>()

    @Provides
    fun provideAuthRepository(api : AuthApi) = AuthRepository(api)

}