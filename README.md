[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/wTylcrtj)
# 📱 Mobile Task Manager – Assignment 9

This repository contains the implementation for **Assignment 9** of the Task Manager App project.

## 🎯 Assignment Goal



## ✅ Implemented Features

- 

## 🚧 Known Issues

- No known issues.

## 📝 Notes

- 

---

> This assignment is part of the Mobile Development course at Escuela Superior de Enxeñaria Informatica - Universidade de Vigo.  
> See the course syllabus and lab instructions for more details.






Room es una abstraccion de la BD, es una API para trabajar con la BD.
Se usan etiquetas para la generacion de codigo en tiempo de compilacion
DAO es la API para las operaciones sobre las tablas
la version en @Database es para indicar la implementacion de la db que puede ir variando, cuando se cambia el esquema de la DB debe cambiarse la version
meter un objeto repositorio intermedio

el livedata ahora almacena la bd no una lista

el nextID hacerlo en la DB directamente (problemas para el edit)
configurar onConflict (Ignore, abort...)
@Insert(onConflict = onConflictStrategy.IGNORE)
Con claves autogeneradas devolver la clave con su tipo en el dao

query para recuperar la lista de tasks, ordenada por id
SELECT * FROM tasks ORDER BY id ASC
la lista hay que convertirla a livedata para el viewmodel, se puede hacer a mano o devolviendo LiveData en el dao

el dao se usa como abstract, entonces la db es abstracta
la db extiende de RoomDatabase()y hay que etiquetarla
tiene un companion object para tener una instancia singleton

Añadir repositorio que use la db y sea usada por el TVM

Se le pasa al repo el contexto application : android.app.Aplication
El repo tiene un miembro que sera referencia al dao (getInstance(application).dao())

Hay un dispacher de IO dispachers.IO

suspend fun algo(algo){
    withContext(dispacher){
        funciones del dao
    }
}

with context es para cambiar el contexto de ejecucion para la corutina


como funciona el generado de clave si se pasa un id?

si solo se hace una funcion suspend no hay que añadirle el suspend
las operaciones de lectura no hace falta suspend



el nextid fuera

VM tiene un repositoryo y lo usa para obtener la lista (live data) eliinando el privado mutable