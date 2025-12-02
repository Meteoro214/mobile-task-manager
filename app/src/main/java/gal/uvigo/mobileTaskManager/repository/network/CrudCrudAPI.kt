package gal.uvigo.mobileTaskManager.repository.network

import android.content.Context
import gal.uvigo.mobileTaskManager.model.Task

//TODO delete and port to repo
class CrudCrudAPI(context: Context) {
    private val service = RetrofitClient.getInstance(context).service

    //Used to know what tasks are stored in CrudCrud
    private val _toCCIndex: MutableMap<Long, String> = mutableMapOf()


    suspend fun insert(task: Task): Long =
        if (!_toCCIndex.containsKey(task.id)) {
            val taskCC =service.insert(task)
            _toCCIndex[taskCC.id] = taskCC._id
            taskCC.id
        } else 0


    //Returns empty list if no data
    suspend fun getAll(): List<Task> {
        //update indexes
        val list: MutableList<Task> = mutableListOf()
        val stored = service.getAll()
        for (taskCC in stored) {
            list.add(taskCC.getTask())
            _toCCIndex[taskCC.id] = taskCC._id
        }
        return list.sortedBy { it.id }
    }

    //Returns number of updated rows
    suspend fun update(updated: Task): Int {
        val id = _toCCIndex[updated.id]
        return if (id != null) {
            service.update(id, updated)
            return 1
        } else 0
    }

    //Returns number of deleted rows
    suspend fun delete(task: Task): Int {
        val id = _toCCIndex[task.id]
        return if (id != null) {
            service.delete(id)
            _toCCIndex.remove(task.id)
            return 1
        } else 0
    }

}