package models;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Transcript {
    private Student student;
    private Map<Course, Mark> marks;
    private double gpa;
    private Date generatedDate;

    public Student getStudent() {
        return student;
    }
    public Map<Course, Mark> getMarks() {
        return marks;
    }
    public double getGpa() {
        return gpa;
    }
    public Date getGeneratedDate() {
        return generatedDate;
    }
    public void setStudent(Student student) {
        this.student = student;
    }
    public void setMarks(Map<Course, Mark> marks) {
        if (marks == null) this.marks = new HashMap<>();
        else this.marks = marks;
    }
    public void setGpa(double gpa) {
        this.gpa = gpa;
    }
    public void setGeneratedDate(Date generatedDate) {
        this.generatedDate = generatedDate;
    }

    public Transcript(Student student, Map<Course, Mark> marks, double gpa, Date generatedDate) {
        this.student = student;
        if (marks == null) this.marks = new HashMap<>();
        else this.marks = marks;
        this.gpa = gpa;
        if (generatedDate == null) this.generatedDate = new Date();
        else this.generatedDate = generatedDate;
    }

    public Transcript(Student student) {
        this.student = student;
        this.marks = new HashMap<>();
        this.gpa = 0;
        this.generatedDate = new Date();
    }

    public void addMark(Course course, Mark mark) {
        marks.put(course, mark);
        calculateGPA();
    }

    private double gradeToPoints(Mark mark) {
        if (mark == null) return 0;
        mark.calculateLetterGrade();
        String letter = mark.getLetterGrade();
        if (letter == null) return 0;
        switch (letter) {
            case "A":
                return 4.00;
            case "A-":
                return 3.67;
            case "B+":
                return 3.33;
            case "B":
                return 3.00;
            case "B-":
                return 2.67;
            case "C+":
                return 2.33;
            case "C":
                return 2.00;
            case "C-":
                return 1.67;
            case "D+":
                return 1.33;
            case "D":
                return 1.00;
            default:
                return 0.00;
        }
    }

    public double calculateGPA() {
        if (marks == null || marks.isEmpty()) return this.gpa = 0;
        double sum = 0, sumCredits = 0;
        for (Mark mark : marks.values()) {
            if (mark == null) continue;
            sum += gradeToPoints(mark) * mark.getCourse().getCredits();
            sumCredits += mark.getCourse().getCredits();
        }
        if (sumCredits == 0) setGpa(0);
        else setGpa(sum / sumCredits);
        return this.gpa;
    }
    public void printTranscript() {
        System.out.println("models.Transcript for student " + student.getFullName() + " (" + student.getStudentId() + "):");
        for (Course course : marks.keySet()) {
            System.out.println(course.toString() + ": " + marks.get(course));
        }
    }
}