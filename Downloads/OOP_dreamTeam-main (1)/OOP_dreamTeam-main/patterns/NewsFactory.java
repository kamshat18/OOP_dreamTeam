package patterns;

import models.News;

public abstract class NewsFactory {
    public News generate() {
        return createNews();
    }

    protected abstract News createNews();
}
