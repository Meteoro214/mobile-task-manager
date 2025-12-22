package gal.uvigo.mobileTaskManager.ui.tasklist.adapter

/**
 * Methods to bind information to the XML layout files.
 */
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import gal.uvigo.mobileTaskManager.R
import gal.uvigo.mobileTaskManager.model.Category
import gal.uvigo.mobileTaskManager.model.Task
import gal.uvigo.mobileTaskManager.model.formattedDueDate
import java.time.LocalDate

/**
 * For a text view, receives a category and sets its text and color with String Resources from the given category.
 */
@BindingAdapter("category")
fun bindCategoryText(tv: TextView, category: Category?) {
    tv.text = when (category) {
        Category.OTHER -> tv.context.getString(R.string.other_cat)
        Category.WORK -> tv.context.getString(R.string.work_cat)
        Category.PERSONAL -> tv.context.getString(R.string.personal_cat)
        Category.URGENT -> tv.context.getString(R.string.urgent_cat)
        else -> ""
    }
    if (tv !is AutoCompleteTextView) {
        val color = when (category) {
            Category.OTHER -> tv.context.getColor(R.color.category_other)
            Category.WORK -> tv.context.getColor(R.color.category_work)
            Category.PERSONAL -> tv.context.getColor(R.color.category_personal)
            Category.URGENT -> tv.context.getColor(R.color.category_urgent)
            else -> tv.context.getColor(R.color.category_other)
        }
        tv.setTextColor(color)
    }
}

/**
 * For a View, receives a category and sets its background color with String Resources from the given category.
 */
@BindingAdapter("cat_background_color")
fun bindCategoryColorBackground(v: View, category: Category?) {
    val color = when (category) {
        Category.OTHER -> v.context.getColor(R.color.category_other)
        Category.WORK -> v.context.getColor(R.color.category_work)
        Category.PERSONAL -> v.context.getColor(R.color.category_personal)
        Category.URGENT -> v.context.getColor(R.color.category_urgent)
        else -> v.context.getColor(R.color.category_other)
    }
    v.setBackgroundColor(color)
}

/**
 * For a TextView, receives a dueDate and sets its text with the formatted date as MM DD.
 */
@BindingAdapter("dueDate")
fun bindDueDateText(tv: TextView, dueDate: LocalDate?) {
    tv.text = dueDate?.formattedDueDate(year = false) ?: ""
}

/**
 * For a TextView, receives a dueDate and sets its text with the formatted date as MM DD YYYY.
 */
@BindingAdapter("dueDateYear")
fun bindDueDateYearText(tv: TextView, dueDate: LocalDate?) {
    tv.text = dueDate?.formattedDueDate() ?: ""
}

/**
 * For a TextView, receives a task and sets its color according to the dueDate & isDone status
 */
@BindingAdapter("expected_date")
fun bindDueDateColor(tv: TextView, task: Task) {
    val color = if (!task.isDone) {
        val currentDate = LocalDate.now()
        if (task.dueDate?.isBefore(currentDate) == true) {
            //if task is undone and already due, red
            tv.context.getColor(R.color.task_undone)
        } else if (task.dueDate?.isEqual(currentDate) == true) {
            //if task is undone and due TODAY, Orange
            tv.context.getColor(R.color.task_pending_today)
        } else if (task.dueDate?.isAfter(currentDate.plusWeeks(1)) == false) {
            //if task is undone and due this week, yellow
            tv.context.getColor(R.color.task_pending_soon)
        } else {
            //else normal color
            tv.context.getColor(R.color.task_default)
        }
    } else {
        tv.context.getColor(R.color.task_default)
    }
    tv.setTextColor(color)
}