package gal.uvigo.mobileTaskManager.networking

import com.squareup.moshi.JsonClass
import gal.uvigo.mobileTaskManager.data_model.Category
import gal.uvigo.mobileTaskManager.data_model.Task
import java.time.LocalDate

//TaskCrudCrud
//Used to hold _id to use with CrudCrud
@JsonClass (generateAdapter = true)
data class TaskCC(
    val _id: String,
    val id: Long,
    val title: String,
    val dueDate: LocalDate,
    val category: Category,
    val description: String,
    val isDone: Boolean
){
 constructor(task : Task) : this("",task.id,task.title,task.dueDate ?: LocalDate.now(),task.category ?: Category.OTHER,task.description,task.isDone)

    fun getTask(): Task = Task(this.id,this.title,this.dueDate,this.category,this.description,this.isDone)

    fun isTask(other : Task) : Boolean =  this.id == other.id

    fun isSameTask(other : Task) : Boolean =  this.getTask() == other

}

