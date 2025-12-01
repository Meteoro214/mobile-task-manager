package gal.uvigo.mobileTaskManager.ui.tasklist.adapter

import gal.uvigo.mobileTaskManager.model.Category
import gal.uvigo.mobileTaskManager.model.Task

sealed class TaskListItem() {
    data class Header(val category: Category) : TaskListItem()
    data class TaskItem(val task: Task) : TaskListItem()
}