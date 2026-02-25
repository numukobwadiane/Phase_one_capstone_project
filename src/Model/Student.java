package Model;

import java.util.HashMap;
import java.util.Map;

public abstract class Student extends Person {

    private String studentID;
    private double gpa;
    private String department;

    // Many-to-many relationship
    private Map<Course, Double> enrolledCourses = new HashMap<>();

    public Student(String name, String email,
                   String studentID, String department) {
        super(name, email);
        this.studentID = studentID;
        this.department = department;
        this.gpa = 0.0;
    }

    // Enroll course + assign grade
    public void enrollCourse(Course course, double grade) {
        enrolledCourses.put(course, grade);
        course.getStudents().add(this);
    }

    // Calculate GPA automatically based on grades
    public void calculateGpa() {
        if (enrolledCourses.isEmpty()) {
            gpa = 0.0;
            return;
        }

        double total = 0.0;

        for (double grade : enrolledCourses.values()) {
            total += grade;
        }

        gpa = total / enrolledCourses.size();
    }

    // Getters & Setters

    public String getStudentID() {
        return studentID;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        if (gpa >= 0 && gpa <= 4.0) {
            this.gpa = gpa;
        }
    }

    public String getDepartment() {
        return department;
    }

    public Map<Course, Double> getEnrolledCourses() {
        return enrolledCourses;
    }

    @Override
    public String getRole() {
        return "Student";
    }

    @Override
    public String toString() {
        return getRole() + " | ID: " + studentID +
                " | Name: " + getName() +
                " | GPA: " + gpa;
    }

    // Required for Lab 1 Exercise 1.2
    public abstract double calculateTuition();
}