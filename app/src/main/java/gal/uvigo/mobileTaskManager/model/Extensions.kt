package gal.uvigo.mobileTaskManager.model

import java.time.DateTimeException
import java.time.LocalDate

/**
 * Extension functions to handle dueDate formats.
 */


/**
 * For this date, returns a String of the date formatted.
 * If year is true, format is MM DD YYYY, else, it is MM DD.
 */
fun LocalDate.formattedDueDate(year: Boolean = true): String =
    this.monthValue.toString().padStart(2, '0') + " " +
            this.dayOfMonth.toString().padStart(2, '0') +
            if (year) {
                " " + this.year.toString().padStart(4, '0')
            } else ""

/**
 * Returns a date created from the given formatted date string.
 * If year is set to false, will expect MM DD and add a year so the date is not in the past.
 * If year is set to true, will expect MM DD YYYY and parse accordingly.
 * Returns null if format or date is not valid.
 * Will force a leap year for 29th february.
 */
fun LocalDate.createDateFromMMDD(formattedString: String, year: Boolean = true): LocalDate? =
    if ((!year && !Regex("(0[1-9]|1[012]) (0[1-9]|[12][0-9]|3[01])").matches(formattedString))
        || (year && !Regex("(0[1-9]|1[012]) (0[1-9]|[12][0-9]|3[01]) \\d{4}").matches(formattedString))
    ) {
        null
    } else {
        //Could be simplified, no longer used without year
        val parsed = formattedString.split(" ")
        val day = Integer.parseInt(parsed[1])
        val month = Integer.parseInt(parsed[0])
        val currentYear = if (!year) LocalDate.now().year else Integer.parseInt(parsed[2])
        try {
            val toRet = if (day == 29 && month == 2) { //need to be a leap year
                val nextLeapYear =
                    if (currentYear % 4 == 0 && (currentYear % 100 != 0 || currentYear % 400 == 0)) {
                        //Current year is leap
                        currentYear
                    } else {
                        //Current year is not leap, next leap year is 1-4 years away
                        currentYear + 4 - (currentYear % 4)
                    }
                LocalDate.of(nextLeapYear, month, day)

            } else { //does not need a leap year
                LocalDate.of(currentYear, month, day)
            }
            //If needed, ensures the date is in the future if it is valid, can only be in the past if not leap
            if (!year && toRet?.isBefore(LocalDate.now()) == true) {
                LocalDate.of(currentYear + 1, month, day)
            } else toRet
        } catch (_: DateTimeException) { //Invalid date null
            null
        }
    }