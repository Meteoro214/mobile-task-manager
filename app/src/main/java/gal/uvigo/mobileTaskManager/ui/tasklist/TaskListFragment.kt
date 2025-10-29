package gal.uvigo.mobileTaskManager.ui.tasklist

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import gal.uvigo.mobileTaskManager.R
import gal.uvigo.mobileTaskManager.databinding.FragmentTaskListBinding
import gal.uvigo.mobileTaskManager.model.TaskViewModel

class TaskListFragment : Fragment(R.layout.fragment_task_list) {

    private val taskViewModel: TaskViewModel by activityViewModels()
    private lateinit var binding: FragmentTaskListBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //another way of binding
        binding = FragmentTaskListBinding.bind(view)
        binding.taskRV.layoutManager = LinearLayoutManager(context)
        val adapter = TaskAdapter(taskViewModel.tasks.value.orEmpty())
        binding.taskRV.adapter = adapter
        taskViewModel.tasks.observe(viewLifecycleOwner){
            tasks -> adapter.submitTaskList(tasks)
        }

        setHasOptionsMenu(true)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.task_list_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

}