package gal.uvigo.mobileTaskManager.networking

import android.content.Context
import gal.uvigo.mobileTaskManager.data_model.Task

class CrudCrudAPI(context: Context) {
    private val service = RetrofitClient.getInstance(context).service

    //Used to know what tasks are stored in CrudCrud
    private val _toCCIndex: MutableMap<Long, TaskCC> = mutableMapOf()

    val toCCIndex: Map<Long, TaskCC>
        get() = _toCCIndex

    suspend fun insert(task: Task): Long =
        if(!toCCIndex.containsKey(task.id)) service.insert(task).id else 0

    //Returns empty list if no data
    suspend fun getAll(): List<Task>{
        //update indexes
        val list : MutableList<Task> = mutableListOf()
        val stored = service.getAll()
        for (taskCC in stored){
            list.add(taskCC.getTask())
            _toCCIndex[taskCC.id] = taskCC
        }
        return list.sortedBy { it.id }
    }

    //Returns number of updated rows
    suspend fun update(updated: Task): Int{
        val id = toCCIndex[updated.id]?._id
        return if(id != null){
            service.update(id,updated)
        } else 0
    }

    //Returns number of deleted rows
    suspend fun delete(task: Task): Int{
        val id = toCCIndex[task.id]?._id
        return if(id != null){
            service.delete(id)
        } else 0
    }

    suspend fun upload(taskList : List<Task>){
        //Almacena los datos en CrudCrud
        //si no estan asociados a un _id, insert
        //si estan asociados a un _id, comprobar si actualizados
        //si actualizados, update
        //si existe un _id no asociado a nada, delete

    }
}