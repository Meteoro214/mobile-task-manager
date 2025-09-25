import java.time.LocalDate

fun LocalDate.formattedDueDate() : String {
    return this.monthValue.toString().padStart(2,'0') +" "+ this.dayOfMonth.toString().padStart(2,'0')
}