package gal.uvigo.mobileTaskManager.model

import android.icu.number.IntegerWidth
import java.time.LocalDate

fun LocalDate.formattedDueDate(): String {
    return this.monthValue.toString().padStart(2, '0') + " " + this.dayOfMonth.toString()
        .padStart(2, '0')
}


/**
 * Assumes the created date is always in the future
 */
fun LocalDate.createDateFromMMDD(mmdd: String): LocalDate {
    val currentYear = LocalDate.now().year
    val parsed = mmdd.split(" ")
    val day = Integer.parseInt(parsed[1])
    val month = Integer.parseInt(parsed[0])
    var new = LocalDate.of(currentYear, month,day)
    //Ensures the date is in the future
    if(!new.isFutureDate()) new = LocalDate.of(currentYear+1, month,day)
    return new
}

fun LocalDate.isFutureDate(): Boolean = this >= LocalDate.now()