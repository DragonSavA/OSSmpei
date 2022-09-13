package ru.acediat.core_android.di

import android.content.Context
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import ru.acediat.core_android.APP_PREFERENCES
import ru.acediat.core_android.FragmentNotificator
import ru.acediat.core_android.Notificator
import javax.inject.Singleton

@Module
class AndroidModule(private val context : Context) {

    @Provides
    @Singleton
    fun provideSharedPreferences() = context
        .getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun providePicasso() = Picasso.Builder(context).build()

    @Provides
    @Singleton
    fun provideFragmentNotificator(): Notificator = FragmentNotificator()
}