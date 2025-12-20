package gal.uvigo.mobileTaskManager.model

import android.os.Parcel
import android.os.Parcelable
import java.time.LocalDate

/**
 * Class to represent a Task, with id, title, a due date, a Category, a description, and a status (done/not done).
 */
class Task(
    val id: Long,
    var title: String = "",
    dueDate: LocalDate? = null,
    category: Category? = null,
    var description: String = "",
    var isDone: Boolean = false
) : Parcelable {

    var dueDate: LocalDate? = dueDate
        /**
         * Ensures dueDate is not in the past.
         */
        set(value) {
            require(
                value?.isBefore(LocalDate.now()) == false
            ) { "due date must exists and not be in the past" }
            field = value
        }

    var category: Category? = category
        /**
         * Ensures Category is not null.
         */
        set(value) {
            require(value != null) { "Cannot set a null category" }
            field = value
        }

    /**
     * Requires constructor given id to be a positive Long, but this id will be modified by TaskRepository on add.
     * TaskRepository expects id to be 0.
     * When creating a Task, title may be empty, date & category may be null.
     * Date & Category setters will ensure no nulls & no past dates.
     * TaskRepository will ensure no nulls, no empty titles & no past dates on add or edit operations.
     */
    init {
        require(id >= 0) { "ID must not be negative" }
    }


    /**
     * Returns a copy of this.
     */
    fun copy(): Task =
        Task(
            this.id,
            this.title,
            this.dueDate,
            this.category,
            this.description,
            this.isDone
        )

    /**
     * Checks if other is a Task with the same parameters.
     */
    override fun equals(other: Any?): Boolean =
        if (other == null || other !is Task) false
        else this.id == other.id && this.title == other.title
                && this.dueDate == other.dueDate && this.category == other.category
                && this.description == other.description && this.isDone == other.isDone


    /*For Parcelable, could use @parcelize too to avoid code*/

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeLong(id)
        dest.writeString(title)
        //Serializing the date is not efficient
        dest.writeInt(dueDate?.year ?: -1)
        dest.writeInt(dueDate?.monthValue ?: -1)
        dest.writeInt(dueDate?.dayOfMonth ?: -1)
        dest.writeInt(category?.ordinal ?: -1)
        dest.writeString(description)
        dest.writeBoolean(isDone)

    }

    companion object CREATOR : Parcelable.Creator<Task> {
        override fun createFromParcel(parcel: Parcel): Task {
            val id = parcel.readLong()
            val title = parcel.readString() ?: ""
            val year = parcel.readInt()
            val month = parcel.readInt()
            val day = parcel.readInt()
            val date = if (day == -1) null else LocalDate.of(year, month, day)
            val catId = parcel.readInt()
            val cat = if (day == -1) null else Category.entries[catId]
            val desc = parcel.readString() ?: ""
            val done = parcel.readBoolean()
            return Task(
                id, title, date, cat, desc, done
            )
        }

        /**
         * Not implemented
         * @deprecated
         */
        override fun newArray(size: Int): Array<Task?> {
            //Not implemented
            return arrayOfNulls(size)
        }
    }

}