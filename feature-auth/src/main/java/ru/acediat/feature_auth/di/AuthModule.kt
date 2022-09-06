package ru.acediat.feature_auth.di

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.acediat.core_network.NetworkModule
import ru.acediat.core_network.buildApi
import ru.acediat.feature_auth.*

@Module(includes = [NetworkModule::class])
class AuthModule {

    @Provides
    fun provideAuthApi(builder : Retrofit.Builder) = builder.buildApi<AuthApi>()

    @Provides
    fun provideRegisterApi(builder : Retrofit.Builder) = builder.buildApi<RegisterApi>()

    @Provides
    fun provideRestorePassApi(builder: Retrofit.Builder) = builder.buildApi<RestorePassApi>()

    @Provides
    fun provideAuthRepository(api : AuthApi) = AuthRepository(api)

    @Provides
    fun provideRegisterRepository(api : RegisterApi) = RegistrationRepository(api)

    @Provides
    fun provideRestorePassRepository(api: RestorePassApi) = RestorePassRepository(api)

}