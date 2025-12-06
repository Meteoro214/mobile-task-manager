[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/wTylcrtj)
# 📱 Mobile Task Manager – Assignment 12

This repository contains the implementation for **Assignment 12** of the Task Manager App project.

## 🎯 Assignment Goal


## ✅ Implemented Features

- 

## 🚧 Known Issues

- No known issues.

## 📝 Notes


Esto es falso despues de meterle append, reescribir cuando acabe y sepa bien 
investigar el isStopped

- I have assumed that sync operations are performed on enqueuing order. This means assuming no update or delete operation will be executed to CrudCrud before the Task has been inserted, and no update can happen after a delete. App will not update if task is pending delete (meaning a task was deleted after an update, but before the update synced) but no checks are performed to ensure a Task actually exists on CrudCrud.
- If 2 or more different updates are enqueued to sync with CrudCrud, only the last one will be executed to save on API calls, as the last update would have overwritten the earlier ones when performed.

TODO readme

order is only persistent on Room (too many API calls) NVM

### 📝 IMPORTANT
CrudCrud API only allows 24h of service OR up to 100 operations. To reset the API, you must obtain a new API key from CrudCrud and change the stored API key in API.xml file.
There is now an APK for easy installation... but CrudCrud API key is likely to be expired.

---

> This assignment is part of the Mobile Development course at Escuela Superior de Enxeñaria Informatica - Universidade de Vigo.  
> See the course syllabus and lab instructions for more details.
