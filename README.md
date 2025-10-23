[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/wTylcrtj)
# 📱 Mobile Task Manager – Assignment 7

This repository contains the implementation for **Assignment 7** of the Task Manager App project.

## 🎯 Assignment Goal

Implement the Task Form to create & edit Tasks while using 2-way Data Binding.

## ✅ Implemented Features

- Task info is now created by the user and is no longer hardcoded. In consequence, TaskGenerator has been removed.
- Task Controller has been finally removed.It is succeeded by TaskRepository, a Singleton which holds a task collection and the next ID for a task, and has methods to add and retrieve tasks from the collection.
- Refactored TaskAdapter & TaskListFragment to use the TaskRepository instead of a TaskCollection.
- Modified nav_graph to have distinct titles on all fragments, add the action to edit a task, and remove unused actions for back navegation.
- Created task_detail_menu.xml, which is inflated on TaskDetailFragment and gives the user the option to edit the current task.
- TaskDetailFragment now has an onResume() method to load edited task data passed by TaskForm using the SavedStateHandle.
- Redid the TaskDetail UI in fragment_task_detail.xml.
- Redid the TaskForm UI in fragment_task_form.xml, now uses 2-way data binding in title, description and isDone status.
- TaskForm UI also uses an AutoCompleteTextView to ask for the category.
- Created & modified different String resources, including new keys for the SavedStateHandle & strings to have a better interface with Category names and not have them hardcoded as the enum values.
- Created a String-array of the category names string resources to use in the AutoCompleteTextView.
- In order for the 2-way data binding to work, Task.kt has been modified. Title can now be empty, and Category & dueDate can be null. For all 3 attributes, checks will be done on the Form & when adding to the repository/collection to ensure title is never empty and no null values exist. 
- Because dueDate can be null (and it will be null as a placeholder on the Form), LocalDate.formattedDueDate() is no longer used in the form as a data bind; instead a new method exists on Task to return the formatted dueDate if it is not null, or an empty string if it is null.
- Implemented fun LocalDate.createDateFromMMDD(mmdd: String): LocalDate? to parse a date from the form and return it if it is valid (or null if it isn`t)
- Implemented TaskFormFragment.kt to handle Form logic. Notable code includes:
- Code to load a task from TaskRepository if a Task ID is passed by SafeArgs (for editing); or create a new placeholder task if no ID is received.
- Code to configure the AutoCompleteTextView.
- Code to verify Task Info (checking title is not empty + handling Category & Date, which are not binded 2 way).
- Code to save a task if the user presses the save button (after data is verified).
- Code to store inputted data on the SavedStateHandle if the user changes fragment or pauses the app, and to retrieve such saved data when the user returns. 

## 🚧 Known Issues

- No known issues.

## 📝 Notes

- Task Information is not persistent yet.
- Downgraded AGP version because lab PC was not compatible with 8.13.0.
- Category names are created as a String resource to use better names than default (PERSONAL, OTHER...) & to permit future updates. Furthermore, they are stored as String resources and referenced on the String-array resource to allow access to a single name instead of needing to search the array.
- String-array & enum category order must be maintained or the names will not be correct.

---

> This assignment is part of the Mobile Development course at Escuela Superior de Enxeñaria Informatica - Universidade de Vigo.  
> See the course syllabus and lab instructions for more details.