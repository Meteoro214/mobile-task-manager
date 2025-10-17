package gal.uvigo.mobileTaskManager

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import gal.uvigo.mobileTaskManager.databinding.ItemTaskBinding
import gal.uvigo.mobileTaskManager.fragments.TaskListFragmentDirections
import gal.uvigo.mobileTaskManager.model.Task
import gal.uvigo.mobileTaskManager.model.TaskCollection
import java.time.LocalDate

class TaskAdapter(val taskCollection: TaskCollection) :
    RecyclerView.Adapter<TaskAdapter.TaskHolder>() {

    class TaskHolder(val taskDataBinding: ItemTaskBinding) :
        RecyclerView.ViewHolder(taskDataBinding.root) {

        fun bind(task: Task){
            this.taskDataBinding.taskData = task
            this.taskDataBinding.executePendingBindings()
            this.taskDataBinding.root.setOnClickListener {
                this.taskDataBinding.root.findNavController().navigate(TaskListFragmentDirections.checkTaskDetails(task))
            }
        }
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TaskHolder {
        //Creates a data bind
        val bind = ItemTaskBinding.inflate(LayoutInflater.from(parent.context),
            parent, false)
        return TaskHolder(bind)
    }

    override fun onBindViewHolder(
        holder: TaskHolder,
        position: Int
    ) {
        //RV has 0-based indexing, Tasks have 1-bases IDs
        val t : Task? = taskCollection.getTask(position+1)
        val t2 = if (t==null) taskCollection.getTaskByIndex(itemCount) else t
        //Should never be null, is only null on a certain error, TODO fix
        holder.bind(t2)
    }

    override fun getItemCount(): Int = taskCollection.getSize()

}