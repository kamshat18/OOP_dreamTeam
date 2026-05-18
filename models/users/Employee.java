package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Employee extends User {
    private double salary;
    private Date hireDate;
    private String employeeId;

    public Employee(String id, String fullName, String email, String password, String language,
                    double salary, Date hireDate, String employeeId) {
        super(id, fullName, email, password, language);
        this.salary = salary;
        this.hireDate = hireDate;
        this.employeeId = employeeId;
    }
    public void sendMessage(Employee receiver, String content) {
    }
    public List<Message> viewMessages() {
        return new ArrayList<>();
    }
    public double getSalary() {
        return salary;
    }
}