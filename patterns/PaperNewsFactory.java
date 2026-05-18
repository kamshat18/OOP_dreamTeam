package patterns;

import enums.NewsType;
import models.News;
import models.ResearchPaper;

public class PaperNewsFactory extends NewsFactory {
    private final ResearchPaper paper;

    public PaperNewsFactory(ResearchPaper paper) {
        this.paper = paper;
    }

    @Override
    protected News createNews() {
        String title = paper == null ? "New paper" : "New paper: " + paper.getTitle();
        String content = paper == null ? "Research paper was published" : paper.getCitation(enums.Format.PLAIN_TEXT);
        return new News(title, content, NewsType.RESEARCH);
    }
}
