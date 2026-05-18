package models.news;

import models.research.ResearchPaper;
import models.users.User;
import patterns.PaperNewsFactory;
import patterns.TopResearcherNewsFactory;

public class NewsGenerator {
    public static News createFromPaper(ResearchPaper paper) {
        return new PaperNewsFactory(paper).generate();
    }
    
    public static News createTopResearcherNews(User researcher) {
        return new TopResearcherNewsFactory(researcher).generate();
    }
}
