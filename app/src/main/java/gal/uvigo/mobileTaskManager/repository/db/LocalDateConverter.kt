package gal.uvigo.mobileTaskManager.repository.db

import androidx.room.TypeConverter
import gal.uvigo.mobileTaskManager.model.createDateFromMMDD
import gal.uvigo.mobileTaskManager.model.formattedDueDateWithYear
import java.time.LocalDate

class LocalDateConverter {

    @TypeConverter
    fun stringToDate(string: String): LocalDate {
        val date = LocalDate.of(1, 1, 1)
        //should never give a null
        return date.createDateFromMMDD(string, false) ?: date

    }

    @TypeConverter
    fun dateToString(date: LocalDate): String = date.formattedDueDateWithYear()
}