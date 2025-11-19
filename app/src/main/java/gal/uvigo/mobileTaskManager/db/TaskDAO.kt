package gal.uvigo.mobileTaskManager.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import gal.uvigo.mobileTaskManager.data_model.Task

@Dao
interface TaskDAO {

    //Returns rowID (equivalent to taskID) will always give the last task ID+1
    //Should return -1 on error
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(task: Task): Long

    //Returns number of updated rows
    @Update
    suspend fun update(updated: Task): Int

    //Returns number of updated rows
    @Query("UPDATE tasks SET isDone = true WHERE id = :id")
    suspend fun markDone(id : Long) : Int

    //Returns number of deleted rows
    @Delete
    suspend fun delete(task: Task): Int

    //Returns empty list if no data
    @Query("SELECT * FROM tasks ORDER BY id ASC")
    fun getAll(): LiveData<List<Task>>
}