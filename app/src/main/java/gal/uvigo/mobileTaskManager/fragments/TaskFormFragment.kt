package gal.uvigo.mobileTaskManager.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import gal.uvigo.mobileTaskManager.R
import gal.uvigo.mobileTaskManager.TaskRepository
import gal.uvigo.mobileTaskManager.databinding.FragmentTaskFormBinding
import gal.uvigo.mobileTaskManager.model.Category
import kotlin.getValue

class TaskFormFragment : Fragment(R.layout.fragment_task_form) {

    private val args: TaskFormFragmentArgs by navArgs()
    private lateinit var navController: NavController

    private val repository: TaskRepository = TaskRepository
    private lateinit var binding : FragmentTaskFormBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //another way of binding
        binding = FragmentTaskFormBinding.bind(view)
        navController = findNavController()

        //Loads task passed in SafeArgs
        loadTask()
        //Config AutoCompleteTextView
        configACTVCategory()
        binding.saveTaskButton.setOnClickListener { v -> saveTask() }
    }

    private fun loadTask() {
        if(isEditingForm()){ //Existing task

        }
        else{ //New task

        }

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

    private fun saveTask() {
        //verificar los datos?, almacenar los datos
        //que pasa si hay requires
        //mejor validar aqui??
        if(isEditingForm()){ //Existing task

        }
        else{ //New task

        }

        //Si ha salido bien abandonar pantalla O hacer que tenga que abandonar con un back?
        //en el navgrafh no tiene accion
        //un navigateUp funciona seguramente
//Toast para decir
    }

    override fun onPause() {
        //Envia hacia atras la task si es necesario, el almacenamiento es en el boton, guarda la info de alguna forma por si salimos y volvemos

        //guardar un incomplete info o un taskInfo dependiendo de si es valida o no
        //leerla en el onResume
    }

    override fun onResume() {
//Recupera info guardada incompleta si existe
        //Hacerlo en el loadTask?
        //mejor aqui
    }

    private fun isEditingForm() : Boolean = args.taskID != -1 //-1 Default value, no real value passed

    //edit the title
    //safeargs to check mode
    //  TaskDetail TaskForm
    //edit button may explode
    //when on binding may explode
}