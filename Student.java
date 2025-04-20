// Student.java
// Represents a Student object with details like Name, PRN, Age, and CGPA. 
// It includes validation for these fields and throws custom exceptions for invalid data.

import customexceptions.*;
import javax.naming.InvalidNameException;

public class Student {
    private String name;   // Student's name
    private long prn;      // Student's PRN (Permanent Registration Number)
    private int age;       // Student's age
    private double cgpa;   // Student's CGPA (Cumulative Grade Point Average)

    // Constructor to initialize the student's details and validate them
    public Student(String name, long prn, int age, double cgpa) throws InvalidNameException, InvalidPRNException, InvalidAgeException, InvalidCGPAException, EmptyNameException, NegativeValueException {
        // Set each property using the respective setter methods that perform validation
        setName(name);
        setPrn(prn);
        setAge(age);
        setCgpa(cgpa);
    }

    // Setter method for name with validation
    public void setName(String name) throws InvalidNameException, EmptyNameException {
        // Check if name is null or empty (ignoring spaces)
        if (name == null || name.trim().isEmpty())
            throw new EmptyNameException("Name cannot be empty");
        
        // Check if name contains only letters and spaces
        if (!name.matches("[a-zA-Z ]+"))
            throw new InvalidNameException("Name must contain only letters and spaces");
        
        // If validation passes, set the name
        this.name = name;
    }

    // Setter method for PRN with validation
    public void setPrn(long prn) throws InvalidPRNException, NegativeValueException {
        // Check if PRN is negative or zero
        if (prn <= 0) throw new NegativeValueException("PRN cannot be negative or zero");
        
        // Check if PRN is not exactly 10 digits long
        if (String.valueOf(prn).length() != 11) 
            throw new InvalidPRNException("PRN must be 10 digits");
        
        // If validation passes, set the PRN
        this.prn = prn;
    }

    // Setter method for age with validation
    public void setAge(int age) throws InvalidAgeException, NegativeValueException {
        // Check if age is negative
        if (age < 0) throw new NegativeValueException("Age cannot be negative");
        
        // Check if age is not in the valid range (16-100)
        if (age < 16 || age > 100)
            throw new InvalidAgeException("Age must be between 16 and 100");
        
        // If validation passes, set the age
        this.age = age;
    }

    // Setter method for CGPA with validation
    public void setCgpa(double cgpa) throws InvalidCGPAException, NegativeValueException {
        // Check if CGPA is negative
        if (cgpa < 0) throw new NegativeValueException("CGPA cannot be negative");
        
        // Check if CGPA is within the valid range (0-10)
        if (cgpa < 0 || cgpa > 10)
            throw new InvalidCGPAException("CGPA must be between 0 and 10");
        
        // If validation passes, set the CGPA
        this.cgpa = cgpa;
    }

    // Getter method for name
    public String getName() { return name; }

    // Getter method for PRN
    public long getPrn() { return prn; }

    // Getter method for age
    public int getAge() { return age; }

    // Getter method for CGPA
    public double getCgpa() { return cgpa; }

    // Method to display the student's details
    public void display() {
        System.out.println("Name: " + name);      // Display name
        System.out.println("PRN: " + prn);        // Display PRN
        System.out.println("Age: " + age);        // Display age
        System.out.println("CGPA: " + cgpa);      // Display CGPA
    }
}
