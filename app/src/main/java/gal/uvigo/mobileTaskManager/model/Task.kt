package gal.uvigo.mobileTaskManager.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass
import java.time.LocalDate

@Entity(tableName = "tasks")
@JsonClass (generateAdapter = true)
class Task(
    @PrimaryKey(autoGenerate = true) val id: Long,
    var title: String = "",
    dueDate: LocalDate? = null,
    category: Category? = null,
    var description: String = "",
    var isDone: Boolean = false
) : Parcelable {

    @ColumnInfo(name = "due_date")
    var dueDate: LocalDate? = dueDate
        set(value) {
            require(
                value?.isBefore(LocalDate.now()) == false
            ) { "due date must exists and not be in the past" }
            field = value
        }

    var category: Category? = category
        set(value) {
            require(value != null) { "Cannot set a null category" }
            field = value
        }

    //Title may be empty,date & category may be null
    // but will not be accepted on adding/editing
    //will accept past dates, only in constructor
    init {
        require(id >= 0) { "ID mustn`t be negative" }
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