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
        return "Lesson " + course + " (" + lessonId + "), " + type + ". At " + date + ", " + time + ". Room " + room
                + ". Taught by " + teacher.toString();
    }

    @Override
    public String toString() {
        return getLessonInfo();
    }
}