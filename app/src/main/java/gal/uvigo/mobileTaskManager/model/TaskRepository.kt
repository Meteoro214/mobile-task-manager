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
    private val toastMsg = Toast.makeText(context, "", Toast.LENGTH_SHORT)


    private val dispatcher = Dispatchers.IO
    private val networkAPI = CrudCrudAPI(context)

    private val _tasks = MutableLiveData<List<Task>>(emptyList())
    val tasks: LiveData<List<Task>>
        get() = _tasks

    //There is no Room now to autogenerate IDs
    private var nextId: Long = 1


    suspend fun addTask(task: Task): Long? {
        val t = Task(nextId, task.title, task.dueDate, task.category, task.description, task.isDone)
        val list = _tasks.value.orEmpty().toMutableList()
        list.add(t)
        try {
            networkAPI.insert(t)
            nextId++
            _tasks.value = list
            return nextId - 1
        } catch (e: Exception) {
            Log.e(logTag, uploadErrorMsg)
            Log.e(logTag, e.toString())
            toastMsg.setText(uploadErrorMsg)
            toastMsg.show()
            return null
        }
    }

    /**
     * Retrieves the task with the given id or returns null if no such task exists
     */
    fun get(id: Long): Task? = tasks.value.orEmpty().find { it.id == id }

    suspend fun updateTask(updated: Task): Boolean {
        val list = _tasks.value.orEmpty().toMutableList()
        val old = get(updated.id)
        return if (old != null) {
            val index = list.indexOf(old)
            list.removeAt(index)
            list.add(index, updated)
            try {
                networkAPI.update(updated)
                _tasks.value = list
                true
            } catch (e: Exception) {
                Log.e(logTag, uploadErrorMsg)
                Log.e(logTag, e.toString())
                toastMsg.setText(uploadErrorMsg)
                toastMsg.show()
                false
            }
        } else false
    }


    suspend fun markTaskDone(id: Long): Boolean {
        val list = _tasks.value.orEmpty().toMutableList()
        val old = get(id)
        return if (old != null) {
            val updated = old.copy()
            updated.isDone = true
            val index = list.indexOf(old)
            list.removeAt(index)
            list.add(index, updated)
            try {
                networkAPI.update(updated)
                _tasks.value = list
                true
            } catch (e: Exception) {
                Log.e(logTag, uploadErrorMsg)
                Log.e(logTag, e.toString())
                toastMsg.setText(uploadErrorMsg)
                toastMsg.show()
                false
            }
        } else false
    }


    suspend fun deleteTask(task: Task): Boolean {
        val list = _tasks.value.orEmpty().toMutableList()
        val toRet = list.remove(task)
        if (toRet) {
            try {
                networkAPI.delete(task)
                _tasks.value = list
            } catch (e: Exception) {
                Log.e(logTag, uploadErrorMsg)
                Log.e(logTag, e.toString())
                toastMsg.setText(uploadErrorMsg)
                toastMsg.show()
                return false
            }
        }
        return toRet
    }


    suspend fun download() {
        try {
            val temp: List<Task>
            withContext(dispatcher) {
                temp = networkAPI.getAll()
            }
            _tasks.value = temp
        } catch (e: Exception) { //network errors
            Log.e(logTag, downloadErrorMsg)
            Log.e(logTag, e.toString())
            //Allow app to function, warn user
            _tasks.value = emptyList<Task>()
            toastMsg.setText(downloadErrorMsg)
            toastMsg.show()
        }
        //No Room to autogenerate ids
        val nextIndex = _tasks.value.orEmpty().size - 1
        val lastId = if (nextIndex > -1) {
            (_tasks.value?.get(nextIndex)?.id ?: 0) + 1
        } else 1
        nextId = lastId
    }
}