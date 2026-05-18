package OOP_dreamTeam.models;

import OOP_dreamTeam.interfaces.Researcher;
import exceptions.SupervisorException;
import models.Course;
import models.Mark;

import java.util.List;

public class MasterStudent extends GraduateStudent {
    private int courseWorkCredits;

    public MasterStudent(String id, String fullName, String email, String password, String language,
                         String studentId, String major, int yearOfStudy, double gpa, int credits,
                         List<Course> enrolledCourses, List<Mark> marks, String thesisTitle,
                         Researcher supervisor, int courseWorkCredits) throws SupervisorException {
        super(id, fullName, email, password, language, studentId, major, yearOfStudy, gpa, credits,
                enrolledCourses, marks, thesisTitle, supervisor);
        this.courseWorkCredits = courseWorkCredits;
    }

    public int getCourseWorkCredits() {
        return courseWorkCredits;
    }
}
