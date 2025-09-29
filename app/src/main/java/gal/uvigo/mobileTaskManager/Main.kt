
import kotlin.collections.mutableListOf

fun main(args : Array<String>){
    println("Initiating mobile task manager...")
    val controller = TaskController()
    printMenu(controller)
    println("Program finished")
}

/**
 * Prints the Menu while verifying the chosen option and launching the selected option, repeats until user exits
 */
fun printMenu(controller : TaskController)  {

    var toRet : String?
    do {
        println("""Please select one of the following options:
        1 - Add a new task
        2 - Mark a task as done
        3 - List all tasks
        4 - Filter tasks
        0 - Exit the program""")
        toRet = readLine()?.trim()
/*TODO  validaciones, pasar lecturas aqui, lectura de enum, lectura de fecha
        when(toRet){
            "0" -> closeApp()
            "1" -> addTask(tasks)
            "2" -> markTaskDone(tasks)
            "3" -> listTasks(tasks)
            "4" -> filterTasks(tasks)
            else -> println("Invalid option selected")
        }

 */
    } while (!toRet.equals("0"))
}