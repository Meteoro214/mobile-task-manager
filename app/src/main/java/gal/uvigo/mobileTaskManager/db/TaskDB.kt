package gal.uvigo.mobileTaskManager.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import gal.uvigo.mobileTaskManager.data_model.Task

@Database (entities = [Task::class], version=1)
abstract class TaskDB : RoomDatabase() {
    abstract fun taskDAO : TaskDAO

    companion object {
        @Volatile
        private var INSTANCE: TaskDB? = null

        fun getInstance(context: Context): TaskDB =
             INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    TaskDB::class.java, "task_db"
                ).fallbackToDestructiveMigration()
                    .build().also { INSTANCE = it }
            }
    }

}