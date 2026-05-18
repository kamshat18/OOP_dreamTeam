package patterns;

import models.news.News;

public abstract class NewsFactory {
    public News generate() {
        return createNews();
    }

    protected abstract News createNews();
}
