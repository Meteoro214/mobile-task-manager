package gal.uvigo.mobileTaskManager.repository.networking

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class LocalDateJsonAdapter {
    private val FORMATTER: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE

    // Serialize the LocalDate to String
    @ToJson
    fun toJson(date: LocalDate): String {
        return date.format(FORMATTER)
    }

    // Parse the String back to a LocalDate
    @FromJson
    fun fromJson(date: String): LocalDate {
        return LocalDate.parse(date, FORMATTER)
    }
}