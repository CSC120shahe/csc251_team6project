# csc251_team6project
This file is for csc251 group project.

## Team Members
- Sha He
- Bethany Hill
- Arthur Holmes
- Cody Jackson

## Project Setup
1. Clone the repository:
   ```bash
   git clone https://github.com/CSC120shahe/csc251_team6project.git
   cd csc251_team6project.git
   ```
2. Install and verify JDK
3. Install VS Code 和 Java extension pack

## Project name: Student Management System (Java CLI)

This is a simple **Student Management System** written in Java. It is a command-line application that allows users to manage student records including ID, name, and grade.This is a **pure in-memory + command-line** project. It does **not use a database**.

### ✅ Features

- Add a student
- Delete a student by ID
- Search for a student by name
- Update a student's grade
- Display all student records
- Automatically load and save student data to a file

### ✅ Project Type

This is a **pure in-memory + command-line** project. It does **not use a database**.

- Student records are stored in memory using `ArrayList<Student>`
- Data is **saved to a `.csv` file** named `students.csv`
- When the program starts, it **reads existing records from the file**
- When the program exits, it **automatically writes the data back to the file**

### ✅ Technologies Used

- Java 17 or above
- File I/O (`BufferedReader`, `BufferedWriter`)
- Java Collections (`ArrayList`)
- Command-line interface (CLI)

### ✅ File Structure

```
Student/ 
Student.java // Defines the Student class
StudentManager.java // Manages the list of students 
Main.java // CLI program entry point
students.csv // Auto-generated data file
README.md // Project overview
```

## ▶️ How to Run

1. **Compile the code:**

   ```
   javac *.java
   ```

2. **Run the program:**
   ```
   java Main
   ```
3. **Follow the menu options to manage students.**

4. **Example CSV Output**

   After exiting the program, the student data will be saved in this format:
   ```
   101,Alice,98.5

   102,Bob,85.0
   ```
## 💡 Possible Improvements
Support sorting by grade or name

Add fuzzy search (partial match)


## data structure
This project demonstrates a deep understanding of ArrayList, one of the most fundamental data structures in Java. It is used to store and manipulate a dynamic list of Student objects, supporting operations like adding, removing, searching, updating, and iterating over elements.

- Use ArrayList to store a dynamic number of student objects
- Use add() to insert students into the list
- Use removeIf() to delete students from the list
- Iterate over the ArrayList to search for students
- Update the content of a specific student object
- Store and reconstruct ArrayList data using file I/O

