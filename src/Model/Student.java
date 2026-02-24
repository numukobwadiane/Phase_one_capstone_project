package Model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Student extends Person {

    private String studentID;
    private double gpa;
    private String department;


    private Map<Course, Double> courseGrades = new HashMap<>();


    public Student(String name, String email, String studentID, String department) {
        super(name, email);
        this.studentID = studentID;
        this.department = department;
        this.gpa = 0.0;
    }


    public String getStudentID() { return studentID; }
    public void setStudentID(String studentID) { this.studentID = studentID; }

    public double getGpa() { return gpa; }
    public void setGpa(double gpa) {
        if (gpa >= 0 && gpa <= 4.0) this.gpa = gpa;
    }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public Map<Course, Double> getCourseGrades() { return courseGrades; }


    public void enrollCourse(Course course, double grade) {
        if (!courseGrades.containsKey(course)) {
            courseGrades.put(course, grade);
            course.enrollStudent(this);
        } else {
            System.out.println("Already enrolled in " + course.getCourseName());
        }
    }


    public void displayCourses() {
        if (courseGrades.isEmpty()) {
            System.out.println(getName() + " is not enrolled in any course.");
            return;
        }
        System.out.println("Courses for " + getName() + ":");
        for (Map.Entry<Course, Double> entry : courseGrades.entrySet()) {
            System.out.println("- " + entry.getKey().getCourseName() + " | Grade: " + entry.getValue());
        }
    }

    public void displayStudentInfo() {
        System.out.println("Name: " + getName());
        System.out.println("Student ID: " + studentID);
        System.out.println("GPA: " + gpa);
        System.out.println("Department: " + department);
    }

    @Override
    public String getRole() { return "Student"; }

    @Override
    public String toString() {
        return super.toString() + " | StudentID: " + studentID;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return Objects.equals(studentID, student.studentID);
    }

    @Override
    public int hashCode() { return Objects.hash(studentID); }
}
