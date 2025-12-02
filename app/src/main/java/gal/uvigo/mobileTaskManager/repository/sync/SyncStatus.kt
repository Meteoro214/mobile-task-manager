package gal.uvigo.mobileTaskManager.repository.sync

/**
 * Enum class to handle TaskEntity sync status between Room-CrudCrud
 */
enum class SyncStatus {
    SYNCED, PENDING_CREATE, PENDING_UPDATE, PENDING_DELETE
}