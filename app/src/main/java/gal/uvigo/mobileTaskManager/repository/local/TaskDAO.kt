package gal.uvigo.mobileTaskManager.repository.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import gal.uvigo.mobileTaskManager.repository.sync.SyncStatus

@Dao
interface TaskDAO {

    /**
     * Expected to return rowID (also used as Task id), will always be last task id +1
     * Expected to return -1 on error
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(task: TaskEntity): Long

    /**
     * Expected to return number of updated rows (1).
     * Stores the CrudCrud generated _id.
     * Call only after sync.
     */
    @Query("UPDATE tasks SET _id = :idCC WHERE id = :id")
    suspend fun syncInsert(id : Long, idCC : String): Int

    /**
     * Expected to return number of updated rows (1).
     */
    @Update
    suspend fun update(updated: TaskEntity): Int

    /**
     * Expected to return number of updated rows (1).
     */
    @Query("UPDATE tasks SET isDone = true WHERE id = :id")
    suspend fun markDone(id: Long): Int

    /**
     * Expected to return number of updated rows (1).
     */
    @Query("UPDATE tasks SET position = :newPos WHERE id = :id")
    suspend fun changePosition(id: Long, newPos : Int): Int

    /**
     * Expected to return number of updated rows (1).
     */
    @Query("UPDATE tasks SET sync_status = :sync WHERE id = :id")
    suspend fun updateSyncStatus(id: Long, sync: SyncStatus = SyncStatus.SYNCED): Int

    /**
     * Expected to return number of updated rows (1).
     * This is a soft delete.
     */
    @Query("UPDATE tasks SET sync_status = 'PENDING_DELETE' WHERE id = :id")
    suspend fun delete(id: Long): Int

    /**
     * Expected to return number of updated rows (1).
     * This is a hard delete.
     * Perform only after sync operations.
     */
    @Delete
    suspend fun trueDelete(task: TaskEntity): Int

    /**
     * Expected to return LiveData of empty list if no values.
     * Will return tasks ordered by category and position
     */
    @Query("SELECT * FROM tasks WHERE sync_status <> 'PENDING_DELETE' ORDER BY category,position ASC")
    fun getAll(): LiveData<List<TaskEntity>>

}