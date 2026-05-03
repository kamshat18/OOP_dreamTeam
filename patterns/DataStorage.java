package OOP_dreamTeam.patterns;
import OOP_dreamTeam.models.ResearchPaper;
import OOP_dreamTeam.models.ResearchProject;
import models.Course;
import models.User;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataStorage implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String DEFAULT_FILE = "data_storage.ser";
    private static DataStorage instance;

    private final List<User> users;
    private final List<Course> courses;
    private final List<ResearchPaper> researchPapers;
    private final List<ResearchProject> researchProjects;

    private DataStorage() {
        this.users = new ArrayList<>();
        this.courses = new ArrayList<>();
        this.researchPapers = new ArrayList<>();
        this.researchProjects = new ArrayList<>();
    }

    public static synchronized DataStorage getInstance() {
        if (instance == null) {
            instance = new DataStorage();
        }
        return instance;
    }

    public List<User> getUsers() {
        return Collections.unmodifiableList(users);
    }

    public List<Course> getCourses() {
        return Collections.unmodifiableList(courses);
    }

    public List<ResearchPaper> getResearchPapers() {
        return Collections.unmodifiableList(researchPapers);
    }

    public List<ResearchProject> getResearchProjects() {
        return Collections.unmodifiableList(researchProjects);
    }

    public void addUser(User user) {
        if (user != null) {
            users.add(user);
        }
    }

    public void addCourse(Course course) {
        if (course != null) {
            courses.add(course);
        }
    }

    public void addResearchPaper(ResearchPaper paper) {
        if (paper != null) {
            researchPapers.add(paper);
        }
    }

    public void addResearchProject(ResearchProject project) {
        if (project != null) {
            researchProjects.add(project);
        }
    }

    public synchronized void save() throws IOException {
        save(DEFAULT_FILE);
    }

    public synchronized void save(String filePath) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(this);
        }
    }

    public static synchronized DataStorage load() throws IOException, ClassNotFoundException {
        return load(DEFAULT_FILE);
    }

    public static synchronized DataStorage load(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            Object obj = ois.readObject();
            if (!(obj instanceof DataStorage loaded)) {
                throw new IOException("Invalid data format for DataStorage.");
            }
            instance = loaded;
            return instance;
        }
    }
}
