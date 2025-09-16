import kotlin.collections.mutableListOf

fun main(args : Array<String>){
    println("Initiating mobile task manager...")
    val tasks = mutableListOf<Task>()
    printMenu(tasks)
    println("Program finished")
}

fun printMenu(t : List<Task>)  {
    var toRet : String?

    do {
        println("Please select one of the following options: ")
        println("1 - Add a new task")
        println("2 - Mark a task as done")
        println("3 - List all tasks")
        println("4 - Filter tasks")
        println("0 - Exit the program")
        toRet = readLine()?.trim()
        when(toRet){
            "0" -> closeApp()
            "1" -> addTask()
            "2" -> markTaskDone()
            "3" -> listTasks()
            "4" -> filterTasks()
            else -> println("Invalid option selected")
        }
    } while (!toRet.equals("0"))
}

