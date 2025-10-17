package gal.uvigo.mobileTaskManager

import gal.uvigo.mobileTaskManager.model.*
import java.time.LocalDate

object DataGenerator {

    fun createDumbData( t : TaskCollection){
        for (i in 1..100){
            t.addTask(Task(i, "title $i",  LocalDate.now(),  Category.entries[i%Category.entries.size],"description description $i", (i%2==0)))
        }
    }
}