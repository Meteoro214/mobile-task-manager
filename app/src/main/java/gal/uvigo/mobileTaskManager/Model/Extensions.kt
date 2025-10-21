package gal.uvigo.mobileTaskManager.model

import java.time.DateTimeException
import java.time.LocalDate

fun LocalDate.formattedDueDate(): String {
    return this.monthValue.toString().padStart(2, '0') + " " + this.dayOfMonth.toString()
        .padStart(2, '0')
}


/**
 * Assumes the created date is always in the future.
 * Returns null if format or date is not valid
 */
fun LocalDate.createDateFromMMDD(mmdd: String): LocalDate? =
    if (!Regex("(0[1-9]|1[012]) (0[1-9]|[12][0-9]|3[01])").matches(mmdd)) {
        null
    } else {
        val currentYear = LocalDate.now().year
        val parsed = mmdd.split(" ")
        val day = Integer.parseInt(parsed[1])
        val month = Integer.parseInt(parsed[0])
        var toRet: LocalDate?
        try {
            toRet = LocalDate.of(currentYear, month, day)
            //Ensures the date is in the future if it is calid
            if (!(toRet?.isFutureDate() ?: true)) {
                LocalDate.of(currentYear + 1, month, day)
            } else toRet

        } catch (e: DateTimeException) { //Invalid date null
            null
        }
    }


fun LocalDate.isFutureDate(): Boolean = this >= LocalDate.now()