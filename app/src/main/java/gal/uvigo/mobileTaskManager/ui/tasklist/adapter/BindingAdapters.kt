package gal.uvigo.mobileTaskManager.ui.tasklist.adapter

import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import gal.uvigo.mobileTaskManager.R
import gal.uvigo.mobileTaskManager.data_model.Category
import gal.uvigo.mobileTaskManager.data_model.Task
import gal.uvigo.mobileTaskManager.data_model.formattedDueDate
import java.time.LocalDate

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
            Category.OTHER -> tv.context.getColor(R.color.grassgreen)
            Category.WORK -> tv.context.getColor(R.color.skyblue)
            Category.PERSONAL -> tv.context.getColor(R.color.purple)
            Category.URGENT -> tv.context.getColor(R.color.red)
            else -> tv.context.getColor(R.color.gray)
        }
        tv.setTextColor(color)
    }
}

@BindingAdapter("cat_background_color")
fun bindCategoryColorBackground(v: View, category: Category?) {
    val color = when (category) {
        Category.OTHER -> v.context.getColor(R.color.grassgreen)
        Category.WORK -> v.context.getColor(R.color.skyblue)
        Category.PERSONAL -> v.context.getColor(R.color.purple)
        Category.URGENT -> v.context.getColor(R.color.red)
        else -> v.context.getColor(R.color.gray)
    }
    v.setBackgroundColor(color)
}

@BindingAdapter("dueDate")
fun bindDueDateText(tv: TextView, dueDate: LocalDate?) {
    tv.text = dueDate?.formattedDueDate() ?: ""
}

@BindingAdapter("expected_date")
fun bindDueDateColor(tv: TextView, task: Task) {
    val color = if (!task.isDone) {
        val currentDate = LocalDate.now()
        if (task.dueDate?.isBefore(currentDate) == true) {
            //if task is undone and already due, red
            tv.context.getColor(R.color.red)
        } else if (task.dueDate?.isEqual(currentDate) == true) {
            //if task is undone and due TODAY, Orange
            tv.context.getColor(R.color.orange)
        } else if (task.dueDate?.isAfter(currentDate.plusWeeks(1)) == false) {
            //if task is undone and due this week, yellow
            tv.context.getColor(R.color.yellow)
        } else {
            //else normal color
            tv.context.getColor(R.color.black)
        }
    } else {
        //there is a strange bug ONLY in a testing version that does not force future dates
        // the bug causes dueDates on TaskList to be colored when task is done in really strange ways when view is modified from TaskList (scrolling & markDone)
        //the bug causes non-deterministic date coloring, ONLY on tasks marked as done, no matter the date, markDone can cause the color to shift between colors
        tv.context.getColor(R.color.black)
    }
    tv.setTextColor(color)
}