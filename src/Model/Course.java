package Model;

import java.util.ArrayList;
import java.util.List;

public class Course {

    private String courseID;
    private String courseName;
    private int credits;

    private List<Student> students = new ArrayList<>();

    public Course(String courseID, String courseName, int credits) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.credits = credits;
    }

    // Getters
    public String getCourseID() { return courseID; }
    public String getCourseName() { return courseName; }
    public int getCredits() { return credits; }
    public List<Student> getStudents() { return students; }

    // Enroll student with duplicate check
    public void enrollStudent(Student student) {
        if (!students.contains(student)) {
            students.add(student);
        } else {
            System.out.println(student.getName() + " is already enrolled in " + courseName);
        }
    }

    // Display roster
    public void displayRoster() {
        if (students.isEmpty()) {
            System.out.println("No students enrolled in " + courseName);
            return;
        }
        System.out.println("Roster for " + courseName + ":");
        for (Student s : students) {
            System.out.println("- " + s.getName() + " | ID: " + s.getStudentID());
        }
    }

    @Override
    public String toString() {
        return "CourseID: " + courseID + " | Name: " + courseName + " | Credits: " + credits;
    }
}