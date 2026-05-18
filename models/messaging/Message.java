package models.messaging;

import java.io.Serializable;
import java.util.Date;
import models.users.User;

public class Message implements Serializable {
    private final User sender;
    private final User receiver;
    private final String text;
    private final Date date;

    public Message(User sender, User receiver, String text) {
        this.sender = sender;
        this.receiver = receiver;
        this.text = text;
        this.date = new Date();
    }

    public String getText() {
        return text;
    }

    public User getSender() {
        return sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public Date getDate() {
        return new Date(date.getTime());
    }

    @Override
    public String toString() {
        return "[" + date + "] " +
                (sender == null ? "unknown" : sender.getFullName()) +
                " -> " +
                (receiver == null ? "unknown" : receiver.getFullName()) +
                ": " + text;
    }
}
