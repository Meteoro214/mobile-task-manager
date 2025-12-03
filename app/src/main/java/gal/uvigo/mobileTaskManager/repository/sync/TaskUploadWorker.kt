package gal.uvigo.mobileTaskManager.repository.sync

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class TaskUploadWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {


    override suspend fun doWork(): Result {
        //read input
        //get context
        //get repo
        //launch operation
        //read return code
        //log if error
        // success or fail
    }
}