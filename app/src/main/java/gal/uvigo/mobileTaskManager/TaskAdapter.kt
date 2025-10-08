package gal.uvigo.mobileTaskManager

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gal.uvigo.mobileTaskManager.databinding.ItemTaskBinding
import gal.uvigo.mobileTaskManager.model.TaskCollection

class TaskAdapter(val taskCollection: TaskCollection) :
    RecyclerView.Adapter<TaskAdapter.TaskHolder>() {

    class TaskHolder(val taskDataBinding: ItemTaskBinding) :
        RecyclerView.ViewHolder(taskDataBinding.root) {
        //Uses data binding
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TaskHolder {
        //Creates a data bind
        val bind = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskHolder(bind)
    }

    override fun onBindViewHolder(
        holder: TaskHolder,
        position: Int
    ) {
        holder.taskDataBinding.taskData = taskCollection.getTask(position)
        //sin la linea: Carga la imagen y el titulo y falla
        holder.taskDataBinding.executePendingBindings()
        //con la linea : Falla inmediatamente
    }

    override fun getItemCount(): Int = taskCollection.getSize()

}