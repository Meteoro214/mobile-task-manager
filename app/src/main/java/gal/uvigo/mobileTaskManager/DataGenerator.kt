package gal.uvigo.mobileTaskManager

import gal.uvigo.mobileTaskManager.model.Category
import gal.uvigo.mobileTaskManager.model.Task
import gal.uvigo.mobileTaskManager.model.TaskCollection
import java.time.LocalDate

object DataGenerator {

    fun createDumbData( t : TaskCollection){
        for (i in 1..100){
            t.addTask(Task(i, "title$i", dueDate = LocalDate.now(), category = Category.entries[i%Category.entries.size], isDone = (i%2==0)))
        }
    }
}