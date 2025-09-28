[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/wTylcrtj)
# 📱 Mobile Task Manager – Assignment 3

This repository contains the implementation for **Assignment 3** of the Task Manager App project.

## 🎯 Assignment Goal

Refactor the previous work to apply an object oriented architecture, including a Task class to represent tasks, and a TaskController to encapsulate operations. Also create an extension function to format the due date, and create a enum to handle categories.
## ✅ Implemented Features

- Implemented extension function LocalDate.formattedDueDate() on Extensions.kt to format a date. 
- Implemented Category enum in Category.kt to handle task category. Implemented options are : OTHER, WORK, PERSONAL, URGENT
- Implemented extra class TaskCollection to make TaskController independent from the collection used to hold Tasks.
- Implemented TaskController to handle task operations.
- Implemented a Task class to represent tasks.
- Refactored Main.kt.
- Changed ID generation logic and avoided using ID based indexing on TaskCollection to prevent unexpected behaviour and possible crashes.
- Implemented auxiliary extension function LocalDate.isFutureDate() to check whenever a date is a future date.

## 🚧 Known Issues

- No known issues.

## 📝 Notes
- ID now starts at 1.
- The program is structured in 6 .kt files; Main.kt handles the menu and I/O operations, TaskController.kt holds the TaskController class, Task.kt holds the Task class, Category.kt holds the Category enum, Extensions.kt holds extension functions for LocalDate, and TaskCollection holds an auxiliary class TaskCollection to encapsulate operations over the Task List
- A Task`s ID is considered to be auto-incremental, this means the user cannot input a new task´s ID,
- Tasks are always considered to begin undone.
- It is considered necessary for a task to values on all fields except for the description, which will be empty if not entered under the assumption future upgrades will allow the user to modify a task´s attributes after creation.

---

> This assignment is part of the Mobile Development course at Escuela Superior de Enxeñaria Informatica - Universidade de Vigo.  
> See the course syllabus and lab instructions for more details.
