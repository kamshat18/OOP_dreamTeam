package models.academic;

import interfaces.Observable;
import interfaces.Observer;
import java.util.ArrayList;
import java.util.List;
import models.research.ResearchPaper;
import models.users.User;

public class Journal implements Observable {
    private String name;
    private List<User> subscribers = new ArrayList<>();
    private List<Observer> observers = new ArrayList<>();

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
        notifyObservers("New paper in " + name + ": " + (paper == null ? "unknown" : paper.getTitle()));
    }

    private void notifySubscribers(ResearchPaper paper) {
        for (User user : subscribers) {
            System.out.println(user.getFullName() + " notified about: " + paper.getTitle());
        }
    }

    @Override
    public void addObserver(Observer observer) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
        }
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }
}
