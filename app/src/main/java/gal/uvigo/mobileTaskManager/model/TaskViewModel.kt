package gal.uvigo.mobileTaskManager.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import gal.uvigo.mobileTaskManager.repository.TaskRepository
import gal.uvigo.mobileTaskManager.ui.tasklist.adapter.TaskListItem
import kotlinx.coroutines.launch
import java.time.LocalDate

/**
 * Class to represent a ViewModel for the App.
 */
class TaskViewModel(app: Application) : AndroidViewModel(app) {

    /**
     * The TaskRepository that will handle our Tasks.
     */
    private val repo = TaskRepository(app)

    /**
     * The List of TaskItems, grouped by category and ordered.
     */
    val taskListItems: LiveData<List<TaskListItem>> =
        repo.tasks.map { tasks ->
            if (tasks.isEmpty()) {
                emptyList()
            } else {
                tasks
                    .groupBy { it.category }
                    .flatMap { (category, categoryTasks) ->
                        //Category is never null
                        listOf(TaskListItem.Header(category ?: Category.OTHER)) +
                                categoryTasks.map { TaskListItem.TaskItem(it) }
                    }
            }
        }

    /**
     * Launches initial Repository configuration (ensures tasks are synced on first launch after a reinstall).
     */
    init {
        viewModelScope.launch {
            repo.init(app)
        }
    }

    /**
     * Adds a new task with the next available ID, and the given info.
     * As a default, use an empty title & description, current date as dueDate and Category other.
     * Throws IllegalArgumentException if title is blank or dueDate has passes.
     */
    fun addTask(
        title: String,
        description: String = "",
        dueDate: LocalDate = LocalDate.now(),
        category: Category = Category.OTHER,
        isDone: Boolean = false
    ) {
        if (title.isBlank() || dueDate.isBefore(LocalDate.now())) throw IllegalArgumentException()
        else {
            // ID must be 0 for TaskRepository to issue an ID
            // If not 0, TaskRepository will maintain the given ID
            val t = Task(0, title, dueDate, category, description, isDone)
            addTask(t)
        }
    }

    /**
     * Launches TaskRepository add operation.
     */
    private fun addTask(t: Task) {
        viewModelScope.launch {
            repo.addTask(t)
        }
    }

    /**
     * Updates the given task maintaining order. Replaces the existing task with the same id on the Task List with the given task.
     * Returns true if update is correct, false if no task with the task id exists.
     * Throws IllegalArgumentException if updated values are not allowed.
     */
    fun updateTask(updated: Task): Boolean {
        val current = this.get(updated.id)
        if (current == null) return false
        if (updated.title.isBlank()
            || updated.dueDate?.isBefore(LocalDate.now()) ?: true
            || updated.category == null
        )
            throw IllegalArgumentException()
        else {
            current.isDone = updated.isDone
            current.category = updated.category
            current.title = updated.title
            current.description = updated.description
            current.dueDate = updated.dueDate
            update(updated)
            return true
        }
    }

    /**
     * Launches TaskRepository update operation.
     */
    private fun update(t: Task) {
        viewModelScope.launch {
            repo.updateTask(t)
        }
    }

    /**
     * Marks the task with given id as done. Returns false if it was already done,
     * null if it does not exist or true if it's marked as done successfully.
     */
    fun markTaskDone(id: Long): Boolean? {
        val current = this.get(id)
        return if (current == null) {
            null
        } else {
            if (current.isDone) {
                false
            } else {
                current.isDone = true
                this.markDone(id)
                true
            }
        }
    }

    /**
     * Launches TaskRepository markTaskDone operation.
     */
    private fun markDone(id: Long) {
        viewModelScope.launch {
            repo.markTaskDone(id)
        }
    }

    /**
     * Swaps the position of the 2 tasks with the given IDs (Memory only).
     */
    fun reorder(fromID: Long, toID: Long): Boolean {
        if (get(fromID) == null || get(toID) == null) return false
        changePosition(fromID, toID)
        return true
    }

    /**
     * Launches TaskRepository reorder operation.
     */
    private fun changePosition(fromID: Long, toID: Long) {
        viewModelScope.launch {
            repo.reorder(fromID, toID)
        }
    }

    /**
     * Launches TaskRepository commit reordering operation.
     */
    fun persistOrder() {
        viewModelScope.launch {
            repo.commitReordering()
        }
    }

    /**
     * Retrieves the task with the given id or returns null if no such task exists.
     */
    fun get(id: Long): Task? = repo.get(id)

    /**
     * Deletes the task with the given ID, if it exists.
     * Returns true if the task gets deleted, or false if it doesn't exist.
     */
    fun deleteTask(id: Long): Boolean {
        val t = this.get(id)
        if (t != null) {
            this.delete(t)
        }
        return t != null
    }

    /**
     * Launches TaskRepository delete operation.
     */
    private fun delete(t: Task) {
        viewModelScope.launch {
            repo.deleteTask(t)
        }
    }

}