package gal.uvigo.mobileTaskManager.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import gal.uvigo.mobileTaskManager.R
import gal.uvigo.mobileTaskManager.databinding.ActivityMainBinding
import gal.uvigo.mobileTaskManager.databinding.FragmentTaskDetailBinding
import gal.uvigo.mobileTaskManager.databinding.FragmentTaskFormBinding
import kotlin.getValue

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
            binding.root.findNavController().navigate(TaskFormFragmentDirections.abandonAddForm())
        }
    }
}