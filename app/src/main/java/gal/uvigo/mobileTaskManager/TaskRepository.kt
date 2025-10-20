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
     * Edits a task with the given ID (ID is not editable).
     * Returns true if edit succeeds, false if it fails, or null if any parameter is not valid
     */
    fun editTask(
        id: Int,
        title: String,
        description: String = "",
        dueDate: LocalDate = LocalDate.now(),
        category: Category = Category.OTHER,
        isDone : Boolean
    ): Boolean? {
        try {
            val t = Task(id, title, dueDate, category, description,isDone)
            return tasks.editTask(t)
        } catch (e: IllegalArgumentException) {
            return null
        }
    }

    /**
     * Retrieves the task with the given id or returns null if no such task exists
     */
    fun getTaskByID(id : Int) : Task? = tasks.getTask(id)

    /**
     * Retrieves the task on the indexed position
     * @throws IndexOutOfBoundsException if index is not on bounds
     */
    fun getTaskByIndex(index : Int) : Task = tasks.getTaskByIndex(index)

    fun getSize() : Int = tasks.getSize()
}