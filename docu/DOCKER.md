## Create a Docker container
```bash
docker run --name todo-postgres -e POSTGRES_DB=todo_db -e POSTGRES_USER=todo_user -e POSTGRES_PASSWORD=todo_pass -p 
5432:5432 -d postgres:16
```

### What each part means
- `docker run`: Create and start a container.
- `--name todo-postgres`: Give the container a readable name.
- `-e ...`: Pass environment variables into the container.
- `POSTGRES_DB=todo_db`: Create a database named `todo_db`.
- `POSTGRES_USER=todo_user`: Create a DB user.
- `POSTGRES_PASSWORD=todo_pass`: Set password for that user.
- `-p 5432:5432`: Map local machine port `5432` to container port `5432`.
- `-d`: Run in background (detached mode).
- `postgres:16`: Image name + tag (PostgreSQL version).

## 
```bash
docker exec -it <CONTAINER_ID> psql -U todo_user -d todo_db
```

## Connect Spring Boot to this database
Use these settings in your `application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/todo_db
spring.datasource.username=todo_user
spring.datasource.password=todo_pass
```


