fun closeApp() {
    println("Exiting...")
}

fun addTask(tasks : List<Task>, nextId : Int) {
    println("TODO adding...")
    //Pedir los datos
    //Crear la Task
    //Añadir la task
}

/**
 * Asks for the id of the task to mark as done and marks it as done
 */
fun markTaskDone(tasks : List<Task>){
    print("Input the id of the task to mark as done: ")
    var id = readLine()
    while( id == null || id.isEmpty() || !id.all { it.isDigit() }){
        print("\nInputted id is not valid, please input a valid id: ")
        id = readLine()
    }
    markTaskDone(tasks,id.toInt())
}

/**
 * Marks the task with given id as done
 */
fun markTaskDone(tasks : List<Task>,id: Int){
    val task = tasks.getOrNull(id)
    if(task == null) println("No task with id $id exists")
    else{
        if(markDone(task )) println("Task with id=${id} was marked as done")
        else println("Task with id=${id} was previously done")
    }
}

/**
 * Iterates over the given list and prints all tasks
 */
fun listTasks(tasks : List<Task>){
    println("Printing all Tasks:")
    if (tasks.isNotEmpty()){
        for (task in tasks){
            println(taskToString(task))
        }
        println("All Tasks printed")
    }else println("There are no tasks to print")
}

fun filterTasks(tasks : List<Task>){
    print("Press C to filter only done tasks, press anything else for undone tasks: ")
    val completed : Boolean = "C".equals(readLine()?.trim(),ignoreCase = true)
    filterTasks(tasks,completed)
}

/**
 * Iterates over the given list and prints tasks that fulfill the filter condition
 */
fun filterTasks(tasks : List<Task>,completed: Boolean){
    print("Printing all tasks that are ")
    if(!completed) print("NOT ")
    println("completed:")
    val filtered = tasks.filter { it[TASK_DONE] == completed }
    if (filtered.isNotEmpty()){
        for (task in filtered){
            println(taskToString(task))
        }
        println("All filtered tasks printed")
    }else println("No task exists after filtering")
}