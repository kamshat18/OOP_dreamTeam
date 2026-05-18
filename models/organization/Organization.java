package models;

import java.util.ArrayList;
import java.util.List;

public class Organization {
    private String name;
    private List<Student> members = new ArrayList<>();
    private Student head;

    public Organization(String name) {
        this.name = name;
    }

    public void addMember(Student student) {
        if (student != null && !members.contains(student)) {
            members.add(student);
        }
    }

    public void setHead(Student student) {
        this.head = student;
    }
}