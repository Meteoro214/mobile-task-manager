package gal.uvigo.mobileTaskManager.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import gal.uvigo.mobileTaskManager.R
import gal.uvigo.mobileTaskManager.model.Category
import gal.uvigo.mobileTaskManager.model.Task
import gal.uvigo.mobileTaskManager.repository.local.TaskDB
import gal.uvigo.mobileTaskManager.repository.local.TaskEntity
import gal.uvigo.mobileTaskManager.repository.network.RetrofitClient
import gal.uvigo.mobileTaskManager.repository.sync.SyncStatus
import gal.uvigo.mobileTaskManager.repository.sync.TaskUploadWorker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate

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
     * A String resource to Log errors on download.
     */
    private val downloadErrorMsg = context.getString(R.string.network_error_down)

    /**
     * A String resource to Log errors.
     */
    private val logTag = context.getString(R.string.Log_Tag)

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
     * Checks if the app is launching for the first time. If it is, downloads tasks from CrudCrud
     */
    suspend fun init(context: Context) {
        val settings = context.getSharedPreferences(
            context.getString(R.string.preferences_file),
            Context.MODE_PRIVATE
        )
        val key = context.getString(R.string.preferences_first_init_key)
        val firstInit = settings.getBoolean(key, true)
        if (firstInit) {
            this.download()
            //set flag so app knows
            settings.edit().putBoolean(key, false).commit()
        }
    }

    /**
     * Downloads all Tasks from CrudCrud and stores them in Room
     */
    private suspend fun download() {
        withContext(dispatcher) {
            try {
                val list = taskService.getAll()
                var entity: TaskEntity
                for (taskDTO in list) {
                    entity = taskMapper.toEntity(taskDTO)
                    if (taskDAO.insert(entity) == -1L
                        || taskDAO.syncInsert(entity.id, taskDTO._id) != 1
                    ) {
                        Log.e(logTag, downloadErrorMsg)
                    }
                }
            } catch (e: Exception) {
                Log.e(logTag, downloadErrorMsg)
                Log.e(logTag, e.toString())
            }
        }
    }

    /**
     * Adds the given task
     */
    suspend fun addTask(task: Task): Long? =
        withContext(dispatcher) {
            //Insert on Room
            val id = taskDAO.insert(taskMapper.toEntity(task, _tasks.size))
            if (id > 0L) {
                //Prepare a WorkRequest to sync
                prepareSync(id, SyncStatus.PENDING_CREATE)
                id
            } else null
        }

    /**
     * Called by sync() to perform a POST to server & update the _id.
     */
    private suspend fun addSync(task: TaskEntity): Int {
        val dto = taskMapper.toDTO(task)
        try {
            val returned = taskService.insert(dto)
            val updatedRows = taskDAO.syncInsert(task.id, returned._id)
            return if (updatedRows == 1) 200 else 500
        } catch (_: Exception) {
            return 400
        }
    }

    /**
     * Retrieves the task with the given id or returns null if no such task exists
     */
    fun get(id: Long): Task? {
        val toRet = _tasks[id]
        return if (toRet == null) null else taskMapper.toTask(toRet)
    }

    /**
     * Retrieves the TaskEntity LiveData from Room via getAll(), then stores it in memory and maps
     * the list to a new LiveData<List<Task>>.
     * The exposed List will be ordered by category and position
     */
    private fun getAll() = taskDAO.getAll().map { entities ->
        val tasks = mutableListOf<Task>()
        _tasks = mutableMapOf() //Empties the map
        for (entity in entities) {
            _tasks[entity.id] = entity
            tasks.add(taskMapper.toTask(entity))
        }
        tasks.toList()
    }

    /**
     * Updates the given task. Returns true if successful
     */
    suspend fun updateTask(updated: Task): Boolean =
        withContext(dispatcher) {
            //Update on Room
            val res = taskDAO.update(
                updated.id, updated.title,
                updated.dueDate ?: LocalDate.now(),
                updated.category ?: Category.OTHER,
                updated.description, updated.isDone
            )
            if (res == 1) {
                //Prepare a WorkRequest to sync
                prepareSync(updated.id, SyncStatus.PENDING_UPDATE)
                true
            } else false
        }

    /**
     * Marks the task with the given ID as done. Returns true if successful
     */
    suspend fun markTaskDone(id: Long): Boolean =
        withContext(dispatcher) {
            //Update on Room
            val res = taskDAO.markDone(id)
            if (res == 1) {
                //Prepare a WorkRequest to sync
                prepareSync(id, SyncStatus.PENDING_UPDATE)
                true
            } else false
        }

    /**
     * Swaps the position of the 2 tasks with the given IDs.
     * Returns true if successful
     */
    suspend fun reorder(fromID: Long, toID: Long): Boolean =
        withContext(dispatcher) {
            //View model already checked if tasks exist
            val fromPos = _tasks[fromID]?.position ?: -1
            val toPos = _tasks[toID]?.position ?: -1
            val res1 = taskDAO.changePosition(fromID, toPos)
            val res2 = if (res1 == 1) taskDAO.changePosition(toID, fromPos) else -1
            if (res1 == res2 && res1 == 1) {
                //Prepare 2 WorkRequests to sync
                prepareSync(fromID, SyncStatus.PENDING_UPDATE)
                prepareSync(toID, SyncStatus.PENDING_UPDATE)
                true
            } else false
        }

    /**
     * Called by sync() to perform a PUT to server & set sync status to SYNCED.
     */
    private suspend fun updateSync(task: TaskEntity): Int {
        val dto = taskMapper.toDTO(task)
        try {
            taskService.update(task._id ?: "", dto)
            val updatedRows = taskDAO.markSynced(dto.id)
            return if (updatedRows == 1) 200 else 500
        } catch (_: Exception) {
            return 400
        }
    }

    /**
     * Deletes the given task. Returns true if successful
     */
    suspend fun deleteTask(task: Task): Boolean =
        withContext(dispatcher) {
            //Update on Room
            val res = taskDAO.delete(task.id)
            if (res == 1) {
                //Prepare a WorkRequest to sync
                prepareSync(task.id, SyncStatus.PENDING_DELETE)
                true
            } else false
        }

    /**
     * Called by sync() to perform a DELETE to server & delete the given task on Room
     * (hard delete,the one no one does).
     */
    private suspend fun deleteSync(task: TaskEntity): Int {
        try {
            taskService.delete(task._id ?: "")
            val updatedRows = taskDAO.trueDelete(task.id)
            return if (updatedRows == 1) 200 else 500
        } catch (_: Exception) {
            return 400
        }
    }

    /**
     * Prepares a WorkRequest to sync Task info.
     */
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

    /**
     * Called by WorkRequest to sync Task information with CruCrud.
     * Will return HTTP Response inspired codes
     * 200 = OK
     * 400 = Service call failed
     * 404 = No task for given ID
     * 409 = Attempted an Invalid operation (Inserting a task pending deletion, Deleting a task not pending deletion, Updating a task pending creation)
     * 500 = Service succeeded, but Room failed to update sync status
     * 501 = Non-Implemented status name
     */
    suspend fun sync(id: Long, syncStatusName: String): Int =
        withContext(dispatcher) {
            val entity = taskDAO.get(id)
            //I think _tasks would not be created if Worker acts with app closed
            //Because LiveData would not be observed
            if (entity != null) {
                //Note that status in Room may be SYNCED when updating after a successful Insert or Update
                when (syncStatusName) {
                    SyncStatus.PENDING_CREATE.name -> {
                        //If Room syncStatus is truly Pending Create, insert
                        //If Room syncStatus is Pending Update, insert because update WorkRequest is appended
                        //If Room syncStatus is Pending Delete, something failed
                        when (entity.syncStatus) {
                            SyncStatus.PENDING_CREATE -> addSync(entity) //Expected behaviour
                            SyncStatus.PENDING_UPDATE -> addSync(entity) //Can happen
                            //Should never happen, delete work request is REPLACE
                            SyncStatus.PENDING_DELETE -> 409
                            SyncStatus.SYNCED -> 409
                            //Not currently implemented
                            else -> 501 //Should never happen
                        }
                    }

                    SyncStatus.PENDING_UPDATE.name -> {
                        //If Room syncStatus is Pending Create or Delete, something failed
                        //If Room syncStatus is Pending Update, all good
                        //If Room syncStatus is SYNCED, Create or Update sync happened after update operation was schedulled
                        when (entity.syncStatus) {
                            SyncStatus.PENDING_CREATE -> 409 //Forbidden
                            SyncStatus.PENDING_UPDATE -> updateSync(entity) //Expected behaviour
                            //Should never happen, delete work request is REPLACE
                            SyncStatus.PENDING_DELETE -> 409
                            SyncStatus.SYNCED -> updateSync(entity) //Can happen
                            //Not currently implemented
                            else -> 501 //Should never happen
                        }
                    }

                    SyncStatus.PENDING_DELETE.name -> {
                        //If Room syncStatus is trully Pending Delete, delete, but only if it was already inserted (_id !=null
                        //Anything else means a task that was marked as deleted was modified, forbidden
                        when (entity.syncStatus) {
                            SyncStatus.PENDING_DELETE -> if (entity._id != null) {
                                deleteSync(entity) //Expected behaviour
                            } else 200 // means task wasnt even inserted yet

                            //Insert or Update syncStatus update happened AFTER Room marked as deleted
                            //Should not happen because of delete WorkRequest being REPLACE
                            SyncStatus.PENDING_CREATE -> 409 // Forbidden
                            SyncStatus.PENDING_UPDATE -> 409 // Forbidden
                            SyncStatus.SYNCED -> 409 // Forbidden
                            //Not currently implemented
                            else -> 501 //Should never happen
                        }
                    }

                    SyncStatus.SYNCED.name -> 409 //Forbidden
                    //Not currently implemented
                    else -> 501 //Should never happen
                }
            } else {
                404
            }
        }

}