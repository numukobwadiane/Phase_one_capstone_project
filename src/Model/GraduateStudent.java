package Model;

public class GraduateStudent extends Student {

    private double researchFee;


    public GraduateStudent(String name, String email, String studentID, String department, double researchFee) {
        super(name, email, studentID, department);
        this.researchFee = researchFee;
    }


    public double getResearchFee() { return researchFee; }
    public void setResearchFee(double researchFee) { this.researchFee = researchFee; }


    public double calculateTuition(int totalCredits) {
        return totalCredits * 300 + researchFee;
    }

    @Override
    public String toString() {
        return super.toString() + " | Type: Graduate";
    }
}