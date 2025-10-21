package gal.uvigo.mobileTaskManager.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import gal.uvigo.mobileTaskManager.R
import gal.uvigo.mobileTaskManager.TaskRepository
import gal.uvigo.mobileTaskManager.databinding.FragmentTaskFormBinding
import gal.uvigo.mobileTaskManager.model.Category
import gal.uvigo.mobileTaskManager.model.Task
import gal.uvigo.mobileTaskManager.model.createDateFromMMDD
import java.time.LocalDate
import kotlin.getValue

class TaskFormFragment : Fragment(R.layout.fragment_task_form) {

    private val args: TaskFormFragmentArgs by navArgs()
    private lateinit var navController: NavController

    private val repository: TaskRepository = TaskRepository
    private lateinit var binding: FragmentTaskFormBinding
    private var saved = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //another way of binding
        binding = FragmentTaskFormBinding.bind(view)
        navController = findNavController()

        //Loads task passed in SafeArgs
        loadTask()
        //Config AutoCompleteTextView
        configACTVCategory()
        binding.saveTaskButton.setOnClickListener { v -> saveButtonAction() }
    }

    private fun loadTask() {
        if (isEditingForm()) { //Existing task
            val t = repository.getTaskByID(args.taskID)?.copy()
            if (t == null) {
                //Should never happen
                Toast.makeText(
                    requireContext(), "Critical Edit Error : Task Not Found",
                    Toast.LENGTH_SHORT
                ).show()
                saved = true
                navController.navigateUp()
            } else {
                binding.taskData = t
            }
        } else { //New task
            //Values will be placeholders, will not be saved unless input is entered
            //ID will change when added, isDone/description/Category use the defaults
            binding.taskData = Task(1, dueDate = LocalDate.now())

            binding.dueDateInput.setText("") //Shows no value on the date, but internally will be .now()
            binding.categoryInput.setText("") //Shows no value on the category, but internally will be Other
        }
    }

    private fun configACTVCategory() {
        val categories = resources.getStringArray(R.array.categories)
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line, categories
        )
        binding.categoryInput.setAdapter(adapter)
        binding.categoryInput.onFocusChangeListener =
            View.OnFocusChangeListener { view, hasFocus ->
                if (hasFocus) {
                    (view as AutoCompleteTextView).showDropDown()
                }
            }
        binding.categoryInput.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                //Requires order be maintained in enum & array
                binding.taskData?.category = Category.entries[position]
            }
    }

    private fun saveButtonAction() {
        //verify info
        if (verifyTask()) {
            //save info
            saveTask()
        }
    }

    private fun verifyTask(): Boolean =
        if (binding.taskData?.title?.isEmpty() ?: true) {
            //Can this even happen? probably not its 2-way binded
            Toast.makeText(
                requireContext(),
                "Title must not be empty", Toast.LENGTH_SHORT
            ).show()
            false
        } else {
            val date =
                binding.taskData?.dueDate?.createDateFromMMDD(binding.dueDateInput.text.toString())
            if (date == null) {
                Toast.makeText(
                    requireContext(),
                    "Date is not valid", Toast.LENGTH_SHORT
                ).show()
                false
            } else {
                binding.taskData?.dueDate = date
                if (binding.categoryInput.text.isEmpty()) {
                    Toast.makeText(
                        requireContext(),
                        "Category must be selected", Toast.LENGTH_SHORT
                    ).show()
                    false
                }
                true
            }
        }

    private fun saveTask() {
        //Data is already verified
        if (isEditingForm()) { //Existing task
            val storedTask = repository.getTaskByID(binding.taskData?.id ?: -1)
            if (storedTask != null) {
                storedTask.isDone = binding.taskData?.isDone ?: storedTask.isDone
                storedTask.title = binding.taskData?.title ?: storedTask.title
                storedTask.dueDate = binding.taskData?.dueDate ?: storedTask.dueDate
                storedTask.description = binding.taskData?.description ?: storedTask.description
                storedTask.category = binding.taskData?.category ?: storedTask.category
            } else { //Should never happen
                Toast.makeText(
                    requireContext(), "Critical Edit Error : Task Not Found",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else { //New task
            //Adds the task to the repository with the info on the binding
            //Only change will be the ID (binding has a placeholder)
            // Returning task is saved to change the ID (is a val)
            //Should never be null, data has already been checked
            //If a safe check fails, it will fail (will never happen)
            binding.taskData = (repository.addTask(
                binding.taskData?.title ?: "",
                binding.taskData?.description ?: "",
                binding.taskData?.dueDate ?: LocalDate.of(1,1,1),
                binding.taskData?.category ?: Category.OTHER,
                binding.taskData?.isDone ?: true
            ) ?: binding.taskData)
        }
        saved = true;
        //Returns to previous fragment
        navController.navigateUp()
    }

    override fun onPause() {
        super.onPause()
        val handle = navController.previousBackStackEntry?.savedStateHandle
        if (handle != null) { //should always be true
            if (!saved) {
                //store unfinished data for a posible reload
                val keyID = getString(R.string.handle_unfinishedFormID_Key)
                val keyTitle = getString(R.string.handle_unfinishedFormTitle_Key)
                val keyIsDone = getString(R.string.handle_unfinishedFormIsDone_Key)
                val keyDate = getString(R.string.handle_unfinishedFormDate_Key)
                val keyDesc = getString(R.string.handle_unfinishedFormDescription_Key)
                val keyCat = getString(R.string.handle_unfinishedFormCategory_Key)

                handle.set(keyID, binding.taskData?.id)
                handle.set(keyTitle, binding.taskData?.title)
                handle.set(keyDesc, binding.taskData?.description)
                handle.set(keyIsDone, binding.taskData?.isDone)
                handle.set(keyDate, binding.dueDateInput.text)
                handle.set(keyCat, binding.categoryInput.text)

            } else if (isEditingForm()) {
                //send task Info (completed) back, it was already saved
                val key = getString(R.string.handle_editedTask_Key)
                handle.set(key, this.binding.taskData)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val handle = navController.previousBackStackEntry?.savedStateHandle
        val keyID = getString(R.string.handle_unfinishedFormID_Key)
        val keyTitle = getString(R.string.handle_unfinishedFormTitle_Key)
        val keyIsDone = getString(R.string.handle_unfinishedFormIsDone_Key)
        val keyDate = getString(R.string.handle_unfinishedFormDate_Key)
        val keyDesc = getString(R.string.handle_unfinishedFormDescription_Key)
        val keyCat = getString(R.string.handle_unfinishedFormCategory_Key)

        if (handle != null && handle.contains(keyID)) {
            //there was a previously unfinished form saved
            if (handle.get<Int>(keyID) == binding.taskData?.id) {
                //Ensure the saved data has the same ID (on edit, we try to edit the same Task)
                //Handle should hold all necesary data
                binding.taskData?.title = handle.get<String>(keyTitle).toString()
                binding.taskData?.description = handle.get<String>(keyDesc).toString()
                binding.taskData?.isDone = handle.get<Boolean>(keyIsDone) == true
                binding.dueDateInput.setText(handle.get<String>(keyDate))
                val cat = handle.get<String>(keyCat)
                if (cat?.isNotBlank() ?: false) {
                    //turn string into cat
                    val index = resources.getStringArray(R.array.categories).indexOf(cat)
                    binding.taskData?.category = Category.entries[index]
                }
            }
            //Clear saved data (Note that if saved data was from an edit from other task, it will still be lost)
            handle.remove<Int>(keyID)
            handle.remove<String>(keyTitle)
            handle.remove<Boolean>(keyIsDone)
            handle.remove<String>(keyDate)
            handle.remove<String>(keyDesc)
            handle.remove<String>(keyCat)
        }
    }

    private fun isEditingForm(): Boolean =
        args.taskID != -1 //-1 Default value, no real value passed

}