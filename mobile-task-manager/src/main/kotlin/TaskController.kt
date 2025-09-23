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
            sb.append("Printing all Tasks:")
            val it : Iterator<Task> = this.tasks.iterator()
            while (it.hasNext()) {
                sb.append(it.next().toString()+"\n")
            }
            sb.append("All Tasks printed")
        }else sb.append("There are no tasks to print")
        return sb.toString()
    }


}
