package models.messaging;

import java.io.Serializable;
import models.users.User;

public class Comment implements Serializable {
    private static final long serialVersionUID = 1L;

    private String text;
    private User author;

    public Comment(String text, User author) {
        this.text = text;
        this.author = author;
    }

    public String getText() {
        return text;
    }
}
