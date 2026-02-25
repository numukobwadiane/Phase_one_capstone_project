import Model.*;
import Service.*;
import Exception.*;

import java.text.NumberFormat;
import java.util.*;

public class Main {

    private static UniversityManager manager = new UniversityManager();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {


        manager.getCourses().addAll(FileManager.loadCourses());
        manager.getStudents().addAll(
                FileManager.loadStudents(manager.getCourses())
        );

        int choice;

        do {
            System.out.println("\n===== UNIVERSITY SYSTEM =====");
            System.out.println("1. Register Student");
            System.out.println("2. Create Course");
            System.out.println("3. Enroll Student");
            System.out.println("4. View Student Record");
            System.out.println("5. Generate Dean's List");
            System.out.println("6. Save and Exit");

            System.out.print("Choose option: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> registerStudent();
                case 2 -> createCourse();
                case 3 -> enrollStudent();
                case 4 -> viewStudent();
                case 5 -> generateDeansList();
                case 6 -> saveAndExit();
                default -> System.out.println("Invalid choice.");
            }

        } while (choice != 6);
    }

    private static void registerStudent() {
        System.out.print("Undergrad or Grad? (U/G): ");
        String type = scanner.nextLine();

        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Student ID: ");
        String id = scanner.nextLine();

        System.out.print("Department: ");
        String dept = scanner.nextLine();

        Student student;

        if (type.equalsIgnoreCase("G")) {
            student = new GraduateStudent(name, email, id, dept);
        } else {
            student = new UndergraduateStudent(name, email, id, dept);
        }

        manager.registerStudent(student);
        System.out.println("Student registered successfully.");
    }

    private static void createCourse() {
        System.out.print("Course ID: ");
        String id = scanner.nextLine();

        System.out.print("Course Name: ");
        String name = scanner.nextLine();

        System.out.print("Credits: ");
        int credits = scanner.nextInt();
        scanner.nextLine();

        manager.createCourse(new Course(id, name, credits));
        System.out.println("Course created successfully.");
    }

    private static void enrollStudent() {

        System.out.print("Student ID: ");
        String sid = scanner.nextLine();

        System.out.print("Course ID: ");
        String cid = scanner.nextLine();

        System.out.print("Grade: ");
        double grade = scanner.nextDouble();
        scanner.nextLine();

        Optional<Student> studentOpt =
                manager.getStudents().stream()
                        .filter(s -> s.getStudentID().equals(sid))
                        .findFirst();

        Optional<Course> courseOpt =
                manager.getCourses().stream()
                        .filter(c -> c.getCourseID().equals(cid))
                        .findFirst();

        if (studentOpt.isEmpty() || courseOpt.isEmpty()) {
            System.out.println("Student or Course not found.");
            return;
        }

        try {
            manager.enrollStudentInCourse(
                    studentOpt.get(),
                    courseOpt.get(),
                    grade
            );
            studentOpt.get().calculateGpa();
            System.out.println("Enrollment successful.");

        } catch (CourseFullException | StudentAlreadyEnrolledException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void viewStudent() {
        System.out.print("Student ID: ");
        String sid = scanner.nextLine();

        manager.getStudents().stream()
                .filter(s -> s.getStudentID().equals(sid))
                .findFirst()
                .ifPresentOrElse(s -> {
                    System.out.println(s);
                    System.out.println("Courses:");
                    s.getEnrolledCourses().forEach((course, grade) ->
                            System.out.println(course.getCourseName()
                                    + " | Grade: " + grade));
                    System.out.println("Tuition Fee: " + formatMoney(s.calculateTuition()));

                }, () -> System.out.println("Student not found."));
    }

    private static String formatMoney(double amount) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.US);
        return formatter.format(amount);
    }

    private static void generateDeansList() {
        System.out.println("=== Dean's List (GPA > 3.5) ===");

        manager.getStudents().stream()
                .filter(s -> s.getGpa() > 3.5)
                .forEach(System.out::println);
    }

    private static void saveAndExit() {
        FileManager.saveCourses(manager.getCourses());
        FileManager.saveStudents(manager.getStudents());
        System.out.println("Data saved successfully.");
    }
}