package gal.uvigo.mobileTaskManager.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import gal.uvigo.mobileTaskManager.DataGenerator
import gal.uvigo.mobileTaskManager.R
import gal.uvigo.mobileTaskManager.TaskAdapter
import gal.uvigo.mobileTaskManager.TaskController
import gal.uvigo.mobileTaskManager.databinding.FragmentTaskListBinding

class TaskListFragment : Fragment(R.layout.fragment_task_list) {

    private val controller: TaskController = TaskController()
    private lateinit var binding : FragmentTaskListBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //another way of binding
        binding = FragmentTaskListBinding.bind(view)
        loadDummyData()

        binding.taskRV.layoutManager = LinearLayoutManager(context)
        binding.taskRV.adapter = TaskAdapter(controller.getCollection())
    }

    fun loadDummyData(){
        DataGenerator.createDumbData(controller.getCollection())
    }
}