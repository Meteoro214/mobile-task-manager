import java.time.LocalDate

fun LocalDate.formattedDueDate() : String {
    return this.monthValue.toString().padStart(2) +" "+ this.getDayOfMonth().toString().padStart(2);
}