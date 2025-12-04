package gal.uvigo.mobileTaskManager.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import gal.uvigo.mobileTaskManager.R
import gal.uvigo.mobileTaskManager.model.Task
import gal.uvigo.mobileTaskManager.repository.local.TaskDB
import gal.uvigo.mobileTaskManager.repository.local.TaskEntity
import gal.uvigo.mobileTaskManager.repository.network.RetrofitClient
import gal.uvigo.mobileTaskManager.repository.sync.SyncStatus
import gal.uvigo.mobileTaskManager.repository.sync.TaskUploadWorker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TaskRepository(context: Context) {

    /**
     * The TaskApiService that will handle CrudCrud API.
     */
    private val taskService = RetrofitClient.getInstance(context).service

    /**
     * The TaskDAO that will handle Room operations.
     */
    private val taskDAO = TaskDB.getInstance(context).taskDAO()

    /**
     * The WorkManager that will handle TaskUploadWorker works.
     */
    private val workManager = WorkManager.getInstance(context)

    /**
     * The Dispacher that will be used to launch coroutines.
     */
    private val dispatcher = Dispatchers.IO

    /**
     * A key from String resources for TaskUploadWorker input data.
     */
    private val taskIDKey = context.getString(R.string.Task_ID_Key)

    /**
     * A key from String resources for TaskUploadWorker input data.
     */
    private val taskStatusKey = context.getString(R.string.Task_Status_Key)

    /**
     * A TaskMapper to handle transformations between all the Task classes
     */
    private val taskMapper = TaskMapper()

    /**
     * A MutableMap that maps TaskEntities by id to allow quicker retrieving.
     */
    private var _tasks: MutableMap<Long, TaskEntity> = mutableMapOf()

    //LiveData will keep the list updated after CUD operations
    /**
     * A LiveData that exposes the Tasks to the ViewModel.
     * Exposed tasks are expected to be ordered by category and then by position
     */
    val tasks: LiveData<List<Task>> = this.getAll()


    /**
     *
     */
    private fun getAll() {
        //Hace repo getAll y transforma entity a task, y ademas almacena en el mapa
    } //en delete borrar del mapa, aqui meter 1 a 1

    /**
     *
     */
    suspend fun init{
        //TODO
        val settings = context.getSharedPreferences(
            context.getString(R.string.preferences_file),
            Context.MODE_PRIVATE
        )
        val key = context.getString(R.string.preferences_first_init_key)
        val firstInit = settings.getBoolean(key, true)
        if (firstInit) {
            //TODO operate and set firstInit to false

            //este init volverlo un metodo normal y llamarlo desde el init de viewmodel (donde llama al download ahora)

            //set flag so app knows
            settings.edit().putBoolean(key, false).commit()
        } else {
            //TODO operate
            //se asume sincronizado por workers
        }

    }

    /**
     * Adds the given task
     */
    suspend fun addTask(task: Task): Long? {
         withContext(dispatcher) {
            //Insert on Room
            val id = taskDAO.insert(taskMapper.toEntity(task, _tasks.size))
            if (id > 0L) {
                //Prepare a WorkRequest to sync
                prepareSync(id, SyncStatus.PENDING_CREATE)
                id
            } else null
        }
    }

    /**
     * Called by sync() to perform a POST to server & update the _id.
     */
    private suspend fun addSync(task : TaskEntity) : Int{
        val dto = taskMapper.toDTO(task)
        try{
            val returned = taskService.insert(dto)
            val updatedRows = taskDAO.syncInsert(task.id,returned._id)
            return if (updatedRows == 1) 0 else 404
        }catch (_ : Exception){
            return 500
        }
    }

    /**
     * Retrieves the task with the given id or returns null if no such task exists
     */
    fun get(id: Long): Task? {
        val toRet = _tasks[id]
        return if (toRet == null) null else taskMapper.toTask(toRet)
    }


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


    suspend fun download() { //ejecutar solo si es primera ejecution, refactorizar
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

    private fun prepareSync(id: Long, status: SyncStatus) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .build()

        val taskWorkRequest = OneTimeWorkRequestBuilder<TaskUploadWorker>()
            .setInputData(
                workDataOf(
                    taskIDKey to id,
                    taskStatusKey to status.name,
                )
            )
            .setConstraints(constraints)
            .build()

        val workPolicy = when (status) {
            SyncStatus.PENDING_CREATE -> {
                //Create operation should only happen once and be the first, so
                // pending work with the same task ID means a bug happened
                // Replace because when request with same name fails it is pending and keeps the name
                ExistingWorkPolicy.REPLACE
            }

            SyncStatus.PENDING_UPDATE -> {
                //Update operation should happen after create operation
                //this means there may be a pending operation
                //create/update could be pending, delete shouldn´t, so we need to APPEND the work request
                // to ensure it is executed after the pending ones
                //If previous fails, it does not matter if it was an update, but a failed insert
                //means update will also fail, so if previous fails so does this one (no AppendOrReplace)
                ExistingWorkPolicy.APPEND
            }

            SyncStatus.PENDING_DELETE -> {
                //Delete operation should be the last to happen
                //It requires Create operation to succeed, and ignores any update operation pending
                //but if create is still pending and is not executed, then delete is not needed, so
                // Delete can check when executing if Task exists in CrudCrud, and if insert was
                // pending and REPLACED, do no API calls
                ExistingWorkPolicy.REPLACE
            }

            //Should never happen, ensures no work is enqueued
            else -> return
        }

        workManager.enqueueUniqueWork(
            id.toString(),
            workPolicy, taskWorkRequest
        )
    }

    suspend fun sync(id: Long, syncStatusName: String): Int {

        //TODO
        //check syncStatus is valid
        withContext(dispatcher) {
            val entity = taskDAO.get(id) //i think _tasks would not be created if Worker acts with app closed
            if (entity != null) {

            } else {
                //Log and fail
                //return error codes so worker knows string to use
                //Log on worker class
            }
            //get from DB (entity)
            // Switch based on sync status
            //map to DTO
            //send to network
            //clean up
            //api calls with try catch, fail and log on catch

            //if operation is update, check status is pending update,
            // not delete (should never be pending_insert, because an update would overwrite sync Status)
            // if it is delete when updating, no API call but no delete (let the delete Worker do it)

            //if operation is insert, check status is not delete, if delete fail and delete the task without API calls
            //if operation is delete, status is always pending delete, only do API call if DB has the item (could be cancelled if not inserted) AND it has _id (it exists in CrudCrud)

            //Note that status may be SYNCED after a succesfull Insert or Update
        }
    }

    fun reorder(fromID: Long, toID: Long) {

    }

}