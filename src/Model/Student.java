package Model;

public class Student extends Person{


    private String studentID;
    private double gpa;

    public Student(String name, String email, String id, String studentID) {
        super(name, email);
        this.studentID = studentID;
        this.gpa = 0.0;
    }


    public void updateGpa(double newGpa) {
        if (newGpa >= 0 && newGpa <= 4.0) {
            gpa = newGpa;
        }
    }

    public void displayStudentInfo() {
        System.out.println("Name: " + getName());
        System.out.println("Student ID: " + studentID);
        System.out.println("GPA: " + gpa);
    }

    @Override
    public String toString() {
        return studentID + " " + " hello";
    }

    @Override
    public String getRole() {

        return "Student";
    }
}