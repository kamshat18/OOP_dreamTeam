package models.academic;

import enums.LessonType;
import java.io.Serializable;
import java.util.Date;
import models.users.Teacher;

public class Lesson implements Serializable {
    private String lessonId;
    private LessonType type;
    private Date date;
    private String time;
    private String room;
    private Course course;
    private Teacher teacher;

    public Lesson(String lessonId, LessonType type, Date date, String time, String room, Course course, Teacher teacher) {
        this.lessonId = lessonId;
        this.type = type;
        this.date = date;
        this.time = time;
        this.room = room;
        this.course = course;
        this.teacher = teacher;
    }
    public String getLessonId() {
        return lessonId;
    }
    public LessonType getType() {
        return type;
    }
    public Date getDate() {
        return date;
    }
    public String getTime() {
        return time;
    }
    public String getRoom() {
        return room;
    }
    public Course getCourse() {
        return course;
    }
    public Teacher getTeacher() {
        return teacher;
    }

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
