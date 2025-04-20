# Student Data Entry System with MySQL (JDBC)

## Overview

The **Student Data Entry System** is a Java-based application that allows users to perform basic CRUD (Create, Read, Update, Delete) operations on student records through a **menu-driven interface**. The system stores student data in a **MySQL database** using **JDBC (Java Database Connectivity)**. The project adheres to **Object-Oriented Programming (OOP)** principles and incorporates **custom exception handling** to ensure robust error management.

---

## Project Structure

### 1. `Student.java`
This class defines the **Student model**, representing a student entity with necessary field validations and encapsulation.

### 2. `StudentDAO.java`
This class contains methods for interacting with the MySQL database. It handles all **CRUD operations** (Create, Read, Update, Delete) for student records.

### 3. `DBConnection.java`
This utility class provides a method to establish a **JDBC connection** to a MySQL database.

### 4. `Main.java`
This class contains the `main()` method, which implements a **menu-driven console interface** to interact with the system.

### 5. `customexceptions/`
This package includes **custom exception classes** used for validation and error handling throughout the application.

---

## Features

The system supports the following operations:

| Option | Feature Description |
|--------|---------------------|
| **1**  | Add a new student (with input validation). |
| **2**  | Display all students stored in the database. |
| **3**  | Search for a student by PRN, Name, or Position. |
| **4**  | Delete a student by PRN. |
| **5**  | Edit student details (Name, Age, CGPA) by PRN. |
| **6**  | Exit the program. |

---

## Method Overview

### `Student.java` (Student Model)

| Method      | Description                                |
|-------------|--------------------------------------------|
| Constructor | Initializes the Student object with field validation. |
| `setName()` / `getName()` | Set and retrieve the student's name. |
| `setPrn()` / `getPrn()` | Set and retrieve the student's PRN (Personal Registration Number). |
| `setAge()` / `getAge()` | Set and retrieve the student's age. |
| `setCgpa()` / `getCgpa()` | Set and retrieve the student's CGPA (Cumulative Grade Point Average). |
| `display()` | Prints the student's details to the console. |

---

### `StudentDAO.java` (Database Operations)

| Method            | Description                                   |
|-------------------|-----------------------------------------------|
| `addStudent(Student student)` | Inserts a new student record into the database. |
| `displayStudents()` | Retrieves and displays all student records from the database. |
| `searchStudent()` | Searches for a student by PRN, Name, or Position. |
| `deleteStudent()` | Deletes a student record from the database based on PRN. |
| `editStudent()` | Updates a student's details in the database. |

---

### `DBConnection.java` (Database Connection)

| Method             | Description                                |
|--------------------|--------------------------------------------|
| `getConnection()`  | Returns a JDBC connection object to connect to the MySQL database. |

---

## Custom Exceptions

The following custom exceptions are used to handle specific validation and logic errors in the application:

- `InvalidNameException`, `InvalidPRNException`, `InvalidAgeException`, `InvalidCGPAException`
- `EmptyStudentListException`, `StudentNotFoundException`, `DuplicatePRNException`
- `TooManyStudentsException`, `InvalidMenuChoiceException`, `InvalidSearchChoiceException`
- `EditNotAllowedException`, `InvalidPositionException`, `NegativeValueException`, `EmptyNameException`, `NullStudentException`, `DeletionNotAllowedException`

These exceptions improve the system’s reliability by ensuring that invalid input or unexpected conditions are appropriately managed.

---
