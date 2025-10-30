[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/wTylcrtj)
# 📱 Mobile Task Manager – Assignment 8

This repository contains the implementation for **Assignment 8** of the Task Manager App project.

## 🎯 Assignment Goal

Update the app architecture to use a ViewModel with LiveData to make reactive UI updates.

## ✅ Implemented Features

- Deleted TaskCollection.
- Refactored TaskCollection into TaskViewModel.
- Reorganized file organization : renamed fragments package to ui, and created a tasklist package to hold TaskListFragment & TaskAdapter.

- Implemented TaskViewModel, which uses LiveData to hold a List of Tasks. There is a private MutableLiveData, which is exposed as LiveData.
- TaskViewModel has CRUD functions : 
    - addTask(), which accepts both a full task and the info for a new task with an ID given by the viewmodel
    - updateTask(task), which updates the existing task with the new task (ID is used to identify tasks, and cannot be edited).
    - get methods, both by ID and Index.
    - deleteTask(taskID)

- Refactored TaskDetailFragment to use TaskViewModel. Also, now receives an ID instead of a task when navigating from TaskListFragment & implemented a delete option.
- Refactored TaskFormFragment to use TaskViewModel.
- Refactored TaskAdapter to use TaskViewModel & implemented submitTaskList() to update the task list.
- Refactored TaskListFragment to use TaskViewModel. Also, now sets an observer to update the TaskAdapter when the live data changes.
- Updated some error messages to use String resources instead of hardcoded Strings.

## 🚧 Known Issues

- No known issues.

## 📝 Notes

- Merged model into the same directory (github was bugged, local files were in the same directory).
- The assignment recommended implementing markTaskDone(id) on the TaskViewModel. I did not implement it because I do not allow the user to mark the task as done without doing a full update. 

---

> This assignment is part of the Mobile Development course at Escuela Superior de Enxeñaria Informatica - Universidade de Vigo.  
> See the course syllabus and lab instructions for more details.
> See the course syllabus and lab instructions for more details.