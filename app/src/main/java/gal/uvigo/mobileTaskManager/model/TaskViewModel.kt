package gal.uvigo.mobileTaskManager.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import gal.uvigo.mobileTaskManager.data_model.Category
import gal.uvigo.mobileTaskManager.data_model.Task
import kotlinx.coroutines.launch
import java.time.LocalDate

class TaskViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = TaskRepository(app)

    val tasks: LiveData<List<Task>>
        get() = repo.tasks


    /**
     * Adds a new task with the next available ID, and the given info.
     * As a default, use an empty title & description, current date as dueDate and Category other.
     * Returns null if add operation fails, or the task if it succeeds
     */
    fun addTask(
        title: String,
        description: String = "",
        dueDate: LocalDate = LocalDate.now(),
        category: Category = Category.OTHER,
        isDone: Boolean = false
    ) {
        if (title.isBlank()) throw IllegalArgumentException()
        else {
            // ID MUST BE 0 OR AUTOINCREMENTAL PK WILL TAKE THE PLACEHOLDER VALUE!!!
            val t = Task(0, title, dueDate, category, description, isDone)
            addTask(t)
        }
    }

    private fun addTask(t: Task) {
        viewModelScope.launch {
            repo.addTask(t)
        }
    }

    /**
     * Updates the given task maintaining order. Replaces the existing task with the same id on the Task List with the given task.
     * Returns true if update is correct, false if no task with the task id exists.
     */
    fun updateTask(updated: Task): Boolean {
        val current = this.get(updated.id)
        return if (current == null || updated.title.isBlank()
            || updated.dueDate == null || updated.category == null
        ) false
        else {
            update(updated)
            true
        }
    }

    private fun update(t: Task) {
        viewModelScope.launch {
            repo.updateTask(t)
        }
    }

    /**
     * Retrieves the task with the given id or returns null if no such task exists
     */
    fun get(id: Long): Task?= repo.get(id)

    /**
     * Deletes the task with the given ID, if it exists.
     * Returns true if the task gets deleted, or false if it doesn't exist
     */
    fun deleteTask(id: Long): Boolean {
        val t = this.get(id)
        if (t != null) {
            this.delete(t)
        }
        return t != null
    }

    private fun delete(t: Task) {
        viewModelScope.launch {
            repo.deleteTask(t)
        }
    }

}