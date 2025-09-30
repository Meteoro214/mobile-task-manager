package gal.uvigo.mobileTaskManager

import Task
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalDate

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
        //addButton.setOnClickListener(addTask())
//        markDoneButton.setOnClickListener(addTask())

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
        val title = addTextView.text?.trim() ?: ""
        msgView.text = if (title.isEmpty()) {
            getString(R.string.add_task_error_msg)
        } else {
            val t = controller.addTask(title as String)
            if (t == null) {
                getString(R.string.add_task_error_msg_generic)
            } else {
                listTasks()
                getString(R.string.add_task_msg) + t.id
            }
        }
    }

    /**
     * Reads the id of the task to mark as done and attempts to mark it as done
     */
    fun markTaskDone() {
        val id = markDoneTextView.text?.trim() ?: ""
        msgView.text = if (id.isEmpty() || !id.all { it.isDigit() }) {
            getString(R.string.mark_done_input_error_msg)
        } else {
            val result = controller.markTaskDone(Integer.parseInt(id as String))
            if (result == null) {
                getString(R.string.mark_done_noID_error_msg)
            } else if (result) {
                listTasks()
                getString(R.string.mark_done_msg) + id
            } else getString(R.string.mark_done_wasDone_error_msg)
        }
    }

}