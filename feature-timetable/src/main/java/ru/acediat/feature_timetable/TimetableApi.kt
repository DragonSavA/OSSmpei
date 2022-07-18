package ru.acediat.feature_timetable

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query
import ru.acediat.core_network.EndpointUrl
import ru.acediat.core_network.OSS_URL
import ru.acediat.feature_timetable.dtos.GroupDTO
import ru.acediat.feature_timetable.dtos.GroupValidDTO
import ru.acediat.feature_timetable.dtos.LessonDTO

@EndpointUrl(OSS_URL)
interface TimetableApi {

    /*@GET("Android/get_person_timetable.php")
    fun getPersonLessons(
        @Query("userId") userId : Int,
        @Query("fromDate") fromDate : String,
        @Query("toDate") toDate : String,
    ) : Observable<List<LessonDTO>>

    @GET("")
    fun getGroupLessons(
        @Query("group") group : String,
        @Query("date") date : String
    ) : Single<List<LessonDTO>>*/

    @GET("Android/get_group_timetable.php")
    fun getGroupLessons(
        @Query("group") group : String,
        @Query("fromDate") fromDate : String,
        @Query("toDate") toDate : String,
    ) : Observable<List<LessonDTO>>

    //@GET("Android/get_user_group.php")
    //fun getUserGroup(
    //    @Query("userId") userId : Int
    //) : Single<GroupDTO>

    @GET("Android/is_group_valid.php")
    fun isGroupValid(
        @Query("group") group : String
    ) : Single<GroupValidDTO>
}