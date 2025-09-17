fun closeApp() {
    println("Exiting...")
}

fun addTask(tasks : List<Task>, nextId : Int) {
    println("TODO adding...")
    //Pedir los datos
    //Crear la Task
    //Añadir la task
}

fun markTaskDone(tasks : List<Task>){
    println("TODO marking...")
    //Pedir la id
    //Buscar en la lista
    //Modificar el campo (añadir confirmacion/informar si estaba ya acabada)
}

fun markTaskDone(tasks : List<Task>,id: Int){

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
    println("Press C to filter only done tasks, press anything else for undone tasks")
    val completed : Boolean = if("C".equals(readLine()?.trim(),ignoreCase = true)) true else false
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