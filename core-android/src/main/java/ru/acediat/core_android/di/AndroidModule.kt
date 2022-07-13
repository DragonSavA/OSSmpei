package ru.acediat.core_android.di

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AndroidModule(private val context : Context) {

    @Provides
    @Singleton
    fun provideSharedPreferences() =
        context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
}