// StudentOperations.java
// Handles Student operations with validation, custom exceptions, and DB sync

import java.util.*;
import customexceptions.*;

class StudentOperations {
    private ArrayList<Student> students = new ArrayList<>();
    private StudentDAO dao = new StudentDAO();

    public StudentOperations() {
        dao.loadAllFromDatabase(students); // Load existing students from DB
    }

    public void addStudent(Student student) throws DuplicatePRNException, TooManyStudentsException, NullStudentException {
        if (student == null) throw new NullStudentException("Cannot add a null student");
        if (students.size() >= 100) throw new TooManyStudentsException("Cannot add more than 100 students");

        for (Student s : students) {
            if (s.getPrn() == student.getPrn()) {
                throw new DuplicatePRNException("Student with this PRN already exists");
            }
        }

        students.add(student);
        dao.addToDatabase(student); // Save to DB
        System.out.println("Student Added Successfully.");
    }

    public void displayStudents() throws EmptyStudentListException {
        if (students.isEmpty()) throw new EmptyStudentListException("No students to display.");
        for (Student s : students) {
            System.out.println("-----------------------------------------------------");
            s.display();
            System.out.println("-----------------------------------------------------\n");
        }
    }

    public void searchStudent() throws EmptyStudentListException, InvalidSearchChoiceException, StudentNotFoundException, InvalidPositionException  {
        if (students.isEmpty()) throw new EmptyStudentListException("No students available to search.");

        Scanner scan = new Scanner(System.in);
        System.out.println("Search by:\n1. PRN\n2. Name\n3. Position");
        int choice = Integer.parseInt(scan.nextLine());

        switch (choice) {
            case 1:
                System.out.println("Enter PRN to search:");
                long prn = Long.parseLong(scan.nextLine());
                for (Student s : students) {
                    if (s.getPrn() == prn) {
                        s.display();
                        return;
                    }
                }
                throw new StudentNotFoundException("Student with PRN not found.");

            case 2:
                System.out.println("Enter Name to search:");
                String name = scan.nextLine();
                for (Student s : students) {
                    if (s.getName().equalsIgnoreCase(name)) {
                        s.display();
                        return;
                    }
                }
                throw new StudentNotFoundException("Student with name not found.");

            case 3:
                System.out.println("Enter Position (Index) to search:");
                int pos = Integer.parseInt(scan.nextLine());
                if (pos < 0 || pos >= students.size())
                    throw new InvalidPositionException("Invalid index position");
                students.get(pos).display();
                break;

            default:
                throw new InvalidSearchChoiceException("Invalid search choice.");
        }
    }

    public void deleteStudent() throws EmptyStudentListException, DeletionNotAllowedException, StudentNotFoundException {
        if (students.isEmpty()) throw new EmptyStudentListException("No students available to delete.");

        Scanner scan = new Scanner(System.in);
        System.out.println("Enter PRN to delete:");
        long prn = Long.parseLong(scan.nextLine());

        Iterator<Student> iterator = students.iterator();
        while (iterator.hasNext()) {
            Student student = iterator.next();
            if (student.getPrn() == prn) {
                iterator.remove();
                dao.deleteFromDatabase(prn); // Sync with DB
                System.out.println("Student deleted successfully.");
                return;
            }
        }
        throw new StudentNotFoundException("Student not found with the given PRN.");
    }

    public void editStudent() throws EmptyStudentListException, EditNotAllowedException, StudentNotFoundException {
        if (students.isEmpty()) throw new EmptyStudentListException("No students available to edit.");

        Scanner scan = new Scanner(System.in);
        System.out.println("Enter PRN of student to edit:");
        long prn = Long.parseLong(scan.nextLine());

        for (Student student : students) {
            if (student.getPrn() == prn) {
                try {
                    System.out.println("Enter New Name:");
                    student.setName(scan.nextLine());
                    System.out.println("Enter New Age:");
                    student.setAge(Integer.parseInt(scan.nextLine()));
                    System.out.println("Enter New CGPA:");
                    student.setCgpa(Double.parseDouble(scan.nextLine()));

                    dao.updateInDatabase(student); // Sync with DB
                    System.out.println("Student details updated successfully!");
                } catch (Exception e) {
                    throw new EditNotAllowedException("Invalid details provided during edit.");
                }
                return;
            }
        }
        throw new StudentNotFoundException("Student not found with the given PRN.");
    }
}