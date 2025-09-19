fun closeApp() {
    println("Exiting...")
}

/**
 * Asks the user for the information on the new task and adds it to the task list
 * Task id is autoincremental
 * Only required fields to enter is the task title
 * Tasks are assumed to be entered undone
 */
fun addTask(tasks : MutableList<Task>) {
    //AutoIncremental value
    val nextId = tasks.size
    println("Task will be created with id $nextId and marked as undone")
    println("Please enter a title: ")
    var title = readLine()?.trim() ?: ""
    while (title.isEmpty()){
        println("A title must be entered")
        println("Please enter a title: ")
        title = readLine()?.trim() ?: ""
    }

    val newTask : Task = createTask(nextId,title)
    //Optional values
    println("Please enter a description (press enter to skip): ")
    val description = readLine()?.trim()
    if(description?.isNotEmpty() ?: false) newTask[TASK_DESCRIPTION] = description

    println("Please enter a dueDate (press enter to skip): ")
    val dueDate = readLine()?.trim()
    if(dueDate?.isNotEmpty() ?: false) newTask[TASK_DUEDATE] = dueDate


    println("Please enter a category (press enter to skip): ")
    val category = readLine()?.trim()
    if(category?.isNotEmpty() ?: false) newTask[TASK_CATEGORY] = category

    println("Adding the following Task:")
    println(taskToString(newTask))
    tasks.add(newTask)
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
        if(markDone(task )) println("Task with id ${id} was marked as done")
        else println("Task with id ${id} was previously done")
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