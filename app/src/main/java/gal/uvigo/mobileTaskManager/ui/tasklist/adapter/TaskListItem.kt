package gal.uvigo.mobileTaskManager.ui.tasklist.adapter

import gal.uvigo.mobileTaskManager.data_model.Category
import gal.uvigo.mobileTaskManager.data_model.Task

sealed class TaskListItem() {
    data class Header(val cat : Category) : TaskListItem()
    data class TaskItem(val task : Task) : TaskListItem()
}