package ru.acediat.feature_feed.di

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.acediat.core_network.NetworkModule
import ru.acediat.core_network.buildApi
import ru.acediat.feature_feed.FeedApi
import ru.acediat.feature_feed.FeedRepository
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
class FeedModule {

    @Singleton
    @Provides
    fun provideFeedApi(builder : Retrofit.Builder) = builder.buildApi<FeedApi>()

    @Singleton
    @Provides
    fun provideBriefRepository(api : FeedApi) = FeedRepository(api)

}