package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class News {
    private NewsType type;
    private String title;
    private String content;
    private Date date;
    private List<Comment> comments = new ArrayList<>();
    private boolean pinned;

    public News(String title, String content,NewsType type) {
        this.title = title;
        this.content = content;
        this.date = new Date();
        this.type = type;
        if (type == NewsType.RESEARCH) {
        pin();
        }
    }

    public void addComment(Comment comment) {
        if (comment != null) {
            comments.add(comment);
        }
    }

    public void pin() {
        this.pinned = true;
    }

    public String getTitle() {
        return title;
    }
}