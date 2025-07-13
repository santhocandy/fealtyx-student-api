# FealtyX Student API 🚀

A simple REST API built with Spring Boot to manage student data with basic CRUD operations, integrated with Ollama (Llama3 model) to generate AI-based student summaries.

---

## ✅ Demo

👉 **Live Demo Video**: [Watch Demo](https://drive.google.com/file/d/1vjD3Nm4t1wPnjDLi_Kx32I1mc0HG1pmv/view?usp=sharing)  
👉 **GitHub Repo**: [https://github.com/santhocandy/fealtyx-student-api](https://github.com/santhocandy/fealtyx-student-api)

---

## Tech Stack

- Java 17
- Spring Boot 3.x
- RESTful API
- Ollama (Llama3 model)
- In-memory data storage (no database)

---

## Features

- Create, Read, Update, Delete (CRUD) for students
- AI-generated summary of student profile using Ollama
- In-memory thread-safe storage
- Input validation and error handling
- Works with curl/Postman
- Lightweight and easy to run

---

## API Endpoints

| Method | Endpoint                 | Description                         |
|--------|--------------------------|-------------------------------------|
| POST   | /students                | Create a new student                |
| GET    | /students                | Get all students                    |
| GET    | /students/{id}          | Get a student by ID                 |
| PUT    | /students/{id}          | Update a student by ID              |
| DELETE | /students/{id}          | Delete a student by ID              |
| GET    | /students/{id}/summary  | Generate AI summary using Ollama    |

---

## How to Run Locally

1. Make sure [Ollama](https://ollama.com) is installed and running:

```bash
   ollama run llama3
```

2. Start the Spring Boot application:
   ```bash
     ./mvnw spring-boot:run
   ```

