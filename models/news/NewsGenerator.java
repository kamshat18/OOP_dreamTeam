package models;

import enums.NewsType;

public class NewsGenerator {
    public static News createFromPaper(ResearchPaper paper) {
        return new models.News("New paper", paper.getTitle(), NewsType.NORMAL);
    }
    
    public static News createTopResearcherNews(User researcher) {
    if (researcher == null) return null;

    return new News("Top Researcher", researcher.getFullName(), NewsType.NORMAL);
}
}