package gal.uvigo.mobileTaskManager.repository.network

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TaskApiService {

    @POST("tasks")
    suspend fun insert(@Body task: TaskSendDTO): TaskGetDTO

    @PUT("tasks/{idCrudCrud}")
    suspend fun update(@Path("idCrudCrud") _id: String, @Body updated: TaskSendDTO)

    @DELETE("tasks/{idCrudCrud}")
    suspend fun delete(@Path("idCrudCrud") _id: String)

    @GET("tasks")
    suspend fun getAll(): List<TaskGetDTO>

}