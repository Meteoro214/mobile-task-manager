package gal.uvigo.mobileTaskManager

import Category
import Task
import TaskCollection
import java.time.LocalDate

class TaskController {
    private val tasks: TaskCollection = TaskCollection()
    private var nextId: Int = 1


    /**
     * Adds a new task with the next available ID, the given title, current date as dueDate, Category other and no description.
     * Returns null if add operation fails, or the task if it succeeds
     */
    fun addTask(
        title: String,
        description: String = "",
        dueDate: LocalDate = LocalDate.now(),
        category: Category = Category.OTHER
    ): Task? {
        try {
            val t = Task(nextId, title, dueDate, category, description)
            tasks.addTask(t)
            nextId++
            return t
        } catch (e: IllegalArgumentException) {
            return null
        }
    }

    /**
     * Marks the task with given id as done. Returns false if it was already done, null if it does not exist or true if it's marked as done successfully
     */
    fun markTaskDone(id: Int): Boolean? = tasks.markTaskDone(id)

    /**
     * Returns an Iterator over the TaskCollection.
     */
    fun getAllTasks(): Iterator<Task> = this.tasks.iterator()

    /**
     * Returns an Iterator over all tasks that fulfill the filter condition
     */
    fun filterTasks(completed: Boolean) : Iterator<Task> = tasks.filter { it.isDone == completed }

}