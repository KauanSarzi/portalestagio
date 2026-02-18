# Internship Portal — Full-stack Job Management System

![Java](https://img.shields.io/badge/Java-21-red?style=flat&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.5-brightgreen?style=flat&logo=springboot)
![SQL](https://img.shields.io/badge/SQL-Database-blue?style=flat)
![Maven](https://img.shields.io/badge/Maven-Build-orange?style=flat)

A full-stack web application that connects students and companies through internship opportunities.  
Built using Spring Boot, REST APIs, and relational database modeling.

---

## Features

- Student registration and management
- Company registration and job posting
- Internship application tracking
- RESTful backend architecture
- Relational data persistence using JPA

---

## Tech Stack

- Java 21
- Spring Boot
- Spring Data JPA (Hibernate)
- SQL Database (H2 or compatible)
- Maven
- Git & GitHub

---

## Architecture

Layered architecture:

Controller → Service → Repository → Database


Project structure:

src/
├── controller/
├── service/
├── repository/
├── model/
└── resources/


---

## How to Run

Clone the repository:

```bash
git clone https://github.com/KauanSarzi/portalestagio.git
cd portalestagio/webservicerest

Run the application:
./mvnw spring-boot:run

Access:
http://localhost:8080
