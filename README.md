
# Todo API

This is a RESTful API for managing TODO items. It provides endpoints to create, read, update, and delete TODOs. Additionally, you can filter TODOs by category and mark them as done.

## Endpoints

### POST /api/v1/todos
#### Request
```json
{
  "title": "Finish homework",
  "category": "Work",
  "description": "Complete math exercises",
  "done": false
}
```
#### Response
```json
{
  "id": 1,
  "title": "Finish homework",
  "category": "Work",
  "description": "Complete math exercises",
  "done": false
}
```

### GET /api/v1/todos
#### Request
```http
GET /api/v1/todos
```
#### Response
```json
[
  {
    "id": 1,
    "title": "Finish homework",
    "category": "Work",
    "description": "Complete math exercises",
    "done": false
  },
  {
    "id": 2,
    "title": "Buy groceries",
    "category": "Home",
    "description": "Milk, bread, eggs",
    "done": true
  }
]
```

### GET /api/v1/todos?category=[category]
#### Request
```http
GET /api/v1/todos?category=Work
```
#### Response
```json
[
  {
    "id": 1,
    "title": "Finish homework",
    "category": "Work",
    "description": "Complete math exercises",
    "done": false
  }
]
```

### GET /api/v1/todos/{id}
#### Request
```http
GET /api/v1/todos/1
```
#### Response
```json
{
  "id": 1,
  "title": "Finish homework",
  "category": "Work",
  "description": "Complete math exercises",
  "done": false
}
```

### POST /api/v1/todos/done
#### Request
```json
[1, 2]
```
#### Response
```json
[
  {
    "id": 1,
    "title": "Finish homework",
    "category": "Work",
    "description": "Complete math exercises",
    "done": true
  },
  {
    "id": 2,
    "title": "Buy groceries",
    "category": "Home",
    "description": "Milk, bread, eggs",
    "done": true
  }
]
```
### Code Guidelines
- Commits & PRs must mention the related issue number.
- Commit messages should complete the sentence: "Applying this commit, it will [commit message]."


### TODO
- [X] Create MVC structure
- [X] Create mapper for DTOs
- [X] Create JPA repositories for data persistence
- [X] Expose REST endpoints via `TodoController`
- [X] Implement `markTodosAsDone` functionality
- [X] Implement `getTodosByCategory` functionality
- [X] Implement `createTodos` functionality
- [ ] Add contract tests
- [ ] Add security (e.g., authentication and authorization)
- [ ] Add logging
- [ ] Add OpenAPI documentation
- [ ] Add CI/CD actions (e.g., GitHub Actions)

---

## How to Run the Application

1. Clone this repository.
2. Build the project using Gradle:
   ```bash
   ./gradlew build
   ```
3. Run the application:
   ```bash
   ./gradlew bootRun
   ```

The API will be available at `http://localhost:8080`.

---

## Testing the Application

To run the unit and integration tests, use the following command:

```bash
./gradlew test
```

This will run all the tests and ensure the functionality of the API.

---

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
