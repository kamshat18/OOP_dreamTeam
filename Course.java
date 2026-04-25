import java.util.ArrayList;
import java.util.List;

public class Course {
    private String courseId;
    private String title;
    private int credits;
    private String major;
    private int yearOfStudy;
    private CourseType courseType;
    private Teacher lectureTeacher;
    private Teacher practiceTeacher;
    private List<Student> enrolledStudents;
    private List<Lesson> lessons;

    public String getTitle() {
        return title;
    }
    public int getCredits() {
        return credits;
    }
    public String getMajor() {
        return major;
    }
    public int getYearOfStudy() {
        return yearOfStudy;
    }
    public CourseType getCourseType() {
        return courseType;
    }
    public Teacher getLectureTeacher() {
        return lectureTeacher;
    }
    public Teacher getPracticeTeacher() {
        return practiceTeacher;
    }
    public List<Student> getEnrolledStudents() {
        return enrolledStudents;
    }
    public List<Lesson> getLessons() {
        return lessons;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setCredits(int credits) {
        this.credits = credits;
    }
    public void setMajor(String major) {
        this.major = major;
    }
    public void setYearOfStudy(int yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }
    public void setCourseType(CourseType courseType) {
        this.courseType = courseType;
    }
    public void setLectureTeacher(Teacher lectureTeacher) {
        this.lectureTeacher = lectureTeacher;
    }
    public void setPracticeTeacher(Teacher practiceTeacher) {
        this.practiceTeacher = practiceTeacher;
    }
    public Course() {
        enrolledStudents = new ArrayList<>();
        lessons = new ArrayList<>();
    }

    boolean addStudent(Student student) {
        if (enrolledStudents.contains(student)) return false;
        return enrolledStudents.add(student);
    }
    public boolean removeStudent(Student student) {
        if (!enrolledStudents.contains(student)) return false;
        return enrolledStudents.remove(student);
    }
    public int getAvailableSeats() {
        return 75 - enrolledStudents.size();
    }
    public Teacher getTeacherForLessonType(LessonType type) {
        if (type == LessonType.LECTURE) return lectureTeacher;
        return practiceTeacher;
    }

    @Override
    public String toString() {
        return "Course " + courseId + ": " + title + " (" + credits + " credits)" +
                ". Major: " + major + ", year of study: " + yearOfStudy + ". " +
                "Course type: " + courseType + ". Lecture teacher: " + (lectureTeacher == null ? "not assigned" : lectureTeacher.getName()) +
                ", practice teacher: " + (practiceTeacher == null ? "not assigned" : practiceTeacher.getName()) +
                ". Enrolled students: " + (enrolledStudents == null ? 0 : enrolledStudents.size()) + "/75" +
                ". Number of lessons: " + (lessons == null ? 0 : lessons.size());
    }
}