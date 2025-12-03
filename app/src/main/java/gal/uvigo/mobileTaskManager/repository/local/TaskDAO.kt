package gal.uvigo.mobileTaskManager.repository.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import gal.uvigo.mobileTaskManager.model.Category
import java.time.LocalDate

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
     * Perform only after insert sync operations.
     */
    @Query("UPDATE tasks SET _id = :idCC, sync_status = 'SYNCED' WHERE id = :id")
    suspend fun syncInsert(id: Long, idCC: String): Int

    /**
     * Expected to return a single instance of TaskEntity or Null if no results.
     */
    @Query("SELECT * FROM tasks WHERE id = :id")
    fun get(id: Long): TaskEntity?

    /**
     * Expected to return number of updated rows (1).
     */
    @Query(
        "UPDATE tasks SET id = :id,title=:title," +
                "due_date=:dueDate, category = :category," +
                " description=:description, isDone = :isDone," +
                "sync_status = 'PENDING_UPDATE' WHERE id = :id"
    )
    suspend fun update(
        id: Long, title: String,
        dueDate: LocalDate, category: Category,
        description: String, isDone: Boolean
    ): Int

    /**
     * Expected to return number of updated rows (1).
     */
    @Query("UPDATE tasks SET isDone = true, sync_status = 'PENDING_UPDATE' WHERE id = :id")
    suspend fun markDone(id: Long): Int

    /**
     * Expected to return number of updated rows (1).
     */
    @Query("UPDATE tasks SET position = :newPos, sync_status = 'PENDING_UPDATE' WHERE id = :id")
    suspend fun changePosition(id: Long, newPos: Int): Int

    /**
     * Expected to return number of updated rows (1).
     * Perform only after update sync operations.
     */
    @Query("UPDATE tasks SET sync_status = 'SYNCED' WHERE id = :id")
    suspend fun markSynced(id: Long): Int

    /**
     * Expected to return number of updated rows (1).
     * This is a soft delete.
     */
    @Query("UPDATE tasks SET sync_status = 'PENDING_DELETE' WHERE id = :id")
    suspend fun delete(id: Long): Int

    /**
     * Expected to return number of updated rows (1).
     * This is a hard delete.
     * Perform only after delete sync operations.
     */
    @Query("DELETE FROM tasks WHERE id = :id")
    suspend fun trueDelete(id: Long): Int

    /**
     * Expected to return LiveData of empty list if no values.
     * Will return tasks ordered by category and position
     */
    @Query(
        "SELECT * FROM tasks WHERE sync_status <> 'PENDING_DELETE' " +
                "ORDER BY category,position ASC"
    )
    fun getAll(): LiveData<List<TaskEntity>>

}