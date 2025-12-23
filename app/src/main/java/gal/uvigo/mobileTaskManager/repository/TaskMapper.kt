package gal.uvigo.mobileTaskManager.repository

import gal.uvigo.mobileTaskManager.model.Category
import gal.uvigo.mobileTaskManager.model.Task
import gal.uvigo.mobileTaskManager.repository.local.TaskEntity
import gal.uvigo.mobileTaskManager.repository.network.TaskGetDTO
import gal.uvigo.mobileTaskManager.repository.network.TaskSendDTO
import gal.uvigo.mobileTaskManager.repository.sync.SyncStatus
import java.time.LocalDate

/**
 * Class to handle conversions between the different classes that represent Tasks.
 */
class TaskMapper {

    /**
     * Transforms a Task into a TaskEntity.
     * Used to store Tasks in Room.
     * Sets syncStatus to PENDING_CREATE and _id to null.
     */
    fun toEntity(task: Task, position: Int): TaskEntity =
        TaskEntity(
            task.id,
            task.title,
            task.dueDate ?: LocalDate.now(),
            task.category ?: Category.OTHER,
            task.description,
            task.isDone,
            position,
            null,
            SyncStatus.PENDING_CREATE
        )

    /**
     * Transforms a TaskGetDTO into a TaskEntity.
     * Will ignore syncStatus, position and _id.
     * Used to read Tasks from CrudCrud on first app launch.
     * Sets syncStatus to SYNCED.
     */
    fun toEntity(taskDTO: TaskGetDTO): TaskEntity =
        TaskEntity(
            taskDTO.id,
            taskDTO.title,
            taskDTO.dueDate,
            taskDTO.category,
            taskDTO.description,
            taskDTO.isDone,
            taskDTO.position,
            taskDTO._id,
            SyncStatus.SYNCED
        )

    /**
     * Transforms a TaskEntity into a Task.
     * Will ignore syncStatus, position and _id.
     * Used to send a TaskEntity to TaskViewModel.
     * Order is expected to be maintained (getAll on DAO ordered by position).
     */
    fun toTask(entity: TaskEntity): Task =
        Task(
            entity.id,
            entity.title,
            entity.dueDate,
            entity.category,
            entity.description,
            entity.isDone
        )

    /**
     * Transforms a TaskEntity into a TaskSendDTO.
     * Will ignore syncStatus and _id.
     * Used to send a TaskEntity to CrudCrud.
     */
    fun toDTO(entity: TaskEntity): TaskSendDTO =
        TaskSendDTO(
            entity.id,
            entity.title,
            entity.dueDate,
            entity.category,
            entity.description,
            entity.isDone,
            entity.position,
        )

}