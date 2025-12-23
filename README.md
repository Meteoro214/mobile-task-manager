[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/wTylcrtj)
# 📱 Mobile Task Manager – Checkpoint 2

This repository contains the implementation for **Checkpoint 2** of the Task Manager App project.

## 🎯 Assignment Goal

This checkpoint contains all work done on the project so far. This document will speak about assignments 7-13, but the work of checkpoint-1 (assignments 1-6) is included

## ✅ Implemented Features

- Task Form was implemented, using 2-way data binding for some fields.

- Task Form and Task Detail are now Bottom Sheets with a greatly improved UI/UX.

- The whole application now has a proper theme & styles, and uses Material 3 components in the UI.

- App now has both english & spanish localizations, being english the default language.

- The app architecture now follows a 3 layer architecture with the MVVM pattern.

- The app`s 3 fragments now use a shared ViewModel that exposes task data via LiveModel for CRUDL operations. The task list exposed is grouped by category and ordered by a user defined ordering, which is persistent. 

- TaskList shows all the existing tasks, grouped by category and ordered inside the categories according to how the user orders them, or by their creation date by default. The user can drag tasks to reorder, or swipe them to delete/mark as done. There is also a Floating Action Button to add tasks. A BindingAdapter is used for some special formats, like coloring the task due date according to how much time remains.

- There is now local and remote data persistence, both of which make use of Kotlin corroutines. Data is handled on a local-first approach, only downloading remote data when app is first installed.  

- Local data persistence is achieved using Room.

- Remote data persistence is achieved using Retrofit to connect to an online REST server, CrudCrud. Moshi is also used to serialize task information to JSON.

- Workers are used to handle local-remote data synchronization.

- All access to data is handled by TaskRepository, which acts as the Single Source of Truth for the app and coordinates data between Room and CrudCrud

- There are now 4 different Task classes : Task (default class with the known attributes, the only one exposed to the UI), TaskEntity (the one stored on Room database, holds all Task attributes + remote _id, position for persistent reordering, and syncStatus for sync operations), TaskSendDTO (used to send data to the REST API, holds all info from TaskEntity except remote _id and syncStatus) and TaskGetDTO (used to receive data from REST API, holds all info from TaskSendDTO plus the remore _id). Task no longer has annotations, TaskEntity has Room annotations, and both TaskDTOs have Moshi annotations.

- Now code is organized in 3 main packages corresponding to the architecture layers: ui, model & repository.

- The ui package holds the files that implement the View Layer: the MainActivity, the 3 fragments & the tasklist package, which holds the TaskListFragment and the adapter package, which holds classes related to how the data is presented (the Adapter, BindingAdapters and TaskListItem definitions)

- The model package holds the files related to the model and the business logic of the app (mostly how we represent the Tasks as exposed to the user, and the TaskViewModel).

- The repository package holds files related to the data layer of the app. It includes TaskRepository, which coordinates the handling of data from the different data sources, TaskMapper, which is used by TaskRepository to transform Tasks between the 4 representations, and 3 subpackages : local, network and sync.

- The sync package holds files related to sync operations with CrudCrud, which are the SyncStatus enum used to know a Task´s sync status and TaskUploadWorked, that implements a Worker to communicate with TaskRepository to upload Tasks.

- The local package holds files related to the Room database and its operations, including TaskDB, TaskDAO, a LocalDateConverter and TaskEntity.

- The network package holds files related to the REST API and its operations, including TaskApiService, RetrofitClient, a LocalDateAdapter and TaskDTOs (both TaskGetDTO & TaskSendDTO).

## 🚧 Known Issues

- No known issues.

## 📝 Notes

- AndroidManifest includes permission to use Internet in order to use REST API.
- The checkpoint includes an APK for testing.
- The app has a custom app icon.

### 📝 IMPORTANT

CrudCrud API only allows 24h of service OR up to 100 operations. To reset the API, you must obtain a new API key from CrudCrud and change the stored API key in API.xml file.

---

> This assignment is part of the Mobile Development course at Escuela Superior de Enxeñaria Informatica - Universidade de Vigo.  
> See the course syllabus and lab instructions for more details.