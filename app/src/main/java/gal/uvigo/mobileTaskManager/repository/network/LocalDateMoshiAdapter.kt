package gal.uvigo.mobileTaskManager.repository.network

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import gal.uvigo.mobileTaskManager.model.createDateFromMMDD
import gal.uvigo.mobileTaskManager.model.formattedDueDate
import java.time.LocalDate

/**
 * Class to allow Moshi to handle LocalDates.
 */
class LocalDateMoshiAdapter {

    @ToJson
    fun toJson(date: LocalDate): String = date.formattedDueDate()

    @FromJson
    fun fromJson(string: String): LocalDate {
        val date = LocalDate.of(1, 1, 1)
        //should never give a null
        return date.createDateFromMMDD(string) ?: date
    }

}