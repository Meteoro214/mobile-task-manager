class TaskCollection {
    private val taskList = mutableListOf<Task>()

    /**
     * Add the given task t to the TaskCollection
     */
    fun addTask(t:Task){
        taskList.add(t)
    }

    /**
     * Retrieves the task with the given id or returns null if no such task exists
     */
    fun getTask(id:Int) : Task?{
        //Tasks IDs start on 1
        return taskList.getOrNull(id-1)
    }

    /**
     * Returns an iterator over the TaskCollection
     */
    fun iterator() : Iterator<Task>{
        return taskList.iterator()
    }
}