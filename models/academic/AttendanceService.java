package models.academic;

import enums.AttendanceStatus;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import models.users.Student;

public class AttendanceService {
    private final List<AttendanceRecord> records = new ArrayList<>();

    public AttendanceRecord markAttendance(Student student, Lesson lesson, AttendanceStatus status) {
        if (student == null || lesson == null) {
            return null;
        }
        AttendanceRecord existing = findRecord(student, lesson);
        if (existing != null) {
            existing.updateStatus(status);
            return existing;
        }
        AttendanceRecord record = new AttendanceRecord(student, lesson, status);
        records.add(record);
        return record;
    }

    public List<AttendanceRecord> getRecordsForStudent(Student student) {
        List<AttendanceRecord> result = new ArrayList<>();
        for (AttendanceRecord record : records) {
            if (record.getStudent().equals(student)) {
                result.add(record);
            }
        }
        return result;
    }

    public List<AttendanceRecord> getRecordsForLesson(Lesson lesson) {
        List<AttendanceRecord> result = new ArrayList<>();
        for (AttendanceRecord record : records) {
            if (record.getLesson().equals(lesson)) {
                result.add(record);
            }
        }
        return result;
    }

    public double calculateAttendanceRate(Student student) {
        List<AttendanceRecord> studentRecords = getRecordsForStudent(student);
        if (studentRecords.isEmpty()) {
            return 0;
        }
        int attended = 0;
        for (AttendanceRecord record : studentRecords) {
            if (record.countsAsAttended()) {
                attended++;
            }
        }
        return (double) attended / studentRecords.size();
    }

    public List<AttendanceRecord> getAllRecords() {
        return Collections.unmodifiableList(records);
    }

    private AttendanceRecord findRecord(Student student, Lesson lesson) {
        for (AttendanceRecord record : records) {
            if (record.getStudent().equals(student) && record.getLesson().equals(lesson)) {
                return record;
            }
        }
        return null;
    }
}
