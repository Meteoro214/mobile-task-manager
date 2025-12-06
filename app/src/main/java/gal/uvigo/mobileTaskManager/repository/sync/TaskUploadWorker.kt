package gal.uvigo.mobileTaskManager.repository.sync

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import gal.uvigo.mobileTaskManager.R
import gal.uvigo.mobileTaskManager.repository.TaskRepository

class TaskUploadWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        //get the keys as resources
        val id = inputData.getLong(applicationContext.getString(R.string.Task_ID_Key), -1)
        val statusName = inputData.getString(applicationContext.getString(R.string.Task_Status_Key))

        if (id == -1L || statusName == null) return Result.failure()


        val repo = TaskRepository(applicationContext)

        val responseCode = repo.sync(id, statusName)

        val tag = applicationContext.getString(R.string.Log_Tag)

        return when (responseCode) {
            200 -> { //Ok
                Log.i(tag, applicationContext.getString(R.string.worker_200_code))
                Result.success()
            }

            400 -> { //Service Call Failed
                if (isStopped) { //Constraints are not met, no WiFi
                    Log.e(tag, applicationContext.getString(R.string.worker_400_down))
                    Result.failure()
                } else { // CrudCrud down or any exception when trying
                    Log.e(tag, applicationContext.getString(R.string.worker_400_code))
                    Result.failure()
                }
            }

            404 -> { //No task for given ID
                Log.e(tag, applicationContext.getString(R.string.worker_404_code))
                Result.failure()
            }

            409 -> { // Attempted an Invalid operation
                Log.e(tag, applicationContext.getString(R.string.worker_409_code))
                Result.failure()
            }

            500 -> { //Room could not sync status
                Log.e(tag, applicationContext.getString(R.string.worker_500_code))
                Result.failure()
            }

            501 -> { //Not Implemented
                Log.e(tag, applicationContext.getString(R.string.worker_501_code))
                Result.failure()
            }

            else -> { //Unexpected value
                Log.e(tag, applicationContext.getString(R.string.worker_XXX_code))
                Result.failure()
            }
        }
    }
}