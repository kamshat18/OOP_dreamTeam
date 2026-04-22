import java.util.ArrayList;
import java.util.List;

public class Teacher extends Employee {
    private String teacherId;
    private TeacherPosition position;
    private List<Course> taughtCourses;

    public Teacher(String teacherId, TeacherPosition position, List<Course> taughtCourses) {
        super();
        this.teacherId = teacherId;
        this.position = position;
        if (taughtCourses == null) this.taughtCourses = new ArrayList<>();
        else this.taughtCourses = taughtCourses;
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
        newMarks.add(mark);
        student.setMarks(newMarks);
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
        return "Student " + student.getName() + ", ID " + student.getStudentId() + ", major " + student.getMajor() +
                ", year " + student.getYearOfStudy() + ", credits " + student.getCredits() + ", GPA " + student.getGpa();
    }
}