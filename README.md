[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/wTylcrtj)
# 📱 Mobile Task Manager – Assignment 2

This repository contains the implementation for **Assignment 2** of the Task Manager App project.

## 🎯 Assignment Goal

Define a data representation for tasks that tracks tracks attributes and create a menu-based CLI that is structured around functions. The program will handle a mutable list of tasks with no data persistence and options to add tasks, mark a task and done, list all tasks and filter finished and unfinished tasks.
## ✅ Implemented Features

- Initial definition of a _Task_ data representation that tracks id, title, description, due date, category and completion status.
- Auxiliary functions to handle a Task, including functions to turn a Task into a printable String, to create a new Task, and to mark a Task as complete.
- Use of a MutableList to store Tasks in memory only.
- Use of a menu to show available options to the user and read a choice, which is then validated.  
- Use of functions to handle basic operations.
- Implemented addTask, which reads the Task info from the CLI, creates a new Task, and adds it to the list.
- Implemented markTaskDone, which marks a Task with a given ID as finished/done.
- Implemented listTasks, which prints the information of all existing Tasks.
- Implemented filterTasks, which filters Tasks according to their completion status and prints them. 

## 🚧 Known Issues

- No known issues.

## 📝 Notes

- The program is structured in 3 .kt files; Main.kt handles the menu, Functions.kt holds all the functions used in the menu, and Task.kt holds information related to the Task data representation, including auxiliary functions.
- A Task`s ID is considered to be auto-incremental, this means the user cannot input a new task´s ID, the ID will start at 0 and increase for all new tasks created.
- Tasks are always considered to begin undone.
- It is considered necessary for a task to have a title, an ID, and a completion status; description, category and due date are considered optional, and will be null if not entered under the assumption future upgrades will allow the user to modify a task´s attributes after creation.
- Tasks are printed with all attributes to check if they are correct.
---

> This assignment is part of the Mobile Development course at Escuela Superior de Enxeñaria Informatica - Universidade de Vigo.  
> See the course syllabus and lab instructions for more details.
