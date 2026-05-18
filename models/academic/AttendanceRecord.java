package models;

import enums.AttendanceStatus;

import java.io.Serializable;
import java.util.Date;

public class AttendanceRecord implements Serializable {
    private final Student student;
    private final Lesson lesson;
    private AttendanceStatus status;
    private final Date recordedAt;

    public AttendanceRecord(Student student, Lesson lesson, AttendanceStatus status) {
        this.student = student;
        this.lesson = lesson;
        this.status = status == null ? AttendanceStatus.ABSENT : status;
        this.recordedAt = new Date();
    }

    public Student getStudent() {
        return student;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public AttendanceStatus getStatus() {
        return status;
    }

    public Date getRecordedAt() {
        return new Date(recordedAt.getTime());
    }

    public void updateStatus(AttendanceStatus status) {
        if (status != null) {
            this.status = status;
        }
    }

    public boolean countsAsAttended() {
        return status == AttendanceStatus.PRESENT || status == AttendanceStatus.LATE;
    }

    @Override
    public String toString() {
        return (student == null ? "Unknown student" : student.getFullName()) +
                " | " + (lesson == null ? "Unknown lesson" : lesson.getLessonId()) +
                " | " + status;
    }
}
