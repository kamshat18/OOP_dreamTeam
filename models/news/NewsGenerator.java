package models;

public class NewsGenerator {
    public static News createFromPaper(ResearchPaper paper) {
        return new News("New paper", paper.getTitle());
    }
    
    public static News createTopResearcherNews(User researcher) {
    if (researcher == null) return null;

    return new News("Top Researcher", researcher.getFullName());
}
}