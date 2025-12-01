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
    suspend fun insert(@Body task : Task) : TaskCC

    @PUT("tasks/{idCrudCrud}")
    suspend fun update(@Path("idCrudCrud") _id : String,@Body updated : Task)

    @DELETE("tasks/{idCrudCrud}")
    suspend fun delete(@Path("idCrudCrud") _id : String)

    @GET("tasks")
    suspend fun getAll(): List<TaskCC>

}