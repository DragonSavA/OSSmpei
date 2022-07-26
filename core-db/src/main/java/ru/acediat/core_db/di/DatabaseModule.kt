package ru.acediat.core_db.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.acediat.core_db.AppDatabase
import javax.inject.Singleton

@Module
class DatabaseModule(val context: Context) {

    @Provides
    @Singleton
    fun provideDatabase() = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        "app_database"
    ).build()

    @Provides
    fun provideTimetableChooseDao(db: AppDatabase) = db.getTimetableChooseDao()
}