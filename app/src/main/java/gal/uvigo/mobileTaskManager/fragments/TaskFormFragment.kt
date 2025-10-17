package gal.uvigo.mobileTaskManager.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import gal.uvigo.mobileTaskManager.R
import gal.uvigo.mobileTaskManager.databinding.FragmentTaskFormBinding

class TaskFormFragment : Fragment(R.layout.fragment_task_form) {

    private lateinit var binding : FragmentTaskFormBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //another way of binding
        binding = FragmentTaskFormBinding.bind(view)
        binding.addTaskButton.setOnClickListener {
            //Shows error msg
            Toast.makeText(this.context, "TODO not implemented", Toast.LENGTH_SHORT).show()
        }
        binding.backButton.setOnClickListener {
            findNavController().navigate(TaskFormFragmentDirections.abandonAddForm())
        }
    }
}