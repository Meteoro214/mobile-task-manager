package gal.uvigo.mobileTaskManager.model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import gal.uvigo.mobileTaskManager.data_model.Task
import gal.uvigo.mobileTaskManager.db.TaskDAO
import gal.uvigo.mobileTaskManager.db.TaskDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TaskRepository(context: Context) {

    private val taskDAO: TaskDAO = TaskDB.getInstance(context).taskDAO()
    val dispatcher = Dispatchers.IO

    //Saves the list from the repo to store info on memory for quicker access & allow get() easier access without DB access
    private var _tasks: MutableMap<Long, Task> = mutableMapOf()

    //LiveData will keep the list updated after CUD operations
    val tasks: LiveData<List<Task>> = this.getAll()

    suspend fun addTask(task: Task): Long? =
        withContext(dispatcher) {
            val id = taskDAO.insert(task)
            if (id <= 0L) null else id
        }

    /**
     * Retrieves the task with the given id or returns null if no such task exists
     */
    fun get(id: Long): Task? = _tasks[id]

    //Livedata will ensure DAO only performs getAll once, but when DAO performs a CUD operation,
    // LiveData updates without a query and .map updates memory-only map
    private fun getAll() = taskDAO.getAll().map { tasks ->
        for (t in tasks) _tasks[t.id] = t
        tasks
    }

    suspend fun updateTask(updated: Task): Boolean =
        withContext(dispatcher) {
            val res = taskDAO.update(updated)
            res == 1
        }

    suspend fun markTaskDone(id: Long) : Boolean =
        withContext(dispatcher) {
            val res = taskDAO.markDone(id)
            res == 1
        }

    suspend fun deleteTask(task: Task): Boolean =
        withContext(dispatcher) {
            val res = taskDAO.delete(task)
            res == 1
        }
}