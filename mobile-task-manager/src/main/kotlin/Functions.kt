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
    for (task in tasks){
        println(taskToString(task))
    }
    println("All Tasks printed")
}

fun filterTasks(tasks : List<Task>){
    println("Press C to filter only done tasks, press anything else for undone tasks")
    val completed : Boolean = if("C".equals(readLine()?.trim())) true else false
    filterTasks(tasks,completed)
}

fun filterTasks(tasks : List<Task>,completed: Boolean){
//Imprimir las que cumplan (lambda)
}
