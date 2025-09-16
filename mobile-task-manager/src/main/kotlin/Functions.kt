
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

fun listTasks(tasks : List<Task>){
    println("TODO listing...")
    //Recorrer la lista e imprimir
}

fun filterTasks(tasks : List<Task>){
    println("TODO filtering...")
    //Pedir condicion de filtrado
    //Imprimir las que cumplan (lambda)
}

fun filterTasks(tasks : List<Task>,completed: Boolean){

}
