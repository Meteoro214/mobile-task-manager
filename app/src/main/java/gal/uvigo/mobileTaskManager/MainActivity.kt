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
    }


}


/*  TODO remains of Main.kt
/**
 * Asks the user for the information on the new task and adds it to the task list
 * Task id is autoincremental
 * Only required fields to enter is the task title
 * Tasks are assumed to be entered undone
 */
fun addTask(controller : TaskController) {
    println("Task will be created with an automatic id and marked as undone")

    println("Please enter a title: ")
    var title = readLine()?.trim() ?: ""
    while (title.isEmpty()){
        println("A title must be entered")
        println("Please enter a title: ")
        title = readLine()?.trim() ?: ""
    }

    val cat : Category = readCategory()

    val date : LocalDate = readDate(controller)

    //Optional value
    println("Please enter a description (press enter to skip): ")
    val description = readLine()?.trim() ?: ""

    val t = controller.addTask(title,description,date,cat)
    if(t == null){
        println("Something went wrong, Task not created")
    } else println("The following task was created:\n $t")
}

/**
 * Asks for the id of the task to mark as done and marks it as done
 */
fun markTaskDone(controller :TaskController) {
    print("Input the id of the task to mark as done: ")
    var id = readLine() ?: ""
    while( id.isEmpty() || !id.all { it.isDigit() }){
        print("\nInputted id is not valid, please input a valid id: ")
        id = readLine() ?: ""
    }
    val result = controller.markTaskDone(id.toInt())
    if (result == null){
        println("No task exists with id $id")
    } else if(result){
        println("Task with id $id marked as done")
    } else println("Task with id $id was already done")
}

/**
 * Prints all tasks in the TaskCollection
 */
fun listTasks(controller: TaskController){
    println(controller.getAllTasks())
}


*/

/* TODO Remains of TaskController.kt

    /**
     * Iterates over the TaskCollection and returns a String with all tasks printed
     */
    private fun getAllTasks(): String {
        val sb = StringBuilder()
        if (!this.tasks.isEmpty()) {
            sb.append("Printing all Tasks:\n")
            val it: Iterator<Task> = this.tasks.iterator()
            while (it.hasNext()) {
                sb.append(it.next().toString() + "\n")
            }
            sb.append("All Tasks printed\n")
        } else sb.append("There are no tasks to print\n")
        return sb.toString()
    }

 */
