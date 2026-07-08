# Todo List Project

In this project, I am creating a simple Todo-List web application that makes use of the following technologies:
- RESTful API with fundamental CRUD operations
- **Frameworks**: Spring Boot
- **Database**: 
  - Development: **In-memory H2 Database**
  - Production: **PostgreSQL**
- Basic web UI **(HTML + CSS + JS)** that communicates with the backend API through **Cross-Origin Resource Sharing 
  (CORS)**
- Containerization of each layer (frontend, backend, database) via Docker Compose
- Deploy using **Amazon Web Services (AWS**

## Prerequisites & Set-up
- Java version: Java 21
- Build system: Maven

## What the webapp is currently covering:
- Add new todo item through `POST` method
- Delete existing todo item through `DELETE` method (with ask-again notification for users)
- Modify the title and description of the existing todo item through
- Fetch and reload new representation of todo-list after each operation, namely POST, DELETE, PATCH

---

## How to run this project locally on your machine
- Download the source code as a zip folder. Unzip it
- Open the project with the IDE of your choice
- Make sure you have [installed **Docker**](https://docs.docker.com/engine/install/ubuntu/), set the correct Java version as the one specified in `pom.xml`
- Then run the root directory of the project
```bash
docker compose up --build
```
- Then wait until the building of containers finishes. Once finished, you can check if the containers are running by:
```bash
docker compose ps
```
and see there are 3 containers for 3 layers of the web app is running

- Besides, you can play around with the website as localhost via: 
  - either http://127.0.0.1:5501 
  - or http://localhost:5501

- When you want to stop and remove containers, networks and images created by `docker compose up`, simply run:
```bash
docker compose down 
```



