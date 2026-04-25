import model.Users.User;

import java.util.ArrayList;
import java.util.List;

public class Student extends User {
    private final String studentId;
    private String major;
    private int eyarOfStudy;
    private double gpa;
    private int credits;
    private List<Course> enrolledCourses;
    private List<Mark> marks;

    public String getStudentId() {
        return studentId;
    }
    public String getMajor() {
        return major;
    }
    public int getYearOfStudy() {
        return eyarOfStudy;
    }
    public double getGpa() {
        return gpa;
    }
    public int getCredits() {
        return credits;
    }
    public List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }
    List<Mark> getMarks() {
        return marks;
    }
    void setMarks(List<Mark> marks) {
        this.marks = marks;
    }
    public Student(String id, String fullName, String email, String password, String language,
                   String studentId, String major, int eyarOfStudy, double gpa, int credits,
                   List<Course> enrolledCourses, List<Mark> marks) {
        super(id, fullName, email, password, language);
        this.studentId = studentId;
        this.major = major;
        this.eyarOfStudy = eyarOfStudy;
        this.gpa = gpa;
        this.credits = credits;
        if (enrolledCourses == null) this.enrolledCourses = new ArrayList<>();
        else this.enrolledCourses = enrolledCourses;
        if (marks == null) this.marks = new ArrayList<>();
        else this.marks = marks;
    }

    public boolean registerCourse(Course course) {
        if (course == null || enrolledCourses.contains(course)) return false;
        if (credits + course.getCredits() > 21) return false;
        if (course.getYearOfStudy() != 0 && eyarOfStudy != course.getYearOfStudy()) return false;
        int countRetakes = 0;
        for (Mark mark : marks) {
            if (mark != null &&
                    mark.getCourse() != null &&
                    mark.getCourse().equals(course) &&
                    "F".equals(mark.getLetterGrade())) countRetakes++;
        }
        if (countRetakes >= 3) return false;
        if (course.getCourseType() == CourseType.MAJOR && !major.equals(course.getMajor())) return false;
        if (course.getAvailableSeats() <= 0) return false;
        if (!course.addStudent(this)) return false;
        enrolledCourses.add(course);
        credits += course.getCredits();
        return true;
    }
    public boolean dropCourse(Course course) {
        if (course == null || !enrolledCourses.contains(course)) return false;
        if (!course.removeStudent(this)) return false;
        enrolledCourses.remove(course);
        credits -= course.getCredits();
        if (credits < 0) credits = 0;
        return true;
    }
    public List<Mark> viewMarks() {
        return new ArrayList<>(marks);
    }
    public Transcript viewTranscript() {
        Transcript transcript = new Transcript(this);
        for (Mark mark : marks) transcript.addMark(mark.getCourse(), mark);
        transcript.calculateGPA();
        return transcript;
    }
    public void rateTeacher(Teacher teacher, int rating) {
        if (teacher == null) return;
        TeacherRating.addRating(this, teacher, rating);
    }
    public Transcript getTranscript() {
        return viewTranscript();
    }
    public void joinOrganization(Organization org) {}
    public String toString() {
        return "Student " + getFullName() + ", ID " + getStudentId() + ", major " + getMajor() +
                ", year " + getYearOfStudy() + ", credits " + getCredits() + ", GPA " + getGpa();
    }
}