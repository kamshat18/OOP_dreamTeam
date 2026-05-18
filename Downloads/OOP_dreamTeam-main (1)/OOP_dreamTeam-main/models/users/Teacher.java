package models;

import enums.TeacherPosition;
import enums.UrgencyLevel;
import interfaces.Researcher;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class Teacher extends Employee implements Researcher, Serializable {
    private String teacherId;
    private TeacherPosition position;
    private List<Course> taughtCourses;
    private final List<ResearchPaper> researchPapers;
    private final List<ResearchProject> researchProjects;
    public String getTeacherId() {
        return teacherId;
    }
    public TeacherPosition getPosition() {
        return position;
    }
    public List<Course> getTaughtCourses() {
        return new ArrayList<>(taughtCourses);
    }

    public List<Course> getCourses() {
        return getTaughtCourses();
    }

    public void sendComplaintToDean(java.util.List<Student> students, String reason, UrgencyLevel urgency) {
        if (students == null || urgency == null || reason == null || reason.trim().isEmpty()) return;
        for (Student student : students) {
            sendComplaint(student, urgency, reason);
        }
    }

    public Teacher(String id, String fullName, String email, String password, String language,
                   double salary, Date hireDate, String employeeId,
                   String teacherId, TeacherPosition position, List<Course> taughtCourses) {
        super(id, fullName, email, password, language, salary, hireDate, employeeId);

        this.teacherId = teacherId;
        this.position = position;
        this.taughtCourses = (taughtCourses == null) ? new ArrayList<>() : taughtCourses;
        this.researchPapers = new ArrayList<>();
        this.researchProjects = new ArrayList<>();
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
        Request request = new Request(
                "Request #" + System.currentTimeMillis(),
                "Complaint about student " + student.getFullName() + ", urgency " + urgency + ": " + reason,
                this
        );
        System.out.println(request.getRequestInfo());
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

    @Override
    public int calculateHIndex() {
        return HIndexCalculator.calculate(researchPapers);
    }

    @Override
    public void printPapers(Comparator<ResearchPaper> comparator) {
        List<ResearchPaper> copy = new ArrayList<>(researchPapers);
        if (comparator != null) {
            copy.sort(comparator);
        }
        for (ResearchPaper paper : copy) {
            System.out.println(paper.getCitation(enums.Format.PLAIN_TEXT));
        }
    }

    @Override
    public List<ResearchProject> getResearchProjects() {
        return new ArrayList<>(researchProjects);
    }

    @Override
    public List<ResearchPaper> getResearchPapers() {
        return new ArrayList<>(researchPapers);
    }

    @Override
    public void publishPaper(ResearchPaper paper) {
        if (paper != null) {
            researchPapers.add(paper);
        }
    }

    @Override
    public void joinProject(ResearchProject project) {
        if (project != null && !researchProjects.contains(project)) {
            researchProjects.add(project);
            project.addParticipant(this);
        }
    }
}
