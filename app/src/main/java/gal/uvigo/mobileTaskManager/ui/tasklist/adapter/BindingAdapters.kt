package gal.uvigo.mobileTaskManager.ui.tasklist.adapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import gal.uvigo.mobileTaskManager.R
import gal.uvigo.mobileTaskManager.data_model.Category
import gal.uvigo.mobileTaskManager.data_model.formattedDueDate
import java.time.LocalDate

@BindingAdapter("category")
fun bindCategoryText(tv: TextView, category: Category?) {
    tv.text =  when(category){
        Category.OTHER -> tv.context.getString(R.string.other_cat)
        Category.WORK -> tv.context.getString(R.string.work_cat)
        Category.PERSONAL -> tv.context.getString(R.string.personal_cat)
        Category.URGENT -> tv.context.getString(R.string.urgent_cat)
        else -> ""
    }
}

@BindingAdapter("dueDate")
fun bindDueDateText(tv: TextView, dueDate: LocalDate?) {
    tv.text = dueDate?.formattedDueDate() ?: ""
  }