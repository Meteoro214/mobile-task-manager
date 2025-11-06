package gal.uvigo.mobileTaskManager.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import gal.uvigo.mobileTaskManager.R
import gal.uvigo.mobileTaskManager.data_model.*
import gal.uvigo.mobileTaskManager.databinding.FragmentTaskFormBinding
import gal.uvigo.mobileTaskManager.model.*
import java.time.LocalDate
import kotlin.getValue

class TaskFormFragment : Fragment(R.layout.fragment_task_form) {

    private val args: TaskFormFragmentArgs by navArgs()
    private lateinit var navController: NavController
    private val viewModel: TaskViewModel by activityViewModels()
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
            val t = viewModel.get(args.taskID)?.copy()
            if (t == null) {
                //Should never happen
                Toast.makeText(
                    requireContext(), getString(R.string.form_load_error_msg),
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
            binding.taskData = Task(0)
            // Category & Date will be null to start to show empty form
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
                    val autoCompleteTV = view as AutoCompleteTextView
                    //Ensures a cleared selection because on edit only selected is shown as option
                    autoCompleteTV.setText("")
                    //Ensures ACTV is internally ready to show the options
                    view.post {
                        if(autoCompleteTV.isAttachedToWindow && autoCompleteTV.isFocused && autoCompleteTV.adapter != null){
                            view.showDropDown() //Shows the options
                        }
                    }
                }
            }
        binding.categoryInput.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                //Requires order be maintained in enum & array
                binding.taskData?.category = Category.entries[position]
                binding.categoryInput.error = null //remove error msg

            }
    }

    private fun saveButtonAction() {
        //verify info
        if (verifyTask()) {
            //save info
            saveTask()
        }
    }

    private fun verifyTask(): Boolean {
        var toRet = true
        if (binding.taskData?.title?.isEmpty() ?: true) {
            binding.titleInput.error = getString(R.string.form_title_empty_msg)
            toRet = false
        }
        val date =
            LocalDate.of(1, 1, 1).createDateFromMMDD(binding.dueDateInput.text.toString())
        if (date == null) {
            binding.dueDateInput.error = getString(R.string.form_date_invalid_msg)
            toRet = false
        } else {
            binding.taskData?.dueDate = date
        }
        if (binding.categoryInput.text.isEmpty()) {
            binding.categoryInput.error = getString(R.string.form_category_empty_msg)
            toRet = false
        }
        return toRet
    }

    private fun saveTask() {
        //Data is already verified
        if (isEditingForm()) { //Existing task
            val validUpdate = viewModel.updateTask(binding.taskData?: Task(-1))
            if (!validUpdate) { //Should never happen
                Toast.makeText(
                    requireContext(), getString(R.string.form_edit_error_msg),
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else { //New task
            //Adds the task  the info on the binding
            //Only change will be the ID (binding has a placeholder, database will save with a real ID)
            //Should never fail, data has already been checked
            viewModel.addTask(
                binding.taskData?.title ?: "",
                binding.taskData?.description ?: "",
                binding.taskData?.dueDate ?: LocalDate.of(1, 1, 1),
                binding.taskData?.category ?: Category.OTHER,
                binding.taskData?.isDone ?: true
            )
        }
        saved = true
        //Returns to previous fragment
        navController.navigateUp()
    }

    override fun onPause() {
        super.onPause()
        //If app is exited, current is still the fragment
        //If back button or save button is pressed, current changed to previous (stack is popped BEFORE onPause)
        val handle = navController.currentBackStackEntry?.savedStateHandle
        if (handle != null) { //should always be true
            if (!saved) {
                //store unfinished data for a possible reload
                val keyID = getString(R.string.handle_unfinishedFormID_Key)
                val keyTitle = getString(R.string.handle_unfinishedFormTitle_Key)
                val keyIsDone = getString(R.string.handle_unfinishedFormIsDone_Key)
                val keyDate = getString(R.string.handle_unfinishedFormDate_Key)
                val keyDesc = getString(R.string.handle_unfinishedFormDescription_Key)
                val keyCat = getString(R.string.handle_unfinishedFormCategory_Key)

                handle[keyID] = binding.taskData?.id
                handle[keyTitle] = binding.taskData?.title
                handle[keyDesc] = binding.taskData?.description
                handle[keyIsDone] = binding.taskData?.isDone
                handle[keyDate] = binding.dueDateInput.text.toString()
                handle[keyCat] = binding.categoryInput.text.toString()

            } else if (isEditingForm()) {
                //send task Info (completed) back, it was already saved
                val key = getString(R.string.handle_editedTask_Key)
                handle[key] = this.binding.taskData
            }
        }
    }

    override fun onResume() {
        super.onResume()
        //If app resumes from exit, current is the fragment and will have a handle
        //If it is access from another fragment, handle is on previous
        val handle = navController.previousBackStackEntry?.savedStateHandle
        val keyID = getString(R.string.handle_unfinishedFormID_Key)

        if (handle != null && handle.contains(keyID)) {
            val keyTitle = getString(R.string.handle_unfinishedFormTitle_Key)
            val keyIsDone = getString(R.string.handle_unfinishedFormIsDone_Key)
            val keyDate = getString(R.string.handle_unfinishedFormDate_Key)
            val keyDesc = getString(R.string.handle_unfinishedFormDescription_Key)
            val keyCat = getString(R.string.handle_unfinishedFormCategory_Key)
            //there was a previously unfinished form saved
            if (handle.get<Long>(keyID) == binding.taskData?.id) {
                //Ensure the saved data has the same ID (on edit, we try to edit the same Task)
                //Handle should hold all necessary data
                binding.taskData?.title = handle.get<String>(keyTitle) ?: ""
                binding.taskData?.description = handle.get<String>(keyDesc) ?: ""
                binding.taskData?.isDone = handle.get<Boolean>(keyIsDone) == true
                val date =
                    LocalDate.of(1, 1, 1).createDateFromMMDD(handle.get<String>(keyDate) ?: "")
                if (date != null) binding.taskData?.dueDate = date
                val cat = handle.get<String>(keyCat)
                if (cat?.isNotBlank() ?: false) {
                    //turn string into cat
                    val index = resources.getStringArray(R.array.categories).indexOf(cat)
                    binding.taskData?.category = Category.entries[index]
                }
            }
            //Clear saved data (Note that if saved data was from an edit from other task, it will still be lost)
            handle.remove<Long>(keyID)
            handle.remove<String>(keyTitle)
            handle.remove<Boolean>(keyIsDone)
            handle.remove<String>(keyDate)
            handle.remove<String>(keyDesc)
            handle.remove<String>(keyCat)
        }

        //If the fragment is resuming after going to background instead of being created,
        //data will be in the current stack entry`s handle, not the previous
        val currentHandle = navController.currentBackStackEntry?.savedStateHandle

        if (currentHandle != null && currentHandle.contains(keyID)) {
            val keyTitle = getString(R.string.handle_unfinishedFormTitle_Key)
            val keyIsDone = getString(R.string.handle_unfinishedFormIsDone_Key)
            val keyDate = getString(R.string.handle_unfinishedFormDate_Key)
            val keyDesc = getString(R.string.handle_unfinishedFormDescription_Key)
            val keyCat = getString(R.string.handle_unfinishedFormCategory_Key)
            //there was a previously unfinished form saved
            if (currentHandle.get<Long>(keyID) == binding.taskData?.id) {
                //Ensure the saved data has the same ID (on edit, we try to edit the same Task)
                //Handle should hold all necessary data
                binding.taskData?.title = currentHandle.get<String>(keyTitle) ?: ""
                binding.taskData?.description = currentHandle.get<String>(keyDesc) ?: ""
                binding.taskData?.isDone = currentHandle.get<Boolean>(keyIsDone) == true
                val date =
                    LocalDate.of(1, 1, 1)
                        .createDateFromMMDD(currentHandle.get<String>(keyDate) ?: "")
                if (date != null) binding.taskData?.dueDate = date
                val cat = currentHandle.get<String>(keyCat)
                if (cat?.isNotBlank() ?: false) {
                    //turn string into cat
                    val index = resources.getStringArray(R.array.categories).indexOf(cat)
                    binding.taskData?.category = Category.entries[index]
                }
            }
            //Clear saved data (Note that if saved data was from an edit from other task, it will still be lost)
            currentHandle.remove<Long>(keyID)
            currentHandle.remove<String>(keyTitle)
            currentHandle.remove<Boolean>(keyIsDone)
            currentHandle.remove<String>(keyDate)
            currentHandle.remove<String>(keyDesc)
            currentHandle.remove<String>(keyCat)
        }

        //When loading unfinished data from an add operation, and only from an add operation,
        // data is correctly bound but not displayed on the form
        //forcing a setText resolves the issue
        binding.taskData = binding.taskData
    }

    private fun isEditingForm(): Boolean =
        args.taskID != -1L //-1 Default value, no real value passed

}