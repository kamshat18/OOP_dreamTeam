import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Manager extends Employee {
    private ManagerType managerType;
    private String department;
    private List<Student> students = new ArrayList<>();
    private List<Teacher> teachers = new ArrayList<>();
    private List<Request> requests = new ArrayList<>();

    public void assignCourseToTeacher(Course course, Teacher teacher) {
        if (course == null || teacher == null) return;
//        if (teachers.contains(teacher)) return;
        if (course.getLectureTeacher() == null) course.setLectureTeacher(teacher);
        else if (course.getPracticeTeacher() == null)course.setPracticeTeacher(teacher);
        else throw new IllegalArgumentException("Course already has two teachers");
        teacher.manageCourse(course);
        teachers.add(teacher);
    }
    public boolean approveRegistration(Student student, Course course) {
        if (student == null || course == null) return false;
        return student.registerCourse(course);
    }
    public void addCourseForRegistraion(Course course, int yearOfStudy, String major) {
        if (course == null) return;
        course.setYearOfStudy(yearOfStudy);
        course.setMajor(major);
    }
    public String generateStatisticalReport() {
        if (students == null || students.isEmpty()) return "No students";
        double sum = 0, min = students.get(0).getGpa(), max = students.get(0).getGpa();
        for (Student student : students) {
            double curGpa = student.getGpa();
            sum += curGpa;
            if (curGpa < min) min = curGpa;
            if (curGpa > max) max = curGpa;
        }
        return "Total number of students: " + students.size() + "\n" +
                "Average GPA: " + sum / students.size() + "\n" +
                "Maximum GPA: " + max + "\n" +
                "Minimum GPA: " + min + "\n";
    }
    public void manageNews(News news, String action) {
        if (news == null || action == null) return;
    }
    public List<Student> viewAllStudents(SortBy sortBy) {
        List<Student> studentsSorted = new ArrayList<>(students);
        if (sortBy == SortBy.NAME) studentsSorted.sort(Comparator.comparing(Student::getName));
        if (sortBy == SortBy.ID) studentsSorted.sort(Comparator.comparing(Student::getStudentId));
        if (sortBy == SortBy.GPA) studentsSorted.sort(Comparator.comparingDouble(Student::getGpa));
        return studentsSorted;
    }
    public List<Teacher> viewAllTeachers() {
        if (teachers == null) return new ArrayList<>();
        return teachers;
    }
    public List<Request> viewRequests() {
        if (requests == null) return new ArrayList<>();
        return requests;
    }
}