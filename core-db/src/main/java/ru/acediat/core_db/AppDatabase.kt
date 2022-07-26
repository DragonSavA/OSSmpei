package ru.acediat.core_db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.acediat.core_db.dao.TimetableChooseDao

@Database(entities = [TimetableChoose::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getTimetableChooseDao(): TimetableChooseDao

}