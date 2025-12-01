package gal.uvigo.mobileTaskManager.ui.tasklist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import gal.uvigo.mobileTaskManager.model.Task
import gal.uvigo.mobileTaskManager.databinding.ItemHeaderBinding
import gal.uvigo.mobileTaskManager.databinding.ItemTaskBinding

class TaskAdapter(onTaskItemClick: (Task) -> Unit) :
    ListAdapter<TaskListItem, RecyclerView.ViewHolder>(TaskListItemDiff) {
    private val _onTaskItemClick: (Task) -> Unit = onTaskItemClick

    class HeaderVH(val headerBinding: ItemHeaderBinding) :
        RecyclerView.ViewHolder(headerBinding.root) {

        fun bind(header: TaskListItem.Header) {
            this.headerBinding.category = header.category
        }

    }

    inner class TaskItemVH(val taskDataBinding: ItemTaskBinding) :
        RecyclerView.ViewHolder(taskDataBinding.root) {

        fun bind(taskItem: TaskListItem.TaskItem) {
            val task = taskItem.task
            this.taskDataBinding.taskData = task
            this.taskDataBinding.root.setOnClickListener { _onTaskItemClick(task) }
            this.taskDataBinding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TaskItemTypes.HEADER.ordinal -> {
                val bind = ItemHeaderBinding.inflate(inflater, parent, false)
                HeaderVH(bind)
            }

            TaskItemTypes.TASK_ITEM.ordinal -> {
                val bind = ItemTaskBinding.inflate(inflater, parent, false)
                TaskItemVH(bind)
            }

            else -> { //should never happen
                val bind = ItemTaskBinding.inflate(inflater, parent, false)
                TaskItemVH(bind)
            }
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        when (holder) {
            is HeaderVH -> holder.bind(getItem(position) as TaskListItem.Header)
            is TaskItemVH -> holder.bind(getItem(position) as TaskListItem.TaskItem)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is TaskListItem.Header -> TaskItemTypes.HEADER.ordinal
            is TaskListItem.TaskItem -> TaskItemTypes.TASK_ITEM.ordinal
        }
    }

    companion object {
        private enum class TaskItemTypes {
            HEADER, TASK_ITEM
        }

        private val TaskListItemDiff = object : DiffUtil.ItemCallback<TaskListItem>() {
            override fun areItemsTheSame(
                oldItem: TaskListItem,
                newItem: TaskListItem
            ): Boolean = when {
                oldItem is TaskListItem.Header && newItem is TaskListItem.Header -> oldItem.category == newItem.category
                oldItem is TaskListItem.TaskItem && newItem is TaskListItem.TaskItem -> oldItem.task.id == newItem.task.id
                else -> false
            }

            override fun areContentsTheSame(
                oldItem: TaskListItem,
                newItem: TaskListItem
            ): Boolean = oldItem == newItem
        }
    }
}