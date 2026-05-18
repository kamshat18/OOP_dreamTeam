package interfaces;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import models.research.ResearchPaper;
import models.research.ResearchProject;

public interface Researcher extends Serializable {
    int calculateHIndex();
    void printPapers(Comparator<ResearchPaper> comparator);
    List<ResearchProject> getResearchProjects();
    List<ResearchPaper> getResearchPapers();
    void publishPaper(ResearchPaper paper);
    void joinProject(ResearchProject project);
}
