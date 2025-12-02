[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/wTylcrtj)
# 📱 Mobile Task Manager – Assignment 12

This repository contains the implementation for **Assignment 12** of the Task Manager App project.

## 🎯 Assignment Goal


## ✅ Implemented Features

- 

## 🚧 Known Issues

- No known issues.

## 📝 Notes


### 📝 IMPORTANT
CrudCrud API only allows 24h of service OR up to 100 operations. To reset the API, you must obtain a new API key from CrudCrud and change the stored API key in API.xml file.

---

> This assignment is part of the Mobile Development course at Escuela Superior de Enxeñaria Informatica - Universidade de Vigo.  
> See the course syllabus and lab instructions for more details.




Room va a ser la fuente de datos real.


LECTURA
La primera vez, se leen los datos del server porque Room estara vacio
En proximas ejecuciones intentara leer Room y no del server, se asume que estan sincronizados

ESCRITURA

filosofia offline primero
Se aplican las escrituras a Room y a la vez se ejecuta sobre el server
Opcional  se solicita la operacion al servidor cuando haya conexion usando workers y hay estado de syncs
hacer un request por cada operacion


1 Task para el modelo   Task
1 Task para el DB       TaskEntity
1 Task para CrudCrud    TaskDTO
Conversiones en el Repo = Mappers
el crudCrudApi renombrarlo a mapper o algo
el repo que use mappers intermedios?
el repo tiene que hacer los mappeos entre tipos de task
valen clases mapper o tenerlo en el repo


Task las 6 clasicas
dto +_id
Entity +_id y posible lo de sync y a mayores el orden

Mappear dto a entity para sync inicial de 1ra vez al recuperar


if Room empty recupera de server
si no empty sync


guardar la id original en crudcrud? yo lo haria



el network lo llevaria todo el work manager

puede que actualice demasiado el livedata al cambiar el estado del sync

el request si falla con server no hace nada, no avisa, en todo caso loggea

mirar themes night para el color de texto

al worker pasarle la id de la task/la propia task y el dao para que pueda acceder y eliminar o modificar el estado?

al dao añadirle metodos de modificacion directa del estado
la task se pasaria al worker como inputData

casi seguro hay que pasar la task como sus valores
necesita el _id, trabajar con el dto
el dao retrievearlo dentro
pasarle la task ya hecha/datos y no hacer un get por optimizar, el dao seria para cambiar estado o deletear


comprobar lo del default color


hacer lo de ordenar con swipe (guardar un orden en el TaskEntity)

preferencias app sharepreferences es un mapa clave valor meterle una variable bool
logica de esto en el repo


todo encima del viewmodel estaria hecho
el viewmodel cambiar el init y lo de move si lo hago



primero se guarda en local con _id null, cuando se haga el sync_created se actualiza el _id


si se actualiza una que este sync_created, no se le pone en uodated
si se deletea, simplemente se deletea sin entrar a sync


en inicio se fuerza sincroniza

en inicio se sincroniza, o se asume sincronizado?


En taskListItems //para orden seria ponerle aqui que ordenase por categoria y luego por orden (pero el orden seria en TaskEntity principalmente)
Al pasar de entity a task la lista normal, que del dao venga ordenada la lista y se muestre ordenada
orden = id inicial y luego se modifica
repo va a tomar el livedata de entitys y transformarlo en task a secas, que el de entitys venga ordenado de la db y ya
taskdto tendra el orden tambien para guardarlo
habria que hacer en el vm metodo de orden, que llame a metodo en el dao, que haga que el worker lo haga como un update




model esta revisado, falta hacer lo del orden en TVM
Crear los workers
meter CCAPI al repo
Hacer repo
hacer lo de orden en general