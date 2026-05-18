package patterns;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import models.academic.Course;
import models.news.News;
import models.organization.Organization;
import models.organization.Request;
import models.research.ResearchPaper;
import models.research.ResearchProject;
import models.users.User;

public class DataStorage implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String DEFAULT_FILE = "data_storage.ser";
    private static DataStorage instance;

    private final List<User> users;
    private final List<Course> courses;
    private final List<ResearchPaper> researchPapers;
    private final List<ResearchProject> researchProjects;
    private final List<News> news;
    private final List<Request> requests;
    private final List<Organization> organizations;

    private DataStorage() {
        this.users = new ArrayList<>();
        this.courses = new ArrayList<>();
        this.researchPapers = new ArrayList<>();
        this.researchProjects = new ArrayList<>();
        this.news = new ArrayList<>();
        this.requests = new ArrayList<>();
        this.organizations = new ArrayList<>();
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

    public List<News> getNews() {
        return Collections.unmodifiableList(news);
    }

    public List<Request> getRequests() {
        return Collections.unmodifiableList(requests);
    }

    public List<Organization> getOrganizations() {
        return Collections.unmodifiableList(organizations);
    }

    public void addUser(User user) {
        if (user != null) {
            users.add(user);
        }
    }

    public boolean removeUser(String idOrEmail) {
        if (idOrEmail == null) {
            return false;
        }
        return users.removeIf(user -> idOrEmail.equals(user.getId()) || idOrEmail.equals(user.getEmail()));
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

    public void addNews(News item) {
        if (item != null) {
            news.add(item);
        }
    }

    public void addRequest(Request request) {
        if (request != null) {
            requests.add(request);
        }
    }

    public void addOrganization(Organization organization) {
        if (organization != null) {
            organizations.add(organization);
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
            if (!(obj instanceof DataStorage)) {
                throw new IOException("Invalid data format for DataStorage.");
            }
            DataStorage loaded = (DataStorage) obj;
            instance = loaded;
            return instance;
        }
    }
}
