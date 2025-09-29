import java.time.LocalDate

class Task(val id : Int, title : String, dueDate : LocalDate,
           var category : Category = Category.OTHER, var description : String = "",var isDone : Boolean = false) {

    var title : String = title.trim()
        set(value){
            require(!value.trim().isEmpty()) {"title must not be empty"}
            field = value
        }
    var dueDate : LocalDate = dueDate
        set(value){
            require(dueDate.isFutureDate()) { "due date must not be in the past" }
            field = value
        }

    init{
        require( id > 0) {"ID must be above 0"}
        require(!title.trim().isEmpty()) {"title must not be empty"}
        require(dueDate.isFutureDate()) { "due date must not be in the past" }
    }

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
        this.isDone = true
        return !wasDone
    }
}