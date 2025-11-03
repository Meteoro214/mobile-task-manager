package gal.uvigo.mobileTaskManager.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import gal.uvigo.mobileTaskManager.R
import gal.uvigo.mobileTaskManager.databinding.FragmentTaskDetailBinding
import gal.uvigo.mobileTaskManager.data_model.Task
import gal.uvigo.mobileTaskManager.model.TaskViewModel
import kotlin.getValue

class TaskDetailFragment : Fragment(R.layout.fragment_task_detail) {

    private val args: TaskDetailFragmentArgs by navArgs()
    private lateinit var binding: FragmentTaskDetailBinding
    private lateinit var navController: NavController
    private val viewModel: TaskViewModel by activityViewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //another way of binding
        binding = FragmentTaskDetailBinding.bind(view)
        navController = findNavController()
        binding.taskData = viewModel.getTaskByID(args.taskID)
        setHasOptionsMenu(true)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.task_detail_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.editTask -> {
            val action = TaskDetailFragmentDirections.openForm(binding.taskData?.id ?: -1)
            navController.navigate(action)
            true
        }

        R.id.deleteTask -> {
            val deleted = viewModel.deleteTask(binding.taskData?.id ?: -1)

            if(deleted){
                Toast.makeText(requireContext(),
                    getString(R.string.check_delete_OK_msg),
                    Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(requireContext(),
                    getString(R.string.check_delete_error_msg),
                    Toast.LENGTH_SHORT).show()

            }

            navController.navigateUp()
        }

        else -> super.onOptionsItemSelected(item)
    }

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