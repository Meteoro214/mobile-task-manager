package gal.uvigo.mobileTaskManager.model

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import gal.uvigo.mobileTaskManager.R
import gal.uvigo.mobileTaskManager.data_model.Task
import gal.uvigo.mobileTaskManager.networking.CrudCrudAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TaskRepository(context: Context) {

    //Some values that use context for network error messages,stored to not store a context
    private val logTag = context.getString(R.string.Log_Tag)
    private val uploadErrorMsg = context.getString(R.string.network_error_up)
    private val downloadErrorMsg = context.getString(R.string.network_error_down)
    private val toastMsg = Toast.makeText(context, R.string.network_error_user, Toast.LENGTH_SHORT)


    private val dispatcher = Dispatchers.IO
    private val networkAPI = CrudCrudAPI(context)

    private val _tasks = MutableLiveData<List<Task>>(emptyList())
    val tasks: LiveData<List<Task>>
        get() = _tasks

    //There is no Room now to autogenerate IDs
    private var nextId: Long = 1


    fun addTask(task: Task): Long? {
        val t = Task(nextId, task.title, task.dueDate, task.category, task.description, task.isDone)
        nextId++
        val list = _tasks.value.orEmpty().toMutableList()
        list.add(t)
        _tasks.value = list
        return nextId - 1
    }

    /**
     * Retrieves the task with the given id or returns null if no such task exists
     */
    fun get(id: Long): Task? = tasks.value.orEmpty().find { it.id == id }

    fun updateTask(updated: Task): Boolean {
        val list = _tasks.value.orEmpty().toMutableList()
        val old = get(updated.id)
        return if (old != null) {
            val index = list.indexOf(old)
            list.removeAt(index)
            list.add(index, updated)
            _tasks.value = list
            true
        } else false
    }


    fun markTaskDone(id: Long): Boolean {
        val list = _tasks.value.orEmpty().toMutableList()
        val old = get(id)
        if (old != null) {
            val updated = old.copy()
            updated.isDone = true
            val index = list.indexOf(old)
            list.removeAt(index)
            list.add(index, updated)
            _tasks.value = list
            true
        }

    }


    fun deleteTask(task: Task): Boolean {
        val list = _tasks.value.orEmpty().toMutableList()
        val toRet = list.remove(task)
        _tasks.value = list
        return toRet
    }

    suspend fun upload() {
        withContext(dispatcher) {
            try {
                networkAPI.upload(tasks.value.orEmpty())
            } catch (_: Exception) { //network errors
                Log.e(logTag, uploadErrorMsg)
            }
        }
    }

    suspend fun download() {
        withContext(dispatcher) {
            try {
                _tasks.value = networkAPI.getAll()
            } catch (_: Exception) { //network errors
                Log.e(logTag, downloadErrorMsg)
                //Allow app to function, warn user
                toastMsg.show()
                _tasks.value = emptyList<Task>()
            }
            //No Room to autogenerate ids
            nextId = _tasks.value?.get(_tasks.value.orEmpty().size - 1)?.id ?: 1
        }
    }
    
}