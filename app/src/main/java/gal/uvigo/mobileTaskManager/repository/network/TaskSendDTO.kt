package gal.uvigo.mobileTaskManager.repository.network

import com.squareup.moshi.JsonClass
import gal.uvigo.mobileTaskManager.model.Category
import java.time.LocalDate

/**
 * Class to represent a Task when sent to CrudCrud, with all Task attributes
 * and order for persistent reordering when dragging,but no syncState
 * (because if it is stored on CrudCrud we assume it is synced)
 * and no CrudCrud _id (because CrudCrud does not allow POST or PUT bodies to contain _id)
 */
@JsonClass(generateAdapter = true)
data class TaskSendDTO(
    val id: Long,
    var title: String,
    var dueDate: LocalDate,
    var category: Category,
    var description: String,
    var isDone: Boolean,
    var order: Int
)