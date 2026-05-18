package OOP_dreamTeam.models;

import OOP_dreamTeam.interfaces.Researcher;
import exceptions.SupervisorException;
import models.Course;
import models.Mark;
import models.Organization;
import models.Student;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class GraduateStudent extends Student implements Researcher {
    private String thesisTitle;
    private Researcher supervisor;
    private final List<ResearchPaper> publishedPapers;
    private final List<ResearchProject> researchProjects;

    public GraduateStudent(String id, String fullName, String email, String password, String language,
                           String studentId, String major, int yearOfStudy, double gpa, int credits,
                           List<Course> enrolledCourses, List<Mark> marks, String thesisTitle,
                           Researcher supervisor) throws SupervisorException {
        super(id, fullName, email, password, language, studentId, major, yearOfStudy, gpa, credits, enrolledCourses, marks);
        this.thesisTitle = thesisTitle;
        this.publishedPapers = new ArrayList<>();
        this.researchProjects = new ArrayList<>();
        setSupervisor(supervisor);
    }

    public boolean defendThesis() {
        return thesisTitle != null && !thesisTitle.isBlank() && !publishedPapers.isEmpty();
    }

    public Researcher getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Researcher supervisor) throws SupervisorException {
        if (supervisor != null && supervisor.calculateHIndex() < 3) {
            throw new SupervisorException("Supervisor h-index must be at least 3.");
        }
        this.supervisor = supervisor;
    }

    @Override
    public int calculateHIndex() {
        return HIndexCalculator.calculate(publishedPapers);
    }

    @Override
    public void printPapers(Comparator<ResearchPaper> comparator) {
        List<ResearchPaper> copy = new ArrayList<>(publishedPapers);
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
        return new ArrayList<>(publishedPapers);
    }

    @Override
    public void publishPaper(ResearchPaper paper) {
        if (paper != null) {
            publishedPapers.add(paper);
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
