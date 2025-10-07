package gal.uvigo.mobileTaskManager

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import gal.uvigo.mobileTaskManager.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val controller: TaskController = TaskController()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        loadDummyData()
        loadLayout()
        setListeners()
    }

    fun loadDummyData(){
        DataGenerator.createDumbData(controller.getCollection())
    }

    fun loadLayout() {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        binding.taskRV.layoutManager = LinearLayoutManager(this)
        binding.taskRV.adapter = TaskAdapter(controller.getCollection())
    }

    fun setListeners() {
        //No current listeners
    }

}
