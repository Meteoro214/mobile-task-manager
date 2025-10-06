package gal.uvigo.mobileTaskManager

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val controller: TaskController = TaskController()
    private lateinit var taskView: TextView
    private lateinit var msgView: TextView
    private lateinit var addTextView: EditText
    private lateinit var markDoneTextView: EditText
    private lateinit var addButton: Button
    private lateinit var markDoneButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        taskView = findViewById(R.id.taskView)
        msgView = findViewById(R.id.msgView)
        addTextView = findViewById(R.id.addTextView)
        markDoneTextView = findViewById(R.id.markDoneTextView)
        addButton = findViewById(R.id.addButton)
        markDoneButton = findViewById(R.id.markDoneButton)

        addButton.setOnClickListener {view -> addTask()}
        markDoneButton.setOnClickListener {view -> markTaskDone()}

        listTasks()
    }

    /**
     * Shows all tasks in the TaskCollection
     */
    fun listTasks() {
        val it = controller.getAllTasks()
        taskView.text = if (!it.hasNext()) {
            getString(R.string.app_no_task_msg)
        } else {
            val sb = StringBuilder()
            sb.append("Tasks:\n")
            while (it.hasNext()) {
                sb.append(it.next().toString() + "-------------------\n")
            }
            sb.toString()
        }
    }

    /**
     * Reads the task title and creates a new task.
     * Does not allow an empty title
     */
    fun addTask() {
        val title = addTextView.text?.toString()?.trim() ?: ""
        msgView.text = if (title.isEmpty()) {
            getString(R.string.add_task_error_msg)
        } else {
            val t = controller.addTask(title)
            if (t == null) {
                getString(R.string.add_task_error_msg_generic)
            } else {
                listTasks()
                getString(R.string.add_task_msg) + t.id
            }
        }
        //Has to be an explicit setText; EditText.text will only allow Editable!
        addTextView.setText("")
    }

    /**
     * Reads the id of the task to mark as done and attempts to mark it as done
     */
    fun markTaskDone() {
        val id = markDoneTextView.text?.toString()?.trim() ?: ""
        msgView.text = if (id.isEmpty() || !id.all { it.isDigit() }) {
            getString(R.string.mark_done_input_error_msg)
        } else {
            val result = controller.markTaskDone(id.toInt())
            if (result == null) {
                getString(R.string.mark_done_noID_error_msg)
            } else if (result) {
                listTasks()
                getString(R.string.mark_done_msg) + id
            } else getString(R.string.mark_done_wasDone_error_msg)
        }
        //Has to be an explicit setText; EditText.text will only allow Editable!
        markDoneTextView.setText("") //clear()
    }

}

/*
* TODO
* scrollbars para mostrar la barra
animationCache para fluidez

dataBinding en el view holder?
* taskControllee deprecar?
hay que importar la rv

en activity hacer funciones
*
crear los ph dentro de values

preparar placeholders a futuro
*
*
*
* data binding en el viewholder, usar variable Task en el xml y recuperar con findView
* */