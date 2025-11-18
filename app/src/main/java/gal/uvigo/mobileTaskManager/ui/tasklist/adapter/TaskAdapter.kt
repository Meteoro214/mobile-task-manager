package gal.uvigo.mobileTaskManager.ui.tasklist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import gal.uvigo.mobileTaskManager.data_model.Task

class TaskAdapter(onTaskItemClick: (Task) -> Unit) :
    ListAdapter<TaskListItem, RecyclerView.ViewHolder>(TaskListItemDiff) {
    private val _onTaskItemClick: (Task) -> Unit = onTaskItemClick


    class TaskHolder(val taskDataBinding: ItemTaskBinding) :
        RecyclerView.ViewHolder(taskDataBinding.root) {

        fun bind(task: Task) {
            this.taskDataBinding.taskData = task
            this.taskDataBinding.executePendingBindings()
            this.taskDataBinding.root.setOnClickListener { //el listener pasarlo como argumento del adapter
                this.taskDataBinding.root.findNavController()
                    .navigate(TaskListFragmentDirections.checkTaskDetails(task.id))
            }
        }
    }

    override fun onCreateViewHolder( //cambiar tipo de retorno
        parent: ViewGroup,
        viewType: Int
    ): TaskHolder { //variar el metodo segun el viewType
        //Creates a data bind
        val bind = ItemTaskBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return TaskHolder(bind)
    }

    override fun onBindViewHolder(
        holder: TaskHolder,
        position: Int
    ) {
        //Should never be out of bounds
        val t: Task = tasks[position]
        holder.bind(t)
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