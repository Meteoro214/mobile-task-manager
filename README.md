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

- I have assumed that sync operations are performed on enqueuing order. This means assuming no update or delete operation will be executed to CrudCrud before the Task has been inserted, and no update can happen after a delete. App will not update if task is pending delete (meaning a task was deleted after an update, but before the update synced) but no checks are performed to ensure a Task actually exists on CrudCrud.
- If 2 or more different updates are enqueued to sync with CrudCrud, only the last one will be executed to save on API calls, as the last update would have overwritten the earlier ones when performed.

### 📝 IMPORTANT
CrudCrud API only allows 24h of service OR up to 100 operations. To reset the API, you must obtain a new API key from CrudCrud and change the stored API key in API.xml file.

---

> This assignment is part of the Mobile Development course at Escuela Superior de Enxeñaria Informatica - Universidade de Vigo.  
> See the course syllabus and lab instructions for more details.




    //Some values that use context for network error messages,stored to not store a context
    private val logTag = context.getString(R.string.Log_Tag)
    private val uploadErrorMsg = context.getString(R.string.network_error_up)
    private val downloadErrorMsg = context.getString(R.string.network_error_down)
    private val toastMsg = Toast.makeText(context, "", Toast.LENGTH_SHORT)

LECTURA
La primera vez, se leen los datos del server porque Room estara vacio
En proximas ejecuciones intentara leer Room y no del server, se asume que estan sincronizados

ESCRITURA

filosofia offline primero
Se aplican las escrituras a Room y a la vez se ejecuta sobre el server
Opcional  se solicita la operacion al servidor cuando haya conexion usando workers y hay estado de syncs
hacer un request por cada operacion


hacer un string file que sea de strings hardcoreadas internas

Crear los workers
Hacer repo
hacer lo de orden en general

metodos en el repo para permitir al worker trbaajar con repo