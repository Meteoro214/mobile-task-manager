[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/wTylcrtj)
# 📱 Mobile Task Manager – Assignment 11

This repository contains the implementation for **Assignment 11** of the Task Manager App project.

## 🎯 Assignment Goal

Replace Room-based persistence with remote access via REST API using the CrudCrud service, Retrofit and Moshi.

## ✅ Implemented Features

- App now has Internet permissions.
- Annotated Task Class so Moshi can parse it to/from JSON.
- CrudCrud API key is and URL are stored in API.xml resource file to allow easy changes to API key.
- Created package networking to store all new files related to networking with CrudCrud.
- Created TaskApiService interface to serve as the service for Retrofit.
- Created LocalDateJsonAdapter so Moshi can handle Localdates.
- Created RetrofitClient to handle Moshi & Retrofit creation and expose the service, all as a singleton.
- Created TaskCC to handle retrieving Tasks from CrudCrud. Because CrudCrud returns tasks with _id but requires tasks to be sent without _id.
- Created CrudCrudAPI to handle communication with CrudCrud via TaskServicePI. CrudCrudAPI exposes CLUD methods to the repository. It also stores a Map that holds the relation between a task id and its CrudCrud _id. 
- Refactored TaskRepository to use Retrofit networking instead of Room. This includes handling the creation of Task ID and maintaining a private MutableLiveData that is created from the List of task received from CrudCrudAPI on start. This MutableLiveData will be stored on memory to avoid retrieving all tasks from CrudCrud whenever a CRUD operation is performed, and be exposed as a LiveData.
- Minor changes to TaskViewModel : taskListItems is now also ordered by id, and created init block to invoke TaskRepository.download() to retrieve tasks from CrudCrud.
- Added a notifyItemChanged() when app fails to delete a task when swiping (only possible if CrudCrud tokens run out) to prevent the list item from remaining stuck. 
- Added some messages and logging when network operations fail.

## 🚧 Known Issues

- No known issues.

## 📝 Notes

- Refactored some dependencies that needed the same versions to force the same version.

### 📝 URGENT
CrudCrud API only allows 24h of service OR up to 100 operations. To reset the API, you must obtain a new API key from CrudCrud and change the stored API key in API.xml file.

---

> This assignment is part of the Mobile Development course at Escuela Superior de Enxeñaria Informatica - Universidade de Vigo.  
> See the course syllabus and lab instructions for more details.