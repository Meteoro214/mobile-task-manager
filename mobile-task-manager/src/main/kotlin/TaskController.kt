class TaskController {
    private val tasks : TaskCollection = TaskCollection()
    private var nextId : Int = 1


//TODO

    /**
     * Iterates over the TaskCollection and prints all tasks
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
     * Iterates over the given list and prints tasks that fulfill the filter condition
     */
    fun filterTasks(completed: Boolean) : String{
        val sb = StringBuilder()
        sb.append("Printing all tasks that are ")
        if(!completed) sb.append("NOT ")
        sb.append("completed:\n")

        val filtered = tasks.filter { it[TASK_DONE] == completed }
        if (filtered.isNotEmpty()){
            for (task in filtered){
                println(taskToString(task))
            }





            sb.append("All filtered tasks printed\n")
        }else sb.append("No task exists after filtering\n")
    }
}
