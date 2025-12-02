package gal.uvigo.mobileTaskManager.ui.tasklist.adapter

import gal.uvigo.mobileTaskManager.model.Category
import gal.uvigo.mobileTaskManager.model.Task

/**
 * Sealed class to handle UI TaskListItems.
 * Has 2 classes:
 * - Header, which represents a Header with a Category.
 * - TaskItem, which holds a Task.
 */
sealed class TaskListItem() {
    data class Header(val category: Category) : TaskListItem()
    data class TaskItem(val task: Task) : TaskListItem()
}