# Internship Portal — Full-Stack Internship Management System
![Java](https://img.shields.io/badge/Java-21-red?style=flat&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.5-brightgreen?style=flat&logo=springboot)
![Maven](https://img.shields.io/badge/Maven-Build-orange?style=flat&logo=apachemaven)
![Status](https://img.shields.io/badge/Status-Completed-brightgreen?style=flat)

## 📌 Overview

Internship Portal is a full-stack web application built with Spring Boot that connects university students and companies offering internship opportunities.

The system provides complete CRUD operations through a RESTful API to manage companies, students, internship positions, areas of interest, and applications.

This project demonstrates backend architecture design, relational data modeling, REST API development, and full-stack integration using modern Java and Spring Boot.

## 🧠 Problem Context

Companies require a structured system to publish internship opportunities and manage candidate applications efficiently. At the same time, students need a centralized platform to explore opportunities aligned with their academic background and professional interests.

This project simulates a real-world internship marketplace, focusing on:

* Clear entity relationship modeling
* Structured REST API design
* Backend validation and business rule enforcement
* Data integrity and referential consistency
* Frontend integration consuming backend services

## 🏗️ System Architecture

The application follows a layered architecture with clear separation of responsibilities:

* **Controller Layer** → Handles HTTP requests and exposes REST endpoints
* **Model Layer** → JPA entities and relational mapping
* **Repository Layer** → Data access abstraction using Spring Data JPA
* **Database Layer** → H2 in-memory database for development
* **Frontend Layer** → HTML, CSS, and Vanilla JavaScript consuming the REST API

This separation of concerns improves maintainability, testability, and scalability while keeping business logic isolated from persistence and presentation layers.

## 🗄️ Data Model

### Main Entities

* Company
* Student
* InternshipPosition
* Area
* Application

### Core Relationships

* One Company → Many Internship Positions (1:N)
* Internship Position ↔ Area (N:M)
* Student ↔ Area (N:M)
* One Student → Many Applications (1:N)
* One Internship Position → Many Applications (1:N)

The relational model was designed to ensure referential integrity, prevent inconsistent deletions, and accurately represent real marketplace interactions.

## 🛠️ Technologies Used

### Backend

* Java 21
* Spring Boot 3.4.5
* Spring Data JPA
* Hibernate (ORM)
* H2 Database (in-memory)
* Maven

### Frontend

* HTML5
* CSS3
* Vanilla JavaScript (REST API consumption)

## ⚙️ Features

✔ Full CRUD operations for Companies, Students, Areas, Internship Positions, and Applications  
✔ RESTful API with structured endpoint design  
✔ Filtering internship positions by company and area  
✔ Pagination support for scalable data retrieval  
✔ Business rule validations (unique email, unique enrollment, required fields)  
✔ Referential integrity protection to prevent inconsistent deletions  
✔ Layered architecture implementation with clear responsibility boundaries  

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
