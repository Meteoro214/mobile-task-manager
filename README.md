[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/wTylcrtj)
# 📱 Mobile Task Manager – Assignment 10

This repository contains the implementation for **Assignment 10** of the Task Manager App project.

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







El adapter ahora extiende de ListAdapter, hay que pasarle las clases que va a manejar (la fuente de datos? y la clase viewholder)
No se pasa la fuente de datos de primeras, luego se hace submit
Se le pasa tambien el DiffUtil

getItem(position) al no pasar la lista directamente para recuperar el dato para el onBind
el getItemCount ya no hace falta overridearlo
definir el diff util como companion objetc

companion object{
    private val PersonDiff = object : DiffUtil.ItemCallBack<Person>{
        overrides
    }
    are items the same comprueba que 2 objetos sean el mismo (mismo id por ejemplo, o string == string)
    are contents the same comprueba que 2 objetos tengan los mismos datos (string.equals(string))

    Implementar equals en Task, y comparacion del id para el items

}


llamar a submit list tras crear el adapter (tiene que pasarse una lista no mutable)

binding adapter para la fecha
@bindingAdapter(argumento)
ar4gumento es un string que indica como se va a invocar en el xml
adapters para todas las cosas raras del xml

fun function(view, dato)

task.argumento llama a la funcion a la hora de settear los datos

Crear en el modelo un sealed class ListItem

sealed fuerza a que no se puedan crear mas hijos que los ya existentes
dentro tiene un data class Header para representar la categoria (su unico atribvuto es la categoria) y otra data class TaskItem o similares que tenga dentro el objeto task
crear un nuevo xml para el header (renombrar el layout para el taskitem) 

Hay que modificar la lista antes de pasarla al submit list
funcion para ordenar? si esta vacio ya nada
al submit se le pasa una vez creada con headers y ordenada

list Adapter ahora maneja ListItem y viewholder generico, el diff adaptarlo
en el companion del item diff crear un enum de los tipos (o valores a secas)
getItemViewType para devolver el tipo segun el int que se reciba

when (getitem(pos)) is tipoheader return TYPE_HEADER is tipoRoe return TYPE_ROW
TYPE_HEADER es un int para saber el tipo, puede ser la pos en un enum



Para permanencia del orden, que el task view al ser destruido actualice el orden en la db, al insertar casi seguro se rompe
el onbindviewHolder al trabajar con ListItem no hace falta tocarlo mucho tal vez, usando .bind en los holders
el diff hay que modificarlo para que sea distinto segun el tipo

en item si son mismo tipo comparar o el id o la categoria
en content dejar a secas sin comparar tipo porque un == hace equals


el header hacerlo que sea una linea pequeña con el no,mbre de la category con el resource y que tenga una linea o algo de separacion, y color  rojo urgente el resto ns


en el item task que la categoria use los resources tambien, tal vez pasarle los textos como un atributo y la category como otro



Revisar todos los xml
Revisar el xml para las category, hacer bindingadapters para fecha y para category