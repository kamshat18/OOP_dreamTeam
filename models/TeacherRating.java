package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TeacherRating {
    private Student student;
    private Teacher teacher;
    private int rating;
    private Date date;
    private static final List<TeacherRating> ratings = new ArrayList<>();

    public Student getStudent() {
        return student;
    }
    public Teacher getTeacher() {
        return teacher;
    }
    public int getRating() {
        return rating;
    }
    public Date getDate() {
        return date;
    }

    public TeacherRating(Student student, Teacher teacher, int rating, Date date) {
        this.student = student;
        this.teacher = teacher;
        this.rating = rating;
        this.date = date;

    }
    public static void addRating(Student student, Teacher teacher, int rating) {
        if (student == null || teacher == null) return;
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be in range 1..5");
        }
        ratings.add(new TeacherRating(student, teacher, rating, new Date()));
    }
    public static double getRating(Teacher teacher) {
        int sum = 0, cnt = 0;
        for (TeacherRating teacherRating : ratings) {
            if (teacherRating.getTeacher().equals(teacher)) {
                sum += teacherRating.getRating();
                cnt++;
            }
        }
        if (cnt < 1) return 0;
        return (double)sum / (double)cnt;

    }
}