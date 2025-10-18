[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/wTylcrtj)
# 📱 Mobile Task Manager – Assignment 6

This repository contains the implementation for **Assignment 6** of the Task Manager App project.

## 🎯 Assignment Goal

Restructure the application to use a single activity - multiple fragment architecture.

## ✅ Implemented Features



- Implemented DataGenerator to create dummy data for UI testing.
- Refactored MainActivity.kt to use DataBinding and ReciclerView, onCreate functionalities have been split into smaller functions. listTasks has been removed due to being deprecated, as the ReciclerView already updates tasks when needed, and addTask/markTaskDone have been temporarily removed due to being unused.
- Implemented TaskAdapter.
- Modified TaskController to allow the MainActivity access to the TaskCollection, in order to use it on the ReciclerView
- Changed activity_main.xml to make use of constraints and a ReciclerView.
- Created item_task.xml.

- Refactored MainActivity.kt to use fragments & navigation via a menu.
- Implemented TaskListFragment.kt, TaskDetailFragment.kt and TaskFormFragment.kt to handle Fragment logic, all of them use DataBinding.
- Moved over ReciclerView & data creation logic to TaskListFragment from MainActivity.
- Made Task class Parcelable.
- Added a new method to TaskCollection to allow TaskAdapter to retrieve Tasks by their index over their ID.
- Modified DataGenerator to not create more data every time TaskListFragment is created.
- Modified activity_main.xml to create a Toolbar & use a NavHostFragment.
- Created fragment_task_{detail/form/list}.xml to as layouts of the 3 implemented fragments.
- Created app_bar_menu.xml & nav_graph.xml to handle fragment navigation via a menu.
- TaskAdapter now sets a listener on all the TaskHolders to handle navigation to Task Details.


## 🚧 Known Issues

- No known issues.

## 📝 Notes

- TaskController remains implemented, but it is deprecated and will probably be removed soon.
- Aplication Theme remains untouched, note that it has no ActionBar by default.
- TaskForm exists, but it is not functional.
- Task info is generated via DataGenerator, Tasks still cannot be created or modified in any form.

---

> This assignment is part of the Mobile Development course at Escuela Superior de Enxeñaria Informatica - Universidade de Vigo.  
> See the course syllabus and lab instructions for more details.