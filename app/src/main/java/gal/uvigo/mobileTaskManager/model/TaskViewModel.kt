package gal.uvigo.mobileTaskManager.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import gal.uvigo.mobileTaskManager.data_model.Category
import gal.uvigo.mobileTaskManager.data_model.Task
import java.time.LocalDate
import kotlin.collections.orEmpty
import kotlin.collections.toMutableList

class TaskViewModel : ViewModel() {

    private val _tasks = MutableLiveData<List<Task>>(emptyList())
    val tasks: LiveData<List<Task>>
        get() = _tasks

    private var nextId: Int = 1

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
    ): Task? =
        if (title.isBlank()) null
        else {
            try {
                val t = Task(nextId, title, dueDate, category, description, isDone)
                if (this.addTask(t)) {
                    t
                } else null
            } catch (_: IllegalArgumentException) {
                null
            }
        }

    /**
     * Add the given task t to the Task List. Must have a title and no null values
     * Returns true if added. Will not add if empty title or null values
     */
    fun addTask(t: Task): Boolean =
        if (t.title.isBlank() || t.dueDate == null || t.category == null) false
        else {
            val current = this._tasks.value.orEmpty().toMutableList()
            current.add(t)
            nextId++
            _tasks.value = current
            true
        }

    /**
     * Updates the given task maintaining order. Replaces the existing task with the same id on the Task List with the given task.
     * Returns true if update is correct, false if no task with the task id exists.
     */
    fun updateTask(updated: Task): Boolean {
        val index = this.getIndex(updated.id)
        return if (index == -1 || updated.title.isBlank()
            || updated.dueDate == null || updated.category == null
        ) false
        else {
            val current = this._tasks.value.orEmpty().toMutableList()
            current.removeAt(index)
            //Maintains order
            current.add(index,updated)
            _tasks.value = current
            true
        }
    }

    /**
     * Retrieves the task with the given id or returns null if no such task exists
     */
    fun getTaskByID(id: Int): Task? {
        val index: Int = this.getIndex(id)
        return if (index == -1) null else getTaskByIndex(index)
    }

    /**
     * Retrieves the task on the indexed position
     * @throws IndexOutOfBoundsException if index is not on bounds
     */
    fun getTaskByIndex(index: Int): Task = tasks.value.orEmpty()[index]

    /**
     * Deletes the task with the given ID, if it exists.
     * Returns true if the task gets deleted, or false if it doesn't exist
     */
    fun deleteTask(id: Int): Boolean {
        val current = _tasks.value.orEmpty().toMutableList()
        val toRet = current.removeIf { it.id == id }
        _tasks.value = current
        return toRet
    }

    /**
     * Returns the number of Tasks stored
     */
    fun size(): Int = tasks.value?.size ?: 0

    /**
     * Checks whenever the internal Task List is empty
     */
    fun isEmpty(): Boolean = this.size() == 0

    /**
     * Auxiliary private method to find the index of a Task with the given id.
     * Returns the index, or -1 if the task does not exist
     */
    private fun getIndex(id: Int): Int =
        tasks.value?.indexOfFirst { it.id == id } ?: -1

}