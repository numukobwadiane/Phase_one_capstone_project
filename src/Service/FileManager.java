package Service;

import Model.*;

import java.io.*;
import java.util.*;

public class FileManager {

    private static final String STUDENT_FILE = "students.csv";
    private static final String COURSE_FILE = "courses.csv";


    public static void saveCourses(List<Course> courses) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(COURSE_FILE))) {
            for (Course c : courses) {
                writer.write(c.getCourseID() + "," +
                        c.getCourseName() + "," +
                        c.getCredits());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving courses: " + e.getMessage());
        }
    }


    public static List<Course> loadCourses() {
        List<Course> courses = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(COURSE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                Course course = new Course(
                        parts[0],
                        parts[1],
                        Integer.parseInt(parts[2])
                );
                courses.add(course);
            }
        } catch (FileNotFoundException e) {
            System.out.println("No previous courses found.");
        } catch (IOException e) {
            System.out.println("Error loading courses: " + e.getMessage());
        }

        return courses;
    }


    public static void saveStudents(List<Student> students) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(STUDENT_FILE))) {

            for (Student s : students) {

                String type = (s instanceof GraduateStudent) ? "GRAD" : "UNDERGRAD";

                writer.write(type + "," +
                        s.getName() + "," +
                        s.getEmail() + "," +
                        s.getStudentID() + "," +
                        s.getDepartment() + "," +
                        s.getGpa());


                if (!s.getEnrolledCourses().isEmpty()) {
                    writer.write(",");
                    List<String> courseData = new ArrayList<>();

                    for (Map.Entry<Course, Double> entry :
                            s.getEnrolledCourses().entrySet()) {

                        courseData.add(
                                entry.getKey().getCourseID()
                                        + ":" +
                                        entry.getValue()
                        );
                    }

                    writer.write(String.join(";", courseData));
                }

                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error saving students: " + e.getMessage());
        }
    }


    public static List<Student> loadStudents(List<Course> courses) {

        List<Student> students = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(STUDENT_FILE))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] parts = line.split(",");

                String type = parts[0];
                String name = parts[1];
                String email = parts[2];
                String id = parts[3];
                String dept = parts[4];

                Student student;

                if (type.equals("GRAD")) {
                    student = new GraduateStudent(name, email, id, dept);
                } else {
                    student = new UndergraduateStudent(name, email, id, dept);
                }

                students.add(student);


                if (parts.length > 6) {
                    String[] courseEntries = parts[6].split(";");

                    for (String entry : courseEntries) {

                        String[] courseParts = entry.split(":");

                        String courseID = courseParts[0];
                        double grade = Double.parseDouble(courseParts[1]);

                        // Find course object
                        Course course = courses.stream()
                                .filter(c -> c.getCourseID().equals(courseID))
                                .findFirst()
                                .orElse(null);

                        if (course != null) {
                            student.enrollCourse(course, grade);
                        }
                    }

                    student.calculateGpa();
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("No previous students found.");
        } catch (IOException e) {
            System.out.println("Error loading students: " + e.getMessage());
        }

        return students;
    }
}