package models;

import java.util.Date;

public class OfficialMessage extends Message {
    private final String subject;
    private final Date eventDate;
    private final String room;

    public OfficialMessage(User sender, User receiver, String subject, String text, Date eventDate, String room) {
        super(sender, receiver, text);
        this.subject = subject;
        this.eventDate = eventDate == null ? null : new Date(eventDate.getTime());
        this.room = room;
    }

    public String getSubject() {
        return subject;
    }

    public Date getEventDate() {
        return eventDate == null ? null : new Date(eventDate.getTime());
    }

    public String getRoom() {
        return room;
    }

    @Override
    public String toString() {
        return "Official message: " + subject + " | " + getText() +
                (room == null ? "" : " | room: " + room) +
                (eventDate == null ? "" : " | event date: " + eventDate);
    }
}
