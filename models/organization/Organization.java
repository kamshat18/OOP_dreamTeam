package models.organization;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import models.users.Student;

public class Organization implements Serializable {
    private static final long serialVersionUID = 1L;

    private String orgId;
    private String name;
    private Student head;
    private List<Student> members = new ArrayList<>();

    public Organization(String orgId, String name) {
        this.orgId = orgId;
        this.name = name;
    }
    public String getOrgId() {
        return orgId;
    }
    public String getName() {
        return name;
    }
    public Student getHead() {
        return head;
    }
    public List<Student> getMembers() {
        return new ArrayList<>(members);
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

    @Override
    public String toString() {
        return "Organization " + orgId + ": " + name +
                ", head: " + (head == null ? "not elected" : head.getFullName()) +
                ", members: " + members.size();
    }
}
