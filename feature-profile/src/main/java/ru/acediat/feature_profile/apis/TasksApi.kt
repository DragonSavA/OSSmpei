package ru.acediat.feature_profile.apis

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query
import ru.acediat.core_network.EndpointUrl
import ru.acediat.core_network.OSS_URL
import ru.acediat.feature_profile.model.dtos.TaskDTO

const val AVAILABLE = "available"
const val TAKEN = "taken"
const val IN_CHECK = "inCheck"
const val ACCEPTED = "accepted"
const val REFUSED = "refused"

@EndpointUrl(OSS_URL)
interface TasksApi {

    @GET("Android/get_tasks.php")
    fun get(
        @Query("id") id: String,
        @Query("type") type: String
    ): Single<List<TaskDTO>>

    @GET("Android/take_task2.php")
    fun take(
        @Query("task_id") taskId : Int,
        @Query("user_id") userId : Int
    ): Completable//: Single<ResponseBody>

}