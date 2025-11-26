package gal.uvigo.mobileTaskManager.networking

import android.content.Context
import gal.uvigo.mobileTaskManager.data_model.Task

class CrudCrudAPI(context: Context) {
    private val service = RetrofitClient.getInstance(context).service

    //Used to know what tasks are stored in CrudCrud
    private val _fromCCIndex : MutableMap<String, Long> = mutableMapOf()
    private val _toCCIndex: MutableMap<Long, String> = mutableMapOf()

    val fromCCIndex : Map<String,Long>
        get() = _fromCCIndex
    val toCCIndex: Map<Long, String>
        get() = _toCCIndex

    suspend fun insert(task: Task): Long = service.insert(task).id

    //Returns empty list if no data
    suspend fun getAll(): List<Task>{
        //update indexes
        val list : MutableList<Task> = mutableListOf()
        val stored = service.getAll()
        for (taskCC in stored){
            list.add(taskCC.getTask())
            _fromCCIndex[taskCC._id] = taskCC.id
            _toCCIndex[taskCC.id] = taskCC._id
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