package Service;

import Model.*;
import Exception.*;

import java.util.*;

public class UniversityManager {

    private List<Student> students = new ArrayList<>();
    private List<Course> courses = new ArrayList<>();

    // Register student
    public void registerStudent(Student student) {
        students.add(student);
    }

    // Create course
    public void createCourse(Course course) {
        courses.add(course);
    }

    // Enroll student with business validation
    public void enrollStudentInCourse(Student student, Course course, double grade)
            throws CourseFullException, StudentAlreadyEnrolledException {

        // Rule 1: Course capacity (example max 3 students)
        if (course.getStudents().size() >= 3) {
            throw new CourseFullException(
                    "Course " + course.getCourseName() + " is full.");
        }

        // Rule 2: Prevent duplicate enrollment
        if (course.getStudents().contains(student)) {
            throw new StudentAlreadyEnrolledException(
                    student.getName() + " is already enrolled in " + course.getCourseName());
        }

        // If all good → enroll
        student.enrollCourse(course, grade);
    }

    // Average GPA by department (Streams)
    public double calculateAverageGpaByDepartment(String department) {
        return students.stream()
                .filter(s -> s.getDepartment().equalsIgnoreCase(department))
                .mapToDouble(Student::getGpa)
                .average()
                .orElse(0.0);
    }

    // Find top student (Streams)
    public Optional<Student> findTopStudent() {
        return students.stream()
                .max(Comparator.comparingDouble(Student::getGpa));
    }

    public List<Student> getStudents() {
        return students;
    }

    public List<Course> getCourses() {
        return courses;
    }
}