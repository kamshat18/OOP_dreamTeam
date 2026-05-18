package models.users;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import models.messaging.Message;

public class Employee extends User {
    private double salary;
    private Date hireDate;
    private String employeeId;
    private final List<Message> messages;

    public Employee(String id, String fullName, String email, String password, String language,
                    double salary, Date hireDate, String employeeId) {
        super(id, fullName, email, password, language);
        this.salary = salary;
        this.hireDate = hireDate;
        this.employeeId = employeeId;
        this.messages = new ArrayList<>();
    }
    public void sendMessage(Employee receiver, String content) {
        if (receiver == null || content == null || content.trim().isEmpty()) {
            return;
        }
        Message message = new Message(this, receiver, content);
        messages.add(message);
        receiver.messages.add(message);
    }
    public List<Message> viewMessages() {
        return new ArrayList<>(messages);
    }
    public double getSalary() {
        return salary;
    }

    public String getEmployeeId() {
        return employeeId;
    }
}
