# Internship Portal — Full-Stack Internship Management System

![Java](https://img.shields.io/badge/Java-21-red?style=flat&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.5-brightgreen?style=flat&logo=springboot)
![Maven](https://img.shields.io/badge/Maven-Build-orange?style=flat&logo=apachemaven)
![Status](https://img.shields.io/badge/Status-Completed-brightgreen?style=flat)

A full-stack web application built with **Spring Boot** that connects **university students** and **companies** offering internship opportunities.  
Provides a complete **REST API** with CRUD operations, **relational modeling**, and a simple **frontend** consuming the backend.

---

## ✨ Highlights

- ✅ Layered architecture (**Controller / Service / Repository / Model**)
- ✅ Relational modeling with **1:N** and **N:M** relationships
- ✅ Business validations (unique email/enrollment, required fields)
- ✅ Referential integrity protection (prevents inconsistent deletions)
- ✅ Filtering + pagination for internship positions
- ✅ Swagger documentation + Postman-friendly responses

---

## 🧩 Problem Context

Companies need a structured way to publish internship opportunities and manage applications.  
Students need a centralized platform to explore positions aligned with their interests and background.

This project simulates a real internship marketplace focusing on:

- Entity relationship design
- REST API structure and good practices
- Business rules & validation
- Data integrity and consistency
- Full-stack integration (frontend consuming API)

---

## 🏗️ Architecture

The application follows a **layered architecture** with separation of concerns:

- **Controller Layer** → HTTP requests and REST endpoints  
- **Service Layer** → business rules and validations  
- **Repository Layer** → data access (Spring Data JPA)  
- **Model Layer** → JPA entities and mappings  
- **Database Layer** → H2 (in-memory) for development  
- **Frontend Layer** → HTML/CSS + Vanilla JS consuming the API  

---

## 🗄️ Data Model

### Entities
- Company
- Student
- InternshipPosition
- Area
- Application

### Relationships
- Company → InternshipPosition (**1:N**)  
- InternshipPosition ↔ Area (**N:M**)  
- Student ↔ Area (**N:M**)  
- Student → Application (**1:N**)  
- InternshipPosition → Application (**1:N**)  

Designed to ensure **referential integrity**, prevent invalid deletions, and represent marketplace interactions.

---

## 🛠️ Tech Stack

### Backend
- Java 21
- Spring Boot 3.4.5
- Spring Data JPA + Hibernate
- H2 Database
- Maven

### Frontend
- HTML5
- CSS3
- Vanilla JavaScript (REST API consumption)

---

## ⚙️ Features

- CRUD for **Companies, Students, Areas, Internship Positions, Applications**
- Structured REST endpoints
- Internship positions filtering by **company** and **area**
- Pagination for scalable retrieval
- Business validations:
  - unique email
  - unique enrollment
  - required fields
- Endpoint documentation (Swagger)

---

## 📡 REST API Endpoints

### Companies

```
GET    /api/empresas
GET    /api/empresas/{id}
POST   /api/empresas
PUT    /api/empresas/{id}
DELETE /api/empresas/{id}
```

### Students

```
GET    /api/estudantes
GET    /api/estudantes/{id}
POST   /api/estudantes
PUT    /api/estudantes/{id}
DELETE /api/estudantes/{id}
```

### Internship Positions

```
GET    /api/vagas
GET    /api/vagas/{id}
POST   /api/vagas
PUT    /api/vagas/{id}
PATCH  /api/vagas/{id}/encerrar
DELETE /api/vagas/{id}
```

Supports filtering by company and area, as well as pagination parameters.

### Areas

```
GET    /api/areas
GET    /api/areas/{id}
POST   /api/areas
PUT    /api/areas/{id}
DELETE /api/areas/{id}
```

### Applications

```
GET    /api/inscricoes
POST   /api/inscricoes
PATCH  /api/inscricoes/{id}
DELETE /api/inscricoes/{id}
```

## 🚀 How to Run

### Requirements

* Java 21+
* Maven 3.9+

### Steps

Clone the repository:

```bash
git clone https://github.com/KauanSarzi/portalestagio
cd portalestagio
```

Build and run:

```bash
mvn clean install
mvn spring-boot:run
```

Access the application at:

```
http://localhost:8080
```
## 👤 Author

**Kauan Sarzi da Rocha**  
- [![LinkedIn](https://img.shields.io/badge/LinkedIn-Kauan%20Sarzi-0077B5?style=flat&logo=linkedin)](https://linkedin.com/in/kauan-sarzi)
- [![Email](https://img.shields.io/badge/Email-kauansarzi24@gmail.com-D14836?style=flat&logo=gmail)](mailto:kauansarzi24@gmail.com)
