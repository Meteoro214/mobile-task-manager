import java.time.LocalDate

class Task(val id: Int, var title : String, var  isDone : Boolean = false,
           var description : String? = null, var dueDate : LocalDate, var category : Category = Category.OTHER){
//Que es modificable, que es nullable, que es private?

    /**
     * Returns this as a String
     */
     override fun toString(): String {
         val sb = StringBuilder()
         sb.append("The task with id = ${this.id} has the following information:\n")
         sb.append("Title = ${this.title}\n")
         sb.append("Is it done? = ${this.isDone}\n")
         sb.append("Due Date = ${this.dueDate.formattedDueDate()}\n")
         sb.append("Category = ${this.category}\n")
         sb.append("Description = ${this.description}\n")
         return sb.toString()
    }

    /**
     * Marks the given task as done, returns true if the task was not previously done
     */
    fun markDone() : Boolean {
        val wasDone = this.isDone
        this.isDone = true;
        return !wasDone;
    }
}





