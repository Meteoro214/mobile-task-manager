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

los argumentos se definen en el fragment destino




Accion se define en el fragmento origen, incluye id del destino y el id de la propia accion
En el .kt se definen listeners que ejecuten la navegacion

App -> MainActivity(tiene fragContainer/navHostFragment)-> Se carga el origen del grafo al fragmento (startDestination), creando una instancia de ese fragmento (se infla su xml creando la vista)
es posible un acceso a elementos del fragment antes de que esten inflados = hay un callback interno que se ejecuta cuando esta todo creado, onViewCreated, donde se debe overwritear y asociar listeners y mas

crtl o muesra los metodos overwriteables
(fragment)Directions es una clase generada a partir del grafo con las actions del fragmento
Para convertir la clase en serializable: se puede convertir en secuencias de bytes
Que implemente Parcelable -> implementar las funciones a sobreescribir (metodos para converetir a secuencias de bytes y a la inversa)
Implementar un companion object CREATOR para convertir de bytes a objeto : createFromParcel(convierte de parcel a objeto con el constructor mismo) y newArray(para trabajar con una serie de muchas tareas, no se suele usar)
El orden en el que se leen los datos tiene que ser el mismo en el que se escriben 
describe contents no se suele usar,es para cuando se parcela una estructura de clases, 0 indica que los datos son simples, todos los que haremos son asi

arguments el nombre es el mismo de la variable, y el tipo el tipo