package gal.uvigo.mobileTaskManager

import gal.uvigo.mobileTaskManager.model.*
import java.time.LocalDate

object TaskRepository {
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
        category: Category = Category.OTHER,
        isDone: Boolean = false
    ): Task? =
        if (title.isBlank()) null
        else {
            try {
                val t = Task(nextId, title, dueDate, category, description, isDone)
                if (tasks.addTask(t)) {
                    nextId++
                    t
                } else null
            } catch (e: IllegalArgumentException) {
                null
            }
        }


    /**
     * Retrieves the task with the given id or returns null if no such task exists
     */
    fun getTaskByID(id: Int): Task? = tasks.getTask(id)

    /**
     * Retrieves the task on the indexed position
     * @throws IndexOutOfBoundsException if index is not on bounds
     */
    fun getTaskByIndex(index: Int): Task = tasks.getTaskByIndex(index)

    fun getSize(): Int = tasks.getSize()
}