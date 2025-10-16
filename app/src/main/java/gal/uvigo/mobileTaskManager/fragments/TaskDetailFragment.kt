package gal.uvigo.mobileTaskManager.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import gal.uvigo.mobileTaskManager.R
import gal.uvigo.mobileTaskManager.TaskAdapter
import gal.uvigo.mobileTaskManager.databinding.FragmentTaskDetailBinding
import gal.uvigo.mobileTaskManager.databinding.FragmentTaskListBinding

class TaskDetailFragment : Fragment(R.layout.fragment_task_detail) {

    private val args : TaskDetailFragmentArgs by navArgs()
    private lateinit var binding : FragmentTaskDetailBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //another way of binding
        binding = FragmentTaskDetailBinding.bind(view)
        binding.taskData = args.task
    }
}