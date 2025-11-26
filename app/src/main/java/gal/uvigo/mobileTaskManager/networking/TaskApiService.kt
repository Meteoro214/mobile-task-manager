package gal.uvigo.mobileTaskManager.networking

import androidx.lifecycle.LiveData
import gal.uvigo.mobileTaskManager.data_model.Task
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TaskApiService {

    @POST("tasks")
    suspend fun insert(@Body task : Task) : Task

    @PUT("tasks/{_id}")
    suspend fun update(@Path("_id") id : String,updated : Task) : Int

    @DELETE("tasks/{_id}")
    suspend fun delete(@Path("_id") id : String): Int

    @GET("tasks")
    suspend fun getAll(): List<TaskCC>

}