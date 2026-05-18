package models;

import interfaces.Researcher;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class ResearchEmployee extends Employee implements Researcher {
    private final List<ResearchPaper> researchPapers;
    private final List<ResearchProject> researchProjects;

    public ResearchEmployee(String id, String fullName, String email, String password, String language,
                            double salary, Date hireDate, String employeeId) {
        super(id, fullName, email, password, language, salary, hireDate, employeeId);
        this.researchPapers = new ArrayList<>();
        this.researchProjects = new ArrayList<>();
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
