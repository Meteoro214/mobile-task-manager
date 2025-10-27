package gal.uvigo.mobileTaskManager

class TaskCollection {
    private val taskList = mutableListOf<Task>()

    /**
     * Add the given task t to the TaskCollection. Must have a title
     * Returns true if added. Will not add if empty title
     */
    fun addTask(t: Task): Boolean = if (t.title.isEmpty()) false else taskList.add(t)


    /**
     * Retrieves the task with the given id or returns null if no such task exists
     */
    fun getTask(id: Int): Task? {
        val index: Int = this.getIndex(id)
        return if (index == -1) null else taskList[index]
    }

    /**
     * Retrieves the task on the indexed position
     * @throws IndexOutOfBoundsException if index is not on bounds
     */
    fun getTaskByIndex(index: Int): Task = taskList[index]

    /**
     * Deletes the task with the given ID, if it exists.
     * Returns true if the task gets deleted, or false if it doesn't exist
     */
    fun deleteTask(id: Int): Boolean {
        val index = this.getIndex(id)
        if (index != -1) taskList.removeAt(index)
        return index != -1
    }

    /**
     * Returns the number of Tasks in the TaskCollection
     */
    fun getSize(): Int {
        return taskList.size
    }

    /**
     * Checks whenever the TaskCollection is empty
     */
    fun isEmpty(): Boolean = this.getSize() == 0

    /**
     * Returns an iterator over the TaskCollection
     */
    fun iterator(): Iterator<Task> {
        return taskList.iterator()
    }

    /**
     * Filters the TasksCollection according to the given filtering function and returns an iterator over the filtered collection
     */
    fun filter(filter: (Task) -> Boolean): Iterator<Task> =
        taskList.asSequence().filter(filter).toList().iterator()

    /**
     * Auxiliary private method to find the index of a Task with the given id.
     * Returns the index, or -1 if the task does not exist
     */
    private fun getIndex(id: Int): Int {
        var i = 0
        val size = taskList.size
        while (i < size && taskList[i].id != id) {
            i++
        }
        return if (i < size) i else -1
    }
}