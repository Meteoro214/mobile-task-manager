package gal.uvigo.mobileTaskManager

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gal.uvigo.mobileTaskManager.databinding.ItemTaskBinding
import gal.uvigo.mobileTaskManager.model.Task
import gal.uvigo.mobileTaskManager.model.TaskCollection

class TaskAdapter(val taskCollection: TaskCollection) :
    RecyclerView.Adapter<TaskAdapter.TaskHolder>() {

    class TaskHolder(val taskDataBinding: ItemTaskBinding) :
        RecyclerView.ViewHolder(taskDataBinding.root) {
        fun bind(task: Task?){
            this.taskDataBinding.taskData = task
            //sin la linea: Carga la imagen y el titulo y falla
     //       this.taskDataBinding.executePendingBindings()
            //con la linea : Falla inmediatamente
        }
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
        //RV has 0-based indexing, Tasks have 1-bases IDs
        val t = taskCollection.getTask(position+1)
        holder.bind(t)
    } //Debug, sin linea de execute hace llamadas aqui, tal vez problema nativo????

    override fun getItemCount(): Int = taskCollection.getSize()

}