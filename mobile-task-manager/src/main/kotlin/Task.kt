typealias Task = MutableMap<String, Any>

const val TASK_ID = "id"
const val TASK_TITLE = "title"
const val TASK_DONE = "isDone"
const val TASK_DESCRIPTION = "description"
const val TASK_DUEDATE = "dueDate"
const val TASK_CATEGORY = "category"

/**
 * Prints a given task
 */
fun taskToString(t:Task) {
    println("The task with id = ${t[TASK_ID]} has the following information:")
    for (key in t.keys){
        println("Property ${key} = ${t[key]}")
    }
    println("\n")
}

/**
 * Creates and returns a new Task
 */
fun createTask(id : Int, title : String, isDone : Boolean = false,
               description : String? = null,dueDate : String? = null, category : String? = null )
    = mutableMapOf(TASK_ID to  id, TASK_TITLE to title, TASK_DONE to isDone,
                    TASK_DESCRIPTION to description, TASK_DUEDATE to dueDate, TASK_CATEGORY to category)

/**
 * Marks the given task as done, returns true if the task was not previously done
 */
fun markDone(t:Task) : Boolean {
    return t.put(TASK_DONE, true) == false
}