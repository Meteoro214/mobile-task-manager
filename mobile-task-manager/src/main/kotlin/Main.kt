import java.time.LocalDate

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
        when(toRet){
            "0" -> closeApp()
            "1" -> addTask(controller)
            "2" -> markTaskDone(controller)
            "3" -> listTasks(controller)
            "4" -> filterTasks(controller)
            else -> println("Invalid option selected")
        }
    } while (!toRet.equals("0"))
}

fun closeApp() {
    println("Exiting...")
}


//TODO  validaciones, pasar lecturas aqui, lectura de enum, lectura de fecha

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

    val date : LocalDate = readDate()

    //Optional value
    println("Please enter a description (press enter to skip): ")
    val description = readLine()?.trim() ?: ""

    val t = controller.addTask(title,description,date,cat)
    if(t == null){
        println("Something went wrong, Task not created")
    } else println("The following task was created:\n ${t.toString()}")
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

/**
 * Reads the choice for completion status and prints the filtered Tasks
 */
fun filterTasks(controller: TaskController){
    print("Press C to filter only done tasks, press anything else for undone tasks: ")
    val completed : Boolean = "C".equals(readLine()?.trim(),ignoreCase = true)
    println(controller.filterTasks(completed))
}

fun readCategory() : Category {
    println("Select the category of the new Task")
    for(cat in Category.entries){
        println("Press ${cat.ordinal} for category '${cat.name}'")
    }
    var index = readLine() ?: ""
    if( index.isEmpty() || !index.all { it.isDigit() }){
        index = "-1"
    }
    var indexNum = index.toInt()
    while(indexNum < 0 || indexNum >= Category.entries.size){
        println("Selected value is not a valid category")
        for(cat in Category.entries){
            println("Press ${cat.ordinal} for category '${cat.name}'")
        }
        index = readLine() ?: ""
        if( index.isEmpty() || !index.all { it.isDigit() }){
            index = "-1"
        }
        indexNum = index.toInt()
    }
    return Category.entries[indexNum]
}

fun readDate() : LocalDate {

    /*
    println("Please enter a dueDate (press enter to skip): ")
    val dueDate = readLine()?.trim()
    if(dueDate?.isNotEmpty() ?: false) newTask[TASK_DUEDATE] = dueDate
*/

}