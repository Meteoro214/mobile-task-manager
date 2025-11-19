package gal.uvigo.mobileTaskManager.data_model

import java.time.DateTimeException
import java.time.LocalDate

fun LocalDate.formattedDueDate(): String =
    this.monthValue.toString().padStart(2, '0') + " " + this.dayOfMonth.toString()
        .padStart(2, '0')


/**
 * Assumes the created date is always in the future.
 * Returns null if format or date is not valid
 * Will force a leap year for 29th february & the next year if date has passed.
 */
fun LocalDate.createDateFromMMDD(mmdd: String): LocalDate? =
    if (!Regex("(0[1-9]|1[012]) (0[1-9]|[12][0-9]|3[01])").matches(mmdd)) {
        null
    } else {
        val currentYear = LocalDate.now().year
        val parsed = mmdd.split(" ")
        val day = Integer.parseInt(parsed[1])
        val month = Integer.parseInt(parsed[0])
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
            //Ensures the date is in the future if it is valid, can only be in the past if not leap
            if (toRet?.isBefore(LocalDate.now()) == true) {
                LocalDate.of(currentYear + 1, month, day)
            } else toRet
        } catch (e: DateTimeException) { //Invalid date null
            null

        }
    }