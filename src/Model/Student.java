package Model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Student extends Person {

    private String studentID;
    private double gpa;
    private String department;

    // Map to track courses and their grades
    private Map<Course, Double> courseGrades = new HashMap<>();

    // Updated constructor includes department
    public Student(String name, String email, String studentID, String department) {
        super(name, email);
        this.studentID = studentID;
        this.department = department;
        this.gpa = 0.0;
    }

    // Getters and setters
    public String getStudentID() { return studentID; }
    public void setStudentID(String studentID) { this.studentID = studentID; }

    public double getGpa() { return gpa; }
    public void setGpa(double gpa) {
        if (gpa >= 0 && gpa <= 4.0) {
            this.gpa = gpa;
        } else {
            System.out.println("GPA must be between 0.0 and 4.0");
        }
    }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public Map<Course, Double> getCourseGrades() { return courseGrades; }

    // Enroll in a course with grade
    public void enrollCourse(Course course, double grade) {
        if (!courseGrades.containsKey(course)) {
            courseGrades.put(course, grade);
            course.enrollStudent(this); // maintain the relationship
        } else {
            System.out.println("Already enrolled in " + course.getCourseName());
        }
    }

    // Display student info
    public void displayStudentInfo() {
        System.out.println("Name: " + getName());
        System.out.println("Student ID: " + studentID);
        System.out.println("GPA: " + gpa);
        System.out.println("Department: " + department);
    }

    @Override
    public String getRole() {
        return "Student";
    }

    @Override
    public String toString() {
        return getRole() + ": " + getName() + " | Email: " + getEmail() + " | StudentID: " + studentID;
    }

    // Optional: Override equals and hashCode so HashMap and List work properly
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return Objects.equals(studentID, student.studentID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentID);
    }
}