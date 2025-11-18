package gal.uvigo.mobileTaskManager.db

import androidx.room.TypeConverter
import gal.uvigo.mobileTaskManager.data_model.createDateFromMMDD
import gal.uvigo.mobileTaskManager.data_model.formattedDueDate
import java.time.LocalDate

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