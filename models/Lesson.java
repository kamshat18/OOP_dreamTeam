package models;

import enums.LessonType;

import java.util.Date;

public class Lesson {
    private String lessonId;
    private LessonType type;
    private Date date;
    private String time;
    private String room;
    private Course course;
    private Teacher teacher;

    public String getLessonInfo() {
        return "models.Lesson " + (course == null ? "N/A" : course.getTitle()) + " (" + lessonId + "), " + type +
                ". At " + date + ", " + time + ". Room " + room
                + ". Taught by " + (teacher == null ? "nobody" : teacher.toString());
    }

    @Override
    public String toString() {
        return getLessonInfo();
    }
}