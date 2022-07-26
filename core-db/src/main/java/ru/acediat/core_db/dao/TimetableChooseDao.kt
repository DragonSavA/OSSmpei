package ru.acediat.core_db.dao

import androidx.room.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import ru.acediat.core_db.TimetableChoose

@Dao
interface TimetableChooseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertChoose(choose: TimetableChoose) : Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertChooses(chooses : ArrayList<TimetableChoose>) : Completable

    @Query("select * from timetables")
    fun getAllChooses() : Maybe<List<TimetableChoose>>

    @Query("select * from timetables where id = :id")
    fun getChoose(id : Int) : Maybe<TimetableChoose>

    @Delete
    fun deleteChoose(choose : TimetableChoose) : Completable
}