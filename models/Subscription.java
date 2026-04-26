package models;

public class Subscription {
    private User user;
    private Journal journal;

    public Subscription(User user, Journal journal) {
        this.user = user;
        this.journal = journal;
    }

    public User getUser() {
        return user;
    }

    public Journal getJournal() {
        return journal;
    }
}