package gal.uvigo.mobileTaskManager.repository.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import gal.uvigo.mobileTaskManager.model.Task
import gal.uvigo.mobileTaskManager.R

/**
 * Class to represent the Database.
 */
@Database(entities = [TaskEntity::class], version = 4, exportSchema = false)
@TypeConverters(LocalDateRoomConverter::class)
abstract class TaskDB : RoomDatabase() {
    abstract fun taskDAO(): TaskDAO

    companion object {
        @Volatile
        private var INSTANCE: TaskDB? = null

        fun getInstance(context: Context): TaskDB =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    TaskDB::class.java, context.getString(R.string.db_name)
                ).fallbackToDestructiveMigration(true)
                    .build().also { INSTANCE = it }
            }
    }

}