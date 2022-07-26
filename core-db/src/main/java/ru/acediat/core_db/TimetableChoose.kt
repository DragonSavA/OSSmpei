package ru.acediat.core_db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "timetables")
data class TimetableChoose(
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "type")
    val type: String,
    @ColumnInfo(name = "choose")
    val choose: String
){
    companion object{
        const val GROUP_TYPE = "group"
        const val PERSON_TYPE = "person"
    }
}