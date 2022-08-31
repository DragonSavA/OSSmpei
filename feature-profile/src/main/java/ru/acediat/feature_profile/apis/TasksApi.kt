package ru.acediat.feature_profile.apis

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*
import ru.acediat.core_network.EndpointUrl
import ru.acediat.core_network.OSS_URL
import ru.acediat.feature_profile.model.dtos.RefuseTaskDTO
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
    ): Completable

    @POST("/Android/refuse_task.php")
    fun refuseTask(
        @Body body: RefuseTaskDTO
    ): Completable

    @Multipart
    @POST("Android/edit_report.php")
    fun sendReport(
        @Part("comment") comment: RequestBody,
        @Part("task_id") task_id: RequestBody,
        @Part("user_id") user_id: RequestBody,
        @Part("file_name") file_name: RequestBody,
        @Part image: MultipartBody.Part?
    ): Completable

}