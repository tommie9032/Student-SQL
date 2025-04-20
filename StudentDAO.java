// StudentDAO.java

import java.sql.*;
import java.util.*;
import customexceptions.*;

public class StudentDAO {

    // Method to add a student to the database
    public void addToDatabase(Student student) {
        try (Connection conn = DBConnection.getConnection()) {
            // SQL query to insert a new student record into the "students" table
            String query = "INSERT INTO students (name, prn, age, cgpa) VALUES (?, ?, ?, ?)";
            
            // Prepare the statement and set the parameters based on the student's details
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, student.getName());  // Set name
            stmt.setLong(2, student.getPrn());     // Set PRN
            stmt.setInt(3, student.getAge());      // Set age
            stmt.setDouble(4, student.getCgpa());  // Set CGPA
            
            // Execute the update (insert the new student record)
            stmt.executeUpdate();
        } catch (SQLException e) {
            // Catch and log any SQL exceptions that occur
            System.out.println("Database Error while adding student: " + e.getMessage());
        }
    }

    // Method to delete a student from the database using their PRN
    public void deleteFromDatabase(long prn) {
        try (Connection conn = DBConnection.getConnection()) {
            // SQL query to delete a student record where the PRN matches
            String query = "DELETE FROM students WHERE prn = ?";
            
            // Prepare the statement and set the PRN to match
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setLong(1, prn);
            
            // Execute the update (delete the student record)
            stmt.executeUpdate();
        } catch (SQLException e) {
            // Catch and log any SQL exceptions that occur
            System.out.println("Database Error while deleting student: " + e.getMessage());
        }
    }

    // Method to update an existing student's details in the database
    public void updateInDatabase(Student student) {
        try (Connection conn = DBConnection.getConnection()) {
            // SQL query to update a student's details where the PRN matches
            String query = "UPDATE students SET name = ?, age = ?, cgpa = ? WHERE prn = ?";
            
            // Prepare the statement and set the student's updated details
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, student.getName());  // Set new name
            stmt.setInt(2, student.getAge());      // Set new age
            stmt.setDouble(3, student.getCgpa());  // Set new CGPA
            stmt.setLong(4, student.getPrn());     // Set the PRN to identify the student
            
            // Execute the update (update the student's record)
            stmt.executeUpdate();
        } catch (SQLException e) {
            // Catch and log any SQL exceptions that occur
            System.out.println("Database Error while updating student: " + e.getMessage());
        }
    }

    // Method to load all students from the database and add them to the provided list
    public void loadAllFromDatabase(ArrayList<Student> list) {
        try (Connection conn = DBConnection.getConnection()) {
            // SQL query to select all student records from the "students" table
            String query = "SELECT * FROM students";
            
            // Create a statement to execute the query
            Statement stmt = conn.createStatement();
            
            // Execute the query and get the result set
            ResultSet rs = stmt.executeQuery(query);
            
            // Loop through each record in the result set and create a Student object
            while (rs.next()) {
                // Create a new Student object from the result set data
                Student s = new Student(
                        rs.getString("name"),    // Get name from the result set
                        rs.getLong("prn"),       // Get PRN from the result set
                        rs.getInt("age"),        // Get age from the result set
                        rs.getDouble("cgpa")     // Get CGPA from the result set
                );
                
                // Add the student to the provided list
                list.add(s);
            }
        } catch (SQLException e) {
            // Catch and log any SQL exceptions that occur
            System.out.println("Database Error while loading students: " + e.getMessage());
        } catch (Exception e) {
            // Catch any other exceptions (such as validation errors) and log them
            System.out.println("Validation error loading students: " + e.getMessage());
        }
    }
}
