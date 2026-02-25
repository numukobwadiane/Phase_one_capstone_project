package Model;

public class GraduateStudent extends Student {

    private static final double COST_PER_CREDIT = 1000.0;
    private static final double RESEARCH_FEE = 2000.0;

    public GraduateStudent(String name, String email,
                           String studentID, String department) {
        super(name, email, studentID, department);
    }

    @Override
    public double calculateTuition() {

        int totalCredits = getEnrolledCourses()
                .keySet()
                .stream()
                .mapToInt(Course::getCredits)
                .sum();

        return (totalCredits * COST_PER_CREDIT) + RESEARCH_FEE;
    }

    @Override
    public String getRole() {
        return "Graduate Student";
    }
}