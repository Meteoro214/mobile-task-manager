package gal.uvigo.mobileTaskManager

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gal.uvigo.mobileTaskManager.databinding.ItemTaskBinding
import gal.uvigo.mobileTaskManager.model.Task
import gal.uvigo.mobileTaskManager.model.TaskCollection
import java.time.LocalDate

class TaskAdapter(val taskCollection: TaskCollection) :
    RecyclerView.Adapter<TaskAdapter.TaskHolder>() {

    class TaskHolder(val taskDataBinding: ItemTaskBinding) :
        RecyclerView.ViewHolder(taskDataBinding.root) {
        fun bind(task: Task){
            this.taskDataBinding.taskData = task
            //sin la linea: Carga la imagen y el titulo y falla
            this.taskDataBinding.executePendingBindings()
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
        //esta pillando null la task aunque la collection tenga
        val t2 = taskCollection.getTask(position)
        val t =  t2 ?: Task(1,"tesy", LocalDate.now(),isDone = true)
        holder.bind(t)
    }

    override fun getItemCount(): Int = taskCollection.getSize()

}