package gal.uvigo.mobileTaskManager.model

import android.content.Context
import gal.uvigo.mobileTaskManager.data_model.Task
import gal.uvigo.mobileTaskManager.db.TaskDAO
import gal.uvigo.mobileTaskManager.db.TaskDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TaskRepository(context: Context) {

    val taskDAO: TaskDAO = TaskDB.getInstance(context).taskDAO()
    val dispatcher = Dispatchers.IO

    suspend fun addTask(task: Task): Long? =
        withContext(dispatcher) {
            val id = taskDAO.insert(task)
            if (id <= 0L) null else id
        }

    suspend fun getAll() =
        withContext(dispatcher) {
            taskDAO.getAll()
        }

    suspend fun updateTask(updated: Task): Boolean =
        withContext(dispatcher) {
            val res = taskDAO.update(updated)
            res == 1
        }

    suspend fun deleteTask(task: Task): Boolean =
        withContext(dispatcher) {
            val res = taskDAO.delete(task)
            res == 1
        }

}