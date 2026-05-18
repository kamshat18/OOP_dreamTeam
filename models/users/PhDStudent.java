package models.users;

import exceptions.SupervisorException;
import interfaces.Researcher;
import java.util.ArrayList;
import java.util.List;
import models.academic.Course;
import models.academic.Mark;
import models.research.ResearchPaper;

public class PhDStudent extends GraduateStudent {
    private String dissertationTopic;
    private final List<ResearchPaper> publicationsRequired;

    public PhDStudent(String id, String fullName, String email, String password, String language,
                      String studentId, String major, int yearOfStudy, double gpa, int credits,
                      List<Course> enrolledCourses, List<Mark> marks, String thesisTitle,
                      Researcher supervisor, String dissertationTopic, List<ResearchPaper> publicationsRequired)
            throws SupervisorException {
        super(id, fullName, email, password, language, studentId, major, yearOfStudy, gpa, credits,
                enrolledCourses, marks, thesisTitle, supervisor);
        this.dissertationTopic = dissertationTopic;
        this.publicationsRequired = publicationsRequired == null ? new ArrayList<>() : new ArrayList<>(publicationsRequired);
    }

    public String getDissertationTopic() {
        return dissertationTopic;
    }

    public List<ResearchPaper> getPublicationsRequired() {
        return new ArrayList<>(publicationsRequired);
    }
}
