package models;

public class Mark {
    private double firstAttestation;
    private double secondAttestation;
    private double finalExam;
    private double total;
    private String letterGrade;
    private Course course;
    private Student student;

    public double getTotal() {
        return total;
    }
    public String getLetterGrade() {
        if (letterGrade == null) calculateLetterGrade();
        return letterGrade;
    }
    public Course getCourse() {
        return course;
    }
    public Student getStudent() {
        return student;
    }
    public void setFirstAttestation(double firstAttestation) {
        this.firstAttestation = firstAttestation;
    }
    public void setSecondAttestation(double secondAttestation) {
        this.secondAttestation = secondAttestation;
    }
    public void setFinalExam(double finalExam) {
        this.finalExam = finalExam;
    }
    public void setTotal(double total) {
        this.total = total;
    }
    public void setCourse(Course course) {
        this.course = course;
    }
    public void setStudent(Student student) {
        this.student = student;
    }
    public double calculateTotal() {
        this.total = firstAttestation + secondAttestation + finalExam;
        return total;
    }
    public String calculateLetterGrade() {
        calculateTotal();
        if (total >= 94.5) letterGrade = "A";
        else if (total >= 89.5) letterGrade = "A-";
        else if (total >= 84.5) letterGrade = "B+";
        else if (total >= 79.5) letterGrade = "B";
        else if (total >= 74.5) letterGrade = "B-";
        else if (total >= 69.5) letterGrade = "C+";
        else if (total >= 64.5) letterGrade = "C";
        else if (total >= 59.5) letterGrade = "C-";
        else if (total >= 54.5) letterGrade = "D+";
        else if (total >= 49.5) letterGrade = "D";
        else letterGrade = "F";
        return letterGrade;
    }
    @Override
    public String toString() {
        return "models.Mark for " + (student == null ? "N/A" : student.getFullName()) + " on " +  (course == null ? "N/A" : course.getTitle()) + ": " + firstAttestation + "+" + secondAttestation + "+" + finalExam + "=" + total + " (" + letterGrade + ")";
    }
}