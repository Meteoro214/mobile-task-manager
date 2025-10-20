[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/wTylcrtj)
# 📱 Mobile Task Manager – Assignment 6

This repository contains the implementation for **Assignment 6** of the Task Manager App project.

## 🎯 Assignment Goal

Restructure the application to use a single activity - multiple fragment architecture.

## ✅ Implemented Features

- Refactored MainActivity.kt to use fragments & navigation via a menu.
- Implemented TaskListFragment.kt, TaskDetailFragment.kt and TaskFormFragment.kt to handle Fragment logic, all of them use DataBinding.
- Moved over ReciclerView & data creation logic to TaskListFragment from MainActivity.
- Made Task class Parcelable.
- Added a new method to TaskCollection to allow TaskAdapter to retrieve Tasks by their index over their ID.
- Modified DataGenerator to not create more data every time TaskListFragment is created.
- Modified activity_main.xml to create a Toolbar & use a NavHostFragment.
- Created fragment_task_{detail/form/list}.xml to as layouts of the 3 implemented fragments.
- Created app_bar_menu.xml & nav_graph.xml to handle fragment navigation via a menu.
- TaskAdapter now sets a listener on all the TaskHolders to handle navigation to Task Details.


## 🚧 Known Issues

- No known issues.

## 📝 Notes

- TaskController remains implemented, but it is deprecated and will probably be removed soon.
- Aplication Theme remains untouched, note that it has no ActionBar by default.
- TaskForm exists, but it is not functional.
- Task info is generated via DataGenerator, Tasks still cannot be created or modified in any form.

---

> This assignment is part of the Mobile Development course at Escuela Superior de Enxeñaria Informatica - Universidade de Vigo.  
> See the course syllabus and lab instructions for more details.



cambiar el titulo del menu
dejar de hardcodear las tasks 
TaskForm funcional
mirar si se le puede añadir al enum strings de recursos

Repositorio de tasks
Se puede usar taskInputLayout o EditText
lista desplegable para categoria (Spinner, AutoCompleteTextView)

accion de menu edit en taskDetail
Crear un repositorio de tasks que almacene las tareas = ya no hace falta mandar la task entera, solo el id
Si se edita y se guarda, crear un nuevo fragmento y luego el back button popea todo el stack para volver a la lista?
hay que devolver la info de form a detail (no es safe args)
al detail pasarle id de la lista, handle del form, al form pasarle el id o los datos

AutoCompleteTextview
añadirlo al layout
meterle hint, inputtype none para que no escriba
En el fragment.kt donde se define:
es similar al RV
Se recupera el array de strings de la caegoria
Resources.getStringArray(R.array.list) si se usan como recursos

Crear adapter (Se usa un adapter ya existente)
ArrayAdapter(contexto,Layout de los items)
**android**.R.layout.simple_dropdown_item_line = layout por defecto del SDK
en fragment para adquirir contexto requireContext()
.setAdapter

configurar threshold

hay que detectar 2 eventos diferentes = evento de que el usuario pulsa el campo y cuando selecciona un item de la lista
hay listeners especificos
Cambio de foco (onFocusChangeListener)
Click del objeto (onItemClickListener)

onFocusChange(lambda)
view,hasFocus -> if(hasFocus) view As AutoCompleteTextView.showDropDown (usar la view mejor)
view referencia a la vista donde cambia el foco
por defecto al perder el foco ya oculta 

onItemClick parece que ya funciona por defecto pero se puede usar para modificar el valor
se puede usar _ para argumentos de lambda para argumentos anonimos

Modificar onResume en TaskDetail para modificar los datos


Ejemplo:
antes de transicion de fragmentos, almacenar los datos mediante el stack   navController.previousBackStackEntry referencia al anterior del stack (por seguridad comprobar not null)
SafeArgs son para navegacion hacia adelante, para navegar hacia atras SavedStateHandle, cajon de info estilo mapa

previous.savedStateHandle.set(Clave,Valor) y forzar un pop de la pila (navController.popBackStack)
(comprobar que hace el navigateUp)

en el fragmento previo, en el onResume procesar los datos

En android solo hay una aplicacion en el foreground/interactiva

onPause ocurre cuando van al background (por ejemplo almacenar la info escrita) para leer en onResume

Las onCreate algo se lanzan 1 vez solo, es cuando se crean todas las estructuras necesarioas


onCreate  (onRestart) onStart onResume
onDestroy             onStop  onPause

generalmente:

variables e interfaz son en onCreateView
cosas como conexiones que queremos cerrar al ir al background se paran en el onPause y se levantan en el onResume

onPause guarda el estado de la app, onResume lo recupera

cambio de configuracion (rotar dispositivo) destruye el layout, entonces hay que recrear la interfaz

onResume{
super.onResume
controller.currentbackStackEnd.savedStateBuldle
val = handle.get(clave) if not null existe
para asegrar que se usa solo 1 vez, .remove
usar strings de recurso
}


@{} (actual) es 1-way, read only
@={} es 2-way data binding
