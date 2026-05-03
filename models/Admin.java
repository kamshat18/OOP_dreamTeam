package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class Admin extends Employee {
    private final List<String> logFiles;
    private final List<User> users;
    private final List<UserLog> userLogs;

    public Admin(String id, String fullName, String email, String password, String language,
                 double salary, Date hireDate, String employeeId) {
        super(id, fullName, email, password, language, salary, hireDate, employeeId);
        this.logFiles = new ArrayList<>();
        this.users = new ArrayList<>();
        this.userLogs = new ArrayList<>();
    }

    public void addUser(User user) {
        if (user == null) {
            return;
        }
        users.add(user);
        logFiles.add("ADD_USER:" + user.getId());
        userLogs.add(new UserLog(getId(), "ADD_USER:" + user.getId()));
    }

    public void removeUser(String userId) {
        if (userId == null) {
            return;
        }
        Iterator<User> it = users.iterator();
        while (it.hasNext()) {
            User user = it.next();
            if (userId.equals(user.getId())) {
                it.remove();
                logFiles.add("REMOVE_USER:" + userId);
                userLogs.add(new UserLog(getId(), "REMOVE_USER:" + userId));
                return;
            }
        }
    }

    public void updateUser(User user) {
        if (user == null) {
            return;
        }
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(user.getId())) {
                users.set(i, user);
                logFiles.add("UPDATE_USER:" + user.getId());
                userLogs.add(new UserLog(getId(), "UPDATE_USER:" + user.getId()));
                return;
            }
        }
        users.add(user);
        logFiles.add("UPSERT_USER:" + user.getId());
        userLogs.add(new UserLog(getId(), "UPSERT_USER:" + user.getId()));
    }

    public List<String> viewLogFiles() {
        return Collections.unmodifiableList(logFiles);
    }

    public String generateSystemReport() {
        return "Users: " + users.size() + ", log entries: " + logFiles.size();
    }

    public List<UserLog> getUserLogs() {
        return Collections.unmodifiableList(userLogs);
    }
}
