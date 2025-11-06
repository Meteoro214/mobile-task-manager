package gal.uvigo.mobileTaskManager.db

import androidx.room.TypeConverter
import java.time.LocalDate
import gal.uvigo.mobileTaskManager.data_model.formattedDueDate
import gal.uvigo.mobileTaskManager.data_model.createDateFromMMDD

class LocalDateConverter {

    @TypeConverter
    fun stringToDate(string: String): LocalDate {
        val date = LocalDate.of(1, 1, 1)
        //should never give a null
        return date.createDateFromMMDD(string) ?: date

    }

    @TypeConverter
    fun dateToString(date: LocalDate): String = date.formattedDueDate()
}