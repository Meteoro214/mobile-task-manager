package gal.uvigo.mobileTaskManager.model

import android.content.Context
import androidx.lifecycle.LiveData
import gal.uvigo.mobileTaskManager.data_model.Task
import gal.uvigo.mobileTaskManager.db.TaskDAO
import gal.uvigo.mobileTaskManager.db.TaskDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TaskRepository(context: Context) {

    private val taskDAO: TaskDAO = TaskDB.getInstance(context).taskDAO()
    val dispatcher = Dispatchers.IO

    //Saves the list from the repo to not call getAll() on get
    private var _tasks: LiveData<List<Task>> = this.getAll()

    //LiveData will keep the list updated after CUD operations
    val tasks: LiveData<List<Task>>
        get() = _tasks

    suspend fun addTask(task: Task): Long? =
        withContext(dispatcher) {
            val id = taskDAO.insert(task)
            if (id <= 0L) null else id
        }

    /**
     * Retrieves the task with the given id or returns null if no such task exists
     */
    fun get(id: Long): Task? {
        val index: Int = this.getIndex(id)
        return if (index == -1) null else getTaskByIndex(index)
    }

    private fun getAll() = taskDAO.getAll()

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

    /**
     * Retrieves the task on the indexed position
     * @throws IndexOutOfBoundsException if index is not on bounds
     */
    private fun getTaskByIndex(index: Int): Task = tasks.value.orEmpty()[index]

    /**
     * Auxiliary private method to find the index of a Task with the given id.
     * Returns the index, or -1 if the task does not exist
     */
    private fun getIndex(id: Long): Int =
        tasks.value?.indexOfFirst { it.id == id } ?: -1

}