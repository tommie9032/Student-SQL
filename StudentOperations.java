// StudentOperations.java
// Handles Student operations with validation, custom exceptions, and DB synchronization.

import java.util.*;
import customexceptions.*;

class StudentOperations {
    private ArrayList<Student> students = new ArrayList<>();  // List to hold student objects
    private StudentDAO dao = new StudentDAO();  // Data Access Object to interact with the database

    // Constructor that loads existing students from the database.
    public StudentOperations() {
        dao.loadAllFromDatabase(students); // Load all students from DB into the students list
    }

    // Method to add a student to the list and database
    public void addStudent(Student student) throws DuplicatePRNException, TooManyStudentsException, NullStudentException {
        // Check if the student object is null
        if (student == null) throw new NullStudentException("Cannot add a null student");

        // Check if the list already contains 100 students
        if (students.size() >= 100) throw new TooManyStudentsException("Cannot add more than 100 students");

        // Check if a student with the same PRN already exists
        for (Student s : students) {
            if (s.getPrn() == student.getPrn()) {
                throw new DuplicatePRNException("Student with this PRN already exists");
            }
        }

        // Add student to the list and sync with the database
        students.add(student);
        dao.addToDatabase(student); // Save the new student to the database
        System.out.println("Student Added Successfully.");
    }

    // Method to display the list of students
    public void displayStudents() throws EmptyStudentListException {
        // Check if there are no students to display
        if (students.isEmpty()) throw new EmptyStudentListException("No students to display.");
        
        // Loop through and display each student's details
        for (Student s : students) {
            System.out.println("-----------------------------------------------------");
            s.display();  // Display the details of the student
            System.out.println("-----------------------------------------------------\n");
        }
    }

    // Method to search for a student based on PRN, name, or position
    public void searchStudent() throws EmptyStudentListException, InvalidSearchChoiceException, StudentNotFoundException, InvalidPositionException  {
        // Check if the student list is empty
        if (students.isEmpty()) throw new EmptyStudentListException("No students available to search.");

        Scanner scan = new Scanner(System.in);
        System.out.println("Search by:\n1. PRN\n2. Name\n3. Position");
        int choice = Integer.parseInt(scan.nextLine());  // Read user choice for search

        // Search by PRN
        switch (choice) {
            case 1:
                System.out.println("Enter PRN to search:");
                long prn = Long.parseLong(scan.nextLine());
                for (Student s : students) {
                    if (s.getPrn() == prn) {
                        s.display();  // Display student if found
                        return;
                    }
                }
                throw new StudentNotFoundException("Student with PRN not found.");

            // Search by Name
            case 2:
                System.out.println("Enter Name to search:");
                String name = scan.nextLine();
                for (Student s : students) {
                    if (s.getName().equalsIgnoreCase(name)) {
                        s.display();  // Display student if found
                        return;
                    }
                }
                throw new StudentNotFoundException("Student with name not found.");

            // Search by Position (Index)
            case 3:
                System.out.println("Enter Position (Index) to search:");
                int pos = Integer.parseInt(scan.nextLine());
                if (pos < 0 || pos >= students.size()) 
                    throw new InvalidPositionException("Invalid index position");
                students.get(pos).display();  // Display student at the given position
                break;

            // Handle invalid choice
            default:
                throw new InvalidSearchChoiceException("Invalid search choice.");
        }
    }

    // Method to delete a student from the list and database
    public void deleteStudent() throws EmptyStudentListException, DeletionNotAllowedException, StudentNotFoundException {
        // Check if there are students to delete
        if (students.isEmpty()) throw new EmptyStudentListException("No students available to delete.");

        Scanner scan = new Scanner(System.in);
        System.out.println("Enter PRN to delete:");
        long prn = Long.parseLong(scan.nextLine());  // Read PRN from user

        // Loop through the students and find the matching PRN to delete
        Iterator<Student> iterator = students.iterator();
        while (iterator.hasNext()) {
            Student student = iterator.next();
            if (student.getPrn() == prn) {
                iterator.remove();  // Remove student from the list
                dao.deleteFromDatabase(prn);  // Remove student from the database
                System.out.println("Student deleted successfully.");
                return;
            }
        }
        // If no student with the given PRN is found, throw an exception
        throw new StudentNotFoundException("Student not found with the given PRN.");
    }

    // Method to edit student details
    public void editStudent() throws EmptyStudentListException, EditNotAllowedException, StudentNotFoundException {
        // Check if there are students to edit
        if (students.isEmpty()) throw new EmptyStudentListException("No students available to edit.");

        Scanner scan = new Scanner(System.in);
        System.out.println("Enter PRN of student to edit:");
        long prn = Long.parseLong(scan.nextLine());  // Read PRN to identify student to edit

        // Loop through the students and find the matching PRN to edit
        for (Student student : students) {
            if (student.getPrn() == prn) {
                try {
                    // Read new details for the student
                    System.out.println("Enter New Name:");
                    student.setName(scan.nextLine());
                    System.out.println("Enter New Age:");
                    student.setAge(Integer.parseInt(scan.nextLine()));
                    System.out.println("Enter New CGPA:");
                    student.setCgpa(Double.parseDouble(scan.nextLine()));

                    dao.updateInDatabase(student);  // Sync changes with the database
                    System.out.println("Student details updated successfully!");
                } catch (Exception e) {
                    // Catch any exception and throw an EditNotAllowedException if input is invalid
                    throw new EditNotAllowedException("Invalid details provided during edit.");
                }
                return;
            }
        }
        // If no student with the given PRN is found, throw an exception
        throw new StudentNotFoundException("Student not found with the given PRN.");
    }
}
