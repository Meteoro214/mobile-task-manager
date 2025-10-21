package gal.uvigo.mobileTaskManager.fragments

import android.content.res.Resources
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.navigation.fragment.navArgs
import gal.uvigo.mobileTaskManager.R
import gal.uvigo.mobileTaskManager.TaskRepository
import gal.uvigo.mobileTaskManager.databinding.FragmentTaskFormBinding
import gal.uvigo.mobileTaskManager.model.Category
import kotlin.getValue

class TaskFormFragment : Fragment(R.layout.fragment_task_form) {

    private val args: TaskFormFragmentArgs by navArgs()

    private val repository: TaskRepository = TaskRepository
    private lateinit var binding : FragmentTaskFormBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //another way of binding
        binding = FragmentTaskFormBinding.bind(view)
        if(args.taskID == -1){ //Default value, no real value passed

        }
        else{//Existing task

        }
        //Config AutoCompleteTextView
        configACTVCategory()



    }

    private fun configACTVCategory(){
        val categories = resources.getStringArray(R.array.categories)
        val adapter = ArrayAdapter(requireContext(),
            android.R.layout.simple_dropdown_item_1line,categories)
        binding.categoryInput.setAdapter(adapter)
        binding.categoryInput.onFocusChangeListener =
            View.OnFocusChangeListener { view, hasFocus ->
                if (hasFocus){
                    (view as AutoCompleteTextView).showDropDown()
                }
            }
        binding.categoryInput.onItemClickListener =
            AdapterView.OnItemClickListener {
                _, _, position, _ ->
                //Requires order be maintained in enum & array
                binding.taskData.category = Category.entries[position]
            }
    }




}