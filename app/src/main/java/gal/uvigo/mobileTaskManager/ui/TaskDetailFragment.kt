package gal.uvigo.mobileTaskManager.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import gal.uvigo.mobileTaskManager.R
import gal.uvigo.mobileTaskManager.databinding.FragmentTaskDetailBinding
import gal.uvigo.mobileTaskManager.model.Task

class TaskDetailFragment : Fragment(R.layout.fragment_task_detail) {

    private val args: TaskDetailFragmentArgs by navArgs()
    private lateinit var binding: FragmentTaskDetailBinding
    private lateinit var navController: NavController


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //another way of binding
        binding = FragmentTaskDetailBinding.bind(view)
        navController = findNavController()
        binding.taskData = args.task
        setHasOptionsMenu(true)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.task_detail_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.editTask -> { //peta aqui y por null en form
            val action = TaskDetailFragmentDirections.openForm(binding.taskData?.id ?: -1)
            navController.navigate(action)
            true
        }

        else -> super.onOptionsItemSelected(item)
    }

    //Because the data is binded from the list, and the list was modified, this isnt actually needed
    override fun onResume() {
        super.onResume()
        val handle = navController.currentBackStackEntry?.savedStateHandle
        if (handle != null) {
            val key = getString(R.string.handle_editedTask_Key)
            val task = handle.get<Task>(key)
            if (task != null) {
                binding.taskData = task
                handle.remove<Task>(key)
            }
        }
    }
}