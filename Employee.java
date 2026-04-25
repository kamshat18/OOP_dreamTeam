import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Employee extends User {
    private double salary;
    private Date hireDate;
    private String employeeId;

    public void sendMessage(Employee receiver, String content) {
    }
    public List<Message> viewMessages() {
        return new ArrayList<>();
    }
    public double getSalary() {
        return salary;
    }
}