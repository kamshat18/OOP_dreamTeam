package OOP_dreamTeam.interfaces;
import OOP_dreamTeam.models.ResearchPaper;
import java.util.Comparator;
import java.util.List;

public interface Researcher {
    int calculateHIndex();
    void printPapers(Comparator<ResearchPaper> comparator);
    List<ResearchPaper> getResearchPapers();
    void addResearchPaper(ResearchPaper paper);
    
}
