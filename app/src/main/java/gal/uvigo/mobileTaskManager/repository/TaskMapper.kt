package gal.uvigo.mobileTaskManager.repository

/**
 * Class to handle conversions between the different classes that represent Tasks
 */
class TaskMapper {

    // Task to Entity (add/update to Room)  _id to null, Sync to pending_insert/update, position to next one
    // Task to SendDTO (NEVER)
    // Task to GetDTO (NEVER)

    // Entity to Task (transforming getAll and get) (ordering is done on dao, ignore position,_id, syncStatus)
    // Entity to SendDTO (sync to CruCrud) (ignore _id, sync)
    // Entity to GetDTO (NEVER)

    // SendDTO to Task (NEVER)
    // SendDTO to Entity (NEVER)
    // SendDTO to GetDTO (NEVER) CrudCrud does it kinda

    // GetDTO to Task (maybe on first launch, maybe NEVER if sync goes to Room first)
    // GetDTO to Entity (sync on first launch, CrudCrud to Room)
    // GetDTO to SendDTO (NEVER)


}