package gal.uvigo.mobileTaskManager.repository.network

import com.squareup.moshi.JsonClass
import gal.uvigo.mobileTaskManager.model.Category
import java.time.LocalDate

/**
 * Class to represent a Task when retrieved from CrudCrud, with all TaskSendDTO attributes
 * and CrudCrud _id
 */
@JsonClass (generateAdapter = true)
data class TaskGetDTO(
    val _id: String,
    val id: Long,
    var title: String,
    var dueDate: LocalDate,
    var category: Category,
    var description: String,
    var isDone: Boolean,
    var order: Int
)

