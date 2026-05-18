package models;

import java.util.ArrayList;
import java.util.List;

public class Journal {
    private String name;
    private List<User> subscribers = new ArrayList<>();

    public Journal(String name) {
        this.name = name;
    }

    public void subscribe(User user) {
        if (user != null && !subscribers.contains(user)) {
            subscribers.add(user);
        }
    }

    public void unsubscribe(User user) {
        subscribers.remove(user);
    }

    public void publishPaper(ResearchPaper paper) {
        notifySubscribers(paper);
    }

    private void notifySubscribers(ResearchPaper paper) {
        for (User user : subscribers) {
            System.out.println(user.getFullName() + " notified about: " + paper.getTitle());
        }
    }
}