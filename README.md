# 📱 Mobile Task Manager

This repository contains the implementation of the Task Manager App project for the 2025/2026 Dispositivos Móbiles course at Grado de Ingeniería Informática - Universidade de VIgo.

## 🎯 Assignment Goal

The goal of the course is to progressively design, implement, and refine a complete Android application: a Task Manager App. The app will allow users to create, edit, organize, and track tasks, while incorporating modern mobile development practices and architectural patterns.

The course is structured to build this application incrementally. Each week introduces a new concept, technique, or system component, with a relevant assignment to evolve the app implementation with the new material.

The implementation will evolve from a simple console-based program to a fully featured mobile app with:

- A modern multi-screen UI
- Reactive state management using ViewModel and LiveData
- Local persistence with Room
- Remote data synchronization via REST API
- Material-compliant design
- A well-structured MVVM architecture using the Repository pattern
- Full CRUD capabilities
- Multi-language support


## 📝 Assignment Description

This project consists of weekly project assignments, including a release at the middle of the semester (checkpoint 01) and a final delivery (checkpoint 02).

The weekly assignments implement specific features, and are developed on their own dedicated branch.

The checkpoints are releases with all features and assignments developed up to the delivery date, saved on their own branches and given a Release. 
Checkpoint 01 includes assignments up to Assignment 6, while checkpoint 02 goes up to Assigment 13, which is the final assignment.
 
Each assignment branch contains:

- All source code and layout files up to the assignment.
- A README.md with a brief description of the weekly goal, a summary of what was implemented and any known limitations.

The last 2 assignments contain a launchable APK for testing.


## ✅ Assignment Branches

The Project has the following assignments

- Assignment-02 : Console-based task manager
- Assignment-03 : Object-oriented refactoring and domain model
- Assignment-04 : First Android implementation
- Assignment-05 : RecyclerView UI refinement
- Assignment-06 (Checkpoint-1) : Fragment-based navigation and one-way data binding

---

- Assignment-07 : Task form and two-way data binding
- Assignment-08 : Shared ViewModel and MVVM integration
- Assignment-09 : Room persistence and coroutine-based operations
- Assignment-10 : Task grouping and LiveData transformation
- Assignment-11 : Networking with Retrofit and Moshi
- Assignment-12 : Final Polish - refined MVVM architecture with Repository pattern
- Assignment-13 (Checkpoint-2) : Final Polish - Material design and UI modernization


### 🚧 Known Issues

CrudCrud API (used for networking) only allows 24h of service OR up to 100 operations. To reset the API, the API key from CrudCrud must be manually updated and changed in the API.xml file.

---

> This assignment is part of the Mobile Development course at Escuela Superior de Enxeñaria Informatica - Universidade de Vigo.  
> See the course syllabus and lab instructions for more details.