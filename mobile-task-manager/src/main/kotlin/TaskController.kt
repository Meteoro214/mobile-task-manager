import java.time.LocalDate
import java.time.DateTimeException

class TaskController {
    private val tasks : TaskCollection = TaskCollection()
    private var nextId : Int = 1


    fun addTask(title : String, description : String,dueDate : LocalDate,  category : Category) : Task?{
        //constructor
        try {
            val t : Task = Task(nextId,title,dueDate,category,description)
            tasks.addTask(t)
            nextId++
            return t
        } catch(e : IllegalArgumentException){
            return null
        }
    }

    /**
    * Marks the task with given id as done. Returns false if it was already done, null if it does not exist or true if it's marked as done successfully
    */
    fun markTaskDone(id: Int) : Boolean? = tasks.markTaskDone(id)

    /**
     * Iterates over the TaskCollection and returns a String with all tasks printed
     */
    fun getAllTasks() : String{
        val sb = StringBuilder()
        if (!this.tasks.isEmpty()){
            sb.append("Printing all Tasks:\n")
            val it : Iterator<Task> = this.tasks.iterator()
            while (it.hasNext()) {
                sb.append(it.next().toString()+"\n")
            }
            sb.append("All Tasks printed\n")
        }else sb.append("There are no tasks to print\n")
        return sb.toString()
    }

    /**
     * Iterates over the given list and returns a String with all tasks that fulfill the filter condition
     */
    fun filterTasks(completed: Boolean) : String{
        val sb = StringBuilder()
        sb.append("Printing all tasks that are ")
        if(!completed) sb.append("NOT ")
        sb.append("completed:\n")

        val filtered : Iterator<Task> = tasks.filter { it.isDone == completed }
        if(filtered.hasNext()){ //At least 1 Task in the iterator
            while (filtered.hasNext()) {
                sb.append(filtered.next().toString()+"\n")
            }
            sb.append("All filtered tasks printed\n")
        }else sb.append("No task exists after filtering\n")
        return sb.toString()
    }

    fun createDate(year:Int,month : Int, day : Int) : LocalDate? {
//        val year = LocalDate.now().year
        var date : LocalDate?
        try {
            date = LocalDate.of(year, month, day)
        } catch (e : DateTimeException){
            date = null
        }
        return date
    }

}
