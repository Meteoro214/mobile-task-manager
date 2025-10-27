package gal.uvigo.mobileTaskManager.model

import android.os.Parcel
import android.os.Parcelable
import java.time.LocalDate

class Task(
    val id: Int,
    var title: String = "",
    dueDate: LocalDate? = null,
    category: Category? = null,
    var description: String = "",
    var isDone: Boolean = false
) : Parcelable {

    var dueDate: LocalDate? = dueDate
        set(value) {
            require(
                value?.isFutureDate() ?: false
            ) { "due date must exists and not be in the past" }
            field = value
        }

    var category: Category? = category
        set(value) {
            require(value != null) { "Cannot set a null categoru" }
            field = value
        }

    //Title may be empty, but no empty title will be accepted on collection
    //date & category may be null when creating only
    init {
        require(id > 0) { "ID must be above 0" }
        require(dueDate?.isFutureDate() ?: true) { "due date must not be in the past" }
    }


    fun copy(): Task =
        Task(
            this.id,
            this.title,
            this.dueDate,
            this.category,
            this.description,
            this.isDone
        )

    fun getStringDate(): String = dueDate?.formattedDueDate() ?: ""



    /*For Parcelable, could use @parcelize too to avoid code*/

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(id)
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
            val id = parcel.readInt()
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