//REFACTOR & DELETE FILE


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




