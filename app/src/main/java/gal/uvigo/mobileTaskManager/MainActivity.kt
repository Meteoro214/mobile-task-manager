package gal.uvigo.mobileTaskManager

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
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
    private val tasks: TaskCollection = TaskCollection()
    private var nextId: Int = 1

 // mix with Main.kt in MainActivity
    /**
     * Marks the task with given id as done. Returns false if it was already done, null if it does not exist or true if it's marked as done successfully
     */
    fun markTaskDone(id: Int): Boolean? = tasks.markTaskDone(id)

    fun addTask(title: String, description: String, dueDate: LocalDate, category: Category): Task? {
        //constructor
        try {
            val t = Task(nextId, title, dueDate, category, description)
            tasks.addTask(t)
            nextId++
            return t
        } catch (e: IllegalArgumentException) {
            return null
        }
    }



    //TODO REFACTOR
//Return iterators not strings

    /**
     * Iterates over the TaskCollection and returns a String with all tasks printed
     */
    fun getAllTasks(): String {
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


    /**
     * Iterates over the given list and returns a String with all tasks that fulfill the filter condition
     */
    fun filterTasks(completed: Boolean): String {
        val sb = StringBuilder()
        sb.append("Printing all tasks that are ")
        if (!completed) sb.append("NOT ")
        sb.append("completed:\n")

        val filtered: Iterator<Task> = tasks.filter { it.isDone == completed }
        if (filtered.hasNext()) { //At least 1 Task in the iterator
            while (filtered.hasNext()) {
                sb.append(filtered.next().toString() + "\n")
            }
            sb.append("All filtered tasks printed\n")
        } else sb.append("No task exists after filtering\n")
        return sb.toString()
    }


 */
