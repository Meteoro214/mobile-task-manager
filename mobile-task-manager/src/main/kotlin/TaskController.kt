class TaskController {
    private val tasks : TaskCollection = TaskCollection()
    private var nextId : Int = 1


//TODO

    /*
/**
 * Marks the task with given id as done
 */
fun markTaskDone(tasks : List<Task>,id: Int){
    val task = tasks.getOrNull(id)
    if(task == null) println("No task with id $id exists")
    else{
        if(markDone(task )) println("Task with id ${id} was marked as done")
        else println("Task with id ${id} was previously done")
    }
}
*/

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
}
