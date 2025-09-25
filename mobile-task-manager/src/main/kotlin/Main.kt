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

    var toRet : String
    do {
        println("""Please select one of the following options:
        1 - Add a new task
        2 - Mark a task as done
        3 - List all tasks
        4 - Filter tasks
        0 - Exit the program""")
        toRet = readLine()?.trim() ?: ""
//TODO  validaciones, pasar lecturas aqui, lectura de enum, lectura de fecha
        when(toRet){
            "0" -> this.closeApp()
            "1" -> this.addTask(controller)
            "2" -> this.markTaskDone(controller)
            "3" -> this.listTask(controller)
            "4" -> this.filterTasks(controller)
            else -> println("Invalid option selected")
        }


    } while (!toRet.equals("0"))
}

fun closeApp() {
    println("Exiting...")
}

fun addTask(controller : TaskController) {

}

/**
 * Asks for the id of the task to mark as done and marks it as done
 */
fun markTaskDone(controller :TaskController) {
    print("Input the id of the task to mark as done: ")
    var id = readLine()
    while( id == null || id.isEmpty() || !id.all { it.isDigit() }){
        print("\nInputted id is not valid, please input a valid id: ")
        id = readLine()
    }
    controller.markTaskDone(id.toInt())
}

fun listTasks(controller: TaskController){
    println(controller.getAllTasks())
}

fun filterTasks(controller: TaskController){
    print("Press C to filter only done tasks, press anything else for undone tasks: ")
    val completed : Boolean = "C".equals(readLine()?.trim(),ignoreCase = true)
    println(controller.filterTasks(completed))
}