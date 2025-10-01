[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/wTylcrtj)
# 📱 Mobile Task Manager – Assignment 4

This repository contains the implementation for **Assignment 4** of the Task Manager App project.

## 🎯 Assignment Goal

Build a barebones Android app reusing the previous week`s model. The new task manager will be a single-screen app that shows all stored tasks and allows for new tasks to be added, or to mark existing tasks as done

## ✅ Implemented Features

- Migrated to an Android proyect.
- Maintained the previously created Model, unchanged except for a minor bug fix on Task.
- New Tasks are now hardcoded with only ID and title being changeable, all tasks will start undone, with the current date as a due date,no description and category "Other".
- Implemented MainActivity.kt, with methods addTask, listTasks, and markTaskDone. MainActivity handles operations over the app screen and validates user input.
- Implemented a basic layout in activity_main.xml.


## 🚧 Known Issues

- No known issues.

## 📝 Notes

- All Model source files from the previous week (Task.kt, TaskCollection.kt, Extensions.kt, Category.kt) are maintained and were ported unchanged (save for a bug fix on the dueDate setter in Task), and placed in a Model directory.
- Maintained the TaskController class to separate functionalities and maintain a Model-View-Controller pattern.
- Refactored TaskController lightly to not hardcode getAllTasks and filterTasks result formatting.
- The layout in activity_main.kt includes extra Views than those required, the most significant is a TextView to show action feedback to the user.

---

> This assignment is part of the Mobile Development course at Escuela Superior de Enxeñaria Informatica - Universidade de Vigo.  
> See the course syllabus and lab instructions for more details.
