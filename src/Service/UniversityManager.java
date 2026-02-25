package Service;

import Model.*;
import Exception.*;

import java.util.*;

public class UniversityManager {

    private List<Student> students = new ArrayList<>();
    private List<Course> courses = new ArrayList<>();


    public void registerStudent(Student student) {
        students.add(student);
    }


    public void createCourse(Course course) {
        courses.add(course);
    }


    public void enrollStudentInCourse(Student student, Course course, double grade)
            throws CourseFullException, StudentAlreadyEnrolledException {

        // Rule 1: Course capacity (example max 3 students)
        if (course.getStudents().size() >= 3) {
            throw new CourseFullException(
                    "Course " + course.getCourseName() + " is full.");
        }


        if (course.getStudents().contains(student)) {
            throw new StudentAlreadyEnrolledException(
                    student.getName() + " is already enrolled in " + course.getCourseName());
        }


        student.enrollCourse(course, grade);
    }


    public double calculateAverageGpaByDepartment(String department) {
        return students.stream()
                .filter(s -> s.getDepartment().equalsIgnoreCase(department))
                .mapToDouble(Student::getGpa)
                .average()
                .orElse(0.0);
    }


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