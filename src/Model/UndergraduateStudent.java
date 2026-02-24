package Model;

public class UndergraduateStudent extends Student {


    public UndergraduateStudent(String name, String email, String studentID, String department) {
        super(name, email, studentID, department);
    }


    public double calculateTuition() {
        return 5000;
    }

    @Override
    public String toString() {
        return super.toString() + " | Type: Undergraduate";
    }
}