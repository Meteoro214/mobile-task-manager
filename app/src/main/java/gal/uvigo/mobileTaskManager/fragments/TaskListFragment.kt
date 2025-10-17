package gal.uvigo.mobileTaskManager.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import gal.uvigo.mobileTaskManager.DataGenerator
import gal.uvigo.mobileTaskManager.R
import gal.uvigo.mobileTaskManager.TaskAdapter
import gal.uvigo.mobileTaskManager.TaskController
import gal.uvigo.mobileTaskManager.databinding.FragmentTaskListBinding

class TaskListFragment : Fragment(R.layout.fragment_task_list) {

    private val controller: TaskController = TaskController()
    private lateinit var binding: FragmentTaskListBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //another way of binding
        binding = FragmentTaskListBinding.bind(view)
        loadDummyData()
        binding.taskRV.layoutManager = LinearLayoutManager(context)
        binding.taskRV.adapter = TaskAdapter(controller.getCollection())
        setHasOptionsMenu(true)
    }

    fun loadDummyData() {
        DataGenerator.createDumbData(controller.getCollection())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.app_bar_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.menu_add_btn -> {
                findNavController().navigate(TaskListFragmentDirections.addTask())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }


}