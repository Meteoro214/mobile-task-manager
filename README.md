[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/wTylcrtj)
# 📱 Mobile Task Manager – Assignment 12

This repository contains the implementation for **Assignment 12** of the Task Manager App project.

## 🎯 Assignment Goal

Refine the app architeture: Use the Repository pattern to coordinate data from both local Room database and remote REST API. Modify the Task data models to have separate models for the app (Task), the Room database (TaskEntity) and the REST API (TaskDTOs). Adapt the app to a offline-first approach with REST API synchronization.

Extensions: Support eventual synchronization pattern by enqueuing WorkRequests. Also implemented persistent reordering of Tasks.

## ✅ Implemented Features

- There are now 4 different Task classes : Task (default class with the known attributes, the only one exposed to the UI), TaskEntity (the one stored on Room database, holds all Task attributes + remote _id, position for persistent reordering, and syncStatus for sync operations), TaskSendDTO (used to send data to the REST API, holds all info from TaskEntity except remote _id and syncStatus) and TaskGetDTO (used to receive data from REST API, holds all info from TaskSendDTO plus the remore _id). Task no longer has annotations, TaskEntity has Room annotations, and both TaskDTOs have Moshi annotations.

- App now implements persistent reordering of Tasks. To handle it, TaskViewModel now only groups Tasks by category without ordering, as ordering is done by the Room database.

- To handle reordering, UI swaps the items and tells the ViewModel to store the swaps done on the repository. Once the task is not being dragged, the ViewModel orders the repository to commit the position changes to Room.

- Implemented TaskMapper.

- Refactored TaskRepository to handle both REST API & Room database, and to prepare and expose sync operations to Workers. 

- Deleted CrudCrudAPI.kt & TaskCC.

- Implemented syncing operations. TaskRepository will prepare a WorkRequest after CUD operations, enqueuing TaskUploadWorkers as unique OneTimeWorkRequests. The TaskUploadWorkers will use the sync method exposed by TaskRepository to synchronize Task information. Since WorkRequests are unique and, for a given Task, share the same id for all operations, Create and Delete WorkRequests will replace previous requests, while Update WorkRequests will be appended to the previous one to ensure the Task is created in CrudCrud. There is also aditional checks to ensure information is consistent on CrudCrud and Room.

- Refactored the code (again) to have a finished structure. There are 3 main packages : ui, model & repository.

- The ui package holds the files that implement the View Layer: the MainActivity, the 3 fragments & the tasklist package, which holds the TaskListFragment and the adapter package, which holds classes related to how the data is presented (the Adapter, BindingAdapters and TaskListItem definitions) 

- The model package holds the files related to the model and the business logic of the app (mostly how we represent the Tasks as exposed to the user, and the TaskViewModel).

- The repository package holds files related to the data layer of the app. It includes TaskRepository, which coordinates the handling of data from the different data sources, TaskMapper, which is used by TaskRepository to transform Tasks between the 4 representations, and 3 subpackages : local, network and sync.

- The sync package holds files related to sync operations with CrudCrud, which are the SyncStatus enum used to know a Task´s sync status and TaskUploadWorked, that implements a Worker to communicate with TaskRepository to upload Tasks.

- The local package holds files related to the Room database and its operations, including TaskDB, TaskDAO, a LocalDateConverter and TaskEntity.

- The network package holds files related to the REST API and its operations, including TaskApiService, RetrofitClient, a LocalDateAdapter and TaskDTOs (both TaskGetDTO & TaskSendDTO).

## 🚧 Known Issues

- No known issues.

## 📝 Notes

- Added an APK to download the app easily. App will not work with CrudCrud due to expired API key, but all functionalities should function anyways (Insert sync should never happen because API returns HTTP 500, updates should not happen because WorkManager should append the update sync Requests to the unfinished Insert sync, and delete can still function without an insertion)

- Data will only be downloaded from CrudCrud when first installing the app. To check if it works, there will be logging messages when the download is attempted.

- There are different error cases with corresponding logger messages for when the syncing WorkRequests fail, the only one expected to actually happen is the 400 error, which means an exception happened when calling the REST API (means CrudCrud expired).

- Documented most of the code to know how it works in the future.

- Refactored String Resource files. strings.xml now holds only UI strings, while all internal strings used to not hardcode data are stored in internal_strings.xml.

- Implemented a colors.xml for night mode (only used for dueDate coloring).

- Refactored item_task.xml so long titles don´t clip into dueDate or category.

- All sync operations will log their results for easy checking.

### 📝 IMPORTANT

CrudCrud API only allows 24h of service OR up to 100 operations. To reset the API, you must obtain a new API key from CrudCrud and change the stored API key in API.xml file.

---

> This assignment is part of the Mobile Development course at Escuela Superior de Enxeñaria Informatica - Universidade de Vigo.  
> See the course syllabus and lab instructions for more details.