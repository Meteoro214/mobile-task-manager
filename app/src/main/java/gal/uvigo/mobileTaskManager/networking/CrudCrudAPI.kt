package gal.uvigo.mobileTaskManager.networking

import android.content.Context
import gal.uvigo.mobileTaskManager.data_model.Task

class CrudCrudAPI(context: Context) {
    private val service = RetrofitClient.getInstance(context).service

    //Used to know what tasks are stored in CrudCrud
    private val _toCCIndex: MutableMap<Long, TaskCC> = mutableMapOf()


    suspend fun insert(task: Task): Long =
        if(!_toCCIndex.containsKey(task.id)) service.insert(task).id else 0

    //Returns empty list if no data
    suspend fun getAll(): List<Task>{
        //update indexes
        val list : MutableList<Task> = mutableListOf()
        val stored = service.getAll()
        for (taskCC in stored){
            list.add(taskCC.getTask())
            _toCCIndex[taskCC.id] = taskCC
        }
        return list.sortedBy { it.id }
    }

    //Returns number of updated rows
    suspend fun update(updated: Task): Int{
        val id = _toCCIndex[updated.id]?._id
        return if(id != null){
            service.update(id,updated)
        } else 0
    }

    //Returns number of deleted rows
    suspend fun delete(task: Task): Int{
        val id = _toCCIndex[task.id]?._id
        return if(id != null){
            service.delete(id)
        } else 0
    }

    suspend fun upload(taskList : List<Task>){
        //Upload all takss
        var taskCC : TaskCC?
        for(task in taskList){
            taskCC = _toCCIndex.get(task.id)
            if(taskCC != null){ //task existed in server
                //task was not deleted or inserted
                //check if updated
                if(task != taskCC.getTask()){//updated
                    update(task)
                }
                //Removes from index to allow delete checks
                _toCCIndex.remove(task.id)
            } else{ //we upload, it is new
                insert(task)
            }
        }
        //Check for deletions
        //if task was not in taskList but is in index, it was deleted
        for(taskCC in _toCCIndex.values){
            delete(taskCC.getTask())
        }
    }
}