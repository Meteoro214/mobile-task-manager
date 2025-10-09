[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/wTylcrtj)
# 📱 Mobile Task Manager – Assignment 5

This repository contains the implementation for **Assignment 5** of the Task Manager App project.

## 🎯 Assignment Goal

Implement a new UI using ReciclerView, Data Binding and ContraintLayout.

## ✅ Implemented Features

- Implemented DataGenerator to create dummy data for UI testing.
- Refactored MainActivity.kt to use DataBinding and ReciclerView, onCreate functionalities have been split into smaller functions. listTasks has been removed due to being deprecated, as the ReciclerView already updates tasks when needed, and addTask/markTaskDone have been temporarily removed due to being unused.
- Implemented TaskAdapter.
- Modified TaskController to allow the MainActivity access to the TaskCollection, in order to use it on the ReciclerView
- Changed activity_main.xml to make use of constraints and a ReciclerView.
- Created item_task.xml.


## 🚧 Known Issues

- No known issues.

## 📝 Notes

- All .kt source files from the previous week (except MainActivity) are maintained and mostly unchanged.
- The layout in item_task.kt includes an extra View than required to show the Task Category.
- Updated kotlin version.

---

> This assignment is part of the Mobile Development course at Escuela Superior de Enxeñaria Informatica - Universidade de Vigo.  
> See the course syllabus and lab instructions for more details.
