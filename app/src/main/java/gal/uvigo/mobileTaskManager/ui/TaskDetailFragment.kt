package gal.uvigo.mobileTaskManager.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import gal.uvigo.mobileTaskManager.R
import gal.uvigo.mobileTaskManager.databinding.FragmentTaskDetailBinding
import gal.uvigo.mobileTaskManager.model.Task
import gal.uvigo.mobileTaskManager.model.TaskViewModel

/**
 * Fragment to show Task data
 */
class TaskDetailFragment : Fragment(R.layout.fragment_task_detail) {

    /**
     * Binding for the layout
     */
    private lateinit var binding: FragmentTaskDetailBinding

    /**
     * NavController for navigation
     */
    private lateinit var navController: NavController

    /**
     * Arguments received on navigation. Expected to receive a taskID (long)
     */
    private val args: TaskDetailFragmentArgs by navArgs()

    /**
     * TaskViewModel to handle Tasks
     */
    private val viewModel: TaskViewModel by activityViewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //another way of binding
        binding = FragmentTaskDetailBinding.bind(view)
        navController = findNavController()
        binding.taskData = viewModel.get(args.taskID)
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

            if (deleted) {
                Snackbar.make(
                    binding.root,
                    getString(R.string.check_delete_OK_msg),
                    Snackbar.LENGTH_SHORT
                ).show()
            } else {
                Snackbar.make(
                    binding.root,
                    getString(R.string.check_delete_error_msg),
                    Snackbar.LENGTH_SHORT
                ).show()
            }

            navController.navigateUp()
        }

        else -> super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        //Loads stored task info
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