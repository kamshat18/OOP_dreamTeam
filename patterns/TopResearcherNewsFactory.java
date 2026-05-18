package patterns;

import enums.NewsType;
import models.news.News;
import models.users.User;

public class TopResearcherNewsFactory extends NewsFactory {
    private final User researcher;

    public TopResearcherNewsFactory(User researcher) {
        this.researcher = researcher;
    }

    @Override
    protected News createNews() {
        String name = researcher == null ? "Unknown researcher" : researcher.getFullName();
        return new News("Top Researcher", name, NewsType.RESEARCH);
    }
}
