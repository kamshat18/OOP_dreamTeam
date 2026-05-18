package models;

import java.util.ArrayList;
import java.util.List;

public class Organization {
    private String orgId;
    private String name;
    private Student head;
    private List<Student> members = new ArrayList<>();

    public Organization(String orgId, String name) {
        this.orgId = orgId;
        this.name = name;
    }

    public void addMember(Student student) {
        if (student != null && !members.contains(student)) {
            members.add(student);
        }
    }
    public void removeMember(Student student) {
        members.remove(student);
    }

    public void electHead(Student student) {
        if (student != null && members.contains(student)) this.head = student;
    }

    public void setHead(Student student) {
        this.head = student;
    }
}