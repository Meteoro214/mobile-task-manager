package gal.uvigo.mobileTaskManager.repository.sync

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import gal.uvigo.mobileTaskManager.repository.TaskRepository
import gal.uvigo.mobileTaskManager.R

class TaskUploadWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {


    override suspend fun doWork(): Result {
        //get the keys as resources
        val id = inputData.getLong(applicationContext.getString(R.string.Task_ID_Key),-1)
        val statusName = inputData.getString(applicationContext.getString(R.string.Task_Status_Key))

        if(id == -1L || statusName == null) return Result.failure()



        val repo = TaskRepository(applicationContext)

        //launch operation
        //read return code
        //log if error
        // success or fail
        //check if isStopped on failure = retry
    }
}