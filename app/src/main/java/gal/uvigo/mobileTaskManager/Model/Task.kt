package gal.uvigo.mobileTaskManager.model

import android.os.Parcel
import android.os.Parcelable
import java.time.LocalDate

class Task(
    val id: Int,
    title: String,
    dueDate: LocalDate,
    var category: Category = Category.OTHER,
    var description: String = "",
    var isDone: Boolean = false
) : Parcelable {

    var title: String = title.trim()
        set(value) {
            require(!value.trim().isEmpty()) { "title must not be empty" }
            field = value
        }
    var dueDate: LocalDate = dueDate
        set(value) {
            require(value.isFutureDate()) { "due date must not be in the past" }
            field = value
        }

    init {
        require(id > 0) { "ID must be above 0" }
        require(!title.trim().isEmpty()) { "title must not be empty" }
        require(dueDate.isFutureDate()) { "due date must not be in the past" }
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

    /*For Parcelable, could use @parcelize too to avoid code*/

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(id)
        dest.writeString(title)
        //Serializing the date is not efficient
        dest.writeInt(dueDate.year)
        dest.writeInt(dueDate.monthValue)
        dest.writeInt(dueDate.dayOfMonth)
        dest.writeInt(category.ordinal)
        dest.writeString(description)
        dest.writeBoolean(isDone)

    }

    companion object CREATOR : Parcelable.Creator<Task> {
        override fun createFromParcel(parcel: Parcel): Task =
            Task(
                parcel.readInt(), parcel.readString() ?: "",
                LocalDate.of(parcel.readInt(), parcel.readInt(), parcel.readInt()),
                Category.entries[parcel.readInt()], parcel.readString() ?: "", parcel.readBoolean()
            )


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