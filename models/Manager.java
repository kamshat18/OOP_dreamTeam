package models;

import enums.LessonType;
import enums.ManagerType;
import enums.SortBy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class Manager extends Employee {
    private ManagerType managerType;
    private String department;
    private List<Student> students = new ArrayList<>();
    private List<Teacher> teachers = new ArrayList<>();
    private List<Request> requests = new ArrayList<>();


    public Manager(String id, String fullName, String email, String password, String language,
                   double salary, Date hireDate, String employeeId,
                   ManagerType managerType, String department) {
        super(id, fullName, email, password, language, salary, hireDate, employeeId);

        this.managerType = managerType;
        this.department = department;
    }

    public void addStudent(Student student) {
        if (student != null && !students.contains(student)) {
            students.add(student);
        }
    }
    public void addTeacher(Teacher teacher) {
        if (teacher != null && !teachers.contains(teacher)) {
            teachers.add(teacher);
        }

    }

    public void assignCourseToTeacher(Course course, Teacher teacher, LessonType lessonType) {
        if (course == null || teacher == null || lessonType == null) return;
        if (lessonType == LessonType.LECTURE) {
            course.setLectureTeacher(teacher);
        }
        else if (lessonType == LessonType.PRACTICE) {
            course.setPracticeTeacher(teacher);
        }
        teacher.manageCourse(course);

        if (!teachers.contains(teacher)) {
            teachers.add(teacher);
        }
    }

    public void assignCourseToTeacher(Course course, Teacher teacher) {
        if (course == null || teacher == null) return;
        if (course.getLectureTeacher() == null) assignCourseToTeacher(course, teacher, LessonType.LECTURE);
        else assignCourseToTeacher(course, teacher, LessonType.PRACTICE);
    }
    public boolean approveRegistration(Student student, Course course) {
        if (student == null || course == null) return false;
        return student.registerCourse(course);
    }
    public void addCourseForRegistration(Course course, int yearOfStudy, String major) {
        if (course == null) return;
        course.setYearOfStudy(yearOfStudy);
        course.setMajor(major);
    }
    public Report generateStatisticalReport() {
        if (students == null || students.isEmpty()) return new Report("Academic Performance Report", "No students");
        double sum = 0, min = students.get(0).getGpa(), max = students.get(0).getGpa();
        for (Student student : students) {
            double curGpa = student.getGpa();
            sum += curGpa;
            if (curGpa < min) min = curGpa;
            if (curGpa > max) max = curGpa;
        }
        String content =
                "Total number of students: " + students.size() + "\n" +
                        "Average GPA: " + sum / students.size() + "\n" +
                        "Maximum GPA: " + max + "\n" +
                        "Minimum GPA: " + min + "\n";
        return new Report("Academic Performance Report", content);
    }
    public void manageNews(News news, String action) {
        if (news == null || action == null) return;
        if (action.equalsIgnoreCase("pin")) news.pin();
    }
    public List<Student> viewAllStudents(SortBy sortBy) {
        List<Student> studentsSorted = new ArrayList<>(students);
        if (sortBy == SortBy.NAME) studentsSorted.sort(Comparator.comparing(Student::getFullName));
        if (sortBy == SortBy.ID) studentsSorted.sort(Comparator.comparing(Student::getStudentId));
        if (sortBy == SortBy.GPA) studentsSorted.sort(Comparator.comparingDouble(Student::getGpa));
        return studentsSorted;
    }
    public List<Teacher> viewAllTeachers() {
        if (teachers == null) return new ArrayList<>();
        return new ArrayList<>(teachers);
    }
    public List<Request> viewRequests() {
        if (requests == null) return new ArrayList<>();
        return new ArrayList<>(requests);
    }
}