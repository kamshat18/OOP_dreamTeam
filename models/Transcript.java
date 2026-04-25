package models;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Transcript {
    private Student student;
    private Map<Course, Mark> marks;
    private double gpa;
    private Date generatedDate;

    public Transcript(Student student, Map<Course, Mark> marks, double gpa, Date generatedDate) {
        this.student = student;
        this.marks = marks;
        this.gpa = gpa;
        this.generatedDate = generatedDate;
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

    public double calculateGPA() {
        if (marks.isEmpty()) return 0;
        double sum = 0, sumCredits = 0;
        for (Mark mark : marks.values()) {
            sum += mark.getTotal() * mark.getCourse().getCredits();
            sumCredits += mark.getCourse().getCredits();
        }
        return this.gpa = sum / sumCredits;
    }
    public void printTranscript() {
        System.out.println("models.Transcript for student " + student.getFullName() + " (" + student.getStudentId() + "):");
        for (Course course : marks.keySet()) {
            System.out.println(course.toString() + ": " + marks.get(course));
        }
    }
}