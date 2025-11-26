package gal.uvigo.mobileTaskManager.networking

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import gal.uvigo.mobileTaskManager.data_model.Task

class CrudCrudAPI(context: Context) {
    private val service = RetrofitClient.getInstance(context).service

    //Used to know what tasks are stored in CrudCrud
    val fromCCIndex : MutableMap<String, Long> = mutableMapOf()
    val toCCIndex: MutableMap<Long, String> = mutableMapOf()


    suspend fun insert(task: Task): Long{
        service.insert(task)
    }

    //Returns empty list if no data
    suspend fun getAll(): List<Task>{
        //update indexes
        val list : MutableList<Task> = mutableListOf()
        val stored = service.getAll()
        for (taskCC in stored){
            list.add(taskCC.getTask())
            fromCCIndex[taskCC._id] = taskCC.id
            toCCIndex[taskCC.id] = taskCC._id
        }
        return list
    }

    //Returns number of updated rows
    suspend fun update(updated: Task): Int{
        val id = toCCIndex[updated.id]
        return if(id != null){
            service.update(id,updated)
        } else 0
    }

    //Returns number of deleted rows
    suspend fun delete(task: Task): Int{
        val id = toCCIndex[task.id]
        return if(id != null){
            service.delete(id)
        } else 0
    }

}