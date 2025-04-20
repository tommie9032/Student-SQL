// Author: Tom Thomas
// PRN: 23070126136
// Batch: AIML B3


import java.util.*;
import customexceptions.*;

public class Main {
    public static void main(String[] args) {
        // Create a Scanner object for user input
        Scanner scanner = new Scanner(System.in);
        
        // Create an instance of StudentOperations to handle the logic for student-related operations
        StudentOperations studentOperations = new StudentOperations();

        // Infinite loop to show the menu and process user input
        while (true) {
            // Displaying the menu options
            System.out.println("-----------------------------------------------------");
            System.out.println("\nMenu:");
            System.out.println("1. Add Student");
            System.out.println("2. Display Students");
            System.out.println("3. Search Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Edit Student");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            try {
                // Read the user's menu choice
                int choice = Integer.parseInt(scanner.nextLine());
                System.out.println("-----------------------------------------------------\n");

                // Switch statement to handle different choices
                switch (choice) {
                    case 1:  // Add a new student
                        // Gather student information from the user
                        System.out.println("Enter Name: ");
                        String name = scanner.nextLine();

                        System.out.println("Enter PRN: ");
                        long prn = Long.parseLong(scanner.nextLine());

                        System.out.println("Enter Age: ");
                        int age = Integer.parseInt(scanner.nextLine());

                        System.out.println("Enter CGPA: ");
                        double cgpa = Double.parseDouble(scanner.nextLine());

                        // Create a new student object and pass it to the `addStudent` method
                        Student student = new Student(name, prn, age, cgpa);
                        studentOperations.addStudent(student);
                        break;

                    case 2:  // Display all students
                        studentOperations.displayStudents();
                        break;

                    case 3:  // Search for a student by PRN, Name, or Position
                        try {
                            studentOperations.searchStudent();
                        } catch (InvalidPositionException e) {
                            // Handle invalid search position
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;

                    case 4:  // Delete a student by PRN
                        studentOperations.deleteStudent();
                        break;

                    case 5:  // Edit an existing student's details
                        studentOperations.editStudent();
                        break;

                    case 6:  // Exit the program
                        System.out.println("Exiting program...");
                        scanner.close();  // Close the scanner to free up resources
                        return;  // Exit the infinite loop and terminate the program

                    default:
                        // Handle invalid menu choice by throwing a custom exception
                        throw new InvalidMenuChoiceException("Invalid menu choice! Please choose a valid option.");
                }
            } catch (InvalidMenuChoiceException | NumberFormatException e) {
                // Handle invalid menu choice or invalid number format errors
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                // Catch any other unexpected exceptions and display the error
                System.out.println("Unhandled Exception: " + e);
            }
        }
    }
}
