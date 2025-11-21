[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/wTylcrtj)
# 📱 Mobile Task Manager – Assignment 10

This repository contains the implementation for **Assignment 10** of the Task Manager App project.

## 🎯 Assignment Goal


## ✅ Implemented Features

- Updated item_Task.xml. Now uses BindingAdapters for category (uses resource strings and colors) & dueDate (call to formattedDueDate,  has colors if task is undone and dueDate has passed/is today / is this week).
- Removed isFutureDate function as it`s uses could be done with an existing function.
- Task class now does not require dueDate to not have passed in constructor to allow past dates to exist. Also removed getStringDate() as the call to formattedDueDate() is now done in a BindingAdapter, and implemented equals() to work with the new TaskListItem.
- Implemented markTaskDone() across the app. This includes functions on TaskDAO, TaskRepository and TaskViewModel.
- TaskDB updated to version 3, as Task dueDate is now stored as MM DD YYYY. 
- Created item_header.xml.
- fragment_task_form.xml and fragment_task_detail.xml now use BindingAdapters to handle showing date & category.
- Created multiple BindingAdapter functions: bindCategoryText() to set category string resource and color, bindCategoryColorBackground() to set color to the Header line, bindDueDateText() to show formatted dueDate, and bindDueDateColor() to put color on dueDates due soon.
- Created package adapter inside package tasklist to contain BindingAdapters.kt, TaskListItem.kt and the refactored TaskAdapter.kt.
- Created extension function formattedDueDateWithYear() to handle new dates with years.
- Refatored function createDateFromMMDD() to allow creating from MM DD YYYY.
- App now allows Tasks to store the dueDate year on the DB. Task dueDate cannot be set in the past with set method or on Task creation, but a Task loaded from TaskDB may have a dueDate in the past (editting it will change the year).
- TaskViewModel now updates the internal Task cache on TaskRepository to fix an editing bug.
- Updated TaskAdapter to extend from ListAdapter.
- App now has Headers. Implemented TaskListItem to have different items: Header (holds a category) and TaskItem (holds a Task). TaskViewModel now exposes a taskListItems, which is a LiveData List of TaskListItem, which is ordered on load. 
- TaskListFragment now implements swipping actions to mark a task as done and to delete a Task.
- TaskListFragment now implements dragging actions to reorder the task list. Reordered task list will only persist until taskListItems is updated on the TaskViewModel (meaning a task was created/updated/deleted) or until TaskListFragment is reloaded.

## 🚧 Known Issues

- No known issues.

## 📝 Notes

- Added kapt dependency for BindingAdapters.
-  Reordered task list will only persist until taskListItems is updated on the TaskViewModel (meaning a task was created/updated/deleted) or until TaskListFragment is reloaded.

---

> This assignment is part of the Mobile Development course at Escuela Superior de Enxeñaria Informatica - Universidade de Vigo.  
> See the course syllabus and lab instructions for more details.