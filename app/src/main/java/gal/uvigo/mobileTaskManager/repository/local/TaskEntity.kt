package gal.uvigo.mobileTaskManager.repository.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import gal.uvigo.mobileTaskManager.model.Category
import gal.uvigo.mobileTaskManager.repository.sync.SyncStatus
import java.time.LocalDate

/**
 * Class to represent a Task when stored on Room / local DataBase, with all Task attributes,
 * CrudCrud _id, position for persistent reordering when dragging, and syncStatus for synchronization with CrudCrud.
 */
@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    var title: String,
    @ColumnInfo(name = "due_date") var dueDate: LocalDate,
    var category: Category,
    var description: String,
    var isDone: Boolean,
    var position: Int,
    var _id: String? = null,
    @ColumnInfo(name = "sync_status") var syncStatus: SyncStatus = SyncStatus.PENDING_CREATE
)