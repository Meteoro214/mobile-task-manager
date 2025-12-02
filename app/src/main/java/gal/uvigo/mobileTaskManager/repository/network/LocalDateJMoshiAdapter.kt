package gal.uvigo.mobileTaskManager.repository.network

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.LocalDate
import gal.uvigo.mobileTaskManager.model.createDateFromMMDD
import gal.uvigo.mobileTaskManager.model.formattedDueDateWithYear

/**
 * Class to allow Moshi to handle LocalDates
 */
class LocalDateJMoshiAdapter {

    @ToJson
    fun toJson(date: LocalDate): String = date.formattedDueDateWithYear()

    @FromJson
    fun fromJson(string: String): LocalDate  {
        val date = LocalDate.of(1, 1, 1)
        //should never give a null
        return date.createDateFromMMDD(string, false) ?: date
    }

}