package models;

import enums.TeacherPosition;
import enums.UrgencyLevel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Teacher extends Employee {
    private String teacherId;
    private TeacherPosition position;
    private List<Course> taughtCourses;
    public String getTeacherId() {
        return teacherId;
    }
    public TeacherPosition getPosition() {
        return position;
    }
    public List<Course> getTaughtCourses() {
        return new ArrayList<>(taughtCourses);
    }

    public Teacher(String id, String fullName, String email, String password, String language,
                   double salary, Date hireDate, String employeeId,
                   String teacherId, TeacherPosition position, List<Course> taughtCourses) {
        super(id, fullName, email, password, language, salary, hireDate, employeeId);

        this.teacherId = teacherId;
        this.position = position;
        this.taughtCourses = (taughtCourses == null) ? new ArrayList<>() : taughtCourses;
    }
    public void putMark(Student student, Course course, Mark mark) {
        if (student == null || course == null || mark == null) return;
        if (!taughtCourses.contains(course)) return;
        if (!course.getEnrolledStudents().contains(student)) return;
        mark.setStudent(student);
        mark.setCourse(course);
        mark.calculateTotal();
        mark.calculateLetterGrade();
        List<Mark> newMarks = student.getMarks();
        if (newMarks == null) return;
        for (int i = 0; i < newMarks.size(); i++) {
            Mark markI = newMarks.get(i);
            if (markI != null && markI.getCourse() != null && markI.getCourse().equals(course)) {
                newMarks.set(i, mark);
                return;
            }
        }
        newMarks.add(mark);
//        student.setMarks(newMarks);
    }
    public void manageCourse(Course course) {
        if (course == null) return;
        if (taughtCourses.contains(course)) return;
        taughtCourses.add(course);
    }
    public List<Student> viewStudents(Course course) {
        if (course == null) return new ArrayList<>();
        if (!taughtCourses.contains(course)) return new ArrayList<>();
        return course.getEnrolledStudents();
    }
    public void sendComplaint(Student student, UrgencyLevel urgency, String reason) {
        if (student == null || urgency == null || reason == null || reason.isEmpty()) return;
        System.out.println("Complaint from " + student.toString() + ", urgency " + urgency + ": " + reason);
    }
    public String viewStudentInfo(Student student) {
        if (student == null) return "";
        return "models.Student " + student.getFullName() + ", ID " + student.getStudentId() + ", major " + student.getMajor() +
                ", year " + student.getYearOfStudy() + ", credits " + student.getCredits() + ", GPA " + student.getGpa();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof Teacher && java.util.Objects.equals(teacherId, ((Teacher) o).teacherId)) return true;
        return false;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(teacherId);
    }
    @Override
    public String toString() {
        return "models.Teacher " + getFullName() + " with ID " + teacherId + ", position " + position + ", number taught courses " + (taughtCourses == null ? 0 : taughtCourses.size());
    }
}