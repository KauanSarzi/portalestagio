# Internship Portal — Full-Stack Internship Management System
![Java](https://img.shields.io/badge/Java-21-red?style=flat&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.5-brightgreen?style=flat&logo=springboot)
![Maven](https://img.shields.io/badge/Maven-Build-orange?style=flat&logo=apachemaven)
![Status](https://img.shields.io/badge/Status-Completed-brightgreen?style=flat)

## 📌 Overview

Internship Portal is a full-stack web application built with Spring Boot that connects university students and companies offering internship opportunities.

The system implements complete CRUD operations and a RESTful API to manage companies, students, internship positions, areas of interest, and applications.

This project demonstrates backend architecture design, relational modeling, and REST API development using modern Java and Spring Boot.

---

## 🧠 Problem Context

Companies need an organized system to publish internship opportunities and manage applications. At the same time, students need a structured platform to explore openings aligned with their interests and academic background.

This project simulates a real-world internship marketplace, focusing on:

- Entity relationship modeling
- REST API design
- Backend validation and data integrity
- Full-stack integration

---

## 🏗️ System Architecture

The application follows a well-defined layered architecture:

- **Controller Layer** → REST endpoints and request handling  
- **Model Layer** → JPA entities and relational mapping  
- **Repository Layer** → Data access using Spring Data JPA  
- **Database Layer** → H2 in-memory database for development  
- **Frontend Layer** → HTML, CSS, and Vanilla JavaScript consuming the REST API  

This separation of concerns improves maintainability, scalability, and clarity of responsibilities.

---

## 🗄️ Data Model

Main entities:

- Company
- Student
- InternshipPosition
- Area
- Application

### Core Relationships

- One Company → Many Internship Positions (1:N)
- Internship Position ↔ Area (N:M)
- Student ↔ Area (N:M)
- One Student → Many Applications (1:N)
- One Internship Position → Many Applications (1:N)

The model ensures referential integrity and consistent data relationships.

---

## 🛠️ Technologies Used

### Backend
- Java 21
- Spring Boot 3.4.5
- Spring Data JPA
- Hibernate (ORM)
- H2 Database (in-memory)
- Maven

### Frontend
- HTML5
- CSS3
- Vanilla JavaScript (REST API consumption)

---

## ⚙️ Features

✔ Full CRUD for Companies, Students, Areas, Internship Positions, and Applications  
✔ RESTful API design  
✔ Filtering internship positions by company and area  
✔ Pagination support  
✔ Business rule validations (unique email, unique enrollment, required fields)  
✔ Referential integrity protection  
✔ Layered architecture implementation  

---

## 📡 REST API Endpoints

### Companies
- GET /api/empresas
- GET /api/empresas/{id}
- POST /api/empresas
- PUT /api/empresas/{id}
- DELETE /api/empresas/{id}

### Students
- GET /api/estudantes
- GET /api/estudantes/{id}
- POST /api/estudantes
- PUT /api/estudantes/{id}
- DELETE /api/estudantes/{id}

### Internship Positions
- GET /api/vagas
- GET /api/vagas/{id}
- POST /api/vagas
- PUT /api/vagas/{id}
- PATCH /api/vagas/{id}/encerrar
- DELETE /api/vagas/{id}

Supports filtering and pagination.

### Applications
- GET /api/inscricoes
- POST /api/inscricoes
- PATCH /api/inscricoes/{id}
- DELETE /api/inscricoes/{id}

---

## 🚀 How to Run

### Requirements

- Java 21+
- Maven 3.9+

### Steps

Clone the repository:

```bash
git clone https://github.com/KauanSarzi/portalestagio
cd portalestagio
